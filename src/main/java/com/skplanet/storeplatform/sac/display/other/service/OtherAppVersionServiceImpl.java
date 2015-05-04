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

import com.google.common.base.Strings;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import com.skplanet.storeplatform.sac.display.other.vo.VersionInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * OtherVersionServiceImpl
 * </p>
 * vmVer, apkVer의 패턴에 따라 조회하도록 하는 방법도 있음.
 * Updated on : 2015. 04. 27 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherAppVersionServiceImpl implements OtherAppVersionService {

    private static final Logger logger = LoggerFactory.getLogger(OtherAppVersionServiceImpl.class);

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    private static final String REDIS_VERSION_INFO_PREFIX = "product:version:"; // product:version:[apkPkgNm]:[deviceModelCd]

    @Override
    public VersionInfo getVersionInfoByPkg(GetVersionInfoByPkgParam param) {
        final Plandasj plandasj = connectionFactory.getConnectionPool().getClient();
        String key = makeKey(param.getApkPkgNm(), param.getDeviceModelCd()),
                osVer = DisplayCommonUtil.extractOsVer(param.getOsVersion());


        if(StringUtils.equals(param.getDeviceModelCd(), DisplayConstants.DP_ANY_PHONE_4APP))
            throw new StorePlatformException("SAC_DSP_0031");

        if(osVer.equals(DisplayCommonUtil.WRONG_OS_VER))
            throw new StorePlatformException("SAC_DSP_0030");

        String v = plandasj.get(key);
        RawVersionInfo rawVersionInfo;

        if (Strings.isNullOrEmpty(v)) {
            Map<String, Object> req = new HashMap<String, Object>();
            req.put("apkPkgNm", param.getApkPkgNm());
            req.put("deviceModelCd", param.getDeviceModelCd());

            rawVersionInfo = commonDAO.queryForObject("OtherAppVersion.getVersionInfoByPkg", req, RawVersionInfo.class);
            if(rawVersionInfo == null)
                return null;

            plandasj.setex(key, 60, rawVersionInfo.makeSerializeData());
        }
        else {
            try {
                rawVersionInfo = new RawVersionInfo(v);
            }
            catch (IllegalStateException e) {
                throw new StorePlatformException("SAC_DSP_9999", e);
            }
        }

        // 통계 처리를 위하여
        // Hash "product:version:stats" << "[prodId]:[deviceModelCd]:[osVer]"
        plandasj.hincrBy("product:version:stats", rawVersionInfo.getProdId() + ":" + param.getDeviceModelCd() + ":" + osVer, 1);

        Map<String, String> map = plandasj.hgetAll("product:version:stats");

        logger.info("Version stats: {}", map);

        if (rawVersionInfo.isSupportVmVer(osVer)) {
            return new VersionInfo(rawVersionInfo.getProdId(), rawVersionInfo.getApkVer());
        }
        else
            throw new StorePlatformException("SAC_DSP_0023");
    }

    @Override
    public void evictVersionInfo(String apkPkgNm, String[] deviceModelCds) {

    }

    private String makeKey(String apkPkgNm, String deviceModelCd) {
        return REDIS_VERSION_INFO_PREFIX + apkPkgNm + ":" + deviceModelCd;
    }

    public static class RawVersionInfo {
        private String prodId;
        private Integer apkVer;
        private String vmVer;
        private Set<String> vmVerSet;

        public RawVersionInfo() {}

        public RawVersionInfo(String rawStr) {
            String[] v = StringUtils.split(rawStr);
            if(v.length != 3)
                throw new IllegalStateException();

            this.prodId = v[0];

            try {
                this.apkVer = Integer.parseInt(v[1]);
            }
            catch (NumberFormatException e) {
                throw new IllegalStateException(e);
            }

            setVmVer(v[2]);
        }

        public String getProdId() {
            return prodId;
        }

        public Integer getApkVer() {
            return apkVer;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public void setApkVer(Integer apkVer) {
            this.apkVer = apkVer;
        }

        public String getVmVer() {
            return vmVer;
        }

        public void setVmVer(String vmVer) {
            this.vmVer = vmVer;
            this.vmVerSet = new HashSet<String>(Arrays.asList(StringUtils.split(vmVer)));
        }

        public boolean isSupportVmVer(String vmVer) {
            return vmVerSet.contains(vmVer);
        }

        public String makeSerializeData() {
            return prodId + " " + apkVer + " " + vmVer;
        }
    }
}
