/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.category.controller;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEpisodeProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEpisodeProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.service.CategoryEpisodeProductService;
import com.skplanet.storeplatform.sac.display.category.vo.SearchEpisodeListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Updated on : 2015-07-30. Updated by : 양해엽, SK Planet.
 */
@Controller
@RequestMapping("/display/category")
public class CategoryEpisodeProductController {

	@Autowired
	private CategoryEpisodeProductService categoryEpisodeProductService;

	@RequestMapping(value = "/episode/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryEpisodeProductSacRes searchEpisodeProductList(SacRequestHeader header, @Validated CategoryEpisodeProductSacReq req) {

		String tenantId = header.getTenantHeader().getTenantId();
		String channelId = req.getChannelId();

		return categoryEpisodeProductService.searchEpisodeProductList(new SearchEpisodeListParam(tenantId, channelId));
	}
}
