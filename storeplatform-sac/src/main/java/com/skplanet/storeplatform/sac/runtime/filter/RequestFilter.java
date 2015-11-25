/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.filter;

import com.skplanet.storeplatform.framework.core.cache.process.GlobalCacheProcessor;
import com.skplanet.storeplatform.framework.core.util.log.TLogThreadHolder;
import com.skplanet.storeplatform.framework.web.filter.AbstractStorePlatformRequestFilter;
import com.skplanet.storeplatform.framework.web.filter.StoreplatformServletRequestWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

/**
 * 모든 Request 에 대해서 흐름 상의 Framework 로직 처리 (대부분 상위 클래스에서 처리)
 *
 * Updated on : 2014-01-06 Updated by : 김상호, 에이엔비.
 * Updated on : 2014-12-12 Updated by : 정희원, SKP.
 * Updated on : 2015-07-10 Updated by : 최은봉, SKP.
 * Updated on : 2015-07-20 Updated by : 최은봉, SKP. - spring bean 으로 변경 & global cache use flag 체크 로직 개선
 */
@Component
public class RequestFilter extends AbstractStorePlatformRequestFilter {

    @Autowired(required = false)
    GlobalCacheProcessor globalCacheProcessor;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    protected String getContextId() {
        return "SAC";
    }

    @Override
    protected void initCustomContexts() {
        // ShuttleThreadHolder 에 기본 Logger 를 지정
        TLogThreadHolder.setShuttleLogger(LoggerFactory.getLogger("TLOG_SAC_LOGGER"));
    }

    @Override
    protected Map<String, String> addCustomHeaders(StoreplatformServletRequestWrapper requestWrapper) {

        Map<String, String> headerMap = new HashMap<String, String>();

        // 테스트를 위해 globalCache 를 사용하지 않도록 설정한 경우에 대한 방어 로직
        if(globalCacheProcessor != null && !globalCacheProcessor.isUseCache()) {
            headerMap.put("x-sac-use-cache", "false");
        }

        return headerMap;
    }

}
