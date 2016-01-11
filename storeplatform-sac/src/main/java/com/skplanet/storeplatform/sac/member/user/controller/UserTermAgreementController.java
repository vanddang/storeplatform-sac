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

import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserModifyService;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * <p>
 * UserTermAgreementController
 * </p>
 * Updated on : 2016. 01. 08 Updated by : 정희원, SK 플래닛.
 */
@Controller
public class UserTermAgreementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTermAgreementController.class);

    @Autowired
    private UserSearchService searchService;

    @Autowired
    private UserModifyService modifyService;

    /**
     * <pre>
     * [I01000010]
     * </pre>
     * @param req
     * @param sacHeader
     * @return
     */
    @RequestMapping(value = "/member/user/listTermsAgreement/v1", method = RequestMethod.POST)
    @ResponseBody
    public ListTermsAgreementSacRes listTermsAgreement(@RequestBody @Valid ListTermsAgreementSacReq req, SacRequestHeader sacHeader) {
        LOGGER.debug("####################################################");
        LOGGER.debug("##### 2.1.10. Store 약관 동의 목록 조회 #####");
        LOGGER.debug("####################################################");

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        ListTermsAgreementSacRes res = this.searchService.listTermsAgreement(sacHeader, req);

        LOGGER.info("Response : {}, size : {}", res.getUserKey(), res.getAgreementList().size());

        return res;
    }

    /**
     * <pre>
     * [I01000016] Store 약관 동의 등록.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @RequestMapping(value = "/member/user/createTermsAgreement/v1", method = RequestMethod.POST)
    @ResponseBody
    public CreateTermsAgreementRes createTermsAgreement(@Valid @RequestBody CreateTermsAgreementReq req, SacRequestHeader sacHeader) {

        LOGGER.debug("#####################################");
        LOGGER.debug("##### 2.1.16 Store 약관 동의 등록 #####");
        LOGGER.debug("#####################################");

        /**
         * Store 약관 동의 등록 Biz
         */
        this.modifyService._mergeTermsAgreement(sacHeader, req.getUserKey(), req.getAgreementList());

        return new CreateTermsAgreementRes(req.getUserKey());

    }

    /**
     * <pre>
     * [I01000017] Store 약관 동의 수정.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @RequestMapping(value = "/member/user/modifyTermsAgreement/v1", method = RequestMethod.POST)
    @ResponseBody
    public ModifyTermsAgreementRes modifyTermsAgreement(@Valid @RequestBody ModifyTermsAgreementReq req, SacRequestHeader sacHeader) {

        LOGGER.debug("#####################################");
        LOGGER.debug("##### 2.1.17 Store 약관 동의 수정 #####");
        LOGGER.debug("#####################################");

        /**
         * Store 약관 동의 수정 Biz
         */
        this.modifyService._mergeTermsAgreement(sacHeader, req.getUserKey(), req.getAgreementList());

        return new ModifyTermsAgreementRes(req.getUserKey());

    }

}
