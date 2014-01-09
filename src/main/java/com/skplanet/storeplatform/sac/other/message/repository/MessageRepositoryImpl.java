/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.repository;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

@Profile(value = { "stag", "real" })
@Service
public class MessageRepositoryImpl implements MessageRepository {

	@Override
	public Map<String, String> smsSend(SmsSendReq smsReq) {
		return null;
	}

	@Override
	public Map<String, String> mmsSend(MmsSendReq mmsReq) {
		return null;
	}

	@Override
	public Map<String, String> aomSend(AomSendReq aomReq) {
		return null;
	}

	@Override
	public Map<String, String> emailSend(EmailSendReq emailReq) {
		return null;
	}

}
