package org.mahjong.matoso.constant;

import java.util.ResourceBundle;

/** Constants for the translations */
public abstract class BundleCst {
	/** the file */
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle("properties/labels");

	public static final String GENERAL_CREATE = "general.create";
	public static final String GENERAL_DELETE = "general.delete";
	public static final String GENERAL_NAME = "general.name";
	public static final String GENERAL_SUBMIT = "general.submit";
	public static final String GENERAL_RESET = "general.reset";
	public static final String GENERAL_HOME = "general.home";
	public static final String GENERAL_BACK = "general.back";
	public static final String GENERAL_TABLE = "general.table";
	public static final String GENERAL_ROUND = "general.round";
	public static final String GENERAL_SAVE = "general.save";

	public static final String TOURNAMENT_TITLE = "tournament.title";
	public static final String TOURNAMENT_NAME = "tournament.name";
	public static final String TOURNAMENT_TEAM_ACTIVATE = "tournament.team.activate";
	public static final String TOURNAMENT_DRAW_GOTO_LINK = "tounament.draw.goto.link";
	public static final String TOURNAMENT_NB_PLAYERS = "tournament.nb.players";
	public static final String TOURNAMENT_FINAL_SESSION_VIEW = "tournament.final.session.view";
	public static final String TOURNAMENT_FINAL_SESSION_TITLE = "tournament.final.session.title";

	public static final String ROUND_NUMBER = "round.number";
	public static final String ROUND_ADD = "round.add";
	public static final String ROUND_LABEL = "round.label.round";

	public static final String CSV_FILE = "csv";

	public static final String PLAYER_MASS_IMPORT = "player.mass.import";
	public static final String PLAYER_FILL_METHOD = "player.fill.method";
	public static final String PLAYER_ADD = "player.add";
	public static final String PLAYER_NAME = "player.name";
	public static final String PLAYER_FIRSTNAME = "player.firstname";
	public static final String PLAYER_EMA = "player.ema";
	public static final String PLAYER_NATIONALITY = "player.nationality";
	public static final String PLAYER_TEAM = "player.team";
	public static final String PLAYER_MAHJONGTIME = "player.mahjontime";
	public static final String PLAYER_SUBMIT = "player.submit";
	public static final String PLAYER_STOP = "player.stop";
	public static final String PLAYER_MODIFY = "player.modify";

	public static final String TABLE_TITLE = "table.title";
	public static final String TABLE_WIND = "table.wind";
	public static final String TABLE_GAME_NUMBER = "table.game.number";
	public static final String TABLE_HAND_VALUE = "table.hand.value";
	public static final String TABLE_PLAYER1 = "table.player1";
	public static final String TABLE_PLAYER2 = "table.player2";
	public static final String TABLE_PLAYER3 = "table.player3";
	public static final String TABLE_PLAYER4 = "table.player4";
	public static final String TABLE_EAST = "table.east";
	public static final String TABLE_NORTH = "table.north";
	public static final String TABLE_SOUTH = "table.south";
	public static final String TABLE_WEST = "table.west";
	public static final String TABLE_SELFPICK = "table.selfpick";
	public static final String TABLE_WINNER = "table.winner";
	public static final String TABLE_LOSER = "table.loser";
	public static final String TABLE_PENALTY = "table.penalty";
	public static final String TABLE_SCORE = "table.score";
	public static final String TABLE_POINTS = "table.points";
	public static final String TABLE_AUTO_CALCULATE = "table.auto.calculate";
	public static final String TABLE_AUTO_CALCULATE_DESC = "table.auto.calculate.desc";

	public static final String RANKING_STATS_GOTO_LINK = "ranking.stats.goto.link";
	public static final String RANKING_STATS_TITLE = "ranking.stats.title";
	public static final String RANKING_POSITION = "ranking.position";
	public static final String RANKING_PLAYER = "ranking.player";
	public static final String RANKING_POINTS = "ranking.points";
	public static final String RANKING_SCORE = "ranking.score";
	public static final String RANKING_NB_GAMES = "ranking.nb.games";
	public static final String RANKING_NB_SELFPICK_VICTORY = "ranking.nb.selfpick.victory";
	public static final String RANKING_NB_VICTORY = "ranking.nb.victory";
	public static final String RANKING_NB_SELFPICK = "ranking.nb.selfpick";
	public static final String RANKING_NB_GIVEN = "ranking.nb.given";
	public static final String RANKING_NB_SUSTAIN_SELFPICK = "ranking.nb.sustain.selfpick";
	public static final String RANKING_NB_DRAW = "ranking.nb.draw";
	public static final String RANKING_NB_DEFEAT = "ranking.nb.defeat";
	public static final String RANKING_DYNAMIC_VIEW = "ranking.dynamic.view";
	public static final String RANKING_TEAM = "ranking.team";

	public static final String RANKING_PERC_SELFPICK = "ranking.perc.selfpick";
	public static final String RANKING_PERC_VICTORY = "ranking.perc.victory";
	public static final String RANKING_PERC_GIVEN = "ranking.perc.given";
	public static final String RANKING_PERC_DRAW = "ranking.perc.draw";

	public interface RANKING {
		String EXPORT_EMA = "ranking.export.ema";
	}

	public interface EXPORT_PLAYERS {
		String ROUND = "export.players.round";
		String GAME = "export.players.game";
		String POINTS = "export.players.points";
		String SCORE = "export.players.score";
		String PLAYER = "export.players.player";
		String POSITION = "export.players.position";
		String TEAM = "export.players.team";
		String NB_GAMES = "export.players.nb.games";
		String NB_SELFPICK_VICTORY = "export.players.nb.selfpick.victory";
		String NB_SELFPICK = "export.players.nb.selfpick";
		String NB_VICTORY = "export.players.nb.victory";
		String NB_DEFEAT = "export.players.nb.defeat";
		String NB_GIVEN = "export.players.nb.given";
		String NB_DRAW = "export.players.nb.draw";
		String NB_SUSTAIN_SELFPICK = "export.players.nb.sustain.selfpick";
	}

	public static final String ERROR_NAME_MISSING = "error.name.missing";
}