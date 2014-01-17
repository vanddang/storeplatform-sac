package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgree;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.SellerDTO;

@Service
@Transactional
public class SellerSearchServiceImpl implements SellerSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSearchServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public DuplicateByIdEmailRes duplicateByIdEmail(SacRequestHeader header, DuplicateByIdEmailReq req) {

		/** SC회원 시작 */
		/** 1. ID/Email Req 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			checkDuplicationSellerRequest.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			checkDuplicationSellerRequest.setCommonRequest(commonRequest);
		}

		/**
		 * 검색 조건 타입 <br>
		 * INSD_USERMBR_NO : 내부 사용자 키 <br>
		 * MBR_ID : 사용자 ID <br>
		 * INSD_SELLERMBR_NO : 내부 판매자 키 <br>
		 * SELLERMBR_ID : 판매자 ID <br>
		 * USERMBR_NO : 통합서비스 키 <br>
		 * INSD_DEVICE_ID : 내부 기기 키 <br>
		 * DEVICE_ID : 기기 ID <br>
		 * EMAIL_ADDR : 사용자 이메일 <br>
		 * EMAIL : 판매자 이메일 <br>
		 * TEL_NO : 사용자 연락처 <br>
		 * WILS_TEL_NO : 판매자 연락처
		 */
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

		/** 3. SC회원(ID/Email중복) Call */
		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		// Response Debug
		LOGGER.info("checkDuplicationSellerResponse Code : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultCode());
		LOGGER.info("checkDuplicationSellerResponse Messge : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultMessage());

		// TODO Exception 재정의 - 결과 값 성공(0000)이 아니면 던져~~~
		if (!MemberConstants.RESULT_SUCCES.equals(checkDuplicationSellerResponse.getCommonResponse().getResultCode())) {
			throw new RuntimeException(checkDuplicationSellerResponse.getCommonResponse().getResultMessage());
		}

		/** 4. TenantRes Response 생성 및 주입 */
		DuplicateByIdEmailRes response = new DuplicateByIdEmailRes(checkDuplicationSellerResponse.getIsRegistered());

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
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req) throws Exception {

		SearchSellerResponse schRes = new SearchSellerResponse();
		SearchSellerRequest schReq = new SearchSellerRequest();

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}

		KeySearch keySearch = new KeySearch();
		if (!req.getSellerKey().equals("") || !req.getAid().equals("")) {
			if (!req.getSellerKey().equals("")) {
				keySearch.setKeyString(req.getSellerKey());
			} else {
				SellerDTO dto = new SellerDTO();
				dto.setSellerKey(req.getAid());
				SellerDTO sellerDTO = this.commonDAO.queryForObject("SellerSearch.sellerKey", dto, SellerDTO.class);
				keySearch.setKeyString(sellerDTO.getSellerKey());
			}
		} else {
			keySearch.setKeyString("");
		}

		DetailInformationRes response = new DetailInformationRes();
		if (keySearch.getKeyString() != null)
			if (!keySearch.getKeyString().equals("")) {

				keySearch.setKeyType(req.getKeyType());
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

				// 약관동의목록
				List<MbrClauseAgree> mList = new ArrayList<MbrClauseAgree>();
				MbrClauseAgree mbrClauseAgreeList = null;
				if (schRes.getMbrClauseAgreeList() != null)
					for (int i = 0; i < schRes.getMbrClauseAgreeList().size(); i++) {
						mbrClauseAgreeList = new MbrClauseAgree();
						mbrClauseAgreeList.setExtraAgreementID(schRes.getMbrClauseAgreeList().get(i)
								.getExtraAgreementID());
						mbrClauseAgreeList.setExtraAgreementVersion(schRes.getMbrClauseAgreeList().get(i)
								.getExtraAgreementVersion());
						mbrClauseAgreeList.setIsExtraAgreement(schRes.getMbrClauseAgreeList().get(i)
								.getIsExtraAgreement());
						mbrClauseAgreeList.setIsMandatory(schRes.getMbrClauseAgreeList().get(i).getIsMandatory());
						mbrClauseAgreeList.setMemberKey(schRes.getMbrClauseAgreeList().get(i).getMemberKey());
						mbrClauseAgreeList.setRegDate(schRes.getMbrClauseAgreeList().get(i).getRegDate());
						mbrClauseAgreeList.setTenantID(schRes.getMbrClauseAgreeList().get(i).getTenantID());
						mbrClauseAgreeList.setUpdateDate(schRes.getMbrClauseAgreeList().get(i).getUpdateDate());
						mList.add(mbrClauseAgreeList);
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
				response.setMbrClauseAgreeList(mList);// 약관동의목록
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
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req)
			throws Exception {

		SearchAccountSellerResponse schRes = new SearchAccountSellerResponse();
		SearchAccountSellerRequest schReq = new SearchAccountSellerRequest();
		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}
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
				extraRightList.setEndDate(schRes.getExtraRight().get(i).getEndDate());
				extraRightList.setRegDate(schRes.getExtraRight().get(i).getRegDate());
				extraRightList.setRegID(schRes.getExtraRight().get(i).getRegID());
				extraRightList.setRightProfileCode(schRes.getExtraRight().get(i).getRightProfileCode());
				extraRightList.setSellerKey(schRes.getExtraRight().get(i).getSellerKey());
				extraRightList.setSellerRate(schRes.getExtraRight().get(i).getSellerRate());
				extraRightList.setStartDate(schRes.getExtraRight().get(i).getStartDate());
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
			sellerAccount.setEndDate(schRes.getSellerAccount().getEndDate());
			sellerAccount.setIbanCode(schRes.getSellerAccount().getIbanCode());
			sellerAccount.setIsUsed(schRes.getSellerAccount().getIsUsed());
			sellerAccount.setReason(schRes.getSellerAccount().getReason());
			sellerAccount.setSellerKey(schRes.getSellerAccount().getSellerKey());
			sellerAccount.setStartDate(schRes.getSellerAccount().getStartDate());
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
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header, String language) throws Exception {

		SellerDTO dto = new SellerDTO();
		dto.setKoUsWhether(language);

		List<SellerDTO> sellerDTO = this.commonDAO.queryForList("SellerSearch.listWithdrawalReason", dto,
				SellerDTO.class);

		List<SecedeReson> sList = new ArrayList<SecedeReson>();
		SecedeReson secedeReson = null;
		if (sellerDTO != null)
			for (int i = 0; i < sellerDTO.size(); i++) {
				secedeReson = new SecedeReson();
				secedeReson.setSecedeReasonCode(sellerDTO.get(i).getSecedeReasonCode());
				secedeReson.setSecedeReasonMessage(sellerDTO.get(i).getSecedeReasonMessage());
				sList.add(secedeReson);
			}

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
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req) throws Exception {

		SearchIDSellerResponse schRes = new SearchIDSellerResponse();
		SearchIDSellerRequest schReq = new SearchIDSellerRequest();
		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}

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

		SearchIdRes response = new SearchIdRes();
		response.setSellerMbr(this.sellerMbrList(schRes.getSellerMbr()));// 판매자 정보 리스트

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
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header, String language)
			throws Exception {

		SearchPwdHintListResponse schRes = new SearchPwdHintListResponse();
		SearchPwdHintListRequest schReq = new SearchPwdHintListRequest();

		schReq.setLanguageCode(language);
		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}

