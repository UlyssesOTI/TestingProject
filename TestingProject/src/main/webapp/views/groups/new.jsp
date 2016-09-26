<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- container -->
<div class="container">
	<div class="col-md-8">
		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="view_courses"><spring:message
						code="editGroup.groups" /></a></li>
			<li class="active"><spring:message code="newGroup.creatingGroup" /></li>
		</ol>
		<!-- breadcrumbs -->
		<div class="panel panel-success ">
			<div class="panel-heading ">
				<h3>
					<spring:message code="newGroup.creatingGroup" />
				</h3>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form action="new_group" method="POST">
					<c:if test="${message}">
							<div class="alert alert-danger">
								<strong><spring:message code="viewGroup.gName"/></strong><spring:message code="warning.unique"/>
							</div>
						</c:if>
					<div class="form-group">
						<label class="control-label"><spring:message
								code="newGroup.groupName" /></label> <input type="text"
							class="form-control" required placeholder="group name"
							name="name">
					</div>
					<div class="form-group">
						<label class="control-label"><spring:message
								code="new_user.course" /></label> <select required name="courseId"
							class="form-control">
							<option></option>
							<c:forEach items="${courses}" var="course">
								<option value="${course.id}">${course.name}</option>
							</c:forEach>
						</select>
					</div>
					<button type="submit" class="btn btn-success">
						<spring:message code="new_user.submit" />
						</label>
					</button>
				</form>
			</div>
		</div>
	</div>
</div>
