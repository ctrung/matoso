<%@page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%><%@page import="
	org.mahjong.matoso.bean.*,
	org.mahjong.matoso.constant.*,
	org.mahjong.matoso.form.RankingForm,
	java.util.List,
	org.mahjong.matoso.display.TournamentStats
"%><%@
	taglib uri="http://displaytag.sf.net" prefix="display"
%><%
Tournament tournament = (Tournament) request.getAttribute("tournament");
if(tournament != null) {
	// because displaytag calls JSP during pagination we exceptionally put the tournament name in session
	request.getSession().setAttribute("tournamentName", tournament.getName());
}
String hrefLoadTournament = request.getContextPath()
	+ ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET
	+ '?' + RequestCst.REQ_PARAM_TOURNAMENT_ID
	+ '=' + request.getSession().getAttribute(SessionCst.SESSION_TOURNAMENT_ID);

RankingForm rankingForm = (RankingForm) session.getAttribute("rankingForm");
TournamentStats ts = (TournamentStats)session.getAttribute("tournamentStats");
int indexrankingTeam = 1;
boolean displayRankingTeam = session.getAttribute("rankingTeam") != null;
boolean displayBestScoreBySession = rankingForm != null && rankingForm.getBestPlayerRoundList() != null && !rankingForm.getBestPlayerRoundList().isEmpty();
boolean displayBestGame = rankingForm != null && rankingForm.getBestScore() != null && rankingForm.getBestScore().getScore() != 0;
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_TITLE)%></title>
		<%@include file="include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="include/head.jsp"%>
		<div class="matoso-content">
			<h2><%=request.getSession().getAttribute("tournamentName") + " - " + BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_TITLE)%></h2>
			<div class="matoso-tabs">
				<ul>
					<li><a href="#matoso-stats"><%=BundleCst.BUNDLE.getString("ranking.stats.tournament")%></a></li>
					<li><a href="#matoso-ranking-players"><%=BundleCst.BUNDLE.getString("ranking.by.player")%></a></li>
<%if(displayRankingTeam){%>
					<li><a href="#matoso-ranking-team"><%=BundleCst.BUNDLE.getString("ranking.by.team")%></a></li>
<%}
if (displayBestScoreBySession) {
%>
					<li><a href="#matoso-ranking-session"><%= BundleCst.BUNDLE.getString("ranking.best.score.by.session") %></a></li>
<%}
if (displayBestGame) {%>
					<li><a href="#matoso-ranking-game"><%= BundleCst.BUNDLE.getString("ranking.best.game.for.tournament") %></a></li>
<%}%>
				</ul>
				<div id="matoso-stats">
					<table>
						<tr>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%></th>
						</tr>
						<tr>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%>"><%=ts.getNbGames() %></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%>"><%=ts.getNbSelfpick() %> (<%=ts.getPercSelfpick() %>)</td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%>"><%=ts.getNbVictory() %> (<%=ts.getPercVictory() %>)</td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%>"><%=ts.getNbDraw() %> (<%=ts.getPercDraw() %>)</td>
						</tr>
					</table>
				</div>

				<div id="matoso-ranking-players">
					<p class="info"><%= BundleCst.BUNDLE.getString("ranking.stats.player.text")%></p>
					<display:table name="sessionScope.ranking" sort="list" decorator="org.mahjong.matoso.util.decorator.PlayerDecorator" id="player" export="true">
						<display:column media="html" headerClass="edit"><a href="<%=request.getContextPath()%>/servlet/EditPlayer?id=<%=((Player) player).getId() %>"><img class="editUser" src="<%=request.getContextPath() + "/img/user_48.png"%>" title="<%= BundleCst.BUNDLE.getString("general.edit") %>" width="10px" /></a></display:column>
					 	<display:column property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" sortable="true" headerClass="position" />
					 	<display:column property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" sortable="true" headerClass="name" class="nowrap" />
					    <display:column property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" sortable="true" defaultorder="descending" />
					    <display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" defaultorder="descending" />
					    <display:column property="nbGames" titleKey="<%=BundleCst.RANKING_NB_GAMES%>" sortable="true" defaultorder="descending" />
					    <display:column property="nbWinAndSelfDraw" titleKey="<%=BundleCst.RANKING_NB_SELFPICK_VICTORY%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					    <display:column property="selfDraw" titleKey="<%=BundleCst.RANKING_NB_SELFPICK%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					 	<display:column property="win" titleKey="<%=BundleCst.RANKING_NB_VICTORY%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					    <display:column property="nbDefeatForRankingPage" titleKey="<%=BundleCst.RANKING_NB_DEFEAT%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					    <display:column property="lose" titleKey="<%=BundleCst.RANKING_NB_GIVEN%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					    <display:column property="nbSustainSelfpickForRankingPage" titleKey="<%=BundleCst.RANKING_NB_SUSTAIN_SELFPICK%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					    <display:column property="nbDrawForRankingPage" titleKey="<%=BundleCst.RANKING_NB_DRAW%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
					</display:table>
				</div>
<%if(displayRankingTeam){%>
				<div id="matoso-ranking-team">
					<display:table name="sessionScope.rankingTeam" sort="list" id="teamRanking">
						<display:column titleKey="<%=BundleCst.RANKING_POSITION%>" sortable="true" headerClass="position"><%=indexrankingTeam++%></display:column>
						<display:column property="nameAndEditPlayers" sortable="true" titleKey="<%=BundleCst.RANKING_TEAM%>" class="nameAndPlayers left" />
						<display:column property="prettyPrintPoints" titleKey="<%=BundleCst.RANKING_POINTS%>" sortable="true" />
						<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" />
					</display:table>
				</div>
<%}
if (displayBestScoreBySession) {%>
				<div id="matoso-ranking-session">
					<display:table name="sessionScope.rankingForm.bestPlayerRoundList" sort="list" id="bestPlayerRoundList">
						<display:column property="round" titleKey="ranking.session" />
						<display:column property="player" titleKey="ranking.player" class="left" />
						<display:column property="score" titleKey="ranking.score" />
					</display:table>
				</div>
<%}
if (displayBestGame) {%>
				<div id="matoso-ranking-game">
					<table>
						<tr>
							<th><%= BundleCst.BUNDLE.getString(BundleCst.RANKING_PLAYER) %></th>
							<td><%= rankingForm.getBestScore().getNamePlayer() %></td>
						</tr>
						<tr>
							<th><%= BundleCst.BUNDLE.getString(BundleCst.RANKING_SCORE) %></th>
							<td><%= rankingForm.getBestScore().getScore() %></td>
						</tr>
					</table>
				</div>
<%}%>
			</div>
		</div>
	</body>
</html>