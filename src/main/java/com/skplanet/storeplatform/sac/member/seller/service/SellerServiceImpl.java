package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.icms.Message;
import com.skplanet.icms.legacy.user.CancelAccountRequest;
import com.skplanet.icms.operation.Command;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
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
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateFlurryRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateFlurryResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerResponse;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAgreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeSimpleRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveAuthorizationKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveAuthorizationKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;

/**
 * 판매자 회원의 가입/수정/탈퇴/인증 기능정의
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);

	/** Default Session Key. */
	@Value("#{propertiesForSac['member.seller.default.session.key']}")
	private String defaultSessionKey;

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
	 * Queue Name 설정.
	 */
	@Resource(name = "sellerWithdrawAmqpTemplate")
	private AmqpTemplate sellerWithdrawAmqpTemplate;

	/**
	 * SPSAC DB 관련 Respository.
	 */
	@Autowired
	private MemberCommonRepository repository;

	/**
	 * <pre>
	 * 2.2.1. 판매자 회원 가입.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateReq
	 * @return CreateRes
	 */
	@Override
	public CreateRes regSeller(SacRequestHeader header, CreateReq req) {

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
		// TenantId 추가
		mbrAuth.setTenantID(header.getTenantHeader().getTenantId());

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
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionId());
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
		mbrPwd.setMemberPW(req.getSellerPw());
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
		// 유선 전화번호
		sellerMbr.setCordedTelephoneCountry(req.getCordedTelephoneCountry());
		// 유선 전화번호
		sellerMbr.setCordedTelephone(req.getCordedTelephone());
		// SMS 수신여부
		sellerMbr.setIsRecvSMS(req.getIsRecvSms());
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
		sellerMbr.setRepEmail(req.getRepEmail());
		sellerMbr.setRepPhone(req.getRepPhone());
		sellerMbr.setRepPhoneArea(req.getRepPhoneArea());
		// 법인등록번호
		sellerMbr.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);
		sellerMbr.setStopStatusCode(MemberConstants.USER_STOP_STATUS_NOMAL);
		// 담당자 명
		sellerMbr.setCharger(req.getCharger());
		sellerMbr.setWebsite(req.getWebsite());

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
		SellerMbrSac resMbr = new SellerMbrSac();
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
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeReq
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
		loginSellerRequest.setSellerPW(req.getSellerPw());

		LOGGER.debug("==>>[SC] LoginSellerRequest.toString() : {}", loginSellerRequest.toString());

		// SC공통 헤더 정보 주입
		loginSellerRequest.setCommonRequest(commonRequest);

		/** 1-1. SC-로그인인증 Call. */
		LoginSellerResponse logInSellerResponse = this.sellerSCI.loginSeller(loginSellerRequest);

		/** 2. SAC-[Response] 생성 및 주입. */
		AuthorizeRes res = new AuthorizeRes();
		SellerMbrSac sellerMbr = null;

		if (logInSellerResponse != null) {
			sellerMbr = new SellerMbrSac();
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

					if (StringUtils.equals(loginStatusCode, MemberConstants.USER_LOGIN_STATUS_NOMAL)
							&& StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL,
									logInSellerResponse.getSellerMainStatus())) { // TODO & -> && 로 수정 2014-05-01
						/** 4. 회원 인증키 생성[SC-REQUEST] 생성 및 주입 */
						UpdateLoginInfoRequest updateLoginInfoRequest = new UpdateLoginInfoRequest();

						LoginInfo loginInfo = new LoginInfo();
						// 만료일시 생성
						String expireDate = this.component.getExpirationTime(Integer.parseInt(req.getExpireDate()));
						loginInfo.setIpAddress(req.getIpAddress());
						loginInfo.setSessionKey(UUID.randomUUID().toString().replaceAll("-", ""));
						loginInfo.setExpireDate(expireDate);

						/** 4-1. 공통 헤더 생성 및 주입. */
						updateLoginInfoRequest.setCommonRequest(commonRequest);

						/** 4-2. [RESPONSE] 회원 인증키 주입. */
						res.setSessionKey(loginInfo.getSessionKey());
						res.setExpireDate(expireDate);
						sellerMbr.setSellerKey(logInSellerResponse.getSellerKey());

						// 서브 계정 Key
						if (StringUtils.equals(MemberConstants.USE_Y, logInSellerResponse.getIsSubSeller())) {
							loginInfo.setSellerKey(logInSellerResponse.getSubSellerKey());
							// Return Tenant
							res.setSubSellerKey(logInSellerResponse.getSubSellerKey());
						} else { // 주계정
							loginInfo.setSellerKey(logInSellerResponse.getSellerKey());
						}

						/** 4-3. SC회원 - 상태변경(회원인증키) Call. */
						updateLoginInfoRequest.setLoginInfo(loginInfo);
						this.sellerSCI.updateLoginInfo(updateLoginInfoRequest);
					}
				}
				/** 2-1. [RESPONSE] 회원 상태 및 로그인 상태 주입. */
				sellerMbr.setSellerClass(logInSellerResponse.getSellerClass());
				sellerMbr.setSellerMainStatus(logInSellerResponse.getSellerMainStatus());
				sellerMbr.setSellerSubStatus(logInSellerResponse.getSellerSubStatus());
				res.setLoginFailCount(loginFailCount);
				res.setIsLoginSuccess(isLoginSuccess);
				res.setLoginStatusCode(loginStatusCode);
				res.setIsSubSeller(logInSellerResponse.getIsSubSeller());
			}
			/** 2-2. [RESPONSE] 회원 정보 주입. */
			res.setSellerMbr(sellerMbr);
			LOGGER.debug("==>>[SAC] SellerMbr.toString() : {}", sellerMbr.toString());
		}

		// Response Debug
		LOGGER.debug("==>>[SAC] AuthorizeRes.toString() : {}", res.toString());
		LOGGER.debug("############ SellerServiceImpl.authorize() [START] ############");
		LOGGER.debug("{} : {}", res.getClass().getSimpleName(), ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.2.10. 판매자 기본정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyInformationSacReq
	 * @return ModifyInformationRes
	 */
	@Override
	public ModifyInformationSacRes modInformation(SacRequestHeader header, ModifyInformationSacReq req) {

		LOGGER.debug("############ SellerServiceImpl.modInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		/** 2. 기본정보수정 [REQUEST] 생성 및 주입. */
		UpdateSellerRequest updateSellerRequest = new UpdateSellerRequest();

		/** 2-3. 판매자 정보 생성 및 주입. */
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerTelecom(req.getSellerTelecom());
		sellerMbr.setSellerPhoneCountry(req.getSellerPhoneCountry());
		sellerMbr.setSellerPhone(req.getSellerPhone());
		sellerMbr.setCordedTelephone(req.getCordedTelephone());
		sellerMbr.setCordedTelephoneCountry(req.getCordedTelephoneCountry());
		sellerMbr.setIsRecvSMS(req.getIsRecvSms());
		sellerMbr.setIsRecvEmail(req.getIsRecvEmail());
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
		sellerMbr.setSellerCompany(req.getSellerCompany());
		sellerMbr.setSellerBizNumber(req.getSellerBizNumber());
		sellerMbr.setCustomerEmail(req.getCustomerEmail());
		sellerMbr.setCharger(req.getCharger());
		sellerMbr.setRepEmail(req.getRepEmail());
		sellerMbr.setRepPhone(req.getRepPhone());
		sellerMbr.setRepPhoneArea(req.getRepPhoneArea());
		sellerMbr.setWebsite(req.getWebsite());
		sellerMbr.setSellerNickName(req.getSellerNickName());
		updateSellerRequest.setSellerMbr(sellerMbr);

		/** 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;
		if (req.getPwReminderList() != null
				&& (StringUtils.isNotBlank(req.getPwReminderList().get(0).getQuestionId()) && StringUtils
						.isNotBlank(req.getPwReminderList().get(0).getAnswerString()))) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPwReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getPwReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionId());
				pwReminder.setQuestionMessage(req.getPwReminderList().get(i).getQuestionMessage());
				pWReminderList.add(pwReminder);
			}
			updateSellerRequest.setPWReminderList(pWReminderList);
			LOGGER.debug("==>>[SC] CreateSellerRequest.pWReminderList.toString() : {}", pWReminderList.toString());
		}
		/** 보안질문 리스트 주입 - [끝]. */

		/** 2-4. 공통 헤더 생성 및 주입. */
		updateSellerRequest.setCommonRequest(commonRequest);

		/** 2-5. SC회원 - 기본정보변경 Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);

		/** 2-6. Tenant [RESPONSE] 생성 및 주입 */
		ModifyInformationSacRes res = new ModifyInformationSacRes();
		res.setSellerKey(updateSellerResponse.getSellerKey());

		LOGGER.debug("############ SellerServiceImpl.modInformation() [START] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.11. 판매자회원 정산 정보 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyAccountInformationSacReq
	 * @return ModifyAccountInformationRes
	 */
	@Override
	public ModifyAccountInformationSacRes modAccountInformation(SacRequestHeader header,
			ModifyAccountInformationSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modAccountInformation() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// 수정 가능 회원 Check
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		// 메인, 서브 상태
		if (!StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
				.getSellerMainStatus())
				|| !StringUtils.equals(MemberConstants.SUB_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
						.getSellerSubStatus())) {
			throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr().getSellerMainStatus(),
					searchSellerResponse.getSellerMbr().getSellerSubStatus());
		}

		// 무료
		if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY, searchSellerResponse.getSellerMbr()
				.getSellerCategory())) {
			// 개인/법인 사업자
			if (!StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, searchSellerResponse
					.getSellerMbr().getSellerClass())) {
				throw new StorePlatformException("SAC_MEM_2004", searchSellerResponse.getSellerMbr()
						.getSellerCategory(), searchSellerResponse.getSellerMbr().getSellerClass());
			}
		}

		UpdateAccountSellerRequest updateAccountSellerRequest = new UpdateAccountSellerRequest();

		updateAccountSellerRequest.setSellerKey(req.getSellerKey());

		SellerMbr sellerMbr = new SellerMbr();
		// 회원 정보
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerBizType(req.getSellerBizType()); // INDT_NM 업종명 종목 종목
		sellerMbr.setSellerBizCategory(req.getSellerBizCategory()); // COND_NM 업태명 업태 업태
		sellerMbr.setSellerBizCorpNumber(req.getSellerBizCorpNumber()); // ("법인등록번호"); CORP_REG_NO
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
		sellerMbr.setIsOfficialAuth(req.getIsOfficialAuth()); // 공인인증여부
		sellerMbr.setCeoName(req.getCeoName());
		sellerMbr.setCeoBirthDay(req.getCeoBirthDay());
		sellerMbr.setCustomerPhoneCountry(req.getSellerBizPhoneCountry());
		sellerMbr.setCustomerPhone(req.getSellerBizPhone());

		// 주민 번호 추가
		sellerMbr.setSellerSSNumber(req.getSellerSSNumber());
		// 개인, 무료, 주민번호가 있을경우 --> 유료 상태 업데이트
		if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, searchSellerResponse
				.getSellerMbr().getSellerClass())
				&& StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY, searchSellerResponse
						.getSellerMbr().getSellerCategory()) && StringUtils.isNotBlank(req.getSellerSSNumber())) {

			sellerMbr.setSellerCategory(MemberConstants.SellerConstants.SELLER_TYPE_PAY);
		}

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
		sellerAccount.setBankBsb(req.getBankBsb());
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
		LOGGER.debug("############ SellerServiceImpl.modAccountInformation() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.13. 판매자회원 이메일 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyEmailSacReq
	 * @return ModifyEmailSacRes
	 */
	@Override
	public ModifyEmailSacRes modEmail(SacRequestHeader header, ModifyEmailSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modEmail() [START] ############");

		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// Default sessionKey 여부
		boolean isDefaultSessionKey = StringUtils.equals(this.defaultSessionKey, req.getSessionKey());

		// default session key (준회원 이메일 변경을 위한)
		if (!isDefaultSessionKey) {
			// SessionKey 유효성 체크
			this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());
		}

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
		// 준회원 요청시 담당자 이메일 변경
		if (isDefaultSessionKey) {
			sellerMbr.setCustomerEmail(req.getNewEmailAddress());
		}
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
	 *            SacRequestHeader
	 * @param req
	 *            ModifyPasswordSacReq
	 * @return ModifyPasswordSacRes
	 */
	@Override
	public ModifyPasswordSacRes modPassword(SacRequestHeader header, ModifyPasswordSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modPassword() [START] ############");
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
		mbrPwd.setOldPW(req.getOldPw());
		mbrPwd.setMemberPW(req.getNewPw());
		updatePasswordSellerRequest.setMbrPwd(mbrPwd);

		updatePasswordSellerRequest.setCommonRequest(commonRequest);

		UpdatePasswordSellerResponse updatePasswordSellerResponse = this.sellerSCI
				.updatePasswordSeller(updatePasswordSellerRequest);

		ModifyPasswordSacRes res = new ModifyPasswordSacRes();

		res.setSellerKey(updatePasswordSellerResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.modPassword() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.15. 판매자 회원 계정 승인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmReq
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
		// 계정승인시 회원가입일 등록
		updateStatusSellerRequest.setIsNewEntry(MemberConstants.USE_Y);

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
	 * [정산정보승인요청/전환신청]
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConversionClassSacReq
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

		if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY, searchSellerResponse.getSellerMbr()
				.getSellerCategory())) {
			// 무료 요청 가능 상태 => [메인 : 정상] , [서브 : 정상, 정산정보승인 거절, 개발자유형전환 거절, 정산정보승인 대기]
			if (!StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
					.getSellerMainStatus())
					|| !(StringUtils.equals(MemberConstants.SUB_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
							.getSellerSubStatus())
							|| StringUtils.equals(MemberConstants.SUB_STATUS_ACCT_JOIN_REJECT, searchSellerResponse
									.getSellerMbr().getSellerSubStatus())
							|| StringUtils.equals(MemberConstants.SUB_STATUS_TURN_REJECT, searchSellerResponse
									.getSellerMbr().getSellerSubStatus()) || StringUtils.equals(
							MemberConstants.SUB_STATUS_ACCT_APPLY_WATING, searchSellerResponse.getSellerMbr()
									.getSellerSubStatus()))) {
				throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr()
						.getSellerMainStatus(), searchSellerResponse.getSellerMbr().getSellerSubStatus());
			}
		} else {
			// 유료 요청 가능 상태 => [메인 : 정상], [서브 : 정상, 정산정보승인 거절, 개발자유형전환 거절]
			if (!StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
					.getSellerMainStatus())
					|| !(StringUtils.equals(MemberConstants.SUB_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
							.getSellerSubStatus())
							|| StringUtils.equals(MemberConstants.SUB_STATUS_ACCT_JOIN_REJECT, searchSellerResponse
									.getSellerMbr().getSellerSubStatus()) || StringUtils.equals(
							MemberConstants.SUB_STATUS_TURN_REJECT, searchSellerResponse.getSellerMbr()
									.getSellerSubStatus()))) {
				throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr()
						.getSellerMainStatus(), searchSellerResponse.getSellerMbr().getSellerSubStatus());
			}
		}

		UpgradeSellerRequest upgradeSellerRequest = new UpgradeSellerRequest();

		// 판매자 정보
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(req.getSellerKey());
		sellerMbr.setSellerID(searchSellerResponse.getSellerMbr().getSellerID());
		sellerMbr.setSellerClass(searchSellerResponse.getSellerMbr().getSellerClass());
		sellerMbr.setSellerCategory(searchSellerResponse.getSellerMbr().getSellerCategory());
		sellerMbr.setSellerMainStatus(searchSellerResponse.getSellerMbr().getSellerMainStatus());
		if (StringUtils.equals(req.getSellerClassTo(), searchSellerResponse.getSellerMbr().getSellerClass())) {
			sellerMbr.setSellerSubStatus(MemberConstants.SUB_STATUS_ACCT_APPLY_WATING);
		} else {
			sellerMbr.setSellerSubStatus(MemberConstants.SUB_STATUS_TURN_MOTION);
		}
		upgradeSellerRequest.setSellerMbr(sellerMbr);

		// 전환 정보
		SellerUpgrade sellerUpgrade = new SellerUpgrade();

		sellerUpgrade.setSellerKey(req.getSellerKey());
		sellerUpgrade.setBankAccount(req.getBankAccount());
		sellerUpgrade.setSellerClassTo(req.getSellerClassTo());
		sellerUpgrade.setRepEmail(req.getRepEmail());
		sellerUpgrade.setSellerBizCorpNumber(req.getSellerBizCorpNumber());
		sellerUpgrade.setSellerBizType(req.getSellerBizType());
		sellerUpgrade.setSellerBizCategory(req.getSellerBizCategory());
		sellerUpgrade.setBankCode(req.getBankCode());
		sellerUpgrade.setBankAcctName(req.getBankAcctName());
		sellerUpgrade.setIsAccountReal(req.getIsAccountReal());
		sellerUpgrade.setIsBizTaxable(req.getIsBizTaxable());
		// 사업장 전화 국가번호
		sellerUpgrade.setRepPhoneArea(req.getSellerBizPhoneCountry());
		// 사업장 전화 번호
		sellerUpgrade.setCustomerPhone(req.getSellerBizPhone());
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
		sellerUpgrade.setSellerCompany(req.getSellerCompany());
		sellerUpgrade.setSellerBizNumber(req.getSellerBizNumber());
		sellerUpgrade.setIsOfficialAuth(req.getIsOfficialAuth());
		sellerUpgrade.setCordedTelephone(req.getCordedTelephone());
		sellerUpgrade.setSellerPhone(req.getSellerPhone());
		sellerUpgrade.setIsRecvSMS(req.getIsRecvSms());
		sellerUpgrade.setCharger(req.getCharger());
		sellerUpgrade.setSellerBizType(req.getSellerBizType());
		sellerUpgrade.setBankBsb(req.getBankBsb());

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
				document.setIsUsed(MemberConstants.USE_N);

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
	 *            SacRequestHeader
	 * @param req
	 *            LockAccountReq
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
	 *            SacRequestHeader
	 * @param req
	 *            ModifyRealNameSacReq
	 * @return ModifyRealNameSacRes
	 */
	@Override
	public ModifyRealNameSacRes modRealName(SacRequestHeader header, ModifyRealNameSacReq req) {

		LOGGER.debug("############ SellerServiceImpl.modRealName() [START] ############");
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		UpdateRealNameSellerRequest updateRealNameSellerRequest = new UpdateRealNameSellerRequest();
		updateRealNameSellerRequest.setIsOwn(req.getIsOwn());
		updateRealNameSellerRequest.setIsRealName(req.getIsRealName());
		updateRealNameSellerRequest.setSellerKey(req.getSellerKey());

		if (StringUtils.equals(MemberConstants.AUTH_TYPE_OWN, req.getIsOwn())) {

			if (StringUtils.isBlank(req.getSellerBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "sellerBirthDay");
			}

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
			// TenantId 추가
			mbrAuth.setTenantID(header.getTenantHeader().getTenantId());
			updateRealNameSellerRequest.setMbrAuth(mbrAuth);

		} else if (StringUtils.equals(MemberConstants.AUTH_TYPE_PARENT, req.getIsOwn())) {

			if (StringUtils.isBlank(req.getParentBirthDay())) {
				throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
			}

			// 수정 가능 회원 Check
			SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
					MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

			// 실명인증정보에서 생년월일이 없을 경우, 회원 테이블의 생년월일 셋팅
			String ownBirth = StringUtils.isBlank(searchSellerResponse.getMbrAuth().getBirthDay()) ? searchSellerResponse
					.getSellerMbr().getSellerBirthDay() : searchSellerResponse.getMbrAuth().getBirthDay();

			// 법정대리인 나이 체크
			this.component.checkParentBirth(ownBirth, req.getParentBirthDay());

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
		LOGGER.debug("############ SellerServiceImpl.modRealName() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.24. 판매자 회원 탈퇴.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            WithdrawReq
	 * @return WithdrawRes
	 */
	@Override
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req) {

		LOGGER.debug("############ SellerServiceImpl.withdraw() [START] ############");

		RemoveSellerRequest schReq = new RemoveSellerRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		// SessionKey 유효성 체크
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		schReq.setCommonRequest(commonRequest);
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSecedeReasonCode(req.getSecedeReasonCode());
		schReq.setSecedeReasonMessage(req.getSecedeReasonMessage());

		this.sellerSCI.removeSeller(schReq);

		WithdrawRes response = new WithdrawRes();
		response.setSellerKey(req.getSellerKey());

		Message<CancelAccountRequest> amqpRequest = new Message<CancelAccountRequest>(Command.CANCEL_ACCOUNT,
				new CancelAccountRequest(req.getSellerKey()));
		try {
			this.sellerWithdrawAmqpTemplate.convertSendAndReceive(amqpRequest);
		} catch (AmqpException ex) {
			LOGGER.info("MQ process fail {}", amqpRequest);
		}
		LOGGER.debug("############ SellerServiceImpl.withdraw() [END] ############");
		return response;

	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            AbrogationAuthKeyReq
	 * @return AbrogationAuthKeyRes
	 */
	@Override
	public RemoveAuthorizationKeySacRes remAuthorizationKey(SacRequestHeader header, RemoveAuthorizationKeySacReq req) {

		RemoveLoginInfoRequest schReq = new RemoveLoginInfoRequest();

		schReq.setCommonRequest(this.component.getSCCommonRequest(header));

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(req.getSellerKey());
		schReq.setLoginInfo(loginInfo);

		this.sellerSCI.removeLoginInfo(schReq);

		RemoveAuthorizationKeySacRes response = new RemoveAuthorizationKeySacRes();

		response.setSellerKey(loginInfo.getSellerKey());

		return response;
	}

	/**
	 * <pre>
	 * 2.2.4. 판매자 회원 단순 인증.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeReq
	 * @return AuthorizeRes
	 */
	@Override
	public AuthorizeSimpleRes authorizeSimple(SacRequestHeader header, AuthorizeSimpleReq req) {

		LOGGER.debug("############ SellerServiceImpl.authorize() [START] ############");
		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		/** 1. SC회원 Req 생성 및 주입. */
		LoginSellerRequest loginSellerRequest = new LoginSellerRequest();
		loginSellerRequest.setSellerID(req.getSellerId());
		loginSellerRequest.setSellerPW(req.getSellerPw());

		LOGGER.debug("==>>[SC] LoginSellerRequest.toString() : {}", loginSellerRequest.toString());

		// SC공통 헤더 정보 주입
		loginSellerRequest.setCommonRequest(commonRequest);

		/** 1-1. SC-로그인인증 Call. */
		LoginSellerResponse schRes = this.sellerSCI.loginSeller(loginSellerRequest);

		AuthorizeSimpleRes response = new AuthorizeSimpleRes();

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
	 *            SacRequestHeader
	 * @param req
	 *            RemoveFlurrySacReq
	 * @return RemoveFlurrySacRes
	 */
	@Override
	public RemoveFlurrySacRes remFlurry(SacRequestHeader header, RemoveFlurrySacReq req) {

		// 1. CommonRequest Setting
		LOGGER.debug("############ SellerServiceImpl.modInformation() [START] ############");
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
				flurryAuth.setAuthToken(req.getFlurryAuthList().get(i).getAuthToken());
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

	/**
	 * <pre>
	 * 2.2.32. Flurry 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateFlurrySacRes
	 * @return CreateFlurrySacRes
	 */
	@Override
	public CreateFlurrySacRes regFlurry(SacRequestHeader header, CreateFlurrySacReq req) {
		// 1. CommonRequest Setting
		LOGGER.debug("############ SellerServiceImpl.createFlurrySacRes() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// 2. SessionKey Check
		this.component.checkSessionKey(commonRequest, req.getSessionKey(), req.getSellerKey());

		// 3. Flurry 등록/수정
		UpdateFlurryRequest updateFlurryRequest = new UpdateFlurryRequest();
		updateFlurryRequest.setSellerKey(req.getSellerKey());
		updateFlurryRequest.setCommonRequest(commonRequest);

		if (req.getFlurryAuthList() != null) {
			List<FlurryAuth> flurryAuthList = new ArrayList<FlurryAuth>();
			FlurryAuth flurryAuth = null;
			for (int i = 0; i < req.getFlurryAuthList().size(); i++) {
				flurryAuth = new FlurryAuth();
				flurryAuth.setSellerKey(req.getSellerKey());
				flurryAuth.setAuthToken(req.getFlurryAuthList().get(i).getAuthToken());
				flurryAuth.setAccessCode(req.getFlurryAuthList().get(i).getAccessCode());
				flurryAuthList.add(flurryAuth);
			}
			updateFlurryRequest.setFlurryAuthList(flurryAuthList);
		}

		UpdateFlurryResponse updateFlurryResponse = this.sellerSCI.updateFlurry(updateFlurryRequest);

		CreateFlurrySacRes res = new CreateFlurrySacRes();
		res.setSellerKey(updateFlurryResponse.getSellerKey());
		LOGGER.debug("############ SellerServiceImpl.createFlurrySacRes() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.33. 가가입 이메일 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyWaitEmailSacReq
	 * @return ModifyWaitEmailSacRes
	 */
	@Override
	public ModifyWaitEmailSacRes modWaitEmail(SacRequestHeader header, ModifyWaitEmailSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.modWaitEmail() [START] ############");

		// SC 공통 헤더 생성
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// 회원 정보 조회
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_SELLERMBR_ID, req.getSellerId());

		// 메인, 서브 상태
		if (!StringUtils.equals(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING, searchSellerResponse.getSellerMbr()
				.getSellerSubStatus())) {
			throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr().getSellerMainStatus(),
					searchSellerResponse.getSellerMbr().getSellerSubStatus());
		}
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
		sellerMbr.setSellerKey(searchSellerResponse.getSellerMbr().getSellerKey());
		sellerMbr.setSellerEmail(req.getNewEmailAddress());
		// 가가입 회원 이메일 변경시 담당자 이메일도 동일하게 변경
		sellerMbr.setCustomerEmail(req.getNewEmailAddress());
		updateSellerRequest.setSellerMbr(sellerMbr);
		updateSellerRequest.setCommonRequest(commonRequest);
		/** 2-5. SC회원 - 기본정보변경 Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);

		ModifyWaitEmailSacRes res = new ModifyWaitEmailSacRes();
		res.setSellerKey(updateSellerResponse.getSellerKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.34. Flurry 단건 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyFlurrySacReq
	 * @return ModifyFlurrySacRes
	 */
	@Override
	public ModifyFlurrySacRes modFlurry(SacRequestHeader header, ModifyFlurrySacReq req) {
		// 1. CommonRequest Setting
		LOGGER.debug("############ SellerServiceImpl.createFlurrySacRes() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// 3. Flurry 수정
		UpdateFlurryRequest updateFlurryRequest = new UpdateFlurryRequest();
		updateFlurryRequest.setCommonRequest(commonRequest);

		List<FlurryAuth> flurryAuthList = new ArrayList<FlurryAuth>();
		FlurryAuth flurryAuth = new FlurryAuth();
		flurryAuth.setAuthToken(req.getAuthToken());
		flurryAuth.setAccessCode(req.getAccessCode());
		flurryAuthList.add(flurryAuth);

		updateFlurryRequest.setFlurryAuthList(flurryAuthList);

		UpdateFlurryResponse updateFlurryResponse = this.sellerSCI.updateFlurry(updateFlurryRequest);
		ModifyFlurrySacRes res = new ModifyFlurrySacRes();
		res.setSellerKey(updateFlurryResponse.getSellerKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.35. 판매자회원 전환가입.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateChangeSacReq
	 * @return CreateChangeSacRes
	 */
	@Override
	public CreateChangeSacRes regChange(SacRequestHeader header, CreateChangeSacReq req) {
		LOGGER.debug("############ SellerServiceImpl.createChange() [START] ############");
		// SC공통 헤더
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);

		// 수정 가능 회원 Check
		SearchSellerResponse searchSellerResponse = this.component.getSearchSeller(commonRequest,
				MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO, req.getSellerKey());

		// 요청 가능 여부 확인 (준회원 확인 - [메인 : 정상, 서브 : 준회원])
		if (!(StringUtils.equals(MemberConstants.MAIN_STATUS_NORMAL, searchSellerResponse.getSellerMbr()
				.getSellerMainStatus()) && StringUtils.equals(MemberConstants.SUB_STATUS_ASSOCIATE_MEMBER,
				searchSellerResponse.getSellerMbr().getSellerSubStatus()))) {
			throw new StorePlatformException("SAC_MEM_2001", searchSellerResponse.getSellerMbr().getSellerMainStatus(),
					searchSellerResponse.getSellerMbr().getSellerSubStatus());
		}

		/** 1. Email 중복체크 [REQUEST] 생성 및 주입 */
		if (StringUtils.isNotBlank(req.getSellerEmail())) {

			CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

			/** 1-2. SC 헤더 셋팅 */
			checkDuplicationSellerRequest.setCommonRequest(commonRequest);

			KeySearch keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
			keySearch.setKeyString(req.getSellerEmail());
			List<KeySearch> keySearchs = new ArrayList<KeySearch>();
			keySearchs.add(keySearch);
			checkDuplicationSellerRequest.setKeySearchList(keySearchs);
			//
			/** 1-3. SC회원(Email 중복) Call */
			if (StringUtils.equals(MemberConstants.USE_Y,
					this.sellerSCI.checkDuplicationSeller(checkDuplicationSellerRequest).getIsRegistered())) {
				throw new StorePlatformException("SAC_MEM_2012", req.getSellerEmail());
			}
		}

		/** 2. SC회원 Req 생성 및 주입. */
		UpdateSellerRequest updateSellerRequest = new UpdateSellerRequest();

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
		// TenantId 추가
		mbrAuth.setTenantID(header.getTenantHeader().getTenantId());

		updateSellerRequest.setMbrAuth(mbrAuth);

		LOGGER.debug("==>>[SC] updateSellerRequest.MbrAuth.toString() : {}", mbrAuth.toString());
		/** 실명인증정보 생성 및 주입 [끝]. */

		/** 2-1. 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;

		if (req.getPwReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPwReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getPwReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionId());
				pwReminder.setQuestionMessage(req.getPwReminderList().get(i).getQuestionMessage());
				pWReminderList.add(pwReminder);
				LOGGER.debug("==>>[SC] updateSellerRequest.PWReminder[{}].toString() : {}", i, pwReminder.toString());
			}
			updateSellerRequest.setPWReminderList(pWReminderList);
			LOGGER.debug("==>>[SC] updateSellerRequest.pWReminderList.toString() : {}", pWReminderList.toString());
		}
		/** 보안질문 리스트 주입 - [끝]. */

		/** 2-3. 판매자 회원 정보 생성 및 주입 - [시작]. */
		SellerMbr sellerMbr = new SellerMbr();
		// 판매자 회원 키
		sellerMbr.setSellerKey(req.getSellerKey());
		// 판매자구분코드
		sellerMbr.setSellerClass(req.getSellerClass());
		// 판매자 분류코드
		sellerMbr.setSellerCategory(MemberConstants.SellerConstants.SELLER_TYPE_NOPAY);
		// 판매자 main 상태 코드
		sellerMbr.setSellerMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
		// 판매자 sub 상태 코드
		sellerMbr.setSellerSubStatus(MemberConstants.SUB_STATUS_NORMAL);
		// 통신사 코드
		sellerMbr.setSellerTelecom(req.getSellerTelecom());
		// 무선 국가번호
		sellerMbr.setSellerPhoneCountry(req.getSellerPhoneCountry());
		// 무선 전화번호
		sellerMbr.setSellerPhone(req.getSellerPhone());
		// 유선 전화번호
		sellerMbr.setCordedTelephoneCountry(req.getCordedTelephoneCountry());
		// 유선 전화번호
		sellerMbr.setCordedTelephone(req.getCordedTelephone());
		// SMS 수신여부
		sellerMbr.setIsRecvSMS(req.getIsRecvSms());
		if (StringUtils.isNotBlank(req.getSellerEmail())) {
			// 판매자 이메일
			sellerMbr.setSellerEmail(req.getSellerEmail());
		}
		// 이메일 수신여부
		sellerMbr.setIsRecvEmail(req.getIsRecvEmail());
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
		sellerMbr.setRepEmail(req.getRepEmail());
		sellerMbr.setRepPhone(req.getRepPhone());
		sellerMbr.setRepPhoneArea(req.getRepPhoneArea());
		// 법인등록번호
		sellerMbr.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_NOMAL);
		sellerMbr.setStopStatusCode(MemberConstants.USER_STOP_STATUS_NOMAL);
		// 담당자 명
		sellerMbr.setCharger(req.getCharger());
		sellerMbr.setWebsite(req.getWebsite());
		// 판매자 회원명
		sellerMbr.setSellerName(req.getSellerName());
		updateSellerRequest.setSellerMbr(sellerMbr);
		// Debug
		LOGGER.debug("==>>[SC] updateSellerRequest.SellerMbr.toString() : {}", sellerMbr.toString());
		/** 판매자 회원 정보 생성 및 주입 - [끝]. */

		/** 2-4. 회원 전환 가입을 위한 공통 헤더 주입. */
		updateSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] updateSellerRequest.toString() : {}", updateSellerRequest.toString());

		/** 2-5. SC회원[updateSeller] Call. */
		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);

		/** 판매자 회원 비밀번호 생성 및 주입. */
		UpdateNewPasswordSellerRequest updateNewPasswordSellerRequest = new UpdateNewPasswordSellerRequest();
		updateNewPasswordSellerRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID(searchSellerResponse.getSellerMbr().getSellerID());
		mbrPwd.setMemberPW(req.getSellerPw());
		updateNewPasswordSellerRequest.setMbrPwd(mbrPwd);

		LOGGER.debug("==>>[SC] updateSellerRequest.MbrPwd.toString() : {}", mbrPwd.toString());

		/** SC회원[updateNewPasswordSeller] Call. */
		this.sellerSCI.updateNewPasswordSeller(updateNewPasswordSellerRequest);

		/** 3. 전환가입 결과 [RESPONSE] 생성 및 주입. */
		CreateChangeSacRes res = new CreateChangeSacRes();
		SellerMbrSac resMbr = new SellerMbrSac();
		resMbr.setSellerId(updateSellerResponse.getSellerID());
		resMbr.setSellerKey(updateSellerResponse.getSellerKey());
		resMbr.setSellerMainStatus(updateSellerResponse.getSellerMainStatus());
		resMbr.setSellerSubStatus(updateSellerResponse.getSellerSubStatus());
		res.setSellerMbr(resMbr);
		// Debug
		LOGGER.debug("==>>[SAC] CreateChangeSacRes.toString() : \n{}", res.toString());

		LOGGER.debug("############ SellerServiceImpl.createChange() [END] ############");
		return res;
	}

	/**
	 * <pre>
	 * 2.2.36. 판매자 약관 동의 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateTermsAgreementSacReq
	 * @return CreateTermsAgreementSacRes
	 */
	@Override
	public CreateTermsAgreementSacRes regTermsAgreement(SacRequestHeader header, CreateTermsAgreementSacReq req) {

		List<SellerAgreement> agreementList = req.getAgreementList();

		LOGGER.debug("############ SellerServiceImpl.regTermsAgreement() [START] ############");

		UpdateAgreementSellerRequest updateAgreementRequest = new UpdateAgreementSellerRequest();
		updateAgreementRequest.setCommonRequest(this.component.getSCCommonRequest(header));
		updateAgreementRequest.setSellerKey(req.getSellerKey());

		if (agreementList.size() > 0) {

			/**
			 * 약관 동의 리스트 setting.
			 */
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (SellerAgreement info : agreementList) {

				if (StringUtils.isBlank(info.getClauseAgreementId())
						|| StringUtils.isBlank(info.getIsClauseAgreement())) {
					throw new StorePlatformException("SAC_MEM_0001", "clauseAgreementId 또는 isClauseAgreement");
				}

				MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
				mbrClauseAgree.setExtraAgreementID(info.getClauseAgreementId());
				mbrClauseAgree.setExtraAgreementVersion(info.getClauseAgreementVersion());
				mbrClauseAgree.setIsExtraAgreement(info.getIsClauseAgreement());
				mbrClauseAgree.setIsMandatory(info.getIsClauseMandatory());
				mbrClauseAgree.setRegDate(DateUtil.getToday("yyyyMMddHHmmss"));
				mbrClauseAgreeList.add(mbrClauseAgree);
			}
			updateAgreementRequest.setMbrClauseAgreeList(mbrClauseAgreeList);

		}

		LOGGER.debug("==>>[SC] updateAgreementSeller.updateAgreementRequest.toString() : {}",
				updateAgreementRequest.toString());

		/**
		 * SC 약관동의 정보를 등록 또는 수정 연동.
		 */
		UpdateAgreementSellerResponse updateAgreementResponse = this.sellerSCI
				.updateAgreementSeller(updateAgreementRequest);

		CreateTermsAgreementSacRes res = new CreateTermsAgreementSacRes();
		res.setSellerKey(updateAgreementResponse.getSellerKey());
		// Debug
		LOGGER.debug("############ SellerServiceImpl.regTermsAgreement() [END] ############");
		return res;
	}

}
