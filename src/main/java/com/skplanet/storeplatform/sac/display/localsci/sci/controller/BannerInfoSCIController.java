/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.BannerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

/**
 * BannerInfoSCIController
 *
 * Created by 1002184 on 2015-01-14.
 */
@LocalSCI
public class BannerInfoSCIController implements BannerInfoSCI {

    @Autowired
    private BannerInfoService bannerInfoService;

    @Override
    public BannerInfoSacRes getBannerInfoList(@Validated BannerInfoSacReq req) {
        return this.bannerInfoService.getBannerInfoList(req);

    }
}
