package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [RESPONSE] 휴대기기 목록 조회.
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListDeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 ID.
	 */
	private String userId;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 휴대기기정보 리스트.
	 */
	private List<DeviceInfo> deviceInfoList;

	/**
	 * @return deviceInfoList
	 */
	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	/**
	 * @param deviceInfoList
	 *            List<DeviceInfo>
	 */
	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
