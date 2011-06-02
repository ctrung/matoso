<%@page import="
	java.util.List,
	java.util.TreeMap,
	java.util.ResourceBundle,
	org.mahjong.matoso.constant.BundleCst,
	org.mahjong.matoso.constant.RequestCst,
	org.mahjong.matoso.constant.ServletCst,
	org.mahjong.matoso.constant.SessionCst,
	org.mahjong.matoso.bean.*,
	org.mahjong.matoso.service.TournamentService,
	org.mahjong.matoso.service.GameResultService,
	org.mahjong.matoso.service.RoundService,
	org.mahjong.matoso.service.TableService,
	org.mahjong.matoso.util.message.MatosoMessages"
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%><%
Tournament tournament = (Tournament) request.getAttribute("tournament");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_FILL_METHOD)%></title>
		<%@include file="../include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div class="matoso-content">
			<h2><%=tournament.getName() + " (" + tournament.getRules()%>)</h2>
			<jsp:include page="../include/info.jsp">
				<jsp:param value="<%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_FILL_METHOD)%>" name="message" />
			</jsp:include>
			<div class="matoso-tabs">
				<ul>
					<li><a href="#matoso-import"><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_MASS_IMPORT)%></a></li>
					<li><a href="#matoso-add"><%= BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NB_PLAYERS) %></a></li>
				</ul>
				<form action="<%="/matoso"+ServletCst.REDIRECT_TO_IMPORT_PLAYER_SERVLET%>" enctype="multipart/form-data" method="POST" id="matoso-import">
<%
	MatosoMessages mm = (MatosoMessages)request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
	if(MatosoMessages.isNotEmpty(mm)) {
		String icon, state;
		for(int i=0; i<mm.getMesgs().size(); i++) {
			icon = mm.getMesgs().get(i).getSeverity() == 0 ? "ui-icon-info" : "ui-icon-alert";
			state = mm.getMesgs().get(i).getSeverity() == 0 ? "ui-state-highlight" : "ui-state-error";
%>
					<div class="ui-widget <%=state%> ui-corner-all matoso-message">
						<span class="ui-icon <%=icon%>"></span>
						<%=mm.getMesgs().get(i).getMesg()%>
					</div>
<%
		}
	}
%>
					<table>
						<tr>
							<td><label for="<%= RequestCst.REQ_PARAM_ROUND %>"><%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" /></td>
						</tr>
					</table>
					<div class="matoso-tabs">
						<ul>
							<li><a href="#matoso-import-csv" onclick="$('#csvImportRadioB').click()"><%=BundleCst.BUNDLE.getString("player.mass.import.type.label") %> CSV</a></li>
							<li><a href="#matoso-import-xls" onclick="$('#xslImportRadioB').click()"><%=BundleCst.BUNDLE.getString("player.mass.import.type.label") %> XLS</a></li>
						</ul>
						<div id="matoso-import-csv">
							<input type="radio" id="csvImportRadioB" name="importType" value="CSV" checked="checked" style="display:none" />
							<input type="file" accept="text/csv" name="csvfile" class="matoso-button" />
							<p><%=BundleCst.BUNDLE.getString("player.mass.import.csv.info")%></p>
							<a class="matoso-button" href="<%=request.getContextPath()+"/csv/players-sample.csv"%>"><%=BundleCst.BUNDLE.getString("player.mass.import.csv.link.sample")%></a>
						</div>
						<div id="matoso-import-xls">
							<input type="radio" id="xlsImportRadioB" name="importType" value="XLS" style="display:none" />
							<div class="ui-widget ui-state-highlight ui-corner-all matoso-message">
								<span class="ui-icon ui-icon-info"></span>En cours d'implémentation / still in development
							</div>
							<input type="file" accept="text/xls" name="xlsfile" disabled="disabled" class="matoso-button" />
						</div>
					</div>
					<input type="submit" name="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SUBMIT)%>" />
				</form>
				<form action="/matoso/servlet/AddPlayer" method="post" id="matoso-add">
					<table>
						<tr>
							<td><label for="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>"><%= BundleCst.BUNDLE.getString(BundleCst.TOURNAMENT_NB_PLAYERS) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_NB_PLAYERS %>" /></td>
						</tr>
						<tr>
							<td><label for="<%= RequestCst.REQ_PARAM_ROUND %>"><%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %></label></td>
							<td><input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" /></td>
						</tr>
					</table>
					<input type="submit" />
				</form>
			</div>
		</div>
	</body>
</html>