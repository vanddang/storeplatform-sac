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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

//import com.skplanet.storeplatform.framework.core.util.StringUtil;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	@Autowired
	MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private IDPService idpService; // IDP 연동 클래스

	@Autowired
	private ImIDPService imIdpService; // 통합 IDP 연동 클래스

	@Autowired
	private UserService userService;

	@Autowired
	private IDPRepository idpRepository;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private MemberCommonComponent mcc;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#createDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq)
	 */
	@Override
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		req.getDeviceInfo().setDeviceModelNo(requestHeader.getDeviceHeader().getModel()); // 단말모델
		/* 헤더로 받는 부가 속성 정보(os버젼) */
		List<DeviceExtraInfo> userDeviceExtraInfo = req.getDeviceInfo().getUserDeviceExtraInfo();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
		deviceExtraInfo.setExtraProfileValue(requestHeader.getDeviceHeader().getOsVersion());
		userDeviceExtraInfo.add(deviceExtraInfo);
		req.getDeviceInfo().setUserDeviceExtraInfo(userDeviceExtraInfo);

		String userKey = req.getUserKey();
		String deviceId = req.getDeviceInfo().getDeviceId();
		String deviceKey = null;

		/* 회원 정보 조회 */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(userKey);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("[" + schUserRes.getCommonResponse().getResultCode() + "] "
					+ schUserRes.getCommonResponse().getResultMessage());
		}

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음.");
		}

		if (req.getRegMaxCnt().equals("0")
				|| (schUserRes.getUserMbr().getDeviceCount() != null && Integer.parseInt(schUserRes.getUserMbr()
						.getDeviceCount()) >= Integer.parseInt(req.getRegMaxCnt()))) {
			throw new RuntimeException("등록 가능한 단말개수가 초과되었습니다.");
		}

		/* 동일한 정보로 등록 요청시 체크 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");// 대표기기만 조회(Y), 모든기기 조회(N)
		listDeviceReq.setUserKey(userKey);
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);
		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		if (deviceInfoList != null) {
			for (DeviceInfo deviceInfo : deviceInfoList) {
				if (deviceInfo.getDeviceId().equals(deviceId)) {
					throw new RuntimeException("이미 등록된 휴대기기 입니다.");
				}
			}
		}

		/* 휴대기기 주요정보 확인 */
		req.setDeviceInfo(this.getDeviceMajorInfo(req.getDeviceInfo()));

		/* 휴대기기 등록 처리 */
		deviceKey = this.insertDeviceInfo(commonRequest.getSystemID(), commonRequest.getTenantID(), userKey,
				req.getDeviceInfo());

		/* 변경된 정보 idp 연동 */
		this.userService.modifyProfileIdp(requestHeader.getTenantHeader().getSystemId(), requestHeader
				.getTenantHeader().getTenantId(), userKey, req.getUserAuthKey());

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
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#modifyDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq)
	 */
	@Override
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, ModifyDeviceReq req) throws Exception {

		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		req.getDeviceInfo().setDeviceModelNo(requestHeader.getDeviceHeader().getModel()); // 단말모델

		/* 헤더로 받는 부가 속성 정보(os버젼) */
		List<DeviceExtraInfo> userDeviceExtraInfo = req.getDeviceInfo().getUserDeviceExtraInfo();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
		deviceExtraInfo.setExtraProfileValue(requestHeader.getDeviceHeader().getOsVersion());
		userDeviceExtraInfo.add(deviceExtraInfo);
		req.getDeviceInfo().setUserDeviceExtraInfo(userDeviceExtraInfo);

		/* 회원 정보 조회 */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(req.getUserKey());
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("[" + schUserRes.getCommonResponse().getResultCode() + "] "
					+ schUserRes.getCommonResponse().getResultMessage());
		}

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음.");
		}

		this.mergeDeviceInfo(requestHeader.getTenantHeader().getSystemId(), requestHeader.getTenantHeader()
				.getTenantId(), req.getDeviceInfo());

		/* userAuthKey가 넘오온 경우만 업데이트 처리 */
		if (req.getUserAuthKey() != null) {
			this.userService.modifyProfileIdp(requestHeader.getTenantHeader().getSystemId(), requestHeader
					.getTenantHeader().getTenantId(), req.getUserKey(), req.getUserAuthKey());
		}

		ModifyDeviceRes res = new ModifyDeviceRes();
		res.setDeviceKey(req.getDeviceKey());
		res.setUserKey(req.getUserKey());
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#listDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq)
	 */
	@Override
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl listDevice start ############################");

		/* 헤더 정보 셋팅 */
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
			DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), userKey);
			res.setUserId(deviceInfo.getUserId());
			res.setUserKey(deviceInfo.getUserKey());
			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			deviceInfoList.add(deviceInfo);
			res.setDeviceInfoList(deviceInfoList);
			return res;
		} else if (req.getDeviceKey() != null) {
			DeviceInfo deviceInfo = this.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID, req.getDeviceKey(), userKey);
			res.setUserId(deviceInfo.getUserId());
			res.setUserKey(deviceInfo.getUserKey());
			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			deviceInfoList.add(deviceInfo);
			res.setDeviceInfoList(deviceInfoList);
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

		logger.info("###### schDeviceListReq : " + schDeviceListReq);
		logger.info("###### schDeviceListReq.getKeySearchList() : " + schDeviceListReq.getKeySearchList());
		logger.info("###### schDeviceListReq.getCommonRequest() : " + schDeviceListReq.getCommonRequest());

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
		if (!schDeviceListRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schDeviceListRes.getCommonResponse().getResultCode() + "]"
					+ schDeviceListRes.getCommonResponse().getResultMessage());
		}

		/* response 셋팅 */
		res.setUserId(schDeviceListRes.getUserID());
		res.setUserKey(schDeviceListRes.getUserKey());

		if (schDeviceListRes.getUserMbrDevice() != null && schDeviceListRes.getUserMbrDevice().size() > 0) {

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				deviceInfoList.add(DeviceUtil.getConverterDeviceInfo(userMbrDevice));
			}
			res.setDeviceInfoList(deviceInfoList);

		}

		logger.info("######################## DeviceServiceImpl listDevice end ############################");

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#searchDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.lang.String, java.lang.String)
	 */
	@Override
	public DeviceInfo searchDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey)
			throws Exception {

		logger.info("######################## DeviceServiceImpl searchDevice start ############################");

		/* 헤더 정보 셋팅 */
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
		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

		if (!schDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schDeviceRes.getCommonResponse().getResultCode() + "]" + schDeviceRes.getCommonResponse().getResultMessage());
		}

		if (schDeviceRes.getUserMbrDevice() == null) {
			throw new Exception("휴대기기 정보가 없습니다.");
		}

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo = DeviceUtil.getConverterDeviceInfo(schDeviceRes.getUserMbrDevice());
		deviceInfo.setUserId(schDeviceRes.getUserID());
		deviceInfo.setUserKey(schDeviceRes.getUserKey());

		logger.info("######################## DeviceServiceImpl searchDevice start ############################");

		return deviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# insertDeviceInfo(java.lang.String,
	 * java.lang.String, java.lang.String, com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo)
			throws Exception {

		logger.info("######################## DeviceServiceImpl insertDeviceInfo start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		/* 1. 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);

		deviceInfo.setTenantId(tenantId);

		logger.info("::::::::::::::::::::::::::::::::: insertDeviceInfo deviceInfo :::::::::::::::::::::::::::::::::::::");
		logger.info(deviceInfo.toString());
		logger.info(deviceInfo.getUserDeviceExtraInfo().toString());
		logger.info("::::::::::::::::::::::::::::::::: insertDeviceInfo deviceInfo :::::::::::::::::::::::::::::::::::::");

		createDeviceReq.setUserMbrDevice(DeviceUtil.getConverterUserMbrDeviceInfo(deviceInfo));

		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 2.휴대기기 정보 등록완료 */
		if (StringUtil.equals(createDeviceRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

			/* 3. 기등록된 회원이 존재하는지 확인 */
			if (createDeviceRes.getPreviousUserKey() != null) {

				String previousUserKey = createDeviceRes.getPreviousUserKey();
				String nowUserKey = createDeviceRes.getUserKey();

				/* 4. 구매이력 이관요청 */

				/* 5. 약관 이관 처리 */
				SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
				schAgreeListReq.setCommonRequest(commonRequest);
				schAgreeListReq.setUserKey(previousUserKey);
				SearchAgreementListResponse schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
				if (schAgreeListRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
					UpdateAgreementRequest updAgreeReq = new UpdateAgreementRequest();
					updAgreeReq.setCommonRequest(commonRequest);
					updAgreeReq.setUserKey(nowUserKey);
					updAgreeReq.setMbrClauseAgreeList(schAgreeListRes.getMbrClauseAgreeList());
					UpdateAgreementResponse updAgreeRes = this.userSCI.updateAgreement(updAgreeReq);

					if (!updAgreeRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
						throw new RuntimeException("약관저장실패 [" + updAgreeRes.getCommonResponse().getResultCode() + "]"
								+ updAgreeRes.getCommonResponse().getResultMessage());
					}
				} else {
					throw new RuntimeException("약관조회실패 [" + schAgreeListRes.getCommonResponse().getResultCode() + "]"
							+ schAgreeListRes.getCommonResponse().getResultMessage());
				}

				/* 6. 통합회원인 경우 무선회원 해지 */
				SearchUserRequest schUserReq = new SearchUserRequest();
				schUserReq.setCommonRequest(commonRequest);
				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				KeySearch key = new KeySearch();
				key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
				key.setKeyString(userKey);
				keySearchList.add(key);
				schUserReq.setKeySearchList(keySearchList);
				SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

				if (!schUserRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
					throw new RuntimeException("[" + schUserRes.getCommonResponse().getResultCode() + "] "
							+ schUserRes.getCommonResponse().getResultMessage());
				}

				/* 통합 회원 아이디에 휴대기기 등록 시 IDP에 무선 회원 해지 요청 - 자동 해지 안된다고 함 */
				if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) {
					IDPReceiverM idpReceiver = this.idpService.authForWap(deviceInfo.getDeviceId());
					if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

						idpReceiver = this.idpService.secedeUser4Wap(deviceInfo.getDeviceId());
						if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(),
								IDPConstants.IDP_RES_CODE_OK)) {
							throw new RuntimeException("IDP secedeForWap fail mdn : [" + deviceInfo.getDeviceId()
									+ "] result code : [" + idpReceiver.getResponseHeader().getResult() + "]");
						}
					}
				}

			}

			logger.info("######################## DeviceServiceImpl insertDeviceInfo end ############################");

			return createDeviceRes.getDeviceKey();
		} else {
			throw new RuntimeException("[" + createDeviceRes.getCommonResponse().getResultCode() + "] "
					+ createDeviceRes.getCommonResponse().getResultMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# mergeDeviceInfo(java.lang.String,
	 * java.lang.String, com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String mergeDeviceInfo(String systemId, String tenantId, DeviceInfo deviceInfo) throws Exception {

		logger.info("################ mergeDeviceInfo start ##################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		String userKey = deviceInfo.getUserKey();
		String deviceId = deviceInfo.getDeviceId();
		String deviceKey = deviceInfo.getDeviceKey();

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (deviceKey != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			key.setKeyString(deviceInfo.getDeviceKey());
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

		if (!schDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schDeviceRes.getCommonResponse().getResultCode() + "] "
					+ schDeviceRes.getCommonResponse().getResultMessage());
		}

		/* 휴대기기 주요정보 확인 */
		deviceInfo = this.getDeviceMajorInfo(deviceInfo);

		/* 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = deviceInfo.getDeviceAccount(); // gmailAddr
		String imMngNum = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_IMMNGNUM,
				deviceInfo.getUserDeviceExtraInfo()); // SKT 서비스 관리번호
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		String deviceNickName = deviceInfo.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = deviceInfo.getIsPrimary(); // 대표폰 여부
		String isRecvSms = deviceInfo.getIsRecvSms(); // sms 수신여부
		String rooting = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_ROOTING_YN,
				deviceInfo.getUserDeviceExtraInfo()); // rooting 여부

		logger.info(":::::::::::::::::: device merge field ::::::::::::::::::");

		if (deviceId != null && !deviceId.equals(userMbrDevice.getDeviceID())) {

			logger.info("[deviceId] {} -> {}", userMbrDevice.getDeviceID(), deviceId);
			userMbrDevice.setDeviceID(deviceId);

		}

		if (deviceModelNo != null && !deviceModelNo.equals(userMbrDevice.getDeviceModelNo())) {

			if (MemberConstants.DEVICE_TELECOM_SKT.equals(userMbrDevice.getDeviceTelecom())) {

				// 폰정보 조회 (deviceModelNo)
				Device device = this.commService.getPhoneInfo(deviceModelNo);

				// OMD 단말이 아닐 경우만
				if (!MemberConstants.DEVICE_TELECOM_OMD.equals(device.getCmntCompCd())) {

					IDPReceiverM idpReceiver = this.idpService.deviceCompare(deviceId);
					if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
						String idpModelId = idpReceiver.getResponseBody().getModel_id();

						if (idpModelId != null && !idpModelId.equals("")) {
							// 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
							if ("SSNU".equals(idpModelId)) { // SHW-M200K->SHW-M200S
								idpModelId = "SSNL";
							} else if ("SP05".equals(idpModelId)) { // SHW-M420K->SHW-M420S
								idpModelId = "SSO0";
							}

							// deviceInfo.setUacd(idpModelId);
						}

					} else {
						throw new Exception("[" + idpReceiver.getResponseHeader().getResult() + "] "
								+ idpReceiver.getResponseHeader().getResult_text());
					}

				}
			}
			logger.info("[deviceModelNo] {} -> {}", userMbrDevice.getDeviceModelNo(), deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

		}

		if (nativeId != null) {

			if (MemberConstants.DEVICE_TELECOM_SKT.equals(userMbrDevice.getDeviceTelecom())) {

				if (!nativeId.equals(userMbrDevice.getNativeID())) {
					logger.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(), nativeId);
					userMbrDevice.setNativeID(nativeId);
				}

				// 자번호 여부
				boolean isOpmd = StringUtils.substring(deviceId, 0, 3).equals("989");

				// 루팅 단말이고 OPMD 단말이 아닌 경우만 nativeId 체크
				if ("Y".equals(rooting) && !isOpmd) {

					// ICAS연동
					Map<String, String> mapIcas = null;

					String paramType = null;
					if (deviceInfo.getDeviceIdType().equals("msisdn")) {
						paramType = "11";
					} else if (deviceInfo.getDeviceIdType().equals("uuid")) {
						paramType = "12";
					} else {
						paramType = "13";
					}
					logger.info("::::  ICAS 연동 :::: deviceType {}, paramType {}", deviceInfo.getDeviceIdType(),
							paramType);
					if (!this.commService.getMappingInfo(deviceId, paramType).getMvnoCD().equals("0")) { // MVNO
						mapIcas = this.commService.getMvService(deviceId);
					} else {
						mapIcas = this.commService.getCustomer(deviceId);
					}

					if (mapIcas.get("RESULT_CODE").equals("0")) {

						// ICAS의 IMEI와 단말 IMEI값 불일치 시 로그인 실패
						if (!mapIcas.get("IMEI_NUM").equals(nativeId)) {
							throw new Exception("로그인에 실패하였습니다.(오류코드 4204).");
						}
					} else if (mapIcas.get("RESULT_CODE").equals("3162")) {
						throw new Exception("휴대폰 번호에 등록된 단말 정보가 일치하지 않아 T store 를 이용할 수 없습니다. T store 를 종료합니다."); // ICAS
																												  // 조회된
																												  // 회선정보
																												  // 없음
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
						throw new Exception("로그인에 실패하였습니다.(오류코드 4204)");
					}
				}
			}

		}

		if (deviceAccount != null && !deviceAccount.equals(userMbrDevice.getDeviceAccount())) {

			logger.info("[deviceAccount] {} -> {}", userMbrDevice.getDeviceAccount(), deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (imMngNum != null && !imMngNum.equals(userMbrDevice.getImMngNum())) {
			logger.info("[imMngNum] {} -> {}", userMbrDevice.getImMngNum(), imMngNum);
			userMbrDevice.setImMngNum(imMngNum);
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

		/* 휴대기기 부가정보 */
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());// 부가정보 등록시 셋팅할 deviceKey
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));

		logger.info(":::::::::::::::::: device merge field ::::::::::::::::::");

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (!createDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("휴대기기정보 업데이트 실패 [" + createDeviceRes.getCommonResponse().getResultCode() + "]"
					+ createDeviceRes.getCommonResponse().getResultMessage());

		}

		logger.info("################ mergeDeviceInfo end ##################");

		return createDeviceRes.getDeviceKey();

	}

	public DeviceInfo getDeviceMajorInfo(DeviceInfo deviceInfo) throws Exception {

		MajorDeviceInfo majorDeviceInfo = this.commService.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(),
				deviceInfo.getDeviceTelecom(), deviceInfo.getDeviceId(), deviceInfo.getDeviceIdType());

		deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());
		deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom());
		if (deviceInfo.getDeviceNickName() == null) {
			deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
		}

		List<DeviceExtraInfo> deviceExtraInfoList = deviceInfo.getUserDeviceExtraInfo();

		for (DeviceExtraInfo info : deviceExtraInfoList) {

			if (info.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_IMMNGNUM)) {
				if (majorDeviceInfo.getImMngNum() != null) {
					deviceExtraInfoList.remove(info);
					deviceExtraInfoList.add(DeviceUtil.addDeviceExtraInfo(MemberConstants.DEVICE_EXTRA_IMMNGNUM,
							majorDeviceInfo.getImMngNum(), deviceInfo));
				}

			} else if (info.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_UACD)) {
				if (majorDeviceInfo.getUacd() != null) {
					deviceExtraInfoList.remove(info);
					deviceExtraInfoList.add(DeviceUtil.addDeviceExtraInfo(MemberConstants.DEVICE_EXTRA_UACD,
							majorDeviceInfo.getUacd(), deviceInfo));
				}

			} else if (info.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_OMDUACD)) {
				if (majorDeviceInfo.getOmdUacd() != null) {
					deviceExtraInfoList.remove(info);
					deviceExtraInfoList.add(DeviceUtil.addDeviceExtraInfo(MemberConstants.DEVICE_EXTRA_OMDUACD,
							majorDeviceInfo.getOmdUacd(), deviceInfo));
				}
			}
		}

		deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList);
		return deviceInfo;
	}

	/**
	 * 
	 * 휴대기기 대표단말 설정
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	@Override
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req)
			throws Exception {

		SetMainDeviceRequest setMainDeviceRequest = new SetMainDeviceRequest();
		SetMainDeviceRes setMainDeviceRes = new SetMainDeviceRes();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		setMainDeviceRequest.setCommonRequest(commonRequest);

		/* userKey, deviceKey 회원존재여부 체크 */
		ExistReq existReq = new ExistReq();
		existReq.setUserKey(req.getUserKey());
		existReq.setDeviceKey(req.getDeviceKey());
		ExistRes existRes = this.userSearchService.exist(requestHeader, existReq);

		logger.info("###### 2. exist Request : {}", existReq.toString());
		logger.info("###### 2. exist Respone : {}", existRes.toString());

		if (existRes == null) {
			throw new RuntimeException("회원정보 없음.");
		}

		if (existRes.getUserKey() != null) {
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			if (!res.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
				throw new RuntimeException("result_code : [" + res.getCommonResponse().getResultCode()
						+ "] result_message : [" + res.getCommonResponse().getResultMessage() + "]");
			} else {
				setMainDeviceRes.setDeviceKey(req.getDeviceKey());
			}
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
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		logger.info("모번호 getOpmdMdnInfo : {}", this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/* SC 회원 정보 여부 */
		SearchUserResponse schUserRes = this.searchUser(requestHeader, req);

		/* 삭제요청한 디바이스 아이디로 디바이스 키 추출 */
		ListDeviceRes schDeviceListKeyResponse = this.searchDeviceKeyResponse(requestHeader, schUserRes, req);

		/* 디바이스 키 세팅 */
		RemoveDeviceRes removeDeviceRes = new RemoveDeviceRes();

		if (schDeviceListKeyResponse.getDeviceInfoList() != null
				&& schDeviceListKeyResponse.getDeviceInfoList().size() > 0) {
			removeDeviceRes.setDeviceKey(schDeviceListKeyResponse.getDeviceInfoList().get(0).getDeviceKey());
		} else {
			throw new RuntimeException("####### 디바이스 아이디로 검색한 디바이스 키가 없습니다.");
		}

		/* 휴대기기 목록 세팅 : 삭제대상 디바이스는 제외시킴 */
		List<DeviceInfo> deviceModifyList = this.deviceModifyList(requestHeader, req, schUserRes);

		/* IDP 연동 데이터 세팅 */
		String userPhoneStr = this.getUserPhoneStr(deviceModifyList);

		/* IDP 휴대기기 정보 등록 세팅 */
		HashMap<String, Object> param = this.getDeviceParam(req, schUserRes);
		RemoveDeviceResponse removeDeviceResponse = new RemoveDeviceResponse();

		/* 통합회원 삭제 */
		if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) {
			ImIDPReceiverM imIdpReceiver = this.imIdpDeviceUpdate(req, param, schUserRes, userPhoneStr);

			if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ 디바이스업데이트 IMIDP 연동결과 imIdpDeviceUpdate : "
						+ imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			} else {
				logger.info("###### 디바이스업데이트 [ImIDP] 연동결과 imIdpDeviceUpdate : Code : {}, Message : {} ", imIdpReceiver
						.getResponseHeader().getResult(), imIdpReceiver.getResponseHeader().getResult_text());
				removeDeviceResponse = this.removeDeviceSC(schUserRes, removeDeviceRes);
				logger.info("###### 디바이스 삭제 [ImIDP] SC Remove Req Code : {}, Mesasage : {}", removeDeviceResponse
						.getCommonResponse().getResultCode(), removeDeviceResponse.getCommonResponse()
						.getResultMessage());
			}

			/* IDP 삭제 */
		} else {

			param.put("key_type", "2");
			param.put("key", schUserRes.getUserMbr().getImMbrNo());
			if (userPhoneStr != null) {
				param.put("user_phone", userPhoneStr);
				param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
			}
			IDPReceiverM idpReceiver = this.idpService.modifyProfile(param);
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ 디바이스삭제 [IDP] 연동결과 : " + idpReceiver.getResponseHeader().getResult()
						+ "] " + idpReceiver.getResponseHeader().getResult_text());
			} else {
				removeDeviceResponse = this.removeDeviceSC(schUserRes, removeDeviceRes);
				logger.info("###### 디바이스 삭제 [IDP] SC Remove Req Code : {}, Mesasage : {}", removeDeviceResponse
						.getCommonResponse().getResultCode(), removeDeviceResponse.getCommonResponse()
						.getResultMessage());
			}

		}

		/* 월정액 탈퇴 */
		// 통신사가 SKT 일 경우만 부분유료화 월정액 해지요청
		// if (MemberConstants.DEVICE_TELECOM_SKT.equals(listDeviceIdRes.getDeviceInfoList().get(0).getDeviceTelecom()))
		// {
		// // 필링크 부분유료화 월정액 해지처리
		// HashMap<String, String> autopayRetireMap = new HashMap<String, String>();
		//
		// autopayRetireMap.put("svcMngNum", listDeviceIdRes.getDeviceInfoList().get(0).getImMngNum());
		// autopayRetireMap.put("mdn", req.getDeviceId());
		// logger.info("[부분유료화 월정액해지처리] svcMngNum = [" + listDeviceIdRes.getDeviceInfoList().get(0).getImMngNum()
		// + "]");
		// logger.info("[부분유료화 월정액해지처리] mdn = [" + req.getDeviceId() + "]");
		// // modifyConsumerInfoservice.autopayRetire(autopayRetireMap);
		// }

		/* 게임센터 연동 처리 */
		// logger.info("===============================================================");
		// logger.info("= START Game Center Data Sync");
		// logger.info("===============================================================");
		// logger.info("USER_DATA_SYNC_PARAM_MBR_NO   : " + member.getMbrNo());
		// logger.info("USER_DATA_SYNC_PARAM_MDN      : " + delMdn);
		// logger.info("USER_DATA_SYNC_PARAM_REQ_TYPE : " + Constants.GameCenter.USER_DATA_SYNC_REQ_TYPE_USER);
		// logger.info("USER_DATA_SYNC_PARAM_WORK_CD  : " + Constants.GameCenter.USER_DATA_SYNC_WORK_CD_05);
		// HashMap gameParam = new HashMap();
		// param.put(Constants.GameCenter.USER_DATA_SYNC_PARAM_MBR_NO, member.getMbrNo());
		// param.put(Constants.GameCenter.USER_DATA_SYNC_PARAM_MDN, delMdn);
		// param.put(Constants.GameCenter.USER_DATA_SYNC_PARAM_REQ_TYPE,
		// Constants.GameCenter.USER_DATA_SYNC_REQ_TYPE_USER);
		// param.put(Constants.GameCenter.USER_DATA_SYNC_PARAM_WORK_CD, Constants.GameCenter.USER_DATA_SYNC_WORK_CD_05);
		// boolean resultFlag = gameCenterUserDataSyncService.insertChangeData(param);
		// logger.info("===============================================================");
		// logger.info("= END Game Center Data Sync resultFlag : " + resultFlag);
		// logger.info("===============================================================");

		logger.info("######################## 결과 : " + removeDeviceResponse.getDelDeviceCount());
		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 End ############################");

		return removeDeviceRes; // 테스트용도로 deviceInfoList 추후 deviceKey Return 으로 변경
	}

	/**
	 * 입력된 Parameter로 회원존재여부 체크 한다. deviceId or userId && userKey
	 */
	@Override
	public SearchUserResponse searchUser(SacRequestHeader requestHeader, RemoveDeviceReq req) throws Exception {
		String deviceId = req.getDeviceId();

		SearchUserRequest schUserReq = new SearchUserRequest();
		SearchUserResponse schUserRes = new SearchUserResponse();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		/* userId & userAuthKey || userId 로 회원정보 조회 */
		if (!deviceId.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);

			logger.info("###### 회원조회 searchUser Request : {}", schUserReq.toString());
			schUserRes = this.userSCI.searchUser(schUserReq);
			logger.info("###### 회원조회 searchUser Responset : {}", schUserRes.toString());
		} else {
			throw new RuntimeException("파라미터 없음 userId, userAuthKey, deviceId");
		}

		// SC 컴포넌트에서 성공이 아닐때
		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("3. SC Member Search Fail : " + schUserRes.getCommonResponse().getResultCode()
					+ ", " + schUserRes.getCommonResponse().getResultMessage());
		} else {
			logger.info("회원조회 SC Member Search Success : {}, {}", schUserRes.getCommonResponse().getResultCode(),
					schUserRes.getCommonResponse().getResultMessage());
			logger.info("회원조회 SC Member Search Success Response {}: ", schUserRes.getUserMbr().toString());
		}

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음.");
		} else if (MemberConstants.SUB_STATUS_SECEDE_FINISH.equals(schUserRes.getUserMbr().getUserSubStatus())) {
			throw new RuntimeException("탈퇴완료 회원 : MainStatusCode [" + schUserRes.getUserMbr().getUserMainStatus() + "]"
					+ "SubStatusCode [" + schUserRes.getUserMbr().getUserSubStatus() + "]");
		} else {
			return schUserRes;
		}
	}

	/**
	 * 휴대기기 목록 조회 : 삭제요청한 디바이스를 제외하고 List로 세팅
	 */
	@Override
	public List<DeviceInfo> deviceModifyList(SacRequestHeader requestHeader, RemoveDeviceReq req,
			SearchUserResponse schUserRes) throws Exception {
		/* SC 회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		logger.info("###### Delete For Device Req : {}", req.toString());
		logger.info("###### UserInfo Res : {}", schUserRes.toString());

		if (req.getDeviceId() != null && !"".equals(req.getDeviceId())) {
			listDeviceReq.setDeviceId(req.getDeviceId());
		} else {
			throw new RuntimeException("Required Param DeviceId is Null");
		}
		listDeviceReq.setUserKey(schUserRes.getUserMbr().getUserKey());
		listDeviceReq.setIsMainDevice("N");
		ListDeviceRes listDeviceIdRes = this.listDevice(requestHeader, listDeviceReq);
		ListDeviceRes listDeviceKeyRes = new ListDeviceRes();

		logger.info("###### DeviceInfo(ID Search) List Res : {}", listDeviceIdRes.toString());

		if (listDeviceIdRes == null) {
			throw new RuntimeException("[ SC Device Search is Null");
		} else {
			/* DeviceKey로 다시 조회 */

			for (DeviceInfo deviceInfo : listDeviceIdRes.getDeviceInfoList()) {
				listDeviceReq.setDeviceKey(deviceInfo.getDeviceKey());
				listDeviceReq.setDeviceId(null);
				listDeviceReq.setUserId(null);
				listDeviceReq.setUserKey(null);
			}

			listDeviceKeyRes = this.listDevice(requestHeader, listDeviceReq);
			logger.info("###### DeviceInfo(Key Search) List Res : {}", listDeviceKeyRes.toString());
		}

		List<DeviceInfo> deviceModifyList = new ArrayList<DeviceInfo>();

		if (listDeviceKeyRes.getDeviceInfoList().size() > 0) {
			for (DeviceInfo deviceInfo : listDeviceKeyRes.getDeviceInfoList()) {

				/* 삭제요청한 휴대기기를 제외하고 리스트로 담는다. */
				if (!req.getDeviceId().equals(deviceInfo.getDeviceId())) {
					if (deviceInfo.getDeviceTelecom() != null && deviceInfo.getDeviceId() != null) {

						DeviceInfo info = deviceInfo;
						deviceModifyList.add(info);

						logger.info("4444444444444444 : {}", deviceInfo.getDeviceTelecom() + deviceInfo.getDeviceId());
						logger.info("4444444444444444 : {}", deviceInfo.getDeviceTelecom() + deviceInfo.getDeviceId());
						logger.info("4444444444444444 : {}", deviceInfo.getDeviceTelecom() + deviceInfo.getDeviceId());
						logger.info("4444444444444444 : {}", deviceInfo.getDeviceTelecom() + deviceInfo.getDeviceId());
						logger.info("4444444444444444 : {}", deviceInfo.getDeviceTelecom() + deviceInfo.getDeviceId());
					}

				}

			}
			logger.info("###### 디바이스 목록 : {}", deviceModifyList.toString());
			logger.info("###### 삭제요청 디바이스 : {}", req.getDeviceId());

		} else {
			logger.info("###### DeviceInfoList(Delete Device Except) List Res is Null");
		}
		logger.info("###### DeviceInfoList(Delete Device Except) List Res : {}", deviceModifyList.toString());
		return deviceModifyList;
	}

	/* 디바이스 아이디로 디바이스 키 추출 */
	@Override
	public ListDeviceRes searchDeviceKeyResponse(SacRequestHeader requestHeader, SearchUserResponse schUserRes,
			RemoveDeviceReq req) throws Exception {
		ListDeviceReq schDeviceListKeyRequest = new ListDeviceReq();
		schDeviceListKeyRequest.setUserKey(schUserRes.getUserMbr().getUserKey());
		schDeviceListKeyRequest.setIsMainDevice("N");
		schDeviceListKeyRequest.setDeviceId(req.getDeviceId());

		logger.info("###### 디바이스 목록에서 키 추출 listDevice From DeviceKey Request : ", schDeviceListKeyRequest);
		ListDeviceRes schDeviceListKeyResponse = this.listDevice(requestHeader, schDeviceListKeyRequest);
		logger.info("###### 디바이스 목록에서 키 추출 listDevice From DeviceKey Response : ", schDeviceListKeyResponse);

		return schDeviceListKeyResponse;
	}

	/* IDP 연동 데이터 세팅 */
	@Override
	public String getUserPhoneStr(List<DeviceInfo> deviceModifyList) throws Exception {
		String userPhoneStr = null;
		if (deviceModifyList != null) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : deviceModifyList) {

				String imMngNum = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_IMMNGNUM,
						deviceInfo.getUserDeviceExtraInfo());
				String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						deviceInfo.getUserDeviceExtraInfo());

				if (deviceInfo.getDeviceTelecom().equals("US001201") && imMngNum != null && uacd != null) {
					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					sbUserPhone.append(imMngNum == null ? "" : imMngNum);
					sbUserPhone.append(",");
					sbUserPhone.append(uacd == null ? "" : uacd);
					sbUserPhone.append(",");
					sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
					// TA에서 DB값 미변경으로 인한 하드코딩
					// if (deviceInfo.getDeviceTelecom().equals("US001201")) {
					//
					// sbUserPhone.append("SKT");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001202")) {
					// sbUserPhone.append("KTF");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001203")) {
					// sbUserPhone.append("LGT");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001204")) {
					// sbUserPhone.append("OMD");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001205")) {
					// sbUserPhone.append("NSH");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001206")) {
					// sbUserPhone.append("NON");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001207")) {
					// sbUserPhone.append("ISB");
					// } else {
					// throw new RuntimeException("통신사 코드 오류 해당 코드 미존재");
					// }

					sbUserPhone.append("|");
				} else if (!deviceInfo.getDeviceTelecom().equals("US001201")) {
					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					sbUserPhone.append(imMngNum == null ? "" : imMngNum);
					sbUserPhone.append(",");
					sbUserPhone.append(uacd == null ? "" : uacd);
					sbUserPhone.append(",");
					sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
					// TA에서 DB값 미변경으로 인한 하드코딩
					// if (deviceInfo.getDeviceTelecom().equals("US001201")) {
					// sbUserPhone.append("SKT");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001202")) {
					// sbUserPhone.append("KTF");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001203")) {
					// sbUserPhone.append("LGT");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001204")) {
					// sbUserPhone.append("OMD");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001205")) {
					// sbUserPhone.append("NSH");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001206")) {
					// sbUserPhone.append("NON");
					// } else if (deviceInfo.getDeviceTelecom().equals("US001207")) {
					// sbUserPhone.append("ISB");
					// } else {
					// throw new RuntimeException("통신사 코드 오류 해당 코드 미존재");
					// }
					sbUserPhone.append("|");
				} else if (deviceInfo.getDeviceTelecom() == null || deviceInfo.getDeviceId() == null) {
					logger.info("###### sbUserPhone Setting : DeviceId && DeviceTelecom is Null : {}",
							deviceInfo.getDeviceKey());
				} else {
					logger.info("###### sbUserPhone Setting : unKnown Error");
				}
			}
			userPhoneStr = sbUserPhone.toString();
			userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));

			logger.info("###### IDP 연동 데이터 삭제제외 : getUserPhoneStr {} ", userPhoneStr);
		}

		return userPhoneStr;
	}

	/* IDP 휴대기기 정보 등록 세팅 */
	@Override
	public HashMap<String, Object> getDeviceParam(RemoveDeviceReq req, SearchUserResponse schUserRes) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("user_auth_key", req.getUserAuthKey());

		UserMbr userMbr = schUserRes.getUserMbr();
		if (userMbr.getUserSex() != null) {
			param.put("user_sex", userMbr.getUserSex());
		}
		if (userMbr.getUserBirthDay() != null) {
			param.put("user_birthday", userMbr.getUserBirthDay());
		}
		if (userMbr.getUserZip() != null) {
			param.put("user_zipcode", userMbr.getUserZip());
		}
		if (userMbr.getUserAddress() != null) {
			param.put("user_address", userMbr.getUserAddress());
		}
		if (userMbr.getUserDetailAddress() != null) {
			param.put("user_address2", userMbr.getUserDetailAddress());
		}
		if (userMbr.getUserPhone() != null) {
			param.put("user_tel", userMbr.getUserPhone());
		}
		if (userMbr.getUserPhoneCountry() != null) {
			param.put("is_foreign", (userMbr.getUserPhoneCountry().equals("82") ? "N" : "Y"));
		}

		return param;
	}

	/* ImIdp 디바이스 업데이트(삭제대상 제외) */
	@Override
	public ImIDPReceiverM imIdpDeviceUpdate(RemoveDeviceReq req, HashMap<String, Object> param,
			SearchUserResponse schUserRes, String userPhoneStr) throws Exception {

		/* 통합 회원 아이디에 휴대기기 등록 시 IDP에 무선 회원 해지 요청 - 자동 해지 안된다고 함 */
		IDPReceiverM idpReceiver = this.idpService.authForWap(req.getDeviceId());
		if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

			idpReceiver = this.idpService.secedeUser4Wap(req.getDeviceId());
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("IDP secedeForWap fail mdn : [" + req.getDeviceId() + "] result code : ["
						+ idpReceiver.getResponseHeader().getResult() + "]");
			}
		}

		param.put("key_type", "1"); // IM 통합서비스 번호
		param.put("key", schUserRes.getUserMbr().getImSvcNo());
		param.put("operation_mode", this.IDP_OPERATION_MODE);
		if (userPhoneStr != null) {
			param.put("user_mdn", userPhoneStr);
			param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
		}

		param.put("modify_req_date", DateUtil.getDateString(new Date(), "yyyyMMddHH"));
		param.put("modify_req_time", DateUtil.getDateString(new Date(), "HHmmss"));

		ImIDPReceiverM imIdpReceiver = this.imIdpService.updateAdditionalInfo(param);
		if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
			throw new RuntimeException("[ 디바이스삭제 IMIDP 연동결과 : " + imIdpReceiver.getResponseHeader().getResult() + "] "
					+ imIdpReceiver.getResponseHeader().getResult_text());
		} else {
			logger.info("[ 디바이스삭제 IMIDP 연동결과 : {}, {}", imIdpReceiver.getResponseHeader().getResult(), imIdpReceiver
					.getResponseHeader().getResult_text());
		}

		return imIdpReceiver;
	}

	@Override
	public RemoveDeviceResponse removeDeviceSC(SearchUserResponse schUserRes, RemoveDeviceRes removeDeviceRes)
			throws Exception {
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		RemoveDeviceResponse removeDeviceResponse = new RemoveDeviceResponse();
		List<String> deviceKeyList = new ArrayList<String>();
		deviceKeyList.add(removeDeviceRes.getDeviceKey());

		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(schUserRes.getUserMbr().getUserKey());
		removeDeviceRequest.setDeviceKey(deviceKeyList);

		removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		if (!StringUtil.equals(removeDeviceResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("SC Device Remove Fail : "
					+ removeDeviceResponse.getCommonResponse().getResultCode() + ", "
					+ removeDeviceResponse.getCommonResponse().getResultMessage());
		} else {
			logger.info("SC Device Remove Success : {}, {}", removeDeviceResponse.getCommonResponse().getResultCode(),
					schUserRes.getCommonResponse().getResultMessage());
			logger.info("SC Device Remove Success Response {}: ", removeDeviceResponse.getDelDeviceCount());
		}

		return removeDeviceResponse;
	}
}
