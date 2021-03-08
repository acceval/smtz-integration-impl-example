package com.acceval.core.cache;

import java.io.Serializable;

public class CacheInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String serviceName;
	private String controllerName;
	private String name;
	private String cacheClass;
	private long activeCache;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCacheClass() {
		return cacheClass;
	}

	public void setCacheClass(String cacheClass) {
		this.cacheClass = cacheClass;
	}

	public long getActiveCache() {
		return activeCache;
	}

	public void setActiveCache(long activeCache) {
		this.activeCache = activeCache;
	}
}
