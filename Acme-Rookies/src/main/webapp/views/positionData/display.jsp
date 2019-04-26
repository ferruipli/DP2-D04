<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="positionData.date.format2" var="dateFormat"/>

<ul>
	<li>
		<strong><spring:message code="positionData.title"/></strong>
		<jstl:out value="${positionData.title}"/>
	</li>
	<li>
		<strong><spring:message code="positionData.description"/></strong>
		<jstl:out value="${positionData.description}"/>
	</li>
	<li>
		<strong><spring:message code="positionData.startDate"/></strong>
		<fmt:formatDate value="${positionData.startDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="positionData.endDate"/></strong>
		<fmt:formatDate value="${positionData.endDate}" pattern="${dateFormat}"/>
	</li>
</ul>

<a href="positionData/hacker/backCurriculum.do?positionDataId=${positionData.id}">
	<spring:message code="positionData.return"/>
</a>