package com.acceval.core.filehandler;

public class FileHandlerException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FileHandlerException(String message) {
		super(message);
	}

	public FileHandlerException(Class<?> clazz, String message) {		
		super((clazz != null ? clazz.getName() + ". " : "") + message);
	}
}
