package com.skplanet.storeplatform.sac.display.feature.category.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.*;
import  com.skplanet.storeplatform.sac.display.feature.category.service.*;

@Controller
@RequestMapping("/display/feature/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchAppList(CategoryAppReq requestVO) {
		//System.out.println("test");
		//logger.info(requestVO.toString());

		CategoryAppService service = new CategoryAppServiceImpl();
		CategoryAppRes responseVO;
		responseVO = service.searchAppList(requestVO);
		return responseVO;
	}
	
	@RequestMapping(value = "/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryEpubRes searchEpubList(CategoryEpubReq requestVO) {
		
		//logger.info(requestVO.toString());

		CategoryEpubService service = new CategoryEpubServiceImpl();
		CategoryEpubRes responseVO;
		responseVO = service.searchEpubList(requestVO);
		return responseVO;
	}
}
