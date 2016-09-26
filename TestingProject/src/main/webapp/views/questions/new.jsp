<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<style>
input[type="radio"]:checked+.label-text {
	background: #1E5A1E;
	border: 1px solid #1e5a1e;
}
</style>
<script>
	function validateQuestion() {
		var questionText = document.getElementsByName("questionText")[0];
		if (questionText.value == "") {
			modalWarning('<spring:message code="werning.questionText"/>');
			$('#modalWindow').modal();
			return false;
		}
		var radioComplexity = document.getElementsByName("complexity");
		var complexity = 0;
		for (var i = 0; i < radioComplexity.length; i++) {
			if (radioComplexity[i].checked) {
				complexity++
			}
		}
		if (complexity == 0) {
			modalWarning('<spring:message code="werning.complexity"/>');
			$('#modalWindow').modal();
			return false;
		}
		var answerText = document.getElementsByName("answerText");
		for (var i = 0; i < answerText.length; i++) {
			if (answerText[i].value == "") {
				modalWarning('<spring:message code="werning.answerText"/>');
				$('#modalWindow').modal();
				return false;
			}
		}
		var answerPicture = document.getElementsByName("answerPicture");
		var answerStatus = document.getElementsByName("answerStatus");
		var answerStatusCounter = 0;
		for (var i = 0; i < answerStatus.length; i++) {
			if (answerStatus[i].checked) {
				answerStatusCounter++;
			}
		}
		if (answerStatusCounter == 0) {
			modalWarning('<spring:message code="werning.answerRight"/>');
			$('#modalWindow').modal();
			return false;
		}
		if (answerStatusCounter / answerStatus.length > 0.5) {
			modalWarning('<spring:message code="werning.answerToMuch"/>');
			$('#modalWindow').modal();
			return false;
		}
		addQuestion();
	}
	function addQuestion() {
		console.log("creating question");
		var formData = new FormData();
		console.log(formData);
		var radioComplexity = document.getElementsByName("complexity");
		var complexity;
		for (var i = 0; i < radioComplexity.length; i++) {
			if (radioComplexity[i].checked)
				complexity = radioComplexity[i].value;
		}
		var themeId = document.getElementsByName("themeId")[0];
		var questionText = document.getElementsByName("questionText")[0];
		//var questionPicture = document.getElementsByName("questionPicture")[0];
		//isMultipleisMultipleisMultipleisMultipleisMultipleisMultiple
		var isMultipleAnswers = false;
		var questionId;

		var answerText = document.getElementsByName("answerText");
		var answerPicture = document.getElementsByName("answerPicture");
		var answerStatus = document.getElementsByName("answerStatus");

		var counter = 0;
		for (var i = 0; i < answerStatus.length; i++) {
			if (answerStatus[i].checked) {
				counter++;
			}
		}
		if (counter > 1)
			isMultipleAnswers = true;

		formData.append("questionText", questionText.value);
		//formData.append("questionPicture", questionPicture.files[0]);
		formData.append("themeId", themeId.value);
		formData.append("complexity", complexity);
		formData.append("isMultipleAnswers", isMultipleAnswers);

		var xhr = new XMLHttpRequest();
		xhr.open("POST", "questions/saveQuestion");
		xhr.send(formData);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					questionId = xhr.responseText;
					console.log("question created");
					console.log("question ID=" + questionId);

					for (var i = 0; i < answerStatus.length; i++) {
						formData = new FormData();
						console.log("new FormData()");
						formData.append("questionId", questionId);
						formData.append("answerText", answerText[i].value);
						formData.append("answerPicture",
								answerPicture[i].files[0]);
						formData
								.append("answerStatus", answerStatus[i].checked);
						console.log("questionId=" + questionId + "|answerText="
								+ answerText[i].value + "|answerPicture="
								+ answerPicture[i].files[0]);
						console.log("|answerStatus=" + answerStatus[i].checked);
						var xmlHR = new XMLHttpRequest();
						xmlHR.open("POST", "questions/saveAnswer");
						xmlHR.send(formData);
						xmlHR.onreadystatechange = function() {
							if (xmlHR.readyState == 4) {
								if (xmlHR.status == 200) {
									console.log("answer created");
								} else {
									console.log("soething wrong with answer");
									if (xmlHR.status == 400) {
										console.log("eror 400");
									}
								}
							}
						}
					}

				} else {
					console.log("something wrong with question")
				}
			}
		}
		var themeId = document.getElementsByName("themeId")[0].value;
		var location = "view_theme_" + themeId;
		window.location = location;

	}
