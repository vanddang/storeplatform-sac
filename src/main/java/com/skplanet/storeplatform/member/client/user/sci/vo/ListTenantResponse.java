package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 가입 테넌트 정보 조회 Value Object
 * 
 * Updated on : 2015. 05. 06. Updated by : 윤보영, 카레즈
 */
public class ListTenantResponse extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 테넌트 리스트 .
	 */
	private List<String> tenantList;

	/**
	 * @return the tenantList
	 */
	public List<String> getTenantList() {
		return this.tenantList;
	}

	/**
	 * @param tenantList
	 *            the tenantList to set
	 */
	public void setTenantList(List<String> tenantList) {
		this.tenantList = tenantList;
	}

}
