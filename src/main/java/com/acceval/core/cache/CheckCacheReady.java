package com.acceval.core.cache;

import com.acceval.core.cache.impl.ConditionRecordCache;
import com.acceval.core.cache.impl.ExchangeRateCache;
import com.acceval.core.cache.impl.MasterDataCache;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class CheckCacheReady {

    @Autowired
    private ConditionRecordCache conditionRecordCache;

    @Autowired
    private MasterDataCache masterDataCache;

    @Autowired
    private ExchangeRateCache exchangeRateCache;

    @Before("execution(public * com.acceval.core.cache.processor.*.*(..))")
    public void checkCacheReady() throws Throwable {
        if (!exchangeRateCache.isCacheReady()) {
            throw new CacheException("Exchange Rate Cache not ready. Please try again later.");
        }
        if (!masterDataCache.isCacheReady()) {
            throw new CacheException("Master Data Cache not ready. Please try again later.");
        }
        if (!conditionRecordCache.isCacheReady()) {
            throw new CacheException("Condition Record Cache not ready. Please try again later.");
        }

//        joinPoint.proceed();
    }
}
