/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.redisdata;

import com.skplanet.plandasj.Plandasj;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.redis.AbstractRedisDataHandler;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.cache.vo.SupportDeviceParam;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SupportDeviceHandlerImpl
 * </p>
 * Updated on : 2015. 11. 05 Updated by : 정희원, SK 플래닛.
 */
public class SupportDeviceHandlerImpl extends AbstractRedisDataHandler<SupportDeviceParam, SupportDevice> {

    @Override
    public Class getResultTypeClass() {
        return SupportDevice.class;
    }

    @Override
    public SupportDevice load(SupportDeviceParam key, Plandasj redis) {
        String str = redis.hget(SacRedisKeys.sprtdev(key.getProdId()), key.getDeviceModelCd());
        if (str == null)
            return null;

        JSONObject v = (JSONObject) JSONValue.parse(str);
        SupportDevice sprtDev = new SupportDevice();
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
    public void store(SupportDeviceParam key, SupportDevice value, Plandasj redis) {
        JSONObject o = new JSONObject();
        o.put("scId", value.getScId());
        o.put("osVer", value.getOsVer());
        o.put("ver", value.getVer());
        o.put("verCd", value.getVerCd());

        redis.hset(SacRedisKeys.sprtdev(key.getProdId()), key.getDeviceModelCd(), o.toJSONString());
    }

    @Override
    public SupportDevice makeValue(SupportDeviceParam key, CommonDAO commonDAO) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", key.getProdId());
        req.put("deviceModelCd", key.getDeviceModelCd());

        return commonDAO.queryForObject("OtherAppVersion.getSprtDev", req, SupportDevice.class);

    }

    @Override
    public void evict(SupportDeviceParam key, Plandasj redis) {

    }
}
