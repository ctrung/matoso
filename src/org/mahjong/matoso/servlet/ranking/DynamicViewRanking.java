/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.ranking;

import static org.mahjong.matoso.constant.SessionCst.ATTR_LAST_PLAYED_SESSION;
import static org.mahjong.matoso.constant.SessionCst.ATTR_RANKING;
import static org.mahjong.matoso.constant.SessionCst.ATTR_TOURNAMENT;
import static org.mahjong.matoso.constant.SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Display an dynamic view of the ranking by shifting the page every N seconds.
 * 
 * @author ctrung
 * @date 7 mars 2010
 */
public class DynamicViewRanking extends MatosoServlet {
	private static final long serialVersionUID = -6276513462234178966L;

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		HttpSession session = request.getSession();

		Tournament tournament = (Tournament) session.getAttribute(ATTR_TOURNAMENT);
		if (tournament == null) {
			tournament = super.getTournament(request);
			session.setAttribute(ATTR_TOURNAMENT, tournament);
		}

		// Get the players ranking
		if (session.getAttribute(ATTR_RANKING) == null)
			session.setAttribute(ATTR_RANKING, RankingService.getByTournament(tournament));

		// The teams ranking
		if (session.getAttribute("rankingTeam") == null)
			session.setAttribute("rankingTeam", RankingService.getTeamsForTournament(tournament));

		// Get the number of the last session
		session.setAttribute(ATTR_LAST_PLAYED_SESSION, RoundService.getLastPlayedSession(tournament));

		// Redefine the nb of elements by page
		Integer newValue = NumberUtils.getInteger(request.getParameter(RequestCst.PARAM_NB_ELEMENTS_PER_PAGE));
		Integer inSessionValue = (Integer) session.getAttribute(SES_ATTR_NB_PLAYERS_BY_PAGE);
		if (newValue != null)
			session.setAttribute(SES_ATTR_NB_PLAYERS_BY_PAGE, newValue);
		else if (inSessionValue == null)
			session.setAttribute(SES_ATTR_NB_PLAYERS_BY_PAGE, ApplicationCst.NB_ELEMENTS_BY_PAGE_DEFAULT);

		// go to jsp
		return ServletCst.REDIRECT_TO_DYNAMIC_VIEW_RANKING;
	}
}