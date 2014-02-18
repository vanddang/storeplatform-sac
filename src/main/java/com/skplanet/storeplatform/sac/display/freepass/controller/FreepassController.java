/*
 * Copyright (c) 2013 SK planet.
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.SeriespassListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.freepass.service.FreepassService;

/**
 * 정액제 Controller
 * 
 * Updated on : 2014. 2. 07. Updated by : 서영배, GTSOFT.
 */
@Controller
@RequestMapping("/display/category/freepass")
public class FreepassController {
	private transient Logger logger = LoggerFactory.getLogger(FreepassController.class);

	@Autowired
	private FreepassService freepassService;

	/**
	 * <pre>
	 * 자유 이용권 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FreepassListRes searchFreepassList(FreepassListReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchFreepassList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.freepassService.searchFreepassList(req, header);
	}

	/**
	 * <pre>
	 * 자유 이용권 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/detail/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FreepassDetailRes searchFreepassDetail(FreepassListReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchFreepassDetail Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.freepassService.searchFreepassDetail(req, header);
	}

	/**
	 * <pre>
	 * 자유 이용권 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/series/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SeriespassListRes searchSeriespassList(FreepassListReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSeriespassList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.freepassService.searchSeriesPassList(req, header);
	}

	/**
	 * <pre>
	 * 특정 상품에 적용할 자유 이용권 조회– GET.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FreepassListRes searchFreepassListByChannel(FreepassListReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchFreepassListByChannel Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.freepassService.searchFreepassListByChannel(req, header);
	}
}
