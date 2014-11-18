/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 6.
 * Updated by : 문동선
 */
public interface ProductListService {
	ProductListSacRes searchProductList(ProductListSacReq requestVO, SacRequestHeader header);
	Product getProduct(SacRequestHeader header, ListProduct listProd);
}
