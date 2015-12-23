package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 삭제 기능을 제공한다
 * 
 * Updated on : 2015. 12. 23. Updated by : 윤보영, 카레즈.
 */
public class RemoveDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/* UserKey */
	private String userKey;

	/* MDN List */
	List<RemoveDeviceListSacReq> deviceIdList;

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public List<RemoveDeviceListSacReq> getDeviceIdList() {
		return deviceIdList;
	}

	public void setDeviceIdList(List<RemoveDeviceListSacReq> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}
}
