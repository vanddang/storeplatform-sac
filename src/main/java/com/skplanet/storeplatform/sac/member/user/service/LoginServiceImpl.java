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
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LoginUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 회원 로그인 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private IDPService idpService;

	@Autowired
	private ImIDPService imIdpService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.LoginService#
	 * authorizeByMdn
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq)
	 */
	@Override
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req) {

		logger.info("############################ LoginServiceImpl authorizeByMdn start ############################");

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
		//		if (StringUtils.equals(userType, MemberConstants.USER_TYPE_MOBILE) && StringUtils.equals(chkDupRes.getIsChangeSubject(), "Y")) {
		//			this.volatileMemberPoc(requestHeader, deviceId, userKey, req.getDeviceTelecom());
		//		}

		/* 원아이디인 경우 */
		if (chkDupRes.getUserMbr().getImSvcNo() != null) {

			/* 단말정보 update */
			this.updateDeviceInfo(requestHeader, userKey, null, req);

			/* 로그인 성공이력 저장 */
			LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, deviceId, "", "Y", "Y", deviceId);

			/* 로그인 결과 */
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));
			res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

		} else { /* 기존IDP회원 / 모바일회원인 경우 */

			try {

				/* 무선회원 인증 */
				this.idpService.authForWap(deviceId);

				/* 단말정보 update */
				this.updateDeviceInfo(requestHeader, userKey, null, req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, deviceId, "", "Y", "Y", deviceId);

				/* 로그인 결과 */
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IDPConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {

					/* 미가입 회원인 경우 로그 님김 */
					logger.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", deviceId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		logger.info("############################ LoginServiceImpl authorizeByMdn end ############################");

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
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req) {

		logger.info("############################ LoginServiceImpl authorizeById start ############################");

		String deviceId = req.getDeviceId();
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
		if (deviceId != null) {
			deviceId = this.commService.getOpmdMdnInfo(deviceId);
		}

		/* 회원정보 조회 */
		CheckDuplicationResponse chkDupRes = this.searchUserInfo(requestHeader, MemberConstants.KEY_TYPE_MBR_ID, userId);

		/* 회원 상태 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "Y")) {

			/* 원아이디 가가입 상태인 경우 */
			if (chkDupRes.getMbrOneID() != null && StringUtils.equals(chkDupRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_WATING)) {

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
				String joinSst = chkDupRes.getMbrOneID().getIntgSiteCode();
				String joinSstCd = null;
				String joinSstNm = null;

				for (Entry<String, String> entry : mapSiteCd.entrySet()) {
					if (StringUtils.contains(joinSst, entry.getKey())) {
						joinSstCd = entry.getKey();
						joinSstNm = entry.getValue();
						break;
					}
				}

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

		} else {

			/* 원아이디 서비스 이용동의 간편 가입 대상 확인 */
			if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() != null) {

				/* 로그인 결과 */
				res.setImIntSvcNo(chkDupRes.getMbrOneID().getIntgSvcNumber());
				res.setLoginStatusCode(chkDupRes.getMbrOneID().getLoginStatusCode());
				res.setStopStatusCode(chkDupRes.getMbrOneID().getStopStatusCode());
				res.setIsLoginSuccess("N");
				return res;

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
		if (chkDupRes.getUserMbr().getImSvcNo() != null) { //원아이디인 경우

			try {

				ImIDPReceiverM imIdpReceiver = this.imIdpService.authForId(userId, userPw);

				/* 잠금해지 요청인 경우 처리 */
				if (StringUtils.equals(req.getReleaseLock(), "Y") && StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {

					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);

					/* 통합IDP 로그인 상태 정상처리 요청 */
					this.imIdpService.setLoginStatus(userId, MemberConstants.USER_LOGIN_STATUS_NOMAL);
				}

				/* 단말정보 update */
				this.updateDeviceInfo(requestHeader, userKey, imIdpReceiver.getResponseBody().getUser_auth_key(), req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				/* 로그인 결과 */
				res.setUserAuthKey(imIdpReceiver.getResponseBody().getUser_auth_key());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, null));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils
						.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + ImIDPConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ ImIDPConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 님김 */
					logger.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		} else { // 기존 IDP 계정인 경우

			try {

				IDPReceiverM idpReceiver = this.idpService.userAuthForId(userId, userPw);

				/* 잠금해지 요청인 경우 */
				if (StringUtils.equals(req.getReleaseLock(), "Y") && StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
					/* 로그인 상태코드 정상처리 */
					this.updateLoginStatus(requestHeader, MemberConstants.USER_LOGIN_STATUS_NOMAL, MemberConstants.KEY_TYPE_MBR_ID, userId);
				}

				/* 단말정보 update */
				this.updateDeviceInfo(requestHeader, userKey, idpReceiver.getResponseBody().getUser_auth_key(), req);

				/* 로그인 성공이력 저장 */
				LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				/* 로그인 결과 */
				res.setUserAuthKey(idpReceiver.getResponseBody().getUser_auth_key());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, null));
				res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

			} catch (StorePlatformException ex) {

				if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IDPConstants.IDP_RES_CODE_WRONG_PASSWD)) {

					/* 로그인 실패이력 저장 */
					LoginUserResponse loginUserRes = this.insertloginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

					/* 로그인 결과 */
					res.setLoginFailCount(String.valueOf(loginUserRes.getLoginFailCount()));
					res.setIsLoginSuccess(loginUserRes.getIsLoginSuccess());

				} else if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
						+ IDPConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

					/* 미존재 회원인 경우 로그 님김 */
					logger.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
					throw ex;

				} else {
					throw ex;
				}

			}

		}

		logger.info("######################## LoginServiceImpl authorizeById end ############################");

		return res;
	}

	/**
	 * 로그인한 deviceId의 deviceKey 조회
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

		if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_DEVICE_ID)) {
			/* mdn 로그인시에는 휴대기기 단건 조회 */
			DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, keyType, keyString, userKey);
			deviceKey = deviceInfo.getDeviceKey();

		} else if (StringUtils.equals(keyType, MemberConstants.KEY_TYPE_INSD_USERMBR_NO)) {
			/* id 로그인시에는 대표휴대기기 조회 */
			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(keyString);
			listDeviceReq.setIsMainDevice("Y");
			ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);
			if (listDeviceRes.getDeviceInfoList() != null && listDeviceRes.getDeviceInfoList().size() == 1) {
				deviceKey = listDeviceRes.getDeviceInfoList().get(0).getDeviceKey();
			}
		}

		return deviceKey;

	}

	/**
	 * SC회원콤포넌트 회원 정보 조회
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
	 * 휴대기기정보 merge
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
	public void updateDeviceInfo(SacRequestHeader requestHeader, String userKey, String userAuthKey, Object obj) {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userKey);

		if (obj instanceof AuthorizeByMdnReq) { // mdn인증

			AuthorizeByMdnReq req = new AuthorizeByMdnReq();
			req = (AuthorizeByMdnReq) obj;

			deviceInfo.setDeviceId(req.getDeviceId());
			deviceInfo.setDeviceIdType(req.getDeviceIdType());
			deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
			deviceInfo.setNativeId(req.getNativeId());
			deviceInfo.setDeviceAccount(req.getDeviceAccount());
			deviceInfo.setUserDeviceExtraInfo(req.getUserDeviceExtraInfo());
			this.deviceService.updateDeviceInfo(requestHeader, deviceInfo);

		} else if (obj instanceof AuthorizeByIdReq) { // id인증

			AuthorizeByIdReq req = new AuthorizeByIdReq();
			req = (AuthorizeByIdReq) obj;

			if (req.getDeviceId() != null) { // deviceId가 파라메터로 넘어왔을 경우에만 휴대기기 정보 merge 요청

				deviceInfo.setDeviceId(req.getDeviceId());
				deviceInfo.setDeviceIdType(req.getDeviceIdType());
				deviceInfo.setDeviceTelecom(req.getDeviceTelecom());
				deviceInfo.setNativeId(req.getNativeId());
				deviceInfo.setDeviceAccount(req.getDeviceAccount());
				deviceInfo.setUserDeviceExtraInfo(req.getUserDeviceExtraInfo());
				this.deviceService.updateDeviceInfo(requestHeader, deviceInfo);

				/* 변경된 휴대기기 정보 IDP도 변경 */
				this.userService.modifyProfileIdp(requestHeader, userKey, userAuthKey);
			}

		}

	}

	/**
	 * 
	 * SC콤포넌트 로그인 이력저장
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
	public LoginUserResponse insertloginHistory(SacRequestHeader requestHeader, String userId, String userPw, String isSuccess, String isMobile,
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

		String svcVersion = requestHeader.getDeviceHeader().getSvcVersion();
		if (svcVersion != null) {
			loginReq.setScVersion(svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()));
		}
		loginReq.setIpAddress(ipAddress);

		LoginUserResponse loginRes = this.userSCI.updateLoginUser(loginReq);
		return loginRes;
	}

	/**
	 * 회원 상태코드 업데이트
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

	/**
	 * 변동성 회원 처리
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            디바이스 아이디
	 * @param userKey
	 *            사용자키
	 * @param deviceTelecom
	 *            통신사코드
	 */
	public void volatileMemberPoc(SacRequestHeader requestHeader, String deviceId, String userKey, String deviceTelecom) {

		logger.info("########## volatileMember process start #########");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* 1. 무선회원 가입 */
		try {

			IDPReceiverM idpReceiver = this.idpService.join4Wap(deviceId, this.commService.convertDeviceTelecom(deviceTelecom));
			String imMbrNo = idpReceiver.getResponseBody().getUser_key(); // IDP 관리번호
			String imMngNum = idpReceiver.getResponseBody().getSvc_mng_num(); // SKT사용자의 경우 사용자 관리번호

			logger.info("[deviceId] {}, [imMbrNo] {}, imMngNum {}", deviceId, imMbrNo, imMngNum);

			/* 2. 회원정보 수정 */
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);
			userMbr.setImMbrNo(imMbrNo);
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);

			UpdateUserRequest updUserReq = new UpdateUserRequest();
			updUserReq.setCommonRequest(commonRequest);
			updUserReq.setUserMbr(userMbr);
			UpdateUserResponse updUserRes = this.userSCI.updateUser(updUserReq);

			if (!StringUtils.equals(updUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
				throw new StorePlatformException("[" + updUserRes.getCommonResponse().getResultCode() + "] "
						+ updUserRes.getCommonResponse().getResultMessage());
			}

			/* 3. imMngNum 부가속성 추가 */
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceId(deviceId);
			deviceInfo.setUserKey(userKey);
			deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
			deviceInfo.setUserDeviceExtraInfo(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_IMMNGNUM, imMngNum, deviceInfo));
			this.deviceService.updateDeviceInfo(requestHeader, deviceInfo);

		} catch (StorePlatformException ex) {
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("########## volatileMember process end #########");
	}

}
