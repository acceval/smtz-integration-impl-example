package com.acceval.core.microservice;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.util.BaseBeanUtil;

@Service
public class MicroServiceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServiceUtil.class);

	@Value("${micoservice.url}")
	private String url;

	public Object getForObject(MicroServiceRequest microServiceRequest, Class<?> type)
			throws MicroServiceUtilException {
		return getForObject(microServiceRequest, null, type);
	}

	public Object getForObject(MicroServiceRequest microServiceRequest, MultiValueMap<String, String> mvmValue,
			Class<?> type) throws MicroServiceUtilException {

		/** null checking */
		microServiceRequest.assertNull();
		Optional.ofNullable(type)
				.orElseThrow(() -> new MicroServiceUtilException(MicroServiceUtil.class, "REST Type is null."));

		//		DiscoveryClient discoveryClient = microServiceRequest.getDiscoveryClient();
		RestTemplate restTemplate = BaseBeanUtil.getBean(OAuth2RestTemplate.class);
		//		String zuulService = "ZUUL-SERVER";
		String msService = microServiceRequest.getMsService();
		String msFunction = microServiceRequest.getMsFunction();
		String param = microServiceRequest.getParam() == null ? "" : microServiceRequest.getParam();
		String token = microServiceRequest.getToken();

		//		List<ServiceInstance> instances = discoveryClient.getInstances(zuulService);
		//		if (instances.isEmpty())
		//			throw new MicroServiceUtilException(MicroServiceUtil.class, zuulService + " Service is Not Available!");
		//		ServiceInstance instance = instances.get(0);
		//		String host = instance.getHost();
		//		String url = "http://" + msService.replaceFirst(Pattern.quote("/"), "") + "/" + msFunction + "/" + param;

		String url2 = url + "/" + msService.replaceFirst(Pattern.quote("/"), "")
				+ "/" + msFunction + "/" + param;

		if (mvmValue != null && !mvmValue.keySet().isEmpty()) {
			UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromHttpUrl(url2);
			for (String key : mvmValue.keySet()) {
				uriCompBuilder.queryParam(key, mvmValue.get(key).toArray());
			}
			url2 = uriCompBuilder.toUriString();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Firing URL: " + url2);
		}
		try {
			Object object = null;
			if (token != null) {
				HttpHeaders headers = new HttpHeaders();
				// headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", token);
				HttpEntity<String> entity = new HttpEntity<String>("", headers);
				ResponseEntity respEntity = restTemplate.exchange(url2, HttpMethod.GET, entity, type);
				object = respEntity.getBody();
			} else {
				object = restTemplate.getForObject(url2, type);
			}

			return object;
		} catch (Throwable e) {
			LOGGER.error("Error occur when fire [" + url2 + "] \r\n" + e.getMessage(), e);
		}

		return null;
	}
}
