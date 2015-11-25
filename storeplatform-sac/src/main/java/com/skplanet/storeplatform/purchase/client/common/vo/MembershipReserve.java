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
 * 멤버쉽 적립 예약 CommonVo
 *
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
public class MembershipReserve extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트ID
	private String typeCd; // 마일리지 타입
	private String prchsId; // 구매ID
	private String statusCd; // 구매상태코드
	private String insdUsermbrNo; // 내부회원번호
	private String insdDeviceId; // 내부디바이스ID
	private String targetDt; // 대상일시 (구매요청일시 또는 구매취소일시)
	private String saveDt; // 적립예정일
	private String prchsDt; // 구매일시
	private String cancelDt; // 구매취소일시
	private String currencyCd; // 통화코드
	private Double totAmt; // 구매총금액
	private Integer prchsProdCnt; // 구매상품건수
	private String prodId; // 상품ID
	private Double prodAmt; // 상품금액
	private Integer prodQty; // 동일상품구매수량 (쇼핑용)
	private String userGrdCd; // 회원등급코드
	private Integer prodSaveRate; // 상품적립율
	private Double targetPaymentAmt; // 적립대상결제금액
	private Double saveExpectAmt; // 적립예정금액
	private Double saveResultAmt; // 적립결과금액
	private String prchsReqPathCd; // 적립금요청경로
	private String saveTypeCd; // 처리타입코드
	private String procStatusCd; // 처리상태코드
	private String regId; // 등록ID
	private String regDt; // 등록일시
	private String updId; // 수정ID
	private String updDt; // 수정일시
	private String pointId; // T store Cash ID
	private String prodNm; // 상품명
	private Integer promId; // 이벤트 프로모션 ID
	private Integer payMethodVdtyDt; // 프로모션 적립금 유효일

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
	 * @return the typeCd
	 */
	public String getTypeCd() {
		return this.typeCd;
	}

	/**
	 * @param typeCd
	 *            the typeCd to set
	 */
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
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
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the targetDt
	 */
	public String getTargetDt() {
		return this.targetDt;
	}

	/**
	 * @param targetDt
	 *            the targetDt to set
	 */
	public void setTargetDt(String targetDt) {
		this.targetDt = targetDt;
	}

	/**
	 * @return the saveDt
	 */
	public String getSaveDt() {
		return this.saveDt;
	}

	/**
	 * @param saveDt
	 *            the saveDt to set
	 */
	public void setSaveDt(String saveDt) {
		this.saveDt = saveDt;
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
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return this.cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	/**
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	/**
	 * @param currencyCd
	 *            the currencyCd to set
	 */
	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the prchsProdCnt
	 */
	public Integer getPrchsProdCnt() {
		return this.prchsProdCnt;
	}

	/**
	 * @param prchsProdCnt
	 *            the prchsProdCnt to set
	 */
	public void setPrchsProdCnt(Integer prchsProdCnt) {
		this.prchsProdCnt = prchsProdCnt;
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
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodQty
	 */
	public Integer getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @return the userGrdCd
	 */
	public String getUserGrdCd() {
		return this.userGrdCd;
	}

	/**
	 * @param userGrdCd
	 *            the userGrdCd to set
	 */
	public void setUserGrdCd(String userGrdCd) {
		this.userGrdCd = userGrdCd;
	}

	/**
	 * @return the prodSaveRate
	 */
	public Integer getProdSaveRate() {
		return this.prodSaveRate;
	}

	/**
	 * @param prodSaveRate
	 *            the prodSaveRate to set
	 */
	public void setProdSaveRate(Integer prodSaveRate) {
		this.prodSaveRate = prodSaveRate;
	}

	/**
	 * @return the targetPaymentAmt
	 */
	public Double getTargetPaymentAmt() {
		return this.targetPaymentAmt;
	}

	/**
	 * @param targetPaymentAmt
	 *            the targetPaymentAmt to set
	 */
	public void setTargetPaymentAmt(Double targetPaymentAmt) {
		this.targetPaymentAmt = targetPaymentAmt;
	}

	/**
	 * @return the saveExpectAmt
	 */
	public Double getSaveExpectAmt() {
		return this.saveExpectAmt;
	}

	/**
	 * @param saveExpectAmt
	 *            the saveExpectAmt to set
	 */
	public void setSaveExpectAmt(Double saveExpectAmt) {
		this.saveExpectAmt = saveExpectAmt;
	}

	/**
	 * @return the saveResultAmt
	 */
	public Double getSaveResultAmt() {
		return this.saveResultAmt;
	}

	/**
	 * @param saveResultAmt
	 *            the saveResultAmt to set
	 */
	public void setSaveResultAmt(Double saveResultAmt) {
		this.saveResultAmt = saveResultAmt;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the saveTypeCd
	 */
	public String getSaveTypeCd() {
		return this.saveTypeCd;
	}

	/**
	 * @param saveTypeCd
	 *            the saveTypeCd to set
	 */
	public void setSaveTypeCd(String saveTypeCd) {
		this.saveTypeCd = saveTypeCd;
	}

	/**
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
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
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
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

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the pointId
	 */
	public String getPointId() {
		return this.pointId;
	}

	/**
	 * @param pointId
	 *            the pointId to set
	 */
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * Gets prom id.
	 *
	 * @return the prom id
	 */
	public Integer getPromId() {
		return promId;
	}

	/**
	 * Sets prom id.
	 *
	 * @param promId
	 *            the prom id
	 */
	public void setPromId(Integer promId) {
		this.promId = promId;
	}

	/**
	 * Gets pay method vdty dt.
	 *
	 * @return the pay method vdty dt
	 */
	public Integer getPayMethodVdtyDt() {
		return payMethodVdtyDt;
	}

	/**
	 * Sets pay method vdty dt.
	 *
	 * @param payMethodVdtyDt
	 *            the pay method vdty dt
	 */
	public void setPayMethodVdtyDt(Integer payMethodVdtyDt) {
		this.payMethodVdtyDt = payMethodVdtyDt;
	}
}
