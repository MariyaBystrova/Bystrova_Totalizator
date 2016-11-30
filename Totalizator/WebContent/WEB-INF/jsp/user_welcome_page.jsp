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
<fmt:message bundle="${loc}" key="local.log_out" var="logout" />
<fmt:message bundle="${loc}" key="local.edit_profile" var="edit_profile" />
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.general" var="general" />
<fmt:message bundle="${loc}" key="local.toto" var="toto" />
<fmt:message bundle="${loc}" key="local.company" var="company" />
<fmt:message bundle="${loc}" key="local.banner" var="banner" />
<fmt:message bundle="${loc}" key="local.page_language"
	var="page_language" />
<link href="https://fonts.googleapis.com/css?family=Pattaya"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

</head>
<body>
	<header>
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
				<li><a href="?command=go-to-general" class="a-dec"><c:out
							value="${general}" /></a></li>
				<li><a href="?command=show-current-coupon" class="a-dec"><c:out
							value="${toto}" /></a></li>
				<li><a href="?command=go-to-edit-profile" class="a-dec"><c:out
							value="${edit_profile}" /></a></li>

				<li class="rightitem">
					<section class="dropdown">
						<button class="dropbtn">
							<c:out value="${page_language}" />
						</button>
						<section class="dropdown-content">
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="change-language" />
								<input type="hidden" name="lang" value="ru" /> <input
									type="submit" name="local" value="${ru}" />
							</form>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="change-language" />
								<input type="hidden" name="lang" value="en" /> <input
									type="submit" name="local" value="${en}" />
							</form>
						</section>
					</section>
				</li>
				<li class="rightitem">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="logout"> <input
							type="submit" value="${logout}" class="log-out">
					</form>
				</li>

			</ul>
		</section>
	</header>

	<aside class="sidebar-right">
		<h2>Новости футбола:</h2>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rfpl/reviews/dubl-propusk-v-sbornuyu-tura-1063201/">ДУБЛЬ
					– ПРОПУСК В СБОРНУЮ ТУРА</a>
			</h3>
			<p>"СЭ" представляет свой вариант 11 лучших футболистов 13-го
				тура.</p>
			<time datetime="2016-11-07">2016-11-07 11:15</time>
		</article>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rusteam/reviews/davno-takogo-ne-bylo-sbornaya-bez-cska-1063197/">ДАВНО
					ТАКОГО НЕ БЫЛО. СБОРНАЯ - БЕЗ ЦСКА</a>
			</h3>
			<p>В воскресенье поздно вечером в составе сборной России
				произошли значительные изменения.</p>
			<time datetime="2016-11-07">2016-11-07 11:00</time>
		</article>
		<article>
			<h3>
				<a
					href="http://www.sport-express.ru/football/rfpl/reviews/bezumie-v-krasnodare-shest-golov-travma-vratarya-i-dva-pryamyh-udaleniya-1063072/?utm_source=materials&utm_medium=link&utm_campaign=plista">БЕЗУМИЕ
					В КРАСНОДАРЕ</a>
			</h3>
			<p>Шесть голов, травма вратаря и два прямых удаления...</p>
			<time datetime="2016-11-06">2016-11-06 21:45</time>
		</article>
	</aside>

	<div class="content main">
		<article>
			<h2>
				<c:out value="${hello}" />
			</h2>
		</article>
	</div>

	<footer class="bottom bottom_clearfix">
		<%@include file="../../footer.jsp"%>
	</footer>
</body>
</html>