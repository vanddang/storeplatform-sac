/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.message.vo.AomReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsReq;
import com.skplanet.storeplatform.sac.other.message.service.AomService;
import com.skplanet.storeplatform.sac.other.message.service.EmailService;
import com.skplanet.storeplatform.sac.other.message.service.MmsService;
import com.skplanet.storeplatform.sac.other.message.service.SmsService;

/**
 * 
 * Calss 설명(E/C Bypass 예정)
 * 
 * Updated on : 2014. 1. 2. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping(value = "/other/message")
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private SmsService smsService;

	@Autowired
	private MmsService mmsService;

	@Autowired
	private AomService aomService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/sms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> smsSend(SmsReq smsReq) {
		return this.smsService.send(smsReq);
	}

	@RequestMapping(value = "/mms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mmsSend(MmsReq mmsReq) {
		return this.mmsService.send(mmsReq);
	}

	@RequestMapping(value = "/aom/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> aomSend(AomReq aomReq) {
		return this.aomService.send(aomReq);
	}

	@RequestMapping(value = "/email/send/v1", method = RequestMethod.POST)
	public Map<String, String> emailSend(EmailReq emailReq) {
		return this.emailService.send(emailReq);
	}

}
