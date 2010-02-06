package org.mahjong.matoso.servlet.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.service.TournamentService;
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
 * @author Nicolas POCHIC
 */
public final class FillTables extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(FillTables.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		if (LOG.isDebugEnabled())
			LOG.debug("=>serve");

		// Get the tournament
		String name = (String) request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT);

		if (LOG.isDebugEnabled())
			LOG.debug("tournament = " + name);

		Tournament tournament = TournamentService.getByName(name);

		// Get the number of wanted rounds
		String parameterRound = request.getParameter(RequestCst.REQ_PARAM_ROUND);
		int nbMaxRounds;
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

		// Save the tables
		for (Table table : tables) {
			table.setTournament(tournament);
			try {
				HibernateUtil.save(table);
			} catch (FatalException e) {
				LOG.error("save error table : " + table, e);
			}
		}

		// Add the rounds to the request for the JSP
		try {
			request.setAttribute("rounds", TournamentService.getTables(tournament));
		} catch (HibernateException e) {
			throw new FatalException(e);
		}

		if (LOG.isDebugEnabled())
			LOG.debug("<=serve");

		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD;
	}
}