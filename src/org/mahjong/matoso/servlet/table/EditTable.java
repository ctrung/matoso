/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.GameResult;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.display.DisplayTableGame;
import org.mahjong.matoso.form.EditTableForm;
import org.mahjong.matoso.service.GameResultService;
import org.mahjong.matoso.service.GameService;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Show the table's form to input games results.
 * 
 * @author ctrung
 * @date 7 mars 2010
 */
public final class EditTable extends MatosoServlet {
	
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = Logger.getLogger(EditTable.class);
	
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		Table table 				= null;
		Integer tableId 			= null;
		List<DisplayTableGame> dtgs = null;
		
		// PUT the table in the request attrs.
		try {
			tableId = NumberUtils.getInteger(request.getParameter(RequestCst.REQ_PARAM_TABLE_ID));
			table = TableService.getTable(tableId);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE, table);
		} catch (FatalException e) {
			LOGGER.error("Can't retrieve table : " + e.getMessage());
		}
		
		// PUT the displayed games data in the request attrs.
		try {
			// we calculate the games data to be displayed 
			dtgs = GameService.getDisplayedTableGames(table);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_DISPLAY_GAMES, dtgs);
		} catch (FatalException e) {
			LOGGER.error("Can't retrieve table games : " + e.getMessage());
		}

		// PUT the result in the request attrs.
		try {
			GameResult results = GameResultService.getByTable(table);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_RESULT, results);
		} catch (FatalException e) {
			LOGGER.error("Can't retrieve table game result : " + e.getMessage());
		}
		

		// Get the tournament
		Tournament tournament = super.getTournament(request);
		request.setAttribute("tournament", tournament);
		
		
		EditTableForm form = new EditTableForm();
		// Player names
		form.setPlayer1Name(table.getPlayer1().getLastname());
		form.setPlayer2Name(table.getPlayer2().getLastname());
		form.setPlayer3Name(table.getPlayer3().getLastname());
		form.setPlayer4Name(table.getPlayer4().getLastname());
		// Player first names
		form.setPlayer1FirstName(table.getPlayer1().getFirstname());
		form.setPlayer2FirstName(table.getPlayer2().getFirstname());
		form.setPlayer3FirstName(table.getPlayer3().getFirstname());
		form.setPlayer4FirstName(table.getPlayer4().getFirstname());
		// EMAs
		form.setPlayer1Ema(table.getPlayer1().getEma());
		form.setPlayer2Ema(table.getPlayer2().getEma());
		form.setPlayer3Ema(table.getPlayer3().getEma());
		form.setPlayer4Ema(table.getPlayer4().getEma());
		// Team names
		Team team1 = TeamService.getTeamForPlayer(table.getPlayer1(), tournament);
		form.setPlayer1Team(team1 == null ? "" : team1.getName());
		Team team2 = TeamService.getTeamForPlayer(table.getPlayer2(), tournament);
		form.setPlayer2Team(team2 == null ? "" : team2.getName());
		Team team3 = TeamService.getTeamForPlayer(table.getPlayer3(), tournament);
		form.setPlayer3Team(team3 == null ? "" : team3.getName());
		Team team4 = TeamService.getTeamForPlayer(table.getPlayer4(), tournament);
		form.setPlayer4Team(team4 == null ? "" : team4.getName());
		// Player numbers
		form.setPlayer1TournamentNumber(table.getPlayer1().getTournamentNumber().toString());
		form.setPlayer2TournamentNumber(table.getPlayer2().getTournamentNumber().toString());
		form.setPlayer3TournamentNumber(table.getPlayer3().getTournamentNumber().toString());
		form.setPlayer4TournamentNumber(table.getPlayer4().getTournamentNumber().toString());
		
		request.setAttribute("EditTableForm", form);
		
		return ServletCst.REDIRECT_TO_TABLE;
	}

}
