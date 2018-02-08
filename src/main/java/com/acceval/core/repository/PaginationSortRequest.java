package com.acceval.core.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;

public class PaginationSortRequest {

	/** used for sorting property's MS, eg sort by Product Name, it will be Master-Data MS request */
	private DiscoveryClient discoveryClient;
	private RestTemplate restTemplate;

	/** for fire query */
	private EntityManager entityManager;
	private Class<?> targetClass;

	private int pageNumber;
	private int pageSize;
	private String orderTarget;
	private String orderParam;
	private boolean isAsc;
	private String whereStatement;

	public PaginationSortRequest(DiscoveryClient discoveryClient, RestTemplate restTemplate, EntityManager entityManager,
			Class<?> targetClass, int pageNumber, int pageSize, String orderTarget, String orderParam, boolean isAsc,
			String whereStatement) {
		super();
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
		this.entityManager = entityManager;
		this.targetClass = targetClass;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.orderTarget = orderTarget;
		this.orderParam = orderParam;
		this.isAsc = isAsc;
		this.whereStatement = whereStatement;
	}

	/**
	 * @deprecated not recommend as most fields are required, call full properties constructor instead
	 */
	@Deprecated
	public PaginationSortRequest() {
		super();
	}

	public void assertNull() throws MicroServiceUtilException {
		assertNull(null);
	}

	public void assertNull(Class<?> clazz) throws MicroServiceUtilException {
		if (StringUtils.isBlank(orderTarget) || StringUtils.isBlank(orderParam))
			throw new MicroServiceUtilException(clazz, "Order Properties input is not Complete");
		Optional.ofNullable(discoveryClient).orElseThrow(() -> new MicroServiceUtilException(clazz, "DiscoveryClient is null"));
		Optional.ofNullable(restTemplate).orElseThrow(() -> new MicroServiceUtilException(clazz, "RestTemplate is null"));
		Optional.ofNullable(entityManager).orElseThrow(() -> new MicroServiceUtilException(clazz, "EntityManager is null."));
		Optional.ofNullable(targetClass).orElseThrow(() -> new MicroServiceUtilException(clazz, "TargetClass is null."));
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(String orderParam) {
		this.orderParam = orderParam;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public String getWhereStatement() {
		return whereStatement;
	}

	public void setWhereStatement(String whereStatement) {
		this.whereStatement = whereStatement;
	}

	public String getOrderTarget() {
		return orderTarget;
	}

	public void setOrderTarget(String orderTarget) {
		this.orderTarget = orderTarget;
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

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}
