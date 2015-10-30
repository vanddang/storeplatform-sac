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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class GetPrchsProdCntScRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<PrchsProdCnt> prchsProdCntList;

	/**
	 * @return the prchsProdCntList
	 */
	public List<PrchsProdCnt> getPrchsProdCntList() {
		return this.prchsProdCntList;
	}

	/**
	 * @param prchsProdCntList
	 *            the prchsProdCntList to set
	 */
	public void setPrchsProdCntList(List<PrchsProdCnt> prchsProdCntList) {
		this.prchsProdCntList = prchsProdCntList;
	}

}
