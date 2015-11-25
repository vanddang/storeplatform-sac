/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import com.skplanet.storeplatform.sac.display.other.vo.VersionInfo;

/**
 * <p>
 * FIXME
 * </p>
 * Updated on : 2015. 04. 24 Updated by : 정희원, SK 플래닛.
 */
public interface OtherAppVersionService {

    VersionInfo getVersionInfoByPkg(GetVersionInfoByPkgParam param);

}
