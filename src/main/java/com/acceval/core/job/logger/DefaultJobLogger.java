package com.acceval.core.job.logger;

import java.util.List;

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
import com.acceval.core.security.PrincipalUtil;


public class DefaultJobLogger implements SchedulerJobLogger {
		
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	

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
			headers.add("Authorization", PrincipalUtil.getToken());
			headers.add("COMPANYID", PrincipalUtil.getCompanyID() != null? 
					String.valueOf(PrincipalUtil.getCompanyID()) : "");
			headers.add("COMPANYCODE", PrincipalUtil.getCompanyCode());
			headers.add("SERVICEPACKAGE", PrincipalUtil.getServicePackage() != null? 
					PrincipalUtil.getServicePackage().toString(): "");
			headers.add("SCHEMANAME", PrincipalUtil.getSchemaName());
			
			HttpEntity<JobLoggerRequest> httpEntity = new HttpEntity<JobLoggerRequest>(request, headers);
			ServiceInstance instance = instances.get(0);
			
			String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/scheduler-job-log/" 
				+ request.getSchedulerJobLogId().toString();
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

			ResponseEntity<JobLoggerRequest> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
					httpEntity, JobLoggerRequest.class);
			JobLoggerRequest body = response.getBody();
			
			System.out.println("the log update result: " + body.isLogged());
		}
	}	

}
