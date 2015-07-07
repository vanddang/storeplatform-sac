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

import com.google.common.base.Strings;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
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

        return commonDAO.queryForObject("CachedExtraInfoManager.getUpdateProductInfo", updateTargetMap, UpdateProduct.class);
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
                        redis.expire(key, 60 * 60 * 12);

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

    @Override
/*
    public ProductBaseInfo getProductBaseInfo(GetProductBaseInfoParam param) {
        return RedisSimpleAction.getOrLoad(param.getProdId(),
                new RedisSimpleGetOrLoadHandler<String, ProductBaseInfo>() {
                    @Override
                    public ProductBaseInfo load(String prodId, Plandasj redis) {
                        Map<String, String> data = redis.hgetAll(SacRedisKeys.prodBase(prodId));
                        if(data.isEmpty())
                            return null;

                        ProductBaseInfo info = new ProductBaseInfo();
                        info.setTopMenuId(data.get("topMenuId"));
                        info.setChnlId(data.get("chnlId"));
                        info.setMetaClsfCd(data.get("metaClsfCd"));
                        info.setSvcGrpCd(data.get("svcGrpCd"));
                        info.setSvcTpCd(data.get("svcTpCd"));
                        info.setContentsTypeCd(data.get("contentsTypeCd"));
                        info.setPartParentClsfCd(data.get("partParentClsfCd"));
                        info.setMenuId(data.get("menuId"));

                        return info;
                    }

                    @Override
                    public void store(String prodId, ProductBaseInfo value, Plandasj redis) {
                        String key = SacRedisKeys.prodBase(prodId);
                        redis.hset(key, "svcGrpCd", value.getSvcGrpCd());
                        redis.hset(key, "contentsTypeCd", value.getContentsTypeCd());
                        redis.hset(key, "topMenuId", value.getTopMenuId());
                        redis.hset(key, "chnlId", value.getChnlId());

                        if(!Strings.isNullOrEmpty(value.getSvcTpCd()))
                            redis.hset(key, "svcTpCd", value.getSvcTpCd());
                        if(!Strings.isNullOrEmpty(value.getMetaClsfCd()))
                            redis.hset(key, "metaClsfCd", value.getMetaClsfCd());
                        if(!Strings.isNullOrEmpty(value.getPartParentClsfCd()))
                            redis.hset(key, "partParentClsfCd", value.getPartParentClsfCd());
                        if(!Strings.isNullOrEmpty(value.getMenuId()))   // FIXME 제거 대상일수 있다.
                            redis.hset(key, "menuId", value.getMenuId());
                    }

                    @Override
                    public ProductBaseInfo makeValue(String prodId) {
                        HashMap<String, Object> req = new HashMap<String, Object>();
                        req.put("prodId", prodId);
                        return commonDAO.queryForObject("CachedExtraInfoManager.getProductBaseInfo", req, ProductBaseInfo.class);
                    }
                });
    }
*/

}
