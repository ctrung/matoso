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

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Display the edit player page.
 * 
 * @author ctrung
 * @date 5 déc. 2009
 */
public class EditPlayer extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		Integer id = NumberUtils.getInteger(request.getParameter("id"));
		Player player = PlayerService.getById(id);
		request.setAttribute(RequestCst.ATTR_PLAYER, player);

		Tournament tournament = super.getTournament(request);
		request.setAttribute("team", TeamService.getTeamForPlayer(player, tournament));

		List<Player> orderedPlayers = (List<Player>) request.getSession().getAttribute(SessionCst.ATTR_RANKING);
		if (request.getSession().getAttribute(SessionCst.ATTR_RANKING) == null) {
			orderedPlayers = RankingService.getByTournament(tournament);
			request.getSession().setAttribute(SessionCst.ATTR_RANKING, orderedPlayers);
		}
		Player playerResult = null;
		for (Player playerI : orderedPlayers)
			if (playerI != null && playerI.getId() != null && id.equals(playerI.getId())) {
				playerResult = playerI;
				break;
			}
		if (playerResult != null)
			request.setAttribute(RequestCst.ATTR_PLAYER_RESULT, playerResult);

		return ServletCst.REDIRECT_TO_PLAYER_EDIT_FORM;
	}
}