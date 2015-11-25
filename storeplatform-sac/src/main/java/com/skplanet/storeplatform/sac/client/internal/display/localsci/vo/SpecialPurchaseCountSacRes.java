package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 쇼핑 특가 상품 구매수 업데이트  SpecialPurchaseCountSacRes VO.
 * 
 * Updated on : 2015. 3. 26. Updated by : 김형식 , 지티소프트
 */
public class SpecialPurchaseCountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	
	private String purchaseId;	//구매ID
	
	private String productId;	//상품ID

	private String purchaseCountStatus;	//구매 수 업데이트 성공/실패 코드 , 성공 : “PD003700” , 실패 : “PD003701”

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
	 * @return the purchaseCountStatus
	 */
	public String getPurchaseCountStatus() {
		return purchaseCountStatus;
	}

	/**
	 * @param purchaseCountStatus the purchaseCountStatus to set
	 */
	public void setPurchaseCountStatus(String purchaseCountStatus) {
		this.purchaseCountStatus = purchaseCountStatus;
	}

}
