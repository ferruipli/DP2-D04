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


<spring:message code="message.date.format" var="formatDate" />
<p> 
	<strong> <spring:message code="message.sentMoment" />: </strong>
	<fmt:formatDate value="${messageToDisplay.sentMoment}" pattern="${formatDate}"/>
</p>
	
<p> 
	<strong> <spring:message code="message.subject" />: </strong>
 	<jstl:out value="${messageToDisplay.subject}" />
</p>

<p> 
	<strong> <spring:message code="message.body" />: </strong>
	<jstl:out value="${messageToDisplay.body}" />
</p>

<jstl:if test="${w_tags != null && w_tags != ''}">
	<strong><spring:message code="message.tags"/>:</strong>
	<jstl:out value="${w_tags}" />
</jstl:if>

<p>
	<strong> <spring:message code="message.sender" />: </strong>
	<jstl:out value="${messageToDisplay.sender.fullname}" />
</p>

<strong> <spring:message code="message.recipients"/>: </strong>
<display:table name="${messageToDisplay.recipients}" id="row" requestURI="message/administrator,company,hacker/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.name"/>
	<display:column property="email" titleKey="actor.email"/>
</display:table>	   

<a href="message/administrator,company,hacker/list.do">
	<spring:message code="message.return" />
</a>
	   