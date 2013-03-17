<%@page
	import="
		java.util.List,
		org.mahjong.matoso.bean.Tournament,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.constant.ServletCst"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TITLE)%></title>
		<%@include file="../include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div class="matoso-tabs matoso-content">
			<ul><li><a href="#matoso-list-tournament"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TITLE)%></a></li></ul>
			<div id="matoso-list-tournament">
<%
List<Tournament> tournaments = (List<Tournament>) request.getAttribute(RequestCst.REQ_PARAM_LIST_TOURNAMENTS);
if (tournaments != null && !tournaments.isEmpty()) {
%>
				<table>
					<tr>
						<th></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NAME)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></th>
						<th></th>
					</tr>
<%
	Tournament t = null;
	for (int i=0; i<tournaments.size(); i++) { 
		t = tournaments.get(i);
%>						<tr>
							<td class="ui-state-default ui-corner-all"><a class="ui-icon ui-icon-zoomin" href="/matoso/servlet/LoadTournament?tournament-id=<%=t.getId()%>" title="Load"></a></td>
							<td><%=t.getName()%></td>
							<td><%=t.getTeamActivateStr()%></td>
							<td class="ui-state-default ui-corner-all"><a title="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_DELETE)%> <%=t.getName()%>" href="/matoso/servlet/DeleteTournament?tournament-id=<%=t.getId()%>" onclick="return check();" class="ui-icon ui-icon-trash"></a></td>
						</tr>
<%
	}
%>				</table>
<%
}
%>
				<form action="<%=request.getContextPath()%>/servlet/CreateTournament" method="post" onsubmit="return checkInputs();">
					<table>
						<tr>
							<td><label for="tournament-name"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NAME)%></label></td>
							<td><input type="text" name="tournament-name" /></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="checkBox" name="team-activate" id="teamActivate" class="matoso-button" /><label for="teamActivate"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></label></td>
						</tr>
						<tr>
							<td><label for="rules"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_RULES)%></label></td>
							<td>
								<select name="rules" id="rules">
									<option value="MCR">MCR</option>
									<option value="RCR">RCR</option>
								</select>
							</td>
						</tr>
					</table>
					<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_CREATE)%>" />
				</form>
			</div>
		</div>
	</body>
</html>