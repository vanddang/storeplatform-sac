package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.RandomString;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;

/**
 * 판매자 회원의 가입/수정/탈퇴/인증 기능정의
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Override
	public CreateRes createSeller(SacRequestHeader header, CreateReq req) {

		/** 1. SC회원 Req 생성 및 주입. */
		CreateSellerRequest createSellerRequest = new CreateSellerRequest();

		/** 실명인증정보 생성 및 주입 [시작]. */
		if ("Y".equals(req.getIsRealName())) {
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
			// 실명 인증 일시
			mbrAuth.setRealNameDate(req.getRealNameDate());
			createSellerRequest.setMbrAuth(mbrAuth);

		}
		/** 실명인증정보 생성 및 주입 [끝]. */

		/** 약관동의 정보 리스트 주입 - [시작]. */
		List<MbrClauseAgree> mbrClauseAgreeList = null;
		MbrClauseAgree mbrClauseAgree = null;
		if (req.getAgreementList() != null) {
			mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (int i = 0; i < req.getAgreementList().size(); i++) {
				mbrClauseAgree = new MbrClauseAgree();
				// 약관동의 ID
				mbrClauseAgree.setExtraAgreementID(req.getAgreementList().get(i).getExtraAgreementId());
				// 약관동의 여부
				mbrClauseAgree.setIsExtraAgreement(req.getAgreementList().get(i).getIsExtraAgreement());
				// 약관 버전
				mbrClauseAgree.setExtraAgreementVersion(req.getAgreementList().get(i).getExtraAgreementVersion());
				mbrClauseAgreeList.add(mbrClauseAgree);
			}
			createSellerRequest.setMbrClauseAgree(mbrClauseAgreeList);
		}
		/** 약관동의 정보 리스트 주입 - [끝]. */

		/** 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;
		if (req.getPwReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPwReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getPwReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionID());
				pwReminder.setQuestionMessage(req.getPwReminderList().get(i).getQuestionMessage());
				pWReminderList.add(pwReminder);
			}
			createSellerRequest.setPWReminderList(pWReminderList);
		}
		/** 보안질문 리스트 주입 - [끝]. */

		/** 판매자 회원 정보 생성 및 주입 - [시작]. */
		// 판매자 회원 PW
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberPW(req.getSellerPW());
		createSellerRequest.setMbrPwd(mbrPwd);
		// sellerMbr.setSellerPW(req.getSellerPW());
		SellerMbr sellerMbr = new SellerMbr();
		// 판매자 회원 ID
		sellerMbr.setSellerID(req.getSellerId());
		// 판매자구분코드
		sellerMbr.setSellerClass(req.getSellerClass());
		// 판매자 분류코드
		sellerMbr.setSellerCategory(req.getSellerCategory());
		// 판매자 main 상태 코드
		sellerMbr.setSellerMainStatus(req.getSellerMainStatus());
		// 판매자 sub 상태 코드
		sellerMbr.setSellerSubStatus(req.getSellerSubStatus());
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
		sellerMbr.setIsDomestic(req.getIsForeign()); // ***
		// 실명인증여부
		sellerMbr.setIsRealName(req.getIsRealName());
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

		// TODO 임시
		sellerMbr.setLoginStatusCode("10");
		sellerMbr.setStopStatusCode("80");

		createSellerRequest.setSellerMbr(new SellerMbr());
		/** 판매자 회원 정보 생성 및 주입 - [끝]. */

		// 최종 vo 에 값 셋팅
		createSellerRequest.setSellerMbr(sellerMbr);

		LOGGER.debug("### 넘긴 데이터 : {}", createSellerRequest.toString());

		/** 2. SC 공통 헤더 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		createSellerRequest.setCommonRequest(commonRequest);

		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(createSellerRequest);

		// Debug
		LOGGER.debug("[createSeller] - CreateSellerResponse CODE : {}, MESSAGE : {}", createSellerResponse
				.getCommonResponse().getResultCode(), createSellerResponse.getCommonResponse().getResultMessage());

		// TODO Exception 재정의 필요
		if (!MemberConstants.RESULT_SUCCES.equals(createSellerResponse.getCommonResponse().getResultCode())) {
			throw new RuntimeException(createSellerResponse.getCommonResponse().getResultMessage());
		}

		/** TODO 통합CMS 연동 ?? 협의중... */

		// 결과 리턴 객체 생성 및 주입
		CreateRes res = new CreateRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr resMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
		resMbr.setSellerId(createSellerResponse.getSellerID());
		resMbr.setSellerKey(createSellerResponse.getSellerKey());
		resMbr.setSellerMainStatus(createSellerResponse.getSellerMainStatus());
		resMbr.setSellerSubStatus(createSellerResponse.getSellerSubStatus());
		res.setSellerMbr(resMbr);
		return res;
	}

	@Override
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req) {

		/** 1. SC회원 Req 생성 및 주입. */
		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		updateStatusSellerRequest.setSellerID(req.getSellerId());

		updateStatusSellerRequest.setSellerMainStatus(MemberConstants.MAIN_STATUS_PAUSE);
		updateStatusSellerRequest.setSellerSubStatus(MemberConstants.SUB_STATUS_LOGIN_PAUSE);

		/** 2. 공통 헤더 생성 및 주입. */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		updateStatusSellerRequest.setCommonRequest(commonRequest);

		/** 3. SC회원 - 상태변경 Call. */
		UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
				.updateStatusSeller(updateStatusSellerRequest);

		// Response Debug
		LOGGER.info("[lockAccount] - UpdateStatusSellerResponse CODE : {}, MESSGE : {}", updateStatusSellerResponse
				.getCommonResponse().getResultCode(), updateStatusSellerResponse.getCommonResponse().getResultMessage());

		// TODO Exception 재정의 - 결과 값 성공(0000)이 아니면 던져~~~
		if (!MemberConstants.RESULT_SUCCES.equals(updateStatusSellerResponse.getCommonResponse().getResultCode())) {
			throw new RuntimeException(updateStatusSellerResponse.getCommonResponse().getResultMessage());
		}

		/** 4. TenantRes Response 생성 및 주입 */
		return new LockAccountRes(updateStatusSellerRequest.getSellerID());
	}

	@Override
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req) {

		/** 1. SC회원 Req 생성 및 주입 */
		LoginSellerRequest loginSellerRequest = new LoginSellerRequest();
		loginSellerRequest.setSellerID(req.getSellerId());
		loginSellerRequest.setSellerPW(req.getSellerPW());

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		loginSellerRequest.setCommonRequest(commonRequest);

		/** 3. SC-로그인인증 Call */
		LoginSellerResponse logInSellerResponse = this.sellerSCI.loginSeller(loginSellerRequest);

		// Response Debug
		LOGGER.info("logInSellerResponse Code : {}", logInSellerResponse.getCommonResponse().getResultCode());
		LOGGER.info("logInSellerResponse Messge : {}", logInSellerResponse.getCommonResponse().getResultMessage());

		// TODO Exception 재정의 - 결과 값 성공(0000)이 아니면 던져~~~
		// if (!MemberConstants.RESULT_SUCCES.equals(logInSellerResponse.getCommonResponse().getResultCode())) {
		// throw new RuntimeException(logInSellerResponse.getCommonResponse().getResultMessage());
		// }

		AuthorizeRes res = new AuthorizeRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr sellerMbr = null;

		if (logInSellerResponse != null) {
			sellerMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
			sellerMbr.setSellerKey(logInSellerResponse.getSellerKey());
			sellerMbr.setSellerClass(logInSellerResponse.getSellerClass());
			sellerMbr.setSellerMainStatus(logInSellerResponse.getSellerMainStatus());
			sellerMbr.setSellerSubStatus(logInSellerResponse.getSellerSubStatus());
			res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
			res.setSellerMbr(sellerMbr);
		}
		res.setIsLoginSuccess(logInSellerResponse.getIsLoginSuccess());
		res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
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
	public WithdrawRes withdraw(SacRequestHeader header, WithdrawReq req) throws Exception {

		RemoveSellerResponse schRes = new RemoveSellerResponse();
		RemoveSellerRequest schReq = new RemoveSellerRequest();
		/** TODO 2. 테스트용 if 헤더 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		schReq.setCommonRequest(commonRequest);
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSecedeReasonCode(req.getSecedeReasonCode());
		schReq.setSecedeReasonMessage(req.getSecedeReasonMessage());

		schRes = this.sellerSCI.removeSeller(schReq);
		if (!MemberConstants.RESULT_SUCCES.equals(schRes.getCommonResponse().getResultCode())) {
			throw new RuntimeException(schRes.getCommonResponse().getResultMessage());
		}

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
	public CreateAuthKeyRes createAuthKey(SacRequestHeader header, CreateAuthKeyReq req) throws Exception {

		UpdateLoginInfoResponse schRes = new UpdateLoginInfoResponse();
		UpdateLoginInfoRequest schReq = new UpdateLoginInfoRequest();

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		schReq.setCommonRequest(commonRequest);

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(req.getSellerKey());
		loginInfo.setIpAddress(req.getIpAddress());
		loginInfo.setSessionKey(req.getSellerKey() + "_" + RandomString.getString(10));
		loginInfo.setRegDate(req.getExpireDate());

		schReq.setLoginInfo(loginInfo);

		schRes = this.sellerSCI.updateLoginInfo(schReq);
		if (!MemberConstants.RESULT_SUCCES.equals(schRes.getCommonResponse().getResultCode())) {
			throw new RuntimeException(schRes.getCommonResponse().getResultMessage());
		}

		CreateAuthKeyRes response = new CreateAuthKeyRes();

		// response.setSessionKey(schRes.g)

		return response;
	}

	/**
	 * <pre>
	 * SC SellerMbr To SAC SellerInfo.
	 * </pre>
	 * 
	 * @param sellerInfo
	 * @return SellerInfo
	 */
	private SellerMbr converterSACToSCSellerMbr(SellerMbr sellerMbr) {
		SellerMbr sellerInfo = new SellerMbr();
		// sellerMbr.set
		return sellerInfo;
	}

}
