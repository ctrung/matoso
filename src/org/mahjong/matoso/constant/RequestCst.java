package org.mahjong.matoso.constant;

public interface RequestCst {
	String REQ_PARAM_LIST_TOURNAMENTS = "list-tournaments";

	String REQ_PARAM_TOURNAMENT_ID = "tournament-id";
	String REQ_PARAM_TEAM_ACTIVATE = "team-activate";
	String REQ_PARAM_ROUND = "round";
	String REQ_PARAM_CHOSEN_TABLE = "chosen-table";
	String REQ_PARAM_POINTS = "points";
	String REQ_PARAM_WINNER = "winner";
	String REQ_PARAM_LOSER = "loser";
	String REQ_PARAM_SELF_DRAW = "self-draw";
	String REQ_PARAM_LANGUAGE = "language";
	String REQ_PARAM_NB_TRIES = "nb-tries";
	String REQ_PARAM_MAX_TIME_TO_WAIT = "max-time-to-wait";
	String REQ_PARAM_NB_PLAYERS = "nb-players";

	String REQ_PARAM_PLAYER_NAME = "player-name";
	String REQ_PARAM_PLAYER_FIRSTNAME = "player-firstname";
	String REQ_PARAM_PLAYER_EMA = "player-ema";
	String REQ_PARAM_PLAYER_NATIONALITY = "player-nationality";
	String REQ_PARAM_PLAYER_TEAM = "player-team";
	String REQ_PARAM_PLAYER_MAHJONGTIME = "player-mahjontime";

	String REQ_PARAM_TABLE_ID = "table-id";
	String REQ_ATTR_TABLE = "table";
	String REQ_ATTR_TABLE_DISPLAY_GAMES = "table_display_games";
	String REQ_ATTR_TABLE_RESULT = "table_result";

	String REQ_ATTR_MATOSO_MESSAGES = "matosoMessages";
	String REQ_ATTR_FINAL_SESSION = "finalSession";

	/** Name of the attribute to get the current player */
	String ATTR_PLAYER = "player";

	/** Name of the attribute to get the current player */
	String ATTR_PLAYER_RESULT = "player_result";

	/** Name of the parameter to get the number of elements to display per page */
	String PARAM_NB_ELEMENTS_PER_PAGE = "nbElementsByPage";
}