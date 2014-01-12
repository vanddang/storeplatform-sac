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

import java.util.logging.Logger;

import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailRes;
import com.skplanet.storeplatform.sac.display.epub.service.EpubService;

/**
 * VOD Controller
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
	 * 채널 상품ID를 조건으로 하여 eBook/코믹 상품 상세 정보를 조회한다.
	 * @param req
	 * @return EpubDetailRes
	 */
	@RequestMapping(value = "/channel/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public EpubDetailRes searchEpub(EpubDetailReq req) {
		return this.epubService.searchEpub(req);

	}

	/**
	 * 채널 상품ID를 조건으로 하여 eBook/코믹 상품 상세 정보를 조회한다.
	 * @param req
	 * @return EpubDetailRes
	 */
	@RequestMapping(value = "/series/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public EpubDetailRes searchEpubSeries(EpubDetailReq req) {
		return this.epubService.searchEpubSeries(req);

	}

}
