<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<fieldset>
	<legend><spring:message code="actor.legend"/></legend>
	
	
	<p> <strong> <spring:message code="actor.fullname" /> : </strong>  <jstl:out value="${actor.fullname}" /></p>


	<p> <strong> <spring:message code="actor.email" /> : </strong>  <jstl:out value="${actor.email}" /></p>


	<jstl:if test="${!empty actor.photo }">
		<p>
			<strong> <spring:message code="actor.photo" /> :
			</strong> <img alt="Photo" src="<jstl:out value="${actor.photo}" />"
				height="200px" width="200px">
		</p>

	</jstl:if>

	<jstl:if test="${!empty actor.phoneNumber }">
		<p>
			<strong> <spring:message code="actor.phoneNumber" /> :
			</strong>
			<jstl:out value="${actor.phoneNumber}" />
		</p>
	</jstl:if>

	<jstl:if test="${!empty actor.address }">
		<p>
			<strong> <spring:message code="actor.address" /> :
			</strong>
			<jstl:out value="${actor.address}" />
		</p>
	</jstl:if>

	<security:authorize access="hasRole('ADMIN')">

		<jstl:if test="${isAuthorized == false }">
			<p>
				<strong> <spring:message code="actor.isSpammer" /> :
				</strong>
				<jstl:if test="${actor.isSpammer != null }">
					<jstl:out value="${actor.isSpammer}" />
				</jstl:if>
				<jstl:if test="${actor.isSpammer == null }">
					<jstl:out value="N/A" />
				</jstl:if>
			</p>
		</jstl:if>

		<jstl:if
			test="${actor.isSpammer == true}">
			<jstl:if test="${ actor.userAccount.isBanned == false}">
				<a href="actor/administrator/changeBan.do?actorId=${actor.id}"><spring:message
						code="actor.ban" /></a>
			</jstl:if>
		</jstl:if>

		<jstl:if test="${actor.userAccount.isBanned}">
			<a href="actor/administrator/changeBan.do?actorId=${actor.id}"><spring:message
					code="actor.unban" /></a>
		</jstl:if>

	</security:authorize>

	<jstl:if test="${isAuthorized == true}">
		<a
			href="actor/administrator,company,hacker/edit.do?actorId=${actor.id}"><spring:message
				code="actor.edit" /></a>
	</jstl:if>
</fieldset>

<jstl:if test="${actor.userAccount.authorities=='[COMPANY]'}">
	<fieldset>
		<legend>
			<spring:message code="actor.company.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.company.commercialName" />:
			</strong>
			<jstl:out value="${actor.commercialName}" />
		</p>


	</fieldset>
</jstl:if>

<fieldset>
	<legend><spring:message code="userAccount.legend"/></legend>
	<p> <strong> <spring:message code="actor.username" />: </strong>  <jstl:out value="${actor.userAccount.username}" /></p>
	
	<p> <strong> <spring:message code="actor.authority" />: </strong>  <jstl:out value="${actor.userAccount.authorities}" /></p>

</fieldset>

<fieldset>
	<legend><spring:message code="other.legend"/></legend>
	<p> <strong> <spring:message code="actor.socialProfiles" />: </strong>  <a href="socialProfile/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a></p>

</fieldset>

