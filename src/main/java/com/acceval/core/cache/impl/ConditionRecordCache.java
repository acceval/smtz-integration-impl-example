package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordCacheHolder;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.core.IMap;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    public ConditionRecordCache(@Value("${microservice.env}") String env,
                                @Value("${microservice.cache-config.kubernetes.enable:false}") boolean kubernetesEnable,
                                @Value("${microservice.cache-config.kubernetes.namespace:dummy}") String kubernetesNamespace,
                                @Value("${microservice.cache-config.kubernetes.service-name:dummy}") String hazelCastServiceName
                                ) {

        Config config = new Config();

        config.getNetworkConfig().setPort(5710);

        logger.info("Condition Record Cache Property : Kubernetes Enable : " + kubernetesEnable);
        logger.info("Condition Record Cache Property : Kubernetes Namespace : " + kubernetesNamespace);
        logger.info("Condition Record Cache Property : Kubernetes Service Name : " + hazelCastServiceName);

        if (StringUtils.isNotBlank(env) && StringUtils.containsIgnoreCase(env, "local")) {
            config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:9090/mancenter");
            NetworkConfig network = config.getNetworkConfig();
            JoinConfig join = network.getJoin();
            join.getMulticastConfig().setEnabled(false);
            join.getTcpIpConfig().setEnabled(true).addMember("localhost");
        } else {
            // other environment

            if (kubernetesEnable) {
                config.setProperty("hazelcast.discovery.enabled", "true");

                NetworkConfig network = config.getNetworkConfig();
                JoinConfig join = network.getJoin();
                join.getMulticastConfig().setEnabled(false);

//            join.getKubernetesConfig().setEnabled(true)
//                    .setProperty("namespace", kubernetesNamespace)
//                    .setProperty("service-name", hazelCastServiceName);

                DiscoveryConfig dc = config.getNetworkConfig().getJoin().getDiscoveryConfig();
                HazelcastKubernetesDiscoveryStrategyFactory factory = new HazelcastKubernetesDiscoveryStrategyFactory();
                DiscoveryStrategyConfig strategyConfig = new DiscoveryStrategyConfig(factory);
                strategyConfig.addProperty("namespace", kubernetesNamespace);
//            strategyConfig.addProperty("service-name", hazelCastServiceName);
//            strategyConfig.addProperty("service-label-name", "hazelcast-member");
//            strategyConfig.addProperty("service-label-value", "active");

                dc.addDiscoveryStrategyConfig(strategyConfig);
            }
        }


        logger.info("Condition Record Cache construct. " + env);
        String instanceName = "smtz_enterprise:" + env + ":" + CACHE_NAME;
        config.getGroupConfig().setName(instanceName);
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    private Map<String, Object> getTopMap(String companyID, String code) {
        String key = companyID + "|" + code;
        return hazelcastInstance.getReplicatedMap(key);
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
//        getTopMap(companyID).clear();
    }

    @Override
    public CacheInfo getCacheInfo(String companyID) {
        return null;
    }
}
