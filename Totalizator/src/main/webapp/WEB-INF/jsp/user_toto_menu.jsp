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

<title>User welcome page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.toto_coupon" var="toto_coupon" />
<fmt:message bundle="${loc}" key="local.date" var="date" />
<fmt:message bundle="${loc}" key="local.match" var="match" />
<fmt:message bundle="${loc}" key="local.make_bet" var="make_bet" />
<fmt:message bundle="${loc}" key="local.amount" var="amount" />
<fmt:message bundle="${loc}" key="local.enter_amount" var="enter_amount" />
<fmt:message bundle="${loc}" key="local.message_coupon_add_success" var="message_success" />
<fmt:message bundle="${loc}" key="local.message_coupon_add_failed" var="message_failed" />
<fmt:message bundle="${loc}" key="local.wait_next_coupon" var="wait_next_coupon" />

<link rel="stylesheet" type="text/css" href="CSS/style.css">
<link href="https://fonts.googleapis.com/css?family=Pattaya"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

</head>
<body>
	<header>
		<%@ include file="user_header.jsp"%>
	</header>

	<aside class="sidebar-right-news">
		<c:if test="${sessionScope.local eq 'ru' }">
			<jsp:include page="../../Content/aside-ru.html" />
		</c:if>
		<c:if test="${sessionScope.local eq 'en' or empty sessionScope.local}">
			<jsp:include page="../../Content/aside-en.html" />
		</c:if>
	</aside>

	<div class="content">
		<article class="main-toto">
			<div>
				<c:if
					test="${not empty sessionScope.resultAdd and sessionScope.resultAdd}">
					<c:out value="${message_success}" />
				</c:if>
				<c:if
					test="${not empty sessionScope.resultAdd and not sessionScope.resultAdd }">
					<c:out value="${message_failed}" />
				</c:if>
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="go-to-make-bet">

					<jsp:useBean id="list"
						class="by.tr.totalizator.tag.bean.JSPListBean" scope="request" />
					
					
					<c:if test="${empty list.list}">
						<h2><c:out value="${wait_next_coupon}" /></h2>
					</c:if>
					
					<c:if test="${not empty list.list}">
						<div>
							<h2>
								<c:out value="${toto_coupon}" />
								<c:out value="${requestScope.coupon}" />
							</h2>
						</div>
						<div>
							<tag:table-tag list="${list}" date="${date}" teams="${match}"
								result1="1" result2="X" result3="2" />
						</div>
						<div class="form-group">
							<div>
								<label for="amount"><c:out value="${amount}" />:</label>
							</div>
							<div>
								<input type="text" name="amount"
									value="${requestScope.minBetAmount}" id="amount"
									class="form-control" placeholder="${enter_amount}"
									required="required" />
							</div>
						</div>
						<input type="submit" value="${make_bet}" class="btn btn-default">
					</c:if>
				</form>
			</div>
		</article>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../Content/footer.jsp"%>
	</footer>
</body>
</html>