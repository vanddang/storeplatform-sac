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

		SearchSellerResponse schRes = new SearchSellerResponse();
		SearchSellerRequest schReq = new SearchSellerRequest();

		schReq.setCommonRequest(this.imsiCommonRequest());

		DetailInformationRes response = new DetailInformationRes();
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

		if (keySearch.getKeyString() != null)
			if (!keySearch.getKeyString().equals("")) {

				keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
				List<KeySearch> list = new ArrayList<KeySearch>();
				list.add(keySearch);
				schReq.setKeySearchList(list);

				schRes = this.sellerSCI.searchSeller(schReq);

				// 판매자 멀티미디어정보
				List<ExtraRightList> eList = new ArrayList<ExtraRightList>();
				for (int i = 0; i < schRes.getExtraRightList().size(); i++) {
					ExtraRightList extraRightList = new ExtraRightList();
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
				List<MbrClauseAgreeList> mList = new ArrayList<MbrClauseAgreeList>();
				for (int i = 0; i < schRes.getMbrClauseAgreeList().size(); i++) {
					MbrClauseAgreeList mbrClauseAgreeList = new MbrClauseAgreeList();
					mbrClauseAgreeList.setExtraAgreementID(schRes.getMbrClauseAgreeList().get(i).getExtraAgreementID());
					mbrClauseAgreeList.setExtraAgreementVersion(schRes.getMbrClauseAgreeList().get(i)
							.getExtraAgreementVersion());
					mbrClauseAgreeList.setIsExtraAgreement(schRes.getMbrClauseAgreeList().get(i).getIsExtraAgreement());
					mbrClauseAgreeList.setIsMandatory(schRes.getMbrClauseAgreeList().get(i).getIsMandatory());
					mbrClauseAgreeList.setMemberKey(schRes.getMbrClauseAgreeList().get(i).getMemberKey());
					mbrClauseAgreeList.setRegDate(schRes.getMbrClauseAgreeList().get(i).getRegDate());
					mbrClauseAgreeList.setTenantID(schRes.getMbrClauseAgreeList().get(i).getTenantID());
					mbrClauseAgreeList.setUpdateDate(schRes.getMbrClauseAgreeList().get(i).getUpdateDate());
					mList.add(mbrClauseAgreeList);
				}

				// 법정대리인정보
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setIsParent(schRes.getMbrLglAgent().getIsParent());
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

				// 판매자 정보
				SellerMbr sellerMbr = new SellerMbr();
				sellerMbr.setApproveDate(schRes.getSellerMbr().getApproveDate());
				sellerMbr.setBizGrade(schRes.getSellerMbr().getBizGrade());
				sellerMbr.setBizKindCd(schRes.getSellerMbr().getBizKindCd());
				sellerMbr.setBizRegNumber(schRes.getSellerMbr().getBizRegNumber());
				sellerMbr.setBizUnregReason(schRes.getSellerMbr().getBizUnregReason());
				sellerMbr.setCeoBirthDay(schRes.getSellerMbr().getCeoBirthDay());
				sellerMbr.setCeoName(schRes.getSellerMbr().getCeoName());
				sellerMbr.setCharger(schRes.getSellerMbr().getCharger());
				sellerMbr.setCordedTelephone(schRes.getSellerMbr().getCordedTelephone());
				sellerMbr.setCordedTelephoneCountry(schRes.getSellerMbr().getCordedTelephoneCountry());
				sellerMbr.setCustomerEmail(schRes.getSellerMbr().getCustomerEmail());
				sellerMbr.setCustomerPhone(schRes.getSellerMbr().getCustomerPhone());
				sellerMbr.setCustomerPhoneCountry(schRes.getSellerMbr().getCustomerPhoneCountry());
				sellerMbr.setIsAccountReal(schRes.getSellerMbr().getIsAccountReal());
				sellerMbr.setIsBizRegistered(schRes.getSellerMbr().getIsBizRegistered());
				sellerMbr.setIsBizTaxable(schRes.getSellerMbr().getIsBizTaxable());
				sellerMbr.setIsDeductible(schRes.getSellerMbr().getIsDeductible());
				sellerMbr.setIsForeign(schRes.getSellerMbr().getIsForeign());
				sellerMbr.setIsParent(schRes.getSellerMbr().getIsParent());
				sellerMbr.setIsRealName(schRes.getSellerMbr().getIsRealName());
				sellerMbr.setIsRecvEmail(schRes.getSellerMbr().getIsRecvEmail());
				sellerMbr.setIsRecvSMS(schRes.getSellerMbr().getIsRecvSMS());
				sellerMbr.setMarketCode(schRes.getSellerMbr().getMarketCode());
				sellerMbr.setMarketStatus(schRes.getSellerMbr().getMarketStatus());
				sellerMbr.setParentSellerKey(schRes.getSellerMbr().getParentSellerKey());
				sellerMbr.setRegDate(schRes.getSellerMbr().getRegDate());
				sellerMbr.setRepEmail(schRes.getSellerMbr().getRepEmail());
				sellerMbr.setRepFax(schRes.getSellerMbr().getRepFax());
				sellerMbr.setRepFaxArea(schRes.getSellerMbr().getRepFaxArea());
				sellerMbr.setRepPhone(schRes.getSellerMbr().getRepPhone());
				sellerMbr.setRepPhoneArea(schRes.getSellerMbr().getRepPhoneArea());
				sellerMbr.setRightProfileList(schRes.getSellerMbr().getRightProfileList());
				sellerMbr.setSecedeDate(schRes.getSellerMbr().getSecedeDate());
				sellerMbr.setSecedePathCd(schRes.getSellerMbr().getSecedePathCd());
				sellerMbr.setSecedeReasonCode(schRes.getSellerMbr().getSecedeReasonCode());
				sellerMbr.setSecedeReasonMessage(schRes.getSellerMbr().getSecedeReasonMessage());
				sellerMbr.setSellerAddress(schRes.getSellerMbr().getSellerAddress());
				sellerMbr.setSellerBirthDay(schRes.getSellerMbr().getSellerBirthDay());
				sellerMbr.setSellerBizCategory(schRes.getSellerMbr().getSellerBizCategory());
				sellerMbr.setSellerBizCorpNumber(schRes.getSellerMbr().getSellerBizCorpNumber());
				sellerMbr.setSellerBizNumber(schRes.getSellerMbr().getSellerBizNumber());
				sellerMbr.setSellerBizType(schRes.getSellerMbr().getSellerBizType());
				sellerMbr.setSellerCategory(schRes.getSellerMbr().getSellerCategory());
				sellerMbr.setSellerCity(schRes.getSellerMbr().getSellerCity());
				sellerMbr.setSellerClass(schRes.getSellerMbr().getSellerClass());
				sellerMbr.setSellerCompany(schRes.getSellerMbr().getSellerCompany());
				sellerMbr.setSellerCountry(schRes.getSellerMbr().getSellerCountry());
				sellerMbr.setSellerDetailAddress(schRes.getSellerMbr().getSellerDetailAddress());
				sellerMbr.setSellerEmail(schRes.getSellerMbr().getSellerEmail());
				sellerMbr.setSellerID(schRes.getSellerMbr().getSellerID());
				sellerMbr.setSellerKey(schRes.getSellerMbr().getSellerKey());
				sellerMbr.setSellerLanguage(schRes.getSellerMbr().getSellerLanguage());
				sellerMbr.setSellerMainStatus(schRes.getSellerMbr().getSellerMainStatus());
				sellerMbr.setSellerName(schRes.getSellerMbr().getSellerName());
				sellerMbr.setSellerNickName(schRes.getSellerMbr().getSellerNickName());
				sellerMbr.setSellerPhone(schRes.getSellerMbr().getSellerPhone());
				sellerMbr.setSellerPhoneCountry(schRes.getSellerMbr().getSellerPhoneCountry());
				sellerMbr.setSellerSex(schRes.getSellerMbr().getSellerSex());
				sellerMbr.setSellerSSNumber(schRes.getSellerMbr().getSellerSSNumber());
				sellerMbr.setSellerState(schRes.getSellerMbr().getSellerState());
				sellerMbr.setSellerSubStatus(schRes.getSellerMbr().getSellerSubStatus());
				sellerMbr.setSellerTelecom(schRes.getSellerMbr().getSellerTelecom());
				sellerMbr.setSellerZip(schRes.getSellerMbr().getSellerZip());
				sellerMbr.setTenantID(schRes.getSellerMbr().getTenantID());
				sellerMbr.setVendorCode(schRes.getSellerMbr().getVendorCode());

				response.setExtraRightList(eList);// 판매자 멀티미디어정보
				response.setMbrClauseAgree(mList);// 약관동의목록
				response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
				response.setSellerMbr(sellerMbr);// 판매자 정보
				response.setSellerKey(schRes.getSellerKey());// 판매자Key
			}
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
