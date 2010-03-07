<%@page
	import="org.mahjong.matoso.constant.*"
	language="java"
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="/matoso/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
		<%@include file="../include/head.jsp" %>
		<h2><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_ADD)%></h2>
		<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+
				RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+request.getSession().getAttribute(SessionCst.SESSION_TOURNAMENT_ID)%>">
				<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%>
		</a>
		<form action="/matoso/servlet/AddPlayer" method="post">
			<div class="field">
				<div class="label"><label for="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>"><%= BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NB_PLAYERS) %></label></div>
				<input type="text" name="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>" />
			</div>
			<div class="field">
				<div class="label"><label for="<%= RequestCst.REQ_PARAM_ROUND %>"><%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %></label></div>
				<input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" />
			</div>
			<div class="field"><input type="submit" value="<%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_SUBMIT) %>" /></div>
		</form>
	</body>
</html>