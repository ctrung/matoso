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
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Servlet used to edit a player to the current tournament.
 * 
 * @author ctrung
 * @date 21 oct. 2009
 */
public class SavePlayer extends MatosoServlet {
	
	private static final long serialVersionUID = 1L;

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		String name = request.getParameter(RequestCst.REQ_PARAM_PLAYER_NAME);
		String firstName = request.getParameter(RequestCst.REQ_PARAM_PLAYER_FIRSTNAME);
		String ema = request.getParameter(RequestCst.REQ_PARAM_PLAYER_EMA);
		String nationality = request.getParameter(RequestCst.REQ_PARAM_PLAYER_NATIONALITY);
		String mahjongtime = request.getParameter(RequestCst.REQ_PARAM_PLAYER_MAHJONGTIME);
		String playerTeam = request.getParameter(RequestCst.REQ_PARAM_PLAYER_TEAM);


		Integer id = NumberUtils.getInteger(request.getParameter("id")); 
		Player player = PlayerService.getById(id);

		request.setAttribute("player", player);
		
		if(player == null) {
			return ServletCst.REDIRECT_TO_PLAYER_EDIT_FORM;
		}
		
		// TODO : better way to get player's team
		Tournament tournament = super.getTournament(request);
		Team team = TeamService.getTeamForPlayer(tournament.getTeams(), player);
		if(team != null) {
			team.setName(playerTeam);
			HibernateUtil.save(team);
			
			request.setAttribute("team", team);
		}

		player.setLastname(name);
		player.setFirstname(firstName);
		player.setEma(ema);
		player.setCountry(nationality);
		player.setPseudo(mahjongtime);
		HibernateUtil.save(player);
		
		return ServletCst.REDIRECT_TO_PLAYER_EDIT_FORM;
	}
}
