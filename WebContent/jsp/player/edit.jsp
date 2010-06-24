<%@page
import="
	org.mahjong.matoso.constant.*,
	org.mahjong.matoso.bean.*,
	org.mahjong.matoso.service.*,
	java.util.List,
	org.mahjong.matoso.display.DisplayTableGame"
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%= BundleCst.BUNDLE.getString("player.edit") %></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
<%@
include file="../include/head.jsp"
%>		<div id="edit">
			<h2><%= BundleCst.BUNDLE.getString("player.edit") %></h2>		
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<br/><br/>
			<form action="/matoso/servlet/SavePlayer" method="post">
<%
Player player = (Player) request.getAttribute("player");
if (player != null) {
%>				<input type="hidden" name="id" value="<%=player.getId().toString() %>" />	
				<table>
					<tr>
						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NAME) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NAME %>" value="<%=player.getLastname()%>" /></td>
						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_FIRSTNAME) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_FIRSTNAME %>" value="<%=player.getFirstname()%>" /></td>
					</tr>
					<tr>
						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_EMA) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_EMA %>" value="<%=player.getEma()%>" /></td>
						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_NATIONALITY) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_NATIONALITY %>" value="<%=player.getCountry()%>" /></td>
					</tr>
					<tr>
						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_MAHJONGTIME) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME %>" value="<%=player.getPseudo()%>" /></td>
<% 
	Team team = (Team) request.getAttribute("team");
	if (team != null) {
		String teamName = team.getName();
%>						<th class="left"><label for="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>"><%= BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM) %></label></th>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_PLAYER_TEAM %>" value="<%=teamName%>" /></td>
<%}%>					</tr>
				</table>
<%
} else {
%><%= BundleCst.BUNDLE.getString("player.error.missing")
%><%
}
%>				<input type="submit" value="Modifier le joueur" />
<%
List<Player> players = (List<Player>) session.getAttribute("ranking");
if (players != null && player != null && player.getId() != null) {
	Player playerSession = null;
	Integer id = player.getId();
	for (Player playerI : players)
		if (playerI != null && playerI.getId() != null && id.equals(playerI.getId())) {
			playerSession = playerI;
			break;
		}
	if (playerSession != null) {
%>				<table>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_POSITION)%></th><td><%=playerSession.getRank()%></td>
					</tr>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_POINTS)%></th><td><%=playerSession.getPoints()%></td>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_SCORE)%></th><td><%=playerSession.getScore()%></td>
					</tr>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%></th><td><%=playerSession.getNbGames()%></td>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK_VICTORY)%></th><td><%=playerSession.getNbWinAndSelfDraw()%></td>
					</tr>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%></th><td><%=playerSession.getSelfDraw()%></td>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%></th><td><%=playerSession.getWin()%></td>
					</tr>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DEFEAT)%></th><td><%=playerSession.getNbDefeatForRankingPage()%></td>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GIVEN)%></th><td><%=playerSession.getLose()%></td>
					</tr>
					<tr>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SUSTAIN_SELFPICK)%></th><td><%=playerSession.getNbSustainSelfpickForRankingPage()%></td>
						<th class="left"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%></th><td><%=playerSession.getNbDrawForRankingPage()%></td>
					</tr>
				</table>					
<%
		Tournament tournament = TournamentService.getById((Integer) session.getAttribute(SessionCst.SESSION_TOURNAMENT_ID));
		Team teamPlayer = TeamService.getTeamForPlayer(player, tournament);
		if (teamPlayer != null) {
			List<Team> teams = (List<Team>) session.getAttribute("rankingTeam");
			if (teams != null) {
				Team teamSession = null;
				int index = 1;
				for (Team team : teams) {
					if (team != null && team.getId() != null && team.getId().equals(teamPlayer.getId())) {
						teamSession = team;
						break;
					}
					index++;
				}
				if (teamSession != null) {
%>				<table>
					<tr>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_TEAM)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_POINTS)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_SCORE)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_POSITION)%></th>
					</tr>
					<tr>
						<td><%=teamSession.getNameAndPlayers()%></td>
						<td><%=teamSession.getPrettyPrintPoints()%></td>
						<td><%=teamSession.getScore()%></td>
						<td><%=index%></td>
					</tr>
				</table>
<%
				}
			}
		}
		List<Table> tables = TableService.getAllByTournament(tournament);
		if (tables != null) {
			List<DisplayTableGame> displayTableGames;
			int nbRounds = 1;
			String scoreGame;
			boolean inTable;
			DisplayTableGame displayTableGame;
			for (Table table : tables) {
				inTable = table.getPlayer1().getId().equals(playerSession.getId())
					|| table.getPlayer2().getId().equals(playerSession.getId())
					|| table.getPlayer3().getId().equals(playerSession.getId())
					|| table.getPlayer4().getId().equals(playerSession.getId());
				if (!inTable)
					continue;
%>				<table>
					<tr><th colspan="4">Round <%=nbRounds++%></th><th colspan="4">Table <%=table.getName()%></th></tr>
					<tr>
						<td colspan="8">
							Players:
							<%=table.getPlayer1().getPrettyPrintName()%>,
							<%=table.getPlayer2().getPrettyPrintName()%>,
							<%=table.getPlayer3().getPrettyPrintName()%>,
							<%=table.getPlayer4().getPrettyPrintName()%>
						</td>
					</tr>
					<tr><th>Game</th><th>Score</th><th>Game</th><th>Score</th><th>Game</th><th>Score</th><th>Game</th><th>Score</th></tr>
<%
				displayTableGames = GameService.getDisplayedTableGames(table);
				int k = 0;
				for (int nbDTG = 0; true; nbDTG = nbDTG + 4) {
					if (nbDTG >= displayTableGames.size()) {
						nbDTG = ++k;
						if (k == 4)
							break;
						else {
%>					<tr>
<%
						}
					}
					displayTableGame = displayTableGames.get(nbDTG);
					scoreGame = null;
					if (table.getPlayer1().getId().equals(playerSession.getId()))
						scoreGame = displayTableGame.getPlayer1ScorePrettyPrint();
					else if (table.getPlayer2().getId().equals(playerSession.getId()))
						scoreGame = displayTableGame.getPlayer2ScorePrettyPrint();
					else if (table.getPlayer3().getId().equals(playerSession.getId()))
						scoreGame = displayTableGame.getPlayer3ScorePrettyPrint();
					else if (table.getPlayer4().getId().equals(playerSession.getId()))
						scoreGame = displayTableGame.getPlayer4ScorePrettyPrint();
					if (scoreGame != null) {
%>						<td><%=nbDTG + 1%></td><td><%=scoreGame.length() == 0 ? "-" : scoreGame%></td>
<%
					}
				}
				int scoreTable = -1;
				if (table.getResult() != null) {
					if (table.getPlayer1().getId().equals(playerSession.getId()))
						scoreTable= table.getResult().getScorePlayer1();
					else if (table.getPlayer2().getId().equals(playerSession.getId()))
						scoreTable = table.getResult().getScorePlayer2();
					else if (table.getPlayer3().getId().equals(playerSession.getId()))
						scoreTable = table.getResult().getScorePlayer3();
					else if (table.getPlayer4().getId().equals(playerSession.getId()))
						scoreTable = table.getResult().getScorePlayer4();
				}
%>					<tr><th colspan="4">Total</th><th colspan="4"><%=scoreTable%></th></tr>
				</table>
<%
				}
			}
		}
}
%>
			</form>
		</div>
	</body>
</html>