
package acme.features.authenticated.messageThreads;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.CustomCommand;
import acme.entities.messageThreads.MessageThread;
import acme.entities.messageThreads.MessageThreadUserAccount;
import acme.entities.messages.Message;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedMessageThreadUpdateService implements AbstractUpdateService<Authenticated, MessageThread> {

	@Autowired
	AuthenticatedMessageThreadRepository repository;


	@Override
	public boolean authorise(final Request<MessageThread> request) {
		assert request != null;
		return this.repository.findFirstUserId(request.getModel().getInteger("messageThreadId")).equals(request.getPrincipal().getAccountId());
	}

	@Override
	public void bind(final Request<MessageThread> request, final MessageThread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<MessageThread> request, final MessageThread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("username", "");
		model.setAttribute("creatorUser", this.repository.findFirstUserId(entity.getId()).equals(request.getPrincipal().getAccountId()));
		model.setAttribute("messageThreadId", request.getModel().getInteger("messageThreadId"));

		request.unbind(entity, model, "title", "moment");
	}

	@Override
	public MessageThread findOne(final Request<MessageThread> request) {
		assert request != null;
		MessageThread res = this.repository.findOneMessageThreadById(request.getModel().getInteger("messageThreadId"));

		return res;
	}

	@Override
	public void validate(final Request<MessageThread> request, final MessageThread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		String username = request.getModel().getString("username");
		UserAccount ua = this.repository.findUserAccountByUsername(username);
		errors.state(request, ua != null, "username", "authenticated.message-thread.error.username");

		if (ua != null) {
			boolean isUserInMessageThread = this.repository.isUserInMessageThread(entity.getId(), ua.getId());
			if (request.getCommand().equals(CustomCommand.ADD_USER)) {
				errors.state(request, !isUserInMessageThread, "username", "authenticated.message-thread.error.user-already-in-message-thread");
			} else if (request.getCommand().equals(CustomCommand.DELETE_USER)) {
				errors.state(request, isUserInMessageThread, "username", "authenticated.message-thread.error.user-not-in-message-thread");
			}
		}

	}

	@Override
	public void update(final Request<MessageThread> request, final MessageThread entity) {
		assert request != null;
		assert entity != null;

		String username = request.getModel().getString("username");
		UserAccount ua = this.repository.findUserAccountByUsername(username);
		ua.getRoles().size();

		MessageThreadUserAccount messageThreadUserAccount;
		if (request.getCommand().equals(CustomCommand.DELETE_USER)) {
			messageThreadUserAccount = this.repository.findMessageThreadUserAccount(ua.getId(), entity.getId());
			this.repository.delete(messageThreadUserAccount);
		} else if (request.getCommand().equals(CustomCommand.ADD_USER)) {
			messageThreadUserAccount = new MessageThreadUserAccount();
			messageThreadUserAccount.setUserAccount(ua);
			messageThreadUserAccount.setMessageThread(entity);
			this.repository.save(messageThreadUserAccount);
		}

		boolean isEmptyListUsersMessageThread = this.repository.isEmptyListUsersMessageThread(request.getModel().getInteger("messageThreadId"));
		if (isEmptyListUsersMessageThread) {
			Collection<Message> messages = this.repository.findMessagesByIdMessageThread(request.getModel().getInteger("messageThreadId"));
			this.repository.deleteAll(messages);
			this.repository.delete(entity);
		} else {
			this.repository.save(entity);
		}
	}

}
