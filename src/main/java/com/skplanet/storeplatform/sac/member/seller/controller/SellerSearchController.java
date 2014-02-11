package com.skplanet.storeplatform.sac.member.seller.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListBanksByCountryRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchAuthKeyReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchPasswordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;

/**
 * 판매자 회원 조회 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 김경복, 부르칸.
 */
@Controller
@RequestMapping(value = "/member/seller")
public class SellerSearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSearchController.class);

	@Autowired
	private SellerSearchService sellerSearchService;

	/**
	 * Data Binding.
	 */
	ObjectMapper objMapper = new ObjectMapper();

	/**
	 * <pre>
	 * 2.2.2 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DuplicateByIdEmailRes
	 */
	@RequestMapping(value = "/duplicateByIdEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	private DuplicateByIdEmailRes duplicateByIdEmail(SacRequestHeader header,
			@RequestBody @Validated DuplicateByIdEmailReq req) {
		return this.sellerSearchService.duplicateByIdEmail(header, req);
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DetailInformationRes
	 */
	@RequestMapping(value = "/detailInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailInformationRes detailInformation(SacRequestHeader header,
			@RequestBody @Validated DetailInformationReq req) {
		LOGGER.debug("request param : {}", req.toString());

		String sellerId = StringUtil.nvl(req.getSellerId(), "");
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");
		String aid = StringUtil.nvl(req.getAid(), "");

		req.setSellerId(sellerId);
		req.setSellerKey(sellerKey);
		req.setAid(aid);

		if (sellerId.equals("") && sellerKey.equals("") && aid.equals(""))
			throw new StorePlatformException("SAC_MEM_0001", "aid,sellerKey,sellerId");

		return this.sellerSearchService.detailInformation(header, req);
	}

	/**
	 * <pre>
	 * 판매자회원 정산정보조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DetailAccountInformationRes
	 */
	@RequestMapping(value = "/detailAccountInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header,
			@Validated DetailAccountInformationReq req, BindingResult result) {

		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}

		return this.sellerSearchService.detailAccountInformation(header, req);
	}

	/**
	 * <pre>
	 * 탈퇴 사유 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return ListWithdrawalReasonRes
	 */
	@RequestMapping(value = "/listWithdrawalReason/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header, SacRequestHeader requestHeader)
			throws Exception {
		TenantHeader theader = requestHeader.getTenantHeader();

		LOGGER.debug("------------------------------------language : {}", theader.getLangCd());
		return this.sellerSearchService.listWithdrawalReason(header, theader.getLangCd());
	}

	/**
	 * <pre>
	 * 판매자 ID 찾기.
	 * </pre>
	 * 
	 * @param req
	 * @return SearchIdRes
	 */
	@RequestMapping(value = "/searchId2/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchIdRes searchId(SacRequestHeader header, @RequestBody @Validated SearchIdReq req) {

		int Stat = 0;
		req.setSellerBizNumber(StringUtil.nvl(req.getSellerBizNumber(), ""));
		req.setSellerCompany(StringUtil.nvl(req.getSellerCompany(), ""));
		req.setSellerEmail(StringUtil.nvl(req.getSellerEmail(), ""));
		req.setSellerPhone(StringUtil.nvl(req.getSellerPhone(), ""));
		if (req.getSellerBizNumber().equals(""))
			Stat++;
		if (req.getSellerCompany().equals(""))
			Stat++;
		if (req.getSellerEmail().equals(""))
			Stat++;
		if (req.getSellerPhone().equals(""))
			Stat++;

		if (Stat == 4) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerBizNumber,sellerCompany,sellerEmail,sellerPhone");
		}

		return this.sellerSearchService.searchId(header, req);
	}

	/**
	 * <pre>
	 * Password 보안 질문 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return ListPasswordReminderQuestionRes
	 */
	@RequestMapping(value = "/listPasswordReminderQuestion2/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			@Validated ListPasswordReminderQuestionReq req, BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}
		return this.sellerSearchService.listPasswordReminderQuestion(header, req);
	}

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 * 
	 * @param req
	 * @return CheckPasswordReminderQuestionRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPasswordReminderQuestion2/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			@RequestBody @Validated CheckPasswordReminderQuestionReq req, BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}
		return this.sellerSearchService.checkPasswordReminderQuestion(header, req);
	}

	/**
	 * <pre>
	 * 판매자 Password 찾기.
	 * </pre>
	 * 
	 * @param req
	 * @return SearchIdRes
	 */
	@RequestMapping(value = "/searchPassword2/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchPasswordRes searchPassword(SacRequestHeader header, @RequestBody @Validated SearchPasswordReq req,
			BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}
		return this.sellerSearchService.searchPassword(header, req);
	}

	/**
	 * <pre>
	 * 2.2.27.	판매자 회원 인증키 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return SearchAuthKeyRes
	 */
	@RequestMapping(value = "/detailInfomationByAuthorizationKey/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailInformationRes searchAuthKey(SacRequestHeader header, @Validated SearchAuthKeyReq req,
			BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}
		return this.sellerSearchService.searchAuthKey(header, req);
	}

	/**
	 * <pre>
	 * 나라별 해외 은행 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return ListBanksByCountryRes
	 */
	@RequestMapping(value = "/listBanksByCountry/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListBanksByCountryRes ListBanksByCountry(SacRequestHeader header) {
		return this.sellerSearchService.listBanksByCountry(header);
	}

}
