/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A bean displaying a game data.
 *
 * @author ctrung
 * @date 15 août 2009
 */
public class DisplayTableGame {

	private Integer handValue;
	private boolean selfpick;
	
	private boolean player1Win;
	private boolean player1Lose;
	private Integer player1Score;
	
	private boolean player2Win;
	private boolean player2Lose;
	private Integer player2Score;
	
	private boolean player3Win;
	private boolean player3Lose;
	private Integer player3Score;
	
	private boolean player4Win;
	private boolean player4Lose;
	private Integer player4Score;

	/**
	 * Empty constructor.
	 */
	public DisplayTableGame() {
		super();
	}

	/**
	 * @param player1ScoreInt
	 * @param player2ScoreInt
	 * @param player3ScoreInt
	 * @param player4ScoreInt
	 */
	public DisplayTableGame(Integer player1ScoreInt, Integer player2ScoreInt,
			Integer player3ScoreInt, Integer player4ScoreInt) {
		
		Integer maxScore = null;
		Integer minScore = null;
		List<Integer> scores = new ArrayList<Integer>();

		// the scores
		if(player1ScoreInt!=null) this.player1Score = player1ScoreInt;
		if(player2ScoreInt!=null) this.player2Score = player2ScoreInt;
		if(player3ScoreInt!=null) this.player3Score = player3ScoreInt;
		if(player4ScoreInt!=null) this.player4Score = player4ScoreInt;
		
		/*
		 *  Exception case : draw game
		 */
		if(this.player1Score.intValue() == 0) {
			this.handValue = 0;
			return;
		}
		
		/*
		 * Normal cases
		 */
		
		// the winner 
		scores.add(player1ScoreInt);
		scores.add(player2ScoreInt);
		scores.add(player3ScoreInt);
		scores.add(player4ScoreInt);
		Collections.sort(scores);
		maxScore = scores.get(3);

		if(maxScore == player1ScoreInt) this.player1Win = true;
		else if(maxScore == player2ScoreInt) this.player2Win = true;
		else if(maxScore == player3ScoreInt) this.player3Win = true;
		else if(maxScore == player4ScoreInt) this.player4Win = true;
		
		// selfpick ?
		minScore = scores.get(0);
		if(minScore.equals(scores.get(1))) this.selfpick = true;
		
		// the loser
		if(!this.selfpick) {
			if(minScore == player1ScoreInt) this.player1Lose = true;
			else if(minScore == player2ScoreInt) this.player2Lose = true;
			else if(minScore == player3ScoreInt) this.player3Lose = true;
			else if(minScore == player4ScoreInt) this.player4Lose = true;
		}
		
		// the hand value
		this.handValue = -minScore - 8;
	}
	
