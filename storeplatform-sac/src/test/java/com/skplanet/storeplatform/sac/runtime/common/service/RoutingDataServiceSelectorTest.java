package com.skplanet.storeplatform.sac.runtime.common.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.Map;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RoutingDataServiceSelectorTest {

    // TODO : spring 4.x 버전 사용시 @Conditional 로 변경
    @Value("#{systemProperties['ENV'].equals('onestore')?" +
            "'com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataOnestoreQaServiceImpl':" +
            "'com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataServiceImpl'}")
    String routingDataServiceBeanName;

    @Autowired Map<String, RoutingDataService> routingDataServiceMap;
    @PostConstruct
    public void initRoutingDataServiceBean() {
        routingDataService = routingDataServiceMap.get(routingDataServiceBeanName);
    }

    RoutingDataService routingDataService;

    @Test
    public void injectionTest() {
        //System.out.println(env);
        System.out.println("routingDataServiceBeanName=" + routingDataServiceBeanName);
        System.out.println("routingDataService=" + routingDataService);
    }
}
