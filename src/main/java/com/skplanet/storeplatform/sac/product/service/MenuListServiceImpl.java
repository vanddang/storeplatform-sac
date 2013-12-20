/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuDetail;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuDetailResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuListResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuRequestVO;
import com.skplanet.storeplatform.sac.product.vo.MenuDetailMapperVO;

/**
 * MenuList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class MenuListServiceImpl implements MenuListService {

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
	public MenuListResponseVO searchMenuList(String tenantId, String systemId, String menuId)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 0;
		MenuListResponseVO responseVO = null;
		MenuRequestVO requestVO = new MenuRequestVO();
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		if (null != menuId && !"".equals(menuId)) {
			requestVO.setMenuId(menuId);
		}
		requestVO.setSystemId(systemId);
		requestVO.setTenantId(tenantId);

		List<MenuDetailMapperVO> resultList = this.commonDAO.queryForList("Menu.selectMenuList", requestVO,
				MenuDetailMapperVO.class);
		if (resultList != null) {
			MenuDetailMapperVO mapperVO = new MenuDetailMapperVO();

			// Response VO를 만들기위한 생성자
			MenuDetail menu = null;
			List<MenuDetail> listVO = new ArrayList<MenuDetail>();

			for (int i = 0; i < resultList.size(); i++) {
				mapperVO = resultList.get(i);

				menu = new MenuDetail();

				totalCount = mapperVO.getTotalCount();

				menu.setBodyFileName(mapperVO.getBodyFileName());
				menu.setBodyFilePath(mapperVO.getBodyFilePath());
				menu.setBodyFileSize(mapperVO.getBodyFileSize());
				menu.setExpoOrd(mapperVO.getExpoOrd());
				menu.setInfrMenuYn(mapperVO.getInfrMenuYn());
				menu.setLnbFileName(mapperVO.getLnbFileName());
				menu.setLnbFilePath(mapperVO.getLnbFilePath());
				menu.setLnbFileSize(mapperVO.getLnbFileSize());
				menu.setMainOffFileName(mapperVO.getMainOffFileName());
				menu.setMainOffFilePath(mapperVO.getMainOffFilePath());
				menu.setMainOnFileName(mapperVO.getMainOnFileName());
				menu.setMenuDepth(mapperVO.getMenuDepth());
				menu.setMenuDesc(mapperVO.getMenuDesc());
				menu.setMenuEngName(mapperVO.getMenuEngName());
				menu.setMenuId(mapperVO.getMenuId());
				menu.setMenuName(mapperVO.getMenuName());
				menu.setRankFileName(mapperVO.getRankFileName());
				menu.setRankFilePath(mapperVO.getRankFilePath());
				menu.setSearchFileName(mapperVO.getSearchFileName());
				menu.setSearchFilePath(mapperVO.getSearchFilePath());
				menu.setSystemId(mapperVO.getSystemId());
				menu.setTargetUrl(mapperVO.getTargetUrl());
				menu.setTenantId(mapperVO.getTenantId());
				menu.setUpMenuId(mapperVO.getUpMenuId());
				menu.setUseYn(mapperVO.getUseYn());

				listVO.add(menu);
			}

			responseVO = new MenuListResponseVO();
			commonResponse = new CommonResponse();
			responseVO.setMenuList(listVO);
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
			String json = objectMapper.writeValueAsString(responseVO);

			this.log.debug("test json : {}", json);
			// System.out.println(json);

		}
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenu(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public MenuDetailResponseVO searchMenu(String tenantId, String systemId, String menuId)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 0;
		MenuDetailResponseVO responseVO = null;
		MenuRequestVO requestVO = new MenuRequestVO();
		CommonResponse commonResponse = null;

		if (null == menuId || "".equals(menuId)) {
			throw new Exception("menuId 는 필수 파라메터 입니다.");
		}
		if (null == tenantId || "".equals(tenantId)) {
			throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		requestVO.setMenuId(menuId);
		requestVO.setSystemId(systemId);
		requestVO.setTenantId(tenantId);

		MenuDetailMapperVO mapperVO = this.commonDAO.queryForObject("Menu.selectMenu", requestVO,
				MenuDetailMapperVO.class);

		MenuDetail menu = null;
		if (null != mapperVO) {

			menu = new MenuDetail();

			totalCount = mapperVO.getTotalCount();

			menu.setBodyFileName(mapperVO.getBodyFileName());
			menu.setBodyFilePath(mapperVO.getBodyFilePath());
			menu.setBodyFileSize(mapperVO.getBodyFileSize());
			menu.setExpoOrd(mapperVO.getExpoOrd());
			menu.setInfrMenuYn(mapperVO.getInfrMenuYn());
			menu.setLnbFileName(mapperVO.getLnbFileName());
			menu.setLnbFilePath(mapperVO.getLnbFilePath());
			menu.setLnbFileSize(mapperVO.getLnbFileSize());
			menu.setMainOffFileName(mapperVO.getMainOffFileName());
			menu.setMainOffFilePath(mapperVO.getMainOffFilePath());
			menu.setMainOnFileName(mapperVO.getMainOnFileName());
			menu.setMenuDepth(mapperVO.getMenuDepth());
			menu.setMenuDesc(mapperVO.getMenuDesc());
			menu.setMenuEngName(mapperVO.getMenuEngName());
			menu.setMenuId(mapperVO.getMenuId());
			menu.setMenuName(mapperVO.getMenuName());
			menu.setRankFileName(mapperVO.getRankFileName());
			menu.setRankFilePath(mapperVO.getRankFilePath());
			menu.setSearchFileName(mapperVO.getSearchFileName());
			menu.setSearchFilePath(mapperVO.getSearchFilePath());
			menu.setSystemId(mapperVO.getSystemId());
			menu.setTargetUrl(mapperVO.getTargetUrl());
			menu.setTenantId(mapperVO.getTenantId());
			menu.setUpMenuId(mapperVO.getUpMenuId());
			menu.setUseYn(mapperVO.getUseYn());
		}

		responseVO = new MenuDetailResponseVO();
		commonResponse = new CommonResponse();
		responseVO.setMenu(menu);
		commonResponse.setTotalCount(totalCount);
		responseVO.setCommonRes(commonResponse);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		String json = objectMapper.writeValueAsString(responseVO);

		this.log.debug("test json : {}", json);

		return responseVO;
	}

}
