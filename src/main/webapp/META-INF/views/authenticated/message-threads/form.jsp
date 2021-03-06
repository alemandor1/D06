<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="false">
	<jstl:if test="${command != 'create' and command != 'show'}">
		<acme:form-hidden path="messageThreadId"/>
		<acme:form-textbox code="authenticated.message-threads.form.label.username" path="username"/>
		<acme:form-submit code="authenticated.message-threads.form.button.add-user" action="/authenticated/message-thread/add-user"/>
		<acme:form-submit code="authenticated.message-threads.form.button.delete-user" action="/authenticated/message-thread/delete-user"/>
	</jstl:if>
	<jstl:if test="${command == 'show' or command == 'create'}">
	<acme:form-textbox code="authenticated.message-threads.form.label.title" path="title"/>
	<jstl:if test="${command == 'show'}">
	<acme:form-moment code="authenticated.message-threads.form.label.moment" path="moment"/>
	</jstl:if>
  
    <acme:form-submit test="${command != 'create' }" code="authenticated.message-threads.form.button.messages" action="/authenticated/message/list?messageThreadId=${id}" method="get"/>
    <acme:form-submit test="${command != 'create' and creatorUser}" code="authenticated.message-threads.form.button.list-users-message-thread" action="/authenticated/user-account/list-involved-users?messageThreadId=${id}" method="get"/>
    <acme:form-submit test="${command != 'create' and creatorUser}" code="authenticated.message-threads.form.button.list-all-users" action="/authenticated/user-account/list-not-involved-users?messageThreadId=${id}" method="get"/>
    <acme:form-submit test="${command != 'create' and creatorUser}" code="authenticated.message-threads.form.button.action-user" action="/authenticated/message-thread/update?messageThreadId=${id}" method="get"/>
	<acme:form-submit test="${command != 'create' }" code="authenticated.message-threads.form.button.create-message" action="/authenticated/message/create?messageThreadId=${id}" method="get"/>
	<acme:form-submit test="${command == 'create' }" code="authenticated.message-threads.form.button.create" action="/authenticated/message-thread/create"/>
	</jstl:if>
	
  <acme:form-return code="authenticated.message-threads.form.button.return"/>
</acme:form>
