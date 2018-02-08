package com.acceval.core.microservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;

public class MicroServiceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceUtil.class);

	public static Object getForObject(MicroServiceRequest microServiceRequest, Class<?> type) throws MicroServiceUtilException {

		/** null checking */
		microServiceRequest.assertNull();
		Optional.ofNullable(type).orElseThrow(() -> new MicroServiceUtilException(MicroServiceUtil.class, "REST Type is null."));

		DiscoveryClient discoveryClient = microServiceRequest.getDiscoveryClient();
		RestTemplate restTemplate = microServiceRequest.getRestTemplate();
		String msService = microServiceRequest.getMsService();
		String msFunction = microServiceRequest.getMsFunction();
		String param = microServiceRequest.getParam() == null ? "" : microServiceRequest.getParam();

		List<ServiceInstance> instances = discoveryClient.getInstances(msService);
		if (instances.isEmpty()) throw new MicroServiceUtilException(MicroServiceUtil.class, msService + " Service is Not Available!");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		String url = "http://" + host + ":" + instance.getPort() + "/" + msFunction + "/" + param;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Firing URL: " + url);
		}
		Object object = restTemplate.getForObject(url, type);

		return object;
	}
}
