<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- container -->
<div class="container">
	<div class="col-md-8">
		<h2>Редагування курсу</h2>
		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="view_courses">Курси</a></li>
			<li><a href="view_course_${course.id}">${course.name}</a></li>
			<li class="active">Редагування</li>
		</ol>
		<!-- breadcrumbs -->

		<div class="panel panel-success ">
					<div class="panel-heading ">
			<span>Інформація</span></div>
			<!-- /.panel-heading -->


			<div class="panel-body">
				<form action="edit_course" method="POST">
					<c:if test="${message}">
							<div class="alert alert-danger">
								<strong>Course name</strong> should be unique.
							</div>
						</c:if>
					<label >Назва курсу:</label>
					<input type="hidden" value="${course.id}" name="courseId">
					<div class="form-group">
						<input type="text" class="form-control course_name" required
							value="${course.name}" name="name">
					</div>
					<input type="hidden" class="coursechange" name="coursechange" value="false">
					<button type="submit" class="btn btn-success">Submit</button>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
	var emailBefor = document.getElementsByName("name")[0].value;	
	$(".course_name").on('change', function(){
		var emailAfter = $('.course_name').val();
		if(emailBefor != emailAfter){
			$('.coursechange').val("true");
		}
	});
});
</script>
