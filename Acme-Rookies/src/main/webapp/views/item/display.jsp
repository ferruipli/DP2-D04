<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p> <strong> <spring:message code="item.name" />:  </strong>  <jstl:out value="${item.name}" /></p>

<p> <strong> <spring:message code="item.description" />:  </strong>  <jstl:out value="${item.description}" /></p>

<p><strong><spring:message code="item.link" />:</strong>
<a href="${item.link}"><jstl:out value="${item.link}"/></a></p>

<p><strong><spring:message code="item.picture"/>:</strong>
		<br>
				<img src="${item.picture}" alt="picture" height="250px" width="250px">	</p>

<br />

<!-- Links -->
<a href="item/allItemsList.do"> <spring:message
		code="item.back" />
</a>
