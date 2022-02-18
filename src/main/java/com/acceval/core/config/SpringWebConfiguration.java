package com.acceval.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.acceval.core.security.PrincipalProviderHandlerInterceptor;

@Configuration
public class SpringWebConfiguration extends WebMvcConfigurerAdapter {
	
	private final PrincipalProviderHandlerInterceptor  principalInterceptor;
//	private final AuditLogHandlerInterceptor auditLogInterceptor;

	public SpringWebConfiguration(PrincipalProviderHandlerInterceptor principalInterceptor) {
		
		this.principalInterceptor = principalInterceptor;		
	}
	
//	public SpringWebConfiguration(PrincipalProviderHandlerInterceptor principalInterceptor, 
//			AuditLogHandlerInterceptor auditLogInterceptor) {
//		
//		this.principalInterceptor = principalInterceptor;
//		this.auditLogInterceptor = auditLogInterceptor;
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(principalInterceptor);
//		registry.addInterceptor(auditLogInterceptor);
	}
}

