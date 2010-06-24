/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.mahjong.matoso.bean.Game;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Table service layer methods.
 *
 * @author ctrung
 * @date 24 janv. 2010
 */
public class TableService {

	public static Player getPlayer(Table table, String playerEMA) {
		for (Player player : table.getListPlayers())
			if (player.getEma().equals(playerEMA))
				return player;
		return null;
	}

	public static void addPlayerToTable(Player player, Table table) {
		// Add the player
		if (table.getPlayer1() == null)
			table.setPlayer1(player);
		else if (table.getPlayer2() == null)
			table.setPlayer2(player);
		else if (table.getPlayer3() == null)
			table.setPlayer3(player);
		else if (table.getPlayer4() == null)
			table.setPlayer4(player);
	}

	/**
	 * Get the table with the given id
	 * 
	 * @param tableId
	 * @return
	 * @throws FatalException
	 */
	public static Table getTable(Integer tableId) throws FatalException {
		// Fetching the players to avoid n+1 queries
		return (Table) HibernateUtil.getSession().createCriteria(Table.class)
			.add(Restrictions.eq("id", tableId))
			.setFetchMode("player1", FetchMode.JOIN)
			.setFetchMode("player2", FetchMode.JOIN)
			.setFetchMode("player3", FetchMode.JOIN)
			.setFetchMode("player4", FetchMode.JOIN)
			.uniqueResult();
	}

	/**
	 * Retrieve the number of games played at this table.
	 * 
	 * @param table
	 * 
	 * @return The number of games played at this table
	 * 
	 * @throws FatalException 
	 */
	public static int getNumberOfGames(Table table) throws FatalException {
		return ((Integer)(HibernateUtil.getSession().createCriteria(Game.class)
			.setProjection( Projections.rowCount())
			.uniqueResult())).intValue();
	}

	/**
	 * Get all the tables from a tournament.
	 * 
	 * @return a never <code>null</code> list of tables
	 * 
	 * @throws FatalException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Table> getAllByTournament(Tournament tournament) throws FatalException {
		// Fetching the players to avoid n+1 queries
		return HibernateUtil.getSession().createCriteria(Table.class)
			.setFetchMode("games", FetchMode.JOIN)
			.setFetchMode("result", FetchMode.JOIN)
			.setFetchMode("player1", FetchMode.JOIN)
			.setFetchMode("player2", FetchMode.JOIN)
			.setFetchMode("player3", FetchMode.JOIN)
			.setFetchMode("player4", FetchMode.JOIN)
			.add(Restrictions.eq("tournament", tournament))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) // allow distinct results for a query with outer join fetching 
			.list();
	}

	/**
	 * Test if the game number X at the table was played.
	 * 
	 * @param table assert is not null.
	 * @param gameNumber index starts at 0 ... n-1
	 * 
	 * @return true if the game number X was effectively played, false otherwise.
	 */
	public static boolean hasGame(Table table, int gameNumber) {
		
		assert(table != null);
		
		boolean gameNotInside = table.getGames()==null || table.getGames().size()==0 || 
				gameNumber >= table.getGames().size() ;
		
		return !gameNotInside;
	}

	/**
	 * Test if the table has saved games.
	 * 
	 * @param table
	 * @return true if the game number X was effectively played, false
	 *         otherwise.
	 */
	public static boolean hasSavedGame(Table table) {
		boolean savedGame = table != null && table.getGames() != null && !table.getGames().isEmpty();
		return savedGame;
	}

}
