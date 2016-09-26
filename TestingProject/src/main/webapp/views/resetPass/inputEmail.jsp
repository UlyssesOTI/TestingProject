<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container margin-top">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<c:if test="${emailIsntExist eq true}">
				<div class="alert alert-danger">
					<strong><spring:message code="resetpass.users" /></strong>
					<spring:message code="resetpass.users2" />
				</div>
			</c:if>
			<c:if test="${messageSended eq true}">
				<div class="alert alert-danger">
					<strong><spring:message code="resetpass.mess" /></strong>
					<spring:message code="resetpass.mess2" />
				</div>
			</c:if>
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<spring:message code="label.enter" />
					</h3>
				</div>
				<div class="panel-body">
					<form action="resetPass" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="inputLogin"><spring:message
										code="label.login" /></label> <input class="form-control"
									placeholder="E-mail Або Телефон" type="text" name="email"
									required autofocus>
							</div>
							<button type="submit" id="submit-btn"
								class="btn btn-lg btn-success btn-block">
								<spring:message code="new_user.submit" />
							</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>