package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * PaymentInfoSacReq Value Object
 * 
 * 결제 시 필요한 상품 메타 정보 조회 VO
 * 요청에 필요한 필드들만 남김
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
public class PaymentInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<String> prodIdList;
	private String tenantId;
	private String langCd;
	private String deviceModelCd; // 디바이스모델코드
	private String userKey;

	/**
	 * @return the prodIdList
	 */
	public List<String> getProdIdList() {
		return this.prodIdList;
	}

	/**
	 * @param prodIdList
	 *            the prodIdList to set
	 */
	public void setProdIdList(List<String> prodIdList) {
		this.prodIdList = prodIdList;
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
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
