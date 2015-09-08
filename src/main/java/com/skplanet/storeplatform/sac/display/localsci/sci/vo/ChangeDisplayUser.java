package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * ChangeDisplayUser Value Object
 * 
 * Updated on : 2015. 9. 8.
 * Updated by : 이태희, 부르칸
 */
public class ChangeDisplayUser extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 ID
	private String newUserId; // 변경될 사용자 ID
	private String oldUserId; // 이전 사용자 ID
	private String newUserKey; // 변경될 사용자 KEY
	private String oldUserKey; // 이전 사용자 KEY
	private String prodId; // 상품 ID
	private String statsKey; // 통계 KEY (좋아요)
	private String statsClsf; // 통계구분코드 (좋아요)
	
	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the newUserId
	 */
	public String getNewUserId() {
		return newUserId;
	}
	/**
	 * @param newUserId the newUserId to set
	 */
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}
	/**
	 * @return the oldUserId
	 */
	public String getOldUserId() {
		return oldUserId;
	}
	/**
	 * @param oldUserId the oldUserId to set
	 */
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}
	/**
	 * @return the newUserKey
	 */
	public String getNewUserKey() {
		return newUserKey;
	}
	/**
	 * @param newUserKey the newUserKey to set
	 */
	public void setNewUserKey(String newUserKey) {
		this.newUserKey = newUserKey;
	}
	/**
	 * @return the oldUserKey
	 */
	public String getOldUserKey() {
		return oldUserKey;
	}
	/**
	 * @param oldUserKey the oldUserKey to set
	 */
	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}
	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return the statsKey
	 */
	public String getStatsKey() {
		return statsKey;
	}
	/**
	 * @param statsKey the statsKey to set
	 */
	public void setStatsKey(String statsKey) {
		this.statsKey = statsKey;
	}
	/**
	 * @return the statsClsf
	 */
	public String getStatsClsf() {
		return statsClsf;
	}
	/**
	 * @param statsClsf the statsClsf to set
	 */
	public void setStatsClsf(String statsClsf) {
		this.statsClsf = statsClsf;
	}
}
