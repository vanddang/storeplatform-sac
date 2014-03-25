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
	 * 5.2.18.	서브계정 등록.
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
		if (StringUtils.isBlank(req.getSubSellerPw())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerPW");
		} else if (StringUtils.isBlank(req.getSubSellerId())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerId");
		}
		return this.sellerSubService.createSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.19.	서브계정 수정.
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
		if (StringUtils.isBlank(req.getSubSellerKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerKey");
		}
		return this.sellerSubService.updateSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.20.	서브계정 삭제.
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
		return this.sellerSubService.removeSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.21.	서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	@RequestMapping(value = "/listSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req) {
		return this.sellerSubService.listSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.22.	서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailSubsellerReq
	 * @return DetailSubsellerRes
	 */
	@RequestMapping(value = "/detailSubseller/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req) {
		return this.sellerSubService.detailSubseller(header, req);
	}

}
