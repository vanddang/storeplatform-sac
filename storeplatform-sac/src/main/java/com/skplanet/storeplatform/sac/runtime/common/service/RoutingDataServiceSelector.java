package com.skplanet.storeplatform.sac.runtime.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class RoutingDataServiceSelector {

    // TODO : spring 4.x 버전 사용시 @Conditional 로 변경
    @Value("#{systemProperties['ROUTING_ENV'] != null && systemProperties['ROUTING_ENV'].equals('onestore') ? " +
            "'com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataOnestoreQaServiceImpl' : " +
            "'com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataServiceImpl'}")
    String routingDataServiceBeanName;

    @Autowired
    Map<String, RoutingDataService> routingDataServiceMap;

    @PostConstruct
    public void initRoutingDataServiceBean() {
        routingDataService = routingDataServiceMap.get(routingDataServiceBeanName);
    }

    RoutingDataService routingDataService;

    public RoutingDataService getRoutingDataService() {

        return this.routingDataService;
    }
}
