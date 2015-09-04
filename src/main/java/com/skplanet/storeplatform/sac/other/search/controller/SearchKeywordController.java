/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.controller;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.common.util.ObjectMapperUtils;
import com.skplanet.storeplatform.sac.other.search.service.SearchKeywordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 검색어 관련 Controller
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class SearchKeywordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKeywordController.class);

    @Autowired
    private SearchKeywordService svc;

    /**
     * <pre>
     * 인기검색어, 급상승 검색어, 테마 검색어 조회.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @RequestMapping(value = "/other/searchKeyword/list/v1", method = RequestMethod.POST)
    @ResponseBody
    public SearchKeywordRes getSearchKeyword(SacRequestHeader sacHeader, @Validated @RequestBody SearchKeywordReq req) {

        LOGGER.debug("#######################################################");
        LOGGER.debug("##### 인기검색어, 급상승 검색어, 테마 검색어 조회 조회 #####");
        LOGGER.debug("#######################################################");

        LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(req));
        SearchKeywordRes res = svc.searchKeyword(req, sacHeader);
        LOGGER.info("Response : {}", ObjectMapperUtils.convertObjectToJson(res));

        return res;

    }

}
