<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-8">
				<div class="panel panel-success">
					<div class="panel-heading ">
						<h3><spring:message code="viewUser.admin_page"/></h3>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
					 <ul class="list-group">
						<li class="list-group-item"><a href="view_users"><spring:message code="allUsers.users"/></a> <span class="pull-right"><a href="new_user" class="btn btn-xs btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a></span></li>
						<li class="list-group-item"><a href="view_courses"><spring:message code="viewUser.courses"/></a> <span class="pull-right"><sec:authorize
							access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')">
							<a href="new_course" class="btn btn-xs btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_TEACHER')">
							<a href="new_course" class="btn btn-xs btn-success disabled"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a>
						</sec:authorize></span></li>
						<li class="list-group-item"><a href="view_courses"><spring:message code="viewUser.groups"/></a> <span class="pull-right"><a href="new_group" class="btn btn-xs btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a></span></li>
						<li class="list-group-item"><a href="view_tests"><spring:message code="viewTest.tests"/></a><span class="pull-right"><a href="new_test" class="btn btn-xs btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a></span></li>
						<li class="list-group-item"><a href="view_themes"><spring:message code="viewTest.themes"/></a> <span class="pull-right"><a href="new_theme" class="btn btn-xs btn-success"><i class="fa fa-plus"></i><spring:message code="admin.add"/></a></span></li>
                    </ul>
					</div>
				</div>
		</div>
	</div>
</div>

