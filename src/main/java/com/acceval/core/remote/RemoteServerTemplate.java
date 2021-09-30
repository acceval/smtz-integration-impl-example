package com.acceval.core.remote;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.jackson.module.APIJavaTimeModule;
import com.acceval.core.model.Company;
import com.acceval.core.security.PrincipalUtil;

@Component
public class RemoteServerTemplate {

	public static final String HDRKEY_ERRORMSG = "errorMsg";

	private RemoteConfig remoteConfig;

	public RemoteServerTemplate() {
	}

	public RemoteServerTemplate(RemoteConfig config) {

		this.remoteConfig = config;
	}
	
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables, 
			String companyUUID) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ignoreSSL(restTemplate);
		String token = this.getRemoteServerToken();
		String completeUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		Company company = this.getRemoteSystemCompany(token, new HashMap<String, String>(), companyUUID);
		this.setHttpCompanyHeader(bearerHeaders, company);
		HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);

		UriComponentsBuilder uriBuilder = buildUriComp(completeUrl);
		
		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
//			uriBuilder.queryParam(key, values);
		}

		for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {

				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

				jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
			}
		}

		ResponseEntity<T> response = null;
		
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, responseType);
		} catch (Exception ex) {
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				try {
//					response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, responseType);
//				} catch (HttpServerErrorException serverEx) {
//					throw new Exception("HttpServerErrorException: [" + url + "]");
//				} catch (Exception ex2) {
//					ex2.printStackTrace();
//					errorMsgHandler(ex2);
//				}
//			} else {
				ex.printStackTrace();
				errorMsgHandler(ex);
//			}
		}

		return response.getBody();

	}

	public <T> T exchange(String url, HttpMethod httpMethod, ParameterizedTypeReference<T> typeReference, Map<String, ?> uriVariables,
			String companyUUID) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ignoreSSL(restTemplate);
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

		UriComponentsBuilder uriBuilder = buildUriComp(completeUrl);
//		UriComponentsBuilder uriBuilderSSL = buildUriComp(completeUrl, true);

		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
//			uriBuilderSSL.queryParam(key, values);
		}

		ResponseEntity<T> response = null;
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), httpMethod, bearerEntity, typeReference);
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMsgHandler(ex);
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				response = restTemplate.exchange(uriBuilder.toUriString(), httpMethod, bearerEntity, typeReference);
//			}
		}

		return response.getBody();

	}

	private void setHttpCompanyHeader(HttpHeaders bearerHeaders, Company company) {

		if (company != null) {
			bearerHeaders.add("COMPANYID", String.valueOf(company.getId()));
			bearerHeaders.add("COMPANYCODE", company.getCode().toLowerCase());
			bearerHeaders.add("SERVICEPACKAGE", company.getServicePackage());
			bearerHeaders.add("SCHEMANAME", company.getCode().toLowerCase());
			bearerHeaders.add(PrincipalUtil.HDRKEY_TIMEZONEID, company.getTimeZone());
		}
	}

	public <T> T postForObject(String url, Object requestBody, Class<T> responseType, String companyUUID) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ignoreSSL(restTemplate);
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

		ResponseEntity<T> response = null;

		UriComponentsBuilder uriBuilder = buildUriComp(completeUrl).queryParam("grant_type", "client_credentials");
//		UriComponentsBuilder uriBuilderSSL = buildUriComp(completeUrl, true).queryParam("grant_type", "client_credentials");
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, bearerEntity, responseType);
		} catch (Exception ex) {
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				try {
//					response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, bearerEntity, responseType);
//				} catch (HttpServerErrorException serverEx) {
//					throw new Exception("HttpServerErrorException: [" + url + "]");
//				} catch (Exception ex2) {
//					ex2.printStackTrace();
//					errorMsgHandler(ex2);
//				}
//			} else {
				ex.printStackTrace();
				errorMsgHandler(ex);
//			}
		}

		return response.getBody();
	}

	public <T> T putForObject(String url, Object requestBody, Class<T> responseType, String companyUUID) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ignoreSSL(restTemplate);
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

		UriComponentsBuilder uriBuilder = buildUriComp(completeUrl).queryParam("grant_type", "client_credentials");
