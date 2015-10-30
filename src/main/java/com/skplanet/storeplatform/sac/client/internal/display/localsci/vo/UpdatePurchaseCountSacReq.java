package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품 구매수 업데이트
 * 
 * Updated on : 2014. 02. 14. Updated by : 이석희
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UpdatePurchaseCountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 Id
	private String productId; // 상품 ID
	private Integer purchaseCount; // 업데이트할 구매건수
	private String spcYn; // 특가 상품 YN
	private String purchaseDate; // 구매 일자

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
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the purchaseCount
	 */
	public Integer getPurchaseCount() {
		return this.purchaseCount;
	}

	/**
	 * @param purchaseCount
	 *            the purchaseCount to set
	 */
	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	/**
	 * @return the spcYn
	 */
	public String getSpcYn() {
		return this.spcYn;
	}

	/**
	 * @param spcYn
	 *            the spcYn to set
	 */
	public void setSpcYn(String spcYn) {
		this.spcYn = spcYn;
	}

	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * @param purchaseDate
	 *            the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}
