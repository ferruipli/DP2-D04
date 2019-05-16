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

<form:form action="problem/company/edit.do" modelAttribute="problem">
	<form:hidden path="id" />
	
	<acme:textbox code="problem.title" path="title"/>
	
	<acme:textarea code="problem.statement" path="statement" />
	
	<acme:textarea code="problem.hint" path="hint" />
	
	
	<p style="color:blue;"><spring:message code="problem.info.attachments"/></p>
	<acme:textarea code="problem.attachments" path="attachments" />

	<acme:submit name="save" code="problem.save"/>	
	<jstl:if test="${problem.id != 0}">
		<acme:submit name="delete" code="problem.delete"/>	
	</jstl:if>
	<acme:cancel url="problem/company/list.do" code="problem.cancel"/>
</form:form>
