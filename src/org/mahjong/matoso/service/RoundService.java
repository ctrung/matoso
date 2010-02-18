package org.mahjong.matoso.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Team;

public abstract class RoundService {
	private static final Logger LOG = Logger.getLogger(RoundService.class);

	/**
	 * Fill the rounds.
	 * 
	 * @param players
	 *            the list of players
	 * @param teams
	 *            the list of teams, null if no team
	 * @param nbTries
	 *            number of tries (a high number could slow the execution)
	 * @param maxTimeToWait
	 *            the number of seconds to wait if the number of tries is too high
	 * @return the list of rounds
	 */
	public static List<Table> fillTables(Set<Player> players, Set<Team> teams, int nbTries, int maxTimeToWait, int nbMaxRounds) {
		LOG.debug("nbTries=" + nbTries);
		long t0 = System.currentTimeMillis();
		List<Table> max = new ArrayList<Table>();
		List<Table> current = new ArrayList<Table>();
		while (nbTries-- > 1 && System.currentTimeMillis() - t0 < maxTimeToWait * 1000) {
			fillTables(current, players, teams, -1, nbMaxRounds);
			if (current.size() * 4 / players.size() >= nbMaxRounds) {
				max.clear();
				if (current.size() * 4 / players.size() > nbMaxRounds) {
					// Delete some tables
					for (Table tableI : current)
						if (tableI.getRound() <= nbMaxRounds)
							max.add(tableI);

				} else
					max.addAll(current);
				break;
			}
			if (current.size() > max.size()) {
				max.clear();
				max.addAll(current);
			}
			if (nbTries % 10000 == 0) {
				LOG.debug("time=" + ((System.currentTimeMillis() - t0) / 1000) + "\tnbTry=" + nbTries + "\tmax=" + max.size() * 4 / players.size());
			}
			current.clear();
		}
		LOG.debug("FINAL time=" + ((System.currentTimeMillis() - t0) / 1000));
		LOG.debug("FINAL nbTry=" + nbTries);
		LOG.debug("FINAL nbrounds=" + max.size() * 4 / players.size());
		return max;
	}

	public static int fillTables(List<Table> tables, Set<Player> playersSet, Set<Team> teams, int addMoreRounds, int nbRoundMax) {
		Player[] players = playersSet.toArray(new Player[] {});
		int cptRepeat = 0;
		// Round round;
		Table table;
		int numberTables = players.length / 4;
		int indexPlayer, cptLoop, roundNumber = 0;
		// "List" is used because the method get(int) is needed in the algorithme
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
				table.setRound(roundNumber);
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
			if (tableI.getRound() == roundNumber) {
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

	private static boolean checkTeam(Set<Team> listTeams, Table table, Player player) {
		Team team = TeamService.getTeamForPlayer(listTeams, player);
		if (team != null)
			for (Player playerTeam : table.getListPlayers()) {
				if (team.getPlayers().contains(playerTeam))
					return true;
			}
		return false;
	}

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
				LOG.debug("time=" + ((System.currentTimeMillis() - t0) / 1000) + "\tnbTry=" + nbTries + "\tminRepeat="
						+ minRepeat);
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
}