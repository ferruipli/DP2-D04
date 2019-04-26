<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p> <strong> <spring:message code="answer.text" />  </strong>  <jstl:out value="${answer.text}" /></p>

<strong><spring:message code="answer.codeLink" />:</strong>
<a href="${answer.codeLink}"><jstl:out value="${answer.codeLink}"/></a>
	<br/>


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
