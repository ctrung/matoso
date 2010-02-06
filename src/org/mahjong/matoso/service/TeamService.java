/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Service layer for Team.
 * 
 * @author ctrung
 * @date 4 juil. 2009
 */
public class TeamService {
	private static final Logger LOG = Logger.getLogger(TeamService.class);

	public static void addPlayerToTeam(String name, String teamName, Player player) throws FatalException {
		if (LOG.isDebugEnabled())
			LOG.debug("addPlayerToTeam(" + player + ", " + name + ')');
		Tournament tournament = TournamentService.getByName(name);
		if (tournament.getTeams() == null)
			tournament.setTeams(new LinkedHashSet<Team>());

		Team teamFound = null;
		for (Team team : tournament.getTeams())
			if (team.getName().equals(teamName)) {
				teamFound = team;
				break;
			}

		if (teamFound == null) {
			// Create new team
			teamFound = new Team();
			teamFound.setName(teamName);
			tournament.getTeams().add(teamFound);
		}

		// Add the player
		if (teamFound.getPlayer1() == null)
			teamFound.setPlayer1(player);
		else if (teamFound.getPlayer2() == null)
			teamFound.setPlayer2(player);
		else if (teamFound.getPlayer3() == null)
			teamFound.setPlayer3(player);
		else if (teamFound.getPlayer4() == null)
			teamFound.setPlayer4(player);

		HibernateUtil.save(teamFound);
		if (LOG.isDebugEnabled())
			LOG.debug("<=addPlayerToTeam(" + player + ", " + tournament.getName() + ')');
	}

	public static void addPlayer(Tournament tournament, Player player) {
		if (tournament.getPlayers() == null)
			// Create a new list
			tournament.setPlayers(new LinkedHashSet<Player>());
		tournament.getPlayers().add(player);
	}

	public static Team getTeamForPlayer(Set<Team> listTeams, Player player) {
		for (Team team : listTeams) {
			if (team.getPlayers().contains(player))
				return team;
		}
		return null;
	}

	/**
	 * Look for a team by its name in database.
	 * 
	 * @param name
	 * @return A Team or <code>null</code>.
	 * @throws FatalException
	 */
	public static Team getByName(String name, Tournament tournament) throws FatalException {
		for (Team team : tournament.getTeams())
			if (team.getName().equals(name))
				return team;
		return null;
	}

	/**
	 * Persist a new team in database.
	 * 
	 * @param name
	 * @param tournament
	 * @throws FatalException
	 */
	public static void create(String name, Tournament tournament) throws FatalException {
		Team team = new Team();
		team.setName(name);
		tournament.getTeams().add(team);
		HibernateUtil.save(team);
	}

}
