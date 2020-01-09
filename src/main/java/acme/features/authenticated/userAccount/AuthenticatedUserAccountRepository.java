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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedUserAccountRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select ua from UserAccount ua where ua.enabled = true and ua.username != 'administrator' and ua.id not in(select u.id from MessageThread mt join mt.users u where mt.id = ?1)")
	Collection<UserAccount> findManyUsers(int id);

	@Query("select u from MessageThread mt join mt.users u where mt.id = ?1")
	Collection<UserAccount> findUserOfMessageThread(int id);

	@Query("select u.id from MessageThread mt join mt.users u where mt.id = ?1")
	List<Integer> isCreatorUser(int id);
}
