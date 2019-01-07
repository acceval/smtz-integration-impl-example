package com.acceval.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {


	public static String getSchemaName() {
				
		String schemaName = "shared";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof CurrentUser) {
				
				CurrentUser currentUser = (CurrentUser) principal;
				
				if (currentUser.getCompanyCode() != null) {
					
					schemaName = currentUser.getCompanyCode().toLowerCase();			
				}
			}		
		}
		return schemaName;
	}
	
	public static Long getCompanyId() {
						
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof CurrentUser) {
				
				CurrentUser currentUser = (CurrentUser) principal;
				return currentUser.getCompanyId();
			}
		}
		return null;
	}	
	
	public static String getCompanyCode() {
						
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof CurrentUser) {
				
				CurrentUser currentUser = (CurrentUser) principal;
				return currentUser.getCompanyCode();
			}
		}
		return null;
	}	
		
}
