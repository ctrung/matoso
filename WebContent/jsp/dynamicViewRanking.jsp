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
	
		<%
			Tournament tournament = (Tournament) request.getAttribute("tournament");
		%>
		<h2><%=tournament.getName()%></h2>
		<a href="<%=request.getContextPath()+"/"+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
		<display:table name="sessionScope.ranking" pagesize="10" uid="dynamicRanking">
			<display:column property="rank" titleKey="<%=BundleCst.RANKING_POSITION%>" />
			<display:column property="prettyPrintName" titleKey="<%=BundleCst.RANKING_PLAYER%>" />
			<display:column property="points" titleKey="<%=BundleCst.RANKING_POINTS%>" format="{0,number,###.##}" />
			<display:column property="score" titleKey="<%=BundleCst.RANKING_SCORE%>" />
		</display:table>
<%
String paramName = new org.displaytag.util.ParamEncoder("dynamicRanking").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
int currentPage = 1;
String value = request.getParameter(paramName);
if (value != null && value.length() !=0)  currentPage = Integer.parseInt(value);
int nextPage = currentPage + 1;
int nbPlayers = ((java.util.List) session.getAttribute("ranking")).size();
if (nextPage > Math.ceil((double) nbPlayers / 10)) nextPage = 1;
String url = request.getContextPath() + "/jsp/dynamicViewRanking.jsp?nbElementsByPage=10&" + paramName + "=" + nextPage;
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