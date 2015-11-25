package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * tenant 정보.
 * 
 * Updated on : 2015. 5. 6. Updated by : 윤보영, 카레즈
 */
public class TenantInfo extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantId;

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

}
