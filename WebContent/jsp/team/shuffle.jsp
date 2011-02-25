<%-- Shuffle screen --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.mahjong.matoso.constant.ServletCst"%>
<%@page import="org.mahjong.matoso.bean.Tournament"%>
<%@page import="org.mahjong.matoso.display.TeamShuffling"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString("team.shuffle")%></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	
	<body>
	
		<% Tournament tournament = (Tournament) request.getAttribute("tournament"); %>
		
		<%@include file="../include/head.jsp"%>
		<div>
			<h2><%=tournament.getName() + " - " + tournament.getRules()%></h2>
			
			<p><%=BundleCst.BUNDLE.getString("team.shuffle.text")%></p>
			
			<a class="link" href="<%=request.getContextPath()%>/servlet/TeamShuffleGo"><%=BundleCst.BUNDLE.getString("team.shuffle.go")%></a>
			<a class="link" href="<%=request.getContextPath()%>/servlet/TeamShuffleValidate"><%=BundleCst.BUNDLE.getString("team.shuffle.validate")%></a>
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<br/><br/>
			
			<%if(request.getSession().getAttribute("shuffling") != null) {
				List<TeamShuffling> tSh = (List<TeamShuffling>)(request.getSession().getAttribute("shuffling"));
				Iterator<TeamShuffling> it = tSh.iterator();
				for(int i=0; it.hasNext(); i++) {
					TeamShuffling tsItem = it.next();
			%>
					<table style="float: left; margin-right: 20px;">
						<tr><th><%= tsItem.getTeam().getName()%></th></tr>
						<tr><td><%= tsItem.getPlayer1().getPrettyPrintName()%></td></tr>
						<tr><td><%= tsItem.getPlayer2().getPrettyPrintName()%></td></tr>
						<tr><td><%= tsItem.getPlayer3().getPrettyPrintName()%></td></tr>
						<tr><td><%= tsItem.getPlayer4().getPrettyPrintName()%></td></tr>
					</table>
					
					<%if(i%3 == 2){%><hr style="visibility: hidden; clear: both;"/><%} %> <%-- line break every 3 teams --%>
			<%	}
			}%>
			
		</div>
	</body>
</html>