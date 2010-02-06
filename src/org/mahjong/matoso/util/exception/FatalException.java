/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.exception;


/**
 * Fatal exception.<br>
 * To use when application stops because of a severe failure.
 * 
 * @author ctrung
 */
public class FatalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param e Exception.
	 */
	public FatalException(Exception e) {
		super(e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message 
	 * @param e Exception.
	 */
	public FatalException(String message, Exception e) {
		super(message, e);
	}
}
