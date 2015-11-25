/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.controller;

import com.skplanet.storeplatform.sac.client.display.vo.app.UpdateListReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.UpdateListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.UpdateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * OtherUpdateListController
 * </p>
 * Updated on : 2014. 05. 02 Updated by : 정희원, SK 플래닛.
 */
@Controller
public class OtherUpdateListController {

    @Autowired
    private DisplayCommonService displayCommonService;

    @RequestMapping(value = "/display/other/update/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public UpdateListRes getUpdateList(@Validated UpdateListReq req) {
        UpdateListRes res = new UpdateListRes();
        res.setUpdateList(new ArrayList<Update>());
        res.setCommonResponse(new CommonResponse());

        // 히스토리
        List<UpdateHistory> updateList = this.displayCommonService.getUpdateList(req.getChannelId(), req.getOffset(), req.getCount());
        for (UpdateHistory hist : updateList) {
            Update update = new Update();
            update.setDate(new Date(DisplayConstants.DP_DATE_REG, hist.getProdUpdDt()));
            update.setUpdateExplain(hist.getUpdtText());
            res.getUpdateList().add(update);
        }

        // 히스토리 갯수
        int count = this.displayCommonService.getUpdateCount(req.getChannelId());
        res.getCommonResponse().setTotalCount(count);

        return res;
    }
}
