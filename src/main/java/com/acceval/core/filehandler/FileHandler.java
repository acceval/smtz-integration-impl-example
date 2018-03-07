package com.acceval.core.filehandler;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.dozer.DozerBeanMapper;

public abstract class FileHandler<T> {

	protected Path path;
	protected Class<T> fileHolder;	
	protected Reader reader;
	protected List records;
	
	private Object holderRecord;
	private int index;
	private int totalCount;
	private int successCount;
	
	public void initWithFileHolder(Class<T> fileHolder) throws IOException {
		
		this.fileHolder = fileHolder;
		this.reader = Files.newBufferedReader(path);
		
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
		
		Object valueObject = beanMapper.map(holderRecord, valueClass);
				
		index++;
		
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

}
