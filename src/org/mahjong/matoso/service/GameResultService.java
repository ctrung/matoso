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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mahjong.matoso.bean.GameResult;
import org.mahjong.matoso.bean.Penalty;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.display.DisplayTableGame;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * The service layer to work with GameResultObjects.
 *
 * @author ctrung
 * @date 15 août 2009
 */
public abstract class GameResultService {

	private static Logger LOGGER = Logger.getLogger(GameResultService.class);
	
	/**
	 * Get a table results.
	 * 
	 * @param table the table
	 * 
	 * @return the table's result, never <code>null</code>
	 */
	public static GameResult getByTable(Table table) throws FatalException {
		Session s = HibernateUtil.getSession();
		Query q = s.createQuery("select r from Table as t inner join t.result as r where t = :table");
		q.setParameter("table", table);
		
		GameResult r = (GameResult) q.uniqueResult();
		if(r==null) r = new GameResult();
		
		return r;
	}

	/**
	 * Try to collect a table points or score.
	 * If collection succeeds, the GameResult is updated.
	 * 
	 * @param paramName parameter name
	 * @param paramValue parameter value
	 * 
	 * @param results 
	 * 
	 * @return true if collection succeeds, that is to say the parameter was table points or score data, false otherwise.
	 */
	public static boolean collectTablePointsOrTableScore(String paramName, String paramValue, GameResult results) {
		
		if(paramName==null) return false;
		
		boolean found = true;
		
		if(paramName.equals("score1")) results.setScorePlayer1(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("score2")) results.setScorePlayer2(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("score3")) results.setScorePlayer3(NumberUtils.getInteger(paramValue));
		else if(paramName.equals("score4")) results.setScorePlayer4(NumberUtils.getInteger(paramValue));
		
		else if(paramName.equals("points1")) results.setPointsPlayer1(NumberUtils.getDouble(paramValue));
		else if(paramName.equals("points2")) results.setPointsPlayer2(NumberUtils.getDouble(paramValue));
		else if(paramName.equals("points3")) results.setPointsPlayer3(NumberUtils.getDouble(paramValue));
		else if(paramName.equals("points4")) results.setPointsPlayer4(NumberUtils.getDouble(paramValue));
		else found = false;
		
		return found;
	}

	/**
	 * Add/update a table's results.<br>
	 * Also does the boring job of auto calculation if you want to.
	 * 
	 * @param table 
	 * @param results  
	 * @param gamesData needed if you asked for auto calculation... assert is not null
	 * @param penalty the penalty to substract some points
	 * @param autoCalculate do you want the boring job done ;) ? 
	 * 
	 * @throws FatalException 
	 */
	public static void addOrUpdate(Table table, GameResult results,
			List<DisplayTableGame> gamesData, Penalty penalty, boolean autoCalculate) throws FatalException {
		
		Iterator<DisplayTableGame> gamesDataIt 	= null;
		DisplayTableGame aGameData				= null;
		
		if(autoCalculate) {
			assert(gamesData != null);
			
			// reinitialise... 
			results.setScorePlayer1(0);
			results.setScorePlayer2(0);
			results.setScorePlayer3(0);
			results.setScorePlayer4(0);
			
			// just loop on games data to update the final scores...
			gamesDataIt = gamesData.iterator();
			while (gamesDataIt.hasNext()) {
				aGameData = gamesDataIt.next();
				
				if(aGameData != null) {
					addInScore(results, aGameData);
				}
			}
			
			// count the penalty in 
			addPenaltyInScore(results, penalty);
		} 
		
		// generate the table points...
		allocateTablePoints(results);
		
		// save
		table.setResult(results);
		HibernateUtil.save(table);
		
	}

	/**
	 * Update each players final scores by adding eventual penalty points.
	 * <br>Penalties are supposed to be nehative values...
	 * 
	 * @param results each players' score must not be null.
	 * @param penalty
	 */
	private static void addPenaltyInScore(GameResult results, Penalty penalty) {
		
		if(penalty == null) return;
		
		Integer p = null;
		
		p = penalty.getPenaltyPlayer1();
		if(p != null) {
			assert results.getScorePlayer1() != null;
			results.setScorePlayer1( results.getScorePlayer1() + p );
		}
		
		p = penalty.getPenaltyPlayer2();
		if(p != null) {
			assert results.getScorePlayer2() != null;
			results.setScorePlayer2( results.getScorePlayer2() + p );
		}
		
		p = penalty.getPenaltyPlayer3();
		if(p != null) {
			assert results.getScorePlayer3() != null;
			results.setScorePlayer3( results.getScorePlayer3() + p );
		}
		
		p = penalty.getPenaltyPlayer4();
		if(p != null) {
			assert results.getScorePlayer4() != null;
			results.setScorePlayer4( results.getScorePlayer4() + p );
		}
	}

