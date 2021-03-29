package com.acceval.core.email;

import java.io.Serializable;

public class EmailRecipient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -729369249701132456L;
	private String emailTo;
	private String emailToName;

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailToName() {
		return emailToName;
	}

	public void setEmailToName(String emailToName) {
		this.emailToName = emailToName;
	}

}
