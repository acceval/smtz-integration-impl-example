package com.acceval.core.cache;

import com.acceval.core.cache.impl.ConditionRecordCache;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class HazelcastCacheInstance {

    private static final Logger logger = LoggerFactory.getLogger(HazelcastCacheInstance.class);

    private HazelcastInstance hazelcastInstance;

    private HazelcastCacheInstance(@Value("${microservice.env}") String env,
                                   @Value("${microservice.cache-config.kubernetes.enable:false}") boolean kubernetesEnable,
                                   @Value("${microservice.cache-config.kubernetes.namespace:dummy}") String kubernetesNamespace,
                                   @Value("${microservice.cache-config.kubernetes.service-name:dummy}") String hazelCastServiceName) {
        Config config = new Config();

//        config.getNetworkConfig().setPort(5710);

        logger.info("Hazelcast Cache Instance Property : Kubernetes Enable : " + kubernetesEnable);
        logger.info("Hazelcast Cache Instance Property : Kubernetes Namespace : " + kubernetesNamespace);
        logger.info("Hazelcast Cache Instance Property : Kubernetes Service Name : " + hazelCastServiceName);

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


        logger.info("Hazelcast Cache Instance construct. " + env);
        String instanceName = "smtz:" + env + ":cluster";
        config.getGroupConfig().setName(instanceName);
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }
}
