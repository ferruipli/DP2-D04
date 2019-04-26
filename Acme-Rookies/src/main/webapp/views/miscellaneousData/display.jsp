<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<ul>
	<li>
		<strong><spring:message code="miscellaneousData.text"/></strong>
		<jstl:out value="${miscellaneousData.text}"/>
	</li>
	<li>
		<jstl:if test="${not empty attachments}">
			<strong><spring:message code="miscellaneousData.attachments"/></strong>
			<br>
			<ul>
				<jstl:forEach var="attachment" items="${attachments}">
					<li> <a href="${attachment}"><jstl:out value="${attachment}"/></a> </li>
				</jstl:forEach>
			</ul>
		</jstl:if>
	</li>
</ul>

<a href="miscellaneousData/hacker/backCurriculum.do?miscellaneousDataId=${miscellaneousData.id}">
	<spring:message code="miscellaneousData.return"/>
</a>