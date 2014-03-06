/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.appguide.vo.Appguide;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * App guide 테마 추천 메인 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 05. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeMainServiceImpl implements AppguideThemeMainService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.AppguideVersionServiceImpl#searchThemeRecommendMain(AppguideSacReq
	 * requestVO, SacRequestHeader requestHeader)
	 */
	@Override
	public AppguideSacRes searchThemeRecommendMain(AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		AppguideSacRes responseVO = new AppguideSacRes();

		CommonResponse commonResponse = new CommonResponse();

		String className = this.getClass().getName();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		mapReq.put("listGrpCd", "TAP"); // 앱가이드 테마추천

		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		mapReq.put("imageCdList", imageCodeList);
		mapReq.put("START_ROW", requestVO.getOffset());
		mapReq.put("END_ROW", (requestVO.getOffset() + requestVO.getCount() - 1));

		List<Appguide> themeMainList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendList", mapReq,
				Appguide.class);
		if (themeMainList == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		List<String> themeIdList = new ArrayList<String>();
		for (Appguide t : themeMainList) {
			themeIdList.add(t.getThemeId());
		}
		mapReq.put("themeIdList", themeIdList);

		List<Appguide> themeProductList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendProductList",
				mapReq, Appguide.class);
		if (themeProductList == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		List<Product> productList = this.makeProductList(themeMainList, themeProductList);

		commonResponse.setTotalCount(this.totalCount);
		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(productList);
		return responseVO;
	}

	private List<Product> makeProductList(List<Appguide> themeMainList, List<Appguide> themeProductList) {

		List<Product> mainListVO = new ArrayList<Product>();
		List<Product> sublistVO = new ArrayList<Product>();

		for (Appguide main : themeMainList) {

			// 테마 정보
			Product theme = new Product();
			Identifier themeId = new Identifier();
			themeId.setText(main.getThemeId());
			themeId.setType("theme");
			theme.setIdentifier(themeId);

			Title themeNm = new Title();
			themeNm.setText(main.getThemeNm());
			theme.setTitle(themeNm);
			theme.setThemeType(main.getThemeType());

			if (!StringUtils.isNullOrEmpty(main.getThemeImg())) {
				List<Source> themeUrlList = new ArrayList<Source>();
				Source themeUrl = new Source();
				themeUrl.setUrl(main.getThemeImg());
				themeUrlList.add(themeUrl);
				theme.setSourceList(themeUrlList);
			}

			this.totalCount = main.getTotalCount();

			for (Appguide mapper : themeProductList) {
				if (main.getThemeId().equals(mapper.getThemeId())) {

					// 테마별 상품 정보
					Product product;
					Identifier identifier;
					Title title;
					App app;
					Rights rights;
					Source source;
					Menu menu;

					// Response VO를 만들기위한 생성자
					List<Menu> menuList;
					List<Source> sourceList;
					List<Identifier> identifierList;

					product = new Product();
					identifier = new Identifier();
					title = new Title();
					app = new App();
					rights = new Rights();
					source = new Source();

					// 상품ID
					identifier = new Identifier();

					// Response VO를 만들기위한 생성자
					menuList = new ArrayList<Menu>();
					sourceList = new ArrayList<Source>();
					identifierList = new ArrayList<Identifier>();

					Map<String, String> idReqMap = new HashMap<String, String>();
					idReqMap.put("prodId", mapper.getProdId());
					idReqMap.put("topMenuId", mapper.getTopMenuId());
					idReqMap.put("contentsTypeCd", mapper.getContentsTypeCd());
					// idReqMap.put("outsdContentsId", mapper.getOutsdContentsId());

					title.setText(mapper.getProdNm());
					product.setTitle(title);

					menu = new Menu();
					menu.setId(mapper.getTopMenuId());
					menu.setName(mapper.getTopMenuNm());
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapper.getMenuId());
					menu.setName(mapper.getMenuNm());
					menuList.add(menu);
					product.setMenuList(menuList);

					/*
					 * Rights grade
					 */
					rights.setGrade(mapper.getProdGrdCd());
					product.setRights(rights);

					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getFilePos()));
					source.setUrl(mapper.getFilePos());
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 상품 SVC_GRP_CD 조회
					// DP000203 : 멀티미디어
					// DP000206 : Tstore 쇼핑
					// DP000205 : 소셜쇼핑
					// DP000204 : 폰꾸미기
					// DP000201 : 애플리캐이션
					if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 앱 타입일 경우

						identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
						identifier.setText(mapper.getProdId());
						identifierList.add(identifier);
						product.setIdentifierList(identifierList);

						app.setAid(mapper.getAid());
						app.setPackageName(mapper.getApkPkg());
						app.setVersionCode(mapper.getApkVerCd());
						app.setVersion(mapper.getProdVer()); // 확인 필요
						product.setApp(app);
					} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 멀티미디어
																											  // 타입일
																											  // 경우
						identifierList = this.generateIdentifierList(idReqMap);
						product.setIdentifierList(identifierList);

						if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(mapper.getTopMenuId())
								|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 영화/방송
						} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook /
																										  // Comic
						} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 음원 상품의 경우
						}
					} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 쇼핑
																												   // 상품의
																												   // 경우
						identifierList = this.generateIdentifierList(idReqMap);
						product.setIdentifierList(identifierList);
					}

					sublistVO.add(product);
				} // end of if
			} // end of for
			theme.setSubProductList(sublistVO);
			mainListVO.add(theme);
			sublistVO = new ArrayList<Product>();

		} // end of for

		return mainListVO;
	}

	private List<Identifier> generateIdentifierList(Map<String, String> param) {
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		String contentsTypeCd = param.get("contentsTypeCd");
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Episode ID 기준검색일 경우 (PD002502)
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				if (param.get("catalogId") != null) {
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(param.get("catalogId"));
					identifierList.add(identifier);
				} else {
					if (param.get("prodId") != null) {
						identifier = new Identifier();
						identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
						identifier.setText(param.get("prodId"));
						identifierList.add(identifier);
					}
				}
			} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_SONG_IDENTIFIER_CD);
				identifier.setText(param.get("outsdContentsId"));
				identifierList.add(identifier);
			}
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd) // Catalog ID 기준 검색일 경우
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("catalogId"));
			identifierList.add(identifier);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Channel ID 기준 검색일 경우
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);
		}
		return identifierList;
	}
}
