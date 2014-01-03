package com.skplanet.storeplatform.sac.display.feature.best.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.feature.best.BestAppReq;
import com.skplanet.storeplatform.sac.client.display.feature.best.BestAppRes;
import com.skplanet.storeplatform.sac.display.feature.best.service.BestAppService;

/**
 * 
 * Calss 설명(BEST 관련)
 * 
 * Updated on : 2014. 1. 3. Updated by : 이석희, 인크로스
 */
@Controller
public class BestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BestController.class);

	@Autowired
	private BestAppService bestAppService;

	@RequestMapping(value = "/display/feature/best/bestAppList/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestAppRes bestAppList(BestAppReq bestAppReq) {
		return this.bestAppService.searchBestAppList(bestAppReq);
	}

}
