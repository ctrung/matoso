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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Game;
import org.mahjong.matoso.bean.GameResult;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Round;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.form.RankingForm;
import org.mahjong.matoso.util.comparator.RankingComparator;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * The layer to work with the final ranking of a tournament.<br>
 * 
 * @author ctrung
 * @date 27 août 2009
 */
public class RankingService {

	private static Logger LOGGER = Logger.getLogger(RankingService.class);

	/**
	 * Returns an ordered list of players for a tournament.<br>
	 * 
	 * @param tournament
	 *            never <code>null</code>
	 * 
	 * @return a never <code>null</code> list.
	 * @throws FatalException
	 */
	public static List<Player> getByTournament(Tournament tournament) throws FatalException {

		List<Player> players = null;
		List<Table> allTables = null;

		assert (tournament != null);

		// 1. Get all tables (games, result, and players initialized within)
		allTables = TableService.getAllByTournament(tournament);

		// 2. Calculate the final score / points, number of victories, defeats,
		// given and selpick for each player
		// by still iterating on each table
		players = getPlayersWithRankingProps(allTables);

		// 3. Order list
		Collections.sort(players, new RankingComparator());

		// 4. Allocate integer ranks to allow displaytag to sort correctly
		// (pageContext.getAttribute("<id>_rowNum") is considered as String)
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setRank(i + 1);
		}

