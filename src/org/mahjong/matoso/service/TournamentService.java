/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.mahjong.matoso.bean.Game;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Round;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.display.TournamentStats;
import org.mahjong.matoso.util.DateUtils;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Tournament service layer.
 * 
 * @author ctrung
 * @date 21 oct. 2009
 */
public abstract class TournamentService {
	private static final Logger LOG = Logger.getLogger(TournamentService.class);

	/**
	 * Persist a new tournament in database.
	 * 
	 * @param name
	 *            The tournament name given.
	 * @param teamActivate
	 *            <code>true</code> if the tournament allows teams, else
	 *            <code>false</code>.
	 * @return The newly persisted tournament ID.
	 * @throws FatalException
	 */
	public static Tournament createNewTournament(String name, boolean teamActivate) throws FatalException {
		if (LOG.isDebugEnabled())
			LOG.debug("createNewTournament(" + name + ", " + teamActivate + ')');

		Tournament tournament = new Tournament();
		tournament.setName(name);
		tournament.setTeamActivate(teamActivate);
		tournament.setPlayers(new LinkedHashSet<Player>());
		HibernateUtil.save(tournament);

		LOG.debug("save " + name + '-' + teamActivate);
		return tournament;
	}

	/**
	 * Add a player to a tournament.
	 * 
	 * @param player
	 * @param tournament
	 *            can't be null
	 * 
	 * @throws FatalException
	 */
	public static void addPlayer(Player player, Tournament tournament) throws FatalException {
		assert (tournament != null);
		if (LOG.isDebugEnabled())
			LOG.debug("addPlayer(" + player + ", " + tournament.getName() + ')');

		tournament.getPlayers().add(player);
		HibernateUtil.save(player);
		if (LOG.isDebugEnabled())
			LOG.debug("<=addPlayer(" + player + ", " + tournament.getName() + ')');
	}

	/**
	 * Import data obtained from a CSV file. <br/>
	 * 0 -> firstname<br/>
	 * 1 -> lastname<br/>
	 * 2 -> email<br/>
	 * 3 -> country<br/>
	 * 4 -> No EMA<br/>
	 * 5 -> Team<br/>
	 * 6 -> Pseudo<br/>
	 * 7 -> Arrival (DD/MM/YYYY)<br/>
	 * 8 -> Departure (DD/MM/YYYY)<br/>
	 * 9 -> Formular (DD/MM/YYYY)<br/>
	 * 10 -> Payment (DD/MM/YYYY)<br/>
	 * 11 -> Payment mode<br/>
	 * 12 -> Photo<br/>
	 * 13 -> CJ<br/>
	 * 14 -> CP<br/>
	 * 15 -> Details<br/>
	 * 16 -> Club<br/>
	 * 
	 * @param tournament
	 * @param lines
	 *            list of arrays. Each array represents a CSV line
	 * 
	 * @throws FatalException
	 */
	public static void addPlayers(Tournament tournament, List<String[]> lines) throws FatalException {

		String[] line = null;
		Player player = null;
		Map<String, List<Player>> teams = new HashMap<String, List<Player>>();

		String firstname = null;
		String lastname = null;
		String email = null;
		String country = null;
		String ema = null;
		String team = null;
		String pseudo = null;
		Date dateArrival = null;
		Date dateDeparture = null;
		Date dateFormular = null;
		Date datePayment = null;
		String paymentMode = null;
		boolean hasPhoto = false;
		String cj = null;
		String cp = null;
		String details = null;
		String club = null;

		if (lines == null || tournament == null)
			return;

		try {
			for (int i = 0; i < lines.size(); i++) {
				line = lines.get(i);

				if (line == null) {
					LOG.warn("found null line");
					continue;
				}

				LOG.info("Found player : " + Arrays.toString(line));

				// 0 -> firstname
				// 1 -> lastname
				// 2 -> email
				// 3 -> country
				// 4 -> No EMA
				// 5 -> Team
				// 6 -> Pseudo
				// 7 -> Arrival (DD/MM/YYYY)
				// 8 -> Departure (DD/MM/YYYY)
				// 9 -> Formular (DD/MM/YYYY)
				// 10 -> Payment (DD/MM/YYYY)
				// 11 -> Payment mode
				// 12 -> Photo
				// 13 -> CJ
				// 14 -> CP
				// 15 -> Details
				// 16 -> Club

				firstname = line[0];
				lastname = line[1];

				if (lastname == null && firstname == null) {
					LOG.warn("found an non empty line but lastname and firstname all null");
					continue;
				}

				country = line[3];
				ema = line[4];
				team = line[5];
				pseudo = line[6];
				dateArrival = DateUtils.parseSQLDate(line[7]);
				dateDeparture = DateUtils.parseSQLDate(line[8]);
				dateFormular = DateUtils.parseSQLDate(line[9]);
				datePayment = DateUtils.parseSQLDate(line[10]);
				paymentMode = line[11];
				hasPhoto = line[12] != null && (line[12].equals("x") || line[12].equals("X"));
				cj = line[13];
				cp = line[14];
				details = line[15];
				club = line[16];

				player = new Player(firstname, lastname, email, country, ema, null, pseudo, dateArrival, dateDeparture, dateFormular, datePayment,
						paymentMode, hasPhoto, cj, cp, details, club);
				player.setTournamentNumber(i + 1);

				addPlayer(player, tournament);

				// add player to team
				if (tournament.isTeamActivate() && team != null && !StringUtils.isBlank(team)) {
					if (teams.get(team) == null) {
						teams.put(team, new ArrayList<Player>());
					}
					List<Player> players = teams.get(team);
					players.add(player);
				}
			}

			// Teams
			if (teams.keySet() != null) {
				for (Iterator<String> iterator = teams.keySet().iterator(); iterator.hasNext();) {
					String tn = (String) iterator.next();

					List<Player> players = teams.get(tn);
					if (players.size() > 4) {
						LOG.warn("Validation let team [" + tn + "] have more than 4 players : " + players.toString());
					}

					Team teamObj = new Team();
					teamObj.setName(tn);
					teamObj.setTournament(tournament);

					if (players.get(0) != null)
						teamObj.setPlayer1(players.get(0));
					if (players.get(1) != null)
						teamObj.setPlayer2(players.get(1));
					if (players.get(2) != null)
						teamObj.setPlayer3(players.get(2));
					if (players.get(3) != null)
						teamObj.setPlayer4(players.get(3));

					HibernateUtil.save(teamObj);
				}
			}

		} catch (FatalException e) {
			throw new FatalException("Can't add player " + player.getPrettyPrintName(), e);
		}

	}

