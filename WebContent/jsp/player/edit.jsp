<%@page
	import="org.mahjong.matoso.constant.*,org.mahjong.matoso.bean.*,org.mahjong.matoso.service.*"
	language="java"
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>

	<body>
		
		<%@include file="../include/head.jsp" %>

		<h2><%= BundleCst.BUNDLE.getString("player.edit") %></h2>
		
		<div id="edit">
		
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+
					RequestCst.REQ_PARAM_TOURNAMENT_NAME+"="+request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT)%>">
					<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%>
			</a>
				
			<form action="<%=request.getContextPath()%>/servlet/SavePlayer" method="post">
				<%
					Player player = (Player) request.getAttribute("player");
					if(player != null) {
				%>

					<input type="hidden" name="id" value="<%=player.getId().toString() %>" />	
								
					<div class="field">
						<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NAME) %></label> :</div>
						<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>" value="<%=player.getLastname()%>" />
					</div>
					<div class="field">
						<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_FIRSTNAME) %></label> :</div>
						<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>" value="<%=player.getFirstname()%>" />
					</div>
					<div class="field">
						<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_EMA) %></label> :</div>
						<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>" value="<%=player.getEma()%>" /></div>
					<div class="field">
						<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NATIONALITY) %></label> :</div>
						<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>" value="<%=player.getCountry()%>" /></div>
					<div class="field">
						<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_MAHJONGTIME) %></label> :</div>
						<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>" value="<%=player.getPseudo()%>" />
					</div>
					
					<% 
						Team team = (Team) request.getAttribute("team");
						if (team != null) {
							String teamName = team.getName(); %>
						<div class="field">
							<div class="label"><label for="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM) %></label> :</div>
							<input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>" value="<%=teamName%>" />
						</div>
					<%}%>
				
				<%} else { %>
					<%= BundleCst.BUNDLE.getString("player.error.missing") %>
				<% } %>
				
				<div class="field"><input type="submit" value="Modifier le joueur" /></div>
			</form>
		</div>
	</body>
</html>