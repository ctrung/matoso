/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.table;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.GameResult;
import org.mahjong.matoso.bean.Penalty;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.constant.BundleCst;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.display.DisplayTableGame;
import org.mahjong.matoso.service.GameResultService;
import org.mahjong.matoso.service.GameService;
import org.mahjong.matoso.service.PenaltyService;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.I18nUtils;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.message.MatosoMessage;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Save the games and scores of a table
 *
 * @author ctrung
 * @date 16 août 2009
 */
public class SaveTableGamesAndScores extends MatosoServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger
	 */
	private static Logger LOGGER = Logger.getLogger(SaveTableGamesAndScores.class);
	
	@SuppressWarnings("unchecked")
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		Table table 					= null;
		Enumeration<String> paramNames 	= request.getParameterNames();
		String paramName				= null;
		String paramValue				= null;
		GameResult results			= null;
		boolean autoCalculate			= false;
		Penalty penalty					= null;
		
		List<DisplayTableGame> rcvdGamesData	= new ArrayList<DisplayTableGame>();
		DisplayTableGame gameData				= null;
		
		/*
		 *  1. Retrieving existing data in DB
		 */
		//  -> Table
		table = TableService.getTable(
				NumberUtils.getInteger(request.getParameter(RequestCst.REQ_PARAM_TABLE_ID)));
		//  -> The table points and scores
		results = GameResultService.getByTable(table);
		assert results!=null;
		//  -> The penalty
		penalty = PenaltyService.getByTable(table);
		assert penalty!=null;
		
		/*
		 *  2. Collect the user inputs via the request parameters...
		 *     DisplayTableGame is used to collect data
		 */
		// initialization
		for(int i=0; i<ApplicationCst.MAX_GAMES_FOR_ONE_SESSION; i++) rcvdGamesData.add(new DisplayTableGame());
		
		// Collecting now...
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			boolean found = false;
			
			if (LOGGER.isDebugEnabled()) LOGGER.debug(paramName + " = " + paramValue);
			
			// is this parameter...
			
			// a game data ?
			// (meaning selfpick info, hand value, who won or who gave) 
			found = GameService.collectGameData(paramName, paramValue, rcvdGamesData);
			if(found) continue;
			
			// or a table points or score data ?
			found = GameResultService.collectTablePointsOrTableScore(paramName, paramValue, results);
			if(found) continue;
			
			// or a penalty data ?
			found = PenaltyService.collectPenaltyData(paramName, paramValue, penalty);
			if(found) continue;
			
			// or perhaps the auto calculate flag ?
			autoCalculate = autoCalculate || paramName.equals("autoCalculate");
		}
		
		results.setAutoCalculate(autoCalculate);
		
		/*
		 * 3. Matoso great feature, calculate the players points for each game
		 */
		for(int i=0; i<rcvdGamesData.size(); i++) {
			GameService.calculatePlayersPointsForAGame(rcvdGamesData.get(i));
		}		
		
		/*
		 * 4. Validating user input data
		 */
		MatosoMessages userMsgs = new MatosoMessages();
		request.setAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES, userMsgs);
		
		// tables scores
		if(!autoCalculate && !GameResultService.isEmpty(results) && !GameResultService.isTableScoresValid(results)) {
			userMsgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("table.errors.scores.not.valid"));
		}
		
		// games data
		for(int i=0; i<rcvdGamesData.size(); i++) {
			gameData = rcvdGamesData.get(i);
			
			if(gameData.getHandValue() != null) {
				// hand-value = 0 OK, draw game
				if(gameData.getHandValue() > 0 && gameData.getHandValue() < 8) {
					String mesg = I18nUtils.getMessage("table.errors.hand.value.invalid", Integer.toString(i+1), I18nUtils.getWind(i));
					userMsgs.addMessage(MatosoMessage.ERROR, mesg);
				}
				
				boolean winnerPresent = GameService.isPresentWinner(gameData);
				boolean loserPresent = GameService.isPresentLoser(gameData);
				
				if(!winnerPresent || !loserPresent) {
					String mesg = null;
					if(!winnerPresent && !loserPresent) 
						mesg = I18nUtils.getMessage("table.errors.winner.and.loser.not.present", Integer.toString(i+1), I18nUtils.getWind(i));
					else if(!winnerPresent) 
						mesg = I18nUtils.getMessage("table.errors.winner.not.present", Integer.toString(i+1), I18nUtils.getWind(i));
					else 
						mesg = I18nUtils.getMessage("table.errors.loser.not.present", Integer.toString(i+1), I18nUtils.getWind(i));
				
					userMsgs.addMessage(MatosoMessage.ERROR, mesg);
				}
			}
		}		
		
		if(MatosoMessages.isNotEmpty(userMsgs)) {
			LOGGER.info("Validation failed, forwarding to round and table JSP");
			request.setAttribute(RequestCst.REQ_ATTR_TABLE, table);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_DISPLAY_GAMES, rcvdGamesData);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_RESULT, results);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_PENALTY, penalty);
			return ServletCst.REDIRECT_TO_TABLE;
		}
		
		/*
		 * 5. Persist the data
		 */
		//  -> Games data
		boolean noGames = true;
		for(int i=0; i<rcvdGamesData.size(); i++) {
			gameData = rcvdGamesData.get(i);
			
			// delete old game (if exists)
			if(gameData.getHandValue()==null) {
				if(TableService.hasGame(table, i)) {
					LOGGER.info("No more game number " + i + " for table " + table.getId() + " , deleting old game.");
					GameService.deleteGame(table, i);
				}
				continue;
			}
			
			// at least one game at this table !
			noGames = false;
			
			GameService.addOrUpdateResult(table, gameData, i+1);
		}
		// -> Table points and score
		// Here comes MATOSO power again, it can sums all the points to get the final table points and scores.
		// (only if autoCalculate activated)
		if(noGames && GameResultService.isEmpty(results)) {
			LOGGER.info("No more games for this table, deleting eventual existing results.");
			GameResultService.delete(table, results);
		} else {
			GameResultService.addOrUpdate(table, results, rcvdGamesData, penalty, autoCalculate);
		}
		// -> Penalty
		if(noGames && PenaltyService.isEmpty(penalty)) {
			LOGGER.info("No more games for this table, deleting eventual existing penalty.");
			PenaltyService.delete(table, penalty);
		} else {
			PenaltyService.addOrUpdate(table, penalty);
		}
		
		
		// info message
		userMsgs.addMessage(MatosoMessage.INFO, BundleCst.BUNDLE.getString("table.info.save.success"));
		
		// warning message
		if(!autoCalculate && GameResultService.isEmpty(results) ) {
			userMsgs.addMessage(MatosoMessage.WARNING, BundleCst.BUNDLE.getString("table.warning.result.not.calculated"));
		}
		
		LOGGER.info("Forwarding to round and table servlet");
		return ServletCst.REDIRECT_TO_TABLE_SERVLET;
	}

}
