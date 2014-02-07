package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 판매자 회원의 가입/수정/탈퇴/인증 기능정의
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);

	/**
	 * SC-SCI.
	 */
	@Autowired
	private SellerSCI sellerSCI;

	/**
	 * 회원 공통 Component.
	 */
	@Autowired
	private MemberCommonComponent component;

	/**
	 * <pre>
	 * 2.2.1. 판매자 회원 가입.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return CreateRes
	 */
	@Override
	public CreateRes createSeller(SacRequestHeader header, CreateReq req) {

		LOGGER.debug("############ SellerServiceImpl.createSeller() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		/** 1. Email 중복체크 [REQUEST] 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** 1-2. SC 헤더 셋팅 */
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
		keySearch.setKeyString(req.getSellerEmail());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);
		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 1-3. SC회원(Email 중복) Call */
		if (StringUtils.equals(MemberConstants.USE_Y,
				this.sellerSCI.checkDuplicationSeller(checkDuplicationSellerRequest).getIsRegistered())) {
			throw new StorePlatformException("SAC_MEM_2012", req.getSellerEmail());
		}

		/** 2. SC회원 Req 생성 및 주입. */
		CreateSellerRequest createSellerRequest = new CreateSellerRequest();

		/** 2-1. 실명인증정보 생성 및 주입 [시작]. */
		MbrAuth mbrAuth = new MbrAuth();
		// 실명인증여부
		mbrAuth.setIsRealName(MemberConstants.USE_Y);
		// CI
		mbrAuth.setCi(req.getSellerCI());
		// DI
		mbrAuth.setDi(req.getSellerDI());
		//
		mbrAuth.setMemberCategory(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY);
		// 인증방법코드
		mbrAuth.setRealNameMethod(req.getRealNameMethod());
		// 통신사 코드
		mbrAuth.setTelecom(req.getSellerTelecom());
		// 무선 전화번호
		mbrAuth.setPhone(req.getSellerPhone());
		// 생년월일
		mbrAuth.setBirthDay(req.getSellerBirthDay());
		// 성별
		mbrAuth.setSex(req.getSellerSex());
		// 회원명
		mbrAuth.setName(req.getSellerName());
		// 실명 인증사이트
		mbrAuth.setRealNameSite(header.getTenantHeader().getSystemId());
		// 실명 인증 일시
		mbrAuth.setRealNameDate(req.getRealNameDate());
		// 내국인 여부
		mbrAuth.setIsDomestic(req.getIsDomestic());
		createSellerRequest.setMbrAuth(mbrAuth);

		LOGGER.debug("==>>[SC] CreateSellerRequest.MbrAuth.toString() : {}", mbrAuth.toString());
		/** 실명인증정보 생성 및 주입 [끝]. */

		/** 2-1. 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;

		if (req.getPwReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPwReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getPwReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionID());
				pwReminder.setQuestionMessage(req.getPwReminderList().get(i).getQuestionMessage());
				pWReminderList.add(pwReminder);
				LOGGER.debug("==>>[SC] CreateSellerRequest.PWReminder[{}].toString() : {}", i, pwReminder.toString());
			}
			createSellerRequest.setPWReminderList(pWReminderList);
			LOGGER.debug("==>>[SC] CreateSellerRequest.pWReminderList.toString() : {}", pWReminderList.toString());
		}
		/** 보안질문 리스트 주입 - [끝]. */

		/** 2-2. 판매자 회원 비밀번호 생성 및 주입. */
		// 판매자 회원 PW
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberPW(req.getSellerPW());
		createSellerRequest.setMbrPwd(mbrPwd);

		LOGGER.debug("==>>[SC] CreateSellerRequest.MbrPwd.toString() : {}", mbrPwd.toString());

		/** 2-3. 판매자 회원 정보 생성 및 주입 - [시작]. */
		SellerMbr sellerMbr = new SellerMbr();
		// 판매자 회원 ID
		sellerMbr.setSellerID(req.getSellerId());
		// 판매자구분코드
		sellerMbr.setSellerClass(req.getSellerClass());
		// 판매자 분류코드
		sellerMbr.setSellerCategory(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY);
		// 판매자 main 상태 코드
		sellerMbr.setSellerMainStatus(MemberConstants.MAIN_STATUS_WATING);
		// 판매자 sub 상태 코드
		sellerMbr.setSellerSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING);
		// 통신사 코드
		sellerMbr.setSellerTelecom(req.getSellerTelecom());
		// 무선 국가번호
		sellerMbr.setSellerPhoneCountry(req.getSellerPhoneCountry());
		// 무선 전화번호
		sellerMbr.setSellerPhone(req.getSellerPhone());
		// SMS 수신여부
		sellerMbr.setIsRecvSMS(req.getIsRecvSMS());
		// 판매자 이메일
		sellerMbr.setSellerEmail(req.getSellerEmail());
		// 이메일 수신여부
		sellerMbr.setIsRecvEmail(req.getIsRecvEmail());
		// 판매자 이름
		sellerMbr.setSellerName(req.getSellerName());
		// 쇼핑 노출명
		sellerMbr.setSellerNickName(req.getSellerNickName());
		// 성별
		sellerMbr.setSellerSex(req.getSellerSex());
		// 생년월일
		sellerMbr.setSellerBirthDay(req.getSellerBirthDay());
		// 주민번호
		sellerMbr.setSellerSSNumber(req.getSellerSSNumber());
		// 우편번호
		sellerMbr.setSellerZip(req.getSellerZip());
		// 주소
		sellerMbr.setSellerAddress(req.getSellerAddress());
		// 상세주소
		sellerMbr.setSellerDetailAddress(req.getSellerDetailAddress());
		// 도시
		sellerMbr.setSellerCity(req.getSellerCity());
		// 지역
		sellerMbr.setSellerState(req.getSellerState());
		// 국내판매자 여부
		sellerMbr.setIsDomestic(req.getIsDomestic());
		// 실명인증여부
		sellerMbr.setIsRealName(MemberConstants.USE_Y);
		// 국가코드
		sellerMbr.setSellerCountry(req.getSellerCountry());
		// 언어코드
		sellerMbr.setSellerLanguage(req.getSellerLanguage());
		// 회사명
		sellerMbr.setSellerCompany(req.getSellerCompany());
		// 사업자 등록번호
		sellerMbr.setSellerBizNumber(req.getSellerBizNumber());
		// 고객 대응 이메일
		sellerMbr.setCustomerEmail(req.getCustomerEmail());
		// 고객 대응 전화번호 국가번호
		sellerMbr.setCustomerPhoneCountry(req.getCustomerPhoneCountry());
		// 고객 대응 전화번호
		sellerMbr.setCustomerPhone(req.getCustomerPhone());
		// 법인등록번호
		sellerMbr.setSellerBizCorpNumber(req.getSellerBizCorpNumber());
		sellerMbr.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);
		sellerMbr.setStopStatusCode(MemberConstants.USER_STOP_STATUS_NOMAL);
		createSellerRequest.setSellerMbr(sellerMbr);
		// Debug
		LOGGER.debug("==>>[SC] CreateSellerRequest.SellerMbr.toString() : {}", sellerMbr.toString());
		/** 판매자 회원 정보 생성 및 주입 - [끝]. */

		/** 2-4. 회원 가입을 위한 공통 헤더 주입. */
		createSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] CreateSellerRequest.toString() : {}", createSellerRequest.toString());

		/** 2-5. SC회원[createSeller] Call. */
		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(createSellerRequest);

		/** 3. 가입 결과 [RESPONSE] 생성 및 주입. */
		CreateRes res = new CreateRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr resMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
		resMbr.setSellerId(createSellerResponse.getSellerID());
		resMbr.setSellerKey(createSellerResponse.getSellerKey());
		resMbr.setSellerMainStatus(createSellerResponse.getSellerMainStatus());
		resMbr.setSellerSubStatus(createSellerResponse.getSellerSubStatus());
		res.setSellerMbr(resMbr);
		// Debug
		LOGGER.debug("==>>[SAC] CreateRes.toString() : \n{}", res.toString());

		LOGGER.debug("############ SellerServiceImpl.createSeller() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.3. 판매자회원 인증.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return AuthorizeRes
	 */
	@Override
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req) {

		LOGGER.debug("############ SellerServiceImpl.authorize() [START] ############");
		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		/** 1. SC회원 Req 생성 및 주입. */
		LoginSellerRequest loginSellerRequest = new LoginSellerRequest();
		loginSellerRequest.setSellerID(req.getSellerId());
		loginSellerRequest.setSellerPW(req.getSellerPW());

		LOGGER.debug("==>>[SC] LoginSellerRequest.toString() : {}", loginSellerRequest.toString());

		// SC공통 헤더 정보 주입
		loginSellerRequest.setCommonRequest(commonRequest);

		/** 1-1. SC-로그인인증 Call. */
		LoginSellerResponse logInSellerResponse = this.sellerSCI.loginSeller(loginSellerRequest);

		// Response Debug
		LOGGER.info("[SellerSCI.loginSeller()] - Response CODE : {}, MESSGE : {}", logInSellerResponse
				.getCommonResponse().getResultCode(), logInSellerResponse.getCommonResponse().getResultMessage());

		/** 2. SAC-[Response] 생성 및 주입. */
		AuthorizeRes res = new AuthorizeRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr sellerMbr = null;

		if (logInSellerResponse != null) {
			sellerMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
			String loginStatusCode = logInSellerResponse.getLoginStatusCode();
			String isLoginSuccess = logInSellerResponse.getIsLoginSuccess();
			// 존재 하지 않는 회원일 경우
			if (StringUtils.equals(logInSellerResponse.getCommonResponse().getResultCode(),
					MemberConstants.RESULT_UNKNOWN_USER_ID)) {
				res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
			} else {
				// 회원 인증 "Y" 일 경우
				if (StringUtils.equals(logInSellerResponse.getIsLoginSuccess(), MemberConstants.USE_Y)) {

					/** 3. 계정 잠금 해제 요청. */
					if (StringUtils.equals(req.getReleaseLock(), MemberConstants.USE_Y)
							&& StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)) {
						/** 3-1. SC회원 Req 생성 및 주입. */
						UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
						updateStatusSellerRequest.setSellerID(req.getSellerId());
						updateStatusSellerRequest.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);

						/** 3-2. 공통 헤더 생성 및 주입. */
						updateStatusSellerRequest.setCommonRequest(commonRequest);

						LOGGER.debug("==>>[SC] UpdateStatusSellerRequest.toString() : {}",
								updateStatusSellerRequest.toString());

						/** 3-3. SC회원 - 상태변경 Call. */
						this.sellerSCI.updateStatusSeller(updateStatusSellerRequest);

						/** 3-4. 회원 정보 조회 */
						SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
								MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, logInSellerResponse.getSellerKey());
						/** 3-5. 로그인 상태 코드 주입. */
						loginStatusCode = searchSellerResponse.getSellerMbr().getLoginStatusCode();
					}

					if (StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_NOMAL)) {
						/** 4. 회원 인증키 생성[SC-REQUEST] 생성 및 주입 */
						UpdateLoginInfoRequest updateLoginInfoRequest = new UpdateLoginInfoRequest();

						LoginInfo loginInfo = new LoginInfo();
						// 만료일시 생성
						String expireDate = this.component.getExpirationTime(Integer.parseInt(req.getExpireDate()));
						loginInfo.setSellerKey(logInSellerResponse.getSellerKey());
						loginInfo.setIpAddress(req.getIpAddress());
						loginInfo.setSessionKey(UUID.randomUUID().toString().replaceAll("-", ""));
						loginInfo.setExpireDate(expireDate);
						updateLoginInfoRequest.setLoginInfo(loginInfo);

						/** 4-1. 공통 헤더 생성 및 주입. */
						updateLoginInfoRequest.setCommonRequest(commonRequest);

						/** 4-2. SC회원 - 상태변경(회원인증키) Call. */
						this.sellerSCI.updateLoginInfo(updateLoginInfoRequest);

						/** 4-3. [RESPONSE] 회원 인증키 주입. */
						res.setSessionKey(loginInfo.getSessionKey());
						res.setExpireDate(expireDate);
						sellerMbr.setSellerKey(logInSellerResponse.getSellerKey());
					} else {
						isLoginSuccess = MemberConstants.USE_N;
					}
				}
				/** 2-1. [RESPONSE] 회원 상태 및 로그인 상태 주입. */
				sellerMbr.setSellerClass(logInSellerResponse.getSellerClass());
				sellerMbr.setSellerMainStatus(logInSellerResponse.getSellerMainStatus());
				sellerMbr.setSellerSubStatus(logInSellerResponse.getSellerSubStatus());
				res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
				res.setIsSubSeller(logInSellerResponse.getIsSubSeller());
				res.setIsLoginSuccess(isLoginSuccess);
				res.setLoginStatusCode(loginStatusCode);
			}
			/** 2-2. [RESPONSE] 회원 정보 주입. */
			res.setSellerMbr(sellerMbr);
			LOGGER.debug("==>>[SAC] SellerMbr.toString() : {}", sellerMbr.toString());
		}

		// Response Debug
		LOGGER.debug("==>>[SAC] AuthorizeRes.toString() : {}", res.toString());
		LOGGER.debug("############ SellerServiceImpl.authorize() [START] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.14. 판매자 회원 계정 승인.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ConfirmRes
	 */
	@Override
	public ConfirmRes confirm(SacRequestHeader header, ConfirmReq req) {
		LOGGER.debug("############ SellerServiceImpl.confirm() [START] ############");
		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		/** 1. 회원 정보 조회 */
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		if (!StringUtils.equals(searchSellerResponse.getSellerMbr().getSellerMainStatus(),
				MemberConstants.MAIN_STATUS_WATING)
				&& !StringUtils.equals(searchSellerResponse.getSellerMbr().getSellerSubStatus(),
						MemberConstants.SUB_STATUS_JOIN_APPLY_WATING)) {

			LOGGER.debug("[SC] 회원메인 상태 : {}", searchSellerResponse.getSellerMbr().getSellerMainStatus());

			throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr().getSellerMainStatus(),
					searchSellerResponse.getSellerMbr().getSellerSubStatus());
		}

		/** 1. SC회원 Req 생성 및 주입. */
		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		updateStatusSellerRequest.setSellerID(searchSellerResponse.getSellerMbr().getSellerID());
		updateStatusSellerRequest.setSellerMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
		updateStatusSellerRequest.setSellerSubStatus(MemberConstants.SUB_STATUS_NORMAL);
		updateStatusSellerRequest.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);

		/** 2. 공통 헤더 생성 및 주입. */
		updateStatusSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] UpdateStatusSellerRequest.toString() : {}", updateStatusSellerRequest.toString());

		/** 3. SC회원 - 상태변경 Call. */
		this.sellerSCI.updateStatusSeller(updateStatusSellerRequest);

		/** 4. TenantRes Response 생성 및 주입 */
		ConfirmRes res = new ConfirmRes();
		res.setSellerKey(req.getSellerKey());
		LOGGER.debug("==>>[SAC] ConfirmRes.toString() : {}", res.toString());
		LOGGER.debug("############ SellerServiceImpl.confirm() [START] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.16 판매자 회원 계정 잠금.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return LockAccountRes
	 */
	@Override
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req) {

		LOGGER.debug("############ SellerServiceImpl.lockAccount() [START] ############");
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		/** 1. 회원 정보 조회 */
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_SELLERMBR_ID, req.getSellerId());

		// 로그인 잠금 상태 확인
		if (StringUtils.equals(MemberConstants.USER_LOGIN_STATUS_PAUSE, searchSellerResponse.getSellerMbr()
				.getLoginStatusCode())) {
			throw new StorePlatformException("SAC_MEM_2011", req.getSellerId());
		}

		/** 2. SC회원 Req 생성 및 주입. */
		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		updateStatusSellerRequest.setSellerID(req.getSellerId());
		updateStatusSellerRequest.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);

		/** 3. 공통 헤더 생성 및 주입. */
		updateStatusSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] UpdateStatusSellerRequest.toString() : {}", updateStatusSellerRequest.toString());

		/** 4. SC회원[로그인 제한] - 상태변경 Call. */
		this.sellerSCI.updateStatusSeller(updateStatusSellerRequest);

		/** 5. TenantRes Response 생성 및 주입 */
		LockAccountRes res = new LockAccountRes(updateStatusSellerRequest.getSellerID());
		LOGGER.debug("==>>[SAC] LockAccountRes.toString() : {}", res.toString());

		LOGGER.debug("############ SellerServiceImpl.lockAccount() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.10. 판매자 기본정보 수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyInformationRes
	 */
	@Override
	public ModifyInformationSacRes modifyInformation(SacRequestHeader header, ModifyInformationSacReq req) {

		LOGGER.debug("############ SellerServiceImpl.modifyInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		/** 2. 기본정보수정 [REQUEST] 생성 및 주입. */
		UpdateSellerRequest updateSellerRequest = new UpdateSellerRequest();

		/** 2-1. 실명인증 정보 생성 및 주입. */
		if (StringUtils.equals(req.getIsRealName(), MemberConstants.USE_Y)) {
			MbrAuth mbrAuth = new MbrAuth();
			// 실명인증여부
			mbrAuth.setIsRealName(req.getIsRealName());
			// CI
			mbrAuth.setCi(req.getSellerCI());
			// DI
			mbrAuth.setDi(req.getSellerDI());
			//
			mbrAuth.setMemberCategory(req.getSellerCategory());
			// 인증방법코드
			mbrAuth.setRealNameMethod(req.getRealNameMethod());
			// 통신사 코드
			mbrAuth.setTelecom(req.getSellerTelecom());
			// 무선 전화번호
			mbrAuth.setPhone(req.getSellerPhone());
			// 생년월일
			mbrAuth.setBirthDay(req.getSellerBirthDay());
			// 성별
			mbrAuth.setSex(req.getSellerSex());
			// 회원명
			mbrAuth.setName(req.getSellerName());
			// 실명 인증사이트
			mbrAuth.setRealNameSite(req.getRealNameSystemId());

			updateSellerRequest.setMbrAuth(mbrAuth);
		}

		/** 2-2. 법정 대리인 정보 생성 및 주입. */
		if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {
			MbrLglAgent mbrLglAgent = new MbrLglAgent();

			mbrLglAgent.setIsParent(req.getIsParent());
			mbrLglAgent.setParentRealNameMethod(req.getParentRealNameMethod());
			mbrLglAgent.setParentName(req.getParentName());
			mbrLglAgent.setParentType(req.getParentType());
			mbrLglAgent.setParentDate(req.getParentDate());
			mbrLglAgent.setParentEmail(req.getParentEmail());
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay());
			mbrLglAgent.setParentTelecom(req.getParentTelecom());
			mbrLglAgent.setParentMDN(req.getParentMDN());
			mbrLglAgent.setParentCI(req.getParentCI());
			mbrLglAgent.setParentRealNameDate(req.getParentRealNameDate());
			mbrLglAgent.setParentRealNameSite(req.getParentRealNameSystemId());
			//
			mbrLglAgent.setMemberKey(req.getSellerKey());

			updateSellerRequest.setMbrLglAgent(mbrLglAgent);
		}

		/** 2-3. 판매자 정보 생성 및 주입. */
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerClass(req.getSellerClass());
		sellerMbr.setSellerCategory(req.getSellerCategory());
		sellerMbr.setSellerMainStatus(req.getSellerMainStatus());
		sellerMbr.setSellerSubStatus(req.getSellerSubStatus());
		sellerMbr.setSellerID(req.getSellerId());
		sellerMbr.setSellerTelecom(req.getSellerTelecom());
		sellerMbr.setSellerPhoneCountry(req.getSellerPhoneCountry());
		sellerMbr.setSellerPhone(req.getSellerPhone());
		sellerMbr.setIsRecvSMS(req.getIsRecvSMS());
		sellerMbr.setSellerName(req.getSellerName());
		sellerMbr.setSellerSex(req.getSellerSex());
		sellerMbr.setSellerBirthDay(req.getSellerBirthDay());
		sellerMbr.setSellerZip(req.getSellerZip());
		sellerMbr.setSellerAddress(req.getSellerAddress());
		sellerMbr.setSellerDetailAddress(req.getSellerDetailAddress());
		sellerMbr.setSellerCity(req.getSellerCity());
		sellerMbr.setSellerState(req.getSellerState());
		sellerMbr.setSellerCountry(req.getSellerCountry());
		sellerMbr.setSellerLanguage(req.getSellerLanguage());
		sellerMbr.setIsDomestic(req.getIsDomestic());
		sellerMbr.setIsParent(req.getIsParent());
		sellerMbr.setSellerCompany(req.getSellerCompany());
		sellerMbr.setSellerBizNumber(req.getSellerBizNumber());
		sellerMbr.setCustomerPhoneCountry(req.getCustomerPhoneCountry());
		sellerMbr.setCustomerPhone(req.getCustomerPhone());
		sellerMbr.setCustomerEmail(req.getCustomerEmail());

		updateSellerRequest.setSellerMbr(sellerMbr);

		/** 2-4. 공통 헤더 생성 및 주입. */
		updateSellerRequest.setCommonRequest(commonRequest);

		/** 2-5. SC회원 - 기본정보변경 Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);

		// Debug
		LOGGER.debug("[SC-UpdateSellerResponse] : \n{}", updateSellerResponse.toString());

		/** 2-6. Tenant [RESPONSE] 생성 및 주입 */
		ModifyInformationSacRes res = new ModifyInformationSacRes();
		res.setSellerKey(updateSellerResponse.getSellerKey());

		LOGGER.debug("############ SellerServiceImpl.modifyInformation() [START] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.11. 판매자회원 정산 정보 수정.
	 * @TODO
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyAccountInformationRes
	 */
	@Override
	public ModifyAccountInformationSacRes modifyAccountInformation(SacRequestHeader header, ModifyAccountInformationSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modifyAccountInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		UpdateAccountSellerRequest updateAccountSellerRequest = new UpdateAccountSellerRequest();

		updateAccountSellerRequest.setSellerKey(req.getSellerKey());

		/** 2. 공통 헤더 생성 및 주입. */
		updateAccountSellerRequest.setCommonRequest(this.component.getSCCommonRequest(header));

		/** 3. SC회원 - 정산정보수정변경 Call. */
		UpdateAccountSellerResponse updateAccountSellerResponse = this.sellerSCI
				.updateAccountSeller(updateAccountSellerRequest);

		/** 4. TenantRes Response 생성 및 주입 */
		ModifyAccountInformationSacRes res = new ModifyAccountInformationSacRes();
		res.setSellerKey(updateAccountSellerResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.modifyAccountInformation() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 판매자회원 탈퇴.
	 * </pre>
	 * 
	 * @param WithdrawReq
	 * @return WithdrawRes
	 */
	@Override
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req) {

		RemoveSellerResponse schRes = new RemoveSellerResponse();
		RemoveSellerRequest schReq = new RemoveSellerRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSecedeReasonCode(req.getSecedeReasonCode());
		schReq.setSecedeReasonMessage(req.getSecedeReasonMessage());

		schRes = this.sellerSCI.removeSeller(schReq);

		WithdrawRes response = new WithdrawRes();
		response.setSellerKey(req.getSellerKey());

		return response;
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 생성/연장.
	 * </pre>
	 * 
	 * @param CreateAuthKeyReq
	 * @return CreateAuthKeyRes
	 */
	@Override
	public CreateAuthKeyRes createAuthKey(SacRequestHeader header, CreateAuthKeyReq req) {

		UpdateLoginInfoResponse schRes = new UpdateLoginInfoResponse();
		UpdateLoginInfoRequest schReq = new UpdateLoginInfoRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(req.getSellerKey());
		loginInfo.setIpAddress(req.getIpAddress());
		// loginInfo.setSessionKey(req.getSellerKey() + "_" + RandomStringUtils.getString(10));
		loginInfo.setExpireDate(req.getExpireDate());

		schReq.setLoginInfo(loginInfo);

		schRes = this.sellerSCI.updateLoginInfo(schReq);
		CreateAuthKeyRes response = new CreateAuthKeyRes();

		response.setSessionKey(loginInfo.getSessionKey());

		return response;
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @param AbrogationAuthKeyReq
	 * @return AbrogationAuthKeyRes
	 */
	@Override
	public AbrogationAuthKeyRes abrogationAuthKey(SacRequestHeader header, AbrogationAuthKeyReq req) {

		RemoveLoginInfoResponse schRes = new RemoveLoginInfoResponse();
		RemoveLoginInfoRequest schReq = new RemoveLoginInfoRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(req.getSellerKey());
		schReq.setLoginInfo(loginInfo);

		schRes = this.sellerSCI.removeLoginInfo(schReq);

		AbrogationAuthKeyRes response = new AbrogationAuthKeyRes();

		response.setSellerKey(loginInfo.getSellerKey());

		return response;
	}

}
