/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;

/**
 * 
 * 결제 이력 생성을 위한 정보 VO
 * 
 * Updated on : 2014. 3. 3. Updated by : 이승택, nTels.
 */
public class CreatePaymentSacInfo extends CommonInfo {
	private static final long serialVersionUID = 201403031L;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String payUserKey; // 결제자 내부회원NO
	private String payDeviceKey; // 결제자 내부디바이스ID
	private String prchsId; // 구매 ID
	private String prchsDt; // 구매일시
	private String statusCd; // 구매상태 (결제상태)
	private double totAmt; // 결제 총 금액
	private List<PaymentInfo> paymentInfoList; // 결제수단정보 리스트

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the payUserKey
	 */
	public String getPayUserKey() {
		return this.payUserKey;
	}

	/**
	 * @param payUserKey
	 *            the payUserKey to set
	 */
	public void setPayUserKey(String payUserKey) {
		this.payUserKey = payUserKey;
	}

	/**
	 * @return the payDeviceKey
	 */
	public String getPayDeviceKey() {
		return this.payDeviceKey;
	}

	/**
	 * @param payDeviceKey
	 *            the payDeviceKey to set
	 */
	public void setPayDeviceKey(String payDeviceKey) {
		this.payDeviceKey = payDeviceKey;
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
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
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
