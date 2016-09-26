<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet" href="resources/css/multiselect.css">
<script src="resources/js/chosen.jquery.js" type="text/javascript"></script>
<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li><a href="view_tests"><spring:message code="viewTest.tests"/></a></li>
				<li><a href="view_test_2${test.id}">${test.name}</a></li>
				<li class="active"><spring:message code="edittest.edit"/></li>
			</ol>
			<!-- breadcrumbs -->
			<div class="panel panel-success ">
				<div class="panel-heading ">
					<h3><spring:message code="edittest.editTest"/></h3>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<c:if test="${permissionToEdit}">
					<div class="alert alert-danger">
						<span class="glyphicon glyphicon-exclamation-sign"
							aria-hidden="true"></span> <span>There's not enough
							questions to form this test.</span>
					</div>
				</c:if>
					
					<form action="edit_test" method="POST" name="edit_test">
						<input type="hidden" value="${test.id}" name="testId">
						<div class="form-group">
							<label><spring:message code="newTest.name"/></label> <input type="text" class="form-control"
								required value="${test.name}" name="name">
						</div>
						<div class="form-group">
							<label class="control-label"><spring:message code="new_user.course"/>:</label><select required
								name="courseId" class="form-control" id="courseId">
								<option value="${test.courseId}">${test.courseName}</option>
								<c:forEach items="${courses}" var="course">
									<option value="${course.id}">${course.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="add-theme">
							<div class="form-group">
								<label class="control-label"><spring:message code="themes"/></label> <select required
									name="themeId" class="form-control chosen-select" multiple>
									<c:forEach items="${testThemes}" var="testTheme">
										<option selected value="${testTheme.id}">${testTheme.name}</option>
									</c:forEach>
									<c:forEach items="${themes}" var="theme">
										<option value="${theme.id}">${theme.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label"><spring:message code="newTest.time"/></label>
							<div class="input-group">
								<div class="input-group-addon"><spring:message code="newTest.minutse"/></div>
								<input type="number" class="form-control"
									placeholder="test time" value="${test.time}" name="time"
									min="1" max="90">
							</div>
						</div>
						<div class="form-group clearfix">
							<label class="control-label"><spring:message code="newTest.questionQuent"/></label>
							<div class="form-inline">
								<div class="clearfix"></div>
								<div class="form-group pull-left1">
									<label class="control-label"><spring:message code="newTest.diff"/></label> <input
										type="number" class="form-control"
										value="${test.highGradeQuestionQuantity}"
										placeholder="difficult" name="highGradeQuestionQuantity"
										min="0" max="90"> <label class="control-label"><spring:message code="newTest.medium"/></label>
									<input type="number" class="form-control" placeholder="medium"
										value="${test.middleGradeQuestionQuantity}"
										name="middleGradeQuestionQuantity" min="0" max="90"> <label
										class="control-label"><spring:message code="newTest.easy"/></label> <input type="number"
										class="form-control" placeholder="easy"
										value="${test.lowGradeQuestionQuantity}"
										name="lowGradeQuestionQuantity" min="0" max="90">
								</div>
							</div>
						</div>
					</form>
					<button onclick="submit()" id="submit-btn"class="btn btn-success btn-block"><spring:message code="new_user.submit"/></button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div id="modalBlock" >
</div>
<!-- Modal -->
<script>
	$(document).ready(function() {
		$("#courseId").change(function() {
			getThemes($("#courseId").val());
		});
		multiselect();
	});
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
	function updateMultiselect() {
		$('.chosen-select').chosen("destroy");
	}
	function getThemes(courseID) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "course_themes",
			data : courseID,
			dataType : 'json',
			success : function(data) {
				display(data);
				updateMultiselect();
				multiselect();
			},
			error : function(e) {
				display(e);
			},
			done : function(e) {
				enableSearchButton(true);
			}
		});
	}
	function display(data) {
		var temp = " <select required name='themeId' class='form-control chosen-select' multiple><option></option>"
		for (var i = 0; i < data.length; i++) {
			temp = temp + "<option value ='"
				+ data[i].id
				+"'>"
					+ data[i].name + "</option>";
		}
		temp = temp + "</select>";
		$('.chosen-select').html(temp);
		$('.chosen-results').html(temp);
	}
	
	function submit() {
		var title = $('[name="name"]').val();
		var courseId = $('[name="courseId"]').val();
		var theme = document.getElementsByClassName("search-choice").length;
		var time =  $('[name="time"]').val();
		var easy =  parseInt($('[name="lowGradeQuestionQuantity"]').val());
		var medium =  parseInt($('[name="middleGradeQuestionQuantity"]').val());
		var hard = parseInt($('[name="highGradeQuestionQuantity"]').val());
		var result = easy + medium + hard;
		
		if(title.trim().length < 2){
			modalWarning('<spring:message code="warning.testName"/>');
			$('#modalWindow').modal();	
		}else if(courseId.trim().length < 1){
			modalWarning('<spring:message code="warning.course2"/>');
			$('#modalWindow').modal();	
		}else if(theme < 1){
			modalWarning('<spring:message code="warning.theme"/>');
			$('#modalWindow').modal();	
		}else if((time < 10) || (time > 90)){
			modalWarning('<spring:message code ="warning.time"/>');
			$('#modalWindow').modal();	
		}else if((result < 10) || (result > 90) || isNaN(easy) || isNaN(medium) || isNaN(hard)){
			modalWarning('<spring:message code="warning.questions"/>');
			$('#modalWindow').modal();	
		}else{
			document.edit_test.submit(); 
		}
	}
	
	function modalWarning(trouble){
		var text = '<div id="modalWindow" class="modal fade" role="dialog"><div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title"><spring:message code="warning.warning" /></h4></div><div class="modal-body"><p>';
		text += trouble;
		text += '</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="warning.ok" /></button></div></div></div></div>';
		$("#modalBlock").html(text);
	}
</script>
