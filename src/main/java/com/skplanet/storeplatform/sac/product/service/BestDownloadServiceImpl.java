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
import com.skplanet.storeplatform.sac.client.product.vo.best.BestDownloadResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 20. Updated by : 이석희, SK 플래닛.
 */

@Service
public class BestDownloadServiceImpl implements BestDownloadService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestDownloadService#BestDownloadService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestDownloadRequestVO)
	 */
	@Override
	public BestDownloadResponse searchBestDownloadList(String listId, String imageSizeCd, String menuId,
			String b2bprod, String hdv, String drm, String prodGradeCd, String offset, String count) {
		BestDownloadResponse response = new BestDownloadResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Support> supportList = new ArrayList<Support>();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		for (int i = 1; i <= 1; i++) {
			Product product = new Product();
			Identifier identifier = new Identifier();
			App app = new App();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Source source = new Source();
			Price price = new Price();
			Title title = new Title();
			Support support = new Support();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("product" + i);
			identifier.setText("H090101222_" + i);

			support.setType("Y");
			support.setText("iab");
			supportList.add(support);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			Menu menu = new Menu();
			menu.setId("dummyMenuId0");
			menu.setName("dummyMenuName0");
			menu.setType("dummyMenuType0");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("dummyMenuId1");
			menu.setName("dummyMenuName1");
			menu.setType("dummyMenuType1");
			menuList.add(menu);

			/*
			 * App aid, packagename, versioncode, version 상품이 앱일 경우 데이터 존재 앱이 아닐 경우 없음
			 */
			app.setAid("A090101222_" + i);
			app.setPackageName("app dummy package" + i);
			app.setVersionCode("dummy version Code" + i);
			app.setVersion("1.1");

			/*
			 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
			 */
			accrual.setVoterCount("1234");
			accrual.setDownloadCount("800");
			accrual.setScore(3.3);

			/*
			 * Rights grade
			 */
			rights.setGrade("1");

			title.setText("다운로드 상품명");

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("media_" + i);
			source.setSize("1024_" + i);
			source.setType("thumbNail");
			source.setUrl("http://./4_182_261_130x186.PNG");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(0);

			product = new Product();
			product.setIdentifier(identifier);
			// product.setSupport("y|iab");
			product.setSupportList(supportList);
			product.setMenuList(menuList);
			product.setApp(app);
			product.setAccrual(accrual);
			product.setRights(rights);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setProductExplain("상품 설명_" + i);
			product.setPrice(price);

			productList.add(product);

		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
