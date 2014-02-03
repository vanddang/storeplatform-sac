/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.vo;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;

/**
 * 쇼핑 쿠폰 사용여부 요청 Parameter VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class UseStatusSacParam extends PurchaseCommonSacParam {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String cpPublishCd;

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the cpPublishCd
	 */
	public String getCpPublishCd() {
		return this.cpPublishCd;
	}

	/**
	 * @param cpPublishCd
	 *            the cpPublishCd to set
	 */
	public void setCpPublishCd(String cpPublishCd) {
		this.cpPublishCd = cpPublishCd;
	}

}
