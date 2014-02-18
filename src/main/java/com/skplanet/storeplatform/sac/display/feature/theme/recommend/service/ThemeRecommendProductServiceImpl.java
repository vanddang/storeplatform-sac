/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.recommend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.theme.recommend.vo.ThemeRecommend;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 테마 추천 상품 리스트 조회 API Interface 구현체.
 * 
 * Updated on : 2014. 02. 18. Updated by : 윤주영, GTSOFT.
 */
@Service
public class ThemeRecommendProductServiceImpl implements ThemeRecommendProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Override
	public ThemeRecommendSacRes searchThemeRecommendProductList(ThemeRecommendSacReq requestVO,
			SacRequestHeader requestHeader) throws StorePlatformException {
		// TODO Auto-generated method stub

		Map<String, Object> mapReq = new HashMap<String, Object>();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getRecommendId())) {
			throw new StorePlatformException("SAC_DSP_0002", "recommendId", requestVO.getRecommendId());
		}

		List<ThemeRecommend> listThemeRecommend = new ArrayList<ThemeRecommend>();
		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		mapReq.put("imageCdList", imageCodeList);

		listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgMainList", mapReq,
				ThemeRecommend.class);

		return this.makeThemeRecommendProductResult(listThemeRecommend, requestVO.getFilteredBy());
	}

	private ThemeRecommendSacRes makeThemeRecommendProductResult(List<ThemeRecommend> resultList, String filteredBy) {
		ThemeRecommendSacRes response = new ThemeRecommendSacRes();

		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		// layout 생성
		Layout layout = new Layout();

		Iterator<ThemeRecommend> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			ThemeRecommend mapper = iterator.next();

			Product packageProduct;
			Product subProduct;
			Identifier identifier;
			Title title;
			Source source;
			Menu menu;

			// Response VO를 만들기위한 생성자
			List<Menu> menuList;
			List<Source> sourceList;
			List<Identifier> identifierList;

			packageProduct = new Product();
			identifier = new Identifier();
			title = new Title();
			source = new Source();

			// Response VO를 만들기위한 생성자
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			identifier.setType(DisplayConstants.DP_THEME_IDENTIFIER_CD);
			identifier.setText(mapper.getPkgId());
			identifierList.add(identifier);

			title.setText(mapper.getPkgNm());

			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getPkgImgPos()));
			source.setUrl(mapper.getPkgImgPos());
			sourceList.add(source);

			packageProduct.setTitle(title);
			packageProduct.setIdentifierList(identifierList);
			packageProduct.setSourceList(sourceList);

			if (StringUtils.equalsIgnoreCase("short", filteredBy)) { // 테마 추천 메인 : 테마 추천 4개 노출

				List<Product> subProductList = new ArrayList<Product>();

				subProduct = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();

				title = new Title();
				source = new Source();

				// 상품ID
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId1());
				identifierList.add(identifier);

				menu = new Menu();
				menu.setId(mapper.getTopMenuId1());
				menu.setName(mapper.getTopMenuNm1());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(mapper.getMenuId1());
				menu.setName(mapper.getMenuNm1());
				menuList.add(menu);

				title.setText(mapper.getProdNm1());

				source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getProdImgPos1()));
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setUrl(mapper.getProdImgPos1());
				sourceList.add(source);

				subProduct.setTitle(title);
				subProduct.setMenuList(menuList);
				subProduct.setSourceList(sourceList);
				subProduct.setIdentifierList(identifierList);

				subProductList.add(subProduct);

				subProduct = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();

				title = new Title();
				source = new Source();

				// 상품ID
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId2());
				identifierList.add(identifier);

				menu = new Menu();
				menu.setId(mapper.getTopMenuId2());
				menu.setName(mapper.getTopMenuNm2());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(mapper.getMenuId2());
				menu.setName(mapper.getMenuNm2());
				menuList.add(menu);

				title.setText(mapper.getProdNm2());

				source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getProdImgPos2()));
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setUrl(mapper.getProdImgPos2());
				sourceList.add(source);

				subProduct.setTitle(title);
				subProduct.setMenuList(menuList);
				subProduct.setSourceList(sourceList);
				subProduct.setIdentifierList(identifierList);

				subProductList.add(subProduct);

				packageProduct.setSubProductList(subProductList);

			}

			productList.add(packageProduct);

		} // end of while

		commonResponse.setTotalCount(productList.size());
		response.setCommonRes(commonResponse);
		response.setProductList(productList);
		response.setLayout(layout);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ThemeRecommendSacRes searchDummyThemeRecommendProductList(ThemeRecommendSacReq requestVO,
			SacRequestHeader header) {
		// TODO Auto-generated method stub

		ThemeRecommendSacRes response = new ThemeRecommendSacRes();
		CommonResponse commonResponse = null;
		List<Product> listVO = new ArrayList<Product>();

		Product product;
		Layout layout;
		Identifier identifier;
		Title title;
		Source source;
		Menu menu;
		App app;
		Accrual accrual;
		Rights right;
		Date date;
		Price price;
		Support support;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Identifier> identifierList;
		List<Support> supportList;

		product = new Product();
		layout = new Layout();
		identifier = new Identifier();
		title = new Title();
		source = new Source();

		// Response VO를 만들기위한 생성자
		sourceList = new ArrayList<Source>();

		// 추천 패키지 추천 사유
		title.setText("나도 이젠 프로게이머");

		source.setMediaType("image/jpeg");
		source.setSize(128);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/bb.jpg");

		layout.setTitle(title);
		layout.setSource(source);

		product = new Product();
		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		identifierList = new ArrayList<Identifier>();
		supportList = new ArrayList<Support>();

		title = new Title();
		source = new Source();

		// 상품ID
		identifier = new Identifier();

		identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
		identifier.setText("0000648339");
		identifierList.add(identifier);

		menu = new Menu();
		menu.setId("DP01");
		menu.setName("GAME");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP01004");
		menu.setName("RPG");
		menuList.add(menu);

		app = new App();
		app.setAid("OA00648339");
		app.setPackageName("com.webzenm.mtg4kakao");
		app.setVersionCode("11");
		app.setVersion("1.1");
		app.setSize(256);

		accrual = new Accrual();
		accrual.setVoterCount(1519);
		accrual.setDownloadCount(31410);
		accrual.setScore(4.5);

		right = new Rights();
		right.setGrade("0");

		date = new Date();
		date.setType("date/issue");
		date.setText("20120913T195630+0900/20121013T195630+0900");

		price = new Price();
		price.setFixedPrice(4300);
		price.setDiscountRate(0.0);
		price.setText(4300);

		title.setText("뮤 더 제네시스 for Kakao ");

		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/0000648339_130_130_0_91_20131204195212.PNG");
		sourceList.add(source);

		support = new Support();
		support.setText("iab");
		support.setText("Y");
		supportList.add(support);

		support = new Support();
		support.setText("drm");
		support.setText("Y");
		supportList.add(support);

		product.setTitle(title);
		product.setMenuList(menuList);
		product.setApp(app);
		product.setAccrual(accrual);
		product.setRights(right);
		product.setDate(date);
		product.setPrice(price);
		product.setSourceList(sourceList);
		product.setSupportList(supportList);
		product.setIdentifierList(identifierList);

		listVO.add(product);

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(listVO.size());

		response.setCommonRes(commonResponse);
		response.setLayout(layout);
		response.setProductList(listVO);

		return response;
	}

}
