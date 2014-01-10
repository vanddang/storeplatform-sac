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

		SearchSellerResponse result = new SearchSellerResponse();
		SearchSellerRequest request = new SearchSellerRequest();

		request.setCommonRequest(this.imsiCommonRequest());

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
		request.setKeySearchList(list);

		result = this.sellerSCI.searchSeller(request);

		DetailInformationRes response = new DetailInformationRes();
		/*
		 * List<ExtraRight> eList = new ArrayList<ExtraRight>(); for (int i = 0; i < result.getExtraRight().size(); i++)
		 * { ExtraRight extraRight = new ExtraRight();
		 * extraRight.setEndDate(result.getExtraRight().get(i).getEndDate());
		 * extraRight.setRegDate(result.getExtraRight().get(i).getRegDate());
		 * extraRight.setRegID(result.getExtraRight().get(i).getRegID());
		 * extraRight.setRightProfileCode(result.getExtraRight().get(i).getRightProfileCode());
		 * extraRight.setSellerKey(result.getExtraRight().get(i).getSellerKey());
		 * extraRight.setSellerRate(result.getExtraRight().get(i).getSellerRate());
		 * extraRight.setStartDate(result.getExtraRight().get(i).getStartDate());
		 * extraRight.setTenantID(result.getExtraRight().get(i).getTenantID());
		 * extraRight.setTenantRate(result.getExtraRight().get(i).getTenantRate());
		 * extraRight.setUpdateDate(result.getExtraRight().get(i).getUpdateDate());
		 * extraRight.setUpdateID(result.getExtraRight().get(i).getUpdateID()); eList.add(extraRight); }
		 * 
		 * List<MbrClauseAgree> mList = new ArrayList<MbrClauseAgree>(); for (int i = 0; i <
		 * result.getMbrClauseAgree().size(); i++) { MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
		 * mbrClauseAgree.setExtraAgreementID(result.getMbrClauseAgree().get(i).getExtraAgreementID());
		 * mbrClauseAgree.setExtraAgreementVersion(result.getMbrClauseAgree().get(i).getExtraAgreementVersion());
		 * mbrClauseAgree.setIsExtraAgreement(result.getMbrClauseAgree().get(i).getIsExtraAgreement());
		 * mbrClauseAgree.setIsMandatory(result.getMbrClauseAgree().get(i).getIsMandatory());
		 * mbrClauseAgree.setMemberKey(result.getMbrClauseAgree().get(i).getMemberKey());
		 * mbrClauseAgree.setRegDate(result.getMbrClauseAgree().get(i).getRegDate());
		 * mbrClauseAgree.setTenantID(result.getMbrClauseAgree().get(i).getTenantID());
		 * mbrClauseAgree.setUpdateDate(result.getMbrClauseAgree().get(i).getUpdateDate()); mList.add(mbrClauseAgree); }
		 */

		MbrLglAgent mbrLglAgent = new MbrLglAgent();

		/*
		 * mbrLglAgent.setIsParent(result.getMbrLglAgent().getIsParent());
		 * mbrLglAgent.setMemberKey(result.getMbrLglAgent().getMemberKey());
		 * mbrLglAgent.setParentBirthDay(result.getMbrLglAgent().getParentBirthDay());
		 * mbrLglAgent.setParentCI(result.getMbrLglAgent().getParentCI());
		 * mbrLglAgent.setParentDate(result.getMbrLglAgent().getParentDate());
		 * mbrLglAgent.setParentEmail(result.getMbrLglAgent().getParentEmail());
		 * mbrLglAgent.setParentMDN(result.getMbrLglAgent().getParentMDN());
		 * mbrLglAgent.setParentName(result.getMbrLglAgent().getParentName());
		 * mbrLglAgent.setParentRealNameDate(result.getMbrLglAgent().getParentRealNameDate());
		 * mbrLglAgent.setParentRealNameMethod(result.getMbrLglAgent().getParentRealNameMethod());
		 * mbrLglAgent.setParentRealNameSite(result.getMbrLglAgent().getParentRealNameSite());
		 * mbrLglAgent.setParentTelecom(result.getMbrLglAgent().getParentTelecom());
		 * mbrLglAgent.setParentType(result.getMbrLglAgent().getParentType());
		 * mbrLglAgent.setSequence(result.getMbrLglAgent().getSequence());
		 */

		SellerMbr sellerMbr = new SellerMbr();
		/*
		 * sellerMbr.setApproveDate(result.getSellerMbr().getApproveDate());
		 * sellerMbr.setBizGrade(result.getSellerMbr().getBizGrade());
		 * sellerMbr.setBizKindCd(result.getSellerMbr().getBizKindCd());
		 * sellerMbr.setBizRegNumber(result.getSellerMbr().getBizRegNumber());
		 * sellerMbr.setBizUnregReason(result.getSellerMbr().getBizUnregReason());
		 * sellerMbr.setCeoBirthDay(result.getSellerMbr().getCeoBirthDay());
		 * sellerMbr.setCeoName(result.getSellerMbr().getCeoName());
		 * sellerMbr.setCharger(result.getSellerMbr().getCharger());
		 * sellerMbr.setCordedTelephone(result.getSellerMbr().getCordedTelephone());
		 * sellerMbr.setCordedTelephoneCountry(result.getSellerMbr().getCordedTelephoneCountry());
		 * sellerMbr.setCustomerEmail(result.getSellerMbr().getCustomerEmail());
		 * sellerMbr.setCustomerPhone(result.getSellerMbr().getCustomerPhone());
		 * sellerMbr.setCustomerPhoneCountry(result.getSellerMbr().getCustomerPhoneCountry());
		 * sellerMbr.setIsAccountReal(result.getSellerMbr().getIsAccountReal());
		 * sellerMbr.setIsBizRegistered(result.getSellerMbr().getIsBizRegistered());
		 * sellerMbr.setIsBizTaxable(result.getSellerMbr().getIsBizTaxable());
		 * sellerMbr.setIsDeductible(result.getSellerMbr().getIsDeductible());
		 * sellerMbr.setIsForeign(result.getSellerMbr().getIsForeign());
		 * sellerMbr.setIsParent(result.getSellerMbr().getIsParent());
		 * sellerMbr.setIsRealName(result.getSellerMbr().getIsRealName());
		 * sellerMbr.setIsRecvEmail(result.getSellerMbr().getIsRecvEmail());
		 * sellerMbr.setIsRecvSMS(result.getSellerMbr().getIsRecvSMS());
		 * sellerMbr.setMarketCode(result.getSellerMbr().getMarketCode());
		 * sellerMbr.setMarketStatus(result.getSellerMbr().getMarketStatus());
		 * sellerMbr.setMemo(result.getSellerMbr().getMemo());
		 * sellerMbr.setParentSellerKey(result.getSellerMbr().getParentSellerKey());
		 * sellerMbr.setRegDate(result.getSellerMbr().getRegDate());
		 * sellerMbr.setRepEmail(result.getSellerMbr().getRepEmail());
		 * sellerMbr.setRepFax(result.getSellerMbr().getRepFax());
		 * sellerMbr.setRepFaxArea(result.getSellerMbr().getRepFaxArea());
		 * sellerMbr.setRepPhone(result.getSellerMbr().getRepPhone());
		 * sellerMbr.setRepPhoneArea(result.getSellerMbr().getRepPhoneArea());
		 * sellerMbr.setRightProfileList(result.getSellerMbr().getRightProfileList());
		 * sellerMbr.setSecedeDate(result.getSellerMbr().getSecedeDate());
		 * sellerMbr.setSecedePathCd(result.getSellerMbr().getSecedePathCd());
		 * sellerMbr.setSecedeReasonCode(result.getSellerMbr().getSecedeReasonCode());
		 * sellerMbr.setSecedeReasonMessage(result.getSellerMbr().getSecedeReasonMessage());
		 * sellerMbr.setSellerAddress(result.getSellerMbr().getSellerAddress());
		 * sellerMbr.setSellerBirthDay(result.getSellerMbr().getSellerBirthDay());
		 * sellerMbr.setSellerBizCategory(result.getSellerMbr().getSellerBizCategory());
		 * sellerMbr.setSellerBizCorpNumber(result.getSellerMbr().getSellerBizCorpNumber());
		 * sellerMbr.setSellerBizNumber(result.getSellerMbr().getSellerBizNumber());
		 * sellerMbr.setSellerBizType(result.getSellerMbr().getSellerBizType());
		 * sellerMbr.setSellerCategory(result.getSellerMbr().getSellerCategory());
		 * sellerMbr.setSellerCity(result.getSellerMbr().getSellerCity());
		 * sellerMbr.setSellerClass(result.getSellerMbr().getSellerClass());
		 * sellerMbr.setSellerCompany(result.getSellerMbr().getSellerCompany());
		 * sellerMbr.setSellerCountry(result.getSellerMbr().getSellerCountry());
		 * sellerMbr.setSellerDetailAddress(result.getSellerMbr().getSellerDetailAddress());
		 * sellerMbr.setSellerEmail(result.getSellerMbr().getSellerEmail());
		 * sellerMbr.setSellerID(result.getSellerMbr().getSellerID());
		 * sellerMbr.setSellerKey(result.getSellerMbr().getSellerKey());
		 * sellerMbr.setSellerLanguage(result.getSellerMbr().getSellerLanguage());
		 * sellerMbr.setSellerMainStatus(result.getSellerMbr().getSellerMainStatus());
		 * sellerMbr.setSellerName(result.getSellerMbr().getSellerName());
		 * sellerMbr.setSellerNickName(result.getSellerMbr().getSellerNickName());
		 * sellerMbr.setSellerPhone(result.getSellerMbr().getSellerPhone());
		 * sellerMbr.setSellerPhoneCountry(result.getSellerMbr().getSellerPhoneCountry());
		 * sellerMbr.setSellerSex(result.getSellerMbr().getSellerSex());
		 * sellerMbr.setSellerSSNumber(result.getSellerMbr().getSellerSSNumber());
		 * sellerMbr.setSellerState(result.getSellerMbr().getSellerState());
		 * sellerMbr.setSellerSubStatus(result.getSellerMbr().getSellerSubStatus());
		 * sellerMbr.setSellerTelecom(result.getSellerMbr().getSellerTelecom());
		 * sellerMbr.setSellerZip(result.getSellerMbr().getSellerZip());
		 * sellerMbr.setTenantID(result.getSellerMbr().getTenantID());
		 * sellerMbr.setVendorCode(result.getSellerMbr().getVendorCode());
		 */

		// response.setExtraRight(eList);// 판매자 멀티미디어정보
		// response.setMbrClauseAgree(mList);// 약관동의목록
		response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
		response.setSellerMbr(sellerMbr);// 판매자 정보
		response.setSellerKey(result.getSellerKey());// 판매자Key

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
