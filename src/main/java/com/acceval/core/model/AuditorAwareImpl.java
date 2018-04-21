package com.acceval.core.model;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
                
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		if (authentication != null) {
    			return (String) authentication.getPrincipal();
    		} else {
    			return "anonymousUser";
    		}
    }
}