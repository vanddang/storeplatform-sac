package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * 2.1.50. 회원 가입 여부 리스트 조회 [RESPONSE].
 * 
 * Updated on : 2014. 12. 12. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExistListSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 휴대기기 리스트. */
	private List<DeviceInfo> deviceIdList;

	/**
	 * @return the deviceIdList
	 */
	public List<DeviceInfo> getDeviceIdList() {
		return this.deviceIdList;
	}

	/**
	 * @param deviceIdList
	 *            the deviceIdList to set
	 */
	public void setDeviceIdList(List<DeviceInfo> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}

}
