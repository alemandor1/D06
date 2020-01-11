
package acme.features.authenticated.messageThreads;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messageThreads.MessageThread;
import acme.entities.messageThreads.MessageThreadUserAccount;
import acme.entities.messages.Message;
import acme.entities.spams.Spam;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessageThreadRepository extends AbstractRepository {

	@Query("select mt from MessageThread mt where mt.id = ?1")
	MessageThread findOneMessageThreadById(int id);

	@Query("select mt.messageThread from MessageThreadUserAccount mt where mt.userAccount.id = ?1")
	Collection<MessageThread> findMany(int idUserAccount);

	@Query("select count(mt) > 0 from MessageThreadUserAccount mt where mt.messageThread.id = ?1 and mt.userAccount.id = ?2")
	boolean isCorrectMT(int idMessageThread, int idUserAccount);

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findUserAccountByUsername(String username);

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findUserAccountById(int id);

	@Query("select count(mt) > 0 from MessageThreadUserAccount mt where mt.messageThread.id = ?1 and ?2 in(mt.userAccount.id)")
	boolean isUserInMessageThread(int idMessageThread, int idUserAccount);

	@Query("select m from MessageThreadUserAccount m where m.userAccount.id = ?1 and m.messageThread.id = ?2")
	MessageThreadUserAccount findMessageThreadUserAccount(int idUserAccount, int idMessageThread);

	@Query("select count(m) = 0 from MessageThreadUserAccount m where m.messageThread.id = ?1")
	boolean isEmptyListUsersMessageThread(int idMessageThread);

	@Query("select s from Spam s")
	Spam findSpam();

	@Query("select m from Message m where m.messageThread.id = ?1")
	Collection<Message> findMessagesByIdMessageThread(int idMessageThread);

	@Query("select mt.userAccount.id from MessageThreadUserAccount mt where mt.messageThread.id = ?1")
	List<Integer> findManagerUser(int idMessageThread, PageRequest pageRequest);

	default Integer findFirstUserId(final int idMessageThread) {
		Integer res;
		PageRequest page;
		List<Integer> list;

		page = PageRequest.of(0, 1);
		list = this.findManagerUser(idMessageThread, page);
		res = list.isEmpty() ? null : list.get(0);
		return res;
	}
}
