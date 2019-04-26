<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<strong><spring:message code="application.hacker" />:</strong>
	<jstl:out value="${application.hacker.fullname}" />
	<br />


<strong><spring:message code="application.applicationMoment" />:</strong>
<spring:message code="application.formatMoment1" var="formatApplicationMoment"/>
		<fmt:formatDate value="${application.applicationMoment}" pattern="${formatApplicationMoment}"/>
<br />

<strong><spring:message code="application.status" />:</strong>
<jstl:out value="${application.status}" />
<br />

<jstl:if test="${existAnswer == true}">
	<strong><spring:message code="application.submittedMoment" />:</strong>
	<spring:message code="application.formatMoment1" var="formatSubmitedMoment"/>
		<fmt:formatDate value="${application.submittedMoment}" pattern="${formatSubmitedMoment}"/>
	<br />

<strong><spring:message code="application.answer" />:</strong>
	<a
		href="answer/company,hacker/display.do?answerId=${application.answer.id}"><spring:message
			code="application.display.answer" /></a>
	<br />

</jstl:if>

<strong><spring:message code="application.curriculum" />:</strong>
<a
	href="curriculum/display.do?curriculumId=${application.curriculum.id}"><spring:message
		code="application.display.curriculum" /></a>
<br />		

<strong><spring:message code="application.position" />:</strong>
<a href="position/display.do?positionId=${application.position.id}"><jstl:out value="${application.position.title}"/></a>
	<br/>

<strong><spring:message code="application.problem" />:</strong>
<a href="problem/company,hacker/display.do?problemId=${application.problem.id}"><jstl:out value="${application.problem.title}"/></a>
	<br/>
<br />

<jstl:if test="${application.status=='PENDING'}">
	<a href="answer/hacker/create.do?applicationId=${application.id}"><spring:message
			code="application.answer.create" /></a>
	<br />
</jstl:if>

<br />
<!-- Links -->
<jstl:if test="${rolActor=='hacker'}">
<a href="application/hacker/list.do"> <spring:message
		code="application.return" />
</a>
</jstl:if>

<jstl:if test="${rolActor=='company'}">
<a href="application/company/list.do?positionId=${application.position.id}"> <spring:message
		code="application.return" />
</a>
</jstl:if>
