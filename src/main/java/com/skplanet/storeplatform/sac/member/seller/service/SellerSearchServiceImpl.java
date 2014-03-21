package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
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
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchFlurryListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchFlurryListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchSellerKeySCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.BanksByCountry;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;
import com.skplanet.storeplatform.sac.client.member.vo.common.TabAuth;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionAllRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyRes;
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
	private SellerSCI sellerSCI; // 회원 Component 판매자 기능 Interface.

	@Autowired
	private SearchSellerKeySCI searchSellerKeySCI; // 전시 Internal Interface.

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

	/**
	 * <pre>
	 * 2.2.2. 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DuplicateByIdEmailReq
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
		if (StringUtils.equalsIgnoreCase("id", req.getKeyType())) {
			keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
		} else if (StringUtils.equalsIgnoreCase("email", req.getKeyType())) {
			keySearch.setKeyType(MemberConstants.KEY_TYPE_EMAIL);
		}
		keySearch.setKeyString(req.getKeyString());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);

		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 1-3. SC회원(ID/Email중복) Call */
		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

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
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailInformationReq
	 * @return DetailInformationRes
	 */
	@Override
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req) {
		LOGGER.debug("[SellerSearchServiceImpl.detailInformation()] START.");

		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(header);

		SearchSellerRequest schReq = new SearchSellerRequest();
		schReq.setCommonRequest(commonRequest);

		KeySearch keySearch = new KeySearch();

		if (StringUtils.isNotBlank(req.getSellerKey())) {
			keySearch.setKeyString(req.getSellerKey());
			keySearch.setKeyType("INSD_SELLERMBR_NO");
		} else if (StringUtils.isNotBlank(req.getSellerId())) {
			keySearch.setKeyString(req.getSellerId());
			keySearch.setKeyType("SELLERMBR_ID");
		} else {
			// App ID로 sellerKey 조회
			keySearch.setKeyString(this.searchSellerKeySCI.searchSellerKeyForAid(req.getAid()));
			keySearch.setKeyType("INSD_SELLERMBR_NO");
		}

		// SC 판매자회원 기본정보 조회.
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq.setKeySearchList(list);
		SearchSellerResponse schRes = this.sellerSCI.searchSeller(schReq);

		// SC 판매자회원 Fluury 연동정보 목록조회.
		SearchFlurryListRequest searchFlurryListRequest = new SearchFlurryListRequest();
		searchFlurryListRequest.setCommonRequest(commonRequest);
		searchFlurryListRequest.setSellerKey(schRes.getSellerKey());
		SearchFlurryListResponse searchFlurryListResponse = this.sellerSCI.searchFlurryList(searchFlurryListRequest);

		// 판매자 멀티미디어정보
		List<ExtraRight> eList = new ArrayList<ExtraRight>();
		ExtraRight extraRightList = null;
		if (schRes.getExtraRightList() != null) {
			for (int i = 0; i < schRes.getExtraRightList().size(); i++) {
				extraRightList = new ExtraRight();
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
		}

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (schRes.getMbrLglAgent() != null) {
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
		}

		// 판매자 탭권한
		List<TabAuth> tList = new ArrayList<TabAuth>();
		TabAuth tabAuthList = null;
		if (schRes.getTabAuthList() != null) {
			for (int i = 0; i < schRes.getTabAuthList().size(); i++) {
				tabAuthList = new TabAuth();
				tabAuthList.setTabCode(schRes.getTabAuthList().get(i).getTabCode());
				tList.add(tabAuthList);
			}
		}

		// 판매자 플러리 인증정보
		List<FlurryAuth> fList = new ArrayList<FlurryAuth>();
		FlurryAuth flurryAuthList = null;
		if (searchFlurryListResponse.getFlurryAuthList() != null)
			for (int i = 0; i < searchFlurryListResponse.getFlurryAuthList().size(); i++) {
				flurryAuthList = new FlurryAuth();
				flurryAuthList.setAccessCode(searchFlurryListResponse.getFlurryAuthList().get(i).getAccessCode());
				flurryAuthList.setAuthToken(searchFlurryListResponse.getFlurryAuthList().get(i).getAuthToken());
				flurryAuthList.setRegDate(searchFlurryListResponse.getFlurryAuthList().get(i).getRegDate());
				flurryAuthList.setSellerKey(searchFlurryListResponse.getFlurryAuthList().get(i).getSellerKey());
				flurryAuthList.setUpdateDate(searchFlurryListResponse.getFlurryAuthList().get(i).getUpdateDate());
				fList.add(flurryAuthList);
			}

		DetailInformationRes response = new DetailInformationRes();
		response.setExtraRightList(eList); // 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent); // 법정대리인정보
		response.setSellerKey(schRes.getSellerKey()); // 판매자Key
		// 실명인증 여부에 따른 판매자 회원정보 Setting.
		SellerMbrSac sellerMbr = this.sellerMbr(schRes.getSellerMbr(), schRes.getMbrAuth()); // 실명인증 판매자 정보 & 판매자 정보
		response.setSellerMbr(sellerMbr); // 판매자 정보
		response.setTabAuthList(tList);
		response.setFlurryAuthList(fList);

		LOGGER.debug("[SellerSearchServiceImpl.detailInformation()] END.");
		return response;

	}

	/**
	 * <pre>
	 * 판매자회원 기본정보조회 App. TODO 로직 확인 필요.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationForProductReq
	 * @return DetailInformationForProductRes
	 */
	@Override
	public DetailInformationForProductRes detailInformationApp(SacRequestHeader header,
			DetailInformationForProductReq req) {

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		KeySearch keySearch = new KeySearch();

		keySearch.setKeyString(req.getSellerKey());
		keySearch.setKeyType("INSD_SELLERMBR_NO");

		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq.setKeySearchList(list);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		Iterator<String> it = schRes.getSellerMbrListMap().keySet().iterator();
		List<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr> sellerMbrs = new ArrayList<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr>();

		List<SellerMbrSac> sellerMbrSacs = null;
		SellerMbrSac sellerMbrSac = null;

		String key = it.next();
		sellerMbrSacs = new ArrayList<SellerMbrSac>();
		sellerMbrs = (List<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr>) schRes
				.getSellerMbrListMap().get(key);

		if (sellerMbrs.get(0).getIsDomestic() == null || sellerMbrs.get(0).getSellerClass() == null) {
			throw new StorePlatformException("SAC_MEM_2101");
		}

		/* 상단 */
		// 내국인, 개인
		if ("Y".equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Top");
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getCharger()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getCharger());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrSacs.add(sellerMbrSac);
		}
		// 내국인, 개인사업자 or 법인 사업자
		else if (sellerMbrs.get(0).getIsDomestic().equals("Y")
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Top");
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrSacs.add(sellerMbrSac);
		}
		// 외국인, 개인 or 개인사업자 or 법인 사업자
		else if (sellerMbrs.get(0).getIsDomestic().equals("N")
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON.equals(sellerMbrs.get(0)
						.getSellerClass())
						|| MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
								.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
							.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Top");
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrSacs.add(sellerMbrSac);
		}

		/* 하단 */
		// 내국인, 개인
		if ("Y".equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Lower");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerName());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCharger()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getCharger());
			else
				sellerMbrSac.setSellerName("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getRepEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getCustomerEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getSellerEmail());
			else
				sellerMbrSac.setSellerEmail("");

			sellerMbrSacs.add(sellerMbrSac);
		}

		// 내국인, 개인사업자 OR 법인 사업자
		else if ("Y".equals(sellerMbrs.get(0).getIsDomestic())
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Lower");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerCompany(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerCompany("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getCeoName()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getCeoName());
			else
				sellerMbrSac.setSellerName("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getRepEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getCustomerEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getSellerEmail());
			else
				sellerMbrSac.setSellerEmail("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getBizRegNumber()))
				sellerMbrSac.setBizRegNumber(sellerMbrs.get(0).getBizRegNumber());
			else
				sellerMbrSac.setBizRegNumber("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerAddress()))
				sellerMbrSac.setSellerAddress(sellerMbrs.get(0).getSellerAddress() + " "
						+ sellerMbrs.get(0).getSellerDetailAddress());
			else
				sellerMbrSac.setSellerAddress("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepPhone()))
				sellerMbrSac.setSellerPhone(sellerMbrs.get(0).getRepPhone());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerPhone()))
				sellerMbrSac.setSellerPhone(sellerMbrs.get(0).getCustomerPhone());
			else
				sellerMbrSac.setSellerPhone("");

			sellerMbrSacs.add(sellerMbrSac);
		}

		// 외국인, 개인
		else if ("N".equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Lower");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerName());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerName("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getRepEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getCustomerEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getSellerEmail());
			else
				sellerMbrSac.setSellerEmail("");

			sellerMbrSacs.add(sellerMbrSac);
		}

		// 외국인, 개인사업자or법인사업자
		else if ("N".equals(sellerMbrs.get(0).getIsDomestic())
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setAppStat("Lower");
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerCompany(sellerMbrs.get(0).getSellerCompany());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()))
				sellerMbrSac.setSellerCompany(sellerMbrs.get(0).getSellerName());
			else
				sellerMbrSac.setSellerCompany("");

			if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getRepEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getCustomerEmail());
			else if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerEmail()))
				sellerMbrSac.setSellerEmail(sellerMbrs.get(0).getSellerEmail());
			else
				sellerMbrSac.setSellerEmail("");

			sellerMbrSacs.add(sellerMbrSac);
		}

		DetailInformationForProductRes response = new DetailInformationForProductRes();

		response.setIsDomestic(sellerMbrs.get(0).getIsDomestic());
		response.setSellerClass(sellerMbrs.get(0).getSellerClass());

		response.setSellerMbrList(sellerMbrSacs);

		return response;

	}

	/**
	 * <pre>
	 * 판매자회원 정산정보조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailAccountInformationReq
	 * @return DetailAccountInformationRes
	 */
	@Override
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req) {

		SearchAccountSellerRequest schReq = new SearchAccountSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());

		SearchAccountSellerResponse schRes = this.sellerSCI.searchAccountSeller(schReq);
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

			sellerAccount.setSellerBizAddress(schRes.getSellerMbr().getSellerBizAddress());
			sellerAccount.setSellerBizDetailAddress(schRes.getSellerMbr().getSellerBizDetailAddress());
			sellerAccount.setSellerBizZip(schRes.getSellerMbr().getSellerBizZip());

			sellerAccount.setSellerBizType(schRes.getSellerMbr().getSellerBizType()); // INDT_NM 업종명 종목 종목
			sellerAccount.setSellerBizCategory(schRes.getSellerMbr().getSellerBizCategory()); // COND_NM 업태명 업태 업태
			sellerAccount.setSellerBizCorpNumber(schRes.getSellerMbr().getSellerBizCorpNumber()); // ("법인등록번호");
																								  // CORP_REG_NO
			sellerAccount.setRepPhoneArea(schRes.getSellerMbr().getRepPhoneArea()); // ("대표전화번호 국가코드");
																					// REP_TEL_NATION_NO
			sellerAccount.setRepPhone(schRes.getSellerMbr().getRepPhone()); // ("대표전화번호"); REP_TEL_NO
			// sellerAccount.setRepEmail(schRes.getSellerMbr().getRepEmail()); // ("대표 이메일"); REP_EMAIL
			sellerAccount.setVendorCode(schRes.getSellerMbr().getVendorCode()); // ("벤더 코드"); VENDOR_CD
			sellerAccount.setIsBizRegistered(schRes.getSellerMbr().getIsBizRegistered()); // ("통신판매업 신고여부");
																						  // MSALBIZ_DECL_YN
			sellerAccount.setBizRegNumber(schRes.getSellerMbr().getBizRegNumber()); // ("통신판매업 신고번호"); MSALBIZ_DECL_NO
			sellerAccount.setBizUnregReason(schRes.getSellerMbr().getBizUnregReason()); // ("통신판매업 미신고사유  코드");
			// MSALBIZ_UNDECL_REASON_CD
			sellerAccount.setIsBizTaxable(schRes.getSellerMbr().getIsBizTaxable()); // ("간이과세여부"); // EASY_TXN_YN 간이 과세
																					// 여부 #####
			// 전환 쪽에서 사용
			sellerAccount.setBizGrade(schRes.getSellerMbr().getBizGrade()); // ("심의등급코드"); DELIB_GRD_CD 심의 등급코드
			sellerAccount.setIsDeductible(schRes.getSellerMbr().getIsDeductible()); // ("자동차감가능대상여부");
																					// AUTO_DED_POSB_TARGET_YN
			sellerAccount.setMarketCode(schRes.getSellerMbr().getMarketCode()); // ("입점상점코드"); LNCHG_MALL_CD 입점 상점코드
			sellerAccount.setMarketStatus(schRes.getSellerMbr().getMarketStatus()); // ("입점상태코드"); LNCHG_MBR_STATUS_CD
			sellerAccount.setIsAccountReal(schRes.getSellerMbr().getIsAccountReal()); // ("   계좌인증여부"); // ACCT_AUTH_YN
																					  // 계좌 인증여부
			// 컬럼
			sellerAccount.setIsOfficialAuth(schRes.getSellerMbr().getIsOfficialAuth());

		}
		DetailAccountInformationRes response = new DetailAccountInformationRes();
		response.setDocumentList(dList);
		response.setExtraRightList(eList);
		response.setSellerAccount(sellerAccount);
		response.setSellerKey(schRes.getSellerKey());
		// response.setSellerMbr(sellerMbr);
		return response;

	}

	/**
	 * <pre>
	 * 탈퇴 사유 목록 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @return ListWithdrawalReasonRes
	 */
	@Override
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header) {

		List<SecedeReson> sList = this.commonDAO.queryForList("SellerSearch.listWithdrawalReason", header
				.getTenantHeader().getLangCd(), SecedeReson.class);

		ListWithdrawalReasonRes response = new ListWithdrawalReasonRes();
		response.setSecedeResonList(sList);

		return response;

	}

	/**
	 * <pre>
	 * 판매자 ID 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            SearchIdReq
	 * @return SearchIdRes
	 */
	@Override
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req) {

		SearchIDSellerRequest schReq = new SearchIDSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		if (StringUtils.isNotBlank(req.getSellerBizNumber()))
			schReq.setSellerBizNumber(req.getSellerBizNumber());
		if (StringUtils.isNotBlank(req.getSellerCompany()))
			schReq.setSellerCompany(req.getSellerCompany());
		if (StringUtils.isNotBlank(req.getSellerEmail()))
			schReq.setSellerEmail(req.getSellerEmail());
		if (StringUtils.isNotBlank(req.getSellerPhone()))
			schReq.setSellerPhone(req.getSellerPhone());

		SearchIDSellerResponse schRes = this.sellerSCI.searchIDSeller(schReq);

		List<SellerMbrSac> sList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrRes = null;
		if (schRes.getSellerMbr() != null)
			for (int i = 0; i < schRes.getSellerMbr().size(); i++) {
				sellerMbrRes = new SellerMbrSac();
				sellerMbrRes.setSellerId(schRes.getSellerMbr().get(i).getSellerID());
				sellerMbrRes.setRegDate(schRes.getSellerMbr().get(i).getRegDate());
				sList.add(sellerMbrRes);
			}

		SearchIdRes response = new SearchIdRes();
		response.setSellerMbr(sList); // 판매자 정보 리스트

		return response;

	}

	/**
	 * <pre>
	 * Password 보안 질문 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListPasswordReminderQuestionReq
	 * @return ListPasswordReminderQuestionRes
	 */
	@Override
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			ListPasswordReminderQuestionReq req) {

		SearchPwdHintListRequest schReq = new SearchPwdHintListRequest();
		schReq.setSellerKey(req.getSellerKey());
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setLanguageCode(header.getTenantHeader().getLangCd());

		SearchPwdHintListResponse schRes = this.sellerSCI.searchPwdHintList(schReq);

		List<SellerMbrPwdHint> sList = new ArrayList<SellerMbrPwdHint>();
		SellerMbrPwdHint sellerMbrPwdHint = null;
		if (schRes.getPWReminderList() != null) {
			for (int i = 0; i < schRes.getPWReminderList().size(); i++) {
				sellerMbrPwdHint = new SellerMbrPwdHint();
				sellerMbrPwdHint.setQuestionID(schRes.getPWReminderList().get(i).getQuestionID());
				sellerMbrPwdHint.setQuestionMessage(schRes.getPWReminderList().get(i).getQuestionName());
				sList.add(sellerMbrPwdHint);
			}
		}

		ListPasswordReminderQuestionRes response = new ListPasswordReminderQuestionRes();
		response.setSellerMbrPwdHintList(sList);

		return response;

	}

	/**
	 * <pre>
	 * Password 보안 질문 조회 All.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return ListPasswordReminderQuestionAllRes
	 */
	@Override
	public ListPasswordReminderQuestionAllRes listPasswordReminderQuestionAll(SacRequestHeader header) {

		SearchPwdHintListAllRequest schReq = new SearchPwdHintListAllRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setLanguageCode(header.getTenantHeader().getLangCd());

		SearchPwdHintListAllResponse schRes = this.sellerSCI.searchPwdHintListAll(schReq);

		List<SellerMbrPwdHint> sList = new ArrayList<SellerMbrPwdHint>();
		SellerMbrPwdHint sellerMbrPwdHint = null;
		if (schRes.getPWReminderAllList() != null) {
			for (int i = 0; i < schRes.getPWReminderAllList().size(); i++) {
				sellerMbrPwdHint = new SellerMbrPwdHint();
				sellerMbrPwdHint.setQuestionID(schRes.getPWReminderAllList().get(i).getQuestionID());
				sellerMbrPwdHint.setQuestionMessage(schRes.getPWReminderAllList().get(i).getQuestionName());
				sellerMbrPwdHint.setDisplayOrder(schRes.getPWReminderAllList().get(i).getDisplayOrder());
				sList.add(sellerMbrPwdHint);
			}
		}
		ListPasswordReminderQuestionAllRes response = new ListPasswordReminderQuestionAllRes();
		response.setSellerMbrPwdHintList(sList);

		return response;

	}

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CheckPasswordReminderQuestionReq
	 * @return CheckPasswordReminderQuestionRes
	 */
	@Override
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			CheckPasswordReminderQuestionReq req) {

		CheckPasswordReminderSellerRequest schReq = new CheckPasswordReminderSellerRequest();

		schReq.setSellerID(req.getSellerId());

		/** 보안질문 리스트 주입 - [시작]. */
		List<PWReminder> pWReminderList = null;
		if (req.getpWReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getpWReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getpWReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getpWReminderList().get(i).getQuestionID());
				// pwReminder.setQuestionMessage(req.getpWReminderList().get(i).getQuestionMessage());
				pwReminder.setSellerID(req.getpWReminderList().get(i).getSellerId());
				pWReminderList.add(pwReminder);
			}
			schReq.setPWReminderList(pWReminderList);
		}
		/** 보안질문 리스트 주입 - [끝]. */

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		CheckPasswordReminderSellerResponse schRes = this.sellerSCI.checkPasswordReminderSeller(schReq);

		CheckPasswordReminderQuestionRes response = new CheckPasswordReminderQuestionRes();
		response.setIsCorrect(schRes.getIsCorrect());
		return response;

	}

	/**
	 * <pre>
	 * 판매자 Password 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchPasswordReq
	 * @return SearchPasswordRes
	 */
	@Override
	public SearchPasswordRes searchPassword(SacRequestHeader header, SearchPasswordReq req) {

		ResetPasswordSellerRequest schReq = new ResetPasswordSellerRequest();

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID(req.getSellerId()); // 셀러 id 를 넣어야한다.

		schReq.setMbrPwd(mbrPwd);

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		ResetPasswordSellerResponse schRes = this.sellerSCI.resetPasswordSeller(schReq);

		SearchPasswordRes response = new SearchPasswordRes();
		response.setNewPassword(schRes.getSellerPW());

		return response;

	}

	/**
	 * <pre>
	 * 2.2.27.	판매자 회원 인증키 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchAuthKeyReq
	 * @return DetailInformationRes
	 */
	@Override
	public SearchAuthKeyRes searchAuthKey(SacRequestHeader header, SearchAuthKeyReq req) {

		SearchLoginInfoRequest schReq = new SearchLoginInfoRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSessionKey(req.getSessionKey());
		SearchLoginInfoResponse schRes = this.sellerSCI.searchLoginInfo(schReq);

		if (Integer.parseInt(req.getExtraDate()) > 0) {
			UpdateLoginInfoRequest schReq2 = new UpdateLoginInfoRequest();
			schReq2.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setSellerKey(schRes.getLoginInfo().getSellerKey());
			loginInfo.setSessionKey(req.getSessionKey());
			loginInfo.setExpireDate(this.commonComponent.getExpirationTime(Integer.parseInt(req.getExtraDate())));
			schReq2.setLoginInfo(loginInfo);
			this.sellerSCI.updateLoginInfo(schReq2);
		}

		SearchSellerRequest schReq3 = new SearchSellerRequest();
		schReq3.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INSD_SELLERMBR_NO"); // TODO member상수로 변경
		keySearch.setKeyString(schRes.getLoginInfo().getSellerKey());
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq3.setKeySearchList(list);

		SearchSellerResponse schRes3 = this.sellerSCI.searchSeller(schReq3);

		SearchAuthKeyRes response = new SearchAuthKeyRes();

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

		response.setExtraRightList(eList); // 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent); // 법정대리인정보
		response.setSellerKey(schRes3.getSellerKey()); // 판매자Key
		response.setSellerMbr(this.sellerMbr(schRes3.getSellerMbr(), schRes3.getMbrAuth())); // 판매자 정보

		return response;

	}

	/**
	 * <pre>
	 * 나라별 해외 은행 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
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
	 * 판매자 정보.
	 * </pre>
	 * 
	 * @param sellerMbr
	 *            판매자정보
	 * @param mbrAuth
	 *            실명인증 판매자 정보
	 * 
	 * @return SellerMbr 실명인증 여부에 따라 셋팅된 판매자 정보
	 */
	private SellerMbrSac sellerMbr(com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr,
			com.skplanet.storeplatform.member.client.common.vo.MbrAuth mbrAuth) {
		// 판매자 정보
		SellerMbrSac sellerMbrRes = new SellerMbrSac();

		if (sellerMbr != null) {
			sellerMbrRes.setApproveDate(sellerMbr.getApproveDate());
			sellerMbrRes.setBizGrade(sellerMbr.getBizGrade());
			sellerMbrRes.setBizKindCd(sellerMbr.getBizKindCd());
			sellerMbrRes.setBizRegNumber(sellerMbr.getBizRegNumber());
			sellerMbrRes.setBizUnregReason(sellerMbr.getBizUnregReason());
			sellerMbrRes.setCeoName(sellerMbr.getCeoName());
			sellerMbrRes.setCeoBirthDay(sellerMbr.getCeoBirthDay());
			sellerMbrRes.setCharger(sellerMbr.getCharger());
			sellerMbrRes.setCordedTelephone(sellerMbr.getCordedTelephone());
			sellerMbrRes.setCordedTelephoneCountry(sellerMbr.getCordedTelephoneCountry());
			sellerMbrRes.setCustomerEmail(sellerMbr.getCustomerEmail());
			sellerMbrRes.setCustomerPhone(sellerMbr.getCustomerPhone());
			sellerMbrRes.setChargerPhone(sellerMbr.getChargerPhone());
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
			sellerMbrRes.setRepPhone(sellerMbr.getRepPhone());
			sellerMbrRes.setRepPhoneArea(sellerMbr.getRepPhoneArea());
			sellerMbrRes.setRightProfile(sellerMbr.getRightProfileList());
			sellerMbrRes.setSellerAddress(sellerMbr.getSellerAddress());
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
			sellerMbrRes.setSellerNickName(sellerMbr.getSellerNickName());
			sellerMbrRes.setSellerPhone(sellerMbr.getSellerPhone());
			sellerMbrRes.setSellerPhoneCountry(sellerMbr.getSellerPhoneCountry());
			sellerMbrRes.setSellerSSNumber(sellerMbr.getSellerSSNumber());
			sellerMbrRes.setSellerState(sellerMbr.getSellerState());
			sellerMbrRes.setSellerSubStatus(sellerMbr.getSellerSubStatus());
			sellerMbrRes.setSellerTelecom(sellerMbr.getSellerTelecom());
			sellerMbrRes.setSellerZip(sellerMbr.getSellerZip());
			sellerMbrRes.setVendorCode(sellerMbr.getVendorCode());
			sellerMbrRes.setUpdateDate(sellerMbr.getUpdateDate());
			sellerMbrRes.setWebsite(sellerMbr.getWebsite());

			if (mbrAuth != null && StringUtils.isNotBlank(sellerMbr.getIsRealName())
					&& "Y".equals(sellerMbr.getIsRealName())) {
				// 실명인증 판매자 정보 셋팅.
				sellerMbrRes.setSellerName(mbrAuth.getName());
				sellerMbrRes.setSellerSex(mbrAuth.getSex());
				sellerMbrRes.setSellerBirthDay(mbrAuth.getBirthDay());
			} else {
				// 판매자 정보 셋팅.
				sellerMbrRes.setSellerName(sellerMbr.getSellerName());
				sellerMbrRes.setSellerSex(sellerMbr.getSellerSex());
				sellerMbrRes.setSellerBirthDay(sellerMbr.getSellerBirthDay());
			}
		}
		return sellerMbrRes;
	}
}
