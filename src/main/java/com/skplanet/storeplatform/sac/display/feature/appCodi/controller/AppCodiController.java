package com.skplanet.storeplatform.sac.display.feature.appCodi.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiReq;
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
	 * @param AppCodiReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppCodiListRes
	 */
	@RequestMapping(value = "/appCodi/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public AppCodiListRes searchAppCodiList(@RequestBody AppCodiReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("AppCodiController.searchAppCodiList start !!");

		return this.appCodiService.searchAppCodiList(requestVO, requestHeader);
	}
}
