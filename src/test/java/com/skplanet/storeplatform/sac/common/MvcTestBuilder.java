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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private static final Map<String, String> DEFAULT_DEVICE_HEADER_MAP;

    static {
        DEFAULT_DEVICE_HEADER_MAP = new HashMap<String, String>();
        DEFAULT_DEVICE_HEADER_MAP.put("model", "SHV-E110S");
        DEFAULT_DEVICE_HEADER_MAP.put("dpi", "320");
        DEFAULT_DEVICE_HEADER_MAP.put("resolution", "480*720");
        DEFAULT_DEVICE_HEADER_MAP.put("os", "Android/4.0.4");
        DEFAULT_DEVICE_HEADER_MAP.put("pkg", "sac.store.skplanet.com/37");
    }

    public static Map<String, String> getDefaultHeader() {
        return new HashMap<String, String>(DEFAULT_DEVICE_HEADER_MAP);
    }

    public static ResultActions buildGet(MockMvc mvc, String url, boolean expectResultSuccess) throws Exception {

        return build(mvc, false, DEFAULT_DEVICE_HEADER_MAP, url, null, expectResultSuccess);
    }

    public static ResultActions buildPost(MockMvc mvc, String url, Object reqObj, boolean expectResultSuccess) throws Exception
    {
        return build(mvc, true, DEFAULT_DEVICE_HEADER_MAP, url, reqObj, expectResultSuccess);
    }

    public static ResultActions build(MockMvc mvc, boolean isPost, String deviceInfoHeader, String url, String postBody, boolean expectResultSuccess) throws Exception {
        MockHttpServletRequestBuilder request;
        if(isPost)
            request = post(url);
        else
            request = get(url);

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

    public static ResultActions build(MockMvc mvc, boolean isPost, String deviceHeader, String url, Object reqObj, boolean expectResultSuccess) throws Exception {
        String postBody;
        try
        {
            postBody = new ObjectMapper().writeValueAsString(reqObj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return build(mvc, isPost, deviceHeader, url, postBody, expectResultSuccess);
    }

    public static ResultActions build(MockMvc mvc, boolean isPost, Map<String, String> deviceInfoMap, String url, Object reqObj, boolean expectResultSuccess) throws Exception {
        String postBody;
        try
        {
            postBody = new ObjectMapper().writeValueAsString(reqObj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> kv : deviceInfoMap.entrySet()) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(kv.getKey()).append("=\"").append(kv.getValue()).append("\"");
        }

        return build(mvc, isPost, sb.toString(), url, postBody, expectResultSuccess);
    }

    public static ResultActions build2(MockMvc mvc, boolean isPost, Map<String, String> headerMap, String url, Object reqObj, Boolean expectResultSuccess) throws Exception {
        String postBody;
        try
        {
            postBody = new ObjectMapper().writeValueAsString(reqObj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        MockHttpServletRequestBuilder request;
        if(isPost)
            request = post(url);
        else
            request = get(url);

        for(Map.Entry<String, String> header : headerMap.entrySet()) {
            request.header(header.getKey(), header.getValue());
        }

        request.contentType(MediaType.APPLICATION_JSON);

        if(isPost && !StringUtils.isEmpty(postBody))
        {
            request.content(postBody);
        }

        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        if(expectResultSuccess != null)
            actions.andExpect(header().string("x-sac-result-code", expectResultSuccess ? "SUCC" : "FAIL"));

        return actions;
    }
}
