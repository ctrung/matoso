/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.player;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportException;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Player mass import. Two types :
 * <ul>
 * <li>CSV</li>
 * <li>XLS (not functionnal yet)</li>
 * </ul>
 *
 * @author ctrung
 * @date 21 oct. 2009
 */
@SuppressWarnings("serial")
public class ImportPlayer extends MatosoServlet {

	
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		try {
			
			MatosoMessages msgs = super.getMatosoMessagesInRequest(request);
			Tournament tournament = super.getTournament(request); 
			
			// get row data in array form
			List<String[]> playersData = PlayerService.getRawDataFromRequest(request, msgs, tournament.isTeamActivate());
			if(MatosoMessages.isNotEmpty(msgs)) return ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM;
			
			// mass save 
			TournamentService.addPlayers(tournament, playersData);
			
		} catch (ImportException e) {
			throw new FatalException("Can't import player file.", e);
		}
		
		return ServletCst.REDIRECT_TO_TABLE_FILL_SERVLET;
	}

}
