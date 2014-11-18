/* Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.stat.service.StatMemberService;

/**
 * StatController
 * 
 * Updated on : 2014. 10. 30.
 * Updated by : 서대영, SK 플래닛.
*/
@Controller
@RequestMapping(value = "/display/stat")
public class StatMemberController {
	
	@Autowired
	private StatMemberService service;
    
    @RequestMapping(value = "/listByMember/v1", method = RequestMethod.POST)
    @ResponseBody
    public ListByMemberRes listByMember(@RequestBody @Validated ListByMemberReq req, SacRequestHeader header) {
        return service.listByMember(req, header);
    }
    
}
