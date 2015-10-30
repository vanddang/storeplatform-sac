/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventRes;

/**
 * <p>
 * PromotionEventSCI
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
@SCI
public interface PromotionEventSCI {

    /**
     * 프로모션 이벤트 정보를 조회한다.
     * @param req
     * @return
     */
    PromotionEventRes getPromotionEvent(PromotionEventReq req);
}
