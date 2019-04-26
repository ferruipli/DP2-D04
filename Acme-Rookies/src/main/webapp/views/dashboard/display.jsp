<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<p> <strong> <spring:message code="dashboard.one" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.two" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerHacker[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerHacker[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerHacker[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerHacker[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.three" />: </strong></p>
<display:table name="findCompaniesOfferedMorePositions" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
	<display:column property="commercialName" titleKey="table.commercialName"/>
</display:table>

<p><strong> <spring:message code="dashboard.four" />: </strong></p>
<display:table name="findHackersWithMoreApplications" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
</display:table>

<p> <strong> <spring:message code="dashboard.five" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.six" />: </strong></p>
<display:table name="dataPositionsBestWorstSalary" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="ticker" titleKey="position.ticker" />
	<display:column property="title" titleKey="position.title" />
	<spring:message code="position.formatDeadline" var="formatMomentDeadline" />
	<display:column property="deadline" titleKey="position.deadline" sortable="true" format="${formatMomentDeadline}"/>
	<display:column property="profile" titleKey="position.profile" />
	<display:column property="skills" titleKey="position.skills" />
	<display:column property="technologies" titleKey="position.technologies" />
	<display:column property="salary" titleKey="position.salary" />
</display:table>
	
<p> <strong> <spring:message code="dashboard.seven" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerHacker[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerHacker[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerHacker[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerHacker[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.eight" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[3]}" /> </td>
	</tr>
</table>

<p>
	<strong> <spring:message code="dashboard.nine" /> </strong>:
	<jstl:out value="${findRatioEmptyVsNonEmpty}" />
</p>