package org.mahjong.matoso.form;

import java.util.ArrayList;
import java.util.List;

public class RankingForm {
	public class BestPlayerRound {
		private String player, round;
		private int score;

		public BestPlayerRound(String round, String player, int score) {
			this.round = round;
			this.player = player;
			this.score = score;
		}

		public String getPlayer() {
			return player;
		}

		public void setPlayer(String player) {
			this.player = player;
		}

		public String getRound() {
			return round;
		}

		public void setRound(String round) {
			this.round = round;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}
	}

	private List<BestPlayerRound> bestPlayerRoundList = new ArrayList<BestPlayerRound>();

	public List<BestPlayerRound> getBestPlayerRoundList() {
		return this.bestPlayerRoundList;
	}

	public void addBestPlayerRound(String round, String player, int score) {
		this.bestPlayerRoundList.add(new BestPlayerRound(round, player, score));
	}
}
