/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.system.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;

/**
 * 캐쉬 컨트롤러
 *
 * Created on : 2014. 4. 28.
 * Created by : 서대영, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/system/cache")
public class CacheController {

	@Autowired
	private AclDataAccessService service;

	@RequestMapping(value = "/flushCacheForAcl", method = RequestMethod.GET)
	@ResponseBody
	public String flushCacheForAcl() {
		// 한방에 모두 캐쉬함. 추후 세분화 필요.
		this.service.flushCache();
		return "ACL Cache is flushed successfully.";
	}

}
