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
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

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
	private IDPRepository idpRepository;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private UAPSSCI uapsSCI;

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
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, CreateDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();
		String deviceId = req.getDeviceInfo().getDeviceId();

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

		if (req.getRegMaxCnt() == 0
				|| (schUserRes.getUserMbr().getDeviceCount() != null && Integer.parseInt(schUserRes.getUserMbr().getDeviceCount()) >= req
						.getRegMaxCnt())) {
			throw new RuntimeException("등록 가능한 단말개수가 초과되었습니다.");
		}

		req.getDeviceInfo().setDeviceModelNo(requestHeader.getDeviceHeader().getModel()); // 단말모델
		req.getDeviceInfo().setOsVer(requestHeader.getDeviceHeader().getOsVersion());// os버젼
		req.getDeviceInfo().setAuthenticationDate(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss")); // 인증일자
		req.getDeviceInfo().setIsAuthenticated("Y"); // 인증여부
		req.getDeviceInfo().setIsUsed("Y"); // 사용여부

		/* 휴대기기 등록 처리 */
		this.insertDeviceInfo(commonRequest.getSystemID(), commonRequest.getTenantID(), userKey, req.getDeviceInfo());

		/* sc회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");//대표기기만 조회(Y), 모든기기 조회(N)
		listDeviceReq.setUserKey(userKey);
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);

		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		String userPhoneStr = null;
		if (deviceInfoList != null) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : deviceInfoList) {
				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getImMngNum());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getUacd());
				sbUserPhone.append(",");
				sbUserPhone.append(this.converterTelecomCode(deviceInfo.getDeviceTelecom()));
				sbUserPhone.append("|");
			}
			userPhoneStr = sbUserPhone.toString();
			userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
		}

		/* IDP 휴대기기 정보 등록 요청 */
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

		if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) { // 통합회원

			param.put("cmd", "TXUpdateAdditionalUserInfoIDP");
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
				throw new RuntimeException("[" + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			}

		} else {

			param.put("key_type", "2");
			param.put("key", schUserRes.getUserMbr().getImMbrNo());
			if (userPhoneStr != null) {
				param.put("user_phone", userPhoneStr);
				param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
			}
			IDPReceiverM idpReceiver = this.idpService.modifyProfile(param);
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			}

		}

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		return null;
	}

	public String converterTelecomCode(String code) {
		String value = "";
		if (code.equals(MemberConstants.DEVICE_TELECOM_SKT)) {
			value = "SKT";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_KT)) {
			value = "KTF";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_LGT)) {
			value = "LGT";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_OMD)) {
			value = "OMD";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_NSH)) {
			value = "NSH";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_NON)) {
			value = "NON";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_IOS)) {
			value = "IOS";
		}
		return value;
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
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl listDevice start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userKey = req.getUserKey();

		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		schDeviceListReq.setUserKey(userKey);
		schDeviceListReq.setIsMainDevice(req.getIsMainDevice()); //대표기기만 조회(Y), 모든기기 조회(N)
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (req.getDeviceId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(req.getDeviceId());
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
		ListDeviceRes res = new ListDeviceRes();
		res.setUserId(schDeviceListRes.getUserID());
		res.setUserKey(schDeviceListRes.getUserKey());

		if (schDeviceListRes.getUserMbrDevice() != null && schDeviceListRes.getUserMbrDevice().size() > 0) {

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				deviceInfoList.add(this.getConverterDeviceInfo(userMbrDevice));
			}
			res.setDeviceInfoList(deviceInfoList);

		}

		logger.info("######################## DeviceServiceImpl listDevice end ############################");

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * insertDeviceInfo(java.lang.String, java.lang.String, java.lang.String,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public void insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo) throws Exception {

		logger.info("######################## DeviceServiceImpl insertDeviceInfo start ############################");

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		/* 휴대기기 주요정보 확인 */
		deviceInfo = this.setMajorDeviceInfo(deviceInfo);

		/* 1. 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);

		deviceInfo.setTenantId(tenantId);
		createDeviceReq.setUserMbrDevice(this.getConverterUserMbrDeviceInfo(deviceInfo));

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
						if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
							throw new RuntimeException("IDP secedeForWap fail mdn : [" + deviceInfo.getDeviceId() + "] result code : ["
									+ idpReceiver.getResponseHeader().getResult() + "]");
						}
					}
				}

			}

		} else {
			throw new RuntimeException("[" + createDeviceRes.getCommonResponse().getResultCode() + "] "
					+ createDeviceRes.getCommonResponse().getResultMessage());
		}

		logger.info("######################## DeviceServiceImpl insertDeviceInfo end ############################");
	}

	public DeviceInfo setMajorDeviceInfo(DeviceInfo deviceInfo) throws Exception {
		MajorDeviceInfo majorDeviceInfo = this.commService.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(), deviceInfo.getDeviceTelecom(),
				deviceInfo.getDeviceId(), deviceInfo.getDeviceIdType());

		deviceInfo.setImMngNum(majorDeviceInfo.getImMngNum() == null ? "" : majorDeviceInfo.getImMngNum()); // SKT 서비스 관리번호
		deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo()); // 기기 모델 번호
		deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName()); // 기기명
		deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom()); // 이동 통신사
		deviceInfo.setUacd(majorDeviceInfo.getUacd());// UA 코드
		deviceInfo.setOmdUacd(majorDeviceInfo.getOmdUacd());// OMD UA코드

		return deviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#
	 * mergeDeviceInfo(java.lang.String, java.lang.String,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public void mergeDeviceInfo(String systemId, String tenantId, DeviceInfo deviceInfo) throws Exception {

		logger.info("################ mergeDeviceInfo start ##################");

		if (deviceInfo.getDeviceId() == null) {

			throw new Exception("deviceId is null 기기정보 수정 불가");
		}

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		String deviceId = deviceInfo.getDeviceId();

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		key.setKeyString(deviceId);
		keySearchList.add(key);
		schDeviceReq.setCommonRequest(commonRequest);
		schDeviceReq.setUserKey(deviceInfo.getUserKey());
		schDeviceReq.setKeySearchList(keySearchList);

		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);
		UserMbrDevice userMbrDevice = schDeviceRes.getUserMbrDevice();

		if (!schDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schDeviceRes.getCommonResponse().getResultCode() + "] " + schDeviceRes.getCommonResponse().getResultMessage());
		}

		/* 휴대기기 주요정보 확인 */
		deviceInfo = this.setMajorDeviceInfo(deviceInfo);

		/* 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = deviceInfo.getDeviceAccount(); // gmailAddr
		String imMngNum = deviceInfo.getImMngNum(); // SKT 서비스 관리번호
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		String deviceNickName = deviceInfo.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = deviceInfo.getIsPrimary(); // 대표폰 여부
		String isRecvSms = deviceInfo.getIsRecvSms(); // sms 수신여부
		String rooting = deviceInfo.getRooting(); // rooting 여부

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

							deviceInfo.setUacd(idpModelId);
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
					logger.info("::::  ICAS 연동 :::: deviceType {}, paramType {}", deviceInfo.getDeviceIdType(), paramType);
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

		logger.info(":::::::::::::::::: device merge field ::::::::::::::::::");

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(this.getConverterUserMbrDeviceDetailList(deviceInfo));

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(schDeviceRes.getUserKey());
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (!createDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("휴대기기정보 업데이트 실패 [" + createDeviceRes.getCommonResponse().getResultCode() + "]"
					+ createDeviceRes.getCommonResponse().getResultMessage());

		}

		logger.info("################ mergeDeviceInfo end ##################");

	}

	/**
	 * 
	 * SC회원콤포넌트 휴대기기 정보 -> SAC 휴대기기 정보
	 * 
	 * @param userMbrDevice
	 * @return
	 */
	public DeviceInfo getConverterDeviceInfo(UserMbrDevice userMbrDevice) {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userMbrDevice.getUserKey());
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());
		deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
		deviceInfo.setTenantId(userMbrDevice.getTenantID());
		deviceInfo.setDeviceModelNo(userMbrDevice.getDeviceModelNo());
		deviceInfo.setImMngNum(userMbrDevice.getImMngNum());
		deviceInfo.setDeviceTelecom(userMbrDevice.getDeviceTelecom());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setIsPrimary(userMbrDevice.getIsPrimary());
		deviceInfo.setIsAuthenticated(userMbrDevice.getIsAuthenticated());
		deviceInfo.setAuthenticationDate(userMbrDevice.getAuthenticationDate());
		deviceInfo.setIsRecvSms(userMbrDevice.getIsRecvSMS());
		deviceInfo.setNativeId(userMbrDevice.getNativeID());
		deviceInfo.setDeviceAccount(userMbrDevice.getDeviceAccount());
		deviceInfo.setJoinId(userMbrDevice.getJoinId());
		deviceInfo.setIsUsed(userMbrDevice.getIsUsed());

		if (userMbrDevice.getUserMbrDeviceDetail() != null) {
			deviceInfo.setUserDeviceExtraInfo(this.getConverterDeviceInfoDetailList(userMbrDevice.getUserMbrDeviceDetail()));
		}

		return deviceInfo;
	}

	/**
	 * 
	 * SC회원콤포넌트 휴대기기 부가서비스 리스트정보 -> SAC 휴대기기 부가서비스 리스트정보
	 * 
	 * @param list
	 * @return
	 */
	public List<DeviceExtraInfo> getConverterDeviceInfoDetailList(List<UserMbrDeviceDetail> list) {

		List<DeviceExtraInfo> deviceExtraInfoList = null;
		DeviceExtraInfo deviceExtraInfo = null;

		if (list.size() > 0) {
			deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
			deviceExtraInfo = new DeviceExtraInfo();
		}

		for (UserMbrDeviceDetail deviceDetail : list) {

			deviceExtraInfo.setExtraProfile(deviceDetail.getExtraProfile());
			deviceExtraInfo.setExtraProfileValue(deviceDetail.getExtraProfileValue());
			deviceExtraInfoList.add(deviceExtraInfo);
		}

		return deviceExtraInfoList;

	}

	/**
	 * 
	 * SAC 휴대기기 정보 --> SC회원콤포넌트 휴대기기 정보
	 * 
	 * @param deviceInfo
	 * @return
	 */
	public UserMbrDevice getConverterUserMbrDeviceInfo(DeviceInfo deviceInfo) {

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(deviceInfo.getUserKey());
		userMbrDevice.setDeviceKey(deviceInfo.getDeviceKey());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());
		userMbrDevice.setTenantID(deviceInfo.getTenantId());
		userMbrDevice.setDeviceModelNo(deviceInfo.getDeviceModelNo());
		userMbrDevice.setImMngNum(deviceInfo.getImMngNum());
		userMbrDevice.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		userMbrDevice.setDeviceNickName(deviceInfo.getDeviceNickName());
		userMbrDevice.setIsPrimary(deviceInfo.getIsPrimary());
		userMbrDevice.setIsAuthenticated(deviceInfo.getIsAuthenticated());
		userMbrDevice.setAuthenticationDate(deviceInfo.getAuthenticationDate());
		userMbrDevice.setIsRecvSMS(deviceInfo.getIsRecvSms());
		userMbrDevice.setNativeID(deviceInfo.getNativeId());
		userMbrDevice.setDeviceAccount(deviceInfo.getDeviceAccount());
		userMbrDevice.setJoinId(deviceInfo.getJoinId());
		userMbrDevice.setIsUsed(deviceInfo.getIsUsed());
		userMbrDevice.setUserMbrDeviceDetail(this.getConverterUserMbrDeviceDetailList(deviceInfo));

		return userMbrDevice;

	}

	/**
	 * 
	 * SAC 휴대기기 부가서비스 리스트정보 --> SC회원콤포넌트 휴대기기 부가서비스 리스트정보
	 * 
	 * @param list
	 * @return
	 */
	public List<UserMbrDeviceDetail> getConverterUserMbrDeviceDetailList(DeviceInfo deviceInfo) {

		List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
		UserMbrDeviceDetail userMbrDeviceDetail = null;

		if (deviceInfo.getOmpDownloaderYn() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMPDOWNLOADER_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getOmpDownloaderYn());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getStandByScreenYn() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_STANDBYSCREEN_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getStandByScreenYn());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getUacd() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getUacd());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getOmpSupportYn() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMPSUPPORT_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getOmpSupportYn());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		// OS버전과 샵클버전 모두 NULL이 아닐경우에만 처리한다.
		if (deviceInfo.getOsVer() != null && deviceInfo.getScVer() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getOsVer());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);

			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getScVer());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getAppStatisticsYn() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_APPSTATISTICS_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getAppStatisticsYn());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getDotoriAuthDate() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getDotoriAuthDate());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getDotoriAuthYn() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getDotoriAuthYn());
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
		}

		if (deviceInfo.getRooting() != null) {
			userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
			userMbrDeviceDetail.setExtraProfileValue(deviceInfo.getRooting());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
			userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
			userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
		}

		return userMbrDeviceDetailList;

	}

	/**
	 * 
	 * 휴대기기 대표단말 설정
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	// @Override
	// public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req)
	// throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
	@Override
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req) throws Exception {

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

		if (existRes.getUserKey() != null) {
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			if (!res.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
				throw new RuntimeException("result_code : [" + res.getCommonResponse().getResultCode() + "] result_message : ["
						+ res.getCommonResponse().getResultMessage() + "]");
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
	public List<DeviceInfo> removeDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 start ############################");

		String userKey = req.getUserKey();
		String deviceKey = req.getDeviceKey();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* SC 회원 정보 조회 */
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
			throw new Exception("[" + schUserRes.getCommonResponse().getResultCode() + "] " + schUserRes.getCommonResponse().getResultMessage());
		}

		/* SC 회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setDeviceId(req.getDeviceId());
		listDeviceReq.setUserKey(userKey);
		ListDeviceRes listDeviceRes = this.listDevice(requestHeader, listDeviceReq);

		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		List<DeviceInfo> deviceModifyList = new ArrayList<DeviceInfo>();

		if (deviceInfoList.size() > 0) {
			for (DeviceInfo deviceInfo : deviceInfoList) {

				/* 삭제요청한 휴대기기를 제외하고 리스트로 담는다. */
				if (!req.getDeviceId().equals(deviceInfo.getDeviceId())) {
					DeviceInfo info = new DeviceInfo();
					info = deviceInfo;
					deviceModifyList.add(info);
				}

				logger.info("###### 삭제요청 디바이스 : " + req.getDeviceId());
				// logger.info("###### 삭제요청제외 디바이스 : " + deviceModifyList.get. getDeviceId());
			}

		}

		/* IDP 휴대기기 정보 등록 요청 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) { // 통합회원
			// TXUpdateAdditionalUserInfoIDP
		} else {
			// modifyProfile
			Map<String, Object> deviceParam = new HashMap<String, Object>();
			for (DeviceInfo info : deviceModifyList) {
				// sp_auth_key = (String) param.get("sp_auth_key");
				// key_type = (String) param.get("key_type");
				// key = (String) param.get("key");
				// user_auth_key = (String) param.get("user_auth_key");
				// user_job_code = (String) param.get("user_job_code");
				// user_zipcode = (String) param.get("user_zipcode");
				// user_address = (String) param.get("user_address");
				// user_address2 = (String) param.get("user_address2");
				// user_sex = (String) param.get("user_sex");
				// user_birthday = (String) param.get("user_birthday");
				// user_social_number = (String) param.get("user_social_number");
				// user_name = (String) param.get("user_name");
				// user_phone = (String) param.get("user_phone");
				// service_profile = (String) param.get("service_profile");
				// user_calendar = (String) param.get("user_calendar");
				// user_tel = (String) param.get("user_tel");
				// user_type = (String) param.get("user_type");
				// is_foreign = (String) param.get("is_foreign");
				// user_nation = (String) param.get("user_nation");
				// is_rname_auth = (String) param.get("is_rname_auth");
				// sn_auth_key = (String) param.get("sn_auth_key");
				// phone_auth_key = (String) param.get("phone_auth_key");
				// user_id = (String) param.get("user_id");
				// user_ci = (String) param.get("user_ci");

			}
			deviceParam.put("userKey", MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			deviceParam.put("userId", MemberConstants.KEY_TYPE_MBR_ID);
			deviceParam.put("deviceKey", MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			deviceParam.put("deviceId", MemberConstants.KEY_TYPE_DEVICE_ID);

			this.idpService.modifyProfile(deviceParam);
		}

		/* IDP 연동결과 성공이면 SC 휴대기기 정보 삭제 */
		/* 여러개의 휴대기기 삭제는 Req 수정해야 됨, 확인필요 */
		RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
		List<String> deviceKeyList = new ArrayList<String>();
		deviceKeyList.add(deviceKey);

		removeDeviceReq.setCommonRequest(commonRequest);
		removeDeviceReq.setUserKey(userKey);
		removeDeviceReq.setDeviceKey(deviceKeyList);

		this.deviceSCI.removeDevice(removeDeviceReq);

		/* 월정액 탈퇴 */

		/* 게임센터 연동 처리 */

		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 start ############################");

		return deviceModifyList;
	}

}
