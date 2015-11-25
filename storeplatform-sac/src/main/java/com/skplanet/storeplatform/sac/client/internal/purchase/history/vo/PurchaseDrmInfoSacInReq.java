/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 구매DRM정보.
 * 
 * Updated on : 2013. 12. 28. Updated by : ntels_yjw.
 */
public class PurchaseDrmInfoSacInReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String tenantId; // Tenant ID
	private String systemId; // 시스템ID
	@NotBlank
	private String userKey; // 내부사용자번호
	@NotBlank
	private String prchsId; // 구매ID
	@NotBlank
	private String prodId; // 상품 아이디

	private String usePeriodSetCd; // 다운로드기간설정기준

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
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the usePeriodSetCd
	 */
	public String getUsePeriodSetCd() {
		return this.usePeriodSetCd;
	}

	/**
	 * @param usePeriodSetCd
	 *            the usePeriodSetCd to set
	 */
	public void setUsePeriodSetCd(String usePeriodSetCd) {
		this.usePeriodSetCd = usePeriodSetCd;
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
