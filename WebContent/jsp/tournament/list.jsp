<%@page
	import="java.util.List,java.util.ResourceBundle,org.mahjong.matoso.bean.Tournament,org.mahjong.matoso.constant.BundleCst,org.mahjong.matoso.constant.RequestCst,org.mahjong.matoso.constant.ServletCst"
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TITLE)%></title>
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
			function check() {
				var pwd=prompt('Confirmer par le mot de passe ?');
				if (pwd=="adminMatoso") {
					return true;
				} else {
					alert("Mot de passe incorrect");
					return false;
				}
			}
		</script>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div id="listTournament">
			<h2><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TITLE)%></h2>
			<form  action="<%=request.getContextPath()%>/servlet/CreateTournament" method="post" onsubmit="return checkInputs();">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="left"><label for="tournament-name"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NAME)%></label></td>
						<td class="left"><input type="text" name="tournament-name" /></td>
					</tr>
					<tr>
						<td class="left"><label for="teamActivate"><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></label></td>
						<td class="left"><input type="checkBox" name="team-activate" id="teamActivate"/></td>
					</tr>
				</table>
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_CREATE)%>" />
			</form>
<%
List<Tournament> tournaments = (List<Tournament>) request.getAttribute(RequestCst.REQ_PARAM_LIST_TOURNAMENTS);
if (tournaments != null && !tournaments.isEmpty()) {
%>				<br/><br/>
				<table cellpadding="0" cellspacing="0">
					<tr id="labels">
						<th><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NAME)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_TEAM_ACTIVATE)%></th>
						<th><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_DELETE)%></th>
					</tr>
<%
	Tournament t = null;
	for (int i=0; i<tournaments.size(); i++) { 
		t = tournaments.get(i);
%>						<tr class="<%="color"+(i%2)%>">
							<td class="left"><a href="/matoso/servlet/LoadTournament?tournament-id=<%=t.getId()%>"><%=t.getName()%></a></td>
							<td><%=t.getTeamActivateStr()%></td>
							<td><a href="/matoso/servlet/DeleteTournament?tournament-id=<%=t.getId()%>" onclick="return check();"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_DELETE)%></a></td>
						</tr>
<%
	}
%>				</table>
<%
}
%>		</div>
		<%@include file="../include/footer.jsp" %>
	</body>
</html>