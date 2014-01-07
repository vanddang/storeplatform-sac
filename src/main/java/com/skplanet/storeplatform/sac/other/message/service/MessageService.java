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

import com.skplanet.storeplatform.external.client.message.vo.AomReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsReq;

/**
 * 
 * Message Service Class
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스.
 */
public interface MessageService {
	/**
	 * 
	 * <pre>
	 * SMS 연동.
	 * </pre>
	 * 
	 * @param smsReq
	 * @return Map
	 */
	public Map<String, String> smsSend(SmsReq smsReq);

	/**
	 * 
	 * <pre>
	 * MMS 연동.
	 * </pre>
	 * 
	 * @param mmsReq
	 *            mmsReq
	 * @return Map
	 */
	public Map<String, String> mmsSend(MmsReq mmsReq);

	/**
	 * 
	 * <pre>
	 * AOM 연동.
	 * </pre>
	 * 
	 * @param aomReq
	 *            aomReq
	 * @return Map
	 */
	public Map<String, String> aomSend(AomReq aomReq);

	/**
	 * 
	 * <pre>
	 * EMAIL 연동.
	 * </pre>
	 * 
	 * @param emailReq
	 *            emailReq
	 * @return Map
	 */
	public Map<String, String> emailSend(EmailReq emailReq);
}
