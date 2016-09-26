<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="resources/css/multiselect.css">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="resources/js/chosen.jquery.js" type="text/javascript"></script>
<!-- container -->
<div class="container">
	<div class="col-md-8">
		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="view_courses"><spring:message code="editGroup.groups"/></a></li>
			<li><a href="view_group_${group.id}">${group.name}</a></li>
			<li class="active"><spring:message code="edittest.edit"/></li>
		</ol>
		<!-- breadcrumbs -->
		<div class="panel panel-success ">
			<div class="panel-heading ">
				<h3><spring:message code="editGroup.editGroup"/></h3>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form action="edit_group" method="POST" name="group_edit_form">
					<input type="hidden" value="${group.id}" name="groupId">
					<div class="form-group">
					<c:if test="${message}">
							<div class="alert alert-danger">
								<strong>Group name</strong> should be unique.
							</div>
						</c:if>
						<B><spring:message code="editGroup.groupName"/></b> <input type="text" class="form-control groupName" required
							value="${group.name}" name="name">
					</div>
					<div class="form-group courseOption">
						<label class="control-label"><spring:message code="edit.courses"/></label> <select required
							name="courseId" class="form-control chosen-select" multiple>
							<c:forEach items="${groupCourses}" var="groupCourse">
								<option selected value="${groupCourse.id}">${groupCourse.name}</option>
							</c:forEach>
							<c:forEach items="${courses}" var="course">
								<option value="${course.id}">${course.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label"><spring:message code="viewGroup.teacher"/></label> <select
							name="userId" class="form-control-teacher form-control"
							id="teacher_input">
							<option value="null"></option>
							<c:forEach var="curTeacher" items="${teachers}">
								<c:choose>
									<c:when test="${curTeacher.id == teacher.id}">
										<option value="${curTeacher.id}" selected>${curTeacher.name}
											${curTeacher.secondname}</option>
									</c:when>
									<c:otherwise>
										<option value="${curTeacher.id}">${curTeacher.name}
											${curTeacher.secondname}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<input type="hidden" class="groupnamechange" name="groupnamechange" value="false">
				</form>
				<button onclick="submit()" class="btn btn-success"><spring:message code="new_user.submit"/></button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="war" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="warning.warning"/></h4>
			</div>
			<div class="modal-body">
				<p><spring:message code="warning.teacher"/></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="warning.ok"/></button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
				<div id="warning" class="modal fade" role="dialog">
					<div class="modal-dialog modal-sm">
						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title"><spring:message code="warning.warning"/></h4>
							</div>
							<div class="modal-body">
								<p><spring:message code="warning.course"/></p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal"><spring:message code="warning.ok"/></button>
							</div>
						</div>
					</div>
				</div>
<script>
	var teacherId, groupNameBefor;

	$(document).ready(function() {
		groupNameBefor = document.getElementsByName("name")[0].value;
		teacherId = $(".form-control-teacher").val();
		multiselect();
	});
	
	$('.groupName').change(function(){
		var groupNameAfter = $('.groupName').val();
		if(groupNameBefor != groupNameAfter){
			$('.groupnamechange').val("true");
		}
	});

	function submit() {
		var inpCount = document.getElementsByClassName("search-choice").length;
		var teacherVal = $("#teacher_input").val();
		if (inpCount < 1) {
			$('#warning').modal();
		} else {
			if (teacherVal == "null" || teacherVal == null || teacherVal == 0) {
				$('#war').modal();
			} else {
				document.group_edit_form.submit();
			}
		}
	}
	$(".courseOption").on('change', function() {
		var courseIds = new Array();
		$(".courseOption select.form-control").each(function(index, element) {
			uploadTeachers($(element).val());
		});
	});

	function uploadTeachers(courseIds) {
		console.log("courseIds " + courseIds);
		$.ajax({
			type : "POST",
			url : "group/uploadTeachers",
			data : {
				courseIds : courseIds
			},
			dataType : 'json',
			success : function(data) {
				console.log(data);
				setTeachers(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log(e);
			}
		});
	}

	function setTeachers(data) {
		$('select.form-control-teacher').find('option').remove().end();
		$("select.form-control-teacher").append($('<option>', {
			value : 0,
			text : ""
		}));
		$.each(data, function(index, element) {
			$("select.form-control-teacher").append($('<option>', {
				value : element.id,
				text : element.name + " " + element.secondname
			}));
		});
		$("select.form-control-teacher").find('option').each(
				function(index, element) {
					if (element.value == teacherId) {
						$("select.form-control-teacher").val(element.value);
					}
				});
	}

	function multiselect() {
		var config = {
			'.chosen-select' : {},
			'.chosen-select-deselect' : {
				allow_single_deselect : true
			},
			'.chosen-select-no-single' : {
				disable_search_threshold : 10
			},
			'.chosen-select-no-results' : {
				no_results_text : 'Oops, nothing found!'
			},
			'.chosen-select-width' : {
				width : "95%"
			}
		}
		for ( var selector in config) {
			$(selector).chosen(config[selector]);
		}
	}
</script>