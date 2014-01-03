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
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetail;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuRequest;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.display.menu.vo.MenuDetailDTO;

/**
 * Category Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
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
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenuList(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public CategoryListResponse searchTopCategoryList(MenuRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";
		String menuId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();

		CategoryListResponse responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			throw new Exception("systemId 는 필수 파라메터 입니다.");
		}
		List<MenuDetailDTO> resultList = this.commonDAO.queryForList("category.getTopCategory", requestVO,
				MenuDetailDTO.class);
		if (resultList != null) {

			// Response VO를 만들기위한 생성자
			Menu category = null;
			Source source = null;
			List<Menu> listVO = new ArrayList<Menu>();

			Iterator<MenuDetailDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuDetailDTO mapperVO = iterator.next();

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount();

				source.setSize(mapperVO.getBodyFileSize());
				// category.setMenuEngName(mapperVO.getMenuEngName());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());
				/*
				 * category.setExpoOrd(mapperVO.getExpoOrd()); category.setInfrMenuYn(mapperVO.getInfrMenuYn());
				 * category.setLnbFileName(mapperVO.getLnbFileName());
				 * category.setLnbFilePath(mapperVO.getLnbFilePath());
				 * category.setLnbFileSize(mapperVO.getLnbFileSize());
				 * category.setMainOffFileName(mapperVO.getMainOffFileName());
				 * category.setMainOffFilePath(mapperVO.getMainOffFilePath());
				 * category.setMainOnFileName(mapperVO.getMainOnFileName());
				 * category.setMenuDepth(mapperVO.getMenuDepth()); category.setRankFileName(mapperVO.getRankFileName());
				 * category.setRankFilePath(mapperVO.getRankFilePath());
				 * category.setSearchFileName(mapperVO.getSearchFileName());
				 * category.setSearchFilePath(mapperVO.getSearchFilePath());
				 * category.setSystemId(mapperVO.getSystemId()); category.setTargetUrl(mapperVO.getTargetUrl());
				 * category.setTenantId(mapperVO.getTenantId()); category.setUpMenuId(mapperVO.getUpMenuId());
				 * category.setUseYn(mapperVO.getUseYn());
				 */

				category.setSource(source);

				listVO.add(category);
			}

			responseVO = new CategoryListResponse();
			commonResponse = new CommonResponse();
			responseVO.setCategoryList(listVO);
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
	public CategoryDetailListResponse searchDetailCategoryList(MenuRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";
		String menuId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();

		CategoryDetailListResponse responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			throw new Exception("systemId 는 필수 파라메터 입니다.");
		}
		if (null == menuId || "".equals(menuId)) {
			throw new Exception("menuId 는 필수 파라메터 입니다.");
		}

		List<MenuDetailDTO> resultList = this.commonDAO.queryForList("category.getDetailCategory", requestVO,
				MenuDetailDTO.class);
		if (resultList != null) {

			// Response VO를 만들기위한 생성자
			Menu category = null;
			Source source = null;
			List<Menu> listVO = new ArrayList<Menu>();
			List<CategoryDetail> detailListVO = new ArrayList<CategoryDetail>();

			boolean tg = false;
			int count = 0;

			CategoryDetail categoryDetail = new CategoryDetail();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

			Iterator<MenuDetailDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuDetailDTO mapperVO = iterator.next();

				String mapperJson = objectMapper.writeValueAsString(mapperVO);
				this.log.debug(mapperJson);

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount();
				this.log.debug("totalCount : " + totalCount);

				source.setSize(mapperVO.getBodyFileSize());
				// category.setMenuEngName(mapperVO.getMenuEngName());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());

				/*
				 * category.setExpoOrd(mapperVO.getExpoOrd()); category.setInfrMenuYn(mapperVO.getInfrMenuYn());
				 * category.setLnbFileName(mapperVO.getLnbFileName());
				 * category.setLnbFilePath(mapperVO.getLnbFilePath());
				 * category.setLnbFileSize(mapperVO.getLnbFileSize());
				 * category.setMainOffFileName(mapperVO.getMainOffFileName());
				 * category.setMainOffFilePath(mapperVO.getMainOffFilePath());
				 * category.setMainOnFileName(mapperVO.getMainOnFileName());
				 * category.setMenuDepth(mapperVO.getMenuDepth()); category.setRankFileName(mapperVO.getRankFileName());
				 * category.setRankFilePath(mapperVO.getRankFilePath());
				 * category.setSearchFileName(mapperVO.getSearchFileName());
				 * category.setSearchFilePath(mapperVO.getSearchFilePath());
				 * category.setSystemId(mapperVO.getSystemId()); category.setTargetUrl(mapperVO.getTargetUrl());
				 * category.setTenantId(mapperVO.getTenantId()); category.setUpMenuId(mapperVO.getUpMenuId());
				 * category.setUseYn(mapperVO.getUseYn());
				 */
				category.setSource(source);

				if (mapperVO.getMenuId().indexOf("MN13") > -1) { // ebook -> 3depth
					this.log.debug("ebook !!");

					if (Integer.valueOf(mapperVO.getMenuDepth()) < 3) { // 2 depth
						this.log.debug("ebook 2 depth!!");
						if (tg == false && count > 0) {
							categoryDetail.setSubCategoryList(listVO);
							detailListVO.add(categoryDetail);

							categoryDetail = new CategoryDetail();
							listVO = new ArrayList<Menu>();

							tg = true;
						}
						categoryDetail.setCategory(category);
						count++;
					} else { // 3 depth
						this.log.debug("ebook 3 depth!!");
						listVO.add(category);
					}

					if (tg == true && count > 0) {
						tg = false;
						// count = 0;
					}
				} else { // 2depth
					listVO.add(category);

					count++;

					if (count >= totalCount) {
						categoryDetail.setSubCategoryList(listVO);
						detailListVO.add(categoryDetail);
					}
				}
				String categoryDetailJson = objectMapper.writeValueAsString(categoryDetail);

				this.log.debug("categoryDetail json : {}", categoryDetailJson);
			}

			responseVO = new CategoryDetailListResponse();
			commonResponse = new CommonResponse();
			responseVO.setCategoryList(detailListVO); // set category detail list
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			/*
			 * ObjectMapper objectMapper = new ObjectMapper();
			 * objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
			 */
			String CategoryDetailList = objectMapper.writeValueAsString(responseVO);

			this.log.debug("CategoryDetailList json : {}", CategoryDetailList);
			// System.out.println(json);

		}
		return responseVO;
	}

}
