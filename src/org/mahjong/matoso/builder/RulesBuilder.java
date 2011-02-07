package org.mahjong.matoso.builder;

import java.util.Comparator;

import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.rules.IGameProps;
import org.mahjong.matoso.rules.Impl.MCRGameProps;
import org.mahjong.matoso.rules.Impl.RCRGameProps;
import org.mahjong.matoso.util.comparator.MCRRankingComparator;

public class RulesBuilder {
	
	public static final IGameProps buildGameProps(String rules) {
		if ("RCR".equals(rules))
			return new RCRGameProps();
		else if ("MCR".equals(rules))
			return new MCRGameProps();
		else
			throw new IllegalArgumentException("the parameter is not in {MCR, RCR}");
	}

	public static final Comparator<? super Player> buildRankingComparator(String rules) {
		if ("RCR".equals(rules))
			return new RCRRankingComparator();
		else if ("MCR".equals(rules))
			return new MCRRankingComparator();
		else
			throw new IllegalArgumentException("the parameter is not in {MCR, RCR}");
	}
}