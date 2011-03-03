package org.mahjong.matoso.builder;

import java.util.Comparator;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.rules.IGameProps;
import org.mahjong.matoso.rules.Impl.MCRGameProps;
import org.mahjong.matoso.rules.Impl.RCRGameProps;
import org.mahjong.matoso.util.comparator.MCRRankingComparator;
import org.mahjong.matoso.util.comparator.RCRRankingComparator;

public class RulesBuilder {
	private static final IGameProps RCR_GAME_PROPS = new RCRGameProps();
	private static final IGameProps MCR_GAME_PROPS = new MCRGameProps();
	private static final Comparator<? super Player> RCR_RANKING_COMPARATOR = new RCRRankingComparator();
	private static final Comparator<? super Player> MCR_RANKING_COMPARATOR = new MCRRankingComparator();

	/**
	 * @param rules
	 *            the rules of the game (MCR or RCR)
	 * @return the properties of a game
	 */
	public static final IGameProps buildGameProps(String rules) {
		if ("RCR".equals(rules))
			return RCR_GAME_PROPS;
		else if ("MCR".equals(rules))
			return MCR_GAME_PROPS;
		else
			throw new IllegalArgumentException("the parameter is not in {MCR, RCR}");
	}

	/**
	 * @param rules
	 *            the rules of the game (MCR or RCR)
	 * @return a comparator of players
	 */
	public static final Comparator<? super Player> buildRankingComparator(String rules) {
		if ("RCR".equals(rules))
			return RCR_RANKING_COMPARATOR;
		else if ("MCR".equals(rules))
			return MCR_RANKING_COMPARATOR;
		else
			throw new IllegalArgumentException("the parameter is not in {MCR, RCR}");
	}
}