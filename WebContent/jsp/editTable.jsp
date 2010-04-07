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
Tournament tournament = (Tournament) request.getAttribute("tournament");
Table table = (Table) request.getAttribute(RequestCst.REQ_ATTR_TABLE);
%>		<h2><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></h2>
		<div id="editTable">
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+RequestCst.REQ_PARAM_TOURNAMENT_ID+"="+tournament.getId()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
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
				<table>
					<colgroup>
						<col class="colWind"/>
						<col class="colGameNb"/>
						<col class="colScore"/>
						<col class="colSelf"/>
						<col class="colP1"/>
						<col class="colP2"/>
						<col class="colP3"/>
						<col class="colP4"/>
					</colgroup>
					<thead>
						<tr class="separate">
							<td colspan="4"></td>
							<td>
								(no <jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player1Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player1Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player1Team" name="EditTableForm"/></li>
								</ul>
							</td>
							<td>
								(no <jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player2Name" name="EditTableForm"/>
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player2Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player2Team" name="EditTableForm"/></li>
								</ul>
							</td>
							<td>
								(no <jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player3Name" name="EditTableForm"/> 
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player3Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player3Team" name="EditTableForm"/></li>
								</ul>
							</td>
							<td>
								(no <jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player4Name" name="EditTableForm"/> 
								<ul class="information">
									<li>EMA : <jsp:getProperty property="player4Ema" name="EditTableForm" /></li>
									<li><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_TEAM)%> : <jsp:getProperty property="player4Team" name="EditTableForm"/></li>
								</ul>
							</td>
						</tr>
					</thead>
					<tbody>
<% 
GameResult gr = (GameResult)request.getAttribute(RequestCst.REQ_ATTR_TABLE_RESULT); 
String checkedAttr = "";
if(gr.isAutoCalculate()) checkedAttr = "checked=\"checked\"";
%>						<tr>
							<td colspan="4"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td><input type="text" name="score1" id="score1" class="score" value="<%=gr.getScorePlayer1PrettyPrint() %>"/></td>
							<td><input type="text" name="score2" id="score2" class="score" value="<%=gr.getScorePlayer2PrettyPrint() %>"/></td>
							<td><input type="text" name="score3" id="score3" class="score" value="<%=gr.getScorePlayer3PrettyPrint() %>"/></td>
							<td><input type="text" name="score4" id="score4" class="score" value="<%=gr.getScorePlayer4PrettyPrint() %>"/></td>
						</tr>
						<tr>
							<td colspan="4"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_POINTS)%></td>
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
					<thead>
						<tr>
							<td class="columnWind"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WIND)%></td>
							<td class="columnGameNumber"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_GAME_NUMBER)%></td>
							<td class="columnHandValue"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%></td>
							<td class="columnHandValue"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%></td>
							<td class="columnPlayer">
								(no <jsp:getProperty property="player1TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player1Name" name="EditTableForm"/>
							</td>
							<td class="columnPlayer">
								(no <jsp:getProperty property="player2TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player2Name" name="EditTableForm"/>
							</td>
							<td class="columnPlayer">
								(no <jsp:getProperty property="player3TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player3Name" name="EditTableForm"/>
							</td>
							<td class="columnPlayer">
								(no <jsp:getProperty property="player4TournamentNumber" name="EditTableForm"/>) <jsp:getProperty property="player4Name" name="EditTableForm"/>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr><td colspan="8"><div class="innerScroll"><table style="border-collapse: collapse;">
						<colgroup>
							<col class="colWind"/>
							<col class="colGameNb"/>
							<col class="colScore"/>
							<col class="colSelf"/>
							<col class="colP1"/>
							<col class="colP2"/>
							<col class="colP3"/>
							<col class="colP4"/>
						</colgroup>						
						<tbody>
<%
//scoring inputs
List<DisplayTableGame> dtgs = (List<DisplayTableGame>)request.getAttribute(RequestCst.REQ_ATTR_TABLE_DISPLAY_GAMES);
DisplayTableGame dtg = null;
int cssWindColorNo = 0;
String wind;
for(int i=0; i<dtgs.size(); i++) {
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
%>						<tr class="<%="game color"+cssWindColorNo %>">
<%
	if (i % 4 == 0) {
%>							<td rowspan="8"><%=wind%></td>
<%
	}
%>							<td rowspan="2"><%=Integer.toString(i+1)%></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%>"><input type="text" name="<%=i+"_handValue"%>" id="<%=i+"_handValue"%>" value="<%=dtg.getHandValuePrettyPrint()%>" class="handValue"/></td>
							<td title="<%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%>"><input type="checkbox" name="<%=i+"_selfpick"%>" value="yes" id="<%=i+"_selfpick"%>" <%=dtg.getSelfpickCheckedAttribute() %> onclick="return actionSelfpickCheckbox(<%=i%>);"/></td>
							<td title="<%=table.getPlayer1().getPrettyPrintName() %>">
								<input type="radio" name="<%=i+"_winOrLose1"%>" value="win" id="<%=i+"_winner1"%>" <%=dtg.getPlayerWinCheckedAttribute(1) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 1);"/> <label for="<%=i+"_winner1"%>" id="<%=i+"_lbl-winner1"%>" <%=dtg.hasWonStyle(1) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
								<input type="radio" name="<%=i+"_winOrLose1"%>" value="lose" id="<%=i+"_loser1"%>" <%=dtg.getPlayerLoseCheckedAttribute(1) %> onclick="return actionLoserRadioInput(<%=i%>, this, 1);"/> <label for="<%=i+"_loser1"%>" id="<%=i+"_lbl-loser1"%>" <%=dtg.hasLoseStyle(1) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>
							</td>
							<td title="<%=table.getPlayer2().getPrettyPrintName() %>">
								<input type="radio" name="<%=i+"_winOrLose2"%>" value="win" id="<%=i+"_winner2"%>" <%=dtg.getPlayerWinCheckedAttribute(2) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 2);"/> <label for="<%=i+"_winner2"%>" id="<%=i+"_lbl-winner2"%>" <%=dtg.hasWonStyle(2) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
								<input type="radio" name="<%=i+"_winOrLose2"%>" value="lose" id="<%=i+"_loser2"%>" <%=dtg.getPlayerLoseCheckedAttribute(2) %> onclick="return actionLoserRadioInput(<%=i%>, this, 2);"/> <label for="<%=i+"_loser2"%>" id="<%=i+"_lbl-loser2"%>" <%=dtg.hasLoseStyle(2) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
							</td>
							<td title="<%=table.getPlayer3().getPrettyPrintName() %>">
								<input type="radio" name="<%=i+"_winOrLose3"%>" value="win" id="<%=i+"_winner3"%>" <%=dtg.getPlayerWinCheckedAttribute(3) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 3);"/> <label for="<%=i+"_winner3"%>" id="<%=i+"_lbl-winner3"%>" <%=dtg.hasWonStyle(3) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
								<input type="radio" name="<%=i+"_winOrLose3"%>" value="lose" id="<%=i+"_loser3"%>" <%=dtg.getPlayerLoseCheckedAttribute(3) %> onclick="return actionLoserRadioInput(<%=i%>, this, 3);"/> <label for="<%=i+"_loser3"%>" id="<%=i+"_lbl-loser3"%>" <%=dtg.hasLoseStyle(3) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
							</td>
							<td title="<%=table.getPlayer4().getPrettyPrintName() %>">
								<input type="radio" name="<%=i+"_winOrLose4"%>" value="win" id="<%=i+"_winner4"%>" <%=dtg.getPlayerWinCheckedAttribute(4) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 4);"/> <label for="<%=i+"_winner4"%>" id="<%=i+"_lbl-winner4"%>" <%=dtg.hasWonStyle(4) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
								<input type="radio" name="<%=i+"_winOrLose4"%>" value="lose" id="<%=i+"_loser4"%>" <%=dtg.getPlayerLoseCheckedAttribute(4) %> onclick="return actionLoserRadioInput(<%=i%>, this, 4);"/> <label for="<%=i+"_loser4"%>" id="<%=i+"_lbl-loser4"%>" <%=dtg.hasLoseStyle(4) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
							</td>
						</tr>
						<tr class="<%="game color"+cssWindColorNo %>">
							<td colspan="2"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td title="<%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer1ScorePrettyPrint()%>" id="<%=i+"_scr1"%>"/></td>
							<td title="<%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer2ScorePrettyPrint()%>" id="<%=i+"_scr2"%>"/></td>
							<td title="<%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer3ScorePrettyPrint()%>" id="<%=i+"_scr3"%>"/></td>
							<td title="<%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer4ScorePrettyPrint()%>" id="<%=i+"_scr4"%>"/></td>
						</tr>
