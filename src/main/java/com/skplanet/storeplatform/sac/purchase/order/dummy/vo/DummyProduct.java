/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Dummy 상품정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyProduct extends CommonInfo {
	private static final long serialVersionUID = 201401101L;

	private String prodId; // 상품 ID
	private String prodNm; // 상품 ID
	private double prodAmt; // 상품 가격
	private String prodGrdCd; // 상품 연령 등급 코드
	private String aid; // AID
	private String availableLimitPrchsPathCd; // 구매 가능 경로 (웹 / 단말)
	private String svcGrpCd; // 서비스 그룹 코드
	private String svcTypeCd; // 서비스 타입 코드
	private boolean bSell; // 판매중 여부
	private String topCategoryNo; // TOP 카테고리 번호
	private boolean bLimitProd; // 한정수량 판매 여부
	private int availableLimitCnt; // (한정판매 경우) 구매 가능한 수량
	private String usePeriodUnitCd; // 사용기간 단위
	private int usePeriod; // 사용기간 값
	private boolean bDupleProd; // 중복구매 허용 상품 여부
	private boolean bMdnProd; // MDN 기반 상품 여부: true-MDN기반상품, false-ID기반상품
	private boolean bSupport; // 단말 지원 상품 여부
	private boolean bFlat; // 정액상품 여부
	private String flatType; // 정액상품 타입: 정액권, 시리즈패스권, 이북/코믹 전권 소장/대여

	private String couponCode;
	private String itemCode;

	// REQ
	private int prodQty; // 구매 수량
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;

	// 비과금 구매요청 시 사용
	private String useExprDt;

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
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * @param prodGrdCd
	 *            the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the availableLimitPrchsPathCd
	 */
	public String getAvailableLimitPrchsPathCd() {
		return this.availableLimitPrchsPathCd;
	}

	/**
	 * @param availableLimitPrchsPathCd
	 *            the availableLimitPrchsPathCd to set
	 */
	public void setAvailableLimitPrchsPathCd(String availableLimitPrchsPathCd) {
		this.availableLimitPrchsPathCd = availableLimitPrchsPathCd;
	}

	/**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * @return the svcTypeCd
	 */
	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	/**
	 * @param svcTypeCd
	 *            the svcTypeCd to set
	 */
	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	/**
	 * @return the bSell
	 */
	public boolean getbSell() {
		return this.bSell;
	}

	/**
	 * @param bSell
	 *            the bSell to set
	 */
	public void setbSell(boolean bSell) {
		this.bSell = bSell;
	}

	/**
	 * @return the topCategoryNo
	 */
	public String getTopCategoryNo() {
		return this.topCategoryNo;
	}

	/**
	 * @param topCategoryNo
	 *            the topCategoryNo to set
	 */
	public void setTopCategoryNo(String topCategoryNo) {
		this.topCategoryNo = topCategoryNo;
	}

	/**
	 * @return the bLimitProd
	 */
	public boolean getbLimitProd() {
		return this.bLimitProd;
	}

	/**
	 * @param bLimitProd
	 *            the bLimitProd to set
	 */
	public void setbLimitProd(boolean bLimitProd) {
		this.bLimitProd = bLimitProd;
	}

	/**
	 * @return the availableLimitCnt
	 */
	public int getAvailableLimitCnt() {
		return this.availableLimitCnt;
	}

	/**
	 * @param availableLimitCnt
	 *            the availableLimitCnt to set
	 */
	public void setAvailableLimitCnt(int availableLimitCnt) {
		this.availableLimitCnt = availableLimitCnt;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public int getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(int usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the bDupleProd
	 */
	public boolean getbDupleProd() {
		return this.bDupleProd;
	}

	/**
	 * @param bDupleProd
	 *            the bDupleProd to set
	 */
	public void setbDupleProd(boolean bDupleProd) {
		this.bDupleProd = bDupleProd;
	}

	/**
	 * @return the bMdnProd
	 */
	public boolean getbMdnProd() {
		return this.bMdnProd;
	}

	/**
	 * @param bMdnProd
	 *            the bMdnProd to set
	 */
	public void setbMdnProd(boolean bMdnProd) {
		this.bMdnProd = bMdnProd;
	}

	/**
	 * @return the bSupport
	 */
	public boolean getbSupport() {
		return this.bSupport;
	}

	/**
	 * @param bSupport
	 *            the bSupport to set
	 */
	public void setbSupport(boolean bSupport) {
		this.bSupport = bSupport;
	}

	// REQ

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
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return this.couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the bFlat
	 */
	public boolean isbFlat() {
		return this.bFlat;
	}

	/**
	 * @param bFlat
	 *            the bFlat to set
	 */
	public void setbFlat(boolean bFlat) {
		this.bFlat = bFlat;
	}

	/**
	 * @return the flatType
	 */
	public String getFlatType() {
		return this.flatType;
	}

	/**
	 * @param flatType
	 *            the flatType to set
	 */
	public void setFlatType(String flatType) {
		this.flatType = flatType;
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
