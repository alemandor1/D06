/*
 * CustomCommand.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import acme.framework.components.Command;

public enum CustomCommand implements Command {
	LIST_MINE, LIST_JOBS_WRITE_AUDIT, LIST_AUDITS_JOB, LIST_NON_JOBS_WRITE_AUDIT, LIST_NON_AUDITS_JOB, ACCEPT, LIST_NOT_INVOLVED_USERS, LIST_INVOLVED_USERS, ADD_USER, DELETE_USER;
}
