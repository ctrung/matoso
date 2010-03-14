/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Table;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Methods for draw.<br>
 * 
 * @author ctrung
 * @date 04 Dec. 2009
 */
public abstract class DrawService {
	
	/**
	 * Initialise the draw of players.<br>
	 * 
	 * Actually, it's just initializing the tables for each player.<br>
	 * 
	 * But 
	 * 
	 * @param players A list of players.
	 * 
	 * @throws FatalException 
	 */
	@SuppressWarnings("unchecked")
	public static void initDraw(List<Player> players) throws FatalException{
		if(players==null || players.isEmpty()) return;
		
		try {
			Session s = HibernateUtil.getSession();
			Query q = s.createQuery("from Table t left join fetch t.round left join fetch t.player1 p1 left join fetch t.player2 p2 " + 
					"left join fetch t.player3 p3 left join fetch t.player4 p4 where :p = p1 or " +
					":p = p2 or :p = p3 or :p = p4 order by t.round.number asc");
			List<Table> tables = null;
			
			for(int i=0; i<players.size(); i++){
				
				Player p = players.get(i);
				
				// initialise the Table where the player plays
				q.setParameter("p", p);
				tables = q.list();
				p.setTables(tables);
			}
		} catch (FatalException e) {
			throw new FatalException(e);
		}
	}
	
}
