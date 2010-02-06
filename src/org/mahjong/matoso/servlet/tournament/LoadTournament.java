package org.mahjong.matoso.servlet.tournament;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/** 
 * Load in the session the selected tournament 
 */
public class LoadTournament extends MatosoServlet {
	
	/** 
	 * Log 
	 */
	private static final Logger LOG = Logger.getLogger(LoadTournament.class);
	
	private static final long serialVersionUID = 1L;

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		String tournamentName = request.getParameter(RequestCst.REQ_PARAM_TOURNAMENT_NAME);
		if (tournamentName == null)
			tournamentName = (String) request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT);

		if (LOG.isDebugEnabled())
			LOG.debug(RequestCst.REQ_PARAM_TOURNAMENT_NAME + " = " + tournamentName);

		try {
			if (tournamentName != null) {
				try {
					Tournament tournament = TournamentService.getByName(tournamentName);
					if (tournament != null) {
						if (LOG.isDebugEnabled())
							LOG.debug("tournament id=" + tournament.getId());

						request.getSession().setAttribute(SessionCst.SES_ATTR_TOURNAMENT, tournamentName);

						LOG.debug("TournamentService.getTables");

						try {
							request.setAttribute("rounds", TournamentService.getTables(tournament));
						} catch (HibernateException e) {
							LOG.error("", e);
						} catch (FatalException e) {
							LOG.error("", e);
						}

						return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD;
					} else {
						LOG.error("tournament " + tournamentName + "not found");
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				} catch (FatalException e) {
					LOG.error("TournamentService.getByName(" + tournamentName, e);
				}
			} else {
				LOG.error("tournamentName is null");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (IOException e) {
			throw new FatalException(e);
		}
		
		return null;
	}
}
