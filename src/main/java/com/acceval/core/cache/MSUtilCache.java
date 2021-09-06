package com.acceval.core.cache;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.microservice.MicroServiceUtil;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Deprecated
//@Component
//@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class MSUtilCache implements CacheIF {

	private static final Logger logger = LoggerFactory.getLogger(MSUtilCache.class);

	private String applicationName;
	private CacheInstance cacheInstance;

	private MSUtilCache(@Value("${microservice.env}") String env, @Value("${spring.application.name}") String applicationName,
			@Autowired CacheInstance cacheInstance) {
		if (StringUtils.isNotBlank(env) && StringUtils.containsIgnoreCase(env, "local")) {
			UUID uuid = UUID.randomUUID();
			this.applicationName = uuid.toString() + ":" + applicationName;
		} else {
			this.applicationName = env + ":" + applicationName;
		}

		this.cacheInstance = cacheInstance;
		MapConfig mapConfig = new MapConfig(applicationName);
		mapConfig.setTimeToLiveSeconds(CacheInstance.TIME_TO_LIVE_SECONDS);
		mapConfig.setMaxIdleSeconds(CacheInstance.MAX_IDLE_SECONDS);
		this.cacheInstance.getInstance().getConfig().addMapConfig(mapConfig);
	}

	private IMap<String, Object> getIMap(String companyID) {
		String key = applicationName + companyID;
		return cacheInstance.getInstance().getMap(key);
	}

	public Object restTemplateExchange(MicroServiceUtil service, RestTemplate restTemplate, String token, String baseUrl, String url,
			String companyID, Class<?> type) throws Throwable {
		String cacheKey = url.replace(baseUrl, companyID);
		Long start = System.currentTimeMillis();
		Object object = this.get(cacheKey, companyID);
		if (object == null) {
			start = System.currentTimeMillis();
			object = service.fireRequest(restTemplate, token, url, type);
			this.put(cacheKey, object, companyID);
			logger.debug("%%%%%%%%% [fire] restTemplateExchange: " + (System.currentTimeMillis() - start) + " msec key: " + cacheKey);
		} else {
			logger.debug("%%%%%%%%% [cache] restTemplateExchange: " + (System.currentTimeMillis() - start) + " msec key: " + cacheKey);
		}
		return object;
	}

	public Object put(String key, Object object, String companyID) {
		if (object == null) return object;

		IMap<String, Object> map = getIMap(companyID);
		return map.putIfAbsent(key, object);
	}

	public Object get(String key, String companyID) {
		IMap<String, Object> map = getIMap(companyID);
		return map.get(key);
	}

	public void testGetAllService() {
		//		Collection<DistributedObject> distributedObjects = testGetInstance().getDistributedObjects();
		//		System.out.println("testGetAllService " + applicationName + " ==> " + testGetInstance().getName());
		Set<String> keys = this.cacheInstance.getInstance().getConfig().getMapConfigs().keySet();
		for (String mapKey : keys) {
			System.out.println("mapKey: " + mapKey);
		}
		//		for (DistributedObject object : distributedObjects) {
		//			if (object instanceof IMap) {
		//				IMap map = testGetInstance().getMap(object.getName());
		//				System.out.println("Mapname=" + map.getName() + "=>" + object.getServiceName());
		//			}
		//		}
	}

	@Override
	public void clearAll(String companyID) {
		getIMap(companyID).clear();
	}

	@Override
	public CacheInfo getCacheInfo(String companyID) {
		CacheInfo cacheInfo = new CacheInfo();
		cacheInfo.setServiceName(applicationName);
		cacheInfo.setCacheClass(MSUtilCache.class.getName());
		cacheInfo.setName("Microservice Object Dependancy");
		cacheInfo.setActiveCache(getIMap(companyID).keySet().size());
		return cacheInfo;
	}

	public HazelcastInstance getInstance() {
		return cacheInstance.getInstance();
	}

}