		return players;
	}

	/**
	 * Get a list of players (not ordered) with all properties needed for
	 * ranking loaded :
	 * <ul>
	 * <li>nb victories</li>
	 * <li>nb defeats</li>
	 * <li>nb given</li>
	 * <li>nb sustain selfpick</li>
	 * <li>nb selfpick</li>
	 * <li>score</li>
	 * <li>points</li>
	 * </ul>
	 * 
	 * @param tables
	 *            The tables.
	 * 
	 * @return a non <code>null</code> list of Player objects
	 */
	private static List<Player> getPlayersWithRankingProps(List<Table> tables) {
		Table table = null;
		long time = 0;
		Iterator<Game> gIt = null;
		Map<Integer, Player> mapPlayers = new HashMap<Integer, Player>();
		Player p1 = null;
		Player p2 = null;
		Player p3 = null;
		Player p4 = null;

		if (tables == null || tables.isEmpty())
			return new ArrayList<Player>();

		// updating 4 players ranking properties by looping on each table

		LOGGER.info("Beginning loading players properties...");
		time = System.currentTimeMillis();

		for (int i = 0; i < tables.size(); i++) {
			table = tables.get(i);

			assert table.getGames() != null;

			p1 = table.getPlayer1();
			p2 = table.getPlayer2();
			p3 = table.getPlayer3();
			p4 = table.getPlayer4();
			assert (p1 != null && p2 != null && p3 != null && p4 != null);

			// to update the same instance of a player, we use a map
			// not elegant but we can't assume Hibernate return the same
			// instance of a player...

			if (mapPlayers.get(p1.getId()) == null)
				mapPlayers.put(p1.getId(), p1);
			p1 = mapPlayers.get(p1.getId());

			if (mapPlayers.get(p2.getId()) == null)
				mapPlayers.put(p2.getId(), p2);
			p2 = mapPlayers.get(p2.getId());

			if (mapPlayers.get(p3.getId()) == null)
				mapPlayers.put(p3.getId(), p3);
			p3 = mapPlayers.get(p3.getId());

			if (mapPlayers.get(p4.getId()) == null)
				mapPlayers.put(p4.getId(), p4);
			p4 = mapPlayers.get(p4.getId());

			// looping on each game to have victories, defeats, given, sustain
			// selfpick and selfpick
			// properties updated for a player...

			gIt = table.getGames().iterator();
			while (gIt.hasNext()) {

				updatePlayersGameProps(p1, p2, p3, p4, gIt.next());

			}

			// updating score and points with table result
			if (table.getResult() != null) {
				updatePlayersScoreProps(p1, p2, p3, p4, table.getResult());
			}
		}

		LOGGER.info("Finished loading players properties : " + ((System.currentTimeMillis() - time) / 1000) + " sec");

		return new ArrayList<Player>(mapPlayers.values());
	}

	/**
	 * Update the score, points of 4 players by looking at a table result.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param result
	 */
	private static void updatePlayersScoreProps(Player p1, Player p2, Player p3, Player p4, GameResult result) {

		p1.addToScore(result.getScorePlayer1());
		p2.addToScore(result.getScorePlayer2());
		p3.addToScore(result.getScorePlayer3());
		p4.addToScore(result.getScorePlayer4());

		p1.addToPoints(result.getPointsPlayer1());
		p2.addToPoints(result.getPointsPlayer2());
		p3.addToPoints(result.getPointsPlayer3());
		p4.addToPoints(result.getPointsPlayer4());
	}

	/**
	 * Update the victories, defeats, given, sustain selfpick and selfpick
	 * figures of 4 players by looking at a game.
	 * 
	 * @param player1
	 * @param player2
	 * @param player3
	 * @param player4
	 * @param game
	 */
	private static void updatePlayersGameProps(Player player1, Player player2, Player player3, Player player4, Game game) {

		if (game == null)
			return;

		updateGamePropsForOnePlayer(player1, game.getScorePlayer1(), game.getScorePlayer2(), game.getScorePlayer3(), game.getScorePlayer4());
		updateGamePropsForOnePlayer(player2, game.getScorePlayer2(), game.getScorePlayer1(), game.getScorePlayer3(), game.getScorePlayer4());
		updateGamePropsForOnePlayer(player3, game.getScorePlayer3(), game.getScorePlayer1(), game.getScorePlayer2(), game.getScorePlayer4());
		updateGamePropsForOnePlayer(player4, game.getScorePlayer4(), game.getScorePlayer1(), game.getScorePlayer2(), game.getScorePlayer3());

	}

	/**
	 * Update the victories, defeats, given, sustain selfpick and selfpick
	 * figures of one player
	 * 
	 * @param player
	 * @param scorePlayer
	 * 
	 * @param scoreOtherPlayer1
	 * @param scoreOtherPlayer2
	 * @param scoreOtherPlayer3
	 */
	private static void updateGamePropsForOnePlayer(Player player, Integer scorePlayer, Integer scoreOtherPlayer1, Integer scoreOtherPlayer2,
			Integer scoreOtherPlayer3) {

		player.incrementNbGames();

		if (scorePlayer.intValue() > 0) {

			if (LOGGER.isDebugEnabled())
				LOGGER.debug(player + " won with score=" + scorePlayer);

			if (scoreOtherPlayer1.intValue() == scoreOtherPlayer2.intValue() && scoreOtherPlayer1.intValue() == scoreOtherPlayer3.intValue()
					&& scoreOtherPlayer2.intValue() == scoreOtherPlayer3.intValue()) {
				// 1. selfpick figure : one must have a positive score
				// and others have the same negative score
				player.incrementNbSelfpick();

				if (LOGGER.isDebugEnabled())
					LOGGER.debug("it was a selfpick because all players got some minus score=" + scoreOtherPlayer1);
			} else {
				// 2. victories figure : one must have a positive score
				// AND others can't have the same negative score
				player.incrementNbVictory();
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("it was a normal victory");
			}

		} else if (scorePlayer.intValue() == 0) {
			player.incrementNbDraw();
		} else {

			boolean selfpickAppeared = scorePlayer.intValue() * 3 == -scoreOtherPlayer1.intValue()
					|| scorePlayer.intValue() * 3 == -scoreOtherPlayer2.intValue() || scorePlayer.intValue() * 3 == -scoreOtherPlayer3.intValue();

			if (scorePlayer.intValue() == -8 && !selfpickAppeared) {
				// 3. defeats figure : one must have a score of -8
				player.incrementNbDefeat();
			} else {

				if (selfpickAppeared) {

					// 4. sustain selfpick figure : one must have a score
					// inferior to -8
					// AND at least another player has the same score
					player.incrementNbSustainSelfpick();
				} else {
					// 5. given figure : one must be the only one with a score
					// inferior to -8 and
					player.incrementNbGiven();
				}
			}
		}
	}

	public static Object getTeamsForTournament(Tournament tournament) {
		LOGGER.debug("start getTeamsForTournament => nb teams in tournament=" + tournament.getTeams().size());
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.addAll(tournament.getTeams());
		Collections.sort(teams, new Comparator<Team>() {
			public int compare(Team o2, Team o1) {
				if (o1.getPoints() == null && o2.getPoints() == null)
					return 0;
				else if (o1.getPoints() != null && o2.getPoints() == null)
					return 1;
				else if (o1.getPoints() == null && o2.getPoints() != null)
					return -1;
				else if (o1.getPoints() > o2.getPoints())
					return 1;
				else if (o1.getPoints() < o2.getPoints())
					return -1;
				else
					return o1.getScore() - o2.getScore();
			}
		});
		LOGGER.debug("end getTeamsForTournament");
		return teams;
	}

	/**
	 * Builds the form for the ranking page
	 * 
	 * @param tournament
	 *            the current tournament
	 * @return the form
	 * @throws FatalException
	 */
	public static RankingForm getRankingForm(Tournament tournament) throws FatalException {
		RankingForm rankingForm = new RankingForm();
		int score1, score2, score3, score4, score, scoreMax;
		Player player;
		String playerMax = null;
		for (Round round : RoundService.getRounds(tournament)) {

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("round=" + round.getNumber());

			scoreMax = 0;
			for (Table table : round.getTables()) {
				// Gets the best player for this table
				score1 = table.getResult().getScorePlayer1();
				score2 = table.getResult().getScorePlayer2();
				score3 = table.getResult().getScorePlayer3();
				score4 = table.getResult().getScorePlayer4();

				if (LOGGER.isDebugEnabled())
					LOGGER.debug("1=" + score1 + "/2=" + score2 + "/3=" + score3 + "/4=" + score4);

				score = Math.max(score1, score2);
				if (score == score1)
					player = table.getPlayer1();
				else
					player = table.getPlayer2();
				score = Math.max(score, score3);
				if (score == score3)
					player = table.getPlayer3();
				score = Math.max(score, score4);
				if (score == score4)
					player = table.getPlayer4();

				if (LOGGER.isDebugEnabled())
					LOGGER.debug("best player=" + player + "/score=" + player.getScore());

				// Compares with the current best player
				if (scoreMax < score) {
					playerMax = player.getPrettyPrintName();
					scoreMax = score;
				}

				if (LOGGER.isDebugEnabled())
					LOGGER.debug("max player=" + playerMax + "/score=" + scoreMax);
			}
			// Adds the best player for the current round
			rankingForm.addBestPlayerRound(String.valueOf(round.getNumber()), playerMax, scoreMax);
		}
		return rankingForm;
	}
}
