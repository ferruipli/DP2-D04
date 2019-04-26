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

	<jstl:if test="${position.isCancelled == true}">
		<h2><strong><spring:message code="position.isCancelled"/></strong></h2>
		<br/>
	</jstl:if>
	
	<security:authorize access="hasRole('COMPANY')">
	<jstl:if test="${principal == position.company && !position.isFinalMode && hasProblem}">
		<h2>
			<a href="position/company/makeFinal.do?positionId=${position.id}"><spring:message code="position.makeFinal" /></a>
		</h2>
	</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('HACKER')">
	<jstl:if test="${!position.isCancelled && position.isFinalMode && isApplied && isDeadlineFuture}">
	<jstl:if test="${noCurriculum}">
		<p style="color:blue;"><spring:message code="problem.info.curriculum"/></p>
	</jstl:if>
	<jstl:if test="${noCurriculum == false}">
		<h2>
			<a href="application/hacker/create.do?positionId=${position.id}"><spring:message code="position.apply" /></a>
		</h2>
	</jstl:if>
	</jstl:if>
	</security:authorize>
	
	<strong><spring:message code="position.company"/>:</strong>
		<a href="actor/display.do?actorId=${position.company.id}"><jstl:out value="${position.company.commercialName}"/></a>
	<br/>
	
	<strong><spring:message code="position.ticker"/>:</strong>
		<jstl:out value="${position.ticker}"/>
	<br/>

	<strong><spring:message code="position.title"/>:</strong>
		<jstl:out value="${position.title}"/>
	<br/>
	
	<strong><spring:message code="position.description"/>:</strong>
		<jstl:out value="${position.description}"/>
	<br/>
	
	<security:authorize access="hasRole('COMPANY')">
	<jstl:if test="${principal == position.company}">
		<strong><spring:message code="position.finalMode"/>:</strong>
			<jstl:out value="${position.isFinalMode}"/>
		<br/>
	</jstl:if>
	</security:authorize>
	
	<strong><spring:message code="position.deadline"/>:</strong>
	<spring:message code="position.formatDeadline1" var="formatDeadline"/>
		<fmt:formatDate value="${position.deadline}" pattern="${formatDeadline}"/>
		
	<br/>
	
	<strong><spring:message code="position.profile"/>:</strong>
		<jstl:out value="${position.profile}"/>
	<br/>
	
	<strong><spring:message code="position.skills"/>:</strong>
		<jstl:out value="${position.skills}"/>
	<br/>
	
		<strong><spring:message code="position.technologies"/>:</strong>
		<jstl:out value="${position.technologies}"/>
	<br/>
	
	<spring:message code="position.vat" var="vatTag"/>
	<strong><spring:message code="position.salary"/>:</strong>
	<fmt:formatNumber type="number" maxFractionDigits="2" value="${position.salary * (1 + position.company.VATnumber/100)}"/> &#8364; <jstl:out value="(${position.company.VATnumber}% ${vatTag} Inc.)"/>

	<br/>
	

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${principal == position.company}">
	<jstl:if test="${position.isFinalMode}">

	<p>
		<strong><spring:message code="position.applications" />:</strong>
			<a href="application/company/list.do?positionId=${position.id}"><spring:message code="position.applications" /></a>
	</p>
	</jstl:if>

<fieldset>
	<legend>
		<spring:message code="position.problems" />
	</legend>
	<display:table name="problemList" id="rowPoroblem"
		pagesize="5" class="displaytag" requestURI="position/display.do">
		
		<display:column>
			<a href="problem/company,hacker/display.do?problemId=${rowPoroblem.id}"><spring:message
					code="position.display" /> </a>
		</display:column>

		<display:column property="title" titleKey="position.title" />
	</display:table>
</fieldset>
</jstl:if>
</security:authorize>
	
	
	<!-- Links -->	
	
	<a href="position/list.do?companyId=${position.company.id}">
		<spring:message	code="position.back" />			
	</a>

