/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import static org.mahjong.matoso.constant.ApplicationCst.MAX_GAMES_FOR_ONE_SESSION;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mahjong.matoso.bean.Game;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.display.DisplayTableGame;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Service layer for handling Game instances.
 * 
 * @author ctrung
 * @date 16 août 2009
 */
public abstract class GameService {
	private static Logger LOG = Logger.getLogger(GameService.class);

	/**
	 * Retrieve the games played at a table ordered by the game number.
	 * 
	 * @param table
	 * @return
	 * @throws FatalException
	 */
	@SuppressWarnings("unchecked")
	public static List<Game> getOrderedListFromTable(Table table) throws FatalException {
		return HibernateUtil.getSession().createCriteria(Game.class).add(Restrictions.eq("table", table)).addOrder(Order.asc("gameNumber")).list();
	}

	/**
	 * Calculate the games data of a table to be displayed in the view.
	 * 
	 * @param table
	 * 
	 * @return a list of 16 games data, the games data are never <code>null</code>
	 * 
	 * @throws FatalException
	 */
	public static List<DisplayTableGame> getDisplayedTableGames(Table table) throws FatalException {
		List<DisplayTableGame> res = new ArrayList<DisplayTableGame>();
		for (int i = 0; i < MAX_GAMES_FOR_ONE_SESSION; i++)
			res.add(null);

		List<Game> games = GameService.getOrderedListFromTable(table);
		assert (games != null && games.size() <= MAX_GAMES_FOR_ONE_SESSION);

		// filling displayed data of found games
		DisplayTableGame dtg;
		for (Game aGame : games) {
			dtg = new DisplayTableGame(aGame.getScorePlayer1(), aGame.getScorePlayer2(), aGame.getScorePlayer3(), aGame.getScorePlayer4());
			if (LOG.isDebugEnabled())
				LOG.debug("getDisplayedTableGames:adds " + (aGame.getGameNumber() - 1));
			res.set(aGame.getGameNumber() - 1, dtg);
		}

		// filling empty games scores if all games haven't been saved
		if (games.size() < MAX_GAMES_FOR_ONE_SESSION)
			for (int i = 0; i < MAX_GAMES_FOR_ONE_SESSION; i++)
				if (res.get(i) == null)
					res.set(i, new DisplayTableGame());

		return res;
	}

	/**
	 * Add or update a game
	 * 
	 * @param table
	 * @param gameData
	 *            all game data retrieved from the HttpServletRequest
	 * @param round
	 * 
	 * @throws FatalException
	 */
	public static void addOrUpdateResult(Table table, DisplayTableGame gameData, int round) throws FatalException {
		Game theGame = null;

		assert (round <= MAX_GAMES_FOR_ONE_SESSION);

		// retrieve the game
		theGame = (Game) HibernateUtil.getSession().createCriteria(Game.class).add(Restrictions.eq("table", table))
				.add(Restrictions.eq("gameNumber", round)).uniqueResult();

		if (theGame == null) { // new game
			theGame = new Game();
			theGame.setGameNumber(round);
			theGame.setTable(table);
		}

		// fill data

		theGame.setScorePlayer1(gameData.getPlayer1Score());
		theGame.setScorePlayer2(gameData.getPlayer2Score());
		theGame.setScorePlayer3(gameData.getPlayer3Score());
		theGame.setScorePlayer4(gameData.getPlayer4Score());

		// persist data
		HibernateUtil.save(theGame);
	}

