<%@page
	import="
		java.util.List,
		java.util.TreeMap,
		java.util.ResourceBundle,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.constant.ServletCst,
		org.mahjong.matoso.constant.SessionCst,
		org.mahjong.matoso.bean.*,
		org.mahjong.matoso.service.TournamentService,
		org.mahjong.matoso.service.GameResultService"
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
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<h2><%=tournament.getName() %></h2>
		<div id="loadTournament">
			<a href="<%=request.getContextPath()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<br/><br/>
<%
if (rounds.size() == 0) {
%>	
			<%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_FILL_METHOD)%><br/>
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_MASS_IMPORT)%></a>
			<br/><br/>
			<form action="/matoso/servlet/AddPlayer" method="post">
				<div class="field">
					<div class="label"><label for="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>"><%= BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NB_PLAYERS) %></label></div>
					<input type="text" name="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>" />
				</div>
				<div class="field">
					<div class="label"><label for="<%= RequestCst.REQ_PARAM_ROUND %>"><%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %></label></div>
					<input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" />
				</div>
				<div class="field"><input type="submit" /></div>
			</form>
<%
} else {
%>			<a href="<%=request.getContextPath()%>/servlet/DynamicViewRanking"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_DYNAMIC_VIEW)%></a>
			<br/>
			<a href="<%=request.getContextPath()%>/servlet/ViewRanking"><%=BundleCst.BUNDLE.getString(BundleCst.RANKING_STATS_GOTO_LINK)%></a>
			<br/>
			<a href="<%=request.getContextPath()%>/servlet/ViewTournamentDraw"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_DRAW_GOTO_LINK)%></a>
			<br/>
			<%=BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER)%> : <%= rounds.size() %>
			<form id="addRound" action="<%=request.getContextPath()%>/servlet/AddMoreRounds" method="post">
				<input type="text" name="nbrounds" value="" />&nbsp;<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.ROUND_ADD)%>" />
			</form>
			<table>
				<tr><td><a href="javascript:show(0)">+++</a></td></tr>
<%
	for (Round round : rounds) {
%>				<tr>
					<td colspan="<%=round.getTables().size()%>"><a href="<%=request.getContextPath() + "/servlet/EditRound?id=" + round.getId() %>"><%= BundleCst.BUNDLE.getString("round.label.round") + " " + round.getNumber() %></a> <a href="javascript:show(<%=round.getNumber()%>)">+</a></td>
				</tr>
				<tr id="round<%=round.getNumber()%>" style="display:none">
<%
		for (Table table : round.getTables()) {
			boolean notEmpty = !GameResultService.isEmpty(table.getResult());
%>					<td <%=notEmpty?" class=\"resultPresent\"":"" %>>
						<a href="<%=request.getContextPath()%>/servlet/EditTable?<%= RequestCst.REQ_PARAM_TABLE_ID %>=<%=table.getId() %>"><%= BundleCst.BUNDLE.getString("round.label.table") + " "  + table.getName() %></a>
<%
			if(notEmpty) {
%>						<img width="12px" height="12px" src="../img/star_48.png" title="<%=BundleCst.BUNDLE.getString("round.table.result.not.empty") %>" />
<%
			}
%>						<ul class="player">
<%
			for (Player player : table.getListPlayers()) {
%>							<li><a href="<%=request.getContextPath() + "/servlet/EditPlayer?id=" + player.getId() %>"><%= player.getPrettyPrintName() %></a></li>
<%
			}
%>						</ul>
					</td>
<%
		}
%>				</tr>
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
show(1);
</script>
<%
}
%>		</div>
	</body>
</html>