package com.skplanet.storeplatform.sac.display.feature.appCodi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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
	 * App Codi 조회.
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
	public AppCodiListSacRes searchAppCodiList(@RequestBody AppCodiSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		this.logger.debug("AppCodiController.searchAppCodiList start !!");
		this.logger.debug("request {}", requestVO);
		if ("dummy".equals(requestVO.getFilteredBy()))
			return this.appCodiService.searchDummyAppCodiList(requestVO, requestHeader);
		else
			return this.appCodiService.searchAppCodiList(requestVO, requestHeader);
	}
}
