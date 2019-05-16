<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="customisation/administrator/edit.do" modelAttribute="customisation">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<p style="color:blue;">
		<spring:message code="customisation.notice" />
	</p>
	
	<acme:textbox code="customisation.systemName" path="name" />
	<acme:textbox code="customisation.banner" path="banner" />
	<acme:textbox code="customisation.englishWelcomeMessage" path="welcomeMessageEn" />
	<acme:textbox code="customisation.spanishWelcomeMessage" path="welcomeMessageEs" />
	<acme:textbox code="customisation.countryCode" path="countryCode" />
	<acme:textbox code="customisation.timeCached" path="timeCachedResults" />
	<acme:textbox code="customisation.maxResults" path="maxNumberResults" />
	<acme:textbox code="customisation.spamWords" path="spamWords" />
	<acme:textbox code="customisation.flatRate" path="frate" />
	<acme:textbox code="customisation.vat" path="VATtax" />
	<br />
	
	<acme:submit name="save" code="customisation.save" />
	<acme:cancel code="customisation.cancel" url="customisation/administrator/display.do" />
</form:form>