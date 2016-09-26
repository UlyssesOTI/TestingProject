<%@ page language="java" contentType="text/html; charset=UTF-"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="container">

		<!-- breadcrumbs -->
		<ul class="breadcrumb">
			<li><a href="view_user_${userId}">Мій профіль</a><span class="divider"></span></li>
			<li class="active">Мoї групи</li>
		</ul>
		<!-- breadcrumbs >-->
		
		
		
	
		
	<c:forEach items="${groups}" var="group" varStatus="rowCounter">
			<c:if test="${rowCounter.count % 3 == 1}">
					<div class="row">
			</c:if>
		<div class="col-lg-4 col-md-6 col-xs-12">
			<div class="panel panel-success">
				<div class="panel-heading ">
					<h3>
						<a href="view_group_${group.id}">${group.name}</a>
					</h3>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<ul class="list-group">
						<c:forEach items="${group.users}" var="student">
							<li class="list-group-item"><a
								href="view_user_${student.id}">${student.name}
									${student.secondname}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
			<c:if
					test="${rowCounter.count % 3 == 0 || rowCounter.count == fn:length(values)}">
		</div>	</c:if>
	</c:forEach>
	
	
	
	</div>
</div>

