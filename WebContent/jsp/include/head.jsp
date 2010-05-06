<%@
page import="org.mahjong.matoso.constant.BundleCst"
%><div id="head" class="noprint">
	<h1>MaToSo - MAhjong TOurnament SOftware</h1>
	<div id="nav">
		<form action="<%=request.getContextPath()%>/servlet/ChangeLanguage" method="post">
			<input type="hidden" name="language" value="fr" id="language" />
			<input type="image" src="<%=request.getContextPath()%>/img/flag/gb.gif" alt="English" title="English" onclick="language.value='en'" />
			<input type="image" src="<%=request.getContextPath()%>/img/flag/fr.gif" alt="Français" title="Français" />
		</form>
		<br/>
		<a href="<%=request.getContextPath()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_HOME)%></a>
	</div>
	<div id="powered">Project MaToSo v1<br/>2010 by Lionel, Nicolas and Cl&eacute;ment</div>
</div>