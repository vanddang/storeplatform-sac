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

import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;

/**
 * <p>
 * 카드 목록 작성에 필요한 파라메터 및 상태값 객체
 * </p>
 * Updated on : 2014. 11. 11 Updated by : 정희원, SK 플래닛.
 */
public class CardListGeneratorContext {

    private String tenantId;
    private String langCd;
    private String userKey;
    private String reqPanelId;
    private SegmentInfo segmentInfo;
    private PreferredCategoryInfo preferredCategoryInfo;
    private boolean disableCardLimit;

    public CardListGeneratorContext(String tenantId, String langCd, String reqPanelId, String userKey, SegmentInfo segmentInfo, PreferredCategoryInfo preferredCategoryInfo, boolean disableCardLimit) {
        this.tenantId = tenantId;
        this.langCd = langCd;
        this.reqPanelId = reqPanelId;
        this.userKey = userKey;
        this.segmentInfo = segmentInfo;
        this.preferredCategoryInfo = preferredCategoryInfo;
        this.disableCardLimit = disableCardLimit;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public String getReqPanelId() {
        return reqPanelId;
    }

    public SegmentInfo getSegmentInfo() {
        return segmentInfo;
    }

    public PreferredCategoryInfo getPreferredCategoryInfo() {
        return preferredCategoryInfo;
    }

    public boolean isDisableCardLimit() {
        return disableCardLimit;
    }

    public String getUserKey() {
        return userKey;
    }
}
