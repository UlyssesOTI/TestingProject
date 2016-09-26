<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li><a href="view_themes"><spring:message code="themes" /></a></li>
				<li><a href="view_theme_${theme.id}">${theme.name}</a></li>
				<li class="active"><spring:message code="editTheme.Edit"/></li>
			</ol>
			<!-- breadcrumbs -->
			<div class="panel panel-success ">
				<div class="panel-heading ">
					<h3><spring:message code="editTheme.Editing"/></h3>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<form action="edit_theme" method="POST">
						<div class="form-group">
							<input type="hidden" value="${theme.id}" name="id"> <label>
								<spring:message code="editTheme.name"/></label> <input type="text" class="form-control" required
								value="${theme.name}" name="name">
						</div>
						<div class="form-group">
							<label><spring:message code="editTheme.course"/></label> <select required name="courseId"
								class="form-control">
								<option value="${theme.courseId}">${theme.course}</option>
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
</div>