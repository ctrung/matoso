<%@page
	import="org.mahjong.matoso.constant.*,org.mahjong.matoso.bean.*,org.mahjong.matoso.service.*,org.mahjong.matoso.util.DateUtils"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%= BundleCst.BUNDLE.getString("round.edit.title") %></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>

	<body>
		
		<%@include file="../include/head.jsp" %>

		<h2><%= BundleCst.BUNDLE.getString("round.edit.title") %></h2>
		
		<div id="edit">
		
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>">
					<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%>
			</a>
				
			<form action="<%=request.getContextPath()%>/servlet/SaveRound" method="post">
				<%
					Round round = (Round) request.getAttribute("round");
					if(round != null) {
				%>

					<input type="hidden" name="id" value="<%=round.getId().toString() %>" />	
					<div class="field">
						<div class="label"><label><%= BundleCst.BUNDLE.getString("round.number.lbl") %></label> :</div>
						<%=round.getNumber()%>
					</div> 
					<div class="field">
						<div class="label"><label for="date"><%= BundleCst.BUNDLE.getString("round.date") %></label> :</div>
						<input type="text" name="date" value="<%=DateUtils.formatSQLDate(round.getDate())%>" />
					</div>										
					<div class="field">
						<div class="label"><label for="startTime"><%= BundleCst.BUNDLE.getString("round.start.time") %></label> :</div>
						<input type="text" name="startTime" value="<%=DateUtils.formatSQLTime(round.getStartTime())%>" />
					</div>
					<div class="field">
						<div class="label"><label for="finishTime"><%= BundleCst.BUNDLE.getString("round.finish.time") %></label> :</div>
						<input type="text" name="finishTime" value="<%=DateUtils.formatSQLTime(round.getFinishTime())%>" />
					</div>
				<%} else { %>
					<%= BundleCst.BUNDLE.getString("round.error.missing") %>
				<% } %>
				
				<div class="field"><input type="submit" value="<%= BundleCst.BUNDLE.getString("general.save") %>" /></div>
			</form>
		</div>
	</body>
</html>