
package acme.features.auditor.duties;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.entities.jobs.JobStatus;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDutiesRepository extends AbstractRepository {

	@Query("select d from Duty d where d.descriptor.id = (select j.descriptor.id from Job j where j.id = ?1)")
	Collection<Duty> findMany(int id);

	@Query("select d from Duty d where d.id = ?1")
	Duty findOne(int id);

	@Query("select j.title from Job j where j.descriptor.id = ?1")
	String findJobTitle(int id);

	@Query("select count(d) > 0 from Duty d where d.descriptor.id = (select j.descriptor.id from Job j where j.id = ?1 and j.status = ?2 and ((j.id not in(select a.job.id from AuditRecord a where a.auditor.id = ?3) and now()<=j.deadline) or (j.id in(select a.job.id from AuditRecord a where a.auditor.id = ?3))))")
	boolean isCorrectJob(int idJob, JobStatus jobStatus, int idAuditor);

	@Query("select count(j) > 0 from Job j where j.descriptor.id = (select d.descriptor.id from Duty d where d.id = ?1) and j.status = ?2 and ((j.id not in(select a.job.id from AuditRecord a where a.auditor.id = ?3) and now()<=j.deadline) or (j.id in(select a.job.id from AuditRecord a where a.auditor.id = ?3)))")
	boolean isCorrectDuty(int id, JobStatus published, int idAuditor);
}
