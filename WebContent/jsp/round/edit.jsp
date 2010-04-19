<%@page
	import="org.mahjong.matoso.constant.*,org.mahjong.matoso.bean.*,org.mahjong.matoso.service.*,org.mahjong.matoso.util.DateUtils"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%= BundleCst.BUNDLE.getString("round.edit.title") %></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
<%@include file="../include/head.jsp" %>
		<div id="edit">
			<h2><%= BundleCst.BUNDLE.getString("round.edit.title") %></h2>		
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<form action="<%=request.getContextPath()%>/servlet/SaveRound" method="post">
<%
Round round = (Round) request.getAttribute("round");
if(round != null) {
%>					<input type="hidden" name="id" value="<%=round.getId().toString() %>" />
				<br />
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="left"><label><%= BundleCst.BUNDLE.getString("round.number.lbl") %></label></td>
						<td><%=round.getNumber()%></td>
					</tr>
					<tr>
						<td class="left"><label for="date"><%= BundleCst.BUNDLE.getString("round.date") %></label></td>
						<td><input type="text" name="date" value="<%=DateUtils.formatSQLDate(round.getDate())%>" /></td>
					</tr>
					<tr>
						<td class="left"><label for="startTime"><%= BundleCst.BUNDLE.getString("round.start.time") %></label></td>
						<td><input type="text" name="startTime" value="<%=DateUtils.formatSQLTime(round.getStartTime())%>" /></td>
					</tr>
					<tr>
						<td class="left"><label for="finishTime"><%= BundleCst.BUNDLE.getString("round.finish.time") %></label></td>
						<td><input type="text" name="finishTime" value="<%=DateUtils.formatSQLTime(round.getFinishTime())%>" /></td>
					</tr>
				</table>
<%} else { %>
<%= BundleCst.BUNDLE.getString("round.error.missing") %>
<% } %>				<input type="submit" value="<%= BundleCst.BUNDLE.getString("general.save") %>" />
			</form>
		</div>
	</body>
</html>