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
<fmt:message bundle="${loc}" key="local.forming_coupon" var="forming_coupon" />
<fmt:message bundle="${loc}" key="local.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="local.end_date" var="end_date" />
<fmt:message bundle="${loc}" key="local.min_bet_amount" var="min_bet_amount" />
<fmt:message bundle="${loc}" key="local.create" var="create" />
<fmt:message bundle="${loc}" key="local.message_coupon_add_success" var="message_success" />
<fmt:message bundle="${loc}" key="local.message_coupon_add_failed" var="message_failed" />

<title>Form coupon</title>

<link rel="stylesheet" type="text/css" href="CSS/admin-style.css">

</head>
<body>
	
	<ul>
		<li><a href="?command=admin-go-to-general"><c:out
					value="${general}" /></a></li>
		<li class="active"><a href="?command=admin-go-to-form-coupon"><c:out
					value="${form_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-form-matches"><c:out
					value="${form_matches_to_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-edit-current-coupon"><c:out
					value="${edit_match_results}" /></a></li>
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
					name="local" value="${en}" class="input-submit"/>
			</form>
		</li>
	</ul>
	
	<div  class="main">
		<c:if test="${not empty sessionScope.resultAdd and sessionScope.resultAdd}">
			<c:out value="${message_success}" />
		</c:if>
		<c:if test="${not empty sessionScope.resultAdd and not sessionScope.resultAdd }">
			<c:out value="${message_failed}" />
		</c:if>
	
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="register-coupon">
			<div>
				<h2><c:out value="${forming_coupon}" />:</h2>
			</div>
			<div>
				<div>
					<label for="coupon-start-date"><c:out value="${start_date}" />:</label>
				</div>
				<div>
					<input type="datetime-local" name="coupon-start-date"
						id="coupon-start-date" placeholder="yyyy-mm-dd hh:mm"  value=""required="required"/>
				</div>
			</div>
			<div>
				<div>
					<label for="coupon-end-date"><c:out value="${end_date}" />:</label>
				</div>
				<div>
					<input type="datetime-local" name="coupon-end-date"
						id="coupon-end-date" placeholder="yyyy-mm-dd hh:mm" value="" required="required"/>
				</div>
			</div>
			<div>
				<div>
					<label for="coupon-min-bet-amount"><c:out value="${min_bet_amount}" />:</label>
				</div>
				<div>
					<input type="number" name="coupon-min-bet-amount"
						id="coupon-min-bet-amount" value="0" required="required"/>
				</div>
			</div>
			<input type="submit" value="${create}" class="btn btn-default">
		</form>
	</div>

</body>
</html>