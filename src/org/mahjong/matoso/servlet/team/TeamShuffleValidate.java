/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.display.TeamShuffling;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Save the shuffling into db.
 * 
 * @author ctrung
 * @date 24 févr. 2011
 */
public class TeamShuffleValidate extends MatosoServlet {

	private static final long serialVersionUID = 3389196833861253202L;

	/* (non-Javadoc)
	 * @see org.mahjong.matoso.servlet.MatosoServlet#serve(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {

		List<TeamShuffling> tShList = (List<TeamShuffling>) request.getSession().getAttribute("shuffling");
		saveShuffling(tShList);
		
		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET;
	}

	public static void saveShuffling(List<TeamShuffling> tShList) throws FatalException {
		if(tShList != null) {
			for (Iterator<TeamShuffling> it = tShList.iterator(); it.hasNext();) {
				TeamShuffling tsItem = it.next();
				List<Player> players = new ArrayList<Player>();
				players.add(PlayerService.getById(tsItem.getPlayer1().getId()));
				players.add(PlayerService.getById(tsItem.getPlayer2().getId()));
				players.add(PlayerService.getById(tsItem.getPlayer3().getId()));
				players.add(PlayerService.getById(tsItem.getPlayer4().getId()));
				
				TeamService.replaceAllPlayers(tsItem.getTeam().getId(), players);
			}
		}
	}

}
