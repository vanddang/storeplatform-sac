/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.shared.UserDevice;

/**
 * <p>
 * UserDeviceRepository
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
public interface UserDeviceRepository {

    /**
     * 기기 정보를 조회한다.
     * @param userKey
     * @param deviceKey
     * @return
     */
    UserDevice findByUserKeyAndDeviceKey(String userKey, String deviceKey);
}
