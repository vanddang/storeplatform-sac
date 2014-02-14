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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuDetail;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.menu.vo.Menu;

/**
 * MenuList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenuList(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public MenuListSacRes searchMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());

		MenuListSacRes menuListSacRes = new MenuListSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<MenuDetail> menuDetailList = new ArrayList<MenuDetail>();
		MenuDetail menuDetail;

		List<Menu> resultList = this.commonDAO.queryForList("Menu.selectMenuList", requestVO, Menu.class);

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
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenu(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public MenuDetailSacRes searchMenuDetail(MenuSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setSystemId(requestHeader.getTenantHeader().getSystemId());

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", requestVO.getMenuId());
		}

		MenuDetailSacRes menuDetailSacRes = new MenuDetailSacRes();
		CommonResponse commonResponse = new CommonResponse();
		MenuDetail menuDetail;

		Menu menu = this.commonDAO.queryForObject("Menu.selectMenuDetail", requestVO, Menu.class);

		if (menu != null) {

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

			menuDetailSacRes.setMenuDetail(menuDetail);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}
		menuDetailSacRes.setCommonResponse(commonResponse);

		return menuDetailSacRes;
	}

}
