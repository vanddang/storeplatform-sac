package com.skplanet.storeplatform.sac.member.seller.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchFlurryListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchFlurryListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.ExtraRightSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.FlurryAuthSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.MbrLglAgentSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.TabAuthSac;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;

/**
 * 판매자 회원 조회 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 한서구, 부르칸.
 */
@LocalSCI
public class SellerSearchSCIController implements SellerSearchSCI {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SellerSearchService sellerSearchService;

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MemberCommonComponent commonComponent;

	/**
	 * <pre>
	 * 판매자회원 기본정보조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DetailInformationRes
	 */
	@Override
	public DetailInformationSacRes detailInformation(DetailInformationSacReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID("S01");
		commonRequest.setTenantID("S01-01002");

		SearchSellerRequest schReq = new SearchSellerRequest();
		schReq.setCommonRequest(commonRequest);

		KeySearch keySearch = new KeySearch();

		if (!req.getSellerKey().equals("")) {
			keySearch.setKeyString(req.getSellerKey());
			keySearch.setKeyType("INSD_SELLERMBR_NO");
		} else if (!req.getSellerId().equals("")) {
			keySearch.setKeyString(req.getSellerId());
			keySearch.setKeyType("SELLERMBR_ID");
		} else {
			DetailInformationSacReq sellerDTO = this.commonDAO.queryForObject("SellerSearch.sellerKey", req,
					DetailInformationSacReq.class);
			keySearch.setKeyString(sellerDTO.getSellerKey());
			keySearch.setKeyType("INSD_SELLERMBR_NO");
		}

		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq.setKeySearchList(list);

		SearchSellerResponse schRes = this.sellerSCI.searchSeller(schReq);

		SearchFlurryListRequest schReq2 = new SearchFlurryListRequest();
		schReq2.setCommonRequest(commonRequest);
		schReq2.setSellerKey(schRes.getSellerKey());

		SearchFlurryListResponse schRes2 = this.sellerSCI.searchFlurryList(schReq2);

		// 판매자 멀티미디어정보
		List<ExtraRightSac> eList = new ArrayList<ExtraRightSac>();
		ExtraRightSac extraRightList = null;
		if (schRes.getExtraRightList() != null)
			for (int i = 0; i < schRes.getExtraRightList().size(); i++) {
				extraRightList = new ExtraRightSac();
				// extraRightList.setEndDate(schRes.getExtraRightList().get(i).getEndDate());
				// extraRightList.setRegDate(schRes.getExtraRightList().get(i).getRegDate());
				extraRightList.setRegID(schRes.getExtraRightList().get(i).getRegID());
				extraRightList.setRightProfileCode(schRes.getExtraRightList().get(i).getRightProfileCode());
				// extraRightList.setSellerKey(schRes.getExtraRightList().get(i).getSellerKey());
				// extraRightList.setSellerRate(schRes.getExtraRightList().get(i).getSellerRate());
				// extraRightList.setStartDate(schRes.getExtraRightList().get(i).getStartDate());
				extraRightList.setTenantID(schRes.getExtraRightList().get(i).getTenantID());
				extraRightList.setTenantRate(schRes.getExtraRightList().get(i).getTenantRate());
				extraRightList.setUpdateDate(schRes.getExtraRightList().get(i).getUpdateDate());
				extraRightList.setUpdateID(schRes.getExtraRightList().get(i).getUpdateID());

				eList.add(extraRightList);
			}

		// 법정대리인정보
		MbrLglAgentSac mbrLglAgent = new MbrLglAgentSac();
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

		// 판매자 탭권한
		List<TabAuthSac> tList = new ArrayList<TabAuthSac>();
		TabAuthSac tabAuthList = null;
		if (schRes.getTabAuthList() != null)
			for (int i = 0; i < schRes.getTabAuthList().size(); i++) {
				tabAuthList = new TabAuthSac();
				tabAuthList.setTabCode(schRes.getTabAuthList().get(i).getTabCode());
				tList.add(tabAuthList);
			}

		// 판매자 플러리 인증정보
		List<FlurryAuthSac> fList = new ArrayList<FlurryAuthSac>();
		FlurryAuthSac flurryAuthList = null;
		if (schRes2.getFlurryAuthList() != null)
			for (int i = 0; i < schRes2.getFlurryAuthList().size(); i++) {
				flurryAuthList = new FlurryAuthSac();
				flurryAuthList.setAccessCode(schRes2.getFlurryAuthList().get(i).getAccessCode());
				flurryAuthList.setAuthToken(schRes2.getFlurryAuthList().get(i).getAuthToken());
				flurryAuthList.setRegDate(schRes2.getFlurryAuthList().get(i).getRegDate());
				flurryAuthList.setSellerKey(schRes2.getFlurryAuthList().get(i).getSellerKey());
				flurryAuthList.setUpdateDate(schRes2.getFlurryAuthList().get(i).getUpdateDate());
				fList.add(flurryAuthList);
			}

		DetailInformationSacRes response = new DetailInformationSacRes();
		response.setExtraRightList(eList);// 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent);// 법정대리인정보
		response.setSellerKey(schRes.getSellerKey());// 판매자Key
		response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));// 판매자 정보
		response.setTabAuthList(tList);
		response.setFlurryAuthList(fList);

		return response;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private SellerMbrSac sellerMbr(com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr) {
		// 판매자 정보
		SellerMbrSac sellerMbrRes = new SellerMbrSac();
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
