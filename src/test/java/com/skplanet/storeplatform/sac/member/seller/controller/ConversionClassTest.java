package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacReq.ExtraDocument;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassSacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 2.2.16. 판매자 회원 전환신청
 * 
 * Updated on : 2014. 2. 13. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ConversionClassTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConversionClassTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static ConversionClassSacReq req;

	/** [RESPONSE]. */
	private static ConversionClassSacRes res;

	/** LOGIN-[REQUEST]. */
	public static AuthorizeReq authorizeReq;
	/** LOGIN-[RESPONSE]. */
	public static AuthorizeRes authorizeRes;

	/** [HEADER]. */
	private static String xStoreAuthInfo;

	/**
	 * <pre>
	 * before method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// TODO 임시 [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";

		// [REQUEST] 초기화
		req = new ConversionClassSacReq();
		authorizeReq = new AuthorizeReq();

		// 로그인 데이터 주입
		authorizeReq.setSellerId("rejoiceTest05");
		authorizeReq.setSellerPW("1234");
		authorizeReq.setExpireDate("100");

		// 로그인 콜
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						LOGGER.debug("[REQUEST(SAC)-회원인증] : \n{}",
								TestConvertMapperUtils.convertObjectToJson(authorizeReq));
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		// 로그인 결과 검증
		if (StringUtils.equals(TestMemberConstant.USE_Y, authorizeRes.getIsLoginSuccess())) {
			// 정보수정의 위한 데이터 주입
			req.setSellerId(authorizeRes.getSellerMbr().getSellerId());
			req.setSellerKey(authorizeRes.getSellerMbr().getSellerKey());
			req.setSessionKey(authorizeRes.getSessionKey());
		} else {
			fail("수정 불가 회원입니다.");
		}
	}

	@After
	public void after() {
		// Debug
		LOGGER.debug("[RESPONSE(SAC)-회원기본정보 수정] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}

	@Test
	public void testConversionClassRes() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/conversionClass/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerKey("SE201402051029143200000587");
						req.setSessionKey("fd6697b8e02e4d9f84d8438bf0bf7b1e");
						req.setSellerId("sctest5");

						req.setSellerClass("US010101");
						req.setSellerCategory("US011301");
						req.setSellerMainStatus("US010201");
						req.setSellerSubStatus("US010301");
						req.setSellerClassTo("US010103"); // BIZ_KIND_CD(US000901,US000904) 신청
						req.setRepEmail("aind050@yopmail.com"); // ("대표 이메일"); 회원 REP_EMAIL > 전환 CHRGPERS_EMAIL
						req.setSellerBizCorpNumber("123123123");// ("법인등록번호"); CORP_REG_NO
						req.setSellerBizType("업종"); // INDT_NM 업종명 종목 종목
						req.setSellerBizCategory("업태"); // COND_NM 업태명 업태 업태
						req.setBankAccount("0112233322511"); // ACCT_NO 계좌번호
						req.setBankCode("123");// BANK_CD 은행코드
						req.setBankAcctName("홍길동"); // DEPSTR_NM 예금자명
						req.setIsAccountReal("Y"); // ACCT_AUTH_YN 계좌인증여부
						req.setIsBizTaxable("N"); // EASY_TXNPERS_YN 간이과세여부
						req.setRepPhone("0262575958"); // REP_TEL_NO 대표전화번호
						req.setRepFax("0262575958"); // FAX_NO 팩스번호
						req.setIsBizRegistered("Y"); // CMNT_SALBIZ_DECL_YN 통신판매업 신고여부
						req.setBizRegNumber("11223344"); // CMNT_SALBIZ_DECL_NO 통신판매업 신고번호
						req.setBizUnregReason("554411"); // CMNT_SALBIZ_UNDECL_REASON_CD 통신판매업 미신고사유
						req.setBankName("우리은행"); // FR_BANK_NM 은행명
						req.setBankBranchCode("A01"); // FR_BANK_NM 은행명
						req.setBankBranch("반포지점"); // FR_BRCH_NM 은행지점명
						req.setSwiftCode("A123"); // INTL_SWIFT_CD Swift 코드
						req.setAbaCode("B12"); // ABA 코드 INTL_ABA 국제 aba
						req.setIbanCode("C1"); // IBAN 코드 INTL_IBAN 국제 iban
						req.setBankAddress("XXXX"); // FR_BANK_ADDR 외국은행주소
						req.setBankLocation("XXXX"); // FR_BANK_LOC 외국은행 위치
						req.setTpinCode("XXXX"); // FR_TIN_NO 외국 tpin 번호
						req.setVendorCode("XXXX");// VENDOR_CD 벤더코드
						req.setRepPhoneArea("XXXX"); // REP_TEL_NATION_NO 대표전화 국가 번호
						req.setRepFaxArea("XXXX"); // FAX_TEL_NATION_NO member 테이블네 넣을때는 FAX_NATION_NO 넣으면 될듯
						req.setBizGrade("XXXX"); // DELIB_GRD_CD 심의등급코드 TB_US_SELLERMBR 에만 있음 테이블에 추가됨
						req.setIsDeductible("Y");// AUTO_DED_POSB_TARGET_YN 자동차감가능대상여부 TB_US_SELLERMBR 에만
						req.setMarketCode("XXXX"); // LNCHG_MALL_CD 입점 상점코드 ##### 전환 쪽에서 사용
						req.setMarketStatus("XXXX"); // LNCHG_MBR_STATUS_CD 입점 회원 상태코드 ##### 전환 쪽에서 사용
						req.setAccountRealDate("20110124135542"); // ACCT_AUTH_DT 계좌인증일시
						req.setSellerBizZip("120757"); // ENPRPL_ZIP 우편번호
						req.setSellerBizAddress("서울시 서대문구 대현동");// ENPRPL_ADDR 주소 의경우
						req.setSellerBizDetailAddress("럭키 아파트 101동 909호");
						req.setCeoBirthDay("231123"); // CEO_BIRTH
						req.setSellerLanguage("KO"); // LANG_CD
						req.setSellerTelecom("US001201"); // MNO_CD 통신사 코드 >> api 추가 하지말고 판매자 테이블에서 가져온다.
						req.setCeoName("심봉사"); //
						req.setSellerCompany("호랑"); // COMP_NM 회사명 >> api 추가 하지말고 판매자 테이블에서 가져온다.
						req.setCordedTelephone("1231231");
						req.setChargerPhone("12312312");
						req.setIsRecvSMS("N");
						req.setCharger("일순");

						ExtraDocument extraDocument = new ExtraDocument();
						extraDocument.setDocumentCode("US001903");
						extraDocument
								.setDocumentPath("/data/mem/IF1023139253020100810165321/20100827101614376_US001903.png");
						extraDocument.setDocumentName("구매내역2.png");
						extraDocument.setDocumentSize("34803");
						List<ExtraDocument> list = new ArrayList<ExtraDocument>();
						list.add(extraDocument);
						req.setExtraDocumentList(list);

						// Debug
						LOGGER.debug("[REQUEST (SAC)-회원전환 신청] : \n{}", TestConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ConversionClassSacRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						assertEquals(res.getSellerKey(), req.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
