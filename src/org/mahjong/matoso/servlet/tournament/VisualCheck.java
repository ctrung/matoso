/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.tournament;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.TableService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Visual check of the tournament
 * 
 * @author npochic
 * @date 30/10/2010
 */
public class VisualCheck extends MatosoServlet {
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		Tournament tournament = super.getTournament(request);
		HashMap<Player, HashMap<Player, Integer>> result = new HashMap<Player, HashMap<Player, Integer>>();
		List<Table> tables = TableService.getAllByTournament(tournament);
		List<Player> players;
		Integer i;
		for (Player player : tournament.getPlayers()) {
			result.put(player, new HashMap<Player, Integer>());
			// Initializes
			for (Player player2 : tournament.getPlayers())
				result.get(player).put(player2, 0);
			// Stores
			for (Table table : tables) {
				players = table.getListPlayers();
				if (players.contains(player))
					for (Player player2 : players)
						if (!player2.equals(player)) {
							i = result.get(player).get(player2);
							i++;
							result.get(player).put(player2, i);
						}
			}
		}
		request.setAttribute("result", result);
		return ServletCst.REDIRECT_TO_VISUAL_CHECK;
	}
}