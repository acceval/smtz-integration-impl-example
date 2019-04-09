package com.acceval.core.email;

import java.io.IOException;

/**
 * @author Julian
 */
public interface EmailSender {
	void sendEmail(SendEmailRequest request) throws IOException;
}
