/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]userKey, deviceKey 목록을 이용하여 회원정보 & 디바이스 목록조회.
 * 
 * Updated on : 2014. 3. 07. Updated by : 강신완, 부르칸.
 * Updated on : 2016. 1. 26. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserDeviceSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

    /**
     * tenantId ( 타파트 작업 후 삭제 처리 필요)
     */
    @Deprecated
    private String tenantId;

	/**
	 * 디바이스 키.
	 */
	private String deviceKey;

    /**
     * 사용자 키.
     */
	private String userKey;

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
