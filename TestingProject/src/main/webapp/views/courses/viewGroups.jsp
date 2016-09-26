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
		<h2>
			<spring:message code="new_user.course" />
			: ${course.name}
		</h2>
		<!-- breadcrumbs -->
		<ul class="breadcrumb">
			<li><a href="${hostURL}/view_courses"><spring:message
						code="editGroup.groups" /></a> <span class="divider"></span></li>
			<li class="active">${course.name}</li>
		</ul>
		<!-- breadcrumbs >-->
		<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
			<div class="form-group clearfix">
			<c:if test="${message}">
			<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign"
						aria-hidden="true"></span><span class="sr-only"><spring:message code="warning.warning"/></span>
				<span><spring:message code="warning.cantDelete"/></span>
			</div>
		</c:if>
		
		
		<div class="btn-group pull-left">
					<a href="new_group" class="btn btn-success btn-sm "><i
						class="fa fa-plus"></i>&#160<spring:message code="edit.addRole" /></a>
				</div>
				
				
				<div class="btn-group pull-right">
					<a href="edit_course_${course.id}" class="btn btn-success btn-sm "><i
						class="fa fa-pencil"></i>&#160<spring:message code="edittest.edit" /></a>
					<button type="button" class="btn btn-danger  btn-sm "
						data-toggle="modal" data-target="#delete_course">
						<i class="fa fa-trash"></i>&#160
						<spring:message code="delete.delete" />
					</button>
				</div>
			</div>
		</sec:authorize>
		<div class="panel panel-success">
			<div class="panel-heading ">
				<spring:message code="viewCourse.groupsList" />
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="list-group">
					<c:forEach items="${groups}" var="group">
						<li class="list-group-item"><a href="view_group_${group.id}">${group.name}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="delete_course" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<spring:message code="warning.warning" />
				</h4>
			</div>
			<div class="modal-body">
				<p>
					<spring:message code="delete.course" />
					${course.name}
				</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-danger" href="delete_course_${course.id}"><spring:message
						code="delete.delete" /></a>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="delete.close" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

