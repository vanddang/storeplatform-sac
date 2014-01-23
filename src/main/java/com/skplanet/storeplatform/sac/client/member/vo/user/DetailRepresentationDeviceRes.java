package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [RESPONSE] 대표 단말 조회 기능을 제공한다.
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailRepresentationDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 자용자 단말 정보 */
	private DeviceInfo userDeviceInfo;

	/* 사용자 단말 부가 정보 */
	private List<DeviceExtraInfo> userDeviceExtraInfo;

	public DeviceInfo getUserDeviceInfo() {
		return this.userDeviceInfo;
	}

	public void setUserDeviceInfo(DeviceInfo userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}

	public List<DeviceExtraInfo> getUserDeviceExtraInfo() {
		return this.userDeviceExtraInfo;
	}

	public void setUserDeviceExtraInfo(List<DeviceExtraInfo> userDeviceExtraInfo) {
		this.userDeviceExtraInfo = userDeviceExtraInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
