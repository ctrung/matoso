/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util;

import org.mahjong.matoso.constant.BundleCst;

/**
 * Utility methods with i18n.
 * 
 * @author ctrung
 * @date 16 janv. 2010
 */
public abstract class I18nUtils {

	/**
	 * Return a message from the i18n bundle.
	 * 
	 * @param key The message key
	 * @param args The placeholders values.
	 * 
	 * @return A message from the i18n bundle.
	 */
	public static String getMessage(String key, String[] args) {
		
		String mesg = BundleCst.BUNDLE.getString(key);
		
		// do the replacing
		if(args!=null && args.length>0) {
			for (int i = 0; i < args.length; i++) {
				mesg = mesg.replace("{"+i+"}", args[i]);
			}
		}
		
		return mesg;
	}

	/**
	 * Return a message from the i18n bundle.
	 * 
	 * @param key The message key
	 * @param arg0 The placeholder value at {0}.
	 * 
	 * @return A message from the i18n bundle.
	 */
	public static String getMessage(String key, String arg0) {
		return getMessage(key, new String[]{arg0});
	}
	
	/**
	 * Return a message from the i18n bundle.
	 * 
	 * @param key The message key
	 * @param arg0 The placeholder value at {0}.
	 * @param arg1 The placeholder value at {1}.
	 * 
	 * @return A message from the i18n bundle.
	 */
	public static String getMessage(String key, String arg0, String arg1) {
		return getMessage(key, new String[]{arg0, arg1});
	}	
	
	/**
	 * Return a message from the i18n bundle.
	 * 
	 * @param key The message key
	 * @param arg0 The placeholder value at {0}.
	 * @param arg1 The placeholder value at {1}.
	 * @param arg2 The placeholder value at {2}.
	 * 
	 * @return A message from the i18n bundle.
	 */
	public static String getMessage(String key, String arg0, String arg1, String arg2) {
		return getMessage(key, new String[]{arg0, arg1, arg2});
	}
	
	/**
	 * Return the wind name from the i18n bundle based on the game number.
	 * 
	 * @param gameNumber The game number. Starts at 0 and stops at 15.
	 * 
	 * @return The wind name or null if it can't be retrieved.
	 */
	public static String getWind(int gameNumber) {
		if(gameNumber < 0 || gameNumber > 15) return null;
		
		String wind = null;
		if (gameNumber>=0 && gameNumber<=3) wind = BundleCst.BUNDLE.getString("table.east");
		else if (gameNumber>=4 && gameNumber<=7) wind = BundleCst.BUNDLE.getString("table.south");
		else if (gameNumber>=8 && gameNumber<=11) wind = BundleCst.BUNDLE.getString("table.west");
		else wind = BundleCst.BUNDLE.getString("table.north");
		
		return wind;
	}

}
