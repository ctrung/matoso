package org.mahjong.matoso.servlet.export;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Round;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

public class EMAExport extends MatosoServlet {
	private static Logger LOGGER = Logger.getLogger(EMAExport.class);

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			LOGGER.error("get output stream of response", e);
		}
		if (out == null)
			return ServletCst.REDIRECT_TO_TOURNAMENT_LIST_SERVLET;

		Tournament tournament = getTournament(request);

		if (tournament == null)
			return ServletCst.REDIRECT_TO_TOURNAMENT_LIST_SERVLET;

		StringBuffer buffer = new StringBuffer("\"Tournament name\";");
		buffer.append("\"Number of participants\";").append("Place;").append("\"Player's first name\";").append("\"Player's last name\";").append(
				"\"Registrate number\";").append("\"Total tablepoints\";").append("\"Total score\";").append("\"EMA member\";")
				.append("\"Country\";").append("\"Date\"\n");

		Date dateTournament = null;
		List<Round> rounds = RoundService.getRounds(tournament);
		if (rounds != null && rounds.isEmpty())
			dateTournament = rounds.get(0).getDate();
		if (dateTournament == null)
			dateTournament = new Date();

		String dateS = DateFormat.getDateInstance().format(dateTournament);

		List<Player> players = RankingService.getByTournament(tournament);
		int index = 1;
		int nbPlayers = players.size();
		String nameTournament = tournament.getName();
		for (Player player : players) {
			buffer.append(nameTournament).append(';');
			buffer.append(nbPlayers).append(';');
			buffer.append(index++).append(';');
			buffer.append(player.getFirstname()).append(';');
			buffer.append(player.getLastname()).append(';');
			buffer.append("").append(';');
			buffer.append(player.getPoints()).append(';');
			buffer.append(player.getScore()).append(';');
			buffer.append(player.getEma()).append(';');
			buffer.append(player.getCountry()).append(';');
			buffer.append(dateS).append(';');
			buffer.append('\n');
		}

		String fileName = tournament.getName();
		fileName = fileName.replaceAll("[^a-zA-Z0-9]", "_");
		response.setContentType("application/download");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "_ema.csv\"");
		try {
			out.print(buffer.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("print in output stream of response", e);
		}
		return null;
	}
}
