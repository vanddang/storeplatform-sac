package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ProductInfoSacReq Value Object
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 VO
 * 
 * Updated on : 2014. 2. 22. Updated by : 오승민, 인크로스
 */
public class ProductInfoSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private List<String> list;
	@NotBlank
	private String deviceModelNo;

	private String lang = "ko";

	private String tenantId;

	/**
	 * @return the list
	 */
	public List<String> getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
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

	/**
	 * @return the lang
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
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

}
