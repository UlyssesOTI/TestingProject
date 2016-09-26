<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- host link --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>

<!-- header -->
<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div class="row">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#responsive-menu">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<img class="navbar-brand logo hidden-sm"
					src="<c:url value="/resources/images/logo.png"/>" alt="logo"
					title="Logos" />
			</div>
			<div class="collapse navbar-collapse" id="responsive-menu">
				<ul class="nav navbar-nav pull-left">
					<sec:authorize access="!isAuthenticated()">
						<li><a class="text-center text-md"><i
								class="fa fa-user  full-scr"></i> <spring:message
									code="header.myCab" /></a></li>

						<li><a class="text-center text-md"><i
								class="fa fa-users fa-2x full-scr"></i> <spring:message
									code="header.myGroup" /></a></li>
						<li><a class="text-center text-md"><i
								class="fa fa-university  fa-2x full-scr"></i> <spring:message
									code="header.schedule" /></a></li>

						<li><a class="text-center text-md"><i
								class="fa fa-graduation-cap fa-2x full-scr"></i> <spring:message
									code="header.materials" /></a></li>

						<li><a class="text-center text-md"><i
								class="fa  fa-list-alt  fa-2x full-scr"></i> <spring:message
									code="header.tests" /></a></li>
						<li><a class="text-center text-md"><i
								class="fa fa-comments  fa-2x full-scr"></i> <spring:message
									code="header.forum" /></a></li>

					</sec:authorize>



					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_STUDENT')">
						<li><a
							href="${hostURL}/view_user_<sec:authentication property="name" />"
							class="text-center text-md"><i class="fa fa-user  full-scr"></i>
								<spring:message code="header.myCab" /></a></li>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
						<li><a href="${hostURL}/admin_page"
							class="text-center text-md"><i class="fa fa-user  full-scr"></i>
								<spring:message code="header.myCab" /></a></li>
					</sec:authorize>


					<sec:authorize access="hasAnyRole('ROLE_STUDENT')">
						<li><a
							href="${hostURL}/view_groups_<sec:authentication property="name" />"
							class="text-center text-md"><i
								class="fa fa-users fa-2x full-scr"></i> <spring:message
									code="header.myGroup" /></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
						<li><a href="${hostURL}/view_courses" class="text-center text-md"><i
								class="fa fa-users fa-2x full-scr"></i> <spring:message
									code="header.myGroup" /></a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li><a href="#" class="text-center text-md"><i
								class="fa fa-university  fa-2x full-scr"></i> <spring:message
									code="header.schedule" /></a></li>

						<li><a href="#" class="text-center text-md"><i
								class="fa fa-graduation-cap fa-2x full-scr"></i> <spring:message
									code="header.materials" /></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_TEACHER','ROLE_ADMIN')">
						<li><a href="${hostURL}/view_tests"
							class="text-center text-md"><i
								class="fa  fa-list-alt  fa-2x full-scr"></i> <spring:message
									code="header.tests" /></a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li><a href="#" class="text-center text-md"><i
								class="fa fa-comments  fa-2x full-scr"></i> <spring:message
									code="header.forum" /></a></li>
					</sec:authorize>
				</ul>

				<sec:authorize access="isAuthenticated()">
					<div class="pull-right login-div">
						<a href="${hostURL}/logout" class="btn btn-primary  login-btn">
							<i class="fa fa-sign-out"></i> <spring:message
								code="header.log_out" />
						</a>
					</div>
				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<div class="pull-right login-div">
						<a href="${hostURL}/loginpage" class="btn btn-primary  login-btn">
							<i class="fa fa-sign-in"></i> <spring:message
								code="header.log_in" />
						</a>
					</div>
				</sec:authorize>

			</div>
		</div>
	</div>
</nav>
<!-- header -->
