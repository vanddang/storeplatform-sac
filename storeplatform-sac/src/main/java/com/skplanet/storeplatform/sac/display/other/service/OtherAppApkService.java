package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.sac.display.other.vo.AppApkInfo;

import java.util.List;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
public interface OtherAppApkService {

    List<AppApkInfo> getApkSignedHash(String packageName);
}
