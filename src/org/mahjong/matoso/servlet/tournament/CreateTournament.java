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
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Create a tournament and ask the user to choose a method to fill the players.<br>
 * (creation only)
 * 
 * @author ctrung
 * @date 21 oct. 2009
 */

@SuppressWarnings("serial")
public class CreateTournament extends MatosoServlet {

	private static final Logger LOGGER = Logger.getLogger(CreateTournament.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		String tournamentName = request.getParameter(RequestCst.REQ_PARAM_TOURNAMENT_NAME);
		String teamActivate = request.getParameter(RequestCst.REQ_PARAM_TEAM_ACTIVATE);
		boolean isTeam = teamActivate != null;
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("tournamentName = " + tournamentName + " / teamActivate = " + isTeam);
		// Replace some characters (authorized : [a-zA-Z0-9 \.] )
		char c;
		String s;
		StringBuffer newName = new StringBuffer();
		for (int i = 0; i < tournamentName.length(); i++) {
			c = tournamentName.charAt(i);
			s = String.valueOf(c);
			if (!s.matches("[a-zA-Z0-9 \\.]"))
				newName.append(" ");
			else
				newName.append(s);

		}
		tournamentName = newName.toString();
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("tournamentName = " + tournamentName);
		TournamentService.createNewTournament(tournamentName, isTeam);
		request.getSession().setAttribute(SessionCst.SES_ATTR_TOURNAMENT, tournamentName);
		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET;
	}
}
