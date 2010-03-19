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

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.PlayerService;
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
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {
		
		Integer id = NumberUtils.getInteger(request.getParameter("id")); 
		Player player = PlayerService.getById(id);
		request.setAttribute("player", player);
		
		// TODO : add a proper mapping for Team property on Player
		Tournament tournament = super.getTournament(request);
		request.setAttribute("team", TeamService.getTeamForPlayer(player, tournament));
		
		return ServletCst.REDIRECT_TO_PLAYER_EDIT_FORM;
	}

}
