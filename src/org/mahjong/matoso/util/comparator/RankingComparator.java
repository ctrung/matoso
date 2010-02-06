/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.comparator;

import java.util.Comparator;

import org.mahjong.matoso.bean.Player;

/**
 * A player comparator for the ranking based on table points first, table score second, nb. of victories third, and nb. of given fourth.
 *
 * @author ctrung
 * @date 6 sept. 2009
 */
public class RankingComparator implements Comparator<Player> {

	/**
	 * Criteria :
	 * <ul>
	 * <li>points</li>
	 * <li>score</li>
	 * <li>nb victories</li>
	 * <li>nb given</li>
	 * </ul>
	 */
	public int compare(Player p1, Player p2) {

		if(p1 == p2) return 0;
		if(p1 == null) return -1;
		if(p2 == null) return 1;
		
		// 1. comparison on points
		
		if(p1.getPoints() == null) return p2.getPoints()==null ? 0 : -1;
		
		if( p1.getPoints().equals(p2.getPoints()) ) {
			
			// 2. compare on score

			if(p1.getScore() == null) return p2.getScore()==null ? 0 : -1;
			
			if( p1.getScore().equals(p2.getScore()) ) {
				
				// 3. compare on nb victories
				
				if(p1.getNbVictory() == null) return p2.getNbVictory()==null ? 0 : -1;
				
				if( p1.getNbVictory().equals(p2.getNbVictory()) ) {
					
					// 4. compare on nb given
					
					if(p1.getNbGiven() == null) return p2.getNbGiven()==null ? 0 : -1;
					
					return -(p1.getNbGiven().compareTo(p2.getNbGiven()));
					
				} 

				return -(p1.getNbVictory().compareTo(p2.getNbVictory()));
				
			} 

			return -(p1.getScore().compareTo(p2.getScore()));
			
		} 

		return -(p1.getPoints().compareTo(p2.getPoints()));
	}

}
