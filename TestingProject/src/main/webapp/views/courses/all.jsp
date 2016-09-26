<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- container -->
<div class="container">
	<div class="col-md-12">
		<h2>
			<spring:message code="viewCourse.groupsList" />
		</h2>
		<!-- breadcrumbs -->
		<ul class="breadcrumb">
			<li class="active"><spring:message code="editGroup.groups" /><span
				class="divider"></span></li>
		</ul>
		<!-- breadcrumbs -->
		
		<div class="bg-success ">
				<a href="new_course" class="btn btn-success btn-sm "><i
					class="fa fa-plus"></i>&#160Add Course</a>
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
						<a href="view_course_${course.id}"><i
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

