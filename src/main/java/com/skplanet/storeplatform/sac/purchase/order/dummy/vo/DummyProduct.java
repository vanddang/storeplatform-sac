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
	private Double prodAmt; // 상품 가격
	private String prodGrdCd; // 상품 연령 등급 코드
	private String availableLimitPrchsPathCd; // 구매 가능 경로 (웹 / 단말)
	private String svcGrpCd; // 서비스 그룹 코드
	private String svcTypeCd; // 서비스 타입 코드
	private Boolean bSell; // 판매중 여부
	private String topCategoryNo; // TOP 카테고리 번호
	private Boolean bLimitProd; // 한정수량 판매 여부
	private Integer availableLimitCnt; // (한정판매 경우) 구매 가능한 수량
	private String usePeriodUnit; // 사용기간 단위
	private Integer usePeriodCnt; // 사용기간 값
	private Boolean bDupleProd; // 중복구매 허용 상품 여부
	private Boolean bMdnProd; // MDN 기반 상품 여부: true-MDN기반상품, false-ID기반상품
	private Boolean bSupport; // 단말 지원 상품 여부

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
	public Boolean getbSell() {
		return this.bSell;
	}

	/**
	 * @param bSell
	 *            the bSell to set
	 */
	public void setbSell(Boolean bSell) {
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
	public Boolean getbLimitProd() {
		return this.bLimitProd;
	}

	/**
	 * @param bLimitProd
	 *            the bLimitProd to set
	 */
	public void setbLimitProd(Boolean bLimitProd) {
		this.bLimitProd = bLimitProd;
	}

	/**
	 * @return the availableLimitCnt
	 */
	public Integer getAvailableLimitCnt() {
		return this.availableLimitCnt;
	}

	/**
	 * @param availableLimitCnt
	 *            the availableLimitCnt to set
	 */
	public void setAvailableLimitCnt(Integer availableLimitCnt) {
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
	public Integer getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	/**
	 * @param usePeriodCnt
	 *            the usePeriodCnt to set
	 */
	public void setUsePeriodCnt(Integer usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
	}

	/**
	 * @return the bDupleProd
	 */
	public Boolean getbDupleProd() {
		return this.bDupleProd;
	}

	/**
	 * @param bDupleProd
	 *            the bDupleProd to set
	 */
	public void setbDupleProd(Boolean bDupleProd) {
		this.bDupleProd = bDupleProd;
	}

	/**
	 * @return the bMdnProd
	 */
	public Boolean getbMdnProd() {
		return this.bMdnProd;
	}

	/**
	 * @param bMdnProd
	 *            the bMdnProd to set
	 */
	public void setbMdnProd(Boolean bMdnProd) {
		this.bMdnProd = bMdnProd;
	}

	/**
	 * @return the bSupport
	 */
	public Boolean getbSupport() {
		return this.bSupport;
	}

	/**
	 * @param bSupport
	 *            the bSupport to set
	 */
	public void setbSupport(Boolean bSupport) {
		this.bSupport = bSupport;
	}

}
