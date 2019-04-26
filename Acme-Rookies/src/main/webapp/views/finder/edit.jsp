<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="finder/hacker/edit.do" modelAttribute="finder">
	<form:hidden path="id"/>
	
	<acme:textbox path="keyword" code="finder.keyword"/>
	<acme:textbox path="deadline" code="finder.deadline" placeholder="dd/mm/yyyy"/>
	<acme:textbox path="maximumDeadline" code="finder.maximum.deadline" placeholder="dd/mm/yyyy"/>
	<acme:textbox path="minimumSalary" code="finder.minimum.salary"/>
	
	<!-- Buttons -->
	<div>
		<acme:submit name="save" code="finder.save"/>
		&nbsp;
		<acme:cancel code="finder.clear" url="finder/hacker/clear.do"/>
		&nbsp;
		<acme:cancel code="finder.cancel" url="finder/hacker/display.do"/>
	</div>
</form:form>