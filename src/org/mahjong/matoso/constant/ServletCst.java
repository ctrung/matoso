package org.mahjong.matoso.constant;

/**
 * Constants for servlet and jsp URLs.
 *
 * @author ctrung
 * @date 23 août 2009
 */
public interface ServletCst {
	
	// Chage language
	String REDIRECT_TO_CHANGE_LANGUAGE = "/servlet/ChangeLanguage";
	
	// Tournament pages
	String REDIRECT_TO_TOURNAMENT_LIST = "/jsp/tournament/list.jsp";
	String REDIRECT_TO_TOURNAMENT_LIST_SERVLET = "/servlet/ListTournament";
	String REDIRECT_TO_TOURNAMENT_LOAD = "/jsp/tournament/load.jsp";
	String REDIRECT_TO_TOURNAMENT_LOAD_SERVLET = "/servlet/LoadTournament";	
	String REDIRECT_TO_TOURNAMENT_DRAW = "/jsp/tournament/draw.jsp";
	
	// Round pages
	String REDIRECT_TO_ROUND_EDIT_SERVLET = "/servlet/EditRound";
	String REDIRECT_TO_ROUND_EDIT_FORM = "/jsp/round/edit.jsp";
	
	// Player pages
	String REDIRECT_TO_PLAYER_IMPORT_FORM = "/jsp/player/import.jsp";
	String REDIRECT_TO_PLAYER_IMPORT_FORM_SERVLET = "/servlet/ImportFormPlayer";
	String REDIRECT_TO_IMPORT_PLAYER_SERVLET = "/servlet/ImportPlayer";
	String REDIRECT_TO_PLAYER_ADD_SERVLET = "/servlet/AddPlayer";
	String REDIRECT_TO_PLAYER_ADD_FORM = "/jsp/player/add.jsp";
	String REDIRECT_TO_PLAYER_ADD_FORM_SERVLET = "/servlet/AddFormPlayer";
	String REDIRECT_TO_PLAYER_EDIT_FORM = "/jsp/player/edit.jsp";
	
	// Table pages
	String REDIRECT_TO_TABLE_FILL_SERVLET = "/servlet/FillTables";
	String REDIRECT_TO_TABLE = "/jsp/editTable.jsp";
	String REDIRECT_TO_TABLE_SERVLET = "/servlet/EditTable";
	
	// Ranking page
	String REDIRECT_TO_RANKING = "/jsp/ranking.jsp";
	String REDIRECT_TO_DYNAMIC_VIEW_RANKING = "/jsp/dynamicViewRanking.jsp";
}
