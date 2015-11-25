package com.skplanet.storeplatform.sac.display.menu.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 메뉴 카테고리 테스트
 *
 * @author 1002159
 * @since 2015-10-01
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class MenuControllerTest {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void searchBestMenuList() throws Exception {

//        this.mvc.perform(
//                get( String.format("/display/menu/best/list/v1?menuCategoryCd=%s", "DP01150201") )
//                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
//                        .header("x-sac-tenant-id", "S01")
//                        .header("x-sac-system-id", "S01-01014")
//        )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(header().string("x-sac-result-code", "SUCC"))
//        ;

        this.mvc.perform(
                get(String.format("/display/menu/best/list/v1?menuCategoryCd=%s", "DP01150201"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-sac-tenant-id", "S01")
                        .header("x-sac-system-id", "S01-01014")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(header().string("x-sac-result-code", "SUCC"))
        ;

    }


}