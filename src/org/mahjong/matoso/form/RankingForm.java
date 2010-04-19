package org.mahjong.matoso.form;

import java.util.ArrayList;
import java.util.List;

public class RankingForm {
	private List<BestPlayerRound> bestPlayerRoundList = new ArrayList<BestPlayerRound>();
	private BestScore bestScore = new BestScore();

	public BestScore getBestScore() {
		return bestScore;
	}

	public class BestScore {
		private String namePlayer = "";
		private int score = 0;

		public String getNamePlayer() {
			return namePlayer;
		}

		public void setNamePlayer(String namePlayer) {
			this.namePlayer = namePlayer;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int number) {
			this.score = number;
		}
	}

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

	public List<BestPlayerRound> getBestPlayerRoundList() {
		return this.bestPlayerRoundList;
	}

	public void addBestPlayerRound(String round, String player, int score) {
		this.bestPlayerRoundList.add(new BestPlayerRound(round, player, score));
	}
}
