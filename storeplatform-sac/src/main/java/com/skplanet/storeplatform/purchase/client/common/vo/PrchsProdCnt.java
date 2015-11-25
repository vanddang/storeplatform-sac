/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매 상품 건수 집계
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public class PrchsProdCnt extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 ID
	private String useUserKey; // 보유자 사용자 Key
	private String useDeviceKey; // 보유자 디바이스 Key
	private String prchsId; // 구매 ID
	private String prchsClas; // 구매 요청 경로
	private String prodId; // 상품 ID
	private double prodAmt; // 상품 가격
	private String prodGrpCd; // 상품 분류 코드
	private String statusCd; // 구매 상태 코드
	private int prodQty; // 상품 수량
	private String prchsDt; // 구매일
	private String sprcProdYn; // 특가 상품 여부
	private String cntProcStatus; // 건수 처리 상태 (N-미처리, R-처리예약, S-처리성공, F-처리실패)
	private String regId;
	private String updId;

	private String useFixrateProdId;

	/**
	 * @return the useFixrateProdId
	 */
	public String getUseFixrateProdId() {
		return this.useFixrateProdId;
	}

	/**
	 * @param useFixrateProdId
	 *            the useFixrateProdId to set
	 */
	public void setUseFixrateProdId(String useFixrateProdId) {
		this.useFixrateProdId = useFixrateProdId;
	}

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
	 * @return the useUserKey
	 */
	public String getUseUserKey() {
		return this.useUserKey;
	}

	/**
	 * @param useUserKey
	 *            the useUserKey to set
	 */
	public void setUseUserKey(String useUserKey) {
		this.useUserKey = useUserKey;
	}

	/**
	 * @return the useDeviceKey
	 */
	public String getUseDeviceKey() {
		return this.useDeviceKey;
	}

	/**
	 * @param useDeviceKey
	 *            the useDeviceKey to set
	 */
	public void setUseDeviceKey(String useDeviceKey) {
		this.useDeviceKey = useDeviceKey;
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
	 * @return the prchsClas
	 */
	public String getPrchsClas() {
		return this.prchsClas;
	}

	/**
	 * @param prchsClas
	 *            the prchsClas to set
	 */
	public void setPrchsClas(String prchsClas) {
		this.prchsClas = prchsClas;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodAmt
	 */
	public double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodGrpCd
	 */
	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	/**
	 * @param prodGrpCd
	 *            the prodGrpCd to set
	 */
	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
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
	 * @return the prodQty
	 */
	public int getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
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
	 * @return the sprcProdYn
	 */
	public String getSprcProdYn() {
		return this.sprcProdYn;
	}

	/**
	 * @param sprcProdYn
	 *            the sprcProdYn to set
	 */
	public void setSprcProdYn(String sprcProdYn) {
		this.sprcProdYn = sprcProdYn;
	}

	/**
	 * @return the cntProcStatus
	 */
	public String getCntProcStatus() {
		return this.cntProcStatus;
	}

	/**
	 * @param cntProcStatus
	 *            the cntProcStatus to set
	 */
	public void setCntProcStatus(String cntProcStatus) {
		this.cntProcStatus = cntProcStatus;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

}
