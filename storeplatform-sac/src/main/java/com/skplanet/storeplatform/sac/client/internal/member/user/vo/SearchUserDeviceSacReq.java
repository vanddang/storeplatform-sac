/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] DeviceKey 이용한 회원정보&디바이스정보 조회.
 * 
 * Updated on : 2014. 3. 07. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserDeviceSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 디바이스 키, 사용자키 VO.
	 */
	private List<SearchUserDeviceSac> searchUserDeviceReqList;

	public List<SearchUserDeviceSac> getSearchUserDeviceReqList() {
		return this.searchUserDeviceReqList;
	}

	public void setSearchUserDeviceReqList(List<SearchUserDeviceSac> searchUserDeviceReqList) {
		this.searchUserDeviceReqList = searchUserDeviceReqList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
