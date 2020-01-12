
package acme.features.sponsor.nonComercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorNonCommercialBannerRepository extends AbstractRepository {

	@Query("select ncb from NonCommercialBanner ncb where ncb.sponsor.id = ?1")
	Collection<NonCommercialBanner> findMany(int idSponsor);

	@Query("select ncb from NonCommercialBanner ncb where ncb.id = ?1")
	NonCommercialBanner findOneById(int id);

	@Query("select s from Sponsor s where s.id = ?1")
	Sponsor findSponsorById(int idSponsor);

	@Query("select count(b) > 0 from NonCommercialBanner b where b.id = ?1 and b.sponsor.id = ?2")
	boolean isCorrectNonCommercialBanner(int idBanner, int idSponsor);
}
