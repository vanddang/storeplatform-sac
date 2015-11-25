/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache;

import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;

import static com.skplanet.storeplatform.sac.common.support.redis.ObjectHashGenerator.*;

/**
 * <p>
 * SacRedisKeys - SAC에서 사용하는 Redis 키들을 상수로 다룬다.
 * 키 프리픽스를 메소드 이름으로 사용한다.
 * </p>
 * Updated on : 2015. 05. 13 Updated by : 정희원, SK 플래닛.
 */
public class SacRedisKeys {

    private static final String PREFIX_PKG_2_PROD = "pkg2prod:";
    private static final String PREFIX_PKGS_IN_PROD = "pkgsInProd:";
    private static final String PREFIX_SPRT_DEV = "sprtdev:";
    private static final String PREFIX_PRODUCT_BASE = hash(ProductBaseInfo.class) + ":prodBase:";
    private static final String SET_PRODUCT_BASE = hash(ProductBaseInfo.class) + ":set:prodBase";

    private static final String SET_PROMOEVENT = hash(PromotionEvent.class) + ":set:promoEvent";
    private static final String PREFIX_PROMOEVENT = hash(PromotionEvent.class) + ":promoEvent:";
    private static final String PREFIX_LIVE_PROMOEVENT = hash(PromotionEvent.class) + ":livePromoEvent";
    private static final String PREFIX_TARGET_USERS = hash(PromotionEvent.class) + ":set:targetUser:";

    private static final String LIVE_PROMO_EVENT_END_LOG = "livePromoEventEndLog";
    private static final String LIVE_PROMO_EVENT_TRANSITION = "livePromoEventTransition";

    public static String pkg2prod(String pkgNm) {
        return PREFIX_PKG_2_PROD + pkgNm;
    }

    public static String pkgsInProd(String prodId) {
        return PREFIX_PKGS_IN_PROD + prodId;
    }

    public static String sprtdev(String prodId) {
        return PREFIX_SPRT_DEV + prodId;
    }

    public static String prodBase(String prodId) {
        return PREFIX_PRODUCT_BASE + prodId;
    }

    public static String prodBaseSet() {
        return SET_PRODUCT_BASE;
    }

    public static String promoEvent(String tenantId, String key) {
        return PREFIX_PROMOEVENT + tenantId + ":" + key;
    }

    public static String promoEvent(String key) {
        return PREFIX_PROMOEVENT + key;
    }

    public static String promoEventSet() {
        return SET_PROMOEVENT;
    }

    public static String livePromoEvent() {
        return PREFIX_LIVE_PROMOEVENT;
    }

    public static String livePromoEventEndLog() {
        return LIVE_PROMO_EVENT_END_LOG;
    }

    public static String livePromoEventTransition() {
        return LIVE_PROMO_EVENT_TRANSITION;
    }

    public static String targetUserSet(Integer promId) {
        return PREFIX_TARGET_USERS + promId;
    }

}
