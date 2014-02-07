/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
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
import com.skplanet.storeplatform.sac.member.common.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIdpService;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

//import com.skplanet.storeplatform.framework.core.util.StringUtil;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private IdpService idpService; // IDP 연동 클래스

	@Autowired
	private ImIdpService imIdpService; // 통합 IDP 연동 클래스

	@Autowired
	private UserService userService;

	@Autowired
	private IdpRepository idpRepository;

	@Autowired
	private UserSearchService userSearchService;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#createDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq)
	 */
	@Override
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req) {

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();
		String deviceId = req.getDeviceInfo().getDeviceId();

		/* 모번호 조회 */
		deviceId = this.commService.getOpmdMdnInfo(deviceId);

		/* 회원 정보 조회 */
		SearchUserResponse schUserRes = this.searchUser(commonRequest, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey);

		/* 등록 가능한 휴대기기 개수 초과 */
		if (req.getRegMaxCnt().equals("0")
				|| (schUserRes.getUserMbr().getDeviceCount() != null && Integer.parseInt(schUserRes.getUserMbr().getDeviceCount()) >= Integer
						.parseInt(req.getRegMaxCnt()))) {
			throw new StorePlatformException("SAC_MEM_1501");
		}

		/* 이미 등록된 휴대기기 체크 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");// 대표기기만 조회(Y), 모든기기 조회(N)
		listDeviceReq.setUserKey(userKey);
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);

		if (listDeviceRes.getDeviceInfoList() != null) {
			List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
			if (deviceInfoList != null) {
				for (DeviceInfo deviceInfo : deviceInfoList) {
					if (deviceInfo.getDeviceId().equals(deviceId)) {
						throw new StorePlatformException("SAC_MEM_1502");
					}
				}
			}
		}

		DeviceInfo deviceInfo = req.getDeviceInfo();

		/* device header 값 셋팅 */
		deviceInfo = this.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

		/* 휴대기기 주요정보 확인 */
		deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setUserKey(userKey);
		deviceInfo = this.getDeviceMajorInfo(deviceInfo);

		/* 휴대기기 등록 처리 */
		String deviceKey = this.insertDeviceInfo(commonRequest.getSystemID(), commonRequest.getTenantID(), userKey, deviceInfo);

		/* 변경된 정보 idp 연동 */
		this.userService.modifyProfileIdp(requestHeader, userKey, req.getUserAuthKey());

		CreateDeviceRes res = new CreateDeviceRes();
		res.setDeviceId(deviceId);
		res.setDeviceKey(deviceKey);
		res.setUserKey(userKey);

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#modifyDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq)
	 */
	@Override
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, ModifyDeviceReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();
		String deviceKey = req.getDeviceInfo().getDeviceKey();

		DeviceInfo deviceInfo = req.getDeviceInfo();

		/* 모번호 조회 */
		deviceInfo.setDeviceId(this.commService.getOpmdMdnInfo(deviceInfo.getDeviceId()));

		/* device header 값 셋팅 */
		deviceInfo = this.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

		/* 수정할 userKey 값 셋팅 */
		deviceInfo.setUserKey(userKey);

		/* 휴대기기 정보 수정 */
		deviceKey = this.updateDeviceInfo(requestHeader, deviceInfo);

		/* userAuthKey가 넘오온 경우만 IDP 업데이트 처리 */
		if (req.getUserAuthKey() != null) {
			this.userService.modifyProfileIdp(requestHeader, req.getUserKey(), req.getUserAuthKey());
		}

		ModifyDeviceRes res = new ModifyDeviceRes();
		res.setDeviceKey(deviceKey);
		res.setUserKey(req.getUserKey());

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#listDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq)
	 */
	@Override
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) {

		logger.info("######################## DeviceServiceImpl listDevice start ############################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();

		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		schDeviceListReq.setUserKey(userKey);
		schDeviceListReq.setIsMainDevice(req.getIsMainDevice()); // 대표기기만 조회(Y), 모든기기 조회(N)
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		ListDeviceRes res = new ListDeviceRes();

		if (req.getDeviceId() != null) {
			/* 단건 조회 처리 */
			DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), userKey);
			if (deviceInfo != null) {
				res.setUserId(deviceInfo.getUserId());
				res.setUserKey(deviceInfo.getUserKey());
				List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
				deviceInfoList.add(deviceInfo);
				res.setDeviceInfoList(deviceInfoList);
			}
			return res;
		} else if (req.getDeviceKey() != null) {
			/* 단건 조회 처리 */
			DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID, req.getDeviceKey(), userKey);
			if (deviceInfo != null) {
				res.setUserId(deviceInfo.getUserId());
				res.setUserKey(deviceInfo.getUserKey());
				List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
				deviceInfoList.add(deviceInfo);
				res.setDeviceInfoList(deviceInfoList);
			}
			return res;
		} else if (req.getUserId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());
		} else if (req.getUserKey() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(req.getUserKey());
		}

		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);
		schDeviceListReq.setCommonRequest(commonRequest);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = null;
		try {
			schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
		} catch (StorePlatformException ex) {
			if (!ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		/* response 셋팅 */
		if (schDeviceListRes != null) {
			res.setUserId(schDeviceListRes.getUserID());
			res.setUserKey(schDeviceListRes.getUserKey());

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			DeviceInfo deviceInfo = null;
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				deviceInfo = new DeviceInfo();
				deviceInfo = DeviceUtil.getConverterDeviceInfo(userMbrDevice);

				/* 폰정보 DB 조회하여 추가 정보 반영 */
				Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
				deviceInfo.setMakeComp(device.getMnftCompCd());
				deviceInfo.setModelNm(device.getModelNm());
				deviceInfo.setVmType(device.getVmTypeCd());

				deviceInfoList.add(deviceInfo);
			}
			res.setDeviceInfoList(deviceInfoList);
		}

		logger.info("######################## DeviceServiceImpl listDevice end ############################");

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#searchDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public DeviceInfo searchDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		logger.info("######################## DeviceServiceImpl searchDevice start ############################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);

		searchDeviceRequest.setUserKey(userKey);
		searchDeviceRequest.setKeySearchList(keySearchList);

		DeviceInfo deviceInfo = null;
		SearchDeviceResponse schDeviceRes = null;
		try {
			schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
		} catch (StorePlatformException ex) {
			if (!ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		if (schDeviceRes != null) {
			deviceInfo = new DeviceInfo();
			deviceInfo = DeviceUtil.getConverterDeviceInfo(schDeviceRes.getUserMbrDevice());
			deviceInfo.setUserId(schDeviceRes.getUserID());
			deviceInfo.setUserKey(schDeviceRes.getUserKey());

			/* 폰정보 DB 조회하여 추가 정보 반영 */
			Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
			deviceInfo.setMakeComp(device.getMnftCompCd());
			deviceInfo.setModelNm(device.getModelNm());
			deviceInfo.setVmType(device.getVmTypeCd());
		}

		logger.info("######################## DeviceServiceImpl searchDevice start ############################");

		return deviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * insertDeviceInfo(java.lang.String, java.lang.String, java.lang.String,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo) {

		logger.info("######################## DeviceServiceImpl insertDeviceInfo start ############################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		/* 1. 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);

		/* 게임센터 연동 API 확인필요!! */

		deviceInfo.setUserKey(userKey);
		deviceInfo.setTenantId(tenantId);

		CreateDeviceResponse createDeviceRes = null;

		createDeviceReq.setUserMbrDevice(DeviceUtil.getConverterUserMbrDeviceInfo(deviceInfo));
		createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 2. 기등록된 회원이 존재하는지 확인 */
		if (createDeviceRes.getPreviousUserKey() != null) {

			logger.info(":::: [PreviousUserKey] {}", createDeviceRes.getPreviousUserKey());
			logger.info(":::: [NowUserKey] {}", createDeviceRes.getUserKey());

			String previousUserKey = createDeviceRes.getPreviousUserKey();
			String nowUserKey = createDeviceRes.getUserKey();

			/* 3. 구매이력 이관요청 */

			/* 4. 약관 이관 처리 */
			SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
			schAgreeListReq.setCommonRequest(commonRequest);
			schAgreeListReq.setUserKey(previousUserKey);

			SearchAgreementListResponse schAgreeListRes = null;
			try {
				schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
			} catch (StorePlatformException ex) {
				/* 약관 조회 결과 없는경우를 제외하고 throw */
				if (!ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
					throw ex;
				}
			}

			if (schAgreeListRes != null) {

				List<MbrClauseAgree> agreeList = new ArrayList<MbrClauseAgree>();
				for (MbrClauseAgree agreeInfo : schAgreeListRes.getMbrClauseAgreeList()) {
					agreeInfo.setMemberKey(nowUserKey);
					agreeList.add(agreeInfo);
				}
				UpdateAgreementRequest updAgreeReq = new UpdateAgreementRequest();
				updAgreeReq.setCommonRequest(commonRequest);
				updAgreeReq.setUserKey(nowUserKey);
				updAgreeReq.setMbrClauseAgreeList(agreeList);
				this.userSCI.updateAgreement(updAgreeReq);

			}

			/* 5. 통합회원인 경우 무선회원 해지 */
			SearchUserResponse schUserRes = this.searchUser(commonRequest, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey);

			if (schUserRes.getUserMbr().getImSvcNo() != null) {

				try {

					this.idpService.authForWap(deviceInfo.getDeviceId());

					this.idpService.secedeUser4Wap(deviceInfo.getDeviceId());

				} catch (StorePlatformException ex) {
					if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {
						throw ex;
					}
				}

			}

		}

		logger.info("######################## DeviceServiceImpl insertDeviceInfo end ############################");

		return createDeviceRes.getDeviceKey();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * updateDeviceInfo(java.lang.String, java.lang.String,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String updateDeviceInfo(SacRequestHeader requestHeader, DeviceInfo deviceInfo) {

		logger.info("################ updateDeviceInfo start ##################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = deviceInfo.getUserKey();
		String deviceId = deviceInfo.getDeviceId();
		String deviceKey = deviceInfo.getDeviceKey();

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (deviceKey != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			key.setKeyString(deviceKey);
		} else if (deviceId != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceId);
		}

		keySearchList.add(key);
		schDeviceReq.setCommonRequest(commonRequest);
		schDeviceReq.setUserKey(deviceInfo.getUserKey());
		schDeviceReq.setKeySearchList(keySearchList);

		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);

		UserMbrDevice userMbrDevice = schDeviceRes.getUserMbrDevice();

		// 부가정보 등록시 셋팅할 값들
		deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());
		deviceInfo.setUserKey(userMbrDevice.getUserKey());

		/* device header 값 셋팅 */
		deviceInfo = this.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

		/* 휴대기기 주요정보 확인 */
		deviceInfo = this.getDeviceMajorInfo(deviceInfo);

		/* 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = deviceInfo.getDeviceAccount(); // gmailAddr
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		String deviceNickName = deviceInfo.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = deviceInfo.getIsPrimary(); // 대표폰 여부
		String isRecvSms = deviceInfo.getIsRecvSms(); // sms 수신여부
		String isAuthenticate = deviceInfo.getIsAuthenticated(); // 인증여부
		String authenticationDate = deviceInfo.getAuthenticationDate(); // 인증일자
		String isUsed = deviceInfo.getIsUsed(); // 사용여부

		String rooting = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_ROOTING_YN, deviceInfo.getUserDeviceExtraInfo()); // rooting 여부

		logger.info(":::::::::::::::::: device update field start ::::::::::::::::::");

		if (deviceId != null && !deviceId.equals(userMbrDevice.getDeviceID())) {

			logger.info("[deviceId] {} -> {}", userMbrDevice.getDeviceID(), deviceId);
			userMbrDevice.setDeviceID(deviceId);

		}

		if (deviceModelNo != null && !deviceModelNo.equals(userMbrDevice.getDeviceModelNo())) {

			if (MemberConstants.DEVICE_TELECOM_SKT.equals(deviceTelecom)) {

				// 폰정보 조회 (deviceModelNo)
				Device device = this.commService.getPhoneInfo(deviceModelNo);

				if (device != null) {
					// OMD 단말이 아닐 경우만
					if (!MemberConstants.DEVICE_TELECOM_OMD.equals(device.getCmntCompCd())) {

						IdpReceiverM idpReceiver = this.idpService.deviceCompare(deviceId);
						String idpModelId = idpReceiver.getResponseBody().getModel_id();

						if (idpModelId != null && !idpModelId.equals("")) {
							// 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
							if ("SSNU".equals(idpModelId)) { // SHW-M200K->SHW-M200S
								idpModelId = "SSNL";
							} else if ("SP05".equals(idpModelId)) { // SHW-M420K->SHW-M420S
								idpModelId = "SSO0";
							}

							// uacd 부가속성 추가
							deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, idpModelId,
									deviceInfo));

							logger.info("[change uacd] {}", idpModelId);
						}
					}
				}

			}
			logger.info("[deviceModelNo] {} -> {}", userMbrDevice.getDeviceModelNo(), deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

		}

		if (nativeId != null) {

			if (MemberConstants.DEVICE_TELECOM_SKT.equals(deviceTelecom)) {

				if (!nativeId.equals(userMbrDevice.getNativeID())) {
					logger.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(), nativeId);
					userMbrDevice.setNativeID(nativeId);
				}

				// 자번호 여부
				boolean isOpmd = StringUtils.substring(deviceId, 0, 3).equals("989");

				// 루팅 단말이고 OPMD 단말이 아닌 경우만 nativeId 체크
				if ("Y".equals(rooting) && !isOpmd) {

					String paramType = null;
					if (deviceInfo.getDeviceIdType().equals("msisdn")) {
						paramType = "11";
					} else if (deviceInfo.getDeviceIdType().equals("uuid")) {
						paramType = "12";
					} else {
						paramType = "13";
					}
					logger.info("::::  ICAS 연동 :::: deviceType {}, paramType {}", deviceInfo.getDeviceIdType(), paramType);

					String icasImei = null;

					if (!this.commService.getMappingInfo(deviceId, paramType).getMvnoCD().equals("0")) { // MVNO

						GetMvnoEcRes mvnoRes = this.commService.getMvService(deviceId);
						icasImei = mvnoRes.getImeiNum();

					} else {

						GetCustomerEcRes costomerRes = this.commService.getCustomer(deviceId);
						icasImei = costomerRes.getImeiNum();

					}

					if (icasImei.equals(nativeId)) {
						throw new StorePlatformException("SAC_MEM_1503");
					}

				}
			} else { // 타사

				if (userMbrDevice.getNativeID() == null || userMbrDevice.getNativeID().equals("")) {
					// DB에 저장된 IMEI 값이 없는 경우 최초 인증으로 보고 IMEI 수집
					logger.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(), nativeId);
					userMbrDevice.setNativeID(nativeId);
				} else {
					if (rooting.equals("Y") && !nativeId.equals(userMbrDevice.getNativeID())) {
						// 루팅된 단말 & DB의 IMEI와 단말 IMEI값 불일치
						throw new StorePlatformException("SAC_MEM_1504");
					}
				}
			}

		}

		if (deviceAccount != null && !deviceAccount.equals(userMbrDevice.getDeviceAccount())) {

			logger.info("[deviceAccount] {} -> {}", userMbrDevice.getDeviceAccount(), deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (deviceTelecom != null && !deviceTelecom.equals(userMbrDevice.getDeviceTelecom())) {

			logger.info("[deviceTelecom] {} -> {}", userMbrDevice.getDeviceTelecom(), deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		if (deviceNickName != null && !deviceNickName.equals(userMbrDevice.getDeviceNickName())) {

			logger.info("[deviceNickName] {} -> {}", userMbrDevice.getDeviceNickName(), deviceNickName);
			userMbrDevice.setDeviceNickName(deviceNickName);

		}

		if (isPrimary != null && !isPrimary.equals(userMbrDevice.getIsPrimary())) {

			logger.info("[isPrimary] {} -> {}", userMbrDevice.getIsPrimary(), isPrimary);
			userMbrDevice.setIsPrimary(isPrimary);

		}

		if (isRecvSms != null && !isRecvSms.equals(userMbrDevice.getIsRecvSMS())) {

			logger.info("[isRecvSms] {} -> {}", userMbrDevice.getIsRecvSMS(), isRecvSms);
			userMbrDevice.setIsRecvSMS(isRecvSms);

		}

		if (isAuthenticate != null && !isAuthenticate.equals(userMbrDevice.getIsAuthenticated())) {

			logger.info("[isAuthenticate] {} -> {}", userMbrDevice.getIsAuthenticated(), isAuthenticate);
			userMbrDevice.setIsAuthenticated(isAuthenticate);

		}

		if (authenticationDate != null && !authenticationDate.equals(userMbrDevice.getAuthenticationDate())) {

			logger.info("[authenticationDate] {} -> {}", userMbrDevice.getAuthenticationDate(), authenticationDate);
			userMbrDevice.setAuthenticationDate(authenticationDate);

		}

		if (isUsed != null && !isUsed.equals(userMbrDevice.getIsUsed())) {

			logger.info("[isUsed] {} -> {}", userMbrDevice.getIsUsed(), isUsed);
			userMbrDevice.setIsUsed(isUsed);

		}

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));

		logger.info(":::::::::::::::::: device update field end ::::::::::::::::::");

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 게임센터 연동 API 확인필요!! */

		logger.info("################ updateDeviceInfo end ##################");

		return createDeviceRes.getDeviceKey();

	}

	/**
	 * SC회원정보 조회
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param keyType
	 *            조회타입
	 * @param keyString
	 *            조회값
	 * @return SearchUserResponse
	 */
	public SearchUserResponse searchUser(CommonRequest commonRequest, String keyType, String keyString) {
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		return this.userSCI.searchUser(schUserReq);
	}

	/**
	 * 휴대기기 헤더 정보 셋팅
	 * 
	 * @param deviceheader
	 *            DeviceHeader
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return DeviceInfo
	 */
	public DeviceInfo setDeviceHeader(DeviceHeader deviceheader, DeviceInfo deviceInfo) {

		if (deviceheader.getModel() != null) { // 단말모델
			deviceInfo.setDeviceModelNo(deviceheader.getModel());
		}

		String osVersion = deviceheader.getOsVersion();
		if (osVersion != null) { // OS버젼
			deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
					osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()), deviceInfo));
		}

		String svcVersion = deviceheader.getSvcVersion();
		if (svcVersion != null) { //SC버젼
			deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_SCVERSION,
					svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()), deviceInfo));
		}

		return deviceInfo;
	}

	/**
	 * 휴대기기 주요정보 셋팅
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return DeviceInfo
	 */
	public DeviceInfo getDeviceMajorInfo(DeviceInfo deviceInfo) {

		MajorDeviceInfo majorDeviceInfo = this.commService.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(), deviceInfo.getDeviceTelecom(),
				deviceInfo.getDeviceId(), deviceInfo.getDeviceIdType());

		deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());
		deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom());
		deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());

		/* <NODATA> 적용요부 확인필요 */
		deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_IMMNGNUM,
				majorDeviceInfo.getImMngNum() == null ? "" : majorDeviceInfo.getImMngNum(), deviceInfo));

		deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, majorDeviceInfo.getUacd() == null ? ""
				: majorDeviceInfo.getUacd(), deviceInfo));

		deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
				majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(), deviceInfo));

		return deviceInfo;
	}

	/**
	 * 대표단말 조회
	 */
	@Override
	public DetailRepresentationDeviceRes detailRepresentationDeviceRes(SacRequestHeader requestHeader, DetailRepresentationDeviceReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		DetailRepresentationDeviceRes res = new DetailRepresentationDeviceRes();
		ListDeviceRes listRes = new ListDeviceRes();
		ListDeviceReq listReq = new ListDeviceReq();

		if (req.getUserId() != null) {

			RemoveDeviceReq deviceReq = new RemoveDeviceReq();
			deviceReq.setUserId(req.getUserId());
			UserInfo userInfo = this.searchUser(deviceReq, requestHeader);

			listReq.setUserKey(userInfo.getUserKey());
			listReq.setUserId(req.getUserId());
			listReq.setIsMainDevice("Y");
			listRes = this.listDevice(requestHeader, listReq);

			logger.info("### userId Req : {}", listReq.toString());
			logger.info("### userId Res : {}", listRes.toString());

		} else if (req.getUserKey() != null) {
			listReq.setUserKey(req.getUserKey());
			listReq.setIsMainDevice("Y");
			listRes = this.listDevice(requestHeader, listReq);

			logger.info("### userKey Req : {}", listReq.toString());
			logger.info("### userKey Res : {}", listRes.toString());
		}

		if (listRes.getDeviceInfoList() != null) {
			for (DeviceInfo info : listRes.getDeviceInfoList()) {
				DeviceInfo addData = new DeviceInfo();
				List<DeviceExtraInfo> listExtraInfo = new ArrayList<DeviceExtraInfo>();

				addData.setDeviceKey(StringUtil.setTrim(info.getDeviceKey()));
				addData.setDeviceId(StringUtil.setTrim(info.getDeviceId()));
				addData.setDeviceModelNo(StringUtil.setTrim(info.getDeviceModelNo()));
				//addData.setImMngNum(StringUtil.setTrim(info.getImMngNum()));
				addData.setDeviceTelecom(StringUtil.setTrim(info.getDeviceTelecom()));
				addData.setDeviceNickName(StringUtil.setTrim(info.getDeviceNickName()));
				addData.setIsPrimary(StringUtil.setTrim(info.getIsPrimary()));
				addData.setIsAuthenticated(StringUtil.setTrim(info.getIsAuthenticated()));
				addData.setAuthenticationDate(StringUtil.setTrim(info.getAuthenticationDate()));
				addData.setIsRecvSms(StringUtil.setTrim(info.getIsRecvSms()));
				addData.setNativeId(StringUtil.setTrim(info.getNativeId()));
				addData.setDeviceAccount(StringUtil.setTrim(info.getDeviceAccount()));
				addData.setJoinId(StringUtil.setTrim(info.getJoinId()));

				for (DeviceExtraInfo extraInfo : info.getUserDeviceExtraInfo()) {
					DeviceExtraInfo addExtraInfo = new DeviceExtraInfo();
					addExtraInfo.setExtraProfile(extraInfo.getExtraProfile());
					addExtraInfo.setExtraProfileValue(extraInfo.getExtraProfileValue());
					listExtraInfo.add(addExtraInfo);
				}

				addData.setUserDeviceExtraInfo(listExtraInfo);

				res.setUserDeviceInfo(addData);
			}
		}

		logger.info("###### Start detailRepresentationDevice Request : {}", req.toString());
		logger.info("###### Fianl detailRepresentationDevice Response : {}", listRes.getDeviceInfoList().toString());
		logger.info("###### Fianl detailRepresentationDevice Response Size : {}", listRes.getDeviceInfoList().size());

		return res;
	}

	/**
	 * 
	 * 휴대기기 대표단말 설정
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	@Override
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req) {

		SetMainDeviceRequest setMainDeviceRequest = new SetMainDeviceRequest();
		SetMainDeviceRes setMainDeviceRes = new SetMainDeviceRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

			ExistReq existReq = new ExistReq();
			existReq.setDeviceId(req.getDeviceId());
			this.userSearchService.exist(requestHeader, existReq);
		}

		if (req.getDeviceKey() != null) {
			ExistReq existReq = new ExistReq();
			existReq.setDeviceKey(req.getDeviceKey());
			this.userSearchService.exist(requestHeader, existReq);
		}

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		setMainDeviceRequest.setCommonRequest(commonRequest);

		/* userKey, deviceKey 회원존재여부 체크 */
		ExistReq existReq = new ExistReq();
		existReq.setUserKey(req.getUserKey());
		ExistRes existRes = this.userSearchService.exist(requestHeader, existReq);

		/* userKey, deviceId 로 디바이스 리스트 조회 -> getDeviceKey */
		ListDeviceRes deviceRes = new ListDeviceRes();
		if (req.getDeviceId() != null) {
			ListDeviceReq deviceReq = new ListDeviceReq();
			deviceReq.setUserKey(req.getUserKey());
			deviceReq.setDeviceId(req.getDeviceId());
			deviceRes = this.listDevice(requestHeader, deviceReq);
			if (deviceRes != null) {
				String deviceKey = deviceRes.getDeviceInfoList().get(0).getDeviceKey();
				req.setDeviceKey(deviceKey);
			}

		}

		logger.info("###### 2. exist Request : {}", existReq.toString());
		logger.info("###### 2. exist Respone : {}", existRes.toString());

		if (existRes.getUserKey() != null) {
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			setMainDeviceRes.setDeviceKey(req.getDeviceKey());
		}

		return setMainDeviceRes;
	}

	/**
	 * 
	 * 휴대기기 삭제
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	@Override
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) {

		logger.info("============================================ DeviceServiceImpl.removeDevice() Start");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);
		}

		/* SC 회원 정보 여부 */
		RemoveDeviceReq removeDeviceReq = new RemoveDeviceReq();
		removeDeviceReq.setUserKey(req.getUserKey());
		UserInfo userInfo = this.searchUser(removeDeviceReq, requestHeader);

		/* 휴대기기 조회 */
		DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), req.getUserKey());
		String IsPrimary = deviceInfo.getIsPrimary();//대표기기 여부
		String deviceKey = deviceInfo.getDeviceKey();//deviceKey

		/* 삭제 가능여부 판단 */
		Integer deviceCount = Integer.parseInt(userInfo.getDeviceCount());
		if ((userInfo.getImSvcNo() != null && !"".equals(userInfo.getImSvcNo())) || userInfo.getUserType().equals(MemberConstants.USER_TYPE_IDPID)) { // 통합/IDP 회원

			/* 단말 1개이상 보유이고, 삭제할 단말이 대표기기인 경우 에러 */
			if (deviceCount > 1 && IsPrimary.equals("Y")) {
				throw new StorePlatformException("SAC_MEM_1510");
			}

		} else if (userInfo.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
			/* 모바일회원 단말삭제 불가 */
			throw new StorePlatformException("SAC_MEM_1511");
		}

		/* SC 휴대기기 삭제요청 */
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(req.getUserKey());
		List<String> removeKeyList = new ArrayList<String>();
		removeKeyList.add(deviceKey);
		removeDeviceRequest.setDeviceKey(removeKeyList);
		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		/* IDP 회원정보 수정 */
		this.userService.modifyProfileIdp(requestHeader, req.getUserKey(), req.getUserAuthKey());

		RemoveDeviceRes removeDeviceRes = new RemoveDeviceRes();
		removeDeviceRes.setDeviceKey(deviceKey);
		/*
		 * RemoveDeviceRes에 삭제 카운트 추가하면 어떨지..
		 * removeDeviceRes.setDelDeviceCount(removeDeviceResponse
		 * .getDelDeviceCount());
		 */

		logger.info("######################## 결과 : " + removeDeviceResponse.getDelDeviceCount());
		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 End ############################");

		return removeDeviceRes; // 테스트용도로 deviceInfoList 추후 deviceKey Return 으로 변경
	}

	/**
	 * 휴대기기 목록 조회 : 삭제요청한 디바이스를 제외하고 List로 세팅
	 */
	@Override
	public List<DeviceInfo> deviceModifyList(SacRequestHeader requestHeader, RemoveDeviceReq req, UserInfo userInfo) {
		/* SC 회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		logger.info("###### Delete For Device Req : {}", req.toString());
		logger.info("###### UserInfo Res : {}", userInfo.toString());

		listDeviceReq.setUserKey(userInfo.getUserKey());
		listDeviceReq.setIsMainDevice("N");
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);
		List<DeviceInfo> deviceModifyList = new ArrayList<DeviceInfo>();

		logger.info("###### SC DeviceInfo List Req : {}", listDeviceReq.toString());
		logger.info("###### SC DeviceInfo List Res : {}", listDeviceRes.toString());
		logger.info("###### SC DeviceInfo List Res Size : {}", listDeviceRes.getDeviceInfoList().size());

		if (listDeviceRes.getDeviceInfoList() == null && listDeviceRes.getDeviceInfoList().size() < 0) {
			throw new StorePlatformException("SAC_MEM_0002", "getDeviceInfoList is NULL");
		} else if (listDeviceRes.getDeviceInfoList() != null && listDeviceRes.getDeviceInfoList().size() > 1) {
			for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {

				/* 삭제요청한 휴대기기를 제외하고 리스트로 담는다. */
				if (!req.getDeviceId().equals(deviceInfo.getDeviceId())) {
					if (deviceInfo.getDeviceTelecom() != null && deviceInfo.getDeviceId() != null) {

						/* 여러개의 단말이 등록되어, 삭제대상을 제외하고 리스트로 담는다. */
						DeviceInfo info = deviceInfo;
						deviceModifyList.add(info);

						logger.info("============================================ Remove Except Telecom & DeviceId : {}, {}",
								deviceInfo.getDeviceTelecom(), deviceInfo.getDeviceId());
						logger.info("============================================ Device List : {}", deviceModifyList.toString());
						logger.info("============================================ DeviceInfoList(Delete Device Except) Res : {}",
								deviceModifyList.toString());
					}

				}

			}

		} else if (listDeviceRes.getDeviceInfoList() != null && listDeviceRes.getDeviceInfoList().size() == 1) {
			logger.debug("============================================ getDeviceInfoList.size() == 1, Null Setting");
			deviceModifyList = null;

		}

		logger.info("###### 삭제요청 디바이스 : {}", req.getDeviceId());

		return deviceModifyList;
	}

	/**
	 * 휴대기기 목록 조회
	 */
	@Override
	public ListDeviceRes deviceList(SacRequestHeader requestHeader, RemoveDeviceReq req, UserInfo userInfo) {
		/* SC 회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		logger.info("###### Delete For Device Req : {}", req.toString());
		logger.info("###### UserInfo Res : {}", userInfo.toString());

		listDeviceReq.setUserKey(userInfo.getUserKey());
		listDeviceReq.setIsMainDevice("N");
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);

		logger.info("###### DeviceInfo List Req : {}", listDeviceReq.toString());
		logger.info("###### DeviceInfo List Res : {}", listDeviceRes.toString());
		logger.info("###### DeviceInfo List Res Size : {}", listDeviceRes.getDeviceInfoList().size());

		logger.info("###### deviceList Res : {}", listDeviceRes.getDeviceInfoList().toString());
		return listDeviceRes;
	}

	/* 디바이스 아이디로 디바이스 키 추출 */
	@Override
	public ListDeviceRes searchDeviceKeyResponse(SacRequestHeader requestHeader, UserInfo userInfo, RemoveDeviceReq req) {
		ListDeviceReq schDeviceListKeyRequest = new ListDeviceReq();
		schDeviceListKeyRequest.setUserKey(req.getUserKey());
		schDeviceListKeyRequest.setDeviceId(req.getDeviceId());
		schDeviceListKeyRequest.setIsMainDevice("N");

		logger.info("============================================ listDevice From DeviceKey Request : {}", schDeviceListKeyRequest.toString());
		ListDeviceRes schDeviceListKeyResponse = this.listDevice(requestHeader, schDeviceListKeyRequest);
		if (schDeviceListKeyResponse.getDeviceInfoList() != null) {
			throw new StorePlatformException("SAC_MEM_0002", schDeviceListKeyRequest.toString());
		}

		return schDeviceListKeyResponse;
	}

	/* IDP 연동 데이터 세팅 */
	@Override
	public String getUserPhoneStr(List<DeviceInfo> deviceModifyList) {
		logger.debug("###### IDP 연동 데이터 세팅 Start {}", deviceModifyList.toString());
		String userPhoneStr = null;
		StringBuffer sbUserPhone = new StringBuffer();
		if (deviceModifyList != null) {
			for (DeviceInfo deviceInfo : deviceModifyList) {

				String imMngNum = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_IMMNGNUM, deviceInfo.getUserDeviceExtraInfo());
				String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, deviceInfo.getUserDeviceExtraInfo());

				if (deviceInfo.getDeviceTelecom().equals(MemberConstants.DEVICE_TELECOM_SKT) && imMngNum != null && uacd != null) {
					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					sbUserPhone.append(imMngNum == null ? "" : imMngNum);
					sbUserPhone.append(",");
					if (MemberConstants.RESULT_FAIL.equals(uacd)) {
						sbUserPhone.append(MemberConstants.NM_DEVICE_TELECOM_NSH);
					} else {
						sbUserPhone.append(uacd == null ? "" : uacd);
					}
					sbUserPhone.append(",");
					sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));

					sbUserPhone.append("|");
				} else if (!deviceInfo.getDeviceTelecom().equals(MemberConstants.DEVICE_TELECOM_SKT)) {
					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					sbUserPhone.append(imMngNum == null ? "" : imMngNum);
					sbUserPhone.append(",");
					sbUserPhone.append(uacd == null ? "" : uacd);
					sbUserPhone.append(",");
					sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
					sbUserPhone.append("|");
				} else if (deviceInfo.getDeviceTelecom() == null) {
					throw new StorePlatformException("SAC_MEM_0002", "getDeviceTelecom() is Null");
				}

			}
		}

		userPhoneStr = sbUserPhone.toString();
		userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));

		logger.info("###### IDP 연동 데이터 삭제제외 : getUserPhoneStr {} ", userPhoneStr);

		return userPhoneStr;
	}

	/* IDP 휴대기기 정보 등록 세팅 */
	@Override
	public HashMap<String, Object> getDeviceParam(RemoveDeviceReq req, UserInfo userInfo) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("user_auth_key", req.getUserAuthKey());

		if (userInfo.getUserSex() != null) {
			param.put("user_sex", userInfo.getUserSex());
		}
		if (userInfo.getUserBirthDay() != null) {
			param.put("user_birthday", userInfo.getUserBirthDay());
		}
		if (userInfo.getUserZip() != null) {
			param.put("user_zipcode", userInfo.getUserZip());
		}
		if (userInfo.getUserAddress() != null) {
			param.put("user_address", userInfo.getUserAddress());
		}
		if (userInfo.getUserDetailAddress() != null) {
			param.put("user_address2", userInfo.getUserDetailAddress());
		}
		if (userInfo.getUserPhone() != null) {
			param.put("user_tel", userInfo.getUserPhone());
		}
		if (userInfo.getUserPhoneCountry() != null) {
			param.put("is_foreign", (userInfo.getUserPhoneCountry().equals("82") ? "N" : "Y"));
		}

		return param;
	}

	/* ImIdp 디바이스 업데이트(삭제대상 제외) */
	@Override
	public ImIdpReceiverM imIdpDeviceUpdate(RemoveDeviceReq req, HashMap<String, Object> param, UserInfo userInfo, String userPhoneStr) {

		/* IDP에 무선 회원 해지 요청 - 자동 해지 안된다고 함 */
		IdpReceiverM idpReceiver = this.idpService.authForWap(req.getDeviceId());
		if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IdpConstants.IDP_RES_CODE_OK)) {

			idpReceiver = this.idpService.secedeUser4Wap(req.getDeviceId());
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IdpConstants.IDP_RES_CODE_OK)) {
				throw new StorePlatformException("SAC_MEM_4002");
			}
		}

		param.put("key_type", "1"); // IM 통합서비스 번호
		param.put("key", userInfo.getImSvcNo());
		param.put("user_auth_key", req.getUserAuthKey());
		param.put("operation_mode", this.IDP_OPERATION_MODE);
		if (userPhoneStr != null) {
			param.put("user_mdn", userPhoneStr);
			param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
		}

		param.put("modify_req_date", DateUtil.getDateString(new Date(), "yyyyMMddHH"));
		param.put("modify_req_time", DateUtil.getDateString(new Date(), "HHmmss"));

		ImIdpReceiverM imIdpReceiver = this.imIdpService.updateAdditionalInfo(param);
		if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIdpConstants.IDP_RES_CODE_OK)) {
			throw new StorePlatformException("SAC_MEM_4002");
		} else {
			logger.info("[ 디바이스삭제 IMIDP 연동결과 : {}, {}", imIdpReceiver.getResponseHeader().getResult(), imIdpReceiver.getResponseHeader()
					.getResult_text());
		}

		return imIdpReceiver;
	}

	/**
	 * SC 디바이스 삭제
	 */
	@Override
	public RemoveDeviceResponse removeDeviceSC(UserInfo userInfo, RemoveDeviceRes removeDeviceRes) {
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		RemoveDeviceResponse removeDeviceResponse = new RemoveDeviceResponse();
		List<String> deviceKeyList = new ArrayList<String>();
		deviceKeyList.add(removeDeviceRes.getDeviceKey());

		CommonRequest commonRequest = new CommonRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(userInfo.getUserKey());
		removeDeviceRequest.setDeviceKey(deviceKeyList);

		removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		if (!StringUtil.equals(removeDeviceResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new StorePlatformException("SC Device Remove Fail : " + removeDeviceResponse.getCommonResponse().getResultCode() + ", "
					+ removeDeviceResponse.getCommonResponse().getResultMessage());
		} else {
			logger.info("SC Device Remove Success : {}, {}", removeDeviceResponse.getCommonResponse().getResultCode(), removeDeviceResponse
					.getCommonResponse().getResultMessage());
			logger.info("SC Device Remove Success Response {}: ", removeDeviceResponse.getDelDeviceCount());
		}

		return removeDeviceResponse;
	}

	/**
	 * 단말 AOM 지원여부 확인
	 */
	@Override
	public SupportAomRes getSupportAom(SacRequestHeader sacHeader, SupportAomReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

		}

		SupportAomRes res = new SupportAomRes();

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.commService.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		this.commService.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);

		/*
		 * userKey, deviceId 두개의 코드로 디바이스 리스트 조회 --> getDeviceModelNo(휴대기기 모델
		 * 코드)
		 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setUserKey(req.getUserKey());
		listDeviceReq.setDeviceId(req.getDeviceId());
		listDeviceReq.setIsMainDevice("N");
		logger.info("============================================ listDeviceReq {}", listDeviceReq.toString());
		ListDeviceRes listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		logger.info("============================================ listDeviceRes {}", listDeviceRes.getDeviceInfoList().toString());

		/* PhoneInfo 조회 */
		if (listDeviceRes.getDeviceInfoList() != null) {
			Device device = this.commService.getPhoneInfo(listDeviceRes.getDeviceInfoList().get(0).getDeviceModelNo());

			logger.info("============================================Phoneinfo getAomSprtYn Res {}", device.getAomSprtYn());

			res.setIsAomSupport(device.getAomSprtYn());

		}

		return res;
	}

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo searchUser(RemoveDeviceReq req, SacRequestHeader sacHeader) {

		UserInfo userInfo = this.commService.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		return userInfo;
	}

	/* SC API 대표기기 여부 */
	@Override
	public ListDeviceRes isPrimaryDevice(RemoveDeviceRes res, UserInfo userInfo, SacRequestHeader sacHeader) {

		DetailRepresentationDeviceReq detailRepresentationDeviceReq = new DetailRepresentationDeviceReq();
		detailRepresentationDeviceReq.setDeviceKey(res.getDeviceKey());

		ListDeviceRes listRes = new ListDeviceRes();
		ListDeviceReq listReq = new ListDeviceReq();

		listReq.setUserKey(userInfo.getUserKey());
		listReq.setDeviceKey(detailRepresentationDeviceReq.getDeviceKey());
		listReq.setIsMainDevice("N");
		listRes = this.listDevice(sacHeader, listReq);

		logger.info("### userKey Req : {}", listReq.toString());
		logger.info("### userKey Res : {}", listRes.toString());

		return listRes;
	}

}
