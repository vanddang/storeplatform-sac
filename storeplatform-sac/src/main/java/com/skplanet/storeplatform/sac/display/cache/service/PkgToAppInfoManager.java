package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.PkgToAppInfo;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
public interface PkgToAppInfoManager {

    PkgToAppInfo get(String pkgNm);
    void evict(String prodId);
}
