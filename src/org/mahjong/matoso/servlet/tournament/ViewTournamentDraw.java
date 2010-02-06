/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.tournament;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.DrawService;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * View all the players draw.
 * 
 * @author ctrung
 * @date 03 Dec. 2009
 */
public class ViewTournamentDraw extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {
		
		String tName = (String) request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT);
		
		// load the tournament
		Tournament t = TournamentService.getByName(tName);
		
		// load all players 
		List<Player> ps = PlayerService.getListFromTournament(t);
		request.setAttribute("players", ps);
		
		// for each player, load its draw
		DrawService.initDraw(ps);
		
		return ServletCst.REDIRECT_TO_TOURNAMENT_DRAW;
	}

}
