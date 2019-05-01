<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="sponsorship/provider/edit.do" modelAttribute="sponsorship">	
	<form:hidden path="id"/>
	<jstl:if test="${sponsorship.id == 0}">
		<form:hidden path="position"/>
	</jstl:if>
	
	<fieldset>
		<legend><spring:message code="sponsorship.general.fields"/></legend>
		
		<acme:textbox code="sponsorship.banner" path="banner"/>
		<acme:textbox code="sponsorship.target.page" path="targetPage"/>
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="sponsorship.creditcard"/></legend>
		
		<acme:textbox code="sponsorship.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="sponsorship.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="sponsorship.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="sponsorship.creditcard.expiration.month" path="creditCard.expirationMonth"/>
		<acme:textbox code="sponsorship.creditcard.expiration.year" path="creditCard.expirationYear"/>
		<acme:textbox code="sponsorship.creditcard.cvv" path="creditCard.cvvCode"/>
	</fieldset>
	
	
	<!-- Buttons -->
	
	<div>
		<acme:submit name="save" code="sponsorship.save"/>
		&nbsp;
		<jstl:if test="${sponsorship.id != 0}">
			<acme:submit name="delete" code="sponsorship.delete"/>
			&nbsp;
		</jstl:if>
		<acme:cancel code="sponsorship.cancel" url="sponsorship/provider/list.do"/>
	</div>
</form:form>