		schRes = this.sellerSCI.searchPwdHintList(schReq);

		List<SellerMbrPwdHint> sList = new ArrayList<SellerMbrPwdHint>();
		SellerMbrPwdHint sellerMbrPwdHint = null;
		if (schRes.getSellerMbrPwdHintList() != null)
			for (int i = 0; i < schRes.getSellerMbrPwdHintList().size(); i++) {
				sellerMbrPwdHint = new SellerMbrPwdHint();
				sellerMbrPwdHint.setDisplayOrder(schRes.getSellerMbrPwdHintList().get(i).getDisplayOrder());
				sellerMbrPwdHint.setIsDisplay(schRes.getSellerMbrPwdHintList().get(i).getIsDisplay());
				sellerMbrPwdHint.setLanguageCode(schRes.getSellerMbrPwdHintList().get(i).getLanguageCode());
				sellerMbrPwdHint.setQuestionDescription(schRes.getSellerMbrPwdHintList().get(i)
						.getQuestionDescription());
				sellerMbrPwdHint.setQuestionID(schRes.getSellerMbrPwdHintList().get(i).getQuestionID());
				sellerMbrPwdHint.setQuestionName(schRes.getSellerMbrPwdHintList().get(i).getQuestionName());
				sellerMbrPwdHint.setRegDate(schRes.getSellerMbrPwdHintList().get(i).getRegDate());
				sellerMbrPwdHint.setRegID(schRes.getSellerMbrPwdHintList().get(i).getRegID());
				sellerMbrPwdHint.setUpdateDate(schRes.getSellerMbrPwdHintList().get(i).getUpdateDate());
				sellerMbrPwdHint.setUpdateID(schRes.getSellerMbrPwdHintList().get(i).getUpdateID());
				sList.add(sellerMbrPwdHint);
			}

		ListPasswordReminderQuestionRes response = new ListPasswordReminderQuestionRes();
		response.setSellerMbrPwdHintList(sList);
		response.setLanguageCode(schRes.getLanguageCode());

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
				sellerMbrRes.setSellerID(sellerMbr.get(i).getSellerID());
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
			sellerMbrRes.setSellerID(sellerMbr.getSellerID());
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

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더.
	 * </pre>
	 * 
	 * @return
	 */
	private CommonRequest imsiCommonRequest() {
		/** TODO 임시 공통헤더 생성 주입 */
		CommonRequest commonRequest = new CommonRequest();
		// S001(ShopClient), S002(WEB), S003(OpenAPI)
		commonRequest.setSystemID("S001");
		// TODO SC회원 문의?? - Reamine ID생성 규칙과 다름
		// T01(T-Store), T02(A-Store), T03(B-Store) - ['S01' 데이터로 마이그레이션 작업 할 예정]
		commonRequest.setTenantID("S01");
		return commonRequest;
	}
}
