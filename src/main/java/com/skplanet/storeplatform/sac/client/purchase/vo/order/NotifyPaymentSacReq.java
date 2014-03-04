/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제 결과 Noti VO
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class NotifyPaymentSacReq extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	private String resultCd; // 결제처리결과 코드
	private String resultMsg; // 결제처리결과 메시지
	private String prchsId; // 구매 ID
	private double totAmt; // 결제 총 금액
	private List<PaymentInfo> paymentInfoList; // 결제수단정보 리스트

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
	 * @return the totAmt
	 */
	public double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList() {
		return this.paymentInfoList;
	}

	/**
	 * @param paymentInfoList
	 *            the paymentInfoList to set
	 */
	public void setPaymentInfoList(List<PaymentInfo> paymentInfoList) {
		this.paymentInfoList = paymentInfoList;
	}

}
