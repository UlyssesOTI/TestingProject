<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src='<c:url value="/resources/js/maskedinput.js"></c:url>'></script>

<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<form action="edit_my_info" id="send" method="POST">
				<div class="panel panel-success">
					<div class="panel-heading ">
						<h3><spring:message code="editOwn.editown"/></h3>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<c:if test="${message}">
							<span class="messageWrong"><spring:message code="warning.pass"/></span>
						</c:if>
						<div class="form-group">
							<label for="email" class="control-label"><spring:message code="new_user.email" />:</label> <input
								id="email" type="hidden" value="${userDTO.id}" name="id">
							<input type="email" class="form-control" required
								value="${userDTO.email}" name="email">
						</div>
						<div class="form-group">
							<label for="phone"><spring:message code="new_user.phone" />:</label><input id="phone" type='tel'
								class="form-control" required value="${userDTO.phone}"
								name="phone" title="Format: +380xxxxxxxxx"
								placeholder="+380xxxxxxxxx" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="phone"><spring:message code="editOwn.oldPass" />:</label>
							<input type="hidden" class="form-control" 
								value="${userDTO.password}" name="password">
							<input type="password" class="form-control"
								placeholder="current password" name="currentPassword">
						</div>
						<div class="form-group">
							<label for="phone"><spring:message code="editOwn.newPass" />:</label>
							<input type="password" class="form-control"
								placeholder="new password" name="newPassword">
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
							<label  class="control-label"><spring:message code="new_user.role" />:</label>
							<input type="text" class="form-control" readonly="readonly"
								required value="${userDTO.role}">
						</div>
						<div class="form-group">
							<div class="form-group">
								<button type="submit" id="submit-btn"
									class="btn btn-success btn-block"><spring:message code="new_user.submit" /></button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
