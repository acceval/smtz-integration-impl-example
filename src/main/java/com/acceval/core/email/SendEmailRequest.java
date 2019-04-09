package com.acceval.core.email;

import java.util.LinkedHashMap;
import java.util.Map;

import com.acceval.core.amqp.QueueRequest;

public final class SendEmailRequest extends QueueRequest {

	private static final long serialVersionUID = 5400154087677655670L;

	private String[] sendTo;
	private String[] sendCcTo;
	private String text;
	private String subject;
	private String template;
	private Map<String, Object> context;

	/** attachment */
	Map<String, Byte[]> lstAttachment = new LinkedHashMap<>();

	public SendEmailRequest() {
	}

	public String[] getSendTo() {
		return sendTo;
	}

	public void setSendTo(String[] sendTo) {
		this.sendTo = sendTo;
	}

	public String[] getSendCcTo() {
		return sendCcTo;
	}

	public void setSendCcTo(String[] sendCcTo) {
		this.sendCcTo = sendCcTo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Byte[]> getLstAttachment() {
		return lstAttachment;
	}

	public void setLstAttachment(Map<String, Byte[]> lstAttachment) {
		this.lstAttachment = lstAttachment;
	}

	public void addAttachment(String fileName, Byte[] content) {
		lstAttachment.put(fileName, content);
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
