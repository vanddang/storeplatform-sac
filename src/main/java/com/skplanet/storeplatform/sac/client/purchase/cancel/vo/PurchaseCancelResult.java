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
 * 구매 취소 응답 결과 상세 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelResult extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String prchsCancelResultCd;
	private String prchsCancelResultMsg;

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
	 * @return the prchsCancelResultCd
	 */
	public String getPrchsCancelResultCd() {
		return this.prchsCancelResultCd;
	}

	/**
	 * @param prchsCancelResultCd
	 *            the prchsCancelResultCd to set
	 */
	public void setPrchsCancelResultCd(String prchsCancelResultCd) {
		this.prchsCancelResultCd = prchsCancelResultCd;
	}

	/**
	 * @return the prchsCancelResultMsg
	 */
	public String getPrchsCancelResultMsg() {
		return this.prchsCancelResultMsg;
	}

	/**
	 * @param prchsCancelResultMsg
	 *            the prchsCancelResultMsg to set
	 */
	public void setPrchsCancelResultMsg(String prchsCancelResultMsg) {
		this.prchsCancelResultMsg = prchsCancelResultMsg;
	}

}
