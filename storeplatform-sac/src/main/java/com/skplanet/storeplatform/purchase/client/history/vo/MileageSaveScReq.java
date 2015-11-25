package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트ID
	private String userKey; // 내부사용자번호
	private String procStatusCd; // 처리상태코드
	private String standardDt; // 기준일

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
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
	}

	/**
	 * @return the standardDt
	 */
	public String getStandardDt() {
		return this.standardDt;
	}

	/**
	 * @param standardDt
	 *            the standardDt to set
	 */
	public void setStandardDt(String standardDt) {
		this.standardDt = standardDt;
	}

}
