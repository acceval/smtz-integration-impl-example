package com.acceval.core.filehandler.impl;

import org.dozer.MappingException;

import com.acceval.core.filehandler.ErrorRecord;
import com.acceval.core.filehandler.FileHandler;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import net.bytebuddy.asm.Advice.This;

public class CsvFileHandler extends FileHandler {

	@Override
	public void initializeFileReader() {
		
		
		try {
			CsvToBean csvToBean = new CsvToBeanBuilder(this.reader)
				.withType(this.fileHolder)
				.withIgnoreLeadingWhiteSpace(true)
				.build();
			
			this.records = csvToBean.parse();
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(0, ex, this.holderRecord);			
			this.errorRecords.add(errorRecord);			
		}
		
	}
	
}
