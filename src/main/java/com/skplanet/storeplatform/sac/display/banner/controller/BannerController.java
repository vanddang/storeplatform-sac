package com.skplanet.storeplatform.sac.display.banner.controller;

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

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.banner.service.BannerService;

@Controller
@RequestMapping("/display/banner")
public class BannerController {
	private static final Logger log = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	private BannerService bannerService;

	/**
	 * <pre>
	 * 배너 조회
	 * </pre>
	 * 
	 * @param BannerReq
	 *            bannerReq
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return BannerRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BannerRes searchBannerList(BannerReq bannerReq, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.log.debug("BannerController.searchBannerList start !!");

		return this.bannerService.searchBannerList(bannerReq, requestHeader);
	}

}
