package com.acceval.core.cache;

import java.util.Set;

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

@Component
@ConditionalOnProperty(name = "micoservice.cache", havingValue = "true")
public class MSUtilCache implements CacheIF {

	private static final Logger logger = LoggerFactory.getLogger(MSUtilCache.class);

	private String applicationName;
	private CacheInstance cacheInstance;

	private MSUtilCache(@Value("${spring.application.name}") String applicationName, @Autowired CacheInstance cacheInstance) {
		this.applicationName = applicationName;
		this.cacheInstance = cacheInstance;
		MapConfig mapConfig = new MapConfig(applicationName);
		mapConfig.setTimeToLiveSeconds(CacheInstance.TIME_TO_LIVE_SECONDS);
		mapConfig.setMaxIdleSeconds(CacheInstance.MAX_IDLE_SECONDS);
		this.cacheInstance.getInstance().getConfig().addMapConfig(mapConfig);
	}

	public Object restTemplateExchange(MicroServiceUtil service, RestTemplate restTemplate, String token, String baseUrl, String url,
			String companyID, Class<?> type) throws Throwable {
		String cacheKey = url.replace(baseUrl, companyID);
		Long start = System.currentTimeMillis();
		Object object = this.get(cacheKey);
		if (object == null) {
			start = System.currentTimeMillis();
			object = service.fireRequest(restTemplate, token, url, type);
			this.put(cacheKey, object);
			logger.debug("%%%%%%%%% [fire] restTemplateExchange: " + (System.currentTimeMillis() - start) + " msec key: " + cacheKey);
		} else {
			logger.debug("%%%%%%%%% [cache] restTemplateExchange: " + (System.currentTimeMillis() - start) + " msec key: " + cacheKey);
		}
		return object;
	}

	public Object put(String key, Object object) {
		IMap<String, Object> map = cacheInstance.getInstance().getMap(applicationName);
		return map.putIfAbsent(key, object);
	}

	public Object get(String key) {
		IMap<String, Object> map = cacheInstance.getInstance().getMap(applicationName);
		return map.get(key);
	}

	private IMap<String, Object> getMap() {
		return cacheInstance.getInstance().getMap(applicationName);
	}

	@Override
	public void clearAll() {
		getMap().clear();
	}

	@Override
	public CacheInfo getCacheInfo() {
		CacheInfo cacheInfo = new CacheInfo();
		cacheInfo.setCacheClass(MSUtilCache.class.getName());
		cacheInfo.setName(applicationName + " - Object Dependancy");
		cacheInfo.setActiveCache(getMap().keySet().size());
		return cacheInfo;
	}

	// TODO testing method
	public HazelcastInstance testGetInstance() {
		return cacheInstance.getInstance();
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

	public void testGetOtherService() {
		IMap imap = testGetInstance().getDistributedObject("hz:impl:mapService", "masterdata-service");
		System.out.println(imap.get("key"));
	}

}
