/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

@Service
@Transactional
public class ThemeThemeZoneServiceImpl implements ThemeThemeZoneService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public ThemeThemeZoneSacRes searchThemeThemeZoneList(ThemeThemeZoneSacReq req, SacRequestHeader header) {

		ThemeThemeZoneSacRes res = new ThemeThemeZoneSacRes();
		if (req.getDummy() == null) {
			return res;
		} else {
			return this.generateDummy();
		}
	}

	/**
	 * <pre>
	 * 더미 데이터 생성.
	 * </pre>
	 * 
	 * @return CategorySpecificSacRes
	 */
	private ThemeThemeZoneSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList;
		Title title = null;
		Menu menu = null;
		Source source = null;
		Price price = null;
		Rights rights = null;
		Contributor contributor = null;
		Date date = null;

		Product product = null;
		Layout layOut = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;

		sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		ThemeThemeZoneSacRes res = new ThemeThemeZoneSacRes();

		productList = new ArrayList<Product>();
		menuList = new ArrayList<Menu>();

		product = new Product();
		identifier = new Identifier();
		source = new Source();
		title = new Title();
		price = new Price();
		rights = new Rights();
		contributor = new Contributor();
		date = new Date();

		layOut = new Layout();
		title = new Title();
		// title 설정
		title.setText("브랜드샵 이름");
		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");

		// mene 설정
		menu = new Menu();
		menu.setId("dummyMenuId0");
		menu.setName("game/simulation");
		menuList.add(menu);
		layOut.setTitle(title);
		layOut.setSource(source);
		layOut.setMenu(menu);

		// Identifier 설정
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		identifier.setType("episodeId");
		identifier.setText("H900063306");
		identifierList.add(identifier);

		// title 설정
		title.setText("추리, 심리, 미스터리 모음 테마 eBook 모음전");

		// price 설정
		price.setText(3000);

		// mene 설정
		menu = new Menu();
		menu.setId("dummyMenuId0");
		menu.setName("game/simulation");
		menuList.add(menu);

		// rights 설정
		rights.setGrade("PD004401");

		// contributor 설정
		contributor.setName("김기백");
		contributor.setPublisher("코믹플러스");
		// date 설정
		date.setType("date/update");
		date.setText("20130820190000");
		contributor.setDate(date);

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		product = new Product();
		product.setIdentifierList(identifierList);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setContributor(contributor);
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setLayOut(layOut);
		res.setProductList(productList);

		return res;
	}
}
