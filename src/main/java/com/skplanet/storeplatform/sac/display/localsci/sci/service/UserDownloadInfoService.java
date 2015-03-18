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

import com.skplanet.storeplatform.sac.display.localsci.sci.vo.GetUserDownloadInfoParam;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.UserDownloadInfo;

/**
 * <p>
 * UserDownloadInfoService
 * </p>
 * Updated on : 2015. 03. 16 Updated by : 정희원, SK 플래닛.
 */
public interface UserDownloadInfoService {

    /**
     * 다운로드 정보를 조회한다.
     * @param param
     * @return
     */
    UserDownloadInfo getUserDownloadInfo(GetUserDownloadInfoParam param);

}
