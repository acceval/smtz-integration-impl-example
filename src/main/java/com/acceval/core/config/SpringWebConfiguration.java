package com.acceval.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.acceval.core.eventlog.EventLogHandlerInterceptor;
import com.acceval.core.security.PrincipalProviderHandlerInterceptor;

@Configuration
public class SpringWebConfiguration extends WebMvcConfigurerAdapter {
	private final PrincipalProviderHandlerInterceptor interceptor;
	private final EventLogHandlerInterceptor eventLogInterceptor;

	public SpringWebConfiguration(PrincipalProviderHandlerInterceptor interceptor, EventLogHandlerInterceptor eventLogInterceptor) {
		this.interceptor = interceptor;
		this.eventLogInterceptor = eventLogInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
		registry.addInterceptor(eventLogInterceptor);
	}
}

