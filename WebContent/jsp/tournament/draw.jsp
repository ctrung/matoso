<!-- The tournament draw page -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page
	import="org.mahjong.matoso.constant.*"
	language="java"
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>

<%@page import="java.util.List"%>
<%@page import="org.mahjong.matoso.bean.Player"%>
<%@page import="org.mahjong.matoso.constant.SessionCst"%>


<%@page import="org.mahjong.matoso.bean.Table"%><html>

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
			
			<% String name = (String) request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT); %>
			<h2><%=name %></h2>
			
			<div class="left">		
				<a href="<%=request.getContextPath()+"/"+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+
						RequestCst.REQ_PARAM_TOURNAMENT_NAME+"="+name%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
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
				<%=session.getAttribute(SessionCst.SES_ATTR_TOURNAMENT) %>
				<p><%=p.getPrettyPrintName()%></p>
				<table>
					<thead>
						<tr><td colspan="2">Session</td><td>Table</td><td>Table points</td><td>Scoring points</td></tr>
					</thead>
					<tbody>
						
						<%
							List<Table> ts = (List<Table>) p.getTables();
							if(ts!=null) {
								for(int j=0; j<ts.size(); j++) {
									Table t = ts.get(j);
						%>						
					
							<tr><td><%=t.getRound()%></td><td>-</td><td><%=t.getName()%></td><td></td><td></td></tr>
						
						<%		}
							} %>
						
						<tr><td colspan="2" class="invisible"></td><td>Total</td><td></td><td></td></tr>
					
					</tbody>
				</table>				
			</div>
			
			<hr class="print"/>
			
		<%} %>		
	</body>
</html>
	