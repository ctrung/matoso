<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import=
	"
		java.util.HashMap,
		java.util.Iterator,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.bean.Player
	"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"

%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/editTable.js"></script>
	</head>
	<body>
<%@ include file="include/head.jsp"
%><table>
	<tr><td></td>
<%
HashMap<Player, HashMap<Player, Integer>> players = (HashMap<Player, HashMap<Player, Integer>>) request.getAttribute("result");
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
String style;
HashMap<Player, Integer> playerNbGames;
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
			style = "background-color:white;color:black";
			nbGamesS = "";
		} else if (nbGamesI == 1) {
			style = "background-color:#99FF99;color:black";
			nbGamesS = "1";
		} else {
			style = "background-color:red;color:white";
			nbGamesS = nbGamesI.toString();
		}
%>		<td style="<%=style%>"><%=nbGamesS%></td>
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