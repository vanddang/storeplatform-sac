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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * 
 * 
 * Updated on : 2014. 02. 05. Updated by : 윤주영, GTSOFT.
 */
@Service
@Transactional
public class ThemeRecommendServiceImpl implements ThemeRecommendService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ThemeRecommendRes searchThemeRecommendList(ThemeRecommendReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub

		ThemeRecommendRes response = new ThemeRecommendRes();
		CommonResponse commonResponse = null;
		List<Product> listVO = new ArrayList<Product>();
		List<Product> subListVO = new ArrayList<Product>();

		Product packageProduct;
		Product subProduct;
		Layout layout;
		Identifier identifier;
		Title title;
		Source source;
		Menu menu;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Identifier> identifierList;

		packageProduct = new Product();
		layout = new Layout();
		identifier = new Identifier();
		title = new Title();
		source = new Source();

		// Response VO를 만들기위한 생성자
		sourceList = new ArrayList<Source>();
		identifierList = new ArrayList<Identifier>();

		// 추천 패키지 추천 사유
		layout.setName("추천사유");

		identifier.setType(DisplayConstants.DP_THEME_IDENTIFIER_CD);
		identifier.setText("1280");

		title.setText("나도 이젠 프로게이머");

		source.setMediaType("image/jpeg");
		source.setSize(128);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/bb.jpg");
		sourceList.add(source);

		identifierList.add(identifier);
		packageProduct.setTitle(title);
		packageProduct.setIdentifierList(identifierList);
		packageProduct.setSourceList(sourceList);

		// sub product list
		for (int i = 0; i < 2; i++) {
			subProduct = new Product();
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

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

			title.setText("뮤 더 제네시스 for Kakao " + (i + 1));

			source.setMediaType("image/png");
			source.setSize(0);
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/0000648339_130_130_0_91_20131204195212.PNG");
			sourceList.add(source);

			subProduct.setTitle(title);
			subProduct.setMenuList(menuList);
			subProduct.setSourceList(sourceList);
			subProduct.setIdentifierList(identifierList);

			subListVO.add(subProduct);
		}
		packageProduct.setSubProductList(subListVO);

		listVO.add(packageProduct);

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(listVO.size());

		response.setCommonRes(commonResponse);
		response.setLayout(layout);
		response.setProductList(listVO);

		return response;
	}

}
