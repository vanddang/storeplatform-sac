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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryResponse;
import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.product.vo.ProductCategoryDTO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ProductCommonService productCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.ProductCategoryService#searchCategoryProductList(com.skplanet.
	 * storeplatform.sac.client.product.vo.category.ProductCategoryRequest)
	 */
	@Override
	public ProductCategoryResponse searchCategoryProductList(ProductCategoryRequest request) {
		ProductCommonResponse res = this.productCommonService.searchMenuInfo(request);

		ProductCategoryResponse responseVO = null;

		List<ProductCategoryDTO> resultList = this.commonDAO.queryForList("ProductCategory.selectCategoryList",
				request, ProductCategoryDTO.class);

		if (resultList != null) {
			ProductCategoryDTO productDto = new ProductCategoryDTO();

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
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				productDto = resultList.get(i);

				// 상품 정보 (상품ID)
				identifier.setType("episode");
				identifier.setText(productDto.getProdId());

				// 상품 정보 (지원구분)
				product.setSupport(productDto.getDrmYn() + "|" + productDto.getPartParentClsfCd());

				// 메뉴 정보
				menu.setType("topClass");
				menu.setId(productDto.getUpMenuId());
				menu.setName(productDto.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(productDto.getMenuId());
				menu.setName(productDto.getMenuNm());
				menuList.add(menu);

				// 어플리케이션 정보
				app.setAid(productDto.getAid());
				app.setPackageName(productDto.getApkPkgNm());
				app.setVersionCode(productDto.getApkVer());
				app.setVersion(productDto.getProdVer());

				// 이용권한 정보
				rights.setGrade(productDto.getProdGrdCd());

				// 상품 정보 (상품명)
				title.setText(productDto.getProdNm());

				// 이미지 정보
				source.setType("thumbnail");
				source.setUrl(productDto.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product.setProductExplain(productDto.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price.setText(Integer.parseInt(productDto.getProdAmt()));

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setApp(app);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);
				productList.add(i, product);

				identifier = new Identifier();
				menu = new Menu();
				app = new App();
				rights = new Rights();
				title = new Title();
				source = new Source();
				price = new Price();
				product = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
			}

			responseVO = new ProductCategoryResponse();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(productDto.getTotalCount());
			responseVO.setCommonResponse(commonResponse);
		}
		return responseVO;
	}
}
