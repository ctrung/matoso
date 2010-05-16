/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.constant.SessionCst;
import org.mahjong.matoso.service.TournamentService;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.SessionUtils;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Mother class to inherit from.<br>
 * It helps deal with exception by displaying an error page.<br>
 * It updates the user's last visited url.<br>
 * Daughter classes can easily get the session's tournament by calling a method.
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

			// set encoding
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO : do something better ?
				e.printStackTrace();
			}

			String forwardTo = serve(request, response);

			/* memorize last url in user's session */
			SessionUtils.saveLastVisitedUrl(request);

			if (forwardTo != null)
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
	 * @param reload
	 *            used to reload the tournament in the session.
	 * 
	 * @return
	 * @throws FatalException
	 */
	public static Tournament getTournament(HttpServletRequest request, boolean reload) throws FatalException {

		Integer tournamentId = null;

		if (reload) {

			// no id in the session ? retrieve it via the request...
			String reqId = request.getParameter("tournament-id");
			tournamentId = NumberUtils.getInteger(reqId);

			if (tournamentId == null)
				throw new FatalException("The tournament with id=" + reqId + " doesn't exist.");

		} else {
			tournamentId = (Integer) request.getSession().getAttribute(SessionCst.SESSION_TOURNAMENT_ID);
		}

		Tournament tournament = TournamentService.getById(tournamentId);
		if (tournament == null)
			throw new FatalException("The tournament with id=" + tournamentId + " doesn't exist.");

		return tournament;
	}

	/**
	 * Return the tournament whose name is in the user's session.
	 * 
	 * @param request
	 * @return
	 * @throws FatalException
	 */
	public static Tournament getTournament(HttpServletRequest request) throws FatalException {
		return getTournament(request, false);
	}

	/**
	 * Get the MatosoMessages object in the request if exists or create a new
	 * one otherwise.
	 * 
	 * @param request
	 *            Can't be null.
	 * @return Always non null MatosoMessages object.
	 */
	public MatosoMessages getMatosoMessagesInRequest(HttpServletRequest request) {
		if (request == null)
			throw new IllegalArgumentException("HttpServletRequest request object can't be null");

		if (request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES) == null) {
			request.setAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES, new MatosoMessages());
		}
		return (MatosoMessages) request.getAttribute(RequestCst.REQ_ATTR_MATOSO_MESSAGES);
	}
}
