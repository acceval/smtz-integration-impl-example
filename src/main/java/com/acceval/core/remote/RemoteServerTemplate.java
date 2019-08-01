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
import com.acceval.core.model.CompanyModelListener;
import com.acceval.core.security.PrincipalUtil;

@Component
public class RemoteServerTemplate {
		
	private RemoteConfig remoteConfig;
	private String sellerUuid;
	
//	private List<HttpMessageConverter<?>> messageConverters;
//	
//	@Autowired
//	private ObjectMapper objectMapper;
	
	public String getSellerUuid() {
		return sellerUuid;
	}

	public void setSellerUuid(String sellerUuid) {
		this.sellerUuid = sellerUuid;
	}

	private RestTemplate restTemplate;
	
	public RemoteServerTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	public RemoteServerTemplate(RemoteConfig config) {
		
		this.remoteConfig = config;
		this.restTemplate = new RestTemplate();
	}
		
	
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		
		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		this.getRemoteSystemCompany(token,new HashMap<String, String>(), bearerHeaders);
        HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(completeUrl);
        
        for (String key: uriVariables.keySet()) {
        	Object values = uriVariables.get(key);
        	uriBuilder.queryParam(key, values);
        }
        
        for (HttpMessageConverter converter: restTemplate.getMessageConverters()) {
        	if (converter instanceof MappingJackson2HttpMessageConverter) {
        		
        		MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;        		
        		
        		jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
        	}
        }
        
        ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, 
        		bearerEntity, responseType);
        
        return response.getBody();
		
	}
	
	public <T> T exchange(String url, HttpMethod httpMethod, ParameterizedTypeReference<T> typeReference, Map<String, ?> uriVariables) {
		
		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;
		
//		DefaultOAuth2AccessToken authToken = new DefaultOAuth2AccessToken(
//				token);
//        authToken.setTokenType(DefaultOAuth2AccessToken.BEARER_TYPE);
//        OAuth2RestTemplate oauthRestTemplate = (OAuth2RestTemplate) this.restTemplate;
//        oauthRestTemplate.getOAuth2ClientContext().setAccessToken(authToken);
        
		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        objectMapper.findAndRegisterModules();
//        jsonConverter.setObjectMapper(objectMapper);
//        if (messageConverters == null) {
//        	messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        }
//        messageConverters.add(jsonConverter);
//        restTemplate.setMessageConverters(messageConverters);
        
        for (HttpMessageConverter converter: restTemplate.getMessageConverters()) {
        	if (converter instanceof MappingJackson2HttpMessageConverter) {
        		
        		MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;        		
        		jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
        		
//        		objectMapper.registerModule(new APIJavaTimeModule());
//        		jsonConverter.setObjectMapper(objectMapper);
        	}
        }
        
        this.getRemoteSystemCompany(token,new HashMap<String, String>(), bearerHeaders);
        
        HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);
        
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(completeUrl);
        
        for (String key: uriVariables.keySet()) {
        	Object values = uriVariables.get(key);
        	uriBuilder.queryParam(key, values);
        }
        
        ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(), httpMethod, bearerEntity, typeReference);
        
        return response.getBody();
		
	}
	
	private void setHttpCompanyHeader(HttpHeaders bearerHeaders, Company company) {
		
		bearerHeaders.add("COMPANYID", String.valueOf(company.getId()));
		bearerHeaders.add("COMPANYCODE", company.getCode());
		bearerHeaders.add("SERVICEPACKAGE", company.getServicePackage());
		bearerHeaders.add("SCHEMANAME", company.getCode());
	}
	
	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {

		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		for (HttpMessageConverter converter: restTemplate.getMessageConverters()) {
        	if (converter instanceof MappingJackson2HttpMessageConverter) {
        		
        		MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;        		
        		
        		jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
        		jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        		messageConverters.add(jsonConverter);
        	}
        }
		
		restTemplate.setMessageConverters(messageConverters);
		
		this.getRemoteSystemCompany(token,new HashMap<String, String>(), bearerHeaders);
        HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);
                
        ResponseEntity<T> response = restTemplate.postForEntity(completeUrl, bearerEntity, responseType);                		
        
        return response.getBody();
	} 
	
	public <T> T putForObject(String url, Object requestBody, Class<T> responseType) {

		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
		this.getRemoteSystemCompany(token,new HashMap<String, String>(), bearerHeaders);
        HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);
        
        for (HttpMessageConverter converter: restTemplate.getMessageConverters()) {
        	if (converter instanceof MappingJackson2HttpMessageConverter) {
        		
        		MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;        		
        		
        		jsonConverter.getObjectMapper().registerModule(new APIJavaTimeModule());
        	}
        }
        
        ResponseEntity<T> response = restTemplate.exchange(completeUrl, HttpMethod.PUT, 
        		bearerEntity, responseType);
        
        return response.getBody();
	} 

	public String getRemoteServerToken() {
		
		String authUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() 
				+ "/auth-service/uaa/oauth/token";						
	    
	    try {
	        HttpHeaders basicHeaders = this.createBasicHeaders(this.remoteConfig.getCredentialUser(), 
	        		this.remoteConfig.getCredentialPassword());
	        	        
	        RestTemplate authRestTemplate = new RestTemplate();
	        HttpEntity<String> basicEntity = new HttpEntity<String>(basicHeaders);
	        
	        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(authUrl)
	                .queryParam("grant_type", "client_credentials");
	        
	        ResponseEntity<AuthToken> authResponse = authRestTemplate.exchange(uriBuilder.toUriString(), 
	        		HttpMethod.POST, basicEntity, AuthToken.class);
	        AuthToken authToken = authResponse.getBody();
	        
	        return authToken.getAccess_token();
	    }
	    catch (Exception ex) {
	        System.out.println("** Exception: "+ ex.getMessage());
	    }
	    
	    return null;
	}
	
	public void getRemoteSystemCompany(String token, Map<String, ?> uriVariables, HttpHeaders requestBearerHeaders) {

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
        HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);
        
		String url = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() 
				+ "/identity-service/company/getObjByUuid/" + getSellerUuid();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        
        for (String key: uriVariables.keySet()) {
        	Object values = uriVariables.get(key);
        	uriBuilder.queryParam(key, values);
        }
        
        ResponseEntity<Company> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, 
        		bearerEntity, Company.class);
        Company comp = (Company) response.getBody();
        this.setHttpCompanyHeader(requestBearerHeaders, comp);
		
		
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
