/*
 * Copyright (c) 2015 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherSpecificReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.freepass.service.VoucherService;

/**
 * 정액제 Controller
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */
@Controller
@RequestMapping("/display/voucher")
public class VoucherController {
	private transient Logger logger = LoggerFactory.getLogger(VoucherController.class);

	@Autowired
	private VoucherService voucherService;

	/**
	 * <pre>
	 * 이용권 상품 조회(자유 이용권 목록 조회)
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public VoucherListRes searchVoucherList(@Validated VoucherListReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVoucherList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.voucherService.searchVoucherList(req, header);
	}

	/**
	 * <pre>
	 * 이용권 상품 상세 조회 (자유 이용권 상품 상세 조회)
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public VoucherDetailRes searchVoucherDetail(@Validated @RequestBody VoucherDetailReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVoucherDetail Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.voucherService.searchVoucherDetail(req, header);
	}

	
	
	
	
	
	
	
	
	
	
	

	/**
	 * <pre>
	 * [I03000172] 2.4.1.3. 특정 상품이 적용된 이용권 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return VoucherListRes
	 */

	@RequestMapping(value = "/specific/v1", method = RequestMethod.POST)
	@ResponseBody
	public VoucherListRes searchVoucherSpecific(@RequestBody @Validated VoucherSpecificReq req,
			SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVoucherSpecific Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.voucherService.searchVoucherSpecific(req, header);
	}
}
