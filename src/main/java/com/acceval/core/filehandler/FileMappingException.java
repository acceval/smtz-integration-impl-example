package com.acceval.core.filehandler;

public class FileMappingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FileMappingException(String message) {
		super(message);
	}

	public FileMappingException(Class<?> clazz, String message) {		
		super((clazz != null ? clazz.getName() + ". " : "") + message);
	}
}
