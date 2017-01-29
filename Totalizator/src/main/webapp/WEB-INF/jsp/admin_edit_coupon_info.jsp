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
<fmt:message bundle="${loc}" key="local.edit_match_results"
	var="edit_match_results" />
<fmt:message bundle="${loc}" key="local.show_all_coupons"
	var="show_all_coupons" />
<fmt:message bundle="${loc}" key="local.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="local.end_date" var="end_date" />
<fmt:message bundle="${loc}" key="local.min_bet_amount"
	var="min_bet_amount" />
<fmt:message bundle="${loc}" key="local.save" var="save" />
<fmt:message bundle="${loc}" key="local.jackpot" var="jackpot" />
<fmt:message bundle="${loc}" key="local.pool" var="pool" />
<fmt:message bundle="${loc}" key="local.status" var="status" />
<fmt:message bundle="${loc}" key="local.edit_coupon_info_success"
	var="edit_coupon_info_success" />
<fmt:message bundle="${loc}" key="local.edit_coupon_info_fail"
	var="edit_coupon_info_fail" />
<fmt:message bundle="${loc}" key="local.edit_coupon" var="edit_coupon" />
<fmt:message bundle="${loc}" key="local.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.open" var="open" />
<fmt:message bundle="${loc}" key="local.closed" var="closed" />
<fmt:message bundle="${loc}" key="local.cancelled" var="cancelled" />
<fmt:message bundle="${loc}" key="local.free" var="free" />


<title><c:out value="${edit_coupon}" /></title>


<link rel="stylesheet" type="text/css" href="CSS/admin-style.css">

</head>
<body>

	<ul>
		<li><a href="?command=admin-go-to-general"><c:out
					value="${general}" /></a></li>
		<li><a href="?command=admin-go-to-form-coupon"><c:out
					value="${form_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-form-matches"><c:out
					value="${form_matches_to_coupon}" /></a></li>
		<li><a href="?command=admin-go-to-edit-current-coupon"><c:out
					value="${edit_match_results}" /></a></li>
		<li><a href="?command=admin-go-to-show-all-coupons"><c:out
					value="${show_all_coupons}" /></a></li>

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
	<div class="main coupon-registration-form">

		<div>
			<div>
				<c:if
					test="${not empty sessionScope.resultEdit and sessionScope.resultEdit}">
					<c:out value="${edit_coupon_info_success}" />
				</c:if>
				<c:if
					test="${not empty sessionScope.resultEdit and not sessionScope.resultEdit }">
					<c:out value="${edit_coupon_info_fail}" />
				</c:if>
			</div>

			<jsp:useBean id="coupon" class="by.tr.totalizator.entity.bean.Coupon"
				scope="request" />

			<form action="Controller" method="post">
				<input type="hidden" name="command" value="edit-coupon-info">
				<input type="hidden" name="coupon-id"
					value="${requestScope.couponId}">
				<h2>
					<c:out value="${edit_coupon}" />
					:
				</h2>
				<div>
					<div>
						<label for="coupon-start-date"><c:out
								value="${start_date}" />:</label>
					</div>
					<div>
						<input type="datetime-local" name="coupon-start-date"
							id="coupon-start-date" placeholder="yyyy-mm-dd hh:mm"
							value="${coupon.startDateString}" required="required" />
					</div>
				</div>
				<div>
					<div>
						<label for="coupon-end-date"><c:out value="${end_date}" />:</label>
					</div>
					<div>
						<input type="datetime-local" name="coupon-end-date"
							id="coupon-end-date" placeholder="yyyy-mm-dd hh:mm"
							value="${coupon.endDateString}" required="required" />
					</div>
				</div>
				<div>
					<div>
						<label for="coupon-min-bet-amount"><c:out
								value="${min_bet_amount}" />:</label>
					</div>
					<div>
						<input type="number" name="coupon-min-bet-amount"
							id="coupon-min-bet-amount" value="${coupon.minBetAmount}"
							required="required" />
					</div>
				</div>
				<div>
					<div>
						<label for="coupon-pull"><c:out value="${pool}" />:</label>
					</div>
					<div>
						<input type="number" name="coupon-pull" id="coupon-pull"
							value="${coupon.pull}" required="required" />
					</div>
				</div>
				<div>
					<div>
						<label for="coupon-jackpot"><c:out value="${jackpot}" />:</label>
					</div>
					<div>
						<input type="number" name="coupon-jackpot" id="coupon-jackpot"
							value="${coupon.jackpot}" required="required" />
					</div>
				</div>
				<div>
					<div>
						<label for="coupon-status"><c:out value="${status}" />:</label>
					</div>
					<div>
						<select name="coupon-status">
							<c:if test="${coupon.status eq 1 }">
								<option value="1" selected>${open}</option>
								<option value="4">${cancelled}</option>
								<option value="6">${free}</option>
							</c:if>
							<c:if test="${coupon.status eq 4 }">
								<option value="1">${open}</option>
								<option value="4" selected>${cancelled}</option>
								<option value="6">${free}</option>
							</c:if>
							<c:if test="${coupon.status eq 6 }">
								<option value="1">${open}</option>
								<option value="4">${cancelled}</option>
								<option value="6" selected>${free}</option>
							</c:if>
							<c:if test="${coupon.status eq 3 }">
								<option value="3" selected>${closed}</option>
							</c:if>
						</select>
					</div>
				</div>
				<input type="submit" value="${save}"
					class="btn btn-default delete-button">
			</form>
			<c:if test="${coupon.status eq 6}">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="delete-coupon">
					<input type="hidden" name="coupon-id"
						value="${requestScope.couponId}"> <input type="submit"
						value="${delete}" class="btn btn-default delete-button">
				</form>
			</c:if>
		</div>
	</div>
</body>
</html>