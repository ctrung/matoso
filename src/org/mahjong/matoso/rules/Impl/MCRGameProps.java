package org.mahjong.matoso.rules.Impl;

import org.apache.log4j.Logger;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.rules.IGameProps;

public class MCRGameProps implements IGameProps {
	private static Logger LOGGER = Logger.getLogger(MCRGameProps.class);

	/**
	 * Update the victories, defeats, given, sustain selfpick and selfpick
	 * figures of one player
	 * 
	 * @param player
	 * @param scorePlayer
	 * 
	 * @param scoreOtherPlayer1
	 * @param scoreOtherPlayer2
	 * @param scoreOtherPlayer3
	 */
	public void updateGamePropsForOnePlayer(Player player, Integer scorePlayer, Integer scoreOtherPlayer1, Integer scoreOtherPlayer2,
			Integer scoreOtherPlayer3, String rules) {
		player.incrementNbGames();
		if (scorePlayer.intValue() > 0) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(player + " won with score=" + scorePlayer);
			if (scoreOtherPlayer1.intValue() == scoreOtherPlayer2.intValue() && scoreOtherPlayer1.intValue() == scoreOtherPlayer3.intValue()
					&& scoreOtherPlayer2.intValue() == scoreOtherPlayer3.intValue()) {
				// 1. selfpick figure : one must have a positive score
				// and others have the same negative score
				player.incrementNbSelfpick();
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("it was a selfpick because all players got some minus score=" + scoreOtherPlayer1);
			} else {
				// 2. victories figure : one must have a positive score
				// AND others can't have the same negative score
				player.incrementNbVictory();
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("it was a normal victory");
			}

		} else if (scorePlayer.intValue() == 0 && scoreOtherPlayer1 == 0 && scoreOtherPlayer2 == 0 && scoreOtherPlayer3 == 0)
			player.incrementNbDraw();
		else {
			boolean selfpickAppeared = scorePlayer.intValue() * 3 == -scoreOtherPlayer1.intValue()
					|| scorePlayer.intValue() * 3 == -scoreOtherPlayer2.intValue() || scorePlayer.intValue() * 3 == -scoreOtherPlayer3.intValue();

			if (scorePlayer.intValue() == -8 && !selfpickAppeared)
				// 3. defeats figure : one must have a score of -8
				player.incrementNbDefeat();
			else {
				if (selfpickAppeared)
					// 4. sustain selfpick figure : one must have a score
					// inferior to -8
					// AND at least another player has the same score
					player.incrementNbSustainSelfpick();
				else
					// 5. given figure : one must be the only one with a score
					// inferior to -8 and
					player.incrementNbGiven();
			}
		}
	}
}
