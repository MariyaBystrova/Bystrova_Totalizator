<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.company" var="company" />
<fmt:message bundle="${loc}" key="local.banner" var="banner" />
<fmt:message bundle="${loc}" key="local.main" var="main" />
<fmt:message bundle="${loc}" key="local.toto" var="toto" />
<fmt:message bundle="${loc}" key="local.results" var="results" />
<fmt:message bundle="${loc}" key="local.help" var="help" />
<fmt:message bundle="${loc}" key="local.page_language"
	var="page_language" />
<link href="https://fonts.googleapis.com/css?family=Pattaya"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

<link rel="stylesheet" type="text/css" href="CSS/style.css">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<c:out value="${company}" />
	</h1>
	<section>
		<p class="banner">
			<i><c:out value="${banner}" /></i>
		<p>
	</section>
	<section>
		<ul>
			<li><a href="/Totalizator" class="a-dec"><c:out
						value="${main}" /></a></li>
			<li><a href="#" class="a-dec"><c:out value="${toto}" /></a></li>
			<li><a href="#" class="a-dec"><c:out value="${results}" /></a></li>
			<li><a href="#" class="a-dec"><c:out value="${help}" /></a></li>

			<li class="rightitem">
				<section class="dropdown">
					<button class="dropbtn">
						<c:out value="${page_language}" />
					</button>
					<section class="dropdown-content">
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="change-language" /> <input
								type="hidden" name="lang" value="ru" /> <input type="submit"
								name="local" value="${ru}" />
						</form>
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="change-language" /> <input
								type="hidden" name="lang" value="en" /> <input type="submit"
								name="local" value="${en}" />
						</form>
					</section>
				</section>
			</li>
		</ul>
	</section>
</body>
</html>