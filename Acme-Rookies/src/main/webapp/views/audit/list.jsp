
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="${positions}" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">

	<display:column>
		<a href="position/display.do?positionId=${row.id}"><spring:message code="position.table.display"/></a>
	</display:column>	
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column>
		<jstl:if test="${principal == row.company && !row.isFinalMode}">
			<a href="position/company/edit.do?positionId=${row.id}"><spring:message code="position.edit"/></a>
		</jstl:if>
	</display:column>
	
	<display:column>	
		<jstl:if test="${principal == row.company && row.isCancelled!=true && row.isFinalMode}">
			<a href="position/company/cancel.do?positionId=${row.id}"><spring:message code="position.cancel"/></a>
		</jstl:if>
	</display:column>
	
	</security:authorize>
	
	<display:column property="company.name" titleKey="position.company" />
	
	<display:column property="ticker" titleKey="position.ticker" />
	
	<display:column property="title" titleKey="position.title" />
	
	<spring:message code="position.formatDeadline" var="formatMomentDeadline" />
	<display:column property="deadline" titleKey="position.deadline" sortable="true" format="${formatMomentDeadline}"/>
			
	<display:column property="profile" titleKey="position.profile" />
	
	<display:column property="skills" titleKey="position.skills" />
	
	<display:column property="technologies" titleKey="position.technologies" />

</display:table>

	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${principal == owner}">
 			<a href="position/company/create.do"><spring:message code="position.create"/></a>
 		</jstl:if>
 	</security:authorize>


