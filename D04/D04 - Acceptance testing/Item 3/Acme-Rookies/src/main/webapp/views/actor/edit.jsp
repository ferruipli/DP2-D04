<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="confirm.telephone" var="confirmTelephone"/>
<form:form action="actor/administrator,auditor,company,provider,rookie/edit.do" modelAttribute="registrationForm" onsubmit="javascript:calcMD5();">
	<jstl:choose>
		<jstl:when test="${rol == 'Company'}">
			<h2>
				<spring:message code="header.company" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Rookie'}">
			<h2>
				<spring:message code="header.rookie" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Administrator'}">
			<h2>
				<spring:message code="header.administrator" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Auditor'}">
			<h2><spring:message code="header.auditor"/></h2>
		</jstl:when>
		<jstl:when test="${rol == 'Provider'}">
			<h2><spring:message code="header.provider"/></h2>
		</jstl:when>

	</jstl:choose>

	<form:hidden path="id"/>
	
	<fieldset>
		<legend><spring:message code="actor.legend"/></legend>
	
		<acme:textbox code="actor.name" path="name"/>
		
		<br />
		
		
		<acme:textbox code="actor.surname" path="surname"/>
		
	
		<br />
		
		<acme:textbox code="actor.VATnumber" path="VATnumber"/>
		
	
		<br />
		
		<acme:textbox code="actor.photo" path="photo"/>
		
		<br />
		
		<acme:textbox code="actor.email" path="email" placeholder="mail@email.com"/>
		
		<br />
		
		<acme:textbox code="actor.phoneNumber" path="phoneNumber" placeholder="+34 (111) 654654654" id="phoneNumber"/>
		
		<br />
		
		<acme:textbox code="actor.address" path="address"/>
		
		<br /> 
		
		<jstl:if test="${rol == 'Company'}">
		
			<acme:textbox code="actor.company.commercialName" path="commercialName"/>
		
			<br />
		
		</jstl:if>
		
		<jstl:if test="${rol == 'Provider'}">
		
			<acme:textbox code="actor.provider.make" path="make"/>
		
			<br />
		
		</jstl:if>
	</fieldset>
 
	<fieldset>
		<legend><spring:message code="userAccount.legend"/></legend>
	
		<acme:textbox path="userAccount.username" code="userAccount.username" />
		<br>

		<acme:password path="userAccount.password" code="userAccount.password" id="passwordId" />
		<br>

		<acme:password path="userAccount.confirmPassword" code="userAccount.confirmPassword" id="confirmPasswordId"/>
		<br>
		
	</fieldset>
	
 
 
 	<jstl:choose>
		<jstl:when test="${rol == 'Administrator'}">
			<acme:submit name="saveAdmin" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteAdmin" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Company'}">
			<acme:submit name="saveCompany" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteCompany" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Rookie'}">
			<acme:submit name="saveRookie" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteRookie" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Auditor'}">
			<acme:submit name="saveAuditor" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteAuditor" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Provider'}">
			<acme:submit name="saveProvider" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteProvider" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:cancel url="actor/display.do" code="actor.cancel"/>
 
	
	<hr>
	
</form:form>