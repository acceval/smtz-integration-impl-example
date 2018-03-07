package com.acceval.core.filehandler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.acceval.core.filehandler.ErrorRecord;
import com.acceval.core.filehandler.FileHandler;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class XlsFileHandler extends FileHandler {

	@Override
	public void initializeFileReader() throws IOException {
		
		String csvFile = this.getPath().toString().replace(".xls", ".csv");
		
		try {
			NPOIFSFileSystem fileSystem = new NPOIFSFileSystem(new File(this.getPath().toString()));
			Workbook workbook = new HSSFWorkbook(fileSystem.getRoot(), true);
			
			DataFormatter formatter = new DataFormatter();
			PrintStream out = new PrintStream(new FileOutputStream(csvFile),
			                                  true, "UTF-8");
			for (Sheet sheet : workbook) {
			    for (Row row : sheet) {
			        boolean firstCell = true;
			        for (Cell cell : row) {
			            if ( ! firstCell ) out.print(',');
			            String text = formatter.formatCellValue(cell);
			            out.print(text);
			            firstCell = false;
			        }
			        out.println();
			    }
			}	       
			
			this.path = Paths.get(csvFile);
			this.reader = Files.newBufferedReader(this.path);
			
			CsvToBean csvToBean = new CsvToBeanBuilder(this.reader)
					.withType(this.fileHolder)
					.withIgnoreLeadingWhiteSpace(true).build();
					
			this.records = csvToBean.parse();
			
		} catch (IOException ex) {
			
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(0, ex, this.holderRecord);			
			this.errorRecords.add(errorRecord);
			throw ex;
			
		} catch (Exception ex) {
				
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(0, ex, this.holderRecord);			
			this.errorRecords.add(errorRecord);				
		}
	}
}
