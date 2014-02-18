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
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.DeviceCompareEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.DeviceCompareEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.GameCenter;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateGameCenterRequest;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceListSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceListSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 휴대기기 관련 인터페이스 구현체.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSearchService userSearchService;

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

		LOGGER.info("######################## DeviceServiceImpl createDevice start ############################");

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
		if (StringUtils.equals(req.getRegMaxCnt(), "0")
				|| (schUserRes.getUserMbr().getDeviceCount() != null && Integer.parseInt(schUserRes.getUserMbr().getDeviceCount()) >= Integer
						.parseInt(req.getRegMaxCnt()))) {
			throw new StorePlatformException("SAC_MEM_1501");
		}

		/* 이미 등록된 휴대기기 체크 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");
		listDeviceReq.setUserKey(userKey);

		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
			if (deviceInfoList != null) {
				for (DeviceInfo deviceInfo : deviceInfoList) {
					if (StringUtils.equals(deviceInfo.getDeviceId(), deviceId)) {
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
		this.userService.updateProfileIdp(requestHeader, userKey, req.getUserAuthKey());

		CreateDeviceRes res = new CreateDeviceRes();
		res.setDeviceId(deviceId);
		res.setDeviceKey(deviceKey);
		res.setUserKey(userKey);

		LOGGER.info("######################## DeviceServiceImpl createDevice start ############################");

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

		/* 수정할 데이터 존재유무 확인 */
		if (this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID, deviceKey, userKey) == null) {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

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
			this.userService.updateProfileIdp(requestHeader, req.getUserKey(), req.getUserAuthKey());
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

		LOGGER.info("######################## DeviceServiceImpl listDevice start ############################");

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

		if (req.getDeviceId() != null && !req.getDeviceId().equals("")) {
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
		} else if (req.getDeviceKey() != null && !req.getDeviceKey().equals("")) {
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
		} else if (req.getUserId() != null && !req.getUserId().equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());
		} else if (req.getUserKey() != null && !req.getUserKey().equals("")) {
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
			/* response 셋팅 */
			res.setUserId(schDeviceListRes.getUserID());
			res.setUserKey(schDeviceListRes.getUserKey());

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				DeviceInfo deviceInfo = DeviceUtil.getConverterDeviceInfo(userMbrDevice);

				/* 폰정보 DB 조회하여 추가 정보 반영 */
				Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
				deviceInfo.setMakeComp(device.getMnftCompCd());
				deviceInfo.setModelNm(device.getModelNm());
				deviceInfo.setVmType(device.getVmTypeCd());

				deviceInfoList.add(deviceInfo);
			}
			res.setDeviceInfoList(deviceInfoList);

		} catch (StorePlatformException ex) {
			if (!StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		LOGGER.info("######################## DeviceServiceImpl listDevice end ############################");

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

		LOGGER.info("######################## DeviceServiceImpl searchDevice start ############################");

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

		try {

			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

			deviceInfo = new DeviceInfo();
			deviceInfo = DeviceUtil.getConverterDeviceInfo(schDeviceRes.getUserMbrDevice());
			deviceInfo.setUserId(schDeviceRes.getUserID());
			deviceInfo.setUserKey(schDeviceRes.getUserKey());

			/* 폰정보 DB 조회하여 추가 정보 반영 */
			Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
			deviceInfo.setMakeComp(device.getMnftCompCd());
			deviceInfo.setModelNm(device.getModelNm());
			deviceInfo.setVmType(device.getVmTypeCd());

		} catch (StorePlatformException ex) {
			if (!StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		LOGGER.info("######################## DeviceServiceImpl searchDevice end ############################");

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

		LOGGER.info("######################## DeviceServiceImpl insertDeviceInfo start ############################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		/* 1. 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);

		deviceInfo.setUserKey(userKey);
		deviceInfo.setTenantId(tenantId);

		UserMbrDevice userMbrDevice = DeviceUtil.getConverterUserMbrDeviceInfo(deviceInfo);
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);
		createDeviceReq.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 2. 기등록된 회원이 존재하는지 확인 */
		String previousUserKey = createDeviceRes.getPreviousUserKey();
		if (previousUserKey != null) {

			LOGGER.info(":::: [PreviousUserKey] {}", previousUserKey);
			LOGGER.info(":::: [NowUserKey] {}", userKey);

			/* 3. 구매이력 이관요청 */

			/* 4. 약관 이관 처리 */
			SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
			schAgreeListReq.setCommonRequest(commonRequest);
			schAgreeListReq.setUserKey(previousUserKey);

			SearchAgreementListResponse schAgreeListRes = null;
			try {

				schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
				List<MbrClauseAgree> agreeList = new ArrayList<MbrClauseAgree>();
				for (MbrClauseAgree agreeInfo : schAgreeListRes.getMbrClauseAgreeList()) {
					agreeInfo.setMemberKey(userKey);
					agreeList.add(agreeInfo);
				}
				UpdateAgreementRequest updAgreeReq = new UpdateAgreementRequest();
				updAgreeReq.setCommonRequest(commonRequest);
				updAgreeReq.setUserKey(userKey);
				updAgreeReq.setMbrClauseAgreeList(agreeList);
				this.userSCI.updateAgreement(updAgreeReq);

			} catch (StorePlatformException ex) {
				/* 약관 조회 결과 없는경우를 제외하고 throw */
				if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
					throw ex;
				}
			}

			/* 5. 통합회원에 휴대기기 등록시 무선회원 해지 */
			SearchUserResponse schUserRes = this.searchUser(commonRequest, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey);

			if (schUserRes.getUserMbr().getImSvcNo() != null) {

				try {

					AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
					authForWapEcReq.setUserMdn(deviceInfo.getDeviceId());
					this.idpSCI.authForWap(authForWapEcReq);

					SecedeForWapEcReq secedeForWapEcReq = new SecedeForWapEcReq();
					secedeForWapEcReq.setUserMdn(deviceInfo.getDeviceId());
					this.idpSCI.secedeForWap(secedeForWapEcReq);

				} catch (StorePlatformException ex) {
					if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {
						throw ex;
					}
				}

			}

		}

		/* 6. 게임센터 연동 */
		GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
		gameCenterSacReq.setUserKey(userKey);
		gameCenterSacReq.setDeviceId(deviceInfo.getDeviceId());
		gameCenterSacReq.setSystemId(systemId);
		gameCenterSacReq.setTenantId(tenantId);
		if (previousUserKey != null) {
			gameCenterSacReq.setPreUserKey(previousUserKey);
		}
		gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);

		this.insertGameCenterIF(gameCenterSacReq);

		LOGGER.info("######################## DeviceServiceImpl insertDeviceInfo end ############################");

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

		LOGGER.info("################ updateDeviceInfo start ##################");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = deviceInfo.getUserKey();
		String deviceId = deviceInfo.getDeviceId();
		String deviceKey = deviceInfo.getDeviceKey();
		String gameCenterYn = null;

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (deviceKey != null && !deviceKey.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			key.setKeyString(deviceKey);
		} else if (deviceId != null && !deviceId.equals("")) {
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
		String isAuthenticated = deviceInfo.getIsAuthenticated(); // 인증여부
		String authenticationDate = deviceInfo.getAuthenticationDate(); // 인증일자
		String isUsed = deviceInfo.getIsUsed(); // 사용여부
		String svcMangNum = deviceInfo.getSvcMangNum(); // SKT 휴대기기 통합 관리 번호
		String rooting = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_ROOTING_YN, deviceInfo.getDeviceExtraInfoList()); // rooting 여부

		LOGGER.info(":::::::::::::::::: device update field start ::::::::::::::::::");

		if (deviceId != null && !StringUtils.equals(deviceId, userMbrDevice.getDeviceID())) {

			LOGGER.info("[deviceId] {} -> {}", userMbrDevice.getDeviceID(), deviceId);
			userMbrDevice.setDeviceID(deviceId);

		}

		if (deviceModelNo != null && !StringUtils.equals(deviceModelNo, userMbrDevice.getDeviceModelNo())) {

			if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) {

				// 폰정보 조회 (deviceModelNo)
				Device device = this.commService.getPhoneInfo(deviceModelNo);

				if (device != null) {
					// OMD 단말이 아닐 경우만
					if (!StringUtils.equals(MemberConstants.DEVICE_TELECOM_OMD, device.getCmntCompCd())) {

						DeviceCompareEcReq deviceCompareEcReq = new DeviceCompareEcReq();
						deviceCompareEcReq.setUserMdn(deviceId);
						DeviceCompareEcRes deviceCompareEcRes = this.idpSCI.deviceCompare(deviceCompareEcReq);

						String idpModelId = deviceCompareEcRes.getModelId();

						// 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
						if (StringUtils.equals(idpModelId, "SSNU")) { // SHW-M200K->SHW-M200S
							idpModelId = "SSNL";
						} else if (StringUtils.equals(idpModelId, "SP05")) { // SHW-M420K->SHW-M420S
							idpModelId = "SSO0";
						}

						// uacd 부가속성 추가
						deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, idpModelId, deviceInfo));

						LOGGER.info("[change uacd] {}", idpModelId);
					}
				}

			}
			LOGGER.info("[deviceModelNo] {} -> {}", userMbrDevice.getDeviceModelNo(), deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

			/* 단말모델이 변경된 경우 게임센터 연동 */
			gameCenterYn = "Y";

		}

		if (nativeId != null) {

			//nativeId 비교 여부
			String isNativeIdAuth = deviceInfo.getIsNativeIdAuth();

			if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) {

				// OPMD 여부
				boolean isOpmd = StringUtils.substring(deviceId, 0, 3).equals("989");

				if (!StringUtils.equals(nativeId, userMbrDevice.getNativeID()) && !isOpmd) {

					String icasImei = null;

					LOGGER.info("::::  ICAS 연동 :::: deviceId : {}", deviceId);

					if (!StringUtils.equals(this.commService.getMappingInfo(deviceId, "mdn").getMvnoCD(), "0")) { // MVNO

						GetMvnoEcRes mvnoRes = this.commService.getMvService(deviceId);
						icasImei = mvnoRes.getImeiNum();

					} else {

						GetCustomerEcRes costomerRes = this.commService.getCustomer(deviceId);
						icasImei = costomerRes.getImeiNum();

					}

					LOGGER.info("::::  ICAS 연동 :::: icasImei : {}", icasImei);

					if (StringUtils.equals(icasImei, nativeId)) {
						LOGGER.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(), nativeId);
						userMbrDevice.setNativeID(nativeId);
					} else {
						throw new StorePlatformException("SAC_MEM_1503");
					}

				}
			} else { // 타사

				if (userMbrDevice.getNativeID() == null || StringUtils.equals(userMbrDevice.getNativeID(), "")) {
					// DB에 저장된 IMEI 값이 없는 경우 최초 인증으로 보고 IMEI 수집
					LOGGER.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(), nativeId);
					userMbrDevice.setNativeID(nativeId);
				} else {
					//isNativeIdAuth="Y"인경우 루팅여부 관계없이 비교
					if (StringUtils.equals(rooting, "Y") || StringUtils.equals(isNativeIdAuth, "Y")) {
						if (!nativeId.equals(userMbrDevice.getNativeID())) {
							throw new StorePlatformException("SAC_MEM_1504");
						}
					}
				}
			}

		}

		if (deviceAccount != null && !StringUtils.equals(deviceAccount, userMbrDevice.getDeviceAccount())) {

			LOGGER.info("[deviceAccount] {} -> {}", userMbrDevice.getDeviceAccount(), deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (deviceTelecom != null && !StringUtils.equals(deviceTelecom, userMbrDevice.getDeviceTelecom())) {

			LOGGER.info("[deviceTelecom] {} -> {}", userMbrDevice.getDeviceTelecom(), deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		if (deviceNickName != null && !StringUtils.equals(deviceNickName, userMbrDevice.getDeviceNickName())) {

			LOGGER.info("[deviceNickName] {} -> {}", userMbrDevice.getDeviceNickName(), deviceNickName);
			userMbrDevice.setDeviceNickName(deviceNickName);

		}

		if (isPrimary != null && !StringUtils.equals(isPrimary, userMbrDevice.getIsPrimary())) {

			LOGGER.info("[isPrimary] {} -> {}", userMbrDevice.getIsPrimary(), isPrimary);
			userMbrDevice.setIsPrimary(isPrimary);

		}

		if (isRecvSms != null && !StringUtils.equals(isRecvSms, userMbrDevice.getIsRecvSMS())) {

			LOGGER.info("[isRecvSms] {} -> {}", userMbrDevice.getIsRecvSMS(), isRecvSms);
			userMbrDevice.setIsRecvSMS(isRecvSms);

		}

		if (isAuthenticated != null && !StringUtils.equals(isAuthenticated, userMbrDevice.getIsAuthenticated())) {

			LOGGER.info("[isAuthenticate] {} -> {}", userMbrDevice.getIsAuthenticated(), isAuthenticated);
			userMbrDevice.setIsAuthenticated(isAuthenticated);

		}

		if (authenticationDate != null && !StringUtils.equals(authenticationDate, userMbrDevice.getAuthenticationDate())) {

			LOGGER.info("[authenticationDate] {} -> {}", userMbrDevice.getAuthenticationDate(), authenticationDate);
			userMbrDevice.setAuthenticationDate(authenticationDate);

		}

		if (isUsed != null && !StringUtils.equals(isUsed, userMbrDevice.getIsUsed())) {

			LOGGER.info("[isUsed] {} -> {}", userMbrDevice.getIsUsed(), isUsed);
			userMbrDevice.setIsUsed(isUsed);

		}

		if (svcMangNum != null && !StringUtils.equals(svcMangNum, userMbrDevice.getSvcMangNum())) {
			LOGGER.info("[svcMangNum] {} -> {}", userMbrDevice.getSvcMangNum(), svcMangNum);
			userMbrDevice.setSvcMangNum(svcMangNum);
		}

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));

		/* 휴대기기 변경 이력 코드 */
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);

		LOGGER.info(":::::::::::::::::: device update field end ::::::::::::::::::");

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 게임센터 연동 */
		if (StringUtils.equals(gameCenterYn, "Y")) {
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setDeviceId(deviceId);
			gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
			gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
			this.insertGameCenterIF(gameCenterSacReq);
		}

		LOGGER.info("################ updateDeviceInfo end ##################");

		return createDeviceRes.getDeviceKey();

	}

	/**
	 * SC회원정보 조회.
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
	 * 휴대기기 헤더 정보 셋팅.
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
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
					osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()), deviceInfo));
		}

		String svcVersion = deviceheader.getSvcVersion();
		if (svcVersion != null) { //SC버젼
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_SCVERSION,
					svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()), deviceInfo));
		}

		return deviceInfo;
	}

	/**
	 * 휴대기기 주요정보 셋팅.
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

		/* 닉네임 미입력시에만 디폴트로 기기명 저장 */
		if (deviceInfo.getDeviceNickName() == null) {
			deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
		}

		/* <NODATA> 적용요부 확인필요 */
		deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum());

		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, majorDeviceInfo.getUacd() == null ? ""
				: majorDeviceInfo.getUacd(), deviceInfo));

		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
				majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(), deviceInfo));

		return deviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * detailRepresentationDeviceRes
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet
	 * .storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq)
	 */
	@Override
	public DetailRepresentationDeviceRes detailRepresentationDeviceRes(SacRequestHeader requestHeader, DetailRepresentationDeviceReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		DetailRepresentationDeviceRes res = new DetailRepresentationDeviceRes();
		ListDeviceRes listRes = new ListDeviceRes();
		ListDeviceReq listReq = new ListDeviceReq();

		if (req.getUserKey() != null) {
			listReq.setUserKey(req.getUserKey());
			listReq.setIsMainDevice("Y");

			try {
				listRes = this.listDevice(requestHeader, listReq);
			} catch (StorePlatformException ex) {
				if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
					throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
				}
			}

		}

		if (listRes.getDeviceInfoList() != null) {
			for (DeviceInfo info : listRes.getDeviceInfoList()) {
				DeviceInfo addData = new DeviceInfo();
				List<DeviceExtraInfo> listExtraInfo = new ArrayList<DeviceExtraInfo>();

				addData.setDeviceKey(StringUtil.setTrim(info.getDeviceKey()));
				addData.setDeviceId(StringUtil.setTrim(info.getDeviceId()));
				addData.setDeviceModelNo(StringUtil.setTrim(info.getDeviceModelNo()));
				addData.setSvcMangNum(StringUtil.setTrim(info.getSvcMangNum()));
				addData.setDeviceTelecom(StringUtil.setTrim(info.getDeviceTelecom()));
				addData.setDeviceNickName(StringUtil.setTrim(info.getDeviceNickName()));
				addData.setIsPrimary(StringUtil.setTrim(info.getIsPrimary()));
				addData.setIsAuthenticated(StringUtil.setTrim(info.getIsAuthenticated()));
				addData.setAuthenticationDate(StringUtil.setTrim(info.getAuthenticationDate()));
				addData.setIsRecvSms(StringUtil.setTrim(info.getIsRecvSms()));
				addData.setNativeId(StringUtil.setTrim(info.getNativeId()));
				addData.setDeviceAccount(StringUtil.setTrim(info.getDeviceAccount()));
				addData.setJoinId(StringUtil.setTrim(info.getJoinId()));

				for (DeviceExtraInfo extraInfo : info.getDeviceExtraInfoList()) {
					DeviceExtraInfo addExtraInfo = new DeviceExtraInfo();
					addExtraInfo.setExtraProfile(extraInfo.getExtraProfile());
					addExtraInfo.setExtraProfileValue(extraInfo.getExtraProfileValue());
					listExtraInfo.add(addExtraInfo);
				}

				addData.setDeviceExtraInfoList(listExtraInfo);

				res.setDeviceInfo(addData);
			}
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

		LOGGER.info("###### Start detailRepresentationDevice Request : {}", req.toString());
		LOGGER.info("###### Fianl detailRepresentationDevice Response : {}", listRes.getDeviceInfoList().toString());
		LOGGER.info("###### Fianl detailRepresentationDevice Response Size : {}", listRes.getDeviceInfoList().size());

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * modifyRepresentationDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq)
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
			LOGGER.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

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
		if (req.getDeviceId() != null) {
			ListDeviceReq deviceReq = new ListDeviceReq();
			deviceReq.setUserKey(req.getUserKey());
			deviceReq.setDeviceId(req.getDeviceId());
			ListDeviceRes deviceRes = this.listDevice(requestHeader, deviceReq);
			if (deviceRes.getDeviceInfoList() != null) {
				String deviceKey = deviceRes.getDeviceInfoList().get(0).getDeviceKey();
				req.setDeviceKey(deviceKey);
			}

		}

		LOGGER.info("###### 2. exist Request : {}", existReq.toString());
		LOGGER.info("###### 2. exist Respone : {}", existRes.toString());

		if (existRes.getUserKey() != null) {
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			setMainDeviceRes.setDeviceKey(res.getDeviceKey());
		}

		return setMainDeviceRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#removeDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq)
	 */
	@Override
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) {

		LOGGER.info("============================================ DeviceServiceImpl.removeDevice() Start");

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceIdList() != null) {
			List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
			for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
				String opmdMdn = this.commService.getOpmdMdnInfo(id.getDeviceId());
				LOGGER.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

				RemoveDeviceListSacReq deviceId = new RemoveDeviceListSacReq();
				deviceId.setDeviceId(opmdMdn);

				deviceIdList.add(deviceId);

			}
			req.setDeviceIdList(deviceIdList);
		}

		/* SC 회원 정보 여부 */
		List<String> removeKeyList = new ArrayList<String>();
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
			RemoveDeviceReq removeDeviceReq = new RemoveDeviceReq();
			removeDeviceReq.setUserKey(req.getUserKey());
			removeDeviceReq.setDeviceId(id.getDeviceId());
			UserInfo userInfo = this.searchUser(removeDeviceReq, requestHeader);

			/* 휴대기기 조회 */
			DeviceInfo deviceInfo = null;
			String isPrimary = "";
			String deviceKey = "";

			deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, id.getDeviceId(), req.getUserKey());
			if (deviceInfo == null) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			}
			isPrimary = deviceInfo.getIsPrimary();
			deviceKey = deviceInfo.getDeviceKey();

			/* 삭제 가능여부 판단 */
			Integer deviceCount = Integer.parseInt(userInfo.getDeviceCount());
			if ((userInfo.getImSvcNo() != null && !"".equals(userInfo.getImSvcNo()))
					|| userInfo.getUserType().equals(MemberConstants.USER_TYPE_IDPID)) { // 통합/IDP 회원

				/* 단말 1개이상 보유이고, 삭제할 단말이 대표기기인 경우 에러 */
				if (deviceCount > 1 && isPrimary.equals("Y")) {
					throw new StorePlatformException("SAC_MEM_1510");
				}

			} else if (userInfo.getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				/* 모바일회원 단말삭제 불가 */
				throw new StorePlatformException("SAC_MEM_1511");
			}

			removeKeyList.add(deviceKey);
		}

		/* SC 휴대기기 삭제요청 */
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(req.getUserKey());
		removeDeviceRequest.setDeviceKey(removeKeyList);
		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		/* IDP 회원정보 수정 */
		this.userService.updateProfileIdp(requestHeader, req.getUserKey(), req.getUserAuthKey());

		/* 게임센터 연동 */
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(req.getUserKey());
			gameCenterSacReq.setDeviceId(id.getDeviceId());
			gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
			gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);
			this.insertGameCenterIF(gameCenterSacReq);
		}

		RemoveDeviceRes removeDeviceRes = new RemoveDeviceRes();
		List<RemoveDeviceListSacRes> resDeviceKeyList = new ArrayList<RemoveDeviceListSacRes>();
		for (String str : removeKeyList) {
			RemoveDeviceListSacRes removeListRes = new RemoveDeviceListSacRes();
			removeListRes.setDeviceKey(str);

			resDeviceKeyList.add(removeListRes);
		}
		removeDeviceRes.setDeviceKeyList(resDeviceKeyList);
		removeDeviceRes.setRemoveDeviceCount(String.valueOf(removeDeviceResponse.getDelDeviceCount()));

		LOGGER.info("######################## 결과 : " + removeDeviceRes.toString());
		LOGGER.info("######################## DeviceServiceImpl 휴대기기 삭제 End ############################");

		return removeDeviceRes; // 테스트용도로 deviceInfoList 추후 deviceKey Return 으로 변경
	}

	/**
	 * 단말 AOM 지원 여부
	 */
	@Override
	public SupportAomRes getSupportAom(SacRequestHeader sacHeader, SupportAomReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		ListDeviceRes listDeviceRes = new ListDeviceRes();
		if (!req.getDeviceId().equals("")) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			LOGGER.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);

			UserInfo deviceIdUser = this.commService.getUserBaseInfo("deviceId", req.getDeviceId(), sacHeader);

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(deviceIdUser.getUserKey());
			listDeviceReq.setDeviceId(req.getDeviceId());
			listDeviceReq.setIsMainDevice("N");

			LOGGER.info("============================================ listDeviceReq {}", listDeviceReq.toString());

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

			LOGGER.info("============================================ listDeviceRes {}", listDeviceRes.getDeviceInfoList().toString());
		}

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		if (!req.getUserKey().equals("")) {
			this.commService.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

			// 대표단말 조회
			DetailRepresentationDeviceReq detailRepresentationDeviceReq = new DetailRepresentationDeviceReq();
			detailRepresentationDeviceReq.setUserKey(req.getUserKey());
			DetailRepresentationDeviceRes detailRepresentationDeviceRes = this
					.detailRepresentationDeviceRes(sacHeader, detailRepresentationDeviceReq);

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setDeviceId(detailRepresentationDeviceRes.getDeviceInfo().getDeviceId());
			listDeviceReq.setIsMainDevice("N");

			LOGGER.info("============================================ listDeviceReq {}", listDeviceReq.toString());

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		}

		/* PhoneInfo 조회 */
		SupportAomRes res = new SupportAomRes();
		if (listDeviceRes.getDeviceInfoList() != null) {
			Device device = this.commService.getPhoneInfo(listDeviceRes.getDeviceInfoList().get(0).getDeviceModelNo());

			LOGGER.info("============================================Phoneinfo getAomSprtYn Res {}", device.getAomSprtYn());

			res.setIsAomSupport(device.getAomSprtYn());

		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#searchUser
	 * (com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public UserInfo searchUser(RemoveDeviceReq req, SacRequestHeader sacHeader) {

		UserInfo userInfo = this.commService.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		return userInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * insertGameCenterIF
	 * (com.skplanet.storeplatform.sac.client.member.vo.common.GameCenter)
	 */
	@Override
	public GameCenterSacRes insertGameCenterIF(@Valid @RequestBody GameCenterSacReq gameCenterSacReq) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(gameCenterSacReq.getSystemId());
		commonRequest.setTenantID(gameCenterSacReq.getTenantId());

		UpdateGameCenterRequest updGameCenterReq = new UpdateGameCenterRequest();
		updGameCenterReq.setCommonRequest(commonRequest);

		GameCenter gameCenterSc = new GameCenter();
		gameCenterSc.setDeviceID(gameCenterSacReq.getDeviceId());
		gameCenterSc.setPreDeviceID(gameCenterSacReq.getPreDeviceId());
		gameCenterSc.setUserKey(gameCenterSacReq.getUserKey());
		gameCenterSc.setPreUserKey(gameCenterSacReq.getPreUserKey());
		gameCenterSc.setRequestDate(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
		gameCenterSc.setWorkCode(gameCenterSacReq.getWorkCd());
		gameCenterSc.setRequestType("0001");
		//gameCenterSc.setFileDate(fileDate);
		updGameCenterReq.setGameCenter(gameCenterSc);

		this.userSCI.updateGameCenter(updGameCenterReq);

		GameCenterSacRes gameCenterSacRes = new GameCenterSacRes();
		gameCenterSacRes.setUserKey(gameCenterSacReq.getUserKey());
		return gameCenterSacRes;

	}
}
