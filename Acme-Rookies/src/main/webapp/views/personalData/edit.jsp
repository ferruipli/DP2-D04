<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="personalData/hacker/edit.do" modelAttribute="personalData">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="personalData.fieldset"/></legend>
		
		<acme:textbox code="personalData.fullname" path="fullname"/>
		<acme:textbox code="personalData.statement" path="statement"/>
		<acme:textbox code="personalData.phoneNumber" path="phoneNumber" id="phoneNumber"/>
		<acme:textbox code="personalData.githubProfile" path="githubProfile" placeholder="https://github.com/..."/>
		<acme:textbox code="personalData.linkedInProfile" path="linkedInProfile" placeholder="https://www.linkedin.com/..."/>
	</fieldset>
	
	<!-- Buttons -->
	<spring:message code="personalData.confirm.phone" var="confirmTelephone"/>
	<acme:submit name="save" code="personalData.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	&nbsp;
	<acme:cancel code="personalData.cancel" url="personalData/hacker/backCurriculum.do?personalDataId=${personalData.id}"/>
</form:form>