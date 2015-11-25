/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class UpdatePrchsProdCntProcStatusScRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Integer resultCnt;

	/**
	 * @return the resultCnt
	 */
	public Integer getResultCnt() {
		return this.resultCnt;
	}

	/**
	 * @param resultCnt
	 *            the resultCnt to set
	 */
	public void setResultCnt(Integer resultCnt) {
		this.resultCnt = resultCnt;
	}

}
