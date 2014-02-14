package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 휴대기기 삭제 기능을 제공한다
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String removeDeviceCount;
	/* 기기 ID List */
	List<RemoveDeviceListSacRes> deviceKeyList;

	public List<RemoveDeviceListSacRes> getDeviceKeyList() {
		return this.deviceKeyList;
	}

	public void setDeviceKeyList(List<RemoveDeviceListSacRes> deviceKeyList) {
		this.deviceKeyList = deviceKeyList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRemoveDeviceCount() {
		return this.removeDeviceCount;
	}

	public void setRemoveDeviceCount(String removeDeviceCount) {
		this.removeDeviceCount = removeDeviceCount;
	}

}
