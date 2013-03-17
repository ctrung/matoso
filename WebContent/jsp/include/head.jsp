<%@ page import="org.mahjong.matoso.constant.*" %>
<div id="head" class="noprint">
	<h1><a href="/matoso/servlet/ServletInit">MaToSo - MAhjong TOurnament SOftware</a></h1>
	<form action="/matoso/servlet/ChangeLanguage" method="post">
		<input type="hidden" name="language" value="fr" id="language" />
		<input type="image" src="/matoso/img/flag/gb.gif" alt="English" title="English" onclick="language.value='en'" />
		<input type="image" src="/matoso/img/flag/fr.gif" alt="Français" title="Français" />
	</form>
	<div id="powered">Project MaToSo v4<br/>2013</div>
</div>
<%
Object tnmtId = session.getAttribute(SessionCst.SESSION_TOURNAMENT_ID);
if (tnmtId != null) {
%>
<div class="matoso-menu noprint">
	<div><a href="#"><%=BundleCst.BUNDLE.getString("menu.views")%></a></div>
	<div>
		<a class="matoso-button" href="/matoso/servlet/LoadTournament?tournament-id=<%=tnmtId%>"><%=BundleCst.BUNDLE.getString("menu.views.sessions")%></a>
		<a class="matoso-button" href="/matoso/servlet/VisualCheck"><%=BundleCst.BUNDLE.getString("menu.views.check")%></a>
		<a class="matoso-button" href="/matoso/servlet/ViewTournamentDraw"><%=BundleCst.BUNDLE.getString("menu.views.draw")%></a>
		<a class="matoso-button" href="/matoso/servlet/ViewRanking"><%=BundleCst.BUNDLE.getString("menu.views.ranking")%></a>
		<a class="matoso-button" href="/matoso/servlet/DynamicViewRanking"><%=BundleCst.BUNDLE.getString("menu.views.display_ranking")%></a>
	</div>
	<div><a href="#"><%=BundleCst.BUNDLE.getString("menu.final")%></a></div>
	<div>
		<a class="matoso-button" href="/matoso/servlet/FinalSession"><%=BundleCst.BUNDLE.getString("menu.final.add")%></a>
		<a class="matoso-button" href="/matoso/servlet/FinalSessionView"><%=BundleCst.BUNDLE.getString("menu.final.display")%></a>
	</div>
	<div><a href="#"><%=BundleCst.BUNDLE.getString("menu.export")%></a></div>
	<div>
		<a class="matoso-button" href="/matoso/servlet/GamesExport"><%=BundleCst.BUNDLE.getString("menu.export.games")%></a>
		<a class="matoso-button" href="/matoso/servlet/RankingExport"><%=BundleCst.BUNDLE.getString("menu.export.ranks")%></a>
		<a class="matoso-button" href="/matoso/servlet/ExportPlayer"><%=BundleCst.BUNDLE.getString("menu.export.player")%></a>
		<a class="matoso-button" href="/matoso/servlet/EMAExport"><%=BundleCst.BUNDLE.getString("menu.export.ema")%></a>
	</div>
</div>
<%
}
%>