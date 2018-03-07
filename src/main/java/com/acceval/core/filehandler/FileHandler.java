package com.acceval.core.filehandler;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

public abstract class FileHandler<T> {

	protected Path path;
	protected Class<T> fileHolder;	
	protected Reader reader;
	protected List records;
	
	protected Object holderRecord;
	protected List<ErrorRecord> errorRecords;
	
	private int index;
	private int totalCount;
	private int successCount;	
	
	public void initWithFileHolder(Class<T> fileHolder) throws IOException {
				
		this.fileHolder = fileHolder;
		this.reader = Files.newBufferedReader(path);
		this.errorRecords = new ArrayList<ErrorRecord>();
		
		this.initializeFileReader();
		
		this.totalCount = records.size();
		
	}
	
	protected abstract void initializeFileReader() throws IOException;
	
	public boolean hasNext() {
		
		if (index <= records.size() - 1) {			
			return true;
		} else {
			return false;
		}
	}
	
	public <T> T next(Class<T> valueClass) {
		
		if (index > records.size() - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		this.holderRecord = this.records.get(index);
		
		Object valueObject = null;
		
		try {
			
			valueObject = beanMapper.map(holderRecord, valueClass);
			
		} catch (MappingException ex) {
			ErrorRecord errorRecord = new ErrorRecord(index, ex, holderRecord);			
			this.errorRecords.add(errorRecord);
		}
		index++;
		successCount++;
		
		return (T) valueObject;		
	}
	
	public <T> T getHolderRecord() {		
		return (T) this.holderRecord;		
	}
	
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public int getErrorCount() {
		return totalCount - successCount;
	}

	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

}
