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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuDetail;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.menu.vo.Menu;

/**
 * MenuServiceImpl 클래스
 * <pre>
 * Created on 2014. 02. 12. by 유시혁.
 * Updated on 2014. 07. 11. by 서대영, SK 플래닛 : 메서드 추가 (searchBestMenuList)
 * </pre>
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MenuDataService dataSvc;

	@Override
	public MenuListSacRes searchBestMenuList(String menuCategoryCd, SacRequestHeader requestHeader) {
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setSystemId(DisplayConstants.DP_SHOP_CLIENT_3_0_SYSTEM_ID); // SYSTEM ID 고정(임시)

		List<Menu> resultList = this.dataSvc.selectBestMenuList(tenant, menuCategoryCd);

		MenuListSacRes menuListSacRes = new MenuListSacRes();
		CommonResponse commonResponse = new CommonResponse();

		if (!resultList.isEmpty()) {
			List<MenuDetail> menuDetailList = new ArrayList<MenuDetail>();
			for (Menu menu : resultList) {
				MenuDetail menuDetail = this.convert(menu);
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
	@Override
	public MenuListSacRes searchMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader) {
		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());
		requestVO.setSystemId(DisplayConstants.DP_SHOP_CLIENT_3_0_SYSTEM_ID); // SYSTEM ID 고정(임시)

		List<Menu> resultList = this.commonDAO.queryForList("Menu.selectMenuList", requestVO, Menu.class);

		MenuListSacRes menuListSacRes = new MenuListSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<MenuDetail> menuDetailList = new ArrayList<MenuDetail>();

		if (!resultList.isEmpty()) {
			for (Menu menu : resultList) {
				MenuDetail menuDetail = this.convert(menu);
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

	/**
	 * <pre>
	 * 테넌트 메뉴 상세 조회.
	 * </pre>
	 *
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	@Override
	public MenuDetailSacRes searchMenuDetail(MenuSacReq requestVO, SacRequestHeader requestHeader) {
		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());
		requestVO.setSystemId(DisplayConstants.DP_SHOP_CLIENT_3_0_SYSTEM_ID); // SYSTEM ID 고정(임시)

		Menu menu = this.commonDAO.queryForObject("Menu.selectMenuDetail", requestVO, Menu.class);

		MenuDetailSacRes menuDetailSacRes = new MenuDetailSacRes();
		CommonResponse commonResponse = new CommonResponse();

		if (menu != null) {
			MenuDetail menuDetail = this.convert(menu);
			menuDetailSacRes.setMenuDetail(menuDetail);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}
		menuDetailSacRes.setCommonResponse(commonResponse);

		return menuDetailSacRes;
	}

	private MenuDetail convert(Menu menu) {
		MenuDetail menuDetail = new MenuDetail();
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
		menuDetail.setLnbFilePath(menu.getLnbFilePath());
		menuDetail.setLnbFileName(menu.getLnbFileNm());
		menuDetail.setLnbFileSize(menu.getLnbFileSize());
		menuDetail.setPreCategoryInfo(menu.getPreCategoryInfo());
		menuDetail.setCategoryMenuYn(menu.getCategoryMenuYn());
		return menuDetail;
	}

}