<%
}
List<Penalty> penalties = table.getPenalties();
if (penalties == null | penalties.size() == 0) {
%>
						<tr id="penalty">
							<td colspan="4"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="<%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" name="penalty1" id="penalty1" class="score" /></td>
							<td title="<%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" name="penalty2" id="penalty2" class="score" /></td>
							<td title="<%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" name="penalty3" id="penalty3" class="score" /></td>
							<td title="<%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" name="penalty4" id="penalty4" class="score" /></td>
						</tr>
<%
} else {
	for (int j = 0; j < penalties.size(); j++) {
		Penalty pen = penalties.get(j);
%>						<tr id="penalty">
							<td colspan="4"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="<%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" name="penalty1" id="penalty1" class="score" value="<%=pen.getPenaltyPlayer1PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" name="penalty2" id="penalty2" class="score" value="<%=pen.getPenaltyPlayer2PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" name="penalty3" id="penalty3" class="score" value="<%=pen.getPenaltyPlayer3PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" name="penalty4" id="penalty4" class="score" value="<%=pen.getPenaltyPlayer4PrettyPrint() %>"/></td>
						</tr>
<%
	}
}
%>					</tbody></table></div></td></tr></tbody>
				</table>
				<br/>				
				<input type="button" class="addPenaltyButton" value="ajouter une pénalité" />
				<br/><br/>
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
				<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');" />
			</form>
		</div>
	</body>
</html>