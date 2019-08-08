package com.acceval.core.filehandler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.acceval.core.filehandler.FileHandler;
import com.acceval.core.filehandler.FileHandlerException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class XlsFileHandler extends FileHandler {

	@Override	
	public void initializeFileReader() throws FileHandlerException {
		
		String csvFile = this.filePath.toString().replace(".xls", ".csv");
				
		try {
			NPOIFSFileSystem fileSystem = new NPOIFSFileSystem(new File(filePath.toString()));
			Workbook workbook = new HSSFWorkbook(fileSystem.getRoot(), true);
			
			DataFormatter formatter = new DataFormatter();
			PrintStream out = new PrintStream(new FileOutputStream(csvFile), true, "UTF-8");
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
			
			this.filePath = Paths.get(csvFile);
			Reader fileReader = Files.newBufferedReader(this.filePath);
			
			CsvToBean csvToBeans = new CsvToBeanBuilder(fileReader)
					.withType(Class.forName(this.fileHandlerConfig.getFileTemplateClass()))					
					.withIgnoreLeadingWhiteSpace(true)
					.withSkipLines(this.fileHandlerConfig.getIgnoreLines())
					.build();
			
			this.iterator = csvToBeans.iterator();
			
		} catch (IOException | IllegalStateException | ClassNotFoundException e) {			
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		}
			
	}
}
