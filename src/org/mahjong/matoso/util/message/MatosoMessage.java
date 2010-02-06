/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.message;

/**
 * A functional application message to display to the user.<br>
 * For example, can be used for validating forms input data.
 * 
 * @author ctrung
 * @date 10 janv. 2010
 */
public class MatosoMessage {

	public static int INFO = 0;
	public static int WARNING = 1;
	public static int ERROR = 2;
	
	private int severity = MatosoMessage.INFO;
	
	private String mesg;

	public MatosoMessage(int severity, String mesg) {
		super();
		this.severity = severity;
		this.mesg = mesg;
	}

	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	/**
	 * @return the mesg
	 */
	public String getMesg() {
		return mesg;
	}

	/**
	 * @param mesg the mesg to set
	 */
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	
}
