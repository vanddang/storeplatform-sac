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

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
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
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVodService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificWebtoonService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryVodBoxService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonSeriesService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonService;

/**
 * 일반 카테고리 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Controller
@RequestMapping("/display/category")
public class CategoryController {
	private transient Logger logger = LoggerFactory.getLogger(CategoryController.class);

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
	private CategorySpecificProductService categorySpecificService;

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
	public CategoryAppSacRes searchAppList(CategoryAppSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAppList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryAppService.searchAppList(req, header);
	}

	/**
	 * 
	 * <pre>
	 * 일반 카테고리 ebook/만화 상품 조회.
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
	public CategoryEbookComicSacRes searchEbookComicList(CategoryEbookComicSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchEbookComicList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryEbookComicService.searchEbookComicList(req, header);
	}

	/**
	 * 
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
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
	public CategoryWebtoonSacRes searchWebtoonList(SacRequestHeader header, CategoryWebtoonSacReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categoryWebtoonService.searchWebtoonList(header, req);

	}

	/**
	 * <pre>
	 * method 설명.
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
	 * method 설명.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return MusicContentsListRes
	 * @throws JsonGenerationException
	 *             JsonGenerationException
	 * @throws JsonMappingException
	 *             JsonGenerationException
	 * @throws IOException
	 *             IOException
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/music/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MusicContentsSacRes searchMusicContentsList(@Validated MusicContentsSacReq requestVO,
			SacRequestHeader requestHeader) throws JsonGenerationException, JsonMappingException, IOException,
			Exception {
		return this.categoryMusicContentsService.searchMusicContentsList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 특정 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param bindingResult
	 *            bindingResult
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	@RequestMapping(value = "/specific/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificProductList(@Validated CategorySpecificSacReq req,
			BindingResult bindingResult, SacRequestHeader header) {

		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_DSP_0001", error.getField());
			}
		}

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificService.getSpecificProductList(req, header);

	}

	/**
	 * <pre>
	 * 특정 상품 APP조회.
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
	public CategorySpecificSacRes searchSpecificAppList(CategorySpecificSacReq req, SacRequestHeader header) {
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
	@RequestMapping(value = "/specific/ebook/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificSacRes searchSpecificEbookList(CategorySpecificSacReq req, SacRequestHeader header) {
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
	public CategorySpecificSacRes searchSpecificVodList(CategorySpecificSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificVodList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categorySpecificVodService.getSpecificVodList(req, header);

	}

	/**
	 * <pre>
	 * 특정 상품 music 조회.
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
	public CategorySpecificSacRes searchSpecificMusicList(CategorySpecificSacReq req, SacRequestHeader header) {
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
	public CategorySpecificSacRes searchSpecificWebtoonList(CategorySpecificSacReq req, SacRequestHeader header) {
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
	public CategoryWebtoonSeriesSacRes searchWebtoonSeriesList(CategoryWebtoonSeriesSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryWebtoonSeriesService.getCategoryWebtoonSeriesList(req, header);

	}
}
