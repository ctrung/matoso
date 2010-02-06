package org.mahjong.matoso.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean for a game :<br/>
 * <ul>
 * 	<li>Number of the game</li>
 * 	<li>A map of Player -> his result</li>
 * </ul>
 */
public class Game {

	/**
	 * Surrogate key, unique.
	 */
	private Integer id;
	
	/**
	 * The table where this game was played.
	 */
	private Table table;
	
	/**
	 * The game number.
	 */
	private Integer gameNumber;

	/**
	 * Result of player 1
	 */
	private Integer scorePlayer1;
	
	/**
	 * Result of player 2
	 */
	private Integer scorePlayer2;
	
	/**
	 * Result of player 3
	 */
	private Integer scorePlayer3;
	
	/**
	 * Result of player 4
	 */
	private Integer scorePlayer4;	
	
	/**
	 * Mandatory for Hibernate
	 */
	public Game() {
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
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * @return the gameNumber
	 */
	public Integer getGameNumber() {
		return gameNumber;
	}

	/**
	 * @param gameNumber the gameNumber to set
	 */
	public void setGameNumber(Integer gameNumber) {
		this.gameNumber = gameNumber;
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
	 * Facade method to get all the players results in a map (key : player -> value : score)
	 * 
	 * @return
	 */
	public Map<Player, Integer> getResults() {
		
		if(table == null) return null;
		
		Map<Player, Integer> res = new HashMap<Player, Integer>();
		res.put(table.getPlayer1(), scorePlayer1);
		res.put(table.getPlayer2(), scorePlayer2);
		res.put(table.getPlayer3(), scorePlayer3);
		res.put(table.getPlayer4(), scorePlayer4);
		return res;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("id=").append(this.id).append("\n");
		buffer.append("table=").append(table).append("\n");
		buffer.append("game-number=").append(this.gameNumber).append("\n");
		buffer.append(this.table.getPlayer1()).append("=").append(this.scorePlayer1).append("\n");
		buffer.append(this.table.getPlayer2()).append("=").append(this.scorePlayer2).append("\n");
		buffer.append(this.table.getPlayer3()).append("=").append(this.scorePlayer3).append("\n");
		buffer.append(this.table.getPlayer4()).append("=").append(this.scorePlayer4);
		return buffer.toString();
	}

}
