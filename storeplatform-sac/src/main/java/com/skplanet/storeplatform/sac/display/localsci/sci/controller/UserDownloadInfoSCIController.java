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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UserDownloadInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.UserDownloadInfoService;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.GetUserDownloadInfoParam;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.UserDownloadInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * UserDownoadInfoSCIController
 * </p>
 * Updated on : 2015. 03. 11 Updated by : 정희원, SK 플래닛.
 */
@LocalSCI
public class UserDownloadInfoSCIController implements UserDownloadInfoSCI {

    @Autowired
    private UserDownloadInfoService userDownloadInfoService;

    @Override
    public UserDownloadInfoRes getUserDownloadInfo(UserDownloadInfoReq req) {
        if(req == null)
            throw new IllegalArgumentException();

        return userDownloadInfoService.getUserDownloadInfo(new GetUserDownloadInfoParam(req.getMdn(), req.getPartProdId(), req.getTenantId()));
    }
}
