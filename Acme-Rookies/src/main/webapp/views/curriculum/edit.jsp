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
	
	<!-- Buttons -->
	<acme:submit name="save" code="curriculum.save"/>
	&nbsp;
	<acme:submit name="delete" code="curriculum.delete"/>
	&nbsp;
	<acme:cancel code="curriculum.cancel" url="curriculum/display.do?curriculumId=${curriculum.id}"/>
</form:form>