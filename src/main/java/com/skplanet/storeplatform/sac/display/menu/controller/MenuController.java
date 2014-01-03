package com.skplanet.storeplatform.sac.display.menu.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuRequest;
import com.skplanet.storeplatform.sac.display.menu.service.CategoryService;
import com.skplanet.storeplatform.sac.display.menu.service.MenuListService;

@Controller
@RequestMapping("/display/menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuListService menuListService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListResponse searchMenuList(MenuRequest requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		return this.menuListService.searchMenuList(requestVO);
	}

	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListResponse searchMenu(MenuRequest requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		this.logger.debug("MenuController.searchMenu start !!");

		return this.menuListService.searchMenuList(requestVO);
	}

	@RequestMapping(value = "/category/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryListResponse searchTopCategoryList(MenuRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchTopCategoryList start !!");

		return this.categoryService.searchTopCategoryList(requestVO);
	}

	@RequestMapping(value = "/category/subCategory/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryDetailListResponse searchDetailCategoryList(MenuRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchDetailCategoryList start !!");

		return this.categoryService.searchDetailCategoryList(requestVO);
	}
}
