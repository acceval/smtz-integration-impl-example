package com.acceval.core.model;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
                
    		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		
    		if (principal instanceof AuthUser) {
    			AuthUser authUser = (AuthUser) principal;
    	
		    	if (authUser != null) {
		    		return authUser.getUsername();
		    	} 
    		}    			
    		return "anonymousUser";    	
    }
}