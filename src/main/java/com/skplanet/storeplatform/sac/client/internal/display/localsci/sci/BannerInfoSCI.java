/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import org.springframework.validation.annotation.Validated;

/**
 * 배너 리스트 조회
 *
 * Created by 1002184 on 2015-01-14.
 */
@SCI
public interface BannerInfoSCI {

    /**
     * 배너 정보 리스트 조회
     *
     * @param req
     */
    public BannerInfoSacRes getBannerInfoList(@Validated BannerInfoSacReq req);
}
