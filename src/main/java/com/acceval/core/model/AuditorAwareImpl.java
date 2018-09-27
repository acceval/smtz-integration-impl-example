package com.acceval.core.model;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
                
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		
    		if (auth != null) {
	    		Object principal = auth.getPrincipal();
	    		
	    		if (principal != null && principal instanceof AuthUser) {
	    			
	    			AuthUser authUser = (AuthUser) principal;	    	
			    	return authUser.getUsername();		 
	    		}    			
    		}
    		return "anonymousUser";    	
    }
}