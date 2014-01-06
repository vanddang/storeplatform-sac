package com.skplanet.storeplatform.sac.display.feature.category.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryAppRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryEpubReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryEpubRes;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryAppService;
import com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryEpubService;

@Controller
@RequestMapping("/display/feature/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private FeatureCategoryAppService categoryAppService;
	@Autowired
	private FeatureCategoryEpubService categoryEpubService;
	
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchAppList(CategoryAppReq requestVO) {

		CategoryAppRes responseVO;
		responseVO = categoryAppService.searchAppList(requestVO);
		return responseVO;
	}
	
	@RequestMapping(value = "/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryEpubRes searchEpubList(CategoryEpubReq requestVO) {

		CategoryEpubRes responseVO;
		responseVO = categoryEpubService.searchEpubList(requestVO);
		return responseVO;
	}
}
