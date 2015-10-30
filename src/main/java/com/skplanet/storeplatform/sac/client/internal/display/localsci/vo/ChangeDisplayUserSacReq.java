package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ChangeDisplayUserSacReq Value Object
 * 
 * 회원 ID, Key 변경에 따른 전시 테이블 회원 ID, Key 업데이트 요청 VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
public class ChangeDisplayUserSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Validation Group
	 * 
	 * Updated on : 2014. 3. 3. Updated by : 김현일, SK플래닛.
	 */
	public interface GroupChangeDisplayUserId {
	}

	/**
	 * 
	 * Validation Group
	 * 
	 * Updated on : 2014. 3. 3. Updated by : 김현일, SK플래닛.
	 */
	public interface GroupChangeDisplayUserKey {
	}

	/**
	 * 테넌트 ID.
	 */
	@NotBlank(groups = { GroupChangeDisplayUserId.class, GroupChangeDisplayUserKey.class })
	private String tenantId;

	/**
	 * 신규 ID.
	 */
	@NotBlank(groups = { GroupChangeDisplayUserId.class })
	private String newUserId;
	/**
	 * 이전 ID.
	 */
	@NotBlank(groups = { GroupChangeDisplayUserId.class })
	private String oldUserId;

	/**
	 * 신규 KEY.
	 */
	@NotBlank(groups = { GroupChangeDisplayUserKey.class })
	private String newUseKey;
	/**
	 * 이전 KEY.
	 */
	@NotBlank(groups = { GroupChangeDisplayUserKey.class })
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
	 * @return the newUserId
	 */
	public String getNewUserId() {
		return this.newUserId;
	}

	/**
	 * @param newUserId
	 *            the newUserId to set
	 */
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}

	/**
	 * @return the oldUserId
	 */
	public String getOldUserId() {
		return this.oldUserId;
	}

	/**
	 * @param oldUserId
	 *            the oldUserId to set
	 */
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	/**
	 * @return the newUseKey
	 */
	public String getNewUseKey() {
		return this.newUseKey;
	}

	/**
	 * @param newUseKey
	 *            the newUseKey to set
	 */
	public void setNewUseKey(String newUseKey) {
		this.newUseKey = newUseKey;
	}

	/**
	 * @return the oldUserKey
	 */
	public String getOldUserKey() {
		return this.oldUserKey;
	}

	/**
	 * @param oldUserKey
	 *            the oldUserKey to set
	 */
	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}

}
