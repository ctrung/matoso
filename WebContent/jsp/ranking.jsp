<%@page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%><%@page import="
	org.mahjong.matoso.bean.*,
	org.mahjong.matoso.constant.*,
	java.util.List,
	org.mahjong.matoso.display.TournamentStats
"%><%@
	taglib uri="http://displaytag.sf.net" prefix="display"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
		<!-- header -->
<%@include file="include/head.jsp"%>
<%
Tournament tournament = (Tournament) request.getAttribute("tournament");
if(tournament != null) {
	// because displaytag calls JSP during pagination we exceptionally put the tournament name in session
	request.getSession().setAttribute("tournamentName", tournament.getName());
}
%>		<h2><%=request.getSession().getAttribute("tournamentName") + " - " + BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_TITLE)%></h2>
		<div id="ranking">
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a><br/><br/>
			<!-- ---------------- -->
			<!-- Tournament stats -->
			<!-- ---------------- -->			
			<h3><%=BundleCst.BUNDLE.getString("ranking.stats.tournament")%></h3>
			<table cellpadding="0" cellspacing="0" border="1">
				<thead class="labels">
					<!-- header labels -->
					<tr>
						<td><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%></td>
						<td><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%></td>
						<td><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%></td>
						<td><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%></td>
					</tr>
				</thead>
				<tbody>
<%
TournamentStats ts = (TournamentStats)session.getAttribute("tournamentStats");
%>					<tr class="color0">
						<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_GAMES)%>"><%=""+ts.getNbGames() %></td>
						<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_SELFPICK)%>"><%=""+ts.getNbSelfpick() %> (<%=ts.getPercSelfpick() %>)</td>
						<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_VICTORY)%>"><%=""+ts.getNbVictory() %> (<%=ts.getPercVictory() %>)</td>
						<td title="<%=BundleCst.BUNDLE.getString(BundleCst.RANKING_NB_DRAW)%>"><%=""+ts.getNbDraw() %> (<%=ts.getPercDraw() %>)</td>
					</tr>
				</tbody>
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
			<br/>
			<display:table name="sessionScope.ranking" sort="list" decorator="org.mahjong.matoso.util.decorator.PlayerDecorator" id="player" export="true">
				<display:column media="html" headerClass="edit"><a href="<%=request.getContextPath()%>/servlet/EditPlayer?id=<%=((Player) player).getId() %>"><img class="editUser" src="<%=request.getContextPath() + "/img/user_48.png"%>" title="<%= BundleCst.BUNDLE.getString("general.edit") %>" /></a></display:column>
			 	<display:column property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" sortable="true" headerClass="position" />
			 	<display:column property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" sortable="true" headerClass="name" />
			    <display:column property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" sortable="true" defaultorder="descending" />
			    <display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" defaultorder="descending" />
			    <display:column property="nbGames" titleKey="<%=BundleCst.RANKING_NB_GAMES%>" sortable="true" defaultorder="descending" />
			    <display:column property="selfDraw" titleKey="<%=BundleCst.RANKING_NB_SELFPICK%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			 	<display:column property="win" titleKey="<%=BundleCst.RANKING_NB_VICTORY%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column property="nbDefeat" titleKey="<%=BundleCst.RANKING_NB_DEFEAT%>" sortable="true" defaultorder="descending" />
			    <display:column property="lose" titleKey="<%=BundleCst.RANKING_NB_GIVEN%>" sortable="true" comparator="org.mahjong.matoso.util.comparator.NumberPercComparator" defaultorder="descending" />
			    <display:column property="nbSustainSelfpick" titleKey="<%=BundleCst.RANKING_NB_SUSTAIN_SELFPICK%>" sortable="true" defaultorder="descending" />
			    <display:column property="nbDraw" titleKey="<%=BundleCst.RANKING_NB_DRAW%>" sortable="true" defaultorder="descending" />
			</display:table>
			<!-- --------------------- -->
			<!-- Teams ranking + stats -->
			<!-- --------------------- -->
			<h3><%=BundleCst.BUNDLE.getString("ranking.by.team")%></h3>
			<display:table name="sessionScope.rankingTeam" sort="list" id="teamRanking">
				<display:column property="nameAndPlayers" sortable="true" titleKey="<%=BundleCst.RANKING_TEAM%>" class="nameAndPlayers" />
				<display:column property="prettyPrintPoints" titleKey="<%=BundleCst.RANKING_POINTS%>" sortable="true" />
				<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" sortable="true" />
			</display:table>
		</div>
	</body>
</html>