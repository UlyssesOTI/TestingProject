<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- ${hostURL} --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>

<div class="col-xs-12">

	<c:if test="${principal==user.id}">
		<h2>
			<spring:message code="viewUser.myCab" />
		</h2>
	</c:if>

	<!-- breadcrumbs -->
	<ol class="breadcrumb">
		<sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_TEACHER')">
			<li><a href="${hostURL}/view_users"><spring:message
						code="viewUser.users" /></a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_STUDENT')">
			<li><spring:message code="viewUser.users" /></li>
		</sec:authorize>
		<li class="active">${user.name}&#160${user.secondname}</li>
	</ol>
	<!-- breadcrumbs -->
	<c:if test="${not empty userGroups}">
		<p class="bg-success">
			<b><spring:message code="viewUser.groups" />: </b>
			<c:forEach items="${userGroups}" var="group">
<a class="btn btn-success btn-xs" href="${hostURL}/view_group_${group.id}">${group.name}</a> 
			</c:forEach>
		</p>
	</c:if>

	<c:if test="${not empty userCourses}">
		<p class="bg-success">
			<span class="pull-left"> <b><spring:message
						code="viewUser.courses" />:</b> <c:forEach items="${userCourses}"
					var="course">
					<c:choose>
						<c:when test="${principal==user.id}">
							<a class="btn btn-success btn-xs"
								href="${hostURL}/view_course_${course.id}">${course.name}</a>
						</c:when>
						<c:otherwise>
							<a class="btn btn-success btn-xs">${course.name}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</span> <span class="clearfix"></span>
		</p>
	</c:if>
	<div class="panel panel-success ">
		<div class="panel-heading ">
			<spring:message code="viewUser.info" />
			<c:choose>
				<c:when test="${principal==user.id}">
					<a href="edit_my_info" class="btn btn-success btn-xs pull-right"><i
						class="fa fa-pencil"></i>&#160<spring:message
							code="viewUser.editOwn" /></a>
				</c:when>
				<c:otherwise>
					<c:if test="${userRightsToEdit}">
						<button type="button" class="btn btn-danger btn-xs pull-right"
							data-toggle="modal" data-target="#delete_user">
							<spring:message code="viewUser.delete" />
						</button>
						<a href="edit_user_${user.id}"
							class="btn btn-success btn-xs pull-right"><i
							class="fa fa-pencil"></i>&#160<spring:message
								code="viewUser.edit" /></a>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body ">
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<tr class="success">
							<td><spring:message code="new_user.f_name" />:</td>
							<td>${user.name}</td>
						</tr>
						<tr>
							<td><spring:message code="new_user.l_name" />:</td>
							<td>${user.secondname}</td>
						</tr>
						<tr class="success">
							<td><spring:message code="new_user.phone" />:</td>
							<td>${user.phone}</td>
						</tr>
						<tr>
							<td><spring:message code="new_user.email" />:</td>
							<td>${user.email}</td>
						</tr>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_TEACHER')">
							<tr class="success">
								<td><spring:message code="new_user.role" />:</td>
								<td>${user.role}</td>
							</tr>
						</sec:authorize>
					</tbody>
				</table>
				<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER')">
					<span class="pull-right"> <a class="btn btn-success "
						href="admin_page"> <i class="fa fa-cogs"></i>&#160<spring:message
								code="viewUser.admin_page" />
					</a>
					</span>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>
<c:if test="${not empty userTests}">
	<div class="col-xs-12">
		<h3><spring:message code="viewUsers.avalibleTests"/></h3>
		<c:forEach var="test" items="${userTests}">
			<div class="col-lg-3 col-md-4 col-sm-6">
				<div class="panel panel-success">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="fa fa-code fa-5x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div>${test.name}</div>
							</div>
						</div>
					</div>
					<c:choose>
						<c:when test="${principal==user.id}">
							<a href="${hostURL}/tests/newTest_${test.id}">
								<div class="panel-footer">
									<span class="pull-left"><a
										href="${hostURL}/tests/newTest_${test.id}">Start</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i>
							</span></a>
							<div class="clearfix"></div>
				</div>
				</a>
				</c:when>
				</c:choose>
			</div>
	</div>
	</c:forEach>
	<div class="clearfix"></div>
	</div>
</c:if>
<c:choose>
	<c:when test="${empty AllTestResults}">
	</c:when>
	<c:otherwise>
		<!-- 100% width block -->
		<div class="col-xs-12">
			<h3>
				<spring:message code="viewUser.testResults" />
			</h3>
			<c:forEach var="result" items="${AllTestResults}">
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-code fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div>${result.testName}</div>
									<div class="huge">${result.testMark}%</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left"><a
									href="${hostURL}/user/testResult_${result.resultID}"><spring:message
											code="viewUser.viewe" /></span> <span class="pull-right"><i
									class="fa fa-arrow-circle-right"></i>
						</a></span>
						<div class="clearfix"></div>
					</div>
					</a>
				</div>
		</div>
		</c:forEach>
		<div class="clearfix"></div>
		</div>
	</c:otherwise>
</c:choose>
<!-- Modal -->
<div id="delete_user" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<spring:message code="delete.delete" />
				</h4>
			</div>
			<div class="modal-body">
				<p>
					<spring:message code="delete.uwant" />${user.name}
					${user.secondname}
				</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-danger" href="delete_user_${user.id}"><spring:message
						code="delete.delete" /></a>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="delete.close" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

