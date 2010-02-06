package org.mahjong.matoso.servlet.tournament;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Delete a tournament by its name.
 */
public final class DeleteTournament extends MatosoServlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(DeleteTournament.class);

	public String serve (HttpServletRequest request, HttpServletResponse response) throws FatalException {
		String name = request.getParameter(RequestCst.REQ_PARAM_TOURNAMENT_NAME);
		LOGGER.debug("name=" + name);
		try {
			TournamentService.deleteTournament(name);
		} catch (FatalException e) {
			LOGGER.error("error in delete tournament : " + name, e);
		}
		return ServletCst.REDIRECT_TO_TOURNAMENT_LIST_SERVLET;
	}
	
}
