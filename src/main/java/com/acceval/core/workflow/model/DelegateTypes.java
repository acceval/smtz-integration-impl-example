package com.acceval.core.workflow.model;

public interface DelegateTypes {
	String HEADER_KEY = "delegateType";

	/**
	 * Listener delegate type doesn't expect a reply
	 */
	String LISTENER = "listener";

	/**
	 * SignalabLe delegate type, expects a reply
	 */
	String SIGNALABLE = "signalable";
}
