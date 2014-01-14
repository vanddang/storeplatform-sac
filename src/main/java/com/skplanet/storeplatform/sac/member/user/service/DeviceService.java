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
	 * 휴대기기 등록
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
	 * 
	 * 휴대기기 등록 서브 모듈 SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인
	 * 경우 IDP에 무선회원 해지 요청.
	 * 
	 * @param userKey
	 * @param deviceInfo
	 * @return
	 * @throws Exception
	 */
	public void insertDeviceInfo(String userKey, DeviceInfo deviceInfo)
			throws Exception;

	/**
	 * 기기정보 수정
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public void mergeDeviceInfo(DeviceInfo req) throws Exception;

}
