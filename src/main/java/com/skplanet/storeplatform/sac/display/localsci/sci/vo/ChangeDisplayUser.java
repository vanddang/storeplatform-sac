package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ChangeDisplayUser Value Object
 * 
 * 회원 ID, KEY 변경 서비스 VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
public class ChangeDisplayUser extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 테넌트 ID.
	 */
	private String tenantId;
	/**
	 * 변경될 사용자 ID.
	 */
	private String newUserId;
	/**
	 * 이전 사용자 ID.
	 */
	private String oldUserId;
	/**
	 * 변경될 사용자 KEY.
	 */
	private String newUserKey;
	/**
	 * 이전 사용자 KEY.
	 */
	private String oldUserKey;

	/**
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return String
	 */
	public String getNewUserId() {
		return this.newUserId;
	}

	/**
	 * @param newUserId
	 *            newUserId
	 */
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}

	/**
	 * @return String
	 */
	public String getOldUserId() {
		return this.oldUserId;
	}

	/**
	 * @param oldUserId
	 *            oldUserId
	 */
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	/**
	 * @return String
	 */
	public String getNewUserKey() {
		return this.newUserKey;
	}

	/**
	 * @param newUserKey
	 *            newUserKey
	 */
	public void setNewUserKey(String newUserKey) {
		this.newUserKey = newUserKey;
	}

	/**
	 * @return String
	 */
	public String getOldUserKey() {
		return this.oldUserKey;
	}

	/**
	 * @param oldUserKey
	 *            oldUserKey
	 */
	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}

}
