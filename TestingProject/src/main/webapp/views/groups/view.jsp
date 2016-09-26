<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- host link --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>
<style>
input[type="radio"]:checked+.label-text {
	background: black;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<div class="container">
	<div class="col-md-12 ">
		<!-- breadcrumbs -->
		<ul class="breadcrumb">
			<sec:authorize
				access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
				<li><a href="view_courses"><spring:message code="editGroup.groups"/></a> <span
					class="divider"></span></li>
<%--  				<li><a href="view_course_${courses.get(0).getId()}"><spring:message code="editGroup.groups"/></a> <span  --%>
<!-- 					class="divider"></span></li>  -->
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_STUDENT')">
				<li><spring:message code="editGroup.groups"/> <span class="divider"></span></li>
			</sec:authorize>
			<li class="active">${group.name}</li>
		</ul>
		<!-- breadcrumbs >-->
		
		<c:if test="${not empty teacher}">
			<p class="bg-success">
				<b><spring:message code="viewGroup.teacher"/></b> ${teacher.name} ${teacher.secondname}
			</p>
		</c:if>
		
		<c:if test="${not empty courses}">
			<p class="bg-success">
				<b><spring:message code="course"/></b>
				<c:forEach items="${courses}" var="course">
					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
						<a class="btn btn-success btn-xs"
							href="${hostURL}/view_course_${course.id}">${course.name}</a>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_STUDENT')">
						<a class="btn btn-success btn-xs">${course.name}</a>
					</sec:authorize>
				</c:forEach>
			</p>
		</c:if>
		<div class="panel panel-success">
			<div class="panel-heading ">
				<spring:message code="viewGroup.students"/>
				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_TEACHER')">
					<button type="button" class="btn btn-danger btn-xs pull-right"
						data-toggle="modal" data-target="#delete_group"><spring:message code="delete.delete"/></button>
					<a href="edit_group_${group.id}"
						class="btn btn-success btn-xs pull-right"><i
						class="fa fa-pencil"></i><spring:message code="edittest.edit"/></a>
				</sec:authorize>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
					<form class="search" action="${hostURL}/set_test_to_students"
						id="search-form" method="POST">
						<input type="hidden" value="${group.id}" name="groupId"
							id="groupId">
						<table class="table table-striped  table-hover ">
							<thead>
								<tr>
									<sec:authorize
										access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
										<th><input type="checkbox" name="selectAll" class="totalCheckd"></th>
									</sec:authorize>
									<th><spring:message code="allUsers.name"/></th>
									<th><spring:message code="new_user.email"/></th>
									<th><spring:message code="new_user.phone"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${students}" var="student">
									<tr>
										<sec:authorize
											access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
											<td class="text-center"><input type="checkbox"
												name="checkbox" class="checkbox" value="${student.id}"></td>
										</sec:authorize>
										<td><a href="view_user_${student.id}">${student.name}
												${student.secondname}</a></td>
										<td>${student.email}</td>
										<td>${student.phone}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${not empty tests}">
							<sec:authorize
								access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
								<h4>
									<spring:message code="viewGroup.avalable" />
								</h4>
								<div class="form-group">
									<c:forEach items="${tests}" var="test">

										<label><input type="radio" hidden name="test"
											value="${test.id}" required class="radioButton" /> <span
											class="btn btn-success label-text"><i
												class="fa fa-check"></i> ${test.name}</span> </label>
									</c:forEach>
								</div>
								<button type="submit" id="submit-btn"
									class="btn btn-primary pull-right">
									<i class="fa fa-check"></i>
									<spring:message code="viewGroup.gaveAcces" />
								</button>
							</sec:authorize>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="delete_group" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="warning.warning"/></h4>
			</div>
			<div class="modal-body">
				<p><spring:message code="warning.deleteGroup"/> ${group.name}</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-danger" href="delete_group_${group.id}"><spring:message code="delete.delete"/></a>
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="delete.close"/></button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<!-- Modal -->
<div id="test_radiobutton" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="warning.warning"/></h4>
			</div>
			<div class="modal-body">
				<p>Select a test</p>
			</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="delete.close"/></button>
			</div>
		</div>

	</div>
</div>
<!-- Modal -->

<script>
	$(".totalCheckd").change(function() {
		var f =  $(".totalCheckd").prop('checked');
		$('.checkbox').prop('checked', f);
	});
	$("#submit-btn").click(function(){
		if (!$("input[name='test']:checked").val()) {
		       $("#test_radiobutton").modal();
		        return false;
		    }
	})
</script>
