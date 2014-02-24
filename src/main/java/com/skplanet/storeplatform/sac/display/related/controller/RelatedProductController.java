package com.skplanet.storeplatform.sac.display.related.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.related.service.BoughtTogetherProductService;
import com.skplanet.storeplatform.sac.display.related.service.SellerProductService;
import com.skplanet.storeplatform.sac.display.related.service.SimilarProductService;

/**
 * 
 * 메뉴 조회 Controller.
 * 
 * Updated on : 2014. 02. 20. Updated by : 유시혁.
 */

@Controller
@RequestMapping("/display/related")
public class RelatedProductController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SimilarProductService similarProductService;

	@Autowired
	private BoughtTogetherProductService boughtTogetherProductService;

	@Autowired
	private SellerProductService sellerProductService;

	/**
	 * 
	 * <pre>
	 * 이 상품과 유사 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            SimilarProductSacReq
	 * @param requestHeader
	 *            SimilarProductSacReq
	 * @return SimilarProductSacRes
	 */
	@RequestMapping(value = "/similar/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SimilarProductSacRes searchSimilarProductList(SimilarProductSacReq requestVO, SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchSimilarProductList start !!");

		return this.similarProductService.searchSimilarProductList(requestVO, requestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 이 상품과 함께 구매한 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            BoughtTogetherProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return BoughtTogetherProductSacRes
	 */
	@RequestMapping(value = "/boughtTogether/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchBoughtTogetherProductList start !!");

		return this.boughtTogetherProductService.searchBoughtTogetherProductList(requestVO, requestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 특정 판매자별 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            SellerProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return SellerProductSacRes
	 */
	@RequestMapping(value = "/seller/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SellerProductSacRes searchSellerProductList(SellerProductSacReq requestVO, SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchSellerProductList start !!");

		return this.sellerProductService.searchSellerProductList(requestVO, requestHeader);
	}
}
