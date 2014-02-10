/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.vod.service.VodService;

/**
 * VOD Controller
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
@Controller
@RequestMapping("/display/vod")
public class VodController {
	private static final Logger logger = LoggerFactory.getLogger(VodController.class);

	@Autowired
	private VodService vodService;

	@RequestMapping(value = "/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public VodDetailRes vodDetail(SacRequestHeader header, @Validated VodDetailReq req) {
		//logger.debug("header={}, req={}", new String[]{ header.toString(), req.toString() });
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());

        logger.debug("req={}", req);
        //FXIME: Test
        //req.setChannelId("H906023417");
		return this.vodService.searchVod(req);

	}

}
