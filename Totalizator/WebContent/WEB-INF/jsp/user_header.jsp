<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<fmt:message bundle="${loc}" key="local.log_out" var="logout" />
<fmt:message bundle="${loc}" key="local.settings" var="settings" />
<fmt:message bundle="${loc}" key="local.edit_profile" var="edit_profile" />
<fmt:message bundle="${loc}" key="local.edit_account" var="edit_account" />
<fmt:message bundle="${loc}" key="local.ru" var="ru" />
<fmt:message bundle="${loc}" key="local.en" var="en" />
<fmt:message bundle="${loc}" key="local.general" var="general" />
<fmt:message bundle="${loc}" key="local.toto" var="toto" />
<fmt:message bundle="${loc}" key="local.company" var="company" />
<fmt:message bundle="${loc}" key="local.banner" var="banner" />
<fmt:message bundle="${loc}" key="local.page_language"
	var="page_language" />

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
				<li><a href="?command=go-to-general" class="a-dec"><c:out
							value="${general}" /></a></li>
				<li><a href="?command=show-current-coupon" class="a-dec"><c:out
							value="${toto}" /></a></li>
				<li>
					<section class="dropdown">
						<button class="dropbtn">
							<c:out value="${settings}" />
						</button>
						<section class="dropdown-content">
							<form action="Controller" method="get">
								<input type="hidden" name="command" value="go-to-edit-profile" />
								<input type="hidden" name="operation" value="personal-data" />
								<input type="submit" value="${edit_profile}" />
							</form>
							<form action="Controller" method="get">
								<input type="hidden" name="command" value="go-to-edit-profile" />
								<input type="hidden" name="operation" value="account" /> <input
									type="submit" value="${edit_account}" />
							</form>
						</section>
					</section>
				</li>

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
</body>
</html>