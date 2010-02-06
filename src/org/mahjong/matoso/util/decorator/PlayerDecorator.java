/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.decorator;

import org.displaytag.decorator.TableDecorator;
import org.mahjong.matoso.bean.Player;

/**
 * This class is a decorator of the Player list that we use in the ranking.<br>
 * This class provides a number of methods for formatting data.

 * @author ctrung
 * @date 17 janv. 2010
 */
public class PlayerDecorator extends TableDecorator {

	/**
	 * Return the link to edit a player.
	 * 
	 * @return formatted number of points
	 */
	public String getLinkEditPlayer() {
		Player player = (Player) this.getCurrentRowObject();
        return "<a href=\"EditPlayer?old-firstname=" + player.getFirstname() + "&old-name=" + 
        player.getLastname() + "\">" + player.getPrettyPrintName() + "</a>";
	}

}
