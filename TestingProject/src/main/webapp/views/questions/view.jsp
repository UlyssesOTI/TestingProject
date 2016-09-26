<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>

<script src="//code.jquery.com/jquery-1.10.2.js"></script>



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
			
			
			<p class="bg-success">
			<span> <b>Складність питання:</b> 
			
				<c:set var="i" scope="session" value="${1}" />
							<c:forTokens items="easy,medium,difficult" delims=","                                    
								var="complexity">
								<c:choose>
									<c:when test="${question.getGrade()==i}">
										<a class="btn btn-success btn-xs dots" >${complexity}</a>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
								<c:set var="i" scope="session" value="${i+1}" />
							</c:forTokens>
			</span>
			</p>

			<div class="panel panel-success ">
					<div class="panel-heading ">
			<spring:message code="viewUser.info" />
			&#160
			<button type="button" class="btn btn-danger btn-xs pull-right"
				data-toggle="modal" data-target="#delete_user">
				<i class="fa fa-trash-o"></i>&#160
				<spring:message code="viewUser.delete" />
			</button>
			&#160<a href="edit_question_id${question.getId()}"
				class="btn btn-success btn-xs pull-right"><i
				class="fa fa-pencil"></i>&#160<spring:message code="viewUser.edit" /></a>
		</div>
		<!-- /.panel-heading -->


				<div class="panel-body">
	


					<div class="form-group">
						<label><spring:message code="question.text" /></label>
						<pre class="bg-success test-text-style" >${question.getText()}</pre>
					</div>
	

				
					<div class="form-group margin-top">
						<label class="control-label"><spring:message
								code="question.answers" /></label>

						<table class="table table-hover table-striped">
							<tbody id="question-table">
			
								<c:forEach items="${question.getAnswers()}" var="answer">
									<input type="hidden" value="${answer.getId()}" name="answerId">
									<tr>

										<c:choose>
											<c:when test="${answer.isStatus()}">
												<td><input type="checkbox" name="answerStatus" checked disabled></td>
											</c:when>
											<c:otherwise>
												<td><input type="checkbox" name="answerStatus" disabled></td>
											</c:otherwise>
										</c:choose>
										<td>${answer.getText()}</td>

									
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>


				</div>
			</div>
		</div>
	</div>
</div>
