package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRightList;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgreeList;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.SellerDTO;

@Service
public class SellerSearchServiceImpl implements SellerSearchService {

	private static final Logger logger = LoggerFactory.getLogger(SellerSearchServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req) {

		/** SC회원 시작 */
		/** 1. ID/Email Req 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** TODO 2. 임시 공통헤더 생성 주입 */
		checkDuplicationSellerRequest.setCommonRequest(this.imsiCommonRequest());

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
		if ("id".equals(req.getKeyType()))
			keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
		else if ("email".equals(req.getKeyType()))
			keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
		keySearch.setKeyString(req.getKeyString());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);

		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 3. SC회원(ID/Email중복) Call */
		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		// Response Debug
		logger.info("UpdateStatusSellerResponse Code : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultCode());
		logger.info("UpdateStatusSellerResponse Messge : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultMessage());

		/** 4. Tenant Response 생성 및 주입 */
		DuplicateByIdEmailRes response = new DuplicateByIdEmailRes(checkDuplicationSellerResponse.getIsRegistered());

		return response;
	}

	@Override
	public DetailInformationRes detailInformation(DetailInformationReq req) {

		SearchSellerResponse sc_res = new SearchSellerResponse();
		SearchSellerRequest sc_req = new SearchSellerRequest();

		sc_req.setCommonRequest(this.imsiCommonRequest());

		KeySearch keySearch = new KeySearch();

		if (req.getSellerKey().equals("") && !req.getAid().equals("")) {
			SellerDTO dto = new SellerDTO();
			dto.setSellerKey(req.getAid());
			SellerDTO sellerDTO = this.commonDAO.queryForObject("SellerSearch.sellerKey", dto, SellerDTO.class);
			keySearch.setKeyString(sellerDTO.getSellerKey());
		} else if (!req.getSellerKey().equals("")) {
			keySearch.setKeyString(req.getSellerKey());
		} else {
			keySearch.setKeyString("");
		}

		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		sc_req.setKeySearchList(list);

		sc_res = this.sellerSCI.searchSeller(sc_req);

		DetailInformationRes response = new DetailInformationRes();

		// 판매자 멀티미디어정보
		List<ExtraRightList> eList = new ArrayList<ExtraRightList>();
		for (int i = 0; i < sc_res.getExtraRightList().size(); i++) {
			ExtraRightList extraRightList = new ExtraRightList();
			extraRightList.setEndDate(sc_res.getExtraRightList().get(i).getEndDate());
			extraRightList.setRegDate(sc_res.getExtraRightList().get(i).getRegDate());
			extraRightList.setRegID(sc_res.getExtraRightList().get(i).getRegID());
			extraRightList.setRightProfileCode(sc_res.getExtraRightList().get(i).getRightProfileCode());
			extraRightList.setSellerKey(sc_res.getExtraRightList().get(i).getSellerKey());
			extraRightList.setSellerRate(sc_res.getExtraRightList().get(i).getSellerRate());
			extraRightList.setStartDate(sc_res.getExtraRightList().get(i).getStartDate());
			extraRightList.setTenantID(sc_res.getExtraRightList().get(i).getTenantID());
			extraRightList.setTenantRate(sc_res.getExtraRightList().get(i).getTenantRate());
			extraRightList.setUpdateDate(sc_res.getExtraRightList().get(i).getUpdateDate());
			extraRightList.setUpdateID(sc_res.getExtraRightList().get(i).getUpdateID());
			eList.add(extraRightList);
		}

		// 약관동의목록
		List<MbrClauseAgreeList> mList = new ArrayList<MbrClauseAgreeList>();
		for (int i = 0; i < sc_res.getMbrClauseAgreeList().size(); i++) {
			MbrClauseAgreeList mbrClauseAgreeList = new MbrClauseAgreeList();
			mbrClauseAgreeList.setExtraAgreementID(sc_res.getMbrClauseAgreeList().get(i).getExtraAgreementID());
			mbrClauseAgreeList.setExtraAgreementVersion(sc_res.getMbrClauseAgreeList().get(i)
					.getExtraAgreementVersion());
			mbrClauseAgreeList.setIsExtraAgreement(sc_res.getMbrClauseAgreeList().get(i).getIsExtraAgreement());
			mbrClauseAgreeList.setIsMandatory(sc_res.getMbrClauseAgreeList().get(i).getIsMandatory());
			mbrClauseAgreeList.setMemberKey(sc_res.getMbrClauseAgreeList().get(i).getMemberKey());
			mbrClauseAgreeList.setRegDate(sc_res.getMbrClauseAgreeList().get(i).getRegDate());
			mbrClauseAgreeList.setTenantID(sc_res.getMbrClauseAgreeList().get(i).getTenantID());
			mbrClauseAgreeList.setUpdateDate(sc_res.getMbrClauseAgreeList().get(i).getUpdateDate());
			mList.add(mbrClauseAgreeList);
		}

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setIsParent(sc_res.getMbrLglAgent().getIsParent());
		mbrLglAgent.setMemberKey(sc_res.getMbrLglAgent().getMemberKey());
		mbrLglAgent.setParentBirthDay(sc_res.getMbrLglAgent().getParentBirthDay());
		mbrLglAgent.setParentCI(sc_res.getMbrLglAgent().getParentCI());
		mbrLglAgent.setParentDate(sc_res.getMbrLglAgent().getParentDate());
		mbrLglAgent.setParentEmail(sc_res.getMbrLglAgent().getParentEmail());
		mbrLglAgent.setParentMDN(sc_res.getMbrLglAgent().getParentMDN());
		mbrLglAgent.setParentName(sc_res.getMbrLglAgent().getParentName());
		mbrLglAgent.setParentRealNameDate(sc_res.getMbrLglAgent().getParentRealNameDate());
		mbrLglAgent.setParentRealNameMethod(sc_res.getMbrLglAgent().getParentRealNameMethod());
		mbrLglAgent.setParentRealNameSite(sc_res.getMbrLglAgent().getParentRealNameSite());
		mbrLglAgent.setParentTelecom(sc_res.getMbrLglAgent().getParentTelecom());
		mbrLglAgent.setParentType(sc_res.getMbrLglAgent().getParentType());
		mbrLglAgent.setSequence(sc_res.getMbrLglAgent().getSequence());

		// 판매자 정보
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setApproveDate(sc_res.getSellerMbr().getApproveDate());
		sellerMbr.setBizGrade(sc_res.getSellerMbr().getBizGrade());
		sellerMbr.setBizKindCd(sc_res.getSellerMbr().getBizKindCd());
		sellerMbr.setBizRegNumber(sc_res.getSellerMbr().getBizRegNumber());
		sellerMbr.setBizUnregReason(sc_res.getSellerMbr().getBizUnregReason());
		sellerMbr.setCeoBirthDay(sc_res.getSellerMbr().getCeoBirthDay());
		sellerMbr.setCeoName(sc_res.getSellerMbr().getCeoName());
		sellerMbr.setCharger(sc_res.getSellerMbr().getCharger());
		sellerMbr.setCordedTelephone(sc_res.getSellerMbr().getCordedTelephone());
		sellerMbr.setCordedTelephoneCountry(sc_res.getSellerMbr().getCordedTelephoneCountry());
		sellerMbr.setCustomerEmail(sc_res.getSellerMbr().getCustomerEmail());
		sellerMbr.setCustomerPhone(sc_res.getSellerMbr().getCustomerPhone());
		sellerMbr.setCustomerPhoneCountry(sc_res.getSellerMbr().getCustomerPhoneCountry());
		sellerMbr.setIsAccountReal(sc_res.getSellerMbr().getIsAccountReal());
		sellerMbr.setIsBizRegistered(sc_res.getSellerMbr().getIsBizRegistered());
		sellerMbr.setIsBizTaxable(sc_res.getSellerMbr().getIsBizTaxable());
		sellerMbr.setIsDeductible(sc_res.getSellerMbr().getIsDeductible());
		sellerMbr.setIsForeign(sc_res.getSellerMbr().getIsForeign());
		sellerMbr.setIsParent(sc_res.getSellerMbr().getIsParent());
		sellerMbr.setIsRealName(sc_res.getSellerMbr().getIsRealName());
		sellerMbr.setIsRecvEmail(sc_res.getSellerMbr().getIsRecvEmail());
		sellerMbr.setIsRecvSMS(sc_res.getSellerMbr().getIsRecvSMS());
		sellerMbr.setMarketCode(sc_res.getSellerMbr().getMarketCode());
		sellerMbr.setMarketStatus(sc_res.getSellerMbr().getMarketStatus());
		sellerMbr.setMemo(sc_res.getSellerMbr().getMemo());
		sellerMbr.setParentSellerKey(sc_res.getSellerMbr().getParentSellerKey());
		sellerMbr.setRegDate(sc_res.getSellerMbr().getRegDate());
		sellerMbr.setRepEmail(sc_res.getSellerMbr().getRepEmail());
		sellerMbr.setRepFax(sc_res.getSellerMbr().getRepFax());
		sellerMbr.setRepFaxArea(sc_res.getSellerMbr().getRepFaxArea());
		sellerMbr.setRepPhone(sc_res.getSellerMbr().getRepPhone());
		sellerMbr.setRepPhoneArea(sc_res.getSellerMbr().getRepPhoneArea());
		sellerMbr.setRightProfileList(sc_res.getSellerMbr().getRightProfileList());
		sellerMbr.setSecedeDate(sc_res.getSellerMbr().getSecedeDate());
		sellerMbr.setSecedePathCd(sc_res.getSellerMbr().getSecedePathCd());
		sellerMbr.setSecedeReasonCode(sc_res.getSellerMbr().getSecedeReasonCode());
		sellerMbr.setSecedeReasonMessage(sc_res.getSellerMbr().getSecedeReasonMessage());
		sellerMbr.setSellerAddress(sc_res.getSellerMbr().getSellerAddress());
		sellerMbr.setSellerBirthDay(sc_res.getSellerMbr().getSellerBirthDay());
		sellerMbr.setSellerBizCategory(sc_res.getSellerMbr().getSellerBizCategory());
		sellerMbr.setSellerBizCorpNumber(sc_res.getSellerMbr().getSellerBizCorpNumber());
		sellerMbr.setSellerBizNumber(sc_res.getSellerMbr().getSellerBizNumber());
		sellerMbr.setSellerBizType(sc_res.getSellerMbr().getSellerBizType());
		sellerMbr.setSellerCategory(sc_res.getSellerMbr().getSellerCategory());
		sellerMbr.setSellerCity(sc_res.getSellerMbr().getSellerCity());
		sellerMbr.setSellerClass(sc_res.getSellerMbr().getSellerClass());
		sellerMbr.setSellerCompany(sc_res.getSellerMbr().getSellerCompany());
		sellerMbr.setSellerCountry(sc_res.getSellerMbr().getSellerCountry());
		sellerMbr.setSellerDetailAddress(sc_res.getSellerMbr().getSellerDetailAddress());
		sellerMbr.setSellerEmail(sc_res.getSellerMbr().getSellerEmail());
		sellerMbr.setSellerID(sc_res.getSellerMbr().getSellerID());
		sellerMbr.setSellerKey(sc_res.getSellerMbr().getSellerKey());
		sellerMbr.setSellerLanguage(sc_res.getSellerMbr().getSellerLanguage());
		sellerMbr.setSellerMainStatus(sc_res.getSellerMbr().getSellerMainStatus());
		sellerMbr.setSellerName(sc_res.getSellerMbr().getSellerName());
		sellerMbr.setSellerNickName(sc_res.getSellerMbr().getSellerNickName());
		sellerMbr.setSellerPhone(sc_res.getSellerMbr().getSellerPhone());
		sellerMbr.setSellerPhoneCountry(sc_res.getSellerMbr().getSellerPhoneCountry());
		sellerMbr.setSellerSex(sc_res.getSellerMbr().getSellerSex());
		sellerMbr.setSellerSSNumber(sc_res.getSellerMbr().getSellerSSNumber());
		sellerMbr.setSellerState(sc_res.getSellerMbr().getSellerState());
		sellerMbr.setSellerSubStatus(sc_res.getSellerMbr().getSellerSubStatus());
		sellerMbr.setSellerTelecom(sc_res.getSellerMbr().getSellerTelecom());
		sellerMbr.setSellerZip(sc_res.getSellerMbr().getSellerZip());
		sellerMbr.setTenantID(sc_res.getSellerMbr().getTenantID());
		sellerMbr.setVendorCode(sc_res.getSellerMbr().getVendorCode());

		response.setExtraRightList(eList);// 판매자 멀티미디어정보
		response.setMbrClauseAgree(mList);// 약관동의목록
		response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
		response.setSellerMbr(sellerMbr);// 판매자 정보
		response.setSellerKey(sc_res.getSellerKey());// 판매자Key

		return response;

	}

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더
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
