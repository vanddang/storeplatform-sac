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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.support.redis.RedisDataService;
import com.skplanet.storeplatform.sac.display.cache.vo.PkgToAppInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.cache.vo.SupportDeviceParam;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import com.skplanet.storeplatform.sac.display.other.vo.VersionInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * <p>
 * OtherVersionServiceImpl
 * </p>
 * Updated on : 2015. 04. 27 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherAppVersionServiceImpl implements OtherAppVersionService {

    private static final Logger logger = LoggerFactory.getLogger(OtherAppVersionServiceImpl.class);

    @Autowired
    private RedisDataService dataService;

    @Override
    public VersionInfo getVersionInfoByPkg(GetVersionInfoByPkgParam param) {
        String osVer = DisplayCommonUtil.extractOsVer(param.getOsVersion());

        if(StringUtils.equals(param.getDeviceModelCd(), DisplayConstants.DP_ANY_PHONE_4APP))
            throw new StorePlatformException("SAC_DSP_0031");

        if(osVer.equals(DisplayCommonUtil.WRONG_OS_VER))
            throw new StorePlatformException("SAC_DSP_0030");

        PkgToAppInfo appInfo = dataService.get(PkgToAppInfo.class, param.getApkPkgNm());
        if(appInfo == null)
            return null;

        String prodId = appInfo.getProdId();

        SupportDevice supportDevice = dataService.get(SupportDevice.class, new SupportDeviceParam(prodId, param.getDeviceModelCd()));

        if (supportDevice == null)
            return null;

        // OS 프로비저닝
        HashSet<String> verSet = new HashSet<String>(Arrays.asList(supportDevice.getOsVer().split(";")));
        if(!verSet.contains(osVer))
            return null;

        // 통계처리
        /*
        final Plandasj plandasj = connectionFactory.getConnectionPool().getClient();
        plandasj.hincrBy("product:version:stats", prodId + ":" + param.getDeviceModelCd() + ":" + osVer, 1);
        */

        return new VersionInfo(prodId, supportDevice.getVerCd(), supportDevice.getVer());
    }

}
