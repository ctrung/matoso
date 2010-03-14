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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Servlet used to fill the tables with the players.<br/>
 * Use :
 * <ul>
 * <li>the attribute "tournament" in the session to get the tournament.</li>
 * <li>the parameter "round" in the request to get the number of wanted rounds</li>
 * </ul>
 * 
 * @author Nicolas POCHIC
 */
public final class FillTables extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(FillTables.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		if (LOG.isDebugEnabled())
			LOG.debug("=>serve");

		// Get the tournament
		Tournament tournament = super.getTournament(request);
		
		// Get the number of wanted rounds
		String parameterRound = request.getParameter(RequestCst.REQ_PARAM_ROUND);

		if (LOG.isDebugEnabled())
			LOG.debug("parameterRound1 =" + parameterRound);

		int nbMaxRounds;
		if (parameterRound == null || parameterRound.length() == 0) {
			parameterRound = (String) request.getAttribute(RequestCst.REQ_PARAM_ROUND);

			if (LOG.isDebugEnabled())
				LOG.debug("parameterRound2 =" + parameterRound);
		}

		if (parameterRound != null && parameterRound.length() != 0)
			nbMaxRounds = Integer.parseInt(parameterRound);
		else
			nbMaxRounds = 100;

		if (LOG.isDebugEnabled())
			LOG.debug("nbMaxRounds =" + nbMaxRounds);

		// Fill the tables
		List<Table> tables = RoundService.fillTables(tournament.getPlayers(), tournament.getTeams(), (int) Math.pow(10, 6), 60, nbMaxRounds);

		if (LOG.isDebugEnabled())
			LOG.debug("tables=" + tables.size());

		// Save tables
		Map<Integer, List<Table>> mapRoundTables = new HashMap<Integer, List<Table>>();
		for (Table table : tables) {
			table.setTournament(tournament);
			try {
				HibernateUtil.save(table);
			} catch (FatalException e) {
				LOG.error("save error table : " + table, e);
			}
			
			// filling the round map
			int round = table.getRoundNbr();
			if(mapRoundTables.get(round) == null) mapRoundTables.put(round, new ArrayList<Table>());
			mapRoundTables.get(round).add(table);
		}

		// Save the rounds
		for(Integer roundInt : mapRoundTables.keySet()) {
			List<Table> roundTables = mapRoundTables.get(roundInt);
			RoundService.create(tournament, roundInt, roundTables);
		}
		
		if (LOG.isDebugEnabled())
			LOG.debug("<=serve");

		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET;
	}
}