	/**
	 * Get all the tournaments in database.
	 * 
	 * @return All the tournaments in database.
	 * @throws FatalException
	 *             If a database problem occurs.
	 */
	@SuppressWarnings("unchecked")
	public static List<Tournament> getList() throws FatalException {
		try {
			return HibernateUtil.getSession().createCriteria(Tournament.class).list();
		} catch (HibernateException e) {
			throw new FatalException(e);
		}
	}

	/**
	 * Return a tournament by its ID.
	 * 
	 * @param id
	 * @return A tournament or <code>null</code> if not found.
	 * @throws FatalException
	 */
	public static Tournament getById(Integer id) throws FatalException {
		return (Tournament) HibernateUtil.getSession().load(Tournament.class, id);
	}

	/**
	 * Delete a tournament
	 * 
	 * @param tournament
	 * @throws FatalException
	 */
	public static void deleteTournament(Tournament tournament) throws FatalException {
		if (LOG.isDebugEnabled())
			LOG.debug("deleteTournament start");

		// Delete tables of the tournament
		List<Table> tables = TableService.getAllByTournament(tournament);
		List<Game> games;
		for (Table table : tables) {
			// Delete games of the current table
			games = GameService.getOrderedListFromTable(table);
			for (Game game : games)
				HibernateUtil.delete(game);
			HibernateUtil.delete(table);
		}
		if (LOG.isDebugEnabled())
			LOG.debug("deleteTournament tables OK");

		// Delete rounds of the tournament
		List<Round> rounds = RoundService.getRounds(tournament);
		for (Round round : rounds)
			HibernateUtil.delete(round);
		if (LOG.isDebugEnabled())
			LOG.debug("deleteTournament rounds OK");

		// Delete teams of the tournament
		for (Team team : tournament.getTeams())
			HibernateUtil.delete(team);
		if (LOG.isDebugEnabled())
			LOG.debug("deleteTournament teams OK");

		// Delete players of the tournament
		for (Player player : tournament.getPlayers())
			HibernateUtil.delete(player);
		if (LOG.isDebugEnabled())
			LOG.debug("deleteTournament players OK");

		// Delete the tournament
		HibernateUtil.delete(tournament);
		LOG.debug("deleteTournament tournament OK");
	}

	/**
	 * Calculate the tournament statitics based on the players statistics.
	 * 
	 * @param players
	 *            The players of the tournament.
	 * 
	 * @return A never null object TournamentStats encapsulating the tournaments
	 *         stats.
	 */
	public static TournamentStats getTournamentStats(List<Player> players) {
		TournamentStats ts = new TournamentStats();

		if (players != null && players.size() > 0) {

			int nbGames = 0;
			int nbVictory = 0;
			int nbSelfpick = 0;
			int nbDefeat = 0;
			int nbGiven = 0;
			int nbSustainSelpick = 0;
			int nbDraw = 0;

			// Iterates over all players to increment each stats values.

			for (Iterator<Player> it = players.iterator(); it.hasNext();) {
				Player p = it.next();

				nbGames += NumberUtils.getIntDefaultValue(p.getNbGames());
				nbVictory += NumberUtils.getIntDefaultValue(p.getNbVictory());
				nbSelfpick += NumberUtils.getIntDefaultValue(p.getNbSelfpick());
				nbDefeat += NumberUtils.getIntDefaultValue(p.getNbDefeat());
				nbGiven += NumberUtils.getIntDefaultValue(p.getNbGiven());
				nbSustainSelpick += NumberUtils.getIntDefaultValue(p.getNbSustainSelfpick());
				nbDraw += NumberUtils.getIntDefaultValue(p.getNbDraw());
			}

			// Time to initialize the TournamentStats object with the collected
			// values.
			ts.setNbVictory(nbVictory);
			ts.setNbSelfpick(nbSelfpick);
			ts.setNbDefeat(nbDefeat);
			ts.setNbGiven(nbGiven);
			ts.setNbSustainSelfpick(nbSustainSelpick);

			assert (nbDraw % 4 == 0); // we added all the players draw games,
			// that's 4 times the total number of draw games played in the
			// tournament !
			ts.setNbDraw(nbDraw / 4);

			assert (nbGames % 4 == 0); // we added all the players games,
			// that's 4 times the total number of games played in the tournament
			// !
			ts.setNbGames(nbGames / 4);
		}

		return ts;
	}
}