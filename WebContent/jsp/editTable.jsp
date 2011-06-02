<%@page
	import="
		java.util.List,
		java.util.ResourceBundle,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.constant.SessionCst,
		org.mahjong.matoso.constant.ServletCst,
		org.mahjong.matoso.bean.*,
		org.mahjong.matoso.form.EditTableForm,
		org.mahjong.matoso.display.DisplayTableGame,
		org.mahjong.matoso.service.TeamService,
		org.mahjong.matoso.service.TournamentService,
		org.mahjong.matoso.util.message.MatosoMessages"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%><jsp:useBean
	id="EditTableForm"
	scope="request"
	class="org.mahjong.matoso.form.EditTableForm"
/><%
Table table = (Table) request.getAttribute(RequestCst.REQ_ATTR_TABLE);
int indexTab = 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></title>
		<%@include file="include/cssAndScripts.jsp" %>
		<script type="text/javascript" src="/matoso/js/editTable.js"></script>
	</head>
	<body>
		<%@include file="include/head.jsp"%>
		<div class="matoso-content">
			<h2><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></h2>
			<h3><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_ROUND)%> : <%=table.getRound().getNumber() %></h3>
			<h4><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_TABLE)%> : <%= table.getName() %></h4>
<%
//Messages
MatosoMessages mm = (MatosoMessages)request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
if(MatosoMessages.isNotEmpty(mm)) {
	String state,icon;
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
GameResult gr = (GameResult)request.getAttribute(RequestCst.REQ_ATTR_TABLE_RESULT);
String checkedAttr = "";
if(gr.isAutoCalculate()) checkedAttr = "checked=\"checked\"";
%>
			<form action="/matoso/servlet/SaveTableGamesAndScores" method="post">
				<input type="hidden" name="<%=RequestCst.REQ_PARAM_TABLE_ID%>" value="<%=table.getId()%>" />
				<div>
					<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
					<input
						onclick="return blankAllFields('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');"
						type="button"
						value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" />
					<input <%=checkedAttr%>
						id="autoCalculate"
						name="autoCalculate"
						onclick="return actionAutoCalculate(this);"
						type="checkbox"
						value="yes" />
					<label for="autoCalculate" title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE_DESC)%>">
						<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE)%>
					</label>
				</div>
				<table id="editTable">
					<thead>
						<tr>
							<td colspan="5" rowspan="4"></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_NAME)%> :</td>
							<td><jsp:getProperty property="player1Name" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player2Name" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player3Name" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player4Name" name="EditTableForm"/></td>
						</tr>
						<tr>
							<td>n° :</td>
							<td><jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/></td>
						</tr>
						<tr>
							<td>EMA :</td>
							<td><jsp:getProperty property="player1Ema" name="EditTableForm" /></td>
							<td><jsp:getProperty property="player2Ema" name="EditTableForm" /></td>
							<td><jsp:getProperty property="player3Ema" name="EditTableForm" /></td>
							<td><jsp:getProperty property="player4Ema" name="EditTableForm" /></td>
						</tr>
						<tr>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> :</td>
							<td><jsp:getProperty property="player1Team" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player2Team" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player3Team" name="EditTableForm"/></td>
							<td><jsp:getProperty property="player4Team" name="EditTableForm"/></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="5"></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td><input type="text"size="5"  name="score1" id="score1" value="<%=gr.getScorePlayer1PrettyPrint() %>" tabindex="<%=++indexTab%>" /></td>
							<td><input type="text" size="5" name="score2" id="score2" value="<%=gr.getScorePlayer2PrettyPrint() %>" tabindex="<%=++indexTab%>" /></td>
							<td><input type="text" size="5" name="score3" id="score3" value="<%=gr.getScorePlayer3PrettyPrint() %>" tabindex="<%=++indexTab%>" /></td>
							<td><input type="text" size="5" name="score4" id="score4" value="<%=gr.getScorePlayer4PrettyPrint() %>" tabindex="<%=++indexTab%>" /></td>
						</tr>
						<tr>
							<td colspan="5"></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_POINTS)%></td>
							<td><input type="text" size="5" name="points1" id="points1" value="<%=gr.getPointsPlayer1PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" size="5" name="points2" id="points2" value="<%=gr.getPointsPlayer2PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" size="5" name="points3" id="points3" value="<%=gr.getPointsPlayer3PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" size="5" name="points4" id="points4" value="<%=gr.getPointsPlayer4PrettyPrint() %>" disabled="disabled"/></td>
						</tr>
					</tbody>
