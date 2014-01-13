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

import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraDocument;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccountInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerId;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.SubSeller;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConversionClassRes;
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
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRes create() {

		CreateRes responseVO = new CreateRes();
		SellerInfo sellerInfo = new SellerInfo();

		sellerInfo.setSellerId("user1");
		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerMainStatus("US010704");
		sellerInfo.setSellerSubStatus("US010301");
		responseVO.setSellerInfo(sellerInfo);

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
	@RequestMapping(value = "/duplicateByIdEmail/v1", method = RequestMethod.GET)
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
	@RequestMapping(value = "/authorize/v1", method = RequestMethod.GET)
	@ResponseBody
	public AuthorizeRes authorize() {

		AuthorizeRes responseVO = new AuthorizeRes();
		SellerInfo sellerInfo = new SellerInfo();

		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerClass("US010101");
		sellerInfo.setSellerMainStatus("US010704");
		sellerInfo.setSellerSubStatus("US010301");
		responseVO.setSellerInfo(sellerInfo);

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 ID찾기.
	 * </pre>
	 * 
	 * @return SearchIdRes
	 */
	@RequestMapping(value = "/searchId/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchIdRes searchId() {

		SearchIdRes responseVO = new SearchIdRes();
		SellerId sellerIdList = null;

		List<SellerId> myList = new ArrayList<SellerId>();
		for (int i = 0; i < 1; i++) {
			sellerIdList = new SellerId();
			sellerIdList.setSellerId("user1");
			myList.add(sellerIdList);
		}
		responseVO.setSllerIdList(myList);
		responseVO.setSellerName("abc@google.com");
		responseVO.setSellerEmail("홍길동");
		responseVO.setRegDate("20131226000000");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 Password찾기.
	 * </pre>
	 * 
	 * @return SearchPasswordRes
	 */
	@RequestMapping(value = "/searchPassword/v1", method = RequestMethod.GET)
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
	@RequestMapping(value = "/checkPasswordReminderQuestion/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/listPasswordReminderQuestion/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion() {

		ListPasswordReminderQuestionRes responseVO = new ListPasswordReminderQuestionRes();
		PwReminder pwReminderList = null;

		List<PwReminder> myList = new ArrayList<PwReminder>();
		for (int i = 0; i < 1; i++) {
			pwReminderList = new PwReminder();
			pwReminderList.setPwReminderCode("TA01");
			pwReminderList.setPwReminderQ("test");
			myList.add(pwReminderList);
		}
		responseVO.setPwReminderList(myList);
		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 기본 정보 조회.
	 * </pre>
	 * 
	 * @return DetailInformationRes
	 */
	@RequestMapping(value = "/detailInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailInformationRes detailInformation() {

		DetailInformationRes responseVO = new DetailInformationRes();
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey("IF1023599819420120111013407");
		sellerMbr.setSellerClass("US010101");
		sellerMbr.setSellerCategory("US011301");
		sellerMbr.setSellerMainStatus("US010704");
		sellerMbr.setSellerSubStatus("US010301");
		sellerMbr.setSellerTelecom("SKT");
		sellerMbr.setSellerPhoneCountry("82");
		sellerMbr.setSellerPhone("01011112222");
		sellerMbr.setSellerEmail("abc@gmail.com");
		sellerMbr.setIsRecvEmail("Y");
		sellerMbr.setSellerName("홍길동");
		sellerMbr.setSellerNickName("Best Seller");
		sellerMbr.setSellerSex("M");
		sellerMbr.setSellerBirthDay("19801105");
		sellerMbr.setSellerSSNumber("130101-1111111");
		sellerMbr.setSellerZip("135070");
		sellerMbr.setSellerAddress("삼평동 680");
		sellerMbr.setSellerDetailAddress("H스퀘어 S동 4층");
		sellerMbr.setSellerCity("123123");
		sellerMbr.setSellerState("LA");
		sellerMbr.setSellerCountry("USA");
		sellerMbr.setSellerLanguage("ko");
		sellerMbr.setIsForeign("Y");
		sellerMbr.setCustomerPhoneCountry("82");
		sellerMbr.setCustomerPhone("01011112222");
		sellerMbr.setCustomerEmail("abc@gmail.com");
		responseVO.setSellerMbr(sellerMbr);
		responseVO.setSellerKey("IF1023599819420120111013407");

		return responseVO;

	}

	/**
	 * <pre>
	 * 판매자회원 정산 정보 조회.
	 * </pre>
	 * 
	 * @return DetailAccountInformationRes
	 */
	@RequestMapping(value = "/detailAccountInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailAccountInformationRes detailAccountInformation() {

		DetailAccountInformationRes responseVO = new DetailAccountInformationRes();
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerKey("IF1023599819420120111013407");
		sellerInfo.setSellerClass("US010101");
		sellerInfo.setSellerCategory("US011301");
		sellerInfo.setSellerMainStatus("US010704");
		sellerInfo.setSellerSubStatus("US010301");
		sellerInfo.setSellerId("user1");
		sellerInfo.setSellerZip("135070");
		sellerInfo.setSellerAddress("삼평동 680");
		sellerInfo.setSellerDetailAddress("H스퀘어 S동 4층");
		responseVO.setSellerInfo(sellerInfo);

		SellerAccountInfo sellerAccountInfo = new SellerAccountInfo();
		sellerAccountInfo.setSellerBizCorpNumber("incross");
		sellerAccountInfo.setSellerBizType("종목");
		sellerAccountInfo.setSellerBizCategory("업태");
		sellerAccountInfo.setRepPhoneArea("82");
		sellerAccountInfo.setRepPhone("070-1111-2222");
		sellerAccountInfo.setRepFaxArea("82");
		sellerAccountInfo.setRepFax("070-1111-3333");
		sellerAccountInfo.setRepEmail("abc@email.com");
		sellerAccountInfo.setBpRate("ABC");
		sellerAccountInfo.setVendorCode("A01");
		sellerAccountInfo.setIsBizRegistered("Y");
		sellerAccountInfo.setBizRegNumber("1201-1212-1222");
		sellerAccountInfo.setBizUnregReason("US000606");
		sellerAccountInfo.setIsBizTaxable("Y");
		sellerAccountInfo.setBizGrade("A01");
		sellerAccountInfo.setIsDeductible("Y");
		sellerAccountInfo.setMarketCode("US001201");
		sellerAccountInfo.setMarketStatus("A01");
		sellerAccountInfo.setBankCode("OR000221");
		sellerAccountInfo.setBankName("신한은행");
		sellerAccountInfo.setIsAccountReal("Y");
		sellerAccountInfo.setAccountRealDate("20131216000000");
		sellerAccountInfo.setBankBranch("테헤란로");
		sellerAccountInfo.setBankBranchCode("A01");
		sellerAccountInfo.setSwiftCode("123");
		sellerAccountInfo.setAbaCode("A01");
		sellerAccountInfo.setIbanCode("A01");
		sellerAccountInfo.setTpinCode("A01");
		sellerAccountInfo.setBankAccount("360ea2f64f770192c02e09e390480715");
		sellerAccountInfo.setBankAcctName("홍길동");
		responseVO.setSellerAccountInfo(sellerAccountInfo);

		ExtraDocument extraDocumentList = null;
		List<ExtraDocument> myList = new ArrayList<ExtraDocument>();
		for (int i = 0; i < 1; i++) {
			extraDocumentList = new ExtraDocument();
			extraDocumentList.setDocumentCode("US001903");
			extraDocumentList.setDocumentPath("/data/mem/IF1023139253020100810165321/20100827101614376_US001903.png");
			extraDocumentList.setDocumentName("구매내역2.png");
			extraDocumentList.setDocumentSize("34803");
			myList.add(extraDocumentList);
		}
		responseVO.setExtraDocumentList(myList);

		responseVO.setRegDate("20131226000000");
		responseVO.setAppoveDate("20131226000000");

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

		responseVO.setSellerId("IF1023599819420120111013407");
		responseVO.setSellerKey("User1");

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
		responseVO.setSellerKey("User1");

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
	@RequestMapping(value = "/confirm/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/lockAccount/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/createSubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSubsellerRes createSubseller() {

		CreateSubsellerRes responseVO = new CreateSubsellerRes();
		responseVO.setSellerKey("User1");

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 수정.
	 * </pre>
	 * 
	 * @return ModifySubsellerRes
	 */
	@RequestMapping(value = "/modifySubseller/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/removeSubseller/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/listSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListSubsellerRes listSubseller() {

		ListSubsellerRes responseVO = new ListSubsellerRes();
		responseVO.setMasSellerKey("IF1023511101420120615164319");
		responseVO.setSubSellerListSize(1);

		SubSeller subSellerList = null;
		List<SubSeller> myList = new ArrayList<SubSeller>();

		SellerRight sellerRightList = null;
		List<SellerRight> myList2 = new ArrayList<SellerRight>();

		for (int i = 0; i < 1; i++) {
			subSellerList = new SubSeller();
			subSellerList.setSellerKey("IF1023511101420120615162000");
			subSellerList.setSellerId("signtest_subid01");
			subSellerList.setSellerPw("123qwe");
			subSellerList.setSellerEmail("signtest_subid01@yopmail.com");
			subSellerList.setSellerPhoneCountry("102");
			subSellerList.setSellerPhone("0101231234");
			subSellerList.setSellerMemo("signtest의 서브계정01 메모");
			subSellerList.setLastLoginDttm("201312101006");

			for (int ii = 0; ii < 1; ii++) {
				sellerRightList = new SellerRight();
				sellerRightList.setRightProfile("DE0101");
				sellerRightList.setRightProfileValue("상품현황");
				myList2.add(sellerRightList);
			}
			subSellerList.setSellerRightList(myList2);

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
	@RequestMapping(value = "/detailSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailSubsellerRes detailSubseller() {

		DetailSubsellerRes responseVO = new DetailSubsellerRes();
		responseVO.setMasSellerKey("IF1023511101420120615164319");
		responseVO.setSellerKey("IF1023511101420120615162000");
		responseVO.setSellerId("signtest_subid01");
		responseVO.setSellerPw("123qwe");
		responseVO.setSellerEmail("signtest_subid01@yopmail.com");
		responseVO.setSellerPhoneCountry("112");
		responseVO.setSellerPhone("0101231234");
		responseVO.setSellerMemo("signtest의 서브계정01 메모");
		responseVO.setLastLoginDttm("201312301006");

		SellerRight sellerRightList = null;
		List<SellerRight> myList = new ArrayList<SellerRight>();
		for (int ii = 0; ii < 1; ii++) {
			sellerRightList = new SellerRight();
			sellerRightList.setRightProfile("DE0101");
			sellerRightList.setRightProfileValue("상품현황");
			myList.add(sellerRightList);
		}

		responseVO.setSellerRightList(myList);

		return responseVO;
	}

	/**
	 * <pre>
	 * 서브계정 ID 중복 조회.
	 * </pre>
	 * 
	 * @return DuplicateBySubsellerIdRes
	 */
	@RequestMapping(value = "/duplicateBySubsellerId/v1", method = RequestMethod.GET)
	@ResponseBody
	public DuplicateBySubsellerIdRes duplicateBySubsellerId() {

		DuplicateBySubsellerIdRes responseVO = new DuplicateBySubsellerIdRes();
		responseVO.setDuplicateYn("Y");

		return responseVO;
	}

	/**
	 * <pre>
	 * 판매자회원 탈퇴.
	 * </pre>
	 * 
	 * @return WithdrawRes
	 */
	@RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/listWithdrawalReason/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListWithdrawalReasonRes listWithdrawalReason() {

		ListWithdrawalReasonRes responseVO = new ListWithdrawalReasonRes();

		SecedeReson secedeResonList = null;
		List<SecedeReson> myList = new ArrayList<SecedeReson>();
		for (int i = 0; i < 1; i++) {
			secedeResonList = new SecedeReson();
			secedeResonList.setSecedeReasonCd("US010401");
			secedeResonList.setSecedeReasonDes("ID 변경");
			myList.add(secedeResonList);
		}

		responseVO.setSecedeResonList(myList);

		return responseVO;
	}

}
