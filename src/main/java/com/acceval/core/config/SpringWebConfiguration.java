package com.acceval.core.config;

import com.acceval.core.security.PrincipalProviderHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringWebConfiguration extends WebMvcConfigurerAdapter {
	private final PrincipalProviderHandlerInterceptor interceptor;

	public SpringWebConfiguration(PrincipalProviderHandlerInterceptor interceptor) {
		this.interceptor = interceptor;
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}
}
