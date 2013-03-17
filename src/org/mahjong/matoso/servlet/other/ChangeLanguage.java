/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.other;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.constant.BundleCst;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.I18n;
import org.mahjong.matoso.util.SessionUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Servlet used to change the default locale. Check the parameter <b>language</b> to build the new locale.<br/>
 * Search the tournaments too.
 */
@SuppressWarnings("serial")
public class ChangeLanguage extends MatosoServlet {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(ChangeLanguage.class);

	public String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException {

		LOGGER.debug(RequestCst.REQ_PARAM_LANGUAGE + " = " + request.getParameter(RequestCst.REQ_PARAM_LANGUAGE));

		// Change the default language
		if (request.getParameter(RequestCst.REQ_PARAM_LANGUAGE) != null) {
			Locale newLocale = new Locale(request.getParameter(RequestCst.REQ_PARAM_LANGUAGE));
			Locale.setDefault(newLocale);

			// Updating the resources locale too
			BundleCst.BUNDLE = new I18n("properties/labels", newLocale);

			LOGGER.debug("new locale = " + Locale.getDefault());
		}

		// Go to last visited ul
		return SessionUtils.getLastVisitedUrl(request.getSession());
	}
}
