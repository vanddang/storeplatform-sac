/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.common.MvcTestBuilder;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 06 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ProductInfoMangerAdoptedMetaInfoServiceTest {
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
        headerMap.put("x-sac-use-cache", "false");

        MvcResult mvcResult1 = MvcTestBuilder.build2(mvc, false, headerMap, "/display/feature/best/app/list/v1?listId=ADM000000012", null, true).andReturn();
        String res1 = mvcResult1.getResponse().getContentAsString();

        headerMap.put("x-sac-use-cache", "true");

        MvcResult mvcResult2 =MvcTestBuilder.build2(mvc, false, headerMap, "/display/feature/best/app/list/v1?listId=ADM000000012", null, true).andReturn();
        String res2 = mvcResult2.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        JsonNode jn1 = om.readTree(res1);
        JsonNode jn2 = om.readTree(res2);
        if(!jn1.equals(jn2)) {
            throw new Exception("NOT_EQUAL");
        }

    }

    @Test
    public void test2() throws Exception {

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("x-sac-use-cache", "true");

        MvcTestBuilder.build2(mvc, false, headerMap, "/display/feature/best/app/list/v1?listId=ADM000000012", null, true);
    }
}
