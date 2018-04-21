package com.acceval.core.audit;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import com.acceval.core.amqp.MessageBody;
import com.acceval.core.amqp.audit.PathAuditQueueSender;
import com.acceval.core.amqp.audit.PathAuthenticationRequest;


@Component
@IgnoreAuditScan
public class PathAuthenticationLogger {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PathAuditQueueSender auditQueueSender;	
	
	@EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {
         
		AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
		logger.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());

        Map<String, Object> data = (Map<String, Object>) auditEvent.getData();
        
        PathAuthenticationRequest pathRequest = new PathAuthenticationRequest();
        pathRequest.setPrincipal(auditEvent.getPrincipal());
        pathRequest.setType(auditEvent.getType());
        pathRequest.setTimestamp(auditEvent.getTimestamp());
		
        for (Map.Entry<String, Object> entry: data.entrySet()) {
        	
        		//Error such as invalid token.
        		if (entry.getValue() instanceof String) {
        			
        			logger.info(entry.getKey() + " : " + entry.getValue());
        		
        		//Any microservice method calls
        		} else if (entry.getValue() instanceof OAuth2AuthenticationDetails) {
        			
        			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) entry.getValue();
        			logger.info("remote address :" + details.getRemoteAddress());
        			logger.info("token type :" + details.getTokenType());
        			logger.info("token value : " + details.getTokenValue());
        			pathRequest.setRemoteAddress(details.getRemoteAddress());
        			pathRequest.setTokenType(details.getTokenType());
        			pathRequest.setRemoteAddress(details.getRemoteAddress());
        			
        		//OAuth login authentication
        		} //else if (entry.getValue() instanceof Map) {
//        			Map<String, String> details = (Map<String, String>) entry.getValue();
//        			
//        			for (Map.Entry<String, String> detail: details.entrySet()) {
//        				logger.info(detail.getKey() + " : " + detail.getValue());
//        			}
//        		}
        }        
        

		MessageBody<PathAuthenticationRequest> messageBody = new MessageBody<PathAuthenticationRequest>(pathRequest);
		auditQueueSender.setMessageBody(messageBody);
		
		auditQueueSender.sendMessage();
    }	

}
