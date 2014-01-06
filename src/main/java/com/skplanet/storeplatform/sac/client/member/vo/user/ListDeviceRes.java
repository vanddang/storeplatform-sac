package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [RESPONSE] 휴대기기 목록 조회
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 휴대기기정보 리스트
	 */
	private List<DeviceInfo> deviceInfoList;

	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

}
