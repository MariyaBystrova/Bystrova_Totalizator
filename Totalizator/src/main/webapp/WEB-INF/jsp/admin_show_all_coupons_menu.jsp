<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="tag"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
<link rel="stylesheet" type="text/css" href="CSS/admin-style.css">
<link rel="stylesheet" href="CSS/bootstrap.min.css">
<link rel="stylesheet" href="CSS/dataTables.bootstrap.min.css">


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.log_out" var="logout" />
<fmt:message bundle="${loc}" key="local.general" var="general" />
<fmt:message bundle="${loc}" key="local.form_coupon" var="form_coupon" />
<fmt:message bundle="${loc}" key="local.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="local.end_date" var="end_date" />
<fmt:message bundle="${loc}" key="local.min_bet_amount"
	var="min_bet_amount" />
<fmt:message bundle="${loc}" key="local.jackpot" var="jackpot" />
<fmt:message bundle="${loc}" key="local.pool" var="pool" />
<fmt:message bundle="${loc}" key="local.status" var="status" />
<fmt:message bundle="${loc}" key="local.form_matches_to_coupon"
	var="form_matches_to_coupon" />
<fmt:message bundle="${loc}" key="local.edit_match_results"
	var="edit_match_results" />
<fmt:message bundle="${loc}" key="local.show_all_coupons"
	var="show_all_coupons" />
<fmt:message bundle="${loc}" key="local.edit" var="edit" />
<fmt:message bundle="${loc}" key="local.all_coupons" var="all_coupons" />
<fmt:message bundle="${loc}" key="local.coupon_and_matches_delete_success" var="coupon_and_matches_delete_success" />
<fmt:message bundle="${loc}" key="local.coupon_and_matches_delete_fail" var="coupon_and_matches_delete_fail" />

<title><c:out value="${all_coupons}" /></title>
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
		<li class="active"><a
			href="?command=admin-go-to-show-all-coupons"><c:out
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
			<c:if
				test="${not empty sessionScope.resultDelete and sessionScope.resultDelete}">
				<c:out value="${coupon_and_matches_delete_success}" />
			</c:if>
			<c:if
				test="${not empty sessionScope.resultDelete and not sessionScope.resultDelete }">
				<c:out value="${coupon_and_matches_delete_fail}" />
			</c:if>
		</div>
		<c:if test="${not empty requestScope.couponList}">
			<div>
				<jsp:useBean id="couponList"
					class="by.tr.totalizator.tag.bean.JSPListBean" scope="request" />
				<%-- <tag:table-all-coupons-tag list="${couponList}"
					startDate="${start_date}" endDate="${end_date}"
					minBetAmount="${min_bet_amount}" jackpot="${jackpot}"
					pool="${pool}" status="${status}" /> --%>
				<table id="myTable"
					class='table table-striped table-bordered table-hover'>
					<thead>
						<tr>
							<th>#</th>
							<th><c:out value="${start_date}"></c:out></th>
							<th><c:out value="${end_date}"></c:out></th>
							<th><c:out value="${min_bet_amount}"></c:out></th>
							<th><c:out value="${jackpot}"></c:out></th>
							<th><c:out value="${pool}"></c:out></th>
							<th><c:out value="${status}"></c:out></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" begin="1" end="${couponList.size}">
							<c:set var="coupon" scope="page"
								value="${couponList.couponElement}"></c:set>
							<tr>
								<form action="Controller" method="get">
									<input type="hidden" name="command"
										value="admin-go-to-edit-coupon-info" /> <input type="hidden"
										name="coupon-id" value="${coupon.id}" />
									<td>${i}</td>
									<td>${coupon.startDate}</td>
									<td>${coupon.endDate}</td>
									<td>${coupon.minBetAmount}</td>
									<td>${coupon.jackpot}</td>
									<td>${coupon.pull}</td>
									<td>${coupon.status}</td>
									<td><input type="submit" value="${edit}"
										class="btn btn-default" /></td>
								</form>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

	</div>

	<script src="JS/jquery.js"></script>
	<script src="JS/bootstrap.min.js"></script>
	<script src="JS/dataTables.bootstrap.min.js"></script>
	<script src="JS/jquery.dataTables.min.js"></script>
	<script>
		$("#myTable").dataTable();
	</script>
</body>
</html>