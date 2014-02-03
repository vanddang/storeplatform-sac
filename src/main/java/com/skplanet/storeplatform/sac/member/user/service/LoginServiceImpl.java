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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	MemberCommonComponent commService;

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
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req) throws Exception {

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

		/* 회원 상태 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
			throw new RuntimeException("회원정보 없음.");
		}

		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		loginStatusCode = chkDupRes.getUserMbr().getLoginStatusCode();
		stopStatusCode = chkDupRes.getUserMbr().getStopStatusCode();

		/* 탈퇴 회원, 로그인 제한, 직권중지 인경우 */
		if (StringUtils.equals(userMainStatus, MemberConstants.MAIN_STATUS_SECEDE)
				|| StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)
				|| StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {

			/* 로그인 실패이력 저장 */
			this.insertloginHistory(requestHeader, deviceId, "", "N", "Y", deviceId);

			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
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
			this.insertloginHistory(requestHeader, deviceId, "", "Y", "Y", deviceId);

			/* 로그인 결과 */
			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));

		} else { /* 기존IDP회원 / 모바일회원인 경우 */

			/* 무선회원 인증 */
			IDPReceiverM idpReceiver = this.idpService.authForWap(deviceId);

			if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

				/* 단말정보 update */
				this.updateDeviceInfo(requestHeader, userKey, null, req);

				/* 로그인 성공이력 저장 */
				this.insertloginHistory(requestHeader, deviceId, "", "Y", "Y", deviceId);

				/* 로그인 결과 */
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey));

			} else if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_MDN_AUTH_NOT_WIRELESS_JOIN)) {

				/* 미가입 회원인 경우 로그 님김 */
				logger.info(":::: authorizeByMdn NOT_EXIST_USER :::: devicdId : {}, {}", deviceId, userType);
				throw new StorePlatformException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());

			} else { // 무선회원 인증 실패

				this.insertloginHistory(requestHeader, deviceId, "", "N", "Y", deviceId);
				throw new StorePlatformException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());

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
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req) throws Exception {

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

		logger.info("::: CheckDuplicationResponse : {}", chkDupRes.toString());

		/* 회원 상태 확인 */
		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
			/* 서비스 이용동의 간편 가입 대상 확인 */
			if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() != null) {
				res.setImIntSvcNo(chkDupRes.getMbrOneID().getIntgSvcNumber());
				res.setUserMainStatus(chkDupRes.getMbrOneID().getEntryStatusCode());
				res.setLoginStatusCode(chkDupRes.getMbrOneID().getLoginStatusCode());
				res.setStopStatusCode(chkDupRes.getMbrOneID().getStopStatusCode());
				return res;
			} else {
				throw new StorePlatformException("회원정보 없음.");
			}
		}

		userKey = chkDupRes.getUserMbr().getUserKey();
		userType = chkDupRes.getUserMbr().getUserType();
		userMainStatus = chkDupRes.getUserMbr().getUserMainStatus();
		userSubStatus = chkDupRes.getUserMbr().getUserSubStatus();
		loginStatusCode = chkDupRes.getUserMbr().getLoginStatusCode();
		stopStatusCode = chkDupRes.getUserMbr().getStopStatusCode();

		/* 탈퇴회원, 정지상태 회원, 로그인 제한, 직권중지 인경우 */
		if (StringUtils.equals(userMainStatus, MemberConstants.MAIN_STATUS_SECEDE)
				|| StringUtils.equals(userMainStatus, MemberConstants.MAIN_STATUS_PAUSE)
				|| StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)
				|| StringUtils.equals(stopStatusCode, MemberConstants.USER_STOP_STATUS_PAUSE)) {

			/* 로그인 실패이력 저장 */
			this.insertloginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());

			res.setUserKey(userKey);
			res.setUserType(userType);
			res.setUserMainStatus(userMainStatus);
			res.setUserSubStatus(userSubStatus);
			res.setLoginStatusCode(loginStatusCode);
			res.setStopStatusCode(stopStatusCode);
			return res;
		}

		/* 회원 인증 요청 */
		if (chkDupRes.getUserMbr().getImSvcNo() != null) { //원아이디인 경우

			ImIDPReceiverM imIdpReceiver = this.imIdpService.authForId(userId, userPw);

			if (StringUtils.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {

				this.updateDeviceInfo(requestHeader, userKey, imIdpReceiver.getResponseBody().getUser_auth_key(), req);

				this.insertloginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				res.setUserAuthKey(imIdpReceiver.getResponseBody().getUser_auth_key());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, null));

			} else if (StringUtils.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_INVALID_USER_INFO)) {

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
				String joinSst = imIdpReceiver.getResponseBody().getJoin_sst_list();
				String joinSstCd = "";
				String joinSstNm = "";

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

				res.setUserAuthKey(imIdpReceiver.getResponseBody().getUser_auth_key());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setJoinSiteCd(joinSstCd);
				res.setJoinSiteNm(joinSstNm);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);

			} else if (StringUtils.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_WRONG_PASSWD)) {

				this.insertloginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());
				throw new StorePlatformException("[" + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());

			} else if (StringUtils.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

				/* 미존재 회원인 경우 로그 님김 */
				logger.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);
				throw new StorePlatformException("[" + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());

			} else {
				throw new StorePlatformException("[" + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			}

		} else { // 기존 IDP 계정인 경우

			IDPReceiverM idpReceiver = this.idpService.userAuthForId(userId, userPw);

			if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

				this.updateDeviceInfo(requestHeader, userKey, idpReceiver.getResponseBody().getUser_auth_key(), req);

				this.insertloginHistory(requestHeader, userId, userPw, "Y", "N", req.getIpAddress());

				res.setUserAuthKey(idpReceiver.getResponseBody().getUser_auth_key());
				res.setUserKey(userKey);
				res.setUserType(userType);
				res.setUserMainStatus(userMainStatus);
				res.setUserSubStatus(userSubStatus);
				res.setLoginStatusCode(loginStatusCode);
				res.setStopStatusCode(stopStatusCode);
				res.setDeviceKey(this.getLoginDeviceKey(requestHeader, MemberConstants.KEY_TYPE_INSD_USERMBR_NO, userKey, null));

			} else if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_WRONG_PASSWD)) {

				this.insertloginHistory(requestHeader, userId, userPw, "N", "N", req.getIpAddress());
				throw new StorePlatformException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());

			} else if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_NOT_EXIST_ID)) {

				/* 미존재 회원인 경우 로그 님김 */
				logger.info(":::: NOT_EXIST_USER authorizeById :::: userId : {}, {}", userId, userType);

			} else {
				throw new StorePlatformException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
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
	 * @throws Exception
	 *             Exception
	 */
	public String getLoginDeviceKey(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) throws Exception {

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
	 * @throws Exception
	 *             Exception
	 */
	public CheckDuplicationResponse searchUserInfo(SacRequestHeader requestHeader, String keyType, String keyString) throws Exception {
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

		if (!StringUtils.equals(chkDupRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new StorePlatformException("[" + chkDupRes.getCommonResponse().getResultCode() + "] "
					+ chkDupRes.getCommonResponse().getResultMessage());
		}

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
	 * @throws Exception
	 *             Exception
	 */
	public void updateDeviceInfo(SacRequestHeader requestHeader, String userKey, String userAuthKey, Object obj) throws Exception {

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
	 * @throws Exception
	 *             LoginUserResponse
	 */
	public LoginUserResponse insertloginHistory(SacRequestHeader requestHeader, String userId, String userPw, String isSuccess, String isMobile,
			String ipAddress) throws Exception {
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
		//loginReq.setScVersion(requestHeader.getDeviceHeader().getPkgVersion());//헤더에서 제공해줄 예정
		loginReq.setIpAddress(ipAddress);

		LoginUserResponse loginRes = this.userSCI.loginUser(loginReq);
		if (!StringUtils.equals(loginRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new StorePlatformException("[" + loginRes.getCommonResponse().getResultCode() + "] "
					+ loginRes.getCommonResponse().getResultMessage());
		}
		return loginRes;
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
	 * @throws Exception
	 *             Exception
	 */
	public void volatileMemberPoc(SacRequestHeader requestHeader, String deviceId, String userKey, String deviceTelecom) throws Exception {

		logger.info("########## volatileMember process start #########");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* 1. 무선회원 가입 */
		IDPReceiverM idpReceiver = this.idpService.join4Wap(deviceId, this.commService.convertDeviceTelecom(deviceTelecom));
		if (StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

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

		} else {

			throw new StorePlatformException("[" + idpReceiver.getResponseHeader().getResult() + "] "
					+ idpReceiver.getResponseHeader().getResult_text());

		}

		logger.info("########## volatileMember process end #########");
	}

}
