/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 단말 정보 조회 내부메소드 호출 Service.
 * 
 * Updated on : 2014. 5. 20. Updated by : 김다슬, 인크로스.
 */
public interface DeviceSCIService {

	/**
	 * <pre>
	 * 단말 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 * 
	 * @param keyType
	 *            (KEY_TYPE_INSD_DEVICE_ID)
	 * @param keyString
	 * @param userKey
	 * 
	 * @return DeviceInfo
	 */
	public DeviceInfo searchDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey);

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param historyRequest
	 *            ChangedDeviceHistoryReq
	 * @return ChangedDeviceHistoryRes
	 */
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(SacRequestHeader sacHeader,
			ChangedDeviceHistorySacReq request);
}