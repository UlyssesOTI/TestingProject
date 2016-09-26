<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="col-xs-12">
	<h2>
		<spring:message code="viewTest.testView" />
	</h2>
	<!-- breadcrumbs -->
	<ol class="breadcrumb">
		<li><a href="view_tests"><spring:message
					code="viewTest.tests" /></a></li>
					<li><a href="view_tests_course${test.courseId}">${test.courseName}</a></li>
		<li class="active">${test.name}</li>
	</ol>
	<!-- breadcrumbs -->
	<c:if test="${not empty themes}">
		<p class="bg-success">
			<span> <b><spring:message code="viewTest.themes" />:</b> <c:forEach
					items="${themes}" var="theme">
					<a class="btn btn-success btn-xs" href="view_theme_${theme.id}">${theme.name}</a>
				</c:forEach>
			</span>
		</p>
	</c:if>
	<div class="panel panel-success ">
		<div class="panel-heading ">
			<spring:message code="viewUser.info" />
			&#160
			<button type="button" class="btn btn-danger btn-xs pull-right"
				data-toggle="modal" data-target="#delete_user">
				<i class="fa fa-trash-o"></i>&#160
				<spring:message code="viewUser.delete" />
			</button>
			&#160<a href="edit_test_${test.id}"
				class="btn btn-success btn-xs pull-right"><i
				class="fa fa-pencil"></i>&#160<spring:message code="viewUser.edit" /></a>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body ">
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<tr class="success">
							<td><spring:message code="viewTest.testName" />:</td>
							<td>${test.name}</td>
						</tr>
						<tr>
							<td><spring:message code="course" />:</td>
							<td>${test.courseName}</td>
						</tr>
						<tr class="success">
							<td><spring:message code="time" />:</td>
							<td>${test.time}</td>
						</tr>
						<tr>
							<td><spring:message code="viewTest.difficult" />:</td>
							<td>${test.highGradeQuestionQuantity}</td>
						</tr>
						<tr class="success">
							<td><spring:message code="viewTest.medium" />:</td>
							<td>${test.middleGradeQuestionQuantity}</td>
						</tr>
						<tr>
							<td><spring:message code="viewTest.easy" />:</td>
							<td>${test.lowGradeQuestionQuantity}</td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
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
					<spring:message code="delete.uwant" />&#160${test.name}</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-danger" href="delete_test_${test.id}"><spring:message
						code="delete.delete" /></a>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="delete.close" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

