package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
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
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public CreateDeviceRes createDevice(HeaderVo headerVo, CreateDeviceReq req)
			throws Exception;

	/**
	 * 휴대기기 목록 조회
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ListDeviceRes listDevice(HeaderVo headerVo, ListDeviceReq req)
			throws Exception;

	/**
	 * 기등록된 회원의 휴대기기 정보 처리
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	public String preRegMemberDeviceRegist(String mdn) throws Exception;

	/**
	 * 기기정보 수정 처리
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public void mergeDeviceInfo(DeviceInfo req) throws Exception;

}
