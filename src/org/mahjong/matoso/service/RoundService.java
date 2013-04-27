/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Round;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.BundleCst;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportException;
import org.mahjong.matoso.util.importing.ImportUtils;
import org.mahjong.matoso.util.message.MatosoMessage;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Round layer.<br>
 * Methods to fill tables too.
 * 
 * @author npochic
 * @date 14 mars 2010
 */
public abstract class RoundService {
	private static final Map<Integer, Integer[][][]> nbPlayers2Rounds = new HashMap<Integer, Integer[][][]>();
	static {
		nbPlayers2Rounds.put(16, new Integer[][][] { { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
				{ { 1, 5, 9, 13 }, { 2, 6, 10, 14 }, { 3, 7, 11, 15 }, { 4, 8, 12, 16 } }, { { 1, 6, 11, 16 }, { 2, 5, 12, 15 }, { 3, 8, 9, 14 }, { 4, 7, 10, 13 } },
				{ { 1, 7, 12, 14 }, { 2, 8, 11, 13 }, { 3, 5, 10, 16 }, { 4, 6, 9, 15 } }, { { 1, 8, 10, 15 }, { 2, 7, 9, 16 }, { 3, 6, 12, 13 }, { 4, 5, 11, 14 } } });
		nbPlayers2Rounds.put(20, new Integer[][][] { { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 }, { 17, 18, 19, 20, } },
				{ { 1, 5, 9, 13 }, { 2, 6, 10, 17 }, { 3, 7, 14, 18 }, { 4, 11, 15, 19 }, { 8, 12, 16, 20, } },
				{ { 1, 6, 15, 20 }, { 2, 5, 12, 18 }, { 3, 9, 16, 19 }, { 4, 7, 10, 13 }, { 8, 11, 14, 17, } },
				{ { 1, 10, 16, 18 }, { 2, 8, 13, 19 }, { 3, 5, 11, 20 }, { 4, 6, 9, 14 }, { 7, 12, 15, 17, } },
				{ { 1, 12, 14, 19 }, { 2, 7, 9, 20 }, { 3, 8, 10, 15 }, { 4, 5, 16, 17 }, { 6, 11, 13, 18, } } });
		nbPlayers2Rounds.put(24, new Integer[][][] { { { 1, 2, 11, 21 }, { 9, 10, 19, 5 }, { 17, 18, 3, 13 }, { 4, 7, 6, 24 }, { 8, 12, 14, 15 }, { 16, 20, 22, 23, } },
				{ { 1, 3, 12, 22 }, { 9, 11, 20, 6 }, { 17, 19, 4, 14 }, { 5, 8, 7, 18 }, { 2, 13, 15, 16 }, { 10, 21, 23, 24, } },
				{ { 1, 4, 13, 23 }, { 9, 12, 21, 7 }, { 17, 20, 5, 15 }, { 6, 2, 8, 19 }, { 3, 10, 14, 16 }, { 11, 18, 22, 24, } },
				{ { 1, 5, 14, 24 }, { 9, 13, 22, 8 }, { 17, 21, 6, 16 }, { 7, 3, 2, 20 }, { 4, 10, 11, 15 }, { 12, 18, 19, 23, } },
				{ { 1, 6, 15, 18 }, { 9, 14, 23, 2 }, { 17, 22, 7, 10 }, { 8, 4, 3, 21 }, { 5, 11, 12, 16 }, { 13, 19, 20, 24, } },
				{ { 1, 7, 16, 19 }, { 9, 15, 24, 3 }, { 17, 23, 8, 11 }, { 2, 5, 4, 22 }, { 6, 10, 12, 13 }, { 14, 18, 20, 21, } },
				{ { 1, 8, 10, 20 }, { 9, 16, 18, 4 }, { 17, 24, 2, 12 }, { 3, 6, 5, 23 }, { 7, 11, 13, 14 }, { 15, 19, 21, 22, } } });
		nbPlayers2Rounds.put(28, new Integer[][][] {
				{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 }, { 17, 18, 19, 20 }, { 21, 22, 23, 24 }, { 25, 26, 27, 28, } },
				{ { 1, 5, 21, 25 }, { 2, 6, 13, 17 }, { 14, 18, 22, 26 }, { 7, 9, 19, 27 }, { 8, 10, 15, 23 }, { 3, 11, 16, 28 }, { 4, 12, 20, 24, } },
				{ { 1, 6, 24, 28 }, { 2, 5, 15, 19 }, { 16, 20, 23, 27 }, { 8, 11, 17, 26 }, { 7, 12, 13, 22 }, { 3, 9, 14, 25 }, { 4, 10, 18, 21, } },
				{ { 1, 9, 17, 23 }, { 2, 10, 14, 28 }, { 5, 11, 13, 24 }, { 6, 12, 18, 27 }, { 16, 19, 21, 26 }, { 3, 7, 15, 20 }, { 4, 8, 22, 25, } },
				{ { 1, 7, 16, 18 }, { 2, 8, 21, 27 }, { 5, 12, 14, 23 }, { 15, 17, 22, 28 }, { 6, 11, 20, 25 }, { 3, 10, 19, 24 }, { 4, 9, 13, 26, } },
				{ { 1, 11, 19, 22 }, { 2, 12, 16, 25 }, { 6, 9, 15, 21 }, { 5, 10, 20, 26 }, { 14, 17, 24, 27 }, { 3, 8, 13, 18 }, { 4, 7, 23, 28, } },
				{ { 1, 8, 14, 20 }, { 2, 7, 24, 26 }, { 6, 10, 16, 22 }, { 13, 19, 23, 25 }, { 5, 9, 18, 28 }, { 3, 12, 17, 21 }, { 4, 11, 15, 27, } },
				{ { 1, 12, 15, 26 }, { 2, 11, 18, 23 }, { 7, 10, 17, 25 }, { 13, 20, 21, 28 }, { 8, 9, 16, 24 }, { 3, 5, 22, 27 }, { 4, 6, 14, 19, } },
				{ { 1, 10, 13, 27 }, { 2, 9, 20, 22 }, { 3, 6, 23, 26 }, { 4, 5, 16, 17 }, { 7, 11, 14, 21 }, { 8, 12, 19, 28 }, { 15, 18, 24, 25, } } });
		nbPlayers2Rounds.put(32, new Integer[][][] {
				{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 }, { 17, 18, 19, 20 }, { 21, 22, 23, 24 }, { 25, 26, 27, 28 }, { 29, 30, 31, 32, } },
				{ { 1, 5, 9, 29 }, { 2, 23, 19, 8 }, { 4, 6, 11, 32 }, { 27, 15, 20, 22 }, { 13, 17, 21, 25 }, { 3, 24, 18, 7 }, { 26, 14, 31, 12 }, { 28, 10, 30, 16, } },
				{ { 1, 28, 31, 22 }, { 2, 24, 27, 29 }, { 4, 14, 7, 25 }, { 6, 17, 12, 16 }, { 13, 18, 8, 11 }, { 3, 15, 21, 10 }, { 5, 26, 19, 32 }, { 23, 9, 20, 30, } },
				{ { 1, 19, 25, 12 }, { 2, 26, 20, 11 }, { 4, 27, 18, 10 }, { 6, 23, 31, 15 }, { 22, 7, 16, 32 }, { 3, 9, 28, 17 }, { 5, 24, 13, 30 }, { 14, 21, 8, 29, } },
				{ { 1, 23, 14, 11 }, { 2, 5, 28, 15 }, { 4, 9, 31, 8 }, { 24, 20, 25, 16 }, { 18, 21, 12, 32 }, { 3, 26, 22, 29 }, { 6, 13, 19, 10 }, { 27, 17, 7, 30, } },
				{ { 1, 24, 10, 32 }, { 2, 6, 25, 30 }, { 4, 19, 29, 16 }, { 9, 14, 18, 22 }, { 28, 21, 7, 11 }, { 3, 13, 31, 20 }, { 5, 23, 27, 12 }, { 26, 15, 17, 8, } },
				{ { 1, 6, 20, 21 }, { 2, 31, 7, 10 }, { 4, 5, 17, 22 }, { 24, 28, 14, 19 }, { 15, 25, 11, 29 }, { 3, 8, 12, 30 }, { 23, 26, 18, 16 }, { 9, 27, 13, 32, } },
				{ { 1, 15, 18, 30 }, { 2, 14, 17, 32 }, { 4, 23, 28, 13 }, { 27, 31, 19, 21 }, { 22, 8, 25, 10 }, { 3, 5, 11, 16 }, { 6, 24, 9, 26 }, { 20, 7, 12, 29, } },
				{ { 1, 26, 13, 7 }, { 2, 9, 21, 16 }, { 4, 24, 15, 12 }, { 23, 17, 10, 29 }, { 19, 22, 11, 30 }, { 3, 6, 27, 14 }, { 5, 31, 18, 25 }, { 28, 20, 8, 32, } },
				{ { 1, 27, 8, 16 }, { 2, 13, 22, 12 }, { 4, 26, 21, 30 }, { 6, 28, 18, 29 }, { 9, 15, 19, 7 }, { 3, 23, 25, 32 }, { 5, 14, 20, 10 }, { 24, 31, 17, 11, } } });
	}
	private static final Logger LOG = Logger.getLogger(RoundService.class);

	/**
	 * Get the rounds up and running by filling all the tables. <br>
	 * The algorithm is a brutal force calculating one.
	 * 
	 * @param players
	 *            the list of players
	 * @param teams
	 *            the list of teams, null if no team
	 * @param nbTries
	 *            number of tries (a high number could slow the execution)
	 * @param maxTimeToWait
	 *            the number of seconds to wait if the number of tries is too high
	 * @param nbWantedRounds
	 *            the max. rounds
	 * 
	 * @return the list tables for the specified rounds
	 */
	public static List<Table> fillTables(Set<Player> players, Set<Team> teams, int nbTries, int maxTimeToWait, int nbWantedRounds) {
		if (LOG.isDebugEnabled())
			LOG.debug("nbTries=" + nbTries);
		long t0 = System.currentTimeMillis();
		List<Table> max = new ArrayList<Table>();
		if (LOG.isDebugEnabled()) {
			LOG.debug("players.size()=" + players.size());
			LOG.debug("nbPlayers2Rounds.containsKey(players.size())=" + nbPlayers2Rounds.containsKey(players.size()));
		}
		if (nbPlayers2Rounds.containsKey(players.size())) {
			Integer[][][] rounds = nbPlayers2Rounds.get(players.size());
			int nbMaxSessions = rounds.length;
			if (LOG.isDebugEnabled())
				LOG.debug("nbMaxSessions=" + nbMaxSessions);
			boolean hasTeams = teams != null && !teams.isEmpty();
			if (LOG.isDebugEnabled())
				LOG.debug("hasTeams=" + hasTeams);
			if (nbMaxSessions >= (!hasTeams ? nbWantedRounds : nbWantedRounds + 1)) {
				// Associates numbers to players
				Map<Integer, Player> numPlayer2Player = new HashMap<Integer, Player>();
				List<Player> playersToAdd = new ArrayList<Player>(players);
				int indexPlayer;
				if (teams == null || teams.isEmpty())
					while (numPlayer2Player.size() != players.size()) {
						indexPlayer = (int) Math.floor(Math.random() * playersToAdd.size());
						if (!numPlayer2Player.containsKey(indexPlayer + 1))
							numPlayer2Player.put(indexPlayer + 1, playersToAdd.get(indexPlayer));
					}
				else {
					int numTable = 0, numPlayer = 0;
					for (Team t : teams)
						for (Player p : t.getPlayers()) {
							numPlayer2Player.put(rounds[0][numTable][numPlayer++], p);
							if (numPlayer == 4) {
								numPlayer = 0;
								numTable++;
							}
						}
				}
				if (LOG.isDebugEnabled())
					for (Integer num : numPlayer2Player.keySet())
						LOG.debug(num + "=>" + numPlayer2Player.get(num));

				int numRound = 1, numTable;
				Table t = null;
				boolean skipFirstRound = hasTeams;
				for (Integer[][] round : rounds) {
					if (skipFirstRound) {
						skipFirstRound = false;
						continue;
					}
					numTable = 1;
					for (Integer[] table : round) {
						t = new Table();
						t.setRoundNbr(numRound);
						t.setName(Integer.toString(numTable++));
						for (Integer player : table)
							TableService.addPlayerToTable(numPlayer2Player.get(player), t);
						max.add(t);
					}
					if (nbWantedRounds == numRound)
						// Stops if the number of filled rounds is the wanted number
						break;
					else
						numRound++;
				}
			}
		}
		if (max.isEmpty()) {
			List<Table> current = new ArrayList<Table>();
			while (nbTries-- > 1 && System.currentTimeMillis() - t0 < maxTimeToWait * 1000) {
				fillTables(current, players, teams, -1, nbWantedRounds);
				if (current.size() * 4 / players.size() >= nbWantedRounds) {
					max.clear();
					if (current.size() * 4 / players.size() > nbWantedRounds) {
						// Delete some tables
						for (Table tableI : current)
							if (tableI.getRoundNbr() <= nbWantedRounds)
								max.add(tableI);

					} else
						max.addAll(current);
					break;
				}
				if (current.size() > max.size()) {
					max.clear();
					max.addAll(current);
				}
				if (LOG.isDebugEnabled() && nbTries % 10000 == 0)
					LOG.debug("time=" + ((System.currentTimeMillis() - t0) / 1000) + "\tnbTry=" + nbTries + "\tmax=" + max.size() * 4 / players.size());
				current.clear();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("FINAL time=" + ((System.currentTimeMillis() - t0) / 1000));
				LOG.debug("FINAL nbTry=" + nbTries);
				LOG.debug("FINAL nbrounds=" + max.size() * 4 / players.size());
			}
		}
		return max;
	}

	public static int fillTables(List<Table> tables, Set<Player> playersSet, Set<Team> teams, int addMoreRounds, int nbRoundMax) {
		Player[] players = playersSet.toArray(new Player[] {});
		int cptRepeat = 0;
		Table table;
		int numberTables = players.length / 4;
		int indexPlayer, cptLoop, roundNumber = 0;
		List<Player> playersToAdd = new ArrayList<Player>();
		Player player = null;

		do {

			roundNumber++;
			if (roundNumber > nbRoundMax)
				break;

			// Get a list of random players
			playersToAdd.clear();

			while (playersToAdd.size() != players.length) {
				indexPlayer = (int) Math.floor(Math.random() * players.length);
				if (!playersToAdd.contains(players[indexPlayer]))
					playersToAdd.add(players[indexPlayer]);
			}

			for (int countTable = 1; countTable <= numberTables; countTable++) {
				// Add a new table
				table = new Table();
				table.setRoundNbr(roundNumber);
				table.setName(Integer.toString(countTable));

				// Find 4 players
				indexPlayer = 0;
				cptLoop = 0;
				while (table.getListPlayers().size() != 4) {
					if (indexPlayer == playersToAdd.size()) {
						indexPlayer = 0;
						cptLoop++;
						if (cptLoop == 2) {
							cptLoop = 0;
							if (addMoreRounds == -1)
								break;
							else if (playersToAdd.contains(player)) {
								cptRepeat++;

								if (LOG.isDebugEnabled())
									LOG.debug(roundNumber + " > " + table + " > " + player);

								// Add the previous player to the table
								TableService.addPlayerToTable(player, table);
								playersToAdd.remove(player);

								// Restart the loop
								continue;
							}
						}
					}

					player = playersToAdd.get(indexPlayer);

					// Check the history of the current player
					if ((teams == null || !checkTeam(teams, table, player)) && !checkAlreadyPlayed(tables, table, player)) {

						// Add the player to the table
						TableService.addPlayerToTable(player, table);
						playersToAdd.remove(player);
						continue;
					}

					indexPlayer++;
				}
				if (table.getListPlayers().size() == 4) {
					tables.add(table);
				} else {
					break;
				}
			}
		} while (tables.size() == numberTables * roundNumber && (addMoreRounds == -1 || --addMoreRounds > 0));

		Set<Table> lastRound = new LinkedHashSet<Table>();
		for (Table tableI : tables) {
			if (tableI.getRoundNbr() == roundNumber) {
				lastRound.add(tableI);
			}
		}
		if (lastRound.size() != numberTables) {
			for (Table tableI : lastRound) {
				tables.remove(tableI);
			}
		}
		return cptRepeat;
	}

	/**
	 * Test if a player is playing with another player of his team at the table.
	 * 
	 * @param listTeams
	 *            the list of all the teams
	 * @param table
	 * @param player
	 * 
	 * @return true if another player of the team is at this table, false otherwise.
	 */
	private static boolean checkTeam(Set<Team> listTeams, Table table, Player player) {
		Team team = TeamService.getTeamForPlayer(listTeams, player);
		if (team != null)
			for (Player playerTeam : table.getListPlayers()) {
				if (team.getPlayers().contains(playerTeam))
					return true;
			}
		return false;
	}

	/**
	 * Test if a player has already played with one of the player of this table.
	 * 
	 * @param listTables
	 *            the list of all the tables filled until now.
	 * @param table
	 * @param player
	 * 
	 * @return true if the player has already played with one of the player of this table, false otherwise.
	 */
	private static boolean checkAlreadyPlayed(List<Table> listTables, Table table, Player player) {
		for (Table tableI : listTables)
			for (Player playerTable : table.getListPlayers())
				if (tableI.getListPlayers().contains(playerTable) && tableI.getListPlayers().contains(player))
					return true;
		return false;
	}

	public static void addMoreRounds(List<Table> currentTables, Set<Player> players, Set<Team> teams, int nbRounds, int nbTries, int maxTimeToWait) {
		LOG.debug("nbTries=" + nbTries);
		long t0 = System.currentTimeMillis();
		Set<Table> best = new LinkedHashSet<Table>();
		int minRepeat = Integer.MAX_VALUE, currentRepeat;
		while (nbTries-- > 1 && System.currentTimeMillis() - t0 < maxTimeToWait * 1000) {
			currentRepeat = fillTables(currentTables, players, teams, nbRounds, 1000);
			if (currentRepeat < minRepeat) {
				minRepeat = currentRepeat;
				best.clear();
				best.addAll(currentTables);
			}
			if (nbTries % 100 == 0) {
				LOG.debug("time=" + ((System.currentTimeMillis() - t0) / 1000) + "\tnbTry=" + nbTries + "\tminRepeat=" + minRepeat);
			}

			// Remove the added rounds
			for (int i = 0; i < nbRounds; i++)
				currentTables.remove(currentTables.size() - 1);
		}
		currentTables.clear();
		currentTables.addAll(best);
		LOG.debug("FINAL time=" + ((System.currentTimeMillis() - t0) / 1000));
		LOG.debug("FINAL nbTry=" + nbTries);
		LOG.debug("FINAL minRepeat=" + minRepeat);
		LOG.debug("FINAL nbrounds=" + best.size() * 4 / players.size());
	}

	/**
	 * Get all rounds of the tournament ordered by number with tables inside.
	 * 
	 * @param tournament
	 * @return
	 * @throws FatalException
	 */
	@SuppressWarnings("unchecked")
	public static List<Round> getRounds(Tournament tournament) throws FatalException {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Round where tournament = :t order by number");
		query.setParameter("t", tournament);

		return (List<Round>) query.list();
	}

	/**
	 * Save a round.
	 * 
	 * @param tournament
	 * @param number
	 * @param tables
	 * @throws FatalException
	 */
	public static void create(Tournament tournament, Integer number, List<Table> tables) throws FatalException {

		Round round = new Round();
		round.setNumber(number);
		round.setTables(tables);
		round.setTournament(tournament);

		HibernateUtil.save(round);
	}

	/**
	 * Get a round by its id.
	 * 
	 * @param id
	 * @return
	 * @throws FatalException
	 */
	public static Round getById(Integer id) throws FatalException {
		return (Round) HibernateUtil.getSession().load(Round.class, id);
	}

	/**
	 * Update a round.
	 * 
	 * @param id
	 * @param date
	 * @param startTime
	 * @param finishTime
	 * 
	 * @throws FatalException
	 */
	public static void update(Integer id, Date date, Time startTime, Time finishTime) throws FatalException {

		Round round = getById(id);
		round.setDate(date);
		round.setStartTime(startTime);
		round.setFinishTime(finishTime);
		HibernateUtil.save(round);

	}

	public static boolean isFilledWithTotal(int id) {
		try {
			Round round = getById(id);
			for (Table table : round.getTables())
				if (GameResultService.isEmpty(table.getResult()))
					return false;
		} catch (FatalException e) {
			LOG.error("error to find the round " + id, e);
		}
		return true;
	}

	public static boolean isFilledWithGames(int id) {
		try {
			Round round = getById(id);
			for (Table table : round.getTables())
				if (table.getGames() == null || table.getGames().isEmpty())
					return false;
		} catch (FatalException e) {
			LOG.error("error to find the round " + id, e);
		}
		return true;
	}

	public static int getLastPlayedSession(Tournament tournament) {
		int i = 0;
		try {
			List<Round> list = getRounds(tournament);
			for (Round round : list) {
				if (isFilledWithTotal(round.getId()))
					i++;
				else
					break;
			}
		} catch (FatalException e) {
			LOG.error("error to get rounds for tournament " + tournament.getName() + " (" + tournament.getId() + ')', e);
		}
		return i;
	}

	/**
	 * Return the players data form the file's request.<br>
	 * Also do all the validation, messages can therefore be retrieved by the msgs objects.
	 * 
	 * @param request
	 * @param msgs
	 *            The validation messages.
	 * 
	 * @return lines corresponding to the rounds
	 * 
	 * @throws ImportException
	 * @throws FatalException
	 */
	// TODO
	public static List<String[]> getRawDataFromRequest(HttpServletRequest request, MatosoMessages msgs) throws ImportException, FatalException {
		InputStream stream = null;
		List<String[]> rounds = null;
		assert msgs != null;

		// Check that we have a file upload request
		if (!ServletFileUpload.isMultipartContent(request))
			throw new ImportException("The encryption type of the form is not multipart/form-data.", null);

		try {
			// Parse the request
			FileItemIterator iter = new ServletFileUpload().getItemIterator(request);
			FileItemStream item;
			String fieldName;
			while (iter.hasNext()) {
				item = iter.next();
				fieldName = item.getFieldName();
				stream = item.openStream();
				if (!item.isFormField() && fieldName.equals("csvfile")) {
					rounds = ImportUtils.readCSV(stream);
					break;
				}
			}

		} catch (FileUploadException e) {
			throw new FatalException("Can't upload players file", e);
		} catch (IOException e) {
			throw new FatalException("Can't retrieve players file stream", e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					throw new FatalException("Can't close players file stream", e);
				}
			}
		}
		if (rounds == null || rounds.isEmpty())
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("import.rounds.error.no.rounds"));
		return rounds;
	}
}