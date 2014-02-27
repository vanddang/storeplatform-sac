/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * Mvc test를 간편하게 만들어주는 헬퍼 클래스.
 * </p>
 * Updated on : 2014. 02. 27 Updated by : 정희원, SK 플래닛.
 */
public class MvcTestBuilder {

    public static ResultActions createMvcTestGet(MockMvc mvc, String url, boolean expectResultSuccess) throws Exception
    {
        return createMvcTest(mvc, false, null, url, null, expectResultSuccess);
    }

    public static ResultActions createMvcTestPost(MockMvc mvc, String url, Object reqObj, boolean expectResultSuccess) throws Exception
    {
        String postBody;
        try
        {
            postBody = new ObjectMapper().writeValueAsString(reqObj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return createMvcTest(mvc, true, null, url, postBody, expectResultSuccess);
    }

    public static ResultActions createMvcTest(MockMvc mvc, boolean isPost, String deviceInfoHeader, String url, String postBody, boolean expectResultSuccess) throws Exception {
        MockHttpServletRequestBuilder request;
        if(isPost)
            request = post(url);
        else
            request = get(url);

        if(deviceInfoHeader == null)
            request.header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37");
        else
            request.header("x-sac-device-info", deviceInfoHeader);

        request.contentType(MediaType.APPLICATION_JSON);

        if(isPost && !StringUtils.isEmpty(postBody))
        {
            request.content(postBody);
        }

        return mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("x-sac-result-code", expectResultSuccess ? "SUCC" : "FAIL"));
    }
}
