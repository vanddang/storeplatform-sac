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
import com.skplanet.storeplatform.sac.client.display.vo.app.UpdateListReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.UpdateListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.app.service.AppService;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.display.app.vo.UpdateHistory;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/app/detail/v1", method = RequestMethod.POST)
    @ResponseBody
    public AppDetailRes getAppDetail(SacRequestHeader header, @Validated @RequestBody AppDetailReq req) {

        AppDetailParam request = new AppDetailParam();
        request.setChannelId(req.getChannelId());
        request.setLangCd(header.getTenantHeader().getLangCd());
        request.setTenantId(header.getTenantHeader().getTenantId());
        request.setDeviceModelCd(header.getDeviceHeader().getModel());
        request.setOsVersion(header.getDeviceHeader().getOs());
        request.setUserKey(req.getUserKey());
        request.setDeviceKey(req.getDeviceKey());

        AppDetailRes appDetail = appService.getAppDetail(request);
        if (appDetail == null) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        return appDetail;
    }

    @RequestMapping(value = "/app/update/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public UpdateListRes getUpdateList(@Validated UpdateListReq req) {
        UpdateListRes res = new UpdateListRes();
        res.setUpdateList(new ArrayList<Update>());
        res.setCommonResponse(new CommonResponse());

        // 히스토리
        List<UpdateHistory> updateList = this.appService.getUpdateList(req.getChannelId(), req.getOffset(), req.getCount());
        for (UpdateHistory hist : updateList) {
            Update update = new Update();
            update.setDate(new Date(DisplayConstants.DP_DATE_REG, hist.getProdUpdDt()));
            update.setUpdateExplain(hist.getUpdtText());
            res.getUpdateList().add(update);
        }

        // 히스토리 갯수
        int count = this.appService.getUpdateCount(req.getChannelId());
        res.getCommonResponse().setTotalCount(count);

        return res;
    }
}