<%
//scoring inputs
List<DisplayTableGame> dtgs = (List<DisplayTableGame>) request.getAttribute(RequestCst.REQ_ATTR_TABLE_DISPLAY_GAMES);
DisplayTableGame dtg = null;
int cssWindColorNo = 0;
Integer player1 = 0, player2 = 0, player3 = 0, player4 = 0;
String wind;
for(int i = 0; i < dtgs.size(); i++) {
	dtg = dtgs.get(i);
	cssWindColorNo = (int) Math.ceil((i == 0 ? 1 : i) / 4);
	if (cssWindColorNo == 0) {
		wind = BundleCst.BUNDLE.getString(BundleCst.TABLE_EAST);
	} else if (cssWindColorNo == 1) {
		wind = BundleCst.BUNDLE.getString(BundleCst.TABLE_SOUTH);
	} else if (cssWindColorNo == 2) {
		wind = BundleCst.BUNDLE.getString(BundleCst.TABLE_WEST);
	} else {
		wind = BundleCst.BUNDLE.getString(BundleCst.TABLE_NORTH);
	}
	if (i % 4 == 0) {
%>					<thead>
						<tr>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WIND)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_GAME_NUMBER)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%></th>
							<th>Win/Lose</th>
							<th></th>
							<th>
								n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player1Name" name="EditTableForm"/>
							</th>
							<th>
								n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player2Name" name="EditTableForm"/>
							</th>
							<th>
								n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player3Name" name="EditTableForm"/>
							</th>
							<th>
								n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player4Name" name="EditTableForm"/>
							</th>
						</tr>
					</thead>
					<tbody>
<%
	}
	player1 += (dtg.getPlayer1Score() == null ? 0 : dtg.getPlayer1Score());
	player2 += (dtg.getPlayer2Score() == null ? 0 : dtg.getPlayer2Score());
	player3 += (dtg.getPlayer3Score() == null ? 0 : dtg.getPlayer3Score());
	player4 += (dtg.getPlayer4Score() == null ? 0 : dtg.getPlayer4Score());
%>
						<tr>
<%
	if (i % 4 == 0) {
%>							<td rowspan="8"><%=wind%></td>
<%
	}
%>							<td rowspan="2"><%=Integer.toString(i+1)%></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%>">
								<input type="text" size="5" name="<%=i+"_handValue"%>" id="<%=i+"_handValue"%>" value="<%=dtg.getHandValuePrettyPrint()%>" tabindex="<%=++indexTab%>" />
							</td>
							<td>
								<label for="<%=i+"_selfpick"%>"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%></label>
								<input type="checkbox" name="<%=i+"_selfpick"%>" value="yes" id="<%=i+"_selfpick"%>" <%=dtg.getSelfpickCheckedAttribute() %> onclick="return actionSelfpickCheckbox(<%=i%>);" tabindex="<%=++indexTab%>"/>
							</td>
							<td>
<div class="matoso-radio">
	<input<%=dtg.isPlayer1Win()?" checked='checked'" : ""%> type="radio" id="radio<%=i%>_1" name="<%=i%>_winner" value="1" tabindex="<%=++indexTab%>" />
	<label for="radio<%=i%>_1"><jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer2Win()?" checked='checked'" : ""%> type="radio" id="radio<%=i%>_2" name="<%=i%>_winner" value="2" tabindex="<%=++indexTab%>" />
	<label for="radio<%=i%>_2"><jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer3Win()?" checked='checked'" : ""%> type="radio" id="radio<%=i%>_3" name="<%=i%>_winner" value="3" tabindex="<%=++indexTab%>" />
	<label for="radio<%=i%>_3"><jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer4Win()?" checked='checked'" : ""%> type="radio" id="radio<%=i%>_4" name="<%=i%>_winner" value="4" tabindex="<%=++indexTab%>" />
	<label for="radio<%=i%>_4"><jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/></label>
