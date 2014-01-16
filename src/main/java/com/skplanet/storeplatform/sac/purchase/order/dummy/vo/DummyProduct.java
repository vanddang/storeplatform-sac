package com.skplanet.storeplatform.sac.purchase.order.dummy.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DummyProduct extends CommonInfo {
	private static final long serialVersionUID = 1L;

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

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Double getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getAvailableLimitPrchsPathCd() {
		return this.availableLimitPrchsPathCd;
	}

	public void setAvailableLimitPrchsPathCd(String availableLimitPrchsPathCd) {
		this.availableLimitPrchsPathCd = availableLimitPrchsPathCd;
	}

	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	public Boolean getbSell() {
		return this.bSell;
	}

	public void setbSell(Boolean bSell) {
		this.bSell = bSell;
	}

	public String getTopCategoryNo() {
		return this.topCategoryNo;
	}

	public void setTopCategoryNo(String topCategoryNo) {
		this.topCategoryNo = topCategoryNo;
	}

	public Boolean getbLimitProd() {
		return this.bLimitProd;
	}

	public void setbLimitProd(Boolean bLimitProd) {
		this.bLimitProd = bLimitProd;
	}

	public Integer getAvailableLimitCnt() {
		return this.availableLimitCnt;
	}

	public void setAvailableLimitCnt(Integer availableLimitCnt) {
		this.availableLimitCnt = availableLimitCnt;
	}

	public String getUsePeriodUnit() {
		return this.usePeriodUnit;
	}

	public void setUsePeriodUnit(String usePeriodUnit) {
		this.usePeriodUnit = usePeriodUnit;
	}

	public Integer getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	public void setUsePeriodCnt(Integer usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
	}

	public Boolean getbDupleProd() {
		return this.bDupleProd;
	}

	public void setbDupleProd(Boolean bDupleProd) {
		this.bDupleProd = bDupleProd;
	}

	public Boolean getbMdnProd() {
		return this.bMdnProd;
	}

	public void setbMdnProd(Boolean bMdnProd) {
		this.bMdnProd = bMdnProd;
	}

	public Boolean getbSupport() {
		return this.bSupport;
	}

	public void setbSupport(Boolean bSupport) {
		this.bSupport = bSupport;
	}
}
