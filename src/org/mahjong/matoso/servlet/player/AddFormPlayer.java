/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.servlet.player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong.matoso.constant.ServletCst;
import org.mahjong.matoso.servlet.MatosoServlet;
import org.mahjong.matoso.util.exception.FatalException;

/**
 * Display the add form page.
 * 
 * @author ctrung
 * @date 5 déc. 2009
 */
public class AddFormPlayer extends MatosoServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String serve(HttpServletRequest request, HttpServletResponse response)
			throws FatalException {
		// just forward to jsp
		return ServletCst.REDIRECT_TO_PLAYER_ADD_FORM;
	}

}
