package com.skplanet.storeplatform.sac.member.seller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListWithdrawalReasonRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SearchIdRes;
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
	 * 5.2.2 판매자 회원 ID/Email 중복 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DuplicateByIdEmailRes
	 */
	@RequestMapping(value = "/duplicateByIdEmail/v1", method = RequestMethod.GET)
	private @ResponseBody
	DuplicateByIdEmailRes duplicateByIdEmail(SacRequestHeader header, DuplicateByIdEmailReq req) {
		// Req Debug
		LOGGER.debug("req : {}", req);
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
	@RequestMapping(value = "/detailInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailInformationRes detailInformation(SacRequestHeader header, DetailInformationReq req) throws Exception {
		LOGGER.debug("request param : {}", req.toString());

		String keyType = StringUtil.nvl(req.getKeyType(), "");
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");
		String aid = StringUtil.nvl(req.getAid(), "");

		if (keyType.equals(""))
			throw new Exception("필수 파라미터 미존재");

		if (sellerKey.equals("") && aid.equals(""))
			throw new Exception("필수 파라미터 미존재");

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
	public DetailAccountInformationRes detailAccountInformation(SacRequestHeader header, DetailAccountInformationReq req)
			throws Exception {

		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");

		if (sellerKey.equals(""))
			throw new Exception("필수 파라미터 미존재");

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
	public ListWithdrawalReasonRes listWithdrawalReason(SacRequestHeader header,
			@RequestHeader("Accept-Language") String language) throws Exception {
		LOGGER.debug("------------------------------------language : {}", language);
		return this.sellerSearchService.listWithdrawalReason(header, language);
	}

	/**
	 * <pre>
	 * 판매자 ID 찾기.
	 * </pre>
	 * 
	 * @param req
	 * @return SearchIdRes
	 */
	@RequestMapping(value = "/searchId/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchIdRes searchId(SacRequestHeader header, SearchIdReq req) throws Exception {

		String sellerBizNumber = StringUtil.nvl(req.getSellerBizNumber(), "");
		String sellerCompany = StringUtil.nvl(req.getSellerCompany(), "");
		String sellerEmail = StringUtil.nvl(req.getSellerEmail(), "");
		String sellerPhone = StringUtil.nvl(req.getSellerPhone(), "");

		if (sellerBizNumber.equals("") & sellerCompany.equals("") & sellerEmail.equals("") & sellerPhone.equals("")) {
			throw new Exception("필수 파라미터 미존재");
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
	@RequestMapping(value = "/ListPasswordReminderQuestion/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListPasswordReminderQuestionRes listPasswordReminderQuestion(SacRequestHeader header,
			@RequestHeader("Accept-Language") String language) throws Exception {
		LOGGER.debug("------------------------------------language : {}", language);
		return this.sellerSearchService.listPasswordReminderQuestion(header, language);
	}
}
