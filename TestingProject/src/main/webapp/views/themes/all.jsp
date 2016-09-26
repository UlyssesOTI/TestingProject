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
		<div class="col-md-12">
			<h2>
				<spring:message code="viewThemes.themes" />
			</h2>
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li class="active"><spring:message code="themes" /></li>
			</ol>
			<!-- breadcrumbs -->
			<div class="panel panel-success ">
				<div class="panel-heading ">
					<a href="new_theme" class="btn btn-success btn-sm "><i
						class="fa fa-plus"></i>&#160<spring:message code="viewThemes.add" /></a>
				</div>
				<c:set var="counter" value="0" />
				<!-- 100% width block -->
				<div class="col-xs-12">
					<c:forEach items="${courses}" var="course" varStatus="rowCounter">
						<c:if test="${rowCounter.count % 4 == 1}">
							<div class="row">
						</c:if>
						<div class="col-lg-3 col-md-6 col-xs-12">
							<h3>
								<a href="view_themes_course${course.id}"><i
									class="fa fa-folder-o  fa-lg"></i> ${course.name}</a>
							</h3>
						</div>
						<c:if
							test="${rowCounter.count % 4 == 0 || rowCounter.count == fn:length(values)}">
				</div>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
</div>
</div>
