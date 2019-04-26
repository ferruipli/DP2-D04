<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="curriculum.date.format" var="tableDateFormat"/>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.general"/></legend>
	
	<p>
		<strong><spring:message code="curriculum.title"/></strong>
		<jstl:out value="${curriculum.title}"/>
	</p>
	
	<jstl:if test="${isOwner && curriculum.isOriginal}">
		<a href="curriculum/hacker/edit.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.edit"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.personalData"/></legend>
	
	<ul>
		<li>
			<strong><spring:message code="curriculum.personalData.fullname"/></strong>
			<jstl:out value="${curriculum.personalData.fullname}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalData.statement"/></strong>
			<jstl:out value="${curriculum.personalData.statement}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalData.phoneNumber"/></strong>
			<jstl:out value="${curriculum.personalData.phoneNumber}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalData.githubProfile"/></strong>
			<a href="${curriculum.personalData.githubProfile}">
				<jstl:out value="${curriculum.personalData.githubProfile}"/>
			</a>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalData.linkedInProfile"/></strong>
			<a href="${curriculum.personalData.linkedInProfile}">
				<jstl:out value="${curriculum.personalData.linkedInProfile}"/>
			</a>
		</li>
	</ul>
	
	<jstl:if test="${isOwner && curriculum.isOriginal}">
		<a href="personalData/hacker/edit.do?personalDataId=${curriculum.personalData.id}">
			<spring:message code="curriculum.edit"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.positionData"/></legend>
	
	<display:table name="${curriculum.positionDatas}" id="posData" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="positionData/display.do?positionDataId=${posData.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner && curriculum.isOriginal}">
			<display:column>
				<a href="positionData/hacker/edit.do?positionDataId=${posData.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="title" titleKey="curriculum.positionData.title" sortable="true"/>
		<display:column property="startDate" titleKey="curriculum.positionData.startDate" sortable="true" format="${tableDateFormat}"/>
		<display:column property="endDate" titleKey="curriculum.positionData.endDate" sortable="true" format="${tableDateFormat}"/>
	</display:table>
	
	<jstl:if test="${isOwner && curriculum.isOriginal}">
		<a href="positionData/hacker/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.positionData"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.educationData"/></legend>
	
	<display:table name="${curriculum.educationDatas}" id="edData" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="educationData/display.do?educationDataId=${edData.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner && curriculum.isOriginal}">
			<display:column>
				<a href="educationData/hacker/edit.do?educationDataId=${edData.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="degree" titleKey="curriculum.educationData.degree" sortable="true"/>
		<display:column property="mark" titleKey="curriculum.educationData.mark" sortable="true"/>
	</display:table>
	
	<jstl:if test="${isOwner && curriculum.isOriginal}">
		<a href="educationData/hacker/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.educationData"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.miscellaneousData"/></legend>
	
	<display:table name="${curriculum.miscellaneousDatas}" id="miscData" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="miscellaneousData/display.do?miscellaneousDataId=${miscData.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner && curriculum.isOriginal}">
			<display:column>
				<a href="miscellaneousData/hacker/edit.do?miscellaneousDataId=${miscData.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="text" titleKey="curriculum.miscellaneousData.text" sortable="true"/>
	</display:table>
	
	<jstl:if test="${isOwner && curriculum.isOriginal}">
		<a href="miscellaneousData/hacker/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.miscellaneousData"/>
		</a>
	</jstl:if>
</fieldset>

<jstl:if test="${isOwner && curriculum.isOriginal}">
	<a href="curriculum/hacker/list.do">
		<spring:message code="curriculum.return"/>
	</a>
</jstl:if>