/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.controller;

import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictManager;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 상품목록조회
 * 
 * Updated on : 2014. 10. 6.
 * Updated by : 정화수
 */
@Controller
@RequestMapping("/display/feature/product")
public class ProductController {

	private transient Logger logger = LoggerFactory.getLogger( ProductController.class );

	@Autowired
	private ProductListService productListService;

	@Autowired
	private CacheEvictManager cacheEvictManager;

	/**
	 * 상품 목록을 조회한다.
	 *
	 * @param request	UserDefine 파라미터
	 * @param header	공통헤더
	 * @return ProductListRes 조회 결과
	 */
	@RequestMapping( value = "/list/v1", method = RequestMethod.GET )
	@ResponseBody
	public ProductListSacRes searchProductList( ProductListSacReq request, SacRequestHeader header) {
		ProductListSacRes responseVO;
		responseVO = this.productListService.searchProductList(request, header);
		return responseVO;
	}

	/**
	 * 상품목록 조회에서 사용중인 메타조립 전 내부 캐시를 모두 초기화한다.
	 */
	@RequestMapping( value = "/list/clearCache/v1", method = RequestMethod.GET )
	@ResponseBody
	public void clearCache() {
		cacheEvictManager.evictAllListProd();
	}

}
