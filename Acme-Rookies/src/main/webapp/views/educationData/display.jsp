<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="educationData.date.format2" var="dateFormat"/>

<ul>
	<li>
		<strong><spring:message code="educationData.degree"/></strong>
		<jstl:out value="${educationData.degree}"/>
	</li>
	<li>
		<strong><spring:message code="educationData.institution"/></strong>
		<jstl:out value="${educationData.institution}"/>
	</li>
	<li>
		<strong><spring:message code="educationData.mark"/></strong>
		<jstl:out value="${educationData.mark}"/>
	</li>
	<li>
		<strong><spring:message code="educationData.startDate"/></strong>
		<fmt:formatDate value="${educationData.startDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="educationData.endDate"/></strong>
		<fmt:formatDate value="${educationData.endDate}" pattern="${dateFormat}"/>
	</li>
</ul>

<a href="educationData/hacker/backCurriculum.do?educationDataId=${educationData.id}">
	<spring:message code="educationData.return"/>
</a>