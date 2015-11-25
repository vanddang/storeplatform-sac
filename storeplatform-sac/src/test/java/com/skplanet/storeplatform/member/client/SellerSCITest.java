/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.Document;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ExtraRight;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminderAll;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerResponse;
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
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerAccount;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerUpgrade;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerResponse;
import com.skplanet.storeplatform.member.common.code.MainStateCode;
import com.skplanet.storeplatform.member.common.code.SellerCategoryCode;
import com.skplanet.storeplatform.member.common.code.SellerClassCode;
import com.skplanet.storeplatform.member.common.code.SubStateCode;

/**
 * The Class SellerSCITest.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/member/spring-test/context-test.xml" })
@Transactional
// @TransactionConfiguration
@TransactionConfiguration(defaultRollback = false)
public class SellerSCITest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSCITest.class);

	/** The seller sci. */
	@Autowired
	private SellerSCI sellerSCI;

	/** The message source accessor. */
	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 판매자 가입.
	 */
	// @Ignore
	@Test
	public void createSeller() {

		// vo 생성
		CreateSellerRequest createSeller = new CreateSellerRequest();
		SellerMbr sellerMbr = new SellerMbr();
		MbrPwd mbrPwd = new MbrPwd();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// List<MbrClauseAgree> mbrClauseAgreeList;
		// MbrClauseAgree mbrClauseAgree;

		sellerMbr.setRepPhone("dd");

		sellerMbr.setSellerBizZip("120-757");
		sellerMbr.setIsOfficialAuth("N");
		sellerMbr.setIsTaxable("Y");
		sellerMbr.setWebsite("http://skt.com");
		sellerMbr.setSellerBizCorpNumber("1234한 글abc");

		// wd 에 있는 정보 anihap-674 (US010317) 6개월 이내 탈퇴 (생성가능 )
		// wd 에 있는 정보 ixippe-127 6개월 이내 탈퇴 (생성 불가능 )
		// wd 에 있는 정보 qasign11 <- 6개월이전 탈퇴 (생성가능 )
		// sctest2 사용중 isRegistered: Y ,resultMessage: 성공 (생성 불가능 )

		sellerMbr.setSellerName("국외법인사업자유료");

		sellerMbr.setSellerMainStatus(MainStateCode.NORMAL.getCode());
		sellerMbr.setSellerSubStatus(SubStateCode.NORMAL.getCode());
		sellerMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
		sellerMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)
		sellerMbr.setSellerPhone("010119119");

		sellerMbr.setIsDomestic("Y"); // 국내판매자 여부. ex 국내 ('Y'), 해외 ('N')
		sellerMbr.setSellerClass("US010103"); // 판매자구분 (개인,개인사업자,법인사업자)
		sellerMbr.setSellerCategory("US011302"); // 판매자분류 (유료/무료)
		sellerMbr.setSellerID("sctest000112"); // sellertest001 sellertest002 sellertest003 scteamtest008
		sellerMbr.setSellerBirthDay("20130317");
		sellerMbr.setSellerAddress("서울시 서대문구 대현동 ");
		sellerMbr.setSellerDetailAddress("부영아파트 101동 101호");
		mbrPwd.setMemberPW("test111");

		// qa 생성 DB ############################################################################################
		// 국내유무, 판매자구분, 판매자분류, ID, pass , 이름 , sellerKey , 멀티미디어 메뉴권한
		// 국내, 개인(US010101), 무료(US011301), sctest1, test111 , 국내개인 무료사용자 , SE201403061033410460000074
		// 국내, 개인(US010101), 유료(US011302), sctest2, test111 , 국내개인 유료사용자 , SE201403061036155000000075
		// 해외, 개인(US010101), 유료(US011302), sctest3, test111 , 해외개인 유료사용자 , SE201403061036537590000076
		// 국내, 개인사업자 (US010102), 유료(US011302), sctest4, test111 , 국내개인사업자 유료사용자 , SE201403061037419590000077 ,멀티미디어 메뉴권한
		// 국내, 법인사업자(US010103), 유료(US011302), sctest5, test111 , 국내법인사업자 유료사용자 , SE201403061038187150000078 ,쇼핑메뉴권한
		// 국내, 법인사업자(US010103), 유료(US011302), sctest6, test111 , 국내법인사업자 유료사용자 , SE201403061038463450000079

		// US011001 멀티미디어
		// US011002 쇼핑상품

		// INSERT INTO SPMBR.TB_US_SELLERMBR_TAB_AUTH (INSD_SELLERMBR_NO,
		// PMT_YN,
		// REG_DT,
		// REG_ID,
		// TAB_CD,
		// TENANT_ID)
		// VALUES ('SE201403061037419590000077',
		// 'Y',
		// SYSDATE,
		// 'ADMIN',
		// 'US011001',
		// 'S01');

		// 개발 생성 DB ############################################################################################
		// 국내유무, 판매자구분, 판매자분류, ID, pass , 이름 , sellerKey
		// 국내, 개인(US010101), 무료(US011301), sctest1, 123456 , 국내개인무료사용자 , SE201402051026319660000583
		// 국내, 개인사업자(US010102), 유료(US011302), sctest2, 123456 , 국내개인사업자유료 , SE201402051027290360000584
		// 국내, 법인사업자(US010103), 유료(US011302), sctest3, 123456 , 국내법인사업자유료 , SE201402051027587210000585

		// 국외, 개인(US010101), 무료(US011301), sctest4, 123456 , 국외개인무료사용자 , SE201402051028461120000586
		// 국외, 개인사업자(US010102), 유료(US011302), sctest5, 123456 , 국외개인사업자유료 , SE201402051029143200000587
		// 국외, 법인사업자(US010103), 유료(US011302), sctest6, 123456 , 국외법인사업자유료 , SE201402051030427530000588

		// 서브셀러
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest1, 123456 , '' , SS201402051035009830000589
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest2, 123456 , '' , SS201402051052009220000590

		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		List<PWReminder> pWReminderList;
		pWReminderList = new ArrayList<PWReminder>();
		PWReminder pWReminder = new PWReminder();
		pWReminder.setQuestionID("WHERE1");
		pWReminder.setAnswerString("Seoul - Korea");
		pWReminder.setQuestionMessage("test");

		pWReminderList.add(pWReminder);

		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		// mbrAuth.setCi("cici");
		mbrAuth.setMemberCategory("US010802");
		mbrAuth.setIsRealName("Y");
		mbrAuth.setIsDomestic("Y");

		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		// mbrLglAgent.setParentCI("부모ci");
		// mbrLglAgent.setParentRealNameMethod("US005302");
		mbrAuth.setIsDomestic("N");

		LOGGER.debug("### mbrPwd : {}", mbrPwd.toString());

		// 최종 vo 에 값 셋팅

		createSeller.setMbrPwd(mbrPwd);
		createSeller.setSellerMbr(sellerMbr);
		createSeller.setCommonRequest(commonRequest);
		// createSeller.setMbrClauseAgree(mbrClauseAgreeList);
		createSeller.setMbrAuth(mbrAuth);
		createSeller.setPWReminderList(pWReminderList);

		// 최종 vo 에 값 셋팅
		LOGGER.debug("### 넘긴 데이터 : {}", createSeller.toString());

		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(createSeller);
		CommonResponse commonResponse = createSellerResponse.getCommonResponse();
		LOGGER.debug("### 받은 데이터 1: {}", createSellerResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(createSellerResponse, notNullValue());

		assertThat(createSellerResponse.getSellerID(), notNullValue());
		assertThat(createSellerResponse.getSellerKey(), notNullValue());
		assertThat(createSellerResponse.getSellerMainStatus(), notNullValue());
		assertThat(createSellerResponse.getSellerSubStatus(), notNullValue());
		assertThat(createSellerResponse.getStopStatusCode(), notNullValue());
		assertThat(createSellerResponse.getLoginStatusCode(), notNullValue());

		assertSame(
				createSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자 기본정보만 조회 (map 리턴).
	 */
	@Test
	public void searchMbrSeller() {

		SearchMbrSellerRequest searchMbrSellerRequest = new SearchMbrSellerRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		searchMbrSellerRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();

		keySearch.setKeyType("BIZ_REG_NO");
		keySearch.setKeyString("1352668460"); // IF1023530314420111128165556
		keySearchList.add(keySearch);

		keySearch = new KeySearch();
		keySearch.setKeyType("BIZ_REG_NO");
		keySearch.setKeyString("1234567891"); // IF1023530314420111128165556
		keySearchList.add(keySearch);

		keySearch = new KeySearch();
		keySearch.setKeyType("BIZ_REG_NO");
		keySearch.setKeyString("1352668460"); // IF1023530314420111128165556
		keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType("EMAIL");
		// keySearch.setKeyString("op_tabs_1001@gmail.com"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);
		//

		// keySearch = new KeySearch();
		// keySearch.setKeyType("INSD_SELLERMBR_NO");
		// keySearch.setKeyString("IF1023599632020120110232253-"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType("INSD_SELLERMBR_NO");
		// keySearch.setKeyString("IF1023599663820120110234358---"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType("INSD_SELLERMBR_NO");
		// keySearch.setKeyString("IF1023627312420120125143617-"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		searchMbrSellerRequest.setKeySearchList(keySearchList);

		// IF1023361425020110414102136 법정대리인키
		// IF1023530314420111128165556 인증키
		// IF102815946620090819200153 멀티미디어 권한
		SearchMbrSellerResponse searchMbrSellerResponse = this.sellerSCI.searchMbrSeller(searchMbrSellerRequest);

		if (searchMbrSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchMbrSellerResponse.getCommonResponse().toString());
		if (searchMbrSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchMbrSellerResponse.toString());
		if (searchMbrSellerResponse.getSellerMbrListMap() != null) {

			Iterator iter = searchMbrSellerResponse.getSellerMbrListMap().entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();

				LOGGER.info("### 서버에서 내려받은 값 3 key : {}", entry.getKey() + "  size "
						+ ((List<SellerMbr>) searchMbrSellerResponse.getSellerMbrListMap().get(entry.getKey())).size());
			}
			LOGGER.info("### 서버에서 내려받은 값 3 value : {}", searchMbrSellerResponse.getSellerMbrListMap().get("1234567891"));

			// for (KeySearch kSearch : keySearchList) {
			// Map<String, SellerMbr> sellerMbrMap = searchMbrSellerResponse.getSellerMbrListMap();
			// SellerMbr sellerMbr = sellerMbrMap.get(kSearch.getKeyString());
			//
			// LOGGER.debug("--> sellerMbr {} : {}", kSearch.getKeyString(), sellerMbr);
			// }
		}

		assertThat(searchMbrSellerResponse, notNullValue());
		assertThat(searchMbrSellerResponse.getSellerMbrListMap(), notNullValue());

		assertSame(
				searchMbrSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자 기본정보 조회.
	 */
	@Test
	public void searchSeller() {

		SearchSellerRequest searchSellerRequest = new SearchSellerRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		searchSellerRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();

		KeySearch keySearch = new KeySearch();
		// keySearch.setKeyType("BIZ_REG_NO");
		// keySearch.setKeyString("1048636968"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		// 암호화 확인
		// SE201403271801283330001930 333B59F7C9A602807E8BF80DB9F1FA89 8812282123456
		// IF1423199008820101115151344 f9f201cf85746a1cd34e4ace73c761bd

		keySearch = new KeySearch();
		keySearch.setKeyType("INSD_SELLERMBR_NO");
		keySearch.setKeyString("IF1023530314420111128165556"); // IF1023530314420111128165556
		keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType("EMAIL");
		// keySearch.setKeyString("op_tabs_1001@gmail.com"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		searchSellerRequest.setKeySearchList(keySearchList);

		// IF1023361425020110414102136 법정대리인키
		// IF1023530314420111128165556 인증키
		// IF102815946620090819200153 멀티미디어 권한
		SearchSellerResponse searchSellerResponse = this.sellerSCI.searchSeller(searchSellerRequest);

		if (searchSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchSellerResponse.getCommonResponse().toString());
		if (searchSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchSellerResponse.toString());
		if (searchSellerResponse.getSellerMbr() != null)
			LOGGER.info("### 서버에서 내려받은 값 3: {}", searchSellerResponse.getSellerMbr().toString());
		if (searchSellerResponse.getMbrPwd() != null)
			LOGGER.info("### 서버에서 내려받은 값 4: {}", searchSellerResponse.getMbrPwd().toString());
		if (searchSellerResponse.getMbrLglAgent() != null)
			LOGGER.info("### 서버에서 내려받은 값 5: {}", searchSellerResponse.getMbrLglAgent().toString());
		if (searchSellerResponse.getMbrAuth() != null)
			LOGGER.info("### 서버에서 내려받은 값 6: {}", searchSellerResponse.getMbrAuth().toString());
		// if (searchSellerResponse.getMbrClauseAgreeList() != null)
		// LOGGER.info("### 서버에서 내려받은 값 7: {}", searchSellerResponse.getMbrClauseAgreeList().toString());
		if (searchSellerResponse.getExtraRightList() != null)
			LOGGER.info("### 서버에서 내려받은 값 8: {}", searchSellerResponse.getExtraRightList().toString());
		if (searchSellerResponse.getPWReminderList() != null)
			LOGGER.info("### 서버에서 내려받은 값 9: {}", searchSellerResponse.getPWReminderList().toString());
		if (searchSellerResponse.getTabAuthList() != null)
			LOGGER.info("### 서버에서 내려받은 값 10: {}", searchSellerResponse.getTabAuthList().toString());

		assertThat(searchSellerResponse, notNullValue());

		assertThat(searchSellerResponse.getSellerMbr(), notNullValue());
		assertThat(searchSellerResponse.getMbrPwd(), notNullValue());

		assertSame(
				searchSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자 정산 조회.
	 */
	@Test
	public void searchAccountSeller() {

		SearchAccountSellerRequest searchAccountSellerRequest = new SearchAccountSellerRequest();
		// searchAccountSellerRequest.setSellerKey("SE201404031721050780002039"); // SE201401291339549830000460

		searchAccountSellerRequest.setSellerKey("SE201403261816597080001907"); // 유료회원 // SE201403261816597080001907 //
																			   // IF1023510184220111110165707
		// searchAccountSellerRequest.setSellerKey("SE201402211548426730001157"); // 무료회원 //qa
		// SE201406091424265040001943

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		searchAccountSellerRequest.setCommonRequest(commonRequest);

		// IF1023361425020110414102136 법정대리인키
		// IF1023530314420111128165556 인증키
		// IF102815946620090819200153 멀티미디어 권한
		// IF102815918620090817163550 정산
		// IF1023139253020100810165321 doc

		// US010201 정상 정상
		// US010202 탈퇴 자의탈퇴/직권탈퇴
		// US010203 가가입 가가입
		// US010204 일시정지 로그인제한/직권중지/7일이용정지/30일이용정지/영구이용정지
		// US010205 전환
		//
		// US010301 정상
		// US010302 탈퇴신청
		// US010303 탈퇴완료
		// US010304 가입승인 만료
		// US010305 가입승인 대기
		// US010306 이메일변경승인대기
		// US010307 로그인 제한
		// US010308 직권중지
		// US010309 7일이용정지 7일이용정지
		// US010310 30일이용정지 30일이용정지
		// US010311 영구이용정지 영구이용정지
		// US010312 전환신청
		// US010313 전환재신청
		// US010314 전환거절

		// IF1023499258920130604160101 US010205 US010314
		// IF1023477771920130131165125 US010205 US010312

		SearchAccountSellerResponse searchAccountSellerResponse = this.sellerSCI
				.searchAccountSeller(searchAccountSellerRequest);

		if (searchAccountSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchAccountSellerResponse.getCommonResponse().toString());
		if (searchAccountSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchAccountSellerResponse.toString());
		if (searchAccountSellerResponse.getSellerMbr() != null)
			LOGGER.info("### 서버에서 내려받은 값 3: {}", searchAccountSellerResponse.getSellerMbr().toString());
		if (searchAccountSellerResponse.getExtraRight() != null)
			LOGGER.info("### 서버에서 내려받은 값 8: {}", searchAccountSellerResponse.getExtraRight().toString());
		if (searchAccountSellerResponse.getSellerAccount() != null)
			LOGGER.info("### 서버에서 내려받은 값 9: {}", searchAccountSellerResponse.getSellerAccount().toString());
		if (searchAccountSellerResponse.getDocument() != null)
			LOGGER.info("### 서버에서 내려받은 값 10: {}", searchAccountSellerResponse.getDocument().toString());
		if (searchAccountSellerResponse.getSellerBp() != null)
			LOGGER.info("### 서버에서 내려받은 값 11: {}", searchAccountSellerResponse.getSellerBp().toString());

		assertThat(searchAccountSellerResponse, notNullValue());

		assertThat(searchAccountSellerResponse.getSellerMbr(), notNullValue());
		// assertThat(searchAccountSellerResponse.getSellerAccount(), notNullValue());

		assertSame(
				searchAccountSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자 정산 조회.
	 */
	@Test
	public void searchAccountSeller305() {

		SearchAccountSellerRequest searchAccountSellerRequest = new SearchAccountSellerRequest();
		searchAccountSellerRequest.setSellerKey("SE201406091325462290002486"); // US010311
		// searchAccountSellerRequest.setSellerKey("SE201403261816589640001902"); // US010305
		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		searchAccountSellerRequest.setCommonRequest(commonRequest);

		// IF1023361425020110414102136 법정대리인키
		// IF1023530314420111128165556 인증키
		// IF102815946620090819200153 멀티미디어 권한
		// IF102815918620090817163550 정산
		// IF1023139253020100810165321 doc

		// US010201 정상 정상
		// US010202 탈퇴 자의탈퇴/직권탈퇴
		// US010203 가가입 가가입
		// US010204 일시정지 로그인제한/직권중지/7일이용정지/30일이용정지/영구이용정지
		// US010205 전환
		//
		// US010301 정상
		// US010302 탈퇴신청
		// US010303 탈퇴완료
		// US010304 가입승인 만료
		// US010305 가입승인 대기
		// US010306 이메일변경승인대기
		// US010307 로그인 제한
		// US010308 직권중지
		// US010309 7일이용정지 7일이용정지
		// US010310 30일이용정지 30일이용정지
		// US010311 영구이용정지 영구이용정지
		// US010312 전환신청
		// US010313 전환재신청
		// US010314 전환거절

		// IF1023499258920130604160101 US010205 US010314
		// IF1023477771920130131165125 US010205 US010312

		SearchAccountSellerResponse searchAccountSellerResponse = this.sellerSCI
				.searchAccountSeller(searchAccountSellerRequest);

		if (searchAccountSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchAccountSellerResponse.getCommonResponse().toString());
		if (searchAccountSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchAccountSellerResponse.toString());
		if (searchAccountSellerResponse.getSellerMbr() != null)
			LOGGER.info("### 서버에서 내려받은 값 3: {}", searchAccountSellerResponse.getSellerMbr().toString());
		if (searchAccountSellerResponse.getExtraRight() != null)
			LOGGER.info("### 서버에서 내려받은 값 8: {}", searchAccountSellerResponse.getExtraRight().toString());
		if (searchAccountSellerResponse.getSellerAccount() != null)
			LOGGER.info("### 서버에서 내려받은 값 9: {}", searchAccountSellerResponse.getSellerAccount().toString());
		if (searchAccountSellerResponse.getDocument() != null)
			LOGGER.info("### 서버에서 내려받은 값 10: {}", searchAccountSellerResponse.getDocument().toString());

		assertThat(searchAccountSellerResponse, notNullValue());

		assertThat(searchAccountSellerResponse.getSellerMbr(), notNullValue());
		// assertThat(searchAccountSellerResponse.getSellerAccount(), notNullValue());

		assertSame(
				searchAccountSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 ID 찾기.
	 */
	@Test
	public void searchIDSeller() {

		SearchIDSellerRequest searchIDSellerRequest = new SearchIDSellerRequest();
		// searchIDSellerRequest.setSellerKey("IF1023139253020100810165321");

		searchIDSellerRequest.setSellerEmail("seacaptainj@gmail.com");
		// searchIDSellerRequest.setSellerBizNumber("6211293750");
		// searchIDSellerRequest.setSellerCompany("이지포켓");
		// searchIDSellerRequest.setSellerPhone("01077447714");

		// yjlover01@yopmail.com 이지포켓 6211293750 01077447714
		// op_tabs_3001@gmail.com 참정보 1352668460 010-2345-6789
		// op_tabs_1001@gmail.com 참정보 1352668460 010-2345-6789
		// op_tabs_NaN@gmail.com 참정보 1352668460 010-2345-6789
		// op_tabs_2010@gmail.com 참정보 1352668460 010-2345-6789
		// op_tabs_5001@gmail.com 참정보 1352668460 010-2345-6789

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		searchIDSellerRequest.setCommonRequest(commonRequest);

		// IF1023361425020110414102136 법정대리인키
		// IF1023530314420111128165556 인증키
		// IF102815946620090819200153 멀티미디어 권한
		// IF102815918620090817163550 정산
		// IF1023139253020100810165321 doc
		SearchIDSellerResponse searchIDSellerResponse = this.sellerSCI.searchIDSeller(searchIDSellerRequest);

		if (searchIDSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchIDSellerResponse.getCommonResponse().toString());
		if (searchIDSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchIDSellerResponse.toString());
		if (searchIDSellerResponse.getSellerMbr() != null) {
			LOGGER.info("### 서버에서 내려받은 값 3: {}", searchIDSellerResponse.getSellerMbr().toString());
			LOGGER.info("### 서버에서 내려받은 값 3: {}", searchIDSellerResponse.getSellerMbr().size());
		}

		assertThat(searchIDSellerResponse, notNullValue());

		assertThat(searchIDSellerResponse.getSellerMbr(), notNullValue());

		assertSame(
				searchIDSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 ID/EMAIL 존재여부 확인.
	 */
	@Test
	public void checkDuplicationSeller() {

		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		KeySearch keySearch = new KeySearch();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();

		// keySearch.setKeyType("INSD_SELLERMBR_NO");
		// keySearch.setKeyString("SE201401291339549830000460"); // IF1023530314420111128165556
		// keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType("EMAIL");
		// keySearch.setKeyString("tes@yopmail.com");
		// keySearchList.add(keySearch);
		// sctest1 ,subsctest1

		keySearch = new KeySearch();
		keySearch.setKeyType("SELLERMBR_ID"); // wd 에 있는 정보 anihap-674 (US010317) 6개월 이내 탈퇴 isRegistered: N
											  // wd 에 있는 정보 ixippe-127 6개월 이내 탈퇴 isRegistered: Y resultMessage: 성공
											  // wd 에 있는 정보 qasign11 <- 6개월이전 탈퇴 isRegistered: N ,resultMessage: 성공
											  // sctest2 사용중 isRegistered: Y ,resultMessage: 성공
		keySearch.setKeyString("kwhong20"); // qatestqwe idpid5_20121217 test4442 <- 6개월이내 탈퇴 testdev004 <-
		keySearchList.add(keySearch);

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);
		checkDuplicationSellerRequest.setKeySearchList(keySearchList);

		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		if (checkDuplicationSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", checkDuplicationSellerResponse.getCommonResponse().toString());
		if (checkDuplicationSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", checkDuplicationSellerResponse.toString());
		if (checkDuplicationSellerResponse.getSellerMbr() != null) {
			LOGGER.info("### 서버에서 내려받은 값 3: {}", checkDuplicationSellerResponse.getSellerMbr().toString());
		}

		assertThat(checkDuplicationSellerResponse, notNullValue());

		assertThat(checkDuplicationSellerResponse.getSellerMbr(), notNullValue());
		assertThat(checkDuplicationSellerResponse.getIsRegistered(), notNullValue());
		assertThat(checkDuplicationSellerResponse.getSellerID(), notNullValue());

		assertSame(
				checkDuplicationSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 ID/EMAIL 존재여부 확인. 판매자는 탈퇴하면 ID를 6개월 동안 재사용할 수 없는 기능
	 */
	@Test
	public void checkDuplicationSeller1() {

		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();
		// 6개월 이내 탈퇴한 id 목록
		// test4442
		// anihap-674
		// ixippe-127
		// aind046
		// test3338
		// test4444
		// dream00703

		// SELECT
		// insd_sellermbr_no insd_sellermbr_no,
		// sellermbr_id sellerID,
		// 'Y' isRegistered ,bolter_day ,seller_status_sub_cd
		// FROM tb_us_sellermbr_wd
		// WHERE 1=1
		// AND bolter_day > TO_CHAR (SYSDATE - 185, 'YYYYMMDD' )
		//

		// isRegistered: Y
		// sellerID: ixippe-127
		// sellerMbr: null

		KeySearch keySearch = new KeySearch();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();

		keySearch = new KeySearch();
		keySearch.setKeyType("SELLERMBR_ID");
		keySearch.setKeyString("idpid5_20121217"); // qatestqwe idpid5_20121217 test4442 <- 6개월이내 탈퇴 testdev004 <-
		keySearchList.add(keySearch);

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);
		checkDuplicationSellerRequest.setKeySearchList(keySearchList);

		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		if (checkDuplicationSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", checkDuplicationSellerResponse.getCommonResponse().toString());
		if (checkDuplicationSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", checkDuplicationSellerResponse.toString());
		if (checkDuplicationSellerResponse.getSellerMbr() != null) {
			LOGGER.info("### 서버에서 내려받은 값 3: {}", checkDuplicationSellerResponse.getSellerMbr().toString());
		}

		assertThat(checkDuplicationSellerResponse, notNullValue());

		// assertThat(checkDuplicationSellerResponse.getSellerMbr(), notNullValue());
		assertThat(checkDuplicationSellerResponse.getIsRegistered(), notNullValue());
		// assertThat(checkDuplicationSellerResponse.getSellerID(), notNullValue());

		assertSame(
				checkDuplicationSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 ID/EMAIL 존재여부 확인. Admin이 판매자 ID에 재사용 허가를 했을 때 6개월 이내에라도 사용할 수 있는 기능
	 */
	@Test
	public void checkDuplicationSeller2() {

		// 6개월 이내 탈퇴한 id 목록
		// test4442
		// anihap-674 <- US010317 ( 재사용 가능설정 )
		// ixippe-127
		// aind046
		// test3338
		// test4444
		// dream00703

		// SELECT
		// insd_sellermbr_no insd_sellermbr_no,
		// sellermbr_id sellerID,
		// 'Y' isRegistered ,bolter_day ,seller_status_sub_cd
		// FROM tb_us_sellermbr_wd
		// WHERE 1=1
		// AND bolter_day > TO_CHAR (SYSDATE - 185, 'YYYYMMDD' )
		// AND seller_status_sub_cd != 'US010317'

		// isRegistered: N
		// sellerID: null
		// sellerMbr: null

		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		KeySearch keySearch = new KeySearch();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();

		keySearch = new KeySearch();
		keySearch.setKeyType("SELLERMBR_ID"); // wd 에 있는 정보 anihap-674 (US010317) 6개월 이내 탈퇴 isRegistered: N
											  // wd 에 있는 정보 ixippe-127 6개월 이내 탈퇴 isRegistered: Y resultMessage: 성공
											  // wd 에 있는 정보 qasign11 <- 6개월이전 탈퇴 isRegistered: N ,resultMessage: 성공
											  // sctest2 사용중 isRegistered: Y ,resultMessage: 성공
		keySearch.setKeyString("anihap-674"); // qatestqwe idpid5_20121217 test4442 <- 6개월이내 탈퇴 testdev004 <-
		keySearchList.add(keySearch);

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);
		checkDuplicationSellerRequest.setKeySearchList(keySearchList);

		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		if (checkDuplicationSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", checkDuplicationSellerResponse.getCommonResponse().toString());
		if (checkDuplicationSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", checkDuplicationSellerResponse.toString());
		if (checkDuplicationSellerResponse.getSellerMbr() != null) {
			LOGGER.info("### 서버에서 내려받은 값 3: {}", checkDuplicationSellerResponse.getSellerMbr().toString());
		}

		assertThat(checkDuplicationSellerResponse, notNullValue());

		// assertThat(checkDuplicationSellerResponse.getSellerMbr(), notNullValue());
		assertThat(checkDuplicationSellerResponse.getIsRegistered(), notNullValue());
		// assertThat(checkDuplicationSellerResponse.getSellerID(), notNullValue());

		assertSame(
				checkDuplicationSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 ID/EMAIL 존재여부 확인. 판매자 탈퇴 이후 6 개월 이후 재사용 가능한 기능
	 */
	@Test
	public void checkDuplicationSeller3() {

		// 6개월 이전 탈퇴한 id 목록
		// testhr007 ,20130221
		// tstoretest110 ,20120220
		// signtest22 ,20120105
		// signtest33 ,20120105

		// SELECT
		// insd_sellermbr_no insd_sellermbr_no,
		// sellermbr_id sellerID,
		// 'Y' isRegistered ,bolter_day ,seller_status_sub_cd
		// FROM tb_us_sellermbr_wd
		// WHERE 1=1
		// AND bolter_day < TO_CHAR (SYSDATE - 185, 'YYYYMMDD' )

		// isRegistered: N
		// sellerID: null
		// sellerMbr: null

		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		KeySearch keySearch = new KeySearch();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();

		keySearch = new KeySearch();
		keySearch.setKeyType("SELLERMBR_ID"); // wd 에 있는 정보 anihap-674 (US010317) 6개월 이내 탈퇴 isRegistered: N
											  // wd 에 있는 정보 ixippe-127 6개월 이내 탈퇴 isRegistered: Y resultMessage: 성공
											  // wd 에 있는 정보 qasign11 <- 6개월이전 탈퇴 isRegistered: N ,resultMessage: 성공
											  // sctest2 사용중 isRegistered: Y ,resultMessage: 성공
		keySearch.setKeyString("signtest33"); // qatestqwe idpid5_20121217 test4442 <- 6개월이내 탈퇴 testdev004 <-
		keySearchList.add(keySearch);

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		checkDuplicationSellerRequest.setCommonRequest(commonRequest);
		checkDuplicationSellerRequest.setKeySearchList(keySearchList);

		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		if (checkDuplicationSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", checkDuplicationSellerResponse.getCommonResponse().toString());
		if (checkDuplicationSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", checkDuplicationSellerResponse.toString());
		if (checkDuplicationSellerResponse.getSellerMbr() != null) {
			LOGGER.info("### 서버에서 내려받은 값 3: {}", checkDuplicationSellerResponse.getSellerMbr().toString());
		}

		assertThat(checkDuplicationSellerResponse, notNullValue());

		// assertThat(checkDuplicationSellerResponse.getSellerMbr(), notNullValue());
		assertThat(checkDuplicationSellerResponse.getIsRegistered(), notNullValue());
		// assertThat(checkDuplicationSellerResponse.getSellerID(), notNullValue());

		assertSame(
				checkDuplicationSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 비밀번호 보안질문 확인.
	 */
	@Test
	public void checkPasswordReminderSeller() {

		CheckPasswordReminderSellerRequest checkPasswordReminderSellerRequest = new CheckPasswordReminderSellerRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		checkPasswordReminderSellerRequest.setCommonRequest(commonRequest);

		checkPasswordReminderSellerRequest.setSellerID("sctest1");

		List<PWReminder> pWReminderList = new ArrayList<PWReminder>();

		PWReminder pWReminder = new PWReminder();
		pWReminder.setQuestionID("QUESTION4");
		pWReminder.setAnswerString("Seoul - Korea");
		// pWReminder.setQuestionMessage("qwdwd");
		pWReminder.setSellerID(checkPasswordReminderSellerRequest.getSellerID());
		pWReminderList.add(pWReminder);

		// SE201401162201423580000063
		//
		// pWReminder = new PWReminder();
		// // pWReminder.setQuestionID("QUESTION4");
		// // pWReminder.setAnswerString("심청이");
		// pWReminder.setQuestionID("QUESTION4");
		// pWReminder.setQuestionMessage("qwdwd");
		// pWReminder.setSellerID(checkPasswordReminderSellerRequest.getSellerID());
		// pWReminderList.add(pWReminder);

		checkPasswordReminderSellerRequest.setPWReminderList(pWReminderList);

		CheckPasswordReminderSellerResponse checkPasswordReminderSellerResponse = this.sellerSCI
				.checkPasswordReminderSeller(checkPasswordReminderSellerRequest);

		if (checkPasswordReminderSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", checkPasswordReminderSellerResponse.getCommonResponse().toString());
		if (checkPasswordReminderSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", checkPasswordReminderSellerResponse.toString());

		assertThat(checkPasswordReminderSellerResponse, notNullValue());

		assertThat(checkPasswordReminderSellerResponse.getIsCorrect(), notNullValue());

		assertSame(
				checkPasswordReminderSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 비밀번호 변경.
	 */
	@Test
	public void updatePasswordSeller() {

		UpdatePasswordSellerRequest updatePasswordSellerRequest = new UpdatePasswordSellerRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		updatePasswordSellerRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("scteamtest003"); // 셀러 id 를 넣어야한다.
		mbrPwd.setOldPW("test111"); // 과거 pass
		mbrPwd.setMemberPW("test111"); // new pass

		updatePasswordSellerRequest.setMbrPwd(mbrPwd);

		UpdatePasswordSellerResponse updatePasswordSellerResponse = this.sellerSCI
				.updatePasswordSeller(updatePasswordSellerRequest);

		if (updatePasswordSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updatePasswordSellerResponse.getCommonResponse().toString());
		if (updatePasswordSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updatePasswordSellerResponse.toString());

		assertThat(updatePasswordSellerResponse, notNullValue());

		assertThat(updatePasswordSellerResponse.getSellerKey(), notNullValue());

		assertSame(
				updatePasswordSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		assertThat(updatePasswordSellerResponse.getSellerKey(), notNullValue());

	}

	/**
	 * 판매자회원 새로운 비밀번호 변경.
	 */
	@Test
	public void updateNewPasswordSeller() {

		UpdateNewPasswordSellerRequest updateNewPasswordSellerRequest = new UpdateNewPasswordSellerRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		updateNewPasswordSellerRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("scteamtest003"); // 셀러 id 를 넣어야한다.
		// mbrPwd.setOldPW("test111"); // 과거 pass
		mbrPwd.setMemberPW("test111"); // new pass

		updateNewPasswordSellerRequest.setMbrPwd(mbrPwd);

		UpdateNewPasswordSellerResponse updateNewPasswordSellerResponse = this.sellerSCI
				.updateNewPasswordSeller(updateNewPasswordSellerRequest);

		if (updateNewPasswordSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updateNewPasswordSellerResponse.getCommonResponse().toString());
		if (updateNewPasswordSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updateNewPasswordSellerResponse.toString());

		assertThat(updateNewPasswordSellerResponse, notNullValue());

		assertThat(updateNewPasswordSellerResponse.getSellerKey(), notNullValue());

		assertSame(
				updateNewPasswordSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		assertThat(updateNewPasswordSellerResponse.getSellerKey(), notNullValue());

	}

	/**
	 * 판매자회원 비밀번호 초기화.
	 */
	@Test
	public void resetPasswordSeller() {

		ResetPasswordSellerRequest resetPasswordSellerRequest = new ResetPasswordSellerRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		resetPasswordSellerRequest.setCommonRequest(commonRequest);
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("sellertest005"); // 셀러 id 를 넣어야한다. << 이게 서브계정이면 실패
		// mbrPwd.setMemberID("biz_7908"); // 셀러 id 를 넣어야한다.
		// mbrPwd.setOldPW("1234"); // 과거 pass
		// mbrPwd.setPW("new_4321"); // new pass
		// Utils.getInitPassword();
		// 서브셀러
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest1, 123456 , '' , SS201402051035009830000589
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest2, 123456 , '' , SS201402051052009220000590

		resetPasswordSellerRequest.setMbrPwd(mbrPwd);

		ResetPasswordSellerResponse resetPasswordSellerResponse = this.sellerSCI
				.resetPasswordSeller(resetPasswordSellerRequest);

		if (resetPasswordSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", resetPasswordSellerResponse.getCommonResponse().toString());
		if (resetPasswordSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", resetPasswordSellerResponse.toString());

		assertThat(resetPasswordSellerResponse.getSellerPW(), notNullValue());

		assertSame(
				resetPasswordSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		assertThat(resetPasswordSellerResponse.getSellerPW(), notNullValue());
	}

	/**
	 * 로그인.
	 */
	// @Ignore
	@Test
	public void loginSeller() {

		// vo 생성
		LoginSellerRequest loginSellerRequest = new LoginSellerRequest();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("ttt");
		commonRequest.setSystemID("W");

		// 최종 vo 에 값 셋팅 aaaaa 1234
		loginSellerRequest.setSellerID("sctest0001_sub"); // idpid5_20121217

		// 가가입 이고 id pass 정상인경우 scteamtest010

		// loginSellerRequest.setSellerID("testdev004"); // idpid5_20121217
		// loginSellerRequest.setSellerID("idpid5_20121217"); //
		// loginSellerRequest.setSellerID("apitesttest");
		loginSellerRequest.setSellerPW("test111");

		// loginSellerRequest.setIsSimple("Y");

		// 국내유무, 판매자구분, 판매자분류, ID, pass , 이름 , sellerKey
		// 국내, 개인(US010101), 무료(US011301), sctest1, 123456 , 국내개인무료사용자 , SE201402051026319660000583
		// 국내, 개인사업자(US010102), 유료(US011302), sctest2, 123456 , 국내개인사업자유료 , SE201402051027290360000584
		// 국내, 법인사업자(US010103), 유료(US011302), sctest3, 123456 , 국내법인사업자유료 , SE201402051027587210000585

		// 국외, 개인(US010101), 무료(US011301), sctest4, 123456 , 국외개인무료사용자 , SE201402051028461120000586
		// 국외, 개인사업자(US010102), 유료(US011302), sctest5, 123456 , 국외개인사업자유료 , SE201402051029143200000587
		// 국외, 법인사업자(US010103), 유료(US011302), sctest6, 123456 , 국외법인사업자유료 , SE201402051030427530000588

		// 서브셀러
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest1, 123456 , '' , SS201402051035009830000589
		// 부모 SE201402051027587210000585 //sctest3
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest2, 123456 , '' , SS201402051052009220000590

		// idpid5_20121217
		// FR21R/X4xRkrVm598w+JK4oW++/bqXP2+qMAZcg7/Cg=

		// loginSellerRequest.setSellerID("tstoretest13");
		// loginSellerRequest.setSellerPW("cS1DhNOYV5JbVO+uLyOk9zltEQEOJFwc9/srL8z02dA=");

		// 가가입자 IF1023479172020111005181208 tstoretest636 aaaa15
		// loginSellerRequest.setSellerID("aaaa15");

		loginSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", loginSellerRequest.toString());

		LoginSellerResponse loginSellerResponse = this.sellerSCI.loginSeller(loginSellerRequest);

		if (loginSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", loginSellerResponse.getCommonResponse().toString());
		if (loginSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", loginSellerResponse.toString());
		LOGGER.info("### 서버에서 내려받은 값 3: {}", loginSellerResponse.getLoginFailCount());

		assertThat(loginSellerResponse, notNullValue());

		CommonResponse commonResponse = loginSellerResponse.getCommonResponse();
		// 응답
		assertThat(loginSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > getSellerClass
		assertNotNull(loginSellerResponse.getSellerClass());
		// 응답 필수 > getSellerCategory
		assertNotNull(loginSellerResponse.getSellerCategory());

		// 응답 필수 > sellerKey
		assertNotNull(loginSellerResponse.getSellerKey());
		// 응답 필수 > sellerMainStatus
		assertNotNull(loginSellerResponse.getSellerMainStatus());
		// 응답 필수 > sellerSubStatus
		assertNotNull(loginSellerResponse.getSellerSubStatus());
		// 응답 필수 > loginFailCount
		assertTrue(loginSellerResponse.getLoginFailCount() >= 0);
		// 응답 필수 > isLoginSuccess
		assertNotNull(loginSellerResponse.getIsLoginSuccess());

	}

	/**
	 * <pre>
	 * 판매자 회원 기본정보 수정.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void updateSeller() {

		// vo 생성
		UpdateSellerRequest updateSellerRequest = new UpdateSellerRequest();

		CommonRequest commonRequest = new CommonRequest();
		// 값셋팅
		commonRequest.setTenantID("S01");

		/** 판매자정보 Value Object. */
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey("SE201403171051151300001723");
		sellerMbr.setSellerName("aaa");
		sellerMbr.setWebsite("http://skt.com");
		sellerMbr.setSellerID("scteamtest003");
		sellerMbr.setSellerCategory(SellerCategoryCode.PAY_SELLER.getCode());
		sellerMbr.setSellerClass(SellerClassCode.PRIVATE_SELLER.getCode());
		sellerMbr.setSellerMainStatus("US010201");
		sellerMbr.setSellerSubStatus("US010301");
		sellerMbr.setSellerEmail("jloveonly@naver.com");
		sellerMbr.setIsDomestic("Y");
		sellerMbr.setSellerPhone("0100000000");
		sellerMbr.setIsTaxable("Y");
		sellerMbr.setSellerBirthDay("20140318");

		/** 실명인증 Value Object. */
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		// mbrAuth.setCi("cici");

		/** 법정대리인 Value Object. */
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentName("부모이름");
		// mbrLglAgent.setParentCI("부모ci");
		// mbrLglAgent.setParentRealNameMethod("US005302");

		List<PWReminder> pWReminderList;
		pWReminderList = new ArrayList<PWReminder>();
		PWReminder pWReminder = new PWReminder();
		pWReminder.setQuestionID("WHERE1");
		pWReminder.setAnswerString("Seoul - Korea");
		pWReminder.setQuestionMessage("test");

		pWReminderList.add(pWReminder);
		pWReminder = new PWReminder();
		pWReminder.setQuestionID("Q123");
		pWReminder.setAnswerString("temp");
		pWReminder.setQuestionMessage("qwdwd");
		pWReminderList.add(pWReminder);

		updateSellerRequest.setPWReminderList(pWReminderList);

		// 최종 vo 에 값 셋팅
		updateSellerRequest.setCommonRequest(commonRequest);
		updateSellerRequest.setSellerMbr(sellerMbr);
		updateSellerRequest.setMbrAuth(mbrAuth);
		updateSellerRequest.setMbrLglAgent(mbrLglAgent);

		LOGGER.debug("### 넘긴 데이터 : {}", updateSellerRequest.toString());

		UpdateSellerResponse updateSellerResponse = this.sellerSCI.updateSeller(updateSellerRequest);
		CommonResponse commonResponse = updateSellerResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", updateSellerResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(updateSellerResponse, notNullValue());

		assertThat(updateSellerResponse.getSellerID(), notNullValue());
		assertThat(updateSellerResponse.getSellerKey(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 사용자회원 탈퇴.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void removeSeller() {

		// vo 생성
		RemoveSellerRequest removeSellerRequest = new RemoveSellerRequest();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("ttt");

		// 최종 vo 에 값 셋팅
		removeSellerRequest.setSellerKey("SE201404141304185510002104"); // SE201403031522107230001389
		removeSellerRequest.setSecedeReasonCode("US010404");
		removeSellerRequest.setSecedeReasonMessage("");
		removeSellerRequest.setCommonRequest(commonRequest);
		// removeSellerRequest.setSecedePathCd("test");

		LOGGER.debug("### 넘긴 데이터 : {}", removeSellerRequest.toString());

		RemoveSellerResponse removeSellerResponse = this.sellerSCI.removeSeller(removeSellerRequest);
		CommonResponse commonResponse = removeSellerResponse.getCommonResponse();
		LOGGER.debug("### 받은 데이터 1: {}", removeSellerResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(removeSellerResponse, notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 판매자회원 상태변경.
	 */
	@Test
	public void updateStatusSeller() {

		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		updateStatusSellerRequest.setSellerID("scteamtest003"); // SE201403171001348990001714
		// updateStatusSellerRequest.setSellerMainStatus("sss");
		updateStatusSellerRequest.setSellerSubStatus("US010301");
		updateStatusSellerRequest.setStopStatusCode("08");
		updateStatusSellerRequest.setLoginStatusCode("10");
		updateStatusSellerRequest.setIsNewEntry("Y");

		updateStatusSellerRequest.setCommonRequest(commonRequest);

		UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
				.updateStatusSeller(updateStatusSellerRequest);

		if (updateStatusSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updateStatusSellerResponse.getCommonResponse().toString());
		if (updateStatusSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updateStatusSellerResponse.toString());

		assertThat(updateStatusSellerResponse, notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * test 서브계정 create.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void createSubSeller() {
		// vo 생성
		CreateSubSellerRequest createSubSeller = new CreateSubSellerRequest();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		createSubSeller.setCommonRequest(commonRequest);
		SellerMbr sellerMbr = new SellerMbr(); // IF102102956220120711144039 한서구 SS201402041452205570000558
		sellerMbr.setParentSellerKey("SE201404141344178950002108"); // IF1023109283920120614112420
																	// US201401231325534800000164
																	// IF1023501437920130904104346
		sellerMbr.setSellerID("sctest0001_subs"); // test4442 <- 6개월이내 탈퇴 testdev004 <- 2013 2월 탈퇴

		// anihap-1234s //SS201402251032360710001257
		// anihap-1234 //SS201402251027090020001256
		// anihap-674sscc -> SS201402251016353940001253 SS201402251017063490001254

		// wd 에 있는 정보 anihap-674 (US010317) 6개월 이내 탈퇴 (생성가능 )
		// wd 에 있는 정보 ixippe-127 6개월 이내 탈퇴 (생성 불가능 )
		// wd 에 있는 정보 qasign11 <- 6개월이전 탈퇴 (생성가능 )
		// sctest2 사용중 isRegistered: Y ,resultMessage: 성공 (생성 불가능 )

		sellerMbr.setSellerKey("SS201402251930273010001302");
		sellerMbr.setSubSellerMemo("anihap-1234s 의 서브셀러33");
		sellerMbr.setSellerEmail("sctest32s1@sk,com");
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberPW("test111");
		// mbrPwd.setOldPW("123456");
		createSubSeller.setIsNew("Y");
		createSubSeller.setMbrPwd(mbrPwd);
		createSubSeller.setSellerMbr(sellerMbr);

		CreateSubSellerResponse createSubSellerResponse = this.sellerSCI.createSubSeller(createSubSeller);
		CommonResponse commonResponse = createSubSellerResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", createSubSellerResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(createSubSellerResponse, notNullValue());

		assertThat(createSubSellerResponse.getSellerKey(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createSubSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 서브계정 탈퇴.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void removeSubSeller() {

		// vo 생성
		RemoveSubSellerRequest removeSubSellerRequest = new RemoveSubSellerRequest();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("ttt");

		removeSubSellerRequest.setParentSellerKey("SE201403272110151040001944"); // IF1023501437920130904104346
		// 최종 vo 에 값 셋팅
		List<String> removeKeyList;

		removeKeyList = new ArrayList<String>();
		removeKeyList.add("SS201403291436593920001970"); // US201312311522096210000038
		// removeKeyList.add("US2013123115220962100000382");

		removeSubSellerRequest.setSellerKeyList(removeKeyList);
		removeSubSellerRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", removeSubSellerRequest.toString());

		RemoveSubSellerResponse removeSubSellerResponse = this.sellerSCI.removeSubSeller(removeSubSellerRequest);
		CommonResponse commonResponse = removeSubSellerResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", removeSubSellerResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		LOGGER.debug("### 받은 데이터 3 : {}", removeSubSellerResponse.getDeletedNumber());

		assertThat(removeSubSellerResponse, notNullValue());

		assertThat(removeSubSellerResponse.getDeletedNumber(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeSubSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * 서브계정 목록 조회.
	 */
	@Test
	public void searchSubSellerList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 서브계정 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchSubSellerListRequest searchSubSellerListRequest;
		SearchSubSellerListResponse searchSubSellerListResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchSubSellerListRequest = new SearchSubSellerListRequest();
		searchSubSellerListRequest.setParentSellerKey("IF102158838420090627230353"); // IF1023477771920130131165125
		searchSubSellerListRequest.setLoginSort("ASC"); // ASC , DESC

		/** 판매자 부모키 키. */
		// private String parentSellerKey;

		// IF1023501437920130904104346
		searchSubSellerListRequest.setCommonRequest(commonRequest);

		// 조회
		searchSubSellerListResponse = this.sellerSCI.searchSubSellerList(searchSubSellerListRequest);

		if (searchSubSellerListResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", searchSubSellerListResponse.getCommonResponse().toString());
		if (searchSubSellerListResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", searchSubSellerListResponse.toString());

		LOGGER.info("### 서버에서 내려받은 값 4: {}", searchSubSellerListResponse.getSubAccountCount());

		// 응답
		assertThat(searchSubSellerListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchSubSellerListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchSubSellerListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchSubSellerListResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchSubSellerListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		if (searchSubSellerListResponse.getSubSellerList() != null) {
			for (SellerMbr sellerMbr : searchSubSellerListResponse.getSubSellerList()) {
				LOGGER.debug("응답 , sellerMbr : {}", sellerMbr);
				// 서브계정 아이디
				assertThat(sellerMbr.getSellerID(), notNullValue());
				// 서브계정 이메일
				assertThat(sellerMbr.getSellerEmail(), notNullValue());
				// 서브계정 등록일
				assertThat(sellerMbr.getRegDate(), notNullValue());
			}
		}

		assertThat(searchSubSellerListResponse.getSellerID(), notNullValue());
		assertThat(searchSubSellerListResponse.getSellerKey(), notNullValue());
		assertThat(searchSubSellerListResponse.getSubAccountCount(), notNullValue());

	}

	/**
	 * 서브계정 상세 조회.
	 */
	@Test
	public void searchSubSeller() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 서브계정 상세 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchSubSellerRequest searchSubSellerRequest;
		SearchSubSellerResponse searchSubSellerResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchSubSellerRequest = new SearchSubSellerRequest();
		searchSubSellerRequest.setSellerKey("SS201403291438501430001974");
		searchSubSellerRequest.setCommonRequest(commonRequest);

		// 국내유무, 판매자구분, 판매자분류, ID, pass , 이름 , sellerKey
		// 국내, 개인(US010101), 무료(US011301), sctest1, 123456 , 국내개인무료사용자 , SE201402051026319660000583
		// 국내, 개인사업자(US010102), 유료(US011302), sctest2, 123456 , 국내개인사업자유료 , SE201402051027290360000584
		// 국내, 법인사업자(US010103), 유료(US011302), sctest3, 123456 , 국내법인사업자유료 , SE201402051027587210000585

		// 국외, 개인(US010101), 무료(US011301), sctest4, 123456 , 국외개인무료사용자 , SE201402051028461120000586
		// 국외, 개인사업자(US010102), 유료(US011302), sctest5, 123456 , 국외개인사업자유료 , SE201402051029143200000587
		// 국외, 법인사업자(US010103), 유료(US011302), sctest6, 123456 , 국외법인사업자유료 , SE201402051030427530000588

		// 서브셀러
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest1, 123456 , '' , SS201402051035009830000589
		// 부모 SE201402051027587210000585 //sctest3
		// 국내, 법인사업자(US010103), 유료(US011302), subsctest2, 123456 , '' , SS201402051052009220000590

		// 조회
		searchSubSellerResponse = this.sellerSCI.searchSubSeller(searchSubSellerRequest);

		LOGGER.info("### 응답 : {}", searchSubSellerResponse);

		// 응답
		assertThat(searchSubSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchSubSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchSubSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchSubSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchSubSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		if (searchSubSellerResponse.getSellerMbr() != null) {
			SellerMbr sellerMbr = searchSubSellerResponse.getSellerMbr();
			LOGGER.debug("응답 , sellerMbr : {}", sellerMbr);
			// 서브계정 아이디
			assertThat(sellerMbr.getSellerID(), notNullValue());
			// 서브계정 이메일
			assertThat(sellerMbr.getSellerEmail(), notNullValue());
			// 서브계정 등록일
			assertThat(sellerMbr.getRegDate(), notNullValue());
		}

	}

	/**
	 * <pre>
	 * 판매자 약관동의 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateAgreement() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 판매자 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateAgreementSellerRequest updateAgreementSellerRequest;
		UpdateAgreementSellerResponse updateAgreementSellerResponse;
		MbrClauseAgree mbrClauseAgree;
		List<MbrClauseAgree> mbrClauseAgreeList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S00");

		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
		mbrClauseAgree = new MbrClauseAgree();

		mbrClauseAgree.setExtraAgreementID("US012101");
		mbrClauseAgree.setIsExtraAgreement("Y");
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 요청 필수
		updateAgreementSellerRequest = new UpdateAgreementSellerRequest();
		updateAgreementSellerRequest.setCommonRequest(commonRequest);
		updateAgreementSellerRequest.setSellerKey("IF142348042772013020816484512");
		updateAgreementSellerRequest.setMbrClauseAgreeList(mbrClauseAgreeList);

		// 요청
		updateAgreementSellerResponse = this.sellerSCI.updateAgreementSeller(updateAgreementSellerRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 1 : {}", updateAgreementSellerResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 2 : {}", updateAgreementSellerResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 3 : {}", updateAgreementSellerResponse.getAgreementCount());

		// 응답
		assertThat(updateAgreementSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateAgreementSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateAgreementSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateAgreementSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateAgreementSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 판매자 약관동의 목록 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchAgreementListSeller() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 판매자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreementListSellerRequest searchAgreementListSellerRequest;
		SearchAgreementListSellerResponse searchAgreementListSellerResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S00");

		// 요청 필수
		searchAgreementListSellerRequest = new SearchAgreementListSellerRequest();
		searchAgreementListSellerRequest.setCommonRequest(commonRequest);
		searchAgreementListSellerRequest.setSellerKey("IF1423480427720130208164845");

		// 요청
		searchAgreementListSellerResponse = this.sellerSCI.searchAgreementListSeller(searchAgreementListSellerRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchAgreementListSellerResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchAgreementListSellerResponse.getCommonResponse());

		// 응답
		assertThat(searchAgreementListSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchAgreementListSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchAgreementListSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchAgreementListSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchAgreementListSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > sellerKey
		assertThat(searchAgreementListSellerResponse.getSellerKey(), notNullValue());
		// 응답필수 > 약관동의 목록
		assertThat(searchAgreementListSellerResponse.getMbrClauseAgreeList(), notNullValue());
		// 응답 필수 > 약관동의 목록
		List<MbrClauseAgree> mbrClauseAgreeList = searchAgreementListSellerResponse.getMbrClauseAgreeList();
		for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
			// 약관동의 ID
			assertNotNull(mbrClauseAgree.getExtraAgreementID());
			// 약관동의여부
			assertNotNull(mbrClauseAgree.getIsExtraAgreement());
		}

	}

	/**
	 * <pre>
	 * 실명인증 정보를 수정 테스트
	 * 실명인증 대상 : 본인
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateRealNameSeller() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 실명인증 정보 수정(본인)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameSellerRequest updateRealNameSellerRequest = new UpdateRealNameSellerRequest();
		UpdateRealNameSellerResponse updateRealNameSellerResponse;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		updateRealNameSellerRequest.setCommonRequest(commonRequest);

		// 실명인증 대상 설정 : 본인용
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		// mbrAuth.setCi("cici");

		/** 법정대리인 Value Object. */
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		// mbrLglAgent.setParentRealNameMethod("US005302");
		mbrLglAgent.setParentDate("29991231235959");
		mbrLglAgent.setParentType("sss");
		mbrLglAgent.setParentEmail("sss@naver.com");

		// 요청 필수
		// updateRealNameSellerRequest.setIsOwn(Constant.REAL_NAME_TYPE_PARANT);
		updateRealNameSellerRequest.setIsOwn(Constant.REAL_NAME_TYPE_OWN);
		updateRealNameSellerRequest.setSellerKey("IF102158838420090627230353");
		updateRealNameSellerRequest.setMbrAuth(mbrAuth);
		updateRealNameSellerRequest.setMbrLglAgent(mbrLglAgent);

		// 수정요청 결과
		updateRealNameSellerResponse = this.sellerSCI.updateRealNameSeller(updateRealNameSellerRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateRealNameSellerResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateRealNameSellerResponse.getCommonResponse());

		// 응답
		assertThat(updateRealNameSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateRealNameSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		assertThat(updateRealNameSellerResponse.getSellerKey(), notNullValue());

	}

	/**
	 * <pre>
	 * 실명인증 정보를 수정 테스트
	 * 실명인증 대상 : 법정대리인
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateRealNameParnet() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 실명인증 정보 수정-법정대리인(부모)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameSellerRequest updateRealNameSellerRequest = new UpdateRealNameSellerRequest();
		UpdateRealNameSellerResponse updateRealNameSellerResponse;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		updateRealNameSellerRequest.setCommonRequest(commonRequest);

		// 실명인증 대상 설정 : 법정대리인용
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("부모이");

		// mbrLglAgent.setParentRealNameMethod("US005301");

		// 요청 필수
		updateRealNameSellerRequest.setIsOwn(Constant.REAL_NAME_TYPE_PARANT);
		updateRealNameSellerRequest.setSellerKey("SE201403141346074800001665");
		updateRealNameSellerRequest.setMbrLglAgent(mbrLglAgent);

		// 요청 결과
		updateRealNameSellerResponse = this.sellerSCI.updateRealNameSeller(updateRealNameSellerRequest);

		// 응답
		assertThat(updateRealNameSellerResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameSellerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameSellerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameSellerResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				updateRealNameSellerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		assertThat(updateRealNameSellerResponse.getSellerKey(), notNullValue());

	}

	/**
	 * 판매자회원 전환신청.
	 */
	@Test
	public void upgradeSeller() {

		UpgradeSellerRequest upgradeSellerRequest = new UpgradeSellerRequest();
		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		upgradeSellerRequest.setCommonRequest(commonRequest);
		upgradeSellerRequest.setSellerKey("SE201403271521209220001927"); // DEV
		// upgradeSellerRequest.setSellerKey("SE201405211538526910001886"); // QA

		// IF102158838420090627230353 멀티미디어 TB_US_SELLERMBR_MULTIMDA_AUTH
		// IF1023139253020100810165321 문서 TB_US_SELLERMBR_DOC
		// IF1023229565820110106110241 정산 TB_US_SELLERMBR_CHRG_SETT_ACCT

		SellerMbr sellerMbr = new SellerMbr();
		SellerUpgrade sellerUpgrade = new SellerUpgrade();
		List<ExtraRight> extraRightList = new ArrayList<ExtraRight>();
		List<Document> documentList = new ArrayList<Document>();

		// 판매자구분 코드
		// US010101
		// 개인
		//
		// US010102
		// 개인사업자
		//
		// US010103
		// 법인사업자

		// 판매자분류 코드
		// US011301
		// 무료 (정산정보가 없다 )
		//
		// US011302
		// 유료 (정산정보가 있다 )
		//
		// US011303
		// BP

		// US001501 유료전환 무료개발자 개인셀러 Y 무료개발자에서 유료개인개발자로 전환
		// US001502 개인사업자가입 무료개발자 개인사업자 Y 무료개발자에서 유료개인사업자로 전환
		// US001503 법인사업자가입 무료개발자 법인사업자 Y 무료개발자에서 유료법인사업자로 전환
		// US001504 개인사업자전환 개인셀러 개인사업자 Y 유료개인개발자에서 유료개인사업자로 전환
		// US001505 법인사업자전환 개인셀러 법인사업자 Y 유료개인개발자에서 유료법인사업자로 전환
		// US001506 사업자간전환 개인사업자 법인사업자 Y 유료개인사업자에서 유료법인사업자로 전환

		// US001506 사업자간전환 개인사업자 법인사업자 Y 무료개인사업자에서 유료법인사업자로 전환
		// US001506 사업자간전환 개인사업자 법인사업자 Y 무료법인사업자에서 유료법인사업자로 전환

		// US001507 해외개발자전환 해외개발자 정산정보 Y 해외개발자 정산정보 입력 전환

		// sellerUpgrade.setChangedCd("US001507"); // CHANGED_CASE_CD 전환 유형코드 (US001507,US001503,US001501)

		// 판매자 기본테이블에서 처리할부분// 업데이트 하도록 하자.
		sellerMbr.setSellerKey(upgradeSellerRequest.getSellerKey());
		// sellerMbr.setSellerClass("US000101"); // 개인법인구분코드 (US000101 개인 ,US000102 사업자)
		// sellerMbr.setSellerCategory("US005501"); // 유료 무료 US005501 US005501 DB 컬럼에는 없는데 받아야한다.

		// sellerMbr.setSellerCategory(SellerCategoryCode.PAY_SELLER.getCode());
		// sellerMbr.setSellerClass(SellerClassCode.PRIVATE_SELLER.getCode());
		sellerMbr.setSellerCategory(SellerCategoryCode.FREE_SELLER.getCode());
		sellerMbr.setSellerClass(SellerClassCode.PRIVATE_BUSINESS_SELLER.getCode());

		sellerMbr.setSellerMainStatus("US010201");
		sellerMbr.setSellerSubStatus("US010311"); // US000804 전환신청 US010305 이면 재신청이다.
		sellerMbr.setSellerID("kwhong41"); // DEV
		// sellerMbr.setSellerID("rejoice_jpn_dev001"); // QA
		// 질문setSellerClassTo 을 setSellerClass 에 set 해야 하는것인가?
		upgradeSellerRequest.setSellerMbr(sellerMbr);

		// 판매자 테이블 수정 >> insert 하도록 하자. (종목 업태부터)
		sellerUpgrade.setSellerKey(upgradeSellerRequest.getSellerKey());
		// DB 에 있음
		sellerUpgrade.setSellerClassTo("US010103"); // BIZ_KIND_CD(US000901,US000904) 신청 구분코드(개인/사업자/법인사업자)
		sellerUpgrade.setCharger("홍길동"); // CHRGPERS_NM 담당자명 >> api 에 포함되지 않아도 되는부분이다. member 에서 가져와도 된다.
		sellerUpgrade.setRepEmail("aind050@yopmail.com"); // ("대표 이메일"); 회원 REP_EMAIL > 전환 CHRGPERS_EMAIL
		sellerUpgrade.setCordedTelephone("0262575958"); // 유선 전화번호 CHRGPERS_WIRE_TEL_NO >member 테이블네 넣을때는 WIRE_TEL_NO
		sellerUpgrade.setSellerPhone("010203444988"); // CHRGPERS_WILS_TEL_NO 담당자 무선전화번호 >> member 테이블네 넣을때는
													  // WILS_TEL_NO
		sellerUpgrade.setIsRecvSMS("Y"); // SMS_RECV_YN 넣으면 될듯
		sellerUpgrade.setIsTaxable("Y");

		sellerUpgrade.setCeoName("사장님"); // CEO_NM// 추가된것 같음
		sellerUpgrade.setSellerBizCorpNumber("xxxx"); // ("법인등록번호"); CORP_REG_NO
		sellerUpgrade.setSellerBizType("업종"); // INDT_NM 업종명 종목 종목
		sellerUpgrade.setSellerBizCategory("업태"); // COND_NM 업태명 업태 업태

		sellerUpgrade.setIsOfficialAuth("Y"); // PUB_AUTH_YN PUB_AUTH_YN 공인인증여부 >
		sellerUpgrade.setBankAccount("0112233322511"); // ACCT_NO 계좌번호
		sellerUpgrade.setBankCode("123"); // BANK_CD 은행코드
		sellerUpgrade.setBankAcctName("홍길동"); // DEPSTR_NM 예금자명
		sellerUpgrade.setIsAccountReal("Y"); // ACCT_AUTH_YN 계좌인증여부
		sellerUpgrade.setSellerCompany("와이즈스톤"); // COMP_NM 회사명 >> api 추가 하지말고 판매자 테이블에서 가져온다.
		sellerUpgrade.setSellerBizNumber("0055448866"); // BIZ_REG_NO 사업자등록번호 >> api 추가 하지말고 판매자 테이블에서 가져온다.
		sellerUpgrade.setSellerBizCategory("XXXXX"); // COND_NM 업태명
		sellerUpgrade.setSellerBizType("이동통신"); // INDT_NM(서비스,이동통신,영상제작,시스템유지보수) 업종명
		// sellerUpgrade.setSellerClassTo("US000904"); // BIZ_KIND_CD(US000901,US000904) 신청 구분코드(개인/사업자/법인사업자)
		sellerUpgrade.setIsBizTaxable("N"); // EASY_TXNPERS_YN 간이과세여부
		sellerUpgrade.setCustomerPhone("0262575958"); // REP_TEL_NO 대표전화번호

		sellerUpgrade.setCeoName("사장님"); //
		sellerUpgrade.setSellerBizCorpNumber("5588775566"); // CORP_REG_NO 법인등록번호
		sellerUpgrade.setSellerBizZip("120757"); // ENPRPL_ZIP 우편번호
		sellerUpgrade.setSellerBizAddress("서울시 서대문구 대현동"); // ENPRPL_ADDR 주소 의경우
		sellerUpgrade.setSellerBizDetailAddress("럭키 아파트 101동 909호");
		sellerUpgrade.setIsBizRegistered("Y"); // CMNT_SALBIZ_DECL_YN 통신판매업 신고여부
		sellerUpgrade.setBizRegNumber("11223344"); // CMNT_SALBIZ_DECL_NO 통신판매업 신고번호
		sellerUpgrade.setBizUnregReason("554411"); // CMNT_SALBIZ_UNDECL_REASON_CD 통신판매업 미신고사유
		sellerUpgrade.setChangedCd("US001507"); // CHANGED_CASE_CD 전환 유형코드 (US001507,US001503,US001501)
		// sellerUpgrade.setChargedDate("XXXXX"); // CHRG_CHANGED_DAY 유료전환일 << api 불필요
		// TO_CHAR(SYSDATE, 'YYYYMMDD'), // APPR_REQ_DAY 승인 요청일 << 등록시 현재시간 넣는다.
		sellerUpgrade.setSellerTelecom("SKT"); // MNO_CD 통신사 코드 >> api 추가 하지말고 판매자 테이블에서 가져온다.
		sellerUpgrade.setBankName("우리은행"); // FR_BANK_NM 은행명
		sellerUpgrade.setBankBranchCode("A01"); // FR_BANK_NM 은행명
		sellerUpgrade.setBankBranch("반포지점"); // FR_BRCH_NM 은행지점명
		sellerUpgrade.setSwiftCode("XXXXX"); // INTL_SWIFT_CD Swift 코드
		sellerUpgrade.setAbaCode("XXXXX"); // ABA 코드 INTL_ABA 국제 aba
		sellerUpgrade.setIbanCode("XXXXX"); // IBAN 코드 INTL_IBAN 국제 iban
		sellerUpgrade.setBankAddress("XXXXX"); // FR_BANK_ADDR 외국은행주소
		sellerUpgrade.setBankLocation("XXXXX"); // FR_BANK_LOC 외국은행 위치
		sellerUpgrade.setCeoBirthDay("19790803"); // CEO_BIRTH
		sellerUpgrade.setSellerLanguage("KO"); // LANG_CD
		sellerUpgrade.setTpinCode("XXXXX"); // FR_TIN_NO 외국 tpin 번호
		sellerUpgrade.setVendorCode("XXXXX"); // VENDOR_CD 벤더코드
		sellerUpgrade.setRepPhoneArea("XXXXX"); // REP_TEL_NATION_NO 대표전화 국가 번호
		// sellerUpgrade.setRepFaxArea("XXXXX"); // FAX_TEL_NATION_NO member 테이블네 넣을때는 FAX_NATION_NO 넣으면 될듯
		sellerUpgrade.setBizGrade("XXXXX"); // DELIB_GRD_CD 심의등급코드 TB_US_SELLERMBR 에만 있음 테이블에 추가됨
		sellerUpgrade.setIsDeductible("Y"); // AUTO_DED_POSB_TARGET_YN 자동차감가능대상여부 TB_US_SELLERMBR 에만 있음 테이블에 추가 해야한다.
		sellerUpgrade.setMarketCode("XXXXX"); // LNCHG_MALL_CD 입점 상점코드 ##### 전환 쪽에서 사용
		sellerUpgrade.setMarketStatus("XXXXX"); // LNCHG_MBR_STATUS_CD 입점 회원 상태코드 ##### 전환 쪽에서 사용
		sellerUpgrade.setAccountRealDate("20140103"); // ACCT_AUTH_DT 계좌인증일시
		sellerUpgrade.setBankBsb("bsb");

		// 정산 테이블 수정 >> update >> 마지막 계좌정보를 수정해야한다.
		// 시작할때는 현재 시간으로 등록한다. 무조건 insert 하는데 현재 날짜 StartDate 값이 있으면 update 없으면 insert
		// sellerAccount.setStartDate("20110124");
		// sellerAccount.setEndDate("29991231");
		sellerUpgrade.setBankCode("20"); // ("은행코드"); // BANK_CD 은행코드
		sellerUpgrade.setBankName("국민은행"); // ("은행명"); // FR_BANK_NM 은행명
		sellerUpgrade.setAccountRealDate("20110124135542"); // ("	계좌인증일시"); ACCT_AUTH_DT
		sellerUpgrade.setBankBranch("국민은행판교점"); // ("은행지점명"); // FR_BRCH_NM 은행지점명
		sellerUpgrade.setBankBranchCode("xxxx"); // ("	은행지점코드");// FR_BRCH_CD 은행지점코드
		sellerUpgrade.setSwiftCode("xxxx"); // ("Swift 코드"); // INTL_SWIFT_CD Swift 코드
		sellerUpgrade.setAbaCode("xxxx"); // ("ABA 코드"); // INTL_ABA_CD ABA 코드
		sellerUpgrade.setIbanCode("xxxx"); // ("IBAN 코드"); // INTL_IBAN IBAN 코드
		sellerUpgrade.setTpinCode("xxxx"); // ("TPIN"); // FR_TPIN_NO
		sellerUpgrade.setBankAccount("64710101200324"); // ("계좌번호"); // ACCT_NO 계좌번호
		sellerUpgrade.setBankAcctName("sk planet"); // ("예금자명"); // DEPSTR_NM 예금자명
		// sellerAccount.setReason("변경"); //(변경 'change', 최초 'new' 등록)으로 등록
		// sellerAccount.setIsUsed("Y"); USE_YN 사용여부 ->알아서 넣어주면된다.
		upgradeSellerRequest.setSellerUpgrade(sellerUpgrade);

		// 멀티미디어 정산율 테이블 수정 >> 있으면 update 없다면 insert
		// bp_rate
		ExtraRight extraRight = new ExtraRight(); //
		extraRight.setTenantID(upgradeSellerRequest.getCommonRequest().getTenantID());
		extraRight.setSellerKey(upgradeSellerRequest.getSellerKey());
		extraRight.setRightProfileCode("AD010101"); // MULTIMDA_CD
		extraRight.setStartDate("20130122162853"); // START_DT //2013-01-22 16:28:53
		extraRight.setEndDate("29991231235959"); // END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')
		extraRight.setSellerRate("40"); // SELLERMBR_SETT_RATE 판매자 정산율
		extraRight.setTenantRate("60"); // SELLERMBR_SETT_RATE tenant 정산율
		extraRight.setUpdateID("test_id");
		extraRight.setRegID("test_id");
		extraRightList.add(extraRight);

		// extraRight = new ExtraRight(); //
		// extraRight.setTenantID(upgradeSellerRequest.getCommonRequest().getTenantID());
		// extraRight.setSellerKey(upgradeSellerRequest.getSellerKey());
		// extraRight.setRightProfileCode("AD010102"); // MULTIMDA_CD
		// extraRight.setStartDate("20130122162853"); // START_DT //2013-01-22 16:28:53
		// extraRight.setEndDate("29991231235959"); // END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')
		// extraRight.setSellerRate("30"); // SELLERMBR_SETT_RATE 판매자 정산율
		// extraRight.setTenantRate("70"); // SELLERMBR_SETT_RATE tenant 정산율
		// extraRight.setUpdateID("test_id");
		// extraRight.setRegID("test_id");
		// extraRightList.add(extraRight);
		// upgradeSellerRequest.setExtraRightList(extraRightList);

		// 첨부파알 수정 >> 있으면 update 없다면 insert
		Document document = new Document(); // TB_US_SELLERMBR_DOC
		document.setSellerKey(upgradeSellerRequest.getSellerKey());
		// document.setAccountChangeKey(accountChangeKey);
		// CHANGED_APLC_ID 전환신청 >> seq DB 에서 자동으로 입력해야한다. 이 키는 전환신청테이블에서 가져와서 입력한다.
		document.setDocumentCode("US001901"); // DOC_CD 서류코드
		document.setDocumentPath("/data/mem/IF1023229565820110106110241/20110530175341571_US001901.jpg"); // REG_DOC_FILE_PATH
		document.setDocumentName("DSC00272.jpg");
		document.setDocumentSize("3846751");
		document.setRegID("test_id"); // REG_ID 등록자 >> 생성시 commonvo 의 ownerID 룰 입력한다.
		document.setUpdateID("test_id");
		document.setRegID("test_id");
		document.setIsUsed("Y");
		documentList.add(document);

		document = new Document(); // TB_US_SELLERMBR_DOC
		document.setSellerKey(upgradeSellerRequest.getSellerKey());
		// document.setAccountChangeKey(accountChangeKey);
		// CHANGED_APLC_ID 전환신청 >> seq DB 에서 자동으로 입력해야한다. 이 키는 전환신청테이블에서 가져와서 입력한다.
		document.setDocumentCode("US001904"); // DOC_CD 서류코드
		document.setDocumentPath("/data/mem/IF1023229565820110106110241/2011053017522933_US001904.jpg"); // REG_DOC_FILE_PATH
		document.setDocumentName("2011053017522933_US001904.jpg");
		document.setDocumentSize("3846751");
		document.setRegID("test_id"); // REG_ID 등록자 >> 생성시 commonvo 의 ownerID 룰 입력한다.
		document.setUpdateID("test_id");
		document.setRegID("test_id");
		document.setIsUsed("Y");
		documentList.add(document);
		upgradeSellerRequest.setDocumentList(documentList);

		UpgradeSellerResponse upgradeSellerResponse = this.sellerSCI.upgradeSeller(upgradeSellerRequest);
		if (upgradeSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", upgradeSellerResponse.getCommonResponse().toString());
		if (upgradeSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", upgradeSellerResponse.toString());

		assertThat(upgradeSellerResponse, notNullValue());

		assertThat(upgradeSellerResponse.getSellerKey(), notNullValue());
		assertThat(upgradeSellerResponse.getSellerID(), notNullValue());

	}

	/**
	 * 판매자회원 정산정보 수정.
	 */
	@Test
	public void updateAccountSeller() {

		UpdateAccountSellerRequest updateAccountSellerRequest = new UpdateAccountSellerRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID("S01");
		updateAccountSellerRequest.setCommonRequest(commonRequest);

		updateAccountSellerRequest.setSellerKey("IF1023510184220111110165707"); // TB_US_SELLERMBR_CHRG_SETT_ACCT
		// IF102158838420090627230353 멀티미디어 TB_US_SELLERMBR_MULTIMDA_AUTH
		// IF1023139253020100810165321 문서 TB_US_SELLERMBR_DOC
		// IF1023229565820110106110241 정산 TB_US_SELLERMBR_CHRG_SETT_ACCT

		SellerMbr sellerMbr = new SellerMbr(); // TB_US_SELLERMBR
		SellerAccount sellerAccount = new SellerAccount(); // TB_US_SELLERMBR_CHRG_SETT_ACCT
		List<ExtraRight> extraRightList = new ArrayList<ExtraRight>();
		List<Document> documentList = new ArrayList<Document>();

		// 판매자 테이블 수정 >> update
		sellerMbr.setSellerKey(updateAccountSellerRequest.getSellerKey());
		sellerMbr.setSellerBizType(""); // INDT_NM 업종명 종목 종목
		sellerMbr.setSellerBizCategory("업태"); // COND_NM 업태명 업태 업태
		sellerMbr.setSellerBizCorpNumber("xxxx"); // ("법인등록번호"); CORP_REG_NO
		sellerMbr.setRepPhoneArea("xxxx"); // ("대표전화번호 국가코드"); REP_TEL_NATION_NO

		sellerMbr.setCeoBirthDay("20090803");
		sellerMbr.setCeoName("대표님1");
		sellerMbr.setRepEmail("sk@skt.com");

		sellerMbr.setRepPhone("0212323213"); // ("대표전화번호"); REP_TEL_NO
		sellerMbr.setSellerZip("135080"); // ("사업장 우편번호"); ZIP 우편번호
		sellerMbr.setSellerAddress("서울 강남구 역삼동"); // ("사업장 주소"); ADDR
		sellerMbr.setSellerDetailAddress("1132-51번지 비전힐스 702호"); // ("사업장 상세주소"); DTL_ADDR
		sellerMbr.setVendorCode("xxxx"); // ("벤더 코드"); VENDOR_CD
		sellerMbr.setIsBizRegistered("Y"); // ("통신판매업 신고여부"); MSALBIZ_DECL_YN
		sellerMbr.setBizRegNumber("xxxx"); // ("통신판매업 신고번호"); MSALBIZ_DECL_NO
		sellerMbr.setBizUnregReason("US000604"); // ("통신판매업 미신고사유  코드"); MSALBIZ_UNDECL_REASON_CD
		sellerMbr.setIsBizTaxable("Y"); // ("간이과세여부"); // EASY_TXN_YN 간이 과세 여부 ##### 전환 쪽에서 사용
		sellerMbr.setBizGrade("xxxx"); // ("심의등급코드"); DELIB_GRD_CD 심의 등급코드
		sellerMbr.setIsDeductible("Y"); // ("자동차감가능대상여부"); AUTO_DED_POSB_TARGET_YN
		sellerMbr.setMarketCode("US001202"); // ("입점상점코드"); LNCHG_MALL_CD 입점 상점코드
		sellerMbr.setMarketStatus("MM000201"); // ("입점상태코드"); LNCHG_MBR_STATUS_CD
		sellerMbr.setIsAccountReal("Y"); // ("   계좌인증여부"); // ACCT_AUTH_YN 계좌 인증여부 컬럼

		updateAccountSellerRequest.setSellerMbr(sellerMbr);

		// 멀티미디어 정산율 테이블 수정 >> 있으면 update 없다면 insert
		// bp_rate
		// ExtraRight extraRight = new ExtraRight(); //
		// extraRight.setTenantID(updateAccountSellerRequest.getCommonRequest().getTenantID());
		// extraRight.setSellerKey(updateAccountSellerRequest.getSellerKey());
		// extraRight.setRightProfileCode("AD010101"); // MULTIMDA_CD
		// extraRight.setStartDate("20130122162853"); // START_DT //2013-01-22 16:28:53
		// extraRight.setEndDate("29991231235959"); // END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')
		// extraRight.setSellerRate("40"); // SELLERMBR_SETT_RATE 판매자 정산율
		// extraRight.setTenantRate("60"); // SELLERMBR_SETT_RATE tenant 정산율
		// extraRight.setUpdateID("test_id");
		// extraRight.setRegID("test_id");
		// extraRightList.add(extraRight);
		//
		// extraRight = new ExtraRight(); //
		// extraRight.setTenantID(updateAccountSellerRequest.getCommonRequest().getTenantID());
		// extraRight.setSellerKey(updateAccountSellerRequest.getSellerKey());
		// extraRight.setRightProfileCode("AD010102"); // MULTIMDA_CD
		// extraRight.setStartDate("20130122162853"); // START_DT //2013-01-22 16:28:53
		// extraRight.setEndDate("29991231235959"); // END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')
		// extraRight.setSellerRate("30"); // SELLERMBR_SETT_RATE 판매자 정산율
		// extraRight.setTenantRate("70"); // SELLERMBR_SETT_RATE tenant 정산율
		// extraRight.setUpdateID("test_id");
		// extraRight.setRegID("test_id");
		// extraRightList.add(extraRight);
		// updateAccountSellerRequest.setExtraRightList(extraRightList);

		// 정산 테이블 수정 >> update >> 마지막 계좌정보를 수정해야한다.
		// 시작할때는 현재 시간으로 등록한다. 무조건 insert 하는데 현재 날짜 StartDate 값이 있으면 update 없으면 insert
		// sellerAccount.setStartDate("20110124");
		// sellerAccount.setEndDate("29991231");
		sellerAccount.setSellerKey(updateAccountSellerRequest.getSellerKey());
		sellerAccount.setBankCode("11"); // ("은행코드"); // BANK_CD 은행코드
		// sellerAccount.setBankName("씨티은행"); // ("은행명"); // FR_BANK_NM 은행명
		// sellerAccount.setAccountRealDate("20110124135542"); // ("	계좌인증일시"); ACCT_AUTH_DT
		// sellerAccount.setBankBranch("국민은행판교점"); // ("은행지점명"); // FR_BRCH_NM 은행지점명
		// sellerAccount.setBankBranchCode("xxxx"); // ("	은행지점코드");// FR_BRCH_CD 은행지점코드
		// sellerAccount.setSwiftCode("xxxx"); // ("Swift 코드"); // INTL_SWIFT_CD Swift 코드
		// sellerAccount.setAbaCode("xxxx"); // ("ABA 코드"); // INTL_ABA_CD ABA 코드
		// sellerAccount.setIbanCode("xxxx"); // ("IBAN 코드"); // INTL_IBAN_CD IBAN 코드
		// sellerAccount.setTpinCode("xxxx"); // ("TPIN"); // FR_TPIN_NO
		// sellerAccount.setBankAccount("4321"); // ("계좌번호"); // ACCT_NO 계좌번호
		// sellerAccount.setBankAcctName("sk planet2"); // ("예금자명"); // DEPSTR_NM 예금자명
		// sellerAccount.setReason("변경"); // (변경 'change', 최초 'new' 등록)으로 등록
		// sellerAccount.setIsUsed("Y"); USE_YN 사용여부 ->알아서 넣어주면된다.
		updateAccountSellerRequest.setSellerAccount(sellerAccount);

		// 첨부파알 수정 >> 있으면 update 없다면 insert
		Document document = new Document(); // TB_US_SELLERMBR_DOC
		document.setSellerKey(updateAccountSellerRequest.getSellerKey());
		// document.setAccountChangeKey(accountChangeKey);
		// CHANGED_APLC_ID 전환신청 >> seq DB 에서 자동으로 입력해야한다. 이 키는 전환신청테이블에서 가져와서 입력한다.
		document.setDocumentCode("US001901"); // DOC_CD 서류코드
		document.setDocumentPath("/data/mem/IF1023229565820110106110241/20110530175341571_US001901.jpg"); // REG_DOC_FILE_PATH
		document.setDocumentName("DSC00272.jpg");
		document.setDocumentSize("3846751");
		document.setRegID("test_id"); // REG_ID 등록자 >> 생성시 commonvo 의 ownerID 룰 입력한다.
		document.setUpdateID("test_id");
		document.setRegID("test_id");
		document.setIsUsed("Y");
		documentList.add(document);

		document = new Document(); // TB_US_SELLERMBR_DOC
		document.setSellerKey(updateAccountSellerRequest.getSellerKey());
		// document.setAccountChangeKey(accountChangeKey);
		// CHANGED_APLC_ID 전환신청 >> seq DB 에서 자동으로 입력해야한다. 이 키는 전환신청테이블에서 가져와서 입력한다.
		document.setDocumentCode("US001904"); // DOC_CD 서류코드
		document.setDocumentPath("/data/mem/IF1023229565820110106110241/2011053017522933_US001904.jpg"); // REG_DOC_FILE_PATH
		document.setDocumentName("2011053017522933_US001904.jpg");
		document.setDocumentSize("3846751");
		document.setRegID("test_id"); // REG_ID 등록자 >> 생성시 commonvo 의 ownerID 룰 입력한다.
		document.setUpdateID("test_id");
		document.setRegID("test_id");
		document.setIsUsed("Y");
		documentList.add(document);

		updateAccountSellerRequest.setDocumentList(documentList);

		UpdateAccountSellerResponse updateAccountSellerResponse = this.sellerSCI
				.updateAccountSeller(updateAccountSellerRequest);

		if (updateAccountSellerResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updateAccountSellerResponse.getCommonResponse().toString());
		if (updateAccountSellerResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updateAccountSellerResponse.toString());

		assertThat(updateAccountSellerResponse, notNullValue());

		assertThat(updateAccountSellerResponse.getSellerKey(), notNullValue());
		assertThat(updateAccountSellerResponse.getSellerID(), notNullValue());

	}

	/**
	 * 비밀번호 힌트 목록 조회.
	 */
	@Test
	public void searchPwdHintList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 비밀번호 힌트 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchPwdHintListRequest searchPwdHintListRequest;
		SearchPwdHintListResponse searchPwdHintListResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchPwdHintListRequest = new SearchPwdHintListRequest();
		searchPwdHintListRequest.setCommonRequest(commonRequest);
		searchPwdHintListRequest.setSellerKey("SE201403171458026260001741"); // SE201403171439063060001738
																			 // SE201403171458026260001741

		// SE201403171439063060001738
		// PWD_QUST_NM PWD_QUST_DESC
		// QUESTIONNAME QUESTIONMESSAGE
		// 강아지 이름 세번째 질문

		// SE201403171458026260001741
		// PWD_QUST_NM PWD_QUST_DESC
		// QUESTIONNAME QUESTIONMESSAGE
		// 강아지 직접입력

		// searchPwdHintListRequest.setQuestionID("QUESTION3");
		searchPwdHintListRequest.setLanguageCode("ko");

		// 요청
		searchPwdHintListResponse = this.sellerSCI.searchPwdHintList(searchPwdHintListRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListResponse.getPWReminderList());

		// 응답
		assertThat(searchPwdHintListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchPwdHintListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchPwdHintListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchPwdHintListResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchPwdHintListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		List<PWReminder> pWReminderList = searchPwdHintListResponse.getPWReminderList();
		for (PWReminder pWReminder : pWReminderList) {
			assertNotNull(pWReminder.getQuestionID());
			// assertNotNull(pWReminder.getQuestionMessage());
			LOGGER.debug("### searchPwdHintList : {}", pWReminder);
		}

	}

	/**
	 * 비밀번호 힌트 목록 조회.
	 */
	@Test
	public void searchPwdHintListAll() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ALL 비밀번호 힌트 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchPwdHintListAllRequest searchPwdHintListAllRequest;
		SearchPwdHintListAllResponse searchPwdHintListAllResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchPwdHintListAllRequest = new SearchPwdHintListAllRequest();
		searchPwdHintListAllRequest.setCommonRequest(commonRequest);
		searchPwdHintListAllRequest.setLanguageCode("ko");

		// 요청
		searchPwdHintListAllResponse = this.sellerSCI.searchPwdHintListAll(searchPwdHintListAllRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListAllResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListAllResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPwdHintListAllResponse.getPWReminderAllList());

		// 응답
		assertThat(searchPwdHintListAllResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchPwdHintListAllResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchPwdHintListAllResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchPwdHintListAllResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchPwdHintListAllResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		List<PWReminderAll> pWReminderListAll = searchPwdHintListAllResponse.getPWReminderAllList();
		for (PWReminderAll pWReminder : pWReminderListAll) {
			assertNotNull(pWReminder.getQuestionID());
			// assertNotNull(pWReminder.getQuestionMessage());
			LOGGER.debug("### searchPwdHintList : {}", pWReminder);
		}

	}

	/**
	 * LoginInfo 정보 조회.
	 */
	@Test
	public void searchLoginInfo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - LoginInfo 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchLoginInfoRequest searchLoginInfoRequest;
		SearchLoginInfoResponse searchLoginInfoResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchLoginInfoRequest = new SearchLoginInfoRequest();
		searchLoginInfoRequest.setCommonRequest(commonRequest);
		searchLoginInfoRequest.setSessionKey("222");
		// searchLoginInfoRequest.setSellerKey("IF1023501414520130903110815");

		// 요청
		searchLoginInfoResponse = this.sellerSCI.searchLoginInfo(searchLoginInfoRequest);

		// 응답
		assertThat(searchLoginInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchLoginInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchLoginInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchLoginInfoResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공

		// 응답필수 > sellerKey
		assertThat(searchLoginInfoResponse.getLoginInfo(), notNullValue());

		LOGGER.debug("### 넘긴 데이터 : {}", searchLoginInfoResponse.getLoginInfo().toString());

		assertSame(
				searchLoginInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LoginInfo data = searchLoginInfoResponse.getLoginInfo();
		assertThat(data.getSellerKey(), notNullValue());
	}

	/**
	 * <pre>
	 * LoginInfo 정보 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateLoginInfo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - LoginInfo 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateLoginInfoRequest updateLoginInfoRequest;
		UpdateLoginInfoResponse updateLoginInfoResponse;
		LoginInfo data;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		data = new LoginInfo();
		data.setSellerKey("SS201403071028557940001487");
		data.setIpAddress("127.0.0.1");
		data.setExpireDate("20150102150000");
		data.setSessionKey("8596d52514f746c9a7c3f48dcb212395");

		// 요청 필수
		updateLoginInfoRequest = new UpdateLoginInfoRequest();
		updateLoginInfoRequest.setCommonRequest(commonRequest);
		updateLoginInfoRequest.setLoginInfo(data);

		// 요청
		updateLoginInfoResponse = this.sellerSCI.updateLoginInfo(updateLoginInfoRequest);

		// 응답
		assertThat(updateLoginInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateLoginInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateLoginInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateLoginInfoResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateLoginInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * LoginInfo 정보 삭제
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void removeLoginInfo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - LoginInfo 정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveLoginInfoRequest removeLoginInfoRequest;
		RemoveLoginInfoResponse removeLoginInfoResponse;
		LoginInfo data;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		data = new LoginInfo();
		data.setSellerKey("SE201402211611241260001177");

		// 요청 필수
		removeLoginInfoRequest = new RemoveLoginInfoRequest();
		removeLoginInfoRequest.setCommonRequest(commonRequest);
		removeLoginInfoRequest.setLoginInfo(data);

		// 요청
		removeLoginInfoResponse = this.sellerSCI.removeLoginInfo(removeLoginInfoRequest);

		// 응답
		assertThat(removeLoginInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeLoginInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeLoginInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeLoginInfoResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeLoginInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

}
