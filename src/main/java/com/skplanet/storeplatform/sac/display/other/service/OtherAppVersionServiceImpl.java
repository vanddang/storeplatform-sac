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

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import com.skplanet.storeplatform.sac.display.other.vo.VersionInfo;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
 * FIXME json 라이브러리를 바꿔서 처리를 좀더 깔끔하게 해야겠음.
 * Updated on : 2015. 04. 27 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherAppVersionServiceImpl implements OtherAppVersionService {

    private static final Logger logger = LoggerFactory.getLogger(OtherAppVersionServiceImpl.class);

//    @Autowired(required = false)
//    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    @Override
    public VersionInfo getVersionInfoByPkg(GetVersionInfoByPkgParam param) {
        String osVer = DisplayCommonUtil.extractOsVer(param.getOsVersion());

        if(StringUtils.equals(param.getDeviceModelCd(), DisplayConstants.DP_ANY_PHONE_4APP))
            throw new StorePlatformException("SAC_DSP_0031");

        if(osVer.equals(DisplayCommonUtil.WRONG_OS_VER))
            throw new StorePlatformException("SAC_DSP_0030");

        String prodId = cachedExtraInfoManager.getProdIdByPkgNm(param.getApkPkgNm());
        if (prodId == null)
            return null;

        SprtDev sprtDev = RedisSimpleAction.getOrLoad(new SprtDevParam(prodId, param.getDeviceModelCd()),
                new RedisSimpleGetOrLoadHandler<SprtDevParam, SprtDev>() {
                    @Override
                    public SprtDev load(SprtDevParam param, Plandasj redis) {
                        String str = redis.hget(SacRedisKeys.sprtdev(param.getProdId()), param.getDeviceModelCd());
                        if (str == null)
                            return null;

                        JSONObject v = (JSONObject) JSONValue.parse(str);
                        SprtDev sprtDev = new SprtDev();
                        try {
                            sprtDev.setScId((String) v.get("scId"));
                            sprtDev.setOsVer((String) v.get("osVer"));
                            sprtDev.setVer((String) v.get("ver"));
                            sprtDev.setVerCd(((Long)v.get("verCd")).intValue());
                        } catch (RuntimeException e) {
                            throw new StorePlatformException("SAC_DSP_9999", e);
                        }

                        return sprtDev;

                    }

                    @Override
                    public void store(SprtDevParam param, SprtDev value, Plandasj redis) {
                        JSONObject o = new JSONObject();
                        o.put("scId", value.getScId());
                        o.put("osVer", value.getOsVer());
                        o.put("ver", value.getVer());
                        o.put("verCd", new Integer(value.getVerCd()));

                        redis.hset(SacRedisKeys.sprtdev(param.getProdId()), param.getDeviceModelCd(), o.toJSONString());

                    }

                    @Override
                    public SprtDev makeValue(SprtDevParam param) {
                        Map<String, Object> req = new HashMap<String, Object>();
                        req.put("prodId", param.getProdId());
                        req.put("deviceModelCd", param.getDeviceModelCd());
                        commonDAO.queryForObject("OtherAppVersion.getSprtDev", req, SprtDev.class);

                        return commonDAO.queryForObject("OtherAppVersion.getSprtDev", req, SprtDev.class);
                    }
                });

        if (sprtDev == null)
            return null;

        // OS 프로비저닝
        HashSet<String> verSet = new HashSet<String>(Arrays.asList(sprtDev.getOsVer().split(",")));
        if(!verSet.contains(osVer))
            return null;

        // 통계처리
        /*
        final Plandasj plandasj = connectionFactory.getConnectionPool().getClient();
        plandasj.hincrBy("product:version:stats", prodId + ":" + param.getDeviceModelCd() + ":" + osVer, 1);
        */

        return new VersionInfo(prodId, sprtDev.getVerCd(), sprtDev.getVer());
    }

    ////////// VO Definition //////////
    private class SprtDevParam {
        private String prodId;
        private String deviceModelCd;

        public SprtDevParam(String prodId, String deviceModelCd) {
            this.prodId = prodId;
            this.deviceModelCd = deviceModelCd;
        }

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public String getDeviceModelCd() {
            return deviceModelCd;
        }

        public void setDeviceModelCd(String deviceModelCd) {
            this.deviceModelCd = deviceModelCd;
        }
    }

    public static class SprtDev {
        private String scId;
        private String osVer;
        private String ver;
        private Integer verCd;

        public String getScId() {
            return scId;
        }

        public void setScId(String scId) {
            this.scId = scId;
        }

        public String getOsVer() {
            return osVer;
        }

        public void setOsVer(String osVer) {
            this.osVer = osVer;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public Integer getVerCd() {
            return verCd;
        }

        public void setVerCd(Integer verCd) {
            this.verCd = verCd;
        }
    }

}
