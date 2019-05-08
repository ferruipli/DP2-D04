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

<p><strong><spring:message code="item.link" />:</strong> <jstl:out value="${item.link}" /></p>

<jstl:if test="${not empty pictures}">
		<p><strong><spring:message code="item.pictures"/>:</strong>
		<br>
		<ul>
			<jstl:forEach var="picture" items="${pictures}">
				<img src="${picture}" alt="picture" height="300px" width="500px"/>			
			</jstl:forEach>
		</ul>
	</jstl:if>
	
<br />

<!-- Links -->
<a href="item/allItemsList.do"> <spring:message
		code="item.back" />
</a>
