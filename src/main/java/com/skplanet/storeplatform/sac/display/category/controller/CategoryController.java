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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsListRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxListRes;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxReq;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryEbookComicService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryMusicContentsService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductDummyService;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryVodBoxService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonService;

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
	private CategorySpecificProductDummyService categorySpecificProductDummyService;

	/**
	 * <pre>
	 * 일반 카테고리 앱 상품 조회.
	 * </pre>
	 * 
	 * @param CategoryAppReq
	 * @return CategoryAppRes
	 */
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchAppList(CategoryAppReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAppList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryAppService.searchAppList(req);
	}

	/**
	 * <pre>
	 * 일반 카테고리 ebook/만화 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryEbookComicRes searchEbookComicList(CategoryEbookComicReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchEbookComicList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryEbookComicService.searchEbookComicList(req);
	}

	@RequestMapping(value = "/webtoonList/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryWebtoonRes searchWebtoonList(CategoryWebtoonReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categoryWebtoonService.searchWebtoonList(req);

	}

	@RequestMapping(value = "/vodBox/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public VodBoxListRes searchVodBoxList(VodBoxReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {
		return this.categoryVodBoxService.searchVodBoxList(requestVO);
	}

	@RequestMapping(value = "/music/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MusicContentsListRes searchMusicContentsList(MusicContentsReq requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {
		return this.categoryMusicContentsService.searchMusicContentsList(requestVO);
	}

	@RequestMapping(value = "/specific/product/list/real/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificRes searchSpecificProductList(CategorySpecificReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categorySpecificService.getSpecificProductList(req);
	}

	@RequestMapping(value = "/specific/product/list//v1", method = RequestMethod.GET)
	@ResponseBody
	public CategorySpecificRes searchSpecificProductDummyList(CategorySpecificReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchSpecificProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.categorySpecificProductDummyService.getSpecificProductList(req);
	}
}
