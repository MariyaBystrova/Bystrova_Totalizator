<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.log_out" var="logout" />
<fmt:message bundle="${loc}" key="local.general" var="general" />
<fmt:message bundle="${loc}" key="local.form_coupon" var="form_coupon" />
<fmt:message bundle="${loc}" key="local.form_matches_to_coupon"
	var="form_matches_to_coupon" />
<fmt:message bundle="${loc}" key="local.edit_match_results" var="edit_match_results" />
<fmt:message bundle="${loc}" key="local.show_all_coupons" var="show_all_coupons" />
<fmt:message bundle="${loc}" key="local.hello_admin" var="hello_admin" />
<fmt:message bundle="${loc}" key="local.welcome_admin"
	var="title" />

<title><c:out value="${title}" /></title>

<link rel="stylesheet" type="text/css" href="CSS/admin-style.css">

</head>
<body>

	<ul>
		<li class="active"><a href="?command=admin-go-to-general"><c:out
					value="${general}" /></a></li>
		<li><a href="?command=admin-go-to-form-coupon"><c:out
					value="${form_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-form-matches"><c:out
					value="${form_matches_to_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-edit-current-coupon"><c:out
					value="${edit_match_results}"  /></a></li>
		<li><a href="?command=admin-go-to-show-all-coupons"><c:out
					value="${show_all_coupons}"  /></a></li>
					
		<li><hr /></li>
		<li><form action="Controller" method="post">
				<input type="hidden" name="command" value="logout"><input
					type="submit" value="${logout}" class="input-submit">
			</form></li>
		<li>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="change-language" /> <input
					type="hidden" name="lang" value="ru" /> <input type="submit"
					name="local" value="${ru}" class="input-submit" />
			</form>
		</li>
		<li>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="change-language" /> <input
					type="hidden" name="lang" value="en" /> <input type="submit"
					name="local" value="${en}" class="input-submit" />
			</form>
		</li>
	</ul>
	<div class="main">
		<h2>
			<c:out value="${hello_admin}" />
		</h2>
	</div>
</body>
</html>