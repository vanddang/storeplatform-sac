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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserExtraInfoService;
import com.skplanet.storeplatform.sac.member.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 서비스 Controller
 *
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserExtraInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtraInfoController.class);

    @Autowired
    private UserExtraInfoService userExtraService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/modifyAdditionalInformation/v1", method = RequestMethod.POST)
    @ResponseBody
    public UserExtraInfoRes modifyAdditionalInformation(@RequestBody @Valid UserExtraInfoReq req, SacRequestHeader sacHeader) {

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        userExtraService.modifyExtraInfo(req.getUserKey(), req.getUserExtraInfoList());

        LOGGER.info("Response : {}", req.getUserKey());

        return new UserExtraInfoRes(req.getUserKey(), null);
    }

    @RequestMapping(value = "/removeAdditionalInformation/v1", method = RequestMethod.POST)
    @ResponseBody
    public UserExtraInfoRes removeAdditionalInformation(@RequestBody @Valid UserExtraInfoReq req, SacRequestHeader sacHeader) {

        String userKey = req.getUserKey();

        List<String> list = Lists.transform(req.getUserExtraInfoList(), new Function<UserExtraInfo, String>() {
            @Nullable
            @Override
            public String apply(UserExtraInfo input) {
                return input.getExtraProfile();
            }
        });

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        this.userExtraService.removeExtraInfo(userKey, list);

        LOGGER.info("Response : {}", userKey);

        return new UserExtraInfoRes(userKey, null);
    }

    @RequestMapping(value = "/listAdditionalInformation/v1", method = RequestMethod.POST)
    @ResponseBody
    public UserExtraInfoRes listAdditionalInformation(@RequestBody @Valid UserExtraInfoReq req, SacRequestHeader sacHeader) {

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        List<UserExtraInfo> infoList = userExtraService.findExtraInfo(req.getUserKey());

        LOGGER.info("Response : {}", req.getUserKey());

        return new UserExtraInfoRes(req.getUserKey(), infoList);
    }

    @RequestMapping(value = "/checkAdditionalInformation/v1", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public MoveUserInfoSacRes moveUserInfo(SacRequestHeader sacHeader, @RequestBody MoveUserInfoSacReq req) {

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
        MoveUserInfoSacRes sacRes = null;

        String userKey = StringUtil.nvl(req.getUserKey(), "");
        String moveType = StringUtil.nvl(req.getMoveType(), "");
        if (userKey.equals("")) {
            throw new StorePlatformException("SAC_MEM_0001", "userKey");
        }
        if (moveType.equals("")) {
            throw new StorePlatformException("SAC_MEM_0001", "moveType");
        }

        // Constant.USERMBR_MOVE_TYPE_ACTIVATE(정상 처리), Constant.USERMBR_MOVE_TYPE_DORMANT(휴면 처리)
        if ("1".equals(req.getMoveType()))
            req.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
        if ("2".equals(req.getMoveType()))
            req.setMoveType(MemberConstants.USER_MOVE_TYPE_DORMANT);

        sacRes = this.userService.moveUserInfo(sacHeader, req);

        // 휴면계정상태해제 계정의 TB_US_USERMBR 테이블의 LAST_LOGIN_DT를 업데이트 한다.
        // 테스트 API에서 복구 처리시 휴면계정고지 배치 대상에 들어가지 않도록 하기 위함.
        if (MemberConstants.USER_MOVE_TYPE_ACTIVATE.equals(req.getMoveType())) {
            this.userService.updateActiveMoveUserLastLoginDt(sacHeader, req);
        }

        LOGGER.info("Response : {}", sacRes);

        return sacRes;
    }
}
