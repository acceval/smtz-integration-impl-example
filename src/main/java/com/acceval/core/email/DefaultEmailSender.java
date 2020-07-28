package com.acceval.core.email;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.acceval.core.amqp.MessageBody;

/**
 * Default implementation of email sender
 *
 * @author Julian
 */
class DefaultEmailSender implements EmailSender {
	
	private final TemplateRenderer renderer;
	private final EmailQueueSender sender;

	@Value("${webapp.url}")
    private String webappURL;

	@Value("${email.titlePrefix}")
    private String titlePrefix;

	public DefaultEmailSender(TemplateRenderer renderer, EmailQueueSender sender) {
		this.renderer = renderer;
		this.sender = sender;
	}

	@Override
	public void sendEmail(EmailContentData data) throws IOException {

		this.defaultData(data);

        SendEmailRequest myRequest = data.toRequest();

		if (StringUtils.isNotBlank(data.getTemplate())) {
			String newContent = renderer.render(data.getTemplate(), data);
			myRequest.setText(newContent);
		}

		MessageBody<SendEmailRequest> messageBody = new MessageBody<>(myRequest);

		sender.sendMessage(messageBody);
	}

	private void defaultData(EmailContentData data) {
	    if (data.getContext() == null) {
	        data.setContext(new HashMap());
        }

	    data.getContext().put("YEAR", Calendar.getInstance().get(Calendar.YEAR));
	    if (data.getDomainPath() != null && data.getDomainPath().trim().length() > 0) {
	    	data.getContext().put("DOMAIN_PATH", data.getDomainPath());	    	
	    } else {
	    	data.getContext().put("DOMAIN_PATH", this.webappURL);
	    }

	    data.setSubject(data.getSubject());
    }
}
