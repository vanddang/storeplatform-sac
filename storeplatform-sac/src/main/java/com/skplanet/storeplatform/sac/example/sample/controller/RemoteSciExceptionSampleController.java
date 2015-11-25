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

import java.net.SocketException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skplanet.storeplatform.external.client.example.sample.sci.ExceptoinSampleSCI;

/**
 * 예외 테스트 컨트롤러
 * 
 * Updated on : 2015. 8. 10. Updated by : 서대영, SK플래닛 
 */

@Controller
@RequestMapping(value = "/example/exception/remoteSci")
public class RemoteSciExceptionSampleController {
	
	@Autowired
	private ExceptoinSampleSCI remoteSci;
	
	@RequestMapping(value = "/business")
	public void business(@RequestParam(required=false, defaultValue="SAC_CNM_9999") String code, @RequestParam(required=false) String[] args) {
		remoteSci.business(code, args);
	}
	
	@RequestMapping(value = "/system")
	public void system() {
		remoteSci.system();
	}
	
	@RequestMapping(value = "/database")
	public void database() throws SQLException {
		remoteSci.database();
	}
	
	@RequestMapping(value = "/network")
	public void network(@RequestParam(required=false) String mode) throws SocketException {
		remoteSci.network(mode);
	}
	
	@RequestMapping(value = "/httpMessageNotReadable")
	public void httpMessageNotReadable() {
		remoteSci.httpMessageNotReadable();
	}
	
	@RequestMapping(value = "/xss")
	public void xss() {
		remoteSci.xss();
	}
	
	@RequestMapping(value = "/hierarchy")
	public void hierarchy() {
		remoteSci.hierarchy();
	}
	
}
