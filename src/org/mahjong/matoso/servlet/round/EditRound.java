/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.round;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.service.RoundService;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Edit a round.
 * 
 * @author ctrung
 * @date 14 mars 2010
 */
@SuppressWarnings("serial")
public class EditRound extends MatosoServlet {

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {
		
		Integer id = NumberUtils.getInteger(request.getParameter("id"));
		
		request.setAttribute("round", RoundService.getById(id));
		
		return ServletCst.REDIRECT_TO_ROUND_EDIT_FORM;
	}

	
}
