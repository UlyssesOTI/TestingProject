<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	function deleteQuestion(questionId) {
		var formData = new FormData();
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "questions/deleteQuestion");
		formData.append("questionId", questionId);
		xhr.send(formData);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					document.getElementById("list").removeChild(
							document.getElementById(questionId));
				}
			}
		}
	}
</script>

<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<h2>
				<spring:message code="theme" />
			</h2>
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li><a href="view_themes"><spring:message code="themes" /></a></li>
				<li><a href="view_themes_course${theme.courseId}">${theme.course}</a></li>
				<li class="active">${theme.name}</li>
			</ol>
			<!-- breadcrumbs -->
			<div class="panel panel-success ">
				<div class="panel-heading ">
					<spring:message code="viewThemes.questions" />
					&#160
					<button type="button" class="btn btn-danger btn-xs pull-right"
						data-toggle="modal" data-target="#delete_theme">
						<i class="fa fa-trash-o"></i>&#160
						<spring:message code="viewUser.delete" />
					</button>
					&#160<a href="edit_theme_${theme.id}"
						class="btn btn-success btn-xs pull-right"><i
						class="fa fa-pencil"></i>&#160<spring:message code="viewUser.edit" /></a>
					<a href="new_question_theme${theme.id}"
						class="btn btn-success btn-xs pull-right"><i
						class="fa fa-plus"></i>&#160<spring:message code="qustion.add" /></a>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body ">
					<div class="pull-right">
						<c:if test="${pageCount > 1}">
							<ul class="pagination">
								<c:choose>
									<c:when test="${pageCount > 7}">
										<li><a><span>&laquo;</span></a></li>
										<li class="active"><a>1</a></li>
										<c:forEach var="i" begin="2" end="6">
											<li><a><c:out value="${i}" /></a></li>
										</c:forEach>
										<li class="dots"><a>...</a></li>
										<li><a><span><c:out value="${pageCount}" /></span></a></li>
										<li><a> <span>&raquo;</span>
										</a></li>
									</c:when>
									<c:otherwise>
										<li><a><span>&laquo;</span></a></li>
										<li class="active"><a>1</a></li>
										<c:forEach var="i" begin="2" end="${pageCount}">
											<li><a><c:out value="${i}" /></a></li>
										</c:forEach>
										<li><a><span>&raquo;</span></a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</c:if>
					</div>
					<div class="clearfix"></div>

					<c:if test="${not empty questions}">
						<ul class="list-group" id="list">
							<c:forEach items="${questions}" var="question">
								<li class="list-group-item" id="${question.id}"><a
									href="view_question_id${question.id}">${question.text} </a></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<input type="hidden" value="${theme.id}" id="themeid"> <input
					type="hidden" value="${pageCount}" id="pageCount">
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="delete_theme" class="modal fade" role="dialog">
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
					<spring:message code="delete.uwant" />
					&#160${theme.name}
				</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-danger" href="delete_theme_${theme.id}"><spring:message
						code="delete.delete" /></a>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="delete.close" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<script>
	$(document)
			.ready(
					function() {
						var pageCount = $("#pageCount").val();
						var currentPage = 1;
						function deleteQuestion(questionId) {
							var formData = new FormData();
							formData.append("questionId", questionId);
							var xhr = new XMLHttpRequest();
							xhr.open("POST", "questions/deleteQuestion");
							xhr.send(formData);
							xhr.onreadystatechange = function() {
								if (xhr.readyState == 4) {
									if (xhr.status == 200) {
										document
												.getElementById("list")
												.removeChild(
														document
																.getElementById(questionId));
									}
								}
							}
						}

						$(".pagination").on(
								'click',
								function(event) {
									var onClick = $(event.target).text();
									if (onClick != "«" && onClick != "»"
											&& onClick != '...') {
										currentPage = onClick;
										currentPage = currentPage - 1;
									}

									console.log("page = " + currentPage);
									if (onClick == "«" && currentPage > 0) {
										currentPage = currentPage - 1;
									}
									if (onClick == "»"
											&& currentPage < pageCount - 1) {
										currentPage = currentPage + 1;
									}

									var themeId = $("#themeid").val();
									searchPaginatedUsersViaAjax(themeId,
											currentPage);

								});

						// AJAX search func
						function searchPaginatedUsersViaAjax(themeId,
								currentPage) {
							$.ajax({
								type : "POST",
								contentType : "application/json",
								url : "question_by_theme_" + currentPage,
								data : JSON.stringify({
									themeId : themeId
								}),
								dataType : 'json',
								success : function(data) {
									display(data);
									paginationFunction(pageCount, currentPage);

								},
								error : function(e) {
									display(e);
								},
								done : function(e) {
									enableSearchButton(true);
								}
							});
						}

						function paginationWriter(start, end, currentPage) {
							text = "";
							for (var i = start; i <= end; i++) {
								if (i == currentPage) {
									text += '<li class="active"><a>' + i
											+ '</a></li>';
								} else {
									text += '<li><a>' + (i) + '</a></li>';
								}
							}
							return text;
						}

						function paginationFunction(maxPage, currentPage) {
							currentPage = currentPage + 1;
							var pageRow = 7;
							var start = 1;
							var leftBorder = 4;
							var rightBorder = 5;
							var border = 2;
							var dots = '<li class="dots"><a>...</a></li>';
							var first = '<li><a><span>&laquo;</span></a></li>';
							var last = '<li><a><span>&raquo;</span></a></li>';
							var text = first;
							if (maxPage > pageRow) {
								if (currentPage <= leftBorder) {
									text += paginationWriter(start, leftBorder
											+ border, currentPage);
									text += dots;
									text += '<li><a>' + maxPage + '</a></li>';
								}
								if (currentPage > leftBorder
										&& currentPage + border < maxPage) {
									text += '<li><a>' + start + '</a></li>';
									text += dots;
									text += paginationWriter(currentPage
											- border, currentPage + border,
											currentPage);
									text += dots;
									text += '<li><a>' + maxPage + '</a></li>';
								}
								if (currentPage + border >= maxPage) {
									text += '<li><a>' + start + '</a></li>';
									text += dots;
									text += paginationWriter(maxPage
											- rightBorder, maxPage, currentPage);
								}

							} else {
								text += paginationWriter(start, maxPage,
										currentPage);
							}
							text += last;
							$('.pagination').html(text);
						}

						function display(data) {
							var temp = "";
							for (var i = 0; i < data.length; i++) {
								temp = temp
										+ '<li class="list-group-item" id="'
			+ data[i].id
			+'"><a href="view_question_id'
			+data[i].id
			+'">'
										+ data[i].text + '</a>'

								temp = temp + "";
								$('#list').html(temp);
							}
						}
					});
</script>