/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.controller;


import com.skplanet.storeplatform.sac.client.display.vo.epub.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.epub.service.EpubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * EBook Controller
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
@Controller
@RequestMapping("/display/epub")
public class EpubController {

	private static final Logger logger = LoggerFactory.getLogger(EpubController.class);

	@Autowired
	private EpubService epubService;

    /**
     * * 채널 상품ID를 조건으로 하여 eBook/코믹 상품 상세 정보를 조회한다.
     * @param header
     * @param req
     * @return
     */
	@RequestMapping(value = "/channel/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public EpubChannelRes searchEpubChannel(SacRequestHeader header, @Validated @RequestBody EpubChannelReq req) {
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());

		return this.epubService.searchEpubChannel(req);

	}

	/**
	 * 채널 상품ID를 조건으로 하여 eBook/코믹 상품 상세 정보를 조회한다.
     * @param header
	 * @param req
	 * @return EpubDetailRes
	 */
	@RequestMapping(value = "/series/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public EpubSeriesRes searchEpubSeries(SacRequestHeader header, @Validated @RequestBody EpubSeriesReq req) {
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());
		return this.epubService.searchEpubSeries(req);

	}

}
