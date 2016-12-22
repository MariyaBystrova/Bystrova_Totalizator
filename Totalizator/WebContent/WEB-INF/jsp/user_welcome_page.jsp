<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="table-tag"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="CSS/style.css">
<title>User welcome page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.hello_user" var="hello" />
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
		<c:if test="${sessionScope.local eq 'ru' }">
			<jsp:include page="../../Content/aside-ru.html" />
		</c:if>
		<c:if test="${sessionScope.local eq 'en' or empty sessionScope.local}">
			<jsp:include page="../../Content/aside-en.html" />
		</c:if>
	</aside>

	<div class="content main">
		<article>
			<h2>
				<c:out value="${hello}" />
			</h2>
		</article>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../Content/footer.jsp"%>
	</footer>
</body>
</html>