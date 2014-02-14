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

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.menu.service.CategoryService;
import com.skplanet.storeplatform.sac.display.menu.service.MenuService;

/**
 * 
 * Calss 설명(메뉴 조회, 대분류/세분류 카테고리 조회 컨트롤러)
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
@Controller
@RequestMapping("/display/menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuService menuService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * <pre>
	 * 테넌트 메뉴 조회.
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		return this.menuService.searchMenuList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 테넌트 메뉴 상세 조회.
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuDetailSacRes searchMenuDetail(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenuDetail start !!");

		return this.menuService.searchMenuDetail(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 대분류 전시 카테고리 조회.
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/category/top/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchTopMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchTopMenuList start !!");

		return this.categoryService.searchTopCategoryList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 세부분류 전시 카테고리 조회.
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/category/sub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchDetailMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchSubCategoryList start !!");

		return this.categoryService.searchSubCategoryList(requestVO, requestHeader);
	}

}
