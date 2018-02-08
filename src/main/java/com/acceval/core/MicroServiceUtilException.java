package com.acceval.core;

public class MicroServiceUtilException extends Exception {
	private static final long serialVersionUID = 1L;

	public MicroServiceUtilException(String msg) {
		super(msg);
	}

	public MicroServiceUtilException(Class<?> clz, String msg) {
		super((clz != null ? clz.getName() + ". " : "") + msg);
	}
}
