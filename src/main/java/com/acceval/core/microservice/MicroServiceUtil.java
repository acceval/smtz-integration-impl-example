package com.acceval.core.microservice;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.MicroServiceUtilException;

public class MicroServiceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceUtil.class);

	public static Object getForObject(MicroServiceRequest microServiceRequest, Class<?> type)
			throws MicroServiceUtilException {
		return getForObject(microServiceRequest, null, type);
	}

	public static Object getForObject(MicroServiceRequest microServiceRequest, MultiValueMap<String, String> mvmValue,
			Class<?> type) throws MicroServiceUtilException {

		/** null checking */
		microServiceRequest.assertNull();
		Optional.ofNullable(type)
				.orElseThrow(() -> new MicroServiceUtilException(MicroServiceUtil.class, "REST Type is null."));

		DiscoveryClient discoveryClient = microServiceRequest.getDiscoveryClient();
		RestTemplate restTemplate = microServiceRequest.getRestTemplate();
		String zuulService = "ZUUL-SERVER";
		String msService = microServiceRequest.getMsService();
		String msFunction = microServiceRequest.getMsFunction();
		String param = microServiceRequest.getParam() == null ? "" : microServiceRequest.getParam();
		String token = microServiceRequest.getToken();

		List<ServiceInstance> instances = discoveryClient.getInstances(zuulService);
		if (instances.isEmpty())
			throw new MicroServiceUtilException(MicroServiceUtil.class, zuulService + " Service is Not Available!");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		String url = "http:// " + msService.replaceFirst(Pattern.quote("/"), "") + "/" + msFunction + "/" + param;

		if (mvmValue != null && !mvmValue.keySet().isEmpty()) {
			UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromHttpUrl(url);
			for (String key : mvmValue.keySet()) {
				uriCompBuilder.queryParam(key, mvmValue.get(key).toArray());
			}
			url = uriCompBuilder.toUriString();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Firing URL: " + url);
		}
		try {
			Object object = null;
			if (token != null) {
				HttpHeaders headers = new HttpHeaders();
				// headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", token);
				HttpEntity<String> entity = new HttpEntity<String>("", headers);
				ResponseEntity respEntity = restTemplate.exchange(url, HttpMethod.GET, entity, type);
				object = respEntity.getBody();
			} else {
				object = restTemplate.getForObject(url, type);
			}

			return object;
		} catch (Throwable e) {
			LOGGER.error("Error occur when fire [" + url + "] \r\n" + e.getMessage(), e);
		}

		return null;
	}
}
