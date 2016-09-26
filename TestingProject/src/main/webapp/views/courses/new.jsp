<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- container -->
<div class="container">
	<div class="col-md-8">
		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="view_courses"><spring:message code="viewUser.courses"/></a></li>
			<li class="active"><spring:message code="newCourse.newCourse"/></li>
		</ol>
		<!-- breadcrumbs -->
		<div class="panel panel-success ">
			<div class="panel-heading ">
				<h3><spring:message code="newCourse.newCourse"/></h3>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form action="new_course" method="POST">
						<c:if test="${message}">
							<div class="alert alert-danger">
								<strong><spring:message code="warning.coursename"/></strong><spring:message code="warning.unique"/>
							</div>
						</c:if>
					<div class="form-group">
						<label><spring:message code="newCourse.courseName"/></label> <input type="text"
							class="form-control" required placeholder="<spring:message code="newCourse.courseName"/>"
							name="name">
					</div>
					<button type="submit" class="btn btn-success"><spring:message code="new_user.submit" /></button>
				</form>
			</div>
		</div>
	</div>
</div>