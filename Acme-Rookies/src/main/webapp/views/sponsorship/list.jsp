<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="sponsorship/provider/display.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.display"/></a>
	</display:column>
	
	<display:column>
		<a href="sponsorship/provider/edit.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.edit"/></a>
	</display:column>
	
	<display:column property="position.title" titleKey="sponsorship.position.table" sortable="true" href="position/display.do" paramId="positionId" paramProperty="position.id"/>
</display:table>
