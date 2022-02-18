package com.acceval.core.cache;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Deprecated
//@Component
//@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class CacheInstance {

	public static final int TIME_TO_LIVE_SECONDS = 24 * 60 * 60;
	public static final int MAX_IDLE_SECONDS = 24 * 60 * 60;

	private HazelcastInstance hazelcastInstance;

	private CacheInstance(@Value("${spring.application.name}") String applicationName) {
		Config config = new Config();
		config.getGroupConfig().setName(applicationName);
		this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	}

	public HazelcastInstance getInstance() {
		return hazelcastInstance;
	}

	@PreDestroy
	public void shutDownHazelcast() {
		//		hazelcastInstance.shutdown();
	}
}
