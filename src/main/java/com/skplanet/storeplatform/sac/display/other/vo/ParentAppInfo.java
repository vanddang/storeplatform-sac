/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * 부모 상품 정보
 * </p>
 * Updated on : 2014. 02. 18 Updated by : 정희원, SK 플래닛.
 */
public class ParentAppInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String parentChannelId;
    private String parentStatus;
    private String parentName;
    private String partName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getParentChannelId() {
        return parentChannelId;
    }

    public void setParentChannelId(String parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public String getParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(String parentStatus) {
        this.parentStatus = parentStatus;
    }
}
