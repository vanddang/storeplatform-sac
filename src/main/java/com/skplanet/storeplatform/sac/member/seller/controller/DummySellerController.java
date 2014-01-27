package com.skplanet.storeplatform.sac.member.seller.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgree;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AbrogationAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifySubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;
import com.skplanet.storeplatform.sac.member.seller.service.SellerService;

/**
 * 판매자 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 한서구, 부르칸.
 */
@RequestMapping(value = "/member/seller")
@Controller
public class DummySellerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummySellerController.class);

	@Autowired
	private SellerService sellerService;

	@Autowired
	private SellerSearchService sellerSearchService;

	/**
	 * <pre>
	 * 판매자 회원 가입.
	 * </pre>
	 * 
	 * @return CreateRes
	 */
	// @RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRes create() {

		CreateRes responseVO = new CreateRes();
		SellerMbr sellerInfo = new SellerMbr();

		sellerInfo.setSellerId("user1");
		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerMainStatus("US010704");
		sellerInfo.setSellerSubStatus("US010301");
		responseVO.setSellerMbr(sellerInfo);

		LOGGER.debug("response : {}" + responseVO.toString());

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 ID/이메일 중복 조회.
	 * </pre>
	 * 
	 * @return DuplicateByIdEmailRes
	 */
	// @RequestMapping(value = "/duplicateByIdEmail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DuplicateByIdEmailRes duplicateByIdEmail() {
		return new DuplicateByIdEmailRes("Y");
	}

	/**
	 * <pre>
	 * 판매자 회원 인증.
	 * </pre>
	 * 
	 * @return AuthorizeRes
	 */
	// @RequestMapping(value = "/authorize/v1", method = RequestMethod.GET)
	@ResponseBody
	public AuthorizeRes authorize() {

		AuthorizeRes responseVO = new AuthorizeRes();
		SellerMbr sellerInfo = new SellerMbr();

		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerClass("US010101");
		sellerInfo.setSellerMainStatus("US010204");
		sellerInfo.setSellerSubStatus("US010307");
		responseVO.setLoginFailCount("2");
		responseVO.setIsLoginSuccess("Y");
		responseVO.setSellerMbr(sellerInfo);
		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 ID찾기.
	 * </pre>
	 * 
	 * @return SearchIdRes
	 */
	// @RequestMapping(value = "/searchId/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchIdRes searchId() {

		SearchIdRes responseVO = new SearchIdRes();
		List<SellerMbr> sList = new ArrayList<SellerMbr>();
		SellerMbr sellerMbrRes = null;
		for (int i = 0; i < 1; i++) {
			sellerMbrRes = new SellerMbr();
			sellerMbrRes.setApproveDate("20120820");
			sellerMbrRes.setBizGrade("ggg");
			sellerMbrRes.setBizKindCd("US000901");
			sellerMbrRes.setBizRegNumber("2");
			sellerMbrRes.setBizUnregReason("US000603");
			sellerMbrRes.setCeoBirthDay("19270304");
			sellerMbrRes.setCeoName("테스트");
			sellerMbrRes.setCharger("김덕중");
			sellerMbrRes.setCordedTelephone("022224444");
			sellerMbrRes.setCordedTelephoneCountry("");
			sellerMbrRes.setCustomerEmail("sel@nate.com");
			sellerMbrRes.setCustomerPhone("022224444");
			sellerMbrRes.setCustomerPhoneCountry("dd");
			sellerMbrRes.setIsAccountReal("Y");
			sellerMbrRes.setIsBizRegistered("N");
			sellerMbrRes.setIsBizTaxable("Y");
			sellerMbrRes.setIsDeductible("");
			sellerMbrRes.setIsDomestic("Y");
			sellerMbrRes.setIsParent("");
			sellerMbrRes.setIsRealName("Y");
			sellerMbrRes.setIsRecvEmail("Y");
			sellerMbrRes.setIsRecvSMS("N");
			sellerMbrRes.setMarketCode("US001202");
			sellerMbrRes.setMarketStatus("");
			sellerMbrRes.setParentSellerKey("");
			sellerMbrRes.setRegDate("20120820");
			sellerMbrRes.setRepEmail("signtest@yopmail.com");
			sellerMbrRes.setRepFax("0211112222");
			sellerMbrRes.setRepFaxArea("");
			sellerMbrRes.setRepPhone("0211112222");
			sellerMbrRes.setRepPhoneArea("");
			sellerMbrRes.setRightProfileList("");
			sellerMbrRes.setSecedeDate("");
			sellerMbrRes.setSecedePathCd("");
			sellerMbrRes.setSecedeReasonCode("");
			sellerMbrRes.setSecedeReasonMessage("");
			sellerMbrRes.setSellerAddress("123123");
			sellerMbrRes.setSellerBirthDay("19781101");
			sellerMbrRes.setSellerBizCategory("test");
			sellerMbrRes.setSellerBizCorpNumber("7887845121");
			sellerMbrRes.setSellerBizNumber("");
			sellerMbrRes.setSellerBizType("test");
			sellerMbrRes.setSellerCategory("US011302");
			sellerMbrRes.setSellerCity("123123");
			sellerMbrRes.setSellerClass("US010101");
			sellerMbrRes.setSellerCompany("(주)동해물과 백두산이 마르고 닳도록");
			sellerMbrRes.setSellerCountry("");
			sellerMbrRes.setSellerDetailAddress("123123");
			sellerMbrRes.setSellerEmail("signtest@yopmail.com");
			sellerMbrRes.setSellerId("signtest");
			sellerMbrRes.setSellerKey("IF102158942020090723111912");
			sellerMbrRes.setSellerLanguage("US004301");
			sellerMbrRes.setSellerMainStatus("US010205");
			sellerMbrRes.setSellerName("김덕중");
			sellerMbrRes.setSellerNickName("판매자정보팝업용판매자명");
			sellerMbrRes.setSellerPhone("01083982958");
			sellerMbrRes.setSellerPhoneCountry("");
			sellerMbrRes.setSellerSex("F");
			sellerMbrRes.setSellerSSNumber("");
			sellerMbrRes.setSellerState("");
			sellerMbrRes.setSellerSubStatus("US010314");
			sellerMbrRes.setSellerTelecom("US001202");
			sellerMbrRes.setSellerZip("120830");
			sellerMbrRes.setTenantID("");
			sellerMbrRes.setVendorCode("");
			sList.add(sellerMbrRes);
		}
		responseVO.setSellerMbr(sList);

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 Password찾기.
	 * </pre>
	 * 
	 * @return SearchPasswordRes
	 */
	// @RequestMapping(value = "/searchPassword/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchPasswordRes searchPassword() {

		SearchPasswordRes responseVO = new SearchPasswordRes();

		responseVO.setNewPassword("1234");

		return responseVO;
	}

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 * 
	 * @return CheckPasswordReminderQuestionRes
	 */
	// @RequestMapping(value = "/checkPasswordReminderQuestion/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion() {

		CheckPasswordReminderQuestionRes responseVO = new CheckPasswordReminderQuestionRes();

		responseVO.setIsCorrect("Y");

		return responseVO;
	}

	/**
	 * <pre>
	 * Password 보안 질문 조회.
	 * </pre>
	 * 
	 * @return ListPasswordReminderQuestionRes
	 */
	// @RequestMapping(value = "/listPasswordReminderQuestion/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion() {

		ListPasswordReminderQuestionRes responseVO = new ListPasswordReminderQuestionRes();
		SellerMbrPwdHint sellerMbrPwdHint = null;

		List<SellerMbrPwdHint> myList = new ArrayList<SellerMbrPwdHint>();
		for (int i = 0; i < 1; i++) {
			sellerMbrPwdHint = new SellerMbrPwdHint();
			sellerMbrPwdHint.setQuestionID("0001");
			myList.add(sellerMbrPwdHint);
		}
		responseVO.setSellerMbrPwdHintList(myList);
		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 기본 정보 조회.
	 * </pre>
	 * 
	 * @return DetailInformationRes
	 */
	// @RequestMapping(value = "/detailInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailInformationRes detailInformation() {

		DetailInformationRes responseVO = new DetailInformationRes();
		// 판매자 멀티미디어정보
		List<ExtraRight> eList = new ArrayList<ExtraRight>();

		// 약관동의목록
		List<MbrClauseAgree> mList = new ArrayList<MbrClauseAgree>();
		MbrClauseAgree mbrClauseAgreeList = null;
		for (int i = 0; i < 1; i++) {
			mbrClauseAgreeList = new MbrClauseAgree();
			mbrClauseAgreeList.setExtraAgreementID("US004901");
			mbrClauseAgreeList.setExtraAgreementVersion("1.0");
			mbrClauseAgreeList.setIsExtraAgreement("Y");
			mbrClauseAgreeList.setIsMandatory("");
			mbrClauseAgreeList.setMemberKey("IF102158942020090723111912");
			mbrClauseAgreeList.setRegDate("2009-07-23 00:00:00");
			mbrClauseAgreeList.setTenantID("");
			mbrClauseAgreeList.setUpdateDate("");
			mList.add(mbrClauseAgreeList);
		}

		// 법정대리인정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();

		// 판매자 정보
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setApproveDate("20120820");
		sellerMbr.setBizGrade("ggg");
		sellerMbr.setBizKindCd("US000901");
		sellerMbr.setBizRegNumber("2");
		sellerMbr.setBizUnregReason("US000603");
		sellerMbr.setCeoBirthDay("19270304");
		sellerMbr.setCeoName("테스트");
		sellerMbr.setCharger("김덕중");
		sellerMbr.setCordedTelephone("022224444");
		sellerMbr.setCordedTelephoneCountry("");
		sellerMbr.setCustomerEmail("sel@nate.com");
		sellerMbr.setCustomerPhone("022224444");
		sellerMbr.setCustomerPhoneCountry("dd");
		sellerMbr.setIsAccountReal("Y");
		sellerMbr.setIsBizRegistered("N");
		sellerMbr.setIsBizTaxable("Y");
		sellerMbr.setIsDeductible("");
		sellerMbr.setIsDomestic("Y");
		sellerMbr.setIsParent("");
		sellerMbr.setIsRealName("Y");
		sellerMbr.setIsRecvEmail("Y");
		sellerMbr.setIsRecvSMS("N");
		sellerMbr.setMarketCode("US001202");
		sellerMbr.setMarketStatus("");
		sellerMbr.setParentSellerKey("");
		sellerMbr.setRegDate("20120820");
		sellerMbr.setRepEmail("signtest@yopmail.com");
		sellerMbr.setRepFax("0211112222");
		sellerMbr.setRepFaxArea("");
		sellerMbr.setRepPhone("0211112222");
		sellerMbr.setRepPhoneArea("");
		sellerMbr.setRightProfileList("");
		sellerMbr.setSecedeDate("");
		sellerMbr.setSecedePathCd("");
		sellerMbr.setSecedeReasonCode("");
		sellerMbr.setSecedeReasonMessage("");
		sellerMbr.setSellerAddress("123123");
		sellerMbr.setSellerBirthDay("19781101");
		sellerMbr.setSellerBizCategory("test");
		sellerMbr.setSellerBizCorpNumber("7887845121");
		sellerMbr.setSellerBizNumber("");
		sellerMbr.setSellerBizType("test");
		sellerMbr.setSellerCategory("US011302");
		sellerMbr.setSellerCity("123123");
		sellerMbr.setSellerClass("US010101");
		sellerMbr.setSellerCompany("(주)동해물과 백두산이 마르고 닳도록");
		sellerMbr.setSellerCountry("");
		sellerMbr.setSellerDetailAddress("123123");
		sellerMbr.setSellerEmail("signtest@yopmail.com");
		sellerMbr.setSellerId("signtest");
		sellerMbr.setSellerKey("IF102158942020090723111912");
		sellerMbr.setSellerLanguage("US004301");
		sellerMbr.setSellerMainStatus("US010205");
		sellerMbr.setSellerName("김덕중");
		sellerMbr.setSellerNickName("판매자정보팝업용판매자명");
		sellerMbr.setSellerPhone("01083982958");
		sellerMbr.setSellerPhoneCountry("");
		sellerMbr.setSellerSex("F");
		sellerMbr.setSellerSSNumber("");
		sellerMbr.setSellerState("");
		sellerMbr.setSellerSubStatus("US010314");
		sellerMbr.setSellerTelecom("US001202");
		sellerMbr.setSellerZip("120830");
		sellerMbr.setTenantID("");
		sellerMbr.setVendorCode("");

		responseVO.setExtraRightList(eList);
		responseVO.setMbrClauseAgreeList(mList);
		responseVO.setMbrLglAgent(mbrLglAgent);
		responseVO.setSellerKey("IF102158942020090723111912");
		responseVO.setSellerMbr(sellerMbr);

		return responseVO;

	}

	/**
	 * <pre>
	 * 판매자회원 정산 정보 조회.
	 * </pre>
	 * 
	 * @return DetailAccountInformationRes
	 */
	// @RequestMapping(value = "/detailAccountInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailAccountInformationRes detailAccountInformation() {

		DetailAccountInformationRes responseVO = new DetailAccountInformationRes();

		// 판매자 문서정보
		List<Document> dList = new ArrayList<Document>();
		for (int i = 0; i < 1; i++) {
			Document document = new Document();
			document.setAccountChangeKey("K0101");
			document.setAccountChangeKey("A01");
			document.setDocumentCode("US001903");
			document.setDocumentName("구매내역2.png");
			document.setDocumentPath("/data/mem/IF1023139253020100810165321/20100827101614376_US001903.png");
			document.setDocumentSize("34803");
			document.setIsUsed("Y");
			document.setRegDate("2013-12-12");
			document.setSellerKey("IF1023599819420120111013407");
			document.setUpdateDate("2013-12-12");
			document.setUpdateID("chd");
			dList.add(document);
		}

		// 판매자 부가정보
		List<ExtraRight> eList = new ArrayList<ExtraRight>();

		// 판매자 정산정보
		SellerAccount sellerAccount = new SellerAccount();
		sellerAccount.setAbaCode("");
		sellerAccount.setAccountRealDate("20130904101746");
		sellerAccount.setBankAccount("18727881802001");
		sellerAccount.setBankAcctName("(주)동해물과 백두산이 마르고 닳도록");
		sellerAccount.setBankAddress("444");
		sellerAccount.setBankBranch("333");
		sellerAccount.setBankBranchCode("");
		sellerAccount.setBankCode("20");
		sellerAccount.setBankLocation("555");
		sellerAccount.setBankName("222");
		sellerAccount.setEndDate("29991231");
		sellerAccount.setIbanCode("");
		sellerAccount.setIsUsed("Y");
		sellerAccount.setReason("");
		sellerAccount.setSellerKey("IF102158942020090723111912");
		sellerAccount.setStartDate("20130904");
		sellerAccount.setSwiftCode("666");
		sellerAccount.setTpinCode("");

		// 판매자 정보
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setApproveDate("20120820");
		sellerMbr.setBizGrade("ggg");
		sellerMbr.setBizKindCd("US000901");
		sellerMbr.setBizRegNumber("2");
		sellerMbr.setBizUnregReason("US000603");
		sellerMbr.setCeoBirthDay("19270304");
		sellerMbr.setCeoName("테스트");
		sellerMbr.setCharger("김덕중");
		sellerMbr.setCordedTelephone("022224444");
		sellerMbr.setCordedTelephoneCountry("");
		sellerMbr.setCustomerEmail("sel@nate.com");
		sellerMbr.setCustomerPhone("022224444");
		sellerMbr.setCustomerPhoneCountry("dd");
		sellerMbr.setIsAccountReal("Y");
		sellerMbr.setIsBizRegistered("N");
		sellerMbr.setIsBizTaxable("Y");
		sellerMbr.setIsDeductible("");
		sellerMbr.setIsDomestic("Y");
		sellerMbr.setIsParent("");
		sellerMbr.setIsRealName("Y");
		sellerMbr.setIsRecvEmail("Y");
		sellerMbr.setIsRecvSMS("N");
		sellerMbr.setMarketCode("US001202");
		sellerMbr.setMarketStatus("");
		sellerMbr.setParentSellerKey("");
		sellerMbr.setRegDate("20120820");
		sellerMbr.setRepEmail("signtest@yopmail.com");
		sellerMbr.setRepFax("0211112222");
		sellerMbr.setRepFaxArea("");
		sellerMbr.setRepPhone("0211112222");
		sellerMbr.setRepPhoneArea("");
		sellerMbr.setRightProfileList("");
		sellerMbr.setSecedeDate("");
		sellerMbr.setSecedePathCd("");
		sellerMbr.setSecedeReasonCode("");
		sellerMbr.setSecedeReasonMessage("");
		sellerMbr.setSellerAddress("123123");
		sellerMbr.setSellerBirthDay("19781101");
		sellerMbr.setSellerBizCategory("test");
		sellerMbr.setSellerBizCorpNumber("7887845121");
		sellerMbr.setSellerBizNumber("");
		sellerMbr.setSellerBizType("test");
		sellerMbr.setSellerCategory("US011302");
		sellerMbr.setSellerCity("123123");
		sellerMbr.setSellerClass("US010101");
		sellerMbr.setSellerCompany("(주)동해물과 백두산이 마르고 닳도록");
		sellerMbr.setSellerCountry("");
		sellerMbr.setSellerDetailAddress("123123");
		sellerMbr.setSellerEmail("signtest@yopmail.com");
		sellerMbr.setSellerId("signtest");
		sellerMbr.setSellerKey("IF102158942020090723111912");
		sellerMbr.setSellerLanguage("US004301");
		sellerMbr.setSellerMainStatus("US010205");
		sellerMbr.setSellerName("김덕중");
		sellerMbr.setSellerNickName("판매자정보팝업용판매자명");
		sellerMbr.setSellerPhone("01083982958");
		sellerMbr.setSellerPhoneCountry("");
		sellerMbr.setSellerSex("F");
		sellerMbr.setSellerSSNumber("");
		sellerMbr.setSellerState("");
		sellerMbr.setSellerSubStatus("US010314");
		sellerMbr.setSellerTelecom("US001202");
		sellerMbr.setSellerZip("120830");
		sellerMbr.setTenantID("");
		sellerMbr.setVendorCode("");

		responseVO.setDocument(dList);
		responseVO.setExtraRight(eList);
		responseVO.setSellerAccount(sellerAccount);
		responseVO.setSellerKey("IF102158942020090723111912");
		responseVO.setSellerMbr(sellerMbr);

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 기본 정보 수정.
	 * </pre>
	 * 
	 * @return ModifyInformationRes
	 */
	@RequestMapping(value = "/modifyInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyInformationRes modifyInformation() {

		ModifyInformationRes responseVO = new ModifyInformationRes();

		responseVO.setSellerKey("IF102158942020090723111912");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 정산 정보 수정.
	 * </pre>
	 * 
	 * @return ModifyAccountInformationRes
	 */
	@RequestMapping(value = "/modifyAccountInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyAccountInformationRes modifyAccountInformation() {

		ModifyAccountInformationRes responseVO = new ModifyAccountInformationRes();
		responseVO.setSellerKey("IF102158942020090723111912");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 이메일 수정.
	 * </pre>
	 * 
	 * @return ModifyEmailRes
	 */
	@RequestMapping(value = "/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail() {

		ModifyEmailRes responseVO = new ModifyEmailRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 Password 수정.
	 * </pre>
	 * 
	 * @return ModifyPasswordRes
	 */
	@RequestMapping(value = "/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordRes modifyPassword() {

		ModifyPasswordRes responseVO = new ModifyPasswordRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 계정 승인.
	 * </pre>
	 * 
	 * @return ConfirmRes
	 */
	// @RequestMapping(value = "/confirm/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmRes confirm() {

		ConfirmRes responseVO = new ConfirmRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자 회원 전환 신청.
	 * </pre>
	 * 
	 * @return ConversionClassRes
	 */
	@RequestMapping(value = "/conversionClass/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConversionClassRes conversionClass() {

		ConversionClassRes responseVO = new ConversionClassRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 계정 잠금.
	 * </pre>
	 * 
	 * @return LockAccountRes
	 */
	// @RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public LockAccountRes lockAccount() {
		return new LockAccountRes("user1");
	}

	/**
	 * <pre>
	 * 판매자회원 실명 인증 정보 수정.
	 * </pre>
	 * 
	 * @return ModifyRealNameRes
	 */
	@RequestMapping(value = "/modifyRealName/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyRealNameRes modifyRealName() {

		ModifyRealNameRes responseVO = new ModifyRealNameRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 등록.
	 * </pre>
	 * 
	 * @return CreateSubsellerRes
	 */
	// @RequestMapping(value = "/createSubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSubsellerRes createSubseller() {

		CreateSubsellerRes responseVO = new CreateSubsellerRes();
		responseVO.setSubSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 수정.
	 * </pre>
	 * 
	 * @return ModifySubsellerRes
	 */
	// @RequestMapping(value = "/modifySubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifySubsellerRes modifySubseller() {

		ModifySubsellerRes responseVO = new ModifySubsellerRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 삭제.
	 * </pre>
	 * 
	 * @return RemoveSubsellerRes
	 */
	// @RequestMapping(value = "/removeSubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveSubsellerRes removeSubseller() {

		RemoveSubsellerRes responseVO = new RemoveSubsellerRes();
		responseVO.setRemoveCnt(1);

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 목록 조회.
	 * </pre>
	 * 
	 * @return ListSubsellerRes
	 */
	// @RequestMapping(value = "/listSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListSubsellerRes listSubseller() {

		ListSubsellerRes responseVO = new ListSubsellerRes();
		responseVO.setSellerKey("IF1023511101420120615164319");
		responseVO.setSubAccountCount(1);

		SellerMbr subSellerList = null;
		List<SellerMbr> myList = new ArrayList<SellerMbr>();

		for (int i = 0; i < 1; i++) {
			subSellerList = new SellerMbr();
			subSellerList.setSellerKey("IF1023511101420120615162000");
			subSellerList.setSellerId("signtest_subid01");
			subSellerList.setSellerEmail("signtest_subid01@yopmail.com");
			subSellerList.setSellerPhoneCountry("102");
			subSellerList.setSellerPhone("0101231234");
			myList.add(subSellerList);

		}
		responseVO.setSubSellerList(myList);

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 상세 조회.
	 * </pre>
	 * 
	 * @return DetailSubsellerRes
	 */
	// @RequestMapping(value = "/detailSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailSubsellerRes detailSubseller() {

		DetailSubsellerRes responseVO = new DetailSubsellerRes();
		SellerMbr sellerInfo = new SellerMbr();

		sellerInfo.setSellerId("user1");
		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerMainStatus("US010704");
		sellerInfo.setSellerSubStatus("US010301");
		responseVO.setSellerMbr(sellerInfo);

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 ID 중복 조회.
	 * </pre>
	 * 
	 * @return DuplicateBySubsellerIdRes
	 */
	// @RequestMapping(value = "/duplicateBySubsellerId/v1", method = RequestMethod.GET)
	@ResponseBody
	public DuplicateBySubsellerIdRes duplicateBySubsellerId() {

		DuplicateBySubsellerIdRes responseVO = new DuplicateBySubsellerIdRes();
		responseVO.setIsRegistered("Y");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 탈퇴.
	 * </pre>
	 * 
	 * @return WithdrawRes
	 */
	// @RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
	@ResponseBody
	public WithdrawRes withdraw() {

		WithdrawRes responseVO = new WithdrawRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자 탈퇴사유 목록 조회.
	 * </pre>
	 * 
	 * @return ListWithdrawalReasonRes
	 */
	// @RequestMapping(value = "/listWithdrawalReason/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListWithdrawalReasonRes listWithdrawalReason() {

		ListWithdrawalReasonRes responseVO = new ListWithdrawalReasonRes();

		SecedeReson secedeResonList = null;
		List<SecedeReson> myList = new ArrayList<SecedeReson>();
		for (int i = 0; i < 1; i++) {
			secedeResonList = new SecedeReson();
			secedeResonList.setSecedeReasonCode("US010401");
			secedeResonList.setSecedeReasonMessage("ID 변경");
			myList.add(secedeResonList);
		}

		responseVO.setSecedeResonList(myList);

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자 회원의 인증키 생성.
	 * </pre>
	 * 
	 * @return CreateAuthKeyRes
	 */
	// @RequestMapping(value = "/createAuthKey/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateAuthKeyRes createAuthKey() {
		CreateAuthKeyRes res = new CreateAuthKeyRes();
		res.setSessionKey("IFAd12e2dad23Sd");
		return res;
	}

	/**
	 * <pre>
	 * 판매자 회원의 인증키 조회.
	 * </pre>
	 * 
	 * @return SearchAuthKeyRes
	 */
	// @RequestMapping(value = "/searchAuthKey/v1", method = RequestMethod.GET)
	public @ResponseBody
	SearchAuthKeyRes searchAuthKey() {
		SearchAuthKeyRes res = new SearchAuthKeyRes();
		res.setExpireDate("20141023");
		res.setSessionKey("IWd12dawda2a23adwd");
		return res;
	}

	/**
	 * <pre>
	 * 판매자 회원 인증키 폐기.
	 * </pre>
	 * 
	 * @return AbrogationAuthKeyRes
	 */
	// @RequestMapping(value = "/abrogationAuthKey/v1", method = RequestMethod.GET)
	public @ResponseBody
	AbrogationAuthKeyRes abrogationAuthKey() {
		AbrogationAuthKeyRes res = new AbrogationAuthKeyRes();
		return res;
	}

}
