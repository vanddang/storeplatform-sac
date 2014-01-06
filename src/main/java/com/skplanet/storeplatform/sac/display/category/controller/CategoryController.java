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
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsListRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxListRes;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxReq;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryMusicContentsService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryVodBoxService;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonService;
import com.skplanet.storeplatform.sac.product.service.ProductCommonServiceImpl;

@Controller
@RequestMapping("/display/category")
public class CategoryController {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	private CategoryAppService categoryAppService;

	@Autowired
	private CategoryWebtoonService categoryWebtoonService;
	
	@Autowired
	private CategoryVodBoxService categoryVodBoxService;

	@Autowired
	private CategoryMusicContentsService categoryMusicContentsService;

	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public CategoryAppRes searchCategoryAppList(CategoryAppReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchCategoryAppList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.categoryAppService.searchCategoryAppList(req);
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

}
