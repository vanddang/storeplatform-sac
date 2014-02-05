package com.skplanet.storeplatform.sac.member.user.service;

import java.util.HashMap;
import java.util.List;

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomRes;
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
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateDeviceReq
	 * @return CreateDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req);

	/**
	 * 휴대기기 수정
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceReq
	 * @return ModifyDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, ModifyDeviceReq req);

	/**
	 * 휴대기기 목록 조회
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListDeviceReq
	 * @return ListDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req);

	/**
	 * 휴대기기 단건 조회
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            String
	 * @param keyString
	 *            String
	 * @param userKey
	 *            String
	 * @return DeviceInfo
	 * @throws Exception
	 *             Exception
	 */
	public DeviceInfo searchDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey);

	/**
	 * 휴대기기 등록 서브 모듈 SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인
	 * 경우 IDP에 무선회원 해지 요청.
	 * 
	 * @param systemId
	 *            String
	 * @param tenanId
	 *            String
	 * @param userKey
	 *            String
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return deviceKey String
	 * @throws Exception
	 *             Exception
	 */
	public String insertDeviceInfo(String systemId, String tenanId, String userKey, DeviceInfo deviceInfo);

	/**
	 * 기기정보 update
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            DeviceInfo
	 * @return deviceKey String
	 * @throws Exception
	 *             Exception
	 */
	public String updateDeviceInfo(SacRequestHeader requestHeader, DeviceInfo req);

	/**
	 * 휴대기기 대표단말 설정
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            SetMainDeviceReq
	 * @return SetMainDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req);

	/**
	 * 휴대기기 삭제
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return List<DeviceInfo>
	 * @throws Exception
	 *             Exception
	 */
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req);

	/**
	 * 사용자 정보 조회
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return List<DeviceInfo>
	 * @throws Exception
	 *             Exception
	 */
	public UserInfo searchUser(RemoveDeviceReq req, SacRequestHeader sacHeader);

	/**
	 * 휴대기기 목록 세팅 : 삭제요청 디바이스를 제외하고 리스트로 세팅
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param requestHeader
	 * @param req
	 * @param schUserRes
	 * @return
	 * @throws Exception
	 */
	public List<DeviceInfo> deviceModifyList(SacRequestHeader requestHeader, RemoveDeviceReq req, UserInfo userInfo);

	/**
	 * 휴대기기 목록
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param requestHeader
	 * @param req
	 * @param schUserRes
	 * @return
	 * @throws Exception
	 */
	public ListDeviceRes deviceList(SacRequestHeader requestHeader, RemoveDeviceReq req, UserInfo userInfo);

	/**
	 * 휴대기기 디바이스 키 추출
	 * 
	 * @param requestHeader
	 * @param schUserRes
	 * @return
	 */
	public ListDeviceRes searchDeviceKeyResponse(SacRequestHeader requestHeader, UserInfo userInfo, RemoveDeviceReq req);

	/**
	 * IDP 연동 데이터 세팅
	 * 
	 * @param deviceModifyList
	 * @return
	 * @throws Exception
	 */
	public String getUserPhoneStr(List<DeviceInfo> deviceModifyList);

	/**
	 * IDP 휴대기기 정보 등록 세팅
	 * 
	 * @param req
	 * @param schUserRes
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getDeviceParam(RemoveDeviceReq req, UserInfo userInfo);

	/**
	 * ImIdp 디바이스 업데이트(삭제대상 제외)
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ImIDPReceiverM imIdpDeviceUpdate(RemoveDeviceReq req, HashMap<String, Object> param, UserInfo userInfo, String userPhoneStr);

	/**
	 * 
	 * <pre>
	 * SC 디바이스 삭제
	 * </pre>
	 * 
	 * @param schUserRes
	 * @param removeDeviceRes
	 * @return
	 * @throws Exception
	 */
	public RemoveDeviceResponse removeDeviceSC(UserInfo userInfo, RemoveDeviceRes removeDeviceRes);

	/**
	 * 대표단말 조회
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param requestHeader
	 * @param req
	 * @return
	 */
	public DetailRepresentationDeviceRes detailRepresentationDeviceRes(SacRequestHeader requestHeader, DetailRepresentationDeviceReq req);

	/**
	 * 대표기기 여부
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param requestHeader
	 * @param req
	 * @return
	 */
	public ListDeviceRes isPrimaryDevice(RemoveDeviceRes res, UserInfo userInfo, SacRequestHeader sacHeader);

	/**
	 * 단말 AOM 확인
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return List<DeviceInfo>
	 * @throws Exception
	 *             Exception
	 */
	public SupportAomRes getSupportAom(SacRequestHeader requestHeader, SupportAomReq req);
}
