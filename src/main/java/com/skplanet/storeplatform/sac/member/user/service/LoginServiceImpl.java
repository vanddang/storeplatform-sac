package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IDPSCI;
import com.skplanet.storeplatform.external.client.idp.vo.IDPTxReq;
import com.skplanet.storeplatform.external.client.idp.vo.IDPTxRes;
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.LogInUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.LogInUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.member.user.controller.UserJoinController;

/**
 * 회원 로그인 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserJoinController.class);

	@Autowired
	private UAPSSCI uapsSCI; // UAPS 연동 인터페이스

	@Autowired
	private IDPSCI idpSCI; // IDP 연동 인터페이스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private DeviceService deviceService; // 휴대기기 관련 서비스

	@Override
	public AuthorizeByMdnRes authorizeByMdn(HeaderVo headerVo,
			AuthorizeByMdnReq req) throws Exception {

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();

		String deviceId = req.getDeviceId();
		String userStateCd = ""; // 사용자구분코드(US011501:기기사용자,US011502:IDP사용자,US011503:OneID사용자)
		String mainStatusCd = ""; // 메인상태코드(US010201:정상,US010202:탈퇴,US010203:가가입,US010204:일시정지,US010205:전환)

		/* 모번호 조회 */
		OpmdRes ompdRes = this.uapsSCI.getOpmdInfo(deviceId);
		if (ompdRes.getResultCode() == 0) {
			deviceId = ompdRes.getMobileMdn();
		} else {
			throw new Exception("UAPS연동시 오류가 발생하였습니다.");
		}

		/* 회원정보 조회 (devicdId) */
		SearchUserRequest schUserReq = new SearchUserRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType("DEVICE_ID");
		key.setKeyString(deviceId);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);

		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		/* 회원 상태 확인 */
		if (schUserRes.getUserMbr() == null) {
			throw new Exception("무선가입상태가 아닙니다.");
		}

		userStateCd = schUserRes.getUserMbr().getUserState();
		mainStatusCd = schUserRes.getUserMbr().getUserMainStatus();

		if (mainStatusCd.equals("US010202")) {// 탈퇴
			throw new Exception("무선가입상태가 아닙니다.");
		}

		/* 모바일회원인경우 변동성 체크, SC콤포넌트 변동성 회원 여부 필드 확인필요!! */
		if (userStateCd.equals("US011501")) {

			/* 변동성 회원 처리 */

		}

		/* 무선회원 인증 */
		if (userStateCd.equals("US011501") || userStateCd.equals("US011502")) {
			// authForWap
			IDPTxReq idpTxReq = new IDPTxReq();
			idpTxReq.setHttpMethod("POST");
			idpTxReq.setHttpProtocal("HTTPS");
			idpTxReq.setReqUrl("");
			idpTxReq.setReqParamHtable(null);

			IDPTxRes idpTxRes = this.idpSCI.send(idpTxReq);
			if (idpTxRes.getResultCode() != (00000)) {
				throw new Exception(idpTxRes.getResultCode() + "");
			}
		} else {

		}

		/* 단말정보 조회 및 merge */
		DeviceInfo deviceInfo = new DeviceInfo();
		this.deviceService.mergeDeviceInfo(deviceInfo);

		/* 로그인 */
		LogInUserRequest loginReq = new LogInUserRequest();
		loginReq.setUserID(deviceId);
		LogInUserResponse loginRes = this.userSCI.logInUser(loginReq);

		if (loginRes.getIsLoginSuccess().equals("Y")) {
			/* 로그인 Response 셋팅 */
			String userStateVal = "";
			if (userStateCd.equals("US011501")) {
				userStateVal = "mobile";
			} else if (userStateCd.equals("US011502")) {
				userStateVal = "tstoreId";
			} else if (userStateCd.equals("US011503")) {
				userStateVal = "oneId";
			}

			if (mainStatusCd.equals("US010203")) {
				userStateVal = "temporary";
			}

			res.setUserKey(schUserRes.getUserMbr().getUserKey());
			// res.setUserAuthKey(userAuthKey);
			res.setUserStatus(userStateVal);
		} else { // 로그인 실패

		}

		return res;
	}

	@Override
	public AuthorizeByIdRes authorizeById(HeaderVo headerVo,
			AuthorizeByIdReq req) throws Exception {

		AuthorizeByIdRes res = new AuthorizeByIdRes();

		String userId = req.getUserId();
		String userPw = req.getUserPw();
		String userStateCd = ""; // 사용자구분코드(US011501:기기사용자,US011502:IDP사용자,US011503:OneID사용자)
		String mainStatusCd = ""; // 메인상태코드(US010201:정상,US010202:탈퇴,US010203:가가입,US010204:일시정지,US010205:전환)

		/* 회원정보 조회 (userId) */
		SearchUserRequest schUserReq = new SearchUserRequest();
		// schUserReq.setKeyType("MBR_ID");
		// schUserReq.setKeyString(userId);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		/* 회원 상태 확인 */
		if (schUserRes.getUserMbr() == null) {
			throw new Exception("존재하지 않는 아이디입니다");
		}

		userStateCd = schUserRes.getUserMbr().getUserState();
		mainStatusCd = schUserRes.getUserMbr().getUserMainStatus();

		if (mainStatusCd.equals("US010204")) {// 일시정지
			throw new Exception(
					"로그인 5회 입력 오류로 계정이 잠금 상태가 되었습니다. T store Web에서 해제 후 로그인해 주세요.");
		}

		/* 회원 인증 요청 */
		String userStateVal = "";
		if (userStateCd.equals("US011502")) { // 기존 IDP회원
			userStateVal = "tstoreId";
		} else if (userStateCd.equals("US011503")) {// 통합회원
			userStateVal = "oneId";
		} else {
			throw new Exception("SC콤포넌트 사용자구분 코드 확인");
		}

		/* 단말기 정보 존재하는 경우 - 단말정보 조회 및 merge */
		DeviceInfo deviceInfo = new DeviceInfo();
		this.deviceService.mergeDeviceInfo(deviceInfo);

		/* 로그인 */
		LogInUserRequest loginReq = new LogInUserRequest();
		loginReq.setUserID(userId);
		loginReq.setUserPW(userPw);
		if (userStateCd.equals("US011503"))
			loginReq.setIsOneID("Y");

		LogInUserResponse loginRes = this.userSCI.logInUser(loginReq);

		if (loginRes.getIsLoginSuccess().equals("Y")) {
			/* 로그인 Response 셋팅 */
			res.setUserKey(schUserRes.getUserMbr().getUserKey());
			res.setUserStatus(userStateVal);
		} else { // 로그인 실패

		}

		return res;
	}

}
