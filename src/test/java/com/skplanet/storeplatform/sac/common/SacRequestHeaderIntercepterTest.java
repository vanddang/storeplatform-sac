/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SacRequestHeaderIntercepterTest
 * </p>
 * Updated on : 2014. 03. 03 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class SacRequestHeaderIntercepterTest {

    private static final String APP_DETAIL_URL = "/display/app/detail/v1";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void test1() throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();
        MvcTestBuilder.build2(mvc, true, headerMap, APP_DETAIL_URL, new AppDetailReq("0000065131"), true);
    }

    @Test
    public void test2() throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("x-sac-device-info", "model=\"SHW-M340S\"");
        MvcTestBuilder.build2(mvc, true, headerMap, APP_DETAIL_URL, new AppDetailReq("0000297941"), true);
    }

    @Test
    public void test3WithWrongKeyName() throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("x-sac-device-info", "modele=\"SHW-M340S\"");
        MvcTestBuilder.build2(mvc, true, headerMap, APP_DETAIL_URL, new AppDetailReq("0000297941"), null);
    }

    @Test
    public void testNetworkHeader() throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();

        headerMap.put(CommonConstants.HEADER_NETWORK, "type=\"lte\"");
        headerMap.put("x-sac-device-info", "model=\"SHW-M340S\"");
        MvcTestBuilder.build2(mvc, true, headerMap, APP_DETAIL_URL, new AppDetailReq("0000297941"), null);
    }

    @Test
    public void testComplexDeviceHeader() throws Exception {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("x-sac-device-info", "model=\"SHW-M340S\", dpi=\"200,\", resolution =\"160*160\" , os=\"Android_/4.0.4\",pkg=\"test/38\",svc=\"\"");
        MvcTestBuilder.build2(mvc, true, headerMap, APP_DETAIL_URL, new AppDetailReq("0000297941"), true);
    }
}
