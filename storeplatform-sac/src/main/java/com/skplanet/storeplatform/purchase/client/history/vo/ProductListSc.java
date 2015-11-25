package com.skplanet.storeplatform.purchase.client.history.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ProductListSc extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tenantProdGrpCd;
	private String prodId;

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

}
