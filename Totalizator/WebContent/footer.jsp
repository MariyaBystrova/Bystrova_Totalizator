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
<fmt:message bundle="${loc}" key="local.all_rights" var="all_rights" />
<fmt:message bundle="${loc}" key="local.contacts" var="contacts" />
<fmt:message bundle="${loc}" key="local.partners" var="partners" />
<title>Insert title here</title>
</head>
<body>
	<article class="bottom_sidebar-left">
		<p>&copy; <c:out value="${all_rights }"></c:out></p>
	</article>
	<section class="bottom_content">
		<p>
			<c:out value="${contacts }"></c:out>: <a href="mailto:bet_match@gtoto.com"
				title="bet_match@gtoto.com">bet_match@toto.com</a> +7 499 211-11-11
			<br /><c:out value="${partners }"></c:out> <a
				href="http://www.sport-express.ru">www.sport-express.ru</a>
		</p>
	</section>
</body>
</html>