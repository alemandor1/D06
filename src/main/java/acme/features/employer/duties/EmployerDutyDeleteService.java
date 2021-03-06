
package acme.features.employer.duties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.jobs.JobStatus;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class EmployerDutyDeleteService implements AbstractDeleteService<Employer, Duty> {

	@Autowired
	EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		return this.repository.isCorrectDutyUpdateDelete(request.getModel().getInteger("id"), request.getPrincipal().getActiveRoleId(), JobStatus.DRAFT);
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "time");

	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty res = this.repository.findOne(request.getModel().getInteger("id"));
		return res;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
