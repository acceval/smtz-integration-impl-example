package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.HazelcastCacheInstance;
import com.acceval.core.cache.model.ExchangeRate;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Cache structure
 *
 * [Company ID] > [ALL_EXCHANGE_RATE] > All Exchange Rates
 */
@Component
@ConditionalOnProperty(name = "microservice.cache-config.exchange-rate", havingValue = "true")
public class ExchangeRateCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateCache.class);

    public final static String CACHE_NAME = "EXCHANGE_RATE_CACHE";
    public final static String KEY_ALL_EXCHANGE_RATE = "ALL_EXCHANGE_RATE";

    @Autowired
    private HazelcastCacheInstance hazelcastCacheInstance;

//    private ExchangeRateCache(@Value("${microservice.env}") String env,
//                              @Value("${microservice.cache-config.kubernetes.enable:false}") boolean kubernetesEnable,
//                              @Value("${microservice.cache-config.kubernetes.namespace:dummy}") String kubernetesNamespace,
//                              @Value("${microservice.cache-config.kubernetes.service-name:dummy}") String hazelCastServiceName
//    ) {
////        NetworkInterface.getNetworkInterfaces()
//
//        Config config = new Config();
//
////        config.getNetworkConfig().setPort(5730);
//
//        logger.info("Exchange Rate Cache Property : Kubernetes Enable : " + kubernetesEnable);
//        logger.info("Exchange Rate Cache Property : Kubernetes Namespace : " + kubernetesNamespace);
//        logger.info("Exchange Rate Cache Property : Kubernetes Service Name : " + hazelCastServiceName);
//
//        if (StringUtils.isNotBlank(env) && StringUtils.containsIgnoreCase(env, "local")) {
////            UUID uuid = UUID.randomUUID();
////            this.applicationName = uuid.toString() + ":" + applicationName;
//            config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:9090/mancenter");
//            NetworkConfig network = config.getNetworkConfig();
//            JoinConfig join = network.getJoin();
//            join.getMulticastConfig().setEnabled(false);
//            join.getTcpIpConfig().setEnabled(true).addMember("localhost");
//
////            instanceName = env;
//        } else {
//            // other environment
////            this.applicationName = env + ":" + applicationName;
////            instanceName = env;
//
//            if (kubernetesEnable) {
//                config.setProperty("hazelcast.discovery.enabled", "true");
//
//                NetworkConfig network = config.getNetworkConfig();
//                JoinConfig join = network.getJoin();
//                join.getMulticastConfig().setEnabled(false);
////            join.getKubernetesConfig().setEnabled(true)
////                    .setProperty("namespace", kubernetesNamespace)
////                    .setProperty("service-name", hazelCastServiceName);
//
//                DiscoveryConfig dc = config.getNetworkConfig().getJoin().getDiscoveryConfig();
//                HazelcastKubernetesDiscoveryStrategyFactory factory = new HazelcastKubernetesDiscoveryStrategyFactory();
//                DiscoveryStrategyConfig strategyConfig = new DiscoveryStrategyConfig(factory);
//                strategyConfig.addProperty("namespace", kubernetesNamespace);
////            strategyConfig.addProperty("service-name", hazelCastServiceName);
////            strategyConfig.addProperty("service-label-name", "hazelcast-member");
////            strategyConfig.addProperty("service-label-value", "active");
//
//                dc.addDiscoveryStrategyConfig(strategyConfig);
//            }
//        }
//
//
//        logger.info("Exchange Rate Cache construct. " + env);
//        String instanceName = "smtz_enterprise:" + env + ":cluster";
//        config.getGroupConfig().setName(instanceName);
//        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
//    }

    private Map<String, Object> getTopMap(String companyID) {
        String key = CACHE_NAME + "|" + companyID;
        return this.hazelcastCacheInstance.getHazelcastInstance().getReplicatedMap(key);
    }

    public List<ExchangeRate> getAllExchangeRates(String companyID) {
        return (List<ExchangeRate>) getTopMap(companyID).get(KEY_ALL_EXCHANGE_RATE);
    }

    public void refreshExchangeRate(String companyID, List<ExchangeRate> rates) {
//        this.clearAll(companyID);

        getTopMap(companyID).put(KEY_ALL_EXCHANGE_RATE, rates);
    }

    @Override
    public void clearAll(String companyID) {
        // TODO dangerous to be clear all first then put in the new data.. cause cache might be blank for few seconds if data is huge
        getTopMap(companyID).clear();
    }

    @Override
    public CacheInfo getCacheInfo(String companyID) {
        return null;
    }
}
