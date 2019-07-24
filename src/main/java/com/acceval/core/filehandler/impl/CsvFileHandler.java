package com.acceval.core.filehandler.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.acceval.core.filehandler.FileHandler;
import com.acceval.core.filehandler.FileHandlerConfig;
import com.acceval.core.filehandler.FileHandlerException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvFileHandler extends FileHandler {

	@Override	
	public void initializeFileReader() throws FileHandlerException  {
		
		try {
			Reader fileReader = Files.newBufferedReader(filePath);
					
			CsvToBean csvToBeans = new CsvToBeanBuilder(fileReader)
				.withType(Class.forName(this.fileHandlerConfig.getFileTemplateClass()))
				.withIgnoreLeadingWhiteSpace(true)
				.withSkipLines(this.fileHandlerConfig.getIgnoreLines())
				.build();
						
			this.iterator = csvToBeans.iterator();
			
		} catch (IOException | ClassNotFoundException e) {
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		}
	}	
}
