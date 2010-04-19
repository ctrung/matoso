package org.mahjong.matoso.bean;

import java.util.HashSet;
import java.util.Set;

import org.mahjong.matoso.util.NumberUtils;

/**
 * A team of mahjong players. <br>
 * With MCR rules, a team must have at least 4 players.<br>
 * A team is bound to one tournament only.
 * 
 * @author ctrung
 * @date 3 juil. 2009
 */

public class Team {

	private Integer id;

	private String name;

	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;

	Tournament tournament;

	public Integer getScore() {
		return player1.getScore() + player2.getScore() + player3.getScore() + player4.getScore();
	}

	public Double getPoints() {
		return player1.getPoints() + player2.getPoints() + player3.getPoints() + player4.getPoints();
	}

	public String getPrettyPrintPoints() {
		return NumberUtils.getPrettyPrintForm(getPoints());
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getNameAndPlayers() {
		return name + "<br/><small>" + this.player1.getPrettyPrintName() + ", " + this.player2.getPrettyPrintName() + ", "
				+ this.player3.getPrettyPrintName() + ", " + this.player4.getPrettyPrintName() + "</small>";
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the player1
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * @param player1
	 *            the player1 to set
	 */
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	/**
	 * @return the player2
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * @param player2
	 *            the player2 to set
	 */
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	/**
	 * @return the player3
	 */
	public Player getPlayer3() {
		return player3;
	}

	/**
	 * @param player3
	 *            the player3 to set
	 */
	public void setPlayer3(Player player3) {
		this.player3 = player3;
	}

	/**
	 * @return the player4
	 */
	public Player getPlayer4() {
		return player4;
	}

	/**
	 * @param player4
	 *            the player4 to set
	 */
	public void setPlayer4(Player player4) {
		this.player4 = player4;
	}

	/**
	 * Facade method for having all team players in a set.
	 * 
	 * @return The Team players, never <code>null</code>.
	 */
	public Set<Player> getPlayers() {

		Set<Player> players = new HashSet<Player>();

		if (player1 != null)
			players.add(player1);
		if (player2 != null)
			players.add(player2);
		if (player3 != null)
			players.add(player3);
		if (player4 != null)
			players.add(player4);

		return players;
	}

	/**
	 * @return the tournament
	 */
	public Tournament getTournament() {
		return tournament;
	}

	/**
	 * @param tournament
	 *            the tournament to set
	 */
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

}
