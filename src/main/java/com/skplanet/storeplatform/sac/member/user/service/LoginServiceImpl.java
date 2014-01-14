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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.LogInUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LogInUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
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

	private static final String SYSTEMID = "S001";
	private static final String TENANTID = "S01";
	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
		commonRequest.setSystemID(SYSTEMID);
		commonRequest.setTenantID(TENANTID);
	}

	@Autowired
	MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UAPSSCI uapsSCI; // UAPS 연동 인터페이스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private DeviceService deviceService; // 휴대기기 관련 서비스

	@Autowired
	private IDPService idpService; // IDP 연동 클래스

	@Autowired
	private ImIDPService imIdpService; // 통합 IDP 연동 클래스

	@Override
	public AuthorizeByMdnRes authorizeByMdn(HeaderVo headerVo, AuthorizeByMdnReq req) throws Exception {

		logger.info("######################## LoginServiceImpl authorizeByMdn start ############################");

		String deviceId = req.getDeviceId();
		String userTypeCd = ""; // 사용자구분코드
		String mainStatusCd = ""; // 메인상태코드

		/* 모번호 조회 */
		deviceId = this.commService.getOpmdMdnInfo(deviceId);

		/* 회원정보 조회 (devicdId) */
		SearchUserResponse schUserRes = this.searchUserInfo(deviceId, null);

		/* 회원 상태 확인 */
		if (schUserRes.getUserMbr() == null
				|| StringUtil.equals(schUserRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_SECEDE)) {
			throw new Exception("무선가입상태가 아닙니다.");
		}

		userTypeCd = schUserRes.getUserMbr().getUserType();
		mainStatusCd = schUserRes.getUserMbr().getUserMainStatus();

		/* 모바일회원인경우 변동성 체크, SC콤포넌트 변동성 회원 여부 필드 확인필요!! */
		if (StringUtil.equals(userTypeCd, MemberConstants.USER_TYPE_MOBILE)) {
			this.volatileMemberPoc(deviceId, schUserRes.getUserMbr().getUserKey());
		}

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();
		String userStateVal = "";
		
		//if (schUserRes.getUserMbr().getImSvcNo() != null) { // 통합아이디
		if (StringUtil.equals(userTypeCd, MemberConstants.USER_TYPE_ONEID)) {
			
			userStateVal = "oneId";
			
			/* 단말정보 merge */
			this.mergeDeviceInfo(schUserRes.getUserMbr().getUserKey(), req, null);
			
			/* 로그인 성공이력 저장 */
			LogInUserResponse loginRes = this.insertloginHistory(deviceId, null, "Y", schUserRes.getUserMbr().getImSvcNo() == null ? "N" : "Y");
			
			res.setUserKey(loginRes.getUserKey());
			res.setUserStatus(userStateVal);
			
		} else {
			
			/* 무선회원 인증 */
			IDPReceiverM idpReceiver = this.idpService.authForWap(deviceId);

			if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(),	IDPConstants.IDP_RES_CODE_OK)) {
				
				if (StringUtil.equals(userTypeCd, MemberConstants.USER_TYPE_MOBILE)) {
					userStateVal = "mobile";
				} else if (StringUtil.equals(userTypeCd, MemberConstants.USER_TYPE_IDPID)) {
					userStateVal = "tstoreId";
				}

				if (StringUtil.equals(mainStatusCd, MemberConstants.MAIN_STATUS_WATING)) {
					userStateVal = "temporary";
				}
				
				/* 단말정보 merge */
				this.mergeDeviceInfo(schUserRes.getUserMbr().getUserKey(), req, null);

				/* 로그인 성공이력 저장 */
				LogInUserResponse loginRes = this.insertloginHistory(deviceId, null, "Y", schUserRes.getUserMbr().getImSvcNo() == null ? "N" : "Y");

				res.setUserKey(loginRes.getUserKey());
				res.setUserStatus(userStateVal);
				
			} else { //무선회원 인증 실패
				/* 로그인 실패이력 저장 */
				this.insertloginHistory(deviceId, null, "N", schUserRes.getUserMbr().getImSvcNo() == null ? "N" : "Y");
				throw new Exception("["	+ idpReceiver.getResponseHeader().getResult() + "] "	+ idpReceiver.getResponseHeader().getResult_text());
			}
			
		}


		logger.info("######################## LoginServiceImpl authorizeByMdn end ############################");

		return res;
	}

	@Override
	public AuthorizeByIdRes authorizeById(HeaderVo headerVo,
			AuthorizeByIdReq req) throws Exception {

		AuthorizeByIdRes res = new AuthorizeByIdRes();

		String userId = req.getUserId();
		String userPw = req.getUserPw();
		String userTypeCd = ""; // 사용자구분코드(US011501:기기사용자,US011502:IDP사용자,US011503:OneID사용자)
		String mainStatusCd = ""; // 메인상태코드(US010201:정상,US010202:탈퇴,US010203:가가입,US010204:일시정지,US010205:전환)

		/* 회원정보 조회 (userId) */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		// schUserReq.setKeyType("MBR_ID");
		// schUserReq.setKeyString(userId);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		/* 회원 상태 확인 */
		if (schUserRes.getUserMbr() == null) {
			throw new Exception("존재하지 않는 아이디입니다");
		}

		userTypeCd = schUserRes.getUserMbr().getUserType();
		mainStatusCd = schUserRes.getUserMbr().getUserMainStatus();

		if (mainStatusCd.equals("US010204")) {// 일시정지
			throw new Exception(
					"로그인 5회 입력 오류로 계정이 잠금 상태가 되었습니다. T store Web에서 해제 후 로그인해 주세요.");
		}

		/* 회원 인증 요청 */
		String userStateVal = "";
		if (userTypeCd.equals("US011502")) { // 기존 IDP회원
			userStateVal = "tstoreId";
		} else if (userTypeCd.equals("US011503")) {// 통합회원
			userStateVal = "oneId";
		} else {
			throw new Exception("SC콤포넌트 사용자구분 코드 확인");
		}

		/* 단말기 정보 존재하는 경우 - 단말정보 조회 및 merge */
		DeviceInfo deviceInfo = new DeviceInfo();
		this.deviceService.mergeDeviceInfo(deviceInfo);

		/* 로그인 성공이력 저장 */
		LogInUserResponse loginRes = this.insertloginHistory(userId, userPw,
				"Y", userTypeCd.equals("US011503") ? "Y" : "N");

		if (loginRes.getIsLoginSuccess().equals("Y")) {
			/* 로그인 Response 셋팅 */
			res.setUserKey(schUserRes.getUserMbr().getUserKey());
			res.setUserStatus(userStateVal);
		} else { // 로그인 실패

		}

		return res;
	}

	/**
	 * 
	 * SC콤포넌트 회원정보 조회
	 * 
	 * @param deviceId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public SearchUserResponse searchUserInfo(String deviceId, String userId) throws Exception {
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		if (deviceId != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		} else if (userId != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		}
		key.setKeyString(deviceId);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);

		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);
		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["
					+ schUserRes.getCommonResponse().getResultCode() + "] "
					+ schUserRes.getCommonResponse().getResultMessage());
		}

		return schUserRes;
	}

	/**
	 * 
	 * 휴대기기정보 merge
	 * 
	 * @param userKey
	 * @param authorizeByMdnReq
	 * @param authorizeByIdReq
	 * @throws Exception 
	 */
	public void mergeDeviceInfo(String userKey, AuthorizeByMdnReq authorizeByMdnReq, AuthorizeByIdReq authorizeByIdReq) throws Exception{
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userKey);
		if (authorizeByMdnReq != null) { // mdn인증인경우
			deviceInfo.setDeviceId(authorizeByMdnReq.getDeviceId());
			deviceInfo.setDeviceTelecom(authorizeByMdnReq.getDeviceTelecom());
			deviceInfo.setDeviceModelNo(authorizeByMdnReq.getDeviceModelNo());
			deviceInfo.setNativeId(authorizeByMdnReq.getNativeId());
			deviceInfo.setRooting(authorizeByMdnReq.getRooting());
			deviceInfo.setDeviceAccount(authorizeByMdnReq.getDeviceAccount());
			deviceInfo.setScVer(authorizeByMdnReq.getScVer());
			deviceInfo.setOsVerOrg(authorizeByMdnReq.getOsVerOrg());
		} else if(authorizeByIdReq != null) { //id인증인 경우
			
		}
		
		this.deviceService.mergeDeviceInfo(deviceInfo);
	}
	
	/**
	 * SC콤포넌트 로그인 이력저장
	 * 
	 * @param key
	 * @param isSuccess
	 * @param isOneId
	 * @return
	 */
	public LogInUserResponse insertloginHistory(String userId, String userPw, String isSuccess, String isOneId) throws Exception {
		LogInUserRequest loginReq = new LogInUserRequest();
		loginReq.setCommonRequest(commonRequest);
		loginReq.setUserID(userId);
		if (userPw != null) {
			loginReq.setUserPW(userPw);
		}
		loginReq.setIsSuccess(isSuccess);
		loginReq.setIsOneID(isOneId);

		LogInUserResponse loginRes = this.userSCI.logInUser(loginReq);
		if (!StringUtil.equals(loginRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["
					+ loginRes.getCommonResponse().getResultCode() + "] "
					+ loginRes.getCommonResponse().getResultMessage());
		}
		return loginRes;
	}

	
	/**
	 * 변동성 회원 처리
	 * 
	 * @param deviceId
	 * @param userKey
	 * @param imMbrNo
	 * @throws Exception
	 */
	public void volatileMemberPoc(String deviceId, String userKey) throws Exception {

		logger.info("########## volatileMember process start #########");

		/* 1. 무선회원 가입 */
		IDPReceiverM idpReceiver = this.idpService.join4Wap(deviceId);
		if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			throw new Exception("["
					+ idpReceiver.getResponseHeader().getResult() + "] "
					+ idpReceiver.getResponseHeader().getResult_text());
		}

		String imMbrNo = idpReceiver.getResponseBody().getUser_key(); // IDP 관리번호
		String imMngNum = idpReceiver.getResponseBody().getSvc_mng_num(); // SKT사용자의 경우 사용자 관리번호

		logger.info("[deviceId] {}, [imMbrNo] {}, imMngNum {}", deviceId, imMbrNo, imMngNum);

		/* 2. 회원정보 수정 */
		UpdateUserRequest updUserReq = new UpdateUserRequest();
		updUserReq.setCommonRequest(commonRequest);
		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(userKey);
		userMbr.setImMbrNo(imMbrNo);
		userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
		updUserReq.setUserMbr(userMbr);
		UpdateUserResponse updUserRes = this.userSCI.updateUser(updUserReq);
		if (!StringUtil.equals(updUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["
					+ updUserRes.getCommonResponse().getResultCode() + "] "
					+ updUserRes.getCommonResponse().getResultMessage());
		}

		/* 3. 휴대기기 정보 수정 */
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceId(deviceId);
		deviceInfo.setImMngNum(imMngNum);
		this.deviceService.mergeDeviceInfo(deviceInfo);

		logger.info("########## volatileMember process end #########");
	}

}
