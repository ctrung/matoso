function addPenaltyButtonClick() {
	$('div#editTable table tbody>tr:last').clone(true).insertAfter('div#editTable table tbody>tr:last'); 
}
function documentReady() {
	// add penalty
	$('.addPenaltyButton').click(addPenaltyButtonClick);
}

$(document).ready(documentReady);

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

function blankAllFields(msg){
	
	var yes = confirm(msg);
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

		cancelPlayerRadioInput(i+'_winner1');
		cancelPlayerRadioInput(i+'_loser1');
		document.getElementById(i+'_scr1').value = '';
		
		cancelPlayerRadioInput(i+'_winner2');
		cancelPlayerRadioInput(i+'_loser2');
		document.getElementById(i+'_scr2').value = '';
		
		cancelPlayerRadioInput(i+'_winner3');
		cancelPlayerRadioInput(i+'_loser3');
		document.getElementById(i+'_scr3').value = '';
		
		cancelPlayerRadioInput(i+'_winner4');
		cancelPlayerRadioInput(i+'_loser4');
		document.getElementById(i+'_scr4').value = '';
	}

	return true;
}