/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * 부모 상품 정보
 * </p>
 * Updated on : 2014. 02. 18 Updated by : 정희원, SK 플래닛.
 */
public class ParentAppInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String parentProdId;
    private String parentStatus;

    public String getParentProdId() {
        return parentProdId;
    }

    public void setParentProdId(String parentProdId) {
        this.parentProdId = parentProdId;
    }

    public String getParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(String parentStatus) {
        this.parentStatus = parentStatus;
    }
}
