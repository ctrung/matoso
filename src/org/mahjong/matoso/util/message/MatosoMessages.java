/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.message;

import static org.mahjong.matoso.util.message.MatosoMessage.ERROR;

import java.util.ArrayList;
import java.util.List;

/**
 * Bundle of functional application messages to display to the user.<br>
 * For example, can be used for validating forms input data.
 * 
 * @author ctrung
 * @date 10 janv. 2010
 */
public class MatosoMessages {

	private List<MatosoMessage> mesgs;

	public MatosoMessages() {
		super();
		mesgs = new ArrayList<MatosoMessage>();
	}

	/**
	 * Add a message to the list of messages.
	 * 
	 * @param severity
	 * @param msg
	 */
	public void addMessage(int severity, String msg) {
		this.mesgs.add(new MatosoMessage(severity, msg));
	}

	/**
	 * Test if the matoso bundle of messages is not empty.
	 * 
	 * @param matosoMessages
	 * @return true if there's at least one message, false otherwise.
	 */
	public static boolean isNotEmpty(MatosoMessages matosoMessages) {
		return matosoMessages != null && matosoMessages.getMesgs() != null && !matosoMessages.getMesgs().isEmpty();
	}

	/**
	 * @return the mesgs
	 */
	public List<MatosoMessage> getMesgs() {
		return mesgs;
	}

	/**
	 * @param mesgs
	 *            the mesgs to set
	 */
	public void setMesgs(List<MatosoMessage> mesgs) {
		this.mesgs = mesgs;
	}

	public static boolean hasError(MatosoMessages mm) {
		if (mm != null && mm.getMesgs() != null)
			for (MatosoMessage msg : mm.getMesgs())
				if (msg != null && msg.getSeverity() == ERROR)
					return true;
		return false;
	}
}