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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcReq;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.SaveAndSync;

/**
 * 회원 로그인 관련 인터페이스 구현체.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private SaveAndSyncService saveAndSyncService;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Value("#{propertiesForSac['idp.mobile.user.auth.key']}")
	private String tempUserAuthKey;

	@Autowired
	private PurchaseUserInfoInternalSCI purchaseUserInfoInternalSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * authorizeByMdn
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes executeAuthorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		LOGGER.info("############################ LoginServiceImpl authorizeByMdn start ############################");

		String deviceId = req.getDeviceId();
		String userKey = null;
		String userType = null;
		String userMainStatus = null;
		String userSubStatus = null;
		String loginStatusCode = null;
		String stopStatusCode = null;

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 모번호 조회 */
		deviceId = this.commService.getOpmdMdnInfo(deviceId);

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId);

		/* 회원 존재유무 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", deviceId);

		}

		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		loginStatusCode = chkDupRes.getUserMbr().getLoginStatusCode();
		stopStatusCode = chkDupRes.getUserMbr().getStopStatusCode();

		/* 로그인 제한 인경우 */
		if (StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setIsLoginSuccess("N");
			return res;
		}

		/* 직권중지 인경우 */
		if (StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setIsLoginSuccess("N");
			return res;
		}

		/* 변동성 회원인 경우 */
		// if (StringUtils.equals(userType, MemberConstants.USER_TYPE_MOBILE) &&
		// StringUtils.equals(chkDupRes.getIsChangeSubject(), "Y")) {
		// this.volatileMemberPoc(requestHeader, deviceId, userKey, req.getDeviceTelecom());
		// }

		/* 원아이디인 경우 */
		if (chkDupRes.getUserMbr().getImSvcNo() != null) {

			/* 단말정보 update */
			this.updateLoginDeviceInfo(requestHeader, userKey, null, req);

			/* 로그인 성공이력 저장 */
			LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, deviceId, null, "Y", "Y", deviceId);

			/* 로그인 결과 */
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));
			res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

		} else { /* 기존IDP회원 / 모바일회원인 경우 */

			try {

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(deviceId);
				this.idpSCI.authForWap(authForWapEcReq);

				/* 단말정보 update */
				this.updateLoginDeviceInfo(requestHeader, userKey, null, req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, deviceId, null, "Y", "Y", deviceId);

				/* 로그인 결과 */
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setUserAuthKey(this.tempUserAuthKey);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {

					/* 미가입 회원인 경우 로그 님김 */
					LOGGER.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", deviceId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		LOGGER.info("############################ LoginServiceImpl authorizeByMdn end ############################");

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * authorizeByMdn
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes executeAuthorizeByMdnTempDev(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		LOGGER.info("############################ LoginServiceImpl authorizeByMdn start ############################");

		String isSignVariability = "N"; // 회원 변동성 여부

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 모번호 조회 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		/* 회원 존재유무 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
			/* 회원 변동성 처리 */
			isSignVariability = this.signVariabilityCheck(requestHeader, req);
		}

		if (StringUtil.equals(isSignVariability, "N")) { // 기존회원인 경우 로그인 제한/직권중지 체크

			if (StringUtils.equals(chkDupRes.getUserMbr().getLoginStatusCode(), MemberConstants.USER_LOGIN_STATUS_PAUSE)
					|| StringUtils.equals(chkDupRes.getUserMbr().getStopStatusCode(), MemberConstants.USER_STOP_STATUS_PAUSE)) {
				res.setUserKey(chkDupRes.getUserMbr().getUserKey());
				res.setUserType(chkDupRes.getUserMbr().getUserType());
				res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
				res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
				res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
				res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
				res.setIsLoginSuccess("N");
				return res;
			}

		} else {

			/* 변동성 회원인경우 DB회원 signVariabilityCheck에서 회원복구를 한 후이므로 재조회 한다. */
			chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());
			if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
				throw new StorePlatformException("", "변동성 회원 DB복구 실패");
			}

			/* 변동성 회원이고 SKT 통신사인경우 약관 동의 확인 */
			// TODO. 역관동의 여부 필드 정의 필요!!
			String isIcasAgree = "";
			if (StringUtil.equals(isIcasAgree, "N") && StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {
				throw new StorePlatformException("", "ICAS 약관 미동의 회원");
			}

		}

		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), null);
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		/* imei 비교 */
		if (!StringUtil.equals(deviceInfo.getNativeId(), req.getNativeId())) {

			if (StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {

				if (this.deviceService.isImeiEquality(req.getDeviceId(), req.getNativeId())) {
					//TODO. IMEI 값수정
					userMbrDevice.setNativeID(req.getNativeId());
				} else {
					throw new StorePlatformException("SAC_MEM_1503");
				}
			} else {
				throw new StorePlatformException("SAC_MEM_1504");
			}

		}

		/* 통신사 비교 */
		if (StringUtil.equals(deviceInfo.getDeviceTelecom(), req.getDeviceTelecom())) {

			String isFirstAppRun = ""; //TODO. 앱 최초구동여부 필드
			if (StringUtil.equals(isFirstAppRun, "N") && StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {
				//TODO. gmail, 모델명 수정
				userMbrDevice.setDeviceAccount(req.getDeviceAccount());
				userMbrDevice.setDeviceModelNo(requestHeader.getDeviceHeader().getModel());
			} else { // 타사이며 최초 구동된 경우
				if (StringUtil.equals(deviceInfo.getDeviceAccount(), req.getDeviceAccount())) {
					//TODO. 통신사, gmail, 모델명 수정
					userMbrDevice.setDeviceTelecom(req.getDeviceTelecom());
					userMbrDevice.setDeviceAccount(req.getDeviceAccount());
					userMbrDevice.setDeviceModelNo(requestHeader.getDeviceHeader().getModel());
				} else {
					//TODO. 인증수단 조회는 어떻게??
					throw new StorePlatformException("로그인실패 인증수단 정보 조회하여 내려줌");
				}
			}
		}

		/* 기기정보 업데이트 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		this.deviceSCI.createDevice(createDeviceReq);

		/* 원아이디인 경우 */
		if (chkDupRes.getUserMbr().getImSvcNo() != null) {

			/* 단말정보 update */
			//this.updateLoginDeviceInfo(requestHeader, chkDupRes.getUserMbr().getUserKey(), null, req);

			/* 로그인 성공이력 저장 */
			LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId());

			/* 로그인 결과 */
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
			res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), chkDupRes.getUserMbr()
					.getUserKey()));
			res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

		} else { /* 기존IDP회원 / 모바일회원인 경우 */

			try {

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(req.getDeviceId());
				this.idpSCI.authForWap(authForWapEcReq);

				/* 단말정보 update */
				//this.updateLoginDeviceInfo(requestHeader, chkDupRes.getUserMbr().getUserKey(), null, req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId());

				/* 로그인 결과 */
				res.setUserKey(chkDupRes.getUserMbr().getUserKey());
				res.setUserType(chkDupRes.getUserMbr().getUserType());
				res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
				res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
				res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
				res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
				res.setUserAuthKey(this.tempUserAuthKey);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), chkDupRes.getUserMbr()
						.getUserKey()));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {

					/* 미가입 회원인 경우 로그 님김 */
					LOGGER.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", req.getDeviceId(), chkDupRes.getUserMbr().getUserType());
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		LOGGER.info("############################ LoginServiceImpl authorizeByMdn end ############################");

		return res;

	}

	/**
	 * <pre>
	 * 변동성 회원 체크.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return String 변동성회원여부
	 */
	public String signVariabilityCheck(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId());

		if (StringUtil.equals(saveAndSync.getIsSaveAndSyncTarget(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

		} else {

			/* 단말정보 수정(gmail/통신사) */
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
			deviceInfo.setDeviceAccount(req.getDeviceAccount());
			this.deviceService.updateLoginDeviceInfo(requestHeader, deviceInfo);
			return "Y";
		}
	}

	/**
	 * <pre>
	 * MDN 로그인 시 변동성 대상 및 필수 주요 데이터를 점검한다.
	 * </pre>
	 * 
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return
	 */
	public String executeMdnLoginCheck(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		/* mdn 존재유무 확인 */
		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), null);

		if (deviceInfo == null) {

			/* 변동성 대상체크 */
			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId());

		} else {

			/* SKT인경우 icas연동하여 imei 비교 */
			if (StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
					&& !this.deviceService.isImeiEquality(req.getDeviceId(), req.getNativeId())) {

				/* 추가인증 수단을 조회하여 내려준다. */
				throw new StorePlatformException("", "추가인증수단 조회 내려줌");

			}

			/* imei 일 */
			if (StringUtil.isEmpty(deviceInfo.getNativeId()) || StringUtil.equals(deviceInfo.getNativeId(), req.getNativeId())) {

				if (StringUtil.isEmpty(deviceInfo.getDeviceTelecom()) || StringUtil.equals(req.getDeviceTelecom(), deviceInfo.getDeviceTelecom())) {

					//S/C 최초접속여부확인(테넌트한테 파라메터로 받을 예정)
					String isFirstJoin = "N";

					if (StringUtil.equals(isFirstJoin, "Y") && !StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {

						/* gmail 비교하여 gmail계정이 동일하면 동일사용자로 판단 */
						if (StringUtil.equals(deviceInfo.getDeviceAccount(), req.getDeviceAccount())) {
							return "";
						} else {
							/* 추가인증 수단을 조회하여 내려준다. */
							throw new StorePlatformException("", "추가인증수단 조회 내려줌");
						}

					}

				} else {

				}
				if (StringUtil.equals(req.getDeviceTelecom(), req.getDeviceTelecom())) {

				}

			} else {

				if (StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {

					/* gmail 비교하여 gmail계정이 동일하면 동일사용자로 판단 */
					if (StringUtil.equals(deviceInfo.getDeviceAccount(), req.getDeviceAccount())) {
						return "";
					} else {
						/* 추가인증 수단을 조회하여 내려준다. */
						throw new StorePlatformException("", "추가인증수단 조회 내려줌");
					}

				} else {
					throw new StorePlatformException("", "IMEI 값이 상이합니다.");
				}

			}

		}

		return "";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeById
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq)
	 */
	@Override
	public AuthorizeByIdRes executeAuthorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req) {

		LOGGER.info("############################ LoginServiceImpl authorizeById start ############################");

		String userId = req.getUserId();
		String userPw = req.getUserPw();
		String userKey = null;
		String userType = null;
		String userMainStatus = null;
		String userSubStatus = null;
		String loginStatusCode = null;
		String stopStatusCode = null;
		AuthorizeByIdRes res = new AuthorizeByIdRes();

		/* 모번호 조회 */
		if (req.getDeviceId() != null) {
			req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));
		}

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId);

		/* 원아이디 서비스 이용동의 간편 가입 대상 및 가가입 상태 체크 */
		if (!StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) {

			if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() != null) {

				try {

					/* 인증요청 */
					AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
					authForIdEcReq.setKey(userId);
					authForIdEcReq.setUserPasswd(userPw);
					AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);

					if (StringUtil.equals(authForIdEcRes.getCommonRes().getResult(), ImIdpConstants.IDP_RES_CODE_OK)) {

						throw new StorePlatformException("SAC_MEM_1200"); // 원아이디 이용동의 간편가입 대상 정보가 상이합니다.(SC회원 DB 미동의회원, IDP 동의회원)
						// TODO. FDS 로그 남김???

					} else if (StringUtil.equals(authForIdEcRes.getCommonRes().getResult(), ImIdpConstants.IDP_RES_CODE_INVALID_USER_INFO)) { // 가가입 상태인 경우 EC에서 성공으로 처리하여 joinSstList를 Response로 받는다.

						Map<String, String> mapSiteCd = new HashMap<String, String>();
						mapSiteCd.put("10100", "네이트");
						mapSiteCd.put("10200", "싸이월드");
						mapSiteCd.put("20100", "11st");
						mapSiteCd.put("30100", "멜론");
						mapSiteCd.put("40100", "Planet X 개발자센터");
						mapSiteCd.put("40300", "Smart Touch Platform");
						mapSiteCd.put("41000", "IDP");
						mapSiteCd.put("41100", "T store");
						mapSiteCd.put("41200", "T cloud");
						mapSiteCd.put("41300", "T map");
						mapSiteCd.put("41400", "SimpleSync");
						mapSiteCd.put("41500", "T-Ad");
						mapSiteCd.put("41600", "T-MapHot");
						mapSiteCd.put("41700", "J-Store");
						mapSiteCd.put("41800", "Gold-In-City");
						mapSiteCd.put("41900", "T-MapNavi");
						mapSiteCd.put("42100", "OK Cashbag");
						mapSiteCd.put("42200", "기프티콘");
						mapSiteCd.put("45000", "Landing Page");
						mapSiteCd.put("50000", "NAP");
						mapSiteCd.put("50100", "상생혁신센터");
						mapSiteCd.put("80100", "BSS");
						mapSiteCd.put("80200", "ISF");
						mapSiteCd.put("80300", "BoSS VOC");
						mapSiteCd.put("90000", "One ID");
						mapSiteCd.put("90100", "Admin");
						mapSiteCd.put("90200", "OAuth");
						mapSiteCd.put("90300", "One ID 사이트");
						mapSiteCd.put("90400", "mOTP");

						// 가가입 상태 - 가입신청 사이트 정보
						String joinSst = authForIdEcRes.getJoinSstList();
						String joinSstCd = null;
						String joinSstNm = null;

						for (Entry<String, String> entry : mapSiteCd.entrySet()) {
							if (StringUtils.contains(joinSst, entry.getKey())) {
								joinSstCd = entry.getKey();
								joinSstNm = entry.getValue();
								break;
							}
						}
						LOGGER.info(":::: {} 가가입 상태 사이트 정보 : {}, {}, {}", chkDupRes.getMbrOneID().getUserID(), joinSst, joinSstCd, joinSstNm);

						if (StringUtils.isEmpty(joinSstCd)) {
							joinSstCd = "90000"; // One ID
							joinSstNm = mapSiteCd.get(joinSstCd);
						}

						/* 로그인 결과 */
						res.setJoinSiteCd(joinSstCd);
						res.setJoinSiteNm(joinSstNm);
						res.setIsLoginSuccess("N");
						return res;

					}

				} catch (StorePlatformException ex) {

					if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER)) { // 서비스 간편가입 대상

						/* 로그인 결과 */
						res.setImIntSvcNo(chkDupRes.getMbrOneID().getIntgSvcNumber());
						res.setLoginStatusCode(chkDupRes.getMbrOneID().getLoginStatusCode());
						res.setStopStatusCode(chkDupRes.getMbrOneID().getStopStatusCode());
						res.setIsLoginSuccess("Y");
						return res;

					} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_WRONG_PASSWD)) { // 서비스 간편가입 대상이나 패스워드 틀림

						/* 로그인 결과 */
						res.setImIntSvcNo(chkDupRes.getMbrOneID().getIntgSvcNumber());
						res.setLoginStatusCode(chkDupRes.getMbrOneID().getLoginStatusCode());
						res.setStopStatusCode(chkDupRes.getMbrOneID().getStopStatusCode());
						res.setIsLoginSuccess("N");
						return res;

					} else {
						throw ex;
					}

				}

			} else {

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "userId", userId);
			}

		}

		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		loginStatusCode = chkDupRes.getUserMbr().getLoginStatusCode();
		stopStatusCode = chkDupRes.getUserMbr().getStopStatusCode();

		/* 로그인 제한상태인 경우 */
		if (!StringUtils.equals(req.getReleaseLock(), "Y") && StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setIsLoginSuccess("N");
			return res;
		}

		/* 직권중지 상태인 경우 */
		if (StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {

			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setIsLoginSuccess("N");
			return res;
		}

		/* 회원 인증 요청 */
		if (chkDupRes.getUserMbr().getImSvcNo() != null) { // 원아이디인 경우

			try {

				/* 잠금해지 요청인 경우 처리 */
				if (StringUtils.equals(req.getReleaseLock(), "Y") && StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);

					/* 통합IDP 로그인 상태 정상처리 요청 */
					SetLoginStatusEcReq setLoginStatusEcReq = new SetLoginStatusEcReq();
					setLoginStatusEcReq.setKey(userId);
					setLoginStatusEcReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);
					this.imIdpSCI.setLoginStatus(setLoginStatusEcReq);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 인증요청 */
				AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
				authForIdEcReq.setKey(userId);
				authForIdEcReq.setUserPasswd(userPw);
				AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);

				authForIdEcRes.getCommonRes().toString();

				/* 단말정보 update */
				this.updateLoginDeviceInfo(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				/* 로그인 결과 */
				res.setUserAuthKey(authForIdEcRes.getUserAuthKey());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, userKey));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils
						.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + ImIdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 님김 */
					LOGGER.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		} else { // 기존 IDP 계정인 경우

			try {

				/* 인증요청 */
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq authForIdEcReq = new com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq();
				authForIdEcReq.setUserId(userId);
				authForIdEcReq.setUserPasswd(userPw);
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcRes authForIdEcRes = this.idpSCI.authForId(authForIdEcReq);

				/* 잠금해지 요청인 경우 */
				if (StringUtils.equals(req.getReleaseLock(), "Y") && StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 단말정보 update */
				this.updateLoginDeviceInfo(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				/* 로그인 결과 */
				res.setUserAuthKey(authForIdEcRes.getUserAuthKey());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, userKey));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 님김 */
					LOGGER.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		LOGGER.info("######################## LoginServiceImpl authorizeById end ############################");

		return res;
	}

	/**
	 * 로그인한 deviceId의 deviceKey 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            조회타입
	 * @param keyString
	 *            조회값
	 * @param userKey
	 *            사용자키
	 * @return deviceKey 휴대기기 키
	 */
	public String getLoginDeviceKey(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		String deviceKey = null;
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_DEVICE_ID)) {
			listDeviceReq.setUserKey(userKey);
			listDeviceReq.setDeviceId(keyString);
		} else if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_INSD_USERMBR_NO)) { // 아이디 로그인시에는 대표기기의
																							// deviceKey 조회
			listDeviceReq.setUserKey(keyString);
			listDeviceReq.setIsMainDevice("Y");
		}

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			deviceKey = listDeviceRes.getDeviceInfoList().get(0).getDeviceKey();
		}

		return deviceKey;

	}

	/**
	 * SC회원콤포넌트 회원 정보 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            조회타입
	 * 
	 * @param keyString
	 *            조회값
	 * 
	 * @return CheckDuplicationResponse
	 * 
	 */
	public CheckDuplicationResponse searchUserInfo(SacRequestHeader requestHeader, String keyType, String keyString) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_DEVICE_ID)) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		} else if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_MBR_ID)) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		}
		key.setKeyString(keyString);
		keySearchList.add(key);

		CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
		chkDupReq.setCommonRequest(commonRequest);
		chkDupReq.setKeySearchList(keySearchList);

		CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);

		return chkDupRes;
	}

	/**
	 * 
	 * 휴대기기정보 update.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            사용자 키
	 * @param userAuthKey
	 *            IDP 키
	 * @param obj
	 *            요청객체
	 */
	public void updateLoginDeviceInfo(SacRequestHeader requestHeader, String userKey, String userAuthKey, Object obj) {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userKey);

		if (obj instanceof AuthorizeByMdnReq) { // mdn인증

			AuthorizeByMdnReq req = new AuthorizeByMdnReq();
			req = (AuthorizeByMdnReq) obj;

			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setDeviceIdType(req.getDeviceIdType());
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
			deviceInfo.setNativeId(req.getNativeId());
			deviceInfo.setIsNativeIdAuth(req.getIsNativeIdAuth());
			deviceInfo.setDeviceAccount(req.getDeviceAccount());
			deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
			this.deviceService.updateLoginDeviceInfo(requestHeader, deviceInfo);

		} else if (obj instanceof AuthorizeByIdReq) { // id인증

			AuthorizeByIdReq req = new AuthorizeByIdReq();
			req = (AuthorizeByIdReq) obj;

			if (req.getDeviceId() != null && !StringUtil.equals(req.getDeviceId(), "")) { // deviceId가 파라메터로 넘어왔을 경우에만 휴대기기 정보 update 요청

				deviceInfo.setDeviceId(req.getDeviceId());
				deviceInfo.setDeviceIdType(req.getDeviceIdType());
				deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
				deviceInfo.setNativeId(req.getNativeId());
				deviceInfo.setIsNativeIdAuth(req.getIsNativeIdAuth());
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
				deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
				this.deviceService.updateLoginDeviceInfo(requestHeader, deviceInfo);

				/* 변경된 휴대기기 정보 IDP도 변경 */
				this.userService.updateProfileIdp(requestHeader, userKey, userAuthKey);
			}

		}

	}

	/**
	 * 
	 * SC콤포넌트 로그인 이력저장.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userId
	 *            사용자아이디
	 * @param userPw
	 *            사용자패스워드
	 * @param isSuccess
	 *            로그인 성공유무
	 * @param isMobile
	 *            MDN 로그인 유무
	 * @param ipAddress
	 *            클라이언트 ip
	 * @return LoginUserResponse
	 */
	public LoginUserResponse insertLoginHistory(SacRequestHeader requestHeader, String userId, String userPw, String isSuccess, String isMobile,
			String ipAddress) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		LoginUserRequest loginReq = new LoginUserRequest();
		loginReq.setCommonRequest(commonRequest);
		loginReq.setUserID(userId);
		loginReq.setUserPW(userPw);
		loginReq.setIsSuccess(isSuccess);
		loginReq.setIsOneID("Y");
		loginReq.setIsMobile(isMobile);
		loginReq.setIsAutoLogin("N");
		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (svcVersion != null) {
			loginReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}
		loginReq.setIpAddress(ipAddress);

		return this.userSCI.updateLoginUser(loginReq);
	}

	/**
	 * 회원 상태코드 업데이트.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param loginStatusCode
	 *            로그인 상태코드
	 * @param keyType
	 *            조회타입
	 * @param keyString
	 *            조회값
	 */
	public void updateLoginStatus(SacRequestHeader requestHeader, String loginStatusCode, String keyType, String keyString) {

		UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);

		updStatusUserReq.setCommonRequest(commonRequest);
		updStatusUserReq.setKeySearchList(keySearchList);
		updStatusUserReq.setLoginStatusCode(loginStatusCode);

		this.userSCI.updateStatus(updStatusUserReq);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * executeAuthorizeForAutoUpdate
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet
	 * .storeplatform.sac.client.member.vo.user.AuthorizeForAutoUpdateReq)
	 */
	@Override
	public AuthorizeSimpleByMdnRes executeAuthorizeSimpleByMdn(SacRequestHeader requestHeader, AuthorizeSimpleByMdnReq req) {

		String deviceId = req.getDeviceId();

		/* 모번호 조회 */
		deviceId = this.commService.getOpmdMdnInfo(deviceId);

		/* 휴대기기 정보 조회 */
		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, null);

		AuthorizeSimpleByMdnRes res = new AuthorizeSimpleByMdnRes();

		if (deviceInfo != null) {

			res.setUserAuthKey(this.tempUserAuthKey);
			res.setUserKey(deviceInfo.getUserKey());
			res.setDeviceKey(deviceInfo.getDeviceKey());

			/* 로그인 히스토리 저장 */
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
			commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

			LoginUserRequest loginReq = new LoginUserRequest();
			loginReq.setCommonRequest(commonRequest);
			loginReq.setUserID(deviceId);
			loginReq.setUserPW(null);
			loginReq.setIsSuccess("Y");
			loginReq.setIsOneID("Y");
			loginReq.setIsMobile("Y");
			loginReq.setIsAutoLogin("Y");

			String svcVersion = requestHeader.getDeviceHeader().getSvc();
			if (svcVersion != null) {
				loginReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
			}
			loginReq.setIpAddress(deviceId);
			this.userSCI.updateLoginUser(loginReq);

		} else {

			res.setIsLoginSuccess("N");

		}

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * executeAuthorizeSaveAndSyncByMac
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet
	 * .storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq)
	 */
	@Override
	public AuthorizeSaveAndSyncByMacRes executeAuthorizeSaveAndSync(SacRequestHeader requestHeader, AuthorizeSaveAndSyncByMacReq req) {

		AuthorizeSaveAndSyncByMacRes res = new AuthorizeSaveAndSyncByMacRes();

		/* mac 정보 조회 */
		DeviceInfo macDeviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getMacAddress(), null);

		/* mac 정보가 없으면 완료 */
		if (macDeviceInfo == null) {
			res.setIsLoginSuccess("N");
			return res;
		}

		/* mdn 기가입 여부 확인 */
		DeviceInfo mdnDeviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), null);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		if (mdnDeviceInfo == null) { // mdn 미가입인 경우

			/* 변동성 대상체크 */
			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId());

			if (StringUtil.equals(saveAndSync.getIsSaveAndSyncTarget(), "Y")) { // 변동성 대상인 경우

				/* 구매내역 이관 */
				UserInfoSacInReq userInfoSacInReq = new UserInfoSacInReq();
				userInfoSacInReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
				userInfoSacInReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
				userInfoSacInReq.setDeviceKey(macDeviceInfo.getDeviceKey());
				userInfoSacInReq.setUserKey(macDeviceInfo.getUserKey());
				userInfoSacInReq.setNewDeviceKey(saveAndSync.getDeviceKey());
				userInfoSacInReq.setNewUserKey(saveAndSync.getUserKey());
				this.purchaseUserInfoInternalSCI.updateUserDevice(userInfoSacInReq);

				/* mac 정보 탈퇴처리 */
				RemoveUserRequest removeUserRequest = new RemoveUserRequest();
				removeUserRequest.setCommonRequest(commonRequest);
				removeUserRequest.setUserKey(macDeviceInfo.getUserKey());
				removeUserRequest.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_DEVICE);
				removeUserRequest.setSecedeReasonMessage("");
				this.userSCI.remove(removeUserRequest);

				res.setDeviceKey(saveAndSync.getDeviceKey());
				res.setUserKey(saveAndSync.getUserKey());
				res.setUserAuthKey(this.tempUserAuthKey);
				res.setIsLoginSuccess("Y");
				return res;

			} else { // 변동성 대상이 아닌 경우

				/* IDP 모바일전용회원 가입 */
				JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
				joinForWapEcReq.setUserMdn(req.getDeviceId());
				joinForWapEcReq.setMdnCorp(this.commService.convertDeviceTelecom(req.getDeviceTelecom()));
				JoinForWapEcRes joinForWapEcRes = this.idpSCI.joinForWap(joinForWapEcReq);

				/* mbrNo 변경 */
				UserMbr userMbr = new UserMbr();
				userMbr.setUserKey(macDeviceInfo.getUserKey());
				userMbr.setImMbrNo(joinForWapEcRes.getUserKey());
				UpdateUserRequest updateUserRequest = new UpdateUserRequest();
				updateUserRequest.setCommonRequest(commonRequest);
				updateUserRequest.setUserMbr(userMbr);
				this.userSCI.updateUser(updateUserRequest);

				/* 가가입 상태인 mac 회원정보를 정상상태로 */
				this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

				/* mac -> mdn으로 변경 처리 */
				CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
				createDeviceReq.setCommonRequest(commonRequest);
				UserMbrDevice userMbrDevice = new UserMbrDevice();
				userMbrDevice.setUserKey(macDeviceInfo.getUserKey());
				userMbrDevice.setDeviceKey(macDeviceInfo.getDeviceKey());
				userMbrDevice.setDeviceID(req.getDeviceId());
				userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE);
				createDeviceReq.setIsNew("N");
				CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

				res.setDeviceKey(createDeviceRes.getDeviceKey());
				res.setUserKey(createDeviceRes.getUserKey());
				res.setUserAuthKey(this.tempUserAuthKey);
				res.setIsLoginSuccess("Y");
				return res;

			}

		} else { // mdn 기가입인 경우

			/* 구매내역 이관 */
			UserInfoSacInReq userInfoSacInReq = new UserInfoSacInReq();
			userInfoSacInReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
			userInfoSacInReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
			userInfoSacInReq.setDeviceKey(macDeviceInfo.getDeviceKey());
			userInfoSacInReq.setUserKey(macDeviceInfo.getUserKey());
			userInfoSacInReq.setNewDeviceKey(mdnDeviceInfo.getDeviceKey());
			userInfoSacInReq.setNewUserKey(mdnDeviceInfo.getUserKey());
			this.purchaseUserInfoInternalSCI.updateUserDevice(userInfoSacInReq);

			/* mac 정보 탈퇴처리 */
			RemoveUserRequest removeUserRequest = new RemoveUserRequest();
			removeUserRequest.setCommonRequest(commonRequest);
			removeUserRequest.setUserKey(macDeviceInfo.getUserKey());
			removeUserRequest.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_DEVICE);
			removeUserRequest.setSecedeReasonMessage("");
			this.userSCI.remove(removeUserRequest);

			res.setDeviceKey(mdnDeviceInfo.getDeviceKey());
			res.setUserKey(mdnDeviceInfo.getUserKey());
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setIsLoginSuccess("Y");
			return res;
		}

	}
}
