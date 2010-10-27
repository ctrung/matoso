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
/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/editTable.js"></script>
	</head>

	<body>
<%@
include file="include/head.jsp"
%><%
Table table = (Table) request.getAttribute(RequestCst.REQ_ATTR_TABLE);
%>		<div id="editTable">
			<h2><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></h2>
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+session.getAttribute(SessionCst.SESSION_TOURNAMENT_ID)%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
			<div id="tableInfos">
				<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_TABLE)%> : <%= table.getName() %><br/>
				<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_ROUND)%> : <%=table.getRound().getNumber() %>
			</div>
<%
//Messages
MatosoMessages mm = (MatosoMessages)request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
if(MatosoMessages.isNotEmpty(mm)) {
%>				<ul class="matosoMesgs">
<%
	for(int i=0; i<mm.getMesgs().size(); i++) {
%>						<li class="<%="s"+mm.getMesgs().get(i).getSeverity()%>"><%=mm.getMesgs().get(i).getMesg()%></li>
<%
	}
%>				</ul>
<%
}
%>			<form action="<%=request.getContextPath()%>/servlet/SaveTableGamesAndScores" method="post">
				<input type="hidden" name="<%=RequestCst.REQ_PARAM_TABLE_ID%>" value="<%=table.getId()%>" />
				<div class="buttonPane">
					<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
					<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');" />
				</div>
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr class="separate">
							<th colspan="6"></th>
							<th>
								n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player1Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player1Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player1Team" name="EditTableForm"/></li>
								</ul>
							</th>
							<th>
								n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player2Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player2Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player2Team" name="EditTableForm"/></li>
								</ul>
							</th>
							<th>
								n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player3Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player3Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player3Team" name="EditTableForm"/></li>
								</ul>
							</th>
							<th>
								n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player4Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player4Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player4Team" name="EditTableForm"/></li>
								</ul>
							</th>
						</tr>
					</thead>
					<tbody>
<%
GameResult gr = (GameResult)request.getAttribute(RequestCst.REQ_ATTR_TABLE_RESULT);
String checkedAttr = "";
if(gr.isAutoCalculate()) checkedAttr = "checked=\"checked\"";
%>						<tr>
							<th colspan="6"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></th>
							<td><input type="text" name="score1" id="score1" class="score" value="<%=gr.getScorePlayer1PrettyPrint() %>"/></td>
							<td><input type="text" name="score2" id="score2" class="score" value="<%=gr.getScorePlayer2PrettyPrint() %>"/></td>
							<td><input type="text" name="score3" id="score3" class="score" value="<%=gr.getScorePlayer3PrettyPrint() %>"/></td>
							<td><input type="text" name="score4" id="score4" class="score" value="<%=gr.getScorePlayer4PrettyPrint() %>"/></td>
						</tr>
						<tr>
							<th colspan="5"></th>
							<th><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_POINTS)%></th>
							<td><input type="text" name="points1" id="points1" class="score" value="<%=gr.getPointsPlayer1PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points2" id="points2" class="score" value="<%=gr.getPointsPlayer2PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points3" id="points3" class="score" value="<%=gr.getPointsPlayer3PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points4" id="points4" class="score" value="<%=gr.getPointsPlayer4PrettyPrint() %>" disabled="disabled"/></td>
						</tr>
						<tr id="auto">
							<td colspan="8">
								<input type="checkbox" name="autoCalculate" value="yes" id="autoCalculate" <%=checkedAttr%> onclick="return actionAutoCalculate(this);"/>
								<label for="autoCalculate"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE)%></label>
								<p><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE_DESC)%></p>
							</td>
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
							<th class="columnWind"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WIND)%></th>
							<th class="columnGameNumber"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_GAME_NUMBER)%></th>
							<th class="columnHandValue"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%></th>
							<th class="columnHandValue"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%></th>
							<th class="columnPlayer">Win/Lose</th>
							<th></th>
							<th class="columnPlayer">
								n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player1Name" name="EditTableForm"/>
							</th>
							<th class="columnPlayer">
								n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player2Name" name="EditTableForm"/>
							</th>
							<th class="columnPlayer">
								n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <jsp:getProperty property="player3Name" name="EditTableForm"/>
							</th>
							<th class="columnPlayer">
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
						<tr class="<%="game color"+(cssWindColorNo%2) %>">
