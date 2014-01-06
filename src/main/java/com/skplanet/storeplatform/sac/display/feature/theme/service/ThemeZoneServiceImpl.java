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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneRes;
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

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

@Service
public class ThemeZoneServiceImpl implements ThemeZoneService {
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
	public ThemeZoneRes searchThemeZoneList(ThemeZoneReq themeZoneRequest) {
		ThemeZoneRes response = new ThemeZoneRes();

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();

		Product product = new Product();
		Identifier identifier = new Identifier();
		Title title = new Title();
		Layout layout = new Layout();
		Price price = new Price();
		Rights rights = new Rights();
		Contributor contributor = new Contributor();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);
		if ("".equals(themeZoneRequest.getThemezoneId()) || themeZoneRequest.getThemezoneId() == null) {
			for (int i = 1; i <= 1; i++) {

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText(" ");

				title.setText("EA 스포츠");

				product = new Product();
				product.setIdentifier(identifier);
				product.setTitle(title);
				productList.add(product);

			}

		} else {
			for (int i = 1; i <= 1; i++) {

				title.setText("테마제목");

				Source source = new Source();
				source.setUrl("http://<<BASE>>/image/bb.jpg");
				source.setType("thmubnail");
				source.setSize("128");
				source.setMediaType("image/jpeg");

				Menu menu = new Menu();
				menu.setId("dummyMenuId0");
				menu.setName("카테고리명(메뉴명)");

				layout.setTitle(title);
				layout.setSource(source);
				layout.setMenu(menu);

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H090101222_" + i);

				title = new Title();
				title.setText("식민행성");

				menu = new Menu();
				menu.setId("dummyMenuId0");
				menu.setName("game/simulation");
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

				contributor.setName("");
				contributor.setCompany("");
				Date date = new Date();
				date.setText("");
				contributor.setDate(date);

				price.setText(1000);
				rights.setGrade("0");

				product = new Product();
				product.setIdentifier(identifier);
				product.setTitle(title);
				product.setMenuList(menuList);
				product.setSourceList(sourceList);
				product.setPrice(price);
				product.setRights(rights);
				product.setContributor(contributor);

				productList.add(product);

			}
		}
		response.setCommonResponse(commonResponse);
		response.setLayout(layout);
		response.setProductList(productList);
		return response;
	}
}
