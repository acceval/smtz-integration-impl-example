package com.acceval.core.filehandler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import com.opencsv.bean.CsvBindByName;


public abstract class FileHandler<T> {
	
	public enum FileMode {
		READ, WRITE
	}

	protected Iterator<T> iterator;
	protected DozerBeanMapper beanMapper;
	protected Object holderRecord;
	protected Class entityClass;
	protected Class templateClass;
	protected Path filePath;
	protected FileHandlerConfig fileHandlerConfig;

	protected List<ErrorRecord> errorRecords;
	protected int fileIndex;
	private LocalDateTime startTime;	

	protected abstract void initializeFileReader() throws FileHandlerException;
	
	public UploadResult getUploadResult() {
		
		UploadResult uploadResult = new UploadResult();
		uploadResult.setName(this.fileHandlerConfig.getName());
		uploadResult.setStartTime(this.startTime);
		uploadResult.setEndTime(LocalDateTime.now());
		uploadResult.setProcessSeconds(ChronoUnit.SECONDS.between(uploadResult.getStartTime(), 
				uploadResult.getEndTime()));
		uploadResult.setFailureCount(this.getFailureCount());
		uploadResult.setFileFunction(this.fileHandlerConfig.getFileFunction());
		uploadResult.setFilename(filePath.getFileName().toString());
		uploadResult.setTotalRecord(this.getTotalCount());
		uploadResult.setFailureCount(this.getFailureCount());
		uploadResult.setSuccessCount(this.getSuccessCount());
		
		if (errorRecords.size() > 0) {
			
			List<Integer> lineNumbers = new ArrayList<Integer>();
			Map<Integer, ErrorRecord> errorMap = new HashMap<Integer, ErrorRecord>();
			
			for (ErrorRecord error: this.errorRecords) {
				lineNumbers.add(Integer.valueOf(error.getLineNumber()));
				errorMap.put(Integer.valueOf(error.getLineNumber()), error);
			}
			
			String csvFile = filePath.toString().replace(".xlsx", ".csv").replace(".xls", ".csv");
						
			try {
				BufferedReader reader = Files.newBufferedReader(Paths.get(csvFile));
				
				for (int i = 0; i < this.fileHandlerConfig.getIgnoreLines(); i++) {
					reader.readLine();
				}
	
				String line = null;
				
				int index = 0;
				
				while ((line = reader.readLine()) != null) {
				    
					if (lineNumbers.contains(Integer.valueOf(index))) {
						ErrorRecord error = errorMap.get(Integer.valueOf(index));
						error.setLineContent(line);
					}
					index++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
		uploadResult.setErrorRecords(this.getErrorRecords().toArray(new ErrorRecord[this.getErrorRecords().size()]));
		
		return uploadResult;
	}

	public void initWithFileHolder(FileMode fileMode, Path filePath, FileHandlerConfig config)
			throws FileHandlerException {
		
		this.filePath = filePath;
		this.fileHandlerConfig = config;
		this.errorRecords = new ArrayList<ErrorRecord>();

		this.beanMapper = new DozerBeanMapper();
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozerJdk8Converters.xml");
		this.beanMapper.setMappingFiles(mappingFiles);
		try {
			if (config.getEntityClass() != null) {
				this.entityClass = Class.forName(config.getEntityClass());
			}
			if (config.getFileTemplateClass() != null) {
				this.templateClass = Class.forName(config.getFileTemplateClass());
			}
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
		}
		
		if (fileMode == FileMode.READ) {
			this.initializeFileReader();
		}
		
		this.startTime = LocalDateTime.now();
	}

	public void writeTemplateFile(Path templateFile) throws FileHandlerException {

		if (this.fileHandlerConfig.isHasSampleTemplate()) {
			return;
		}
		
		Writer writer = null;

		try {
			
			if (!Files.exists(templateFile.getParent())) {
				
				Files.createDirectories(templateFile.getParent());				
			}

			writer = new FileWriter(templateFile.toFile());
			Field[] fields = this.templateClass.getDeclaredFields();

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
					writer.write("\"" + column + "\"");
					writer.write(",");
				} else {
					writer.write("\"" + column + "\"");
				}

				index++;
			}
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
			
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {					
					e.printStackTrace();
					throw new FileHandlerException(this.getClass(), e.getLocalizedMessage());
				}
			}
		}
	}

	public boolean hasNext() {

		return this.iterator.hasNext();
	}

	public void nextWithoutMapping() throws FileMappingException {
		try {
			this.holderRecord = iterator.next();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(this.fileIndex, ex.getLocalizedMessage());
			this.errorRecords.add(errorRecord);

			throw new FileMappingException(this.getClass(), ex.getLocalizedMessage());
		}
		this.fileIndex++;
	}

	public <T> T next() throws FileMappingException {

		try {

			this.holderRecord = iterator.next();

		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(this.fileIndex, ex.getLocalizedMessage());
			this.errorRecords.add(errorRecord);

			throw new FileMappingException(this.getClass(), ex.getLocalizedMessage());
		}

		Object valueObject = null;

		try {

			valueObject = beanMapper.map(holderRecord, this.entityClass);

		} catch (MappingException ex) {
			ex.printStackTrace();
			ErrorRecord errorRecord = new ErrorRecord(this.fileIndex, ex.getLocalizedMessage());
			this.errorRecords.add(errorRecord);

			throw new FileMappingException(this.getClass(), ex.getLocalizedMessage());
		}

		this.fileIndex++;
		return (T) valueObject;
	}
	
	public void logErrorRecord(Exception ex) {
		
		ErrorRecord errorRecord = new ErrorRecord(this.fileIndex, ex.getLocalizedMessage());
		this.errorRecords.add(errorRecord);

	}

	public Object getHolderRecord() {
		return holderRecord;
	}

	public int getTotalCount() {

		return this.fileIndex;
	}

	public int getSuccessCount() {

		return this.fileIndex - errorRecords.size();
	}

	public int getFailureCount() {

		return errorRecords.size();
	}

	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

}
