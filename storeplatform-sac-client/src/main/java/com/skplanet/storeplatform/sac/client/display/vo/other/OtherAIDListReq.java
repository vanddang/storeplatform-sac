package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * AID로 상품ID 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 14. Updated by : 백승현, 인크로스.
 */
public class OtherAIDListReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드

	@NotBlank
	private String aidList; // App 상품 AID 리스트

	@NotBlank
	private String deviceModelNo; // 단말 모델명.

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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the aidList
	 */
	public String getAidList() {
		return this.aidList;
	}

	/**
	 * @param aidList
	 *            the aidList to set
	 */
	public void setAidList(String aidList) {
		this.aidList = aidList;
	}

	/**
	 * @return the deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}
}
