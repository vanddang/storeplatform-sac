package com.skplanet.storeplatform.sac.runtime.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Collections2;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.skplanet.storeplatform.framework.core.cache.process.GlobalCacheProcessor;
import com.skplanet.storeplatform.framework.core.util.log.TLogThreadHolder;
import com.skplanet.storeplatform.framework.integration.filter.AbstractStorePlatformRequestFilter;

/**
 * Thread Context Filter
 * 
 * Updated on : 2014-01-06 Updated by : 김상호, 에이엔비.
 * Updated on : 2014-12-12 Updated by : 정희원, SKP.
 */
public class RequestFilter extends AbstractStorePlatformRequestFilter {

    private boolean checkGlobalCache = true;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        // 실행 환경의 프로파일이 local로 지정된 경우 globalCacheProcessor가 정의되지 않은 경우 plandas의 상태값을 헤더에 포함시키지 않도록 처리한다.
        if(ArrayUtils.contains(ctx.getEnvironment().getActiveProfiles(), "local")) {
            checkGlobalCache = ctx.containsBeanDefinition("globalCacheProcessor");
        }
    }

    @Override
	protected String getContextId() {
		return "SAC";
	}

	@Override
	protected void initExtraRequestFilter(HttpServletRequest request, HttpServletResponse response) {
		/* ShuttleThreadHolder 에 기본 Logger 를 지정 한다. */
		TLogThreadHolder.setShuttleLogger(LoggerFactory.getLogger("TLOG_SAC_LOGGER"));
	}

	@Override
	protected Map<String, String> getReqeustHeader(HttpServletRequest request) {

        Map<String, String> headerMap = new HashMap<String, String>();

        if (checkGlobalCache) {
            ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.filterConfig
                    .getServletContext());

		    /* GlobalCacheProcessor useCache 가 false 일 경우 request header 내 'x-sac-use-cache' 에 false 를 셋팅 한다. */
            GlobalCacheProcessor globalCacheProcessor = (GlobalCacheProcessor) ac.getBean("globalCacheProcessor");
            if (!globalCacheProcessor.isUseCache()) {
                headerMap.put("x-sac-use-cache", "false");
            }
        }

		return headerMap;
	}

}
