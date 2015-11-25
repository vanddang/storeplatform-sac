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

import com.skplanet.storeplatform.sac.common.support.redis.ObjectHashGenerator;
import com.skplanet.storeplatform.sac.display.cache.service.PromotionEventConverter;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import org.junit.Test;

import java.util.Date;

/**
 * <p>
 * PromotionEventDataTest
 * </p>
 * Updated on : 2015. 08. 18 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventDataTest {

    @Test
    public void test1() {
        PromotionEvent event = new PromotionEvent();
        event.setTargetId("1");
        event.setStartDt(new Date());
        event.setEndDt(new Date());
        event.setPromId(10);

        byte[] data = PromotionEventConverter.convert(event);
        PromotionEvent event2 = PromotionEventConverter.convert(data);
        assert event.getTargetId().equals(event2.getTargetId());
        assert event.getStartDt().equals(event2.getStartDt());
        assert event.getPromId().equals(event2.getPromId());
        System.out.println(ObjectHashGenerator.hash(PromotionEvent.class));
    }
}
