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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;
import com.skplanet.storeplatform.sac.client.example.sample.vo.SampleRes;
import com.skplanet.storeplatform.sac.example.sample.service.SampleService;

@Controller
@RequestMapping(value = "/example/sample")
public class SampleController {

	@Autowired
	private SampleService service;

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public SampleRes detail(@RequestParam("no") Integer no) {
		Sample sample = this.service.detail(no);
		SampleRes res = this.convert(sample);
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

}
