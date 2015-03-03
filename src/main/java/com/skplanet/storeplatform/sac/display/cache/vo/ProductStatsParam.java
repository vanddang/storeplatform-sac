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

import java.io.Serializable;

/**
 * <p>
 * 상품 통계
 * </p>
 * Updated on : 2015. 02. 25 Updated by : 정희원, SK 플래닛.
 */
public class ProductStatsParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodId;

    public ProductStatsParam() {}

    public ProductStatsParam(String prodId) {
        this.prodId = prodId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getCacheKey() {
        return prodId;
    }
}
