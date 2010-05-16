package org.mahjong.matoso.servlet.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Game;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

public class GamesExport extends MatosoServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(GamesExport.class);

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

		StringBuffer buffer = new StringBuffer("round;main");
		List<Player> players = new ArrayList<Player>(tournament.getPlayers());
		for (Player player : players)
			buffer.append(";").append(player.getEma());
		buffer.append('\n');

		List<Table> tables = TableService.getAllByTournament(tournament);
		if (tables == null)
			return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET + "?tournament-id=" + tournament.getId();

		List<Game> games;
		int min;
		for (Table table : tables) {
			games = table.getGames();
			for (Game game : games) {
				buffer.append(table.getRound().getNumber()).append(';');

				// Get the hand score
				min = 1;
				min = Math.min(game.getScorePlayer1(), game.getScorePlayer2());
				min = Math.min(min, game.getScorePlayer3());
				min = Math.min(min, game.getScorePlayer4());

				if (min == 0)
					buffer.append("0;");
				else
					buffer.append(Math.abs(min + 8)).append(';');

				for (Player player : players) {
					if (table.getPlayer1().getId().equals(player.getId()))
						buffer.append(game.getScorePlayer1());
					else if (table.getPlayer2().getId().equals(player.getId()))
						buffer.append(game.getScorePlayer2());
					else if (table.getPlayer3().getId().equals(player.getId()))
						buffer.append(game.getScorePlayer3());
					else if (table.getPlayer4().getId().equals(player.getId()))
						buffer.append(game.getScorePlayer4());
					buffer.append(";");
				}
				buffer.append("\n");
			}
		}
		String fileName = tournament.getName();
		fileName = fileName.replaceAll("[^a-zA-Z0-9]", "_");
		response.setContentType("application/download");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".csv\"");
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
