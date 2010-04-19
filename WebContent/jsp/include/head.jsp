<%@
page import="org.mahjong.matoso.constant.BundleCst"
%><div id="head" class="noprint">
	<a href="http://mahjong-europe.org/"><img id="emalogo" src="/matoso/img/logo_ema_small.png"/></a>
	<h1>MaToSo - MAhjong TOurnament SOftware</h1>
	<div id="nav">
		<form action="<%=request.getContextPath()%>/servlet/ChangeLanguage" method="post">
			<input type="hidden" name="language" value="fr" id="language" />
			<input type="image" src="<%=request.getContextPath()%>/img/en.jpg" alt="English" title="English" onclick="language.value='en'" />
			<input type="image" src="<%=request.getContextPath()%>/img/fr.gif" alt="Français" title="Français" />
		</form>
		<br/>
		<a href="<%=request.getContextPath()%>"><%=BundleCst.BUNDLE.getString(BundleCst.GENERAL_HOME)%></a>
	</div>
</div>