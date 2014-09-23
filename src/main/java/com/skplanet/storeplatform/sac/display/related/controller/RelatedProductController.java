package com.skplanet.storeplatform.sac.display.related.controller;

import com.skplanet.storeplatform.sac.client.display.vo.related.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.related.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@Autowired
	private AuthorProductService authorProductService;

	@Autowired
	private ArtistProductService artistProductService;

	/**
	 *
	 * <pre>
	 * [I03000032] 2.5.1.이 상품과 유사 상품 조회.
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
	public SimilarProductSacRes searchSimilarProductList(@Validated SimilarProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchSimilarProductList start !!");

		return this.similarProductService.searchSimilarProductList(requestVO, requestHeader);
	}

	/**
	 * [I03000033] 2.5.2. 이 상품과 함께 구매한 상품 조회 v1
	 */
	@RequestMapping(value = "/boughtTogether/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(@Validated BoughtTogetherProductSacReq requestVO, SacRequestHeader requestHeader) {
		this.logger.debug("RelatedProductController.searchBoughtTogetherProductList start !!");
		return this.boughtTogetherProductService.searchBoughtTogetherProductList(requestVO, requestHeader);
	}

	/**
	 * [I03000126] 2.5.6. 이 상품과 함께 구매한 상품 조회 v2
	 * - 조회 조건에 menuId 추가
	 */
	@RequestMapping(value = "/boughtTogether/product/list/v2", method = RequestMethod.GET)
	@ResponseBody
	public BoughtTogetherProductSacRes searchBoughtTogetherProductListV2(@Validated BoughtTogetherProductSacV2Req requestVO, SacRequestHeader requestHeader) {
		this.logger.debug("RelatedProductController.searchBoughtTogetherProductListV2 start !!");
		return this.boughtTogetherProductService.searchBoughtTogetherProductList(requestVO, requestHeader);
	}

	/**
	 *
	 * <pre>
	 * [I03000034] 2.5.3. 특정 판매자별 상품 조회
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
	public SellerProductSacRes searchSellerProductList(@Validated SellerProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchSellerProductList start !!");

		return this.sellerProductService.searchSellerProductList(requestVO, requestHeader);
	}

	/**
	 *
	 * <pre>
	 * [I03000036] 2.5.4.특정 작가별 상품 조회
	 * </pre>
	 *
	 * @param requestVO
	 *            AuthorProductSacReq
	 * @param requestHeader
	 *            AuthorProductSacReq
	 * @return AuthorProductSacRes
	 */
	@RequestMapping(value = "/author/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AuthorProductSacRes searchAuthorProductList(@Validated AuthorProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchAuthorProductList start !!");

		return this.authorProductService.searchAuthorProductList(requestVO, requestHeader);
	}

	/**
	 *
	 * <pre>
	 * [I03000037] 2.5.5. 특정 아티스트별 상품(곡) 조회
	 * </pre>
	 *
	 * @param requestVO
	 *            ArtistProductSacReq
	 * @param requestHeader
	 *            ArtistProductSacReq
	 * @return ArtistProductSacRes
	 */
	@RequestMapping(value = "/artist/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ArtistProductSacRes searchArtistProductList(@Validated ArtistProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		this.logger.debug("RelatedProductController.searchArtistProductList start !!");

		return this.artistProductService.searchArtistProductList(requestVO, requestHeader);
	}
}
