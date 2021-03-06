/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Servlet used to add n players to the current tournament.
 * 
 * @author ctrung & Nicolas POCHIC
 */
public class AddPlayer extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(AddPlayer.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("=>serve");
		String nbPlayersParam = request.getParameter(RequestCst.REQ_PARAM_NB_PLAYERS);
		if (nbPlayersParam == null || nbPlayersParam.length() == 0)
			return ServletCst.REDIRECT_TO_PLAYER_ADD_FORM_SERVLET;
		int nbPlayers = Integer.parseInt(nbPlayersParam);
		Player player;
		Tournament tournament = super.getTournament(request);
		for (int i = 1; i < nbPlayers + 1; i++) {
			player = new Player("player", "" + i, "fr", "ema" + i, "");
			player.setTournamentNumber(i);
			try {
				TournamentService.addPlayer(player, tournament);
			} catch (FatalException e) {
				LOGGER.error("unable to save the player " + player, e);
			}
		}
		if (tournament.isTeamActivate())
			TeamService.buildsRandomTeams(tournament);

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("<=serve");
		return ServletCst.REDIRECT_TO_TABLE_FILL_SERVLET;
	}
}
