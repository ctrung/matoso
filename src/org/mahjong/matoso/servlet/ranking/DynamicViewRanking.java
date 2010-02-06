/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.ranking;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

public class DynamicViewRanking extends MatosoServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		HttpSession session  = request.getSession();
		// which tournament ?
		String name = (String) session.getAttribute(SessionCst.SES_ATTR_TOURNAMENT);
		Tournament tournament = TournamentService.getByName(name);
		assert (tournament != null);
		// The players ranking 
		List<Player> orderedPlayers = RankingService.getByTournament(tournament);
		session.setAttribute("ranking", orderedPlayers);
		// The tournament stats.
		session.setAttribute("tournamentStats", TournamentService.getTournamentStats(orderedPlayers));
		// The teams ranking 
		session.setAttribute("rankingTeam", RankingService.getTeamsForTournament(tournament));
		// Redefine the nb. of elements by page
		Integer newValue = NumberUtils.getInteger(request.getParameter("nbElementsByPage"));
		Integer inSessionValue = (Integer) session.getAttribute(SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE);
		if(newValue!=null) {
			session.setAttribute(SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE, newValue);
		} else if(inSessionValue==null) {
			session.setAttribute(SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE, ApplicationCst.NB_ELEMENTS_BY_PAGE_DEFAULT);
		}
		// go to jsp
		return ServletCst.REDIRECT_TO_DYNAMIC_VIEW_RANKING;
	}

}