//		UriComponentsBuilder uriBuilderSSL = buildUriComp(completeUrl, true).queryParam("grant_type", "client_credentials");

		ResponseEntity<T> response = null;
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, bearerEntity, responseType);
		} catch (Exception ex) {
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				try {
//					response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, bearerEntity, responseType);
//				} catch (HttpServerErrorException serverEx) {
//					throw new Exception("HttpServerErrorException: [" + url + "]");
//				} catch (Exception ex2) {
//					ex2.printStackTrace();
//					errorMsgHandler(ex2);
//				}
//			} else {
				ex.printStackTrace();
				errorMsgHandler(ex);
//			}
		}

		return response != null ? response.getBody() : null;
	}

	public String getRemoteServerToken() throws Exception {

		String authUrl = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + "/auth-service/uaa/oauth/token";

		HttpHeaders basicHeaders =
				this.createBasicHeaders(this.remoteConfig.getCredentialUser(), this.remoteConfig.getCredentialPassword());

		RestTemplate authRestTemplate = new RestTemplate();
		ignoreSSL(authRestTemplate);
		HttpEntity<String> basicEntity = new HttpEntity<String>(basicHeaders);

		ResponseEntity<AuthToken> authResponse = null;
		try {
			UriComponentsBuilder uriBuilder = buildUriComp(authUrl).queryParam("grant_type", "client_credentials");
			authResponse = authRestTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, basicEntity, AuthToken.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMsgHandler(ex);
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				UriComponentsBuilder uriBuilder = buildUriComp(authUrl, false).queryParam("grant_type", "client_credentials");
//				authResponse = authRestTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, basicEntity, AuthToken.class);
//			}
		}

		AuthToken authToken = authResponse.getBody();

		return authToken.getAccess_token();

	}

	public Company getRemoteSystemCompany(String token, Map<String, ?> uriVariables, String companyUUID) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ignoreSSL(restTemplate);
		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);

		String url = this.remoteConfig.getRemoteIp() + ":" + this.remoteConfig.getRemotePort() + "/identity-service/company/getObjByUuid/"
				+ companyUUID;

		UriComponentsBuilder uriBuilder = buildUriComp(url);
//		UriComponentsBuilder uriBuilderSSL = buildUriComp(url, true);

		for (String key : uriVariables.keySet()) {
			Object values = uriVariables.get(key);
			uriBuilder.queryParam(key, values);
//			uriBuilderSSL.queryParam(key, values);
		}

		ResponseEntity<Company> response = null;
		try {
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, Company.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMsgHandler(ex);
//			if (ex instanceof javax.net.ssl.SSLException || ex instanceof ResourceAccessException) {
//				response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, bearerEntity, Company.class);
//			}
		}
		if (response == null || response.getBody() == null)
			throw new Exception("Unable to get Company in Enterprise for [" + companyUUID + "]");
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

	private UriComponentsBuilder buildUriComp(String url) {
		UriComponentsBuilder uriBuilder = null;
		try {
			uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		} catch (IllegalArgumentException ex) {
			// error try append http
//			String httpUrl = isHttps ? "https://" + url : "http://" + url;
			//			System.out.println("=>buildUri: " + httpUrl);
//			try {
//				uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
//			} catch (IllegalArgumentException ex2) {
//				throw ex2;
//			}
			throw ex;
		}
		return uriBuilder;
	}

	private void ignoreSSL(RestTemplate restTemplate) {
		/** TODO shld disable, now use for temp solution */
		try {
			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			};
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			restTemplate.setRequestFactory(requestFactory);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e2) {
			e2.printStackTrace();
		}
	}

	private void errorMsgHandler(Exception ex) throws Exception {
		if (ex instanceof HttpClientErrorException) {
			HttpHeaders headers = ((HttpClientErrorException) ex).getResponseHeaders();
			if (CollectionUtils.isNotEmpty(headers.get(HDRKEY_ERRORMSG))) {
				throw new Exception("Error occurred in Smarttradzt Enterprise. Error message: " + headers.get(HDRKEY_ERRORMSG).get(0));
			}
		}
		throw ex;
	}
}
