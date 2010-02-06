package org.mahjong.matoso.servlet.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.GameResult;
import org.mahjong.matoso.bean.Penalty;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.display.DisplayTableGame;
import org.mahjong.matoso.service.GameResultService;
import org.mahjong.matoso.service.GameService;
import org.mahjong.matoso.service.PenaltyService;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Servlet implementation class EditTable
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
		
		// PUT the penalties in the request attrs.
		try {
			Penalty penalty = PenaltyService.getByTable(table);
			request.setAttribute(RequestCst.REQ_ATTR_TABLE_PENALTY, penalty);
		} catch (FatalException e) {
			LOGGER.error("Can't retrieve table penalties : " + e.getMessage());
		}
		
		return ServletCst.REDIRECT_TO_TABLE;
	}

}
