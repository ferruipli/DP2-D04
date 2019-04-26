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

	<security:authorize access="hasRole('HACKER')">
		<strong><spring:message code="problem.company.commercialName"/>:</strong>
			<a href="actor/display.do?actorId=${problem.company.id}"><jstl:out value="${problem.company.commercialName}"/></a>
		<br/>
	</security:authorize>
	
	<strong><spring:message code="problem.title"/>:</strong>
		<jstl:out value="${problem.title}"/>
	<br/>
	
	<strong><spring:message code="problem.statement"/>:</strong>
		<jstl:out value="${problem.statement}"/>
	<br/>
	
	<strong><spring:message code="problem.hint"/>:</strong>
		<jstl:out value="${problem.hint}"/>
	<br/>

	<jstl:if test="${not empty attachments}">
		<strong><spring:message code="problem.attachments"/>:</strong>
		<br>
		<ul>
			<jstl:forEach var="attachment" items="${attachments}">
				<li> <a href="${attachment}"><jstl:out value="${attachment}"/></a> </li>			
			</jstl:forEach>
		</ul>
	</jstl:if>
	
	<security:authorize access="hasRole('COMPANY')">
		<strong><spring:message code="problem.finalMode"/>:</strong>
			<jstl:out value="${problem.isFinalMode}"/>
		<br/>
	</security:authorize>
	
	
<fieldset>
	<legend>
		<spring:message code="problem.positions" />
	</legend>
	<display:table name="positionsList" id="rowPosition"
		pagesize="5" class="displaytag" requestURI="problem/company,hacker/display.do">

		<security:authorize access="hasRole('COMPANY')">
			<jstl:if test="${principal == rowPosition.company}">
				<display:column>
					<a
						href="position/company/edit.do?positionId=${rowPosition.id}">
						<spring:message code="problem.edit" />
					</a>
				</display:column>
			</jstl:if>
		</security:authorize>

		<display:column>
			<a href="position/display.do?positionId=${rowPosition.id}"><spring:message
					code="problem.display" /> </a>
		</display:column>

		<display:column property="title" titleKey="problem.position.title" />
		
		<spring:message code="problem.formatDeadline" var="formatMomentDeadline" />
		<display:column property="deadline" titleKey="problem.position.deadline" sortable="true" format="${formatMomentDeadline}"/>
		
		<display:column property="profile" titleKey="problem.position.profile" />
		
		<display:column property="skills" titleKey="problem.position.skills" />
		
		<display:column property="technologies" titleKey="problem.position.technologies" />
	</display:table>
</fieldset>
	
	<!-- Links -->
		
	
	<security:authorize access="hasRole('COMPANY')">
		<a href="problem/company/list.do">
			<spring:message	code="position.back" />			
		</a>
	</security:authorize>
	
	<security:authorize access="hasRole('HACKER')">
		<a href="position/list.do?companyId=${problem.company.id}">
			<spring:message	code="position.back" />			
		</a>
	</security:authorize>
	


