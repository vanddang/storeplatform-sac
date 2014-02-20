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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;
import com.skplanet.storeplatform.sac.member.user.service.ClauseService;

/**
 * 약관 조회 서비스 Controller
 * 
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 */
@Controller
public class ClauseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClauseController.class);

	@Autowired
	private ClauseService svc;

	@RequestMapping(value = "/member/user/listClause/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListClauseSacRes listClause() {
		LOGGER.info("####################################################");
		LOGGER.info("##### 2.1.32. 약관목록 조회 #####");
		LOGGER.info("####################################################");

		ListClauseSacRes res = this.svc.listClause();

		LOGGER.info("Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/detailClause/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailClauseSacRes detailClause(DetailClauseSacReq req) {
		LOGGER.info("####################################################");
		LOGGER.info("##### 2.1.33. 약관목록 상세조회 #####");
		LOGGER.info("####################################################");

		String itemCd = StringUtil.nvl(req.getClauseItemCd(), "");

		if ("".equals(itemCd)) {
			throw new StorePlatformException("SAC_MEM_0001", "getClauseItemCd()");
		}

		DetailClauseSacRes res = this.svc.detailClauseList(req);

		LOGGER.info("Final Response : {}", res.toString());

		return res;
	}
}
