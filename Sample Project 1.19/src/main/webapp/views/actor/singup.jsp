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
<form:form action="actor/${urlAdmin}register${rol}.do" modelAttribute="registrationForm" onsubmit="javascript:calcMD5();">
		
	<form:hidden path="id"/>
	
	<jstl:choose>
		<jstl:when test="${rol == 'Company'}">
			<h2><spring:message code="header.company"/></h2>
		</jstl:when>
		<jstl:when test="${rol == 'Hacker'}">
			<h2><spring:message code="header.hacker"/></h2>
		</jstl:when>
		<jstl:when test="${rol == 'Administrator'}">
			<h2><spring:message code="header.administrator"/></h2>
		</jstl:when>
		
	
	</jstl:choose>
	
	<p><spring:message code="form.note"/></p>
	
	<fieldset>
		<legend><spring:message code="actor.legend"/></legend>
		
		<acme:textbox code="actor.name.requested" path="name"/>
	
		<br />
		
		<acme:textbox code="actor.middlename" path="middleName"/>
		
		<br />
		
		<acme:textbox code="actor.surname.requested" path="surname"/>
		
		<br />
		
		<acme:textbox code="actor.photo" path="photo"/>
		
		<br />
		
		<acme:textbox code="actor.email.requested" path="email"/> 
		
		<br />
		
		<acme:textbox code="actor.phoneNumber" path="phoneNumber" placeholder="+34 111 654654654" id="phoneNumber"/>
		
		<br />
		
		<acme:textbox code="actor.address" path="address"/>
		
		<br /> 
		 
 		 
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="userAccount.legend"/></legend>
		

		<acme:textbox path="userAccount.username" code="userAccount.username.requested" />
		<br>

		<acme:password path="userAccount.password" code="userAccount.password.requested" id="passwordId" />
		<br>

		<acme:password path="userAccount.confirmPassword" code="userAccount.confirmPassword.requested" id="confirmPasswordId"/>
		<br>

		<security:authorize access="hasRole('ADMIN')" >
			
			<acme:textbox code="actor.authority" path="userAccount.authorities" readonly="true" value="ADMIN"/>
				
		</security:authorize>
 
 
		<security:authorize access="isAnonymous()" >
		
		<jstl:if test="${rol == 'Company'}">
		
			<acme:textbox code="actor.authority" path="userAccount.authorities" readonly="true" value="COMPANY"/>
			
		</jstl:if>
		
		<jstl:if test="${rol == 'Hacker'}">
		
			<acme:textbox code="actor.authority" path="userAccount.authorities" readonly="true" value="HACKER"/>
		
		</jstl:if>
		
		</security:authorize>
		
	</fieldset>
	<br>

	<div>
		<form:checkbox path="checkBoxAccepted" />
		<spring:message code="actor.checkBox" />
		<a href="welcome/terms.do"><spring:message code="actor.terms"/></a>
		<form:errors path="checkBoxAccepted" cssClass="error" />
	</div>
	<br>
	
	<div>
		<form:checkbox path="checkBoxDataProcessesAccepted" />
		<spring:message code="actor.checkBox" />
		<a href="welcome/dataProcesses.do"><spring:message code="actor.dataProcesses"/></a>
		<form:errors path="checkBoxDataProcessesAccepted" cssClass="error" />
	</div>
	<br>

	<acme:submit name="save" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	<acme:cancel url="welcome/index.do" code="actor.cancel"/>
	
</form:form>
