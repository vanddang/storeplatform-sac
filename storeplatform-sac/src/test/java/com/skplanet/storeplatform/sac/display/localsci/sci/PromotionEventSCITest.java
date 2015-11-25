/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PromotionEventSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * <p>
 * PromotionEventSCITest
 * </p>
 * Updated on : 2015. 08. 19 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PromotionEventSCITest {

    @Autowired
    private PromotionEventSCI promotionEventSCI;

    @Test
    public void test01() {
        PromotionEventRes event = promotionEventSCI.getPromotionEvent(new PromotionEventReq("S01", Arrays.asList(995)));
        assert event.getPromotionEventMap().get(995).getPrivateAcmlLimit() == 100;
    }
}
