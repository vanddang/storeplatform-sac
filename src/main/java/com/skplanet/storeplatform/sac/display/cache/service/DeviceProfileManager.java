/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.device.vo.DeviceProfile;

/**
 * <pre>
 * DP_CM_DEVICE 조회 인터페이스
 * </pre>
 * Updated on : 2015. 02. 12 Updated by : 양해엽, SK 플래닛.
 */
public interface DeviceProfileManager {

    /**
     * DEVICE_MODEL_CD별 DP_CM_DEVICE 조회
     *
     * @param deviceModelCd
     * @param langCd
     * @return
     */
    public DeviceProfile getDeviceProfile(String deviceModelCd, String langCd);

}
