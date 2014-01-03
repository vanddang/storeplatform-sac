package com.skplanet.storeplatform.sac.display.category.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppService;
import com.skplanet.storeplatform.sac.product.service.ProductCommonServiceImpl;

@Controller
@RequestMapping("/display/category")
public class CategoryController {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	private CategoryAppService categoryAppService;

	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchCategoryAppList(CategoryAppReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchCategoryAppList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryAppService.searchCategoryAppList(req);
	}
}
