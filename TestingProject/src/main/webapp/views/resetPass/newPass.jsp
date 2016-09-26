<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="container margin-top">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<spring:message code="label.enter" />
					</h3>
				</div>
				<div class="panel-body">

					<form action=restoringPass method="POST" name="restore">


						<div class="form-group">
							<label for="inputLogin"><spring:message
									code="resetpass.pass1" /></label> <input class="form-control"
								type="text" name="pass1" required autofocus>
						</div>
						<div class="form-group">
							<label for="inputLogin"><spring:message
									code="resetpass.pass2" /></label> <input class="form-control"
								type="text" name="pass2" required autofocus>
						</div>

						<input type="hidden" name="userId" value="${usersId}">
					</form>
					<button onclick="submit()" id="submit-btn"
						class="btn btn-success btn-block">
						<spring:message code="new_user.submit" />
					</button>

				</div>
			</div>
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
					<spring:message code="resetpass.equlas" />
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

</html>

<script>
	function submit() {
		var password1 = $("[name=pass1]").val();
		var password2 = $("[name=pass2]").val();
		if (password1 == password2) {
			document.restore.submit();
		} else {
			$('#warning1').modal();
		}

	}
</script>
