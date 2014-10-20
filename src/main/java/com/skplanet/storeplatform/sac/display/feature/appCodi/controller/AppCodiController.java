package com.skplanet.storeplatform.sac.display.feature.appCodi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.appCodi.service.AppCodiService;

/**
 * 
 * Calss 설명(AppCodi 조회 컨트롤러)
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Controller
@RequestMapping("/display/feature")
public class AppCodiController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppCodiService appCodiService;

	/**
	 * <pre>
	 * [I03000057] 2.8.1. App Codi
	 * </pre>
	 * 
	 * @param AppCodiSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppCodiListRes
	 */
	@RequestMapping(value = "/appCodi/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public AppCodiListSacRes searchAppCodiList(@RequestBody @Validated AppCodiSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("AppCodiController.searchAppCodiList start !!");
		this.logger.debug("request {}", requestVO);
		return this.appCodiService.searchAppCodiList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * [I03000128] 2.8.1. App Codi
	 * </pre>
	 * 
	 * @param AppCodiSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppCodiListRes
	 */
	@RequestMapping(value = "/appCodi/list/v2", method = RequestMethod.POST)
	@ResponseBody
	public AppCodiListSacRes searchAppCodiListV2(@RequestBody @Validated AppCodiSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("AppCodiController.searchAppCodiListV2 start !!");
		this.logger.debug("request {}", requestVO);
		return this.appCodiService.searchAppCodiListV2(requestVO, requestHeader);
	}
}
