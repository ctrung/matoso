/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * A servlet filter responsible for opening and closing the Hibernate Session for each request.<br>
 * 
 * Hibernate doesn't work well with long life session.<br>
 * So we use Filter to implement <b>Session Per Request</b> pattern.  
 *
 * @author ctrung
 * @date 28 juin 2009
 */
public class HibernateFilter implements Filter {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(HibernateFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		logger.debug("ServletFilter destroy.");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		logger.debug("ServletFilter doFilter().");
		try {
			// There is actually no explicit "opening" of a session.
			// The first call to HibernateUtil.beginTransaction() in control logic
			// (e.g. use case controller/event handler) will get a fresh session.
			chain.doFilter(request, response);
			// Commit any pending database transaction.
			HibernateUtil.commitTransaction();
		} catch (FatalException e) {
			throw new ServletException(e);
		} finally {
			// No matter what, close the Session.
			try {
				HibernateUtil.closeSession();
			} catch (FatalException e) {
				throw new ServletException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) {
		logger.debug("ServletFilter init, now opening/closing a Session for each request.");
	}

}
