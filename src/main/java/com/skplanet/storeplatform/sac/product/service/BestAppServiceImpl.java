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
import com.skplanet.storeplatform.sac.client.product.vo.best.BestAppRequestVO;
import com.skplanet.storeplatform.sac.client.product.vo.best.BestAppResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.best.BestAppVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
public class BestAppServiceImpl implements BestAppService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestAppService#BestAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestAppRequestVO)
	 */
	@Override
	public BestAppResponseVO searchBestAppList(BestAppRequestVO bestAppRequestVO) {
		BestAppResponseVO responseVO = null;

		// List<ProductCategoryMapperVO> resultList = this.commonDAO.queryForList("ProductCategory.selectCategoryList",

		// Response VO를 만들기위한 생성자
		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();

		BestAppVO bestAppVO = null;
		List<BestAppVO> listVO = new ArrayList<BestAppVO>();

		for (int i = 0; i < 5; i++) {
			Product product = new Product();
			Identifier identifier = new Identifier();
			App app = new App();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Source source = new Source();
			Price price = new Price();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("product" + i);
			identifier.setText("H090101222_" + i);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			Menu topMenu = new Menu();
			topMenu.setId("dummyMenuId0");
			topMenu.setName("dummyMenuName0");
			topMenu.setType("dummyMenuType0");
			menuList.add(topMenu);
			Menu menu = new Menu();
			menu.setId("dummyMenuId1");
			menu.setName("dummyMenuName1");
			menu.setType("dummyMenuType1");
			menuList.add(menu);

			/*
			 * App aid, packagename, versioncode, version
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

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("media_" + i);
			source.setSize("1024_" + i);
			source.setType("thumbNail");
			source.setUrl("http://www.naver.com");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText("0");

			product = new Product();
			product.setIdentifier(identifier);
			product.setSupport("y|iab");
			product.setMenuList(menuList);
			product.setApp(app);
			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain("베스트 앱_" + i);
			product.setSourceList(sourceList);
			product.setPrice(price);

			bestAppVO = new BestAppVO();
			bestAppVO.setProduct(product);
			listVO.add(bestAppVO);

		}
		responseVO = new BestAppResponseVO();
		responseVO.setBestAppList(listVO);
		return responseVO;
	}
}
