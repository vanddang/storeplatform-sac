/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.display.MvcTestBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * <p>
 * AppDetailTest
 * </p>
 * Updated on : 2014. 02. 17 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AppDetailTest {

    private static final String URL = "/display/app/detail/v1";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void appDetailTest() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setChannelId("0000065131"); // 0000065131 - 구매 이력이 존재하는 앱 상품

        MvcTestBuilder.createMvcTestPost(mvc, URL, req, true);
    }

    @Test
    public void appDetailTestWithPurchase() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setChannelId("0000065131"); // 0000065131 - 구매 이력이 존재하는 앱 상품

        MvcTestBuilder.createMvcTestPost(mvc, URL, req, true);
    }

    @Test
    public void validatorTest1() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setUserKey("AA");

        MvcTestBuilder.createMvcTestPost(mvc, URL, req, false);
    }

    @Test
    public void validatorTest2() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setDeviceKey("BB");
        MvcTestBuilder.createMvcTestPost(mvc, URL, req, false);
    }

    @Test
    public void appDetailTestWithPurchase2() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setChannelId("0000065131"); // 0000065131 - 구매 이력이 존재하는 앱 상품
        req.setUserKey("IF1023000075420110321134705");
        req.setDeviceKey("01045916961");    // 개발계에는 deviceKey가 MDN으로 정의되어 있음.

        MvcTestBuilder.createMvcTestPost(mvc, URL, req, true);
    }

    @Test
    public void appDetailTestWithPurchase3() throws Exception {
        AppDetailReq req = new AppDetailReq();
        req.setChannelId("0000065131"); // 0000065131 - 구매 이력이 존재하는 앱 상품
        req.setUserKey("IF1023000075420110321134705");
        req.setDeviceKey("");

        MvcTestBuilder.createMvcTestPost(mvc, URL, req, false);
    }


}
