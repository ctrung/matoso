/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.I18nUtils;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.message.MatosoMessage;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Service layer for Team.
 * 
 * @author ctrung
 * @date 4 juil. 2009
 */
public class TeamService {
	private static final Logger LOG = Logger.getLogger(TeamService.class);

	/**
	 * Builds random teams with the players in the given tournament
	 * 
	 * @param tnmt
	 *            a tournament
	 * @throws FatalException
	 */
	public static void buildsRandomTeams(Tournament tnmt) throws FatalException {
		if (LOG.isDebugEnabled())
			LOG.debug("=>buildsRandomTeams");
		List<Player> players = new ArrayList<Player>(tnmt.getPlayers());
		List<Player> dealedPlayers = new ArrayList<Player>();
		int indexPlayer;
		int numberOfTeam = 0;
		while (dealedPlayers.size() != players.size()) {
			// Gets a random player
			indexPlayer = (int) Math.floor(Math.random() * players.size());

			// Checks if the user is not dealed
			if (!dealedPlayers.contains(players.get(indexPlayer))) {
				// Builds a new team
				if (dealedPlayers.size() % 4 == 0)
					numberOfTeam++;

				// Adds the player to a team
				addPlayerToTeam(tnmt, "Team " + numberOfTeam, players.get(indexPlayer));
				dealedPlayers.add(players.get(indexPlayer));
			}
		}
		if (LOG.isDebugEnabled())
			LOG.debug("<=buildsRandomTeams");
	}

	public static void addPlayerToTeam(Tournament tournament, String teamName, Player player) throws FatalException {
		if (LOG.isDebugEnabled())
			LOG.debug("addPlayerToTeam(" + player + ", " + tournament.getName() + ')');
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

		// Save the link between the tournament and the team
		teamFound.setTournament(tournament);
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

	/**
	 * Loop around a list of teams and returns the team the player is in.
	 * 
	 * @param listTeams
	 * @param player
	 * @return
	 */
	public static Team getTeamForPlayer(Set<Team> listTeams, Player player) {
		for (Team team : listTeams) {
			if (team.getPlayers().contains(player))
				return team;
		}
		return null;
	}

	/**
	 * Get the team of a player in a tournament by querying the database.
	 * 
	 * @param player
	 * @param tournament
	 * @return
	 * @throws FatalException
	 */
	public static Team getTeamForPlayer(Player player, Tournament tournament) throws FatalException {

		Session session = HibernateUtil.getSession();
		Query query = session
				.createQuery("from Team as t where t.tournament = :t and (t.player1 = :p or t.player2 = :p or t.player3 = :p or t.player4 = :p)");
		query.setParameter("t", tournament);
		query.setParameter("p", player);

		return (Team) query.uniqueResult();
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

	/**
	 * Checks the team has 4 players.
	 * 
	 * @param players
	 * @param msgs
	 *            Can't be null.
	 */
	public static void validate(List<String[]> players, MatosoMessages msgs) {

		if (players == null)
			return;
		assert msgs != null;

		Map<String, Integer> teamCount = new TreeMap<String, Integer>();
		StringBuilder badTeams = new StringBuilder();
		String team;
		Integer count;
		for (String[] player : players) {
			if (player == null)
				continue;
			team = player[5]; // team property is at position 5
			if (team != null) {
				if (!teamCount.containsKey(team))
					teamCount.put(team, 0);
				count = teamCount.get(team);
				teamCount.put(team, ++count);
			}
		}
		for (String teamName : teamCount.keySet())
			if (teamCount.get(teamName) != 4) {
				if (badTeams.length() != 0)
					badTeams.append(", ");
				badTeams.append(teamName);
			}

		if (badTeams.length() > 0)
			msgs.addMessage(MatosoMessage.ERROR, I18nUtils.getMessage("player.mass.import.error.team.too.many.players", badTeams.toString()));
	}

	public static int getIndexInList(Team t, List<Team> teams) {
		if (t == null || teams == null | teams.size() == 0)
			return -1;
		for (Team team : teams)
			if (team.getId().equals(t.getId()))
				return teams.indexOf(team);
		return -1;
	}

	/**
	 * Replace all 4 players
	 * 
	 * @param teamId
	 * @param players
	 *            must be size 4
	 * @throws FatalException
	 */
	public static void replaceAllPlayers(Integer teamId, List<Player> players) throws FatalException {

		if (players != null && players.size() == 4) {
			// get the team
			Team team = getById(teamId);
			if (team != null) {
				players.get(0).setTeam(team);
				players.get(1).setTeam(team);
				players.get(2).setTeam(team);
				players.get(3).setTeam(team);

				team.setPlayer1(players.get(0));
				team.setPlayer2(players.get(1));
				team.setPlayer3(players.get(2));
				team.setPlayer4(players.get(3));
			}
			HibernateUtil.save(team);
		}
	}

	public static Team getById(Integer id) throws FatalException {

		if (id == null)
			return null;

		try {
			Query q = HibernateUtil.getSession().createQuery("from Team t where t.id = :id");
			q.setParameter("id", id);

			return (Team) q.uniqueResult();

		} catch (HibernateException e) {
			throw new FatalException(e);
		}
	}
}
