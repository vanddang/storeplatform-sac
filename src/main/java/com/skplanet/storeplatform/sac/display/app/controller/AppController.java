/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.app.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.app.service.AppService;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
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
 * AppController
 * Updated on : 2014. 01. 06 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/display")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        dataBinder.setValidator(new AppDetailReqValidator());
//    }

    /**
     * I03000038
     * @param header
     * @param req
     * @return
     */
    @RequestMapping(value = "/app/detail/v1", method = RequestMethod.POST)
    @ResponseBody
    public AppDetailRes getAppDetail(SacRequestHeader header, @Validated @RequestBody AppDetailReq req) {

        AppDetailParam request = new AppDetailParam();
        request.setChannelId(req.getChannelId());
        request.setLangCd(header.getTenantHeader().getLangCd());
        request.setTenantId(header.getTenantHeader().getTenantId());
        request.setDeviceModelCd(header.getDeviceHeader().getModel());
        request.setOsVersion(header.getDeviceHeader().getOs());
//        request.setSdkCd(header.getDeviceHeader().getSdkCdNum());
        request.setSdkCd(null);
        request.setUserKey(req.getUserKey());
        request.setDeviceKey(req.getDeviceKey());

        AppDetailRes appDetail = appService.searchAppDetail(request);
        if (appDetail == null) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        appDetail.getProduct().getRights().setAgeAllowedFrom(null);

        return appDetail;
    }

    /**
     * ?
     * @param header
     * @param req
     * @return
     */
    @RequestMapping(value = "/app/detail/v2", method = RequestMethod.POST)
    @ResponseBody
    public AppDetailRes getAppDetailV2(SacRequestHeader header, @Validated @RequestBody AppDetailReq req) {

        AppDetailParam request = new AppDetailParam();
        request.setChannelId(req.getChannelId());
        request.setLangCd(header.getTenantHeader().getLangCd());
        request.setTenantId(header.getTenantHeader().getTenantId());
        request.setDeviceModelCd(header.getDeviceHeader().getModel());
        request.setOsVersion(header.getDeviceHeader().getOs());
//        request.setSdkCd(header.getDeviceHeader().getSdkCdNum());
        request.setSdkCd(null);
        request.setUserKey(req.getUserKey());
        request.setDeviceKey(req.getDeviceKey());

        AppDetailRes appDetail = appService.searchAppDetail(request);
        if (appDetail == null) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        return appDetail;
    }

}
