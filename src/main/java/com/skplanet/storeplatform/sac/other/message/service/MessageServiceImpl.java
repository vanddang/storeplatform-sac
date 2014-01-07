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

import com.skplanet.storeplatform.external.client.message.vo.AomReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsReq;
import com.skplanet.storeplatform.sac.other.message.repository.MessageRepository;

/**
 * 
 * Message Service Class 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스.
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.service.MessageService#smsSend(com.skplanet.storeplatform.external
	 * .client.message.vo.SmsReq)
	 */
	@Override
	public Map<String, String> smsSend(SmsReq smsReq) {

		return this.messageRepository.smsSend(smsReq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.service.MessageService#mmsSend(com.skplanet.storeplatform.external
	 * .client.message.vo.MmsReq)
	 */
	@Override
	public Map<String, String> mmsSend(MmsReq mmsReq) {

		return this.messageRepository.mmsSend(mmsReq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.service.MessageService#aomSend(com.skplanet.storeplatform.external
	 * .client.message.vo.AomReq)
	 */
	@Override
	public Map<String, String> aomSend(AomReq aomReq) {

		return this.messageRepository.aomSend(aomReq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.service.MessageService#emailSend(com.skplanet.storeplatform.external
	 * .client.message.vo.EmailReq)
	 */
	@Override
	public Map<String, String> emailSend(EmailReq emailReq) {

		return this.messageRepository.emailSend(emailReq);
	}

}
