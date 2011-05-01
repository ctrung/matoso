<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="
java.util.List,
org.mahjong.matoso.constant.BundleCst,
org.mahjong.matoso.constant.RequestCst,
org.mahjong.matoso.constant.ServletCst,
org.mahjong.matoso.constant.SessionCst,
org.mahjong.matoso.bean.Player,
org.mahjong.matoso.bean.Round,
org.mahjong.matoso.bean.Table,
org.mahjong.matoso.bean.Tournament,
org.mahjong.matoso.service.RoundService,
org.mahjong.matoso.service.TournamentService"
%><%@ taglib uri="http://displaytag.sf.net" prefix="display"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<%@include file="../include/cssAndScripts.jsp" %>
	</head>
	<body class="matoso-display">
<%
Integer tournamentId = (Integer) session.getAttribute(SessionCst.SESSION_TOURNAMENT_ID);
Tournament tournament = TournamentService.getById(tournamentId);
%>
		<h2><a href="/matoso/<%= ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET + "?" + RequestCst.REQ_PARAM_TOURNAMENT_ID + "=" + tournament.getId()%>"><%=tournament.getName()%></a></h2>
		<h3><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_FINAL_SESSION_TITLE)%></h3>
<%
Round round = (Round) request.getAttribute(RequestCst.REQ_ATTR_FINAL_SESSION);
if (round == null) out.print("no round");
else {
	List<Table> tables = round.getTables();
	if (tables == null) out.print("no tables");
	else {
%>
		<div class="matoso-round">
<%
		for (Table table : tables) {
%>
			<div class="ui-state-highlight">
				<h4>Table <%=table.getName()%></h4>
				<ul>
					<li><%=table.getPlayer1().getPrettyPrintName()%></li>
					<li><%=table.getPlayer2().getPrettyPrintName()%></li>
					<li><%=table.getPlayer3().getPrettyPrintName()%></li>
					<li><%=table.getPlayer4().getPrettyPrintName()%></li>
				</ul>
			</div>
<%
		}
%>
		</div>
<%
	}
}
%>	</body>
</html>