</script>

<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-10">
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li><a href="view_themes"><spring:message code="themes" /></a></li>
				<li><a href="view_themes_course${theme.courseId}">${theme.course}</a></li>
				<li><a href="view_theme_${theme.id}">${theme.name}</a></li>
				<li class="active"><spring:message code="question.questions" /></li>
			</ol>
			<!-- breadcrumbs -->


			<div class="panel panel-success ">
				<div class="panel-heading ">
					<h3>
						<spring:message code="question.creating" />
					</h3>

				</div>
				<!-- /.panel-heading -->


				<div class="panel-body">
					<form action="new_question" method="POST"
						enctype="multipart/form-data" name="questionForm">
						<input type="hidden" value="${theme.id}" name="themeId">
						<div class="form-group">
							<label><spring:message code="question.text" /></label>
							<textarea class="form-control" required
								placeholder="question text" name="questionText"></textarea>
						</div>

						<div class="form-inline text-left">
							<label class="control-label"><spring:message
									code="question.complexity" /></label>
							<div class="form-group">
								<label><input type="radio" hidden name="complexity"
									value="1" required class="radioButton" /> <span
									class="btn btn-xs btn-success label-text"><i
										class="fa fa-check"></i> <spring:message code="newTest.easy" /></span>
								</label> <label><input type="radio" hidden name="complexity"
									value="2" required class="radioButton" /> <span
									class="btn btn-xs btn-success label-text"><i
										class="fa fa-check"></i> <spring:message code="newTest.medium" /></span>
								</label> <label><input type="radio" hidden name="complexity"
									value="3" required class="radioButton" /> <span
									class="btn btn-xs btn-success label-text"><i
										class="fa fa-check"></i> <spring:message code="newTest.diff" /></span>
								</label>
							</div>
						</div>
						<div class="form-group margin-top">
							<label class="control-label"><spring:message
									code="question.answers" /></label>
							<table class="table table-hover table-striped">

								<tbody id="question-table">
									<tr>
										<!-- 										<div class="add-answer"> -->
										<td><input type="checkbox" name="answerStatus"></td>

										<td><input type="text" name="answerText"></td>
										<!-- 										</div> -->
										<td>

											<button class="btn btn-xs btn-default add_field_button">
												<spring:message code="question.addAnswer" />
											</button>
										</td>
								</tbody>
							</table>
						</div>
					</form>
					<button class="btn btn-success" onClick="validateQuestion()">
						<spring:message code="new_user.submit" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="modalBlock"></div>
<!-- Modal -->
<script>
	$(document)
			.ready(
					function() {
						var add_button = $(".add_field_button"); //Add button ID

						$(add_button)
								.click(
										function(e) { //on add input button click

											// add new input
											$("#question-table")

													.append(
															'<tr><td><input type="checkbox" name="answerStatus"></td><td><input type="text" required name="answerText"></td><td><a class="btn btn-xs btn-danger remove_field">Remove</a></td></tr>');
											console.log(">>");
											return false;
										});

						$("body").on("click", ".remove_field", function(e) { //user click on remove text
							$('.add_field_button').prop('disabled', false);
							$(this).parent('td').parent('tr').remove();
						});

						/*	$('button.test').click( function(e){
								var aaaa = 'view_themes_course' + ${theme.courseId};
								modalWarning(aaaa);
							});
						 */

					});

	function modalWarning(trouble) {
		var text = '<div id="modalWindow" class="modal fade" role="dialog"><div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title"><spring:message code="warning.warning" /></h4></div><div class="modal-body"><p>';
		text += trouble;
		text += '</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="warning.ok" /></button></div></div></div></div>';
		$("#modalBlock").html(text);
	}
</script>