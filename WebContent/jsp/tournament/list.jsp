<%@page
	import="java.util.List,java.util.ResourceBundle,org.mahjong.matoso.bean.Tournament,org.mahjong.matoso.constant.BundleCst,org.mahjong.matoso.constant.RequestCst,org.mahjong.matoso.constant.ServletCst"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
		<script>
			function checkInputs() {
				elt = document.getElementsByName('tournament-name');
				if (elt && elt.length == 1) {
					if (elt[0] && elt[0].value == "") {
						alert("<%=BundleCst.BUNDLE.getString(BundleCst.ERROR_NAME_MISSING)%>");
						return false;
					}
				}
			}
		</script>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<h2><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TITLE)%></h2>
		<div id="listTournament">
			<form  action="<%=request.getContextPath()%>/servlet/CreateTournament" method="post" onsubmit="return checkInputs();">
				<div class="label"><label for="tournament-name"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NAME)%></label></div>
				<input type="text" name="tournament-name" /><br/>
				<div class="label"><label for="teamActivate"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></label></div>
				<input type="checkBox" name="team-activate" id="teamActivate"/><br/>
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_CREATE)%>" />
			</form>
<%
List<Tournament> tournaments = (List<Tournament>) request.getAttribute(RequestCst.REQ_PARAM_LIST_TOURNAMENTS);
if (tournaments != null && !tournaments.isEmpty()) {
%>				<table>
					<thead>
						<!-- header labels -->
						<tr id="labels">
							<td><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_NAME)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_DELETE)%></td>
						</tr>
					</thead>
					<tbody>
<%
	Tournament t = null;
	for (int i=0; i<tournaments.size(); i++) { 
		t = tournaments.get(i);
%>							<tr class="<%="color"+(i%2)%>">
								<td><a href="<%=request.getContextPath()+"/servlet/LoadTournament?tournament-id="+t.getId()%>">
									<%=t.getName()%></a></td>
								<td><%=t.getTeamActivateStr()%></td>
								<td><a href="<%=request.getContextPath()+"/servlet/DeleteTournament?tournament-id="+t.getId()%>">
									<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_DELETE)%></a></td>
							</tr>
<%
	}
%>					</tbody>
				</table>
<%
}
%>		</div>
		<%@include file="../include/footer.jsp" %>
	</body>
</html>