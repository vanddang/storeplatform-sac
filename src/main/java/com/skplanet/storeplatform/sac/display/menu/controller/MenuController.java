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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.menu.service.CategoryService;
import com.skplanet.storeplatform.sac.display.menu.service.MenuListService;

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
	private MenuListService menuListService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * <pre>
	 * 테넌트 (전체) 메뉴 목록 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListRes searchMenuList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		return this.menuListService.searchMenuList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 테넌트 (세부) 메뉴 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListRes
	 */
	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListRes searchMenu(MenuReq requestVO, SacRequestHeader requestHeader) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenu start !!");

		return this.menuListService.searchMenuList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 대분류 카테고리(전시 메뉴) 조회 - Dummy.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryListRes
	 */
	@RequestMapping(value = "/category/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryListRes searchTopCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchTopCategoryList start !!");

		return this.categoryService.searchTopCategoryList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 세분류 카테고리(전시 메뉴) 조회 - Dummy.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryDetailRes
	 */
	@RequestMapping(value = "/category/subCategory/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryDetailRes searchDetailCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchDetailCategoryList start !!");

		return this.categoryService.searchDetailCategoryList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 대분류 카테고리(전시 메뉴) 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryListRes
	 */
	@RequestMapping(value = "/category/top/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryListRes searchTopMenuList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchTopMenuList start !!");

		return this.categoryService.searchTopCategoryList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 세분류 카테고리(전시 메뉴) 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryDetailRes
	 */
	@RequestMapping(value = "/category/sub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryDetailRes searchDetailMenuList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchSubCategoryList start !!");

		return this.categoryService.searchSubCategoryList(requestVO, requestHeader);
	}

}
