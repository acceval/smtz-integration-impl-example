package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordCacheHolder;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Cache structure
 *
 * [Company ID] > [CONDITION_RECORD_CONFIG_CODE] > Condition Record Cache Holder
 */
@Component
@ConditionalOnProperty(name = "microservice.cache-config.condition-record", havingValue = "true")
public class ConditionRecordCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(ConditionRecordCache.class);

    private final static String CACHE_NAME = "CONDITION_RECORD_CACHE";
    private final static String KEY_HOLDER = "HOLDER";

    private HazelcastInstance hazelcastInstance;

    public ConditionRecordCache(@Value("${microservice.env}") String env) {

        Config config = new Config();

        config.getNetworkConfig().setPort(5710);

        if (StringUtils.isNotBlank(env) && StringUtils.containsIgnoreCase(env, "local")) {
            config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:9090/mancenter");
            NetworkConfig network = config.getNetworkConfig();
            JoinConfig join = network.getJoin();
            join.getMulticastConfig().setEnabled(false);
            join.getTcpIpConfig().setEnabled(true).addMember("localhost");
        } else {
            // other environment
        }
        logger.info("Condition Record Cache construct. " + env);
        String instanceName = "smtz_enterprise:" + env + ":" + CACHE_NAME;
        config.getGroupConfig().setName(instanceName);
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    private IMap<String, Object> getTopMap(String companyID) {
        String key = companyID;
        return hazelcastInstance.getMap(key);
    }

    private ConditionRecordCacheHolder getHolder(String companyID, String code) {
        ConditionRecordCacheHolder holder = (ConditionRecordCacheHolder) getTopMap(companyID).get(code);

        if (holder == null) {
            holder = new ConditionRecordCacheHolder();
            getTopMap(companyID).put(code, holder);
        }

        return holder;
    }

    private void saveHolder(String companyID, String code, ConditionRecordCacheHolder holder) {
        getTopMap(companyID).put(code, holder);
    }

    public ConditionRecordConfig getConditionRecordConfig(String companyID, String code) {
        return getHolder(companyID, code).getConfig();
    }

    public List<ConditionRecord> getAllConditionRecords(String companyID, String code) {
        ConditionRecordCacheHolder holder = getHolder(companyID, code);
        return holder.getRecords();
    }

    public void refreshConditionRecord(String companyID, ConditionRecordConfig config, List<ConditionRecord> records) {
        ConditionRecordCacheHolder holder = getHolder(companyID, config.getCode());

        holder.setConfig(config);
        holder.setRecords(records);

        saveHolder(companyID, config.getCode(), holder);
    }

    @Override
    public void clearAll(String companyID) {
        getTopMap(companyID).clear();
    }

    @Override
    public CacheInfo getCacheInfo(String companyID) {
        return null;
    }
}
