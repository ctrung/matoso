package org.mahjong.matoso.bean;

import java.util.Set;

public class Tournament {
	private Integer id;
	private String name;
	private String teamActivateStr;
	private String rules;
	private Set<Player> players;
	private Set<Team> teams;

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the players
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public String getTeamActivateStr() {
		return teamActivateStr;
	}

	public void setTeamActivateStr(String teamActivateStr) {
		this.teamActivateStr = teamActivateStr;
	}

	public boolean isTeamActivate() {
		return teamActivateStr != null && teamActivateStr.equals("Y");
	}

	public void setTeamActivate(boolean teamActivate) {
		if (teamActivate) {
			this.teamActivateStr = "Y";
			return;
		}
		this.teamActivateStr = "N";
	}

	/**
	 * @return the teams
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	/**
	 * @param teams
	 *            the teams to set
	 */
	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

}
