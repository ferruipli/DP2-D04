<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="position/company/edit.do" modelAttribute="position">
	<form:hidden path="id" />
	
	<acme:textarea code="position.title" path="title" />
	
	<acme:textarea code="position.description" path="description" />

	<acme:textbox code="position.deadline" placeholder="dd/MM/yyyy" path="deadline"/>
	
	<acme:textbox code="position.profile" path="profile"/>
	
	<acme:textarea code="position.skills" path="skills"/>
	
	<acme:textarea code="position.technologies" path="technologies"/>
	
	<acme:textbox code="position.salary" path="salary"/>
	
	<acme:selectMandatory items="${problems}" multiple="true" 
		 itemLabel="title" code="position.problems" path="problems"/>

	<acme:submit name="save" code="position.save"/>	
	<jstl:if test="${position.id != 0}">
		<acme:submit name="delete" code="position.delete"/>	
	</jstl:if>
	<acme:cancel url="position/list.do?companyId=${principal.id}" code="position.cancel"/>
	<br />
</form:form>
