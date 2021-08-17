package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.model.ExchangeRate;
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
 * [Company ID] > [ALL_EXCHANGE_RATE] > All Exchange Rates
 */
@Component
@ConditionalOnProperty(name = "microservice.cache-config.exchange-rate", havingValue = "true")
public class ExchangeRateCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateCache.class);

    public final static String CACHE_NAME = "EXCHANGE_RATE_CACHE";
    public final static String KEY_ALL_EXCHANGE_RATE = "ALL_EXCHANGE_RATE";

    private HazelcastInstance hazelcastInstance;

    private ExchangeRateCache(@Value("${microservice.env}") String env) {
//        NetworkInterface.getNetworkInterfaces()

        Config config = new Config();

        config.getNetworkConfig().setPort(5730);

        if (StringUtils.isNotBlank(env) && StringUtils.containsIgnoreCase(env, "local")) {
//            UUID uuid = UUID.randomUUID();
//            this.applicationName = uuid.toString() + ":" + applicationName;
            config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:9090/mancenter");
            NetworkConfig network = config.getNetworkConfig();
            JoinConfig join = network.getJoin();
            join.getMulticastConfig().setEnabled(false);
            join.getTcpIpConfig().setEnabled(true).addMember("localhost");

//            instanceName = env;
        } else {
            // other environment
//            this.applicationName = env + ":" + applicationName;
//            instanceName = env;
        }
        logger.info("Exchange Rate Cache construct. " + env);
        String instanceName = "smtz_enterprise:" + env + ":" + CACHE_NAME;
        config.getGroupConfig().setName(instanceName);
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    private IMap<String, Object> getTopMap(String companyID) {
        String key = companyID;
        return hazelcastInstance.getMap(key);
    }

    public List<ExchangeRate> getAllExchangeRates(String companyID) {
        return (List<ExchangeRate>) getTopMap(companyID).get(KEY_ALL_EXCHANGE_RATE);
    }

    public void refreshExchangeRate(String companyID, List<ExchangeRate> rates) {
        this.clearAll(companyID);

        getTopMap(companyID).put(KEY_ALL_EXCHANGE_RATE, rates);
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
