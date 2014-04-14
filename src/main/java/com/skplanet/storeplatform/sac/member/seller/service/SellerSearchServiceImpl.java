package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.BanksByCountry;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerBpSac;
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
	 * 상품상세의 판매자 정보.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationForProductReq
	 * @return DetailInformationForProductRes
	 */
	@Override
	public DetailInformationForProductRes detailInformationForProduct(SacRequestHeader header,
			DetailInformationForProductReq req) {

		String sellerKey = StringUtils.isNotBlank(req.getSellerKey()) ? req.getSellerKey() : this.searchSellerKeySCI
				.searchSellerKeyForAid(req.getAid());

		SearchSellerRequest searchSellerRequest = new SearchSellerRequest();
		// Header 셋팅
		searchSellerRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		// 검색 조회 셋팅
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyString(sellerKey);
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		searchSellerRequest.setKeySearchList(list);

		// SC-회원 기본 정보 조회
		SearchSellerResponse searchSellerResponse = this.sellerSCI.searchSeller(searchSellerRequest);

		// 상단
		String nameTop = null;
		// 하단
		String nameLower = null;
		String compNmLower = null;
		String emailLower = null;
		String bizNoLower = null;
		String addrLower = null;
		String phoneLower = null;

		// Top + Lower
		// 내국인
		if (StringUtils.equals(MemberConstants.USE_Y, searchSellerResponse.getSellerMbr().getIsDomestic())) {
			// 개인
			if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, searchSellerResponse
					.getSellerMbr().getSellerClass())) {
				nameTop = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getCharger(), "");
				nameLower = StringUtils
						.defaultString( // first:sellerName, second:charger, default:""
								StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getSellerName()) ? searchSellerResponse
										.getSellerMbr().getSellerName() : searchSellerResponse.getSellerMbr()
										.getCharger(), "");
			}
			// 개인 사업자, 법인 사업자
			if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS, searchSellerResponse
					.getSellerMbr().getSellerClass())
					|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS,
							searchSellerResponse.getSellerMbr().getSellerClass())) {
				nameTop = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getSellerCompany(), "");

				compNmLower = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getSellerCompany(), "");
				nameLower = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getCeoName(), "");
				bizNoLower = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getBizRegNumber(), "");
				phoneLower = StringUtils
						.defaultString( // first:repPhone, second:cordedTelephone, default:""
								StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getRepPhone()) ? searchSellerResponse
										.getSellerMbr().getRepPhone() : searchSellerResponse.getSellerMbr()
										.getCordedTelephone(), "");
				addrLower = StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getSellerAddress()) ? searchSellerResponse
						.getSellerMbr().getSellerAddress() : "";
				addrLower += StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getSellerDetailAddress()) ? " "
						+ searchSellerResponse.getSellerMbr().getSellerDetailAddress() : "";
			}

		} else { // 외국인
			nameTop = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getSellerCompany(), "");

			// 개인 ( 판매자명, 이메일 )
			if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, searchSellerResponse
					.getSellerMbr().getSellerClass())) {

				nameLower = StringUtils
						.defaultString( // first:sellerName, second:sellerCompany, default:""
								StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getSellerName()) ? searchSellerResponse
										.getSellerMbr().getSellerName() : searchSellerResponse.getSellerMbr()
										.getSellerCompany(), "");
			}
			// 개인 사업자, 법인 사업자 ( 상호명, 이메일 )
			if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS, searchSellerResponse
					.getSellerMbr().getSellerClass())
					|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS,
							searchSellerResponse.getSellerMbr().getSellerClass())) {

				compNmLower = StringUtils
						.defaultString( // first:sellerCompany, second:sellerName, default:""
								StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getSellerCompany()) ? searchSellerResponse
										.getSellerMbr().getSellerCompany() : searchSellerResponse.getSellerMbr()
										.getSellerName(), "");
			}
		}

		// first:repEmail, second:customerEmail, third:sellerEmail, default:""
		if (StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getRepEmail())) {
			emailLower = searchSellerResponse.getSellerMbr().getRepEmail();
		} else if (StringUtils.isNotBlank(searchSellerResponse.getSellerMbr().getCustomerEmail())) {
			emailLower = searchSellerResponse.getSellerMbr().getCustomerEmail();
		} else {
			emailLower = StringUtils.defaultString(searchSellerResponse.getSellerMbr().getSellerEmail(), "");
		}

		SellerMbrAppSac sellerMbrSac = new SellerMbrAppSac();
		List<SellerMbrAppSac> resList = new ArrayList<DetailInformationForProductRes.SellerMbrAppSac>();

		// 상단 셋팅
		sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
		sellerMbrSac.setSellerName(nameTop);
		resList.add(0, sellerMbrSac);

		sellerMbrSac = new SellerMbrAppSac();
		// 하단 셋팅
		sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
		resList.add(1, sellerMbrSac);
		sellerMbrSac.setSellerName(nameLower);
		sellerMbrSac.setSellerCompany(compNmLower);
		sellerMbrSac.setSellerEmail(emailLower);
		sellerMbrSac.setBizRegNumber(bizNoLower);
		sellerMbrSac.setSellerAddress(addrLower);
		sellerMbrSac.setSellerPhone(phoneLower);

		// RESPONSE
		DetailInformationForProductRes response = new DetailInformationForProductRes();
		response.setSellerId(searchSellerResponse.getSellerMbr().getSellerID());
		response.setIsDomestic(searchSellerResponse.getSellerMbr().getIsDomestic());
		response.setSellerClass(searchSellerResponse.getSellerMbr().getSellerClass());
		response.setSellerMbrList(resList);

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
		List<Document> documentList = new ArrayList<Document>();
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
				documentList.add(document);
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
			sellerAccount.setChangedCd(schRes.getSellerAccount().getChangedCd()); // 전환 유형코드
		}

		// 판매자 BP 정보.
		SellerBpSac sellerBpSac = new SellerBpSac();
		if (schRes.getSellerBp() != null) {
			sellerBpSac.setBpFileName(schRes.getSellerBp().getBpFileName());
			sellerBpSac.setBpFilePath(schRes.getSellerBp().getBpFilePath());
		}

		DetailAccountInformationRes response = new DetailAccountInformationRes();
		response.setDocumentList(documentList);
		response.setSellerAccount(sellerAccount);
		response.setSellerBp(sellerBpSac);
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

		schReq.setSellerBizNumber(StringUtils.isNotBlank(req.getSellerBizNumber()) ? req.getSellerBizNumber() : null);
		schReq.setSellerCompany(StringUtils.isNotBlank(req.getSellerCompany()) ? req.getSellerCompany() : null);
		schReq.setSellerEmail(StringUtils.isNotBlank(req.getSellerEmail()) ? req.getSellerEmail() : null);
		schReq.setSellerPhone(StringUtils.isNotBlank(req.getSellerPhone()) ? req.getSellerPhone() : null);

		SearchIDSellerResponse searchIdSellerRes = this.sellerSCI.searchIDSeller(schReq);

		List<SellerMbrSac> sellerList = null;
		if (searchIdSellerRes.getSellerMbr() != null) {
			sellerList = new ArrayList<SellerMbrSac>();
			SellerMbrSac sellerMbr = null;
			for (int i = 0; i < searchIdSellerRes.getSellerMbr().size(); i++) {
				sellerMbr = new SellerMbrSac();
				sellerMbr.setSellerId(searchIdSellerRes.getSellerMbr().get(i).getSellerID());
				sellerMbr.setRegDate(searchIdSellerRes.getSellerMbr().get(i).getRegDate());
				sellerList.add(sellerMbr);
			}
		}

		SearchIdRes response = new SearchIdRes();
		response.setSellerMbr(sellerList); // 판매자 정보 리스트

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

		CheckPasswordReminderSellerRequest passwordReminderReq = new CheckPasswordReminderSellerRequest();

		passwordReminderReq.setSellerID(req.getSellerId());

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
			passwordReminderReq.setPWReminderList(pwReminderList);
		}
		/** 보안질문 리스트 주입 - [끝]. */

		passwordReminderReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		CheckPasswordReminderSellerResponse passwordReminderRes = this.sellerSCI
				.checkPasswordReminderSeller(passwordReminderReq);

		CheckPasswordReminderQuestionRes response = new CheckPasswordReminderQuestionRes();
		response.setIsCorrect(passwordReminderRes.getIsCorrect());
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

		SearchPwdHintListRequest sellerPwdHintReq = new SearchPwdHintListRequest();
		sellerPwdHintReq.setSellerKey(req.getSellerKey());
		sellerPwdHintReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		sellerPwdHintReq.setLanguageCode(header.getTenantHeader().getLangCd()); // Header에서 LangCd 추출하여 Setting.

		SearchPwdHintListResponse sellerPwdHintRes = this.sellerSCI.searchPwdHintList(sellerPwdHintReq);

		List<SellerMbrPwdHint> sellerPwdHintList = null;
		SellerMbrPwdHint sellerPwdHint = null;
		if (sellerPwdHintRes.getPWReminderList() != null) {
			sellerPwdHintList = new ArrayList<SellerMbrPwdHint>();
			for (int i = 0; i < sellerPwdHintRes.getPWReminderList().size(); i++) {
				sellerPwdHint = new SellerMbrPwdHint();
				sellerPwdHint.setQuestionId(sellerPwdHintRes.getPWReminderList().get(i).getQuestionID());
				sellerPwdHint.setQuestionMessage(sellerPwdHintRes.getPWReminderList().get(i).getQuestionName());
				sellerPwdHintList.add(sellerPwdHint);
			}
		}

		ListPasswordReminderQuestionRes response = new ListPasswordReminderQuestionRes();
		response.setSellerMbrPwdHintList(sellerPwdHintList);

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

		SearchPwdHintListAllResponse pwdHintListAllRes = this.sellerSCI.searchPwdHintListAll(schReq);

		List<SellerMbrPwdHint> sellerPwdHintList = new ArrayList<SellerMbrPwdHint>();
		SellerMbrPwdHint sellerPwdHint = null;
		if (pwdHintListAllRes.getPWReminderAllList() != null) {
			for (int i = 0; i < pwdHintListAllRes.getPWReminderAllList().size(); i++) {
				sellerPwdHint = new SellerMbrPwdHint();
				sellerPwdHint.setQuestionId(pwdHintListAllRes.getPWReminderAllList().get(i).getQuestionID());
				sellerPwdHint.setQuestionMessage(pwdHintListAllRes.getPWReminderAllList().get(i).getQuestionName());
				sellerPwdHint.setDisplayOrder(pwdHintListAllRes.getPWReminderAllList().get(i).getDisplayOrder());
				sellerPwdHintList.add(sellerPwdHint);
			}
		}
		ListPasswordReminderQuestionAllRes response = new ListPasswordReminderQuestionAllRes();
		response.setSellerMbrPwdHintList(sellerPwdHintList);

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
	 *            DetailInfomationByAuthorizationKeySacReq
	 * @return DetailInfomationByAuthorizationKeySacRes
	 */
	@Override
	public DetailInfomationByAuthorizationKeySacRes detailInfomationByAuthorizationKey(SacRequestHeader header,
			DetailInfomationByAuthorizationKeySacReq req) {

		SearchLoginInfoRequest searchLoginInReq = new SearchLoginInfoRequest();
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(header);

		searchLoginInReq.setCommonRequest(commonRequest);
		searchLoginInReq.setSessionKey(req.getSessionKey());
		SearchLoginInfoResponse searchLoginInRes = this.sellerSCI.searchLoginInfo(searchLoginInReq);

		// 세션키 업데이트
		UpdateLoginInfoRequest updateLoginInfoReq = new UpdateLoginInfoRequest();
		updateLoginInfoReq.setCommonRequest(commonRequest);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setSellerKey(searchLoginInRes.getLoginInfo().getSellerKey());
		loginInfo.setSessionKey(req.getSessionKey());
		loginInfo.setExpireDate(this.commonComponent.getExpirationTime(Integer.parseInt(req.getExpireDate())));
		updateLoginInfoReq.setLoginInfo(loginInfo);
		this.sellerSCI.updateLoginInfo(updateLoginInfoReq);

		SearchSellerRequest searchSellerReq = new SearchSellerRequest();
		searchSellerReq.setCommonRequest(commonRequest);
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		keySearch.setKeyString(searchLoginInRes.getLoginInfo().getSellerKey());
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		searchSellerReq.setKeySearchList(list);

		SearchSellerResponse searchSellerRes = this.sellerSCI.searchSeller(searchSellerReq);

		DetailInfomationByAuthorizationKeySacRes response = new DetailInfomationByAuthorizationKeySacRes();

		// 판매자 멀티미디어정보
		List<ExtraRight> extraRightList = new ArrayList<ExtraRight>();
		ExtraRight extraRight = null;
		if (searchSellerRes.getExtraRightList() != null)
			for (int i = 0; i < searchSellerRes.getExtraRightList().size(); i++) {
				extraRight = new ExtraRight();
				extraRight.setRightProfileCode(searchSellerRes.getExtraRightList().get(i).getRightProfileCode());
				extraRightList.add(extraRight);
			}

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (searchSellerRes.getMbrLglAgent() != null) {
			mbrLglAgent.setMemberKey(searchSellerRes.getMbrLglAgent().getMemberKey());
			mbrLglAgent.setParentBirthDay(searchSellerRes.getMbrLglAgent().getParentBirthDay());
			mbrLglAgent.setParentCI(searchSellerRes.getMbrLglAgent().getParentCI());
			mbrLglAgent.setParentDate(searchSellerRes.getMbrLglAgent().getParentDate());
			mbrLglAgent.setParentEmail(searchSellerRes.getMbrLglAgent().getParentEmail());
			mbrLglAgent.setParentMsisdn(searchSellerRes.getMbrLglAgent().getParentMDN());
			mbrLglAgent.setParentName(searchSellerRes.getMbrLglAgent().getParentName());
			mbrLglAgent.setParentRealNameDate(searchSellerRes.getMbrLglAgent().getParentRealNameDate());
			mbrLglAgent.setParentRealNameMethod(searchSellerRes.getMbrLglAgent().getParentRealNameMethod());
			mbrLglAgent.setParentRealNameSite(searchSellerRes.getMbrLglAgent().getParentRealNameSite());
			mbrLglAgent.setParentTelecom(searchSellerRes.getMbrLglAgent().getParentTelecom());
			mbrLglAgent.setParentType(searchSellerRes.getMbrLglAgent().getParentType());
			mbrLglAgent.setSequence(searchSellerRes.getMbrLglAgent().getSequence());
		}

		// SC 판매자회원 Fluury 연동정보 목록조회.
		SearchFlurryListRequest searchFlurryListReq = new SearchFlurryListRequest();
		searchFlurryListReq.setCommonRequest(commonRequest);
		searchFlurryListReq.setSellerKey(searchSellerRes.getSellerKey());
		SearchFlurryListResponse searchFlurryListResponse = this.sellerSCI.searchFlurryList(searchFlurryListReq);

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

		response.setExtraRightList(extraRightList); // 판매자 멀티미디어정보
		response.setMbrLglAgent(mbrLglAgent); // 법정대리인정보
		response.setFlurryAuthList(flurryAuthList);
		response.setSellerMbr(this.sellerMbr(searchSellerRes.getSellerMbr(), searchSellerRes.getMbrAuth())); // 판매자 정보

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
	 * 판매자 회원 정보 조회 - 내부메서드.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailInformationSacReq
	 * @return DetailInformationSacRes
	 */
	@Override
	public DetailInformationSacRes detailInformationList(SacRequestHeader header, DetailInformationSacReq req) {

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		List<KeySearch> sellerMbrSacList = null;
		if (req.getSellerMbrSacList() != null) {
			sellerMbrSacList = new ArrayList<KeySearch>();
			for (int i = 0; i < req.getSellerMbrSacList().size(); i++) {
				KeySearch keySearch = new KeySearch();

				if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerKey())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerKey());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
					sellerMbrSacList.add(keySearch);
				} else if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerId())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerId());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
					sellerMbrSacList.add(keySearch);
				} else if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerBizNumber())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerBizNumber());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_BIZ_NO); // 사업자 등록번호.
					sellerMbrSacList.add(keySearch);
				}
			}

		}
		schReq.setKeySearchList(sellerMbrSacList);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		Iterator<String> it = schRes.getSellerMbrListMap().keySet().iterator();

		List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac> sellerMbrSacs = null;
		com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac sellerMbrSac = null;
		Map<String, List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>> sellerMbrSacMap = new HashMap<String, List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>>();

		while (it.hasNext()) {
			String key = it.next();
			sellerMbrSacs = new ArrayList<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>();
			List<SellerMbr> sellerMbrs = (List<SellerMbr>) schRes.getSellerMbrListMap().get(key);
			for (int i = 0; i < sellerMbrs.size(); i++) {
				sellerMbrSac = new com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac();
				sellerMbrSac.setSellerKey(sellerMbrs.get(i).getSellerKey());
				sellerMbrSac.setSellerId(sellerMbrs.get(i).getSellerID());
				sellerMbrSac.setSellerClass(sellerMbrs.get(i).getSellerClass());
				sellerMbrSac.setCharger(sellerMbrs.get(i).getCharger());
				sellerMbrSac.setSellerCompany(sellerMbrs.get(i).getSellerCompany());
				sellerMbrSac.setSellerNickName(sellerMbrs.get(i).getSellerNickName());
				sellerMbrSac.setSellerBizNumber(sellerMbrs.get(i).getSellerBizNumber());
				sellerMbrSac.setSellerName(sellerMbrs.get(i).getSellerName());
				sellerMbrSac.setRepPhone(sellerMbrs.get(i).getRepPhone());
				sellerMbrSac.setSellerEmail(sellerMbrs.get(i).getSellerEmail());
				sellerMbrSac.setSellerAddress(sellerMbrs.get(i).getSellerAddress());
				sellerMbrSac.setSellerDetailAddress(sellerMbrs.get(i).getSellerDetailAddress());
				sellerMbrSac.setBizRegNumber(sellerMbrs.get(i).getBizRegNumber());
				sellerMbrSacs.add(sellerMbrSac);
			}
			sellerMbrSacMap.put(key, sellerMbrSacs);
		}

		DetailInformationSacRes response = new DetailInformationSacRes();

		response.setSellerMbrListMap(sellerMbrSacMap);
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
