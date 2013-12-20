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
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryRequestVO;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.product.vo.ProductCategoryMapperVO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.ProductCategoryService#searchCategoryProductList(com.skplanet.
	 * storeplatform.sac.client.product.vo.category.ProductCategoryRequestVO)
	 */
	@Override
	public ProductCategoryResponseVO searchCategoryProductList(ProductCategoryRequestVO requestVO) {
		ProductCategoryResponseVO responseVO = null;

		List<ProductCategoryMapperVO> resultList = this.commonDAO.queryForList("ProductCategory.selectCategoryList",
				requestVO, ProductCategoryMapperVO.class);

		if (resultList != null) {
			ProductCategoryMapperVO mapperVO = new ProductCategoryMapperVO();

			// Response VO를 만들기위한 생성자
			Identifier identifier = new Identifier();
			Menu menu = new Menu();
			App app = new App();
			Rights rights = new Rights();
			Title title = new Title();
			Source source = new Source();
			Price price = new Price();
			Product product = new Product();

			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();

			ProductCategoryVO productCategoryVO = new ProductCategoryVO();
			List<ProductCategoryVO> listVO = new ArrayList<ProductCategoryVO>();

			for (int i = 0; i < resultList.size(); i++) {
				mapperVO = resultList.get(i);

				// 상품 정보 (상품ID)
				identifier.setType("episode");
				identifier.setText(mapperVO.getProdId());

				// 상품 정보 (지원구분)
				product.setSupport(mapperVO.getDrmYn() + "|" + mapperVO.getPartParentClsfCd());

				// 메뉴 정보
				menu.setType("topClass");
				menu.setId(mapperVO.getUpMenuId());
				menu.setName(mapperVO.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(mapperVO.getMenuId());
				menu.setName(mapperVO.getMenuNm());
				menuList.add(menu);

				// 어플리케이션 정보
				app.setAid(mapperVO.getAid());
				app.setPackageName(mapperVO.getApkPkgNm());
				app.setVersionCode(mapperVO.getApkVer());
				app.setVersion(mapperVO.getProdVer());

				// 이용권한 정보
				rights.setGrade(mapperVO.getProdGrdCd());

				// 상품 정보 (상품명)
				title.setText(mapperVO.getProdNm());

				// 이미지 정보
				source.setType("thumbnail");
				source.setUrl(mapperVO.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product.setProductExplain(mapperVO.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price.setText(Integer.parseInt(mapperVO.getProdAmt()));

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setApp(app);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);

				productCategoryVO.setProduct(product);
				listVO.add(i, productCategoryVO);

				identifier = new Identifier();
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				app = new App();
				rights = new Rights();
				title = new Title();
				source = new Source();
				sourceList = new ArrayList<Source>();
				price = new Price();

				product = new Product();
				productCategoryVO = new ProductCategoryVO();
			}

			responseVO = new ProductCategoryResponseVO();
			responseVO.setProductCategoryList(listVO);
		}
		return responseVO;
	}
}
