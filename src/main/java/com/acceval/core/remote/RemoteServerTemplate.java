package com.acceval.core.remote;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.jackson.module.APIJavaTimeModule;
import com.acceval.core.model.Company;

@Component
public class RemoteServerTemplate {

	private RemoteConfig remoteConfig;

	public RemoteServerTemplate() {
	}

	public RemoteServerTemplate(RemoteConfig config) {

		this.remoteConfig = config;
	}

	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables, String companyUUID) {

		RestTemplate restTemplate = new RestTemplate();
		String token = this.getRemoteServerToken();
		String completeUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		Company company = this.getRemoteSystemCompany(token, new HashMap<String, String>(), companyUUID);
		this.setHttpCompanyHeader(bearerHeaders, company);
		HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(completeUrl);

		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
		}

		for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {

				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

				jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
			}
		}

		ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, responseType);

		return response.getBody();

	}

	public <T> T exchange(String url, HttpMethod httpMethod, ParameterizedTypeReference<T> typeReference, Map<String, ?> uriVariables,
			String companyUUID) {

		RestTemplate restTemplate = new RestTemplate();
		String token = this.getRemoteServerToken();
		String completeUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);

		for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {

				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());

			}
		}

		Company company = this.getRemoteSystemCompany(token, new HashMap<String, String>(), companyUUID);
		this.setHttpCompanyHeader(bearerHeaders, company);

		HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(completeUrl);

		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
		}

		ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(), httpMethod, bearerEntity, typeReference);

		return response.getBody();

	}

	private void setHttpCompanyHeader(HttpHeaders bearerHeaders, Company company) {

		if (company != null) {
			bearerHeaders.add("COMPANYID", String.valueOf(company.getId()));
			bearerHeaders.add("COMPANYCODE", company.getCode());
			bearerHeaders.add("SERVICEPACKAGE", company.getServicePackage());
			bearerHeaders.add("SCHEMANAME", company.getCode());
		}
	}

	public <T> T postForObject(String url, Object requestBody, Class<T> responseType, String companyUUID) {

		RestTemplate restTemplate = new RestTemplate();
		String token = this.getRemoteServerToken();
		String completeUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {

				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

				jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
				jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
				messageConverters.add(jsonConverter);
			}
		}

		restTemplate.setMessageConverters(messageConverters);

		Company company = this.getRemoteSystemCompany(token, new HashMap<String, String>(), companyUUID);
		this.setHttpCompanyHeader(bearerHeaders, company);
		HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);

		ResponseEntity<T> response = restTemplate.postForEntity(completeUrl, bearerEntity, responseType);

		return response.getBody();
	}

	public <T> T putForObject(String url, Object requestBody, Class<T> responseType, String companyUUID) {

		RestTemplate restTemplate = new RestTemplate();
		String token = this.getRemoteServerToken();
		String completeUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		Company company = this.getRemoteSystemCompany(token, new HashMap<String, String>(), companyUUID);
		this.setHttpCompanyHeader(bearerHeaders, company);
		HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);

		for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {

			if (converter instanceof MappingJackson2HttpMessageConverter) {

				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

				jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
			}
		}

		ResponseEntity<T> response = restTemplate.exchange(completeUrl, HttpMethod.PUT, bearerEntity, responseType);

		return response.getBody();
	}

	public String getRemoteServerToken() {

		String authUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + "/auth-service/uaa/oauth/token";

		HttpHeaders basicHeaders =
				this.createBasicHeaders(this.remoteConfig.getCredentialUser(), this.remoteConfig.getCredentialPassword());

		RestTemplate authRestTemplate = new RestTemplate();
		HttpEntity<String> basicEntity = new HttpEntity<String>(basicHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(authUrl).queryParam("grant_type", "client_credentials");

		ResponseEntity<AuthToken> authResponse =
				authRestTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, basicEntity, AuthToken.class);
		AuthToken authToken = authResponse.getBody();

		return authToken.getAccess_token();

	}

	public Company getRemoteSystemCompany(String token, Map<String, ?> uriVariables, String companyUUID) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);

		String url = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + "/identity-service/company/getObjByUuid/"
				+ companyUUID;

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
		}

		ResponseEntity<Company> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, Company.class);
		Company company = (Company) response.getBody();

		return company;
	}

	public HttpHeaders createBasicHeaders(String user, String password) {

		String notEncoded = user + ":" + password;
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", encodedAuth);
		return headers;
	}

	public HttpHeaders createBearerHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);
		return headers;
	}

	public RemoteConfig getRemoteConfig() {
		return remoteConfig;
	}

	public void setRemoteConfig(RemoteConfig remoteConfig) {
		this.remoteConfig = remoteConfig;
	}

}
