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
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;
import com.skplanet.storeplatform.sac.display.related.service.ArtistProductListService;
import com.skplanet.storeplatform.sac.display.related.service.AuthorProductListService;
import com.skplanet.storeplatform.sac.display.related.service.GenreProductListService;
import com.skplanet.storeplatform.sac.display.related.service.SellerProductListService;
import com.skplanet.storeplatform.sac.display.related.service.SimilarProductListService;

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
	private SimilarProductListService similarProductListService;

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

	@RequestMapping(value = "/contributor/another/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RelatedProductListRes searchSellerProductList(RelatedProductReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchSellerProductList start !!");

		return this.sellerProductListService.searchSellerProductList(requestVO);
	}

	@RequestMapping(value = "/similar/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RelatedProductListRes searchSimilarProductList(RelatedProductReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		this.logger.debug("RelatedProductController.searchSimilarProductList start !!");

		return this.similarProductListService.searchSimilarProductList(requestVO);
	}
}
