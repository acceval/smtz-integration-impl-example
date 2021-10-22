package com.acceval.core.job.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobLoggerConfig {
	
	@Bean
	public SchedulerJobLogger jobLogger() {
		return new DefaultJobLogger();
	}
}
