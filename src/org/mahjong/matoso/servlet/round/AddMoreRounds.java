package org.mahjong.matoso.servlet.round;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/** 
 * Servlet used to add a player to the current tournament. 
 */
public class AddMoreRounds extends MatosoServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(AddMoreRounds.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		Tournament tournament = super.getTournament(request);

		String nbroundS = request.getParameter("nbrounds");
		int nbRound = Integer.parseInt(nbroundS);

		List<Table> tables = TableService.getAllByTournament(tournament);

		RoundService.addMoreRounds(tables, tournament.getPlayers(), tournament.getTeams(), nbRound, 10000, 60);

		if (LOG.isDebugEnabled())
			LOG.debug("tables=" + tables.size());

		for (Table table : tables) {
			table.setTournament(tournament);
			HibernateUtil.save(table);
		}

		LOG.debug("TournamentService.getTables");

		try {
			request.setAttribute("rounds", TournamentService.getTables(tournament));
		} catch (HibernateException e) {
			throw new FatalException(e);
		} 

		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD;
	}
}
