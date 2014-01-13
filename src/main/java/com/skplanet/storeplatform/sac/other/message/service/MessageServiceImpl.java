/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;
import com.skplanet.storeplatform.sac.other.message.repository.MessageRepository;

/**
 * 
 * Message 전송 Service 구현체
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Map<String, String> smsSend(SmsSendReq smsReq) {
		return this.messageRepository.smsSend(smsReq);
	}

	@Override
	public Map<String, String> mmsSend(MmsSendReq mmsReq) {
		return this.messageRepository.mmsSend(mmsReq);
	}

	@Override
	public Map<String, String> aomSend(AomSendReq aomReq) {
		return this.messageRepository.aomSend(aomReq);
	}

	@Override
	public Map<String, String> emailSend(EmailSendReq emailReq) {
		return this.messageRepository.emailSend(emailReq);
	}
}
