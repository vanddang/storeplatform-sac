/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserWithdrawService;

/**
 * 회원 탈퇴 서비스 Controller
 *
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserWithdrawController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithdrawController.class);

    ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    private UserWithdrawService svc;

    @RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
    @ResponseBody
    public WithdrawRes withdraw(@RequestBody WithdrawReq req, SacRequestHeader sacHeader) {

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        /**
         * 파라미터 체크 (userId, deviceId 둘다 미존재)
         */
        if (StringUtils.isBlank(req.getUserId()) && StringUtils.isBlank(req.getDeviceId())) {
            throw new StorePlatformException("SAC_MEM_0002", "userId or deviceId");
        }

        /**
         * 파라미터 체크 (userId 존재, userAuthKey 미존재)
         */
        if (StringUtils.isNotBlank(req.getUserId()) && StringUtils.isBlank(req.getUserAuthKey())) {
            throw new StorePlatformException("SAC_MEM_0002", "userAuthKey");
        }

        WithdrawRes res = this.svc.executeWithdraw(sacHeader, req);

        LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

        return res;
    }

}
