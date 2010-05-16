package org.mahjong.matoso.servlet.round;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Round;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RankingService;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

public class FinalSession extends MatosoServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(FinalSession.class);

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		Tournament tournament = getTournament(request);
		if (tournament != null) {
			List<Round> rounds = RoundService.getRounds(tournament);
			if (rounds != null) {
				int lastRoundNb = rounds.size() + 1;

				if (LOGGER.isDebugEnabled())
					LOGGER.debug("lastRoundNb " + lastRoundNb);

				Table table = null;
				int tableNb = 0;
				int index = 0;
				Round round = new Round();
				round.setNumber(lastRoundNb);
				round.setTournament(tournament);
				round.setTables(new ArrayList<Table>());
				for (Player player : RankingService.getByTournament(tournament)) {

					if (LOGGER.isDebugEnabled())
						LOGGER.debug("index " + index);

					if (index % 4 == 0) {
						table = new Table();
						table.setName(Integer.toString(++tableNb));
						table.setRoundNbr(lastRoundNb);
						table.setTournament(tournament);

						if (LOGGER.isDebugEnabled())
							LOGGER.debug("table " + tableNb);
					}
					TableService.addPlayerToTable(player, table);
					if (index++ % 4 == 3) {

						if (LOGGER.isDebugEnabled())
							LOGGER.debug("save table " + tableNb + " =" + table);

						round.getTables().add(table);
						HibernateUtil.save(table);
					}
				}
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("save round " + lastRoundNb);

				HibernateUtil.save(round);
			}
		}
		return ServletCst.REDIRECT_TO_TOURNAMENT_LOAD_SERVLET;
	}
}