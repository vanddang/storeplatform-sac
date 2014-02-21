/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other;

import com.skplanet.storeplatform.sac.display.other.service.ProductStatusService;
import com.skplanet.storeplatform.sac.display.other.vo.ParentAppInfo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * ProductStatusServiceTest
 * </p>
 * Updated on : 2014. 02. 18 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ProductStatusServiceTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Autowired
    private ProductStatusService productStatusService;

    @Test
    public void selectParentProductStatusTest() {

        ParentAppInfo parentAppInfo = productStatusService.selectParentInfo("S01", "ko", "0900121441");
        assert parentAppInfo.getParentChannelId() != null;

    }

    @Test
    public void selectParentProductStatusTestWithNoData() {

        ParentAppInfo parentAppInfo = productStatusService.selectParentInfo("S01", "ko", "");
        assert parentAppInfo == null;

    }

    @Test
    public void parentStatusTest() throws Exception {
        this.mvc.perform(
                get("/display/other/parentStatus/get/v1?partChannelId=0900121441")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(header().string("x-sac-result-code", "SUCC"));
    }

    @Test
    public void parentStatusTestWithError() throws Exception {
        this.mvc.perform(
                get("/display/other/parentStatus/get/v1?partChannelId=QQ")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(header().string("x-sac-result-code", "FAIL"))
        ;
    }

    @Test
    public void parentStatusTestWithNoParam() throws Exception {
        this.mvc.perform(
                get("/display/other/parentStatus/get/v1")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(header().string("x-sac-result-code", "FAIL"))
        ;
    }

}
