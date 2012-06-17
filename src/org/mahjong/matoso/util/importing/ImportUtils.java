/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.util.importing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportCSVException;
import org.mahjong.matoso.util.exception.imports.ImportXLSException;

import au.com.bytecode.opencsv.CSVReader;

/** Import utility methods. */
public class ImportUtils {
	private static final Logger LOGGER = Logger.getLogger(ImportUtils.class);
	private static final char CSV_QUOTE = '"', CSV_SEPARATOR = ';';
	private static final int SKIP_FIRST_LINE = 1;

	/**
	 * Reads a XLS file.
	 * 
	 * @param is
	 * @param spreadSheet
	 *            spread sheet number to parse (start from 0)
	 * @return The XLS file representation.
	 */
	public static XLSFile readXLS(InputStream is, int spreadSheet) throws ImportXLSException {

		XLSFile xlsFile = null;

		LOGGER.debug("Reading XLS file...");

		try {
			HSSFWorkbook wb = new HSSFWorkbook(is);

			// working with first sheet only
			HSSFSheet sheet = wb.getSheetAt(spreadSheet);

			if (sheet == null) {
				LOGGER.warn("Sheet position [" + spreadSheet + "] not found in HSSFWorkbook ! Returning null.");
				return null;
			}

			int rows = sheet.getPhysicalNumberOfRows();

			String spreadsheetName = wb.getSheetName(0);

			LOGGER.info("Sheet 0 \"" + spreadsheetName + "\" has " + rows + " row(s).");

			xlsFile = new XLSFile();
			xlsFile.setPlayerSpreadsheetName(spreadsheetName);

			if (rows != 0) {

				List<String[]> pdata = new ArrayList<String[]>();

				xlsFile.setPlayersData(pdata);

				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) {
						LOGGER.info(" -> Skipping row " + r + " because is NULL.");
						continue;
					}

					int cells = row.getPhysicalNumberOfCells();
					LOGGER.info("ROW " + row.getRowNum() + " has " + cells + " cell(s).");

					if (cells == 0) {
						LOGGER.debug(" -> Skipping row because 0 cell.");
						continue;
					}

					String[] playerData = new String[cells];
					pdata.add(playerData);

					for (int c = 0; c < cells; c++) {
						HSSFCell cell = row.getCell(c);

						if (cell == null) {
							LOGGER.debug(" -> Skipping cell because NULL.");
							continue;
						}

						String value = null;

						switch (cell.getCellType()) {

						case HSSFCell.CELL_TYPE_FORMULA:
							value = cell.getCellFormula();
							break;

						case HSSFCell.CELL_TYPE_NUMERIC:
							value = Double.toString(cell.getNumericCellValue());
							break;

						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getRichStringCellValue().toString();
							break;

						default:
						}

						LOGGER.info("CELL col=" + cell.getColumnIndex() + " VALUE=" + value);
						playerData[c] = value;

					}
				}
			}
		} catch (IOException e) {
			throw new ImportXLSException("Can't read XLS stream", e);
		}

		return xlsFile;
	}

	/**
	 * Reads a CSV file.
	 * 
	 * @param is
	 * @return The found values of each rows in a List<String[]> object.
	 * @throws FatalException
	 */
	public static List<String[]> readCSV(InputStream is) throws ImportCSVException {
		List<String[]> res = null;
		LOGGER.debug("Reading CSV file...");
		try {
			CSVReader csvReader = new CSVReader(new InputStreamReader(is), CSV_SEPARATOR, CSV_QUOTE, SKIP_FIRST_LINE);
			if (csvReader != null)
				res = csvReader.readAll();
		} catch (IOException e) {
			throw new ImportCSVException("Can't read CSV stream", e);
		}
		return res;
	}
}
