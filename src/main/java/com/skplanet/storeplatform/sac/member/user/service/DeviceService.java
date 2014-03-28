package com.skplanet.storeplatform.sac.member.user.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 휴대기기 관련 인터페이스.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public interface DeviceService {

	/**
	 * 휴대기기 등록.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateDeviceReq
	 * @return CreateDeviceRes
	 */
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req);

	/**
	 * 휴대기기 수정.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceReq
	 * @return ModifyDeviceRes
	 */
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, ModifyDeviceReq req);

	/**
	 * 휴대기기 목록 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListDeviceReq
	 * @return ListDeviceRes
	 */
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req);

	/**
	 * 휴대기기 단건 조회.
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
	 */
	public String insertDeviceInfo(String systemId, String tenanId, String userKey, DeviceInfo deviceInfo);

	/**
	 * 기기정보 update.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            DeviceInfo
	 * @return deviceKey String
	 */
	public String updateDeviceInfo(SacRequestHeader requestHeader, DeviceInfo req);

	/**
	 * 로그인한 기기정보 update.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceInfo
	 *            파라메터 휴대기기 정보
	 * @param dbDeviceInfo
	 *            SC회원에서 조회한 휴대기기 정보
	 * @param version
	 *            MDN 로그인 버젼
	 * @return deviceKey String
	 */
	public String updateDeviceInfoForLogin(SacRequestHeader requestHeader, DeviceInfo deviceInfo, DeviceInfo dbDeviceInfo, String version);

	/**
	 * 휴대기기 대표단말 설정.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            SetMainDeviceReq
	 * @return SetMainDeviceRes
	 */
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req);

	/**
	 * 휴대기기 삭제.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return List<DeviceInfo>
	 */
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req);

	/**
	 * 대표단말 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            DetailRepresentationDeviceReq
	 * @return DetailRepresentationDeviceRes
	 */
	public DetailRepresentationDeviceRes detailRepresentationDeviceRes(SacRequestHeader requestHeader, DetailRepresentationDeviceReq req);

	/**
	 * 단말 AOM 확인.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            SupportAomReq
	 * @return SupportAomRes
	 */
	public SupportAomRes getSupportAom(SacRequestHeader requestHeader, SupportAomReq req);

	/**
	 * 게임센터 연동.
	 * 
	 * @param gameCenterSac
	 *            GameCenterSac
	 * @return GameCenterSacRes
	 */
	public GameCenterSacRes insertGameCenterIF(@Valid @RequestBody GameCenterSacReq gameCenterSac);

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
			@Validated @RequestBody ChangedDeviceHistorySacReq historyRequest);

	/**
	 * <pre>
	 * ICAS 연동하여 imei 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            String
	 * @return String
	 */
	public String getIcasImei(String deviceId);

	/**
	 * <pre>
	 * 로그인한 휴대기기 정보를 비교한다.
	 * </pre>
	 * 
	 * @param deviceId
	 *            String
	 * @param reqVal
	 *            String
	 * @param dbVal
	 *            String
	 * @param equalsType
	 *            String
	 * @return boolean
	 */
	public boolean isEqualsLoginDevice(String deviceId, String reqVal, String dbVal, String equalsType);

	/**
	 * <pre>
	 * 휴대기기 관련 헤더 정보 셋팅.
	 * </pre>
	 * 
	 * @param deviceheader
	 *            DeviceHeader
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return DeviceInfo
	 */
	public DeviceInfo setDeviceHeader(DeviceHeader deviceheader, DeviceInfo deviceInfo);

}
