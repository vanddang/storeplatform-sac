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

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

/**
 * 
 * Message 연동 Repository Sample
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "dev", "local" })
@Service
public class MessageRepositorySampleImpl implements MessageRepository {

	@Override
	public Map<String, String> smsSend(SmsSendReq smsReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	@Override
	public Map<String, String> mmsSend(MmsSendReq mmsReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	@Override
	public Map<String, String> aomSend(AomSendReq aomReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	@Override
	public Map<String, String> emailSend(EmailSendReq emailReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

}
