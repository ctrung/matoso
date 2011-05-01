/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.tournament;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
@SuppressWarnings("serial")
public class VisualCheck extends MatosoServlet {
	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {
		Tournament tournament = super.getTournament(request);
		Map<Player, TreeMap<Player, Integer>> result = new TreeMap<Player, TreeMap<Player, Integer>>(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				return o1.getPrettyPrintName().compareTo(o2.getPrettyPrintName());
			}
		});
		List<Table> tables = TableService.getAllByTournament(tournament);
		List<Player> players;
		Integer i;
		for (Player player : tournament.getPlayers()) {
			result.put(player, new TreeMap<Player, Integer>(new Comparator<Player>() {
				public int compare(Player o1, Player o2) {
					return o1.getPrettyPrintName().compareTo(o2.getPrettyPrintName());
				}
			}));
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