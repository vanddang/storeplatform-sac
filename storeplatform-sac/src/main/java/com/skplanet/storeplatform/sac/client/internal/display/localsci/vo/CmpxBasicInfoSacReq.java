package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CmpxBasicInfoSacReq Value Object
 * 
 * 이용권 기본정보 조회 시 필요한 상품 메타 정보 조회 VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식, 지티소프트
 */
public class CmpxBasicInfoSacReq extends CommonInfo {

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
