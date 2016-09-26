<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href=<c:url value="/resources/css/bootstrap.min.css"></c:url>>
<link rel="stylesheet"
	href=<c:url value="/resources/css/font-awesome.min.css"></c:url>>
<link rel="stylesheet"
	href=<c:url value="/resources/css/style.css"></c:url>>

<!-- Latest compiled and minified JavaScript -->
<!-- Latest compiled and minified CSS -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


<meta charset="UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>

<style>
.body228 {
	min-height: 78vh;
}

</style>
<script >

</script>

</head>
<body>


	<header>
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</header>
	<div class="container body228">
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</div>

	<footer>
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</footer>

	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

	<script>
		$(document).ready(
				function() {
/*
					$('body').bind("DOMSubtreeModified", function() {
						footerPosition();
						//console.log("change >>>")
					});

					$('body').on('resize', function() {
						footerPosition();
					});

					document.addEventListener("DOMSubtreeModified",
							function(e) {
								footerPosition();
							}, false);

					footerPosition();

					function footerPosition() {
						 
						if (window.innerHeight >= $('body').height()) {
							$("footer.navbar-bottom").addClass(
									"navbar-fixed-bottom")
						} else {
							$("footer.navbar-bottom").removeClass(
									"navbar-fixed-bottom")
						}
					}*/

				});
	</script>

</body>
</html>