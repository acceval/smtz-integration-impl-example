package com.acceval.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acceval.core.filehandler.StorageProperties;
import com.google.common.base.Splitter;

public class FileUtil {

	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd" };

	public static boolean isExcelFile(Path path) {

		String extension = null;

		int index = path.getFileName().toString().lastIndexOf('.');
		if (index > 0) {
			extension = path.getFileName().toString().substring(index + 1);
		}

		if (extension != null && (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls"))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isCsvFile(Path path) {

		String extension = null;

		int index = path.getFileName().toString().lastIndexOf('.');
		if (index > 0) {
			extension = path.getFileName().toString().substring(index + 1);
		}

		if (extension != null && (extension.equalsIgnoreCase("csv"))) {
			return true;
		} else {
			return false;
		}
	}

	public static Path convertExcelToCsv(Path path) throws IOException, InvalidFormatException {

		String extension = null;

		int index = path.getFileName().toString().lastIndexOf('.');
		if (index > 0) {
			extension = path.getFileName().toString().substring(index + 1);
		}

		String csvFile = path.toString().replace(".xlsx", ".csv").replace(".xls", ".csv");

		Workbook wb = null;

		boolean isXssf = true;

		if (extension.equalsIgnoreCase("xls")) {
			NPOIFSFileSystem fs = new NPOIFSFileSystem(new File(path.toString()));
			wb = new HSSFWorkbook(fs.getRoot(), true);
			isXssf = false;

		} else {
			wb = new XSSFWorkbook(new File(path.toString()));
			isXssf = true;
		}
		DataFormatter formatter = new DataFormatter();
		PrintStream out = new PrintStream(new FileOutputStream(csvFile), true, "UTF-8");
		for (Sheet sheet : wb) {
			boolean firstRow = true;
			int headerCellSize = 0;
			
			for (int c = sheet.getFirstRowNum(); c <= sheet.getLastRowNum(); c++) {
				boolean isRowEmpty = isRowEmpty(sheet.getRow(c));
				if (isRowEmpty) {
					sheet.getRow(c).setZeroHeight(true);
				    sheet.removeRow(sheet.getRow(c));
				}
			}
			
			for (Row row : sheet) {
				if (firstRow) {
					headerCellSize = row.getLastCellNum();
				}
				boolean firstCell = true;
				
				if (row.getCell(0) != null) {
					for (int cn=0; cn<headerCellSize; cn++) {
						if (cn >= headerCellSize) {
							break;
						}
						Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						if (!firstCell) out.print(',');
						String text = formatter.formatCellValue(cell);

						if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
							String cellValue = null;
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								Date date = cell.getDateCellValue();
								cellValue = df.format(date);
								text = cellValue;
							}
						}

						out.print("\"" + text + "\"");
						firstCell = false;
					}
				}
				
				out.println();
				firstRow = false;
			}
		}

		wb.close();

		return Paths.get(csvFile);
	}
	
	private static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null) {
		        if (cell != null && Cell.CELL_TYPE_BLANK != cell.getCellType()) 
		            return false;
	        }
	    }
	    return true;
	}

	public static Path convertCsvToExcel(Path path) throws IOException, InvalidFormatException {

		String extension = null;

		int index = path.getFileName().toString().lastIndexOf('.');
		if (index > 0) {
			extension = path.getFileName().toString().substring(index + 1);
		}

		if (!isCsvFile(path)) {
			return path;
		}


		// open input file
		BufferedReader br = Files.newBufferedReader(path);

		String xlsxFile = path.toString().replace(".csv", ".xlsx");
		Path rootLocation = Paths.get(new StorageProperties().getLocation());
		Path xlsxPath = rootLocation.resolve(xlsxFile);
		if (!Files.exists(xlsxPath.getParent())) {
			Files.createDirectories(xlsxPath.getParent());
		}
		// create sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		// read from file
		String line = br.readLine();
		for (int rows = 0; line != null; rows++) {
			// create one row per line
			Row row = sheet.createRow(rows);
			// split by comma
			List<String> itemsLst = Splitter.on(Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")).splitToList(line);
			String[] items = itemsLst.toArray(new String[0]);
			// ignore first item
			for (int i = 0, col = 0; i < items.length; i++) {
				String item = "";
				// strip quotation marks
				if (StringUtils.isNotBlank(items[i])) {
					item = items[i].substring(1, items[i].length() - 1);
				}

				Cell cell = row.createCell(col++);
				// set item
				if (isValidDate(item)) {
					try {
						Date itemDate = DateUtils.parseDateStrictly(item, STD_DATEFORMAT);
						CellStyle builtInShortDate = wb.createCellStyle();
						cell.setCellStyle(builtInShortDate);
						builtInShortDate.setDataFormat((short) 14);
						cell.setCellValue(itemDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					cell.setCellValue(item);
				}
			}
			// read next line
			line = br.readLine();
		}
		// write to xlsx
		FileOutputStream out = new FileOutputStream(xlsxFile);
		wb.write(out);
		// close resources
		br.close();
		out.close();

		return Paths.get(xlsxFile);
	}

	public static final void writeToFile(File file, String encoding, String content) throws IOException {

		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			if (encoding == null) {
				osw = new OutputStreamWriter(fos);
			} else {
				osw = new OutputStreamWriter(fos, encoding);
			}
			bw = new BufferedWriter(osw);
			bw.write(content);
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Throwable t) {
				}
			}
			bw = null;
			osw = null;
			fos = null;
		}
	}

	public static boolean isValidDate(String dateString) {
		try {
			DateUtils.parseDateStrictly(dateString, STD_DATEFORMAT);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
