package com.acceval.core.email;

import com.acceval.core.amqp.MessageBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

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
	public void sendEmail(EmailContentData data) throws IOException {
		SendEmailRequest myRequest = data.toRequest();

		this.defaultData(data);

		if (StringUtils.isNotBlank(data.getTemplate())) {
			String newContent = renderer.render(data.getTemplate(), data);
			myRequest.setText(newContent);
		}

		MessageBody<SendEmailRequest> messageBody = new MessageBody<>(myRequest);

		// TODO: fix multithreaded sending of email
		sender.setMessageBody(messageBody);
		sender.sendMessage();
	}

	private void defaultData(EmailContentData data) {
	    if (data.getContext() == null) {
	        data.setContext(new HashMap());
        }

	    data.getContext().put("YEAR", Calendar.getInstance().get(Calendar.YEAR));
	    data.getContext().put("DOMAIN_PATH", "http://www.smarttradzt.com");
    }
}
