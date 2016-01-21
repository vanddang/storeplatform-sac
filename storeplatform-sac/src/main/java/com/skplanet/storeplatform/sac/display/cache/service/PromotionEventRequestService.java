package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;

public interface PromotionEventRequestService {
    PromotionEvent getPromotionEvent(GetPromotionEventParam param);
}
