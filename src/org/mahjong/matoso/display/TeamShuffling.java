package org.mahjong.matoso.display;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Team;

/**
 * A Team for shuffling.
 * 
 * @author ctrung
 * @date 24 févr. 2011
 */
public class TeamShuffling {

	private Team team;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	public void addPlayer(Player player) {
		// do it the dirty way
		if(this.player1 == null) this.player1 = player;
		else if(this.player2 == null) this.player2 = player;
		else if(this.player3 == null) this.player3 = player;
		else this.player4 = player;
	}
	
}
