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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProduct;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProductParam;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * <p>
 * UpdateProductInfoManagerImpl
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CachedExtraInfoManagerImpl implements CachedExtraInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Override
    @Cacheable(value = "sac:display:updateProductInfo:v3", key = "#param.getCacheKey()")
    public UpdateProduct getUpdateProductInfo(UpdateProductParam param) {

        Map<String, Object> updateTargetMap = new HashMap<String, Object>();

        updateTargetMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
        updateTargetMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
        updateTargetMap.put("contentsTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
        updateTargetMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
        updateTargetMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

        updateTargetMap.put("tenantId", param.getTenantId());
        updateTargetMap.put("epsdId", param.getEpsdId());
        updateTargetMap.put("subContentsId", param.getSubContentsId());
        updateTargetMap.put("langCd", param.getLangCd());

        return commonDAO.queryForObject("UpdateProductInfo.getUpdateProductInfo", updateTargetMap, UpdateProduct.class);
    }

    @Override
    public String getProdIdByPkgNm(String pkgNm) {

        return RedisSimpleAction.getOrLoad(pkgNm,
                new RedisSimpleGetOrLoadHandler<String, String>() {
                    @Override
                    public String load(String pkgNm, Plandasj redis) {
                        return redis.get(SacRedisKeys.pkg2prod(pkgNm));
                    }

                    @Override
                    public void store(String pkgNm, String value, Plandasj redis) {
                        String key = SacRedisKeys.pkg2prod(pkgNm);
                        redis.set(key, value);
                        redis.expire(key, 60 * 5);

                        redis.sadd(SacRedisKeys.pkgsInProd(value), pkgNm);
                    }

                    @Override
                    public String makeValue(String pkgNm) {
                        String hashedPkgNm = DisplayCryptUtils.hashPkgNm(pkgNm);
                        Map<String, Object> req = new HashMap<String, Object>();
                        req.put("tenantIds", ServicePropertyManager.getSupportTenantList());
                        req.put("hashedPkgNm", hashedPkgNm);
                        String prodId = commonDAO.queryForObject("CachedExtraInfoManager.getProdIdByPkgNm", req, String.class);

                        return prodId;
                    }
                });
    }

    @Override
    public void evictPkgsInProd(String prodId) {
        Plandasj c = connectionFactory.getConnectionPool().getClient();
        Set<String> pkgs = c.smembers(SacRedisKeys.pkgsInProd(prodId));
        for(String pkg : pkgs) {
            c.del(SacRedisKeys.pkg2prod(pkg));
        }
    }
}
