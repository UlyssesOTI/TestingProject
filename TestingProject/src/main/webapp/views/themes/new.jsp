<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- container -->
<div class="container">
	<div class="col-md-8">
		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="view_themes"><spring:message code="themes"/></a></li>
			<li class="active"><spring:message code="newTheme.createTheme"/></li>
		</ol>
		<!-- breadcrumbs -->
		<div class="panel panel-success ">
			<div class="panel-heading ">
				<h3><spring:message code="newTheme.createTheme"/></h3>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form action="new_theme" method="POST">
					<div class="form-group">
						<input type="text" class="form-control" required
							placeholder="theme name" name="name">
					</div>
					<div class="form-group">
						<select required name="courseId" class="form-control">
							<option></option>
							<c:forEach items="${courses}" var="course">
								<option value="${course.id}">${course.name}</option>
							</c:forEach>
						</select>
					</div>
					<button type="submit" class="btn btn-success"><spring:message code="new_user.submit"/></button>
				</form>
			</div>
		</div>
	</div>
</div>