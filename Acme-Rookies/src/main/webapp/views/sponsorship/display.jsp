<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<fieldset>
	<legend><spring:message code="sponsorship.general.fields"/></legend>
	
	<p>
		<strong><spring:message code="sponsorship.position.referenced"/> </strong>
		<a href="position/display.do?positionId=${sponsorship.position.id}"><jstl:out value="${sponsorship.position.title}"/></a>
	</p>
	
	<p>
		<strong><spring:message code="sponsorship.target.url"/> </strong>
		<a href="${sponsorship.targetPage}"><jstl:out value="${sponsorship.targetPage}"/></a>
	</p>
	
	<p>
		<strong><spring:message code="sponsorship.banner"/> </strong>
		<img alt="Sponsorship banner" src="${sponsorship.banner}" height="275px" width="525px">
	</p>
</fieldset>

<fieldset>
	<legend><spring:message code="sponsorship.creditcard"/></legend>
	
	<p>
		<strong><spring:message code="sponsorship.creditcard.holder"/> </strong>
		<jstl:out value="${sponsorship.creditCard.holder}"/>
	</p>
	
	<p>
		<strong><spring:message code="sponsorship.creditcard.make"/> </strong>
		<jstl:out value="${sponsorship.creditCard.make}"/>
	</p>
	
	<p>
		<jstl:set var="length" value="${fn:length(sponsorship.creditCard.number)}"/>
		<strong><spring:message code="sponsorship.creditcard.number"/> </strong>
		<jstl:out value="****${fn:substring(sponsorship.creditCard.number, length - 4, length)}"/>
	</p>
	
	<p>
		<strong><spring:message code="sponsorship.creditcard.expiration.month"/> </strong>
		<jstl:out value="${sponsorship.creditCard.expirationMonth}"/>
	</p>
	
	<p>
		<strong><spring:message code="sponsorship.creditcard.expiration.year"/> </strong>
		<jstl:out value="${sponsorship.creditCard.expirationYear}"/>
	</p>
</fieldset>


<!-- Links -->

<div>
	<a href="sponsorship/provider/list.do"><spring:message code="sponsorship.return.list"/></a>
	&nbsp;
	<a href="sponsorship/provider/edit.do?sponsorshipId=${sponsorship.id}"><spring:message code="sponsorship.edit.extended"/></a>
	&nbsp;
	<a href="sponsorship/provider/delete.do?sponsorshipId=${sponsorship.id}"><spring:message code="sponsorship.delete"/></a>
</div>
