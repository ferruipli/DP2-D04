
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


<display:table name="${audits}" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">

	<display:column>
		<a href="audit/auditor/display.do?auditId=${row.id}"><spring:message code="audit.display"/></a>
	</display:column>	
	
	<display:column>
		<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="audit.edit"/></a>
	</display:column>
	
	<display:column property="auditor.fullName" titleKey="audit.auditor" />
	
	<display:column property="position.title" titleKey="audit.position" />
	
	<spring:message code="audit.formatMoment" var="formatMoment" />
	<display:column property="writtenMoment" titleKey="audit.writtenMoment" sortable="true" format="${formatMoment}"/>
</display:table>




