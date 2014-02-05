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
	private double prodAmt; // 상품 가격
	private String prodGrdCd; // 상품 연령 등급 코드
	private String availableLimitPrchsPathCd; // 구매 가능 경로 (웹 / 단말)
	private String svcGrpCd; // 서비스 그룹 코드
	private String svcTypeCd; // 서비스 타입 코드
	private boolean bSell; // 판매중 여부
	private String topCategoryNo; // TOP 카테고리 번호
	private boolean bLimitProd; // 한정수량 판매 여부
	private int availableLimitCnt; // (한정판매 경우) 구매 가능한 수량
	private String usePeriodUnit; // 사용기간 단위
	private int usePeriodCnt; // 사용기간 값
	private boolean bDupleProd; // 중복구매 허용 상품 여부
	private boolean bMdnProd; // MDN 기반 상품 여부: true-MDN기반상품, false-ID기반상품
	private boolean bSupport; // 단말 지원 상품 여부

	// REQ
	private int prodQty; // 구매 수량
	private String tenantProdGrpCd;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;

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
	 * @return the usePeriodUnit
	 */
	public String getUsePeriodUnit() {
		return this.usePeriodUnit;
	}

	/**
	 * @param usePeriodUnit
	 *            the usePeriodUnit to set
	 */
	public void setUsePeriodUnit(String usePeriodUnit) {
		this.usePeriodUnit = usePeriodUnit;
	}

	/**
	 * @return the usePeriodCnt
	 */
	public int getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	/**
	 * @param usePeriodCnt
	 *            the usePeriodCnt to set
	 */
	public void setUsePeriodCnt(int usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
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
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
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

}
