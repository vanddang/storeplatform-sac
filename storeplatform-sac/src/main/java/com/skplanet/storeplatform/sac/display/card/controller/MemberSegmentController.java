/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentReq;
import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.service.MemberSegmentService;

/**
 * <p>
 * MemberSegmentController
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
@Controller
public class MemberSegmentController {

    @Autowired
    private MemberSegmentService service;

    @RequestMapping(value = "/display/card/memberSegment/v1", method = RequestMethod.POST)
    @ResponseBody
    public MemberSegmentRes getMemberSegment(@RequestBody @Valid MemberSegmentReq req, SacRequestHeader header) {
    	return service.findMemberSegment(req, header);
    }
    
}
