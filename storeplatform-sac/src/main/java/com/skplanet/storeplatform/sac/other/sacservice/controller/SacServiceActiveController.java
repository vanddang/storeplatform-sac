/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.sacservice.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReqList;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveRes;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveResList;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceService;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceTypeService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacService On/Off Configuration 컨트롤러
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛
 */
@Controller
@RequestMapping(value = "/other/service/")
public class SacServiceActiveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SacServiceActiveController.class);

	@Autowired
	private SacServiceService svc;

	@Autowired
	private SacServiceTypeService typeSvc;

	public void setDataSvc(SacServiceService dataSvc) {
		this.svc = dataSvc;
	}

	public void setTypeSvc(SacServiceTypeService typeSvc) {
		this.typeSvc = typeSvc;
	}

	@RequestMapping(value = "/getActive/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetActiveRes getActive(@RequestBody @Validated GetActiveReq req, SacRequestHeader sacRequestHeader) {
		LOGGER.info("/getActive/v1's req : {}", req);
		SacService vo = this.typeSvc.fromGetReq(req, sacRequestHeader);
		vo = this.svc.getServiceActive(vo);
		GetActiveRes res = this.typeSvc.toGetRes(vo);
		return res;
	}

	@RequestMapping(value = "/setActive/v1", method = RequestMethod.POST)
	@ResponseBody
	public SetActiveRes setActive(@RequestBody @Validated SetActiveReq req, SacRequestHeader sacRequestHeader) {
		LOGGER.info("/setActive/v1's req : {}", req);
		SacService vo = this.typeSvc.fromSetReq(req, sacRequestHeader);
		vo = this.svc.getServiceActive(vo);
		SetActiveRes res = this.typeSvc.toSetRes(vo);
		return res;
	}

	@RequestMapping(value = "/getActiveList/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetActiveResList getActiveList(@RequestBody @Validated GetActiveReqList reqList, SacRequestHeader sacRequestHeader) {
		LOGGER.info("/getActiveList/v1's reqList : {}", reqList);
		if (CollectionUtils.isEmpty(reqList.getServiceList())) {
			return new GetActiveResList();
		}
		
		List<SacService> voList = this.typeSvc.fromGetReqList(reqList.getServiceList(), sacRequestHeader);
		voList = this.svc.getServiceActiveList(voList);
		List<GetActiveRes> resList = this.typeSvc.toGetResList(voList);
		return new GetActiveResList(resList);
	}

	@RequestMapping(value = "/setActiveList/v1", method = RequestMethod.POST)
	@ResponseBody
	public List<SetActiveRes> setActiveList(@RequestBody @Validated List<SetActiveReq> reqList, SacRequestHeader sacRequestHeader) {
		LOGGER.info("/setActiveList/v1's reqList : {}", reqList);
		List<SacService> voList = this.typeSvc.fromSetReqList(reqList, sacRequestHeader);
		voList = this.svc.setServiceActiveList(voList);
		List<SetActiveRes> resList = this.typeSvc.toSetResList(voList);
		return resList;
	}

}
