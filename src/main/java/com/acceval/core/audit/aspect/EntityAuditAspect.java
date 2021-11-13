package com.acceval.core.audit.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.acceval.core.amqp.AuditLogQueueSender;
import com.acceval.core.amqp.AuditTrailChange;
import com.acceval.core.amqp.AuditTrailRequest;
import com.acceval.core.audit.AuditEntity;
import com.acceval.core.audit.AuditType;
import com.acceval.core.controller.GenericCommonController;
import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.ClassUtil;

@Aspect
@Component
public class EntityAuditAspect extends CompareAspect {
	
	@Autowired
	private GenericCommonController genericCommonController;
		
	@Autowired
	private AuditLogQueueSender auditLogQueueSender;
	
    @Around("@annotation(com.acceval.core.audit.AuditEntity)")
    public Object auditEntityModel(ProceedingJoinPoint joinPoint) throws Throwable {
    	
    	Object proceed = null;
        
		AuditTrailRequest logRequest = new AuditTrailRequest();
		logRequest.setLogTime(LocalDateTime.now());
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();    	
			    	
		Method method = signature.getMethod();
		AuditEntity auditEntity = method.getAnnotation(AuditEntity.class);
		
		logRequest.setInfo1(auditEntity.entity());
		        
	    if (auditEntity.auditType() == AuditType.UPDATE) {
	    		        	
	    	Type returnType = signature.getReturnType();
	    	
	    	if (returnType == null) {	
	    		proceed = joinPoint.proceed();
	    		return proceed;
	    	}
	    		        	
	    	Object queryObject = null; 
	    	
	    	try {
				String primaryKey = ClassUtil.getPrimaryKeyName(Class.forName(returnType.getTypeName()));
				Object[] args = joinPoint.getArgs();
								
				if (StringUtils.isNotBlank(primaryKey) && args.length > 0) {
					String id = args[0].toString();
					logRequest.setInfo2(id);
					MultiValueMap<String, String> mapParam = new LinkedMultiValueMap<>();
					mapParam.add(GenericCommonController.KEY_IS_COLLECTION, "N");
					mapParam.add(GenericCommonController.KEY_ENTITY_CLASS, returnType.getTypeName());					
					mapParam.add(primaryKey, id);
					
					queryObject = genericCommonController.query(mapParam);					
				}
	    	} catch (Throwable t) {
	    		t.printStackTrace();
	    	}
        	
	        proceed = joinPoint.proceed();
	        
	        if (queryObject != null && proceed != null) {
	        	
	        	List<AuditTrailChange> changes = this.compareChanges(queryObject, proceed);
	    		
	    		if (changes.size() > 0) {
	    			logRequest.setAuditTrailChanges(changes);
	    		}
	        } 
        	
        } else if (auditEntity.auditType() == AuditType.CREATE) {
        		         	
        	Type returnType = signature.getReturnType();
	    	
	    	if (returnType == null) {	
	    		proceed = joinPoint.proceed();
	    		return proceed;
	    	}		    		    
        	
        	proceed = joinPoint.proceed();	        	
        	String primaryKey = ClassUtil.getPrimaryKeyName(Class.forName(returnType.getTypeName()));

        	if (StringUtils.isNotBlank(primaryKey) && proceed != null) {
	        	Method idMethod = ClassUtil.getGetterMethod(Class.forName(returnType.getTypeName()), 
	        			primaryKey);
				Long id = (Long) idMethod.invoke(proceed, new Object[] {});
				logRequest.setInfo2(id.toString());
        	}
        	
        } else if (auditEntity.auditType() == AuditType.DELETE) {
        	
        	proceed = joinPoint.proceed();
        	
        	Object[] args = joinPoint.getArgs();
        	
        	if (args.length > 0) {
        		if (args[0] instanceof Long) {
        			Long id = (Long) args[0];
        			logRequest.setInfo2(id.toString());
        		} else {
	        		Long[] ids = (Long[]) args[0];
	        		
	        		String idText = "";
	        		boolean isFirst = true;
	        		for (Long id: ids) {
	        			if (!isFirst) {
	        				idText = idText + ", " + id.toString();
	        			} else {
	        				idText = id.toString();
	        			}
	        			isFirst = false;
	        		}
	        		
	        		logRequest.setInfo2(idText);
        		}
        	}
        }
        	
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
		logRequest.setAuditType(auditEntity.auditType());
		
		auditLogQueueSender.sendMessage(logRequest);
		
        return proceed;
    }
    
}