	/**
	 * Calculate the player scores for a game and update the DisplayTableGame object.
	 * 
	 * @param gameData
	 *            the table game data, asserts not null.
	 */
	public static void calculatePlayersPointsForAGame(DisplayTableGame gameData) {
		Integer pointsForTheWinner = null;

		assert gameData != null;

		if (gameData.getHandValue() == null)
			return;

		pointsForTheWinner = gameData.getHandValue() + ApplicationCst.SCORE_BASE_POINTS;

		/*
		 * Exception case : draw game !
		 */
		if (gameData.getHandValue().intValue() == 0) {
			gameData.setPlayer1Score(0);
			gameData.setPlayer2Score(0);
			gameData.setPlayer3Score(0);
			gameData.setPlayer4Score(0);
			return;
		}

		/*
		 * Normal cases
		 */

		if (gameData.isSelfpick()) {

			// player 1
			if (gameData.isPlayer1Win())
				gameData.setPlayer1Score(pointsForTheWinner * 3);
			else
				gameData.setPlayer1Score(-pointsForTheWinner);

			// player 2
			if (gameData.isPlayer2Win())
				gameData.setPlayer2Score(pointsForTheWinner * 3);
			else
				gameData.setPlayer2Score(-pointsForTheWinner);

			// player 3
			if (gameData.isPlayer3Win())
				gameData.setPlayer3Score(pointsForTheWinner * 3);
			else
				gameData.setPlayer3Score(-pointsForTheWinner);

			// player 4
			if (gameData.isPlayer4Win())
				gameData.setPlayer4Score(pointsForTheWinner * 3);
			else
				gameData.setPlayer4Score(-pointsForTheWinner);

		} else {

			// player 1
			if (gameData.isPlayer1Win())
				gameData.setPlayer1Score(pointsForTheWinner + 16);
			else if (gameData.isPlayer1Lose())
				gameData.setPlayer1Score(-pointsForTheWinner);
			else
				gameData.setPlayer1Score(-ApplicationCst.SCORE_BASE_POINTS);

			// player 2
			if (gameData.isPlayer2Win())
				gameData.setPlayer2Score(pointsForTheWinner + 16);
			else if (gameData.isPlayer2Lose())
				gameData.setPlayer2Score(-pointsForTheWinner);
			else
				gameData.setPlayer2Score(-ApplicationCst.SCORE_BASE_POINTS);

			// player 3
			if (gameData.isPlayer3Win())
				gameData.setPlayer3Score(pointsForTheWinner + 16);
			else if (gameData.isPlayer3Lose())
				gameData.setPlayer3Score(-pointsForTheWinner);
			else
				gameData.setPlayer3Score(-ApplicationCst.SCORE_BASE_POINTS);

			// player 4
			if (gameData.isPlayer4Win())
				gameData.setPlayer4Score(pointsForTheWinner + 16);
			else if (gameData.isPlayer4Lose())
				gameData.setPlayer4Score(-pointsForTheWinner);
			else
				gameData.setPlayer4Score(-ApplicationCst.SCORE_BASE_POINTS);

		}
	}

