<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script
	src="http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.js"
	type="text/javascript"></script>

<style type="text/css">
@import
	url("http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.css")
	;

#feedControl {
	margin-left: auto;
	margin-right: auto;
	color: #9CADD0;
}
</style>
<script type="text/javascript">
	function load() {
		<%-- var ses = <% Session.local %>;(String)<%= session.getAttribute("local") %>
		alert(ses); --%>
		/* if(ses==='ru'){ */
		var feed = "http://news.yandex.ru/football.rss";
		/* } */
		//var feed ="http://feeds.bbci.co.uk/sport/football/rss.xml";
		new GFdynamicFeedControl(feed, "feedControl", {
			numResults : 8
		});
	}
	google.load("feeds", "1");
	google.setOnLoadCallback(load);
</script>
</head>
<body>
	<article>
		<%-- <c:if test="${sessionScope.local eq 'ru' }"> --%>
		<div id="feedControl">Loading...</div>
		<%-- </c:if> --%>
	</article>
</body>
</html>