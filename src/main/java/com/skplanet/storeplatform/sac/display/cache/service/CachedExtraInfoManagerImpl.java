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

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.support.redis.DistributedLock;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleAction;
import com.skplanet.storeplatform.sac.common.support.redis.RedisSimpleGetOrLoadHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * UpdateProductInfoManagerImpl
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CachedExtraInfoManagerImpl implements CachedExtraInfoManager {

    public static final String LOCK_KEY = "SyncPromotionEvent";
    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    private static final Logger logger = LoggerFactory.getLogger(CachedExtraInfoManagerImpl.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

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
                        info.setCatId(data.get("catId"));

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
                        if(!Strings.isNullOrEmpty(value.getCatId()))
                            redis.hset(key, "catId", value.getCatId());
                    }

                    @Override
                    public ProductBaseInfo makeValue(String prodId) {
                        HashMap<String, Object> req = new HashMap<String, Object>();
                        req.put("prodId", prodId);
                        return commonDAO.queryForObject("CachedExtraInfoManager.getProductBaseInfo", req, ProductBaseInfo.class);
                    }
                });
    }

    @Override
    public int syncPromotionEvent() {

        if(connectionFactory == null)
            return -1;

        Plandasj redis = connectionFactory.getConnectionPool().getClient();

        // 모든 적재된 데이터 제거
        Stopwatch stopwatch = Stopwatch.createStarted();
        Collection<String> promoEventSet = redis.smembers(SacRedisKeys.promoEventSet());
        for (String promoEventKey : promoEventSet) {
            redis.del(SacRedisKeys.promoEvent(promoEventKey));
        }
        redis.del(SacRedisKeys.promoEventSet());
        logger.debug("Exist events are removed - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 데이터 적재
        List<RawPromotionEvent> promEventList = commonDAO.queryForList("CachedExtraInfoManager.getPromotionEventList", null, RawPromotionEvent.class);
        Multimap<String, RawPromotionEvent> eventMap = LinkedHashMultimap.create();
        for (RawPromotionEvent e : promEventList) {
            eventMap.put(e.getTenantId() + ":" + e.getPromTypeValue(), e);
        }
        logger.debug("Fetch events from db - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // 이벤트 등록
        DistributedLock lock = new DistributedLock(LOCK_KEY);
        lock.lock();    // TODO NOTICE callback형태로 처리하는건 어떨까? 문제가 생기면 그냥 빠져나오도록.
        int updtCnt = 0;

        for (Map.Entry<String, Collection<RawPromotionEvent>> events : eventMap.asMap().entrySet()) {

            for (RawPromotionEvent event : events.getValue()) {
                String strEvent = convertRawPromotionEvent2Str(event);
                redis.rpush(SacRedisKeys.promoEvent(events.getKey()),
                        event.getEventKey() + ":" + strEvent);
                ++updtCnt;
            }

            redis.sadd(SacRedisKeys.promoEventSet(), events.getKey());
        }
        logger.debug("Regist events to redis - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        lock.unlock();

        stopwatch.stop();
        logger.debug("Event sync processing time = {}", stopwatch);
        // TODO 정상적으로 등록되었는지 검증. 갯수 카운팅

        return updtCnt;
    }

    private String convertRawPromotionEvent2Str(RawPromotionEvent obj) {

        Object[] v = new Object[]{Integer.toHexString(obj.hashCode()),
                obj.getPromId(),
                obj.getRateGrd1(),
                obj.getRateGrd2(),
                obj.getRateGrd3(),
                obj.getAcmlMethodCd(),
                obj.getAcmlDt()};

        return StringUtils.join(v, " ");
    }

    @Override
    public PromotionEvent getPromotionEvent(GetPromotionEventParam param) {

        if(connectionFactory == null)
            return getPromotionEventFromDb(param);

        Date now = param.getNowDt();
        if(now == null)
            now = new Date(); // TODO DB에서 시간 조회

        PromotionEvent event;

        // TODO prodId -> 2Depth menu -> topMenu 순서로 조회하는데 이것을 한번에 찾도록 개선할 방법이 있는가?
        // Hash에 가장 최근의 데이터를 넣어놓고 만료되면 다음 것을 찾아와 적재하도록 하면?

        Plandasj client = connectionFactory.getConnectionPool().getClient();

        // prodId로 조회
        event = getLivePromotionEvent(client, param.getTenantId(), param.getProdId(), now);

        if(event != null) {
            event.setTargetId(param.getProdId());
            return event;
        }

        // 2Depth menuId or topMenuId로 조회
        event = getLivePromotionEvent(client, param.getTenantId(), param.getMenuId(), now);

        if(event != null) {
            event.setTargetId(param.getMenuId());
            return event;
        }

        if(param.getMenuId().length() == 4)
            return null;

        // topMenuId로 조회
        String topMenuId = param.getMenuId().substring(0, 4);
        event = getLivePromotionEvent(client, param.getTenantId(), topMenuId, now);

        if(event != null) {
            event.setTargetId(topMenuId);
            return event;
        }

        return null;
    }

    private PromotionEvent getLivePromotionEvent(Plandasj redis, String tenantId, String key, Date now) {

        Preconditions.checkNotNull(now);

        String findKey = SacRedisKeys.promoEvent(tenantId, key);
        PromotionEvent event = null;

        List<String> data;
        while(event == null && !(data = redis.lrange(findKey, 0, 0)).isEmpty())
        {
            String str = data.get(0);
            String[] strPart = null;
            Date startDt = null, endDt = null;

            try {
                strPart = StringUtils.split(str, ":");
                Preconditions.checkArgument(strPart.length == 2, "Event message is wrong.");

                String[] datePart = StringUtils.split(strPart[0], "_");
                Preconditions.checkArgument(datePart.length == 2, "Event body date part is wrong.");

                startDt = DATE_FORMAT.parse(datePart[0]);
                endDt = DATE_FORMAT.parse(datePart[1]);
            }
            catch (ParseException e) {
                logger.error("데이터 변환시 오류", e);
                continue;
            }
            catch (RuntimeException e) {
                logger.error("데이터 변환시 오류", e);
                continue;
                // TODO log. 컨버팅 에러시: 재적재? 좀 심플하게 가야는데...
            }

            if(now.after(startDt)) {
                if(now.before(endDt)) {
                    event = convertStr2PromtionEvent(startDt, endDt, strPart[1]);
                }
                else {
                    // 지난 이벤트이면 제거하고 다음 이벤트를 조회한다.
                    Long remCnt = redis.lrem(findKey, 0, str);
                    if (remCnt != 1)
                        logger.debug("다른 프로세스에 의해 이벤트가 정리되었습니다. (data:{})", str);
                }
            }
            else
                break;
        }

        return event;
    }

    private PromotionEvent convertStr2PromtionEvent(Date startDt, Date endDt, String bodyPart) {

        String[] body = StringUtils.split(bodyPart);
        Preconditions.checkArgument(body.length == 7, "Event body is wrong.");

        PromotionEvent event = new PromotionEvent();

        String hashHex;

        try {
            event.setStartDt(startDt);
            event.setEndDt(endDt);

            hashHex = body[0];

            event.setPromId(Integer.parseInt(body[1]));
            event.setRateGrd1(Integer.parseInt(body[2]));
            event.setRateGrd2(Integer.parseInt(body[3]));
            event.setRateGrd3(Integer.parseInt(body[4]));
            event.setAcmlMethodCd(body[5]);
            event.setAcmlDt(body[6]);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }

        if (!hashHex.equals(Integer.toHexString(event.hashCode())))
            throw new RuntimeException("해시 값이 다릅니다.");

        return event;
    }

    @Override
    public Map<String, PromotionEvent> getPromotionEventMap(boolean liveOnly) {
        if(connectionFactory == null)
            return null; // TODO Impl.

        Plandasj redis = connectionFactory.getConnectionPool().getClient();

        return null;
    }

    /**
     * DB 에서 이벤트를 조회한다.
     * @param param
     * @return
     */
    private PromotionEvent getPromotionEventFromDb(GetPromotionEventParam param) {
        throw new RuntimeException("아직 구현되지 않았습니다.");
    }
}
