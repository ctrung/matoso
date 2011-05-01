<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import="
		java.util.Map,
		java.util.Iterator,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.constant.ServletCst,
		org.mahjong.matoso.constant.SessionCst,
		org.mahjong.matoso.bean.Player"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></title>
		<%@include file="include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="include/head.jsp"%>
<table class="matoso-visualCheck">
	<tr><td></td>
<%
Map<Player, Map<Player, Integer>> players = (Map<Player, Map<Player, Integer>>) request.getAttribute("result");
Iterator<Player> iteratorPlayers = players.keySet().iterator();
while (iteratorPlayers.hasNext()) {
%>	<th><%=iteratorPlayers.next().getPrettyPrintName()%></th>
<%
}
%>	</tr>
<%
iteratorPlayers = players.keySet().iterator();
Player player;
Iterator<Integer> nbGames;
Iterator<Player> opponents;
Integer nbGamesI;
String nbGamesS;
String classCSS;
Map<Player, Integer> playerNbGames;
while (iteratorPlayers.hasNext()) {
	player = iteratorPlayers.next();
%>	<tr>
		<th><%=player.getPrettyPrintName()%></th>
<%
	playerNbGames = players.get(player);
	opponents = playerNbGames.keySet().iterator();
	nbGames = playerNbGames.values().iterator();
	while (opponents.hasNext()) {
		if (opponents.next().equals(player))
			break;
		nbGamesI = nbGames.next();
		if (nbGamesI == 0) {
			classCSS = "";
			nbGamesS = "";
		} else if (nbGamesI == 1) {
			classCSS = "ui-state-highlight";
			nbGamesS = "1";
		} else {
			classCSS = "ui-state-error";
			nbGamesS = nbGamesI.toString();
		}
%>		<td class="<%=classCSS%>"><%=nbGamesS%></td>
<%
	}
%>
	</tr>
<%	
}
%>
</table>
	</body>
</html>