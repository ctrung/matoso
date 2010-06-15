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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body id="bodyfinalsession">
<%
Integer tournamentId = (Integer) session.getAttribute(SessionCst.SESSION_TOURNAMENT_ID);
Tournament tournament = TournamentService.getById(tournamentId);
%>		<h2><a href="/matoso/<%=
			ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET + "?"
					+ RequestCst.REQ_PARAM_TOURNAMENT_ID
					+ "=" + tournament.getId()%>"><%=tournament.getName()%></a></h2>
		<h3><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_FINAL_SESSION_TITLE)%></h3>
<%
Round round = (Round) request.getAttribute(RequestCst.REQ_ATTR_FINAL_SESSION);
if (round == null) out.print("no round");
else {
	List<Table> tables = round.getTables();
	if (tables == null) out.print("no tables");
	else {
%>		<table cellpadding="0" cellspacing="0" border="0">
<%
		int i = 0;
		boolean close = false;
		for (Table table : tables) {
			if (i++ % 4 == 0) {
				close = false;
%>				<tr>
<%
			}
%>					<td>
						Table <%=table.getName()%>
						<ul>
							<li><%=table.getPlayer1().getPrettyPrintName()%></li>
							<li><%=table.getPlayer2().getPrettyPrintName()%></li>
							<li><%=table.getPlayer3().getPrettyPrintName()%></li>
							<li><%=table.getPlayer4().getPrettyPrintName()%></li>
						</ul>
					</td>
<%
			if (i % 4 == 0) {
				close = true;
%>				</tr>
<%
			}
		}
		if (!close) {
%>				</tr>
<%
		}
%>		</table>
<%
	}
}
%>	</body>
</html>