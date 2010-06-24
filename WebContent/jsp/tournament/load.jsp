<%@page import="
java.util.List,
java.util.TreeMap,
java.util.ResourceBundle,
org.mahjong.matoso.constant.BundleCst,
org.mahjong.matoso.constant.RequestCst,
org.mahjong.matoso.constant.ServletCst,
org.mahjong.matoso.constant.SessionCst,
org.mahjong.matoso.bean.*,
org.mahjong.matoso.service.TournamentService,
org.mahjong.matoso.service.GameResultService,
org.mahjong.matoso.service.RoundService,
org.mahjong.matoso.service.TableService"
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%><%
Tournament tournament = (Tournament) request.getAttribute("tournament");
List<Round> rounds = (List<Round>) request.getAttribute("rounds");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=rounds.size() == 0 ? BundleCst.BUNDLE.getString(BundleCst.PLAYER_FILL_METHOD) : BundleCst.BUNDLE.getString("round.label.round")%></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div id="loadTournament">
			<h2><%=tournament.getName() %></h2>
			<a href="<%=request.getContextPath()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
<%
if (rounds.size() == 0) {
%>			<br/><br/>
			<%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_FILL_METHOD)%><br/>
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_MASS_IMPORT)%></a>
			<br/><br/>
			<form action="/matoso/servlet/AddPlayer" method="post">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td><label for="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>"><%= BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NB_PLAYERS) %></label></td>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>" /></td>
					</tr><tr>
						<td><label for="<%= RequestCst.REQ_PARAM_ROUND %>"><%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %></label></td>
						<td><input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" /></td>
					</tr>
				</table>
				<input type="submit" />
			</form>
<%
} else {
%>			| <a href="<%=request.getContextPath()%>/servlet/DynamicViewRanking"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_DYNAMIC_VIEW)%></a>
			| <a href="<%=request.getContextPath()%>/servlet/ViewRanking"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_GOTO_LINK)%></a>
			| <a href="<%=request.getContextPath()%>/servlet/ViewTournamentDraw"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_DRAW_GOTO_LINK)%></a>
			| <a href="<%=ServletCst.SERVLET_FINAL_SESSION%>"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_FINAL_SESSION_VIEW)%></a>
			<br/><br/>
			<%=BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER)%> : <%=rounds.size()%>
<%--			<form id="addRound" action="<%=request.getContextPath()%>/servlet/AddMoreRounds" method="post">
				<input type="text" name="nbrounds" value="" />&nbsp;<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.ROUND_ADD)%>" />
			</form>
--%>			<br/><br/>
 			<a href="javascript:show(0)">+++</a>
			<br/><br/>
			<table cellpadding="0" cellspacing="0" id="tableRounds">
<%
	String classCss;
	for (Round round : rounds) {
		if (RoundService.isFilledWithGames(round.getId()))
			classCss = " filledWithGames";
		else if(RoundService.isFilledWithTotal(round.getId()))
			classCss = " filledWithTotal";
		else classCss = "";
%>				<tr>
					<th class="left<%=classCss%>">
						<a href="<%=request.getContextPath() + "/servlet/EditRound?id=" + round.getId() %>"><%= BundleCst.BUNDLE.getString("round.label.round") + " " + round.getNumber() %></a>
						<a href="javascript:show(<%=round.getNumber()%>)">+</a>
					</th>
				</tr>
				<tr id="round<%=round.getNumber()%>">
					<td>
						<div style="<%=(round.getTables().size() > 10) ? "width:1000px;overflow:scroll" : "" %>">
						<table cellpadding="0" cellspacing="0">
							<tr>
<%
		for (Table table : round.getTables()) {
			if (TableService.hasSavedGame(table))
				classCss = " class=\"filledWithGames\"";
			else if (!GameResultService.isEmpty(table.getResult()))
				classCss = " class=\"filledWithTotal\"";
			else
				classCss = "";
%>					<td<%=classCss%>>
						<a class="table" href="<%=request.getContextPath()%>/servlet/EditTable?<%= RequestCst.REQ_PARAM_TABLE_ID %>=<%=table.getId() %>"><%= BundleCst.BUNDLE.getString("round.label.table") + " "  + table.getName() %></a>
						<ul class="player">
<%
			for (Player player : table.getListPlayers()) {
%>							<li><a href="<%=request.getContextPath() + "/servlet/EditPlayer?id=" + player.getId() %>"><%= player.getPrettyPrintName() %></a></li>
<%
			}
%>						</ul>
					</td>
<%
		}
%>							</tr>
						</table>
						</div>
					</td>
				</tr>
<%
	}
%>			</table>
<script type="text/javascript">
function show(number){
	var i = 0;
	while (document.getElementById("round" + ++i)) {
		if (i == number || 0 == number) {document.getElementById("round" + i).style.display="block";}
		else {document.getElementById("round" + i).style.display="none";}
	}
}
</script>
<%
}
%>		</div>
	</body>
</html>