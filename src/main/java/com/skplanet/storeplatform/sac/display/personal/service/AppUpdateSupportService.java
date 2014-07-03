/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;

import java.util.List;

/**
 * <p>
 * AppUpdateInfoManager
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public interface AppUpdateSupportService {

    /**
     * 패키지명으로 서브컨텐트 정보를 조회한다.
     * @param deviceModelCd
     * @param pkgList @return
     * @param isHashed
     */
    List<SubContentInfo> searchSubContentByPkg(String deviceModelCd, List<String> pkgList, boolean isHashed);

    java.util.Set<String> getPurchaseSet(String tenantId, String userKey, String deviceKey, List<String> prodIdList);
}
