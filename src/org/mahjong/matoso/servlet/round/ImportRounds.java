package org.mahjong.matoso.servlet.round;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportException;
import org.mahjong.matoso.util.message.MatosoMessages;

@SuppressWarnings("serial")
public class ImportRounds extends MatosoServlet {
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		MatosoMessages msgs = super.getMatosoMessagesInRequest(request);
		Tournament tournament = super.getTournament(request);
		try {

			// get row data in array form
			List<String[]> rounds = RoundService.getRawDataFromRequest(request, msgs);
			if (MatosoMessages.isNotEmpty(msgs))
				return ServletCst.REDIRECT_TO_PLAYER_IMPORT_FORM;

			// mass save
			TournamentService.addRounds(tournament, rounds);

		} catch (ImportException e) {
			throw new FatalException("Can't import player file.", e);
		}
		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET + "?tournament-id=" + tournament.getId();
	}
}