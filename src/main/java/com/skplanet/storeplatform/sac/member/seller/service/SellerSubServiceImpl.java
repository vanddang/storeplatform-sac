package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;

@Service
@Transactional
public class SellerSubServiceImpl implements SellerSubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

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

		CreateSubSellerResponse schRes = new CreateSubSellerResponse();
		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

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
		schReq.setSubSellerID(req.getSubSellerID());
		schReq.setSubSellerPW(req.getSubSellerPW());
		schReq.setSubSellerMemo(req.getSubSellerMemo());
		schReq.setSubSellerEmail(req.getSubSellerEmail());

		schRes = this.sellerSCI.createSubSeller(schReq);
		if (!MemberConstants.RESULT_SUCCES.equals(schRes.getCommonResponse().getResultCode())) {
			throw new RuntimeException(schRes.getCommonResponse().getResultMessage());
		}

		CreateSubsellerRes response = new CreateSubsellerRes();

		response.setSubSellerKey(schRes.getSubSellerKey());

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
		// 최종 vo 에 값 셋팅
		List<String> removeKeyList = new ArrayList<String>();
		removeKeyList = req.getSubSellerKey();
		schReq.setSubSeller(removeKeyList);

		schRes = this.sellerSCI.removeSubSeller(schReq);
		if (!MemberConstants.RESULT_SUCCES.equals(schRes.getCommonResponse().getResultCode())) {
			throw new RuntimeException(schRes.getCommonResponse().getResultMessage());
		}

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

		schRes = this.sellerSCI.searchSubSellerList(schReq);
		if (!MemberConstants.RESULT_SUCCES.equals(schRes.getCommonResponse().getResultCode())) {
			throw new RuntimeException(schRes.getCommonResponse().getResultMessage());
		}

		ListSubsellerRes response = new ListSubsellerRes();
		response.setSellerID(schRes.getSellerID());
		response.setSellerKey(schRes.getSellerKey());
		response.setSubAccountCount(schRes.getSubAccountCount());
		response.setSubSellerList(this.sellerMbrList(schRes.getSubSellerList()));// 판매자 정보 리스트

		return response;
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

}
