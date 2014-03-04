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

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;

/**
 * 
 * 구매 상품 (전시Part 에서 넘겨받는 상품Object 기반)
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PurchaseProduct extends PaymentInfo {
	private String bonusCashPoint; // 보너스 캐쉬 지급 Point
	private String bonusCashUsableDayCnt; // 보너스 캐쉬 유효기간(일)
	private String afterAutoPayDt; // 다음 자동 결제일
	private String dwldAvailableDayCnt; // 다운로드 가능기간(일)
	private String usePeriodCnt; // 이용기간(일)
	private String loanPid; // 대여하기 상품 ID
	private String loanAmt; // 대여하기 상품 금액
	private String ownPid; // 소장하기 상품 ID
	private String ownAmt; // 소장하기 상품 금액

	private int prodQty;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String useExprDt; // [비과금 구매요청 시 사용]

	/**
	 * @return the bonusCashPoint
	 */
	public String getBonusCashPoint() {
		return this.bonusCashPoint;
	}

	/**
	 * @param bonusCashPoint
	 *            the bonusCashPoint to set
	 */
	public void setBonusCashPoint(String bonusCashPoint) {
		this.bonusCashPoint = bonusCashPoint;
	}

	/**
	 * @return the bonusCashUsableDayCnt
	 */
	public String getBonusCashUsableDayCnt() {
		return this.bonusCashUsableDayCnt;
	}

	/**
	 * @param bonusCashUsableDayCnt
	 *            the bonusCashUsableDayCnt to set
	 */
	public void setBonusCashUsableDayCnt(String bonusCashUsableDayCnt) {
		this.bonusCashUsableDayCnt = bonusCashUsableDayCnt;
	}

	/**
	 * @return the afterAutoPayDt
	 */
	public String getAfterAutoPayDt() {
		return this.afterAutoPayDt;
	}

	/**
	 * @param afterAutoPayDt
	 *            the afterAutoPayDt to set
	 */
	public void setAfterAutoPayDt(String afterAutoPayDt) {
		this.afterAutoPayDt = afterAutoPayDt;
	}

	/**
	 * @return the dwldAvailableDayCnt
	 */
	public String getDwldAvailableDayCnt() {
		return this.dwldAvailableDayCnt;
	}

	/**
	 * @param dwldAvailableDayCnt
	 *            the dwldAvailableDayCnt to set
	 */
	public void setDwldAvailableDayCnt(String dwldAvailableDayCnt) {
		this.dwldAvailableDayCnt = dwldAvailableDayCnt;
	}

	/**
	 * @return the usePeriodCnt
	 */
	public String getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	/**
	 * @param usePeriodCnt
	 *            the usePeriodCnt to set
	 */
	public void setUsePeriodCnt(String usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
	}

	/**
	 * @return the loanPid
	 */
	public String getLoanPid() {
		return this.loanPid;
	}

	/**
	 * @param loanPid
	 *            the loanPid to set
	 */
	public void setLoanPid(String loanPid) {
		this.loanPid = loanPid;
	}

	/**
	 * @return the loanAmt
	 */
	public String getLoanAmt() {
		return this.loanAmt;
	}

	/**
	 * @param loanAmt
	 *            the loanAmt to set
	 */
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}

	/**
	 * @return the ownPid
	 */
	public String getOwnPid() {
		return this.ownPid;
	}

	/**
	 * @param ownPid
	 *            the ownPid to set
	 */
	public void setOwnPid(String ownPid) {
		this.ownPid = ownPid;
	}

	/**
	 * @return the ownAmt
	 */
	public String getOwnAmt() {
		return this.ownAmt;
	}

	/**
	 * @param ownAmt
	 *            the ownAmt to set
	 */
	public void setOwnAmt(String ownAmt) {
		this.ownAmt = ownAmt;
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
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return this.resvCol01;
	}

	/**
	 * @param resvCol01
	 *            the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}

	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return this.resvCol02;
	}

	/**
	 * @param resvCol02
	 *            the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}

	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return this.resvCol03;
	}

	/**
	 * @param resvCol03
	 *            the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}

	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return this.resvCol04;
	}

	/**
	 * @param resvCol04
	 *            the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}

	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return this.resvCol05;
	}

	/**
	 * @param resvCol05
	 *            the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
	}

	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return this.useExprDt;
	}

	/**
	 * @param useExprDt
	 *            the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

}
