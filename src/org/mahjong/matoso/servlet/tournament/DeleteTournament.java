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

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Delete a tournament.
 * 
 * @author ctrung
 * @date 7 mars 2010
 */
public final class DeleteTournament extends MatosoServlet {
	
	private static final long serialVersionUID = 1L;

	public String serve (HttpServletRequest request, HttpServletResponse response) throws FatalException {

		Tournament tournament = super.getTournament(request);
		TournamentService.deleteTournament(tournament);
		
		return ServletCst.REDIRECT_TO_TOURNAMENT_LIST_SERVLET;
	}
	
}
