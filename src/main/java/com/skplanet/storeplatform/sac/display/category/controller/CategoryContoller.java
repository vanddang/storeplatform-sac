package com.skplanet.storeplatform.sac.display.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppService;

@Controller
@RequestMapping("/display/category")
public class CategoryContoller {
	@Autowired
	private CategoryAppService categoryAppService;

	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchCategoryAppList(CategoryAppReq req) {
		return this.categoryAppService.searchCategoryAppList(req);
	}
}
