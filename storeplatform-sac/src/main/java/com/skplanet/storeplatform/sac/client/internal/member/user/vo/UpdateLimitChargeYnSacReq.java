package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.13. 회원 한도 요금제 사용여부 업데이트 [REQUEST].
 * 
 * Updated on : 2015. 5. 12. Updated by : Rejoice, Burkhan
 */
public class UpdateLimitChargeYnSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 회원 키. */
	@NotBlank
	private String userKey;
	/** 단말 키. */
	@NotBlank
	private String deviceKey;
	/** 조회 시간. */
	@NotBlank
	private String searchDt;
	/** 한도요금제 YN . */
	@NotBlank
	private String limitChargeYn;

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
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the searchDt
	 */
	public String getSearchDt() {
		return this.searchDt;
	}

	/**
	 * @param searchDt
	 *            the searchDt to set
	 */
	public void setSearchDt(String searchDt) {
		this.searchDt = searchDt;
	}

	/**
	 * @return the limitChargeYn
	 */
	public String getLimitChargeYn() {
		return this.limitChargeYn;
	}

	/**
	 * @param limitChargeYn
	 *            the limitChargeYn to set
	 */
	public void setLimitChargeYn(String limitChargeYn) {
		this.limitChargeYn = limitChargeYn;
	}

}
