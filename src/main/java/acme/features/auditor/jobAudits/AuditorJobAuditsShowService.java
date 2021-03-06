
package acme.features.auditor.jobAudits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.jobs.JobStatus;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorJobAuditsShowService implements AbstractShowService<Auditor, Job> {

	@Autowired
	AuditorJobAuditsRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return this.repository.isDraftJob(request.getModel().getInteger("id"), JobStatus.PUBLISHED, request.getPrincipal().getActiveRoleId());
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "status", "title", "deadline", "salary", "link", "descriptor.description");
	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;
		Job res = this.repository.findOne(request.getModel().getInteger("id"));
		res.getEmployer().getUserAccount().getRoles().size();

		return res;
	}

}
