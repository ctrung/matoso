/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.tournament;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * List all the tournaments.
 *
 * @author ctrung
 * @date 23 août 2009
 */
public class ListTournament extends MatosoServlet {

	private static final long serialVersionUID = 1L;
	
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		request.setAttribute(RequestCst.REQ_PARAM_LIST_TOURNAMENTS, TournamentService.getList());
		
		return ServletCst.REDIRECT_TO_TOURNAMENT_LIST;
	}
	
}
