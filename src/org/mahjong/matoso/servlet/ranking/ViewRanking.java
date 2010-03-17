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

import org.apache.log4j.Logger;
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

/**
 * This servlet displays :
 * <ul>
 * <li>the tournament stats.</li>
 * <li>the ranking of teams for this tournament (facultative).</li>
 * <li>the ranking of all players for this tournament.</li>
 * <li>the stats of all players for this tournament.</li>
 * </ul>
 * 
 * @author ctrung
 * @date 27 août 2009
 */
public class ViewRanking extends MatosoServlet {

	private static Logger LOGGER = Logger.getLogger(ViewRanking.class);

	private static final long serialVersionUID = 1L;

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		LOGGER.debug("start");
		HttpSession session = request.getSession();

		Tournament tournament = super.getTournament(request);
		request.setAttribute("tournament", tournament);

		/*
		 * We use displaytag to render the ranking table. It has great
		 * functionalities : - auto paging and sorting - dynamic link -
		 * decorators - export (csv, excel, pdf) - i18n
		 * 
		 * Drawback is our processed data should be in session scope for
		 * displaytag to work/scale correctly.
		 */

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
		if (newValue != null) {
			session.setAttribute(SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE, newValue);
		} else if (inSessionValue == null) {
			session.setAttribute(SessionCst.SES_ATTR_NB_PLAYERS_BY_PAGE, ApplicationCst.NB_ELEMENTS_BY_PAGE_DEFAULT);
		}

		LOGGER.debug("end");
		// go to jsp
		return ServletCst.REDIRECT_TO_RANKING;
	}
}
