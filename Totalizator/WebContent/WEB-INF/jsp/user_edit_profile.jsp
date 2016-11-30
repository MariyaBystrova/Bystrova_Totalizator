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
<fmt:message bundle="${loc}" key="local.save" var="save" />
<fmt:message bundle="${loc}" key="local.personal_info"
	var="personal_info" />
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
<fmt:message bundle="${loc}" key="local.hello_user" var="hello" />
<fmt:message bundle="${loc}" key="local.log_out" var="logout" />
<fmt:message bundle="${loc}" key="local.edit_profile" var="edit_profile" />
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.general" var="general" />
<fmt:message bundle="${loc}" key="local.toto" var="toto" />
<fmt:message bundle="${loc}" key="local.company" var="company" />
<fmt:message bundle="${loc}" key="local.banner" var="banner" />
<fmt:message bundle="${loc}" key="local.page_language"
	var="page_language" />

<title><c:out value="${edit_profile}" /></title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="CSS/style.css">

</head>
<body>
	<header>
		<h1>
			<c:out value="${company}" />
		</h1>
		<section>
			<p class="banner">
				<i><c:out value="${banner}" /></i>
			<p>
		</section>
		<section>
			<ul>
				<li><a href="?command=go-to-general" class="a-dec"><c:out
							value="${general}" /></a></li>
				<li><a href="?command=show-current-coupon" class="a-dec"><c:out
							value="${toto}" /></a></li>
				<li><a href="?command=go-to-edit-profile" class="a-dec"><c:out
							value="${edit_profile}" /></a></li>

				<li class="rightitem">
					<section class="dropdown">
						<button class="dropbtn">
							<c:out value="${page_language}" />
						</button>
						<section class="dropdown-content">
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="change-language" />
								<input type="hidden" name="lang" value="ru" /> <input
									type="submit" name="local" value="${ru}" />
							</form>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="change-language" />
								<input type="hidden" name="lang" value="en" /> <input
									type="submit" name="local" value="${en}" />
							</form>
						</section>
					</section>
				</li>
				<li class="rightitem">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="logout"> <input
							type="submit" value="${logout}" class="log-out">
					</form>
				</li>

			</ul>
		</section>
	</header>
	<aside class="sidebar-right">
		<h2>Новости футбола:</h2>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rfpl/reviews/dubl-propusk-v-sbornuyu-tura-1063201/">ДУБЛЬ
					– ПРОПУСК В СБОРНУЮ ТУРА</a>
			</h3>
			<p>"СЭ" представляет свой вариант 11 лучших футболистов 13-го
				тура.</p>
			<time datetime="2016-11-07">2016-11-07 11:15</time>
		</article>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rusteam/reviews/davno-takogo-ne-bylo-sbornaya-bez-cska-1063197/">ДАВНО
					ТАКОГО НЕ БЫЛО. СБОРНАЯ - БЕЗ ЦСКА</a>
			</h3>
			<p>В воскресенье поздно вечером в составе сборной России
				произошли значительные изменения.</p>
			<time datetime="2016-11-07">2016-11-07 11:00</time>
		</article>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rfpl/reviews/bezumie-v-krasnodare-shest-golov-travma-vratarya-i-dva-pryamyh-udaleniya-1063072/?utm_source=materials&utm_medium=link&utm_campaign=plista">БЕЗУМИЕ
					В КРАСНОДАРЕ</a>
			</h3>
			<p>Шесть голов, травма вратаря и два прямых удаления...</p>
			<time datetime="2016-11-06">2016-11-06 21:45</time>
		</article>
	</aside>

	<div class="content main">
		<jsp:useBean id="user" class="by.tr.totalizator.entity.User"
			scope="session" />

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
				<c:out value="${personal_info}" />
				:
			</h3>

			<input type="hidden" name="command" value="edit-profile" />
			<section>
				<label for="first-name"><c:out value="${first_name}" />:</label> <input
					type="text" name="first-name" id="first-name"
					value="${user.firstName }" onchange='return validateFirstName()' />
				<p id="err-first-name"></p>
			</section>

			<section>
				<label for="last-name"><c:out value="${last_name}" />:</label> <input
					type="text" name="last-name" id="last-name"
					value="${user.lastName }" onchange='return validateLastName()' />
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
					type="email" name="email" id="email" value="${user.email }"
					onchange='return validateEmail()' />
				<p id="err-email"></p>
			</section>
			<section>
				<label for="country"><c:out value="${country}" />:</label> <input
					type="text" name="country" id="country" value="${user.country }"
					onchange='return validateCountry()' />
				<p id="err-country"></p>
			</section>
			<section>
				<label for="city"><c:out value="${city}" />:</label> <input
					type="text" name="city" id="city" value="${user.city }"
					onchange='return validate()' />
				<p id="err-city"></p>
			</section>
			<section>
				<label for="address"><c:out value="${address}" />:</label> <input
					type="text" name="address" id="address" value="${user.address }"
					onchange='return validate()' />
				<p id="err-address"></p>
			</section>
			<section>
				<input type="submit" value="${save}">
			</section>
		</form>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../footer.jsp"%>
	</footer>

	<script src="JS/script.js"></script>
</body>

</html>