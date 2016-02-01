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

import com.skplanet.storeplatform.sac.member.domain.shared.UserDevicePK;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDeviceSetting;

/**
 * <p>
 * UserDeviceSettingRepository
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
public interface UserDeviceSettingRepository {

    void save(UserDeviceSetting userDeviceSetting);

    UserDeviceSetting findOne(UserDevicePK id);

    UserDeviceSetting findOne(String userKey, String deviceKey);
}
