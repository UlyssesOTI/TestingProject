<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="container margin-top">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">

			<c:if test="${not empty message}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign"
						aria-hidden="true"></span> <span class="sr-only">Error:</span>


					<c:out value="${message}" />

				</div>
			</c:if>

			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><spring:message code="label.enter" /></h3>
				</div>
				<div class="panel-body">
					<form role="form" action="loginprocessing" method="post"
						autocomplete="off">



						<fieldset>
							<div class="form-group">
								<label for="inputLogin"><spring:message code="label.login" /></label> <input
									class="form-control" placeholder="E-mail Або Телефон"
									type="text" name="username" required autofocus>
							</div>
							<div class="form-group">
								<label for="inputPassword"><spring:message code="label.password" /></label> <input
									class="form-control" type="password" name="password"
									placeholder="Пароль" required>
							</div>

							<!-- Change this to a button or input when using this as a form -->
							<button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="label.sign_inn" /></button>

						</fieldset>
					</form>
				</div>
				<a  class="btn btn-link pull-right" href="resetPass"><spring:message code="resetpass.forgot"/></a>
			</div>
		</div>
	</div>
</div>



