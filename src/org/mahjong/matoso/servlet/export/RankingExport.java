package org.mahjong.matoso.servlet.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Team;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.TeamService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

public class RankingExport extends MatosoServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(RankingExport.class);

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

		StringBuffer buffer = new StringBuffer("ema;rank;teamrank;francerank\n");
		List<Player> players = RankingService.getByTournament(tournament);
		List<Team> orderedTeams = RankingService.getTeamsForTournament(tournament);
		int index = 1;
		for (Player player : players)
			buffer.append(player.getEma()).append(';').append(players.indexOf(player) + 1).append(';').append(
					TeamService.getIndexInList(TeamService.getTeamForPlayer(player, tournament), orderedTeams) + 1).append(';').append(
					player.getCountry() != null && player.getCountry().toLowerCase().startsWith("fr") ? index++ : 0).append('\n');

		String fileName = tournament.getName();
		fileName = fileName.replaceAll("[^a-zA-Z0-9]", "_");
		response.setContentType("application/download");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "_ranking.csv\"");
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
