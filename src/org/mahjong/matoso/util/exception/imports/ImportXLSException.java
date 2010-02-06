/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.exception.imports;


/**
 * XLS related import exception.
 * 
 * @author ctrung
 * @date 1 févr. 2010
 */
@SuppressWarnings("serial")
public class ImportXLSException extends ImportException {

	private String sheetName;
	
	public ImportXLSException(String mesg, Throwable t) {
		super(mesg, t);
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

}
