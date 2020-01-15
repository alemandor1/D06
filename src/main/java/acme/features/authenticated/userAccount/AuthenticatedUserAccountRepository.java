/*
 * AuthenticatedUserAccountRepository.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.userAccount;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedUserAccountRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select ua from UserAccount ua where ua.enabled = true and ua.id not in(select mt.userAccount.id from MessageThreadUserAccount mt where mt.messageThread.id = ?1)")
	Collection<UserAccount> findManyUsers(int idMessageThread);

	@Query("select mt.userAccount from MessageThreadUserAccount mt where mt.messageThread.id = ?1")
	Collection<UserAccount> findUserOfMessageThread(int idMessageThread);

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
