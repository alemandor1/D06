
package acme.features.sponsor.comercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorCommercialBannerRepository extends AbstractRepository {

	@Query("select cb from CommercialBanner cb where cb.sponsor.id = ?1")
	Collection<CommercialBanner> findMany(int idSponsor);

	@Query("select cb from CommercialBanner cb where cb.id = ?1")
	CommercialBanner findOneById(int id);

	@Query("select s from Sponsor s where s.id = ?1")
	Sponsor findSponsorById(int idSponsor);

	@Query("select count(b) > 0 from CommercialBanner b where b.id = ?1 and b.sponsor.id = ?2")
	boolean isCorrectCommercialBanner(int idBanner, int idSponsor);

}
