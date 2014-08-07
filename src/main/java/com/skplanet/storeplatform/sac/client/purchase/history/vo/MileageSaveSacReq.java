package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * T마일리지 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveSacReq extends PurchaseHeaderSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String tenantId; // 테넌트ID
	@NotBlank
	private String userKey; // 내부사용자번호

	/**
	 * @return the tenantId
	 */
	@Override
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
