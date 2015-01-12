/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * PanelItem
 * </p>
 * Updated on : 2014. 10. 10 Updated by : 정희원, SK 플래닛.
 */
public class PanelItem extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String panelId;

    private Integer panelLevel;

    private String panelDesc;

    private Integer maxDpCntCard;

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public Integer getPanelLevel() {
        return panelLevel;
    }

    public void setPanelLevel(Integer panelLevel) {
        this.panelLevel = panelLevel;
    }

    public String getPanelDesc() {
        return panelDesc;
    }

    public void setPanelDesc(String panelDesc) {
        this.panelDesc = panelDesc;
    }

    public Integer getMaxDpCntCard() {
        return maxDpCntCard;
    }

    public void setMaxDpCntCard(Integer maxDpCntCard) {
        this.maxDpCntCard = maxDpCntCard;
    }
}
