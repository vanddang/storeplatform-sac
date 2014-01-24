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
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetail;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
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
	public CategoryListRes searchTopCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 0;

		String tenantId = "";
		String systemId = "";

		tenantId = requestHeader.getTenantHeader().getTenantId();
		systemId = requestHeader.getTenantHeader().getSystemId();

		requestVO.setTenantId(tenantId);
		requestVO.setSystemId(systemId);

		CategoryListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId) || "S01-01002".equals(systemId)) {
			requestVO.setSystemId("test01");
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}
		List<MenuDetailDTO> resultList = this.commonDAO.queryForList("Menu.getTopCategoryList", requestVO,
				MenuDetailDTO.class);
		if (resultList != null) {

			// Response VO를 만들기위한 생성자
			Menu category = null;
			Source source = null;
			List<Object> listVO = new ArrayList<Object>();

			Iterator<MenuDetailDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuDetailDTO mapperVO = iterator.next();

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount();

				source.setSize(mapperVO.getBodyFileSize());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());
				category.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);

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
	public CategoryDetailListRes searchDetailCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 0;
		boolean threeDepth = false;

		String tenantId = "";
		String systemId = "";
		String menuId = "";
		String statementId = "";

		tenantId = requestHeader.getTenantHeader().getTenantId();
		systemId = requestHeader.getTenantHeader().getSystemId();

		requestVO.setTenantId(tenantId);
		requestVO.setSystemId(systemId);

		menuId = requestVO.getMenuId();

		CategoryDetailListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == systemId || "".equals(systemId) || "S01-01002".equals(systemId)) {
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
			List<Object> listVO = new ArrayList<Object>();
			List<Object> detailListVO = new ArrayList<Object>();

			boolean tg = false;
			int count = 0;
			int idx = 1;

			CategoryDetail categoryDetail = new CategoryDetail();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

			responseVO = new CategoryDetailListRes();
			Iterator<MenuDetailDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuDetailDTO mapperVO = iterator.next();

				String mapperJson = objectMapper.writeValueAsString(mapperVO);
				this.log.debug(mapperJson);

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount();
				// this.log.debug("totalCount : " + totalCount);

				source.setSize(mapperVO.getBodyFileSize());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());

				category.setSource(source);

				if (threeDepth) { // => 3 DEPTH MENU
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
						responseVO.setCategory(category);
					} else {
						if (Integer.valueOf(mapperVO.getMenuDepth()) < 3) { // 2 depth
							if (tg == false && count > 0) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);

								categoryDetail = new CategoryDetail();
								listVO = new ArrayList<Object>();

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
						category.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
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
			}

			commonResponse = new CommonResponse();
			if (threeDepth) {
				responseVO.setCategoryList(detailListVO); // set category detail list = 3 DEPTH MENU
			}
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);

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
	public CategoryDetailListRes searchSubCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 0;
		boolean threeDepth = false;

		String tenantId = "";
		String systemId = "";
		String menuId = "";
		String deviceCd = "";
		String langCd = "";

		tenantId = requestHeader.getTenantHeader().getTenantId();
		systemId = requestHeader.getTenantHeader().getSystemId();
		deviceCd = requestHeader.getDeviceHeader().getModel();

		requestVO.setTenantId(tenantId);
		requestVO.setSystemId(systemId);
		requestVO.setDeviceModelCd(deviceCd);

		menuId = requestVO.getMenuId();
		langCd = requestVO.getLangCd();

		CategoryDetailListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
		}
		if (null == systemId || "".equals(systemId) || "S01-01002".equals(systemId)) {
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
			List<Object> listVO = new ArrayList<Object>();
			List<Object> detailListVO = new ArrayList<Object>();

			boolean tg = false;
			int count = 0;
			int idx = 1;

			CategoryDetail categoryDetail = new CategoryDetail();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

			responseVO = new CategoryDetailListRes();

			Iterator<MenuCategoryDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MenuCategoryDTO mapperVO = iterator.next();

				String mapperJson = objectMapper.writeValueAsString(mapperVO);
				this.log.debug(mapperJson);

				category = new Menu();
				source = new Source();

				totalCount = mapperVO.getTotalCount(); // 메뉴 목록 총 조회수

				source.setSize(mapperVO.getFileSize());
				source.setUrl(mapperVO.getFilePos());
				category.setId(mapperVO.getMenuId());
				category.setName(mapperVO.getMenuNm());
				category.setCount(mapperVO.getMenuProdCnt()); // 전시 메뉴 상품수
				category.setSource(source);

				if (threeDepth) { // => 3 DEPTH MENU
					if (Integer.valueOf(mapperVO.getMenuDepth()) == 1) {
						category.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
						responseVO.setCategory(category);
					} else {
						if (Integer.valueOf(mapperVO.getMenuDepth()) < 3) { // 2 depth
							if (tg == false && count > 0) {
								categoryDetail.setSubCategoryList(listVO);
								detailListVO.add(categoryDetail);

								categoryDetail = new CategoryDetail();
								listVO = new ArrayList<Object>();

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
						category.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
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
			responseVO.setCommonResponse(commonResponse);

			String CategoryDetailList = objectMapper.writeValueAsString(responseVO);

			this.log.debug("searchSubCategoryList json : {}", CategoryDetailList);
			// System.out.println(json);

		}
		return responseVO;
	}

	private String getStatementId(MenuReq requestVO) {

		String statementId = "";
		String menuId = requestVO.getMenuId();

		if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(menuId)
				|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(menuId)) { // ebook/commic
			statementId = "MenuCategory.getEBookComicDetailCategoryList";
		} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(menuId)) { // 음악
			statementId = "MenuCategory.getMusicDetailCategoryList";
		} else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(menuId)
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(menuId)) { // 영화/TV 방송
			statementId = "MenuCategory.getMovieTvDetailCategoryList";
		} else if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(menuId)) { // 쇼핑 - 쿼리 검증 요망
			statementId = "MenuCategory.getShoppingDetailCategoryList";
		} else { // 그외 APP
			statementId = "MenuCategory.getAppDetailCategoryList";
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
