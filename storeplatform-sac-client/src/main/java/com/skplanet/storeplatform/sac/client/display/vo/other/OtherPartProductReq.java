/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 25 Updated by : 정희원, SK 플래닛.
 */
public class OtherPartProductReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String aid;

    private String partProdId;

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

	public String getPartProdId() {
		return this.partProdId;
	}

	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

}
