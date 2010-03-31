<!-- The tournament draw page -->

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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/print.css" media="print" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	
	<body id="draw">
		
		<div class="noprint">
	
			<%@include file="../include/head.jsp"%>
			
			<% Tournament tournament = (Tournament) request.getAttribute("tournament"); %>
			<h2><%=tournament.getName() %></h2>
			
			<div class="left">		
				<a href="<%=request.getContextPath()+"/"+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+
						RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
				<br/>
				<%-- iterate over each player to display its rounds --%>
		
				<%
					List<Player> ps = (List<Player>) request.getAttribute("players"); %>
					
				nb : <%=Integer.toString(ps.size()) %>
			</div>
		</div>
		
		<hr/>			
			
		<% 	
			for(int i=0; i<ps.size(); i++){
				Player p = ps.get(i);
		%>
		
			<div class="player">
				<p><%=p.getPrettyPrintName() + " (no " + p.getTournamentNumber() + ")" %></p>
				<%if(p.getTeam() != null) { %>
					<p class="team"><%="(" + BundleCst.BUNDLE.getString("draw.player.team.lbl") + " " + p.getTeam().getName() + ")"%></p>
				<%} %>
				<table>
					<thead>
						<tr>
							<td colspan="2"><%=BundleCst.BUNDLE.getString("draw.player.session.lbl")%></td>
							<td><%=BundleCst.BUNDLE.getString("draw.player.table.lbl")%></td>
							<td><%=BundleCst.BUNDLE.getString("draw.player.table.points.lbl")%></td>
							<td><%=BundleCst.BUNDLE.getString("draw.player.score.lbl")%></td>
						</tr>
					</thead>
					<tbody>
						
						<%
							List<Table> ts = (List<Table>) p.getTables();
							if(ts!=null) {
								for(int j=0; j<ts.size(); j++) {
									Table t = ts.get(j);
									String date = DateUtils.formatSQLDate(t.getRound().getDate());
									String startTime = DateUtils.formatSQLTime(t.getRound().getStartTime());
									String finishTime = DateUtils.formatSQLTime(t.getRound().getFinishTime());
						%>						
							<tr><td><%=t.getRound().getNumber()%></td><td><%= date + " " + startTime + "-" + finishTime %></td><td><%=t.getName()%></td><td></td><td></td></tr>
						
						<%		}
							} %>
						
						<tr>
							<td colspan="2" class="invisible"></td>
							<td><%=BundleCst.BUNDLE.getString("draw.player.total.lbl")%></td>
							<td></td>
							<td></td>
						</tr>
					
					</tbody>
				</table>				
			</div>
			
			<hr class="print"/>
			
		<%} %>		
	</body>
</html>
	