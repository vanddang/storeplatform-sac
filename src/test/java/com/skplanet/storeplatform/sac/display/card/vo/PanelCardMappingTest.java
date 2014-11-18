/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelCardMapping;
import org.junit.Test;

import java.util.Date;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 11. 11 Updated by : 정희원, SK 플래닛.
 */
public class PanelCardMappingTest {

    private Date stdDt = DateUtils.parseDate("20141111");   // 화요일

    @Test
    public void test_expoDt정상() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        assert panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_expoDt실패() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141112");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        assert !panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_요일1() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);
        panCard.setExpoWkdy("0010000");

        assert panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_노출시간1() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 09~10시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101090000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001011000000"));

        assert !panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_노출시간2() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 09~10시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101090000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001011000000"));

        Date stdDt2 = DateUtils.parseDate("20141111090000");
        assert panCard.isVisibleForDate(stdDt2);
    }

    @Test
    public void test_노출시간3() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 22~06시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101220000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010600000"));

        Date stdDt2 = DateUtils.parseDate("20141111090000");
        assert !panCard.isVisibleForDate(stdDt2);
    }

    @Test
    public void test_노출시간4() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 22~06시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101220000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010600000"));

        assert panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_노출시간5() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 00~00시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101000000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010000000"));

        assert panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_노출시간6() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 00~00시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101000000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010600000"));

        assert panCard.isVisibleForDate(stdDt);
    }

    @Test
    public void test_노출시간7() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 00~00시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101220000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010000000"));

        assert panCard.isVisibleForDate(DateUtils.parseDate("20141111235959"));
    }

    @Test
    public void test_노출시간8() {
        Date startDt, endDt;
        startDt = DateUtils.parseDate("20141101");
        endDt = DateUtils.parseDate("99991231");

        PanelCardMapping panCard = new PanelCardMapping();
        panCard.setExpoStartDt(startDt);
        panCard.setExpoEndDt(endDt);

        // 00~00시 노출
        panCard.setDdStartTm(DateUtils.parseDate("19700101220000"));
        panCard.setDdEndTm(DateUtils.parseDate("197001010000000"));

        assert !panCard.isVisibleForDate(stdDt);
    }

}
