/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.featureswitch;

import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 * SacFeatureSwitchComponentImpl
 * </p>
 * Updated on : 2015. 08. 12 Updated by : 정희원, SK 플래닛.
 */
@Component
public class SacFeatureSwitchComponentImpl implements SacFeatureSwitchManager, SacFeatureSwitchAccessor {

    private static final Logger logger = LoggerFactory.getLogger(SacFeatureSwitchComponentImpl.class);
    private static final byte[] SAC_FEATURE_REDIS_KEY = "sacFeature".getBytes();
    private static final String CTX_ATTR_KEY = "SAC_FEATURESW_VALUE";

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    @Override
    public boolean get(int featureKey) {

        if(featureKey < 0x01)
            throw new IllegalArgumentException("정의되지 않은 키입니다.");

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        boolean isDebug;
        byte[] swValByte;

        if(requestAttributes != null) {
            Object valObj = requestAttributes.getAttribute(CTX_ATTR_KEY, RequestAttributes.SCOPE_REQUEST);
            if (valObj != null) {
                swValByte = ((FeatureSwitchValue)valObj).getValue();
                isDebug = returnValue(swValByte, FeatureKey.FEATURE_SWITCH_DEBUG);

                if (isDebug) {
                    logger.info("RequestAttribute에서 조회하여 응답함");
                }

                return returnValue(swValByte, featureKey);
            }
        }

        if(connectionFactory == null) {
            logger.warn("[FeatureSwitch] 저장소 연결에 실패하여 기본값(false)을 응답합니다. key={}", featureKey);
            return false;
        }

        final Plandasj c = connectionFactory.getConnectionPool().getClient();
        swValByte = c.get(SAC_FEATURE_REDIS_KEY);
        isDebug = returnValue(swValByte, FeatureKey.FEATURE_SWITCH_DEBUG);

        if (requestAttributes != null) {

            if(isDebug)
                logger.info("RequestAttribute에 스위치 값들을 저장함");

            requestAttributes.setAttribute(CTX_ATTR_KEY, new FeatureSwitchValue(swValByte), RequestAttributes.SCOPE_REQUEST);
        }

        return returnValue(swValByte, featureKey);
    }

    private boolean returnValue(byte[] swVal, int featureKey) {
        if(swVal == null || swVal.length == 0 || featureKey < 0x00)
            return false;

        int offset = featureKey / 8;
        int sh = featureKey % 8;
        byte mask = (byte)(0x80 >> sh);

        return offset <= swVal.length && ((swVal[offset] & mask) != 0);
    }

    @Override
    public int refreshValues() {
        throw new IllegalStateException("아직 구현되지 않았습니다.");
    }
}
