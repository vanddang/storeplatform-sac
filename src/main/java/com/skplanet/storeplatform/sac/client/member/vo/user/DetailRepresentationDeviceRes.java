package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [RESPONSE] 대표 단말 설정 기능을 제공한다.
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailRepresentationDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 자용자 단말 정보 */
	private List<DeviceInfo> userDeviceInfo;

	public List<DeviceInfo> getUserDeviceInfo() {
		return this.userDeviceInfo;
	}

	public void setUserDeviceInfo(List<DeviceInfo> userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
