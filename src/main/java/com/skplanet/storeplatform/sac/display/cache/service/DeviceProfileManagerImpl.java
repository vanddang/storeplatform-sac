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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.device.vo.DeviceProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * DP_CM_DEVICE 조회 구현체
 * </pre>
 * Updated on : 2015. 02. 12 Updated by : 양해엽, SK 플래닛.
 */
@Service
public class DeviceProfileManagerImpl implements DeviceProfileManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    @Cacheable(value = "sac:display:device:profile:v1", key = "#deviceModelCd + '_' + #langCd", unless = "#result == null")
    public DeviceProfile getDeviceProfile(String deviceModelCd, String langCd) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("deviceModelCd", deviceModelCd);
        req.put("langCd", langCd);
        return commonDAO.queryForObject("DeviceProfile.selectDeviceProfile", req, DeviceProfile.class);
    }
}
