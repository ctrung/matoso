/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.importing;

import java.util.List;

/**
 * XLS file representation.
 * 
 * @author ctrung
 * @date 3 févr. 2010
 */
public class XLSFile {

	private String playerSpreadsheetName;
	
	private List<String[]> playersData;

	public String getPlayerSpreadsheetName() {
		return playerSpreadsheetName;
	}

	public void setPlayerSpreadsheetName(String playerSpreadsheetName) {
		this.playerSpreadsheetName = playerSpreadsheetName;
	}

	public List<String[]> getPlayersData() {
		return playersData;
	}

	public void setPlayersData(List<String[]> playersData) {
		this.playersData = playersData;
	}
	
}
