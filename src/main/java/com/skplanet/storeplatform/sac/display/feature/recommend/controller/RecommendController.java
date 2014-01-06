package com.skplanet.storeplatform.sac.display.feature.recommend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonRes;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendWebtoonService;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminRes;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendAdminService;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendAdminServiceImpl;

@Controller
@RequestMapping("/display/feature/recommand")
public class RecommendController {
	private transient Logger logger = LoggerFactory.getLogger(RecommendController.class);

	@Autowired
	private RecommendWebtoonService recommendWebtoonService;

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
		
		logger.info(requestVO.toString());

		RecommendAdminService service = new RecommendAdminServiceImpl();
		RecommendAdminRes responseVO;
		responseVO = service.searchAdminList(requestVO);
		return responseVO;
	}
}
