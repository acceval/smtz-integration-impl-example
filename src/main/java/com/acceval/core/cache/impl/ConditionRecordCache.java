package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.HazelcastCacheInstance;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordCacheHelper;
import com.acceval.core.cache.model.ConditionRecordCacheHolder;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.acceval.core.security.PrincipalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Cache structure
 *
 * CONDITION_RECORD_CACHE|[Company ID]|[CONDITION_RECORD_CONFIG_CODE] > HOLDER > Condition Record Cache Holder
 */
@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class ConditionRecordCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(ConditionRecordCache.class);

    private final static String CACHE_NAME = "CONDITION_RECORD_CACHE";
    private final static String KEY_HOLDER = "HOLDER";

    @Autowired
    private HazelcastCacheInstance hazelcastCacheInstance;


    private Map<String, Object> getTopMap(String companyID, String code) {
        String key = CACHE_NAME + "|" + companyID + "|" + code.toUpperCase();
        return this.hazelcastCacheInstance.getHazelcastInstance().getReplicatedMap(key);
    }

    private ConditionRecordCacheHolder getHolder(String companyID, String code) {
        ConditionRecordCacheHolder holder = (ConditionRecordCacheHolder) getTopMap(companyID, code).get(KEY_HOLDER);

        if (holder == null) {
            holder = new ConditionRecordCacheHolder();
            getTopMap(companyID, code).put(KEY_HOLDER, holder);
        }

        return holder;
    }

    private void saveHolder(String companyID, String code, ConditionRecordCacheHolder holder) {
        getTopMap(companyID, code).put(KEY_HOLDER, holder);
    }

    public ConditionRecordConfig getConditionRecordConfig(String companyID, String code) {
        return getHolder(companyID, code).getConfig();
    }

    public ConditionRecordCacheHolder getConditionRecordCacheHolder(String code) {
        String companyID = Long.toString(PrincipalUtil.getCompanyID());
        return getHolder(companyID, code);
    }

    public ConditionRecordConfig getConditionRecordConfig(String code) {
        String companyID = Long.toString(PrincipalUtil.getCompanyID());
        return getHolder(companyID, code).getConfig();
    }

    public List<ConditionRecordCacheHelper> getAllConditionRecords(String companyID, String code) {
        ConditionRecordCacheHolder holder = getHolder(companyID, code);
        return holder.getRecords();
    }

    public void refreshConditionRecord(String companyID, ConditionRecordConfig config, List<ConditionRecord> records) {
        ConditionRecordCacheHolder holder = getHolder(companyID, config.getCode());

        holder.setConfig(config);

        List<ConditionRecordCacheHelper> helpers = records.stream().map(conditionRecord -> holder.toHelper(conditionRecord)).collect(Collectors.toList());
        holder.setRecords(helpers);

        saveHolder(companyID, config.getCode(), holder);
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

    public String test(String code) {
        String companyID = PrincipalUtil.getCompanyID().toString();

        StringBuffer buffer = new StringBuffer();

        List temp = getAllConditionRecords(companyID, code);
        if (temp == null) {
            buffer.append("Condition Record : No condition record ["+code+"] found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Condition Record : Total condition record ["+code+"] : " + temp.size() + ". ");
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
