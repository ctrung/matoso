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
import org.mahjong.matoso.constant.BundleCst;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.PlayerService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportException;
import org.mahjong.matoso.util.message.MatosoMessage;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Player mass import. Two types :
 * <ul>
 * <li>CSV</li>
 * <li>XLS</li>
 * </ul>
 *
 * @author ctrung
 * @date 21 oct. 2009
 */
@SuppressWarnings("serial")
public class ImportPlayer extends MatosoServlet {

	
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		
		
		MatosoMessages msgs = new MatosoMessages();
		request.setAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES, msgs);
		
		List<String[]> playersData;
		try {
			playersData = PlayerService.readFileFromRequest(request, msgs);
		} catch (ImportException e) {
			throw new FatalException("Can't import player file.", e);
		}
		
		if(MatosoMessages.isNotEmpty(msgs)) return ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM;
		
		if(playersData == null) {
			
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("player.mass.import.error.no.file"));
			return ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM;
			
		} else if(playersData.size() == 0) {
			
			msgs.addMessage(MatosoMessage.WARNING, BundleCst.BUNDLE.getString("player.mass.import.error.no.players"));
			return ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM;
			
		} else {
			
			Tournament tournament = super.getTournament(request);
			
			TournamentService.importPlayers(tournament, playersData);
		}
		
		return ServletCst.REDIRECT_TO_TABLE_FILL_SERVLET;
	}

}
