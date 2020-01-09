
package acme.features.auditor.jobAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.CustomCommand;
import acme.entities.jobs.Job;
import acme.entities.jobs.JobStatus;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class AuditorJobAuditsListService implements AbstractListService<Auditor, Job> {

	@Autowired
	AuditorJobAuditsRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "salary", "deadline");
	}

	@Override
	public Collection<Job> findMany(final Request<Job> request) {
		assert request != null;
		Collection<Job> res = null;

		if (request.isCommand(CustomCommand.LIST_JOBS_WRITE_AUDIT)) {
			res = this.repository.jobsAuditorHasWrittenAudit(request.getPrincipal().getActiveRoleId());
		} else if (request.isCommand(CustomCommand.LIST_NON_JOBS_WRITE_AUDIT)) {
			res = this.repository.jobsAuditorHasNotWrittenAudit(request.getPrincipal().getActiveRoleId(), JobStatus.PUBLISHED);
		}

		return res;
	}

}
