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

<link rel="stylesheet" type="text/css" href="CSS/style.css">
<link href="https://fonts.googleapis.com/css?family=Pattaya"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

</head>
<body>
	<header>
		<%@ include file="user_header.jsp" %>
	</header>

	<aside class="sidebar-right">
		<%@ include file="../../aside.jsp" %>
	</aside>

	<div class="content">
		<article class="main-toto">
			<div>
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="make-bet">
				
					<jsp:useBean id="map" class="by.tr.totalizator.tag.bean.JspMapBean"
						scope="request" />
					<jsp:useBean id="list"
						class="by.tr.totalizator.tag.bean.JSPListBean" scope="request" />
				
					<tag:bet-choices list="${list}" map="${map}" result="result"
						team="team"></tag:bet-choices>
					<div class="form-group">
						<div>
							<label for="amount"><c:out value="${amount}" />:</label>
						</div>
						<div>
							<input type="text" name="amount" value="${requestScope.amount }"
								id="amount" class="form-control" disabled/>
							<input type="hidden" name="amount" value="${requestScope.amount}"/>
						</div>
					</div>
					<div class="form-group">
						<div>
							<label for="credit-card-number">Credit card number:</label>
						</div>
						<div>
							<input type="text" name="credit-card-number" value=""
								id="credit-card-number" class="form-control" placeholder="xxxx-xxxx-xxxx-xxxx" required/>
						</div>
					</div>
					<input type="submit" value="${make_bet}" class="btn btn-default">
				</form>
			</div>
		</article>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../footer.jsp"%>
	</footer>
</body>
</html>