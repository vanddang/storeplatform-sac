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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * EvictProductMetaSacRes
 * </p>
 * Updated on : 2014. 06. 13 Updated by : 정희원, SK 플래닛.
 */
public class EvictProductMetaSacRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String prodType;

    
    private List<String> prodId;

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

	/**
	 * @return the prodId
	 */
	public List<String> getProdId() {
		return prodId;
	}

	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(List<String> prodId) {
		this.prodId = prodId;
	}

 
}