</div>
<div class="matoso-radio">
	<input<%=dtg.isPlayer1Lose()?" checked='checked'" : ""%> type="radio" id="lose_radio<%=i%>_1" name="<%=i%>_loser" value="1" tabindex="<%=++indexTab%>" />
	<label for="lose_radio<%=i%>_1"><jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer2Lose()?" checked='checked'" : ""%> type="radio" id="lose_radio<%=i%>_2" name="<%=i%>_loser" value="2" tabindex="<%=++indexTab%>" />
	<label for="lose_radio<%=i%>_2"><jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer3Lose()?" checked='checked'" : ""%> type="radio" id="lose_radio<%=i%>_3" name="<%=i%>_loser" value="3" tabindex="<%=++indexTab%>" />
	<label for="lose_radio<%=i%>_3"><jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/></label>
	<input<%=dtg.isPlayer4Lose()?" checked='checked'" : ""%> type="radio" id="lose_radio<%=i%>_4" name="<%=i%>_loser" value="4" tabindex="<%=++indexTab%>" />
	<label for="lose_radio<%=i%>_4"><jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/></label>
</div>
							</td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName()%>">
								<input type="text" size="5" disabled="disabled" value="<%=dtg.getPlayer1ScorePrettyPrint()%>" id="<%=i+"_scr1"%>"/>
							</td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName()%>">
								<input type="text" size="5" disabled="disabled" value="<%=dtg.getPlayer2ScorePrettyPrint()%>" id="<%=i+"_scr2"%>"/>
							</td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName()%>">
								<input type="text" size="5" disabled="disabled" value="<%=dtg.getPlayer3ScorePrettyPrint()%>" id="<%=i+"_scr3"%>"/>
							</td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName()%>">
								<input type="text" size="5" disabled="disabled" value="<%=dtg.getPlayer4ScorePrettyPrint()%>" id="<%=i+"_scr4"%>"/>
							</td>
						</tr>
						<tr>
							<td colspan="3"></td>
							<td>TOTAL</td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName()%>"><%=player1%></td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName()%>"><%=player2%></td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName()%>"><%=player3%></td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName()%>"><%=player4%></td>
						</tr>
<%
	if (i % 4 == 3) {
%>						</tbody>
<%
	}
}
List<Penalty> penalties = table.getPenalties();
if (penalties == null | penalties.size() == 0) {
%>
					<tbody id="penalties">
						<tr>
							<td colspan="6"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName() %>">
								<input type="text" size="5" name="penalty1" id="penalty1" tabindex="<%=++indexTab%>" />
							</td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName() %>">
								<input type="text" size="5" name="penalty2" id="penalty2" tabindex="<%=++indexTab%>" />
							</td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName() %>">
								<input type="text" size="5" name="penalty3" id="penalty3" tabindex="<%=++indexTab%>" />
							</td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName() %>">
								<input type="text" size="5" name="penalty4" id="penalty4" tabindex="<%=++indexTab%>" />
							</td>
						</tr>
					</tbody>
<%
} else {
%>					<tbody id="penalties">
<%
	for (Penalty pen : penalties) {
%>						<tr>
							<td colspan="6"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" size="5" name="penalty1" id="penalty1" value="<%=pen.getPenaltyPlayer1PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" size="5" name="penalty2" id="penalty2" value="<%=pen.getPenaltyPlayer2PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" size="5" name="penalty3" id="penalty3" value="<%=pen.getPenaltyPlayer3PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" size="5" name="penalty4" id="penalty4" value="<%=pen.getPenaltyPlayer4PrettyPrint() %>"/></td>
						</tr>
<%
	}
%>					</tbody>
<%
}
%>				</table>
				<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_ADD_PENALTY)%>" class="addPenaltyButton" />
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
				<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');" />
			</form>
		</div>
	</body>
</html>