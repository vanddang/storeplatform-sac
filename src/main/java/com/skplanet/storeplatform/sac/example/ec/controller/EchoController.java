package com.skplanet.storeplatform.sac.example.ec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sc.client.vo.UserCareerSearch;

@Controller
@RequestMapping("/test")
public class EchoController {
	private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

	@RequestMapping(value = "/echoGetWithQueryString.do", method = RequestMethod.GET)
	@ResponseBody
	public String echoGetWithQueryString(@RequestParam("name") String name) {
		logger.info("==================================::{}", name);
		return name;
	}

	@RequestMapping(value = "/echoPostWithResponseBody.do", method = RequestMethod.POST)
	@ResponseBody
	public UserCareerSearch echoPostWithResponseBody(@RequestBody UserCareerSearch userCareerSearch) {
		logger.info(userCareerSearch.toString());

		return userCareerSearch;
	}

	@RequestMapping(value = "/echoPostWithResponseBodyAndQueryString.do", method = RequestMethod.POST)
	@ResponseBody
	public UserCareerSearch echoPostWithResponseBodyAndQueryString(@RequestBody UserCareerSearch userCareerSearch,
			@RequestParam("name") String name) {

		logger.info(userCareerSearch.toString());

		UserCareerSearch result = new UserCareerSearch();

		result.setId(userCareerSearch.getId() + "-" + name);

		result.setName(userCareerSearch.getName() + "-" + name);

		result.setDescription(userCareerSearch.getDescription() + "-" + name);

		return result;
	}
}
