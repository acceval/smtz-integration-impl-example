package com.acceval.core.email;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmailContentData implements Serializable {

    private String receiverName;
    private String[] sendTo;
    private String[] sendCcTo;
	private String[] sendBccTo;
    private String subject;
    private String title;
    private String text;
    private String domainPath;
    private String template = "default";
	private Map<String, Object> context;
	private String sender;

    /** attachment */
    Map<String, Byte[]> lstAttachment = new LinkedHashMap<>();
    
    public SendEmailRequest toRequest() {

        SendEmailRequest req = new SendEmailRequest();
        req.setSendTo(this.sendTo);
        req.setSendCcTo(this.sendCcTo);
        req.setSubject(this.subject);
        req.setLstAttachment(this.lstAttachment);
		if (this.sender != null) {
			req.setSender(this.sender);
		}
		if (this.sendBccTo != null) {
			req.setSendBccTo(this.sendBccTo);
		}

        return req;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public Map<String, Byte[]> getLstAttachment() {
        return lstAttachment;
    }

    public void setLstAttachment(Map<String, Byte[]> lstAttachment) {
        this.lstAttachment = lstAttachment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	public String getDomainPath() {
		return domainPath;
	}

	public void setDomainPath(String domainPath) {
		this.domainPath = domainPath;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getSendBccTo() {
		return sendBccTo;
	}

	public void setSendBccTo(String[] sendBccTo) {
		this.sendBccTo = sendBccTo;
	}
    
}
