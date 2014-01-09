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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;
import com.skplanet.storeplatform.sac.other.message.service.MessageService;

@Controller
@RequestMapping(value = "/other/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/sms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> smsSend(SmsSendReq smsReq) {
		return this.messageService.smsSend(smsReq);
	}

	@RequestMapping(value = "/mms/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mmsSend(MmsSendReq mmsReq) {
		return this.messageService.mmsSend(mmsReq);
	}

	@RequestMapping(value = "/aom/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> aomSend(AomSendReq aomReq) {
		return this.messageService.aomSend(aomReq);
	}

	@RequestMapping(value = "/email/send/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> emailSend(EmailSendReq emailReq) {
		return this.messageService.emailSend(emailReq);
	}

}
