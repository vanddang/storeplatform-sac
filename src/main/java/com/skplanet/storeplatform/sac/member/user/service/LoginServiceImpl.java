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

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.skplanet.storeplatform.external.client.example.daum.sci.DaumWebSearchSCI;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.AuthForIdEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcReq;
import com.skplanet.storeplatform.external.client.market.sci.MarketSCI;
import com.skplanet.storeplatform.external.client.market.vo.MarketAuthorizeEcReq;
import com.skplanet.storeplatform.external.client.market.vo.MarketAuthorizeEcRes;
import com.skplanet.storeplatform.external.client.market.vo.MarketClauseExtraInfoEc;
import com.skplanet.storeplatform.external.client.tstore.sci.TstoreTransferSCI;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSetSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SimpleLoginRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SimpleLoginResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MarketPinInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.TstoreEtcInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserAuthMethod;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberCommonInternalComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.vo.SaveAndSync;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;

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
	private UserSCI userSCI;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private UserService userService;

	@Autowired
	private SaveAndSyncService saveAndSyncService;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private UserWithdrawService userWithdrawService;

	@Autowired
	private MiscellaneousService miscellaneousService;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private DeviceSetSCI deviceSetSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MarketSCI marketSCI;

	@Autowired
	private DaumWebSearchSCI daumWebSearchSCI;

	@Autowired
	private MemberCommonInternalComponent mcic;

	@Autowired
	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	@Autowired
	private TstoreTransferSCI tstoreTransferSCI;

	@Value("#{propertiesForSac['idp.mobile.user.auth.key']}")
	private String tempUserAuthKey;

	@Value("#{propertiesForSac['member.user.pincode.set.url']}")
	private String pinCodeSetUrl;

	@Value("#{propertiesForSac['member.user.pincode.init.url']}")
	private String pinCodeInitUrl;

	@Value("#{propertiesForSac['member.user.pincode.confirm.url']}")
	private String pinCodeConfirmUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService# authorizeByMdn
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 자번호 셋팅(mdn 로그인시 deviceId는 자번호로 넘어온다) */
		String oDeviceId = req.getDeviceId();

		/* 모번호 조회 및 셋팅 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader,
				MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		/* 회원 존재유무 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

		}

		/* 휴대기기 정보 조회 */
		DeviceInfo dbDeviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
				req.getDeviceId(), chkDupRes.getUserMbr().getUserKey());

		/* 휴대기기 정보 수정 */
		String deviceKey = this.modDeviceInfoForLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(), req,
				oDeviceId, dbDeviceInfo, "v1");

		try {

			if (StringUtils.isNotBlank(chkDupRes.getUserMbr().getImSvcNo())) { // 원아이디인 경우

				/* 통신사가 변경되었을 때 변경되는 정보(통신사, 서비스관리번호)가 존재하므로 변경된 정보를 올려준다. */
				if (!StringUtils.equals(req.getDeviceTelecom(), dbDeviceInfo.getDeviceTelecom())) {
					this.userService.modAdditionalInfoForNonLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(),
							chkDupRes.getUserMbr().getImSvcNo());
				}

			} else { /* 기존IDP회원 / 모바일회원인 경우 */

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(req.getDeviceId());
				this.idpSCI.authForWap(authForWapEcReq);

			}

		} catch (StorePlatformException ex) {

			if ((StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
					+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN))
					|| (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER))) {

				/* 미가입 회원인 경우 로그 남김 */
				LOGGER.info("NOT_EXIST_USER authorizeByMdn devicdId : {}, {}", req.getDeviceId(), chkDupRes
						.getUserMbr().getUserType());
				throw ex;

			} else {
				throw ex;
			}

		}

		/* 로그인 성공이력 저장 */
		this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(),
				req.getIsAutoUpdate(), req.getLoginReason());

		/* 일시정지 인경우 (로그인제한상태 / 직권중지상태는 MDN로그인시는 샵클 정상이용가능하게 해야하므로 체크하지 않음) */
		if (StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
			res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
			res.setIsLoginSuccess("Y");
			return res;
		}

		/* 로그인 결과 */
		res.setUserKey(chkDupRes.getUserMbr().getUserKey());
		res.setUserType(chkDupRes.getUserMbr().getUserType());
		res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
		res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
		res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
		res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
		res.setUserAuthKey(this.tempUserAuthKey);
		res.setDeviceKey(deviceKey);
		res.setIsLoginSuccess("Y");

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService# executeAuthorizeByMdnV2
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes authorizeByMdnV2(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		/* 자번호 셋팅(mdn 로그인시 deviceId는 자번호로 넘어온다) */
		String oDeviceId = req.getDeviceId();

		/* 모번호 조회 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader,
				MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		/* 회원 존재유무 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

		}

		/* SKT인경우 개인정보 3자 제공 동의약관 동의여부 체크 */
		if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& !StringUtils.equals(this.isAgreementByAgreementCode(requestHeader, chkDupRes.getUserMbr()
						.getUserKey(), MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_OTHERS), "Y")) {
			throw new StorePlatformException("SAC_MEM_1506"); // 개인정보 3자 제공 동의약관 미동의 상태입니다.
		}

		/* 휴대기기 정보 조회 */
		DeviceInfo dbDeviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
				req.getDeviceId(), chkDupRes.getUserMbr().getUserKey());

		/* Tcloud 약관동의 노출 여부 체크 */
		// String tcloudAgreeViewYn = "N"; // Tcloud 이용동의 노출 여부
		// ChangedDeviceHistorySacReq changedDeviceHistoryReq = new ChangedDeviceHistorySacReq();
		// changedDeviceHistoryReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
		// changedDeviceHistoryReq.setDeviceId(req.getDeviceId());
		// ChangedDeviceHistorySacRes changedDeviceHistoryRes =
		// this.deviceService.srhChangedDeviceHistory(requestHeader, changedDeviceHistoryReq);
		//
		// if (StringUtils.equals(changedDeviceHistoryRes.getIsChanged(), "Y")) {
		//
		// /*
		// * 최근 1개월 이내 기기변경이력이 있고 Tcloud 약관동의 되어있지 않은 경우 "T"로 임시 업데이트하여 Tcloud
		// * 약관동의 최초 한번만 노출되도록 처리
		// */
		// String tcloudAgreeYn = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_TCLOUD_SUPPORT_YN,
		// dbDeviceInfo.getDeviceExtraInfoList()); // Tcloud 약관동의 여부
		//
		// if (StringUtils.isBlank(tcloudAgreeYn) || StringUtils.equals(tcloudAgreeYn, "N")) {
		//
		// tcloudAgreeViewYn = "Y";
		// List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		// if (req.getDeviceExtraInfoList() != null) {
		// deviceExtraInfoList = req.getDeviceExtraInfoList();
		// }
		// DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		// deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_TCLOUD_SUPPORT_YN);
		// deviceExtraInfo.setExtraProfileValue("T");
		// deviceExtraInfoList.add(deviceExtraInfo);
		// req.setDeviceExtraInfoList(deviceExtraInfoList);
		//
		// }
		// }

		/* 휴대기기 정보 수정 */
		String deviceKey = this.modDeviceInfoForLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(), req,
				oDeviceId, dbDeviceInfo, "v2");

		try {

			if (StringUtils.isNotBlank(chkDupRes.getUserMbr().getImSvcNo())) { // 원아이디인 경우

				/* 통신사가 변경되었을 때 변경되는 정보(통신사, 서비스관리번호)가 존재하므로 변경된 정보를 올려준다. */
				if (!StringUtils.equals(req.getDeviceTelecom(), dbDeviceInfo.getDeviceTelecom())) {
					this.userService.modAdditionalInfoForNonLogin(requestHeader, chkDupRes.getUserMbr().getUserKey(),
							chkDupRes.getUserMbr().getImSvcNo());
				}

			} else { /* 기존IDP회원 / 모바일회원인 경우 */

				/* 무선회원 인증 */
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(req.getDeviceId());
				this.idpSCI.authForWap(authForWapEcReq);

			}

		} catch (StorePlatformException ex) {

			if ((StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
					+ IdpConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN))
					|| (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
							+ ImIdpConstants.IDP_RES_CODE_UNAUTHORIZED_USER))) {

				/* 미가입 회원인 경우 로그 남김 */
				LOGGER.info("NOT_EXIST_USER authorizeByMdn devicdId : {}, {}", req.getDeviceId(), chkDupRes
						.getUserMbr().getUserType());
				throw ex;

			} else {
				throw ex;
			}

		}

		/* 로그인 성공이력 저장 */
		this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(),
				req.getIsAutoUpdate(), req.getLoginReason());

		/* 일시정지 인경우 (로그인제한상태 / 직권중지상태는 MDN로그인시는 샵클 정상이용가능하게 해야하므로 체크하지 않음) */
		if (StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
			res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
			res.setIsLoginSuccess("Y");
			return res;
		}

		/* 로그인 결과 */
		res.setUserKey(chkDupRes.getUserMbr().getUserKey());
		res.setUserType(chkDupRes.getUserMbr().getUserType());
		res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
		res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
		res.setLoginStatusCode(chkDupRes.getUserMbr().getLoginStatusCode());
		res.setStopStatusCode(chkDupRes.getUserMbr().getStopStatusCode());
		res.setUserAuthKey(this.tempUserAuthKey);
		res.setDeviceKey(deviceKey);
		res.setIsLoginSuccess("Y");

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService# executCheckVariability
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq)
	 */
	@Override
	public CheckVariabilityRes checkVariability(SacRequestHeader requestHeader, CheckVariabilityReq req) {

		CheckVariabilityRes res = new CheckVariabilityRes();
		String oDeviceId = req.getDeviceId(); // 자번호
		boolean isOpmd = this.commService.isOpmd(oDeviceId);
		String isVariability = "Y"; // 변동성 체크 성공 유무
		String isSaveAndSyncTarget = "N"; // 변동성 mdn 유무
		String userKey = null;
		String deviceKey = null;
		DeviceInfo deviceInfo = null;

		/* 모번호 조회 및 셋팅 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

		/* mdn 회원유무 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader,
				MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		if (StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) {

			if (!isOpmd) { // OPMD단말인경우 변동성 체크를 하지 않는다.
				/* 휴대기기 정보 조회 */
				deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
						req.getDeviceId(), null);

				userKey = deviceInfo.getUserKey();
				deviceKey = deviceInfo.getDeviceKey();

				/* 통산사가 일치한 경우 IMEI와 GMAIL이 다르면 변동성 체크 실패 */
				if (this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceTelecom(),
						deviceInfo.getDeviceTelecom(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_TELECOM)) {

					if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getNativeId(),
							deviceInfo.getNativeId(), MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

						if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(),
								deviceInfo.getDeviceAccount(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

							isVariability = "N";

						}

					}

				} else { /* 통신사가 다른경우 GMAIL이 다르면 변동성 체크 실패 */

					LOGGER.info("{} telecom 정보 수정 {} -> {}", req.getDeviceId(), deviceInfo.getDeviceTelecom(),
							req.getDeviceTelecom());
					if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(),
							deviceInfo.getDeviceAccount(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

						isVariability = "N";

					}

				}
			} else {
				LOGGER.info("{} {} OPMD 단말 변동성 성공처리", req.getDeviceId(), oDeviceId);
			}
		} else {

			/* 변동성 여부 조회 */
			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId(),
					req.getDeviceTelecom());
			isSaveAndSyncTarget = saveAndSync.getIsSaveAndSyncTarget();

			if (!StringUtils.equals(isSaveAndSyncTarget, "Y")) {

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

			}

			userKey = saveAndSync.getUserKey();
		}

		if (StringUtils.equals(isVariability, "Y")) {

			if (!isOpmd) { // OPMD 단말인경우 휴대기기 정보 업데이트를 하지 않는다.
				LOGGER.info("{} 변동성 체크 성공", req.getDeviceId());

				/* 휴대기기 정보 수정 (통신사, GMAIL) */
				DeviceInfo paramDeviceInfo = new DeviceInfo();
				paramDeviceInfo.setUserKey(userKey);
				paramDeviceInfo.setDeviceId(req.getDeviceId());
				if (StringUtils.isNotBlank(req.getDeviceTelecom())) {
					paramDeviceInfo.setDeviceTelecom(req.getDeviceTelecom());
				}
				if (StringUtils.isNotBlank(req.getDeviceAccount())) {
					paramDeviceInfo.setDeviceAccount(req.getDeviceAccount());
				}

				deviceKey = this.deviceService.modDeviceInfo(requestHeader, paramDeviceInfo, false);
				res.setDeviceKey(deviceKey);
			} else {
				res.setDeviceKey(deviceKey);
				LOGGER.info("{} {} OPMD 단말 변동성 휴대기기 업데이트 안함", req.getDeviceId(), oDeviceId);
			}
			res.setUserKey(userKey);

		} else {

			LOGGER.info("{} 변동성 체크 실패", req.getDeviceId());

			/* 인증수단 조회 */
			UserAuthMethod userAuthMethod = this.srhUserAuthMethod(requestHeader, req.getDeviceId(), userKey);

			if (StringUtils.isBlank(userAuthMethod.getUserId())
					&& StringUtils.equals(userAuthMethod.getIsRealName(), "N")) { // 인증수단이
																				  // 없는경우

				LOGGER.info("{} 추가인증수단 없음, 탈퇴처리", req.getDeviceId());

				/* SC회원탈퇴 */
				RemoveUserRequest removeUserReq = new RemoveUserRequest();
				removeUserReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				removeUserReq.setUserKey(userKey);
				removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
				removeUserReq.setSecedeReasonCode(MemberConstants.WITHDRAW_REASON_OTHER);
				removeUserReq.setSecedeReasonMessage("변동성인증수단없음");
				this.userSCI.remove(removeUserReq);

				/* IDP 탈퇴 */
				SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
				ecReq.setUserMdn(req.getDeviceId());
				this.idpSCI.secedeForWap(ecReq);

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

			} else {
				LOGGER.info("{} 추가인증수단 userId : {}, 실명인증여부 : {}", req.getDeviceId(), userAuthMethod.getUserId(),
						userAuthMethod.getIsRealName());
			}

			res.setUserAuthMethod(userAuthMethod);

		}

		res.setIsVariability(isVariability);
		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeById
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq)
	 */
	@Override
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req) {

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

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, MemberConstants.KEY_TYPE_MBR_ID,
				userId);

		/* 원아이디 서비스 이용동의 간편 가입 대상 및 가가입 상태 체크 */
		if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() == null) {

			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "userId", userId);

		} else if (chkDupRes.getMbrOneID() != null) {

			// 회원정보가 없거나 회원메인상태가 가가입이면 가가입정보를 내려줌
			if (chkDupRes.getUserMbr() == null
					|| StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(),
							MemberConstants.MAIN_STATUS_WATING)) {

				try {

					/* 인증요청 */
					AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
					authForIdEcReq.setKey(userId);
					authForIdEcReq.setUserPasswd(userPw);
					AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);

					if (StringUtils.equals(authForIdEcRes.getCommonRes().getResult(), ImIdpConstants.IDP_RES_CODE_OK)) {

						LOGGER.info("SC_INVALID_USER authorizeById userId : {}", userId);
						throw new StorePlatformException("SAC_MEM_1200"); // 원아이디 이용동의 간편가입 대상 정보가 상이합니다.

					} else if (StringUtils.equals(authForIdEcRes.getCommonRes().getResult(),
							ImIdpConstants.IDP_RES_CODE_INVALID_USER_INFO)) {

						// 가가입상태인 경우 EC에서 성공으로 처리하여 joinSstList 받는다.
						HashMap<String, String> invalidSiteInfo = this.getInvalidSiteInfo(userId,
								authForIdEcRes.getJoinSstList());

						/* 로그인 결과 */
						res.setJoinSiteCd(invalidSiteInfo.get("joinSstCd"));
						res.setJoinSiteNm(invalidSiteInfo.get("joinSstNm"));
						res.setIsLoginSuccess("Y");
						return res;

					}

				} catch (StorePlatformException ex) {

					if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
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
						res.setLoginFailCount("0");
						res.setIsLoginSuccess("N");
						return res;

					} else {
						throw ex;
					}

				}

			}

		}

		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		loginStatusCode = chkDupRes.getUserMbr().getLoginStatusCode();
		stopStatusCode = chkDupRes.getUserMbr().getStopStatusCode();

		/* 회원 인증 요청 */
		if (StringUtils.isNotBlank(chkDupRes.getUserMbr().getImSvcNo())) { // 원아이디인 경우

			try {

				/* 잠금해지 요청인 경우 처리 */
				if (StringUtils.equals(req.getReleaseLock(), "Y")
						&& StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

					/* 통합IDP 로그인 상태 정상처리 요청 */
					SetLoginStatusEcReq setLoginStatusEcReq = new SetLoginStatusEcReq();
					setLoginStatusEcReq.setKey(userId);
					setLoginStatusEcReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);
					this.imIdpSCI.setLoginStatus(setLoginStatusEcReq);

					/* 로그인 상태코드 정상처리 */
					this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId,
							MemberConstants.USER_LOGIN_STATUS_NOMAL, null, null, null);
					this.modOneIdInfo(requestHeader, chkDupRes.getUserMbr().getImSvcNo(),
							MemberConstants.USER_LOGIN_STATUS_NOMAL, null);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 인증요청 */
				AuthForIdEcReq authForIdEcReq = new AuthForIdEcReq();
				authForIdEcReq.setKey(userId);
				authForIdEcReq.setUserPasswd(userPw);
				AuthForIdEcRes authForIdEcRes = this.imIdpSCI.authForId(authForIdEcReq);

				if (StringUtils.equals(authForIdEcRes.getCommonRes().getResult(), ImIdpConstants.IDP_RES_CODE_OK)) {

					userAuthKey = authForIdEcRes.getUserAuthKey();

				} else if (StringUtils.equals(authForIdEcRes.getCommonRes().getResult(),
						ImIdpConstants.IDP_RES_CODE_INVALID_USER_INFO)) {

					// throw new StorePlatformException("SAC_MEM_1202"); // 원아이디 회원상태정보가 상이합니다.

					LOGGER.info("IDP_INVALID_USER authorizeById userId : {}, {}", userId);

					HashMap<String, String> invalidSiteInfo = this.getInvalidSiteInfo(userId,
							authForIdEcRes.getJoinSstList());

					/* 로그인 결과 */
					res.setJoinSiteCd(invalidSiteInfo.get("joinSstCd"));
					res.setJoinSiteNm(invalidSiteInfo.get("joinSstNm"));
					res.setIsLoginSuccess("Y");
					return res;

				}

				/* IDP 로그인이 정상으로 되었으나 직원중지코드가 정상이 아닌경우 정상으로 업데이트 */
				if (!StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_NOMAL)) {
					LOGGER.info("{} 직권중지코드가 상이하여 업데이트(stopStatusCode : {} -> {})", userId, stopStatusCode,
							MemberConstants.USER_STOP_STATUS_NOMAL);
					this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId, null,
							MemberConstants.USER_STOP_STATUS_NOMAL, null, null);
					this.modOneIdInfo(requestHeader, chkDupRes.getUserMbr().getImSvcNo(), null,
							MemberConstants.USER_STOP_STATUS_NOMAL);
					stopStatusCode = MemberConstants.USER_STOP_STATUS_NOMAL;
				}

				/* IDP 로그인이 정상으로 되었으나 로그인상태코드가 정상이 아닌경우 정상으로 업데이트 */
				if (!StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_NOMAL)) {
					LOGGER.info("{} 로그인제한상태코드가 상이하여 업데이트(loginStatusCode : {} -> {})", userId, loginStatusCode,
							MemberConstants.USER_LOGIN_STATUS_NOMAL);
					this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId,
							MemberConstants.USER_LOGIN_STATUS_NOMAL, null, null, null);
					this.modOneIdInfo(requestHeader, chkDupRes.getUserMbr().getImSvcNo(),
							MemberConstants.USER_LOGIN_STATUS_NOMAL, null);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 단말정보 update */
				// this.modDeviceInfoForLogin(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.regLoginHistory(requestHeader, userId, userPw, "N", "N",
							req.getIpAddress(), "N", null);

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess("N");
					return res;

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 남김 */
					LOGGER.info("NOT_EXIST_USER authorizeById userId : {}, {}", userId, userType);
					throw ex;

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_SUSPEND)
						|| StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
								+ ImIdpConstants.IDP_RES_CODE_SUSPEND_02)) { // 직권중지 상태인 경우

					if (!StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) { // IDP와 직권중지상태
																									   // 코드가 다를경우
																									   // 직권중지상태로 업데이트
						LOGGER.info("{} 직권중지상태코드가 상이하여 업데이트(stopStatusCode : {} -> {})", userId, stopStatusCode,
								MemberConstants.USER_STOP_STATUS_PAUSE);
						this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId, null,
								MemberConstants.USER_STOP_STATUS_PAUSE, null, null);
						this.modOneIdInfo(requestHeader, chkDupRes.getUserMbr().getImSvcNo(), null,
								MemberConstants.USER_STOP_STATUS_PAUSE);
						stopStatusCode = MemberConstants.USER_STOP_STATUS_PAUSE;
					}

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIdpConstants.IDP_RES_CODE_LOGIN_RESTRICT)) { // 로그인제한 상태인 경우

					if (!StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) { // IDP와 로그인
																										 // 제한상태 코드가
																										 // 다를경우 로그인
																										 // 제한상태로 업데이트
						LOGGER.info("{} 로그인제한상태코드가 상이하여 업데이트(loginStatusCode : {} -> {})", userId, loginStatusCode,
								MemberConstants.USER_LOGIN_STATUS_PAUSE);
						this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId,
								MemberConstants.USER_LOGIN_STATUS_PAUSE, null, null, null);
						this.modOneIdInfo(requestHeader, chkDupRes.getUserMbr().getImSvcNo(),
								MemberConstants.USER_LOGIN_STATUS_PAUSE, null);
						loginStatusCode = MemberConstants.USER_LOGIN_STATUS_PAUSE;
					}

				} else {
					throw ex;
				}

			}

		} else { // 기존 IDP 계정인 경우

			try {

				/* 잠금해지 요청인 경우 */
				if (StringUtils.equals(req.getReleaseLock(), "Y")
						&& StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
					/* 로그인 상태코드 정상처리 */
					this.modStatus(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId,
							MemberConstants.USER_LOGIN_STATUS_NOMAL, null, null, null);
					loginStatusCode = MemberConstants.USER_LOGIN_STATUS_NOMAL;
				}

				/* 인증요청 */
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq authForIdEcReq = new com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcReq();
				authForIdEcReq.setUserId(userId);
				authForIdEcReq.setUserPasswd(userPw);
				com.skplanet.storeplatform.external.client.idp.vo.AuthForIdEcRes authForIdEcRes = this.idpSCI
						.authForId(authForIdEcReq);

				userAuthKey = authForIdEcRes.getUserAuthKey();

				/* 단말정보 update */
				// this.modDeviceInfoForLogin(requestHeader, userKey, authForIdEcRes.getUserAuthKey(), req);

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.regLoginHistory(requestHeader, userId, userPw, "N", "N",
							req.getIpAddress(), "N", null);

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess("N");
					return res;

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 남김 */
					LOGGER.info("NOT_EXIST_USER authorizeById userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		/* 로그인 성공이력 저장 */
		this.regLoginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress(), "N", null);

		/* 일시정지 / 로그인제한 / 직권중지 상태인 경우 */
		if (StringUtils.equals(userMainStatus, MemberConstants.MAIN_STATUS_PAUSE)
				|| StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)
				|| StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {

			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setIsLoginSuccess("Y");
			return res;
		}

		/* 로그인 결과 */
		res.setUserAuthKey(userAuthKey);
		res.setUserKey(userKey);
		res.setUserType(userType);
		res.setUserMainStatus(userMainStatus);
		res.setUserSubStatus(userSubStatus);
		res.setLoginStatusCode(loginStatusCode);
		res.setStopStatusCode(stopStatusCode);
		res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey,
				userKey));
		res.setIsLoginSuccess("Y");

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService# executeAuthorizeForAutoUpdate
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, com.skplanet
	 * .storeplatform.sac.client.member.vo.user.AuthorizeForAutoUpdateReq)
	 */
	@Override
	public AuthorizeSimpleByMdnRes authorizeSimpleByMdn(SacRequestHeader requestHeader, AuthorizeSimpleByMdnReq req) {

		String deviceId = req.getDeviceId();

		/* 모번호 조회 */
		deviceId = this.commService.getOpmdMdnInfo(deviceId);

		SimpleLoginRequest simpleLoginRequest = new SimpleLoginRequest();

		simpleLoginRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));

		simpleLoginRequest.setDeviceID(deviceId);
		simpleLoginRequest.setConnIp(deviceId);

		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (StringUtils.isNotBlank(svcVersion)) {
			simpleLoginRequest.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}

		SimpleLoginResponse simpleLoginResponse = this.userSCI.simpleLogin(simpleLoginRequest);

		AuthorizeSimpleByMdnRes res = new AuthorizeSimpleByMdnRes();
		if (StringUtils.equals(MemberConstants.USE_Y, simpleLoginResponse.getIsLoginSuccess())) {
			res.setDeviceKey(simpleLoginResponse.getDeviceKey());
			res.setUserKey(simpleLoginResponse.getUserKey());
			res.setUserAuthKey(this.tempUserAuthKey);
		}
		res.setIsLoginSuccess(simpleLoginResponse.getIsLoginSuccess());

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService# authorizeSaveAndSyncByMac
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, com.skplanet
	 * .storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq)
	 */
	@Override
	public AuthorizeSaveAndSyncByMacRes authorizeSaveAndSyncByMac(SacRequestHeader requestHeader,
			AuthorizeSaveAndSyncByMacReq req) {

		AuthorizeSaveAndSyncByMacRes res = new AuthorizeSaveAndSyncByMacRes();

		/* mac 정보 조회 */
		DeviceInfo macDeviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
				req.getMacAddress(), null);

		if (macDeviceInfo == null) {
			throw new StorePlatformException("SAC_MEM_0003", "macAddress", req.getMacAddress());
		}

		/* 모번호 조회 및 셋팅 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

		/* mdn 회원유무 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader,
				MemberConstants.KEY_TYPE_DEVICE_ID, req.getDeviceId());

		String isPurchaseChange = "N";
		String isJoinMdn = "N";
		String isVariability = "N"; // 변동성 여부

		String oldDeviceKey = macDeviceInfo.getDeviceKey();
		String oldUserKey = macDeviceInfo.getUserKey();
		String newDeviceKey = null;
		String newUserKey = null;

		DeviceInfo mdnDeviceInfo = null;
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) { // 회원인 경우

			mdnDeviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
					req.getDeviceId(), null);

			isPurchaseChange = "Y";

			newDeviceKey = mdnDeviceInfo.getDeviceKey();
			newUserKey = mdnDeviceInfo.getUserKey();

			LOGGER.info("{} 기가입된 deviceId", req.getDeviceId());

		} else { // 회원이 아닌경우 변동성 대상체크

			SaveAndSync saveAndSync = this.saveAndSyncService.checkSaveAndSync(requestHeader, req.getDeviceId(),
					MemberConstants.DEVICE_TELECOM_SKT);

			if (StringUtils.equals(saveAndSync.getIsSaveAndSyncTarget(), "Y")) { // 변동성 대상인 경우

				isVariability = "Y";
				newDeviceKey = saveAndSync.getDeviceKey();
				newUserKey = saveAndSync.getUserKey();

				mdnDeviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
						newDeviceKey, newUserKey);
			} else { // 변동성 대상이 아닌 경우

				isJoinMdn = "Y";
				LOGGER.info("{} 신규가입처리대상", req.getDeviceId());

			}

		}

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		if (StringUtils.equals(isPurchaseChange, "Y") || StringUtils.equals(isVariability, "Y")) {

			/* mac 정보 탈퇴처리 */
			RemoveUserRequest removeUserRequest = new RemoveUserRequest();
			removeUserRequest.setCommonRequest(commonRequest);
			removeUserRequest.setUserKey(oldUserKey);
			removeUserRequest.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
			removeUserRequest.setSecedeReasonMessage("Save&Sync인증탈퇴");
			this.userSCI.remove(removeUserRequest);
			LOGGER.info("{} 탈퇴 처리", req.getMacAddress());

			/* 휴대기기 정보 수정 */
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUserKey(newUserKey);
			deviceInfo.setDeviceKey(newDeviceKey);
			deviceInfo.setDeviceId(req.getDeviceId());
			if (StringUtils.isNotBlank(req.getNativeId())) {
				deviceInfo.setNativeId(req.getNativeId());
			}
			if (StringUtils.isNotBlank(req.getDeviceAccount())) {
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
			}
			deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());

			/* 휴대기기 헤더 정보 셋팅 */
			deviceInfo = this.deviceService.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);
			/* 휴대기기 주요정보 조회 */
			MajorDeviceInfo majorDeviceInfo = this.commService
					.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(), MemberConstants.DEVICE_TELECOM_SKT,
							req.getDeviceId(), MemberConstants.DEVICE_ID_TYPE_MSISDN, false);

			deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum());
			/* 디바이스 헤더에 모델정보가 있거나 디폴트 모델이 아닌경우만 단말정보 변경 */
			if (StringUtils.isNotBlank(deviceInfo.getDeviceModelNo())
					&& !this.commService.isDefaultDeviceModel(deviceInfo.getDeviceModelNo())) {
				deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom());
				deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());
				if (StringUtils.equals(majorDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) {
					deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
				} else {

					/* 정상단말로 요청시 DB에 단말정보가 미지원단말정보이면 디폴트 모델명을 닉네임에 셋팅 */
					if (StringUtils.equals(mdnDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
							&& StringUtils.equals(mdnDeviceInfo.getDeviceModelNo(),
									MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
						deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
					}
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(), deviceInfo));
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
						majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(), deviceInfo));
			} else {
				deviceInfo.setDeviceModelNo(null); // 디폴트 모델명이 넘어온경우도 있으므로 초기화 하여 업데이트 하지 않게 함
				if (!StringUtils.equals(mdnDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)
						&& !StringUtils.equals(mdnDeviceInfo.getDeviceNickName(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) { // DB에 저장된 단말정보가 미지원단말이 아닌경우 통신사정보를 업데이트 한다.
					deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
				}
			}
			this.deviceService.modDeviceInfo(requestHeader, deviceInfo, false);

			/* 전시/기타, 구매 파트 키 변경 */
			if (StringUtils.equals(isPurchaseChange, "Y")) {
				this.mcic.excuteInternalMethod(true, requestHeader.getTenantHeader().getSystemId(), requestHeader
						.getTenantHeader().getTenantId(), newUserKey, oldUserKey, newDeviceKey, oldDeviceKey);
			}

			res.setDeviceKey(newDeviceKey);
			res.setUserKey(newUserKey);

		} else if (StringUtils.equals(isJoinMdn, "Y")) {

			/* IDP 모바일전용회원 가입 */
			LOGGER.info("{} IDP 모바일 회원 가입 요청", req.getDeviceId());
			JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
			joinForWapEcReq.setUserMdn(req.getDeviceId());
			joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT);

			JoinForWapEcRes joinForWapEcRes = null;
			try {

				joinForWapEcRes = this.idpSCI.joinForWap(joinForWapEcReq);

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IdpConstants.IDP_RES_CODE_ALREADY_JOIN)) {

					/* IDP 기가입인경우 탈퇴 처리 */
					SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
					ecReq.setUserMdn(req.getDeviceId());
					this.idpSCI.secedeForWap(ecReq);

					throw new StorePlatformException("SAC_MEM_1201", ex);

				} else {

					throw ex;

				}
			}

			/* 가가입 상태인 mac 회원정보를 정상상태로 */
			this.modStatus(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getMacAddress(), null, null,
					MemberConstants.MAIN_STATUS_NORMAL, MemberConstants.SUB_STATUS_NORMAL);

			/* mac -> mdn으로 변경 처리 및 휴대기기 정보 수정 */
			LOGGER.info("{} -> {} deviceId 변경", req.getMacAddress(), req.getDeviceId());
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUserKey(oldUserKey);
			deviceInfo.setDeviceKey(oldDeviceKey);
			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setSvcMangNum(joinForWapEcRes.getSvcMngNum());
			deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
			if (StringUtils.isNotBlank(req.getNativeId())) {
				deviceInfo.setNativeId(req.getNativeId());
			}
			if (StringUtils.isNotBlank(req.getDeviceAccount())) {
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
			}
			deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());

			/* 휴대기기 헤더 정보 셋팅 */
			deviceInfo = this.deviceService.setDeviceHeader(requestHeader.getDeviceHeader(), deviceInfo);

			/* 휴대기기 주요정보 조회 */
			MajorDeviceInfo majorDeviceInfo = this.commService
					.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(), MemberConstants.DEVICE_TELECOM_SKT,
							req.getDeviceId(), MemberConstants.DEVICE_ID_TYPE_MSISDN, false);

			/* 디바이스 헤더에 모델정보가 있고 디폴트 모델이 아닌경우만 단말정보 변경 */
			if (StringUtils.isNotBlank(deviceInfo.getDeviceModelNo())
					&& !this.commService.isDefaultDeviceModel(deviceInfo.getDeviceModelNo())) {
				deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom());
				deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());
				if (StringUtils.equals(majorDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) {
					deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(), deviceInfo));
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
						majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(), deviceInfo));
			} else {
				deviceInfo.setDeviceModelNo(null); // 디폴트 모델명이 넘어온경우도 있으므로 초기화 하여 업데이트 하지 않게 함
				if (!StringUtils.equals(macDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)
						&& !StringUtils.equals(macDeviceInfo.getDeviceNickName(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) { // MAC정보 DB에 저장된 단말정보가 미지원단말이 아닌경우 통신사정보를
																			// 업데이트 한다.
					deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
				}
			}

			this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

			/* mbrNo 변경 */
			LOGGER.info("{} 신규가입한 IDP userKey로 업데이트 newMbrNo={}", req.getDeviceId(), joinForWapEcRes.getUserKey());
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(oldUserKey);
			userMbr.setImMbrNo(joinForWapEcRes.getUserKey());
			UpdateUserRequest updateUserRequest = new UpdateUserRequest();
			updateUserRequest.setCommonRequest(commonRequest);
			updateUserRequest.setUserMbr(userMbr);
			this.userSCI.updateUser(updateUserRequest);

			/* MQ 연동 */
			CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(joinForWapEcRes.getUserKey());
				mqInfo.setDeviceKey(oldDeviceKey);
				mqInfo.setDeviceId(deviceInfo.getDeviceId());
				mqInfo.setMnoCd(deviceInfo.getDeviceTelecom());
				this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);
			}

			res.setUserKey(oldUserKey);
			res.setDeviceKey(oldDeviceKey);

		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeForInApp(com.skplanet.storeplatform.
	 * sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacReq)
	 */
	@Override
	public AuthorizeForInAppSacRes authorizeForInApp(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		// Tstore 회원전용이므로 tenantId S01
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		tenant.setSystemId(MemberConstants.SYSTEM_ID_INAPP_2);
		requestHeader.setTenantHeader(tenant);

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회

		AuthorizeForInAppSacRes res = this.getTstoreMemberInfoForInApp(requestHeader, req); // 회원 정보 조회

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(), "N", "");
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeForInAppV2(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacReq)
	 */
	@Override
	public AuthorizeForInAppSacRes authorizeForInAppV2(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		// 타사 인증전 까지는 tenantId S01
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		tenant.setSystemId(MemberConstants.SYSTEM_ID_INAPP_2);
		requestHeader.setTenantHeader(tenant);

		AuthorizeForInAppSacRes res = new AuthorizeForInAppSacRes();

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회 (989 일 경우만)
		String tenantId = this.commService.getTenantIdByDeviceTelecom(req.getDeviceTelecom()); // 이통사 정보로 TenantID 부여
		IapProductInfoRes iapProductInfoRes = this.mcic.getIapProdInfo(tenantId, req.getDeviceId(), req.getProdId()); // 마켓배포상품
																													  // 정보조회
		boolean isMarketProd = true; // 마켓배포상품 유무
		if (iapProductInfoRes == null || StringUtils.isBlank(iapProductInfoRes.getParentProdId())) {
			isMarketProd = false;
		}

		LOGGER.info("{} tenantId : {}, 마켓배포상품여부 : {}", req.getDeviceId(), tenantId, isMarketProd);

		if (StringUtils.equals(MemberConstants.TENANT_ID_TSTORE, tenantId) || !isMarketProd) {

			// SKT로 인증요청 or 마켓배포상품이 아니면 Tstore 회원인증
			LOGGER.info("{} Tstore 회원인증", req.getDeviceId());
			res = this.getTstoreMemberInfoForInApp(requestHeader, req);

		} else { // 타사 && 마켓배포상품인 경우

			if (this.isPurchasedFromTstore(requestHeader, req.getDeviceId(), iapProductInfoRes.getParentProdId())) {

				// 타사 폰에서 Tstore 샵클을 이용하여 상품을 구매한 회원이므로 Tstore 회원인증
				LOGGER.info("{} 타사폰에서 Tstore 구매내역 존재", req.getDeviceId());
				LOGGER.info("{} Tstore 회원인증", req.getDeviceId());
				res = this.getTstoreMemberInfoForInApp(requestHeader, req);

			} else {

				LOGGER.info("{} 타사 마켓 회원인증", req.getDeviceId());

				// 통신사에 따라 tenantId 셋팅
				tenant.setTenantId(tenantId);
				requestHeader.setTenantHeader(tenant);

				res = this.getMarketMemberInfoForInApp(requestHeader, req, tenantId);

			}
		}

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(), "N", "");
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorize(com.skplanet.storeplatform.sac.common
	 * .header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacReq)
	 */
	@Override
	public AuthorizeSacRes authorize(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeSacReq req) {

		// Tstore 회원전용이므로 tenantId S01
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		tenant.setSystemId(MemberConstants.SYSTEM_ID_INAPP_2);
		requestHeader.setTenantHeader(tenant);

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회 (989 일 경우만)

		AuthorizeSacRes res = this.getTstoreMemberInfoForPayPlanet(requestHeader, req);

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(), "N", "");
		}

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet InApp 에 제공할 Tstore 회원정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	private AuthorizeForInAppSacRes getTstoreMemberInfoForInApp(SacRequestHeader requestHeader,
			AuthorizeForInAppSacReq req) {

		AuthorizeForInAppSacRes res = new AuthorizeForInAppSacRes();

		res.setTrxNo(req.getTrxNo());
		res.setDeviceId(req.getDeviceId());
		res.setDeviceTelecom(req.getDeviceTelecom());
		res.setTenantId(MemberConstants.TENANT_ID_TSTORE);

		DetailReq detailReq = new DetailReq();
		detailReq.setDeviceId(req.getDeviceId());
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		searchExtent.setAgreementInfoYn(MemberConstants.USE_Y);
		searchExtent.setMbrAuthInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);
		DetailV2Res detailRes = null;
		try {
			detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				LOGGER.info("{} Tstore 비회원", req.getDeviceId());
				res.setUserStatus(MemberConstants.INAPP_USER_STATUS_NO_MEMBER);
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setPinInfo(new MarketPinInfo());
				res.setMbrAuth(new MbrAuth());
				res.setTstoreEtcInfo(new TstoreEtcInfo());
				return res;
			} else {
				throw e;
			}
		}

		// 사용자 기본정보
		UserInfo userInfo = new UserInfo();
		userInfo.setUserKey(detailRes.getUserInfo().getUserKey());
		userInfo.setUserId(detailRes.getUserInfo().getUserId());
		userInfo.setUserType(detailRes.getUserInfo().getUserType());
		userInfo.setImSvcNo(detailRes.getUserInfo().getImSvcNo());
		userInfo.setImMbrNo(detailRes.getUserInfo().getImMbrNo());
		userInfo.setUserPhoneCountry(detailRes.getUserInfo().getUserPhoneCountry());
		userInfo.setUserEmail(detailRes.getUserInfo().getUserEmail());
		userInfo.setIsRecvEmail(detailRes.getUserInfo().getIsRecvEmail());
		userInfo.setIsRealName(detailRes.getUserInfo().getIsRealName());
		userInfo.setUserCountry(detailRes.getUserInfo().getUserCountry());
		userInfo.setUserLanguage(detailRes.getUserInfo().getUserLanguage());
		userInfo.setProdExpoLevl(this.getProdExpoLevl(detailRes.getUserInfo().getRealAge()));

		// 휴대기기 정보
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		deviceInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
		deviceInfo.setDeviceTelecom(detailRes.getDeviceInfoList().get(0).getDeviceTelecom());
		deviceInfo.setDeviceModelNo(detailRes.getDeviceInfoList().get(0).getDeviceModelNo());
		deviceInfo.setSvcMangNum(detailRes.getDeviceInfoList().get(0).getSvcMangNum());
		deviceInfo.setDeviceAccount(detailRes.getDeviceInfoList().get(0).getDeviceAccount());
		deviceInfo.setDeviceExtraInfoList(detailRes.getDeviceInfoList().get(0).getDeviceExtraInfoList());

		// PIN 정보
		SearchDeviceSetInfoRequest searchDeviceSetInfoRequest = new SearchDeviceSetInfoRequest();
		searchDeviceSetInfoRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySearch.setKeyString(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		keySearchList.add(keySearch);
		searchDeviceSetInfoRequest.setKeySearchList(keySearchList);

		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = this.deviceSetSCI
				.searchDeviceSetInfo(searchDeviceSetInfoRequest);
		MarketPinInfo pinInfo = new MarketPinInfo();
		pinInfo.setIsPinSet(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPin());
		pinInfo.setIsPinRetry(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPinRetry());
		pinInfo.setIsPinClosed(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthLockYn());
		pinInfo.setSetPinUrl(this.getPinSetUrl(pinInfo));

		res.setUserStatus(MemberConstants.INAPP_USER_STATUS_NORMAL); // 회원상태 정상
		res.setUserAuthKey(this.tempUserAuthKey); // 임시 인증키
		res.setUserInfo(userInfo);
		res.setAgreementList(detailRes.getAgreementList()); // 약관정보
		res.setDeviceInfo(deviceInfo);
		res.setPinInfo(pinInfo);
		res.setMbrAuth(detailRes.getMbrAuth()); // 실명인증정보
		res.setTstoreEtcInfo(this.getTstoreEtcInfo(requestHeader, deviceInfo.getDeviceId(),
				deviceInfo.getDeviceTelecom(), userInfo)); // 기타정보

		return res;
	}

	/**
	 * <pre>
	 * Tstore 회원의 PIN 정보에 따라서 PIN 설정 URL을 리턴.
	 * </pre>
	 * 
	 * @param pinInfo
	 *            MarketPinInfo
	 * @return pinSetUrl String
	 */
	private String getPinSetUrl(MarketPinInfo pinInfo) {
		String pinSetUrl = "";

		if (StringUtils.equals(pinInfo.getIsPinSet(), MemberConstants.USE_N)) { // PIN 설정
			pinSetUrl = this.pinCodeSetUrl;
		} else if (StringUtils.equals(pinInfo.getIsPinSet(), MemberConstants.USE_Y)
				&& StringUtils.equals(pinInfo.getIsPinRetry(), MemberConstants.USE_Y)
				&& StringUtils.equals(pinInfo.getIsPinClosed(), MemberConstants.USE_N)) { // PIN 확인
			pinSetUrl = this.pinCodeConfirmUrl;
		} else if (StringUtils.equals(pinInfo.getIsPinSet(), MemberConstants.USE_Y)
				&& StringUtils.equals(pinInfo.getIsPinRetry(), MemberConstants.USE_Y)
				&& StringUtils.equals(pinInfo.getIsPinClosed(), MemberConstants.USE_Y)) { // PIN 재설정
			pinSetUrl = this.pinCodeInitUrl;
		}

		return pinSetUrl;

	}

	/**
	 * <pre>
	 * T Store 기타 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param deviceTelecom
	 *            String
	 * @param userInfo
	 *            UserInfo
	 * @return TstoreEtcInfo
	 */
	private TstoreEtcInfo getTstoreEtcInfo(SacRequestHeader requestHeader, String deviceId, String deviceTelecom,
			UserInfo userInfo) {

		TstoreEtcInfo tstoreEtcInfo = new TstoreEtcInfo();

		if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceTelecom)) { // SKT 통신과금 부가서비스 가입여부 조회
			GetAdditionalServiceReq req = new GetAdditionalServiceReq();
			req.setMsisdn(deviceId);
			req.setSvcCode("NA00004184");
			GetAdditionalServiceRes res = this.miscellaneousService.getAdditionalService(req);

			tstoreEtcInfo.setSvcJoinResult(res.getSvcJoinResult());
		}

		if (StringUtils.equals(MemberConstants.USER_TYPE_ONEID, userInfo.getUserType())) { // ONE ID 정보조회(IDP)
			MbrOneidSacReq req = new MbrOneidSacReq();
			req.setSearchType("1");
			req.setUserKey(userInfo.getUserKey());
			MbrOneidSacRes res = this.userSearchService.srhUserOneId(requestHeader, req);

			tstoreEtcInfo.setIsRealName(res.getIsRealName());
			tstoreEtcInfo.setCiYn(res.getIsCi());
			tstoreEtcInfo.setIsMemberPoint(res.getIsMemberPoint());
		}

		return tstoreEtcInfo;
	}

	/**
	 * <pre>
	 * 회원의 연령대 정보를 코드로 반환.
	 * </pre>
	 * 
	 * @param realAge
	 *            String
	 * @return prodExpoLevl 회원의 연령대 코드
	 */
	private String getProdExpoLevl(String realAge) {

		String prodExpoLevl = "";

		if (StringUtils.isBlank(realAge)) {
			return prodExpoLevl;
		}
		int age = Integer.parseInt(realAge);

		if (age >= 19) {
			prodExpoLevl = "US014705";
		} else if (age >= 18) {
			prodExpoLevl = "US014704";
		} else if (age >= 15) {
			prodExpoLevl = "US014703";
		} else if (age >= 12) {
			prodExpoLevl = "US014702";
		} else {
			prodExpoLevl = "US014701";
		}
		return prodExpoLevl;
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

		String deviceKey = "";
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
	 * 회원유무 확인.
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
	private CheckDuplicationResponse checkDuplicationUser(SacRequestHeader requestHeader, String keyType,
			String keyString) {
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

		return this.userSCI.checkDuplication(chkDupReq);

	}

	/**
	 * <pre>
	 * SC회원정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            String
	 * @param keyString
	 *            String
	 * @return String
	 */
	private SearchUserResponse srhUser(SacRequestHeader requestHeader, String keyType, String keyString) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

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
	 * @param isAutoUpdate
	 *            자동업데이트여부
	 * @param loginReason
	 *            로그인 사유
	 * @return LoginUserResponse
	 */
	private LoginUserResponse regLoginHistory(SacRequestHeader requestHeader, String userId, String userPw,
			String isSuccess, String isMobile, String ipAddress, String isAutoUpdate, String loginReason) {
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
		loginReq.setIsAutoLogin(StringUtils.equals(isAutoUpdate, "Y") ? isAutoUpdate : "N");

		if (StringUtils.isNotBlank(loginReason)) {
			loginReq.setLoginReason(loginReason);
		}

		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (StringUtils.isNotBlank(svcVersion)) {
			loginReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}
		loginReq.setIpAddress(ipAddress);

		return this.userSCI.updateLoginUser(loginReq);
	}

	/**
	 * <pre>
	 * 회원 상태코드 업데이트.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param keyType
	 *            String
	 * @param keyString
	 *            String
	 * @param loginStatusCode
	 *            로그인 상태코드
	 * @param stopStatusCode
	 *            직권중지 상태코드
	 * @param userMainStatus
	 *            메인 상태코드
	 * @param userSubStatus
	 *            서브메인 상태코드
	 */
	private void modStatus(SacRequestHeader requestHeader, String keyType, String keyString, String loginStatusCode,
			String stopStatusCode, String userMainStatus, String userSubStatus) {

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
		if (StringUtils.isNotBlank(loginStatusCode)) {
			updStatusUserReq.setLoginStatusCode(loginStatusCode);
		}
		if (StringUtils.isNotBlank(stopStatusCode)) {
			updStatusUserReq.setStopStatusCode(stopStatusCode);
		}
		if (StringUtils.isNotBlank(userMainStatus)) {
			updStatusUserReq.setUserMainStatus(userMainStatus);
		}
		if (StringUtils.isNotBlank(userSubStatus)) {
			updStatusUserReq.setUserSubStatus(userSubStatus);
		}

		this.userSCI.updateStatus(updStatusUserReq);

	}

	/**
	 * <pre>
	 * 원아이디 회원 정보 업데이트.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param imSvcNo
	 *            OneID 통합서비스 관리번호
	 * @param loginStatusCode
	 *            로그인 상태코드
	 * @param stopStatusCode
	 *            직권중지 상태코드
	 */
	private void modOneIdInfo(SacRequestHeader sacHeader, String imSvcNo, String loginStatusCode, String stopStatusCode) {

		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(this.commService.getSCCommonRequest(sacHeader));
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setIntgSvcNumber(imSvcNo);
		mbrOneID.setLoginStatusCode(loginStatusCode);
		mbrOneID.setStopStatusCode(stopStatusCode);
		mbrOneID.setUpdateDate(DateUtil.getToday("yyyyMMddHHmmss"));

		updateMbrOneIDRequest.setMbrOneID(mbrOneID);
		this.userSCI.createAgreeSite(updateMbrOneIDRequest);

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

		String isAgreeYn = "N";

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
		schAgreeListReq.setCommonRequest(commonRequest);
		schAgreeListReq.setUserKey(userKey);

		try {
			SearchAgreementListResponse schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
			for (MbrClauseAgree agreeInfo : schAgreeListRes.getMbrClauseAgreeList()) {
				if (StringUtils.equals(agreeInfo.getExtraAgreementID(), agreementCode)) {
					isAgreeYn = agreeInfo.getIsExtraAgreement();
					break;
				}

			}
		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return isAgreeYn;

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
	 * @param userKey
	 *            String
	 * @return UserAuthMethod
	 */
	private UserAuthMethod srhUserAuthMethod(SacRequestHeader requestHeader, String deviceId, String userKey) {

		SearchUserResponse schUserRes = this.srhUser(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey);

		UserAuthMethod userAuthMethod = new UserAuthMethod();

		/* 인증수단 userId */
		if (!StringUtils.equals(schUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {
			userAuthMethod.setUserId(schUserRes.getUserMbr().getUserID());
		}

		/* 인증수단 실명인증 여부 */
		if (StringUtils.isNotBlank(schUserRes.getMbrAuth().getCi())) {
			userAuthMethod.setIsRealName("Y");
		} else {
			userAuthMethod.setIsRealName("N");
		}
		return userAuthMethod;
	}

	/**
	 * <pre>
	 * 휴대기기정보 update.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @param obj
	 *            Object
	 * @param oDeviceId
	 *            String
	 * @param dbDeviceInfo
	 *            DeviceInfo
	 * @param version
	 *            String
	 * @return deviceKey String
	 */
	private String modDeviceInfoForLogin(SacRequestHeader requestHeader, String userKey, Object obj, String oDeviceId,
			DeviceInfo dbDeviceInfo, String version) {

		if (!this.commService.isOpmd(oDeviceId)) { // OPMD 단말이 아닌경우만 휴대기기정보 수정
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setUserKey(userKey);

			if (obj instanceof AuthorizeByMdnReq) { // mdn인증

				AuthorizeByMdnReq req = new AuthorizeByMdnReq();
				req = (AuthorizeByMdnReq) obj;

				deviceInfo.setDeviceId(req.getDeviceId()); // MDN
				deviceInfo.setDeviceIdType(req.getDeviceIdType()); // MDN Type
				deviceInfo.setDeviceAccount(req.getDeviceAccount()); // GMAIL
				deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 통신사
				deviceInfo.setNativeId(req.getNativeId()); // IMEI
				deviceInfo.setIsNativeIdAuth(req.getIsNativeIdAuth()); // IMEI 비교여부
				deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 휴대기기 부가속성 정보

			} else if (obj instanceof AuthorizeByIdReq) { // id인증

				AuthorizeByIdReq req = new AuthorizeByIdReq();
				req = (AuthorizeByIdReq) obj;

				if (StringUtils.isNotBlank(req.getDeviceId())) { // deviceId가 파라메터로 넘어왔을 경우에만 휴대기기 정보 update 요청

					deviceInfo.setDeviceId(req.getDeviceId()); // MDN
					deviceInfo.setDeviceIdType(req.getDeviceIdType()); // MDN Type
					deviceInfo.setDeviceAccount(req.getDeviceAccount()); // GMAIL
					deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 통신사
					deviceInfo.setNativeId(req.getNativeId()); // IMEI
					deviceInfo.setIsNativeIdAuth(req.getIsNativeIdAuth()); // IMEI 비교여부
					deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 휴대기기 부가속성 정보

				}

			}

			return this.deviceService.modDeviceInfoForLogin(requestHeader, deviceInfo, dbDeviceInfo, version);

		} else {
			LOGGER.info("{} {} OPMD 단말로 휴대기기 업데이트 하지 않음", dbDeviceInfo.getDeviceId(), oDeviceId);
			return dbDeviceInfo.getDeviceKey();
		}

	}

	/**
	 * <pre>
	 * 가가입한 원아이디 사이트 정보 조회.
	 * </pre>
	 * 
	 * @param userId
	 *            String
	 * @param joinSstList
	 *            String
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> getInvalidSiteInfo(String userId, String joinSstList) {

		HashMap<String, String> invalidSiteInfo = new HashMap<String, String>();

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
		for (Entry<String, String> entry : mapSiteCd.entrySet()) {
			if (StringUtils.contains(joinSstList, entry.getKey())) {
				invalidSiteInfo.put("joinSstCd", entry.getKey());
				invalidSiteInfo.put("joinSstNm", entry.getValue());
				break;
			}
		}

		if (StringUtils.isBlank(invalidSiteInfo.get("joinSstCd"))) {
			invalidSiteInfo.put("joinSstCd", "90000"); // One ID
			invalidSiteInfo.put("joinSstNm", mapSiteCd.get("90000"));
		}

		LOGGER.info("{} invalid user site info : {}, {}, {}", userId, joinSstList, invalidSiteInfo.get("joinSstCd"),
				invalidSiteInfo.get("joinSstNm"));

		return invalidSiteInfo;

	}

	/**
	 * <pre>
	 * 타사 마켓회원 인증 연동.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @param tenantId
	 *            String
	 * @return AuthorizeForInAppSacRes
	 */
	private AuthorizeForInAppSacRes getMarketMemberInfoForInApp(SacRequestHeader requestHeader,
			AuthorizeForInAppSacReq req, String tenantId) {

		AuthorizeForInAppSacRes res = new AuthorizeForInAppSacRes();
		MarketAuthorizeEcRes marketRes = null;

		res.setTrxNo(req.getTrxNo());
		res.setTenantId(tenantId);
		res.setDeviceId(req.getDeviceId());
		res.setDeviceTelecom(req.getDeviceTelecom());

		// 타사 마켓회원 인증 요청
		MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
		marketReq.setTrxNo(req.getTrxNo());
		marketReq.setDeviceId(req.getDeviceId());
		marketReq.setDeviceTelecom(req.getDeviceTelecom());
		marketReq.setNativeId(req.getNativeId());
		marketReq.setSimSerialNo(req.getSimSerialNo());
		marketReq.setUserVerifyReason("InApp");

		LOGGER.info("{} authorizeMarket Request : {}", req.getDeviceId(), marketReq);

		if (StringUtils.equals(MemberConstants.TENANT_ID_OLLEH_MARKET, tenantId)) {
			marketRes = this.marketSCI.authorizeForOllehMarket(marketReq);
			LOGGER.info("{} authorizeForOllehMarket Response : {}", req.getDeviceId(), marketRes);
		} else if (StringUtils.equals(MemberConstants.TENANT_ID_UPLUS_STORE, tenantId)) {
			marketRes = this.marketSCI.authorizeForUplusStore(marketReq);
			LOGGER.info("{} authorizeForUplusStore Response : {}", req.getDeviceId(), marketRes);
		}

		if (marketRes != null
				&& StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상인증

			this.validChkMarketUserInfo(marketRes); // 타사 회원정보 유효성 체크

			// Tstore 회원가입여부 조회
			DetailReq detailReq = new DetailReq();
			SearchExtentReq searchExtent = new SearchExtentReq();
			searchExtent.setUserInfoYn(MemberConstants.USE_Y);
			searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
			detailReq.setDeviceId(req.getDeviceId());
			detailReq.setSearchExtent(searchExtent);

			DetailV2Res detailRes = null;

			try {

				detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
				LOGGER.info("{} Tstore 회원", req.getDeviceId());

				if (!StringUtils.equals(detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey())) {

					// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
					LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes.getUserInfo()
							.getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
					this.removeMarketUser(requestHeader, detailRes.getUserInfo().getUserKey());
					this.joinMaketUser(requestHeader, req, marketRes);

					// 재가입시킨 회원정보 재조회
					detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

				}

			} catch (StorePlatformException e) {

				if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {

					// 타사 회원키로 가입된 회원정보 조회
					detailReq.setDeviceId(null);
					detailReq.setMbrNo(marketRes.getDeviceInfo().getDeviceKey());
					detailReq.setSearchExtent(searchExtent);

					try {

						detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

						// 번호변경 케이스로 판단하여 deviceId 업데이트
						LOGGER.info("{} deviceId 변경 : {} -> {}", req.getDeviceId(), detailRes.getDeviceInfoList()
								.get(0).getDeviceId(), marketRes.getDeviceId());
						DeviceInfo deviceInfo = new DeviceInfo();
						deviceInfo.setUserKey(detailRes.getUserInfo().getUserKey());
						deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
						deviceInfo.setDeviceId(marketRes.getDeviceId());
						this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

						// 변경된 deviceId로 회원정보 재조회
						detailReq.setDeviceId(marketRes.getDeviceId());
						detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

					} catch (StorePlatformException ex) {

						if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
							// 회원정보 없으면 Tstore 회원가입
							LOGGER.info("{} Tstore 회원가입", req.getDeviceId());
							this.joinMaketUser(requestHeader, req, marketRes);

							// 가입된 deviceId로 회원정보 재조회
							detailReq.setDeviceId(marketRes.getDeviceId());
							detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
						} else {
							throw e;
						}
					}

				} else {
					throw e;
				}
			}

			// 사용자 기본정보
			UserInfo userInfo = new UserInfo();
			userInfo.setUserKey(detailRes.getUserInfo().getUserKey());
			userInfo.setUserId(detailRes.getUserInfo().getUserId());
			userInfo.setUserType(detailRes.getUserInfo().getUserType());
			userInfo.setImMbrNo(detailRes.getUserInfo().getImMbrNo());
			userInfo.setProdExpoLevl(marketRes.getDeviceInfo().getProdExpoLevl());

			// 약관 정보
			List<Agreement> agreementList = this.getMarketClauseAgreeMappingInfo(marketRes.getDeviceInfo()
					.getClauseExtraInfoList());

			// 휴대기기 정보
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
			deviceInfo.setMarketDeviceKey(detailRes.getUserInfo().getImMbrNo()); // 타사 회선의 고유 Key
			deviceInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
			deviceInfo.setDeviceTelecom(detailRes.getDeviceInfoList().get(0).getDeviceTelecom());
			deviceInfo.setDeviceModelNo(detailRes.getDeviceInfoList().get(0).getDeviceModelNo());

			// Pin 정보
			MarketPinInfo pinInfo = new MarketPinInfo();
			pinInfo.setIsPinSet(marketRes.getDeviceInfo().getPinInfo().getIsSet());
			pinInfo.setIsPinClosed(marketRes.getDeviceInfo().getPinInfo().getIsPinClosed());
			pinInfo.setSetPinUrl(marketRes.getDeviceInfo().getPinInfo().getSetPinURL());
			if (StringUtils.isNotBlank(marketRes.getDeviceInfo().getPinInfo().getIsNotShowingAgain())) {
				// 결제핀 입력여부가 타사와는 반대의 의미이다.
				if (StringUtils.equals(marketRes.getDeviceInfo().getPinInfo().getIsNotShowingAgain(),
						MemberConstants.USE_N)) {
					pinInfo.setIsPinRetry(MemberConstants.USE_Y);
				} else {
					pinInfo.setIsPinRetry(MemberConstants.USE_N);
				}
			}

			res.setUserStatus(marketRes.getUserStatus());
			res.setUserAuthKey(this.tempUserAuthKey);
			res.setUserInfo(userInfo);
			res.setAgreementList(agreementList);
			res.setDeviceInfo(deviceInfo);
			res.setPinInfo(pinInfo);
			res.setMbrAuth(new MbrAuth()); // 타사회원은 존재하지 않음
			res.setTstoreEtcInfo(new TstoreEtcInfo()); // 타사회원은 존재하지 않음

		} else {

			res.setUserStatus(marketRes.getUserStatus());
			res.setUserInfo(new UserInfo());
			res.setAgreementList(new ArrayList<Agreement>());
			res.setDeviceInfo(new DeviceInfo());
			res.setPinInfo(new MarketPinInfo());
			res.setMbrAuth(new MbrAuth());
			res.setTstoreEtcInfo(new TstoreEtcInfo());

		}

		return res;

	}

	/**
	 * <pre>
	 * 타사 회원 약관 수정.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param userKey
	 *            String
	 * @param agreementList
	 *            List<Agreement>
	 * @param marketClauseAgreeList
	 *            List<MbrClauseAgree>
	 */
	private void updateMarketClauseAgree(SacRequestHeader requestHeader, String deviceId, String userKey,
			List<Agreement> agreementList, List<MbrClauseAgree> marketClauseAgreeList) {
		boolean isUpdate = false;

		StringBuffer logBuf = new StringBuffer();

		for (Agreement clauseInfo : agreementList) {
			for (MbrClauseAgree marketClauseInfo : marketClauseAgreeList) {
				if (StringUtils.equals(clauseInfo.getExtraAgreementId(), marketClauseInfo.getExtraAgreementID())) {
					if (!StringUtils.equals(clauseInfo.getIsExtraAgreement(), marketClauseInfo.getIsExtraAgreement())) {
						logBuf.append(" ").append(clauseInfo.getExtraAgreementId()).append(" : ")
								.append(clauseInfo.getIsExtraAgreement()).append(" -> ")
								.append(marketClauseInfo.getIsExtraAgreement());
						isUpdate = true;
					}
				}
			}
		}

		if (isUpdate) {
			LOGGER.info("{} 약관 수정 : {}", deviceId, logBuf.toString());
			UpdateAgreementRequest updateAgreementRequest = new UpdateAgreementRequest();
			updateAgreementRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
			updateAgreementRequest.setUserKey(userKey);
			updateAgreementRequest.setMbrClauseAgreeList(marketClauseAgreeList);
			this.userSCI.updateAgreement(updateAgreementRequest); // 약관정보수정
		}
	}

	/**
	 * <pre>
	 * 타사 회원 정보 유효성 체크.
	 * </pre>
	 * 
	 * @param marketRes
	 *            MarketAuthorizeEcRes
	 */
	private void validChkMarketUserInfo(MarketAuthorizeEcRes marketRes) {

		if (marketRes.getDeviceId() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 MDN");
		}

		if (marketRes.getDeviceTelecom() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 이동통신사");
		}

		if (marketRes.getUserStatus() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원상태코드");
		}

		if (marketRes.getDeviceInfo() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원정보");
		}

		if (marketRes.getDeviceInfo().getDeviceKey() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 고유 Key");
		}

		if (marketRes.getDeviceInfo().getProdExpoLevl() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 연령대");
		}

		if (marketRes.getDeviceInfo().getPinInfo() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 결제PIN 정보");
		}

		if (marketRes.getDeviceInfo().getClauseExtraInfoList() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "타사 회원 약관");
		}

	}

	/**
	 * <pre>
	 * 타사 회원 탈퇴처리.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 */
	private void removeMarketUser(SacRequestHeader requestHeader, String userKey) {
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		scReq.setUserKey(userKey);
		scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
		scReq.setSecedeReasonMessage("");
		this.userSCI.remove(scReq);
	}

	/**
	 * <pre>
	 * 타사 마켓회원 Tstore 회원가입 처리.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @param marketRes
	 *            MarketAuthorizeEcRes
	 * @return userKey String
	 */
	private String joinMaketUser(SacRequestHeader requestHeader, AuthorizeForInAppSacReq req,
			MarketAuthorizeEcRes marketRes) {

		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader)); // 공통코드 셋팅

		// SC 사용자 기본정보 setting
		UserMbr userMbr = new UserMbr();
		userMbr.setImMbrNo(marketRes.getDeviceInfo().getDeviceKey()); // MBR_NO
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setUserID(req.getDeviceId()); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부
		createUserRequest.setUserMbr(userMbr);

		// SC 사용자 가입요청
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		// SC 휴대기기 등록
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(createUserResponse.getUserKey());

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);
		userMbrDevice.setDeviceID(marketRes.getDeviceId()); // 기기 ID
		userMbrDevice.setDeviceTelecom(marketRes.getDeviceTelecom()); // 이동 통신사
		userMbrDevice.setDeviceModelNo(MemberConstants.DP_ANY_PHONE_4APP); // 단말 모델
		userMbrDevice.setDeviceNickName(MemberConstants.DP_ANY_PHONE_4APP_NM); // 단말닉네임
		userMbrDevice.setNativeID(req.getNativeId()); // 기기고유 ID (imei)
		userMbrDevice.setIsPrimary(MemberConstants.USE_Y); // 대표기기 유무
		createDeviceReq.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (StringUtils.isBlank(createDeviceRes.getDeviceKey())) {
			throw new StorePlatformException("SAC_MEM_1102"); // 휴대기기 등록에 실패하였습니다.
		}

		return createUserResponse.getUserKey();
	}

	/**
	 * <pre>
	 * 타사 약관코드를 Tstore 약관코드로 변환.
	 * </pre>
	 * 
	 * @param marketClauseAgreeList
	 *            List<MarketClauseExtraInfoEc>
	 * @return List<Agreement>
	 */
	private List<Agreement> getMarketClauseAgreeMappingInfo(List<MarketClauseExtraInfoEc> marketClauseAgreeList) {

		String tenantId = MemberConstants.TENANT_ID_TSTORE;
		List<Agreement> agreementList = new ArrayList<Agreement>();
		List<AgreementInfo> agreementInfoList = new ArrayList<AgreementInfo>();

		if (marketClauseAgreeList != null) {
			for (MarketClauseExtraInfoEc info : marketClauseAgreeList) {
				AgreementInfo agreement = new AgreementInfo();
				String extraAgreementId = "";
				if (StringUtils.equals(info.getExtraProfile(), "US003505")) { // 개인정보 수집 및 이용동의
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_TSTORE;
					agreement.setExtraAgreementURL(info.getExtraProfileSetURL());
				} else if (StringUtils.equals(info.getExtraProfile(), "US003509")) { // 통신과금서비스 이용약관
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE;
					agreement.setExtraAgreementURL(info.getExtraProfileSetURL());
				} else if (StringUtils.equals(info.getExtraProfile(), "US003511")) { // 마켓 서비스 이용약관
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_SAVE;
					agreement.setExtraAgreementURL(info.getExtraProfileSetURL());
				} else { // 규격서에 정의되지 않은 약관코드인 경우
					throw new StorePlatformException("SAC_MEM_1105", info.getExtraProfile());
				}

				agreement.setExtraAgreementId(extraAgreementId);
				agreement.setIsExtraAgreement(info.getExtraProfileValue());
				agreementInfoList.add(agreement);
			}
		}

		// 필수 약관여부, 버젼 맵핑
		agreementInfoList = this.commService.getClauseMappingInfo(tenantId, agreementInfoList);

		for (AgreementInfo info : agreementInfoList) {
			Agreement agreement = new Agreement();
			agreement.setExtraAgreementId(info.getExtraAgreementId());
			agreement.setIsExtraAgreement(info.getIsExtraAgreement());
			agreement.setExtraAgreementVersion(info.getExtraAgreementVersion());
			agreement.setIsMandatory(info.getMandAgreeYn());
			agreement.setExtraAgreementURL(info.getExtraAgreementURL());
			agreementList.add(agreement);
		}

		return agreementList;
	}

	/**
	 * <pre>
	 * PayPlanet 인증시 요청한 상품 Tstore 구매내역 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param parentProdId
	 *            String
	 * @return boolean 구매내역 존재유무
	 */
	private boolean isPurchasedFromTstore(SacRequestHeader requestHeader, String deviceId, String parentProdId) {

		boolean isPurchasedFromTstore = false;

		// 회원 정보 조회
		DetailReq detailReq = new DetailReq();
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setDeviceId(deviceId);
		detailReq.setSearchExtent(searchExtent);

		try {

			DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

			List<ExistenceItem> existenceItemList = new ArrayList<ExistenceItem>();
			ExistenceItem existenceItem = new ExistenceItem();
			existenceItem.setProdId(parentProdId);
			existenceItemList.add(existenceItem);

			// 기구매체크
			ExistenceReq existenceReq = new ExistenceReq();
			existenceReq.setTenantId(MemberConstants.TENANT_ID_TSTORE);
			existenceReq.setUserKey(detailRes.getUserInfo().getUserKey());
			existenceReq.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
			existenceReq.setExistenceItem(existenceItemList);
			LOGGER.info("{} 기구매체크 Request : {}", deviceId, existenceReq);
			ExistenceListRes existenceListRes = this.mcic.srhExistenceList(existenceReq);
			LOGGER.info("{} 기구매체크 Response : {}", deviceId, existenceListRes);
			if (existenceListRes != null && existenceListRes.getExistenceListRes() != null
					&& existenceListRes.getExistenceListRes().size() > 0) { // 구매내역 존재
				isPurchasedFromTstore = true;
			}

		} catch (StorePlatformException e) {
			if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				throw e;
			}
		}

		return isPurchasedFromTstore;

	}

	/**
	 * <pre>
	 * PayPlanet 통합인증에 제공 할 Tstore 회원정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSacReq
	 * @return AuthorizeSacRes
	 */
	private AuthorizeSacRes getTstoreMemberInfoForPayPlanet(SacRequestHeader requestHeader, AuthorizeSacReq req) {

		AuthorizeSacRes res = new AuthorizeSacRes();

		// 회원정보조회
		DetailReq detailReq = new DetailReq();
		detailReq.setDeviceId(req.getDeviceId());
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		searchExtent.setAgreementInfoYn(MemberConstants.USE_Y);
		searchExtent.setMbrAuthInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);
		DetailV2Res detailRes = null;
		try {
			detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {

				/* 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

			} else {
				throw e;
			}
		}

		// 사용자 기본정보
		UserInfo userInfo = new UserInfo();
		userInfo.setUserKey(detailRes.getUserInfo().getUserKey());
		userInfo.setUserId(detailRes.getUserInfo().getUserId());
		userInfo.setUserType(detailRes.getUserInfo().getUserType());
		userInfo.setImSvcNo(detailRes.getUserInfo().getImSvcNo());
		userInfo.setImMbrNo(detailRes.getUserInfo().getImMbrNo());
		userInfo.setUserPhoneCountry(detailRes.getUserInfo().getUserPhoneCountry());
		userInfo.setUserEmail(detailRes.getUserInfo().getUserEmail());
		userInfo.setIsRecvEmail(detailRes.getUserInfo().getIsRecvEmail());
		userInfo.setIsRealName(detailRes.getUserInfo().getIsRealName());
		userInfo.setUserCountry(detailRes.getUserInfo().getUserCountry());
		userInfo.setUserLanguage(detailRes.getUserInfo().getUserLanguage());

		// 휴대기기 정보
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		deviceInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
		deviceInfo.setDeviceTelecom(detailRes.getDeviceInfoList().get(0).getDeviceTelecom());
		deviceInfo.setDeviceModelNo(detailRes.getDeviceInfoList().get(0).getDeviceModelNo());
		deviceInfo.setSvcMangNum(detailRes.getDeviceInfoList().get(0).getSvcMangNum());
		deviceInfo.setDeviceAccount(detailRes.getDeviceInfoList().get(0).getDeviceAccount());
		deviceInfo.setDeviceExtraInfoList(detailRes.getDeviceInfoList().get(0).getDeviceExtraInfoList());

		// PIN 정보
		SearchDeviceSetInfoRequest searchDeviceSetInfoRequest = new SearchDeviceSetInfoRequest();
		searchDeviceSetInfoRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySearch.setKeyString(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		keySearchList.add(keySearch);
		searchDeviceSetInfoRequest.setKeySearchList(keySearchList);

		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = this.deviceSetSCI
				.searchDeviceSetInfo(searchDeviceSetInfoRequest);
		MarketPinInfo pinInfo = new MarketPinInfo();
		pinInfo.setIsPinSet(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPin());
		pinInfo.setIsPinRetry(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPinRetry());
		pinInfo.setIsPinClosed(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthLockYn());
		pinInfo.setSetPinUrl(this.getPinSetUrl(pinInfo));

		res.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 회원상태 정상
		res.setUserAuthKey(this.tempUserAuthKey); // 임시 인증키
		res.setUserInfo(userInfo);
		res.setAgreementList(detailRes.getAgreementList());
		res.setDeviceInfo(deviceInfo);
		res.setPinInfo(pinInfo);
		res.setMbrAuth(detailRes.getMbrAuth()); // 실명인증정보
		res.setTstoreEtcInfo(this.getTstoreEtcInfo(requestHeader, deviceInfo.getDeviceId(),
				deviceInfo.getDeviceTelecom(), userInfo)); // 기타정보

		return res;
	}
}
