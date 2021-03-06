/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryEbookComicService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryMusicContentsService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificAppService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificEbookService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificMusicService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificShoppingService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificSongService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVodService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVoucherService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificWebtoonService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryVodBoxService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonSeriesService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonService;
import com.skplanet.storeplatform.sac.display.category.vo.SearchProductListParam;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * 일반 카테고리 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Controller
@RequestMapping("/display/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryAppService categoryAppService;

	@Autowired
	private CategoryEbookComicService categoryEbookComicService;

	@Autowired
	private CategoryWebtoonService categoryWebtoonService;

	@Autowired
	private CategoryVodBoxService categoryVodBoxService;

	@Autowired
	private CategoryMusicContentsService categoryMusicContentsService;

	@Autowired
	private CategorySpecificAppService categorySpecificAppService;

	@Autowired
	private CategorySpecificEbookService categorySpecificEbookService;

	@Autowired
	private CategorySpecificVodService categorySpecificVodService;

	@Autowired
	private CategorySpecificMusicService categorySpecificMusicService;

	@Autowired
	private CategorySpecificWebtoonService categorySpecificWebtoonService;

	@Autowired
	private CategoryWebtoonSeriesService categoryWebtoonSeriesService;

	@Autowired
	private CategorySpecificSongService categorySpecificSongService;

	@Autowired
	private CategorySpecificShoppingService categorySpecificShoppingService;

	@Autowired
	private CategorySpecificVoucherService categorySpecificVoucherService;

	@Autowired
	private CategorySpecificProductService categorySpecificProductService;

	/**
	 * <pre>
	 * 일반 카테고리 앱 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 앱 상품 리스트
	 */
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppSacRes searchAppList(@Validated CategoryAppSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAppList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryAppService.searchAppList(req, header);
	}

	/**
	 * 
	 * <pre>
	 * [I03000024] 2.4.3.일반 카테고리 ebook/코믹 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 ebook/만화 상품 리스트
	 */
	@RequestMapping(value = "/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryEbookComicSacRes searchEbookComicList(@Validated CategoryEbookComicSacReq req,
			SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchEbookComicList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryEbookComicService.searchEbookComicList(req, header);
	}

	/**
	 * 
	 * <pre>
	 * [I03000025] 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 웹툰 리스트
	 */
	@RequestMapping(value = "/webtoonList/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryWebtoonSacRes searchWebtoonList(SacRequestHeader header, @Validated CategoryWebtoonSacReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categoryWebtoonService.searchWebtoonList(header, req);

	}

	/**
	 * <pre>
	 * VOD보관함(Vod Box)
	 * </pre>
	 * 
	 * @param requestVO
	 *            CategoryVodBoxSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return CategoryVodBoxSacRes
	 */
	@RequestMapping(value = "/vodBox/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryVodBoxSacRes searchVodBoxList(@Validated CategoryVodBoxSacReq requestVO,
			SacRequestHeader requestHeader) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodBoxList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categoryVodBoxService.searchVodBoxList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * [I03000015] 음원 컨텐츠 조회
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return MusicContentsListRes
	 */
	@RequestMapping(value = "/music/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MusicContentsSacRes searchMusicContentsList(@Validated MusicContentsSacReq requestVO,
			SacRequestHeader requestHeader) {
		return this.categoryMusicContentsService.searchMusicContentsList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * [I03000017] 2.4.1.1. 특정 상품 APP 조회
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificAppList(@Validated CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificAppList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificAppService.getSpecificAppList(req, header);

	}

	/**
	 * <pre>
	 * 특정 상품 Ebook 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificEbookList(@Validated CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificEbookList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificEbookService.getSpecificEbookList(req, header);

	}

	/**
	 * <pre>
	 * 특정 상품 Vod 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/vod/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificVodList(@Validated CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificVodList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificVodService.getSpecificVodList(req, header);

	}

	/**
	 * <pre>
	 * [I03000020] 2.4.1.4. 특정 상품 music 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/music/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificMusicList(@Validated CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificMusicList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificMusicService.getSpecificMusicList(req, header);

	}

	/**
	 * <pre>
	 * 특정 상품 webtoon 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/webtoon/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificWebtoonList(@Validated CategorySpecificSacReq req,
			SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificWebtoonService.getSpecificWebtoonList(req, header);

	}

	/**
	 * <pre>
	 * 웹툰 회차별 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/webtoon/series/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryWebtoonSeriesSacRes searchWebtoonSeriesList(@Validated CategoryWebtoonSeriesSacReq req,
			SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryWebtoonSeriesService.getCategoryWebtoonSeriesList(req, header);

	}

	/**
	 * <pre>
	 * [I03000109] 2.4.1.5. 특정 상품 music 조회 (by songID)
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/song/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificSongList(@Validated CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificMusicList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificSongService.getSpecificSongList(req, header);

	}

	/**
	 * <pre>
	 * [I03000055] 2.4.1.7 특정 상품 Shopping 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specific/shopping/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryShoppingSacRes searchSpecificShoppingDetail(SacRequestHeader header,
			@Validated CategoryShoppingSacReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificShoppingDetail Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categorySpecificShoppingService.searchSpecificShoppingDetail(header, req);

	}

	/**
	 * <pre>
	 * [I03000055] 2.4.1.8 특정 상품 이용권 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specific/voucher/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryVoucherSacRes searchSpecificVoucherDetail(SacRequestHeader header,
			@Validated CategoryVoucherSacReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificVoucherDetail Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categorySpecificVoucherService.searchSpecificVoucherDetail(header, req);

	}

	@RequestMapping(value = "/specific/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificProductList(SacRequestHeader header,
			@Validated CategorySpecificSacReq req) {

		String tenantId = header.getTenantHeader().getTenantId();
		String langCd = header.getTenantHeader().getLangCd();
		String deviceModelCd = Strings.nullToEmpty(req.getIgnoreProvisionYn()).equals("N") ? header.getDeviceHeader()
				.getModel() : DisplayConstants.DP_ANY_PHONE_4APP;

		List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
		if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list",
					DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
		}

        SearchProductListParam param = new SearchProductListParam(tenantId, langCd, deviceModelCd, prodIdList);
        if(StringUtils.isNotEmpty(req.getProdGradeCd()))
            param.setFilterProdGradeCdStr(req.getProdGradeCd());
        if(StringUtils.isNotEmpty(req.getPlus19Yn()))
            param.setFilter19plus(req.getPlus19Yn().equals("Y"));

        return this.categorySpecificProductService.searchProductList(param);
	}
}
