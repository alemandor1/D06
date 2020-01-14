
package acme.features.employer.justifications;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.spams.Spam;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerJustificationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id = ?1")
	Application findOne(int id);

	@Query("select count(a) > 0 from Application a where a.id = ?1 and a.job.employer.id = ?2 and a.status = ?3")
	boolean isCorrectEmployer(int idApplication, int idEmployer, ApplicationStatus status);

	@Query("select s from Spam s")
	Spam findSpam();
}
