package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * UserKey, TenantId를 이용한 회원정보 조회.
 * 
 * Updated on : 2015. 4. 13. Updated by : 반범진.
 */
public class SearchSapUserInfoSac extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	private String userKey;

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
