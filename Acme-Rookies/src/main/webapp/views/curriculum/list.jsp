<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="curriculums" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">
	<display:column>
		<a href="curriculum/display.do?curriculumId=${row.id}">
			<spring:message code="curriculum.display"/>
		</a>
	</display:column>
	
	<display:column>
		<a href="curriculum/hacker/delete.do?curriculumId=${row.id}" onclick="return confirm('<spring:message code="curriculum.confirm.delete"/>')">
			<spring:message code="curriculum.delete"/>
		</a>
	</display:column>
	
	<display:column property="title" titleKey="curriculum.title.table" sortable="true"/>
</display:table>

<a href="curriculum/hacker/create.do">
	<spring:message code="curriculum.create.curriculum"/>
</a>