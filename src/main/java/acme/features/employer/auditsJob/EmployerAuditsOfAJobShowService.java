
package acme.features.employer.auditsJob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.AuditRecordStatus;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class EmployerAuditsOfAJobShowService implements AbstractShowService<Employer, AuditRecord> {

	@Autowired
	EmployerAuditsOfAJobRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;

		return this.repository.isCorrectAuditRecord(request.getModel().getInteger("id"), AuditRecordStatus.PUBLISHED, request.getPrincipal().getActiveRoleId());
	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("username", entity.getAuditor().getUserAccount().getUsername());

		request.unbind(entity, model, "title", "status", "moment", "body", "job.title");

	}

	@Override
	public AuditRecord findOne(final Request<AuditRecord> request) {
		assert request != null;

		AuditRecord res = this.repository.findOne(request.getModel().getInteger("id"));

		return res;
	}

}
