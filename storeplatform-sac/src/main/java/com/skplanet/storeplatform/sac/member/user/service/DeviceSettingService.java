/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.member.domain.shared.UserDeviceSetting;

/**
 * <p>
 * DeviceSettingService
 * 이 서비스는 휴면DB를 참조하지 않음
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
public interface DeviceSettingService {

    UserDeviceSetting find(String userKey, String deviceKey);

    UserDeviceSetting find(String userKey, String deviceKey, boolean checkMarketPin);

    void merge(String userKey, String deviceKey, UserDeviceSetting v);
}
