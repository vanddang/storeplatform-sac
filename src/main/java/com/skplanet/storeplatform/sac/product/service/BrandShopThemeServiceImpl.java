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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.theme.BrandShopThemeRequest;
import com.skplanet.storeplatform.sac.client.product.vo.theme.BrandShopThemeResponse;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

@Service
public class BrandShopThemeServiceImpl implements BrandShopThemeService {
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
	public BrandShopThemeResponse searchBrandShopThemeList(BrandShopThemeRequest BrandShopThemeRequest) {
		BrandShopThemeResponse response = new BrandShopThemeResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();

		Product product = new Product();
		Identifier identifier = new Identifier();
		Title title = new Title();
		Layout layout = new Layout();
		Price price = new Price();
		Accrual accrual = new Accrual();
		Rights rights = new Rights();
		Menu menu = new Menu();
		Source source = new Source();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);
		if ("".equals(BrandShopThemeRequest.getBrandshopId()) || BrandShopThemeRequest.getBrandshopId() == null) {
			for (int i = 1; i <= 1; i++) {

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText(" ");

				/*
				 * 상품에 따라 메뉴가 ShoppingStore, shoppingCoupon으로 노출될수 있음(Code)
				 */
				menu.setId("DP000501");
				menu.setName("게임");
				menu.setType("topClass");
				menuList.add(menu);

				title.setText("게임빌 브랜드샵");

				source.setMediaType("image/jpeg");
				source.setSize("26636");
				source.setType("thumbnail");
				source.setUrl("http://.../image.jpg");
				sourceList.add(source);

				product = new Product();
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setSourceList(sourceList);
				productList.add(product);

			}

		} else {
			for (int i = 1; i <= 1; i++) {

				title.setText("브랜드샵 이름");

				source = new Source();
				source.setUrl("http://<<BASE>>/image/bb.jpg");
				source.setType("thmubnail");
				source.setSize("128");
				source.setMediaType("image/jpeg");

				menu = new Menu();
				menu.setId("dummyMenuId0");
				menu.setName("카테고리명(메뉴명)");

				layout.setTitle(title);
				layout.setSource(source);
				layout.setMenu(menu);

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product");
				identifier.setText("A123456780");

				title = new Title();
				title.setText("마구마구");

				menu = new Menu();
				menu.setId("dummyMenuId0");
				menu.setName("대분류 카테고리명");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId1");
				menu.setName("중분류 카테고리명");
				menuList.add(menu);

				/*
				 * source mediaType, size, type, url
				 */
				source = new Source();
				source.setUrl("http://<<BASE>>/image/bb.jpg");
				source.setType("thmubnail");
				source.setSize("128");
				source.setMediaType("image/jpeg");
				sourceList.add(source);

				price.setText(1000);
				accrual.setVoterCount("2");
				accrual.setScore(4.2);
				rights.setGrade("0");

				product = new Product();
				product.setIdentifier(identifier);
				product.setTitle(title);
				product.setMenuList(menuList);
				product.setSourceList(sourceList);
				product.setPrice(price);
				product.setAccrual(accrual);
				product.setRights(rights);

				productList.add(product);

			}
		}
		response.setCommonResponse(commonResponse);
		response.setLayout(layout);
		response.setProductList(productList);
		return response;
	}
}
