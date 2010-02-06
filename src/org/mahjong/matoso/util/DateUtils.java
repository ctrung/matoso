/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * Utility date methods class.
 * 
 * @author ctrung
 * @date 3 janv. 2010
 */
public class DateUtils {

	private static Logger LOGGER = Logger.getLogger(DateUtils.class);
	
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Return a date object based on its string representation.
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseSQLDate(String dateStr) {
		
		Date d = null;
		
		try {
			d = new Date(df.parse(dateStr).getTime());
		} catch (ParseException e) {
			LOGGER.warn("Trying to parse a non date string : " + dateStr + ", please verify the format : " + df.toString());
		}
		
		return d;
	}

	
}
