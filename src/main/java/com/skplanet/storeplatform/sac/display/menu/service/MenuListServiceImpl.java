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
import java.util.Iterator;
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
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetail;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.display.menu.vo.MenuDetailDTO;

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
	public MenuListRes searchMenuList(MenuReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";
		String menuId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();

		MenuListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		List<MenuDetailDTO> resultList = this.commonDAO.queryForList("Menu.selectMenuList", requestVO,
				MenuDetailDTO.class);
		if (resultList != null) {

			// Response VO를 만들기위한 생성자
			MenuDetail menu = null;
			List<MenuDetail> listVO = new ArrayList<MenuDetail>();

			Iterator<MenuDetailDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuDetailDTO mapperVO = iterator.next();

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
				menu.setMenuEngName(mapperVO.getMenuEngNm());
				menu.setMenuId(mapperVO.getMenuId());
				menu.setMenuName(mapperVO.getMenuNm());
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

			responseVO = new MenuListRes();
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
	public MenuDetailRes searchMenu(MenuReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";
		String menuId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();

		MenuDetailRes responseVO = null;
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

		MenuDetailDTO mapperVO = this.commonDAO.queryForObject("Menu.selectMenu", requestVO, MenuDetailDTO.class);

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
			menu.setMenuEngName(mapperVO.getMenuEngNm());
			menu.setMenuId(mapperVO.getMenuId());
			menu.setMenuName(mapperVO.getMenuNm());
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

		responseVO = new MenuDetailRes();
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
