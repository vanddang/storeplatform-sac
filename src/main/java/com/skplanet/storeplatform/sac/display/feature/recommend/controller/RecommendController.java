package com.skplanet.storeplatform.sac.display.feature.recommend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonRes;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendAdminService;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendWebtoonService;

@Controller
@RequestMapping("/display/feature/recommend")
public class RecommendController {
	private transient Logger logger = LoggerFactory.getLogger(RecommendController.class);

	@Autowired
	private RecommendWebtoonService recommendWebtoonService;
	@Autowired
	private RecommendAdminService recommendAdminService;

	@RequestMapping(value = "/webtoonList/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendWebtoonRes searchWebtoonList(RecommendWebtoonReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAdminWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.recommendWebtoonService.searchWebtoonList(req);

	}

	@RequestMapping(value = "/admin/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendAdminRes searchAdminList(RecommendAdminReq requestVO) {

		//this.logger.info(requestVO.toString());
		if ( requestVO.getCount() == 0 )
			requestVO.setCount(10);
		//임시 저장
		requestVO.setTenantId("S01");
		requestVO.setDeviceModelCd("SHW-M180L");
		requestVO.setLangCd("ko");
		requestVO.setListId("ADM000000012");
		requestVO.setTopMenuId(StringUtil.nvl(requestVO.getTopMenuId(), "DP01"));
		
		RecommendAdminRes responseVO;
		responseVO = recommendAdminService.searchAdminList(requestVO);
		return responseVO;
	}
}
