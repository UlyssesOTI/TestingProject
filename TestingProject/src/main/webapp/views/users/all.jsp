<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- host link --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>


<script>
	$(document)
			.ready(
					function() {
						var pageCount = 1;
						var currentPage = 1;
						var users;
						
						// On button click run AJAX
						$("#search-form").submit(function(event) {
							var search = $("#username").val();
							var roleId = $("#forma").val();
							currentPage = 0;
							event.preventDefault();
							searchPaginatedUsersViaAjax(search, roleId, 0);
						});

		 				$('#username').on('input', function(e) {
		 					var search = $("#username").val();
							var roleId = $("#forma").val();
							if (search.length == 3) {
								searchAutocompleteUsersViaAjax(search, roleId);
						}
		 				}); 
						
						$( "#username").autocomplete({
								/*search: function( event, ui ) {
										$('#username').autocomplete("option", { source: users });
									},*/
						 		source: users,
						      	minLength: 2,
						 });

						$(".pagination").on(
								'click',
								function(event) {
									var onClick = $(event.target).text();
									
									if (onClick != "«" && onClick != "»"
											&& onClick != "...") {
										currentPage = onClick;
										currentPage = currentPage - 1;
									}
									if (onClick == "...") {
									}
									if (onClick == "«" && currentPage > 0) {
										currentPage = currentPage - 1;
									}
									if (onClick == "»"
											&& currentPage < pageCount - 1) {
										currentPage = currentPage + 1;
									}
									
									var search = $("#username").val();
									var roleId = $("#forma").val();
									searchPaginatedUsersViaAjax(search, roleId,
											currentPage);
								});

						// AJAX search func
						function searchPaginatedUsersViaAjax(search, roleId,
								currentPage) {
							$.ajax({
								type : "POST",
								contentType : "application/json",
								url : "${hostURL}/user/search/" + currentPage,
								data : JSON.stringify({
									search : search,
									roleId : roleId
								}),
								dataType : 'json',
								success : function(data) {
									display(data);
									pageCount = data.paginationPageCount;
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
						function searchAutocompleteUsersViaAjax(search, roleId) {
							$
									.ajax({
										type : "POST",
										contentType : "application/json",
										url : "user/search",
										data : JSON.stringify({
											search : search,
											roleId : roleId
										}),
										dataType : 'json',
										success : function(data) {
											users = data;
											$('#username').autocomplete("option", { source: users });
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
							if(maxPage == 0) {
								$("ul.pagination").hide();
							}else {
								$("ul.pagination").show();
							}
							text += last;
							$('.pagination').html(text);
						}

						// AJAX Data to Page || button
						function display(data) {
							var temp = "";
							for (var i = 0; i < data.users.length; i++) {
								temp = temp
										+ "<tr><td> "
										+ "<a  href='${hostURL}/view_user_" + data.users[i].id + "'>"
										+ data.users[i].name
										+ " </a></td>"
										+ "<td> "
										+ "<a  href='${hostURL}/view_user_" + data.users[i].id + "'>"
										+ data.users[i].secondname + "</a>"
										+ "</td><td>" + data.users[i].email
										+ "</td><td>" + data.users[i].phone
										+ "</td></tr>";
							}
							temp = temp + "";
							$('#feedback').html(temp);
						}


					});
</script>

<!-- container -->
<div class="container">
	<div class="col-xs-12">
		<h2><spring:message code="allUsers.users" /></h2>
		<form class="form-inline search" id="search-form">
			<div class="input-group form-group search-form ">
				<span class="input-group-addon"><i class="fa fa-search"></i></span>
				<input type="text" class="form-control" id="username"
					autocomplete="off">
		
			</div>
			<div class="form-group search-select">
				<select name="role_id" class="form-control" id="forma">
					<option selected value="%">All</option>
					<c:forEach items="${roles}" var="role">
						<option value="${role.name}">${role.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="search-btn">
				<button type="submit" id="bth-search" class="btn btn-primary">
					<i class="fa fa-search"></i><spring:message code="allUsers.search" />
				</button>
			</div>
		</form>
		<div class="clearfix"></div>
		<div class="form-group pull-left">
			<a href="${hostURL}/new_user" class="btn btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a>
		</div>
			<!-- Pagination -->
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
		<!-- Pagination -->
		<div class="clearfix"></div>
		<table class="table table-hover table-striped">
			<thead>
				<tr>
					<th><spring:message code="allUsers.name" /></th>
					<th><spring:message code="allUsers.lastName" /></th>
					<th><spring:message code="new_user.email" /></th>
					<th><spring:message code="new_user.phone" /></th>
				</tr>
			</thead>
			<tbody id="feedback">
				<c:forEach items="${users}" var="user">
					<tr>
						<td><a href="${hostURL}/view_user_${user.id}">${user.name}</a></td>
						<td><a href="${hostURL}/view_user_${user.id}">
								${user.secondname} </a></td>
						<td>${user.email}</td>
						<td>${user.phone}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
