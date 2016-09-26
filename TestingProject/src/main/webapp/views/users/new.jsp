<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="secur"%>
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
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="resources/css/multiselect.css">
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="resources/js/chosen.jquery.js" type="text/javascript"></script>

<script src='<c:url value="/resources/js/maskedinput.js"></c:url>'></script>


<script>

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

	$(document).ready(function() {
		
		multiselect();
		
		// disable submit-group on start
		$("body").ready(function() {
			$("#groupId1").hide();
			$("#courseId1").hide();
		});

		$("#select-role").change(function() {
			var text = $("#select-role option:selected").text();
			var student = "STUDENT";
			var techer = "TEACHER";
			if (text.toUpperCase() === student.toUpperCase()) {
				$("#groupId1").show();
			} else {
				$("#groupId2").val(" ");
				$("#groupId1").hide();
			}
			if (text.toUpperCase() === techer.toUpperCase()) {
				$("#courseId1").show();
			} else {
				$("#courseId2").val(" ");
				$("#courseId1").hide();
			}
		});
	});
</script>

<script>
	jQuery(function($) {
		$("#phone").mask("+380999999999", {
			placeholder : "+380xxxxxxxxx"
		});
	});
</script>


<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">

			<form action="new_user" id="send" method="POST" >

				<div class="panel panel-success">
					<div class="panel-heading ">
						<h3><spring:message code="new_user.creating" /></h3>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">

						<c:if test="${emailMessage eq true}">
							<div class="alert alert-danger">
								<strong><spring:message code="new_user.email" /></strong>  <spring:message code="warning.unique"/>
							</div>
						</c:if>
						<div class="form-group">
							<label for="email" class="control-label"><spring:message code="new_user.email" />:</label> <input
								id="email" type="email" class="form-control" required
								placeholder="E-mail" name="mail" autocomplete="off">
						</div>

						<c:if test="${param.phoneMessage eq true}">
							<div class="alert alert-danger">
								<strong><spring:message code="new_user.phone" /></strong><spring:message code="warning.unique"/>
							</div>
						</c:if>


						<div class="form-group">
							<label for="phone"><spring:message code="new_user.phone" /></label> <input id="phone" type='tel'
								class="form-control" required name="phone" 
								placeholder="+380xxxxxxxxx" title="Format: +380xxxxxxxxx"
								autocomplete="off" >
						</div>
						<div class="form-group">
							<label for="password" class="control-label"><spring:message code="new_user.password" />:</label> <input
								id="password" type="password" class="form-control" required
								placeholder="password" name="password" autocomplete="new-password">
						</div>
						<div class="form-group">
							<label for="name" class="control-label"><spring:message code="new_user.f_name" />:</label> <input
								id="name" type="text" class="form-control" required
								placeholder="first name" name="name" pattern="[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+">
						</div>

						<div class="form-group">
							<label for="surname" class="control-label"><spring:message code="new_user.l_name" />:</label> <input
								id="surname" type="text" class="form-control" required
								placeholder="last name" name="secondname" pattern="[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+">
						</div>


						<div class="form-group">
							<label for="select-role" class="control-label"><spring:message code="new_user.role" />:</label> <select
								required name="roleId" class="form-control" id="select-role">
								<option></option>
								<c:forEach items="${rolesDTO}" var="role">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group" id="groupId1">
							<label for="groupId" class="control-label"><spring:message code="new_user.group" /></label>
							 <select class="form-control chosen-select" multiple name="groupId" id="groupId2">
							 	<option value=" "></option>
								<c:forEach items="${groups}" var="group">
									<option value="${group.id}">${group.name}</option>
								</c:forEach>
								</select>
						</div>
						<div class="form-group" id="courseId1">
							<label class="control-label"><spring:message code="new_user.course" />:</label>
							 <select class="form-control" name="courseId" id="courseId2">
								<option value=" "></option>
								<c:forEach items="${courses}" var="course">
									<option value="${course.id}">${course.name}</option>
								</c:forEach>
							</select>

						</div>
						<div class="form-group">
							<button type="submit" id="submit-btn"
								class="btn btn-success btn-block"><spring:message code="new_user.submit" /></button>
						</div>
					</div>
				</div>
			</form>

		</div>

	</div>
</div>