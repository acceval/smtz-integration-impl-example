package com.acceval.core.audit;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.acceval.core.amqp.AuditLogQueueSender;
import com.acceval.core.amqp.audit.PathAuthenticationRequest;

@Component
public class PathAuthenticationLogger {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String[] AUDITED_URL_PATH = {
			"/role/set-accounts",
			"/function/saveAngularFunction"
	};
	
	@Autowired
	private AuditLogQueueSender auditLogQueueSender;
	
	@EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {
         
		AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
		
		List<String> auditedPaths = Arrays.asList(AUDITED_URL_PATH);		
		
		String requestUrl = this.getRequestUrl();
		if (requestUrl == null || !this.startWithPath(requestUrl, auditedPaths)) {
			return;
		}

        Map<String, Object> data = (Map<String, Object>) auditEvent.getData();
        
        PathAuthenticationRequest pathRequest = new PathAuthenticationRequest();
        pathRequest.setPrincipal(auditEvent.getPrincipal());        
        pathRequest.setType(auditEvent.getType());
        pathRequest.setTimestamp(new Date());
		
        for (Map.Entry<String, Object> entry: data.entrySet()) {
        	
    		// Error for invalid token & access denied (without token)
    		if (entry.getValue() instanceof String) {
    			
    			 if (entry.getKey().equals("type")) {
    				 pathRequest.setType((String) entry.getValue());
    			 } else if (entry.getKey().equals("message")) {
    				 pathRequest.setTokenValue((String) entry.getValue());
    			 } else if (entry.getKey().equals("requestUrl")) {
    				 pathRequest.setRequestUrl((String) entry.getValue());        				         				 
    			 }
    			 
    			 if (pathRequest.getRequestUrl() == null) {
    				 
    				 pathRequest.setRequestUrl(this.getRequestUrl());
    			 }
    		// Error for access denied (without token)
    		} else if (entry.getValue() instanceof WebAuthenticationDetails) {
    			
    			WebAuthenticationDetails details = (WebAuthenticationDetails) entry.getValue();
    			pathRequest.setRemoteAddress(details.getRemoteAddress());
    			pathRequest.setSessionId(details.getSessionId());
    			
    		// Any microservice method calls
    		} else if (entry.getValue() instanceof OAuth2AuthenticationDetails) {
    			        			
    			pathRequest.setRequestUrl(this.getRequestUrl());
    			        			
    			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) entry.getValue();
    			pathRequest.setRemoteAddress(details.getRemoteAddress());
    			pathRequest.setTokenType(details.getTokenType());
    			pathRequest.setTokenValue(details.getTokenValue());
    			    			
    		// OAuth login authentication
    		} //else if (entry.getValue() instanceof Map) {
//        		Map<String, String> details = (Map<String, String>) entry.getValue();
//        			
//        		for (Map.Entry<String, String> detail: details.entrySet()) {
//        			logger.info(detail.getKey() + " : " + detail.getValue());
//        		}
//        	}
        }        
        
        try {
        	auditLogQueueSender.sendMessage(pathRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }	

	private String getRequestUrl() {
		
		if (RequestContextHolder.getRequestAttributes() != null) {
			String className = RequestContextHolder.getRequestAttributes().getClass().getSimpleName();
			
			if (className.equals("ServletRequestAttributes")) {
				
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest httpRequest = attributes.getRequest();
				return httpRequest.getRequestURI();    			
			}
		}
		
		return null;
	}
	
	private boolean startWithPath(String requestUrl, List<String> auditedPaths) {
		
		for (String path: auditedPaths) {
			if (requestUrl.startsWith(path)) {
				return true;
			}
		}
		
		return false;
	}
	
}
