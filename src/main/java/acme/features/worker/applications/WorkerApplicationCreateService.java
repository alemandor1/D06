
package acme.features.worker.applications;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.jobs.Job;
import acme.entities.jobs.JobStatus;
import acme.entities.roles.Worker;
import acme.entities.spams.Spam;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class WorkerApplicationCreateService implements AbstractCreateService<Worker, Application> {

	@Autowired
	WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		return this.repository.isAuthoriseCreate(request.getModel().getInteger("idJob"), JobStatus.PUBLISHED);
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "status");

	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("idJob", request.getModel().getInteger("idJob"));

		request.unbind(entity, model, "reference", "statement", "skills", "qualifications");
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		assert request != null;

		Application res = new Application();

		Worker worker = this.repository.findWorker(request.getPrincipal().getActiveRoleId());
		res.setWorker(worker);

		res.setStatus(ApplicationStatus.PENDING);
		int id = request.getModel().getInteger("idJob");
		Job job = this.repository.findJobApplied(id);
		res.setJob(job);
		res.setSkills(worker.getSkills());
		res.setQualifications(worker.getQualifications());

		return res;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDuplicateTicker = this.repository.findApplicationByReference(entity.getReference()) != null;
		errors.state(request, !isDuplicateTicker, "reference", "worker.application.reference.error.duplicated");

		Spam spam = this.repository.findSpam();
		String[] spamWords = spam.getSpam().split(",");

		int spamQualification = (int) (Arrays.asList(spamWords).stream().filter(x -> entity.getQualifications().toLowerCase().contains(x.toLowerCase().trim())).count() * 100 / spamWords.length);
		int spamReference = (int) (Arrays.asList(spamWords).stream().filter(x -> entity.getReference().toLowerCase().contains(x.toLowerCase().trim())).count() * 100 / spamWords.length);
		int spamStatement = (int) (Arrays.asList(spamWords).stream().filter(x -> entity.getStatement().toLowerCase().contains(x.toLowerCase().trim())).count() * 100 / spamWords.length);
		int spamSkills = (int) (Arrays.asList(spamWords).stream().filter(x -> entity.getSkills().toLowerCase().contains(x.toLowerCase().trim())).count() * 100 / spamWords.length);
		int sumSpam = spamQualification + spamSkills + spamReference + spamStatement;
		boolean isWorkerSpam = sumSpam <= spam.getThreshold();
		errors.state(request, isWorkerSpam, "qualifications", "worker.application.error.spam-entity");

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		entity.setMoment(new Date(System.currentTimeMillis() - 1));
		this.repository.save(entity);

	}
}