	/**
	 * @return the handValue
	 */
	public Integer getHandValue() {
		return handValue;
	}
	/**
	 * @param handValue the handValue to set
	 */
	public void setHandValue(Integer handValue) {
		this.handValue = handValue;
	}
	/**
	 * @return the selfpick
	 */
	public boolean isSelfpick() {
		return selfpick;
	}
	/**
	 * @param selfpick the selfpick to set
	 */
	public void setSelfpick(boolean selfpick) {
		this.selfpick = selfpick;
	}
	/**
	 * @return the player1Win
	 */
	public boolean isPlayer1Win() {
		return player1Win;
	}
	/**
	 * @param player1Win the player1Win to set
	 */
	public void setPlayer1Win(boolean player1Win) {
		this.player1Win = player1Win;
	}
	/**
	 * @return the player1Score
	 */
	public Integer getPlayer1Score() {
		return player1Score;
	}
	/**
	 * @param player1Score the player1Score to set
	 */
	public void setPlayer1Score(Integer player1Score) {
		this.player1Score = player1Score;
	}
	/**
	 * @return the player2Win
	 */
	public boolean isPlayer2Win() {
		return player2Win;
	}
	/**
	 * @param player2Win the player2Win to set
	 */
	public void setPlayer2Win(boolean player2Win) {
		this.player2Win = player2Win;
	}
	/**
	 * @return the player2Score
	 */
	public Integer getPlayer2Score() {
		return player2Score;
	}
	/**
	 * @param player2Score the player2Score to set
	 */
	public void setPlayer2Score(Integer player2Score) {
		this.player2Score = player2Score;
	}
	/**
	 * @return the player3Win
	 */
	public boolean isPlayer3Win() {
		return player3Win;
	}
	/**
	 * @param player3Win the player3Win to set
	 */
	public void setPlayer3Win(boolean player3Win) {
		this.player3Win = player3Win;
	}
	/**
	 * @return the player3Score
	 */
	public Integer getPlayer3Score() {
		return player3Score;
	}
	/**
	 * @param player3Score the player3Score to set
	 */
	public void setPlayer3Score(Integer player3Score) {
		this.player3Score = player3Score;
	}
	/**
	 * @return the player4Win
	 */
	public boolean isPlayer4Win() {
		return player4Win;
	}
	/**
	 * @param player4Win the player4Win to set
	 */
	public void setPlayer4Win(boolean player4Win) {
		this.player4Win = player4Win;
	}
	/**
	 * @return the player4Score
	 */
	public Integer getPlayer4Score() {
		return player4Score;
	}
	/**
	 * @param player4Score the player4Score to set
	 */
	public void setPlayer4Score(Integer player4Score) {
		this.player4Score = player4Score;
	}
	
	/**
	 * @return the player1Lose
	 */
	public boolean isPlayer1Lose() {
		return player1Lose;
	}

	/**
	 * @param player1Lose the player1Lose to set
	 */
	public void setPlayer1Lose(boolean player1Lose) {
		this.player1Lose = player1Lose;
	}

	/**
	 * @return the player2Lose
	 */
	public boolean isPlayer2Lose() {
		return player2Lose;
	}

	/**
	 * @param player2Lose the player2Lose to set
	 */
	public void setPlayer2Lose(boolean player2Lose) {
		this.player2Lose = player2Lose;
	}

	/**
	 * @return the player3Lose
	 */
	public boolean isPlayer3Lose() {
		return player3Lose;
	}

	/**
	 * @param player3Lose the player3Lose to set
	 */
	public void setPlayer3Lose(boolean player3Lose) {
		this.player3Lose = player3Lose;
	}

	/**
	 * @return the player4Lose
	 */
	public boolean isPlayer4Lose() {
		return player4Lose;
	}

	/**
	 * @param player4Lose the player4Lose to set
	 */
	public void setPlayer4Lose(boolean player4Lose) {
		this.player4Lose = player4Lose;
	}

	/*
	 * Other methods
	 */
	
	/**
	 * @return the correct value of the "checked" attribute depending of the selfpick value.
	 */
	public String getSelfpickCheckedAttribute() {
		if (handValue==null || !selfpick) return "";
		return "checked=\"checked\"";
	}
	
	/**
	 * @param noPlayer The player number
	 * 
	 * @return the correct value of the "checked" attribute depending of the win of a player.<br>
	 * Is always the opposite of the value returned by the method getPlayerLoseCheckedAttribute(int).
	 */
	public String getPlayerWinCheckedAttribute(int noPlayer) {
		
		boolean thePlayerWin 	= false;
		Integer thePlayerScore 	= null;
		
		switch(noPlayer){
			case 1 : 
				thePlayerWin = player1Win;
				thePlayerScore = player1Score;
				break;
			case 2 : 
				thePlayerWin = player2Win;
				thePlayerScore = player2Score;
				break;
			case 3 :
				thePlayerWin = player3Win;
				thePlayerScore = player3Score;
				break;
			case 4 :
				thePlayerWin = player4Win;
				thePlayerScore = player4Score;
				break;
			default :
				return "";
		}
		
		if (thePlayerScore != null && thePlayerWin) return "checked=\"checked\"";
		return "";
	}
	
