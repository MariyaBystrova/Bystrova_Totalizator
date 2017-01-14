<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.remember_me" var="remember_me" />
<fmt:message bundle="${loc}" key="local.enter_password"
	var="enter_password" />
<fmt:message bundle="${loc}" key="local.enter_login" var="enter_login" />
<fmt:message bundle="${loc}" key="local.sign_in" var="sign_in" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.registration" var="registration" />
<fmt:message bundle="${loc}" key="local.registration_form"
	var="registration_form" />
<fmt:message bundle="${loc}" key="local.first_name" var="first_name" />
<fmt:message bundle="${loc}" key="local.last_name" var="last_name" />
<fmt:message bundle="${loc}" key="local.create_login" var="create_login" />
<fmt:message bundle="${loc}" key="local.create_password"
	var="create_password" />
<fmt:message bundle="${loc}" key="local.confirm_password"
	var="confirm_password" />
<fmt:message bundle="${loc}" key="local.gender" var="gender" />
<fmt:message bundle="${loc}" key="local.unknown" var="unknown" />
<fmt:message bundle="${loc}" key="local.male" var="male" />
<fmt:message bundle="${loc}" key="local.female" var="female" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.country" var="country" />
<fmt:message bundle="${loc}" key="local.city" var="city" />
<fmt:message bundle="${loc}" key="local.address" var="address" />
<fmt:message bundle="${loc}" key="local.register" var="register" />
<fmt:message bundle="${loc}" key="local.message_register_user_success"
	var="message_register_success" />
<fmt:message bundle="${loc}" key="local.message_register_user_failed"
	var="message_register_failed" />

<title><c:out value="${registration}" /></title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="CSS/style.css">

</head>
<body>
	<header>
		<%@include file="../../Content/header.jsp"%>
	</header>

	<aside class="sidebar-right">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="logination" /> <label
				for="signin-login"><c:out value="${login}" />:</label> <input
				type="text" name="signin-login" value="" id="signin-login"
				class="form-control" placeholder="${enter_login}"
				required="required" /> <label for="signin-password"><c:out
					value="${password}" />:</label> <input type="password"
				name="signin-password" value="" id="signin-password"
				class="form-control" placeholder="${enter_password}"
				required="required" />
			<section>
				<div class="checkbox">
					<label><input type="checkbox"> <c:out
							value="${remember_me}" /></label>
				</div>
				<input type="submit" value="${sign_in}" class="btn btn-default">
			</section>
		</form>
	</aside>

	<aside class="sidebar-right-news">
		<c:if test="${sessionScope.local eq 'ru' }">
			<jsp:include page="../../Content/aside-ru.html" />
		</c:if>
		<c:if test="${sessionScope.local eq 'en' or empty sessionScope.local}">
			<jsp:include page="../../Content/aside-en.html" />
		</c:if>
	</aside>



	<div class="content main">

		<div class="container">
			<c:if
				test="${not empty sessionScope.resultAdd and sessionScope.resultAdd}">
				<c:out value="${message_register_success}" />
			</c:if>
			<c:if
				test="${not empty sessionScope.resultAdd and not sessionScope.resultAdd }">
				<c:out value="${message_register_failed}" />
			</c:if>
		</div>
		<form action="Controller" method="post" onsubmit='return validate()'
			class="registration-form">
			<h3>
				<c:out value="${registration_form}" />
				:
			</h3>

			<input type="hidden" name="command" value="registration" />
			<section>
				<label for="first-name"><c:out value="${first_name}" />:</label> <input
					type="text" name="first-name" id="first-name"
					onchange='return validateFirstName()' />
				<p id="err-first-name"></p>
			</section>

			<section>
				<label for="last-name"><c:out value="${last_name}" />:</label> <input
					type="text" name="last-name" id="last-name"
					onchange='return validateLastName()' />
				<p id="err-last-name"></p>
			</section>

			<section>
				<label for="sex"><c:out value="${gender}" />:</label> <select
					name="sex" id="sex">
					<option value="unknown" selected><c:out value="${unknown}" /></option>
					<option value="M"><c:out value="${male}" /></option>
					<option value="F"><c:out value="${female}" /></option>
				</select>
			</section>
			<section>
				<label for="login"><c:out value="${create_login}" />:</label> <input
					type="text" name="login" value="" id="login"
					onchange='return validateLogin()' />
				<p id="err-login"></p>
			</section>
			<section>
				<label for="password"><c:out value="${create_password}" />:</label>
				<input type="password" name="password" value="" id="password"
					onchange='return validatePassword()' />
				<p id="err-password"></p>
			</section>
			<section>
				<label for="rpassword"><c:out value="${confirm_password}" />:</label>
				<input type="password" name="password-again" value="" id="rpassword"
					onchange='return validateRPassword()' />
				<p id="err-rpassword"></p>
			</section>
			<section>
				<label for="email"><c:out value="${email}" />:</label> <input
					type="email" name="email" value="" id="email"
					onchange='return validateEmail()' />
				<p id="err-email"></p>
			</section>
			<section>
				<label for="country"><c:out value="${country}" />:</label> <input
					type="text" name="country" value="" id="country"
					onchange='return validateCountry()' />
				<p id="err-country"></p>
			</section>
			<section>
				<label for="city"><c:out value="${city}" />:</label> <input
					type="text" name="city" value="" id="city"
					onchange='return validate()' />
				<p id="err-city"></p>
			</section>
			<section>
				<label for="address"><c:out value="${address}" />:</label> <input
					type="text" name="address" value="" id="address"
					onchange='return validate()' />
				<p id="err-address"></p>
			</section>
			<section>
				<input type="submit" value="${register}">
			</section>
		</form>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../Content/footer.jsp"%>
	</footer>

	<script src="JS/script.js"></script>


</body>

</html>