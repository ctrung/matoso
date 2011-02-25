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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.display.TeamShuffling;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Do the shuffling.
 * 
 * @author ctrung
 * @date 24 févr. 2011
 */
public class TeamShuffleGo extends MatosoServlet {

	private static final long serialVersionUID = 5427409881215178597L;

	/* (non-Javadoc)
	 * @see org.mahjong.matoso.servlet.MatosoServlet#serve(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {
		
		// get the players
		Tournament tournament = getTournament(request);
		request.setAttribute("tournament", tournament);
		
		// generate new shuffling
		List<TeamShuffling> tShList = generateNewShuffling(tournament);
		request.getSession().setAttribute("shuffling", tShList);
		
		return "/jsp/team/shuffle.jsp";
	}

	public static  List<TeamShuffling> generateNewShuffling(Tournament tournament) throws FatalException {
		
		List<TeamShuffling> tShList = new ArrayList<TeamShuffling>();
		
		List<Player> players = PlayerService.getListFromTournament(tournament);
		
		if(players!=null) {
			// shuffle the players
			Collections.shuffle(players);
			
			// fill the shuffle object
		
		
			for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
				Player p = iterator.next();
				addPlayerToShuffling(p, tShList);
			}
			
			// put the team names right
			putTeams(players, tShList);
		}
		
		return tShList;
	}

	/**
	 * avoid to have two same teams 
	 * @param players
	 * @param tShList
	 */
	private static void putTeams(List<Player> players,
			List<TeamShuffling> tShList) {

		// retrieve teams
		List<Team> teams = new ArrayList<Team>();
		for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
			Player p = iterator.next();
			
			boolean found = false;
			for(int i=0; i<teams.size(); i++) {
				if(teams.get(i).getName().equals(p.getTeam().getName())) found = true;
			}
			if(!found) {
				teams.add(p.getTeam());
			}
		}
		
		// sort them
		Collections.sort(teams, new Comparator<Team>(){
			public int compare(Team o1, Team o2) {
				if(o1==null && o2==null) return 0;
				if(o1 == null) return -1;
				if(o2 == null) return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		// fill teams in shuffling
		for (int i=0; i<tShList.size(); i++) {
			TeamShuffling ts = tShList.get(i);
			
			if(teams.size() > i) {
				ts.setTeam(teams.get(i));
			}
		}
		
	}

	/**
	 * baka method that append 4 players to make a team
	 * @param p
	 * @param tShList
	 */
	private static void addPlayerToShuffling(Player p, List<TeamShuffling> tShList) {
		
		TeamShuffling tsLast = null;
		boolean createNewTeam = false;
		
		// retrieve last item
		if(tShList.size()>0) {
			tsLast = tShList.get(tShList.size() -1);
			if(tsLast.getPlayer4() != null) {
				createNewTeam = true;
			}
		} else {
			createNewTeam = true;
		}
		
		// create new team ?
		if(createNewTeam) {
			tsLast = new TeamShuffling();
			tShList.add(tsLast);
		}
		
		// set the player
		tsLast.addPlayer(p);
		
	}
	
}
