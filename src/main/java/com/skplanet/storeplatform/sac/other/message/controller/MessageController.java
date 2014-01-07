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
import com.skplanet.storeplatform.sac.other.message.service.MessageService;

/**
 * 
 * Message Controller 기능
 * 
 * Updated on : 2014. 1. 2. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping(value = "/other/message")
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	/**
	 * 
	 * <pre>
	 * SMS 전송.
	 * </pre>
	 * 
	 * @param smsReq
	 *            smsReq
	 * @return Map
	 */
	@RequestMapping(value = "/sms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> smsSend(SmsReq smsReq) {
		LOGGER.debug("####################################################");
		LOGGER.debug("#######  8.SMS전송                              ########");
		LOGGER.debug("####################################################");
		return this.messageService.smsSend(smsReq);
	}

	/**
	 * 
	 * <pre>
	 * MMS 전송.
	 * </pre>
	 * 
	 * @param mmsReq
	 *            mmsReq
	 * @return Map
	 */
	@RequestMapping(value = "/mms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mmsSend(MmsReq mmsReq) {
		LOGGER.debug("####################################################");
		LOGGER.debug("#######  9.MMS전송                              ########");
		LOGGER.debug("####################################################");
		return this.messageService.mmsSend(mmsReq);
	}

	/**
	 * 
	 * <pre>
	 * AOM 전송.
	 * </pre>
	 * 
	 * @param aomReq
	 *            aomReq
	 * @return Map
	 */
	@RequestMapping(value = "/aom/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> aomSend(AomReq aomReq) {
		LOGGER.debug("####################################################");
		LOGGER.debug("#######  10.AOM전송                             ########");
		LOGGER.debug("####################################################");
		return this.messageService.aomSend(aomReq);
	}

	/**
	 * 
	 * <pre>
	 * EMAIL 전송.
	 * </pre>
	 * 
	 * @param emailReq
	 *            emailReq
	 * @return Map
	 */
	@RequestMapping(value = "/email/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> emailSend(EmailReq emailReq) {
		LOGGER.debug("####################################################");
		LOGGER.debug("#######  11.Email전송                           ########");
		LOGGER.debug("####################################################");
		return this.messageService.emailSend(emailReq);
	}

}
