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

import com.skplanet.storeplatform.sac.client.display.vo.category.*;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
