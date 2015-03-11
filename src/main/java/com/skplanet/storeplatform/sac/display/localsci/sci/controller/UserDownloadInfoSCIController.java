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

import java.util.Date;

/**
 * <p>
 * UserDownoadInfoSCIController
 * </p>
 * Updated on : 2015. 03. 11 Updated by : 정희원, SK 플래닛.
 */
@LocalSCI
public class UserDownloadInfoSCIController implements UserDownloadInfoSCI {

    @Override
    public UserDownloadInfoRes getUserDownloadInfo(UserDownloadInfoReq req) {
        UserDownloadInfoRes res = new UserDownloadInfoRes();
        res.setProdId("0000600000");
        res.setLatestTenantId("S01");
        res.setLatestDownloadDate(new Date());

        return res;
    }
}
