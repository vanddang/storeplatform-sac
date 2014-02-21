/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuDetail;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.menu.vo.Menu;

/**
 * Category Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 유시혁, SK 플래닛.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuSacReq
	 * requestVO)
	 */
	@Override
	public MenuListSacRes searchTopCategoryList(MenuSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());

		MenuListSacRes menuListSacRes = new MenuListSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<MenuDetail> menuDetailList = new ArrayList<MenuDetail>();
		MenuDetail menuDetail;

		List<Menu> resultList = this.commonDAO.queryForList("Menu.selectTopCategoryList", requestVO, Menu.class);

		if (!resultList.isEmpty()) {

			for (Menu menu : resultList) {
				menuDetail = new MenuDetail();
				menuDetail.setBodyFileName(menu.getBodyFileNm());
				menuDetail.setBodyFilePath(menu.getBodyFilePath());
				menuDetail.setBodyFileSize(menu.getBodyFileSize());
				menuDetail.setExposureOrder(menu.getExpoOrd());
				menuDetail.setInfrMenuYn(menu.getInfrMenuYn());
				menuDetail.setMainOffFileName(menu.getMainOffFileNm());
				menuDetail.setMainOffFilePath(menu.getMainOffFilePath());
				menuDetail.setMainOnFileName(menu.getMainOnFileNm());
				menuDetail.setMainOnFilePath(menu.getMainOnFilePath());
				menuDetail.setMenuDepth(menu.getMenuDepth());
				menuDetail.setMenuDescription(menu.getMenuDesc());
				menuDetail.setMenuEnglishName(menu.getMenuEngNm());
				menuDetail.setMenuId(menu.getMenuId());
				menuDetail.setMenuName(menu.getMenuNm());
				menuDetail.setRankFileName(menu.getRankFileNm());
				menuDetail.setRankFilePath(menu.getRankFilePath());
				menuDetail.setSearchFileName(menu.getSearchFileNm());
				menuDetail.setSearchFilePath(menu.getSearchFilePath());
				menuDetail.setSystemId(menu.getSystemId());
				menuDetail.setTargetUrl(menu.getTargetUrl());
				menuDetail.setTenantId(menu.getTenantId());
				menuDetail.setUpMenuId(menu.getUpMenuId());
				menuDetail.setUseYn(menu.getUseYn());
				menuDetail.setMenuIdType(menu.getMenuIdType());

				menuDetailList.add(menuDetail);
			}

			menuListSacRes.setMenuDetailList(menuDetailList);
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());

		} else {
			commonResponse.setTotalCount(0);
		}
		menuListSacRes.setCommonResponse(commonResponse);
		return menuListSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchSubCategoryList(MenuSacReq
	 * requestVO)
	 */
	@Override
	public MenuListSacRes searchSubCategoryList(MenuSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());

		// 필수 파라미터 체크
		this.log.debug("필수 파라미터 체크");
		if (StringUtils.isEmpty(requestVO.getTopMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "topMenuId", requestVO.getTopMenuId());
		}

		MenuListSacRes menuListSacRes = new MenuListSacRes();
		CommonResponse commonResponse = new CommonResponse();
		// List<Menu> menuList = new ArrayList<Menu>();
		// Menu menu1depth;
		List<MenuDetail> menuDetail2DepthList = new ArrayList<MenuDetail>();
		List<Menu> resultList = null;

		// 카테고리 조회
		this.log.debug("카테고리 조회");
		if (requestVO.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)
				|| requestVO.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)
				|| requestVO.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)
				|| requestVO.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) { // 멀티미디어 카테고리 조회
			this.log.debug("멀티미디어 카테고리 조회");
			resultList = this.commonDAO.queryForList("MenuCategory.selectMultiSubCategoryList", requestVO, Menu.class);
		} else if (requestVO.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) { // 뮤직 카테고리 조회
			this.log.debug("뮤직 카테고리 조회");
			resultList = this.commonDAO.queryForList("MenuCategory.selectMusicSubCategoryList", requestVO, Menu.class);
		} else if (requestVO.getTopMenuId().equals(DisplayConstants.DP_SHOPPING_TOP_MENU_ID)) { // 쇼핑 카테고리 조회
			this.log.debug("쇼핑 카테고리 조회");
			resultList = this.commonDAO.queryForList("MenuCategory.selectShoppingSubCategoryList", requestVO,
					Menu.class);
		} else { // 앱 카테고리 조회
			this.log.debug("앱 카테고리 조회");
			resultList = this.commonDAO.queryForList("MenuCategory.selectAppSubCategoryList", requestVO, Menu.class);
		}

		if (!resultList.isEmpty()) {

			for (Menu menu : resultList) {

				// 2Depth
				if (menu.getMenuDepth() == 2) {
					MenuDetail menuDetail2Depth = new MenuDetail();
					menuDetail2Depth.setMenuProductCount(menu.getMenuProdCnt());
					menuDetail2Depth.setBodyFileName(menu.getBodyFileNm());
					menuDetail2Depth.setBodyFilePath(menu.getBodyFilePath());
					menuDetail2Depth.setBodyFileSize(menu.getBodyFileSize());
					menuDetail2Depth.setExposureOrder(menu.getExpoOrd());
					menuDetail2Depth.setInfrMenuYn(menu.getInfrMenuYn());
					menuDetail2Depth.setMainOffFileName(menu.getMainOffFileNm());
					menuDetail2Depth.setMainOffFilePath(menu.getMainOffFilePath());
					menuDetail2Depth.setMainOnFileName(menu.getMainOnFileNm());
					menuDetail2Depth.setMainOnFilePath(menu.getMainOnFilePath());
					menuDetail2Depth.setMenuDepth(menu.getMenuDepth());
					menuDetail2Depth.setMenuDescription(menu.getMenuDesc());
					menuDetail2Depth.setMenuEnglishName(menu.getMenuEngNm());
					menuDetail2Depth.setMenuId(menu.getMenuId());
					menuDetail2Depth.setMenuName(menu.getMenuNm());
					menuDetail2Depth.setRankFileName(menu.getRankFileNm());
					menuDetail2Depth.setRankFilePath(menu.getRankFilePath());
					menuDetail2Depth.setSearchFileName(menu.getSearchFileNm());
					menuDetail2Depth.setSearchFilePath(menu.getSearchFilePath());
					menuDetail2Depth.setSystemId(menu.getSystemId());
					menuDetail2Depth.setTargetUrl(menu.getTargetUrl());
					menuDetail2Depth.setTenantId(menu.getTenantId());
					menuDetail2Depth.setUpMenuId(menu.getUpMenuId());
					menuDetail2Depth.setUseYn(menu.getUseYn());
					menuDetail2Depth.setMenuIdType(menu.getMenuIdType());

					List<MenuDetail> menuDetail3DepthList = new ArrayList<MenuDetail>();

					for (Menu subMenu : resultList) {
						// 3Depth
						if (subMenu.getMenuDepth() == 3 && menu.getMenuId().equals(subMenu.getUpMenuId())) {
							MenuDetail menuDetail3Depth = new MenuDetail();
							menuDetail3Depth.setMenuProductCount(menu.getMenuProdCnt());
							menuDetail3Depth.setBodyFileName(subMenu.getBodyFileNm());
							menuDetail3Depth.setBodyFilePath(subMenu.getBodyFilePath());
							menuDetail3Depth.setBodyFileSize(subMenu.getBodyFileSize());
							menuDetail3Depth.setExposureOrder(subMenu.getExpoOrd());
							menuDetail3Depth.setInfrMenuYn(subMenu.getInfrMenuYn());
							menuDetail3Depth.setMainOffFileName(subMenu.getMainOffFileNm());
							menuDetail3Depth.setMainOffFilePath(subMenu.getMainOffFilePath());
							menuDetail3Depth.setMainOnFileName(subMenu.getMainOnFileNm());
							menuDetail3Depth.setMainOnFilePath(subMenu.getMainOnFilePath());
							menuDetail3Depth.setMenuDepth(subMenu.getMenuDepth());
							menuDetail3Depth.setMenuDescription(subMenu.getMenuDesc());
							menuDetail3Depth.setMenuEnglishName(subMenu.getMenuEngNm());
							menuDetail3Depth.setMenuId(subMenu.getMenuId());
							menuDetail3Depth.setMenuName(subMenu.getMenuNm());
							menuDetail3Depth.setRankFileName(subMenu.getRankFileNm());
							menuDetail3Depth.setRankFilePath(subMenu.getRankFilePath());
							menuDetail3Depth.setSearchFileName(subMenu.getSearchFileNm());
							menuDetail3Depth.setSearchFilePath(subMenu.getSearchFilePath());
							menuDetail3Depth.setSystemId(subMenu.getSystemId());
							menuDetail3Depth.setTargetUrl(subMenu.getTargetUrl());
							menuDetail3Depth.setTenantId(subMenu.getTenantId());
							menuDetail3Depth.setUpMenuId(subMenu.getUpMenuId());
							menuDetail3Depth.setUseYn(subMenu.getUseYn());
							menuDetail3Depth.setMenuIdType(subMenu.getMenuIdType());

							menuDetail3DepthList.add(menuDetail3Depth);
						}
					}
					if (!menuDetail3DepthList.isEmpty()) {
						menuDetail2Depth.setSubMenuDetailList(menuDetail3DepthList);
					}
					menuDetail2DepthList.add(menuDetail2Depth);
				}
			}

			menuListSacRes.setMenuDetailList(menuDetail2DepthList);
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());

		} else {
			commonResponse.setTotalCount(0);
		}
		menuListSacRes.setCommonResponse(commonResponse);
		return menuListSacRes;
	}
}
