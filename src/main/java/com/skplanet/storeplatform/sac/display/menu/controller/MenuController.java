package com.skplanet.storeplatform.sac.display.menu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq.SearchMenuDetail;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq.SearchMenuList;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq.SearchSubMenuList;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq.SearchTopMenuList;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.menu.service.CategoryService;
import com.skplanet.storeplatform.sac.display.menu.service.MenuService;

/**
 * 메뉴 조회 Controller.
 * <pre>
 * Created on 2013. 12. 20. by 유시혁.
 * Updated on 2014. 07. 11. by 서대영, SK 플래닛 : API I03000125 추가 (2.11.5. eBook Best 카테고리 조회)
 * </pre>
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
	 * I03000125 - 2.11.5. Best 카테고리 조회
	 */
	@RequestMapping(value = "/best/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchBestMenuList(@RequestParam("menuCategoryCd") String menuCategoryCd, SacRequestHeader requestHeader) {
		this.logger.debug("MenuController.searchBestMenuList start !!");
		return this.menuService.searchBestMenuList(menuCategoryCd, requestHeader);
	}

	/**
	 * <pre>
	 * 테넌트 메뉴 조회.
	 * </pre>
	 *
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchMenuList(@Validated(SearchMenuList.class) MenuSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("MenuController.searchMenuList start !!");

		return this.menuService.searchMenuList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 테넌트 메뉴 상세 조회.
	 * </pre>
	 *
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuDetailSacRes
	 */
	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuDetailSacRes searchMenuDetail(@Validated(SearchMenuDetail.class) MenuSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("MenuController.searchMenuDetail start !!");

		return this.menuService.searchMenuDetail(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 대분류 전시 카테고리 조회.
	 * </pre>
	 *
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/category/top/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchTopMenuList(@Validated(SearchTopMenuList.class) MenuSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("MenuController.searchTopMenuList start !!");

		return this.categoryService.searchTopCategoryList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 세부분류 전시 카테고리 조회.
	 * </pre>
	 *
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	@RequestMapping(value = "/category/sub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListSacRes searchSubMenuList(@Validated(SearchSubMenuList.class) MenuSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("MenuController.searchSubCategoryList start !!");

		return this.categoryService.searchSubCategoryList(requestVO, requestHeader);
	}

}
