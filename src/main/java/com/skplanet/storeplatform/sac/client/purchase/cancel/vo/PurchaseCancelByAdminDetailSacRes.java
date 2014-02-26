/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 취소(사용자) 응답 상세 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelByAdminDetailSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String resultCd;
	private String resultMsg;

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
	 * @return the resultCd
	 */
	public String getResultCd() {
		return this.resultCd;
	}

	/**
	 * @param resultCd
	 *            the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return this.resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
