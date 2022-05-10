package com.acceval.core.cache.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.HazelcastCacheInstance;
import com.acceval.core.cache.model.ExchangeRate;
import com.acceval.core.cache.model.ExchangeRateType;
import com.acceval.core.microservice.ObjectNotFoundException;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.util.DateUtil;
import com.hazelcast.core.ReplicatedMap;

/**
 * Cache structure
 *
 * EXCHANGE_RATE_CACHE|[Company ID]|ALL_EXCHANGE_RATE       > DEFAULT > All Exchange Rates
 *                                 |DEFAULT_EXCHANGE_RATE   > DEFAULT > Default Exchange Rate Type Code
 *                                 |ALL_EXCHANGE_RATE_TYPE  > DEFAULT > All Exchange Rate Types
 */
@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class ExchangeRateCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateCache.class);

    private final static String CACHE_NAME = "EXCHANGE_RATE_CACHE";

    private final static String KEY_CACHE_READY = "READY";
    private final static String KEY_CACHE_INIT = "INIT";
    private final static String KEY_DEFAULT = "DEFAULT";

    private final static String KEY_ALL_EXCHANGE_RATE = "ALL_EXCHANGE_RATE";
    private final static String KEY_ALL_EXCHANGE_RATE_TYPE = "ALL_EXCHANGE_RATE_TYPE";
    private final static String KEY_DEFAULT_EXCHANGE_RATE_TYPE = "DEFAULT_EXCHANGE_RATE_TYPE";
    
    @Autowired
    private HazelcastCacheInstance hazelcastCacheInstance;

    private ReplicatedMap<String, Object> getTopMap(String companyID, String code) {
        String key = CACHE_NAME + "|" + companyID + "|" + code;
        return this.hazelcastCacheInstance.getHazelcastInstance().getReplicatedMap(key);
    }

    public void setCacheReady(String companyID) {
        getTopMap(companyID, KEY_CACHE_READY).put(KEY_DEFAULT, Boolean.TRUE);
    }

    public boolean isCacheReady() {
        String companyID = PrincipalUtil.getCompanyID().toString();
        Boolean ready = (Boolean) getTopMap(companyID, KEY_CACHE_READY).get(KEY_DEFAULT);

        if (ready != null && ready) return true;

        return false;
    }
    

    public boolean isInitialized() {
    	
    	String companyID = PrincipalUtil.getCompanyID().toString();
        Boolean ready = (Boolean) getTopMap(companyID, KEY_CACHE_INIT).get(KEY_DEFAULT);

        if (ready != null && ready) return true;

        return false;
        
	}

	public void setInitialized(String companyId) {
		
		getTopMap(companyId, KEY_CACHE_INIT).put(KEY_DEFAULT, Boolean.TRUE);		
	}
	
	public void unsetInitialized(String companyId) {
		
		getTopMap(companyId, KEY_CACHE_INIT).put(KEY_DEFAULT, Boolean.FALSE);
	}

    /**
     * Exchange Rate Cache
     * @return
     */
    public List<ExchangeRate> getAllExchangeRates() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<ExchangeRate>) getTopMap(companyID, KEY_ALL_EXCHANGE_RATE).get(KEY_DEFAULT);
    }

    public void refreshExchangeRate(String companyID, List<ExchangeRate> rates) {
//        this.clearAll(companyID);

        getTopMap(companyID, KEY_ALL_EXCHANGE_RATE).put(KEY_DEFAULT, rates);
    }

    /**
     * Default Exchange Rate Type cache
     * @return
     */
    public String getDefaultExchangeRateType() {
        String companyID = PrincipalUtil.getCompanyID().toString();
        return (String) getTopMap(companyID, KEY_DEFAULT_EXCHANGE_RATE_TYPE).get(KEY_DEFAULT);
    }

    public void refreshDefaultExchangeRateType(String companyID, String exchangeRateTypeCode) {
        getTopMap(companyID, KEY_DEFAULT_EXCHANGE_RATE_TYPE).put(KEY_DEFAULT, exchangeRateTypeCode);
    }

    /**
     * Exchange Rate Type Cache
     * @return
     */
    public List<ExchangeRateType> getAllExchangeRateTypes() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<ExchangeRateType>) getTopMap(companyID, KEY_ALL_EXCHANGE_RATE_TYPE).get(KEY_DEFAULT);
    }

    public ExchangeRateType getExchangeRateTypeByCode(String code) throws ObjectNotFoundException {
        List<ExchangeRateType> exchangeRateTypes = getAllExchangeRateTypes();

        if (exchangeRateTypes != null && !exchangeRateTypes.isEmpty()) {

            Optional<ExchangeRateType> temp = exchangeRateTypes.stream().filter(exchangeRateType -> exchangeRateType.getCode().equals(code)).findFirst();

            if (temp.isPresent()) return temp.get();
        }

        throw new ObjectNotFoundException("Exchange Rate Type [" + code + "] not found.");
    }

    public void refreshAllExchangeRateType(String companyID, List<ExchangeRateType> exchangeRateTypes) {
        getTopMap(companyID, KEY_ALL_EXCHANGE_RATE_TYPE).put(KEY_DEFAULT, exchangeRateTypes);
    }

