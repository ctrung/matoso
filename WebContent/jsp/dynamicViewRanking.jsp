<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="org.mahjong.matoso.constant.*,
org.mahjong.matoso.bean.Player,
org.mahjong.matoso.bean.Tournament"
%><%@ taglib uri="http://displaytag.sf.net" prefix="display"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body id="bodyDynamicRanking">
<%
if (request.getParameter("team") == null) {
	Tournament tournament = (Tournament) session.getAttribute("tournament");
%>	<div id="dynamicRanking">
		<h2><a href="/matoso/<%=ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=tournament.getName()%></a></h2>
		<display:table name="sessionScope.ranking" pagesize="20" cellpadding="0" cellspacing="0" id="tableDynamicRanking">
			<display:column property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" style="width:10%" />
			<display:column titleKey="<%=BundleCst.PLAYER_NATIONALITY%>" style="width:10%"><img src="/matoso/img/flag/<%=((Player) tableDynamicRanking).getCountry()%>.gif" /></display:column>
			<display:column property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" class="left" style="width:60%" />
			<display:column property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" style="width:10%" />
			<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" style="width:10%" />
		</display:table>
	</div>
<%
} else {
	String paramName = new org.displaytag.util.ParamEncoder("tableDynamicRanking").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
	String value = request.getParameter(paramName);
	int indexrankingTeam = 1;
	if (value != null && value.length() != 0)
		indexrankingTeam = 1 + (Integer.parseInt(value) - 1) * 10;
	Tournament tournament = (Tournament) session.getAttribute("tournament");
%>	<div id="dynamicTeamRanking">
		<h2><a href="/matoso/<%=ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=tournament.getName()%></a></h2>
		<display:table name="sessionScope.rankingTeam" pagesize="10" cellpadding="0" cellspacing="0" id="tableDynamicRanking">
			<display:column titleKey="<%=BundleCst.RANKING_POSITION%>" headerClass="position" style="width:10%"><%=indexrankingTeam++%></display:column>
			<display:column property="nameAndPlayers" titleKey="<%=BundleCst.RANKING_TEAM%>" class="nameAndPlayers" class="left" style="width:70%" />
			<display:column property="prettyPrintPoints" titleKey="<%=BundleCst.RANKING_POINTS%>" style="width:10%" />
			<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" style="width:10%" />
		</display:table>
	</div>
<%
}
String paramName = new org.displaytag.util.ParamEncoder("tableDynamicRanking").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
int currentPage = 1;
String value = request.getParameter(paramName);
if (value != null && value.length() != 0)
	currentPage = Integer.parseInt(value);
int nextPage = currentPage + 1;
int nbPlayers = 0;
if (session.getAttribute("ranking") != null)
	nbPlayers = ((java.util.List) session.getAttribute("ranking")).size();
int nbTeams = 0;
if (session.getAttribute("rankingTeam") != null)
	nbTeams = ((java.util.List) session.getAttribute("rankingTeam")).size();

String url;
if (request.getParameter("team") == null) {
	if (nextPage > Math.ceil((double) nbPlayers / 20))
		url = "/matoso/jsp/dynamicViewRanking.jsp?" + paramName + "=" + 1 + "&team=1";
	else
		url = "/matoso/jsp/dynamicViewRanking.jsp?" + paramName + "=" + nextPage;
} else {
	if (nextPage > Math.ceil((double) nbTeams / 10))
		url = "/matoso/jsp/dynamicViewRanking.jsp?" + paramName + "=" + 1;
	else
		url = "/matoso/jsp/dynamicViewRanking.jsp?" + paramName + "=" + nextPage + "&team=1";
}
%>		<script>
			newurl='<%=url%>';
			var spans = document.getElementsByTagName("span");
			for (i=0;i<spans.length;i++){
				spans[i].style.display="none";
			}
			function setNewTime() {
				clearTimeout();
				var time = 10;
				setTimeout("window.location.href='<%=url%>'", time * 1000);
			}
			setNewTime();
		</script>
	</body>
</html>