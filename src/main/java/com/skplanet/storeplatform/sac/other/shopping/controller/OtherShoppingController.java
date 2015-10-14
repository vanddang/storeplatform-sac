/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.shopping.controller;

import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckReq;
import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.common.util.ObjectMapperUtils;
import com.skplanet.storeplatform.sac.other.shopping.service.OtherShoppingService;
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
 * 쇼핑 충전권 연동 Controller
 *
 * Updated on : 2015. 10. 07. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class OtherShoppingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherShoppingController.class);

    @Autowired
    private OtherShoppingService svc;

    /**
     * <pre>
     * 제휴사 회원 유효성 체크.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @RequestMapping(value = "/other/shopping/allianceUserCheck/v1", method = RequestMethod.POST)
    @ResponseBody
    public AllianceUserCheckRes allianceUserCheck(SacRequestHeader sacHeader, @Validated @RequestBody AllianceUserCheckReq req) {

        LOGGER.debug("#####################################");
        LOGGER.debug("##### 제휴사 회원 유효성 체크 조회 #####");
        LOGGER.debug("#####################################");

        LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(req));

        /**
         * 제휴사 회원 유효성 체크 연동 Biz
         */
        AllianceUserCheckRes res = this.svc.allianceUserCheck(sacHeader, req);

        LOGGER.info("Response : {}", ObjectMapperUtils.convertObjectToJson(res));

        return res;

    }

}
