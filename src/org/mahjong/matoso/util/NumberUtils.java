/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util;

import java.text.DecimalFormat;

/**
 * Utility class to deal with numbers.
 * 
 * @author ctrung
 * @date 2 août 2009
 */
public class NumberUtils {

	private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	/**
	 * Utility method to convert a string to its Integer representation. <br>
	 * No exception is thrown.
	 * 
	 * @param str
	 * 
	 * @return an Integer or <code>null</code> if the string can't be parsed.
	 */
	public static Integer getInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			// nothing
		}
		return null;
	}

	/**
	 * Utility method to convert a string to its Double representation. <br>
	 * No exception is thrown.
	 * 
	 * @param str
	 * 
	 * @return a Double or <code>null</code> if the string can't be parsed.
	 */
	public static Double getDouble(String str) {
		Double d = null;
		try {
			d = Double.parseDouble(str);
		} catch (Exception e) {
			if (str != null)
				try {
					d = Double.parseDouble(str.replace(',', '.'));
				} catch (Exception e1) {
				}
		}
		return d;
	}

	/**
	 * @return the pretty print form of a double
	 * 
	 * @param d
	 * @return
	 */
	public static String getPrettyPrintForm(Double d) {
		if (d == null)
			return "0";

		if (Math.floor(d) == d)
			return Integer.toString(d.intValue());

		return DECIMAL_FORMAT.format(d);
	}

	/**
	 * @return the pretty print form of a integer
	 * 
	 * @param d
	 * @return
	 */
	public static String getPrettyPrintForm(Integer i) {
		if (i == null)
			return "";
		return i.toString();
	}

	/**
	 * Does the same job as Integer.intValue() but handles null pointer too.
	 * 
	 * @param integer
	 * 
	 * @return The int value or 0 if the object is a null reference.
	 */
	public static int getIntDefaultValue(Integer integer) {
		if (integer == null)
			return 0;
		return integer.intValue();
	}
}
