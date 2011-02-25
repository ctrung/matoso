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
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.display.TeamShuffling;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Display the team shuffle screan.
 * 
 * @author ctrung
 * @date 24 févr. 2011
 */
public class TeamShuffle extends MatosoServlet {

	private static final long serialVersionUID = -2706394716537025941L;

	/* (non-Javadoc)
	 * @see org.mahjong.matoso.servlet.MatosoServlet#serve(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {

		// get the players
		Tournament tournament = getTournament(request);
		request.setAttribute("tournament", tournament);
		List<Player> players = PlayerService.getListFromTournament(tournament);
		
		// fill the shuffle object
		List<TeamShuffling> tShList = new ArrayList<TeamShuffling>();
		if(players!=null) {
			for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
				Player p = iterator.next();
				addPlayerToShuffling(p, tShList);
			}
			
			request.getSession().setAttribute("shuffling", tShList);
		}
		
		return "/jsp/team/shuffle.jsp";
	}

	/**
	 * @param p
	 * @param tShList
	 */
	public static void addPlayerToShuffling(Player p, List<TeamShuffling> tShList) {
		// do it the long way
		if(p.getTeam()!=null) {
			// look for the team first before creating it
			for (Iterator<TeamShuffling> iterator = tShList.iterator(); iterator.hasNext();) {
				TeamShuffling ts = iterator.next();
				if(ts.getTeam().getName().equals(p.getTeam().getName())) {
					ts.addPlayer(p);
					return;
				}
			}
			
			TeamShuffling ts = new TeamShuffling();
			ts.setTeam(p.getTeam());
			ts.addPlayer(p);
			tShList.add(ts);
		}
	}

}
