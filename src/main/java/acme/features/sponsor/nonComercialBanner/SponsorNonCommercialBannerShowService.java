
package acme.features.sponsor.nonComercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class SponsorNonCommercialBannerShowService implements AbstractShowService<Sponsor, NonCommercialBanner> {

	@Autowired
	private SponsorNonCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		return this.repository.isCorrectNonCommercialBanner(request.getModel().getInteger("id"), request.getPrincipal().getActiveRoleId());
	}

	@Override
	public void unbind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "slogan", "link", "picture", "jingle");

	}

	@Override
	public NonCommercialBanner findOne(final Request<NonCommercialBanner> request) {
		assert request != null;
		NonCommercialBanner result;
		result = this.repository.findOneById(request.getModel().getInteger("id"));
		return result;
	}

}
