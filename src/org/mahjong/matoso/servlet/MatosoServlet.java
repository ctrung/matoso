package org.mahjong.matoso.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.util.SessionUtils;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.message.MatosoMessages;

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
	
	/**
	 * Return the tournament whose name is in the user's session.
	 * 
	 * @param request 
	 * @return
	 * @throws FatalException 
	 */
	public static Tournament getTournament(HttpServletRequest request) throws FatalException{
		
		// TODO : get the tournament by id, not by name (error prone)
		
		String tournamentName = (String)request.getSession().getAttribute(SessionCst.SES_ATTR_TOURNAMENT);
		if(tournamentName==null) {
			throw new FatalException("Tournament ??? not found", null);
		}
		
		Tournament tournament	= TournamentService.getByName(tournamentName);
		if(tournament==null) {
			throw new FatalException("Tournament " + tournamentName + " not found", null);
		}
		
		return tournament;
		
	}

	/**
	 * Get the MatosoMessages object in the request if exists or create a new one otherwise.
	 * 
	 * @param request Can't be null.
	 * @return Always non null MatosoMessages object.
	 */
	public MatosoMessages getMatosoMessagesInRequest(HttpServletRequest request) {
		if (request == null) throw new IllegalArgumentException("HttpServletRequest request object can't be null");
		
		if(request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES) == null) {
			request.setAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES, new MatosoMessages());
		}
		return (MatosoMessages) request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
	}
}
