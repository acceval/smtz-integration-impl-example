package com.acceval.core.notification;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.acceval.core.amqp.QueueRequest;

public class NotificationRequest extends QueueRequest {
	
	private static final long serialVersionUID = 1L;
	
	public enum Application {
		MARKETPLACE,
		ENTERPRISE				
	}
	
	public enum MessageStatus {
		CREATED,
		SENT,
		READ			
	}
	
	private String uuid;
	private Long userId;
	private String username;
	private Long companyId;
	@Enumerated(EnumType.STRING)
	private Application application;
	private String notificationFunction;
	private String langCode;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus messageStatus;	
	private LocalDateTime readTime;
	private LocalDateTime sentTime;
	private String receipientAction;
	
	private List<NotificationParam> notificationParams;
	private String imageUrl;
	private String altText;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getNotificationFunction() {
		return notificationFunction;
	}

	public void setNotificationFunction(String notificationFunction) {
		this.notificationFunction = notificationFunction;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	public LocalDateTime getReadTime() {
		return readTime;
	}

	public void setReadTime(LocalDateTime readTime) {
		this.readTime = readTime;
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}

	public String getReceipientAction() {
		return receipientAction;
	}

	public void setReceipientAction(String receipientAction) {
		this.receipientAction = receipientAction;
	}

	public List<NotificationParam> getNotificationParams() {
		return notificationParams;
	}

	public void setNotificationParams(List<NotificationParam> notificationParams) {
		this.notificationParams = notificationParams;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
