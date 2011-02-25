/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.player;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.display.TeamShuffling;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.servlet.team.TeamShuffleGo;
import org.mahjong.matoso.servlet.team.TeamShuffleValidate;
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
			LOGGER.debug("=>serv");
		String s = request.getParameter(RequestCst.REQ_PARAM_NB_PLAYERS);
		if (s == null || s.length() == 0)
			return ServletCst.REDIRECT_TO_PLAYER_ADD_FORM_SERVLET;
		int nbPlayers = Integer.parseInt(s);
		String playerTeam = "team ";
		String team = "";
		Player player;

		Tournament tournament = super.getTournament(request);
		boolean isTeam = tournament.isTeamActivate();
		for (int i = 1; i < nbPlayers + 1; i++) {
			player = new Player("player", "" + i, "fr", "ema" + i, "");
			player.setTournamentNumber(i + 1);
			if (isTeam)
				if ((i - 1) % 4 == 0)
					team = playerTeam + ((i + 3) / 4);
			try {
				TournamentService.addPlayer(player, tournament);
				if (isTeam)
					TeamService.addPlayerToTeam(tournament, team, player);
			} catch (FatalException e) {
				LOGGER.error("unable to save the player " + player, e);
			}
		}
		
		// shuffle players to create new teams
		if (isTeam) {
			List<TeamShuffling> tShList = TeamShuffleGo.generateNewShuffling(tournament);
			TeamShuffleValidate.saveShuffling(tShList);
		}
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("<=serv");
		return ServletCst.REDIRECT_TO_TABLE_FILL_SERVLET;
	}
}
