/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.sample.controller;

import java.util.Date;

import com.skplanet.storeplatform.sac.client.example.sample.vo.SampleReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.example.sample.vo.SampleRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacReq;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.example.sample.service.SampleService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

@Controller
@RequestMapping(value = "/example/sample")
public class SampleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleService service;

	/**
	 * 더미 데이터
	 */
	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public SampleRes dummy(@RequestParam(value = "no", defaultValue = "0") Integer no) {
		if (no == 0) {
			throw new StorePlatformException("SAC_CMN_9998");
		}

		SampleRes res = new SampleRes();
		res.setNo(no);
		res.setId("#" + no);
		res.setName("My name");
		res.setDescription("My description");
		res.setDate(new Date());
		
		return res;
	}

	/**
	 * 더미 데이터
	 */
	@RequestMapping(value = "/postDummy", method = RequestMethod.POST)
	@ResponseBody
	public SampleRes postDummy(@RequestBody SampleReq req) {

		SampleRes res = new SampleRes();
		res.setNo(req.getNo());
		res.setId(req.getId());
		res.setName(req.getName());
		res.setDescription("New dummy has been posted.");
		res.setDate(new Date());

		return res;
	}
	
	/**
	 * 더미 데이터 (jsp 버전)
	 */
	@RequestMapping(value = "/dummy_jsp", method = RequestMethod.GET)
	public String dummy_jsp(@RequestParam(value = "no", defaultValue = "0") Integer no, Model model) {
		SampleRes res = dummy(no);
		model.addAttribute(res);
		return "/detail";
	}
	
	/**
	 * Database 연동
	 */
	@RequestMapping(value = "/database", method = RequestMethod.GET)
	public @ResponseBody Tenant database(@RequestParam String tenantId) {
		LOGGER.debug("# tennatId : {}", tenantId);
		
		if (StringUtils.isBlank(tenantId)) {
			throw new StorePlatformException("SAC_CMN_9998");
		}
		
		Tenant tenant = service.findTenantFromDb(tenantId);
		LOGGER.debug("# tenant : {}", tenant);
		
		return tenant;
	}
	
	/**
	 * Local SCI (Dummy) 연동
	 */
	@RequestMapping(value = "/localSci", method = RequestMethod.GET)
	public @ResponseBody SampleSacRes localSci(@RequestParam(value = "no", defaultValue = "0") Integer no) {
		LOGGER.debug("# no : {}", no);
		SampleSacReq req = new SampleSacReq(no);
		SampleSacRes res = service.findDummyFromLocalSci(req);
		LOGGER.debug("# SampleSacRes : {}", res);
		
		return res;
	}
	
	/**
	 * Local SCI (Banner List) 연동
	 */
	@RequestMapping(value = "/localSci2", method = RequestMethod.POST)
	public @ResponseBody BannerInfoSacRes localSci2(@RequestBody BannerInfoSacReq req) {
		LOGGER.debug("# BannerInfoSacReq : {}", req);
		BannerInfoSacRes res = service.findBannerFromLocalSci(req);
		LOGGER.debug("# BannerInfoSacRes : {}", res);
		return res;
	}

	/**
	 * Remote SCI (EC Dummy) 연동
	 */
	@RequestMapping(value = "/remoteSci", method = RequestMethod.GET)
	@ResponseBody
	public SampleRes remoteSci(@RequestParam("no") Integer no) {
		LOGGER.debug("# no : {}", no);
		Sample sample = service.findOneFromRemoteSci(no);
		SampleRes res = convert(sample);
		LOGGER.debug("# SampleSacRes : {}", res);
		return res;
	}

	private SampleRes convert(Sample sample) {
		SampleRes res = new SampleRes();
		res.setNo(sample.getNo());
		res.setId(sample.getId());
		res.setName(sample.getName());
		res.setDescription(sample.getDescription());
		res.setDate(sample.getDate());
		return res;
	}
	
	/**
	 * Remote SCI (EC SMS) 연동
	 */
	@RequestMapping(value = "/remoteSci2", method = RequestMethod.POST)
	@ResponseBody
	public SmsSendEcRes remoteSci2(@RequestBody SmsSendEcReq req) {
		LOGGER.debug("# SmsSendEcReq : {}", req);
		SmsSendEcRes res = service.sendSms(req);
		LOGGER.debug("# SampleSacRes : {}", res);
		return res;
	}
	
	/**
	 * SacRequestHeader ArguementReslover
	 */
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	@ResponseBody
	public SacRequestHeader resolveSacRequestHeader(SacRequestHeader header) {
		LOGGER.debug("# header : {}", header);
		return header;
	}

}
