<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.totalizator_title"
	var="totalizator" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.sign_in" var="sign_in" />
<fmt:message bundle="${loc}" key="local.registration" var="registration" />
<fmt:message bundle="${loc}" key="local.remember_me" var="remember_me" />
<fmt:message bundle="${loc}" key="local.enter_password"
	var="enter_password" />
<fmt:message bundle="${loc}" key="local.enter_login" var="enter_login" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${totalizator}" /></title>

<link rel="stylesheet" type="text/css" href="CSS/style.css">
<link href="https://fonts.googleapis.com/css?family=Pattaya"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

</head>
<body>

	<header>
		<%@include file="Content/header.jsp"%>
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
		<form action="Controller" method="get">
			<input type="hidden" name="command" value="go-to-registration" /> <span>
				<input type="submit" value="${registration}" class="btn btn-default" />
			</span>
		</form>
	</aside>

	<aside class="sidebar-right-news">
		<c:if test="${sessionScope.local eq 'ru' }">
			<jsp:include page="Content/aside-ru.html" />
		</c:if>
		<c:if test="${sessionScope.local eq 'en' or empty sessionScope.local}">
			<jsp:include page="Content/aside-en.html" />
		</c:if>
	</aside>

	<div class="content main">
		<c:if test="${sessionScope.local eq 'ru' }">
			<jsp:include page="Content/rules-ru.html" />
		</c:if>
		<c:if test="${sessionScope.local eq 'en' or empty sessionScope.local}">
			<jsp:include page="Content/rules-en.html" />
		</c:if>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="Content/footer.jsp"%>
	</footer>

</body>
</html>