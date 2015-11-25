package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.TenantInfo;

/**
 * 2.1.56. 가입 테넌트 정보 조회 [RESPONSE].
 * 
 * Updated on : 2015. 05. 06. Updated by : 윤보영, 카레즈
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListTenantRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	private String deviceId;

	/**
	 * 테넌트 리스트 .
	 */
	private List<TenantInfo> tenantList;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the tenantList
	 */
	public List<TenantInfo> getTenantList() {
		return this.tenantList;
	}

	/**
	 * @param tenantList
	 *            the tenantList to set
	 */
	public void setTenantList(List<TenantInfo> tenantList) {
		this.tenantList = tenantList;
	}

}
