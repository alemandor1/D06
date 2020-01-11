
package acme.features.authenticated.userAccount;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.CustomCommand;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedUserAccountListService implements AbstractListService<Authenticated, UserAccount> {

	@Autowired
	AuthenticatedUserAccountRepository repository;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;
		return this.repository.findFirstUserId(request.getModel().getInteger("messageThreadId")).equals(request.getPrincipal().getAccountId());
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("messageThreadId", request.getModel().getInteger("messageThreadId"));

		request.unbind(entity, model, "username", "identity.name", "identity.surname");
	}

	@Override
	public Collection<UserAccount> findMany(final Request<UserAccount> request) {
		assert request != null;

		Collection<UserAccount> res = new ArrayList<UserAccount>();
		int MtId = request.getModel().getInteger("messageThreadId");

		if (request.getCommand().equals(CustomCommand.LIST_NOT_INVOLVED_USERS)) {
			res = this.repository.findManyUsers(MtId);
		} else if (request.getCommand().equals(CustomCommand.LIST_INVOLVED_USERS)) {
			res = this.repository.findUserOfMessageThread(MtId);
		}
		res.forEach(x -> x.getRoles().size());

		return res;
	}
}
