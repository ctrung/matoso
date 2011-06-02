/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.displaytag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;
import org.displaytag.localization.I18nResourceProvider;
import org.mahjong.matoso.constant.BundleCst;

/**
 * Displaytag will call our own implementation of resources provider to get the messages.
 * 
 * @author ctrung
 * @date 23 janv. 2010
 */
public class MatosoI18nResourceProvider implements I18nResourceProvider {
	private static final Logger LOGGER = Logger.getLogger(MatosoI18nResourceProvider.class);

	/**
	 * Just get it the way we set it in org.mahjong.matoso.servlet.other.ChangeLanguage servlet
	 */
	public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext context) {
		String i18nStr = BundleCst.BUNDLE.getString(resourceKey);
		if (i18nStr == null)
			i18nStr = "???" + resourceKey + "???";
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("displaytag is getting the message for [resourceKey=" + resourceKey + ", defaultValue=" + defaultValue + "] ---> " + i18nStr);
		return i18nStr;
	}

}
