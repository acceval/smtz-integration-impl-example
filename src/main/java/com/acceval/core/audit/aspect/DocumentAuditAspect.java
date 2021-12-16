package com.acceval.core.audit.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.acceval.core.amqp.AuditLogQueueSender;
import com.acceval.core.amqp.AuditTrailRequest;
import com.acceval.core.audit.AuditDocument;
import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;

@Aspect
@Component
public class DocumentAuditAspect {
		
	@Autowired
	private AuditLogQueueSender auditLogQueueSender;
	
    @Around("@annotation(com.acceval.core.audit.AuditDocument)")
    public Object auditDocument(ProceedingJoinPoint joinPoint) throws Throwable {
    	
    	Object proceed = null;
        
		AuditTrailRequest logRequest = new AuditTrailRequest();
		logRequest.setLogTime(LocalDateTime.now());
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();    	
			    	
		Method method = signature.getMethod();
		AuditDocument auditDocument = method.getAnnotation(AuditDocument.class);
		logRequest.setAuditType(auditDocument.auditType());
		
		Object[] args = joinPoint.getArgs();
		
		logRequest.setInfo1(auditDocument.documentType());
		
		if (auditDocument.contentType().equals("MULTIPART")) {
			
			for (Object arg: args) {
				if (arg instanceof MultipartFile) {
					MultipartFile file = (MultipartFile) arg;
					logRequest.setInfo3(file.getOriginalFilename());				
				}
				
				if (arg instanceof String) {
					String fileFunction = (String) arg;
					if (auditDocument.documentType().equals("Condition Record")							
							|| auditDocument.documentType().equals("Allocation Record")							
							|| auditDocument.documentType().equals("Dimension Record")
							|| auditDocument.documentType().equals("Transaction Record")) {
						
						String functionName = fileFunction.replace("_", " ");
						functionName = WordUtils.capitalize(functionName);
						logRequest.setInfo2(functionName);
						
					} else {
						logRequest.setInfo2(fileFunction);
					}					
				}
			}
			
		} else if (auditDocument.contentType().equals("TEXT")) {
			
			if (args.length > 0 && args[0] instanceof String) {
				
				String filename = (String) args[0];
				logRequest.setInfo2(filename);
			}
		} else if (auditDocument.contentType().equals("HTTP")) {
			
			if (args.length > 1 && args[0] instanceof String 
					&& args[1] instanceof HttpServletRequest) {
				String bucket = (String) args[0];
				logRequest.setInfo2(bucket);
				HttpServletRequest request = (HttpServletRequest) args[1];
			
				String uri = request.getRequestURI();
				int lastIndex = uri.lastIndexOf("/");				
				if (lastIndex != -1) {
					String file = uri.substring(lastIndex + 1);
					logRequest.setInfo3(file);
				}
			}			
		} else if (auditDocument.contentType().equals("CUSTOM")) {
			
			String[] words = method.getName().split("(?=\\p{Upper})");
			String name = "";
			for (String word: words) {
				if (name.length() == 0) {
					name = StringUtils.capitalize(word);
				} else {
					name = name + " " + word;
				}
			}
			logRequest.setInfo2(name);
			
		} else if (auditDocument.contentType().equals("TEMPLATE")) {
			
			if (args.length > 1 && args[0] instanceof String 
					&& args[1] instanceof String) {
				
				String fileFunction = (String) args[0];
				String filename = (String) args[1];				
				logRequest.setInfo2(fileFunction);
				logRequest.setInfo3(filename);
			} else if ( args.length > 0 && args[0] instanceof String) {
				String fileFunction = (String) args[0];
				if (auditDocument.documentType().equals("Condition Record Template")							
						|| auditDocument.documentType().equals("Allocation Record Template")) {
					
					String functionName = fileFunction.replace("_", " ");
					functionName = WordUtils.capitalize(functionName);
					logRequest.setInfo2(functionName);
					
				} else {
					logRequest.setInfo2(fileFunction);
				}
			}
		} else if (auditDocument.contentType().equals("RECORD")) {
						
			if ( args.length > 0 && args[0] instanceof String) {
				
				String fileFunction = (String) args[0];
				
				String functionName = fileFunction.replace("_", " ");
				functionName = WordUtils.capitalize(functionName);
				logRequest.setInfo2(functionName);
				
				logRequest.setInfo3(fileFunction);
			}
		}

	    proceed = joinPoint.proceed();

		/** other info */
		CurrentUser currentUser = PrincipalUtil.getCurrentUser();
		if (currentUser != null && currentUser.getId() != null) {
			logRequest.setUserId(currentUser.getId());
			logRequest.setEmail(currentUser.getEmail());
			logRequest.setUsername(currentUser.getUsername());
			logRequest.setFirstName(currentUser.getFirstName());
			logRequest.setLastName(currentUser.getLastName());
			logRequest.setCompanyId(currentUser.getCompanyId());
			logRequest.setCompanyCode(currentUser.getCompanyCode());
		}
		
		logRequest.setUuid(UUID.randomUUID().toString());			
		
		auditLogQueueSender.sendMessage(logRequest);
		
        return proceed;
    }
    
    
}
