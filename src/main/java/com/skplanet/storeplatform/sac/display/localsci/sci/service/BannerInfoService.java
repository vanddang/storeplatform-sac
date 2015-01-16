/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;

/**
 * BannerInfoService
 *
 * Created by 1002184 on 2015-01-14.
 */
public interface BannerInfoService {

    /**
     * 배너 리스트 조회
     * @param req
     * @return
     */
    public BannerInfoSacRes getBannerInfoList(BannerInfoSacReq req);

}
