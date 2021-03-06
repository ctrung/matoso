<%@page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@page import="org.mahjong.matoso.constant.SessionCst, 
org.mahjong.matoso.constant.RequestCst, 
org.mahjong.matoso.constant.BundleCst, 
org.mahjong.matoso.constant.ServletCst,
org.mahjong.matoso.util.message.MatosoMessages"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MaToSo - <%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_MASS_IMPORT)%></title>
		<%@include file="../include/cssAndScripts.jsp" %>
	</head>
	<body>
		<%@include file="../include/head.jsp"%>
		<div id="import">
			<h2><%=BundleCst.BUNDLE.getString(BundleCst.PLAYER_MASS_IMPORT)%></h2>
			<a href="<%=request.getContextPath()+ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_BACK)%></a>
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
			<form action="<%="/matoso"+ServletCst.REDIRECT_TO_IMPORT_PLAYER_SERVLET%>" enctype="multipart/form-data" method="POST">
				<script type="text/javascript">
					function updateField(type) {
						var isCsvType = type=='csv';
						var isXlsType = type=='xls';
						
						if(isCsvType) {
							document.getElementById('csvField').style.display = 'block';
							document.getElementById('xlsField').style.display = 'none';
						} else if(isXlsType) {
							document.getElementById('xlsField').style.display = 'block';
							document.getElementById('csvField').style.display = 'none';
						} else {
							document.getElementById('xlsField').style.display = 'none';
							document.getElementById('csvField').style.display = 'none';
						}
					}
				</script>
				
				
				<div>
				
					<label class="label"><%=BundleCst.BUNDLE.getString("player.mass.import.type.label") %></label>
					
					<div id="choice">
						<input type="radio" id="csvImportRadioB" name="importType" value="CSV" onclick="updateField('csv');"/>
						<label for="csvImportRadioB">
							<%= BundleCst.BUNDLE.getString("player.mass.import.csv.type") %>
						</label>
						
						<div id="csvField">
							<input type="file" accept="text/csv" name="csvfile" />
							
							<div class="info">
								<%=BundleCst.BUNDLE.getString("player.mass.import.csv.info")%>
								<br/><br/>
								<a href="<%=request.getContextPath()+"/csv/players-sample.csv"%>">
									<%=BundleCst.BUNDLE.getString("player.mass.import.csv.link.sample")%>
								</a>
							</div>
						</div>
						
						<br/>
						
						<!-- Experimental, not to be used now -->
						<input type="radio" id="xlsImportRadioB" name="importType" value="XLS" onclick="updateField('xls');" disabled="disabled"/>
						<label for="xlsImportRadioB">
							<%= BundleCst.BUNDLE.getString("player.mass.import.xls.type") %> (en cours d'implémentation / still in development)
						</label>
						
						<div id="xlsField">
							<input type="file" accept="text/xls" name="xlsfile" disabled="disabled"/>
						</div>
					</div>
				</div>			
				
				<hr/>
				
				<div>
					<label class="label" for="<%= RequestCst.REQ_PARAM_ROUND %>">
						<%= BundleCst.BUNDLE.getString(BundleCst.ROUND_NUMBER) %>
					</label>
					<input type="text" name="<%= RequestCst.REQ_PARAM_ROUND %>" />
				</div>
				
				<hr/>
				
				<input type="submit" name="submit" value="<%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_SUBMIT)%>" />
			</form>
			
		</div>
		
		<script type="text/javascript">
			updateField('none');
		</script>
		
	</body>
</html>