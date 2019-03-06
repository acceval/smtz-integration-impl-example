package com.acceval.core.remote;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class RemoteServerTemplate {
		
	private RemoteConfig remoteConfig;
	
	private RestTemplate restTemplate;
	
	public RemoteServerTemplate() {
		this.restTemplate = new RestTemplate();
	}
		
	
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		
		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
        HttpEntity<String> bearerEntity = new HttpEntity<String>(bearerHeaders);
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(completeUrl);
        
        for (String key: uriVariables.keySet()) {
        	Object values = uriVariables.get(key);
        	uriBuilder.queryParam(key, values);
        }
        
        ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, 
        		bearerEntity, responseType);
        
        return response.getBody();
		
	}
	
	public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {

		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
        HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);
        
        ResponseEntity<T> response = restTemplate.exchange(completeUrl, HttpMethod.POST, 
        		bearerEntity, responseType);
        
        return response.getBody();
	} 
	
	public <T> T putForObject(String url, Object requestBody, Class<T> responseType) {

		String token = this.getRemoteServerToken();
		String completeUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() + url;

		HttpHeaders bearerHeaders = this.createBearerHeaders(token);
        HttpEntity<?> bearerEntity = new HttpEntity<>(requestBody, bearerHeaders);
        
        ResponseEntity<T> response = restTemplate.exchange(completeUrl, HttpMethod.PUT, 
        		bearerEntity, responseType);
        
        return response.getBody();
	} 

	private String getRemoteServerToken() {
		
		String authUrl = "http://" + this.remoteConfig.getRemoteIp() + ":"
				+ this.remoteConfig.getRemotePort() 
				+ "/auth-service/uaa/oauth/token";						
	    
	    try {
	        HttpHeaders basicHeaders = this.createBasicHeaders(this.remoteConfig.getCredentialUser(), 
	        		this.remoteConfig.getCredentialPassword());
	        
	        HttpEntity<String> basicEntity = new HttpEntity<String>(basicHeaders);
	        
	        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(authUrl)
	                .queryParam("grant_type", "client_credentials");
	        
	        ResponseEntity<AuthToken> authResponse = restTemplate.exchange(uriBuilder.toUriString(), 
	        		HttpMethod.POST, basicEntity, AuthToken.class);
	        AuthToken authToken = authResponse.getBody();
	        
	        return authToken.getAccess_token();
	    }
	    catch (Exception ex) {
	        System.out.println("** Exception: "+ ex.getMessage());
	    }
	    
	    return null;
	}
	    
	private HttpHeaders createBasicHeaders(String user, String password) {
		
	    String notEncoded = user + ":" + password;
	    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", encodedAuth);
	    return headers;
	}
	
	private HttpHeaders createBearerHeaders(String token) {
			    	    
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
