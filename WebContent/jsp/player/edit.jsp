<%@page
	import="org.mahjong.matoso.constant.*,org.mahjong.matoso.bean.*,org.mahjong.matoso.service.*"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%= BundleCst.BUNDLE.getString("player.edit") %></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>

	<body>
<%@include file="../include/head.jsp" %>
		<div id="edit">
			<h2><%= BundleCst.BUNDLE.getString("player.edit") %></h2>		
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<br/><br/>
			<form action="<%=request.getContextPath()%>/servlet/SavePlayer" method="post">
				<%
					Player player = (Player) request.getAttribute("player");
					if(player != null) {
				%>

					<input type="hidden" name="id" value="<%=player.getId().toString() %>" />	
					<table>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NAME) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>" value="<%=player.getLastname()%>" /></td>
						</tr>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_FIRSTNAME) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>" value="<%=player.getFirstname()%>" /></td>
						</tr>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_EMA) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>" value="<%=player.getEma()%>" /></td>
						</tr>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NATIONALITY) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>" value="<%=player.getCountry()%>" /></td>
						</tr>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_MAHJONGTIME) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>" value="<%=player.getPseudo()%>" /></td>
						</tr>
					<% 
						Team team = (Team) request.getAttribute("team");
						if (team != null) {
							String teamName = team.getName(); %>
						<tr>
							<td class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>" value="<%=teamName%>" /></td>
						</tr>
					<%}%>
					</table>
				
				<%} else { %>
					<%= BundleCst.BUNDLE.getString("player.error.missing") %>
				<% } %>
				<br/>
				<input type="submit" value="Modifier le joueur" />
			</form>
		</div>
	</body>
</html>