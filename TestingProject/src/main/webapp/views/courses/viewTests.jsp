<%@ page language="java" contentType="text/html; charset=UTF-"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- ${hostURL} --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>
<div class="container">
	<div class="col-md-8 ">
		<h2><spring:message code="viewTest.tests"/>   ${course.name}</h2>
		<!-- breadcrumbs -->
		<ul class="breadcrumb">
			<li><a href="${hostURL}/view_tests"><spring:message code="viewTest.tests"/></a> <span
				class="divider"></span></li>
			<li class="active">${course.name}</li>
		</ul>
		<!-- breadcrumbs >-->
		<div class="panel panel-success">
			<div class="panel-heading "><spring:message code="newTest.list"/></div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="list-group">
					<c:forEach items="${tests}" var="test">
						<li class="list-group-item"><a href="view_test_${test.id}">${test.name}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
