$(function() {
	$(".matoso-tabs").tabs();
	$(".matoso-menu").accordion({autoHeight:false,collapsible:true});
	$(".matoso-button,input[type='submit'],input[type='checkbox'],input[type='button']").button();
	$(".matoso-radio").buttonset();
	$("tr").addClass("ui-state-default ui-corner-all");
	$("input[type='text'],select").addClass("ui-corner-all");
	$("th,#head").addClass("ui-widget-header");
});
function checkInputs() {
	elt = document.getElementsByName("tournament-name");
	if (elt && elt.length == 1) {
		if (elt[0] && elt[0].value == "") {
			alert("<%=BundleCst.BUNDLE.getString(BundleCst.ERROR_NAME_MISSING)%>");
			return false;
		}
	}
}
function check() {
	var pwd = prompt("Confirmer par le mot de passe ?");
	if (pwd == "adminMatoso") {
		return true;
	} else {
		alert("Mot de passe incorrect");
		return false;
	}
}