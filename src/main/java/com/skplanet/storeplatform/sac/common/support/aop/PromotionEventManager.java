/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.aop;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 * PromotionEventManager
 * PromotionEvenntProcessor 작동 여부를 관리한다.
 * </p>
 * Updated on : 2015. 08. 20 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventManager {

    public static final String DISABLE_PROMOTION_EVENT = "_DISABLE_PROMOTION_EVENT";

    public static void disableProcessEvent() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(DISABLE_PROMOTION_EVENT, Boolean.TRUE, RequestAttributes.SCOPE_REQUEST);

    }

    static boolean isRun() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null)
            return true;

        Object v = requestAttributes.getAttribute(DISABLE_PROMOTION_EVENT, RequestAttributes.SCOPE_REQUEST);
        return v == null;
    }
}
