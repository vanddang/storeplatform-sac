/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.csp.controller;

import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointReq;
import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.common.util.ObjectMapperUtils;
import com.skplanet.storeplatform.sac.other.csp.service.CspService;
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
 * CSP 연동 Controller
 *
 * Updated on : 2015. 8. 12. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class CspController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CspController.class);

    @Autowired
    private CspService svc;

    /**
     * <pre>
     * CSP 팅 포인토 조회.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @RequestMapping(value = "/other/csp/getInquiryBalance/v1", method = RequestMethod.POST)
    @ResponseBody
    public CspTingPointRes createOcbInformation(SacRequestHeader sacHeader, @Validated @RequestBody CspTingPointReq req) {

        LOGGER.debug("###############################");
        LOGGER.debug("##### Csp Ting Point 조회 #####");
        LOGGER.debug("###############################");

        LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(req));

        /**
         * CSP 팅 포인트 조회 Biz
         */
        CspTingPointRes res = this.svc.getTingPoint(sacHeader, req);

        LOGGER.info("Response : {}", ObjectMapperUtils.convertObjectToJson(res));

        return res;

    }

}
