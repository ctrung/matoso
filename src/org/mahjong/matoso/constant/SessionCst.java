/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.constant;

/**
 * Application attribute names that reside in session scope.
 * 
 * @author ctrung
 * @date 7 mars 2010
 */
public interface SessionCst {

	String SESSION_TOURNAMENT_ID = "_session_tournament_id";

	String SES_ATTR_NB_PLAYERS_BY_PAGE = "nbPlayersByPage";

	/** Name of the attribute to get the ranking of the current tournament */
	String ATTR_RANKING = "ranking";

	/** Name of the attribute to get the tournament */
	String ATTR_TOURNAMENT = "tournament";

	/** Name of the attribute to get the number of the last played session */
	String ATTR_LAST_PLAYED_SESSION = "LastPlayedSession";
}