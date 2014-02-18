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

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryAppService;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryEpubService;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryVodService;

/**
 * Feature 카테고리 컨트롤러.
 * 
 * Updated on : 2014-01-24 Updated by : , GTSOFT.
 */
@Controller
@RequestMapping("/display/feature/category")
public class FeatureCategoryController {
	private transient Logger logger = LoggerFactory.getLogger(FeatureCategoryController.class);

	@Autowired
	private FeatureCategoryVodService featureCategoryVodService;
	@Autowired
	private FeatureCategoryAppService categoryAppService;
	@Autowired
	private FeatureCategoryEpubService categoryEpubService;

	/**
	 * <pre>
	 * Feature VOD 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return FeatureCategoryVodRes
	 */
	@RequestMapping(value = "/vod/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FeatureCategoryVodSacRes searchVodList(FeatureCategoryVodSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("Input Parameters {}", header.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.featureCategoryVodService.searchVodList(req, header);
	}

	/**
	 * <pre>
	 * Feature App 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return FeatureCategoryAppRes
	 */
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FeatureCategoryAppSacRes searchAppList(FeatureCategoryAppSacReq requestVO, SacRequestHeader header) {

		FeatureCategoryAppSacRes responseVO;
		responseVO = this.categoryAppService.searchMenuAppList(requestVO, header);
		return responseVO;
	}

	/**
	 * <pre>
	 * Feature Epub 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return FeatureCategoryEpubRes
	 */
	@RequestMapping(value = "/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public FeatureCategoryEpubSacRes searchEpubList(FeatureCategoryEpubSacReq requestVO, SacRequestHeader header) {

		FeatureCategoryEpubSacRes responseVO;
		responseVO = this.categoryEpubService.searchEpubList(requestVO, header);
		return responseVO;
	}
}
