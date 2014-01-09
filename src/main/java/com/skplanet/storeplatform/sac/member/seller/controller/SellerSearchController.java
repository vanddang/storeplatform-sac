package com.skplanet.storeplatform.sac.member.seller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;

/**
 * 판매자 회원 조회 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 김경복, 부르칸.
 */
@Controller
@RequestMapping(value = "/member/seller/junit")
public class SellerSearchController {

	private static final Logger logger = LoggerFactory.getLogger(SellerSearchController.class);

	@Autowired
	private SellerSearchService sellerSearchService;

	/**
	 * <pre>
	 * 5.2.2 판매자 회원 ID/Email 중복 조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/duplicateByIdEmail/v1", method = RequestMethod.GET)
	private @ResponseBody
	DuplicateByIdEmailRes duplicateByIdEmail(@RequestBody DuplicateByIdEmailReq req) {
		// Req Debug
		logger.info("req : {}", req);
		return this.sellerSearchService.duplicateByIdEmail(req);
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detailInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailInformationRes detailInformation(@RequestBody DetailInformationReq req) throws Exception {

		return this.sellerSearchService.detailInformation(req);
	}
}
