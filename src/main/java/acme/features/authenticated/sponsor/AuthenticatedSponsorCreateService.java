
package acme.features.authenticated.sponsor;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Sponsor;
import acme.entities.roles.Worker;
import acme.entities.spams.Spam;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedSponsorCreateService implements AbstractCreateService<Authenticated, Sponsor> {

	@Autowired
	private AuthenticatedSponsorRepository repository;


	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;

		return !request.getPrincipal().hasRole(Worker.class);
	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "organisationName", "creditCard");
	}

	@Override
	public Sponsor instantiate(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Sponsor();
		result.setUserAccount(userAccount);

		return result;

	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Spam spam = this.repository.findSpam();
		String[] spamWords = spam.getSpam().split(",");

		int spamOrganisationName = (int) (Arrays.asList(spamWords).stream().filter(x -> entity.getOrganisationName().toLowerCase().contains(x.toLowerCase().trim())).count() * 100 / spamWords.length);
		boolean isSponsorSpam = spamOrganisationName <= spam.getThreshold();
		errors.state(request, isSponsorSpam, "organisationName", "authenticated.sponsor.error.spam-entity");
	}

	@Override
	public void create(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}
}