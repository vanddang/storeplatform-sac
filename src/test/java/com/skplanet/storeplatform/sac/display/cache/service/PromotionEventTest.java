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

import com.google.common.base.Objects;
import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.SyncPromotionEventResult;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
//import static org.mockito.Mockito.*;

/**
 * <p>
 * PromotionEventTest
 * </p>
 * Updated on : 2015. 08. 10 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
public class PromotionEventTest {

    @Autowired
    private PromotionEventSyncService promotionEventSyncService;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    private static final Logger logger = LoggerFactory.getLogger(PromotionEventTest.class);

    @Before
    public void init() throws Exception {
        //when(PromotionEventSyncDaoImpl.class, "getRawEventList", "S01", Lists.newArrayList(), false).thenReturn(getRawPromotionEvent());
        /*
         Mocking 실패.
         - 위의 구문은 static method에 적용되는것 같다
         - 테스트 대상의 메소드에서 실행하는 다른 빈의 메소드에 Mock을 걸어야 하는데 그게 안된다.

         => DB에 테스트 데이터를 넣고 실행
          */
    }

    @Test
    public void test01() throws Exception {

        /* 사전조건
          - 이어지는 이벤트 2건을 입력
        */

        // 프로모션 이벤트 동기화
        SyncPromotionEventResult result = promotionEventSyncService.syncPromotionEvent("S01", "DP99");
        assert result.getUpdtCnt() > 0;

        // 이벤트 조회 테스트
        DateTime dateTime = DATE1.minusMillis(500);
        PromotionEvent promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent == null;

        dateTime = DATE1;
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent != null;
        assert promotionEvent.getPromId() == 1381;

        dateTime = DATE2.minusMillis(500);
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent != null;
        assert promotionEvent.getPromId() == 1381;

        dateTime = DATE2;
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent != null;
        assert promotionEvent.getPromId() == 1381;

        dateTime = DATE2.plusMillis(500);
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent == null;

        dateTime = DATE3;
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent != null;
        assert promotionEvent.getPromId() == 1382;

        dateTime = DATE4;
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent != null;
        assert promotionEvent.getPromId() == 1382;

        dateTime = dateTime.plusMillis(500);
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent == null;

        dateTime = dateTime.plusMillis(500);
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent == null;

        dateTime = dateTime.plusMillis(500);
        promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam("S01", "DP99", "XXXXX", dateTime.toDate()));
        assert promotionEvent == null;
    }

    private DateTime DATE1 = DateTime.parse("2015-08-12");
    private DateTime DATE2 = DateTime.parse("2015-08-13T23:59:59");
    private DateTime DATE3 = DateTime.parse("2015-08-14");
    private DateTime DATE4 = DateTime.parse("2015-08-15T23:59:59");

    @Test
    public void test02() {
        DateTime dateTime = DateTime.parse("2015-08-11T23:59:59");

        int transition = 0;
        boolean v = true;
        while (dateTime.isBefore(DATE3)) {
            Date date = dateTime.toDate();

            boolean newV = !DATE1.isAfter(date.getTime()) && !DATE2.isBefore(date.getTime());
            if(newV != v) {
                ++transition;
                v = newV;
                logger.info("[{}]{} - {}", transition, dateTime, v);
            }

            dateTime = dateTime.plusMillis(500);
        }
    }



}
