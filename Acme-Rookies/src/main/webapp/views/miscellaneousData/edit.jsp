<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="miscellaneousData/hacker/edit.do" modelAttribute="miscellaneousData">
	<input type="hidden" name="curriculumId" value="${curriculumId}"/>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="miscellaneousData.fieldset"/></legend>
		
		<acme:textarea code="miscellaneousData.text" path="text"/>
		
		<p style="color:blue;"><spring:message code="miscellaneousData.info.attachments"/></p>
		<acme:textarea code="miscellaneousData.attachments" path="attachments"/>
	</fieldset>
	
	<!-- Buttons -->
	<acme:submit name="save" code="miscellaneousData.save"/>
	<jstl:if test="${miscellaneousData.id != 0}">
		&nbsp;
		<acme:submit name="delete" code="miscellaneousData.delete"/>
	</jstl:if>
	&nbsp;
	<jstl:choose>
		<jstl:when test="${curriculumId ne null}">
			<acme:cancel code="miscellaneousData.cancel" url="curriculum/display.do?curriculumId=${curriculumId}"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:cancel code="miscellaneousData.cancel" url="miscellaneousData/hacker/backCurriculum.do?miscellaneousDataId=${miscellaneousData.id}"/>
		</jstl:otherwise>
	</jstl:choose>
</form:form>