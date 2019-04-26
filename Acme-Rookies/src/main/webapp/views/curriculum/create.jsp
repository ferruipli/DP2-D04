<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="curriculum/hacker/edit.do" modelAttribute="curriculum">
	<form:hidden path="id" />

	<fieldset>
		<legend><spring:message code="curriculum.fieldset.general"/></legend>
		
		<acme:textbox code="curriculum.title" path="title"/>
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="curriculum.fieldset.personalData"/></legend>
		
		<acme:textbox code="curriculum.personalData.fullname" path="personalData.fullname" readonly="true"/>
		<acme:textbox code="curriculum.personalData.statement" path="personalData.statement"/>
		<acme:textbox code="curriculum.personalData.phoneNumber" path="personalData.phoneNumber" id="phoneNumber"/>
		<acme:textbox code="curriculum.personalData.githubProfile" path="personalData.githubProfile" placeholder="https://github.com/..."/>
		<acme:textbox code="curriculum.personalData.linkedInProfile" path="personalData.linkedInProfile" placeholder="https://www.linkedin.com/..."/>
	</fieldset>
	
	<!-- Buttons -->
	<spring:message code="curriculum.confirm.phone" var="confirmTelephone"/>
	<acme:submit name="save" code="curriculum.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	&nbsp;
	<acme:cancel code="curriculum.cancel" url="curriculum/hacker/list.do"/>
</form:form>