package com.skplanet.storeplatform.sac.display.banner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/banner")
public class BannerController {
	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public String specificDetail(@RequestParam("name") String name) {
		logger.info("==================================::{}", name);
		return name;
	}

}
