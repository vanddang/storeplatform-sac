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

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.GameCenter;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateGameCenterRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceAmqpSacReq;
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
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
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
	private MemberCommonComponent commService;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	@Autowired
	@Resource(name = "memberDelDeviceAmqpTemplate")
	private AmqpTemplate memberDelDeviceAmqpTemplate;

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
		if (StringUtil.equals(req.getRegMaxCnt(), "0")
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
					if (StringUtil.equals(deviceInfo.getDeviceId(), deviceId)) {
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

		CreateDeviceRes res = new CreateDeviceRes();
		res.setDeviceId(deviceId);
		res.setDeviceKey(deviceKey);
		res.setUserKey(userKey);

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

		DeviceInfo deviceInfo = req.getDeviceInfo();

		/* 모번호 조회 */
		if (StringUtil.isNotBlank(deviceInfo.getDeviceId())) {
			deviceInfo.setDeviceId(this.commService.getOpmdMdnInfo(deviceInfo.getDeviceId()));
		}

		/* 수정할 userKey 값 셋팅 */
		deviceInfo.setUserKey(req.getUserKey());

		/* 휴대기기 정보 수정 */
		String deviceKey = this.updateDeviceInfo(requestHeader, deviceInfo);

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

		if (StringUtil.isNotBlank(req.getDeviceId())) {
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
		} else if (StringUtil.isNotBlank(req.getDeviceKey())) {
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
		} else if (StringUtil.isNotBlank(req.getUserId())) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());
		} else if (StringUtil.isNotBlank(req.getUserKey())) {
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
				if (device != null) {
					deviceInfo.setMakeComp(device.getMnftCompCd());
					deviceInfo.setModelNm(device.getModelNm());
					deviceInfo.setVmType(device.getVmTypeCd());
				}
				deviceInfoList.add(deviceInfo);
			}
			res.setDeviceInfoList(deviceInfoList);

		} catch (StorePlatformException ex) {
			if (!StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

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
			if (device != null) {
				deviceInfo.setMakeComp(device.getMnftCompCd());
				deviceInfo.setModelNm(device.getModelNm());
				deviceInfo.setVmType(device.getVmTypeCd());
			}

		} catch (StorePlatformException ex) {
			if (!StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

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

		if (StringUtil.isBlank(createDeviceRes.getDeviceKey())) {
			throw new StorePlatformException("SAC_MEM_1102"); // 휴대기기 등록에 실패하였습니다.
		}

		/* 2. 기등록된 회원이 존재하는지 확인(모바일 회원에 대해서만 previous* 값들이 리턴됨) */
		String previousUserKey = createDeviceRes.getPreviousUserKey();
		String previousDeviceKey = createDeviceRes.getPreviousDeviceKey();
		String previousMbrNo = createDeviceRes.getPreMbrNo();
		String deviceKey = createDeviceRes.getDeviceKey();

		if (StringUtil.isNotBlank(previousUserKey) && StringUtil.isNotBlank(previousDeviceKey)) {

			LOGGER.info("기등록된 모바일 회원 이관처리 deviceId : {}, PreviousDeviceKey : {}, nowDeviceKey : {}, PreviousUserKey : {}, NowUserKey : {}",
					deviceInfo.getDeviceId(), previousDeviceKey, deviceKey, previousUserKey, userKey);

			/* 3. 전시/기타, 구매 파트 키 변경 */
			this.commService.excuteInternalMethod(true, systemId, tenantId, userKey, previousUserKey, deviceKey, previousDeviceKey);

			/* 4. 실명인증 비교 후 초기화 */
			SearchRealNameRequest schRealNameReq = new SearchRealNameRequest();
			schRealNameReq.setCommonRequest(commonRequest);
			schRealNameReq.setUserKey(previousUserKey);
			SearchRealNameResponse preSchRealNameRes = this.userSCI.searchRealName(schRealNameReq);

			schRealNameReq.setUserKey(userKey);
			SearchRealNameResponse schRealNameRes = this.userSCI.searchRealName(schRealNameReq);
			if (preSchRealNameRes.getMbrAuth() != null && schRealNameRes.getMbrAuth() != null) {

				if (!StringUtil.equals(preSchRealNameRes.getMbrAuth().getName(), schRealNameRes.getMbrAuth().getName())
						|| !StringUtil.equals(preSchRealNameRes.getMbrAuth().getBirthDay(), schRealNameRes.getMbrAuth().getBirthDay())
						|| !StringUtil.equals(preSchRealNameRes.getMbrAuth().getSex(), schRealNameRes.getMbrAuth().getSex())) { // 이름 생년월일 성별이 다른경우 초기화

					UpdateRealNameRequest updRealNameReq = new UpdateRealNameRequest();
					updRealNameReq.setCommonRequest(commonRequest);
					updRealNameReq.setIsRealName("N");
					updRealNameReq.setUserKey(userKey);
					this.userSCI.updateRealName(updRealNameReq);
					LOGGER.debug("realName init : {}", deviceInfo.getDeviceId());
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
					if (!StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
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
		if (StringUtil.isNotBlank(previousUserKey) && StringUtil.isNotBlank(previousDeviceKey)) {
			gameCenterSacReq.setPreUserKey(previousUserKey);
			gameCenterSacReq.setPreMbrNo(previousMbrNo);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_CHANGE);

		} else {
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
		}
		this.insertGameCenterIF(gameCenterSacReq);

		/* 7. MQ 연동 */
		CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
		try {
			mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
			if (StringUtil.isNotBlank(previousUserKey) && StringUtil.isNotBlank(previousDeviceKey)) {
				mqInfo.setOldUserKey(previousUserKey);
				mqInfo.setOldDeviceKey(previousDeviceKey);
			}
			mqInfo.setUserKey(userKey);
			mqInfo.setDeviceKey(deviceKey);
			mqInfo.setDeviceId(deviceInfo.getDeviceId());
			mqInfo.setMnoCd(deviceInfo.getDeviceTelecom());
			this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
		} catch (AmqpException ex) {
			LOGGER.info("MQ process fail {}", mqInfo);
		}

		return deviceKey;

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

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = deviceInfo.getUserKey();
		String gameCenterYn = null;

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (StringUtil.isNotBlank(deviceInfo.getDeviceKey())) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			key.setKeyString(deviceInfo.getDeviceKey());
		} else if (StringUtil.isNotBlank(deviceInfo.getDeviceId())) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceInfo.getDeviceId());
		}

		keySearchList.add(key);
		schDeviceReq.setCommonRequest(commonRequest);
		schDeviceReq.setUserKey(deviceInfo.getUserKey());
		schDeviceReq.setKeySearchList(keySearchList);

		SearchDeviceResponse schDeviceRes = null;

		try {

			schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			} else {
				throw ex;
			}
		}

		UserMbrDevice userMbrDevice = schDeviceRes.getUserMbrDevice();
		/* deviceKey로 휴대기기 정보 업데이트시 로깅을 위해 조회한 deviceId를 셋팅 */
		if (StringUtil.isBlank(deviceInfo.getDeviceId())) {
			deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
		}

		// 부가정보 등록시 셋팅할 값들
		deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());
		deviceInfo.setUserKey(userMbrDevice.getUserKey());

		/* device header 값 셋팅 */
		deviceInfo = this.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

		/* 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = deviceInfo.getDeviceAccount(); // gmailAddr
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		String deviceNickName = deviceInfo.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = deviceInfo.getIsPrimary(); // 대표폰 여부
		String isRecvSms = deviceInfo.getIsRecvSms(); // sms 수신여부
		String svcMangNum = deviceInfo.getSvcMangNum(); // SKT 휴대기기 통합 관리 번호

		StringBuffer deviceInfoChangeLog = new StringBuffer();
		if (StringUtil.isNotBlank(deviceInfo.getDeviceId()) && !StringUtil.equals(deviceInfo.getDeviceId(), userMbrDevice.getDeviceID())) {

			deviceInfoChangeLog.append("[deviceId]").append(userMbrDevice.getDeviceID()).append("->").append(deviceInfo.getDeviceId());
			userMbrDevice.setDeviceID(deviceInfo.getDeviceId());

		}

		if (StringUtil.isNotBlank(deviceModelNo)) {

			deviceInfoChangeLog.append("[deviceModelNo]").append(userMbrDevice.getDeviceModelNo()).append("->").append(deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

			/* 단말모델이 변경된 경우 게임센터 연동 */
			gameCenterYn = "Y";

		}

		if (StringUtil.isNotBlank(nativeId)) {

			deviceInfoChangeLog.append("[nativeId]").append(userMbrDevice.getNativeID()).append("->").append(nativeId);
			userMbrDevice.setNativeID(nativeId);

		}

		if (StringUtil.isNotBlank(deviceAccount)) {

			deviceInfoChangeLog.append("[deviceAccount]").append(userMbrDevice.getDeviceAccount()).append("->").append(deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (StringUtil.isNotBlank(deviceTelecom)) {

			deviceInfoChangeLog.append("[deviceTelecom]").append(userMbrDevice.getDeviceTelecom()).append("->").append(deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		if (StringUtil.isNotBlank(deviceNickName)) {

			deviceInfoChangeLog.append("[deviceNickName]").append(userMbrDevice.getDeviceNickName()).append("->").append(deviceNickName);
			userMbrDevice.setDeviceNickName(deviceNickName);

		}

		if (StringUtil.isNotBlank(isPrimary)) {

			deviceInfoChangeLog.append("[isPrimary]").append(userMbrDevice.getIsPrimary()).append("->").append(isPrimary);
			userMbrDevice.setIsPrimary(isPrimary);

		}

		if (StringUtil.isNotBlank(isRecvSms)) {

			deviceInfoChangeLog.append("[isRecvSms]").append(userMbrDevice.getIsRecvSMS()).append("->").append(isRecvSms);
			userMbrDevice.setIsRecvSMS(isRecvSms);

		}

		if (StringUtil.isNotBlank(svcMangNum)) {

			deviceInfoChangeLog.append("[svcMangNum]").append(userMbrDevice.getSvcMangNum()).append("->").append(svcMangNum);
			userMbrDevice.setSvcMangNum(svcMangNum);

		}

		deviceInfoChangeLog.append("[deviceExtraInfo]").append(ConvertMapperUtils.convertObjectToJson(deviceInfo.getDeviceExtraInfoList()));

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));

		/* 휴대기기 변경 이력 코드 */
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 게임센터 연동 */
		if (StringUtil.equals(gameCenterYn, "Y")) {
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setDeviceId(deviceInfo.getDeviceId());
			gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
			gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
			this.insertGameCenterIF(gameCenterSacReq);

			/* MQ 연동 */
			CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(createDeviceRes.getUserKey());
				mqInfo.setDeviceKey(createDeviceRes.getDeviceKey());
				mqInfo.setDeviceId(deviceInfo.getDeviceId());
				if (StringUtil.isBlank(deviceTelecom)) { // MQ연동시 deviceTelecom은 필수이므로 파라메터에 없으면 DB 정보를 넣어준다.
					mqInfo.setMnoCd(userMbrDevice.getDeviceTelecom());
				} else {
					mqInfo.setMnoCd(deviceTelecom);
				}

				this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);
			}
		}

		LOGGER.info("{} updateDeviceInfo field : {}", deviceInfo.getDeviceId(), deviceInfoChangeLog.toString());

		return createDeviceRes.getDeviceKey();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * updateDeviceInfoForLogin
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo,
	 * java.lang.String)
	 */
	@Override
	public String updateDeviceInfoForLogin(SacRequestHeader requestHeader, DeviceInfo deviceInfo, DeviceInfo dbDeviceInfo, String version) {

		String gameCenterYn = null;

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(dbDeviceInfo.getUserKey());
		userMbrDevice.setDeviceKey(dbDeviceInfo.getDeviceKey());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());

		// 부가정보 등록시 셋팅할 값들
		deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setUserKey(dbDeviceInfo.getUserKey());
		deviceInfo.setDeviceKey(dbDeviceInfo.getDeviceKey());

		/* device header 값 셋팅 */
		deviceInfo = this.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

		/* 휴대기기 주요정보 확인 */
		deviceInfo = this.getDeviceMajorInfo(deviceInfo);

		/* 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = deviceInfo.getDeviceAccount(); // gmailAddr
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		String svcMangNum = deviceInfo.getSvcMangNum(); // SKT 휴대기기 통합 관리 번호
		String rooting = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_ROOTING_YN, deviceInfo.getDeviceExtraInfoList()); // rooting 여부
		String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, deviceInfo.getDeviceExtraInfoList());

		StringBuffer deviceInfoChangeLog = new StringBuffer();
		if (StringUtil.isNotBlank(deviceModelNo) && !StringUtil.equals(deviceModelNo, dbDeviceInfo.getDeviceModelNo())) {

			if (StringUtil.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) {

				// // 폰정보 조회 (deviceModelNo)
				// Device device = this.commService.getPhoneInfo(deviceModelNo);
				//
				// if (device != null) {
				// // OMD 단말이 아닐 경우만
				// if (!StringUtil.equals(MemberConstants.DEVICE_TELECOM_OMD, device.getCmntCompCd())) {
				//
				// DeviceCompareEcReq deviceCompareEcReq = new DeviceCompareEcReq();
				// deviceCompareEcReq.setUserMdn(deviceInfo.getDeviceId());
				// DeviceCompareEcRes deviceCompareEcRes = this.idpSCI.deviceCompare(deviceCompareEcReq);
				//
				// String idpModelId = deviceCompareEcRes.getModelId();
				//
				// // 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
				// if (StringUtil.equals(idpModelId, "SSNU")) { // SHW-M200K->SHW-M200S
				// idpModelId = "SSNL";
				// } else if (StringUtil.equals(idpModelId, "SP05")) { // SHW-M420K->SHW-M420S
				// idpModelId = "SSO0";
				// }
				//
				// // uacd 부가속성 추가
				// deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
				// idpModelId, deviceInfo));
				//
				// LOGGER.info("[change uacd] {}", idpModelId);
				// }
				// }

				if (StringUtil.equals(uacd, "SSNU")) { // SHW-M200K->SHW-M200S
					uacd = "SSNL";
				} else if (StringUtil.equals(uacd, "SP05")) { // SHW-M420K->SHW-M420S
					uacd = "SSO0";
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, uacd, deviceInfo));
			}

			deviceInfoChangeLog.append("[deviceModelNo]").append(dbDeviceInfo.getDeviceModelNo()).append("->").append(deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

			/* 단말모델이 변경된 경우 게임센터 연동 */
			gameCenterYn = "Y";

		}

		if (StringUtil.equals(version, "v1")) {

			if (StringUtil.isNotBlank(nativeId)) {

				if (StringUtil.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) {

					// OPMD 여부
					boolean isOpmd = StringUtils.substring(deviceInfo.getDeviceId(), 0, 3).equals("989");

					if (!StringUtil.equals(nativeId, dbDeviceInfo.getNativeId()) && !isOpmd) {
						/* ICAS IMEI 비교 */
						if (StringUtil.equals(nativeId, this.getIcasImei(deviceInfo.getDeviceId()))) {

							deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->").append(nativeId);
							userMbrDevice.setNativeID(nativeId);

						} else {
							throw new StorePlatformException("SAC_MEM_1503");
						}

					}
				} else { // 타사

					// nativeId 비교 여부
					String isNativeIdAuth = deviceInfo.getIsNativeIdAuth();

					if (StringUtil.isBlank(dbDeviceInfo.getNativeId())) { // DB에 없는 경우만 최초 수집

						deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->").append(nativeId);
						userMbrDevice.setNativeID(nativeId);

					} else if (StringUtil.equals(rooting, "Y") || StringUtil.equals(isNativeIdAuth, "Y")) { // isNativeIdAuth="Y"인경우
																											// 루팅여부 관계없이
																											// 비교

						if (!nativeId.equals(dbDeviceInfo.getNativeId())) {
							throw new StorePlatformException("SAC_MEM_1504");
						}

					}
				}

			}

		} else { // v2

			/* DB에 IMEI가 있으면 비교 */
			if (!this.isEqualsLoginDevice(deviceInfo.getDeviceId(), nativeId, dbDeviceInfo.getNativeId(),
					MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

				if (StringUtil.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {

					// OPMD 여부
					boolean isOpmd = StringUtils.substring(deviceInfo.getDeviceId(), 0, 3).equals("989");

					if (!isOpmd) {
						/* ICAS IMEI 비교 */
						if (!StringUtil.equals(nativeId, this.getIcasImei(deviceInfo.getDeviceId()))) {
							throw new StorePlatformException("SAC_MEM_1503");

						}
					}

				} else { // 타사는 IMEI가 다르면 에러

					throw new StorePlatformException("SAC_MEM_1504");

				}

			} else if (StringUtil.isBlank(dbDeviceInfo.getNativeId())) { // DB에 IMEI가 없은경우 최초 수집

				if (StringUtil.isNotBlank(nativeId)) {

					if (StringUtil.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) {

						String icasImei = this.getIcasImei(deviceInfo.getDeviceId());
						deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->").append(icasImei);
						userMbrDevice.setNativeID(icasImei);

					} else {

						deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->").append(nativeId);
						userMbrDevice.setNativeID(nativeId);

					}

				}
			}

		}

		if (StringUtil.isNotBlank(deviceAccount) && !StringUtil.equals(deviceAccount, dbDeviceInfo.getDeviceAccount())) {

			deviceInfoChangeLog.append("[deviceAccount]").append(dbDeviceInfo.getDeviceAccount()).append("->").append(deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (StringUtil.isNotBlank(deviceTelecom) && !StringUtil.equals(deviceTelecom, dbDeviceInfo.getDeviceTelecom())) {

			deviceInfoChangeLog.append("[deviceTelecom]").append(dbDeviceInfo.getDeviceTelecom()).append("->").append(deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		if (StringUtil.isNotBlank(svcMangNum) && !StringUtil.equals(svcMangNum, dbDeviceInfo.getSvcMangNum())) {
			deviceInfoChangeLog.append("[svcMangNum]").append(dbDeviceInfo.getSvcMangNum()).append("->").append(svcMangNum);
			userMbrDevice.setSvcMangNum(svcMangNum);
		}

		deviceInfoChangeLog.append("[deviceExtraInfo]").append(ConvertMapperUtils.convertObjectToJson(deviceInfo.getDeviceExtraInfoList()));

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));

		/* 휴대기기 변경 이력 코드 */
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		createDeviceReq.setUserKey(dbDeviceInfo.getUserKey());
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 게임센터 연동 */
		if (StringUtil.equals(gameCenterYn, "Y")) {
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(dbDeviceInfo.getUserKey());
			gameCenterSacReq.setDeviceId(deviceInfo.getDeviceId());
			gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
			gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
			this.insertGameCenterIF(gameCenterSacReq);

			/* MQ 연동 */
			CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(createDeviceRes.getUserKey());
				mqInfo.setDeviceKey(createDeviceRes.getDeviceKey());
				mqInfo.setDeviceId(deviceInfo.getDeviceId());
				mqInfo.setMnoCd(deviceInfo.getDeviceTelecom());
				this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);
			}
		}

		LOGGER.info("{} updateDeviceInfoForLogin field : {}", deviceInfo.getDeviceId(), deviceInfoChangeLog.toString());

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

		try {
			return this.userSCI.searchUser(schUserReq);
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)
					|| ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_USERKEY)) {
				throw new StorePlatformException("SAC_MEM_0003", "userKey", keyString);
			} else {
				throw ex;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * setDeviceHeader
	 * (com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public DeviceInfo setDeviceHeader(DeviceHeader deviceheader, DeviceInfo deviceInfo) {

		if (deviceheader != null) {
			String model = deviceheader.getModel(); // 단말모델코드
			String osVersion = deviceheader.getOs(); // OS버젼
			String svcVersion = deviceheader.getSvc(); // SC버젼

			if (StringUtil.isBlank(deviceInfo.getDeviceModelNo()) && StringUtil.isNotBlank(model)) {
				deviceInfo.setDeviceModelNo(model);
			}

			if (StringUtil.isNotBlank(osVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
						osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()), deviceInfo));
			}

			if (StringUtil.isNotBlank(svcVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_SCVERSION,
						svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()), deviceInfo));
			}
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

		deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum());

		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, majorDeviceInfo.getUacd() == null ? ""
				: majorDeviceInfo.getUacd(), deviceInfo));

		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
				majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(), deviceInfo));

		return deviceInfo;
	}

	/**
	 * 대표 단말 조회.
	 * 
	 * @param DetailRepresentationDeviceReq
	 * @return DetailRepresentationDeviceRes
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

		return res;
	}

	/**
	 * 대표 단말 설정.
	 * 
	 * @param SetMainDeviceReq
	 * @return SetMainDeviceRes
	 */
	@Override
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req) {

		SetMainDeviceRequest setMainDeviceRequest = new SetMainDeviceRequest();
		SetMainDeviceRes setMainDeviceRes = new SetMainDeviceRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId()); // TODO
			req.setDeviceId(opmdMdn);

			ListDeviceReq deviceReq = new ListDeviceReq();
			deviceReq.setUserKey(req.getUserKey());
			deviceReq.setDeviceId(req.getDeviceId());

			ListDeviceRes deviceRes = this.listDevice(requestHeader, deviceReq);

			if (deviceRes.getDeviceInfoList() != null) {

				String deviceKey = deviceRes.getDeviceInfoList().get(0).getDeviceKey();
				req.setDeviceKey(deviceKey);
			}
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

		if (existRes.getUserKey() != null) {
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			setMainDeviceRes.setDeviceKey(res.getDeviceKey());
		}

		return setMainDeviceRes;
	}

	/**
	 * 휴대기기 삭제
	 */
	@Override
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) {

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

				RemoveDeviceListSacReq deviceId = new RemoveDeviceListSacReq();
				deviceId.setDeviceId(opmdMdn);

				deviceIdList.add(deviceId);

			}
			req.setDeviceIdList(deviceIdList);
		}

		/* SC 회원 정보 여부 */
		List<String> removeKeyList = new ArrayList<String>();
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {

			UserInfo userInfo = this.commService.getUserBaseInfo("deviceId", id.getDeviceId(), requestHeader);

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

		/* MQ 연동 : SC휴대기기 삭제를 하면 정보조회 할수 없어서 미리 처리함. */
		for (String key : removeKeyList) {

			DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID, key, req.getUserKey());
			if (deviceInfo == null) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			}

			RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();

			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(deviceInfo.getUserKey());
				mqInfo.setDeviceKey(deviceInfo.getDeviceKey());
				mqInfo.setDeviceId(deviceInfo.getDeviceId());
				mqInfo.setSvcMangNo(deviceInfo.getSvcMangNum());
				mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

				this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);

			}

		}

		/* SC 휴대기기 삭제요청 */
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(req.getUserKey());
		removeDeviceRequest.setDeviceKey(removeKeyList);

		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

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

		return removeDeviceRes;
	}

	/**
	 * 단말 AOM 지원 여부
	 */
	@Override
	public SupportAomRes getSupportAom(SacRequestHeader sacHeader, SupportAomReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");

		req.setDeviceId(deviceId);
		req.setUserKey(userKey);

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		ListDeviceRes listDeviceRes = new ListDeviceRes();
		if (!req.getDeviceId().equals("")) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);

			DetailReq detailReq = new DetailReq();
			detailReq.setDeviceId(req.getDeviceId());

			DetailRes detailRes = this.userSearchService.searchUser(detailReq, sacHeader);
			UserInfo deviceIdUser = detailRes.getUserInfo();

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(deviceIdUser.getUserKey());
			listDeviceReq.setDeviceId(req.getDeviceId());
			listDeviceReq.setIsMainDevice("N");

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		}

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		else if (!req.getUserKey().equals("")) {

			// 대표단말 조회
			DetailRepresentationDeviceReq detailRepresentationDeviceReq = new DetailRepresentationDeviceReq();
			detailRepresentationDeviceReq.setUserKey(req.getUserKey());

			DetailRepresentationDeviceRes detailRepresentationDeviceRes = this
					.detailRepresentationDeviceRes(sacHeader, detailRepresentationDeviceReq);

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setDeviceId(detailRepresentationDeviceRes.getDeviceInfo().getDeviceId());
			listDeviceReq.setIsMainDevice("Y");

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		}

		/* PhoneInfo 조회 */
		SupportAomRes res = new SupportAomRes();
		if (listDeviceRes.getDeviceInfoList() != null) {

			Device device = this.commService.getPhoneInfo(listDeviceRes.getDeviceInfoList().get(0).getDeviceModelNo());

			res.setIsAomSupport(device.getAomSprtYn());

		}

		return res;
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
		gameCenterSc.setRequestType(gameCenterSacReq.getSystemId());
		gameCenterSc.setPreMbrNo(gameCenterSacReq.getPreMbrNo());
		gameCenterSc.setMbrNo(gameCenterSacReq.getMbrNo());
		// gameCenterSc.setFileDate(fileDate);
		updGameCenterReq.setGameCenter(gameCenterSc);
		this.userSCI.updateGameCenter(updGameCenterReq);

		GameCenterSacRes gameCenterSacRes = new GameCenterSacRes();
		gameCenterSacRes.setUserKey(gameCenterSacReq.getUserKey());

		return gameCenterSacRes;

	}

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
	@Override
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(SacRequestHeader sacHeader,
			@Validated @RequestBody ChangedDeviceHistorySacReq request) {
		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String errorValue = "userKey 또는 ";

		// 필수파라미터 미입력.
		if (request.getDeviceId() == null || "".equals(request.getDeviceId())) {
			errorValue = StringUtil.capitalize(errorValue + "deviceKey");
		} else {
			errorValue = StringUtil.capitalize(errorValue + "deviceId");
		}

		// SC 회원 기기변경이력 조회 기능 호출.
		SearchChangedDeviceRequest searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setUserKey(request.getUserKey());
		searchChangedDeviceRequest.setDeviceKey(request.getDeviceKey());
		searchChangedDeviceRequest.setDeviceID(request.getDeviceId());
		searchChangedDeviceRequest.setCommonRequest(commonRequest);

		LOGGER.debug("[DeviceSCIController.searchChangedDeviceHistory] SC Request userSCI.searchChangedDevice : {}", searchChangedDeviceRequest);
		SearchChangedDeviceResponse searchChangedDeviceResponse = this.userSCI.searchChangedDevice(searchChangedDeviceRequest);

		ChangedDeviceHistorySacRes changedDeviceHistorySacRes = new ChangedDeviceHistorySacRes();

		if (searchChangedDeviceResponse != null && searchChangedDeviceResponse.getChangedDeviceLog() != null
				&& StringUtils.isNotBlank(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey())) {
			LOGGER.debug("[DeviceSCIController.searchChangedDeviceHistory] SC Response userSCI.searchChangedDevice : {}",
					searchChangedDeviceResponse.getChangedDeviceLog());
			changedDeviceHistorySacRes.setDeviceKey(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey());
			changedDeviceHistorySacRes.setIsChanged(searchChangedDeviceResponse.getChangedDeviceLog().getIsChanged());
		} else {
			// Response DeviceKey값이 null일 경우, UserKey or DeviceId or DeviceKey 불량
			throw new StorePlatformException("SAC_MEM_0003", errorValue, "오류");
		}
		return changedDeviceHistorySacRes;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.DeviceService#getIcasImei
	 * (java.lang.String)
	 */
	@Override
	public String getIcasImei(String deviceId) {

		String icasImei = null;

		if (!StringUtil.equals(this.commService.getMappingInfo(deviceId, "mdn").getMvnoCD(), "0")) { // MVNO

			GetMvnoEcRes mvnoRes = this.commService.getMvService(deviceId);
			icasImei = mvnoRes.getImeiNum();

		} else {

			GetCustomerEcRes costomerRes = this.commService.getCustomer(deviceId);
			icasImei = costomerRes.getImeiNum();

		}

		LOGGER.info("{} ICAS 연동 icasImei : {}", deviceId, icasImei);

		return icasImei;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * isEqualsLoginDevice(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean isEqualsLoginDevice(String deviceId, String reqVal, String dbVal, String equalsType) {

		// DB에 값이 없을 경우 아래와 같이 처리
		// 1. gmail : 불일치. 단, 단말에서 gmail 없이 올라온 경우 일치로 판별
		// 2. 통신사, IMEI : 일치.
		boolean isEquals = false;

		if (StringUtil.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_TELECOM)) {

			if (StringUtil.isBlank(dbVal) || StringUtil.equals(reqVal, dbVal)) {
				isEquals = true;
			}

		} else if (StringUtil.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

			if (StringUtil.isBlank(dbVal) || StringUtil.equals(reqVal, dbVal)) {
				isEquals = true;
			}

		} else if (StringUtil.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

			if ((StringUtil.isBlank(reqVal) && StringUtil.isBlank(dbVal)) || StringUtil.equals(reqVal, dbVal)) {
				isEquals = true;
			}

		}

		LOGGER.info("{} {} 일치여부 : {}, request : {}, db : {}", deviceId, equalsType, isEquals, reqVal, dbVal);

		return isEquals;
	}
}
