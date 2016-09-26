
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!--  FOOTER -->
<!-- <footer class="navbar-bottom navbar-inverse" id="footer1"> -->
<footer class="navbar-bottom navbar-inverse">
	<div class="navbar-inner">
		<div class="container">

			<div class="col-md-9 col-xs-12">

				<div class="footer-contact-cont">
					<div class="footer-bl">
						<div class="col-md-3 col-sm-3 col-xs-3 contact-item">
							<p class="fa fa-2x fa-phone"></p>
							<p>(032) 290-49-98</p>
							<p>(067) 990-11-22</p>
						</div>
						<div class="col-md-3 col-sm-3  col-xs-3 contact-item">
							<p class="fa fa-2x fa-map-marker"></p>
							<p>
								<spring:message code="footer.city" />
								<br />
								<spring:message code="footer.street" />
								<br />
								<spring:message code="footer.cab" />
							</p>
						</div>

					</div>
					<div class="footer-bl">
						<div class="col-md-3 col-sm-3 col-xs-3 contact-item">
							<p class="fa fa-2x fa-envelope-o"></p>
							<p>info@lgs.lviv.ua</p>
						</div>
						<div class="col-md-3 col-sm-3 col-xs-3 contact-item">
							<p class="fa fa-2x fa-skype"></p>
							<p>logos_lviv</p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-xs-12">
				<ul class="footer-ul">
					<li class="footer-li"><a href="http://instagram.com"><i
							class="fa fa-2x fa-instagram"></i></a></li>
					<li class="footer-li"><a href="http://instagram.com"><i
							class="fa  fa-2x fa-facebook-square"></i></a></li>
					<li class="footer-li"><a href="http://instagram.com"><i
							class="fa  fa-2x fa-vk"></i></a></li>
					<li class="footer-li"><a href="?lang=en"
						>en</a> <a
						href="?lang=ua">ua</a></li>

				</ul>
			</div>
		</div>
	</div>
</footer>
<!--  FOOTER -->

