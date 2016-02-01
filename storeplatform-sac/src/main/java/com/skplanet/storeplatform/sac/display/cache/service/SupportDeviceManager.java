package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.SupportDevice;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
public interface SupportDeviceManager {

    SupportDevice get(String prodId, String deviceModelCd);
    void evict(String prodId);
}
