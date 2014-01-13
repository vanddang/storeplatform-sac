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

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

/**
 * 
 * Message 전송 Service
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface MessageService {

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
	public Map<String, String> smsSend(SmsSendReq smsReq);

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
	public Map<String, String> mmsSend(MmsSendReq mmsReq);

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
	public Map<String, String> aomSend(AomSendReq aomReq);

	/**
	 * 
	 * <pre>
	 * Email 전송.
	 * </pre>
	 * 
	 * @param emailReq
	 *            emailReq
	 * @return Map
	 */
	public Map<String, String> emailSend(EmailSendReq emailReq);
}
