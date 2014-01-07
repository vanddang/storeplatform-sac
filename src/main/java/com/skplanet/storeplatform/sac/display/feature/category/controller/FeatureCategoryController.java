/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodRes;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryVodService;
import com.skplanet.storeplatform.sac.product.service.ProductCommonServiceImpl;

@Controller
@RequestMapping("/display/feature/category")
public class FeatureCategoryController {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	private FeatureCategoryVodService featureCategoryVodService;

	/**
	 * <pre>
	 * Feature VOD 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param FeatureCategoryVodReq
	 * @return FeatureCategoryVodRes
	 */
	@RequestMapping(value = "/vod/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FeatureCategoryVodRes searchVodList(FeatureCategoryVodReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.featureCategoryVodService.searchVodList(req);
	}
}
