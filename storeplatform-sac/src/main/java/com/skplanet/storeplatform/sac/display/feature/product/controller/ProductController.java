/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.controller;

import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 6.
 * Updated by : 문동선
 */
@Controller
@RequestMapping( "/display/feature/product" )
public class ProductController {

	@Autowired
	private ProductListService productListService;

	/**
	 * <pre>
	 * 상품 목록 조회를 위한 통합 API – GET.
	 * </pre>
	 * 
	 * @param request	UserDefine 파라미터
	 * @param header	공통헤더
	 * @return ProductListRes 조회 결과
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ProductListSacRes searchProductList( ProductListSacReq request, SacRequestHeader header ) {
		return productListService.searchProductList( request );
	}

}
