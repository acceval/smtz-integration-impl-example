package com.acceval.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtil {

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

	public static Path convertExcelToCsv(Path path) throws IOException, InvalidFormatException {

		String extension = null;

		int index = path.getFileName().toString().lastIndexOf('.');
		if (index > 0) {
			extension = path.getFileName().toString().substring(index + 1);
		}

		String csvFile = path.toString().replace(".xlsx", ".csv").replace(".xls", ".csv");

		Workbook wb = null;

		if (extension.equalsIgnoreCase("xls")) {
			NPOIFSFileSystem fs = new NPOIFSFileSystem(new File(path.toString()));
			wb = new HSSFWorkbook(fs.getRoot(), true);

		} else {
			wb = new XSSFWorkbook(new File(path.toString()));
		}
		DataFormatter formatter = new DataFormatter();
		PrintStream out = new PrintStream(new FileOutputStream(csvFile), true, "UTF-8");
		for (Sheet sheet : wb) {
			for (Row row : sheet) {
				boolean firstCell = true;
				for (Cell cell : row) {
					if (!firstCell) out.print(',');
					String text = formatter.formatCellValue(cell);
					out.print(text);
					firstCell = false;
				}
				out.println();
			}
		}

		return Paths.get(csvFile);
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
}
