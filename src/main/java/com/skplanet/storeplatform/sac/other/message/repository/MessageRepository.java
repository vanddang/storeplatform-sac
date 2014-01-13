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

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

/**
 * 
 * Message 연동 Repository
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface MessageRepository {

	/**
	 * 
	 * <pre>
	 * SMS 연동.
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
	 * MMS 연동.
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
	 * AOM 연동.
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
	 * Email 연동.
	 * </pre>
	 * 
	 * @param emailReq
	 *            emailReq
	 * @return Map
	 */
	public Map<String, String> emailSend(EmailSendReq emailReq);

}
