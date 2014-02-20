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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
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
@RequestMapping(value = "/member/seller")
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
	 * 5.2.18.	서브계정 등록.
	 * </pre>
	 * 
	 * @param req
	 * @return CreateSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/createSubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateSubsellerRes createSubseller(SacRequestHeader header, @RequestBody @Validated CreateSubsellerReq req,
			BindingResult result) {

		if (StringUtil.nvl(req.getSellerKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerKey");
		} else if (StringUtil.nvl(req.getSubSellerID(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerID");
		} else if (StringUtil.nvl(req.getMemberPW(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "memberPW");
		}
		req.setIsNew("Y");
		return this.sellerSubService.createSubseller(header, req);
	}

	/**
	 * <pre>
	 * 5.2.19.	서브계정 수정.
	 * </pre>
	 * 
	 * @param req
	 * @return CreateSubsellerRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifySubseller/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateSubsellerRes modifySubseller(SacRequestHeader header, @RequestBody @Validated CreateSubsellerReq req,
			BindingResult result) {

		if (StringUtil.nvl(req.getSellerKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerKey");
		} else if (StringUtil.nvl(req.getSubSellerID(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerID");
		} else if (StringUtil.nvl(req.getSubSellerKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "subSellerKey");
		}
		req.setIsNew("N");
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
	RemoveSubsellerRes removeSubseller(SacRequestHeader header, @RequestBody @Validated RemoveSubsellerReq req,
			BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerKey");
		}
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
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req, BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerKey");
		}

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
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req, BindingResult result) {
		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", "sellerKey");
		}

		return this.sellerSubService.detailSubseller(header, req);
	}

}
