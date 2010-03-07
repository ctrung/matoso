/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.tournament;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Create a tournament and forward the user to a page asking 
 * to choose a method to fill the players.
 * 
 * @author ctrung
 * @date 21 oct. 2009
 */
@SuppressWarnings("serial")
public class CreateTournament extends MatosoServlet {

	private static final Logger LOGGER = Logger.getLogger(CreateTournament.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		String tournamentName = request.getParameter("tournament-name");
		String teamActivate = request.getParameter(RequestCst.REQ_PARAM_TEAM_ACTIVATE);
		boolean isTeam = teamActivate != null;
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("found in request, tournamentName=" + tournamentName + ", teamActivate=" + isTeam);
		
		Tournament tournament = TournamentService.createNewTournament(tournamentName, isTeam);
		
		request.getSession().setAttribute(SessionCst.SESSION_TOURNAMENT_ID, tournament.getId());
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("A new tournament with name=" + tournamentName + " id=" + tournament.getId() + 
					" has been created.");
		
		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET;
	}
}
