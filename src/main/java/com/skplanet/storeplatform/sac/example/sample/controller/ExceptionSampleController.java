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

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformError;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformXssInvalidException;

/**
 * 예외 테스트 컨트롤러
 * 
 * Updated on : 2015. 8. 10. Updated by : 서대영, SK플래닛 
 */

@Controller
@RequestMapping(value = "/example/excetpion")
public class ExceptionSampleController {
	
	@RequestMapping(value = "/business")
	public void business(@RequestParam(required=false, defaultValue="SAC_CNM_9999") String code, @RequestParam(required=false) String[] args) {
		throw new StorePlatformException(code, (Object[]) args);
	}
	
	@RequestMapping(value = "/system")
	public void system() {
		throw new StorePlatformError("SYS_ERROR");
	}
	
	@RequestMapping(value = "/database")
	public void database() throws SQLException {
		throw new SQLException("Test Reason", "Test SQLState", 1234);
	}
	
	@RequestMapping(value = "/network")
	public void network(@RequestParam(required=false) String mode) throws SocketException {
		if ("CON_REFUSED".equals(mode))
			throw new ConnectException("Connection refused: connect");
		if ("TIMEOUT_CON".equals(mode))
			throw new SocketException("connect timed out");
		if ("TIMEOUT_REQ".equals(mode))
			throw new SocketException("Read timed out");
		throw new SocketException("connection timed out: no further information");
	}
	
	@RequestMapping(value = "/httpMessageNotReadable")
	public void httpMessageNotReadable() {
		throw new HttpMessageNotReadableException("Could not read JSON");
	}
	
	@RequestMapping(value = "/xss")
	public void xss() {
		throw new StorePlatformXssInvalidException("SYS_ERROR_SECURITY_XSS", "<script/>");
	}
	
	@RequestMapping(value = "/hierarchy")
	public void hierarchy() {
		SocketTimeoutException e1 = new SocketTimeoutException("connect timed out");
		ResourceAccessException e2 = new ResourceAccessException("ResourceAccessException", e1);
		StorePlatformException ex = new StorePlatformException("EC_ISF_9999", e2);
		throw ex; 
	}
	
}
