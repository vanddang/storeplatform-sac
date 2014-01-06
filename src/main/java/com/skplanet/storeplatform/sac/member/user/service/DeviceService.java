package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;

/**
 * 휴대기기 관련 인터페이스
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public interface DeviceService {

	/**
	 * 휴대기기 등록 처리
	 * 
	 * @return CreateDeviceRes
	 */
	public CreateDeviceRes createDevice(CreateDeviceReq req);

	/**
	 * 휴대기기 목록 조회
	 * 
	 * @return ListDeviceRes
	 */
	public ListDeviceRes listDevice(String queryString);

	/**
	 * 기등록된 회원의 휴대기기 정보 처리
	 * 
	 * @return String
	 */
	public String preRegMemberDeviceRegist(String mdn);

	/**
	 * 기기정보 수정 처리
	 * 
	 * @return String
	 */
	public String modifyDeviceInfo(DeviceInfo req);
}
