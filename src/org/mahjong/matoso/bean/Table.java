/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * A Table instance represent a group of 4 mahjong players 
 * playing together for a given round.<br>
 * It only exists in a given Tournament.
 *
 * @author clement
 * @date 4 juil. 2009
 */

public class Table {
	
	/**
	 * Surrogate key to ensure backward compatibility.<br>
	 * More easy to handle with Hibernate than composed key.
	 */
	private Integer id ;
	
	/**
	 * Name of the table.
	 */
	private String name;
	
	/**
	 * Tournament.
	 * The triplet (idTable, tournament, round) is unique.
	 */
	private Tournament tournament;
	
	/**
	 * The round.
	 * The triplet (idTable, tournament, round) is unique.
	 */
	private Integer round;
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	
	private List<Game> games;
	
	private GameResult result;
	private Penalty penalty;
	
	public Table() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tournament
	 */
	public Tournament getTournament() {
		return tournament;
	}

	/**
	 * @param tournament the tournament to set
	 */
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	/**
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(Integer round) {
		this.round = round;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getPlayer3() {
		return player3;
	}

	public void setPlayer3(Player player3) {
		this.player3 = player3;
	}

	public Player getPlayer4() {
		return player4;
	}

	public void setPlayer4(Player player4) {
		this.player4 = player4;
	}

	/**
	 * @return the games
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(List<Game> games) {
		this.games = games;
	}

	/**
	 * @return the result
	 */
	public GameResult getResult() {
		return result;
	}

	/**
	 * @return the penalty
	 */
	public Penalty getPenalty() {
		return penalty;
	}

	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(Penalty penalty) {
		this.penalty = penalty;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(GameResult result) {
		this.result = result;
	}

	/**
	 * Facade method for having all table players in a list.
	 * @return The Table players, never <code>null</code>.
	 */
	public List<Player> getListPlayers() {
		List<Player> players = new ArrayList<Player>();
		
		if(player1!=null) players.add(player1);
		if(player2!=null) players.add(player2);
		if(player3!=null) players.add(player3);
		if(player4!=null) players.add(player4);
		
		return players;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.player1.getEma()).append("-");
		buffer.append(this.player2.getEma()).append("-");
		buffer.append(this.player3.getEma()).append("-");
		buffer.append(this.player4.getEma());
		return buffer.toString();
	}
	
}