	/**
	 * Allocate table points based on table scores.<br>
	 * In case of equality among players, table points are shared.<br>
	 * 
	 * Example : P1, P2 and P3 have same score 10 and P4 has 5. The result is :
	 * <ul>
	 * <li>P1, P2 and P3 share 7 points (4+2+1) ~ each one get 2.33 </li>
	 * <li>P4 get 0 point</li>
	 * </ul>
	 * 
	 * @param results 
	 * 
	 * @see ApplicationCst#RANK_POINTS_FIRST
	 * @see ApplicationCst#RANK_POINTS_SECOND
	 * @see ApplicationCst#RANK_POINTS_THIRD
	 * @see ApplicationCst#RANK_POINTS_FOURTH
	 */
	private static void allocateTablePoints(GameResult results) {

		// if one null, do nothing
		if(results.getScorePlayer1()==null || results.getScorePlayer2()==null ||
				results.getScorePlayer3()==null || results.getScorePlayer4()==null) {
			LOGGER.debug("at least one null score detected, not allocating...");
			return;
		}
		
		List<Integer> allScores 	= new ArrayList<Integer>();

		/*
		 *  ascending sort
		 */
		allScores.add(results.getScorePlayer1());
		allScores.add(results.getScorePlayer2());
		allScores.add(results.getScorePlayer3());
		allScores.add(results.getScorePlayer4());
		Collections.sort(allScores);
	
		/*
		 *  allocate ranks 
		 */
		
		int[] rankingPoints = new int[]{ApplicationCst.RANK_POINTS_FOURTH, ApplicationCst.RANK_POINTS_THIRD, 
			ApplicationCst.RANK_POINTS_SECOND, ApplicationCst.RANK_POINTS_FIRST};
		
		// a player is reprensented by its score object
		
		// algorithm is :
		// 1. we try to match equal 
		// 2. as long as we can, we memorize the total growing points to share and the players
		// (represented by their scores objects)
		// 3. if a player can't share, we allocate the shared points and create a new sharing with this player
		
		int i = 3;
		List<Integer> listSharedScores = new ArrayList<Integer>();
		int sharedPoints = 0;
		
		while(i>=0) {
			
			// points are shared, memorizing who and how many points
			if(canShare(listSharedScores, allScores.get(i))) {
				sharedPoints += rankingPoints[i];
				listSharedScores.add(allScores.get(i));
				
				i--;
				continue;
			}
			
			// time to allocate the others
			allocateBetweenPlayers(sharedPoints, listSharedScores, results);
			
			// beginning a new sharing
			listSharedScores = new ArrayList<Integer>();
			listSharedScores.add(allScores.get(i));
			sharedPoints = rankingPoints[i];
			i--;
		}
		
		// the last sharing
		allocateBetweenPlayers(sharedPoints, listSharedScores, results);
	}

	/**
	 * Allocate a total of points between players.
	 * It can be strange but players are represented by an integer score.
	 * 
	 * @param totalPtsToShare
	 * @param players
	 * @param results
	 */
	private static void allocateBetweenPlayers(int totalPtsToShare,
			List<Integer> players, GameResult results) {
		
		
		double points = ((double)totalPtsToShare) / players.size();
		boolean pts1 = false;
		boolean pts2 = false;
		boolean pts3 = false;

		for(int j=0; j<players.size(); j++) {
			
			// players are represented by their scores
			
			if(players.get(j).equals(results.getScorePlayer1()) && !pts1 ){
				results.setPointsPlayer1(points);
				pts1 = true;
			} else if(players.get(j).equals(results.getScorePlayer2()) && !pts2){
				results.setPointsPlayer2(points);
				pts2 = true;
			} else if(players.get(j).equals(results.getScorePlayer3()) && !pts3) {
				results.setPointsPlayer3(points);
				pts3 = true;
			} else{ 
				results.setPointsPlayer4(points);
			}
		}
		LOGGER.debug("allocating " + totalPtsToShare + " between " + players.size() + " players (" + points + " each)");
	}

	/**
	 * Test if a player can share with a list of players.<br>
	 * It can be strange but players are represented by an integer score.
	 * 
	 * @param players
	 * @param player
	 * 
	 * @see GameResultService#allocateTablePoints(GameResult)
	 * 
	 * @return true if he can share or if he's the first, false otherwise.
	 */
	private static boolean canShare(List<Integer> players,
			Integer player) {
		
		if(players==null || players.isEmpty()) {
			return true;
		}
		
		return players.get(0).intValue() == player.intValue();
	}

	/**
	 * Update players final scores by adding a game's points
	 * 
	 * @param scoringsPoints the final scores to update
	 * @param gameData the game data
	 */
	private static void addInScore(GameResult scoringsPoints, DisplayTableGame gameData) {
		
		Integer theScore = null;
		
		theScore = gameData.getPlayer1Score();
		if(theScore != null) scoringsPoints.setScorePlayer1( theScore + scoringsPoints.getScorePlayer1() );
		
		theScore = gameData.getPlayer2Score();
		if(theScore != null) scoringsPoints.setScorePlayer2( theScore + scoringsPoints.getScorePlayer2() );

		theScore = gameData.getPlayer3Score();
		if(theScore != null) scoringsPoints.setScorePlayer3( theScore + scoringsPoints.getScorePlayer3() );
		
		theScore = gameData.getPlayer4Score();
		if(theScore != null) scoringsPoints.setScorePlayer4( theScore + scoringsPoints.getScorePlayer4() );
	}

	/**
	 * Delete a game result of a table.
	 * 
	 * @param table
	 * @param result
	 * 
	 * @throws FatalException 
	 */
	public static void delete(Table table, GameResult result) throws FatalException {
		table.setResult(null);
		HibernateUtil.delete(result);
	}

	/**
	 * Test if a table's result is empty, ie no scores.
	 * 
	 * @param results
	 * 
	 * @return true if result is empty, false otherwise.
	 */
	public static boolean isEmpty(GameResult results) {
		return results==null || results.getScorePlayer1()==null || results.getScorePlayer2()==null 
		|| results.getScorePlayer3()==null || results.getScorePlayer4()==null;
	}

	/**
	 * Test if the tables scores are valid, meaning the sum is equal to 0.
	 * 
	 * @param results
	 * @return true if the sum equals to 0, false otherwise.
	 */
	public static boolean isTableScoresValid(GameResult results) {
		return results.getScorePlayer1() + results.getScorePlayer2() 
		+ results.getScorePlayer3() + results.getScorePlayer4() == 0;
	}
	
}
