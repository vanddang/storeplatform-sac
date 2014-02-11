package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

@Service
public class SellerSubServiceImpl implements SellerSubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	private MemberCommonComponent commonComponent;

	/**
	 * <pre>
	 * 판매자 서브계정 등록/수정.
	 * </pre>
	 * 
	 * @param CreateSubsellerReq
	 * @return CreateSubsellerRes
	 */
	@Override
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req) {

		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr = new com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr();
		sellerMbr.setParentSellerKey(req.getSellerKey()); // US201401231325534800000164
														  // IF1023501437920130904104346
		sellerMbr.setSellerID(req.getSubSellerID());
		sellerMbr.setSellerKey(req.getSubSellerKey());
		sellerMbr.setSubSellerMemo(req.getSubSellerMemo());
		sellerMbr.setSellerEmail(req.getSubSellerEmail());
		sellerMbr.setRightProfileList(req.getSubSellerCateList());
		sellerMbr.setSellerPhone(req.getSubSellerPhone());
		sellerMbr.setSellerPhoneCountry(req.getSubSellerPhoneCountry());
		schReq.setSellerMbr(sellerMbr);
		schReq.setIsNew(req.getIsNew());

		if (req.getMemberPW() != null && req.getOldPW() != null) {
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setMemberPW(req.getMemberPW());
			mbrPwd.setOldPW(req.getOldPW());
			schReq.setMbrPwd(mbrPwd);
		}
		if (req.getIsNew().equals("Y")) {
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setMemberPW(req.getMemberPW());
			schReq.setMbrPwd(mbrPwd);
		}

		CreateSubSellerResponse schRes = this.sellerSCI.createSubSeller(schReq);

		LOGGER.info("---------" + schRes.getSellerKey());
		CreateSubsellerRes response = new CreateSubsellerRes();

		response.setSubSellerKey(schRes.getSellerKey());

		return response;
	}

	/**
	 * <pre>
	 * 판매자 서브계정 삭제.
	 * </pre>
	 * 
	 * @param RemoveSubsellerReq
	 * @return RemoveSubsellerRes
	 */
	@Override
	public RemoveSubsellerRes removeSubseller(SacRequestHeader header, RemoveSubsellerReq req) {

		RemoveSubSellerResponse schRes = new RemoveSubSellerResponse();
		RemoveSubSellerRequest schReq = new RemoveSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		schReq.setParentSellerKey(req.getSellerKey());
		// 최종 vo 에 값 셋팅
		List<String> removeKeyList = new ArrayList<String>();
		removeKeyList = req.getSubSellerKey();
		schReq.setSellerKeyList(removeKeyList);

		schRes = this.sellerSCI.removeSubSeller(schReq);

		RemoveSubsellerRes response = new RemoveSubsellerRes();

		response.setRemoveCnt(schRes.getDeletedNumber());

		return response;
	}

	/**
	 * <pre>
	 * 서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	@Override
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req) {

		SearchSubSellerListResponse schRes = new SearchSubSellerListResponse();
		SearchSubSellerListRequest schReq = new SearchSubSellerListRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setParentSellerKey(req.getSellerKey());

		schRes = this.sellerSCI.searchSubSellerList(schReq);

		ListSubsellerRes response = new ListSubsellerRes();
		response.setSellerID(schRes.getSellerID());
		response.setSellerKey(schRes.getSellerKey());
		response.setSubAccountCount(schRes.getSubAccountCount());
		response.setSubSellerList(this.sellerMbrList(schRes.getSubSellerList()));// 판매자 정보 리스트

		return response;
	}

	/**
	 * <pre>
	 * 서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param DetailSubsellerReq
	 * @return DetailSubsellerRes
	 */
	@Override
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req) {

		SearchSubSellerResponse schRes = new SearchSubSellerResponse();
		SearchSubSellerRequest schReq = new SearchSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());

		schRes = this.sellerSCI.searchSubSeller(schReq);

		DetailSubsellerRes response = new DetailSubsellerRes();
		response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));

		return response;
	}

	@Override
	public DuplicateBySubsellerIdRes duplicateBySubsellerId(SacRequestHeader header, DuplicateBySubsellerIdReq req)
			throws Exception {

		/** SC회원 시작 */
		/** 1. ID/Email Req 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		checkDuplicationSellerRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
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

		/** 4. TenantRes Response 생성 및 주입 */
		DuplicateBySubsellerIdRes response = new DuplicateBySubsellerIdRes();

		// TODO Exception 재정의 - 성공또는 값없음의 경우
		if (MemberConstants.RESULT_SUCCES.equals(checkDuplicationSellerResponse.getCommonResponse().getResultCode())) {
			response.setIsRegistered(checkDuplicationSellerResponse.getIsRegistered());
		} else if (MemberConstants.RESULT_FAIL.equals(checkDuplicationSellerResponse.getCommonResponse()
				.getResultCode())) {
			response.setIsRegistered("N");
		} else {
			throw new RuntimeException(checkDuplicationSellerResponse.getCommonResponse().getResultMessage());
		}

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
				sellerMbrRes.setRightProfile(sellerMbr.get(i).getRightProfileList());
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
			sellerMbrRes.setRightProfile(sellerMbr.getRightProfileList());
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

}
