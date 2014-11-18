/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <p>
 * CardListInPanelReq
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
public class CardListInPanelReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    @NotEmpty @Pattern(regexp = "PN\\d{5}(\\d{3})?", message = "2,3Depth panelId를 입력해야 합니다.")
    private String panelId;

    @Valid
    private SegmentReq segment;

    private String userKey;

    private List<PreferredCategoryReq> preferredCategoryList;

    @Pattern(regexp = "Y|N")
    private String disableCardLimit = "N";

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public SegmentReq getSegment() {
        return segment;
    }

    public void setSegment(SegmentReq segment) {
        this.segment = segment;
    }

    public List<PreferredCategoryReq> getPreferredCategoryList() {
        return preferredCategoryList;
    }

    public void setPreferredCategoryList(List<PreferredCategoryReq> preferredCategoryList) {
        this.preferredCategoryList = preferredCategoryList;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDisableCardLimit() {
        return disableCardLimit;
    }

    public void setDisableCardLimit(String disableCardLimit) {
        this.disableCardLimit = disableCardLimit;
    }
}