	/**
	 * @param noPlayer The player number
	 * 
	 * @return the correct value of the "checked" attribute depending of the lose of a player. <br>
	 * Is always the opposite of the value returned by the method getPlayerWinCheckedAttribute(int).
	 */
	public String getPlayerLoseCheckedAttribute(int noPlayer) {
		boolean thePlayerLose 	= false;
		Integer thePlayerScore 	= null;
		
		switch(noPlayer){
			case 1 : 
				thePlayerLose = player1Lose;
				thePlayerScore = player1Score;
				break;
			case 2 : 
				thePlayerLose = player2Lose;
				thePlayerScore = player2Score;
				break;
			case 3 :
				thePlayerLose = player3Lose;
				thePlayerScore = player3Score;
				break;
			case 4 :
				thePlayerLose = player4Lose;
				thePlayerScore = player4Score;
				break;
			default :
				return "";
		}
		
		if (thePlayerScore != null && thePlayerLose) return "checked=\"checked\"";
		return "";
	}
	
	/**
	 * @param noPlayer The player number
	 * 
	 * @return the style attribute if the player has won.
	 */
	public String hasWonStyle(int noPlayer) {
		
		boolean thePlayerWin 	= false;
		Integer thePlayerScore 	= null;
		
		switch(noPlayer){
			case 1 : 
				thePlayerWin = player1Win;
				thePlayerScore = player1Score;
				break;
			case 2 : 
				thePlayerWin = player2Win;
				thePlayerScore = player2Score;
				break;
			case 3 :
				thePlayerWin = player3Win;
				thePlayerScore = player3Score;
				break;
			case 4 :
				thePlayerWin = player4Win;
				thePlayerScore = player4Score;
				break;
			default :
				return "";
		}
		
		if (thePlayerScore != null && thePlayerWin) return "style=\"color:red; font-weight:bold;\"";
		return "";
	}	
	
	/**
	 * @param noPlayer The player number
	 * 
	 * @return the style attribute if the player has won.
	 */
	public String hasLoseStyle(int noPlayer) {
		
		boolean thePlayerLose 	= false;
		Integer thePlayerScore 	= null;
		
		switch(noPlayer){
			case 1 : 
				thePlayerLose = player1Lose;
				thePlayerScore = player1Score;
				break;
			case 2 : 
				thePlayerLose = player2Lose;
				thePlayerScore = player2Score;
				break;
			case 3 :
				thePlayerLose = player3Lose;
				thePlayerScore = player3Score;
				break;
			case 4 :
				thePlayerLose = player4Lose;
				thePlayerScore = player4Score;
				break;
			default :
				return "";
		}
		
		if (thePlayerScore != null && thePlayerLose) return "style=\"color:blue; font-weight:bold;\"";
		return "";
	}		
	
	/**
	 * @return the pretty print form of the handValue
	 */
	public String getHandValuePrettyPrint() {
		if(handValue==null) return "";
		return handValue.toString();
	}
	
	/**
	 * @return the pretty print form of the player1Score
	 */
	public String getPlayer1ScorePrettyPrint() {
		if(player1Score==null) return "";
		return player1Score.toString();
	}
	
	/**
	 * @return the pretty print form of the player2Score
	 */
	public String getPlayer2ScorePrettyPrint() {
		if(player2Score==null) return "";
		return player2Score.toString();
	}
	
	/**
	 * @return the pretty print form of the player3Score
	 */
	public String getPlayer3ScorePrettyPrint() {
		if(player3Score==null) return "";
		return player3Score.toString();
	}
	
	/**
	 * @return the pretty print form of the player4Score
	 */
	public String getPlayer4ScorePrettyPrint() {
		if(player4Score==null) return "";
		return player4Score.toString();
	}
	
}
