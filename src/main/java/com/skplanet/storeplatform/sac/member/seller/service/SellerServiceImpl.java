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
import com.skplanet.storeplatform.member.client.seller.sci.vo.Document;
import com.skplanet.storeplatform.member.client.seller.sci.vo.FlurryAuth;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveFlurryRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveFlurryResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerAccount;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerUpgrade;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateFlurryRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacRes;
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
	 * SC-SellerSCI.
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
		mbrAuth.setRealNameSite(commonRequest.getSystemID());
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
		// 담당자 이메일
		sellerMbr.setCustomerEmail(req.getCustomerEmail());
		// 담당자 유선 전화 국가번호
		sellerMbr.setCustomerPhoneCountry(req.getCustomerPhoneCountry());
		// 담당자 유선 전화번호
		sellerMbr.setCustomerPhone(req.getCustomerPhone());
		// 법인등록번호
		sellerMbr.setSellerBizCorpNumber(req.getSellerBizCorpNumber());
		sellerMbr.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);
		sellerMbr.setStopStatusCode(MemberConstants.USER_STOP_STATUS_NOMAL);

		// 담당자 명
		sellerMbr.setCharger(req.getCharger());
		// 담당자 무선 전화 번호
		sellerMbr.setChargerPhone(req.getChargerPhone());

		// sellerMbr.setCustomer
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

		/** 2. SAC-[Response] 생성 및 주입. */
		AuthorizeRes res = new AuthorizeRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr sellerMbr = null;

		if (logInSellerResponse != null) {
			sellerMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
			String loginStatusCode = logInSellerResponse.getLoginStatusCode();
			String isLoginSuccess = logInSellerResponse.getIsLoginSuccess();
			String loginFailCount = String.valueOf(logInSellerResponse.getLoginFailCount());
			// 존재 하지 않는 회원일 경우
			if (StringUtils.equals(logInSellerResponse.getCommonResponse().getResultCode(),
					MemberConstants.RESULT_UNKNOWN_USER_ID)) {
				res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
			} else {
				// 회원 인증 "Y" 일 경우
				if (StringUtils.equals(logInSellerResponse.getIsLoginSuccess(), MemberConstants.USE_Y)) {

					/** 3. 계정 잠금 해제 요청. */
					if (StringUtils.equals(req.getReleaseLock(), MemberConstants.USE_Y)
							&& StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_PAUSE)
							&& StringUtils.equals(MemberConstants.USE_N, logInSellerResponse.getIsSubSeller())) {
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
						// FailCount '0' Reset
						loginFailCount = "0";
					}

					// logInSellerResponse = this.sellerSCI.loginSeller(loginSellerRequest);

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
						// 서브 계정 Key
						if (StringUtils.equals(MemberConstants.USE_Y, logInSellerResponse.getIsSubSeller())) {
							res.setSubSellerKey(logInSellerResponse.getSubSellerKey());
						}
					} else {
						isLoginSuccess = MemberConstants.USE_N;
					}
				}
				/** 2-1. [RESPONSE] 회원 상태 및 로그인 상태 주입. */
				sellerMbr.setSellerClass(logInSellerResponse.getSellerClass());
				sellerMbr.setSellerMainStatus(logInSellerResponse.getSellerMainStatus());
				sellerMbr.setSellerSubStatus(logInSellerResponse.getSellerSubStatus());
				res.setLoginFailCount(loginFailCount);
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
			// mbrAuth.setRealNameSite(req.getRealNameSystemId());
			mbrAuth.setRealNameSite(commonRequest.getSystemID());

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
			mbrLglAgent.setParentRealNameSite(commonRequest.getSystemID());
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
		sellerMbr.setCharger(req.getCharger());
		sellerMbr.setChargerPhone(req.getChargerphone());

		updateSellerRequest.setSellerMbr(sellerMbr);

		/** 2-4. 공통 헤더 생성 및 주입. */
		updateSellerRequest.setCommonRequest(commonRequest);

		/** 2-5. SC회원 - 기본정보변경 Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);

		/** Flurry 정보 수정. */
		if (req.getFlurryAuthList() != null) {
			UpdateFlurryRequest updateFlurryRequest = new UpdateFlurryRequest();
			updateFlurryRequest.setCommonRequest(commonRequest);
			updateFlurryRequest.setSellerKey(req.getSellerKey());
			List<FlurryAuth> flurryAuthList = new ArrayList<FlurryAuth>();
			FlurryAuth flurryAuth = null;
			for (int i = 0; i < req.getFlurryAuthList().size(); i++) {
				flurryAuth = new FlurryAuth();
				flurryAuth.setAccessCode(req.getFlurryAuthList().get(i).getAccessCode());
				flurryAuth.setAuthToken(req.getFlurryAuthList().get(i).getAuthToken());
				flurryAuth.setSellerKey(req.getSellerKey());
				flurryAuthList.add(flurryAuth);
			}
			updateFlurryRequest.setFlurryAuthList(flurryAuthList);
			this.sellerSCI.updateFlurry(updateFlurryRequest);
		}

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
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyAccountInformationRes
	 */
	@Override
	public ModifyAccountInformationSacRes modifyAccountInformation(SacRequestHeader header,
			ModifyAccountInformationSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modifyAccountInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// 수정 가능 회원 Check
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		// 메인, 서브 상태
		if (!StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL, req.getSellerMainStatus())
				|| !StringUtils.equals(MemberConstants.SUB_STATUS_NORMAL, req.getSellerSubStatus())) {
			throw new StorePlatformException("SAC_MEM_2001", req.getSellerMainStatus(), req.getSellerSubStatus());
		}

		// 무료, BP
		if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY, searchSellerResponse.getSellerMbr()
				.getSellerCategory())
				|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_BP, searchSellerResponse
						.getSellerMbr().getSellerCategory())) {
			throw new StorePlatformException("SAC_MEM_2004", searchSellerResponse.getSellerMbr().getSellerCategory(),
					searchSellerResponse.getSellerMbr().getSellerClass());
		}

		UpdateAccountSellerRequest updateAccountSellerRequest = new UpdateAccountSellerRequest();

		updateAccountSellerRequest.setSellerKey(req.getSellerKey());

		SellerMbr sellerMbr = new SellerMbr();
		// 회원 정보
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerBizType(req.getSellerBizType()); // INDT_NM 업종명 종목 종목
		sellerMbr.setSellerBizCategory(req.getSellerBizCategory()); // COND_NM 업태명 업태 업태
		sellerMbr.setSellerBizCorpNumber(req.getSellerBizCorpNumber()); // ("법인등록번호"); CORP_REG_NO
		sellerMbr.setRepPhoneArea(req.getRepFaxArea()); // ("대표전화번호 국가코드"); REP_TEL_NATION_NO
		sellerMbr.setRepPhone(req.getRepPhone()); // ("대표전화번호"); REP_TEL_NO
		sellerMbr.setRepFaxArea(req.getRepFaxArea()); // ("대표팩스번호 국가코드"); FAX_NATION_NO
		sellerMbr.setRepFax(req.getRepFax()); // ("대표팩스번호"); FAX_NO
		sellerMbr.setRepEmail(req.getRepEmail()); // ("대표 이메일"); REP_EMAIL
		sellerMbr.setSellerBizAddress(req.getSellerBizAddress());
		sellerMbr.setSellerBizZip(req.getSellerBizZip());
		sellerMbr.setSellerBizDetailAddress(req.getSellerBizDetailAddress());
		sellerMbr.setVendorCode(req.getVendorCode()); // ("벤더 코드"); VENDOR_CD
		sellerMbr.setIsBizRegistered(req.getIsBizRegistered()); // ("통신판매업 신고여부"); MSALBIZ_DECL_YN
		sellerMbr.setBizRegNumber(req.getBizRegNumber()); // ("통신판매업 신고번호"); MSALBIZ_DECL_NO
		sellerMbr.setBizUnregReason(req.getBizUnregReason()); // ("통신판매업 미신고사유  코드"); MSALBIZ_UNDECL_REASON_CD
		sellerMbr.setIsBizTaxable(req.getIsBizTaxable()); // ("간이과세여부"); // EASY_TXN_YN 간이 과세 여부 ##### 전환 쪽에서 사용
		sellerMbr.setBizGrade(req.getBizGrade()); // ("심의등급코드"); DELIB_GRD_CD 심의 등급코드
		sellerMbr.setIsDeductible(req.getIsDeductible()); // ("자동차감가능대상여부"); AUTO_DED_POSB_TARGET_YN
		sellerMbr.setMarketCode(req.getMarketCode()); // ("입점상점코드"); LNCHG_MALL_CD 입점 상점코드
		sellerMbr.setMarketStatus(req.getMarketStatus()); // ("입점상태코드"); LNCHG_MBR_STATUS_CD
		sellerMbr.setIsAccountReal(req.getIsAccountReal()); // ("   계좌인증여부"); // ACCT_AUTH_YN 계좌 인증여부 컬럼

		updateAccountSellerRequest.setSellerMbr(sellerMbr);

		// 정산정보
		SellerAccount sellerAccount = new SellerAccount();
		sellerAccount.setSellerKey(req.getSellerKey());
		sellerAccount.setBankAccount(req.getBankAccount()); // ACCT_NO 계좌번호
		sellerAccount.setBankCode(req.getBankCode()); // BANK_CD 은행코드
		sellerAccount.setBankAcctName(req.getBankAcctName()); // DEPSTR_NM 예금자명
		sellerAccount.setBankName(req.getBankName()); // FR_BANK_NM 은행명
		sellerAccount.setBankBranchCode(req.getBankBranchCode()); // FR_BANK_NM 은행명
		sellerAccount.setBankBranch(req.getBankBranch()); // FR_BRCH_NM 은행지점명
		sellerAccount.setSwiftCode(req.getSwiftCode()); // INTL_SWIFT_CD Swift 코드
		sellerAccount.setAbaCode(req.getAbaCode()); // ABA 코드 INTL_ABA 국제 aba
		sellerAccount.setIbanCode(req.getIbanCode()); // IBAN 코드 INTL_IBAN 국제 iban
		sellerAccount.setBankAddress(req.getBankAddress()); // FR_BANK_ADDR 외국은행주소
		sellerAccount.setBankLocation(req.getBankLocation()); // FR_BANK_LOC 외국은행 위치
		sellerAccount.setTpinCode(req.getTpinCode()); // FR_TIN_NO 외국 tpin 번호
		sellerAccount.setAccountRealDate(req.getAccountRealDate()); // ACCT_AUTH_DT 계좌인증일시

		updateAccountSellerRequest.setSellerAccount(sellerAccount);

		List<Document> documentList = null;
		if (req.getExtraDocumentList() != null) {
			documentList = new ArrayList<Document>();
			for (int i = 0; i < req.getExtraDocumentList().size(); i++) {
				Document document = new Document();
				document.setDocumentCode(req.getExtraDocumentList().get(i).getDocumentCode());
				document.setDocumentName(req.getExtraDocumentList().get(i).getDocumentName());
				document.setDocumentPath(req.getExtraDocumentList().get(i).getDocumentPath());
				document.setDocumentSize(req.getExtraDocumentList().get(i).getDocumentSize());
				document.setIsUsed(req.getExtraDocumentList().get(i).getIsUsed());
				documentList.add(document);
			}
			updateAccountSellerRequest.setDocumentList(documentList);
		}

		/** 2. 공통 헤더 생성 및 주입. */
		updateAccountSellerRequest.setCommonRequest(commonRequest);

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
	 * 2.2.13. 판매자회원 이메일 수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyEmailSacRes
	 */
	@Override
	public ModifyEmailSacRes modifyEmail(SacRequestHeader header, ModifyEmailSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modifyEmail() [START] ############");
		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		/** 1. Email 중복체크 [REQUEST] 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** 1-2. SC 헤더 셋팅 */
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
		keySearch.setKeyString(req.getNewEmailAddress());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);
		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 1-3. SC회원(Email 중복) Call */
		if (StringUtils.equals(MemberConstants.USE_Y,
				this.sellerSCI.checkDuplicationSeller(checkDuplicationSellerRequest).getIsRegistered())) {
			throw new StorePlatformException("SAC_MEM_2012", req.getNewEmailAddress());
		}

		UpdateSellerRequest updateSellerRequest = new UpdateSellerRequest();

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerEmail(req.getNewEmailAddress());
		updateSellerRequest.setSellerMbr(sellerMbr);
		updateSellerRequest.setCommonRequest(commonRequest);
		/** 2-5. SC회원 - 기본정보변경 Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);
		ModifyEmailSacRes res = new ModifyEmailSacRes();
		res.setSellerKey(updateSellerResponse.getSellerKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.14. 판매자회원 Password 수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyPasswordSacRes
	 */
	@Override
	public ModifyPasswordSacRes modifyPassword(SacRequestHeader header, ModifyPasswordSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modifyPassword() [START] ############");
		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// SellerId 조회
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		UpdatePasswordSellerRequest updatePasswordSellerRequest = new UpdatePasswordSellerRequest();

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID(searchSellerResponse.getSellerMbr().getSellerID());
		mbrPwd.setOldPW(req.getOldPW());
		mbrPwd.setMemberPW(req.getNewPW());
		updatePasswordSellerRequest.setMbrPwd(mbrPwd);

		updatePasswordSellerRequest.setCommonRequest(commonRequest);

		UpdatePasswordSellerResponse updatePasswordSellerResponse = this.sellerSCI
				.updatePasswordSeller(updatePasswordSellerRequest);

		ModifyPasswordSacRes res = new ModifyPasswordSacRes();

		res.setSellerKey(updatePasswordSellerResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.modifyPassword() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.15. 판매자 회원 계정 승인.
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

			LOGGER.debug("[SC] 회원메인 상태 : {}, 서브 상태 {}", searchSellerResponse.getSellerMbr().getSellerMainStatus(),
					searchSellerResponse.getSellerMbr().getSellerSubStatus());

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
	 * 2.2.16. 판매자 회원 전환 신청.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ConversionClassResSacRes
	 */
	@Override
	public ConversionClassSacRes conversionClass(SacRequestHeader header, ConversionClassSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.conversionClassRes() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// 수정 가능 회원 Check
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		// 법인 / BP
		if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS, searchSellerResponse
				.getSellerMbr().getSellerClass())
				|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_BP, searchSellerResponse
						.getSellerMbr().getSellerCategory())) {
			throw new StorePlatformException("SAC_MEM_2004", searchSellerResponse.getSellerMbr().getSellerCategory(),
					searchSellerResponse.getSellerMbr().getSellerClass());
		}

		UpgradeSellerRequest upgradeSellerRequest = new UpgradeSellerRequest();

		// 판매자 정보
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerID(req.getSellerId());
		sellerMbr.setSellerMainStatus(req.getSellerMainStatus()); // 정상 회원
		sellerMbr.setSellerClass(req.getSellerClass());
		sellerMbr.setSellerCategory(req.getSellerCategory()); //
		sellerMbr.setSellerSubStatus(req.getSellerSubStatus()); // US000804 전환신청
		upgradeSellerRequest.setSellerMbr(sellerMbr);

		// 전환 정보
		SellerUpgrade sellerUpgrade = new SellerUpgrade();

		sellerUpgrade.setSellerKey(req.getSellerKey());
		sellerUpgrade.setSellerClassTo(req.getSellerCategoryTo());
		sellerUpgrade.setRepEmail(req.getRepEmail());
		sellerUpgrade.setSellerBizCorpNumber(req.getSellerBizCorpNumber());
		sellerUpgrade.setSellerBizType(req.getSellerBizType());
		sellerUpgrade.setSellerBizCategory(req.getSellerBizCategory());
		sellerUpgrade.setBankAccount(req.getBankAccount());
		sellerUpgrade.setBankCode(req.getBankCode());
		sellerUpgrade.setBankAcctName(req.getBankAcctName());
		sellerUpgrade.setIsAccountReal(req.getIsAccountReal());
		sellerUpgrade.setIsBizTaxable(req.getIsBizTaxable());
		sellerUpgrade.setRepPhone(req.getRepPhone());
		sellerUpgrade.setRepFax(req.getRepFax());
		sellerUpgrade.setIsBizRegistered(req.getIsBizRegistered());
		sellerUpgrade.setBizRegNumber(req.getBizRegNumber());
		sellerUpgrade.setBizUnregReason(req.getBizUnregReason());
		sellerUpgrade.setBankName(req.getBankName());
		sellerUpgrade.setBankBranchCode(req.getBankBranchCode());
		sellerUpgrade.setBankBranch(req.getBankBranch());
		sellerUpgrade.setSwiftCode(req.getSwiftCode());
		sellerUpgrade.setAbaCode(req.getAbaCode());
		sellerUpgrade.setIbanCode(req.getIbanCode());
		sellerUpgrade.setBankAddress(req.getBankAddress());
		sellerUpgrade.setBankLocation(req.getBankLocation());
		sellerUpgrade.setTpinCode(req.getTpinCode());
		sellerUpgrade.setVendorCode(req.getVendorCode());
		sellerUpgrade.setRepPhoneArea(req.getRepPhoneArea());
		sellerUpgrade.setRepFaxArea(req.getRepFaxArea());
		sellerUpgrade.setBizGrade(req.getBizGrade());
		sellerUpgrade.setIsDeductible(req.getIsDeductible());
		sellerUpgrade.setMarketCode(req.getMarketCode());
		sellerUpgrade.setMarketStatus(req.getMarketStatus());
		sellerUpgrade.setAccountRealDate(req.getAccountRealDate());
		sellerUpgrade.setSellerBizZip(req.getSellerBizZip());
		sellerUpgrade.setSellerBizAddress(req.getSellerBizAddress());
		sellerUpgrade.setSellerBizDetailAddress(req.getSellerBizDetailAddress());
		sellerUpgrade.setCeoBirthDay(req.getCeoBirthDay());
		sellerUpgrade.setSellerLanguage(req.getSellerLanguage());
		sellerUpgrade.setSellerTelecom(req.getSellerTelecom());
		sellerUpgrade.setCeoName(req.getCeoName());
		sellerUpgrade.setSellerBizCorpNumber(req.getSellerBizCorpNumber());
		sellerUpgrade.setSellerClassTo(req.getSellerClassTo());
		sellerUpgrade.setSellerCompany(req.getSellerCompany());
		sellerUpgrade.setSellerBizNumber(req.getSellerBizNumber());
		sellerUpgrade.setIsOfficialAuth(req.getIsOfficialAuth());
		sellerUpgrade.setCordedTelephone(req.getCordedTelephone());
		sellerUpgrade.setChargerPhone(req.getChargerPhone());
		sellerUpgrade.setIsRecvSMS(req.getIsRecvSMS());
		sellerUpgrade.setCeoName(req.getCeoName());
		sellerUpgrade.setCharger(req.getCharger());
		sellerUpgrade.setSellerBizType(req.getSellerBizType());

		upgradeSellerRequest.setSellerUpgrade(sellerUpgrade);

		// 서류
		List<Document> documentList = null;
		if (req.getExtraDocumentList() != null) {
			documentList = new ArrayList<Document>();
			for (int i = 0; i < req.getExtraDocumentList().size(); i++) {
				Document document = new Document();
				document.setDocumentCode(req.getExtraDocumentList().get(i).getDocumentCode());
				document.setDocumentName(req.getExtraDocumentList().get(i).getDocumentName());
				document.setDocumentPath(req.getExtraDocumentList().get(i).getDocumentPath());
				document.setDocumentSize(req.getExtraDocumentList().get(i).getDocumentSize());
				document.setIsUsed(MemberConstants.USE_Y);

				documentList.add(document);
			}
			upgradeSellerRequest.setDocumentList(documentList);
		}

		upgradeSellerRequest.setSellerKey(req.getSellerKey());
		upgradeSellerRequest.setCommonRequest(commonRequest);

		/** 3. SC회원 - 전환신청 Call. */
		UpgradeSellerResponse updateAccountSellerResponse = this.sellerSCI.upgradeSeller(upgradeSellerRequest);

		/** 4. TenantRes Response 생성 및 주입 */
		ConversionClassSacRes res = new ConversionClassSacRes();
		res.setSellerKey(updateAccountSellerResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.conversionClassRes() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.17. 판매자 회원 계정 잠금.
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
	 * 2.2.18. 판매자회원 실명 인증 정보 수정
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return ModifyRealNameSacRes
	 */
	@Override
	public ModifyRealNameSacRes modifyRealName(SacRequestHeader header, ModifyRealNameSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modifyRealName() [START] ############");
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		UpdateRealNameSellerRequest updateRealNameSellerRequest = new UpdateRealNameSellerRequest();
		updateRealNameSellerRequest.setIsOwn(req.getIsOwn());
		updateRealNameSellerRequest.setIsRealName(req.getIsRealName());
		updateRealNameSellerRequest.setSellerKey(req.getSellerKey());

		if (StringUtils.equals(MemberConstants.AUTH_TYPE_OWN, req.getIsOwn())) {
			// 실명인증 정보
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setBirthDay(req.getSellerBirthDay());
			mbrAuth.setCi(req.getSellerCI());
			mbrAuth.setDi(req.getSellerDI());
			mbrAuth.setIsDomestic(req.getIsDomestic());
			mbrAuth.setIsRealName(req.getIsRealName());
			mbrAuth.setName(req.getSellerName());
			mbrAuth.setPhone(req.getSellerPhone());
			mbrAuth.setRealNameDate(req.getRealNameDate());
			mbrAuth.setRealNameMethod(req.getRealNameMethod());
			mbrAuth.setRealNameSite(commonRequest.getSystemID());
			mbrAuth.setSex(req.getSex());
			mbrAuth.setTelecom(req.getSellerTelecom());
			updateRealNameSellerRequest.setMbrAuth(mbrAuth);

		} else if (StringUtils.equals(MemberConstants.AUTH_TYPE_PARENT, req.getIsOwn())) {
			// 법정 대리인 정보
			MbrLglAgent mbrLglAgent = new MbrLglAgent();
			mbrLglAgent.setParentBirthDay(req.getParentBirthDay());
			mbrLglAgent.setParentDate(req.getParentDate());
			mbrLglAgent.setParentEmail(req.getParentEmail());
			mbrLglAgent.setParentType(req.getParentType());
			mbrLglAgent.setParentCI(req.getSellerCI());
			mbrLglAgent.setParentMDN(req.getSellerPhone());
			mbrLglAgent.setParentName(req.getSellerName());
			mbrLglAgent.setParentRealNameDate(req.getRealNameDate());
			mbrLglAgent.setParentRealNameMethod(req.getRealNameMethod());
			mbrLglAgent.setParentRealNameSite(commonRequest.getSystemID());
			mbrLglAgent.setParentTelecom(req.getSellerTelecom());
			updateRealNameSellerRequest.setMbrLglAgent(mbrLglAgent);
		}

		updateRealNameSellerRequest.setCommonRequest(commonRequest);

		UpdateRealNameSellerResponse updateRealNameSellerResponse = this.sellerSCI
				.updateRealNameSeller(updateRealNameSellerRequest);

		ModifyRealNameSacRes res = new ModifyRealNameSacRes();
		res.setSellerKey(updateRealNameSellerResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.modifyRealName() [END] ############");
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

		RemoveSellerRequest schReq = new RemoveSellerRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSecedeReasonCode(req.getSecedeReasonCode());
		schReq.setSecedeReasonMessage(req.getSecedeReasonMessage());

		this.sellerSCI.removeSeller(schReq);

		WithdrawRes response = new WithdrawRes();
		response.setSellerKey(req.getSellerKey());

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

		RemoveLoginInfoRequest schReq = new RemoveLoginInfoRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(req.getSellerKey());
		schReq.setLoginInfo(loginInfo);

		this.sellerSCI.removeLoginInfo(schReq);

		AbrogationAuthKeyRes response = new AbrogationAuthKeyRes();

		response.setSellerKey(loginInfo.getSellerKey());

		return response;
	}

	/**
	 * <pre>
	 * 2.2.4. 판매자회원 단순 인증.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return AuthorizeRes
	 */
	@Override
	public AuthorizeRes authorizeSimple(SacRequestHeader header, AuthorizeSimpleReq req) {

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
		LoginSellerResponse schRes = this.sellerSCI.loginSeller(loginSellerRequest);

		AuthorizeRes response = new AuthorizeRes();

		if (schRes.getIsLoginSuccess() == null)
			response.setIsLoginSuccess("N");
		else
			response.setIsLoginSuccess(schRes.getIsLoginSuccess());

		return response;
	}

	/**
	 * <pre>
	 * 2.2.30. Flurry 삭제.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return RemoveFlurrySacRes
	 */
	@Override
	public RemoveFlurrySacRes removeFlurry(SacRequestHeader header, RemoveFlurrySacReq req) {

		// 1. CommonRequest Setting
		LOGGER.debug("############ SellerServiceImpl.modifyInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// 2. SessionKey Check
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// 3. Flurry 삭제
		RemoveFlurryRequest removeFlurryRequest = new RemoveFlurryRequest();
		removeFlurryRequest.setCommonRequest(commonRequest);

		removeFlurryRequest.setSellerKey(req.getSellerKey());

		if (req.getFlurryAuthList() != null) {
			List<FlurryAuth> flurrtAuthList = new ArrayList<FlurryAuth>();
			FlurryAuth flurryAuth = null;
			for (int i = 0; i < req.getFlurryAuthList().size(); i++) {
				flurryAuth = new FlurryAuth();
				flurryAuth.setAccessCode(req.getFlurryAuthList().get(i).getAccessCode());
				flurryAuth.setSellerKey(req.getSellerKey());
				flurrtAuthList.add(flurryAuth);
			}
			removeFlurryRequest.setFlurryAuthList(flurrtAuthList);
		}

		RemoveFlurryResponse removeFlurryResponse = this.sellerSCI.removeFlurry(removeFlurryRequest);

		// 4. Response
		RemoveFlurrySacRes res = new RemoveFlurrySacRes();
		res.setSellerKey(removeFlurryResponse.getSellerKey());
		return res;
	}
}