//    @Query("select u from #{#entityName} u where u.fromCurrency.currencyID = ?1 and u.toCurrency.currencyID = ?2 "
//            + "and u.effectiveDate <= ?3 and u.type.exchangeRateTypeID = ?4 and u.recordStatus = ?5 "
//            + "ORDER BY u.effectiveDate DESC")
    public List<ExchangeRate> getExchangeRates(long fromCurrencyID, long toCurrencyID, LocalDateTime effectiveDate, long exchangeRateTypeID) {
        List<ExchangeRate> rates = getAllExchangeRates();

        if (CollectionUtils.isEmpty(rates)) return Collections.emptyList();

        return rates.stream().filter(exchangeRate -> {
            return exchangeRate.getFromCurrency().getCurrencyID() == fromCurrencyID &&
                    exchangeRate.getToCurrency().getCurrencyID() == toCurrencyID &&
                    exchangeRate.getType().getExchangeRateTypeID() == exchangeRateTypeID &&
                    !exchangeRate.getEffectiveDate().isAfter(effectiveDate);
        }).sorted(new Comparator<ExchangeRate>() {
            @Override
            public int compare(ExchangeRate o1, ExchangeRate o2) {
                return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void clearAll(String companyID) {
        // TODO dangerous to be clear all first then put in the new data.. cause cache might be blank for few seconds if data is huge
//        getTopMap(companyID).clear();
    }

    @Override
    public CacheInfo getCacheInfo(String companyID) {
        return null;
    }
    

	public String test() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        StringBuffer buffer = new StringBuffer();

        List<ExchangeRate> temp = this.getAllExchangeRates();

        if (temp == null) {
            buffer.append("Exchange Rate : No exchange rates found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Exchange Rate : Total exchange rates : " + temp.size());
            buffer.append("\n");
        }

        buffer.append("Exchange Rate : Default Exchange Rate : " + getDefaultExchangeRateType());
        buffer.append("\n");

        List<ExchangeRateType> temp2 = this.getAllExchangeRateTypes();

        if (temp2 == null) {
            buffer.append("Exchange Rate Type : No exchange rate types found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Exchange Rate Type : Total exchange rate types : " + temp2.size());
            buffer.append("\n");
        }

        String exchangeRateTypeCode = "ABC";
        try {
            buffer.append("Exchange Rate Type : Exchange Rate Type By Code ["+exchangeRateTypeCode+"] : " + getExchangeRateTypeByCode(exchangeRateTypeCode).getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Exchange Rate Type : Exchange Rate Type By Code ["+exchangeRateTypeCode+"] : " +e.getMessage());
            buffer.append("\n");
        }

        exchangeRateTypeCode = "M";
        ExchangeRateType type = null;
        try {
            type = getExchangeRateTypeByCode(exchangeRateTypeCode);
            buffer.append("Exchange Rate Type : Exchange Rate Type By Code ["+exchangeRateTypeCode+"]: " + type.getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Exchange Rate Type : Exchange Rate Type By Code ["+exchangeRateTypeCode+"]: " +e.getMessage());
            buffer.append("\n");
        }

        // search exchange rate type
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime dateTime = LocalDateTime.parse("2020-01-02", formatter);
        LocalDateTime refDate = DateUtil.parseToLocalDateTime("2020-01-03");

        List<ExchangeRate> rates = getExchangeRates(202102011170006l, 202102011170005l, refDate, type.getExchangeRateTypeID());

        buffer.append("Get Exchange Rate : Total " + rates.size());
        buffer.append("\n");
        if (!rates.isEmpty()) {
            ExchangeRate exchangeRate = rates.get(0);
            buffer.append("Get Exchange Rate : First : " + exchangeRate.getFromCurrency().getCode() + " -> " + exchangeRate.getToCurrency().getCode() +
                    " (" + formatter.format(exchangeRate.getEffectiveDate()) + ") : " + exchangeRate.getRate());
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
