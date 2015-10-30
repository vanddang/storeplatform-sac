package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 쇼핑 특가 상품 구매수 업데이트  SpecialPurchaseCountSacReq VO.
 * 
 * Updated on : 2015. 3. 26. Updated by : 김형식 , 지티소프트
 */
public class SpecialPurchaseCountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String tenantId;	//테넌트ID
	@NotNull
	private String purchaseId;	//구매ID
	@NotNull
	private String productId;	//상품ID
	@NotNull
	private String purchaseStatusCd;	//구매상태 코드 ,구매완료 : “OR000301” ,구매취소 : “OR000302”
	@NotNull
	private Integer purchaseCount;	//업데이트할 구매건수
	@NotNull
	private String purchaseDate;	//구매일시
	
	private String purchaseCancelDate;	//구매취소 일시

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the purchaseId
	 */
	public String getPurchaseId() {
		return purchaseId;
	}

	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the purchaseStatusCd
	 */
	public String getPurchaseStatusCd() {
		return purchaseStatusCd;
	}

	/**
	 * @param purchaseStatusCd the purchaseStatusCd to set
	 */
	public void setPurchaseStatusCd(String purchaseStatusCd) {
		this.purchaseStatusCd = purchaseStatusCd;
	}

	/**
	 * @return the purchaseCount
	 */
	public Integer getPurchaseCount() {
		return purchaseCount;
	}

	/**
	 * @param purchaseCount the purchaseCount to set
	 */
	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the purchaseCancelDate
	 */
	public String getPurchaseCancelDate() {
		return purchaseCancelDate;
	}

	/**
	 * @param purchaseCancelDate the purchaseCancelDate to set
	 */
	public void setPurchaseCancelDate(String purchaseCancelDate) {
		this.purchaseCancelDate = purchaseCancelDate;
	}
		
	

}
