package com.skplanet.storeplatform.sac.display.banner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.display.banner.service.BannerService;

@Controller
@RequestMapping("/display/banner")
public class BannerController {
	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	private BannerService bannerService;

	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BannerRes specificDetail(BannerReq req) {
		return this.bannerService.searchBannerList(req);

	}

}
