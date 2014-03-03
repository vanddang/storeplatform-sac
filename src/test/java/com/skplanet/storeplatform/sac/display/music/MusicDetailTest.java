/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.music;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailReq;
import com.skplanet.storeplatform.sac.common.MvcTestBuilder;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * <p>
 * MusicDetailTest
 * </p>
 * Updated on : 2014. 02. 17 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class MusicDetailTest {

    private static final String URL = "/display/music/detail/v1";
    private static final String CHNL_ID_OK = "H000460202";
    private static final String CHNL_ID_INVAL = "H000460";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void musicDetailTest() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId(CHNL_ID_OK);

        MvcTestBuilder.buildPost(mvc, URL, req, true);
    }

    @Test
    public void validatorTest1() throws Exception{
        MusicDetailReq req = new MusicDetailReq();

        MvcTestBuilder.buildPost(mvc, URL, req, false);
    }

    @Test
    public void validatorTest2() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId("H000460202");

        MvcTestBuilder.buildPost(mvc, URL, req, true);
    }

    @Test
    public void validatorTest3() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId("H000460202");
        req.setUserKey("AA");

        MvcTestBuilder.buildPost(mvc, URL, req, false);
    }

    @Test
    public void validatorTest4() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId("H000460202");
        req.setDeviceKey("BB");

        MvcTestBuilder.buildPost(mvc, URL, req, false);
    }

    @Test
    public void purchase1() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId("H000460202");
//        req.setDeviceKey("BB");
//        req.setUserKey("");

        MvcTestBuilder.buildPost(mvc, URL, req, true)
                .andExpect(jsonPath("/product/salesStatus").doesNotExist());
    }

    @Test
    public void purchase2() throws Exception {
        MusicDetailReq req = new MusicDetailReq();
        req.setChannelId("H000460202");
        req.setDeviceKey("01046129429");
        req.setUserKey("IF1023541315020111207133720");

        MvcTestBuilder.buildPost(mvc, URL, req, true)
                .andExpect(jsonPath("/product/salesStatus").doesNotExist());
    }

}
