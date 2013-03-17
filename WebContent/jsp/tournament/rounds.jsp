<%@page import="
	java.util.List,
	java.util.TreeMap,
	org.mahjong.matoso.constant.BundleCst,
	org.mahjong.matoso.constant.RequestCst,
	org.mahjong.matoso.constant.ServletCst,
	org.mahjong.matoso.constant.SessionCst,
	org.mahjong.matoso.bean.*,
	org.mahjong.matoso.service.TournamentService,
	org.mahjong.matoso.service.GameResultService,
	org.mahjong.matoso.service.RoundService,
	org.mahjong.matoso.service.TableService,
	org.mahjong.matoso.util.message.MatosoMessages"
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
		<title>MaToSo - <%=BundleCst.BUNDLE.getString("round.label.round")%></title>
		<%@include file="../include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div class="matoso-content">
			<h2><%=tournament.getName() + " (" + tournament.getRules()%>)</h2>
			<div class="matoso-tabs">
				<ul>
					<li><a href="#tab-rounds"><%=BundleCst.BUNDLE.getString("round.label.rounds")%></a></li>
					<li><a href="#tab-parameter"><%=BundleCst.BUNDLE.getString("round.label.parameters")%></a></li>
				</ul>
				<div class="matoso-tabs" id="tab-rounds">
					<ul>
<%
	String classCss;
	for (Round round : rounds) {
		if (RoundService.isFilledWithGames(round.getId()))
			classCss = "filledWithGames";
		else if(RoundService.isFilledWithTotal(round.getId()))
			classCss = "filledWithTotal";
		else
			classCss = "notFilled";

%>						<li class="<%=classCss%>"><a href="#round<%=round.getNumber()%>"><%= BundleCst.BUNDLE.getString("round.label.round") + " " + round.getNumber() %></a></li>
<%
	}
%>
					</ul>
<%
	for (Round round : rounds) {
%>					<div id="round<%=round.getNumber()%>" class="matoso-round">
<%
		for (Table table : round.getTables()) {
			if (TableService.hasSavedGame(table))
				classCss = "filledWithGames";
			else if (!GameResultService.isEmpty(table.getResult()))
				classCss = "filledWithTotal";
			else
				classCss = "notFilled";

%>						<div class="<%=classCss%> ui-state-highlight">
							<a class="matoso-button" href="<%=request.getContextPath()%>/servlet/EditTable?<%= RequestCst.REQ_PARAM_TABLE_ID %>=<%=table.getId() %>">
								<%= BundleCst.BUNDLE.getString("round.label.table") + " "  + table.getName() %>
							</a>
<%
			for (Player player : table.getListPlayers()) {
%>							<a href="/matoso/servlet/EditPlayer?id=<%=player.getId()%>" class="matoso-player">
								<%= player.getPrettyPrintName() %>
							</a>
<%
			}
%>						</div>
<%
		}
%>					</div>
<%
	}
%>				</div>
				<div id="tab-parameter">
<%
	for (Round round : rounds) {
%>
					<a href="/matoso/servlet/EditRound?id=<%=round.getId()%>" class="matoso-button">
						<%= BundleCst.BUNDLE.getString("round.label.round") + " " + round.getNumber() %>
					</a>
<%
	}
%>
				</div>
			</div>
		</div>
	</body>
</html>