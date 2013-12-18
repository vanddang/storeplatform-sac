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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
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
	 * com.skplanet.storeplatform.sac.biz.product.service.ProductCategoryService#searchProductCategoryList(com.skplanet
	 * .storeplatform.sac.client.product.vo.ProductCategoryReqVO)
	 */
	@Override
	public ProductCategoryResponseVO searchCategoryProductList(ProductCategoryRequestVO requestVO) {
		ProductCategoryResponseVO responseVO = null;

		List<ProductCategoryMapperVO> resultList = this.commonDAO.queryForList("ProductCategory.selectCategoryList",
				requestVO, ProductCategoryMapperVO.class);
		if (resultList != null) {
			ProductCategoryMapperVO mapperVO = new ProductCategoryMapperVO();

			// Response VO를 만들기위한 생성자
			Identifier identifier = null;
			Product product = null;
			ProductCategoryVO productCategoryVO = null;
			List<ProductCategoryVO> listVO = new ArrayList<ProductCategoryVO>();

			for (int i = 0; i < resultList.size(); i++) {
				mapperVO = resultList.get(i);

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product");
				identifier.setText(mapperVO.getProdId());

				// 지원구분
				product = new Product();
				product.setSupport(mapperVO.getDrmYn() + "|" + mapperVO.getPartParentClsfCd());

				product.setIdentifier(identifier);

				productCategoryVO = new ProductCategoryVO();
				productCategoryVO.setProduct(product);
				listVO.add(productCategoryVO);
			}

			responseVO = new ProductCategoryResponseVO();
			responseVO.setProductCategoryList(listVO);
		}
		return responseVO;
	}
}
