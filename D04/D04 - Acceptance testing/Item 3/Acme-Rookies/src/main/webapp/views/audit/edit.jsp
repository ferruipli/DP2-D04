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


<form:form action="audit/auditor/edit.do" modelAttribute="audit">
	<form:hidden path="id" />
	<input type="hidden" name="positionId" value="${positionId}"/>
	
	<acme:textarea code="audit.text" path="text" />
	
	<acme:textbox code="audit.score" path="score"/>

	<acme:submit name="save" code="audit.save"/>	
	<jstl:if test="${audit.id != 0}">
		<acme:submit name="delete" code="audit.delete"/>	
	</jstl:if>
	<acme:cancel url="audit/auditor/list.do" code="audit.cancel"/>
	<br />
</form:form>
