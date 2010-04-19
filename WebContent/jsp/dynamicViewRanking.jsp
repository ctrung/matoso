<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="org.mahjong.matoso.constant.*" %>
<%@ page import="org.mahjong.matoso.bean.Tournament" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>
	<body id="bodyDynamicRanking">
		<div>
<%
Tournament tournament = (Tournament) session.getAttribute("tournament");
%>		<h2><%=tournament.getName()%></h2>
		<a href="<%=request.getContextPath()+"/"+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
		<br/><br/>
<%
if (request.getParameter("team") == null) {
%>
		<display:table name="sessionScope.ranking" pagesize="20" uid="dynamicRanking" cellpadding="0" cellspacing="0">
			<display:column property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" style="width:10%" />
			<display:column property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" class="left" style="width:70%" />
			<display:column property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" style="width:10%" />
			<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" style="width:10%" />
		</display:table>
<%
} else {
	int indexrankingTeam = 1;
%>		<display:table name="sessionScope.rankingTeam" sort="list" id="teamRanking" cellpadding="0" cellspacing="0">
			<display:column titleKey="<%=BundleCst.RANKING_POSITION%>" headerClass="position" style="width:10%"><%=indexrankingTeam++%></display:column>
			<display:column property="nameAndPlayers" titleKey="<%=BundleCst.RANKING_TEAM%>" class="nameAndPlayers" class="left" style="width:70%" />
			<display:column property="prettyPrintPoints" titleKey="<%=BundleCst.RANKING_POINTS%>" style="width:10%" />
			<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" style="width:10%" />
		</display:table>
<%
}
String paramName = new org.displaytag.util.ParamEncoder("dynamicRanking").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
int currentPage = 1;
String value = request.getParameter(paramName);
if (value != null && value.length() != 0)  currentPage = Integer.parseInt(value);
int nextPage = currentPage + 1;
int nbPlayers = 0;
if (session.getAttribute("ranking") != null) nbPlayers = ((java.util.List) session.getAttribute("ranking")).size();
if (nextPage > Math.ceil((double) nbPlayers / 20)) {
	nextPage = 0;
}
String url = request.getContextPath() + "/jsp/dynamicViewRanking.jsp?" + (nextPage == 0 ? "team=1&" : "") + paramName + "=" + nextPage;
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
		</div>
	</body>
</html>