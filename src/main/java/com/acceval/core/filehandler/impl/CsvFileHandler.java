package com.acceval.core.filehandler.impl;

import com.acceval.core.filehandler.FileHandler;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvFileHandler extends FileHandler {

	@Override
	public void initializeFileReader() {
		
		CsvToBean csvToBean = new CsvToBeanBuilder(this.reader)
				.withType(this.fileHolder)
				.withIgnoreLeadingWhiteSpace(true).build();
				
		this.records = csvToBean.parse();
	}
	
}
