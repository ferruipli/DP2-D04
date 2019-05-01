
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

<display:table name="${items}" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">

	<display:column>
		<a href="item/display.do?itemId=${row.id}"><spring:message code="item.table.display"/></a>
	</display:column>	
	
	<security:authorize access="hasRole('PROVIDER')">
	<display:column>
		<jstl:if test="${principal == row.provider}">
			<a href="item/provider/edit.do?itemId=${row.id}"><spring:message code="item.edit"/></a>
		</jstl:if>
	</display:column>	
	</security:authorize>
	
	<display:column property="name" titleKey="item.name" />
	
	<display:column>
		<a href="actor/display.do?actorId=${row.provider.id}"><jstl:out value="${row.provider.name}"/></a>
	</display:column>

</display:table>

	<security:authorize access="hasRole('PROVIDER')">
 			<a href="item/provider/create.do"><spring:message code="item.create"/></a>
 	</security:authorize>


