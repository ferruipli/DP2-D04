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


	<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${isOwner && audit.finalMode}">
		<strong><spring:message code="audit.finalMode"/></strong>
	<br/>	
	</jstl:if>
	
	<jstl:if test="${isOwner && not audit.finalMode}">
		<strong><spring:message code="audit.draftMode"/></strong>
	<br/>	
	</jstl:if>
	</security:authorize>
	
	<strong><spring:message code="audit.auditor"/>:</strong>
		<jstl:out value="${audit.auditor.fullname}"/>
	<br/>
	
	<strong><spring:message code="audit.position"/>:</strong>
		<jstl:out value="${audit.position.title}"/>
	<br/>


	<strong><spring:message code="audit.writtenMoment"/>:</strong>
	<spring:message code="audit.formatMoment1" var="formatMoment"/>
		<fmt:formatDate value="${audit.writtenMoment}" pattern="${formatMoment}"/>
		
	<br/>

	<strong><spring:message code="audit.text"/>:</strong>
		<jstl:out value="${audit.text}"/>
	<br/>
	
	<strong><spring:message code="audit.score"/>:</strong>
		<jstl:out value="${audit.score}"/>
	<br/>

	
	<!-- Links -->	
	
	<security:authorize access="hasRole('AUDITOR')">
		<a href="audit/auditor/list.do">
			<spring:message	code="audit.back" />			
		</a>
	</security:authorize>

