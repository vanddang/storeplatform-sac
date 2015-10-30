package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.io.Serializable;

/**
 * 구매DRM정보 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class PurchaseDrmInfoScReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String userKey; // 내부사용자번호
	private String prodId; // 상품 아이디
	private String prchsId;

	/**
	 * Gets tenant id.
	 *
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * Sets tenant id.
	 *
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * Gets system id.
	 *
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * Sets system id.
	 *
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * Gets user key.
	 *
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * Sets user key.
	 *
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * Gets prchs id.
	 *
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * Sets prchs id.
	 *
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * Gets prod id.
	 *
	 * @return the prod id
	 */
	public String getProdId() {
		return prodId;
	}

	/**
	 * Sets prod id.
	 *
	 * @param prodId
	 *            the prod id
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
}
