package com.acceval.core.microservice;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;

public class MicroServiceRequest {

	private DiscoveryClient discoveryClient;
	private RestTemplate restTemplate;
	private String token;
	private String msService;
	private String msFunction;
	private String param;

	public MicroServiceRequest(DiscoveryClient discoveryClient, RestTemplate restTemplate, String token, String msService,
			String msFunction, String param) {
		super();
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
		this.token = token;
		this.msService = msService;
		this.msFunction = msFunction;
		this.param = param;
	}

	/**
	 * @deprecated not recommend as most fields are required, call full properties constructor instead
	 */
	@Deprecated
	public MicroServiceRequest() {
		super();
	}

	public void assertNull() throws MicroServiceUtilException {
		assertNull(null);
	}

	public void assertNull(Class<?> clazz) throws MicroServiceUtilException {
		Optional.ofNullable(discoveryClient).orElseThrow(() -> new MicroServiceUtilException(clazz, "DiscoveryClient is null."));
		Optional.ofNullable(restTemplate).orElseThrow(() -> new MicroServiceUtilException(clazz, "RestTemplate is null."));
		if (StringUtils.isBlank(msService)) new MicroServiceUtilException(clazz, "Microservice Service Name is null.");
		if (StringUtils.isBlank(msFunction)) new MicroServiceUtilException(clazz, "Microservice Function is null.");
		//		if (StringUtils.isBlank(param)) new MicroServiceUtilException(clazz,"Microservice Request is null.");
	}

	public DiscoveryClient getDiscoveryClient() {
		return discoveryClient;
	}

	public void setDiscoveryClient(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getMsService() {
		return msService;
	}

	public void setMsService(String msService) {
		this.msService = msService;
	}

	public String getMsFunction() {
		return msFunction;
	}

	public void setMsFunction(String msFunction) {
		this.msFunction = msFunction;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
