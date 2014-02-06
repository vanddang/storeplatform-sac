package com.skplanet.storeplatform.sac.member.seller.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.BanksByCountry;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 판매자 회원 조회관련 기능 항목들
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
@Service
public class SellerSearchServiceImpl implements SellerSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSearchServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MemberCommonComponent commonComponent;

	/**
	 * <pre>
	 * 2.2.2. 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return DuplicateByIdEmailRes
	 */
	@Override
	public DuplicateByIdEmailRes duplicateByIdEmail(SacRequestHeader header, DuplicateByIdEmailReq req) {

		/** SC회원 시작 */
		/** 1. ID/Email Req 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** 1-2. SC 헤더 셋팅 */
		checkDuplicationSellerRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		KeySearch keySearch = new KeySearch();
		if ("id".equals(req.getKeyType())) {
			keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
		} else if ("email".equals(req.getKeyType())) {
			keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
		}
		keySearch.setKeyString(req.getKeyString());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);

		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 1-3. SC회원(ID/Email중복) Call */
		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		// Debug
		LOGGER.info("checkDuplicationSellerResponse CODE : {}, MESSAGE : {}", checkDuplicationSellerResponse
				.getCommonResponse().getResultCode(), checkDuplicationSellerResponse.getCommonResponse()
				.getResultMessage());

		/** 1-4. [RESPONSE] 생성 및 주입 */
		DuplicateByIdEmailRes response = new DuplicateByIdEmailRes();
		response.setIsRegistered(checkDuplicationSellerResponse.getIsRegistered());
		return response;
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보 조회.
	 * </pre>
	 * 
	 * @param DetailInformationReq
	 * @return DetailInformationRes
	 */
	@Override
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req) {

		SearchSellerResponse schRes = new SearchSellerResponse();
		SearchSellerRequest schReq = new SearchSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		KeySearch keySearch = new KeySearch();

		if (!req.getSellerKey().equals("")) {
			keySearch.setKeyString(req.getSellerKey());
		} else {
			DetailInformationReq sellerDTO = this.commonDAO.queryForObject("SellerSearch.sellerKey", req,
					DetailInformationReq.class);
			keySearch.setKeyString(sellerDTO.getSellerKey());
		}

		DetailInformationRes response = new DetailInformationRes();
		if (keySearch.getKeyString() != null)
			if (!keySearch.getKeyString().equals("")) {

				keySearch.setKeyType("INSD_SELLERMBR_NO");
				List<KeySearch> list = new ArrayList<KeySearch>();
				list.add(keySearch);
				schReq.setKeySearchList(list);

				schRes = this.sellerSCI.searchSeller(schReq);
				// 판매자 멀티미디어정보
				List<ExtraRight> eList = new ArrayList<ExtraRight>();
				ExtraRight extraRightList = null;
				if (schRes.getExtraRightList() != null)
					for (int i = 0; i < schRes.getExtraRightList().size(); i++) {
						extraRightList = new ExtraRight();
						extraRightList.setEndDate(schRes.getExtraRightList().get(i).getEndDate());
						extraRightList.setRegDate(schRes.getExtraRightList().get(i).getRegDate());
						extraRightList.setRegID(schRes.getExtraRightList().get(i).getRegID());
						extraRightList.setRightProfileCode(schRes.getExtraRightList().get(i).getRightProfileCode());
						extraRightList.setSellerKey(schRes.getExtraRightList().get(i).getSellerKey());
						extraRightList.setSellerRate(schRes.getExtraRightList().get(i).getSellerRate());
						extraRightList.setStartDate(schRes.getExtraRightList().get(i).getStartDate());
						extraRightList.setTenantID(schRes.getExtraRightList().get(i).getTenantID());
						extraRightList.setTenantRate(schRes.getExtraRightList().get(i).getTenantRate());
						extraRightList.setUpdateDate(schRes.getExtraRightList().get(i).getUpdateDate());
						extraRightList.setUpdateID(schRes.getExtraRightList().get(i).getUpdateID());
						eList.add(extraRightList);
					}

				// 법정대리인정보
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				if (schRes.getMbrLglAgent() != null) {
					mbrLglAgent.setMemberKey(schRes.getMbrLglAgent().getMemberKey());
					mbrLglAgent.setParentBirthDay(schRes.getMbrLglAgent().getParentBirthDay());
					mbrLglAgent.setParentCI(schRes.getMbrLglAgent().getParentCI());
					mbrLglAgent.setParentDate(schRes.getMbrLglAgent().getParentDate());
					mbrLglAgent.setParentEmail(schRes.getMbrLglAgent().getParentEmail());
					mbrLglAgent.setParentMDN(schRes.getMbrLglAgent().getParentMDN());
					mbrLglAgent.setParentName(schRes.getMbrLglAgent().getParentName());
					mbrLglAgent.setParentRealNameDate(schRes.getMbrLglAgent().getParentRealNameDate());
					mbrLglAgent.setParentRealNameMethod(schRes.getMbrLglAgent().getParentRealNameMethod());
					mbrLglAgent.setParentRealNameSite(schRes.getMbrLglAgent().getParentRealNameSite());
					mbrLglAgent.setParentTelecom(schRes.getMbrLglAgent().getParentTelecom());
					mbrLglAgent.setParentType(schRes.getMbrLglAgent().getParentType());
					mbrLglAgent.setSequence(schRes.getMbrLglAgent().getSequence());
				}

				response.setExtraRightList(eList);// 판매자 멀티미디어정보
				response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
				response.setSellerKey(schRes.getSellerKey());// 판매자Key
				response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));// 판매자 정보
			}
		return response;

	}

	/**
	 * <pre>
	 * 판매자회원 정산정보 조회.
	 * </pre>
	 * 
	 * @param DetailAccountInformationReq
	 * @return DetailAccountInformationRes
	 */
	@Override
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req) {

		SearchAccountSellerResponse schRes = new SearchAccountSellerResponse();
		SearchAccountSellerRequest schReq = new SearchAccountSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());

		schRes = this.sellerSCI.searchAccountSeller(schReq);
		// 판매자 문서정보
		List<Document> dList = new ArrayList<Document>();
		Document document = null;
		if (schRes.getDocument() != null)
			for (int i = 0; i < schRes.getDocument().size(); i++) {
				document = new Document();
				document.setAccountChangeKey(schRes.getDocument().get(i).getAccountChangeKey());
				document.setAccountChangeKey(schRes.getDocument().get(i).getAccountChangeKey());
				document.setDocumentCode(schRes.getDocument().get(i).getDocumentCode());
				document.setDocumentName(schRes.getDocument().get(i).getDocumentName());
				document.setDocumentPath(schRes.getDocument().get(i).getDocumentPath());
				document.setDocumentSize(schRes.getDocument().get(i).getDocumentSize());
				document.setIsUsed(schRes.getDocument().get(i).getIsUsed());
				document.setRegDate(schRes.getDocument().get(i).getRegDate());
				document.setSellerKey(schRes.getDocument().get(i).getSellerKey());
				document.setUpdateDate(schRes.getDocument().get(i).getUpdateDate());
				document.setUpdateID(schRes.getDocument().get(i).getUpdateID());
				dList.add(document);
			}

		// 판매자 부가정보 목록
		List<ExtraRight> eList = new ArrayList<ExtraRight>();
		ExtraRight extraRightList = null;
		if (schRes.getExtraRight() != null)
			for (int i = 0; i < schRes.getExtraRight().size(); i++) {
				extraRightList = new ExtraRight();
				// extraRightList.setEndDate(schRes.getExtraRight().get(i).getEndDate());
				// extraRightList.setRegDate(schRes.getExtraRight().get(i).getRegDate());
				extraRightList.setRegID(schRes.getExtraRight().get(i).getRegID());
				extraRightList.setRightProfileCode(schRes.getExtraRight().get(i).getRightProfileCode());
				// extraRightList.setSellerKey(schRes.getExtraRight().get(i).getSellerKey());
				// extraRightList.setSellerRate(schRes.getExtraRight().get(i).getSellerRate());
				// extraRightList.setStartDate(schRes.getExtraRight().get(i).getStartDate());
				extraRightList.setTenantID(schRes.getExtraRight().get(i).getTenantID());
				extraRightList.setTenantRate(schRes.getExtraRight().get(i).getTenantRate());
				extraRightList.setUpdateDate(schRes.getExtraRight().get(i).getUpdateDate());
				extraRightList.setUpdateID(schRes.getExtraRight().get(i).getUpdateID());
				eList.add(extraRightList);
			}

		// 판매자 정산정보
		SellerAccount sellerAccount = new SellerAccount();
		if (schRes.getSellerAccount() != null) {
			sellerAccount.setAbaCode(schRes.getSellerAccount().getAbaCode());
			sellerAccount.setAccountRealDate(schRes.getSellerAccount().getAccountRealDate());
			sellerAccount.setBankAccount(schRes.getSellerAccount().getBankAccount());
			sellerAccount.setBankAcctName(schRes.getSellerAccount().getBankAcctName());
			sellerAccount.setBankAddress(schRes.getSellerAccount().getBankAddress());
			sellerAccount.setBankBranch(schRes.getSellerAccount().getBankBranch());
			sellerAccount.setBankBranchCode(schRes.getSellerAccount().getBankBranchCode());
			sellerAccount.setBankCode(schRes.getSellerAccount().getBankCode());
			sellerAccount.setBankLocation(schRes.getSellerAccount().getBankLocation());
			sellerAccount.setBankName(schRes.getSellerAccount().getBankName());
			// sellerAccount.setEndDate(schRes.getSellerAccount().getEndDate());
			sellerAccount.setIbanCode(schRes.getSellerAccount().getIbanCode());
			sellerAccount.setIsUsed(schRes.getSellerAccount().getIsUsed());
			sellerAccount.setReason(schRes.getSellerAccount().getReason());
			// sellerAccount.setSellerKey(schRes.getSellerAccount().getSellerKey());
			// sellerAccount.setStartDate(schRes.getSellerAccount().getStartDate());
			sellerAccount.setSwiftCode(schRes.getSellerAccount().getSwiftCode());
			sellerAccount.setTpinCode(schRes.getSellerAccount().getTpinCode());
		}

		DetailAccountInformationRes response = new DetailAccountInformationRes();
		response.setDocument(dList);
		response.setExtraRight(eList);
		response.setSellerAccount(sellerAccount);
		response.setSellerKey(schRes.getSellerKey());
		response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));
		return response;

	}

	/**
	 * <pre>
	 * 탈퇴 사유 목록 조회.
	 * </pre>
	 * 
	 * @param language
	 * @return ListWithdrawalReasonRes
	 */
	@Override
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header, String language) {

		ListWithdrawalReasonReq req = new ListWithdrawalReasonReq();
		req.setKoUsWhether(language);

		List<SecedeReson> sList = this.commonDAO.queryForList("SellerSearch.listWithdrawalReason", req,
				SecedeReson.class);

		/*
		 * List<SecedeReson> sList = new ArrayList<SecedeReson>(); SecedeReson secedeReson = null; if (sellerDTO !=
		 * null) for (int i = 0; i < sellerDTO.size(); i++) { secedeReson = new SecedeReson();
		 * secedeReson.setSecedeReasonCode(sellerDTO.get(i).getSecedeReasonCode());
		 * secedeReson.setSecedeReasonMessage(sellerDTO.get(i).getSecedeReasonMessage()); sList.add(secedeReson); }
		 */

		ListWithdrawalReasonRes response = new ListWithdrawalReasonRes();
		response.setSecedeResonList(sList);

		return response;

	}

	/**
	 * <pre>
	 * 판매자회원 ID 찾기.
	 * </pre>
	 * 
	 * @param SearchIdReq
	 * @return SearchIdRes
	 */
	@Override
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req) {

		SearchIDSellerResponse schRes = new SearchIDSellerResponse();
		SearchIDSellerRequest schReq = new SearchIDSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		boolean reqStat = false;
		if (req.getSellerBizNumber() != null)
			if (!req.getSellerBizNumber().isEmpty()) {
				schReq.setSellerBizNumber(req.getSellerBizNumber());
				reqStat = true;
			}
		if (req.getSellerCompany() != null)
			if (!req.getSellerCompany().isEmpty()) {
				schReq.setSellerCompany(req.getSellerCompany());
				reqStat = true;
			}
		if (req.getSellerEmail() != null)
			if (!req.getSellerEmail().isEmpty()) {
				schReq.setSellerEmail(req.getSellerEmail());
				reqStat = true;
			}
		if (req.getSellerPhone() != null)
			if (!req.getSellerPhone().isEmpty()) {
				schReq.setSellerPhone(req.getSellerPhone());
				reqStat = true;
			}

		if (reqStat)
			schRes = this.sellerSCI.searchIDSeller(schReq);

		List<SellerMbr> sList = new ArrayList<SellerMbr>();
		SellerMbr sellerMbrRes = null;
		if (schRes.getSellerMbr() != null)
			for (int i = 0; i < schRes.getSellerMbr().size(); i++) {
				sellerMbrRes = new SellerMbr();
				sellerMbrRes.setSellerId(schRes.getSellerMbr().get(i).getSellerID());
				sList.add(sellerMbrRes);
			}

		SearchIdRes response = new SearchIdRes();
		response.setSellerMbr(sList);// 판매자 정보 리스트

		return response;

	}

	/**
	 * <pre>
	 * Password 보안 질문 조회.
	 * </pre>
	 * 
	 * @param language
	 * @return ListPasswordReminderQuestionRes
	 */
	@Override
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			ListPasswordReminderQuestionReq req) {

		SearchPwdHintListResponse schRes = new SearchPwdHintListResponse();
		SearchPwdHintListRequest schReq = new SearchPwdHintListRequest();

		schReq.setSellerKey(req.getSellerKey());
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		schRes = this.sellerSCI.searchPwdHintList(schReq);

		List<SellerMbrPwdHint> sList = new ArrayList<SellerMbrPwdHint>();
		SellerMbrPwdHint sellerMbrPwdHint = null;
		if (schRes.getPWReminderList() != null)
			for (int i = 0; i < schRes.getPWReminderList().size(); i++) {
				sellerMbrPwdHint = new SellerMbrPwdHint();
				sellerMbrPwdHint.setQuestionID(schRes.getPWReminderList().get(i).getQuestionID());
				sellerMbrPwdHint.setQuestionMessage(schRes.getPWReminderList().get(i).getQuestionMessage());
				sellerMbrPwdHint.setUpdateDate(schRes.getPWReminderList().get(i).getUpdateDate());
				sList.add(sellerMbrPwdHint);
			}

		ListPasswordReminderQuestionRes response = new ListPasswordReminderQuestionRes();
		response.setSellerMbrPwdHintList(sList);

		return response;

	}

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 * 
	 * @param language
	 * @return CheckPasswordReminderQuestionRes
	 */
	@Override
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			CheckPasswordReminderQuestionReq req) {

		CheckPasswordReminderSellerResponse schRes = new CheckPasswordReminderSellerResponse();
		CheckPasswordReminderSellerRequest schReq = new CheckPasswordReminderSellerRequest();

		schReq.setSellerID(req.getSellerID());

		/** 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;
		if (req.getpWReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getpWReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getpWReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getpWReminderList().get(i).getQuestionID());
				pwReminder.setQuestionMessage(req.getpWReminderList().get(i).getQuestionMessage());
				pwReminder.setSellerID(req.getpWReminderList().get(i).getSellerID());
				pWReminderList.add(pwReminder);
			}
			schReq.setPWReminderList(pWReminderList);
		}
		/** 보안질문 리스트 주입 - [끝]. */

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schRes = this.sellerSCI.checkPasswordReminderSeller(schReq);

		CheckPasswordReminderQuestionRes response = new CheckPasswordReminderQuestionRes();
		response.setIsCorrect(schRes.getIsCorrect());
		return response;

	}

	/**
	 * <pre>
	 * Password 찾기.
	 * </pre>
	 * 
	 * @param language
	 * @return SearchPasswordRes
	 */
	@Override
	public SearchPasswordRes searchPassword(SacRequestHeader header, SearchPasswordReq req) {

		ResetPasswordSellerResponse schRes = new ResetPasswordSellerResponse();
		ResetPasswordSellerRequest schReq = new ResetPasswordSellerRequest();

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID(req.getSellerId()); // 셀러 id 를 넣어야한다.

		schReq.setMbrPwd(mbrPwd);

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		schRes = this.sellerSCI.resetPasswordSeller(schReq);

		SearchPasswordRes response = new SearchPasswordRes();
		response.setNewPassword(schRes.getSellerPW());

		return response;

	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 조회.
	 * </pre>
	 * 
	 * @param language
	 * @return SearchAuthKeyRes
	 */
	@Override
	public DetailInformationRes searchAuthKey(SacRequestHeader header, SearchAuthKeyReq req) {

		SearchLoginInfoResponse schRes = new SearchLoginInfoResponse();
		SearchLoginInfoRequest schReq = new SearchLoginInfoRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSessionKey(req.getSessionKey());
		schRes = this.sellerSCI.searchLoginInfo(schReq);

		if (Integer.parseInt(req.getExtraDate()) > 0) {
			UpdateLoginInfoResponse schRes2 = new UpdateLoginInfoResponse();
			UpdateLoginInfoRequest schReq2 = new UpdateLoginInfoRequest();
			schReq2.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setSellerKey(schRes.getLoginInfo().getSellerKey());
			loginInfo.setSessionKey(req.getSessionKey());
			loginInfo.setExpireDate(this.getExpirationTime(Integer.parseInt(req.getExtraDate())));
			schReq2.setLoginInfo(loginInfo);
			schRes2 = this.sellerSCI.updateLoginInfo(schReq2);
		}

		SearchSellerResponse schRes3 = new SearchSellerResponse();
		SearchSellerRequest schReq3 = new SearchSellerRequest();
		schReq3.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INSD_SELLERMBR_NO");
		keySearch.setKeyString(schRes.getLoginInfo().getSellerKey());
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq3.setKeySearchList(list);

		schRes3 = this.sellerSCI.searchSeller(schReq3);

		DetailInformationRes response = new DetailInformationRes();

		// 판매자 멀티미디어정보
		List<ExtraRight> eList = new ArrayList<ExtraRight>();
		ExtraRight extraRightList = null;
		if (schRes3.getExtraRightList() != null)
			for (int i = 0; i < schRes3.getExtraRightList().size(); i++) {
				extraRightList = new ExtraRight();
				extraRightList.setEndDate(schRes3.getExtraRightList().get(i).getEndDate());
				extraRightList.setRegDate(schRes3.getExtraRightList().get(i).getRegDate());
				extraRightList.setRegID(schRes3.getExtraRightList().get(i).getRegID());
				extraRightList.setRightProfileCode(schRes3.getExtraRightList().get(i).getRightProfileCode());
				extraRightList.setSellerKey(schRes3.getExtraRightList().get(i).getSellerKey());
				extraRightList.setSellerRate(schRes3.getExtraRightList().get(i).getSellerRate());
				extraRightList.setStartDate(schRes3.getExtraRightList().get(i).getStartDate());
				extraRightList.setTenantID(schRes3.getExtraRightList().get(i).getTenantID());
				extraRightList.setTenantRate(schRes3.getExtraRightList().get(i).getTenantRate());
				extraRightList.setUpdateDate(schRes3.getExtraRightList().get(i).getUpdateDate());
				extraRightList.setUpdateID(schRes3.getExtraRightList().get(i).getUpdateID());
				eList.add(extraRightList);
			}

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (schRes3.getMbrLglAgent() != null) {
			mbrLglAgent.setMemberKey(schRes3.getMbrLglAgent().getMemberKey());
			mbrLglAgent.setParentBirthDay(schRes3.getMbrLglAgent().getParentBirthDay());
			mbrLglAgent.setParentCI(schRes3.getMbrLglAgent().getParentCI());
			mbrLglAgent.setParentDate(schRes3.getMbrLglAgent().getParentDate());
			mbrLglAgent.setParentEmail(schRes3.getMbrLglAgent().getParentEmail());
			mbrLglAgent.setParentMDN(schRes3.getMbrLglAgent().getParentMDN());
			mbrLglAgent.setParentName(schRes3.getMbrLglAgent().getParentName());
			mbrLglAgent.setParentRealNameDate(schRes3.getMbrLglAgent().getParentRealNameDate());
			mbrLglAgent.setParentRealNameMethod(schRes3.getMbrLglAgent().getParentRealNameMethod());
			mbrLglAgent.setParentRealNameSite(schRes3.getMbrLglAgent().getParentRealNameSite());
			mbrLglAgent.setParentTelecom(schRes3.getMbrLglAgent().getParentTelecom());
			mbrLglAgent.setParentType(schRes3.getMbrLglAgent().getParentType());
			mbrLglAgent.setSequence(schRes3.getMbrLglAgent().getSequence());
		}

		response.setExtraRightList(eList);// 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
		response.setSellerKey(schRes3.getSellerKey());// 판매자Key
		response.setSellerMbr(this.sellerMbr(schRes3.getSellerMbr()));// 판매자 정보

		return response;

	}

	/**
	 * <pre>
	 * 나라별 해회은행 정보 조회.
	 * </pre>
	 * 
	 * @param language
	 * @return ListBanksByCountryRes
	 */
	@Override
	public ListBanksByCountryRes listBanksByCountry(SacRequestHeader header) {

		ListBanksByCountryReq req = new ListBanksByCountryReq();
		req.setUseYn("Y");

		List<BanksByCountry> sList = this.commonDAO.queryForList("SellerSearch.listBanksByCountry", req,
				BanksByCountry.class);

		/*
		 * List<SecedeReson> sList = new ArrayList<SecedeReson>(); SecedeReson secedeReson = null; if (sellerDTO !=
		 * null) for (int i = 0; i < sellerDTO.size(); i++) { secedeReson = new SecedeReson();
		 * secedeReson.setSecedeReasonCode(sellerDTO.get(i).getSecedeReasonCode());
		 * secedeReson.setSecedeReasonMessage(sellerDTO.get(i).getSecedeReasonMessage()); sList.add(secedeReson); }
		 */

		ListBanksByCountryRes response = new ListBanksByCountryRes();
		response.setBanksByCountry(sList);

		return response;

	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private List<SellerMbr> sellerMbrList(
			List<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr> sellerMbr) {

		List<SellerMbr> sList = new ArrayList<SellerMbr>();
		SellerMbr sellerMbrRes = null;
		if (sellerMbr != null)
			for (int i = 0; i < sellerMbr.size(); i++) {
				sellerMbrRes = new SellerMbr();
				sellerMbrRes.setApproveDate(sellerMbr.get(i).getApproveDate());
				sellerMbrRes.setBizGrade(sellerMbr.get(i).getBizGrade());
				sellerMbrRes.setBizKindCd(sellerMbr.get(i).getBizKindCd());
				sellerMbrRes.setBizRegNumber(sellerMbr.get(i).getBizRegNumber());
				sellerMbrRes.setBizUnregReason(sellerMbr.get(i).getBizUnregReason());
				sellerMbrRes.setCeoBirthDay(sellerMbr.get(i).getCeoBirthDay());
				sellerMbrRes.setCeoName(sellerMbr.get(i).getCeoName());
				sellerMbrRes.setCharger(sellerMbr.get(i).getCharger());
				sellerMbrRes.setCordedTelephone(sellerMbr.get(i).getCordedTelephone());
				sellerMbrRes.setCordedTelephoneCountry(sellerMbr.get(i).getCordedTelephoneCountry());
				sellerMbrRes.setCustomerEmail(sellerMbr.get(i).getCustomerEmail());
				sellerMbrRes.setCustomerPhone(sellerMbr.get(i).getCustomerPhone());
				sellerMbrRes.setCustomerPhoneCountry(sellerMbr.get(i).getCustomerPhoneCountry());
				sellerMbrRes.setIsAccountReal(sellerMbr.get(i).getIsAccountReal());
				sellerMbrRes.setIsBizRegistered(sellerMbr.get(i).getIsBizRegistered());
				sellerMbrRes.setIsBizTaxable(sellerMbr.get(i).getIsBizTaxable());
				sellerMbrRes.setIsDeductible(sellerMbr.get(i).getIsDeductible());
				sellerMbrRes.setIsDomestic(sellerMbr.get(i).getIsDomestic());
				sellerMbrRes.setIsParent(sellerMbr.get(i).getIsParent());
				sellerMbrRes.setIsRealName(sellerMbr.get(i).getIsRealName());
				sellerMbrRes.setIsRecvEmail(sellerMbr.get(i).getIsRecvEmail());
				sellerMbrRes.setIsRecvSMS(sellerMbr.get(i).getIsRecvSMS());
				sellerMbrRes.setMarketCode(sellerMbr.get(i).getMarketCode());
				sellerMbrRes.setMarketStatus(sellerMbr.get(i).getMarketStatus());
				sellerMbrRes.setParentSellerKey(sellerMbr.get(i).getParentSellerKey());
				sellerMbrRes.setRegDate(sellerMbr.get(i).getRegDate());
				sellerMbrRes.setRepEmail(sellerMbr.get(i).getRepEmail());
				sellerMbrRes.setRepFax(sellerMbr.get(i).getRepFax());
				sellerMbrRes.setRepFaxArea(sellerMbr.get(i).getRepFaxArea());
				sellerMbrRes.setRepPhone(sellerMbr.get(i).getRepPhone());
				sellerMbrRes.setRepPhoneArea(sellerMbr.get(i).getRepPhoneArea());
				sellerMbrRes.setRightProfileList(sellerMbr.get(i).getRightProfileList());
				sellerMbrRes.setSecedeDate(sellerMbr.get(i).getSecedeDate());
				sellerMbrRes.setSecedePathCd(sellerMbr.get(i).getSecedePathCd());
				sellerMbrRes.setSecedeReasonCode(sellerMbr.get(i).getSecedeReasonCode());
				sellerMbrRes.setSecedeReasonMessage(sellerMbr.get(i).getSecedeReasonMessage());
				sellerMbrRes.setSellerAddress(sellerMbr.get(i).getSellerAddress());
				sellerMbrRes.setSellerBirthDay(sellerMbr.get(i).getSellerBirthDay());
				sellerMbrRes.setSellerBizCategory(sellerMbr.get(i).getSellerBizCategory());
				sellerMbrRes.setSellerBizCorpNumber(sellerMbr.get(i).getSellerBizCorpNumber());
				sellerMbrRes.setSellerBizNumber(sellerMbr.get(i).getSellerBizNumber());
				sellerMbrRes.setSellerBizType(sellerMbr.get(i).getSellerBizType());
				sellerMbrRes.setSellerCategory(sellerMbr.get(i).getSellerCategory());
				sellerMbrRes.setSellerCity(sellerMbr.get(i).getSellerCity());
				sellerMbrRes.setSellerClass(sellerMbr.get(i).getSellerClass());
				sellerMbrRes.setSellerCompany(sellerMbr.get(i).getSellerCompany());
				sellerMbrRes.setSellerCountry(sellerMbr.get(i).getSellerCountry());
				sellerMbrRes.setSellerDetailAddress(sellerMbr.get(i).getSellerDetailAddress());
				sellerMbrRes.setSellerEmail(sellerMbr.get(i).getSellerEmail());
				sellerMbrRes.setSellerId(sellerMbr.get(i).getSellerID());
				sellerMbrRes.setSellerKey(sellerMbr.get(i).getSellerKey());
				sellerMbrRes.setSellerLanguage(sellerMbr.get(i).getSellerLanguage());
				sellerMbrRes.setSellerMainStatus(sellerMbr.get(i).getSellerMainStatus());
				sellerMbrRes.setSellerName(sellerMbr.get(i).getSellerName());
				sellerMbrRes.setSellerNickName(sellerMbr.get(i).getSellerNickName());
				sellerMbrRes.setSellerPhone(sellerMbr.get(i).getSellerPhone());
				sellerMbrRes.setSellerPhoneCountry(sellerMbr.get(i).getSellerPhoneCountry());
				sellerMbrRes.setSellerSex(sellerMbr.get(i).getSellerSex());
				sellerMbrRes.setSellerSSNumber(sellerMbr.get(i).getSellerSSNumber());
				sellerMbrRes.setSellerState(sellerMbr.get(i).getSellerState());
				sellerMbrRes.setSellerSubStatus(sellerMbr.get(i).getSellerSubStatus());
				sellerMbrRes.setSellerTelecom(sellerMbr.get(i).getSellerTelecom());
				sellerMbrRes.setSellerZip(sellerMbr.get(i).getSellerZip());
				sellerMbrRes.setTenantID(sellerMbr.get(i).getTenantID());
				sellerMbrRes.setVendorCode(sellerMbr.get(i).getVendorCode());
				sList.add(sellerMbrRes);
			}

		return sList;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private SellerMbr sellerMbr(com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr) {
		// 판매자 정보
		SellerMbr sellerMbrRes = new SellerMbr();
		if (sellerMbr != null) {
			sellerMbrRes.setApproveDate(sellerMbr.getApproveDate());
			sellerMbrRes.setBizGrade(sellerMbr.getBizGrade());
			sellerMbrRes.setBizKindCd(sellerMbr.getBizKindCd());
			sellerMbrRes.setBizRegNumber(sellerMbr.getBizRegNumber());
			sellerMbrRes.setBizUnregReason(sellerMbr.getBizUnregReason());
			sellerMbrRes.setCeoBirthDay(sellerMbr.getCeoBirthDay());
			sellerMbrRes.setCeoName(sellerMbr.getCeoName());
			sellerMbrRes.setCharger(sellerMbr.getCharger());
			sellerMbrRes.setCordedTelephone(sellerMbr.getCordedTelephone());
			sellerMbrRes.setCordedTelephoneCountry(sellerMbr.getCordedTelephoneCountry());
			sellerMbrRes.setCustomerEmail(sellerMbr.getCustomerEmail());
			sellerMbrRes.setCustomerPhone(sellerMbr.getCustomerPhone());
			sellerMbrRes.setCustomerPhoneCountry(sellerMbr.getCustomerPhoneCountry());
			sellerMbrRes.setIsAccountReal(sellerMbr.getIsAccountReal());
			sellerMbrRes.setIsBizRegistered(sellerMbr.getIsBizRegistered());
			sellerMbrRes.setIsBizTaxable(sellerMbr.getIsBizTaxable());
			sellerMbrRes.setIsDeductible(sellerMbr.getIsDeductible());
			sellerMbrRes.setIsDomestic(sellerMbr.getIsDomestic());
			sellerMbrRes.setIsParent(sellerMbr.getIsParent());
			sellerMbrRes.setIsRealName(sellerMbr.getIsRealName());
			sellerMbrRes.setIsRecvEmail(sellerMbr.getIsRecvEmail());
			sellerMbrRes.setIsRecvSMS(sellerMbr.getIsRecvSMS());
			sellerMbrRes.setMarketCode(sellerMbr.getMarketCode());
			sellerMbrRes.setMarketStatus(sellerMbr.getMarketStatus());
			sellerMbrRes.setParentSellerKey(sellerMbr.getParentSellerKey());
			sellerMbrRes.setRegDate(sellerMbr.getRegDate());
			sellerMbrRes.setRepEmail(sellerMbr.getRepEmail());
			sellerMbrRes.setRepFax(sellerMbr.getRepFax());
			sellerMbrRes.setRepFaxArea(sellerMbr.getRepFaxArea());
			sellerMbrRes.setRepPhone(sellerMbr.getRepPhone());
			sellerMbrRes.setRepPhoneArea(sellerMbr.getRepPhoneArea());
			sellerMbrRes.setRightProfileList(sellerMbr.getRightProfileList());
			sellerMbrRes.setSecedeDate(sellerMbr.getSecedeDate());
			sellerMbrRes.setSecedePathCd(sellerMbr.getSecedePathCd());
			sellerMbrRes.setSecedeReasonCode(sellerMbr.getSecedeReasonCode());
			sellerMbrRes.setSecedeReasonMessage(sellerMbr.getSecedeReasonMessage());
			sellerMbrRes.setSellerAddress(sellerMbr.getSellerAddress());
			sellerMbrRes.setSellerBirthDay(sellerMbr.getSellerBirthDay());
			sellerMbrRes.setSellerBizCategory(sellerMbr.getSellerBizCategory());
			sellerMbrRes.setSellerBizCorpNumber(sellerMbr.getSellerBizCorpNumber());
			sellerMbrRes.setSellerBizNumber(sellerMbr.getSellerBizNumber());
			sellerMbrRes.setSellerBizType(sellerMbr.getSellerBizType());
			sellerMbrRes.setSellerCategory(sellerMbr.getSellerCategory());
			sellerMbrRes.setSellerCity(sellerMbr.getSellerCity());
			sellerMbrRes.setSellerClass(sellerMbr.getSellerClass());
			sellerMbrRes.setSellerCompany(sellerMbr.getSellerCompany());
			sellerMbrRes.setSellerCountry(sellerMbr.getSellerCountry());
			sellerMbrRes.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
			sellerMbrRes.setSellerEmail(sellerMbr.getSellerEmail());
			sellerMbrRes.setSellerId(sellerMbr.getSellerID());
			sellerMbrRes.setSellerKey(sellerMbr.getSellerKey());
			sellerMbrRes.setSellerLanguage(sellerMbr.getSellerLanguage());
			sellerMbrRes.setSellerMainStatus(sellerMbr.getSellerMainStatus());
			sellerMbrRes.setSellerName(sellerMbr.getSellerName());
			sellerMbrRes.setSellerNickName(sellerMbr.getSellerNickName());
			sellerMbrRes.setSellerPhone(sellerMbr.getSellerPhone());
			sellerMbrRes.setSellerPhoneCountry(sellerMbr.getSellerPhoneCountry());
			sellerMbrRes.setSellerSex(sellerMbr.getSellerSex());
			sellerMbrRes.setSellerSSNumber(sellerMbr.getSellerSSNumber());
			sellerMbrRes.setSellerState(sellerMbr.getSellerState());
			sellerMbrRes.setSellerSubStatus(sellerMbr.getSellerSubStatus());
			sellerMbrRes.setSellerTelecom(sellerMbr.getSellerTelecom());
			sellerMbrRes.setSellerZip(sellerMbr.getSellerZip());
			sellerMbrRes.setTenantID(sellerMbr.getTenantID());
			sellerMbrRes.setVendorCode(sellerMbr.getVendorCode());
		}
		return sellerMbrRes;
	}

	private String getExpirationTime(int hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hour);
		return sdf.format(cal.getTime());
	}

}
