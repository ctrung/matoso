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
 * A class to store all the mahjong applicative constants.<br>
 *
 * @author ctrung
 * @date 15 août 2009
 */
public abstract class ApplicationCst {
	
	/**
	 * The number of points a first place scores.
	 */
	public static final int RANK_POINTS_FIRST 	= 4;
	
	/**
	 * The number of points a second place scores.
	 */
	public static final int RANK_POINTS_SECOND 	= 2;
	
	/**
	 * The number of points a third place scores.
	 */
	public static final int RANK_POINTS_THIRD 	= 1;
	
	/**
	 * The number of points a fourth place scores.
	 */
	public static final int RANK_POINTS_FOURTH 	= 0;

	/**
	 * Nb of maximum games for one session in CO rules
	 */
	public static final int MAX_GAMES_FOR_ONE_SESSION = 16;
	
	/**
	 * Scoring base points when winning or losing
	 */
	public static final int SCORE_BASE_POINTS = 8;

	/**
	 * Lists default number of elements by page.
	 */
	public static final int NB_ELEMENTS_BY_PAGE_DEFAULT = 20;
	
	/**
	 * Nb max columns in import file.
	 */
	public static final int NB_COLUMNS_IMPORT_PLAYERS = 17;
	
	
}
