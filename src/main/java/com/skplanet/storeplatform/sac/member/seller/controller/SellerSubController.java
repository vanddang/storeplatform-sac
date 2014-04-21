package com.skplanet.storeplatform.sac.member.seller.controller;

import org.apache.commons.lang.StringUtils;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSubService;

/**
 * 판매자 서브계정 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 김경복, 부르칸.
 */
@Controller
@RequestMapping(value = "/member/seller")
public class SellerSubController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubController.class);

	@Autowired
	private SellerSubService sellerSubService;

	/**
	 * <pre>
	 * 2.2.18.	서브계정 등록.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateSubsellerReq
	 * @return CreateSubsellerRes
	 */
	@RequestMapping(value = "/createSubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateSubsellerRes createSubseller(SacRequestHeader header, @RequestBody @Validated CreateSubsellerReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.isBlank(req.getSubSellerPw())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerPw");
		} else if (StringUtils.isBlank(req.getSubSellerId())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerId");
		}
		CreateSubsellerRes res = this.sellerSubService.createSubseller(header, req);
		LOGGER.info("Response : {}", res.getSubSellerKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.19.	서브계정 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateSubsellerReq
	 * @return CreateSubsellerRes
	 */
	@RequestMapping(value = "/modifySubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	UpdateSubsellerRes modifySubseller(SacRequestHeader header, @RequestBody @Validated UpdateSubsellerReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.isBlank(req.getSubSellerKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerKey");
		}

		UpdateSubsellerRes res = this.sellerSubService.updateSubseller(header, req);
		LOGGER.info("Response : {}", res.getSubSellerKey());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.20.	서브계정 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveSubsellerReq
	 * @return RemoveSubsellerRes
	 */
	@RequestMapping(value = "/removeSubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	RemoveSubsellerRes removeSubseller(SacRequestHeader header, @RequestBody @Validated RemoveSubsellerReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		RemoveSubsellerRes res = this.sellerSubService.removeSubseller(header, req);
		LOGGER.info("Response : {}", res.getRemoveCnt());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.21.	서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	@RequestMapping(value = "/listSubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListSubsellerRes listSubseller(SacRequestHeader header, @RequestBody @Validated ListSubsellerReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		ListSubsellerRes res = this.sellerSubService.listSubseller(header, req);
		LOGGER.info("Response : {}", res.getSubAccountCount());
		return res;
	}

	/**
	 * <pre>
	 * 2.2.22.	서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailSubsellerReq
	 * @return DetailSubsellerRes
	 */
	@RequestMapping(value = "/detailSubseller/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, @RequestBody @Validated DetailSubsellerReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		DetailSubsellerRes res = this.sellerSubService.detailSubseller(header, req);
		LOGGER.info("Response : {}", res.getSubSellerMbr().getSubSellerKey());
		return res;
	}

}
