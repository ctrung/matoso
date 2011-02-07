package org.mahjong.matoso.util.comparator;

import java.util.Comparator;

import org.mahjong.matoso.bean.Player;

/**
 * Comparator of players with MCR rules.
 * <ol>
 * <li>Points</li>
 * <li>Score</li>
 * <li>Nb victories</li>
 * <li>Nb given</li>
 * </ol>
 */
public class MCRRankingComparator implements Comparator<Player> {
	/**
	 * Compare two players
	 * 
	 * @param p1
	 *            the first player
	 * @param p2
	 *            the second player
	 * @return if the two players are equal then 0 else if p1 is better than p2
	 *         then 1 else -1
	 */
	public int compare(Player p1, Player p2) {
		if (p1 == p2)
			return 0;
		if (p1 == null)
			return -1;
		if (p2 == null)
			return 1;

		Double pointsP1 = p1.getPoints();
		Double pointsP2 = p2.getPoints();
		if (pointsP1 == null)
			return pointsP2 == null ? 0 : -1;
		if (pointsP1.equals(pointsP2)) {
			Integer scoreP1 = p1.getScore();
			Integer scoreP2 = p2.getScore();
			if (scoreP1 == null)
				return scoreP2 == null ? 0 : -1;
			if (scoreP1.equals(scoreP2)) {
				Integer nbVictoryP1 = p1.getNbVictory();
				Integer nbVictoryP2 = p2.getNbVictory();
				if (nbVictoryP1 == null)
					return nbVictoryP2 == null ? 0 : -1;
				if (nbVictoryP1.equals(nbVictoryP2)) {
					if (p1.getNbGiven() == null)
						return p2.getNbGiven() == null ? 0 : -1;
					return -(p1.getNbGiven().compareTo(p2.getNbGiven()));
				}
				return -(nbVictoryP1.compareTo(nbVictoryP2));
			}
			return -(scoreP1.compareTo(scoreP2));
		}
		return -(pointsP1.compareTo(pointsP2));
	}
}
