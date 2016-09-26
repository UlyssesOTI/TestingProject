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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="resources/css/multiselect.css">
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="resources/js/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(function($) {
		$("#phone").mask("+380999999999", {
			placeholder : "+380xxxxxxxxx"
		});
	});
</script>
<script src='<c:url value="/resources/js/maskedinput.js"></c:url>'></script>

<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<form action="edit_user" id="send" method="POST" name="edit_user_form">
				<div class="panel panel-success">
					<div class="panel-heading ">
						<h3><spring:message code="edit.userEdit"/></h3>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<c:if test="${emailMessage}">
							<div class="alert alert-danger">
								<strong><spring:message code="new_user.email" /></strong><spring:message code="warning.unique"/>
							</div>
						</c:if>
						<div class="form-group">
							<label for="email" class="control-label"><spring:message code="new_user.email" />:</label> <input
								 type="hidden" value="${userDTO.id}" name="id">
							<input type="email" class="form-control" required id="email"
								value="${userDTO.email}" name="email">
						</div>
						<c:if test="${phoneMessage}">
							<div class="alert alert-danger">
								<strong><spring:message code="new_user.phone" /></strong><spring:message code="warning.unique"/>
							</div>
						</c:if>
						<div class="form-group">
							<label for="phone"><spring:message code="new_user.phone" />:</label> <input id="phone" type='tel'
								class="form-control" required value="${userDTO.phone}"
								name="phone" title="Format: +380xxxxxxxxx"
								placeholder="+380xxxxxxxxx" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="name" class="control-label"><spring:message code="new_user.f_name" />:</label> <input
								id="name" type="text" class="form-control" required
								value="${userDTO.name}" name="name"  pattern="[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+">
						</div>
						<div class="form-group">
							<label for="surname" class="control-label"><spring:message code="new_user.l_name" />:</label> <input
								id="surname" type="text" class="form-control" required
								value="${userDTO.secondname}" name="secondname"  pattern="[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+[\-]?'?[A-Za-zА-Яа-я]+">
						</div>
						<div class="form-group">
							<label class="control-label"><spring:message code="new_user.role" />:</label> <input type="text"
								class="form-control user_role" readonly="readonly" required
								value="${userDTO.role}">
						</div>
						<input type="hidden" class="emailischange" name="emailischange" value="false">
						<input type="hidden" class="phoneischange" name="phoneischange" value="false">
						
						<c:choose>
							<c:when test="${userDTO.role == 'Student'}">
								<label class="control-label"><spring:message code="new_user.group" /></label>
								<div class="add-group">
									<select name="groupId" class="form-control input-left chosen-select" multiple id="groupId">
										<option  value=" "></option>
										<c:forEach items="${studentGroups}" var="currGroups">
											<option selected value="${currGroups.id}">${currGroups.name}</option>
										</c:forEach>
										<c:forEach items="${othersGroups}" var="otherGroups">
											<option value="${otherGroups.id}">${otherGroups.name}</option>
										</c:forEach>
									</select>
								</div>
								<input type="hidden" class="form-control courseId"
									name="courseId" autocomplete="off" value="">
							</c:when>
							<c:when test="${userDTO.role == 'Teacher'}">
								<div class="form-group">
								<label class="control-label"><spring:message code="edit.courses" /></label>
								<div class="clearfix"></div>
								<select name="courseId" class="form-control input-left chosen-select" multiple>
									<c:forEach items="${teacherCourses}" var="currCourses">
										<option selected value="${currCourses.id}">${currCourses.name}</option>
									</c:forEach>
									<c:forEach items="${uncheckedCourses}" var="unchecdCourses">
										<option value="${unchecdCourses.id}">${unchecdCourses.name}</option>
									</c:forEach>
								</select>
								<input type="hidden" class="form-control groupId" name="groupId"
									autocomplete="off" value="">
									</div>
							</c:when>
							<c:otherwise>
								<input type="hidden" class="form-control groupId" name="groupId"
									autocomplete="off" value="">
								<input type="hidden" class="form-control courseId"
									name="courseId" autocomplete="off" value="">
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form>
			<button onclick="submit()" id="submit-btn"class="btn btn-success btn-block"><spring:message code="new_user.submit"/></button>
		</div>
	</div>
</div>

<!-- Modal -->
<div id="warning1" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<spring:message code="warning.warning" />
				</h4>
			</div>
			<div class="modal-body">
				<p>
					<spring:message code="warning.course1" />
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="warning.ok" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<script>

$(document).ready(function() {
	multiselect();
	var emailBefor = document.getElementsByName("email")[0].value;
	var phoneBefore = document.getElementsByName("phone")[0].value;
	
	$("#email").on('change', function(){
		var emailAfter = $('#email').val();
		if(emailBefor != emailAfter){
			$('.emailischange').val("true");
		}
	});
  	
 	$("#phone").change(function(){
		var phoneAfter = $("#phone").val();
		if(phoneBefore != phoneAfter){
			$(".phoneischange").val("true");
		}
	}); 
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
	
	function submit(){
		var userRole = $(".user_role").val();
		if(userRole == 'Student'){
			var inputCount1 = document.getElementsByClassName("search-choice").length;

			if(inputCount1 < 1){
				$("#groupId").val(" ");
				document.edit_user_form.submit();
			}else{
				document.edit_user_form.submit();
			}
		}
		if(userRole == 'Teacher'){
			var inputCount2 = document.getElementsByClassName("search-choice").length;
			if(inputCount2 < 1){
				$('#warning1').modal();
			}else{
				document.edit_user_form.submit();
			}
		}
		if(userRole == 'Manager'){
			document.edit_user_form.submit();
		}
	}

	function modalWarning(trouble){
		var text = '<div id="modalWindow" class="modal fade" role="dialog"><div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title"><spring:message code="warning.warning" /></h4></div><div class="modal-body"><p>';
		text += trouble;
		text += '</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="warning.ok" /></button></div></div></div></div>';
		console.log(text);
		$("#modalBlock").html(text);
	}
</script>