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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcRes;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserAuthMethod;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ChangedDeviceHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ChangedDeviceHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
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

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 모번호 조회 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		/* 회원 존재유무 확인 */
		if (StringUtil.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

		}

		/* 로그인 제한 / 직권중지 인경우 */
		if (StringUtil.equals(chkDupRes.getUserMbr().getLoginStatusCode(), MemberConstants.USER_LOGIN_STATUS_PAUSE)
				|| StringUtil.equals(chkDupRes.getUserMbr().getStopStatusCode(), MemberConstants.USER_STOP_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
			res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
			res.setIsLoginSuccess("N");
			return res;
		}

		/* 휴대기기 정보 조회 */
		SearchDeviceResponse schDeviceRes = this.searchDeviceInfo(requestHeader, req.getDeviceId());
		String dbDeviceTelecom = schDeviceRes.getUserMbrDevice().getDeviceTelecom();

		/* 휴대기기 정보 수정 */
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(chkDupRes.getUserMbr().getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId()); // MDN
		deviceInfo.setDeviceIdType(req.getDeviceIdType()); // MDN Type
		deviceInfo.setDeviceAccount(req.getDeviceAccount()); // GMAIL
		deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 통신사
		deviceInfo.setNativeId(req.getNativeId()); // IMEI
		deviceInfo.setIsNativeIdAuth(req.getIsNativeIdAuth()); // IMEI 비교여부
		deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 휴대기기 부가속성 정보
		String deviceKey = this.deviceService.updateDeviceInfoForLogin(requestHeader, deviceInfo, schDeviceRes.getUserMbrDevice(), "v1");

		try {

			if (chkDupRes.getUserMbr().getImSvcNo() != null) { /* 원아이디인 경우 */

				/* 통신사가 변경되었을 때 변경되는 정보(통신사, 서비스관리번호)가 존재하므로 변경된 정보를 올려준다. */
				if (!StringUtil.equals(req.getDeviceTelecom(), dbDeviceTelecom)) {
					this.updateAdditionalInfoForMdnLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(), chkDupRes.getUserMbr().getImSvcNo());
				}

			} else { /* 기존IDP회원 / 모바일회원인 경우 */

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(req.getDeviceId());
				AuthForWapEcRes authForWapEcRes = this.idpSCI.authForWap(authForWapEcReq);
				LOGGER.info(authForWapEcRes.toString());

			}

		} catch (StorePlatformException ex) {
			LOGGER.info(ex.getErrorInfo().toString());
			if ((StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
					+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN))
					|| (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER))) {

				/* 미가입 회원인 경우 로그 남김 */
				LOGGER.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", req.getDeviceId(), chkDupRes.getUserMbr().getUserType());
				throw ex;

			} else {
				throw ex;
			}

		}

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
		res.setDeviceKey(deviceKey);
		res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * executeAuthorizeByMdnV2
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes executeAuthorizeByMdnV2(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 모번호 조회 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		/* 회원 존재유무 확인 */
		if (StringUtil.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

		}

		/* 로그인 제한 / 직권중지 인경우 */
		if (StringUtil.equals(chkDupRes.getUserMbr().getLoginStatusCode(), MemberConstants.USER_LOGIN_STATUS_PAUSE)
				|| StringUtil.equals(chkDupRes.getUserMbr().getStopStatusCode(), MemberConstants.USER_STOP_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
			res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
			res.setIsLoginSuccess("N");
			return res;
		}

		/* SKT인경우 개인정보 3자 제공 동의약관 동의여부 체크 */
		if (StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& !StringUtil.equals(this.isAgreementByAgreementCode(requestHeader, chkDupRes.getUserMbr().getUserKey(),
						MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_OTHERS), "Y")) {
			throw new StorePlatformException("SAC_MEM_1506"); // 개인정보 3자 제공 동의약관 미동의 상태입니다.
		}

		/* 휴대기기 정보 조회 */
		SearchDeviceResponse schDeviceRes = this.searchDeviceInfo(requestHeader, req.getDeviceId());
		String dbDeviceTelecom = schDeviceRes.getUserMbrDevice().getDeviceTelecom();

		/* Tcloud 약관동의 노출 여부 체크 */
		String tcloudAgreeViewYn = "N"; // Tcloud 이용동의 노출 여부
		ChangedDeviceHistoryReq changedDeviceHistoryReq = new ChangedDeviceHistoryReq();
		changedDeviceHistoryReq.setUserKey(schDeviceRes.getUserKey());
		changedDeviceHistoryReq.setDeviceId(req.getDeviceId());
		ChangedDeviceHistoryRes changedDeviceHistoryRes = this.deviceService.searchChangedDeviceHistory(requestHeader, changedDeviceHistoryReq);

		if (StringUtil.equals(changedDeviceHistoryRes.getIsChanged(), "Y")) {

			/*
			 * 최근 1개월 이내 기기변경이력이 있고 Tcloud 약관동의 되어있지 않은 경우 "T"로 임시 업데이트하여 Tcloud
			 * 약관동의 최초 한번만 노출되도록 처리
			 */
			String tcloudAgreeYn = DeviceUtil.getUserMbrDeviceDetailValue(MemberConstants.DEVICE_EXTRA_TCLOUD_SUPPORT_YN, schDeviceRes
					.getUserMbrDevice().getUserMbrDeviceDetail()); // Tcloud 약관동의 여부 

			if (StringUtil.isBlank(tcloudAgreeYn) || StringUtil.equals(tcloudAgreeYn, "N")) {

				tcloudAgreeViewYn = "Y";
				List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
				if (req.getDeviceExtraInfoList() != null) {
					deviceExtraInfoList = req.getDeviceExtraInfoList();
				}
				DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
				deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_TCLOUD_SUPPORT_YN);
				deviceExtraInfo.setExtraProfileValue("T");
				deviceExtraInfoList.add(deviceExtraInfo);
				req.setDeviceExtraInfoList(deviceExtraInfoList);

			}
		}

		/* 휴대기기 정보 수정 */
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(chkDupRes.getUserMbr().getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId()); // MDN
		deviceInfo.setDeviceIdType(req.getDeviceIdType()); // MDN Type
		deviceInfo.setDeviceAccount(req.getDeviceAccount()); // GMAIL
		deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 통신사
		deviceInfo.setNativeId(req.getNativeId()); // IMEI
		deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 휴대기기 부가속성 정보
		String deviceKey = this.deviceService.updateDeviceInfoForLogin(requestHeader, deviceInfo, schDeviceRes.getUserMbrDevice(), "v2");

		try {

			if (chkDupRes.getUserMbr().getImSvcNo() != null) { /* 원아이디인 경우 */

				/* 통신사가 변경되었을 때 변경되는 정보(통신사, 서비스관리번호)가 존재하므로 변경된 정보를 올려준다. */
				if (!StringUtil.equals(req.getDeviceTelecom(), dbDeviceTelecom)) {
					this.updateAdditionalInfoForMdnLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(), chkDupRes.getUserMbr().getImSvcNo());
				}

			} else { /* 기존IDP회원 / 모바일회원인 경우 */

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(req.getDeviceId());
				AuthForWapEcRes authForWapEcRes = this.idpSCI.authForWap(authForWapEcReq);
				LOGGER.info(authForWapEcRes.toString());

			}

		} catch (StorePlatformException ex) {
			LOGGER.info(ex.getErrorInfo().toString());
			if ((StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
					+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN))
					|| (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER))) {

				/* 미가입 회원인 경우 로그 남김 */
				LOGGER.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", req.getDeviceId(), chkDupRes.getUserMbr().getUserType());
				throw ex;

			} else {
				throw ex;
			}

		}

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
		res.setDeviceKey(deviceKey);
		res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());
		if (StringUtil.equals(tcloudAgreeViewYn, "Y")) {
			res.setTcloudAgreeViewYn(tcloudAgreeViewYn);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * executCheckVariability
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq)
	 */
	@Override
	public CheckVariabilityRes executCheckVariability(SacRequestHeader requestHeader, CheckVariabilityReq req) {

		CheckVariabilityRes res = new CheckVariabilityRes();

		/* 모번호 조회 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

		/* 휴대기기 정보 조회 */
		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), null);

		String isVariability = "Y"; // 변동성 체크 성공 유무

		if (deviceInfo == null) { // 회원아님

			/* 변동성 여부 조회 */
			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId());

			LOGGER.info("### {} SC회원 변동성 여부 : {}", req.getDeviceId(), saveAndSync.getIsSaveAndSyncTarget());

			if (StringUtil.equals(saveAndSync.getIsSaveAndSyncTarget(), "N")) {

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

			}

		} else {

			/* 통산사가 일치한 경우 IMEI와 GMAIL이 다르면 변동성 체크 실패 */
			if (this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceTelecom(), deviceInfo.getDeviceTelecom(),
					MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_TELECOM)) {

				if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getNativeId(), deviceInfo.getNativeId(),
						MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

					if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(), deviceInfo.getDeviceAccount(),
							MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

						isVariability = "N";

					}

				}

			} else { /* 통신사가 다른경우 GMAIL이 다르면 변동성 체크 실패 */

				if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(), deviceInfo.getDeviceAccount(),
						MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

					isVariability = "N";

				}

			}

		}

		LOGGER.info("### {} 변동성 체크 성공 여부 : {}", req.getDeviceId(), isVariability);

		if (StringUtil.equals(isVariability, "Y")) {

			/* 휴대기기 정보 수정 (통신사, GMAIL) */
			DeviceInfo paramDeviceInfo = new DeviceInfo();
			paramDeviceInfo.setUserKey(deviceInfo.getUserKey());
			paramDeviceInfo.setDeviceId(deviceInfo.getDeviceId());
			paramDeviceInfo.setDeviceTelecom(req.getDeviceTelecom());
			if (StringUtil.isNotEmpty(req.getDeviceAccount())) {
				paramDeviceInfo.setDeviceAccount(req.getDeviceAccount());
			}
			String deviceKey = this.deviceService.updateDeviceInfo(requestHeader, paramDeviceInfo);

			res.setDeviceKey(deviceKey);
			res.setUserKey(deviceInfo.getUserKey());
			res.setIsVariability("Y");

		} else {

			/* 인증수단 조회 */
			UserAuthMethod userAuthMethod = this.searchUserAuthMethod(requestHeader, req.getDeviceId());

			if (StringUtil.isBlank(userAuthMethod.getUserId())
					&& (StringUtil.isBlank(userAuthMethod.getIsRealName()) || StringUtil.equals(userAuthMethod.getIsRealName(), "N"))) { // 인증수단이 없는경우

				LOGGER.info("### {} 인증 수단 없음", req.getDeviceId());

				/* SC회원탈퇴 */
				RemoveUserRequest removeUserReq = new RemoveUserRequest();
				removeUserReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				removeUserReq.setUserKey(deviceInfo.getUserKey());
				removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
				this.userSCI.remove(removeUserReq);

				/* IDP 탈퇴 */
				SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
				ecReq.setUserMdn(req.getDeviceId());
				this.idpSCI.secedeForWap(ecReq);

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
			}

			res.setUserAuthMethod(userAuthMethod);
			res.setIsVariability("N");

		}

		return res;

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

		String userId = req.getUserId();
		String userPw = req.getUserPw();
		String userKey = null;
		String userAuthKey = null;
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
		if (!StringUtil.equals(chkDupRes.getIsRegistered(), "Y")) {

			if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() != null) {

				try {

					/* 인증요청 */
					AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
					authForIdEcReq.setKey(userId);
					authForIdEcReq.setUserPasswd(userPw);
					AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);
					LOGGER.info(authForIdEcRes.toString());

					if (StringUtil.equals(authForIdEcRes.getCommonRes().getResult(), ImIdpConstants.IDP_RES_CODE_OK)) {

						throw new StorePlatformException("SAC_MEM_1200"); // 원아이디 이용동의 간편가입 대상 정보가 상이합니다.(SC회원 DB 미동의회원, IDP 동의회원)

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
							if (StringUtil.contains(joinSst, entry.getKey())) {
								joinSstCd = entry.getKey();
								joinSstNm = entry.getValue();
								break;
							}
						}
						LOGGER.info(":::: {} 가가입 상태 사이트 정보 : {}, {}, {}", chkDupRes.getMbrOneID().getUserID(), joinSst, joinSstCd, joinSstNm);

						if (StringUtil.isEmpty(joinSstCd)) {
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

					LOGGER.info(ex.getErrorInfo().toString());

					if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER)) { // 서비스 간편가입 대상

						/* 로그인 결과 */
						res.setImIntSvcNo(chkDupRes.getMbrOneID().getIntgSvcNumber());
						res.setLoginStatusCode(chkDupRes.getMbrOneID().getLoginStatusCode());
						res.setStopStatusCode(chkDupRes.getMbrOneID().getStopStatusCode());
						res.setIsLoginSuccess("Y");
						return res;

					} else if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
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
		if (!StringUtil.equals(req.getReleaseLock(), "Y") && StringUtil.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

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
		if (StringUtil.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {

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
				if (StringUtil.equals(req.getReleaseLock(), "Y") && StringUtil.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

					/* 통합IDP 로그인 상태 정상처리 요청 */
					SetLoginStatusEcReq setLoginStatusEcReq = new SetLoginStatusEcReq();
					setLoginStatusEcReq.setKey(userId);
					setLoginStatusEcReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);
					SetLoginStatusEcRes setLoginStatusEcRes = this.imIdpSCI.setLoginStatus(setLoginStatusEcReq);
					LOGGER.info(setLoginStatusEcRes.toString());

					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);

					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 인증요청 */
				AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
				authForIdEcReq.setKey(userId);
				authForIdEcReq.setUserPasswd(userPw);
				AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);
				LOGGER.info(authForIdEcRes.toString());

				userAuthKey = authForIdEcRes.getUserAuthKey();

				/* 단말정보 update */
				// this.updateDeviceInfoForLogin(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

			} catch (StorePlatformException ex) {
				LOGGER.info(ex.getErrorInfo().toString());
				if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + ImIdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());
					return res;

				} else if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 남김 */
					LOGGER.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		} else { // 기존 IDP 계정인 경우

			try {

				/* 잠금해지 요청인 경우 */
				if (StringUtil.equals(req.getReleaseLock(), "Y") && StringUtil.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 인증요청 */
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq authForIdEcReq = new com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq();
				authForIdEcReq.setUserId(userId);
				authForIdEcReq.setUserPasswd(userPw);
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcRes authForIdEcRes = this.idpSCI.authForId(authForIdEcReq);
				LOGGER.info(authForIdEcRes.toString());

				userAuthKey = authForIdEcRes.getUserAuthKey();

				/* 단말정보 update */
				// this.updateDeviceInfoForLogin(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

			} catch (StorePlatformException ex) {

				LOGGER.info(ex.getErrorInfo().toString());

				if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());
					return res;

				} else if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 남김 */
					LOGGER.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		/* 로그인 성공이력 저장 */
		LoginUserResponse loginUserRes = this.insertLoginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

		/* 로그인 결과 */
		res.setUserAuthKey(userAuthKey);
		res.setUserKey(userKey);
		res.setUserType(userType);
		res.setUserMainStatus(userMainStatus);
		res.setUserSubStatus(userSubStatus);
		res.setLoginStatusCode(loginStatusCode);
		res.setStopStatusCode(stopStatusCode);
		res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, userKey));
		res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

		return res;
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
			if (StringUtil.isNotBlank(svcVersion)) {
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
	public AuthorizeSaveAndSyncByMacRes executeAuthorizeSaveAndSyncByMac(SacRequestHeader requestHeader, AuthorizeSaveAndSyncByMacReq req) {

		AuthorizeSaveAndSyncByMacRes res = new AuthorizeSaveAndSyncByMacRes();

		/* mac 정보 조회 */
		DeviceInfo macDeviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getMacAddress(), null);

		if (macDeviceInfo == null) {
			throw new StorePlatformException("SAC_MEM_0003", "macAddress", req.getMacAddress());
		}

		/* mdn 기가입 여부 확인 */
		DeviceInfo mdnDeviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId(), null);

		String isPurchaseChange = "N";
		String isJoinMdn = "N";

		if (mdnDeviceInfo == null) { // mdn 미가입인 경우

			/* 변동성 대상체크 */
			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId());
			LOGGER.info("::: mdn 미가입 변동성 대상 여부 : {} :::", saveAndSync.getIsSaveAndSyncTarget());
			if (StringUtil.equals(saveAndSync.getIsSaveAndSyncTarget(), "Y")) { // 변동성 대상인 경우

				isPurchaseChange = "Y";

			} else { // 변동성 대상이 아닌 경우

				isJoinMdn = "Y";

			}

		} else { // mdn 기가입인 경우

			LOGGER.info("::: mdn 기가입 :::");
			isPurchaseChange = "Y";

		}

		LOGGER.info("::: 변동성 구매이관 대상여부 {}", isPurchaseChange);
		LOGGER.info("::: 변동성 신규가입처리 대상여부 {}", isJoinMdn);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		if (StringUtil.equals(isPurchaseChange, "Y")) {

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

			/* 휴대기기 정보 수정 */
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUserKey(mdnDeviceInfo.getUserKey());
			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
			if (StringUtil.isNotBlank(req.getNativeId())) {
				deviceInfo.setNativeId(req.getNativeId());
			}
			if (StringUtil.isNotBlank(req.getDeviceAccount())) {
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
			}
			deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
			this.deviceService.updateDeviceInfo(requestHeader, deviceInfo);

			res.setDeviceKey(mdnDeviceInfo.getDeviceKey());
			res.setUserKey(mdnDeviceInfo.getUserKey());
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setIsLoginSuccess("Y");

		} else if (StringUtil.equals(isJoinMdn, "Y")) {

			/* IDP 모바일전용회원 가입 */
			JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
			joinForWapEcReq.setUserMdn(req.getDeviceId());
			joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT);

			JoinForWapEcRes joinForWapEcRes = null;
			try {

				joinForWapEcRes = this.idpSCI.joinForWap(joinForWapEcReq);

			} catch (StorePlatformException ex) {

				if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IdpConstants.IDP_RES_CODE_ALREADY_JOIN)) {

					/* IDP 기가입인경우 탈퇴 처리 */
					SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
					ecReq.setUserMdn(req.getDeviceId());
					this.idpSCI.secedeForWap(ecReq);

					throw new StorePlatformException("SAC_MEM_1201", ex);

				} else {

					throw ex;

				}
			}

			LOGGER.info(joinForWapEcRes.toString());

			/* mbrNo 변경 */
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(macDeviceInfo.getUserKey());
			userMbr.setImMbrNo(joinForWapEcRes.getUserKey());
			UpdateUserRequest updateUserRequest = new UpdateUserRequest();
			updateUserRequest.setCommonRequest(commonRequest);
			updateUserRequest.setUserMbr(userMbr);
			this.userSCI.updateUser(updateUserRequest);

			/* 가가입 상태인 mac 회원정보를 정상상태로 */
			this.updateMainStatus(requestHeader, MemberConstants.MAIN_STATUS_NORMAL, MemberConstants.KEY_TYPE_DEVICE_ID, req.getMacAddress());

			/* mac -> mdn으로 변경 처리 및 휴대기기 정보 수정 */
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUserKey(macDeviceInfo.getUserKey());
			deviceInfo.setDeviceKey(macDeviceInfo.getDeviceKey());
			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
			if (StringUtil.isNotBlank(req.getNativeId())) {
				deviceInfo.setNativeId(req.getNativeId());
			}
			if (StringUtil.isNotBlank(req.getDeviceAccount())) {
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
			}
			deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
			this.deviceService.updateDeviceInfo(requestHeader, deviceInfo);

			res.setDeviceKey(macDeviceInfo.getDeviceKey());
			res.setUserKey(macDeviceInfo.getUserKey());
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setIsLoginSuccess("Y");

		}

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
	private String getLoginDeviceKey(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		String deviceKey = null;
		ListDeviceReq listDeviceReq = new ListDeviceReq();

		if (StringUtil.equals(keyType, MemberConstants.KEY_TYPE_DEVICE_ID)) {
			listDeviceReq.setUserKey(userKey);
			listDeviceReq.setDeviceId(keyString);
		} else if (StringUtil.equals(keyType, MemberConstants.KEY_TYPE_INSD_USERMBR_NO)) { // 아이디 로그인시에는 대표기기의
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
	private CheckDuplicationResponse searchUserInfo(SacRequestHeader requestHeader, String keyType, String keyString) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		if (StringUtil.equals(keyType, MemberConstants.KEY_TYPE_DEVICE_ID)) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		} else if (StringUtil.equals(keyType, MemberConstants.KEY_TYPE_MBR_ID)) {
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
	private LoginUserResponse insertLoginHistory(SacRequestHeader requestHeader, String userId, String userPw, String isSuccess, String isMobile,
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

		if (StringUtil.isNotBlank(svcVersion)) {
			loginReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}
		loginReq.setIpAddress(ipAddress);

		return this.userSCI.updateLoginUser(loginReq);
	}

	/**
	 * 회원 로그인 상태코드 업데이트.
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
	private void updateLoginStatus(SacRequestHeader requestHeader, String loginStatusCode, String keyType, String keyString) {

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

	/**
	 * 회원 메인상태코드 업데이트.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param mbrStatusMainCd
	 *            메인 상태코드
	 * @param keyType
	 *            조회타입
	 * @param keyString
	 *            조회값
	 */
	private void updateMainStatus(SacRequestHeader requestHeader, String userMainStatus, String keyType, String keyString) {

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
		updStatusUserReq.setUserMainStatus(userMainStatus);

		this.userSCI.updateStatus(updStatusUserReq);

	}

	/**
	 * <pre>
	 * 특정 약관의 약관동의 여부 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @param agreementCode
	 *            String
	 * @return String 약관 동의 여부
	 */
	private String isAgreementByAgreementCode(SacRequestHeader requestHeader, String userKey, String agreementCode) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
		schAgreeListReq.setCommonRequest(commonRequest);
		schAgreeListReq.setUserKey(userKey);

		SearchAgreementListResponse schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
		for (MbrClauseAgree agreeInfo : schAgreeListRes.getMbrClauseAgreeList()) {
			if (StringUtil.equals(agreeInfo.getExtraAgreementID(), agreementCode)) {
				return agreeInfo.getIsExtraAgreement();
			}

		}

		return "N";

	}

	/**
	 * <pre>
	 * 통합아이디에 붙은 MDN 휴대기기 정보 수정.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @param imSvcNo
	 *            String
	 * @return UpdateAdditionalInfoEcRes
	 */
	private UpdateAdditionalInfoEcRes updateAdditionalInfoForMdnLogin(SacRequestHeader requestHeader, String userKey, String imSvcNo) {
		/* 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");
		listDeviceReq.setUserKey(userKey);

		String userPhoneStr = "";

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {

				String imMngNum = deviceInfo.getSvcMangNum();
				String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, deviceInfo.getDeviceExtraInfoList());

				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				sbUserPhone.append(imMngNum == null ? "" : imMngNum);
				sbUserPhone.append(",");
				sbUserPhone.append(uacd == null ? "" : uacd);
				sbUserPhone.append(",");
				sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
				sbUserPhone.append("|");
			}
			userPhoneStr = sbUserPhone.toString();
			userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
		}

		UpdateAdditionalInfoEcReq req = new UpdateAdditionalInfoEcReq();
		req.setExecuteMode("A");
		req.setKey(imSvcNo);
		req.setUserMdn(userPhoneStr);
		UpdateAdditionalInfoEcRes updAddInfoRes = this.imIdpSCI.updateAdditionalInfo(req);
		LOGGER.info(":::: UpdateAdditionalInfoEcRes : {}", updAddInfoRes.toString());

		return updAddInfoRes;
	}

	/**
	 * <pre>
	 * 변동성 회원 체크 실패인경우 인증한 수단을 조회한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @return UserAuthMethod
	 */
	private UserAuthMethod searchUserAuthMethod(SacRequestHeader requestHeader, String deviceId) {
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId);
		UserAuthMethod userAuthMethod = new UserAuthMethod();
		if (!StringUtil.equals(chkDupRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {
			userAuthMethod.setUserId(chkDupRes.getUserMbr().getUserID());
		}
		userAuthMethod.setIsRealName(chkDupRes.getUserMbr().getIsRealName());
		return userAuthMethod;
	}

	/**
	 * <pre>
	 * 로그인한 MDN의 휴대기기 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @return SearchDeviceResponse
	 */
	private SearchDeviceResponse searchDeviceInfo(SacRequestHeader requestHeader, String deviceId) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		key.setKeyString(deviceId);
		keySearchList.add(key);
		schDeviceReq.setCommonRequest(commonRequest);
		schDeviceReq.setKeySearchList(keySearchList);

		try {

			return this.deviceSCI.searchDevice(schDeviceReq);

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			} else {
				throw ex;
			}
		}

	}

}
