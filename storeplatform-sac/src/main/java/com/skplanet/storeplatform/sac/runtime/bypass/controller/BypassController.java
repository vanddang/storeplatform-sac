/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.bypass.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skplanet.storeplatform.sac.runtime.bypass.service.BypassService;

@Controller
public class BypassController {
	
	@Autowired
	private BypassService srvc;

	@RequestMapping(value = "/bypass")
	public ResponseEntity<String> bypass(@RequestBody(required=false) String requestBody, HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		String queryString = request.getQueryString();
		HttpHeaders requestHeaders = makeRequestHeaders(request);
		ResponseEntity<String> responseEntity = srvc.bypass(method, queryString, requestHeaders, requestBody, response);
		return responseEntity;
	}

	/**
	 * 스프링 3.2 버전에서는 HttpHeaders를 핸들러 Argument Type으로 쓸 수 없어서 직접 변환해
	 */
	private HttpHeaders makeRequestHeaders(HttpServletRequest request) {
		ServletServerHttpRequest httpRequest = new ServletServerHttpRequest(request);
		return httpRequest.getHeaders();
	}

}
