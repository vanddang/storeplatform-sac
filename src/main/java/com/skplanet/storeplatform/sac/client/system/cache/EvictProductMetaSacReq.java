/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.system.cache;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * EvictProductMetaSacReq
 * </p>
 * Updated on : 2014. 06. 13 Updated by : 정희원, SK 플래닛.
 */
public class EvictProductMetaSacReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    String prodType;

    @NotEmpty
    String prodId;

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
