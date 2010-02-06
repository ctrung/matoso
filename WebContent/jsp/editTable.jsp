<%@page
	import="
		java.util.List,
		java.util.ResourceBundle,
		org.mahjong.matoso.constant.BundleCst,
		org.mahjong.matoso.constant.RequestCst,
		org.mahjong.matoso.constant.SessionCst,
		org.mahjong.matoso.constant.ServletCst,
		org.mahjong.matoso.bean.*,
		org.mahjong.matoso.display.DisplayTableGame,
		org.mahjong.matoso.util.message.MatosoMessages"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>

<%-- 
	The input scores/points form for a table 
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/theme.css" />
		<link rel="shortcut icon"  href="<%=request.getContextPath()%>/img/favicon.ico" />
	</head>

	<body>
	
		<!-- header -->
		<%@include file="include/head.jsp"%>	
	
		<script type="text/javascript">

			function cancelPlayerRadioInput(id, labelId) {
				document.getElementById(id).checked = false;
				document.getElementById(labelId).style.color = 'black';
				document.getElementById(labelId).style.fontWeight = 'normal';
			}
		
			function actionWinnerRadioInput(gameNo, input, pos){
				cancelPlayerRadioInput(gameNo+'_winner1', gameNo+'_lbl-winner1');
				cancelPlayerRadioInput(gameNo+'_winner2', gameNo+'_lbl-winner2');
				cancelPlayerRadioInput(gameNo+'_winner3', gameNo+'_lbl-winner3');
				cancelPlayerRadioInput(gameNo+'_winner4', gameNo+'_lbl-winner4');
				input.checked = true;
				document.getElementById(gameNo+'_lbl-winner'+pos).style.color = 'red';
				document.getElementById(gameNo+'_lbl-winner'+pos).style.fontWeight = 'bold';
				return true;
			} 
	
			function actionLoserRadioInput(gameNo, input, pos){
				cancelPlayerRadioInput(gameNo+'_loser1', gameNo+'_lbl-loser1');
				cancelPlayerRadioInput(gameNo+'_loser2', gameNo+'_lbl-loser2');
				cancelPlayerRadioInput(gameNo+'_loser3', gameNo+'_lbl-loser3');
				cancelPlayerRadioInput(gameNo+'_loser4', gameNo+'_lbl-loser4');
				input.checked = true;
				document.getElementById(gameNo+'_lbl-loser'+pos).style.color = 'blue';
				document.getElementById(gameNo+'_lbl-loser'+pos).style.fontWeight = 'bold';
				return true;
			} 

			function actionSelfpickCheckbox(gameNo){
				cancelPlayerRadioInput(gameNo+'_loser1', gameNo+'_lbl-loser1');
				cancelPlayerRadioInput(gameNo+'_loser2', gameNo+'_lbl-loser1');
				cancelPlayerRadioInput(gameNo+'_loser3', gameNo+'_lbl-loser1');
				cancelPlayerRadioInput(gameNo+'_loser4', gameNo+'_lbl-loser1');
				return true;
			}

			function actionAutoCalculate(input){
				document.getElementById('score1').disabled = input.checked;
				document.getElementById('score2').disabled = input.checked;
				document.getElementById('score3').disabled = input.checked;
				document.getElementById('score4').disabled = input.checked;
				return true;
			}

			function blankAllFields(){
				
				var yes = confirm('<%=BundleCst.BUNDLE.getString("general.reset.confirm")%>');
				if(!yes) return false;
				
				var i = 0;
				
				// table scores, points, and penality
				for(i=1; i<=4; i++) {
					document.getElementById('score'+i).value = '';
					document.getElementById('points'+i).value = '';
					document.getElementById('penalty'+i).value = '';
				}
					
				// 16 games max
				for(i=0; i<16; i++) {
					document.getElementById(i+'_handValue').value = '';
					document.getElementById(i+'_selfpick').checked = false;

					cancelPlayerRadioInput(i+'_winner1', i+'_lbl-winner1');
					cancelPlayerRadioInput(i+'_loser1', i+'_lbl-loser1');
					document.getElementById(i+'_scr1').value = '';
					
					cancelPlayerRadioInput(i+'_winner2', i+'_lbl-winner2');
					cancelPlayerRadioInput(i+'_loser2', i+'_lbl-loser2');
					document.getElementById(i+'_scr2').value = '';
					
					cancelPlayerRadioInput(i+'_winner3', i+'_lbl-winner3');
					cancelPlayerRadioInput(i+'_loser3', i+'_lbl-loser3');
					document.getElementById(i+'_scr3').value = '';
					
					cancelPlayerRadioInput(i+'_winner4', i+'_lbl-winner4');
					cancelPlayerRadioInput(i+'_loser4', i+'_lbl-loser4');
					document.getElementById(i+'_scr4').value = '';
				}

				return true;
			}
		</script>

		<%
			String name = (String) request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT);
			Table table = (Table) request.getAttribute(RequestCst.REQ_ATTR_TABLE);
		%>
		
		<h2><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_TITLE)%></h2>

		<div id="editTable">
	
			<a href="<%=request.getContextPath()+"/"+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET+"?"+
					RequestCst.REQ_PARAM_TOURNAMENT_NAME+"="+name%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
	
	
			<div id="tableInfos">
				<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_TABLE)%> : <%= table.getName() %>
				<br/>
				<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_ROUND)%> : <%=table.getRound() %>
			</div>
			
			<%-- Messages --%>
			<%
				MatosoMessages mm = (MatosoMessages)request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
				if(MatosoMessages.isNotEmpty(mm)) { %>
				<ul class="matosoMesgs">
					<%for(int i=0; i<mm.getMesgs().size(); i++) { %>	
						<li class="<%="s"+mm.getMesgs().get(i).getSeverity()%>">
							<%=mm.getMesgs().get(i).getMesg()%></li>
					<%} %>
				</ul>
			<%} %>
			
			
			<form action="<%=request.getContextPath()%>/servlet/SaveTableGamesAndScores" method="post">
				
				<input type="hidden" name="<%=RequestCst.REQ_PARAM_TABLE_ID%>" value="<%=table.getId()%>" />
				
				<div class="buttonPane">
					<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
					<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields();" />
				</div>
				
				<table>
					<thead>
						
						<!-- header labels -->
						<tr>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WIND)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_GAME_NUMBER)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_HAND_VALUE)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PLAYER1)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PLAYER2)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PLAYER3)%></td>
							<td><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PLAYER4)%></td>
						</tr>
						
						<!-- names and licence no -->
						
						<tr class="separate, labels">
							<td colspan="3"></td>
							<td>
								<%=table.getPlayer1().getPrettyPrintName() %>
								<br/>
								(EMA : <%=table.getPlayer1().getEma()%>)
							</td>
							<td>
								<%=table.getPlayer2().getPrettyPrintName() %>
								<br/>
								(EMA : <%=table.getPlayer2().getEma()%>)
							</td>
							<td>
								<%=table.getPlayer3().getPrettyPrintName() %>
								<br/>
								(EMA : <%=table.getPlayer3().getEma()%>)
							</td>
							<td>
								<%=table.getPlayer4().getPrettyPrintName() %>
								<br/>
								(EMA : <%=table.getPlayer4().getEma()%>)
							</td>
						</tr>
					</thead>
					
					<tbody>
					
						<!-- auto calculate ? -->
						<% 
							GameResult gr = (GameResult)request.getAttribute(RequestCst.REQ_ATTR_TABLE_RESULT); 
							String checkedAttr = "";
							if(gr.isAutoCalculate()) checkedAttr = "checked=\"checked\"";
						%>
						<tr id="auto">
							<td colspan="7">
								<input type="checkbox" name="autoCalculate" value="yes" id="autoCalculate" 
									<%=checkedAttr%> onclick="return actionAutoCalculate(this);"/> 
									<label for="autoCalculate"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE)%></label>
								<p><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_AUTO_CALCULATE_DESC)%></p>
							</td>
						</tr>
						
						<!-- total scores -->
						<tr>
							<td colspan="3"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SCORE)%></td>
							<td><input type="text" name="score1" id="score1" class="score" value="<%=gr.getScorePlayer1PrettyPrint() %>"/></td>
							<td><input type="text" name="score2" id="score2" class="score" value="<%=gr.getScorePlayer2PrettyPrint() %>"/></td>
							<td><input type="text" name="score3" id="score3" class="score" value="<%=gr.getScorePlayer3PrettyPrint() %>"/></td>
							<td><input type="text" name="score4" id="score4" class="score" value="<%=gr.getScorePlayer4PrettyPrint() %>"/></td>
						</tr>
						
						<!-- points -->
						<tr>
							<td colspan="3"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_POINTS)%></td>
							<td><input type="text" name="points1" id="points1" class="score" value="<%=gr.getPointsPlayer1PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points2" id="points2" class="score" value="<%=gr.getPointsPlayer2PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points3" id="points3" class="score" value="<%=gr.getPointsPlayer3PrettyPrint() %>" disabled="disabled"/></td>
							<td><input type="text" name="points4" id="points4" class="score" value="<%=gr.getPointsPlayer4PrettyPrint() %>" disabled="disabled"/></td>
						</tr>					
						
						<!-- scoring inputs -->
						
						<%
							List<DisplayTableGame> dtgs = (List<DisplayTableGame>)request.getAttribute(RequestCst.REQ_ATTR_TABLE_DISPLAY_GAMES);
							DisplayTableGame dtg = null;
							int cssWindColorNo = 0;
							String wind;
							for(int i=0; i<dtgs.size(); i++){
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
						%>
						
							<tr class="<%="game color"+cssWindColorNo %>">
								<%if (i % 4 == 0) {%>
								<td rowspan="4"><%=wind%></td>
								<%}%>
								<td><%=Integer.toString(i+1)%></td>
								<td>
									<input type="text" name="<%=i+"_handValue"%>" id="<%=i+"_handValue"%>" value="<%=dtg.getHandValuePrettyPrint()%>" class="handValue"/>
									<br/>
									<input type="checkbox" name="<%=i+"_selfpick"%>" value="yes" id="<%=i+"_selfpick"%>" <%=dtg.getSelfpickCheckedAttribute() %> onclick="return actionSelfpickCheckbox(<%=i%>);"/> <label for="<%=i+"_selfpick"%>"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_SELFPICK)%></label>
								</td>
								<td title="<%=table.getPlayer1().getPrettyPrintName() %>">
									<input type="radio" name="<%=i+"_winOrLose1"%>" value="win" id="<%=i+"_winner1"%>" <%=dtg.getPlayerWinCheckedAttribute(1) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 1);"/> <label for="<%=i+"_winner1"%>" id="<%=i+"_lbl-winner1"%>" <%=dtg.hasWonStyle(1) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
									<input type="radio" name="<%=i+"_winOrLose1"%>" value="lose" id="<%=i+"_loser1"%>" <%=dtg.getPlayerLoseCheckedAttribute(1) %> onclick="return actionLoserRadioInput(<%=i%>, this, 1);"/> <label for="<%=i+"_loser1"%>" id="<%=i+"_lbl-loser1"%>" <%=dtg.hasLoseStyle(1) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
									<input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer1ScorePrettyPrint()%>" id="<%=i+"_scr1"%>"/>
								</td>
								<td title="<%=table.getPlayer2().getPrettyPrintName() %>">
									<input type="radio" name="<%=i+"_winOrLose2"%>" value="win" id="<%=i+"_winner2"%>" <%=dtg.getPlayerWinCheckedAttribute(2) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 2);"/> <label for="<%=i+"_winner2"%>" id="<%=i+"_lbl-winner2"%>" <%=dtg.hasWonStyle(2) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
									<input type="radio" name="<%=i+"_winOrLose2"%>" value="lose" id="<%=i+"_loser2"%>" <%=dtg.getPlayerLoseCheckedAttribute(2) %> onclick="return actionLoserRadioInput(<%=i%>, this, 2);"/> <label for="<%=i+"_loser2"%>" id="<%=i+"_lbl-loser2"%>" <%=dtg.hasLoseStyle(2) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
									<input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer2ScorePrettyPrint()%>" id="<%=i+"_scr2"%>"/>
								</td>
								<td title="<%=table.getPlayer3().getPrettyPrintName() %>">
									<input type="radio" name="<%=i+"_winOrLose3"%>" value="win" id="<%=i+"_winner3"%>" <%=dtg.getPlayerWinCheckedAttribute(3) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 3);"/> <label for="<%=i+"_winner3"%>" id="<%=i+"_lbl-winner3"%>" <%=dtg.hasWonStyle(3) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
									<input type="radio" name="<%=i+"_winOrLose3"%>" value="lose" id="<%=i+"_loser3"%>" <%=dtg.getPlayerLoseCheckedAttribute(3) %> onclick="return actionLoserRadioInput(<%=i%>, this, 3);"/> <label for="<%=i+"_loser3"%>" id="<%=i+"_lbl-loser3"%>" <%=dtg.hasLoseStyle(3) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
									<input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer3ScorePrettyPrint()%>" id="<%=i+"_scr3"%>"/>
								</td>
								<td title="<%=table.getPlayer4().getPrettyPrintName() %>">
									<input type="radio" name="<%=i+"_winOrLose4"%>" value="win" id="<%=i+"_winner4"%>" <%=dtg.getPlayerWinCheckedAttribute(4) %> onclick="return actionWinnerRadioInput(<%=i%>, this, 4);"/> <label for="<%=i+"_winner4"%>" id="<%=i+"_lbl-winner4"%>" <%=dtg.hasWonStyle(4) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_WINNER)%></label><br/>
									<input type="radio" name="<%=i+"_winOrLose4"%>" value="lose" id="<%=i+"_loser4"%>" <%=dtg.getPlayerLoseCheckedAttribute(4) %> onclick="return actionLoserRadioInput(<%=i%>, this, 4);"/> <label for="<%=i+"_loser4"%>" id="<%=i+"_lbl-loser4"%>" <%=dtg.hasLoseStyle(4) %>><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_LOSER)%></label>&nbsp;
									<input type="text" class="score" disabled="disabled" value="<%=dtg.getPlayer4ScorePrettyPrint()%>" id="<%=i+"_scr4"%>"/>
								</td>						
							</tr>
							
						<%}%>
						
						<% Penalty pen = (Penalty)request.getAttribute(RequestCst.REQ_ATTR_TABLE_PENALTY); %>
						
						<tr id="penalty">
							<td colspan="3"><%=BundleCst.BUNDLE.getString(BundleCst.TABLE_PENALTY)%></td>
							<td title="<%=table.getPlayer1().getPrettyPrintName() %>"><input type="text" name="penalty1" id="penalty1" class="score" value="<%=pen.getPenaltyPlayer1PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer2().getPrettyPrintName() %>"><input type="text" name="penalty2" id="penalty2" class="score" value="<%=pen.getPenaltyPlayer2PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer3().getPrettyPrintName() %>"><input type="text" name="penalty3" id="penalty3" class="score" value="<%=pen.getPenaltyPlayer3PrettyPrint() %>"/></td>
							<td title="<%=table.getPlayer4().getPrettyPrintName() %>"><input type="text" name="penalty4" id="penalty4" class="score" value="<%=pen.getPenaltyPlayer4PrettyPrint() %>"/></td>
						</tr>
						
					</tbody>
				</table>
				<br/>
				<input type="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SAVE)%>" />
				<input type="button" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_RESET)%>" onclick="return blankAllFields();" />
			</form>
			
		</div>
	</body>
</html>