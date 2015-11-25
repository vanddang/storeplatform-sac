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
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.redis.AbstractRedisDataHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.PkgToAppInfo;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * PkgToAppInfoHandlerImpl
 * </p>
 * Updated on : 2015. 11. 05 Updated by : 정희원, SK 플래닛.
 */
public class PkgToAppInfoHandlerImpl extends AbstractRedisDataHandler<String, PkgToAppInfo> {

    @Override
    public Class getResultTypeClass() {
        return PkgToAppInfo.class;
    }

    @Override
    public PkgToAppInfo load(String key, Plandasj redis) {
        String v = redis.get(SacRedisKeys.pkg2prod(key));
        if(v == null)
            return null;

        return new PkgToAppInfo(v);
    }

    @Override
    public void store(String key, PkgToAppInfo value, Plandasj redis) {
        String _key = SacRedisKeys.pkg2prod(key);
        redis.set(_key, value.getProdId());
        redis.expire(key, 60 * 60 * 12);

        redis.sadd(SacRedisKeys.pkgsInProd(_key), key);
    }

    @Override
    public PkgToAppInfo makeValue(String key, CommonDAO commonDAO) {
        DisplayCryptUtils.SacSha1Mac sha1Mac = DisplayCryptUtils.createSha1Mac();
        String hashedPkgNm = sha1Mac.hashPkgNm(key);
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantIds", ServicePropertyManager.getSupportTenantList());
        req.put("hashedPkgNm", hashedPkgNm);
        String prodId = commonDAO.queryForObject("CachedExtraInfoManager.getProdIdByPkgNm", req, String.class);
        return new PkgToAppInfo(prodId);
    }

    @Override
    public void evict(String key, Plandasj redis) {
        redis.del(SacRedisKeys.pkg2prod(key));
    }
}
