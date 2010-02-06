/**
 * 
 */
package org.mahjong.matoso.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.mahjong.matoso.constant.ServletCst;

/**
 * Methods used with session.
 * 
 * @author ctrung
 * @date 2 dec. 2009
 */
public class SessionUtils {

	private static Logger LOGGER = Logger.getLogger(SessionUtils.class);
	
	/**
	 * Get the last visited url by looking in the user's session.
	 * 
	 * @param session The user's session.
	 * @return The last visited url.
	 * @see SessionUtils#saveUrlInSession(HttpSession) to update the session.
	 */
	public static String getLastVisitedUrl(HttpSession session){
		
		String res = (String) session.getAttribute("__last_visited_url");
		
		if(res==null || res.length()==0) {
			res = "/"; // default 
		}
		return res;
	}
	
	/**
	 * Save this current http request url in the user's session 
	 * so it can be retrieved for the next request.
	 * 
	 * @param request The request.
	 * @see SessionUtils#getLastUrl(HttpSession) to retrieve the last browse url. 
	 */
	@SuppressWarnings("unchecked")
	public static void saveLastVisitedUrl(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		String url = request.getServletPath();
		
		LOGGER.debug("servlet path="+url);
		
		if(url.equals(ServletCst.REDIRECT_TO_CHANGE_LANGUAGE)) return;
		
		Enumeration<String> pNames = request.getParameterNames();
		
		if(pNames!=null) {
			boolean first = true;
			while(pNames.hasMoreElements()) {
				String label = (String) pNames.nextElement();
				
				if(label!=null && label.length()>0) {
					if(first) {
						first = false;
						url += "?";
					} else {
						url += "&";
					}
					
					String value = request.getParameter(label);
					url += label + "=" + (value==null?"":value);
				}
			}
		}
		
		LOGGER.debug("url="+url);
		
		session.setAttribute("__last_visited_url", url);
	}
	
}
