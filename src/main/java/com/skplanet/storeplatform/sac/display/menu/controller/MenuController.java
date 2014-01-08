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

import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuReq;
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
	public MenuListRes searchMenuList(MenuReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		return this.menuListService.searchMenuList(requestVO);
	}

	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListRes searchMenu(MenuReq requestVO) throws JsonGenerationException, JsonMappingException, IOException,
			Exception {

		this.logger.debug("MenuController.searchMenu start !!");

		return this.menuListService.searchMenuList(requestVO);
	}

	@RequestMapping(value = "/category/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryListRes searchTopCategoryList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchTopCategoryList start !!");

		return this.categoryService.searchTopCategoryList(requestVO);
	}

	@RequestMapping(value = "/category/subCategory/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryDetailRes searchDetailCategoryList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchDetailCategoryList start !!");

		return this.categoryService.searchDetailCategoryList(requestVO);
	}

	@RequestMapping(value = "/category/top/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryListRes searchTopMenuList(MenuReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		this.logger.debug("MenuController.searchTopMenuList start !!");

		return this.categoryService.searchTopCategoryList(requestVO);
	}

	@RequestMapping(value = "/category/sub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryDetailRes searchDetailMenuList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchSubCategoryList start !!");

		return this.categoryService.searchSubCategoryList(requestVO);
	}

}
