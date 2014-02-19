package com.skplanet.storeplatform.sac.display.related.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.related.service.ArtistProductListService;
import com.skplanet.storeplatform.sac.display.related.service.AuthorProductListService;
import com.skplanet.storeplatform.sac.display.related.service.BoughtTogetherProductService;
import com.skplanet.storeplatform.sac.display.related.service.GenreProductListService;
import com.skplanet.storeplatform.sac.display.related.service.SellerProductListService;
import com.skplanet.storeplatform.sac.display.related.service.SellerProductService;
import com.skplanet.storeplatform.sac.display.related.service.SimilarProductService;

@Controller
@RequestMapping("/display/related")
public class RelatedProductController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ArtistProductListService artistProductListService;

	@Autowired
	private AuthorProductListService authorProductListService;

	@Autowired
	private GenreProductListService genreProductListService;

	@Autowired
	private SellerProductListService sellerProductListService;

	@Autowired
	private SimilarProductService similarProductService;

	@Autowired
	private BoughtTogetherProductService boughtTogetherProductService;

	@Autowired
	private SellerProductService sellerProductService;

	@RequestMapping(value = "/contributor/artist/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RelatedProductListRes searchArtistProductList(RelatedProductReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchArtistProductList start !!");

		return this.artistProductListService.searchArtistProductList(requestVO);
	}

	@RequestMapping(value = "/contributor/author/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AuthorProductListRes searchAuthorProductList(RelatedProductReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchAuthorProductList start !!");

		return this.authorProductListService.searchAuthorProductList(requestVO);
	}

	@RequestMapping(value = "/genre/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RelatedProductListRes searchGenreProductList(RelatedProductReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchGenreProductList start !!");

		return this.genreProductListService.searchGenreProductList(requestVO);
	}

	/**
	 * 
	 * <pre>
	 * 이 상품과 유사 상품 조회.
	 * </pre>
	 * 
	 * @param SimilarProductSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return SimilarProductSacRes
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/similar/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SimilarProductSacRes searchSimilarProductList(SimilarProductSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchSimilarProductList start !!");

		return this.similarProductService.searchSimilarProductList(requestVO, requestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 이 상품과 함께 구매한 상품 조회.
	 * </pre>
	 * 
	 * @param BoughtTogetherProductSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return BoughtTogetherProductSacRes
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/boughtTogether/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader) throws JsonGenerationException, JsonMappingException, IOException,
			Exception {

		this.logger.debug("RelatedProductController.searchBoughtTogetherProductList start !!");

		return this.boughtTogetherProductService.searchBoughtTogetherProductList(requestVO, requestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 특정 판매자별 상품 조회.
	 * </pre>
	 * 
	 * @param SellerProductSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @returnSellerProductSacRes
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/seller/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SellerProductSacRes searchSellerProductList(SellerProductSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchSellerProductList start !!");

		return this.sellerProductService.searchSellerProductList(requestVO, requestHeader);
	}
}
