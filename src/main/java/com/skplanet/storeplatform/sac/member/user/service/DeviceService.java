package com.skplanet.storeplatform.sac.member.user.service;

import java.util.List;

import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

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
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req) throws Exception;

	/**
	 * 휴대기기 목록 조회
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) throws Exception;

	/**
	 * 휴대기기 등록 서브 모듈 SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인
	 * 경우 IDP에 무선회원 해지 요청.
	 * 
	 * @param systemId
	 * @param tenanId
	 * @param userKey
	 * @param deviceInfo
	 * @throws Exception
	 */
	public void insertDeviceInfo(String systemId, String tenanId, String userKey, DeviceInfo deviceInfo) throws Exception;

	/**
	 * 기기정보 merge
	 * 
	 * @param systemId
	 * @param tenanId
	 * @param req
	 * @throws Exception
	 */
	public void mergeDeviceInfo(String systemId, String tenanId, DeviceInfo req) throws Exception;

	/**
	 * 휴대기기 대표단말 설정
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public DeviceInfo modifyRepresentationDevice(HeaderVo headerVo, SetMainDeviceRequest req) throws Exception;

	/**
	 * 휴대기기 삭제
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public List<DeviceInfo> removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) throws Exception;
	// public RemoveDeviceRes removeDevice(HeaderVo headerVo, RemoveDeviceReq req) throws Exception;
}
