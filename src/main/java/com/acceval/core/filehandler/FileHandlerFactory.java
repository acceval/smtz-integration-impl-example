package com.acceval.core.filehandler;

import java.io.IOException;
import java.nio.file.Path;

import com.acceval.core.filehandler.impl.CsvFileHandler;
import com.acceval.core.filehandler.impl.XlsFileHandler;
import com.acceval.core.filehandler.impl.XlsxFileHandler;


public class FileHandlerFactory {
		
	public static FileHandler getFileHandler(Path path, FileHandlerConfig config) 
		throws FileHandlerException {
		
		String extension = null;
    	
    	int index = path.getFileName().toString().lastIndexOf('.');
    	if (index > 0) {
    	    extension = path.getFileName().toString().substring(index+1);
    	}
	
    	extension = extension.toUpperCase();		
				
    	FileHandler fileHandler = null;
	    	
		switch (extension) {
			case FileType.CSV_FILE: fileHandler = new CsvFileHandler(); break;					
			case FileType.XLSX_FILE: fileHandler = new XlsxFileHandler();	 break;	
			case FileType.XLS_FILE: fileHandler = new XlsFileHandler(); break;
			default: fileHandler = new CsvFileHandler(); break;
		}
		
		fileHandler.initWithFileHolder(path, config);
						
		return fileHandler;
	}
}
