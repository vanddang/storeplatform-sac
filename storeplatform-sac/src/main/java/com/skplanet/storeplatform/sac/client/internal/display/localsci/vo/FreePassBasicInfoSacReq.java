package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * FreePassBasicInfoSacReq Value Object
 * 
 * 정액권 구매 내역 조회 시 필요한 상품 메타 정보 조회 VO.
 * 
 * Updated on : 2014. 6. 09. Updated by : 김형식, 지티소프트
 */
public class FreePassBasicInfoSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private List<String> list;

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
