/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.displaytag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.displaytag.localization.LocaleResolver;

/**
 * Displaytag will call our own implementation of locale resolver to know the language.
 * 
 * @author ctrung
 * @date 23 janv. 2010
 */
public class MatosoLocaleResolver implements LocaleResolver {

	/** 
	 * Logger 
	 */
	private static final Logger LOGGER = Logger.getLogger(MatosoLocaleResolver.class);
	
	/**
	 * Just get it the way we set it in org.mahjong.matoso.servlet.other.ChangeLanguage servlet
	 */
	public Locale resolveLocale(HttpServletRequest arg0) {
		
		Locale locale = Locale.getDefault();
		LOGGER.info("displaytag is resolving the locale, send him : " + locale.toString());
		return locale;
	}

}
