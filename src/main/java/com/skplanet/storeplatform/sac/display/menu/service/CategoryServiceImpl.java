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
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetail2ListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetail3ListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.display.menu.vo.MenuCategoryDTO;
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
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public CategoryListRes searchTopCategoryList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();

		CategoryListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			requestVO.setSystemId("test01");
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}
		List<MenuDetailDTO> resultList = this.commonDAO.queryForList("Menu.getTopCategoryList", requestVO,
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
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());
				category.setType("topClass");

				category.setSource(source);

				listVO.add(category);
			}

			responseVO = new CategoryListRes();
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
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchDetailCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public CategoryDetailRes searchDetailCategoryList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;
		boolean threeDepth = false;

		String tenantId = "";
		String systemId = "";
		String menuId = "";
		String statementId = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();

		CategoryDetailRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId)) {
			requestVO.setSystemId("test01");
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}
		if (null == menuId || "".equals(menuId)) {
			throw new Exception("menuId 는 필수 파라메터 입니다.");
		}

		statementId = "Menu.getDetailCategoryList";

		List<MenuDetailDTO> resultList = this.commonDAO.queryForList(statementId, requestVO, MenuDetailDTO.class);
		if (resultList != null) {

			threeDepth = this.check3DepthMenu(requestVO);

			// Response VO를 만들기위한 생성자
			Menu category = null;
			Source source = null;
			List<Menu> listVO = new ArrayList<Menu>();
			List<CategoryDetail> detailListVO = new ArrayList<CategoryDetail>();

			boolean tg = false;
			int count = 0;
			int idx = 1;

			CategoryDetail categoryDetail = new CategoryDetail();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

			if (threeDepth) {
				responseVO = new CategoryDetail3ListRes();
			} else {
				responseVO = new CategoryDetail2ListRes();
			}

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
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());

				category.setSource(source);

				if (threeDepth) { // => 3 DEPTH MENU
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType("topClass");
						responseVO.setCategory(category);
					} else {
						if (Integer.valueOf(mapperVO.getMenuDepth()) < 3) { // 2 depth
							if (tg == false && count > 0) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);

								categoryDetail = new CategoryDetail();
								listVO = new ArrayList<Menu>();

								tg = true;
							}

							categoryDetail.setCategory(category);
							count++;

							if (idx == totalCount && tg == false) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);
							}
						} else { // 3 depth
							listVO.add(category);
						}

						if (tg == true && count > 0) {
							tg = false;
							// count = 0;
						}

						if (idx == totalCount && tg == false) {
							categoryDetail.setSubCategoryList(listVO);
							detailListVO.add(categoryDetail);
						}
					}
					idx++;
				} else { // 2depth
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType("topClass");
						responseVO.setCategory(category);
						count++;
					} else {
						listVO.add(category);

						count++;

						if (count >= totalCount) {
							/*
							 * categoryDetail.setSubCategoryList(listVO); detailListVO.add(categoryDetail);
							 */
							responseVO.setCategoryList(listVO);
						}
					}
				}
			}

			commonResponse = new CommonResponse();
			if (threeDepth) {
				responseVO.setCategoryList(detailListVO); // set category detail list = 3 DEPTH MENU
			}
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			String CategoryDetailList = objectMapper.writeValueAsString(responseVO);

			this.log.debug("CategoryDetailList json : {}", CategoryDetailList);
			// System.out.println(json);

		}
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchSubCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public CategoryDetailRes searchSubCategoryList(MenuReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;
		boolean threeDepth = false;

		String tenantId = "";
		String systemId = "";
		String menuId = "";
		String deviceCd = "";
		String langCd = "";

		tenantId = requestVO.getTenantId();
		systemId = requestVO.getSystemId();
		menuId = requestVO.getMenuId();
		langCd = requestVO.getLangCd();
		deviceCd = requestVO.getDeviceModelCd();

		CategoryDetailRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
		}
		if (null == systemId || "".equals(systemId)) {
			requestVO.setSystemId("test01");
		}
		if (null == deviceCd || "".equals(deviceCd)) {
			requestVO.setDeviceModelCd("SHW-M250S");
		}
		if (null == langCd || "".equals(langCd)) {
			requestVO.setLangCd("ko");
		}

		if (null == menuId || "".equals(menuId)) {
			throw new Exception("menuId 는 필수 파라메터 입니다.");
		}

		List<MenuCategoryDTO> resultList = this.commonDAO.queryForList(this.getStatementId(requestVO), requestVO,
				MenuCategoryDTO.class);
		if (resultList != null) {

			threeDepth = this.check3DepthMenu(requestVO);

			// Response VO를 만들기위한 생성자
			Menu category = null;
			Source source = null;
			List<Menu> listVO = new ArrayList<Menu>();
			List<CategoryDetail> detailListVO = new ArrayList<CategoryDetail>();

			boolean tg = false;
			int count = 0;
			int idx = 1;

			CategoryDetail categoryDetail = new CategoryDetail();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

			if (threeDepth) {
				responseVO = new CategoryDetail3ListRes();
			} else {
				responseVO = new CategoryDetail2ListRes();
			}

			Iterator<MenuCategoryDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuCategoryDTO mapperVO = iterator.next();

				String mapperJson = objectMapper.writeValueAsString(mapperVO);
				this.log.debug(mapperJson);

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount(); // 메뉴 목록 총 조회수

				source.setSize(Integer.toString(mapperVO.getFileSize()));
				source.setUrl(mapperVO.getFilePos());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());
				category.setCount(Integer.toString(mapperVO.getMenuProdCnt())); // 전시 메뉴 상품수
				category.setSource(source);

				if (threeDepth) { // => 3 DEPTH MENU
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType("topClass");
						responseVO.setCategory(category);
					} else {
						if (Integer.valueOf(mapperVO.getMenuDepth()) < 3) { // 2 depth
							if (tg == false && count > 0) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);

								categoryDetail = new CategoryDetail();
								listVO = new ArrayList<Menu>();

								tg = true;
							}
							categoryDetail.setCategory(category);
							count++;

							if (idx == totalCount && tg == false) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);
							}
						} else { // 3 depth
							listVO.add(category);
						}

						if (tg == true && count > 0) {
							tg = false;
							// count = 0;
						}

						if (idx == totalCount && tg == false) {
							categoryDetail.setSubCategoryList(listVO);
							detailListVO.add(categoryDetail);
						}
					}
					idx++;

				} else { // 2depth
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType("topClass");
						responseVO.setCategory(category);
						count++;
					} else {
						listVO.add(category);

						count++;

						if (count >= totalCount) {
							responseVO.setCategoryList(listVO);
						}
					}
				}
			} // end of while

			commonResponse = new CommonResponse();
			if (threeDepth) {
				responseVO.setCategoryList(detailListVO); // set category detail list = 3 DEPTH MENU
			}
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			String CategoryDetailList = objectMapper.writeValueAsString(responseVO);

			this.log.debug("searchSubCategoryList json : {}", CategoryDetailList);
			// System.out.println(json);

		}
		return responseVO;
	}

	private String getStatementId(MenuReq requestVO) {

		String statementId = "";
		String menuId = requestVO.getMenuId();

		if ("DP13".equals(menuId) || "DP14".equals(menuId)) { // ebook/commic
			statementId = "MenuCategory.getEBookComicDetailCategoryList";
		} else if ("DP16".equals(menuId)) { // 음악
			statementId = "MenuCategory.getMusicDetailCategoryList";
		} else if ("DP17".equals(menuId) || "DP18".equals(menuId)) { // 영화/TV 방송
			statementId = "MenuCategory.getMovieTvDetailCategoryList";
		} else if ("DP28".equals(menuId)) { // 쇼핑 - 쿼리 검증 요망
			statementId = "MenuCategory.getShoppingDetailCategoryList";
		} else {
			statementId = "MenuCategory.getDetailCategoryList";
		}
		return statementId;
	}

	private boolean check3DepthMenu(MenuReq requestVO) {
		boolean result = false;

		MenuDetailDTO mapperVO = this.commonDAO.queryForObject("Menu.getTopMenu3Detph", requestVO, MenuDetailDTO.class);

		if (mapperVO != null && mapperVO.getMenuId().equals(requestVO.getMenuId())) {
			result = true;
		}

		return result;
	}

}
