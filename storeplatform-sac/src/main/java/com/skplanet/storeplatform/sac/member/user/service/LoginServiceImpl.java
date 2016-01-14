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

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.*;
import com.skplanet.storeplatform.external.client.market.vo.MarketAuthorizeEcReq;
import com.skplanet.storeplatform.external.client.market.vo.MarketAuthorizeEcRes;
import com.skplanet.storeplatform.external.client.market.vo.MarketClauseExtraInfoEc;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.util.RandomString;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSetSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.member.vo.common.*;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.CommonUtils;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberCommonInternalComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.common.vo.SaveAndSync;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.AdditionalServiceService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	private AdditionalServiceService additionalServiceService;

	@Autowired
	private UserWithdrawService userWithdrawService;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private DeviceSetSCI deviceSetSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MemberCommonInternalComponent mcic;

	@Autowired
	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	@Autowired
	@Resource(name = "memberRetireAmqpTemplate")
	private AmqpTemplate memberRetireAmqpTemplate;

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

        String keyType = null;
        String keyValue = null;

        if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
            String svcMangNo = null;

            /* 모번호 조회 및 셋팅 */
            req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

            if(System.getProperty("spring.profiles.active", "local").equals("local")) {
                // local에서는 외부연동이 안되므로 하드코딩
                HashMap<String, String> mdnMap = new HashMap<String, String>();
                mdnMap.put("01011110001", "svc001");
                mdnMap.put("01011110002", "svc002");
                mdnMap.put("01011110003", "svc003");
                mdnMap.put("01011110004", "svc004");
                mdnMap.put("01011110005", "svc005");
                mdnMap.put("01065261233", "4486071533");
                mdnMap.put("01065261234", "4486071534");
                mdnMap.put("01065261235", "4486071535");
                mdnMap.put("01065261236", "4486071536");
                mdnMap.put("01065261241", "4486071541");
                mdnMap.put("01066786220", "7243371580");
                if(mdnMap.get(req.getDeviceId()) != null){
                    svcMangNo = mdnMap.get(req.getDeviceId());
                }else{
                    throw new StorePlatformException("정상적으로 svc_mang_no가 조회되지 않았습니다.");
                }
            }else{
                svcMangNo = this.commService.getSvcMangNo(req.getDeviceId(), req.getDeviceTelecom(), req.getNativeId(), null);
            }

            keyType = MemberConstants.KEY_TYPE_SVC_MANG_NO;
            keyValue = svcMangNo;

        }else{
            keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
            keyValue = req.getDeviceId();
        }


		/* 회원정보 조회 */
        CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, keyType, keyValue);

		/* 회원 존재유무 확인 */
        if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
			/* 회원 정보가 존재 하지 않습니다. */
            throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
        }

        /* 휴대기기 정보 조회 */
        DeviceInfo dbDeviceInfo = this.deviceService.srhDevice(requestHeader, keyType, keyValue, chkDupRes.getUserMbr().getUserKey());

        /* 휴면계정인 경우 복구 처리 */
        if (StringUtils.equals(chkDupRes.getUserMbr().getIsDormant(), MemberConstants.USE_Y)) {
            LOGGER.info("{} 휴면 {} 회원 복구", req.getDeviceId(),
                    StringUtils.equals(chkDupRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE) ? "모바일" : "IDP ID");
            MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
            moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
            moveUserInfoSacReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
            this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
        }
        /**
         * 휴대기기 정보 수정.
         */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserKey(dbDeviceInfo.getUserKey());
        deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입

        if(StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
            deviceInfo.setDeviceId("");
            deviceInfo.setMdn(req.getDeviceId()); // MDN 번호
        }else {
            deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
        }
        deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
        deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
        deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
        deviceInfo.setNativeId(req.getNativeId()); // 기기 IMEI
        deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 단말부가정보

        String deviceKey = this.deviceService.regDeviceInfo(requestHeader, dbDeviceInfo);

        if(!StringUtils.equals(dbDeviceInfo.getDeviceKey(), deviceKey)){
            LOGGER.info("MDN 회원 {} 의 조회된 deviceKey {}와 수정 처리한 deviceKey {} 정보가 상이", dbDeviceInfo.getUserKey(), dbDeviceInfo.getDeviceKey(), deviceKey);
            throw new StorePlatformException("SAC_MEM_1102"); // 휴대기기 등록에 실패하였습니다.
        }

		/* 로그인 성공이력 저장 */
		this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceIp(),
				req.getIsAutoUpdate(), req.getLoginReason(), "Y", dbDeviceInfo.getDeviceKey());

		/* 일시정지 인경우 (로그인제한상태 / 직권중지상태는 MDN로그인시는 샵클 정상이용가능하게 해야하므로 체크하지 않음) */
		if (StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setIsLoginSuccess("Y");
			return res;
		}

		// 한도 요금제 여부 로직 추가 (2015-05-27 => 06-24)
		// 공통 파라미터 셋팅
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		searchDeviceRequest.setUserKey(dbDeviceInfo.getUserKey());
		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(dbDeviceInfo.getDeviceKey());
		keySearchList.add(keySchUserKey);
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		boolean limitChargeFlag = true;
		List<UserMbrDeviceDetail> userMbrDeviceDetails = searchDeviceResponse.getUserMbrDevice()
				.getUserMbrDeviceDetail();
		if (userMbrDeviceDetails != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetail : userMbrDeviceDetails) {

				// 단말 부가속성중 한도요금제 속성 여부
				if (StringUtils.equals(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN,
						userMbrDeviceDetail.getExtraProfile())) {

					if (StringUtils.isNotBlank(userMbrDeviceDetail.getUpdateDatePlue7())) {
						if (NumberUtils.createLong(userMbrDeviceDetail.getUpdateDatePlue7()) > NumberUtils
								.createLong(userMbrDeviceDetail.getNowDate())) {
							limitChargeFlag = false;
						}
					} else if (StringUtils.isNotBlank(userMbrDeviceDetail.getRegDatePlus7())) {
						if (NumberUtils.createLong(userMbrDeviceDetail.getRegDatePlus7()) > NumberUtils
								.createLong(userMbrDeviceDetail.getNowDate())) {
							limitChargeFlag = false;
						}
					}
					break;
				}
			}
		}

		// UAPS 연동, 단말 부가정보 등록
		// (2015-07-22 패치 제거).
		// 자사, 오늘 끝자리와 디바이스 끝번호가 같을 경우 (ex : '20150612 == '01088880002') UAPS 연동
		// String today = DateUtil.getToday();
		// 자사(SKT)
		if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, req.getDeviceTelecom())) {
			// 1. 한도요금제 조회시간 +7 < sysdate
			// 2015-07-22 --> 2. 조건 항목 패치 제거
			// 2. 현재 날짜의 끝자리(12일 -> 2)와 휴대폰 번호 끝자리(01012345432 -> 2)가 동일한 경우
			// if (limitChargeFlag
			// && StringUtils.equals(today.substring(today.length() - 1),
			// req.getDeviceId().substring(req.getDeviceId().length() - 1))) {
			if (limitChargeFlag) {
				UpdateDeviceManagementRequest updateDeviceManagementRequest = new UpdateDeviceManagementRequest();
				updateDeviceManagementRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				updateDeviceManagementRequest.setDeviceKey(dbDeviceInfo.getDeviceKey());
				updateDeviceManagementRequest.setUserKey(dbDeviceInfo.getUserKey());

				UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN);
				userMbrDeviceDetail.setExtraProfileValue(MemberConstants.USE_N);
				userMbrDeviceDetail.setDeviceKey(dbDeviceInfo.getDeviceKey());
				userMbrDeviceDetail.setUserKey(dbDeviceInfo.getUserKey());
				userMbrDeviceDetail.setRegID(dbDeviceInfo.getUserId());
				try {
					// UAPS 연동 : 한도요금제(NA00002125) 여부 확인
					UserEcRes userEcRes = this.commService.getMappingInfo(req.getDeviceId(), "mdn");
					if (userEcRes != null) {
						if (userEcRes.getServiceCD() != null) {
							for (String serviceCD : userEcRes.getServiceCD()) {
								if (StringUtils.equals("NA00002125", serviceCD)) {
									userMbrDeviceDetail.setExtraProfileValue(MemberConstants.USE_Y);
									break;
								}
							}
						}
					}
					List<UserMbrDeviceDetail> setDeviceDetails = new ArrayList<UserMbrDeviceDetail>();
					setDeviceDetails.add(userMbrDeviceDetail);
					updateDeviceManagementRequest.setUserMbrDeviceDetail(setDeviceDetails);

					this.deviceSCI.updateDeviceManagement(updateDeviceManagementRequest);

					// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (성공)
					final String tlogUserKey = dbDeviceInfo.getUserKey();
					final String tlogDeviceKey = dbDeviceInfo.getDeviceKey();
					final String tlogDeviceId = dbDeviceInfo.getDeviceId();
					final String tlogTingYn = userMbrDeviceDetail.getExtraProfileValue();
					final String resultMessage = "팅요금제 사용여부 업데이트 성공(MDN인증 v1)";

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey)
									.insd_device_id(tlogDeviceKey).device_id(tlogDeviceId).result_code("Y")
									.result_message(resultMessage).ting_yn(tlogTingYn);
						}
					});

				} catch (StorePlatformException e) {
					// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (실패)
					final String tlogUserKey = dbDeviceInfo.getUserKey();
					final String tlogDeviceKey = dbDeviceInfo.getDeviceKey();
					final String tlogDeviceId = dbDeviceInfo.getDeviceId();
					final String tlogTingYn = userMbrDeviceDetail.getExtraProfileValue();
					final String resultMessage = "팅요금제 사용여부 업데이트 실패(MDN인증 v1) ErrCode " + e.getErrorInfo().getCode();

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey)
									.insd_device_id(tlogDeviceKey).device_id(tlogDeviceId).result_code("N")
									.result_message(resultMessage).ting_yn(tlogTingYn);
						}
					});

					LOGGER.info("UAPS getMappingInfo MDN : {}, errorCode : {}, errorMessage : {}", req.getDeviceId(), e
							.getErrorInfo().getCode(), e.getErrorInfo().getMessage());
				}
			}
		}

		/* 로그인 결과 */
		res.setUserKey(chkDupRes.getUserMbr().getUserKey());
		res.setUserType(chkDupRes.getUserMbr().getUserType());
		res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
		res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
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

        /**
         * 1. MDN / MVNO 구분 후 인증 처리
         *  - MDN : mdn으로 uaps(타사는 타사서버) 찔러서 조회된 svc_no로 DB 조회하여 mdn이 동일한 경우 인증 처리 ( 2건 이상일 경우 조회된 MDN이 존재하는 회원 userKey를 조회 )
         *  - MVNO :  mvno단말 인 경우 mdn, imei가 (req = db) 일치하는 경우 인증 성공 처리 함
         * 2. 위 조회된 userKey로 SC 휴대기기 등록 호출
         * 3. 인증 처리
         */
        /* 자번호 셋팅(mdn 로그인시 deviceId는 자번호로 넘어온다) */
        String oDeviceId = req.getDeviceId();

        String keyType = null;
        String keyValue = null;

        /** 01. TYPE MDN UAPS 인증 */
        if (StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
            String svcMangNo = null;

            /**
             * 모번호 조회 (989 일 경우만)
             */
            req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

            // mvno / mdn 단말 구분
//          if(!StringUtils.equals(this.commService.getMappingInfo(req.getDeviceId(), "mdn").getMvnoCD(), "0")){ // mvno
            if(StringUtils.equals("+08265261233", req.getDeviceId())){ // mvno
                keyType = MemberConstants.KEY_TYPE_MDN;
                keyValue = req.getDeviceId();

            }else{ // mdn
                if(System.getProperty("spring.profiles.active", "local").equals("local")) {
                    // local에서는 외부연동이 안되므로 하드코딩
                    HashMap<String, String> mdnMap = new HashMap<String, String>();
                    mdnMap.put("01011110001", "svc001");
                    mdnMap.put("01011110002", "svc002");
                    mdnMap.put("01011110003", "svc003");
                    mdnMap.put("01011110004", "svc004");
                    mdnMap.put("01011110005", "svc005");
                    mdnMap.put("01065261233", "4486071533");
                    mdnMap.put("01065261234", "4486071534");
                    mdnMap.put("01065261235", "4486071535");
                    mdnMap.put("01065261236", "4486071536");
                    mdnMap.put("01065261241", "4486071541");
                    mdnMap.put("01066786220", "7243371580");
                    if(mdnMap.get(req.getDeviceId()) != null){
                        svcMangNo = mdnMap.get(req.getDeviceId());
                    }else{
                      throw new StorePlatformException("정상적으로 svc_mang_no가 조회되지 않았습니다.");
                    }
                }else{
                    svcMangNo = this.commService.getSvcMangNo(req.getDeviceId(), req.getDeviceTelecom(), req.getNativeId(), null);
                }

                keyType = MemberConstants.KEY_TYPE_SVC_MANG_NO;
                keyValue = svcMangNo;
            }

        }else {
            keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
            keyValue = req.getDeviceId();
        }

        /* 회원정보 조회 */
        CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, keyType, keyValue);

		/* 회원 존재유무 확인 */
        if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
			/* 회원 정보가 존재 하지 않습니다. */
            throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
        }

        /* 개인정보 3자 제공 동의약관 동의여부 체크 */
        if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
                && !StringUtils.equals(this.isAgreementByAgreementCode(requestHeader, chkDupRes.getUserMbr()
                .getUserKey(), MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_OTHERS, chkDupRes
                .getUserMbr().getIsDormant()), "Y")) {
            throw new StorePlatformException("SAC_MEM_1506"); // 개인정보 3자 제공 동의약관 미동의 상태입니다.
        }

        /* 휴대기기 정보 조회 */
        DeviceInfo dbDeviceInfo = this.deviceService.srhDevice(requestHeader, keyType, keyValue, chkDupRes.getUserMbr().getUserKey());

        /**
         * MVNO 단말 인증
         * mdn이 있는 경우  imei가 (req = db) 일치하는 경우 인증 성공 처리, imei가 다르면 mdn에 대한 탈퇴 처리
         */
        if(StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)
                && StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKM)){
            LOGGER.info("MVNO MDN 회원의 인증 처리 {}, {}", dbDeviceInfo.getUserKey(), req.getDeviceId());

            if(!StringUtils.equals(req.getNativeId(), dbDeviceInfo.getNativeId())){
                LOGGER.info("IMEI 정보 상이로 탈퇴 처리 {}, {}", req.getNativeId(), dbDeviceInfo.getNativeId());

                // 회원탈퇴처리
				RemoveUserRequest scReq = new RemoveUserRequest();
				scReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				scReq.setUserKey(dbDeviceInfo.getUserKey());
				scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
				scReq.setSecedeReasonMessage("MVNO MDN 회원 IMEI 정보 상이로 탈퇴 처리");
				this.userSCI.remove(scReq);

                // 탈퇴 MQ연동
                RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
                mqInfo.setUserId(dbDeviceInfo.getUserId());
                mqInfo.setUserKey(dbDeviceInfo.getUserKey());
                mqInfo.setDeviceId(dbDeviceInfo.getMdn());
                mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
                LOGGER.info("{} 탈퇴 MQ info: {}", dbDeviceInfo.getMdn(), mqInfo);
                try {
                    this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
                } catch (AmqpException ex) {
                    LOGGER.error("MQ process fail {}", mqInfo);
                }

                throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
            }
        }

        /* 휴면계정인 경우 복구 처리 */
        if (StringUtils.equals(chkDupRes.getUserMbr().getIsDormant(), MemberConstants.USE_Y)) {
            LOGGER.info("{} 휴면 {} 회원 복구", req.getDeviceId(),
                    StringUtils.equals(chkDupRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE) ? "모바일" : "IDP ID");
            MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
            moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
            moveUserInfoSacReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
            this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
        }

        /**
         * 휴대기기 정보 수정.
         */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserKey(dbDeviceInfo.getUserKey());
        deviceInfo.setDeviceIdType(req.getDeviceIdType()); // 기기 ID 타입

        if(StringUtils.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
            deviceInfo.setDeviceId("");
            deviceInfo.setMdn(req.getDeviceId()); // MDN 번호
        }else {
            deviceInfo.setDeviceId(req.getDeviceId()); // 기기 ID
        }
        deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
        deviceInfo.setDeviceAccount(req.getDeviceAccount()); // 기기 계정 (Gmail)
        deviceInfo.setNativeId(req.getNativeId()); // 기기고유 ID (imei)
        deviceInfo.setNativeId(req.getNativeId()); // 기기 IMEI
        deviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList()); // 단말부가정보

        String deviceKey = this.deviceService.regDeviceInfo(requestHeader, dbDeviceInfo);

        if(!StringUtils.equals(dbDeviceInfo.getDeviceKey(), deviceKey)){
            LOGGER.info("MDN 회원 의 조회된 deviceKey 와 수정 처리한 deviceKey 정보가 상이 {}, {}", dbDeviceInfo.getDeviceKey(), deviceKey);
            throw new StorePlatformException("SAC_MEM_1102"); // 휴대기기 등록에 실패하였습니다.
        }

		/* Device 로그인 성공이력 저장 */
        this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceIp(),
                req.getIsAutoUpdate(), req.getLoginReason(), "Y", dbDeviceInfo.getDeviceKey());

		/* 일시정지 인경우 (로그인제한상태 / 직권중지상태는 MDN로그인시는 샵클 정상이용가능하게 해야하므로 체크하지 않음) */
		if (StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_PAUSE)) {
			res.setUserKey(chkDupRes.getUserMbr().getUserKey());
			res.setUserType(chkDupRes.getUserMbr().getUserType());
			res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
			res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
			res.setIsLoginSuccess("Y");
			return res;
		}

		// 한도 요금제 여부 로직 추가 (2015-05-27 => 06-24)
		// 공통 파라미터 셋팅
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		searchDeviceRequest.setUserKey(dbDeviceInfo.getUserKey());
		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(dbDeviceInfo.getDeviceKey());
		keySearchList.add(keySchUserKey);
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		boolean limitChargeFlag = true;
		List<UserMbrDeviceDetail> userMbrDeviceDetails = searchDeviceResponse.getUserMbrDevice()
				.getUserMbrDeviceDetail();
		if (userMbrDeviceDetails != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetail : userMbrDeviceDetails) {

				// 단말 부가속성중 한도요금제 속성 여부
				if (StringUtils.equals(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN,
						userMbrDeviceDetail.getExtraProfile())) {

					if (StringUtils.isNotBlank(userMbrDeviceDetail.getUpdateDatePlue7())) {
						if (NumberUtils.createLong(userMbrDeviceDetail.getUpdateDatePlue7()) > NumberUtils
								.createLong(userMbrDeviceDetail.getNowDate())) {
							limitChargeFlag = false;
						}
					} else if (StringUtils.isNotBlank(userMbrDeviceDetail.getRegDatePlus7())) {
						if (NumberUtils.createLong(userMbrDeviceDetail.getRegDatePlus7()) > NumberUtils
								.createLong(userMbrDeviceDetail.getNowDate())) {
							limitChargeFlag = false;
						}
					}
					break;
				}
			}
		}

		// UAPS 연동, 단말 부가정보 등록
		// (2015-07-22 패치 제거).
		// 자사, 오늘 끝자리와 디바이스 끝번호가 같을 경우 (ex : '20150612 == '01088880002') UAPS 연동
		// String today = DateUtil.getToday();
		// 자사(SKT)
		if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, req.getDeviceTelecom())) {
			// 1. 한도요금제 조회시간 +7 < sysdate
			// 2015-07-22 --> 2. 조건 항목 패치 제거
			// 2. 현재 날짜의 끝자리(12일 -> 2)와 휴대폰 번호 끝자리(01012345432 -> 2)가 동일한 경우
			// if (limitChargeFlag
			// && StringUtils.equals(today.substring(today.length() - 1),
			// req.getDeviceId().substring(req.getDeviceId().length() - 1))) {
			if (limitChargeFlag) {
				UpdateDeviceManagementRequest updateDeviceManagementRequest = new UpdateDeviceManagementRequest();
				updateDeviceManagementRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				updateDeviceManagementRequest.setDeviceKey(dbDeviceInfo.getDeviceKey());
				updateDeviceManagementRequest.setUserKey(dbDeviceInfo.getUserKey());

				UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN);
				userMbrDeviceDetail.setExtraProfileValue(MemberConstants.USE_N);
				userMbrDeviceDetail.setDeviceKey(dbDeviceInfo.getDeviceKey());
				userMbrDeviceDetail.setUserKey(dbDeviceInfo.getUserKey());
				userMbrDeviceDetail.setRegID(dbDeviceInfo.getUserId());
				try {
					// UAPS 연동 : 한도요금제(NA00002125) 여부 확인
					UserEcRes userEcRes = this.commService.getMappingInfo(req.getDeviceId(), "mdn");
					if (userEcRes != null) {
						if (userEcRes.getServiceCD() != null) {
							for (String serviceCD : userEcRes.getServiceCD()) {
								if (StringUtils.equals("NA00002125", serviceCD)) {
									userMbrDeviceDetail.setExtraProfileValue(MemberConstants.USE_Y);
									break;
								}
							}
						}
					}
					List<UserMbrDeviceDetail> setDeviceDetails = new ArrayList<UserMbrDeviceDetail>();
					setDeviceDetails.add(userMbrDeviceDetail);
					updateDeviceManagementRequest.setUserMbrDeviceDetail(setDeviceDetails);

					this.deviceSCI.updateDeviceManagement(updateDeviceManagementRequest);

					// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (성공)
					final String tlogUserKey = dbDeviceInfo.getUserKey();
					final String tlogDeviceKey = dbDeviceInfo.getDeviceKey();
					final String tlogDeviceId = dbDeviceInfo.getDeviceId();
					final String tlogTingYn = userMbrDeviceDetail.getExtraProfileValue();
					final String resultMessage = "팅요금제 사용여부 업데이트 성공(MDN인증 v2)";

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey)
									.insd_device_id(tlogDeviceKey).device_id(tlogDeviceId).result_code("Y")
									.result_message(resultMessage).ting_yn(tlogTingYn);
						}
					});

				} catch (StorePlatformException e) {
					// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (실패)
					final String tlogUserKey = dbDeviceInfo.getUserKey();
					final String tlogDeviceKey = dbDeviceInfo.getDeviceKey();
					final String tlogDeviceId = dbDeviceInfo.getDeviceId();
					final String tlogTingYn = userMbrDeviceDetail.getExtraProfileValue();
					final String resultMessage = "팅요금제 사용여부 업데이트 실패(MDN인증 v2) ErrCode " + e.getErrorInfo().getCode();

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey)
									.insd_device_id(tlogDeviceKey).device_id(tlogDeviceId).result_code("N")
									.result_message(resultMessage).ting_yn(tlogTingYn);
						}
					});
					LOGGER.info("UAPS getMappingInfo MDN : {}, errorCode : {}, errorMessage : {}", req.getDeviceId(), e
							.getErrorInfo().getCode(), e.getErrorInfo().getMessage());
				}
			}
		}

		/* 로그인 결과 */
		res.setUserKey(chkDupRes.getUserMbr().getUserKey());
		res.setUserType(chkDupRes.getUserMbr().getUserType());
		res.setUserMainStatus(chkDupRes.getUserMbr().getUserMainStatus());
		res.setUserSubStatus(chkDupRes.getUserMbr().getUserSubStatus());
		res.setDeviceKey(deviceKey);
		res.setIsLoginSuccess("Y");

		return res;
	}

	/**
	 * 모바일 전용 회원 인증 v3.
	 *
	 * @param requestHeader SacRequestHeader
	 * @param req           AuthorizeByMdnV3SacReq
	 * @return AuthorizeByMdnV3SacRes
	 */
	@Override
	public AuthorizeByMdnV3SacRes authorizeByMdnV3(SacRequestHeader requestHeader, AuthorizeByMdnV3SacReq req) {

		String oDeviceId = req.getDeviceId();
		DeviceInfo deviceInfo = null;
		// 모번호 조회 및 셋팅
		req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));
		String svcMangNo = null;

		if (!this.commService.isOpmd(oDeviceId)) {
			// 서비스 관리 번호 조회
			try {
				svcMangNo = commService.getSvcMangNo(req.getMdn(), req.getDeviceTelecom(), req.getNativeId(), req.getSimSerialNo());
			}catch(StorePlatformException e){
				if(StringUtils.equals(e.getErrorInfo().getCode(), "SAC_MEM_0003")){ // 타사 연동시 비회원 응답
					// DB에 회원정보가 있으면 invalid 처리 한다.
					this.userWithdrawService.removeDevice(requestHeader, req.getMdn());
					throw e;
				}
			}

			if(StringUtils.isNotBlank(svcMangNo)){
				// 서비스관리번호로 휴대기기 정보 조회
				deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_SVC_MANG_NO, svcMangNo, null);
				if(deviceInfo != null){
					DeviceInfo updateDeviceInfo = new DeviceInfo();
					updateDeviceInfo.setUserKey(deviceInfo.getUserKey());
					updateDeviceInfo.setDeviceId(req.getDeviceId());
					updateDeviceInfo.setMdn(req.getMdn());
					updateDeviceInfo.setDeviceTelecom(req.getDeviceTelecom());
					updateDeviceInfo.setNativeId(req.getNativeId());
					updateDeviceInfo.setDeviceSimNm(req.getSimSerialNo());
					updateDeviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
					deviceService.regDeviceInfo(requestHeader, updateDeviceInfo);
				}else{
					// 서비스관리번호로 없는경우 MDN 으로 조회
					deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_MDN, req.getMdn(), null);
					if(deviceInfo != null){
						if(StringUtils.equals(req.getNativeId(), deviceInfo.getNativeId())
								&& StringUtils.equals(req.getDeviceTelecom(), deviceInfo.getDeviceTelecom())){
							// 서비스 관리번호 업데이트
							ModifyDeviceRequest modifyDeviceRequest = new ModifyDeviceRequest();
							modifyDeviceRequest.setUserKey(deviceInfo.getUserKey());
							UserMbrDevice userMbrDevice = new UserMbrDevice();
							userMbrDevice.setDeviceKey(deviceInfo.getDeviceKey());
							userMbrDevice.setSvcMangNum(svcMangNo);
							modifyDeviceRequest.setUserMbrDevice(userMbrDevice);
							deviceSCI.modifyDevice(modifyDeviceRequest);

							DeviceInfo updateDeviceInfo = new DeviceInfo();
							updateDeviceInfo.setUserKey(deviceInfo.getUserKey());
							updateDeviceInfo.setDeviceId(req.getDeviceId());
							updateDeviceInfo.setMdn(req.getMdn());
							updateDeviceInfo.setDeviceTelecom(req.getDeviceTelecom());
							updateDeviceInfo.setNativeId(req.getNativeId());
							updateDeviceInfo.setDeviceSimNm(req.getSimSerialNo());
							updateDeviceInfo.setDeviceExtraInfoList(req.getDeviceExtraInfoList());
							deviceService.regDeviceInfo(requestHeader, updateDeviceInfo);
						}else{
							this.userWithdrawService.removeDevice(requestHeader, req.getMdn());
							throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
						}
					}else{
						throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
					}
				}
			}else{
				deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_MDN, req.getMdn(), null);
				if(deviceInfo != null){
					if (StringUtils.equals(req.getDeviceId(), deviceInfo.getDeviceId())
							&& StringUtils.equals(req.getNativeId(), deviceInfo.getNativeId())
							&& StringUtils.equals(req.getSimSerialNo(), deviceInfo.getDeviceSimNm())) {
						// 서비스관리번호 조회 실패 했지만 mdn으로 조회한 결과와 deviceId, imei, sim정보가 같으면 인증 성공처리
					}else{
						this.userWithdrawService.removeDevice(requestHeader, req.getMdn());
						throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
					}
				}else{
					throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
				}
			}
		}else{ // 자번호로 인증 요청한 opmd 단말 처리
			deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_MDN, req.getMdn(), null);
			if(deviceInfo != null){
				if(StringUtils.equals(req.getDeviceTelecom(), deviceInfo.getDeviceTelecom())){
					// opmd 단말인 경우 mdn으로 조회한 결과와 deviceTelecom정보가 같으면 인증 성공처리
				}else{
					this.userWithdrawService.removeDevice(requestHeader, req.getMdn());
					throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
				}
			}else{
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
			}
		}



		/* 로그인 성공이력 저장 */
		this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceIp(), null, null, "Y", deviceInfo.getDeviceKey());

		AuthorizeByMdnV3SacRes res = new AuthorizeByMdnV3SacRes();
		res.setUserKey(deviceInfo.getUserKey());
		res.setDeviceKey(deviceInfo.getDeviceKey());
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
		String userKey = null;
		String deviceKey = null;
		DeviceInfo deviceInfo = null;

		/** 1. 모번호 조회 및 셋팅 */
		req.setDeviceId(this.commService.getOpmdMdnInfo(oDeviceId));

		/** 2. req deviceId keyType 결정 */
		String keyType = MemberConstants.KEY_TYPE_MDN;
		if (ValidationCheckUtils.isDeviceId(req.getDeviceId())) {
			keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
		}

		/** 3. 회원 유무 조회 */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, keyType, req.getDeviceId());
		if (chkDupRes.getUserMbr() != null) {
			LOGGER.info("{} 휴면계정유무 : {}", oDeviceId, chkDupRes.getUserMbr().getIsDormant());
		}

		/** 3-1. 회원이라면 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) {
            /** 3-1-1. OPMD 단말이 아니라면 */
			if (!isOpmd) {
				/** 3-1-1-1. 휴대기기 정보 조회후 변동성 체크 */
				deviceInfo = this.deviceService.srhDevice(requestHeader, keyType, req.getDeviceId(), null);
				userKey = deviceInfo.getUserKey();
				deviceKey = deviceInfo.getDeviceKey();

				/** req, DB의 통신사가 일치한 경우 */
				if (this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceTelecom(),
						deviceInfo.getDeviceTelecom(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_TELECOM)) {
					/** req, DB의 통신사가 일치 하고 IMEI가 다르면 */
					if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getNativeId(),
							deviceInfo.getNativeId(), MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {
						/** 3-1-1-2. 변동성 체크 실패 : req, DB의 통신사가 일치, IMEI이 불일치, GMAIL이 불일치 */
						if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(),
								deviceInfo.getDeviceAccount(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {
							isVariability = "N";
						}

					}
				/** 통신사가 다른경우 */
				} else {
					LOGGER.info("{} telecom 정보 수정 {} -> {}", req.getDeviceId(), deviceInfo.getDeviceTelecom(),
							req.getDeviceTelecom());
					/** 3-1-1-3. 변동성 체크 실패 : req, DB의 통신사와 GMAIL이 불일치 */
					if (!this.deviceService.isEqualsLoginDevice(req.getDeviceId(), req.getDeviceAccount(),
							deviceInfo.getDeviceAccount(), MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {
						isVariability = "N";
					}

				}
			}else{
				LOGGER.info("{} {} OPMD 단말 변동성 성공처리", req.getDeviceId(), oDeviceId);
			}

			/** tLog 정보 셋팅 */
			if (deviceInfo != null) {
				final String tLogDeviceId = req.getDeviceId();
				final String tLogMnoTypeReq = req.getDeviceTelecom();
				final String tLogEmailReq = req.getDeviceAccount();
				final String tLogImeiReq = req.getNativeId();
				final String tLogUserKey = deviceInfo.getUserKey();
				final String tLogDeviceKey = deviceKey;
				final String tLogMnoTypeDB = deviceInfo.getDeviceTelecom();
				final String tLogEmailDB = deviceInfo.getDeviceAccount();
				final String tLogImeiDB = deviceInfo.getNativeId();
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SAC_MEM_0005").device_id(tLogDeviceId).mno_type_req(tLogMnoTypeReq)
								.email_req(tLogEmailReq).imei_req(tLogImeiReq).insd_usermbr_no(tLogUserKey)
								.insd_device_id(tLogDeviceKey).mno_type_db(tLogMnoTypeDB).email_db(tLogEmailDB)
								.imei_db(tLogImeiDB);
					}
				});
			}
		/** 회원이 아니라면 오류 처리 */
		}else{
			throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());
		}

		/** 4. 변동성 체크 성공 처리 */
		if (StringUtils.equals(isVariability, "Y")) {

			LOGGER.info("{} 변동성 체크 성공", req.getDeviceId());
            /** 4-1. OPMD 단말이 아니라면 */
			if (!isOpmd) {

				/** 4-1-1. GMAIL 업데이트 */
				ModifyDeviceRequest modifyDeviceRequest = new ModifyDeviceRequest();
				UserMbrDevice userMbrDevice = new UserMbrDevice();
				modifyDeviceRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				modifyDeviceRequest.setUserKey(userKey);
				if ( StringUtils.equals(keyType, MemberConstants.KEY_TYPE_MDN)) {
					userMbrDevice.setMdn(req.getDeviceId());
				} else {
					userMbrDevice.setDeviceID(req.getDeviceId());
				}
				if (StringUtils.isNotBlank(req.getDeviceAccount())) {
					userMbrDevice.setDeviceAccount(req.getDeviceAccount());
				}
				if (StringUtils.isNotBlank(req.getDeviceTelecom())) {
					userMbrDevice.setDeviceTelecom(req.getDeviceTelecom());
				}
				modifyDeviceRequest.setUserMbrDevice(userMbrDevice);


				ModifyDeviceResponse modifyDeviceResponse = this.deviceSCI.modifyDevice(modifyDeviceRequest);
				res.setDeviceKey(modifyDeviceResponse.getDeviceKey());

			}else{
				res.setDeviceKey(deviceKey);
				LOGGER.info("{} {} OPMD 단말 변동성 휴대기기 업데이트 안함", req.getDeviceId(), oDeviceId);
			}

			res.setUserKey(userKey);

		/** 5. 변동성 체크 실패 처리 */
		} else {

			LOGGER.info("{} 변동성 체크 실패", req.getDeviceId());

			/** 5-1. 인증수단 조회 */
			UserAuthMethod userAuthMethod = this.srhUserAuthMethod(requestHeader, req.getDeviceId(), userKey);

			/** 5-2. 인증수단이 없는 경우 탈퇴 처리 */
			if (StringUtils.isBlank(userAuthMethod.getUserId())
					&& StringUtils.equals(userAuthMethod.getIsRealName(), "N")) {

				LOGGER.info("{} 추가인증수단 없음, 탈퇴처리", req.getDeviceId());

				/** 5-2-1. MQ 연동(회원 탈퇴) */
				DetailReq detailReq = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn(MemberConstants.USE_Y);
				detailReq.setDeviceId(req.getDeviceId());
				detailReq.setSearchExtent(searchExtent);
				DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
				RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
				try {
					mqInfo.setUserId(detailRes.getUserInfo().getUserId());
					mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setDeviceId(req.getDeviceId());
					List<UserExtraInfo> list = detailRes.getUserInfo().getUserExtraInfoList();
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							UserExtraInfo extraInfo = list.get(i);
							if (StringUtils.equals(MemberConstants.USER_EXTRA_PROFILEIMGPATH,
									extraInfo.getExtraProfile())) {
								mqInfo.setProfileImgPath(extraInfo.getExtraProfileValue());
							}
						}
					}
					this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

				} catch (AmqpException ex) {
					LOGGER.error("MQ process fail {}", mqInfo);
				}

				/** 5-2-2. SC 회원탈퇴 */
				RemoveUserRequest removeUserReq = new RemoveUserRequest();
				removeUserReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
				removeUserReq.setUserKey(userKey);
				removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
				removeUserReq.setSecedeReasonCode(MemberConstants.WITHDRAW_REASON_OTHER);
				removeUserReq.setSecedeReasonMessage("변동성인증수단없음");
				removeUserReq.setIsDormant(chkDupRes.getUserMbr().getIsDormant());
				this.userSCI.remove(removeUserReq);

				/** 5-2-3. 회원 탈퇴후 오류 처리 회원 정보가 존재 하지 않습니다. */
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", req.getDeviceId());

			/** 5-2. 인증수단이 있는 경우 응답값 셋팅 처리 */
			} else {

				LOGGER.info("{} 추가인증수단 userId : {}, 실명인증여부 : {}", req.getDeviceId(), userAuthMethod.getUserId(),
						userAuthMethod.getIsRealName());

				res.setUserAuthMethod(userAuthMethod);

			}

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
		String userType = null;
		String userMainStatus = null;
		String userSubStatus = null;
		String isDormant = null;
		AuthorizeByIdRes res = new AuthorizeByIdRes();

		/** 1. 회원정보 조회 (ID로 조회). */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, MemberConstants.KEY_TYPE_MBR_ID,
				userId);

		/** 1-1. 회원정보 없거나 회원상태가 정상이 아니면 오류 - 회원 정보가 존재 하지 않습니다. */
		if (chkDupRes.getUserMbr() == null
				|| !StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(),MemberConstants.MAIN_STATUS_NORMAL)) {
			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "userId", userId);
		}

		/** 2. 조회된 회원정보 셋팅. */
		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		isDormant = chkDupRes.getUserMbr().getIsDormant();

		/** 2-2. 일시정지 상태면 응답처리. */
		if (StringUtils.equals(userMainStatus, MemberConstants.MAIN_STATUS_PAUSE)) {
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setIsLoginSuccess("Y");
			return res;
		}

		try{
			/** 3-1. 그외의 회원은 req의 pwd 일치 체크 */
			CheckUserPwdResponse chkUserPwdRes = this.checkUserPwd(requestHeader, userKey, userPw, isDormant);
			if (chkUserPwdRes.getUserKey()==null || chkUserPwdRes.getUserKey().length() <= 0) {
				throw new StorePlatformException("SAC_MEM_1406", userKey);
			}

			/**  3-1-1. 해당계정이 휴면아이디라면 정상 복구 */
			if (StringUtils.equals(isDormant, MemberConstants.USE_Y)) {
				LOGGER.info("{} 휴면 회원 복구", req.getUserId());
				MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
				moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
				moveUserInfoSacReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
				this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
			}

			/**  3-1-3. 로그인 성공이력 저장후 리턴 */
			this.regLoginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress(), "N", null, "Y", null);

			/* 정상 로그인 결과 */
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey,
					userKey));
			res.setIsLoginSuccess("Y");

		/** 3-2. pwd 불일치 - 로그인 실패 */
		} catch ( StorePlatformException e ) {
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				/** 3-2-1. 로그인 실패이력 저장후 리턴 */
				this.regLoginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress(), "N", null, "N", null);

				/* 로그인 결과 */
				res.setIsLoginSuccess("N");
			} else {
				throw e;
			}
		}

		return res;

	}

	/**
	 * ID기반(Tstore ID / Social ID)회원의 인증 기능을 제공한다. [OneStore 단말을 위한 신규규격].
	 *
	 * @param requestHeader SacRequestHeader
	 * @param req           AuthorizeByIdV2SacReq
	 * @return AuthorizeByIdV2SacRes
	 */
	@Override
	public AuthorizeByIdV2SacRes authorizeByIdV2(SacRequestHeader requestHeader, AuthorizeByIdV2SacReq req) {

		AuthorizeByIdV2SacRes res = new AuthorizeByIdV2SacRes();

		/* 회원정보 조회 (ID로 조회) */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, req.getUserId());

		/*  회원정보 없으면 Exception (ID자체가 없음) */
		if (chkDupRes.getUserMbr() == null) {
			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "userId", req.getUserId());
		}

		/*	userAuthToken 유효성 체크 */
		CheckUserAuthTokenRequest chkUserAuthTkReqeust = new CheckUserAuthTokenRequest();
		chkUserAuthTkReqeust.setCommonRequest(commService.getSCCommonRequest(requestHeader));
		chkUserAuthTkReqeust.setUserKey(chkDupRes.getUserMbr().getUserKey());
		chkUserAuthTkReqeust.setUserAuthToken(req.getUserAuthToken());
		chkUserAuthTkReqeust.setIsDormant(chkDupRes.getUserMbr().getIsDormant());
		CheckUserAuthTokenResponse chkUserAuthTkResponse = this.userSCI.checkUserAuthToken(chkUserAuthTkReqeust);
		if (chkUserAuthTkResponse == null || StringUtils.isBlank(chkUserAuthTkResponse.getUserAuthToken())){ // 유효성 체크 실패
			boolean isValid = false;
			if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_TSTORE)){

			}else if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_FACEBOOK)){

			}else if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_GOOGLE)){

			}else if (StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_NAVER)){

			}

			if(!isValid){
				// 로그인 실패이력 저장
				this.regLoginHistory(requestHeader, req.getUserId(), null, "N", "N", req.getDeviceIp(), "N", null, "N", null);
				res.setIsLoginSuccess(MemberConstants.USE_N);
				return res;
			}
		}

		/* 휴유회원 복구 */
		if (StringUtils.equals(chkDupRes.getUserMbr().getIsDormant(), MemberConstants.USE_Y)) {
			MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
			moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
			moveUserInfoSacReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
			this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
		}

		/*	휴대기기 처리 */
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(chkDupRes.getUserMbr().getUserKey());
		deviceInfo.setDeviceId(req.getDeviceId());
		deviceInfo.setMdn(req.getMdn());
		deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
		deviceInfo.setNativeId(req.getNativeId());
		deviceInfo.setDeviceSimNm(req.getSimSerialNo());
		SearchDeviceListRequest searchDeviceListRequest = new SearchDeviceListRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(chkDupRes.getUserMbr().getUserKey());
		keySearchList.add(key);
		searchDeviceListRequest.setCommonRequest(commService.getSCCommonRequest(requestHeader));
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice(MemberConstants.USE_Y);
		try{
			SearchDeviceListResponse searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);
		}catch(StorePlatformException e){
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				// 대표기기가 없는 회원인경우 대표기기 Y로 업데이트
				deviceInfo.setIsPrimary(MemberConstants.USE_Y);
			}else{
				throw e;
			}
		}

		String deviceKey = this.deviceService.regDeviceInfo(requestHeader, deviceInfo);

		// 로그인 이력 저장
		this.regLoginHistory(requestHeader, req.getUserId(), null, "Y", "N", req.getDeviceIp(), "N", null, "Y", deviceKey);

		res.setUserKey(chkDupRes.getUserMbr().getUserKey());
		res.setDeviceKey(deviceKey);
		res.setUserType(req.getUserType());
		res.setIsLoginSuccess(MemberConstants.USE_Y);
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

		String model = requestHeader.getDeviceHeader().getModel();
		simpleLoginRequest.setDeviceModelNm(model);

		String os = requestHeader.getDeviceHeader().getOs();

		if (StringUtils.isNotBlank(os) && os.contains("/")) {
			simpleLoginRequest.setDeviceOsNm(os.substring(0, os.lastIndexOf("/")));
			simpleLoginRequest.setDeviceOsVersion(os.substring(os.lastIndexOf("/") + 1, os.length()));
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
				req.getPreDeviceId(), null);

		if (macDeviceInfo == null) {
			throw new StorePlatformException("SAC_MEM_0003", req.getPreDeviceType(), req.getPreDeviceId());
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
			LOGGER.info("{} 탈퇴 처리", req.getPreDeviceId());

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
			/* device header 값 셋팅(OS버젼, SC버젼) */
			String osVersion = requestHeader.getDeviceHeader().getOs(); // OS버젼
			String svcVersion = requestHeader.getDeviceHeader().getSvc(); // SC버젼
			if (StringUtils.isNotBlank(osVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
						osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()),
						deviceInfo.getDeviceExtraInfoList()));
			}
			if (StringUtils.isNotBlank(svcVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(
						MemberConstants.DEVICE_EXTRA_SCVERSION,
						svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()),
						deviceInfo.getDeviceExtraInfoList()));
			}

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
//					deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
				} else {

					/* 정상단말로 요청시 DB에 단말정보가 미지원단말정보이면 디폴트 모델명을 닉네임에 셋팅 */
					/*if (StringUtils.equals(mdnDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
							&& StringUtils.equals(mdnDeviceInfo.getDeviceModelNo(),
									MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
						deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
					}*/
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(),
						deviceInfo.getDeviceExtraInfoList()));
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
						majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(),
						deviceInfo.getDeviceExtraInfoList()));
			} else {
			/*	deviceInfo.setDeviceModelNo(null); // 디폴트 모델명이 넘어온경우도 있으므로 초기화 하여 업데이트 하지 않게 함
				if (!StringUtils.equals(mdnDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)
						&& !StringUtils.equals(mdnDeviceInfo.getDeviceNickName(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) { // DB에 저장된 단말정보가 미지원단말이 아닌경우 통신사정보를 업데이트 한다.
					deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
				}*/
			}
			this.deviceService.modDeviceInfo(requestHeader, deviceInfo, false);

			/* 전시/기타, 구매 파트 키 변경 */
			if (StringUtils.equals(isPurchaseChange, "Y")) {
				this.mcic.excuteInternalMethod(true, requestHeader.getTenantHeader().getSystemId(), newUserKey, oldUserKey, newDeviceKey, oldDeviceKey);
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
			this.modStatus(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, req.getPreDeviceId(),
					MemberConstants.USE_N, null, null, MemberConstants.MAIN_STATUS_NORMAL,
					MemberConstants.SUB_STATUS_NORMAL);

			/* mac -> mdn으로 변경 처리 및 휴대기기 정보 수정 */
			LOGGER.info("{} -> {} deviceId 변경", req.getPreDeviceId(), req.getDeviceId());
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

			/* device header 값 셋팅(OS버젼, SC버젼) */
			String osVersion = requestHeader.getDeviceHeader().getOs(); // OS버젼
			String svcVersion = requestHeader.getDeviceHeader().getSvc(); // SC버젼
			if (StringUtils.isNotBlank(osVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
						osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()),
						deviceInfo.getDeviceExtraInfoList()));
			}
			if (StringUtils.isNotBlank(svcVersion)) {
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(
						MemberConstants.DEVICE_EXTRA_SCVERSION,
						svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()),
						deviceInfo.getDeviceExtraInfoList()));
			}

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
//					deviceInfo.setDeviceNickName(majorDeviceInfo.getDeviceNickName());
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(),
						deviceInfo.getDeviceExtraInfoList()));
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
						majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(),
						deviceInfo.getDeviceExtraInfoList()));
			} else {
				deviceInfo.setDeviceModelNo(null); // 디폴트 모델명이 넘어온경우도 있으므로 초기화 하여 업데이트 하지 않게 함
//				if (!StringUtils.equals(macDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)
//						&& !StringUtils.equals(macDeviceInfo.getDeviceNickName(),
//								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) { // MAC정보 DB에 저장된 단말정보가 미지원단말이 아닌경우 통신사정보를
//																			// 업데이트 한다.
//					deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
//				}
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
			this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(), "N", "", "N", null);
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

			if (this.isPurchasedFromTstore(requestHeader, req.getDeviceId(), req.getProdId())) {

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
			this.regLoginHistory(requestHeader, res.getDeviceId(), null, "Y", "Y", res.getDeviceId(), "N", "", "N", null);
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
	public AuthorizeForInAppSacRes authorizeForInAppV3(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회 (989 일 경우만)
		AuthorizeForInAppSacRes res = null;
		String tenantId = this.commService.getTenantIdByDeviceTelecom(req.getDeviceTelecom()); // 이통사 정보로 TenantID 부여

		// 모상품 prodId 최근 다운로드 정보조회
		UserDownloadInfoRes userDownloadInfoRes = this.mcic.getUserDownloadInfo(tenantId, req.getDeviceId(),
				req.getProdId());

		if (userDownloadInfoRes == null || StringUtils.isBlank(userDownloadInfoRes.getLatestTenantId())) {
			res = new AuthorizeForInAppSacRes();
			res.setTrxNo(req.getTrxNo());
			res.setDeviceId(req.getDeviceId());
			res.setDeviceTelecom(req.getDeviceTelecom());
			res.setUserStatus(MemberConstants.INAPP_USER_STATUS_NOT_PURCHASE);
			res.setUserInfo(new UserInfo());
			res.setAgreementList(new ArrayList<Agreement>());
			res.setDeviceInfo(new DeviceInfo());
			res.setPinInfo(new MarketPinInfo());
			res.setMbrAuth(new MbrAuth());
			res.setTstoreEtcInfo(new TstoreEtcInfo());
			return res;
		} else {
			tenantId = userDownloadInfoRes.getLatestTenantId();
		}

		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(tenantId);
		tenant.setSystemId(MemberConstants.SYSTEM_ID_INAPP_2);
		requestHeader.setTenantHeader(tenant);

		String updLastLoginDtYn = null; // 마지막 로그인일자 업데이트 유무
		if (StringUtils.equals(MemberConstants.TENANT_ID_TSTORE, tenantId)) {

			LOGGER.info("{} Tstore 회원인증", req.getDeviceId());
			res = this.getTstoreMemberInfoForInApp(requestHeader, req);
			updLastLoginDtYn = MemberConstants.USE_Y;

		} else {

			LOGGER.info("{} 타사 마켓 회원인증", req.getDeviceId());
			res = this.getMarketMemberInfoForInApp(requestHeader, req, tenantId);
			updLastLoginDtYn = MemberConstants.USE_N;

		}

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, res.getDeviceId(), null, "Y", "Y", res.getDeviceId(), "N", "",
					updLastLoginDtYn, null);
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

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회 (989 일 경우만)

		AuthorizeSacRes res = this.getTstoreMemberInfoForPayPlanet(requestHeader, req);

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, req.getDeviceId(), null, "Y", "Y", req.getDeviceId(), "N", "", "N", null);
		}

		return res;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeForOllehMarket(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForOllehMarketSacReq)
	 */
	@Override
	public AuthorizeForOllehMarketSacRes authorizeForOllehMarket(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForOllehMarketSacReq req) {

		MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
		marketReq.setTrxNo(req.getTrxNo());
		marketReq.setDeviceId(req.getDeviceId());
		marketReq.setDeviceTelecom(req.getDeviceTelecom());
		marketReq.setNativeId(req.getNativeId());
		marketReq.setSimSerialNo(req.getSimSerialNo());
		marketReq.setExtraInfo(req.getExtraInfo());

		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (StringUtils.isNotBlank(svcVersion)) {
			marketReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}

		LOGGER.info("{} authorizeForOllehMarket Request : {}", req.getDeviceId(),
				ConvertMapperUtils.convertObjectToJson(marketReq));

		MarketAuthorizeEcRes marketRes = this.commService.simpleAuthorizeForOllehMarket(marketReq);

		AuthorizeForOllehMarketSacRes res = new AuthorizeForOllehMarketSacRes();

		if (marketRes != null) {

			LOGGER.info("{} authorizeForOllehMarket Response : {}", req.getDeviceId(),
					ConvertMapperUtils.convertObjectToJson(marketRes));

			if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상회원

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

					if (!StringUtils.equals(detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo()
							.getDeviceKey())) {

						try {
							// marketdevicekey 중복 확인 체크 추가
							detailReq.setDeviceId(null);
							detailReq.setMbrNo(marketRes.getDeviceInfo().getDeviceKey());
							DetailV2Res checkDupDetailRes = this.userSearchService.detailV2(requestHeader, detailReq);

							LOGGER.info("marketDeviceKey 변경으로 deviceId 변경 : [ {} ] {} -> {}", marketRes.getDeviceInfo()
									.getDeviceKey(), checkDupDetailRes.getDeviceInfoList().get(0).getDeviceId(),
									marketRes.getDeviceId());
							// deviceId 변경
							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setUserKey(checkDupDetailRes.getUserInfo().getUserKey());
							deviceInfo.setDeviceKey(checkDupDetailRes.getDeviceInfoList().get(0).getDeviceKey());
							deviceInfo.setDeviceId(marketRes.getDeviceId());
							this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

							// 기존 번호 탈퇴 처리
							this.removeMarketUser(requestHeader, detailRes);

							// 회원정보 재조회
							detailReq.setDeviceId(marketRes.getDeviceId());
							detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

						} catch (StorePlatformException e) {
							if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
								// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
								LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes
										.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
								this.removeMarketUser(requestHeader, detailRes);
								this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

								TlogInfo tlogInfo = new TlogInfo();
								tlogInfo.setTlogID("TL_SC_MEM_0006");
								tlogInfo.setDeviceId(req.getDeviceId());
								tlogInfo.setUsermbrNoPre(detailRes.getUserInfo().getImMbrNo());
								tlogInfo.setUsermbrNoPost(marketRes.getDeviceInfo().getDeviceKey());

								// 재가입시킨 회원정보 재조회
								detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

								tlogInfo.setUserKey(detailRes.getUserKey());
								tlogInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
								TlogRequest tlogRequest = new TlogRequest();
								tlogRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
								tlogRequest.setTlogInfo(tlogInfo);
								this.userSCI.tlog(tlogRequest);
							}
						}
					}

					this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

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

							// deviceId 변경 Tlog
							final String tLogUserKey = detailRes.getUserKey();
							final String tLogBeMdn = detailRes.getDeviceInfoList().get(0).getDeviceId();
							final String tLogMdn = marketRes.getDeviceId();
							final String tLogSvcMngNum = detailRes.getDeviceInfoList().get(0).getSvcMangNum();
							final String tLogDeviceKey = detailRes.getDeviceInfoList().get(0).getDeviceKey();
							new TLogUtil().set(new ShuttleSetter() {
								@Override
								public void customize(TLogSentinelShuttle shuttle) {
									shuttle.log_id("TL_SAC_MEM_0002").insd_usermbr_no(tLogUserKey)
											.insd_device_id(tLogDeviceKey).device_id(tLogMdn).device_id_pre(tLogBeMdn)
											.device_id_post(tLogMdn).svc_mng_no(tLogSvcMngNum)
											.insd_device_id_pre(tLogDeviceKey).insd_device_id_post(tLogDeviceKey);
								}
							});

							// 변경된 deviceId로 회원정보 재조회
							detailReq.setDeviceId(marketRes.getDeviceId());
							detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

							this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

						} catch (StorePlatformException ex) {

							if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
								// 회원정보 없으면 Tstore 회원가입
								LOGGER.info("{} Tstore 회원가입", req.getDeviceId());
								this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

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
				userInfo.setProdExpoLevl(marketRes.getDeviceInfo().getProdExpoLevl());

				// 약관 정보
				List<Agreement> agreementList = new ArrayList<Agreement>();
				if (marketRes.getDeviceInfo().getClauseExtraInfoList() != null
						&& marketRes.getDeviceInfo().getClauseExtraInfoList().size() > 0) {
					Agreement agreement = null;
					for (MarketClauseExtraInfoEc clauseInfo : marketRes.getDeviceInfo().getClauseExtraInfoList()) {
						agreement = new Agreement();
						agreement.setExtraAgreementId(clauseInfo.getExtraProfile());
						agreement.setIsExtraAgreement(clauseInfo.getExtraProfileValue());
						agreement.setExtraAgreementURL(clauseInfo.getExtraProfileSetURL());
						agreementList.add(agreement);
					}
				}

				// 휴대기기 정보
				DeviceInfo deviceInfo = new DeviceInfo();
				deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
				deviceInfo.setMarketDeviceKey(detailRes.getUserInfo().getImMbrNo()); // 타사 회선의 고유 Key
				deviceInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
				deviceInfo.setDeviceTelecom(detailRes.getDeviceInfoList().get(0).getDeviceTelecom());

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(marketRes.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S02
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(userInfo);
				res.setAgreementList(agreementList);
				res.setDeviceInfo(deviceInfo);
				if (marketRes.getExtraInfo() == null) {
					res.setExtraInfo("");
				} else {
					res.setExtraInfo(marketRes.getExtraInfo());
				}

				// 로그인 이력 저장
				this.regLoginHistory(requestHeader, marketRes.getDeviceId(), null, "Y", "Y", marketRes.getDeviceId(),
						"N", "", "N", null);

			} else if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NO_MEMBER)) { // 비회원

				// Tstore에 가입된 회원인경우 탈퇴 처리
				DetailReq detailReq = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
				detailReq.setDeviceId(req.getDeviceId());
				detailReq.setSearchExtent(searchExtent);

				try {

					DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
					LOGGER.info("{} 타사마켓 비회원으로 Tstore 탈퇴처리", req.getDeviceId());
					this.removeMarketUser(requestHeader, detailRes);

				} catch (StorePlatformException e) {

					if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
						throw e;
					}
				}

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(req.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S02
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setExtraInfo("");

			} else {

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(req.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S02
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setExtraInfo("");

			}

			// TL_SAC_MEM_0006 tlog set 정상회원응답이 아닌경우 회원상태를 Tlog 남김
			if (!StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
				final String userStatus = marketRes.getUserStatus();
				final String userStatusTxt = this.commService.convertSapUserStatusCode(marketRes.getUserStatus());
				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.result_code(userStatus).result_message(userStatusTxt);
					}
				});
			}

		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeForUplusStore(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForUplusStoreSacReq)
	 */
	@Override
	public AuthorizeForUplusStoreSacRes authorizeForUplusStore(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForUplusStoreSacReq req) {

		MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
		marketReq.setTrxNo(req.getTrxNo());
		marketReq.setDeviceId(req.getDeviceId());
		marketReq.setDeviceTelecom(req.getDeviceTelecom());
		marketReq.setNativeId(req.getNativeId());
		marketReq.setSimSerialNo(req.getSimSerialNo());
		marketReq.setDeviceType(req.getDeviceType());
		marketReq.setExtraInfo(req.getExtraInfo());

		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (StringUtils.isNotBlank(svcVersion)) {
			marketReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}

		LOGGER.info("{} authorizeForUplusStore Request : {}", req.getDeviceId(),
				ConvertMapperUtils.convertObjectToJson(marketReq));

		MarketAuthorizeEcRes marketRes = this.commService.simpleAuthorizeForUplusStore(marketReq);

		AuthorizeForUplusStoreSacRes res = new AuthorizeForUplusStoreSacRes();

		if (marketRes != null) {

			LOGGER.info("{} authorizeForUplusStore Response : {}", req.getDeviceId(),
					ConvertMapperUtils.convertObjectToJson(marketRes));

			if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상회원

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

					if (!StringUtils.equals(detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo()
							.getDeviceKey())) {

						// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
						LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes
								.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
						this.removeMarketUser(requestHeader, detailRes);
						this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

						TlogInfo tlogInfo = new TlogInfo();
						tlogInfo.setTlogID("TL_SC_MEM_0006");
						tlogInfo.setDeviceId(req.getDeviceId());
						tlogInfo.setUsermbrNoPre(detailRes.getUserInfo().getImMbrNo());
						tlogInfo.setUsermbrNoPost(marketRes.getDeviceInfo().getDeviceKey());

						// 재가입시킨 회원정보 재조회
						detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

						tlogInfo.setUserKey(detailRes.getUserKey());
						tlogInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
						TlogRequest tlogRequest = new TlogRequest();
						tlogRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
						tlogRequest.setTlogInfo(tlogInfo);
						this.userSCI.tlog(tlogRequest);

					}

					this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

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

							// deviceId 변경 Tlog
							final String tLogUserKey = detailRes.getUserKey();
							final String tLogBeMdn = detailRes.getDeviceInfoList().get(0).getDeviceId();
							final String tLogMdn = marketRes.getDeviceId();
							final String tLogSvcMngNum = detailRes.getDeviceInfoList().get(0).getSvcMangNum();
							final String tLogDeviceKey = detailRes.getDeviceInfoList().get(0).getDeviceKey();
							new TLogUtil().set(new ShuttleSetter() {
								@Override
								public void customize(TLogSentinelShuttle shuttle) {
									shuttle.log_id("TL_SAC_MEM_0002").insd_usermbr_no(tLogUserKey)
											.insd_device_id(tLogDeviceKey).device_id(tLogMdn).device_id_pre(tLogBeMdn)
											.device_id_post(tLogMdn).svc_mng_no(tLogSvcMngNum)
											.insd_device_id_pre(tLogDeviceKey).insd_device_id_post(tLogDeviceKey);
								}
							});

							// 변경된 deviceId로 회원정보 재조회
							detailReq.setDeviceId(marketRes.getDeviceId());
							detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

							this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

						} catch (StorePlatformException ex) {

							if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
								// 회원정보 없으면 Tstore 회원가입
								LOGGER.info("{} Tstore 회원가입", req.getDeviceId());
								this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

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
				userInfo.setProdExpoLevl(marketRes.getDeviceInfo().getProdExpoLevl());

				// 약관 정보
				List<Agreement> agreementList = new ArrayList<Agreement>();
				if (marketRes.getDeviceInfo().getClauseExtraInfoList() != null
						&& marketRes.getDeviceInfo().getClauseExtraInfoList().size() > 0) {
					Agreement agreement = null;
					for (MarketClauseExtraInfoEc clauseInfo : marketRes.getDeviceInfo().getClauseExtraInfoList()) {
						agreement = new Agreement();
						agreement.setExtraAgreementId(clauseInfo.getExtraProfile());
						agreement.setIsExtraAgreement(clauseInfo.getExtraProfileValue());
						agreement.setExtraAgreementURL(clauseInfo.getExtraProfileSetURL());
						agreementList.add(agreement);
					}
				}

				// 휴대기기 정보
				DeviceInfo deviceInfo = new DeviceInfo();
				deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
				deviceInfo.setMarketDeviceKey(detailRes.getUserInfo().getImMbrNo()); // 타사 회선의 고유 Key
				deviceInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
				deviceInfo.setDeviceTelecom(detailRes.getDeviceInfoList().get(0).getDeviceTelecom());

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(marketRes.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S03
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(userInfo);
				res.setAgreementList(agreementList);
				res.setDeviceInfo(deviceInfo);
				if (marketRes.getExtraInfo() == null) {
					res.setExtraInfo("");
				} else {
					res.setExtraInfo(marketRes.getExtraInfo());
				}

				// 로그인 이력 저장
				this.regLoginHistory(requestHeader, marketRes.getDeviceId(), null, "Y", "Y", marketRes.getDeviceId(),
						"N", "", "N", null);

			} else if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NO_MEMBER)) { // 비회원

				// Tstore에 가입된 회원인경우 탈퇴 처리
				DetailReq detailReq = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
				detailReq.setDeviceId(req.getDeviceId());
				detailReq.setSearchExtent(searchExtent);

				try {

					DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
					LOGGER.info("{} 타사마켓 비회원으로 Tstore 탈퇴처리", req.getDeviceId());
					this.removeMarketUser(requestHeader, detailRes);

				} catch (StorePlatformException e) {

					if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
						throw e;
					}
				}

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(req.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S03
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setExtraInfo("");

			} else {

				res.setTrxNo(req.getTrxNo());
				res.setDeviceId(req.getDeviceId());
				res.setTenantId(requestHeader.getTenantHeader().getTenantId()); // S03
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setExtraInfo("");

			}

			// TL_SAC_MEM_0006 tlog set 정상회원응답이 아닌경우 회원상태를 Tlog 남김
			if (!StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
				final String userStatus = marketRes.getUserStatus();
				final String userStatusTxt = this.commService.convertSapUserStatusCode(marketRes.getUserStatus());
				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.result_code(userStatus).result_message(userStatusTxt);
					}
				});
			}

		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeV2(com.skplanet.storeplatform.sac.common
	 * .header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV2SacReq)
	 */
	@Override
	public AuthorizeV2SacRes authorizeV2(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeV2SacReq req) {

		req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId())); // 모번호 조회

		AuthorizeV2SacRes res = new AuthorizeV2SacRes();
		res.setTrxNo(req.getTrxNo());
		res.setTenantId(req.getTenantId());

		if (StringUtils.equals(req.getTenantId(), MemberConstants.TENANT_ID_TSTORE)) {

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
					res.setUserMainStatus(MemberConstants.INAPP_USER_STATUS_NO_MEMBER); // 회원상태 비회원
				} else {
					res.setUserMainStatus(MemberConstants.INAPP_USER_STATUS_SYSTEM_ERROR); // 시스템 연동 오류
				}

				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setPinInfo(new MarketPinInfo());
				res.setMbrAuth(new MbrAuth());
				res.setTstoreEtcInfo(new TstoreEtcInfo());

				return res;
			}

			// imei 업데이트
			if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
					&& StringUtils.isNotBlank(req.getNativeId())
					&& !StringUtils.equals(req.getNativeId(), detailRes.getDeviceInfoList().get(0).getNativeId())) {

				try {
					String cspImei = this.deviceService.getIcasImei(req.getDeviceId());
					if (StringUtils.equals(req.getNativeId(), cspImei)) {
						LOGGER.info("{} imei 변경 : {} -> {}", req.getDeviceId(), detailRes.getDeviceInfoList().get(0)
								.getNativeId(), cspImei);
						UserMbrDevice userMbrDevice = new UserMbrDevice();
						userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE);
						userMbrDevice.setNativeID(cspImei);
						userMbrDevice.setDeviceID(req.getDeviceId());
						userMbrDevice.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
						CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
						createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
						createDeviceReq.setUserKey(detailRes.getUserKey());
						//createDeviceReq.setIsNew("N");
						createDeviceReq.setUserMbrDevice(userMbrDevice);
						this.deviceSCI.createDevice(createDeviceReq);
					} else {
						LOGGER.info("{} IMEI 불일치 : {}, {}", req.getDeviceId(), req.getNativeId(), cspImei);
						res.setUserMainStatus(MemberConstants.INAPP_USER_STATUS_IMEI_MISMATCH);
						res.setUserInfo(new UserInfo());
						res.setAgreementList(new ArrayList<Agreement>());
						res.setDeviceInfo(new DeviceInfo());
						res.setPinInfo(new MarketPinInfo());
						res.setMbrAuth(new MbrAuth());
						res.setTstoreEtcInfo(new TstoreEtcInfo());
						return res;
					}
				} catch (StorePlatformException e) {
					// error skip
					LOGGER.info("{} icas error : {}, {}", req.getDeviceId(), e.getErrorInfo().getCode(), e
							.getErrorInfo().getMessage());
				}
			}

			// 사용자 기본정보
			UserInfo userInfo = new UserInfo();
			userInfo.setUserKey(detailRes.getUserInfo().getUserKey());
			userInfo.setUserId(detailRes.getUserInfo().getUserId());
			userInfo.setUserType(detailRes.getUserInfo().getUserType());
			userInfo.setProdExpoLevl(this.getProdExpoLevl(detailRes.getUserInfo().getRealAge()));
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
			KeySearch keySearch = null;
			keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySearch.setKeyString(detailRes.getDeviceInfoList().get(0).getDeviceKey());
			keySearchList.add(keySearch);

			keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			keySearch.setKeyString(detailRes.getUserKey());
			keySearchList.add(keySearch);

			searchDeviceSetInfoRequest.setKeySearchList(keySearchList);

			SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = this.deviceSetSCI
					.searchDeviceSetInfo(searchDeviceSetInfoRequest);
			MarketPinInfo pinInfo = new MarketPinInfo();
			pinInfo.setIsPinSet(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPin());
			pinInfo.setIsPinRetry(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPinRetry());
			pinInfo.setIsPinClosed(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthLockYn());
			pinInfo.setSetPinUrl(this.getPinSetUrl(pinInfo));

			res.setUserMainStatus(MemberConstants.INAPP_USER_STATUS_NORMAL); // 회원상태 정상
			res.setUserAuthKey(this.tempUserAuthKey); // 임시 인증키
			res.setUserInfo(userInfo);
			res.setAgreementList(detailRes.getAgreementList());
			res.setDeviceInfo(deviceInfo);
			res.setPinInfo(pinInfo);
			res.setMbrAuth(detailRes.getMbrAuth()); // 실명인증정보
			res.setTstoreEtcInfo(this.getTstoreEtcInfo(requestHeader, deviceInfo.getDeviceId(),
					deviceInfo.getDeviceTelecom(), userInfo)); // 기타정보

		} else { // 타사 인증

			MarketAuthorizeEcRes marketRes = null;

			// 타사 마켓회원 인증 요청
			MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
			marketReq.setTrxNo(req.getTrxNo());
			marketReq.setDeviceId(req.getDeviceId());
			marketReq.setDeviceTelecom(req.getDeviceTelecom());
			marketReq.setNativeId(req.getNativeId());
			marketReq.setSimSerialNo(req.getSimSerialNo());
			marketReq.setUserVerifyReason("ShopClient");

			LOGGER.info("{} authorizeMarket Request : {}", req.getDeviceId(),
					ConvertMapperUtils.convertObjectToJson(marketReq));

			if (StringUtils.equals(MemberConstants.TENANT_ID_OLLEH_MARKET, req.getTenantId())) {
				marketRes = this.commService.authorizeForOllehMarket(marketReq);
				LOGGER.info("{} authorizeForOllehMarket Response : {}", req.getDeviceId(),
						ConvertMapperUtils.convertObjectToJson(marketRes));
			} else if (StringUtils.equals(MemberConstants.TENANT_ID_UPLUS_STORE, req.getTenantId())) {
				marketRes = this.commService.authorizeForUplusStore(marketReq);
				LOGGER.info("{} authorizeForUplusStore Response : {}", req.getDeviceId(),
						ConvertMapperUtils.convertObjectToJson(marketRes));
			}

			if (marketRes != null) {

				if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상인증

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

						if (!StringUtils.equals(detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo()
								.getDeviceKey())) {

							// Olleh market 일 경우 중복 체크 후 처리 2015.08.10
							if (StringUtils.equals(MemberConstants.TENANT_ID_OLLEH_MARKET, req.getTenantId())) {
								try {
									// marketdevicekey 중복 확인 체크 추가
									detailReq.setDeviceId(null);
									detailReq.setMbrNo(marketRes.getDeviceInfo().getDeviceKey());
									DetailV2Res checkDupDetailRes = this.userSearchService.detailV2(requestHeader,
											detailReq);

									LOGGER.info("marketDeviceKey 변경으로 deviceId 변경 : [ {} ] {} -> {}", marketRes
											.getDeviceInfo().getDeviceKey(),
											checkDupDetailRes.getDeviceInfoList().get(0).getDeviceId(), marketRes
													.getDeviceId());
									// deviceId 변경
									DeviceInfo deviceInfo = new DeviceInfo();
									deviceInfo.setUserKey(checkDupDetailRes.getUserInfo().getUserKey());
									deviceInfo
											.setDeviceKey(checkDupDetailRes.getDeviceInfoList().get(0).getDeviceKey());
									deviceInfo.setDeviceId(marketRes.getDeviceId());
									this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

									// 기존 번호 탈퇴 처리
									this.removeMarketUser(requestHeader, detailRes);

									// 회원정보 재조회
									detailReq.setDeviceId(marketRes.getDeviceId());
									detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

								} catch (StorePlatformException e) {
									if (StringUtils.equals(e.getErrorInfo().getCode(),
											MemberConstants.SC_ERROR_NO_USERKEY)) {
										// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
										LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(),
												detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo()
														.getDeviceKey());
										this.removeMarketUser(requestHeader, detailRes);
										this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(),
												marketRes);

										TlogInfo tlogInfo = new TlogInfo();
										tlogInfo.setTlogID("TL_SC_MEM_0006");
										tlogInfo.setDeviceId(req.getDeviceId());
										tlogInfo.setUsermbrNoPre(detailRes.getUserInfo().getImMbrNo());
										tlogInfo.setUsermbrNoPost(marketRes.getDeviceInfo().getDeviceKey());

										// 재가입시킨 회원정보 재조회
										detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

										tlogInfo.setUserKey(detailRes.getUserKey());
										tlogInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
										TlogRequest tlogRequest = new TlogRequest();
										tlogRequest
												.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
										tlogRequest.setTlogInfo(tlogInfo);
										this.userSCI.tlog(tlogRequest);
									}
								}

							} else {
								// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
								LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes
										.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
								this.removeMarketUser(requestHeader, detailRes);
								this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

								// 재가입시킨 회원정보 재조회
								detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
							}

						}

						this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

					} catch (StorePlatformException e) {

						if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {

							// 타사 회원키로 가입된 회원정보 조회
							detailReq.setDeviceId(null);
							detailReq.setMbrNo(marketRes.getDeviceInfo().getDeviceKey());
							detailReq.setSearchExtent(searchExtent);

							try {

								detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

								// 번호변경 케이스로 판단하여 deviceId 업데이트
								LOGGER.info("{} deviceId 변경 : {} -> {}", req.getDeviceId(), detailRes
										.getDeviceInfoList().get(0).getDeviceId(), marketRes.getDeviceId());
								DeviceInfo deviceInfo = new DeviceInfo();
								deviceInfo.setUserKey(detailRes.getUserInfo().getUserKey());
								deviceInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
								deviceInfo.setDeviceId(marketRes.getDeviceId());
								this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

								// 변경된 deviceId로 회원정보 재조회
								detailReq.setDeviceId(marketRes.getDeviceId());
								detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

								this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

							} catch (StorePlatformException ex) {

								if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
									// 회원정보 없으면 Tstore 회원가입
									LOGGER.info("{} Tstore 회원가입", req.getDeviceId());
									this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

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
					userInfo.setImSvcNo(detailRes.getUserInfo().getImSvcNo());
					userInfo.setImMbrNo(detailRes.getUserInfo().getImMbrNo());
					userInfo.setUserPhoneCountry(detailRes.getUserInfo().getUserPhoneCountry());
					userInfo.setUserEmail(detailRes.getUserInfo().getUserEmail());
					userInfo.setIsRecvEmail(detailRes.getUserInfo().getIsRecvEmail());
					userInfo.setIsRealName(detailRes.getUserInfo().getIsRealName());
					userInfo.setUserCountry(detailRes.getUserInfo().getUserCountry());
					userInfo.setUserLanguage(detailRes.getUserInfo().getUserLanguage());
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
					if (StringUtils.isNotBlank(marketRes.getDeviceInfo().getDeviceKeyAuth())) {
						deviceInfo.setDeviceKeyAuth(marketRes.getDeviceInfo().getDeviceKeyAuth()); // U+ Store 필드
					}

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

					res.setUserMainStatus(marketRes.getUserStatus()); // 회원상태 정상
					res.setUserAuthKey(this.tempUserAuthKey);
					res.setUserInfo(userInfo);
					res.setAgreementList(agreementList);
					res.setDeviceInfo(deviceInfo);
					res.setPinInfo(pinInfo);
					res.setMbrAuth(new MbrAuth()); // 타사회원은 존재하지 않음
					res.setTstoreEtcInfo(new TstoreEtcInfo()); // 타사회원은 존재하지 않음

				} else if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NO_MEMBER)) { // 비회원

					// Tstore에 가입된 회원인경우 탈퇴 처리
					DetailReq detailReq = new DetailReq();
					SearchExtentReq searchExtent = new SearchExtentReq();
					searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
					detailReq.setDeviceId(req.getDeviceId());
					detailReq.setSearchExtent(searchExtent);

					try {

						DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
						LOGGER.info("{} 타사마켓 비회원으로 Tstore 탈퇴처리", req.getDeviceId());
						this.removeMarketUser(requestHeader, detailRes);

					} catch (StorePlatformException e) {

						if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
							throw e;
						}
					}

					res.setUserMainStatus(marketRes.getUserStatus());
					res.setUserInfo(new UserInfo());
					res.setAgreementList(new ArrayList<Agreement>());
					res.setDeviceInfo(new DeviceInfo());
					res.setPinInfo(new MarketPinInfo());
					res.setMbrAuth(new MbrAuth());
					res.setTstoreEtcInfo(new TstoreEtcInfo());

				} else {

					res.setUserMainStatus(marketRes.getUserStatus());
					res.setUserInfo(new UserInfo());
					res.setAgreementList(new ArrayList<Agreement>());
					res.setDeviceInfo(new DeviceInfo());
					res.setPinInfo(new MarketPinInfo());
					res.setMbrAuth(new MbrAuth());
					res.setTstoreEtcInfo(new TstoreEtcInfo());

				}
			}
		}

		// 로그인 이력 저장
		if (StringUtils.equals(res.getUserMainStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) {
			this.regLoginHistory(requestHeader, res.getDeviceInfo().getDeviceId(), null, "Y", "Y", res.getDeviceInfo()
					.getDeviceId(), "N", "", "N", null);
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

		// imei 업데이트
		if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& StringUtils.isNotBlank(req.getNativeId())
				&& !StringUtils.equals(req.getNativeId(), detailRes.getDeviceInfoList().get(0).getNativeId())) {

			try {
				String cspImei = this.deviceService.getIcasImei(req.getDeviceId());
				if (StringUtils.equals(req.getNativeId(), cspImei)) {
					LOGGER.info("{} imei 변경 : {} -> {}", req.getDeviceId(), detailRes.getDeviceInfoList().get(0)
							.getNativeId(), cspImei);
					UserMbrDevice userMbrDevice = new UserMbrDevice();
					userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE);
					userMbrDevice.setNativeID(cspImei);
					userMbrDevice.setDeviceID(req.getDeviceId());
					userMbrDevice.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
					CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
					createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
					createDeviceReq.setUserKey(detailRes.getUserKey());
					//createDeviceReq.setIsNew("N");
					createDeviceReq.setUserMbrDevice(userMbrDevice);
					this.deviceSCI.createDevice(createDeviceReq);
				} else {
					LOGGER.info("{} IMEI 불일치 : {}, {}", req.getDeviceId(), req.getNativeId(), cspImei);
					res.setUserStatus(MemberConstants.INAPP_USER_STATUS_IMEI_MISMATCH);
					res.setUserInfo(new UserInfo());
					res.setAgreementList(new ArrayList<Agreement>());
					res.setDeviceInfo(new DeviceInfo());
					res.setPinInfo(new MarketPinInfo());
					res.setMbrAuth(new MbrAuth());
					res.setTstoreEtcInfo(new TstoreEtcInfo());
					return res;
				}
			} catch (StorePlatformException e) {
				// error skip
				LOGGER.info("{} icas error : {}, {}", req.getDeviceId(), e.getErrorInfo().getCode(), e.getErrorInfo()
						.getMessage());
			}
		}

		// 휴면계정인 경우 복구처리
		if (StringUtils.equals(detailRes.getUserInfo().getIsDormant(), MemberConstants.USE_Y)) {
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(detailRes.getUserInfo().getUserKey());
			userMbr.setImSvcNo(detailRes.getUserInfo().getImSvcNo());
			userMbr.setUserType(detailRes.getUserInfo().getUserType());
			this.recorverySleepUser(requestHeader, req.getDeviceId(), userMbr);
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
		KeySearch keySearch = null;
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySearch.setKeyString(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		keySearchList.add(keySearch);
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySearch.setKeyString(detailRes.getUserKey());
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
				&& StringUtils.equals(pinInfo.getIsPinClosed(), MemberConstants.USE_Y)) { // PIN 재설정
			pinSetUrl = this.pinCodeInitUrl;
		} else if (StringUtils.equals(pinInfo.getIsPinSet(), MemberConstants.USE_Y)
				&& StringUtils.equals(pinInfo.getIsPinClosed(), MemberConstants.USE_N)) { // PIN 확인
			pinSetUrl = this.pinCodeConfirmUrl;
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
			GetAdditionalServiceRes res = this.additionalServiceService.getAdditionalService(req);

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
			prodExpoLevl = MemberConstants.PROD_EXPO_LEVL_19_MORE;
		} else if (age >= 18) {
			prodExpoLevl = MemberConstants.PROD_EXPO_LEVL_18_MORE;
		} else if (age >= 15) {
			prodExpoLevl = MemberConstants.PROD_EXPO_LEVL_15_MORE;
		} else if (age >= 12) {
			prodExpoLevl = MemberConstants.PROD_EXPO_LEVL_12_MORE;
		} else {
			prodExpoLevl = MemberConstants.PROD_EXPO_LEVL_12_UNDER;
		}
		return prodExpoLevl;
	}

	/**
	 * <pre>
	 * 회원 연령코드를 생년월일로 변환.
	 * 만생년 + 1231 임시로 생성
	 * </pre>
	 *
	 * @param prodExpoLevl
	 *            String
	 * @return birth 생년월일
	 */
	private String getProdExpoLevlToBirth(String prodExpoLevl) {

		String birth = "";
		if (StringUtils.isBlank(prodExpoLevl)) {
			return birth;
		}

		Integer year = Integer.parseInt(DateUtil.getToday("yyyy"));

		if (MemberConstants.PROD_EXPO_LEVL_19_MORE.equals(prodExpoLevl)) {
			year -= 20;
		} else if (MemberConstants.PROD_EXPO_LEVL_18_MORE.equals(prodExpoLevl)) {
			year -= 19;
		} else if (MemberConstants.PROD_EXPO_LEVL_15_MORE.equals(prodExpoLevl)) {
			year -= 16;
		} else if (MemberConstants.PROD_EXPO_LEVL_12_MORE.equals(prodExpoLevl)) {
			year -= 13;
		} else if (MemberConstants.PROD_EXPO_LEVL_12_UNDER.equals(prodExpoLevl)) {
			year -= 12;
		} else {
			return birth;
		}

		birth = String.valueOf(year) + "1231";

		return birth;
	}

	/**
	 * <pre>
	 * 생년월일을 회원 연령코드로 변환.
	 * </pre>
	 *
	 * @param birth
	 *            String
	 * @return prodExpoLevl 회원연령코드
	 */
	private String getBirthToProdExpoLevl(String birth) {

		if (StringUtils.isBlank(birth) || !StringUtils.isNumeric(birth) || birth.length() != 8) {
			return "";
		}

		String ageChk = ("19".equals(birth.substring(0, 2))) ? "1" : "3";
		int realAge = CommonUtils.getAgeBySocalNumber(birth.substring(2, 8), ageChk);

		return this.getProdExpoLevl(String.valueOf(realAge));
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
		} else if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_MDN)) {
			key.setKeyType(MemberConstants.KEY_TYPE_MDN);
		} else if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_SVC_MANG_NO)) {
            key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
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
	 * @param isUpdLastLoginDt
	 *            마지막 로그인 일자 업데이트 유무
	 * @param deviceKey
	 *            휴대기기 Key
	 * @return LoginUserResponse
	 */
	private LoginUserResponse regLoginHistory(SacRequestHeader requestHeader, String userId, String userPw,
			String isSuccess, String isMobile, String ipAddress, String isAutoUpdate, String loginReason,
			String isUpdLastLoginDt, String deviceKey) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		LoginUserRequest loginReq = new LoginUserRequest();
		loginReq.setCommonRequest(commonRequest);
		loginReq.setUserID(userId);
		loginReq.setUserPW(userPw);
		loginReq.setDeviceKey(deviceKey);
		loginReq.setIsSuccess(isSuccess);
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
		loginReq.setIsUpdLastLoginDt(isUpdLastLoginDt);

		String model = requestHeader.getDeviceHeader().getModel();
		loginReq.setDeviceModelNm(model);

		String os = requestHeader.getDeviceHeader().getOs();

		if (StringUtils.isNotBlank(os) && os.contains("/")) {
			loginReq.setDeviceOsNm(os.substring(0, os.lastIndexOf("/")));
			loginReq.setDeviceOsVersion(os.substring(os.lastIndexOf("/") + 1, os.length()));
		}

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
	 * @param isDormant
	 *            휴면계정유무
	 * @param loginStatusCode
	 *            로그인 상태코드
	 * @param stopStatusCode
	 *            직권중지 상태코드
	 * @param userMainStatus
	 *            메인 상태코드
	 * @param userSubStatus
	 *            서브메인 상태코드
	 */
	private void modStatus(SacRequestHeader requestHeader, String keyType, String keyString, String isDormant,
			String loginStatusCode, String stopStatusCode, String userMainStatus, String userSubStatus) {

		UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

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
		updStatusUserReq.setIsDormant(isDormant);
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
	 * @param isDormant
	 *            String
	 * @return String 약관 동의 여부
	 */
	private String isAgreementByAgreementCode(SacRequestHeader requestHeader, String userKey, String agreementCode,
			String isDormant) {

		String isAgreeYn = "N";

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
		schAgreeListReq.setCommonRequest(commonRequest);
		schAgreeListReq.setUserKey(userKey);
		schAgreeListReq.setIsDormant(isDormant);
		try {
			SearchAgreementListResponse schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
			for (MbrClauseAgree agreeInfo : schAgreeListRes.getMbrClauseAgreeList()) {
				if (StringUtils.equals(agreeInfo.getExtraAgreementID(), agreementCode)) {
					isAgreeYn = agreeInfo.getIsExtraAgreement();
					break;
				}

			}
		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					&& !StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
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

		// 타사 마켓회원 인증 요청
		MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
		marketReq.setTrxNo(req.getTrxNo());
		marketReq.setDeviceId(req.getDeviceId());
		marketReq.setDeviceTelecom(req.getDeviceTelecom());
		marketReq.setNativeId(req.getNativeId());
		marketReq.setSimSerialNo(req.getSimSerialNo());
		marketReq.setUserVerifyReason("InApp");

		LOGGER.info("{} authorizeMarket Request : {}", req.getDeviceId(),
				ConvertMapperUtils.convertObjectToJson(marketReq));

		if (StringUtils.equals(MemberConstants.TENANT_ID_OLLEH_MARKET, tenantId)) {
			marketRes = this.commService.authorizeForOllehMarket(marketReq);
			LOGGER.info("{} authorizeForOllehMarket Response : {}", req.getDeviceId(),
					ConvertMapperUtils.convertObjectToJson(marketRes));
		} else if (StringUtils.equals(MemberConstants.TENANT_ID_UPLUS_STORE, tenantId)) {
			marketRes = this.commService.authorizeForUplusStore(marketReq);
			LOGGER.info("{} authorizeForUplusStore Response : {}", req.getDeviceId(),
					ConvertMapperUtils.convertObjectToJson(marketRes));
		}

		if (marketRes != null) {

			if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상인증

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

					if (!StringUtils.equals(detailRes.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo()
							.getDeviceKey())) {
						// Olleh market 일 경우 중복 체크 후 처리 2015.08.10
						if (StringUtils.equals(MemberConstants.TENANT_ID_OLLEH_MARKET, tenantId)) {
							try {
								// marketdevicekey 중복 확인 체크 추가
								detailReq.setDeviceId(null);
								detailReq.setMbrNo(marketRes.getDeviceInfo().getDeviceKey());
								DetailV2Res checkDupDetailRes = this.userSearchService.detailV2(requestHeader,
										detailReq);

								LOGGER.info("marketDeviceKey 변경으로 deviceId 변경 : [ {} ] {} -> {}", marketRes
										.getDeviceInfo().getDeviceKey(), checkDupDetailRes.getDeviceInfoList().get(0)
										.getDeviceId(), marketRes.getDeviceId());
								// deviceId 변경
								DeviceInfo deviceInfo = new DeviceInfo();
								deviceInfo.setUserKey(checkDupDetailRes.getUserInfo().getUserKey());
								deviceInfo.setDeviceKey(checkDupDetailRes.getDeviceInfoList().get(0).getDeviceKey());
								deviceInfo.setDeviceId(marketRes.getDeviceId());
								this.deviceService.modDeviceInfo(requestHeader, deviceInfo, true);

								// 기존 번호 탈퇴 처리
								this.removeMarketUser(requestHeader, detailRes);

								// 회원정보 재조회
								detailReq.setDeviceId(marketRes.getDeviceId());
								detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

							} catch (StorePlatformException e) {
								if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
									// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
									LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes
											.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
									this.removeMarketUser(requestHeader, detailRes);
									this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

									TlogInfo tlogInfo = new TlogInfo();
									tlogInfo.setTlogID("TL_SC_MEM_0006");
									tlogInfo.setDeviceId(req.getDeviceId());
									tlogInfo.setUsermbrNoPre(detailRes.getUserInfo().getImMbrNo());
									tlogInfo.setUsermbrNoPost(marketRes.getDeviceInfo().getDeviceKey());

									// 재가입시킨 회원정보 재조회
									detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

									tlogInfo.setUserKey(detailRes.getUserKey());
									tlogInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
									TlogRequest tlogRequest = new TlogRequest();
									tlogRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
									tlogRequest.setTlogInfo(tlogInfo);
									this.userSCI.tlog(tlogRequest);
								}
							}

						} else {
							// 타사 deviceKey가 다르면 탈퇴 후 재가입(사용자 변경)
							LOGGER.info("{} 회원탈퇴 후 재가입 타사 userKey 변경 : {} -> {}", req.getDeviceId(), detailRes
									.getUserInfo().getImMbrNo(), marketRes.getDeviceInfo().getDeviceKey());
							this.removeMarketUser(requestHeader, detailRes);
							this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

							// 재가입시킨 회원정보 재조회
							detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
						}

					}

					this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

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

							this.updateMarketUserInfo(requestHeader, req.getDeviceId(), detailRes, marketRes);

						} catch (StorePlatformException ex) {

							if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
								// 회원정보 없으면 Tstore 회원가입
								LOGGER.info("{} Tstore 회원가입", req.getDeviceId());
								this.joinMaketUser(requestHeader, req.getDeviceId(), req.getNativeId(), marketRes);

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
				userInfo.setImSvcNo(detailRes.getUserInfo().getImSvcNo());
				userInfo.setImMbrNo(detailRes.getUserInfo().getImMbrNo());
				userInfo.setUserPhoneCountry(detailRes.getUserInfo().getUserPhoneCountry());
				userInfo.setUserEmail(detailRes.getUserInfo().getUserEmail());
				userInfo.setIsRecvEmail(detailRes.getUserInfo().getIsRecvEmail());
				userInfo.setIsRealName(detailRes.getUserInfo().getIsRealName());
				userInfo.setUserCountry(detailRes.getUserInfo().getUserCountry());
				userInfo.setUserLanguage(detailRes.getUserInfo().getUserLanguage());
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
				if (StringUtils.isNotBlank(marketRes.getDeviceInfo().getDeviceKeyAuth())) {
					deviceInfo.setDeviceKeyAuth(marketRes.getDeviceInfo().getDeviceKeyAuth()); // U+ Store 필드
				}

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

				res.setTrxNo(req.getTrxNo());
				res.setTenantId(tenantId);
				res.setDeviceId(marketRes.getDeviceId());
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserAuthKey(this.tempUserAuthKey);
				res.setUserInfo(userInfo);
				res.setAgreementList(agreementList);
				res.setDeviceInfo(deviceInfo);
				res.setPinInfo(pinInfo);
				res.setMbrAuth(new MbrAuth()); // 타사회원은 존재하지 않음
				res.setTstoreEtcInfo(new TstoreEtcInfo()); // 타사회원은 존재하지 않음

			} else if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NO_MEMBER)) { // 비회원

				// Tstore에 가입된 회원인경우 탈퇴 처리
				DetailReq detailReq = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
				detailReq.setDeviceId(req.getDeviceId());
				detailReq.setSearchExtent(searchExtent);

				try {

					DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
					LOGGER.info("{} 타사마켓 비회원으로 Tstore 탈퇴처리", req.getDeviceId());
					this.removeMarketUser(requestHeader, detailRes);

				} catch (StorePlatformException e) {

					if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
						throw e;
					}
				}

				res.setTrxNo(req.getTrxNo());
				res.setTenantId(tenantId);
				res.setDeviceId(req.getDeviceId());
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setPinInfo(new MarketPinInfo());
				res.setMbrAuth(new MbrAuth());
				res.setTstoreEtcInfo(new TstoreEtcInfo());

			} else {

				res.setTrxNo(req.getTrxNo());
				res.setTenantId(tenantId);
				res.setDeviceId(req.getDeviceId());
				res.setDeviceTelecom(req.getDeviceTelecom());
				res.setUserStatus(marketRes.getUserStatus());
				res.setUserInfo(new UserInfo());
				res.setAgreementList(new ArrayList<Agreement>());
				res.setDeviceInfo(new DeviceInfo());
				res.setPinInfo(new MarketPinInfo());
				res.setMbrAuth(new MbrAuth());
				res.setTstoreEtcInfo(new TstoreEtcInfo());

			}
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
	 * @param detailRes
	 *            DetailV2Res
	 */
	private void removeMarketUser(SacRequestHeader requestHeader, DetailV2Res detailRes) {
		// 회원탈퇴처리
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		scReq.setUserKey(detailRes.getUserKey());
		scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
		scReq.setSecedeReasonMessage("SAP 회원탈퇴");
		this.userSCI.remove(scReq);

		// 탈퇴 MQ연동
		RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
		//mqInfo.setTenantId(detailRes.getDeviceInfoList().get(0).getTenantId());
		mqInfo.setUserId(detailRes.getDeviceInfoList().get(0).getUserId());
		mqInfo.setUserKey(detailRes.getUserKey());
		mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
		mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
		LOGGER.info("{} 탈퇴 MQ info: {}", detailRes.getDeviceInfoList().get(0).getDeviceId(), mqInfo);
		try {
			this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
		} catch (AmqpException ex) {
			LOGGER.error("MQ process fail {}", mqInfo);
		}
	}

	/**
	 * <pre>
	 * 타사 마켓회원 Tstore 회원가입 처리.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param nativeId
	 *            String
	 * @param marketRes
	 *            MarketAuthorizeEcRes
	 * @return userKey String
	 */
	private String joinMaketUser(SacRequestHeader requestHeader, String deviceId, String nativeId,
			MarketAuthorizeEcRes marketRes) {

		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader)); // 공통코드 셋팅

		String birth = this.getProdExpoLevlToBirth(marketRes.getDeviceInfo().getProdExpoLevl());

		// SC 사용자 기본정보 setting
		UserMbr userMbr = new UserMbr();
		userMbr.setImMbrNo(marketRes.getDeviceInfo().getDeviceKey()); // MBR_NO
		userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE); // 모바일 회원
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 정상
		userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 정상
		userMbr.setIsRecvEmail(MemberConstants.USE_N); // 이메일 수신 여부
		userMbr.setUserID(deviceId); // 회원 컴포넌트에서 새로운 MBR_ID 를 생성하여 넣는다.
		userMbr.setIsParent(MemberConstants.USE_N); // 부모동의 여부
		if (StringUtils.isNotBlank(birth)) {
			userMbr.setUserBirthDay(birth); // 생년월일
			userMbr.setIsRealName(MemberConstants.USE_Y);
		}
		createUserRequest.setUserMbr(userMbr);

		// SC 사용자 가입요청
		CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);
		if (createUserResponse.getUserKey() == null || StringUtils.equals(createUserResponse.getUserKey(), "")) {
			throw new StorePlatformException("SAC_MEM_0002", "userKey");
		}

		// SC 휴대기기 등록
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		//createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(createUserResponse.getUserKey());

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);
		userMbrDevice.setDeviceID(marketRes.getDeviceId()); // 기기 ID
		userMbrDevice.setDeviceTelecom(marketRes.getDeviceTelecom()); // 이동 통신사
		userMbrDevice.setDeviceModelNo(MemberConstants.DP_ANY_PHONE_4APP); // 단말 모델
		//userMbrDevice.setDeviceNickName(MemberConstants.DP_ANY_PHONE_4APP_NM); // 단말닉네임
		userMbrDevice.setNativeID(nativeId); // 기기고유 ID (imei)
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
	 * 마켓회원 회원정보 수정.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param detailRes
	 *            DetailV2Res
	 * @param marketRes
	 *            MarketAuthorizeEcRes
	 */
	private void updateMarketUserInfo(SacRequestHeader requestHeader, String deviceId, DetailV2Res detailRes,
			MarketAuthorizeEcRes marketRes) {

		UserMbr userMbr = null;
		String userBirthDay = null;

		// 회원 연령대 코드에 따라 생년월일 업데이트
		if (StringUtils.isBlank(detailRes.getUserInfo().getUserBirthDay())) {
			userBirthDay = this.getProdExpoLevlToBirth(marketRes.getDeviceInfo().getProdExpoLevl());
			LOGGER.info("{} 생년월일 최초 수집 {}", deviceId, userBirthDay);
		} else {
			if (!StringUtils.equals(this.getBirthToProdExpoLevl(detailRes.getUserInfo().getUserBirthDay()), marketRes
					.getDeviceInfo().getProdExpoLevl())) {
				userBirthDay = this.getProdExpoLevlToBirth(marketRes.getDeviceInfo().getProdExpoLevl());
				LOGGER.info("{} 생년월일 변경 {} -> {}", deviceId, detailRes.getUserInfo().getUserBirthDay(), userBirthDay);
			}
		}

		if (StringUtils.isNotBlank(userBirthDay)) {
			if (userMbr == null)
				userMbr = new UserMbr();

			/**
			 * sap회원은 TB_US_MBR_AUTH 미저장. 회원정보조회 V2, LocalSCI 2.1.4.사용자키, 디바이스키를 이용한 회원 정보 조회 에서 보정로직으로 TB_US_USERMBR의
			 * 생년월일이 내려간다.
			 **/
			userMbr.setUserBirthDay(userBirthDay);
			userMbr.setIsRealName(MemberConstants.USE_Y);
		}

		if (userMbr != null) {
			userMbr.setUserKey(detailRes.getUserKey());
			UpdateUserRequest updateUserRequest = new UpdateUserRequest();
			updateUserRequest.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
			updateUserRequest.setUserMbr(userMbr);
			this.userSCI.updateUser(updateUserRequest);
		}
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
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_SAVE;
					agreement.setExtraAgreementURL(info.getExtraProfileSetURL());
				} else if (StringUtils.equals(info.getExtraProfile(), "US003509")) { // 통신과금서비스 이용약관
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE;
					agreement.setExtraAgreementURL(info.getExtraProfileSetURL());
				} else if (StringUtils.equals(info.getExtraProfile(), "US003511")) { // 마켓 서비스 이용약관
					extraAgreementId = MemberConstants.POLICY_AGREEMENT_CLAUSE_TSTORE;
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
		agreementInfoList = this.commService.getClauseMappingInfo(agreementInfoList);

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
	 * @param prodId
	 *            String
	 * @return boolean 구매내역 존재유무
	 */
	private boolean isPurchasedFromTstore(SacRequestHeader requestHeader, String deviceId, String prodId) {

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

			// Tstore 모상품 ID 조회
			IapProductInfoRes iapProductInfoRes = this.mcic.getIapProdInfo(MemberConstants.TENANT_ID_TSTORE, deviceId,
					prodId);

			if (iapProductInfoRes != null && StringUtils.isNotBlank(iapProductInfoRes.getParentProdId())) {
				List<ExistenceItem> existenceItemList = new ArrayList<ExistenceItem>();
				ExistenceItem existenceItem = new ExistenceItem();
				existenceItem.setProdId(iapProductInfoRes.getParentProdId());
				existenceItemList.add(existenceItem);

				// 기구매체크
				ExistenceReq existenceReq = new ExistenceReq();
				existenceReq.setTenantId(MemberConstants.TENANT_ID_TSTORE);
				existenceReq.setUserKey(detailRes.getUserInfo().getUserKey());
				existenceReq.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
				existenceReq.setExistenceItem(existenceItemList);
				LOGGER.info("{} 기구매체크 Request : {}", deviceId, ConvertMapperUtils.convertObjectToJson(existenceReq));
				ExistenceListRes existenceListRes = this.mcic.srhExistenceList(existenceReq);
				boolean exists = existenceListRes != null;
				if (exists) {
					LOGGER.info("{} 기구매체크 Response : {}", deviceId,
							ConvertMapperUtils.convertObjectToJson(existenceListRes));
				} else {
					LOGGER.info("{} 기구매체크 Response : {}", deviceId, existenceListRes);
				}

				if (existenceListRes != null && existenceListRes.getExistenceListRes() != null
						&& existenceListRes.getExistenceListRes().size() > 0) { // 구매내역 존재
					isPurchasedFromTstore = true;
				}
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
		KeySearch keySearch = null;
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySearch.setKeyString(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		keySearchList.add(keySearch);

		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySearch.setKeyString(detailRes.getUserKey());
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

	/**
	 * <pre>
	 * 휴면계정 복구.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param userMbr
	 *            UserMbr
	 */
	private void recorverySleepUser(SacRequestHeader requestHeader, String deviceId, UserMbr userMbr) {
		String idpResultYn = null;
		String idpResultErrorCode = null;
		try {

			if (StringUtils.isNotBlank(userMbr.getImSvcNo())) {
				ActivateUserEcReq activateUserEcReq = new ActivateUserEcReq();
				activateUserEcReq.setKeyType("1");
				activateUserEcReq.setKey(userMbr.getImSvcNo());
				activateUserEcReq.setReqDate(DateUtil.getToday("yyyyMMdd"));
				LOGGER.info("{} 휴면 OneID 회원 복구", deviceId);
				LOGGER.info("{} idp activateUser request : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(activateUserEcReq));
				ActivateUserEcRes activateUserEcRes = this.idpSCI.activateUser(activateUserEcReq);
				LOGGER.info("{} idp activateUser response : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(activateUserEcRes));
			} else {
				AuthForWapEcReq authForWapEcReq = new AuthForWapEcReq();
				authForWapEcReq.setUserMdn(deviceId);
				authForWapEcReq.setAutoActivate(MemberConstants.USE_Y);
				LOGGER.info("{} 휴면 {} 회원 복구", deviceId,
						StringUtils.equals(userMbr.getUserType(), MemberConstants.USER_TYPE_MOBILE) ? "모바일" : "IDP ID");
				LOGGER.info("{} idp authForWap request : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(authForWapEcReq));
				AuthForWapEcRes authForWapEcRes = this.idpSCI.authForWap(authForWapEcReq);
				LOGGER.info("{} idp authForWap response : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(authForWapEcRes));
			}
			idpResultYn = MemberConstants.USE_Y;
		} catch (StorePlatformException e) {
			idpResultYn = MemberConstants.USE_N;
			idpResultErrorCode = StringUtils.substringAfter(e.getErrorInfo().getCode(),
					MemberConstants.EC_IDP_ERROR_CODE_TYPE);
			if (!StringUtils.equals(idpResultErrorCode, IdpConstants.IDP_RES_CODE_STATUS_ALREAY_APPLY)
					&& !StringUtils.equals(idpResultErrorCode, ImIdpConstants.IDP_RES_CODE_STATUS_ALREAY_APPLY)) {
				throw e;
			}
		} finally {
			MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
			moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
			moveUserInfoSacReq.setUserKey(userMbr.getUserKey());
			this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
		}
	}

	/**
	 * <pre>
	 * 타사 간편인증 연동후 marketDeviceKey를 svcMangNo로 업데이트.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @param deviceId
	 *            String
	 * @param deviceTelecom
	 *            String
	 * @param nativeId
	 *            String
	 * @param svcMangNo
	 *            String
	 */
	private void updateMarketDeviceKey(SacRequestHeader requestHeader, String userKey, String deviceId,
			String deviceTelecom, String nativeId, String svcMangNo) {

		if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_LGT)) {

			String trxNo = new StringBuffer("trx").append("-")
					.append(RandomString.getString(20, RandomString.TYPE_NUMBER + RandomString.TYPE_LOWER_ALPHA))
					.append("-").append(DateUtil.getToday("yyyyMMddHHmmssSSS")).toString();

			MarketAuthorizeEcReq marketReq = new MarketAuthorizeEcReq();
			marketReq.setTrxNo(trxNo);
			marketReq.setDeviceId(deviceId);
			marketReq.setDeviceTelecom(deviceTelecom);
			marketReq.setNativeId(nativeId);
			MarketAuthorizeEcRes marketRes = null;
			String svcVersion = requestHeader.getDeviceHeader().getSvc();
			if (StringUtils.isNotBlank(svcVersion)) {
				marketReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
			}

			try {

				LOGGER.info("{} authorizeForUplusStore Request : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(marketReq));
				marketRes = this.commService.simpleAuthorizeForUplusStore(marketReq);
				LOGGER.info("{} authorizeForUplusStore Response : {}", deviceId,
						ConvertMapperUtils.convertObjectToJson(marketRes));

				if (marketRes != null) {
					if (StringUtils.equals(marketRes.getUserStatus(), MemberConstants.INAPP_USER_STATUS_NORMAL)) { // 정상회원
						if (StringUtils.isBlank(svcMangNo)
								|| !StringUtils.equals(svcMangNo, marketRes.getDeviceInfo().getDeviceKey())) {
							ModifyDeviceReq modifyDeviceReq = new ModifyDeviceReq();
							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceId(deviceId);
							deviceInfo.setSvcMangNum(marketRes.getDeviceInfo().getDeviceKey());
							modifyDeviceReq.setUserKey(userKey);
							modifyDeviceReq.setDeviceInfo(deviceInfo);
							this.deviceService.modDevice(requestHeader, modifyDeviceReq);
							LOGGER.info("{} marketDeviceKey update : {}", deviceId, marketRes.getDeviceInfo()
									.getDeviceKey());
						} else {
							LOGGER.info("{} marketDeviceKey equal : {}", deviceId, marketRes.getDeviceInfo()
									.getDeviceKey());
						}
					}
				}
			} catch (StorePlatformException e) {
				// ignore Exception
				LOGGER.info("{} SAP 연동 에러 : {}", deviceId, e.getErrorInfo().getMessage());
			}
		}
	}

	/**
	 * ID에 따른 PW 확인.
	 *
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            사용자 Key
	 * @param userPw
	 *            사용자 PW
	 *
	 * @return CheckUserPwdResponse
	 *
	 */
	private CheckUserPwdResponse checkUserPwd(SacRequestHeader requestHeader, String userKey, String userPw, String isDormant){

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		CheckUserPwdRequest chkUserPwdReq = new CheckUserPwdRequest();
		chkUserPwdReq.setCommonRequest(commonRequest);
		chkUserPwdReq.setUserKey(userKey);
		chkUserPwdReq.setUserPw(userPw);
		chkUserPwdReq.setIsDormant(isDormant);

		return this.userSCI.checkUserPwd(chkUserPwdReq);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#authorizeById
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq)
	 */
	@Override
	public AuthorizeByPwdSacRes authorizeByPassword(SacRequestHeader requestHeader, AuthorizeByPwdSacReq req){

		String userId = req.getUserId();
		String userPw = req.getUserPw();

		String userKey = null;
		String userType = null;
		String isDormant = null;
		AuthorizeByPwdSacRes res = new AuthorizeByPwdSacRes();

		/** 1. 회원정보 조회 (ID로 조회) */
		CheckDuplicationResponse chkDupRes = this.checkDuplicationUser(requestHeader, MemberConstants.KEY_TYPE_MBR_ID,
				userId);

		/**  1-1. 회원정보가 없거나 정상이 아니면 오류 (ID자체가 없음) */
		if (chkDupRes.getUserMbr() == null
				|| !StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(),MemberConstants.MAIN_STATUS_NORMAL)) {
			/* 회원 정보가 존재 하지 않습니다. */
			throw new StorePlatformException("SAC_MEM_0003", "userId", userId);
		}

		/**  2. 조회된 회원정보 셋팅 */
		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		isDormant = chkDupRes.getUserMbr().getIsDormant();

		try{
			/** 3-1. 그외의 회원은 req의 pwd 일치 체크 */
			CheckUserPwdResponse chkUserPwdRes = this.checkUserPwd(requestHeader, userKey, userPw, isDormant);
			if (chkUserPwdRes.getUserKey()==null || chkUserPwdRes.getUserKey().length() <= 0) {
				throw new StorePlatformException("SAC_MEM_1406", userKey);
			}

			/** 3-1-1. 해당계정이 휴면아이디라면 정상 복구 */
			if (StringUtils.equals(isDormant, MemberConstants.USE_Y)) {
				LOGGER.info("{} 휴면 회원 복구", req.getUserId());
				MoveUserInfoSacReq moveUserInfoSacReq = new MoveUserInfoSacReq();
				moveUserInfoSacReq.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
				moveUserInfoSacReq.setUserKey(chkDupRes.getUserMbr().getUserKey());
				this.userService.moveUserInfo(requestHeader, moveUserInfoSacReq);
			}

			/** 3-1-3. 로그인 성공이력 저장후 리턴 */
			this.regLoginHistory(requestHeader, userId, userPw, "Y", "N", null, "N", null, "Y", null);

			/** 3-1-4-1. 로그인 성공시 userAuthToken이 있으면 해당 토큰을 넘겨줌 */
			if ( chkUserPwdRes.getUserAuthToken() != null ){
				res.setUserAuthToken(chkUserPwdRes.getUserAuthToken());
			/** 3-1-4-2. 로그인 성공시 userAuthToken이 없으면 토큰 생성후 넘겨줌 */
			} else {
				CreateUserAuthTokenResponse createUserAuthTokenRes = this.createUserAuthToken(requestHeader, userKey);
				res.setUserAuthToken(createUserAuthTokenRes.getUserAuthToken());
			}

			/* 정상 로그인 결과 */
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setIsLoginSuccess("Y");

		/** 3-2. pwd불일치 - 로그인 실패 */
		} catch( StorePlatformException e ) {
			if ( StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA) ) {
				/** 3-2-1. 로그인 실패이력 저장후 리턴 */
				this.regLoginHistory(requestHeader, userId, userPw, "N", "N", null, "N", null, "N", null);
				/* 실패 로그인 결과 */
				res.setIsLoginSuccess("N");
			} else {
				throw e;
			}
		}

		return res;

	}

	/**
	 * 사용자 인증 토큰 생성 ( 있으면 새로운 인증토큰 생성후 update).
	 *
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            사용자 Key
	 *
	 * @return CreateUserAuthTokenResponse
	 *
	 */
	private CreateUserAuthTokenResponse createUserAuthToken(SacRequestHeader requestHeader, String userKey){

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		CreateUserAuthTokenRequest createUserAuthTokenReq = new CreateUserAuthTokenRequest();
		createUserAuthTokenReq.setCommonRequest(commonRequest);
		createUserAuthTokenReq.setUserKey(userKey);

		return this.userSCI.createUserAuthToken(createUserAuthTokenReq);
	}

}
