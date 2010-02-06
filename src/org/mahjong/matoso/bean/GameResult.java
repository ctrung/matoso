/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.bean;

import org.mahjong.matoso.util.NumberUtils;

/**
 * This bean defines the final scores and points at one table for one round.<br>
 *
 * @author ctrung
 * @date 15 août 2009
 */
public class GameResult {

	private Integer id;
	
	private boolean autoCalculate;
	
	private Integer scorePlayer1; 
	private Double pointsPlayer1;
	
	private Integer scorePlayer2; 
	private Double pointsPlayer2;
	
	private Integer scorePlayer3; 
	private Double pointsPlayer3;
	
	private Integer scorePlayer4; 
	private Double pointsPlayer4;
	
	/**
	 * Empty constructor.
	 */
	public GameResult() {
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the autoCalculate
	 */
	public boolean isAutoCalculate() {
		return autoCalculate;
	}

	/**
	 * @param autoCalculate the autoCalculate to set
	 */
	public void setAutoCalculate(boolean autoCalculate) {
		this.autoCalculate = autoCalculate;
	}

	/**
	 * @return the scorePlayer1
	 */
	public Integer getScorePlayer1() {
		return scorePlayer1;
	}

	/**
	 * @param scorePlayer1 the scorePlayer1 to set
	 */
	public void setScorePlayer1(Integer scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	/**
	 * @return the pointsPlayer1
	 */
	public Double getPointsPlayer1() {
		return pointsPlayer1;
	}

	/**
	 * @param pointsPlayer1 the pointsPlayer1 to set
	 */
	public void setPointsPlayer1(Double pointsPlayer1) {
		this.pointsPlayer1 = pointsPlayer1;
	}

	/**
	 * @return the scorePlayer2
	 */
	public Integer getScorePlayer2() {
		return scorePlayer2;
	}

	/**
	 * @param scorePlayer2 the scorePlayer2 to set
	 */
	public void setScorePlayer2(Integer scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}

	/**
	 * @return the pointsPlayer2
	 */
	public Double getPointsPlayer2() {
		return pointsPlayer2;
	}

	/**
	 * @param pointsPlayer2 the pointsPlayer2 to set
	 */
	public void setPointsPlayer2(Double pointsPlayer2) {
		this.pointsPlayer2 = pointsPlayer2;
	}

	/**
	 * @return the scorePlayer3
	 */
	public Integer getScorePlayer3() {
		return scorePlayer3;
	}

	/**
	 * @param scorePlayer3 the scorePlayer3 to set
	 */
	public void setScorePlayer3(Integer scorePlayer3) {
		this.scorePlayer3 = scorePlayer3;
	}

	/**
	 * @return the pointsPlayer3
	 */
	public Double getPointsPlayer3() {
		return pointsPlayer3;
	}

	/**
	 * @param pointsPlayer3 the pointsPlayer3 to set
	 */
	public void setPointsPlayer3(Double pointsPlayer3) {
		this.pointsPlayer3 = pointsPlayer3;
	}

	/**
	 * @return the scorePlayer4
	 */
	public Integer getScorePlayer4() {
		return scorePlayer4;
	}

	/**
	 * @param scorePlayer4 the scorePlayer4 to set
	 */
	public void setScorePlayer4(Integer scorePlayer4) {
		this.scorePlayer4 = scorePlayer4;
	}

	/**
	 * @return the pointsPlayer4
	 */
	public Double getPointsPlayer4() {
		return pointsPlayer4;
	}

	/**
	 * @param pointsPlayer4 the pointsPlayer4 to set
	 */
	public void setPointsPlayer4(Double pointsPlayer4) {
		this.pointsPlayer4 = pointsPlayer4;
	}
	
	/*
	 * other methods
	 */
	
	/**
	 * @return the pretty print form of the score of player 1
	 */
	public String getScorePlayer1PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(scorePlayer1);
	}
	
	/**
	 * @return the pretty print form of the score of player 2
	 */
	public String getScorePlayer2PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(scorePlayer2);
	}
	
	/**
	 * @return the pretty print form of the score of player 3
	 */
	public String getScorePlayer3PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(scorePlayer3);
	}
	
	/**
	 * @return the pretty print form of the score of player 4
	 */
	public String getScorePlayer4PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(scorePlayer4);
	}	
	
	/**
	 * @return the pretty print form of the points of player 1
	 */
	public String getPointsPlayer1PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(pointsPlayer1);
	}
	
	/**
	 * @return the pretty print form of the points of player 2
	 */
	public String getPointsPlayer2PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(pointsPlayer2);
	}
	
	/**
	 * @return the pretty print form of the points of player 3
	 */
	public String getPointsPlayer3PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(pointsPlayer3);
	}
	
	/**
	 * @return the pretty print form of the points of player 4
	 */
	public String getPointsPlayer4PrettyPrint(){
		return NumberUtils.getPrettyPrintForm(pointsPlayer4);
	}	
}