	/**
	 * Try to collect a game data, that is a selfpick info, hand value, who won or who gave. If collection succeeds, the list of DisplayTableGame is
	 * updated.
	 * 
	 * @param paramName
	 *            the parameter name
	 * @param paramValue
	 *            the parameter value
	 * 
	 * @param rcvdGamesData
	 *            the games list to fill, can't be <code>null</code>
	 * 
	 * @return true if collection succeeds, that is to say the parameter was a game data, false otherwise.
	 */
	public static boolean collectGameData(String paramName, String paramValue, List<DisplayTableGame> rcvdGamesData) {

		Integer round = null;

		if (paramName == null)
			return false;
		assert (rcvdGamesData != null);

		// the parameter name possible,
		// (X = game number, Y = player position)
		// - X_handValue (value = int)
		// - X_winOrLoseY (value = lose|win)

		/*
		 * 1. Retrieve the round (round index starts at 0)
		 */
		if (paramName.indexOf("_") != -1)
			round = NumberUtils.getInteger(paramName.substring(0, paramName.indexOf("_")));
		else
			round = null;

		if (round == null || round + 1 > MAX_GAMES_FOR_ONE_SESSION)
			return false;

		/*
		 * 2. Identify data type, collect data
		 */
		boolean found = true;

		DisplayTableGame dtg = rcvdGamesData.get(round);
		assert dtg != null;

		// -> hand value data ?
		if (paramName.contains("_handValue")) {
			if (paramValue != null && paramValue.length() != 0) {
				dtg.setHandValue(NumberUtils.getInteger(paramValue));
			}
		}
		// -> self pick info ?
		else if (paramName.contains("_selfpick")) {
			dtg.setSelfpick(true);
		} else if (paramName.endsWith("_winner")) {
			if (paramValue != null && paramValue.length() != 0) {
				int indexWinner = 0;
				try {
					indexWinner = Integer.parseInt(paramValue);
				} catch (NumberFormatException e) {
					LOG.debug(paramValue + " is not an integer");
				}
				if (indexWinner != 0) {
					dtg.setPlayer1Win(false);
					dtg.setPlayer2Win(false);
					dtg.setPlayer3Win(false);
					dtg.setPlayer4Win(false);
					switch (indexWinner) {
					case 1:
						dtg.setPlayer1Win(true);
						break;
					case 2:
						dtg.setPlayer2Win(true);
						break;
					case 3:
						dtg.setPlayer3Win(true);
						break;
					case 4:
						dtg.setPlayer4Win(true);
						break;
					default:
						break;
					}
				}
			}
		} else if (paramName.endsWith("_loser")) {
			if (paramValue != null && paramValue.length() != 0) {
				int indexLoser = 0;
				try {
					indexLoser = Integer.parseInt(paramValue);
				} catch (NumberFormatException e) {
					LOG.debug(paramValue + " is not an integer");
				}
				if (indexLoser != 0) {
					dtg.setPlayer1Lose(false);
					dtg.setPlayer2Lose(false);
					dtg.setPlayer3Lose(false);
					dtg.setPlayer4Lose(false);
					switch (indexLoser) {
					case 1:
						dtg.setPlayer1Lose(true);
						break;
					case 2:
						dtg.setPlayer2Lose(true);
						break;
					case 3:
						dtg.setPlayer3Lose(true);
						break;
					case 4:
						dtg.setPlayer4Lose(true);
						break;
					default:
						break;
					}
				}
			}
		} else
			found = false;
		return found;
	}

	/**
	 * Delete the game number X from a table. This method will never fail. You should test if the game exists before calling deleteGame if you want to
	 * be sure deletion is effective.
	 * 
	 * @param table
	 * @param gameNumber
	 *            Starts at 0 ... n-1.
	 * 
	 * @see TableService#hasGame(Table, int) test if the game number X exists at a table.
	 */
	public static void deleteGame(Table table, int gameNumber) {

		if (table == null || table.getGames() == null)
			return;

		try {
			Game game = table.getGames().get(gameNumber);
			HibernateUtil.delete(game);
		} catch (Exception e) {
			// do not throw exception, see javadoc
			LOG.warn("Deletion of game " + gameNumber + " of table [id=" + table.getId() + "] has gone wrong. This can be an important error.", e);
		}

	}

	/**
	 * Test if a winner is present. A game always have a winner except for draw games !
	 * 
	 * @param game
	 * @return true if he's present, false otherwise.
	 */
	public static boolean isPresentWinner(DisplayTableGame game) {
		return game.getHandValue().intValue() == 0 || game.isPlayer1Win() || game.isPlayer2Win() || game.isPlayer3Win() || game.isPlayer4Win();
	}

	/**
	 * Test if a loser is present.
	 * 
	 * @param game
	 * @return true if he's present, false otherwise.
	 */
	public static boolean isPresentLoser(DisplayTableGame game) {
		return game.getHandValue().intValue() == 0 || game.isSelfpick() || game.isPlayer1Lose() || game.isPlayer2Lose() || game.isPlayer3Lose()
				|| game.isPlayer4Lose();
	}
}
