package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
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
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
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
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchSellerKeySCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.BanksByCountry;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInfomationByAuthorizationKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInfomationByAuthorizationKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationForProductRes.SellerMbrAppSac;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes.TabAuthSac;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionAllRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
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
			keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		} else if (StringUtils.isNotBlank(req.getSellerId())) {
			keySearch.setKeyString(req.getSellerId());
			keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
		} else {
			// App ID로 sellerKey 조회
			keySearch.setKeyString(this.searchSellerKeySCI.searchSellerKeyForAid(req.getAid()));
			keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
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

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (schRes.getMbrLglAgent() != null) {
			mbrLglAgent.setIsParent(schRes.getMbrLglAgent().getIsParent());
			mbrLglAgent.setParentBirthDay(schRes.getMbrLglAgent().getParentBirthDay());
			mbrLglAgent.setParentCI(schRes.getMbrLglAgent().getParentCI());
			mbrLglAgent.setParentDate(schRes.getMbrLglAgent().getParentDate());
			mbrLglAgent.setParentEmail(schRes.getMbrLglAgent().getParentEmail());
			mbrLglAgent.setParentMsisdn(schRes.getMbrLglAgent().getParentMDN());
			mbrLglAgent.setParentName(schRes.getMbrLglAgent().getParentName());
			mbrLglAgent.setParentRealNameDate(schRes.getMbrLglAgent().getParentRealNameDate());
			mbrLglAgent.setParentRealNameMethod(schRes.getMbrLglAgent().getParentRealNameMethod());
			mbrLglAgent.setParentRealNameSite(schRes.getMbrLglAgent().getParentRealNameSite());
			mbrLglAgent.setParentTelecom(schRes.getMbrLglAgent().getParentTelecom());
			mbrLglAgent.setParentType(schRes.getMbrLglAgent().getParentType());
		}

		// 판매자 탭권한
		List<TabAuthSac> tabAuthList = new ArrayList<TabAuthSac>();
		TabAuthSac tabAuth = null;
		if (schRes.getTabAuthList() != null) {
			for (int i = 0; i < schRes.getTabAuthList().size(); i++) {
				tabAuth = new TabAuthSac();
				tabAuth.setTabCode(schRes.getTabAuthList().get(i).getTabCode());
				tabAuthList.add(tabAuth);
			}
		}

		// 판매자 멀티미디어정보
		List<ExtraRight> extraRightList = null;
		if (schRes.getExtraRightList() != null) {
			extraRightList = new ArrayList<ExtraRight>();
			ExtraRight extraRight = null;
			for (int i = 0; i < schRes.getExtraRightList().size(); i++) {
				extraRight = new ExtraRight();
				extraRight.setRightProfileCode(schRes.getExtraRightList().get(i).getRightProfileCode());
				extraRightList.add(extraRight);
			}
		}

		// 판매자 플러리 인증정보
		List<FlurryAuth> flurryAuthList = new ArrayList<FlurryAuth>();
		FlurryAuth flurryAuth = null;
		if (searchFlurryListResponse.getFlurryAuthList() != null) {
			for (int i = 0; i < searchFlurryListResponse.getFlurryAuthList().size(); i++) {
				flurryAuth = new FlurryAuth();
				flurryAuth.setAccessCode(searchFlurryListResponse.getFlurryAuthList().get(i).getAccessCode());
				flurryAuth.setAuthToken(searchFlurryListResponse.getFlurryAuthList().get(i).getAuthToken());
				flurryAuth.setRegDate(searchFlurryListResponse.getFlurryAuthList().get(i).getRegDate());
				flurryAuth.setUpdateDate(searchFlurryListResponse.getFlurryAuthList().get(i).getUpdateDate());
				flurryAuthList.add(flurryAuth);
			}
		}

		// 실명인증 여부에 따른 판매자 회원정보 Setting.
		SellerMbrSac sellerMbr = this.sellerMbr(schRes.getSellerMbr(), schRes.getMbrAuth()); // 실명인증 판매자 정보 & 판매자 정보

		DetailInformationRes response = new DetailInformationRes();
		response.setSellerMbr(sellerMbr); // 판매자 정보
		response.setMbrLglAgent(mbrLglAgent); // 법정대리인정보
		response.setExtraRightList(extraRightList); // 판매자 멀티미디어 정보
		response.setTabAuthList(tabAuthList); // 탭권한 정보
		response.setFlurryAuthList(flurryAuthList); // 판매자 플러리 인증정보

		LOGGER.debug("[SellerSearchServiceImpl.detailInformation()] END.");
		return response;

	}

	/**
	 * <pre>
	 * 판매자회원 기본정보조회 App.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationForProductReq
	 * @return DetailInformationForProductRes
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DetailInformationForProductRes detailInformationForProduct(SacRequestHeader header,
			DetailInformationForProductReq req) {

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		KeySearch keySearch = new KeySearch();

		keySearch.setKeyString(req.getSellerKey());
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);

		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq.setKeySearchList(list);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		List<SellerMbrAppSac> sellerMbrList = null;
		SellerMbrAppSac sellerMbrSac = null;

		List<SellerMbr> sellerMbrs = new ArrayList<SellerMbr>();
		sellerMbrList = new ArrayList<SellerMbrAppSac>();

		sellerMbrs = (List<SellerMbr>) schRes.getSellerMbrListMap().get(
				schRes.getSellerMbrListMap().keySet().iterator().next());

		if (sellerMbrs.get(0).getIsDomestic() == null || sellerMbrs.get(0).getSellerClass() == null) {
			throw new StorePlatformException("SAC_MEM_2101");
		}

		/* 상단 */
		// 내국인, 개인
		if (MemberConstants.USE_Y.equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getCharger()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getCharger());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrList.add(sellerMbrSac);
		}
		// 내국인, 개인사업자 or 법인 사업자
		else if (StringUtils.equals(MemberConstants.USE_Y, sellerMbrs.get(0).getIsDomestic())
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrList.add(sellerMbrSac);
		}
		// 외국인, 개인 or 개인사업자 or 법인 사업자
		else if (sellerMbrs.get(0).getIsDomestic().equals(MemberConstants.USE_N)
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON.equals(sellerMbrs.get(0)
						.getSellerClass())
						|| MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
								.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
							.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
			if (StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()))
				sellerMbrSac.setSellerName(sellerMbrs.get(0).getSellerCompany());
			else
				sellerMbrSac.setSellerName("");
			sellerMbrList.add(sellerMbrSac);
		}

		/* 하단 */
		// 내국인, 개인
		if (MemberConstants.USE_Y.equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);

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

			sellerMbrList.add(sellerMbrSac);
		}

		// 내국인, 개인사업자 OR 법인 사업자
		else if (MemberConstants.USE_Y.equals(sellerMbrs.get(0).getIsDomestic())
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);

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
				// sellerMbrSac.setBizRegNumber(sellerMbrs.get(0).getBizRegNumber());
				// else
				// sellerMbrSac.setBizRegNumber("");

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

			sellerMbrList.add(sellerMbrSac);
		}

		// 외국인, 개인
		else if (MemberConstants.USE_N.equals(sellerMbrs.get(0).getIsDomestic())
				&& MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON
						.equals(sellerMbrs.get(0).getSellerClass())) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);

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

			sellerMbrList.add(sellerMbrSac);
		}

		// 외국인, 개인사업자or법인사업자
		else if (MemberConstants.USE_N.equals(sellerMbrs.get(0).getIsDomestic())
				&& (MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS.equals(sellerMbrs.get(0)
						.getSellerClass()) || MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS
						.equals(sellerMbrs.get(0).getSellerClass()))) {
			sellerMbrSac = new SellerMbrAppSac();
			sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
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

			sellerMbrList.add(sellerMbrSac);
		}

		DetailInformationForProductRes response = new DetailInformationForProductRes();

		response.setIsDomestic(sellerMbrs.get(0).getIsDomestic());
		response.setSellerClass(sellerMbrs.get(0).getSellerClass());

		response.setSellerMbrList(sellerMbrList);

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
				document.setDocumentCode(schRes.getDocument().get(i).getDocumentCode());
				document.setDocumentName(schRes.getDocument().get(i).getDocumentName());
				document.setDocumentPath(schRes.getDocument().get(i).getDocumentPath());
				document.setDocumentSize(schRes.getDocument().get(i).getDocumentSize());
				document.setIsUsed(schRes.getDocument().get(i).getIsUsed());
				dList.add(document);
			}

		// 판매자 정산정보
		SellerAccount sellerAccount = new SellerAccount();
		if (schRes.getSellerAccount() != null) {
			sellerAccount.setSellerKey(schRes.getSellerAccount().getSellerKey());
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
			sellerAccount.setIbanCode(schRes.getSellerAccount().getIbanCode());
			sellerAccount.setSwiftCode(schRes.getSellerAccount().getSwiftCode());
			sellerAccount.setTpinCode(schRes.getSellerAccount().getTpinCode());
			sellerAccount.setBankBsb(schRes.getSellerAccount().getBankBsb());
			sellerAccount.setSellerBizAddress(schRes.getSellerMbr().getSellerBizAddress());
			sellerAccount.setSellerBizDetailAddress(schRes.getSellerMbr().getSellerBizDetailAddress());
			sellerAccount.setSellerBizZip(schRes.getSellerMbr().getSellerBizZip());
			sellerAccount.setSellerBizType(schRes.getSellerMbr().getSellerBizType());
			sellerAccount.setSellerBizCategory(schRes.getSellerMbr().getSellerBizCategory());
			sellerAccount.setSellerBizCorpNumber(schRes.getSellerMbr().getSellerBizCorpNumber());
			sellerAccount.setVendorCode(schRes.getSellerMbr().getVendorCode());
			sellerAccount.setIsBizRegistered(schRes.getSellerMbr().getIsBizRegistered());
			sellerAccount.setBizRegNumber(schRes.getSellerMbr().getBizRegNumber());
			sellerAccount.setBizUnregReason(schRes.getSellerMbr().getBizUnregReason());
			sellerAccount.setIsBizTaxable(schRes.getSellerMbr().getIsBizTaxable());
			sellerAccount.setBizGrade(schRes.getSellerMbr().getBizGrade());
			sellerAccount.setIsDeductible(schRes.getSellerMbr().getIsDeductible());
			sellerAccount.setMarketCode(schRes.getSellerMbr().getMarketCode());
			sellerAccount.setMarketStatus(schRes.getSellerMbr().getMarketStatus());
			sellerAccount.setIsAccountReal(schRes.getSellerMbr().getIsAccountReal());
			sellerAccount.setIsOfficialAuth(schRes.getSellerMbr().getIsOfficialAuth());
			sellerAccount.setSellerBizPhoneCountry(schRes.getSellerMbr().getCustomerPhoneCountry());
			sellerAccount.setSellerBizPhone(schRes.getSellerMbr().getCustomerPhone());
			sellerAccount.setCeoBirthDay(schRes.getSellerMbr().getCeoBirthDay());
			sellerAccount.setCeoName(schRes.getSellerMbr().getCeoName());
		}
		DetailAccountInformationRes response = new DetailAccountInformationRes();
		response.setDocumentList(dList);
		response.setSellerAccount(sellerAccount);
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

		// 파라미터 Setting. SC 쿼리에서 null check 하므로 전체 setting 해서 넘겨줘도 무관.
		schReq.setSellerBizNumber(req.getSellerBizNumber());
		schReq.setSellerCompany(req.getSellerCompany());
		schReq.setSellerEmail(req.getSellerEmail());
		schReq.setSellerPhone(req.getSellerPhone());

		SearchIDSellerResponse schRes = this.sellerSCI.searchIDSeller(schReq);

		List<SellerMbrSac> sList = null;
		if (schRes.getSellerMbr() != null) {
			sList = new ArrayList<SellerMbrSac>();
			SellerMbrSac sellerMbrRes = null;
			for (int i = 0; i < schRes.getSellerMbr().size(); i++) {
				sellerMbrRes = new SellerMbrSac();
				sellerMbrRes.setSellerId(schRes.getSellerMbr().get(i).getSellerID());
				sellerMbrRes.setRegDate(schRes.getSellerMbr().get(i).getRegDate());
				sList.add(sellerMbrRes);
			}
		}

		SearchIdRes response = new SearchIdRes();
		response.setSellerMbr(sList); // 판매자 정보 리스트

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
		List<PWReminder> pwReminderList = null;
		if (req.getPwReminderList() != null) {
			pwReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPwReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				pwReminder.setAnswerString(req.getPwReminderList().get(i).getAnswerString());
				pwReminder.setQuestionID(req.getPwReminderList().get(i).getQuestionId());
				pwReminderList.add(pwReminder);
			}
			schReq.setPWReminderList(pwReminderList);
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
		schReq.setLanguageCode(header.getTenantHeader().getLangCd()); // Header에서 LangCd 추출하여 Setting.

		SearchPwdHintListResponse schRes = this.sellerSCI.searchPwdHintList(schReq);

		List<SellerMbrPwdHint> sList = null;
		SellerMbrPwdHint sellerMbrPwdHint = null;
		if (schRes.getPWReminderList() != null) {
			sList = new ArrayList<SellerMbrPwdHint>();
			for (int i = 0; i < schRes.getPWReminderList().size(); i++) {
				sellerMbrPwdHint = new SellerMbrPwdHint();
				sellerMbrPwdHint.setQuestionId(schRes.getPWReminderList().get(i).getQuestionID());
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
				sellerMbrPwdHint.setQuestionId(schRes.getPWReminderAllList().get(i).getQuestionID());
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
	public DetailInfomationByAuthorizationKeySacRes detailInfomationByAuthorizationKey(SacRequestHeader header,
			DetailInfomationByAuthorizationKeySacReq req) {

		SearchLoginInfoRequest schReq = new SearchLoginInfoRequest();
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(header);

		schReq.setCommonRequest(commonRequest);
		schReq.setSessionKey(req.getSessionKey());
		SearchLoginInfoResponse schRes = this.sellerSCI.searchLoginInfo(schReq);

		// 세션키 업데이트
		UpdateLoginInfoRequest schReq2 = new UpdateLoginInfoRequest();
		schReq2.setCommonRequest(commonRequest);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(schRes.getLoginInfo().getSellerKey());
		loginInfo.setSessionKey(req.getSessionKey());
		loginInfo.setExpireDate(this.commonComponent.getExpirationTime(Integer.parseInt(req.getExpireDate())));
		schReq2.setLoginInfo(loginInfo);
		this.sellerSCI.updateLoginInfo(schReq2);

		SearchSellerRequest schReq3 = new SearchSellerRequest();
		schReq3.setCommonRequest(commonRequest);
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		keySearch.setKeyString(schRes.getLoginInfo().getSellerKey());
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq3.setKeySearchList(list);

		SearchSellerResponse schRes3 = this.sellerSCI.searchSeller(schReq3);

		DetailInfomationByAuthorizationKeySacRes response = new DetailInfomationByAuthorizationKeySacRes();

		// 판매자 멀티미디어정보
		List<ExtraRight> eList = new ArrayList<ExtraRight>();
		ExtraRight extraRightList = null;
		if (schRes3.getExtraRightList() != null)
			for (int i = 0; i < schRes3.getExtraRightList().size(); i++) {
				extraRightList = new ExtraRight();
				extraRightList.setRightProfileCode(schRes3.getExtraRightList().get(i).getRightProfileCode());
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
			mbrLglAgent.setParentMsisdn(schRes3.getMbrLglAgent().getParentMDN());
			mbrLglAgent.setParentName(schRes3.getMbrLglAgent().getParentName());
			mbrLglAgent.setParentRealNameDate(schRes3.getMbrLglAgent().getParentRealNameDate());
			mbrLglAgent.setParentRealNameMethod(schRes3.getMbrLglAgent().getParentRealNameMethod());
			mbrLglAgent.setParentRealNameSite(schRes3.getMbrLglAgent().getParentRealNameSite());
			mbrLglAgent.setParentTelecom(schRes3.getMbrLglAgent().getParentTelecom());
			mbrLglAgent.setParentType(schRes3.getMbrLglAgent().getParentType());
			mbrLglAgent.setSequence(schRes3.getMbrLglAgent().getSequence());
		}

		// SC 판매자회원 Fluury 연동정보 목록조회.
		SearchFlurryListRequest searchFlurryListRequest = new SearchFlurryListRequest();
		searchFlurryListRequest.setCommonRequest(commonRequest);
		searchFlurryListRequest.setSellerKey(schRes3.getSellerKey());
		SearchFlurryListResponse searchFlurryListResponse = this.sellerSCI.searchFlurryList(searchFlurryListRequest);

		// 판매자 플러리 인증정보
		List<FlurryAuth> flurryAuthList = new ArrayList<FlurryAuth>();
		FlurryAuth flurryAuth = null;
		if (searchFlurryListResponse.getFlurryAuthList() != null) {
			for (int i = 0; i < searchFlurryListResponse.getFlurryAuthList().size(); i++) {
				flurryAuth = new FlurryAuth();
				flurryAuth.setAccessCode(searchFlurryListResponse.getFlurryAuthList().get(i).getAccessCode());
				flurryAuth.setAuthToken(searchFlurryListResponse.getFlurryAuthList().get(i).getAuthToken());
				flurryAuth.setRegDate(searchFlurryListResponse.getFlurryAuthList().get(i).getRegDate());
				flurryAuth.setUpdateDate(searchFlurryListResponse.getFlurryAuthList().get(i).getUpdateDate());
				flurryAuthList.add(flurryAuth);
			}
		}

		response.setExtraRightList(eList); // 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent); // 법정대리인정보
		response.setFlurryAuthList(flurryAuthList);
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
	@SuppressWarnings("unchecked")
	@Override
	public ListBanksByCountryRes listBanksByCountry(SacRequestHeader header) {

		ListBanksByCountryRes response = new ListBanksByCountryRes();
		response.setBanksByCountry((List<BanksByCountry>) this.commonDAO.queryForList(
				"SellerSearch.listBanksByCountry", BanksByCountry.class));

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
	private SellerMbrSac sellerMbr(SellerMbr sellerMbr, MbrAuth mbrAuth) {
		// 판매자 정보
		SellerMbrSac sellerMbrRes = new SellerMbrSac();

		if (sellerMbr != null) {
			sellerMbrRes.setSellerKey(sellerMbr.getSellerKey());
			sellerMbrRes.setSellerId(sellerMbr.getSellerID());
			sellerMbrRes.setSellerClass(sellerMbr.getSellerClass());
			sellerMbrRes.setSellerCategory(sellerMbr.getSellerCategory());
			sellerMbrRes.setSellerMainStatus(sellerMbr.getSellerMainStatus());
			sellerMbrRes.setSellerSubStatus(sellerMbr.getSellerSubStatus());
			sellerMbrRes.setIsRealName(sellerMbr.getIsRealName()); // 실명인증 여부

			/* 개인회원의 경우, 생년월일 하나만도 수정 가능. */
			sellerMbrRes.setSellerBirthDay(sellerMbr.getSellerBirthDay());
			if (mbrAuth != null && StringUtils.equals(MemberConstants.USE_Y, sellerMbr.getIsRealName())) {
				// 실명인증 판매자 정보 셋팅.
				sellerMbrRes.setSellerName(mbrAuth.getName());
				sellerMbrRes.setSellerSex(mbrAuth.getSex());
			} else {
				// 판매자 정보 셋팅.
				sellerMbrRes.setSellerName(sellerMbr.getSellerName());
				sellerMbrRes.setSellerSex(sellerMbr.getSellerSex());
			}
			sellerMbrRes.setSellerSSNumber(sellerMbr.getSellerSSNumber());
			sellerMbrRes.setSellerEmail(sellerMbr.getSellerEmail());
			sellerMbrRes.setCharger(sellerMbr.getCharger());
			sellerMbrRes.setCustomerEmail(sellerMbr.getCustomerEmail());
			sellerMbrRes.setSellerPhone(sellerMbr.getSellerPhone());
			sellerMbrRes.setSellerPhoneCountry(sellerMbr.getSellerPhoneCountry());
			sellerMbrRes.setCordedTelephone(sellerMbr.getCordedTelephone());
			sellerMbrRes.setCordedTelephoneCountry(sellerMbr.getCordedTelephoneCountry());
			sellerMbrRes.setIsDomestic(sellerMbr.getIsDomestic());
			sellerMbrRes.setIsRecvEmail(sellerMbr.getIsRecvEmail());
			sellerMbrRes.setIsRecvSms(sellerMbr.getIsRecvSMS());
			sellerMbrRes.setRepEmail(sellerMbr.getRepEmail());
			sellerMbrRes.setSellerAddress(sellerMbr.getSellerAddress());
			sellerMbrRes.setSellerBizNumber(sellerMbr.getSellerBizNumber());
			sellerMbrRes.setSellerCity(sellerMbr.getSellerCity());
			sellerMbrRes.setSellerCompany(sellerMbr.getSellerCompany());
			sellerMbrRes.setSellerCountry(sellerMbr.getSellerCountry());
			sellerMbrRes.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
			sellerMbrRes.setSellerLanguage(sellerMbr.getSellerLanguage());
			sellerMbrRes.setSellerNickName(sellerMbr.getSellerNickName());
			sellerMbrRes.setSellerState(sellerMbr.getSellerState());
			sellerMbrRes.setSellerTelecom(sellerMbr.getSellerTelecom());
			sellerMbrRes.setSellerZip(sellerMbr.getSellerZip());
			sellerMbrRes.setRepPhone(sellerMbr.getRepPhone());
			sellerMbrRes.setRepPhoneArea(sellerMbr.getRepPhoneArea());
			sellerMbrRes.setWebsite(sellerMbr.getWebsite());
			sellerMbrRes.setRegDate(sellerMbr.getRegDate());
			sellerMbrRes.setUpdateDate(sellerMbr.getUpdateDate());

			// 구매 요청으로 통신판매업 관련 파라미터 3개 추가. 2014.03.26
			sellerMbrRes.setIsBizRegistered(sellerMbr.getIsBizRegistered());
			sellerMbrRes.setBizRegNumber(sellerMbr.getBizRegNumber());
			sellerMbrRes.setBizUnregReason(sellerMbr.getBizUnregReason());
		}
		return sellerMbrRes;
	}
}
