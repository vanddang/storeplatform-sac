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

import com.skplanet.storeplatform.external.client.message.vo.AomReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsReq;

/**
 * 
 * Message E/C를 연동하는 Repository Sample Class 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "dev", "local" })
@Service
public class MessageRepositorySampleImpl implements MessageRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.repository.MessageRepository#smsSend(com.skplanet.storeplatform.
	 * external.client.message.vo.SmsReq)
	 */
	@Override
	public Map<String, String> smsSend(SmsReq smsReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.repository.MessageRepository#mmsSend(com.skplanet.storeplatform.
	 * external.client.message.vo.MmsReq)
	 */
	@Override
	public Map<String, String> mmsSend(MmsReq mmsReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.repository.MessageRepository#aomSend(com.skplanet.storeplatform.
	 * external.client.message.vo.AomReq)
	 */
	@Override
	public Map<String, String> aomSend(AomReq aomReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.message.repository.MessageRepository#emailSend(com.skplanet.storeplatform
	 * .external.client.message.vo.EmailReq)
	 */
	@Override
	public Map<String, String> emailSend(EmailReq emailReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("resultStatus", "success");
		return result;
	}

}
