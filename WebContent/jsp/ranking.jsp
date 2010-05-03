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

%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_TITLE)%></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
		<!-- header -->
<%@include file="include/head.jsp"%>
		<div id="ranking">
			<h2><%=request.getSession().getAttribute("tournamentName") + " - " + BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_TITLE)%></h2>
			<a href="<%=hrefLoadTournament%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a><br/><br/>
			<!-- ---------------- -->
			<!-- Tournament stats -->
			<!-- ---------------- -->			
			<h3><%=BundleCst.BUNDLE.getString("ranking.stats.tournament")%></h3>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%></th>
					<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%></th>
					<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%></th>
					<th><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%></th>
				</tr>
<%
TournamentStats ts = (TournamentStats)session.getAttribute("tournamentStats");
%>				<tr>
					<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%>"><%=""+ts.getNbGames() %></td>
					<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%>"><%=""+ts.getNbSelfpick() %> (<%=ts.getPercSelfpick() %>)</td>
					<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%>"><%=""+ts.getNbVictory() %> (<%=ts.getPercVictory() %>)</td>
					<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%>"><%=""+ts.getNbDraw() %> (<%=ts.getPercDraw() %>)</td>
				</tr>
			</table>
			<!-- ----------------------- -->
			<!-- Players ranking + stats -->
			<!-- ----------------------- -->
			<h3><%=BundleCst.BUNDLE.getString("ranking.by.player")%></h3>
			<p class="info"><%= BundleCst.BUNDLE.getString("ranking.stats.player.text")%></p>
			<form action="<%=request.getContextPath()%>/servlet/ViewRanking?nbElementsByPage=redefine" id="redefineNbElementsByPage">
				<label for="nbElementsByPageId"><%= BundleCst.BUNDLE.getString("ranking.nb.elements.by.page")%></label>
				<input type="text" id="nbElementsByPageId" name="nbElementsByPage" />
				<input type="submit" value="<%= BundleCst.BUNDLE.getString("ranking.nb.elements.by.page.define.button")%>" />
			</form>
			<a href="<%=request.getContextPath()%>/servlet/ViewRanking"><%= BundleCst.BUNDLE.getString("general.reload.default") %></a>
			<br/><br/>
			<display:table name="sessionScope.ranking" sort="list" decorator="org.mahjong.matoso.util.decorator.PlayerDecorator" id="player" export="true" cellpadding="0" cellspacing="0">
				<display:column style="width:1%" media="html" headerClass="edit"><a href="<%=request.getContextPath()%>/servlet/EditPlayer?id=<%=((Player) player).getId() %>"><img class="editUser" src="<%=request.getContextPath() + "/img/user_48.png"%>" title="<%= BundleCst.BUNDLE.getString("general.edit") %>" width="10px" /></a></display:column>
			 	<display:column style="width:1%" property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" sortable="true" headerClass="position" />
			 	<display:column style="width:35%" property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" sortable="true" headerClass="name" class="left" />
			    <display:column style="width:1%" property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" sortable="true" defaultorder="descending" />
			    <display:column style="width:1%" property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" defaultorder="descending" />
			    <display:column style="width:1%" property="nbGames" titleKey="<%=BundleCst.RANKING_NB_GAMES%>" sortable="true" defaultorder="descending" />
			    <display:column style="width:10%" property="nbWinAndSelfDraw" titleKey="<%=BundleCst.RANKING_NB_SELFPICK_VICTORY%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column style="width:10%" property="selfDraw" titleKey="<%=BundleCst.RANKING_NB_SELFPICK%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			 	<display:column style="width:10%" property="win" titleKey="<%=BundleCst.RANKING_NB_VICTORY%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column style="width:10%" property="nbDefeatForRankingPage" titleKey="<%=BundleCst.RANKING_NB_DEFEAT%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column style="width:10%" property="lose" titleKey="<%=BundleCst.RANKING_NB_GIVEN%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column style="width:10%" property="nbSustainSelfpickForRankingPage" titleKey="<%=BundleCst.RANKING_NB_SUSTAIN_SELFPICK%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column style="width:10%" property="nbDrawForRankingPage" titleKey="<%=BundleCst.RANKING_NB_DRAW%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			</display:table>
			<!-- --------------------- -->
			<!-- Teams ranking + stats -->
			<!-- --------------------- -->
			<h3><%=BundleCst.BUNDLE.getString("ranking.by.team")%></h3>
			<% int indexrankingTeam = 1; %>
			<display:table name="sessionScope.rankingTeam" sort="list" id="teamRanking" cellpadding="0" cellspacing="0">
				<display:column titleKey="<%=BundleCst.RANKING_POSITION%>" sortable="true" headerClass="position"><%=indexrankingTeam++%></display:column>
				<display:column property="nameAndPlayers" sortable="true" titleKey="<%=BundleCst.RANKING_TEAM%>" class="nameAndPlayers left" />
				<display:column property="prettyPrintPoints" titleKey="<%=BundleCst.RANKING_POINTS%>" sortable="true" />
				<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" />
			</display:table>
			<h3><%= BundleCst.BUNDLE.getString("ranking.best.score.by.session") %></h3>
			<display:table name="sessionScope.rankingForm.bestPlayerRoundList" sort="list" id="bestPlayerRoundList" cellpadding="0" cellspacing="0">
				<display:column property="round" titleKey="ranking.session" />
				<display:column property="player" titleKey="ranking.player" class="left" />
				<display:column property="score" titleKey="ranking.score" />
			</display:table>
<%
RankingForm rankingForm = (RankingForm) session.getAttribute("rankingForm");
if (rankingForm != null) {
%>
			<h3><%= BundleCst.BUNDLE.getString("ranking.best.game.for.tournament") %></h3>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<th><%= BundleCst.BUNDLE.getString(BundleCst.RANKING_PLAYER) %></th>
					<td><%= rankingForm.getBestScore().getNamePlayer() %></td>
				</tr>
				<tr>
					<th><%= BundleCst.BUNDLE.getString(BundleCst.RANKING_SCORE) %></th>
					<td><%= rankingForm.getBestScore().getScore() %></td>
				</tr>
			</table>
<%
}
%>
		</div>
	</body>
</html>