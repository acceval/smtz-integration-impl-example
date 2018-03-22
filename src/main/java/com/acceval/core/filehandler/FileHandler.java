package com.acceval.core.filehandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import com.opencsv.bean.CsvBindByName;

public abstract class FileHandler<T> {

	protected Path path;
	protected Class<T> fileHolder;	
	protected Reader reader;
	protected List records;
	protected DozerBeanMapper beanMapper;
	
	protected Object holderRecord;
	protected List<ErrorRecord> errorRecords;
	
	private int index;
	private int totalCount;
	private int successCount;	
	
	public void initWithFileHolder(Class<T> fileHolder) throws IOException {
				
		this.fileHolder = fileHolder;		
		this.reader = Files.newBufferedReader(path);
		this.errorRecords = new ArrayList<ErrorRecord>();

		this.beanMapper = new DozerBeanMapper();
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozerJdk8Converters.xml");
		beanMapper.setMappingFiles(mappingFiles);
		
		
		this.initializeFileReader();
		
		this.totalCount = records.size();
		
	}
	
	public void writeTemplateFile(Path templateFile, Class<T> templateClass) throws IOException {
				
		Writer writer = null;
		
		try {
			
			writer = new FileWriter(templateFile.toFile());
			Field[] fields = templateClass.getDeclaredFields();	
			
			ArrayList<String> columns = new ArrayList<String>();
			
			for (Field field : fields) {			
							
				if (field.isAnnotationPresent(CsvBindByName.class)) {
					
					CsvBindByName bindByName = field.getAnnotation(CsvBindByName.class);
					columns.add(bindByName.column());
				}			
			}
			
			int index = 0;
			for (String column : columns) {
				
				if (index < columns.size() - 1) {
					writer.write(column);
					writer.write(",");
				} else {
					writer.write(column);
				}
				
				index++;
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
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
