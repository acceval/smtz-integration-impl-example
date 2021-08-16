package com.acceval.core.filehandler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acceval.core.filehandler.FileHandler;
import com.acceval.core.filehandler.FileHandlerException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class XlsxFileHandler extends FileHandler {

	@Override	
	public void initializeFileReader() throws FileHandlerException {
		
		String csvFile = filePath.toString().replace(".xlsx", ".csv");
						
		PrintStream out = null;
		try {
			Workbook workbook  = new XSSFWorkbook(new File(filePath.toString()));
			
			DataFormatter formatter = new DataFormatter();
			out = new PrintStream(new FileOutputStream(csvFile), true, "UTF-8");
			
			for (Sheet sheet : workbook) {
			    for (Row row : sheet) {			        
			        int index = 0;
			        for (Cell cell : row) {
			            if (index != 0) {
			            	out.print(',');
			            }
			            
			            if (index < cell.getColumnIndex()) {
			            	int loop = cell.getColumnIndex() - index;
			            	for (int i = 0; i < loop; i++) {	            		
			            		out.print(',');
			            		index++;
			            	}
			            }
			            String text = formatter.formatCellValue(cell);
			            out.print(text);
			            index++;
			        }
			        out.println();
			    }
			}	       
			
			filePath = Paths.get(csvFile);
			Reader fileReader = Files.newBufferedReader(filePath);
			
			CsvToBean csvToBeans = new CsvToBeanBuilder(fileReader)
					.withType(Class.forName(this.fileHandlerConfig.getFileTemplateClass()))
					.withIgnoreLeadingWhiteSpace(true)
					.withSkipLines(this.fileHandlerConfig.getIgnoreLines())
					.build();
					
			this.iterator = csvToBeans.iterator();
			
		} catch (IOException | InvalidFormatException | IllegalStateException | ClassNotFoundException e) {			
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