<%
	if (i % 4 == 0) {
%>							<td rowspan="8"><%=wind%></td>
<%
	}
%>							<td rowspan="2"><%=Integer.toString(i+1)%></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%>"><input type="text" name="<%=i+"_handValue"%>" id="<%=i+"_handValue"%>" value="<%=dtg.getHandValuePrettyPrint()%>" class="handValue"/></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%>"><input type="checkbox" name="<%=i+"_selfpick"%>" value="yes" id="<%=i+"_selfpick"%>" <%=dtg.getSelfpickCheckedAttribute() %> onclick="return actionSelfpickCheckbox(<%=i%>);"/></td>
							<td>
								<label><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label>
								<select id="<%=i%>_winner" name="<%=i%>_winner">
									<option value="0"></option>
									<option<%=dtg.getPlayerWinCheckedAttribute(1)%> value="1">n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerWinCheckedAttribute(2)%> value="2">n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerWinCheckedAttribute(3)%> value="3">n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerWinCheckedAttribute(4)%> value="4">n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName()%></option>
								</select><br/>
								<label><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>
								<select id="<%=i%>_loser" name="<%=i%>_loser">
									<option value="0"></option>
									<option<%=dtg.getPlayerLoseCheckedAttribute(1)%> value="1">n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerLoseCheckedAttribute(2)%> value="2">n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerLoseCheckedAttribute(3)%> value="3">n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName()%></option>
									<option<%=dtg.getPlayerLoseCheckedAttribute(4)%> value="4">n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName()%></option>
								</select>
							</td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName()%>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer1ScorePrettyPrint()%>" id="<%=i+"_scr1"%>"/></td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName()%>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer2ScorePrettyPrint()%>" id="<%=i+"_scr2"%>"/></td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName()%>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer3ScorePrettyPrint()%>" id="<%=i+"_scr3"%>"/></td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName()%>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer4ScorePrettyPrint()%>" id="<%=i+"_scr4"%>"/></td>
						</tr>
						<tr class="<%="game color"+(cssWindColorNo%2) %>">
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
					<tbody>
						<tr id="penalty">
							<td colspan="6"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" name="penalty1" id="penalty1" class="score" /></td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" name="penalty2" id="penalty2" class="score" /></td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" name="penalty3" id="penalty3" class="score" /></td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" name="penalty4" id="penalty4" class="score" /></td>
						</tr>
					</tbody>
<%
} else {
%>					<tbody>
<%
	for (int j = 0; j < penalties.size(); j++) {
		Penalty pen = penalties.get(j);
%>						<tr id="penalty">
							<td colspan="6"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="n°<jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/> <%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" name="penalty1" id="penalty1" class="score" value="<%=pen.getPenaltyPlayer1PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/> <%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" name="penalty2" id="penalty2" class="score" value="<%=pen.getPenaltyPlayer2PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/> <%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" name="penalty3" id="penalty3" class="score" value="<%=pen.getPenaltyPlayer3PrettyPrint() %>"/></td>
							<td title="n°<jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/> <%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" name="penalty4" id="penalty4" class="score" value="<%=pen.getPenaltyPlayer4PrettyPrint() %>"/></td>
						</tr>
<%
	}
%>					</tbody>
<%
}
%>				</table>
				<br/>
				<input type="button" class="addPenaltyButton" value="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_ADD_PENALTY)%>" />
				<br/><br/>
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
				<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');" />
			</form>
		</div>
	</body>
</html>