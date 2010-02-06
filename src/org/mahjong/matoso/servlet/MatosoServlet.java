package org.mahjong.matoso.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.util.SessionUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Mother class to inherit from.<br>
 * It helps deal with exception by displaying a error page.
 * It updates the user's last visited url.
 * 
 * @author ctrung
 * @date 2 Dec. 2009
 */
public abstract class MatosoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Http GET method servicing.
	 */
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String forwardTo = serve(request, response);
			
			/* memorize last url in user's session */
			SessionUtils.saveLastVisitedUrl(request);

			request.getRequestDispatcher(forwardTo).forward(request, response);

		} catch (Exception e) {
			// TODO contain and deal with exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Http POST method servicing.
	 */
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	/**
	 * To be implemented by inherited servlets.
	 * 
	 * @param request 
	 * @param response
	 * @return The servlet to dispatch to.
	 * 
	 * @throws FatalException
	 */
	public abstract String serve(HttpServletRequest request, HttpServletResponse response) throws FatalException;
	
}
