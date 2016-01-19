/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinRes;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserMarketPinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 회원 Market Pin 서비스 Controller
 * 
 * Updated on : 2016. 1. 18. Updated by : 임근대, SKP.
 */
@Controller
public class UserMarketPinController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserMarketPinController.class);

	@Autowired
	private UserMarketPinService userMarketPinService;

	/**
	 * I01000146 회원 Market PIN 번호 등록
	 * @param createMarketPinReq
	 * @return CreateMarketPinRes
	 */
	@RequestMapping(value = "/member/user/createMarketPin/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateUserMarketPinRes createMarketPin(@RequestBody @Valid CreateUserMarketPinReq createMarketPinReq) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(createMarketPinReq));
		CreateUserMarketPinRes res = userMarketPinService.createMarketPin(createMarketPinReq);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * I01000147 회원 Market PIN 번호 확인
	 * @param checkMarketPinReq
	 * @return CheckMarketPinRes
	 */
	@RequestMapping(value = "/member/user/checkMarketPin/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckUserMarketPinRes checkMarketPin(@RequestBody @Valid CheckUserMarketPinReq checkMarketPinReq) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(checkMarketPinReq));
		CheckUserMarketPinRes res = userMarketPinService.checkMarketPin(checkMarketPinReq);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}


}
