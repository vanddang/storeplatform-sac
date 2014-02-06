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

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSubService;

/**
 * 판매자 서브계정 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 김경복, 부르칸.
 */
@Controller
@RequestMapping(value = "/dev/member/seller")
public class SellerSubController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubController.class);

	@Autowired
	private SellerSubService sellerSubService;

	/**
	 * Data Binding.
	 */
	ObjectMapper objMapper = new ObjectMapper();

	/**
	 * <pre>
	 * 5.2.18.	서브계정 등록/수정.
	 * </pre>
	 * 
	 * @param req
	 * @return CreateSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/createSubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateSubsellerRes createSubseller(SacRequestHeader header, @RequestBody @Validated CreateSubsellerReq req) {
		return this.sellerSubService.createSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.20.	서브계정 삭제.
	 * </pre>
	 * 
	 * @param req
	 * @return RemoveSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeSubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	RemoveSubsellerRes removeSubseller(SacRequestHeader header, @RequestBody @Validated RemoveSubsellerReq req) {
		// LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));
		return this.sellerSubService.removeSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.21.	서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return ListSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/listSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req) {
		// LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));

		return this.sellerSubService.listSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.22.	서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DetailSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req) {
		// LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));

		return this.sellerSubService.detailSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.23 판매자 회원 서브계정 ID 중복 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DuplicateByIdEmailRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/duplicateBySubsellerId/v1", method = RequestMethod.POST)
	public @ResponseBody
	DuplicateBySubsellerIdRes duplicateBySubsellerId(SacRequestHeader header,
			@RequestBody @Validated DuplicateBySubsellerIdReq req, BindingResult result) throws Exception {
		LOGGER.debug("Request : {}", this.objMapper.writeValueAsString(req));
		// TODO Exception (01/17이후 적용)
		if (result.hasErrors()) {
			throw new RuntimeException("parameter error~~~");
		}
		return this.sellerSubService.duplicateBySubsellerId(header, req);
	}

}
