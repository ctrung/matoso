<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import="org.mahjong.matoso.constant.*"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@page import="java.util.List"%>
<%@page import="org.mahjong.matoso.bean.Player"%>
<%@page import="org.mahjong.matoso.bean.Tournament"%>
<%@page import="org.mahjong.matoso.constant.SessionCst"%>
<%@page import="org.mahjong.matoso.bean.Table"%>
<%@page import="org.mahjong.matoso.util.DateUtils"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<script src="/matoso/js/jquery-1.5.1.min.js"></script>
		<script src="/matoso/js/jquery-ui-1.8.12.custom.min.js"></script>
		<script src="/matoso/js/matoso.js"></script>
		<link rel="stylesheet" type="text/css" href="/matoso/css/jquery-ui-1.8.12.custom.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="/matoso/css/theme.css" media="screen" />
		<link rel="shortcut icon"  href="/matoso/img/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/print.css" media="print" />
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div class="noprint">
<% Tournament tournament = (Tournament) request.getAttribute("tournament"); %>
			<h2><%=tournament.getName() %></h2>
<%
List<Player> ps = (List<Player>) request.getAttribute("players");
%>
		</div>
		<div class="matoso-content matoso-draw">
<% 	
	Player p;
	int nbLines = 0;
	for(int i=0; i<ps.size(); i++){
		p = ps.get(i);
		if (i%2==0) {
%>
			<table cellpadding="0" cellspacing="0"<%=(++nbLines)%5==0?" class=\"matoso-page-break\"":""%>>
				<tr><td>
<%
		} else {
%>
				<td>
<%
		}
%>
			<table cellpadding="0" cellspacing="0" border="1">
				<thead>
					<tr>
						<th colspan="5">
							n°<%= p.getTournamentNumber()%> <%= p.getPrettyPrintName() %><%=p.getTeam()!=null?" - "+p.getTeam().getName():""%>
						</th>
					</tr>
					<tr>
						<th colspan="2"><%=BundleCst.BUNDLE.getString("draw.player.session.lbl")%></th>
						<th><%=BundleCst.BUNDLE.getString("draw.player.table.lbl")%></th>
						<th><%=BundleCst.BUNDLE.getString("draw.player.table.points.lbl")%></th>
						<th><%=BundleCst.BUNDLE.getString("draw.player.score.lbl")%></th>
					</tr>
				</thead>
				<tbody>
<%
	List<Table> ts = (List<Table>) p.getTables();
	if(ts!=null) {
		for(int j=0; j<ts.size(); j++) {
			Table t = ts.get(j);
			String date;
			if (t.getRound().getDate() == null ||
					(j != 0 && ts.get(j-1).getRound().getDate() != null && t.getRound().getDate().equals(ts.get(j-1).getRound().getDate())))
				date = "";
			else
				date = DateUtils.formatSQLDate(t.getRound().getDate()) + " ";
			String startTime = DateUtils.formatSQLTime(t.getRound().getStartTime());
			String finishTime = DateUtils.formatSQLTime(t.getRound().getFinishTime());

%>
					<tr>
						<td><%=t.getRound().getNumber()%></td>
						<td><%= date == null ? "" : (date + startTime + "-" + finishTime) %></td>
						<td><%=t.getName()%></td>
						<td></td>
						<td></td>
					</tr>
<%
		}
	}
%>
					<tr>
						<td colspan="2">Powered by MaToSo</td>
						<td><%=BundleCst.BUNDLE.getString("draw.player.total.lbl")%></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
<%
	if (i%2==0) {
%>
		</td>
<%
	} else {
%>
		</td></tr></table>
<%
	}
}
%>
		</div>
	</body>
</html>