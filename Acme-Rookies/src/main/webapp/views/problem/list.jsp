
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

<display:table name="problems" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">

	<display:column>
		<a href="problem/company,hacker/display.do?problemId=${row.id}"><spring:message code="problem.display"/></a>
	</display:column>	
	
	<display:column>
		<jstl:if test="${!row.isFinalMode}">
			<a href="problem/company/edit.do?problemId=${row.id}"><spring:message code="problem.edit"/></a>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${!row.isFinalMode}">
			<a href="problem/company/makeFinal.do?problemId=${row.id}"><spring:message code="problem.makeFinal"/></a>
		</jstl:if>
	</display:column>
	
	<display:column property="title" titleKey="problem.title" />
</display:table>

	<a href="problem/company/create.do"><spring:message code="problem.create"/></a>



