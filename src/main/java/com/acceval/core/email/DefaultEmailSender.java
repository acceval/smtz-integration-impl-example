package com.acceval.core.email;

import com.acceval.core.amqp.MessageBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Default implementation of email sender
 *
 * @author Julian
 */
class DefaultEmailSender implements EmailSender {
	private final TemplateRenderer renderer;
	private final EmailQueueSender sender;

	public DefaultEmailSender(TemplateRenderer renderer, EmailQueueSender sender) {
		this.renderer = renderer;
		this.sender = sender;
	}

	@Override
	public void sendEmail(SendEmailRequest request) throws IOException {
		SendEmailRequest myRequest = request;

		if (StringUtils.isNotBlank(myRequest.getTemplate())) {
			myRequest = new SendEmailRequest(request);

			String newContent = renderer.render(myRequest.getTemplate(), myRequest.getText());
			myRequest.setText(newContent);
		}

		MessageBody<SendEmailRequest> messageBody = new MessageBody<>(myRequest);

		// TODO: fix multithreaded sending of email
		sender.setMessageBody(messageBody);
		sender.sendMessage();
	}
}
