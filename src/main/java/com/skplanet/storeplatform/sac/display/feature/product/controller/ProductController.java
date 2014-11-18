/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 6.
 * Updated by : 문동선
 */
@Controller
@RequestMapping("/display/feature/product")
public class ProductController {
	private transient Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductListService productListService;

	/**
	 * <pre>
	 * 상품 목록 조회를 위한 통합 API – GET.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return ProductListRes 조회 결과
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ProductListSacRes searchProductList( ProductListSacReq requestVO, SacRequestHeader header) {

		ProductListSacRes responseVO;
		responseVO = this.productListService.searchProductList(requestVO, header);
		return responseVO;
	}
}
