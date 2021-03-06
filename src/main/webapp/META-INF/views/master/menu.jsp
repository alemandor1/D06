
<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.list-announcement" action="/anonymous/announcement/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-company-record" action="/anonymous/company-record/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-investor-records" action="/anonymous/investor-records/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.request-auditor" action="/administrator/request-auditor/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-announcement" action="/administrator/announcement/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-announcement" action="/administrator/announcement/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-company-record" action="/administrator/company-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-company-record" action="/administrator/company-record/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-investor-records" action="/administrator/investor-records/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-investor-records" action="/administrator/investor-records/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-challenges" action="/administrator/challenge/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-challenge" action="/administrator/challenge/create"/>
			<acme:menu-suboption code="master.menu.administrator.spam" action="/administrator/spam/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.list-announcement" action="/authenticated/announcement/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-company-record" action="/authenticated/company-record/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-investor-records" action="/authenticated/investor-records/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-challenge" action="/authenticated/challenge/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-active-jobs" action="/authenticated/job/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-message-thread" action="/authenticated/message-thread/list"/>
			<acme:menu-suboption code="master.menu.authenticated.create-message-thread" action="/authenticated/message-thread/create"/>
		</acme:menu-option>


		
		<acme:menu-option code="master.menu.employer" access="hasRole('Employer')">
			<acme:menu-suboption code="master.menu.employer.list-jobs-mine" action="/employer/job/list-mine"/>
			<acme:menu-suboption code="master.menu.employer.list-applications-mine" action="/employer/application/list-mine"/>
			<acme:menu-suboption code="master.menu.employer.create-job" action="/employer/job/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.worker" access="hasRole('Worker')">
			<acme:menu-suboption code="master.menu.worker.list-applications-mine" action="/worker/application/list-mine"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list-jobs-write-auditor" action="/auditor/job/list-jobs-write-audit"/>
			<acme:menu-suboption code="master.menu.auditor.list-non-jobs-write-auditor" action="/auditor/job/list-non-jobs-write-audit"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-employer" action="/authenticated/employer/create" access="!hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.employer" action="/authenticated/employer/update" access="hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-worker" action="/authenticated/worker/create" access="!hasRole('Worker')"/>
			<acme:menu-suboption code="master.menu.user-account.worker" action="/authenticated/worker/update" access="hasRole('Worker')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/request-auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
		
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

