package com.acceval.core.cache;

public interface CacheIF {

	void clearAll(String companyID);

	CacheInfo getCacheInfo(String companyID);
}
