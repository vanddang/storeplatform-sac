package com.skplanet.storeplatform.sac.member.seller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
	 * <pre>
	 * 2.2.2 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DuplicateByIdEmailReq
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
	 * @param header
	 *            SacRequestHeaders
	 * @param req
	 *            DetailInformationReq
	 * @return DetailInformationRes
	 */
	@RequestMapping(value = "/detailInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailInformationRes detailInformation(SacRequestHeader header,
			@RequestBody @Validated DetailInformationReq req) {
		LOGGER.debug("request param : {}", req.toString());

		if (StringUtils.isBlank(req.getSellerId()) && StringUtils.isBlank(req.getAid())
				&& StringUtils.isBlank(req.getSellerKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "aid, sellerKey, sellerId");
		}

		return this.sellerSearchService.detailInformation(header, req);
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
	@RequestMapping(value = "/detailInformationForProduct/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailInformationForProductRes detailInformationApp(SacRequestHeader header,
			@Validated DetailInformationForProductReq req) {
		LOGGER.debug("request param : {}", req.toString());

		return this.sellerSearchService.detailInformationApp(header, req);
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
	@RequestMapping(value = "/detailAccountInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header,
			@Validated DetailAccountInformationReq req) {
		return this.sellerSearchService.detailAccountInformation(header, req);
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
	@RequestMapping(value = "/listWithdrawalReason/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header) {
		LOGGER.debug("------------------------------------language : {}", header.getTenantHeader().getLangCd());
		return this.sellerSearchService.listWithdrawalReason(header);
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
	@RequestMapping(value = "/searchId/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchIdRes searchId(SacRequestHeader header, @RequestBody @Validated SearchIdReq req) {

		// sellerBizNumber, sellerCompany, sellerEmail, sellerPhone 중 하나 필수.
		if (StringUtils.isBlank(req.getSellerBizNumber()) && StringUtils.isBlank(req.getSellerCompany())
				&& StringUtils.isBlank(req.getSellerEmail()) && StringUtils.isBlank(req.getSellerPhone())) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerBizNumber, sellerCompany, sellerEmail, sellerPhone");
		}

		return this.sellerSearchService.searchId(header, req);
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
	@RequestMapping(value = "/listPasswordReminderQuestion/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			@Validated ListPasswordReminderQuestionReq req) {
		return this.sellerSearchService.listPasswordReminderQuestion(header, req);
	}

	/**
	 * <pre>
	 * Password 보안 질문 조회 All.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return ListPasswordReminderQuestionRes
	 */
	@RequestMapping(value = "/listPasswordReminderQuestionAll/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionAllRes listPasswordReminderQuestionAll(SacRequestHeader header) {
		return this.sellerSearchService.listPasswordReminderQuestionAll(header);
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
	@RequestMapping(value = "/checkPasswordReminderQuestion/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckPasswordReminderQuestionRes checkPasswordReminderQuestion(SacRequestHeader header,
			@RequestBody @Validated CheckPasswordReminderQuestionReq req) {
		return this.sellerSearchService.checkPasswordReminderQuestion(header, req);
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
	@RequestMapping(value = "/searchPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchPasswordRes searchPassword(SacRequestHeader header, @RequestBody @Validated SearchPasswordReq req) {
		return this.sellerSearchService.searchPassword(header, req);
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
	@RequestMapping(value = "/detailInfomationByAuthorizationKey/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchAuthKeyRes searchAuthKey(SacRequestHeader header, @Validated SearchAuthKeyReq req) {
		return this.sellerSearchService.searchAuthKey(header, req);
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
	@RequestMapping(value = "/listBanksByCountry/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListBanksByCountryRes ListBanksByCountry(SacRequestHeader header) {
		return this.sellerSearchService.listBanksByCountry(header);
	}

}
