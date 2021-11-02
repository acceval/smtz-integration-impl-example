package com.acceval.core.job.logger;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.acceval.core.job.JobStatus;
import com.acceval.core.remote.AuthToken;
import com.acceval.core.security.PrincipalUtil;


public class DefaultJobLogger implements SchedulerJobLogger {
		
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String serviceClientName;
		
	private static Logger logger = LoggerFactory.getLogger(DefaultJobLogger.class);
		
	public String getServiceClientName() {
		return serviceClientName;
	}

	public void setServiceClientName(String serviceClientName) {
		this.serviceClientName = serviceClientName;
	}

	public void logProgress(Long schedulerJobLogId, double percentage) {
		
		JobLoggerRequest request = new JobLoggerRequest();
		request.setSchedulerJobLogId(schedulerJobLogId);
		request.setProgress(percentage);
		request.setJobStatus(JobStatus.RUNNING);

		this.updateSchedulerJobStatus(request);	
	}
	
	public void logProgress(Long schedulerJobLogId, int currentCount, int totalCount) {
		
		JobLoggerRequest request = new JobLoggerRequest();
		request.setSchedulerJobLogId(schedulerJobLogId);
		request.setProgress(currentCount / totalCount);
		request.setJobStatus(JobStatus.RUNNING);

		this.updateSchedulerJobStatus(request);	
	}
	
	public void logSuccess(Long schedulerJobLogId) {
		
		JobLoggerRequest request = new JobLoggerRequest();
		request.setSchedulerJobLogId(schedulerJobLogId);		
		request.setJobStatus(JobStatus.SUCCESS);

		this.updateSchedulerJobStatus(request);
	}
	
	public void logError(Long schedulerJobLogId) {
		this.logError(schedulerJobLogId, null, null);
	}
	
	public void logError(Long schedulerJobLogId, String message) {
		this.logError(schedulerJobLogId, message, null);
	}
	
	public void logError(Long schedulerJobLogId, Exception exception) {
		this.logError(schedulerJobLogId, null, exception);
	}
	
	
	public void logError(Long schedulerJobLogId, String message, Exception exception) {
		
		JobLoggerRequest request = new JobLoggerRequest();
		request.setSchedulerJobLogId(schedulerJobLogId);		
		request.setJobStatus(JobStatus.FAILURE);
		
		if (exception != null) {
			request.setException(exception);
			request.setMessage(exception.getMessage());
		}
		
		if (message != null) {
			request.setMessage(message);
		}

		this.updateSchedulerJobStatus(request);
		
	}
	
	private void updateSchedulerJobStatus(JobLoggerRequest request) {
				
		List<ServiceInstance> instances = this.discoveryClient.getInstances("commons-service");
		
		if (!instances.isEmpty()) {
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			String token = this.getRemoteServerToken();
			
			logger.info("Pricipal token job logger: [" + PrincipalUtil.getToken() + "]");
			
			if (token != null) {
				headers.add("Authorization", "Bearer " + token);
				logger.info("Queried auth token: [" + token + "]");
			} else {
				headers.add("Authorization", PrincipalUtil.getToken());
			}
			
			headers.add("COMPANYID", PrincipalUtil.getCompanyID() != null? String.valueOf(PrincipalUtil.getCompanyID()) : "");
			headers.add("COMPANYCODE", PrincipalUtil.getCompanyCode());
			headers.add("SERVICEPACKAGE", PrincipalUtil.getServicePackage() != null? PrincipalUtil.getServicePackage().toString(): "");
			headers.add("SCHEMANAME", PrincipalUtil.getSchemaName());
			
			HttpEntity<JobLoggerRequest> httpEntity = new HttpEntity<JobLoggerRequest>(request, headers);
			ServiceInstance instance = instances.get(0);
			
			String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/scheduler-job-log/" 
				+ request.getSchedulerJobLogId().toString();
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

			ResponseEntity<JobLoggerRequest> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
					httpEntity, JobLoggerRequest.class);
			JobLoggerRequest body = response.getBody();
			
			logger.info("The job log id [" + request.getSchedulerJobLogId() + "] status is: "+ body.isLogged());
		}
	}	
	
	public String getRemoteServerToken() {

		List<ServiceInstance> instances = this.discoveryClient.getInstances("auth-server");
		
		if (!instances.isEmpty()) {
			
			ServiceInstance instance = instances.get(0);

			String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/uaa/oauth/token"; 
						
			String notEncoded = this.getServiceClientName() + ":password";
			String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
			HttpHeaders basicHeaders = new HttpHeaders();
			basicHeaders.setContentType(MediaType.APPLICATION_JSON);
			basicHeaders.add("Authorization", encodedAuth);
			HttpEntity<String> basicEntity = new HttpEntity<String>(basicHeaders);
	
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("grant_type", "client_credentials");
						
			ResponseEntity<AuthToken> authResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, basicEntity, AuthToken.class);
			
			AuthToken authToken = authResponse.getBody();
	
			return authToken.getAccess_token();
		}
		
		return null;

	}
	

}
