<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tag"%>
<!DOCTYPE html">
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
<fmt:message bundle="${loc}" key="local.edit_match_results"
	var="edit_match_results" />
<fmt:message bundle="${loc}" key="local.show_all_coupons"
	var="show_all_coupons" />

<fmt:message bundle="${loc}" key="local.step_one_choose_coupon"
	var="step_one_choose_coupon" />
<fmt:message bundle="${loc}" key="local.show_matches" var="show_matches" />
<fmt:message bundle="${loc}" key="local.step_two_fill_coupon"
	var="step_two_fill_coupon" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.team_one" var="team_one" />
<fmt:message bundle="${loc}" key="local.team_two" var="team_two" />
<fmt:message bundle="${loc}" key="local.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="local.end_date" var="end_date" />

<fmt:message bundle="${loc}" key="local.form_matches_to_coupon"
	var="form_matches_to_coupon" />
<fmt:message bundle="${loc}" key="local.message_match_add_success"
	var="message_match_add_success" />
<fmt:message bundle="${loc}" key="local.message_match_add_failed"
	var="message_match_add_failed" />
<fmt:message bundle="${loc}" key="local.message_match_edit_success"
	var="message_match_edit_success" />
<fmt:message bundle="${loc}" key="local.message_match_edit_failed"
	var="message_match_edit_failed" />
<fmt:message bundle="${loc}" key="local.fill_coupon"
	var="title" />

<title><c:out value="${title}" /></title>

<link rel="stylesheet" type="text/css" href="CSS/admin-style.css">

</head>
<body>

	<ul>
		<li><a href="?command=admin-go-to-general"><c:out
					value="${general}" /></a></li>
		<li><a href="?command=admin-go-to-form-coupon"><c:out
					value="${form_coupon}" /></a></li>
		<li class="active"><a href="?command=admin-go-to-form-matches"><c:out
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
	<div class="main">
		<div>
			<c:if test="${not empty requestScope.coupons }">
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="show-coupon-matches" />
					<input type="hidden" name="page" value="admin-form-matches" />
					<div>
						<div>
							<label for="coupon"><c:out
									value="${step_one_choose_coupon}"></c:out>:</label>
						</div>
						<div>
							<jsp:useBean id="coupons"
								class="by.tr.totalizator.tag.bean.JSPListBean" scope="request" />
							<tag:coupon-dropdown-tag list="${coupons}"
								activeCouponId="${requestScope.coupon }" />
						</div>
					</div>

					<div>
						<input type="submit" value="${show_matches}"
							class="btn btn-default">
					</div>
				</form>
			</c:if>
		</div>

		<div id="list-matches">
			<c:if test="${not empty requestScope.list }">
				<div>
					<div>
						<c:if
							test="${not empty sessionScope.resultAdd and sessionScope.resultAdd}">
							<c:out value="${message_match_add_success}" />
						</c:if>
						<c:if
							test="${not empty sessionScope.resultAdd and not sessionScope.resultAdd}">
							<c:out value="${message_match_add_failed}" />
						</c:if>
					</div>
					<div>
						<c:if
							test="${not empty sessionScope.resultEdit and sessionScope.resultEdit}">
							<c:out value="${message_match_edit_success}" />
						</c:if>
						<c:if
							test="${not empty sessionScope.resultEdit and not sessionScope.resultEdit }">
							<c:out value="${message_match_edit_failed}" />
						</c:if>
					</div>
					<div>
						<label for="matches"><c:out
								value="${step_two_fill_coupon}"></c:out>:</label>
					</div>

					<div>
						<jsp:useBean id="list"
							class="by.tr.totalizator.tag.bean.JSPListBean" scope="request" />
						<tag:table-tag-matches list="${list}" matchName="${name}"
							teamOne="${team_one}" teamTwo="${team_two}"
							startDate="${start_date}" endDate="${end_date}" />
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<script src="JS/script.js"></script>
</body>
</html>
